package com.directparking.app.util;

import android.content.Context;
import android.graphics.Bitmap;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

public class FileCacher<T> {

    private Context context;

    /**
     * Create an instance of FileCacher
     *
     * @param context App context
     */
    public FileCacher(Context context) {
        this.context = context;
    }

    public void writeCache(String fileName, String filePath) throws IOException {
        Timber.d("Writing cache...");
        if (hasCache(fileName)) {
            Timber.d("Clearing existing cache...");
            clearCache(fileName);
        }

        FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
        fos.write(FileUtil.convertFileToByteArray(filePath));
        fos.flush();
        fos.close();
    }

    public void writeCache(String fileName, Bitmap bitmap) throws IOException {
        Timber.d("Writing cache...");
        if (hasCache(fileName)) {
            Timber.d("Clearing existing cache...");
            clearCache(fileName);
        }

        FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, fos);
        fos.flush();
        fos.close();
    }

    /**
     * Append an object if cache file exists. Otherwise, create a new file and write an object.
     *
     * @param object any object which could be anything, e.g. int, String[], ArrayList<Object> etc.
     * @throws IOException
     */
    public void appendOrWriteCache(String fileName, T object) throws IOException {
        FileOutputStream fos;
        ObjectOutputStream oos;
        if (hasCache(fileName)) {
            fos = context.openFileOutput(fileName, Context.MODE_APPEND);
            oos = new ObjectOutputStream(fos) {
                protected void writeStreamHeader() throws IOException {
                    reset();
                }
            };
        } else {
            fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
        }
        oos.writeObject(object);
        oos.flush();
        oos.close();

        fos.close();
    }

    /**
     * Read object back from the cache.
     * Make sure it is not NULL before using it.
     *
     * @return cache object
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public T readCache(String fileName) throws IOException {
        FileInputStream fis = context.openFileInput(fileName);
        ObjectInputStream ois = new ObjectInputStream(fis);
        Object object = null;
        try {
            object = ois.readObject();
        } catch (ClassNotFoundException e) {
            Timber.e(e);
        }
        fis.close();
        return (T) object;
    }

    /**
     * get all caches
     *
     * @return List of caches
     * @throws IOException
     */
    public List<T> getAllCaches(String fileName) throws IOException {
        FileInputStream fis = context.openFileInput(fileName);
        ObjectInputStream ois = new ObjectInputStream(fis);
        Object object = null;
        List<T> cacheList = new ArrayList<T>();
        try {
            while ((object = ois.readObject()) != null) {
                cacheList.add((T) object);
            }
        } catch (ClassNotFoundException e) {
            Timber.e(e);
        } catch (EOFException e) {
            fis.close();
        }

        return cacheList;
    }

    /**
     * delete cache file
     *
     * @throws IOException
     */
    public boolean clearCache(String fileName) throws IOException {
        boolean success = context.deleteFile(fileName);
        if (success) {
            return true;
        }
        return false;
    }

    /**
     * get file path
     *
     * @return String of file path
     */
    public String getFilePath(String fileName) {
        return context.getFileStreamPath(fileName).getPath();
    }

    /**
     * Check whether the cache exists or not.
     *
     * @return 'true' if cache exists or 'false' if cache does not exists.
     */
    public boolean hasCache(String fileName) {
        File file = context.getFileStreamPath(fileName);
        if (file.exists()) {
            return true;
        }
        return false;
    }

    /**
     * get cache size
     *
     * @return long of cache size
     */
    public long getSize(String fileName) {
        long size = -1;
        File file = context.getFileStreamPath(fileName);

        if (file.exists()) {
            size = file.length();
        }

        return size;
    }
}