package com.directparking.app.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.util.Base64;

import com.directparking.app.data.user.UserManager;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import id.zelory.compressor.Compressor;
import timber.log.Timber;

import static com.directparking.app.util.Constants.UPLOAD_IMAGE_QUALITY;

public class FileUtil {

    private FileUtil() {
    }

    public static String convertFileToBase64EncodedString(String filePath) {
        try {
            return Base64.encodeToString(convertFileToByteArray(filePath), Base64.DEFAULT);
        } catch (Exception e) {
            Timber.e(e);
        }
        return "";
    }

    public static byte[] convertFileToByteArray(String filePath) {
        File file = new File(filePath.replace("file:", ""));
        try {
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            try {
                for (int readNum; (readNum = fis.read(buf)) != -1; ) {
                    bos.write(buf, 0, readNum);
                }
            } catch (IOException e) {
                Timber.e(e);
            }
            byte[] byteArray = bos.toByteArray();
            return byteArray;
        } catch (Exception e) {
            Timber.e(e);
            return new byte[]{};
        }
    }

    public static String getFileNameWithoutExt(String filePath) {
        String fileNameWithExt = new File(filePath).getName();
        int pos = fileNameWithExt.lastIndexOf(".");
        return pos > 0 ? fileNameWithExt.substring(0, pos) : fileNameWithExt;
    }

    public static String getFileExt(String filePath) {
        return filePath.substring(filePath.lastIndexOf(".") + 1);
    }

    public static String getFileSizeInMb(String filePath) {
        long fileSizeInBytes = new File(filePath).length();
        long fileSizeInKB = fileSizeInBytes / 1024;
        long fileSizeInMB = fileSizeInKB / 1024;
        return String.valueOf(fileSizeInMB);
    }

    public static Uri compressFile(Context context, File actualFile) throws IOException {
        Timber.d("Compressing file...");

        File compressedFile = new Compressor(context)
                .setQuality(UPLOAD_IMAGE_QUALITY)
                .setCompressFormat(Bitmap.CompressFormat.JPEG)
                .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_PICTURES).getAbsolutePath())
                .compressToFile(actualFile);

        return Uri.parse(compressedFile.getPath());
    }

    public static void storeFileInCache(String fileName, String filePath, UserManager userManager, FileCacher<File> fileCacher) {
        Timber.d("Storing file in cache...");
        try {
            fileCacher.writeCache(fileName, filePath);
            userManager.setProfilePath(new File(fileCacher.getFilePath(fileName)).toURI().toString());
        } catch (IOException e) {
            Timber.e(e, "Exception:");
        }
    }

    public static void storeBitmapInCache(String fileName, Bitmap bitmap, UserManager userManager, FileCacher<File> fileCacher) {
        Timber.d("Storing file in cache...");
        try {
            fileCacher.writeCache(fileName, bitmap);
            userManager.setProfilePath(new File(fileCacher.getFilePath(fileName)).toURI().toString());
        } catch (IOException e) {
            Timber.e(e, "Exception:");
        }
    }

}