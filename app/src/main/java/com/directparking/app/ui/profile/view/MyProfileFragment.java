package com.directparking.app.ui.profile.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.text.Layout;
import android.text.TextUtils;
import android.text.style.AlignmentSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.directparking.app.BaseApplication;
import com.directparking.app.R;
import com.directparking.app.data.user.UserManager;
import com.directparking.app.ui.base.BaseFragment;
import com.directparking.app.ui.custom.SpannyText;
import com.directparking.app.ui.login.model.CarData;
import com.directparking.app.ui.login.model.UserData;
import com.directparking.app.ui.profile.edit.EditProfileView;
import com.directparking.app.ui.profile.edit.ProfilePresenter;
import com.directparking.app.ui.profile.view.model.MyProfileResponse;
import com.directparking.app.util.AlertUtil;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import timber.log.Timber;

public class MyProfileFragment extends BaseFragment implements MyProfileView, EditProfileView {

    private View rootView;
    private Unbinder unbinder;
    private Callback callback;

    @Inject
    Picasso picasso;

    @Inject
    UserManager userManager;

    @BindView(R.id.civ_profile)
    CircleImageView civProfile;

    @BindView(R.id.tv_rating)
    TextView tvRating;

    @BindView(R.id.tv_name)
    TextView tvName;

    @OnClick(R.id.tv_history)
    void showHistory() {
        if (callback != null)
            callback.showUserHistory();
    }

    @BindView(R.id.et_university)
    TextInputEditText etUniversity;

    @BindView(R.id.et_email)
    TextInputEditText etEmail;

    @BindView(R.id.et_dob)
    TextInputEditText etDob;

    @BindView(R.id.et_phone)
    TextInputEditText etPhone;

    @BindView(R.id.et_color)
    TextInputEditText etColor;

    @BindView(R.id.et_model)
    TextInputEditText etModel;

    @BindView(R.id.et_car_make)
    TextInputEditText etCarMake;

    @BindView(R.id.et_plate_no)
    TextInputEditText etPlateNo;

    @BindView(R.id.et_licence_no)
    TextInputEditText etLicenceNo;

    @Inject
    ProfilePresenter presenter;

    public static MyProfileFragment newInstance() {
        return new MyProfileFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((BaseApplication) getActivity().getApplication()).getHomeComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_my_profile, container, false);
        unbinder = ButterKnife.bind(this, rootView);

        try {
            picasso.load(userManager.getProfilePath())
                    .placeholder(R.drawable.ic_loading)
                    .error(R.drawable.ic_default_profile)
                    .into(civProfile);
        } catch (Exception e) {
            Timber.e(e);
            picasso.load(R.drawable.ic_default_profile).fit().into(civProfile);
        }
        presenter.setView(this);
        presenter.getProfile();
        return rootView;
    }

    @Override
    public void showMessage(String msg) {
        AlertUtil.showSnackBar(rootView, msg);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callback = (Callback) context;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onDetach() {
        callback = null;
        super.onDetach();
    }

    @Override
    public void showHomeScreen(String msg) {

    }

    @Override
    public void myProfileData(MyProfileResponse myProfileResponse) {
        UserData userData = myProfileResponse.getUserDetails();
        CarData carData = myProfileResponse.getCarDetails();

        tvName.setText(String.format("%s %s", userData.getFirstName(), userData.getLastName()));

        if (!TextUtils.isEmpty(userData.getRating() + "")) {
            SpannyText spanny = new SpannyText(userData.getRating() + "")
                    .append(" ")
                    .append("★", new RelativeSizeSpan(0.8f), new ForegroundColorSpan(getResources().getColor(R.color.yellow)),
                            new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER));
            tvRating.setText(spanny);
        }

        etUniversity.setText(userData.getUniversity().getName());
        etEmail.setText(userData.getEmail());
        etDob.setText(userData.getDob());
        etPhone.setText(userData.getPhone());


        etColor.setText(carData.getColor());
        etModel.setText(carData.getModel());
        etCarMake.setText(carData.getCarMake());
        etPlateNo.setText(carData.getPlateNumber());
        etLicenceNo.setText(carData.getLicenceNumber());
    }


    public interface Callback {
        void showUserHistory();
    }
}