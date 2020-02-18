package com.directparking.app.ui.profile.edit;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.directparking.app.BaseApplication;
import com.directparking.app.R;
import com.directparking.app.data.user.UserManager;
import com.directparking.app.ui.base.BaseFragment;
import com.directparking.app.ui.custom.country.CountryCodePicker;
import com.directparking.app.ui.login.model.CarData;
import com.directparking.app.ui.login.model.UserData;
import com.directparking.app.ui.profile.view.model.MyProfileResponse;
import com.directparking.app.util.AlertUtil;
import com.directparking.app.util.FileUtil;
import com.directparking.app.util.validation.BirthDate;
import com.google.gson.Gson;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.Validator.ValidationListener;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Order;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.mobsandgeeks.saripaar.annotation.Pattern;
import com.squareup.picasso.Picasso;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.enums.EPickType;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import timber.log.Timber;

import static com.directparking.app.util.Constants.IMAGE_UPLOAD_COMPRESS_LIMIT;
import static com.directparking.app.util.Constants.IMAGE_UPLOAD_MAX_SIZE;
import static com.mobsandgeeks.saripaar.annotation.Password.Scheme.ALPHA_NUMERIC;

public class EditProfileFragment extends BaseFragment implements EditProfileView, ValidationListener {

    private View rootView;
    private Unbinder unbinder;
    private Callback callback;
    private String profilePath;
    private Validator validator;

    @Inject
    Picasso picasso;

    @Inject
    UserManager userManager;

    @Inject
    ProfilePresenter presenter;

    @BindView(R.id.civ_profile)
    CircleImageView civProfile;

    @Order(1)
    @NotEmpty(sequence = 1, trim = true, messageResId = R.string.empty_name_msg)
    @Pattern(sequence = 2, regex = "[A-Za-z\\s]+", messageResId = R.string.invalid_name_msg)
    @BindView(R.id.et_first_name)
    TextInputEditText etFirstName;

    @Order(2)
    @NotEmpty(sequence = 1, trim = true, messageResId = R.string.empty_name_msg)
    @Pattern(sequence = 2, regex = "[A-Za-z\\s]+", messageResId = R.string.invalid_name_msg)
    @BindView(R.id.et_last_name)
    TextInputEditText etLastName;

    @BindView(R.id.et_university)
    TextInputEditText etUniversity;

    @BindView(R.id.et_email)
    TextInputEditText etEmail;

    @Order(3)
    @NotEmpty(sequence = 1, trim = true, messageResId = R.string.empty_dob_msg)
    @BirthDate(sequence = 2, messageResId = R.string.invalid_dob_msg)
    @BindView(R.id.et_dob)
    TextInputEditText etDob;

    @Order(4)
    @NotEmpty(sequence = 1, trim = true, messageResId = R.string.empty_password_msg)
    @Password(sequence = 2, min = 8, scheme = ALPHA_NUMERIC, messageResId = R.string.invalid_password_msg)
    @BindView(R.id.et_password)
    TextInputEditText etPassword;

    @BindView(R.id.country_code_picker)
    CountryCodePicker countryCodePicker;

    @BindView(R.id.et_phone)
    TextInputEditText etPhone;

    @Order(5)
    @NotEmpty(sequence = 1, trim = true, messageResId = R.string.empty_color_msg)
    @BindView(R.id.et_color)
    TextInputEditText etColor;

    @Order(6)
    @NotEmpty(sequence = 1, trim = true, messageResId = R.string.empty_model_msg)
    @BindView(R.id.et_model)
    TextInputEditText etModel;

    @Order(7)
    @NotEmpty(sequence = 1, trim = true, messageResId = R.string.empty_car_make_msg)
    @BindView(R.id.et_car_make)
    TextInputEditText etCarMake;

    @Order(8)
    @NotEmpty(sequence = 1, trim = true, messageResId = R.string.empty_plate_msg)
    @BindView(R.id.et_plate_no)
    TextInputEditText etPlateNo;

    @Order(9)
    @NotEmpty(sequence = 1, trim = true, messageResId = R.string.empty_licence_msg)
    @BindView(R.id.et_licence_no)
    TextInputEditText etLicenceNo;

    @OnClick(R.id.civ_profile)
    void launchCamera() {
        PickSetup pickSetup = new PickSetup()
                .setTitle("Choose")
                .setPickTypes(EPickType.GALLERY, EPickType.CAMERA)
                .setSystemDialog(true);

        PickImageDialog.build(pickSetup).setOnPickResult(result -> {
            if (result.getError() == null) {

                Uri uri = Uri.parse(result.getPath());

                Timber.d("Uri: %s", uri);

                File file = new File(uri.toString());

                if (file.exists()) {
                    long fileSizeInBytes = file.length();
                    long fileSizeInMB = fileSizeInBytes / 1048576;

                    Timber.d("File size: " + fileSizeInBytes + " KB");

                    if (fileSizeInMB >= IMAGE_UPLOAD_MAX_SIZE) {
                        AlertUtil.showToast(context(), getString(R.string.file_upload_size_error));
                        return;
                    }

                    if (fileSizeInBytes > IMAGE_UPLOAD_COMPRESS_LIMIT) {
                        try {
                            uri = FileUtil.compressFile(context(), file);
                            File newFile = new File(uri.toString());
                            long newFileSizeInBytes = newFile.length();
                            Timber.d("New File size: " + newFileSizeInBytes + " KB");

                        } catch (IOException e) {
                            Timber.e(e);
                            AlertUtil.showToast(getContext(), e.getMessage());
                            return;
                        }
                    }

                    profilePath = uri.toString();

                    if (!uri.toString().contains("content://")) {
                        uri = Uri.fromFile(new File(uri.toString()));
                    }

                    picasso.load(uri).resize(100, 100).centerCrop().into(civProfile);
                } else {
                    Timber.d("File doesn't exist!");
                }
            } else {
                AlertUtil.showToast(context(), result.getError().getMessage());
            }
        }).show(getActivity());
    }

    @OnClick(R.id.btn_submit)
    void submit() {
        validator.validate();
    }

    public static EditProfileFragment newInstance() {
        return new EditProfileFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callback = (Callback) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((BaseApplication) getActivity().getApplication()).getHomeComponent().inject(this);

        validator = new Validator(this);
        validator.setValidationMode(Validator.Mode.IMMEDIATE);
        validator.setValidationListener(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_edit_profile, container, false);
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

        UserData userData = new Gson().fromJson(userManager.getUserData(), UserData.class);

        etFirstName.setText(userData.getFirstName());
        etLastName.setText(userData.getLastName());
        etUniversity.setText(userData.getUniversity().getName());
        etEmail.setText(userData.getEmail());
        etDob.setText(userData.getDob());
        etPassword.setText(userData.getPassword());
        countryCodePicker.setCountryForPhoneCode(userData.getPhoneCode());
        etPhone.setText(userData.getPhone());


        CarData carData = new Gson().fromJson(userManager.getCarData(), CarData.class);

        etColor.setText(carData.getColor());
        etModel.setText(carData.getModel());
        etCarMake.setText(carData.getCarMake());
        etPlateNo.setText(carData.getPlateNumber());
        etLicenceNo.setText(carData.getLicenceNumber());

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        presenter.setView(this);
    }

    @Override
    public void onValidationSucceeded() {
        UserData userData = new UserData.Builder()
                .setUserId(userManager.getUserId())
                .setFirstName(etFirstName.getText().toString().trim())
                .setLastName(etLastName.getText().toString().trim())
                .setDob(etDob.getText().toString().trim())
//              .setPassword(etPassword.getText().toString().trim())
                .setProfile(!TextUtils.isEmpty(profilePath) ? profilePath : null)

                .build();

        CarData carData = new CarData.Builder()
                .setColor(etColor.getText().toString().trim())
                .setModel(etModel.getText().toString().trim())
                .setCarMake(etCarMake.getText().toString().trim())
                .setPlateNumber(etPlateNo.getText().toString().trim())
                .setLicenceNumber(etLicenceNo.getText().toString().trim())
                .build();

        presenter.updateProfile(userData, carData);
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String[] listMessage = error.getCollatedErrorMessage(context()).split("\n");
            String msg = listMessage[0];

            if (view instanceof EditText) {
                ((EditText) view).setError(msg);
                view.requestFocus();
            } else {
                showMessage(msg);
            }
        }
    }

    @Override
    public void showMessage(String msg) {
        AlertUtil.showSnackBar(rootView, msg);
    }

    @Override
    public void showHomeScreen(String msg) {
        AlertUtil.showToast(context(), msg);

        if (callback != null) {
            callback.showProfileDetail();
        }
    }

    @Override
    public void myProfileData(MyProfileResponse myProfileResponse) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        presenter.destroy();
        unbinder.unbind();
    }

    @Override
    public void onDetach() {
        callback = null;
        super.onDetach();
    }

    public interface Callback {
        void showProfileDetail();
    }
}