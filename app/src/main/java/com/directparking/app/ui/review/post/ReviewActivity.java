package com.directparking.app.ui.review.post;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.directparking.app.BaseApplication;
import com.directparking.app.R;
import com.directparking.app.ui.base.BaseActivity;
import com.directparking.app.ui.home.HomeActivity;
import com.directparking.app.ui.review.post.model.RideDetailResponse;
import com.directparking.app.util.AlertUtil;
import com.directparking.app.util.Util;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import timber.log.Timber;

public class ReviewActivity extends BaseActivity implements ReviewView, Validator.ValidationListener {

    Validator validator;
    @Inject
    ReviewPresenter presenter;

    @Inject
    Picasso picasso;

    @BindView(R.id.tv_arrive_time)
    TextView tvArrivalTime;

    @BindView(R.id.tv_ride_distance)
    TextView tvRideDistance;

    @BindView(R.id.tv_user_name)
    TextView tvUsername;

    @BindView(R.id.tv_ride_time)
    TextView tvRideTime;

    @BindView(R.id.tv_pickup_address)
    TextView tvPickupAddress;

    @BindView(R.id.tv_drop_address)
    TextView tvDropAddress;

    //    @Order(1)
//    @NotEmpty(sequence = 1, messageResId = R.string.empty_review_msg)
    @BindView(R.id.et_rider_driver_review)
    TextInputEditText etRiderUserReview;

    @BindView(R.id.civ_rider_driver_profile)
    CircleImageView civUserImage;

    @BindView(R.id.rb_rider_driver_rating)
    RatingBar rbUserRating;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.btn_submit_review_rating)
    Button btnSubmitRating;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    String rideId;
    String totalRideTime, isRider, relatedId;

    @OnClick(R.id.btn_submit_review_rating)
    void onSubmitRating(View v) {
//        validator.validate();
        presenter.callRatingSubmit(relatedId, rbUserRating.getRating() + "",
                etRiderUserReview.getText().toString(), rideId);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        ButterKnife.bind(this);
        ((BaseApplication) getApplication()).getReviewComponent().inject(this);
        rideId = getIntent().getExtras().getString("rideId");
        totalRideTime = getIntent().getExtras().getString("totalTime");
        isRider = getIntent().getExtras().getString("isRider");
        relatedId = getIntent().getExtras().getString("relatedId");
        tvArrivalTime.setText(totalRideTime);

//        SimpleDateFormat totalTime = new SimpleDateFormat("HH:mm:ss");
//        try {
//            Date ti = totalTime.parse(totalRideTime);
//            String aa = totalTime.format(ti);
//            Log.e("dad", aa);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }

        presenter.setView(this);
        toolbar.setTitle(getString(R.string.title_review_trip));
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
       /* if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }*/
        validator = new Validator(this);
        validator.setValidationMode(Validator.Mode.IMMEDIATE);
        validator.setValidationListener(this);
        presenter.getRideDetail(rideId, isRider);
    }

    @Override
    public void showSuccessMessage(String msg) {
        AlertUtil.showToast(this, msg);
        finish();
        startActivity(new Intent(this, HomeActivity.class));
    }

    @Override
    public void showRideData(RideDetailResponse data) {
        double rideDistance = Util.distance(data.getRideLocationData().getDrop_lat(), data.getRideLocationData().getDrop_log(), data.getRideLocationData().getPick_lat(), data.getRideLocationData().getPick_log());
        String distanceinMiles = String.valueOf(rideDistance);
        String distanceinMiless = distanceinMiles.length() > 5 ? distanceinMiles.subSequence(0, 5) + " Miles" : distanceinMiles + " Miles";

        tvRideDistance.setText(data.getRideLocationData().getDistance() + "m");
        tvUsername.setText(data.getUserDetails().getFirstName() + " " + data.getUserDetails().getLastName());
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Long.parseLong(data.getRideLocationData().getSchedule_time()));
        SimpleDateFormat df = new SimpleDateFormat("hh:mm aa");
        String rideTime = df.format(calendar.getTime());
        tvRideTime.setText(rideTime);
        tvPickupAddress.setText(data.getRideLocationData().getPick_address());
        tvDropAddress.setText(data.getRideLocationData().getDrop_address());
        try {
            picasso.load(data.getUserDetails().getProfile())
                    .placeholder(R.drawable.ic_loading)
                    .error(R.mipmap.ic_launcher)
                    .into(civUserImage);
        } catch (Exception e) {
            Timber.e(e);
            picasso.load(R.mipmap.ic_launcher).fit().into(civUserImage);
        }
    }

    @Override
    public void showProgressBar() {
        super.showProgressBar();
        progressBar.setVisibility(View.VISIBLE);
        btnSubmitRating.setVisibility(View.INVISIBLE);
    }

    @Override
    public void hideProgressBar() {
        super.hideProgressBar();
        progressBar.setVisibility(View.INVISIBLE);
        btnSubmitRating.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.destroy();
        ((BaseApplication) getApplication()).releaseReviewComponent();
    }

    @Override
    public void onValidationSucceeded() {
        presenter.callRatingSubmit(relatedId, rbUserRating.getRating() + "", etRiderUserReview.getText().toString(), rideId);
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

   /* @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }*/

}
