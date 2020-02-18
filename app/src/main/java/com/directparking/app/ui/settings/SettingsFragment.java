package com.directparking.app.ui.settings;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.directparking.app.R;
import com.directparking.app.ui.base.BaseFragment;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import butterknife.Unbinder;


public class SettingsFragment extends BaseFragment {

    @BindView(R.id.lv_settings)
    ListView lvSettings;

    @BindArray(R.array.setting_list)
    String listItems[];

    private View rootView;
    private Unbinder unbinder;
    private Callback callback;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_settings, container, false);
        unbinder = ButterKnife.bind(this, rootView);

        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(context(), android.R.layout.simple_list_item_1, listItems);
        lvSettings.setAdapter(stringArrayAdapter);

        return rootView;
    }

    @OnItemClick(R.id.lv_settings)
    public void onClickListItem(int position) {
        switch (position) {
            case 0:
                if (callback != null)
                    callback.openChangePasswordScreen();
                break;
            case 1:
                if (callback != null)
                    callback.openEditProfileScreen();
                break;

        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callback = (Callback) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callback = null;
        unbinder.unbind();
    }

    public interface Callback {
        void openChangePasswordScreen();

        void openEditProfileScreen();
    }
}
