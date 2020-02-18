package com.directparking.app.ui.signup;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.directparking.app.BaseApplication;
import com.directparking.app.R;
import com.directparking.app.ui.base.BaseActivity;
import com.directparking.app.ui.common.CustomSpinnerAdapter;
import com.directparking.app.ui.custom.DateFormatWatcher;
import com.directparking.app.ui.custom.country.CountryCodePicker;
import com.directparking.app.ui.login.model.CarData;
import com.directparking.app.ui.login.model.UserData;
import com.directparking.app.ui.signup.model.IdNameItem;
import com.directparking.app.util.AlertUtil;
import com.directparking.app.util.Util;
import com.directparking.app.util.validation.BirthDate;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.Validator.ValidationListener;
import com.mobsandgeeks.saripaar.annotation.Checked;
import com.mobsandgeeks.saripaar.annotation.ConfirmEmail;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
//import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Order;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.mobsandgeeks.saripaar.annotation.Pattern;
import com.mobsandgeeks.saripaar.annotation.Select;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;

import static com.directparking.app.util.Constants.PRIVACY_URL;
import static com.directparking.app.util.Constants.TNC_URL;
import static com.mobsandgeeks.saripaar.annotation.Password.Scheme.ALPHA_NUMERIC_MIXED_CASE_SYMBOLS;

//import com.directparking.app.util.validation.Email;

public class SignupActivity extends BaseActivity implements SignupView, ValidationListener {

    private Validator validator;
    private int selectedUniversityIndex;
    private List<IdNameItem> universities;
    private View rootview;
    List<String> universityNames;

    @Inject
    SignupPresenter presenter;

    @BindView(R.id.layout_main)
    RelativeLayout layoutMain;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

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

    @Order(3)
    @Select(messageResId = R.string.select_university_msg)
    @BindView(R.id.sp_university)
    Spinner spUniversity;

    @BindView(R.id.autoTv_university)
    AutoCompleteTextView autoTvUniversity;


    @OnItemSelected(R.id.sp_university)
    void universitySelected(int selectedUniversityIndex) {
        if (selectedUniversityIndex != 0) {
            this.selectedUniversityIndex = selectedUniversityIndex;
        }
    }


    //    @OnItemClick(R.id.autoTv_university)
//    void universityAutoSuggestionSelected(int selectedUniversityIndex) {
//        if (selectedUniversityIndex != 0) {
//            this.selectedUniversityIndex = selectedUniversityIndex;
//        }
//    }
    @Order(4)
    @NotEmpty(sequence = 1, trim = true, messageResId = R.string.empty_email_msg)
    @Email(sequence = 2, messageResId = R.string.invalid_email_msg)

    @BindView(R.id.et_email)
    TextInputEditText etEmail;

    @Order(5)
    @NotEmpty(sequence = 1, trim = true, messageResId = R.string.empty_confirm_email_msg)
    @ConfirmEmail(sequence = 2, messageResId = R.string.invalid_confirm_email_msg)
    @BindView(R.id.et_confirm_email)
    TextInputEditText etConfirmEmail;


    @Order(6)
    @NotEmpty(sequence = 1, trim = true, messageResId = R.string.empty_dob_msg)
    @BirthDate(sequence = 2, messageResId = R.string.invalid_dob_msg)
    @BindView(R.id.et_dob)
    TextInputEditText etDob;

    @Order(7)
    @NotEmpty(sequence = 1, trim = true, messageResId = R.string.empty_password_msg)
    @Password(sequence = 2, min = 8, scheme = ALPHA_NUMERIC_MIXED_CASE_SYMBOLS, messageResId = R.string.invalid_password_msg)
    @BindView(R.id.et_password)
    TextInputEditText etPassword;

    @Order(8)
    @NotEmpty(sequence = 1, trim = true, messageResId = R.string.empty_confirm_password_msg)
    @ConfirmPassword(sequence = 2, messageResId = R.string.password_mistach_msg)
    @BindView(R.id.et_confirm_password)
    TextInputEditText etConfirmPassword;

    @BindView(R.id.country_code_picker)
    CountryCodePicker countryCodePicker;

    //    @Order(7)
//    @NotEmpty(sequence = 1, trim = true, messageResId = R.string.empty_phone_msg)
//    @Length(sequence = 2, min = 10, messageResId = R.string.invalid_phone_msg)
    @BindView(R.id.et_phone)
    TextInputEditText etPhone;

    @Order(9)
    @NotEmpty(sequence = 1, trim = true, messageResId = R.string.empty_car_make_msg)
    @BindView(R.id.et_car_make)
    TextInputEditText etCarMake;

    @Order(10)
    @NotEmpty(sequence = 1, trim = true, messageResId = R.string.empty_model_msg)
    @BindView(R.id.et_model)
    TextInputEditText etModel;

    @Order(11)
    @NotEmpty(sequence = 1, trim = true, messageResId = R.string.empty_color_msg)
    @BindView(R.id.et_color)
    TextInputEditText etColor;

    @Order(12)
    @NotEmpty(sequence = 1, trim = true, messageResId = R.string.empty_plate_msg)
    @BindView(R.id.et_plate_no)
    TextInputEditText etPlateNo;

    @Order(13)
    @NotEmpty(sequence = 1, trim = true, messageResId = R.string.empty_licence_msg)
    @BindView(R.id.et_licence_no)
    TextInputEditText etLicenceNo;

    @Order(14)
    @Checked(messageResId =
            R.string.unaccept_tnc_msg)
    @BindView(R.id.cbTerms)
    CheckBox cbTnC;

    @BindView(R.id.btn_signup)
    Button btnSignup;

    @OnClick(R.id.btn_signup)
    void signup() {
        validator.validate();
    }

    @OnClick(R.id.tv_privacy)
    void showPrivacyPolicy() {
        Util.handleUrl(this, PRIVACY_URL);
    }

    @OnClick(R.id.tv_tnc)
    void showTnc() {
        Util.handleUrl(this, TNC_URL);
    }

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.til_university)
    TextInputLayout tilUniversities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);

        ((BaseApplication) getApplication()).getSignupComponent().inject(this);

        autoTvUniversity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("name & ID of university", universityNames.indexOf(autoTvUniversity.getText().toString()) + "");
//                selectedUniversityIndex = universities.indexOf(autoTvUniversity.getText().toString());

                selectedUniversityIndex = universityNames.indexOf(autoTvUniversity.getText().toString());
                Log.e("nID university =", selectedUniversityIndex + "");
            }
        });
        autoTvUniversity.setThreshold(2);
        etPassword.setTypeface(Typeface.DEFAULT);
        etPassword.setTransformationMethod(new PasswordTransformationMethod());

        etConfirmPassword.setTypeface(Typeface.DEFAULT);
        etConfirmPassword.setTransformationMethod(new PasswordTransformationMethod());

        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setTitle("Sign up");
        }

        validator = new Validator(this);
        validator.setValidationMode(Validator.Mode.IMMEDIATE);
        validator.setValidationListener(this);
        validator.registerAnnotation(Email.class);
        validator.registerAnnotation(ConfirmEmail.class);
        validator.registerAnnotation(BirthDate.class);

        cbTnC.setSaveEnabled(false);

        etDob.addTextChangedListener(new DateFormatWatcher());

        presenter.setView(this);
      //  presenter.fetchUniversities();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onValidationSucceeded() {
        UserData userData = new UserData.Builder()
                .setFirstName(etFirstName.getText().toString().trim())
                .setLastName(etLastName.getText().toString().trim())
               // .setUniversityId(universities.get(selectedUniversityIndex).getId())
                .setUniversityId("953")
                .setEmail(etEmail.getText().toString().trim())
                .setDob(etDob.getText().toString().trim())
                .setPassword(etPassword.getText().toString().trim())
                .setPhoneCode(countryCodePicker.getSelectedCountryCodeAsInt())
                .setPhone(etPhone.getText().toString().trim())
                .build();

        CarData carData = new CarData.Builder()
                .setColor(etColor.getText().toString().trim())
                .setModel(etModel.getText().toString().trim())
                .setCarMake(etCarMake.getText().toString().trim())
                .setPlateNumber(etPlateNo.getText().toString().trim())
                .setLicenceNumber(etLicenceNo.getText().toString().trim())
                .build();

        presenter.callSignup(userData, carData);
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String[] listMessage = error.getCollatedErrorMessage(this).split("\n");
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
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
        btnSignup.setVisibility(View.INVISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.INVISIBLE);
        btnSignup.setVisibility(View.VISIBLE);
    }

    @Override
    public void showMessage(String msg) {
        AlertUtil.showSnackBar(layoutMain, msg);
    }

    @Override
    public void showUniversities(List<IdNameItem> universities) {
        this.universities = universities;
        spUniversity.setAdapter(new CustomSpinnerAdapter(this, 0, spUniversity, universities));
//        spUniversity.setVisibility(View.VISIBLE);
        tilUniversities.setVisibility(View.GONE);

        universityNames = new ArrayList<>();
        for (int i = 0; i < this.universities.size(); i++) {
            universityNames.add(this.universities.get(i).getName());
        }

//        suggestionAdapter = new SuggestionAdapter(this, "");
//        suggestionAdapter.suggestions = universityNames;
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this, R.layout.item_select_autocomplete, universityNames);
        autoTvUniversity.setAdapter(adapter);
    }

    @Override
    public void setUniversitiesAdapter(List<IdNameItem> universities) {

    }

    @Override
    public void showLoginScreen(String msg) {
       AlertUtil.showMyActionAlertDialog(context(), "Please Confirm Email Address",msg,"OK",(dialog, which) ->
                finish());

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        presenter.destroy();
        ((BaseApplication) getApplication()).releaseSignupComponent();
    }
}