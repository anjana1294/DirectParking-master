package com.directparking.app.ui.confrimation;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.style.StyleSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.appolica.interactiveinfowindow.InfoWindowManager;
import com.appolica.interactiveinfowindow.fragment.MapInfoWindowFragment;
import com.directparking.app.BaseApplication;
import com.directparking.app.R;
import com.directparking.app.data.prefs.StringPreference;
import com.directparking.app.ui.base.BaseActivity;
import com.directparking.app.ui.confrimation.model.CancelResponse;
import com.directparking.app.ui.confrimation.model.ConfirmationResponse;
import com.directparking.app.ui.confrimation.model.RideData;
import com.directparking.app.ui.custom.SpannyText;
import com.directparking.app.ui.home.HomeActivity;
import com.directparking.app.ui.home.model.AddressData;
import com.directparking.app.util.AlertUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.parceler.Parcels;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.graphics.Typeface.BOLD;
import static com.directparking.app.util.Constants.DROP_ADDRESS;
import static com.directparking.app.util.Constants.PARKING_NAME_NUMBER;
import static com.directparking.app.util.Constants.PICKUP_ADDRESS;
import static com.directparking.app.util.Constants.PICKUP_TIME;

public class ConfirmationActivity extends BaseActivity implements ConfirmationView, OnMapReadyCallback {

    @Inject
    ConfirmationPresenter presenter;

    @Inject
    @Named("baseUrl")
    StringPreference apiEndpoint;

    @BindView(R.id.btn_confirmation_request)
    Button btnConfirmation;

    @BindView(R.id.tv_pickup_address)
    TextView tvPickupAddress;

    @BindView(R.id.tv_drop_address)
    TextView tvDropAddress;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.ll_confirm_request)
    LinearLayout linearLayoutConfirmBtn;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    private MapInfoWindowFragment mapFragment;
    private InfoWindowManager infoWindowManager;
    private GoogleMap googleMap;
    private AddressData pickUpAddress;
    private AddressData dropAddress;
    private long rideTime;
    private String parkingNameNumber;

    private Marker pickupMarker;
    private Marker dropMarker;
    private boolean isConfirm=false;


    @OnClick(R.id.btn_confirmation_request)
    void onClickConfirmation(View v) {

        if (!isConfirm) {
            Dialog successDialog = new Dialog(this);
            successDialog.setCancelable(false);
            successDialog.setContentView(R.layout.dialog_layout_success);
            successDialog.findViewById(R.id.tv_request_sent).setVisibility(View.GONE);
            successDialog.findViewById(R.id.tv_confirmation).setVisibility(View.VISIBLE);
            TextView tvMessage = (TextView) successDialog.findViewById(R.id.tv_success_message);

            SimpleDateFormat df = new SimpleDateFormat("hh:mm aa");
            String time = df.format(rideTime);

            SpannyText spandny = new SpannyText(getString(R.string.dialog_confirmation_message) + " at ")

                    .append(time, new StyleSpan(Typeface.BOLD)).append("?");

            tvMessage.setText(spandny);


            TextView tvDone = (TextView) successDialog.findViewById(R.id.tv_done);
            tvDone.setText("No");
            TextView tvCancel = (TextView) successDialog.findViewById(R.id.tv_cancel);
            tvCancel.setVisibility(View.VISIBLE);
            tvCancel.setText("Yes");
            tvCancel.setTextColor(ContextCompat.getColor(ConfirmationActivity.this, R.color.black));
            tvCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    successDialog.dismiss();
                    isConfirm=true;
                    RideData pickupPoint = new RideData.Builder()
                            .setAddress(pickUpAddress.getAddressLine1())
                            .setLatitude(pickUpAddress.getPosition().latitude)
                            .setLongitude(pickUpAddress.getPosition().longitude).build();

                    RideData dropPoint = new RideData.Builder()
                            .setAddress(dropAddress.getAddressLine1())
                            .setLatitude(dropAddress.getPosition().latitude)
                            .setLongitude(dropAddress.getPosition().longitude).build();

                    presenter.callConfirmationRequest(pickupPoint, dropPoint, rideTime + "", parkingNameNumber);
                }
            });
            tvDone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    successDialog.dismiss();
                }
            });
            successDialog.show();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);
        ButterKnife.bind(this);
        ((BaseApplication) getApplication()).getConfirmationComponent().inject(this);
        presenter.setView(this);
        pickUpAddress = Parcels.unwrap(getIntent().getParcelableExtra(PICKUP_ADDRESS));
        dropAddress = Parcels.unwrap(getIntent().getParcelableExtra(DROP_ADDRESS));
        rideTime = getIntent().getExtras().getLong(PICKUP_TIME);
        parkingNameNumber = getIntent().getExtras().getString(PARKING_NAME_NUMBER);

        mapFragment = ((MapInfoWindowFragment) getSupportFragmentManager().findFragmentById(R.id.map));
        mapFragment.getMapAsync(this);
        infoWindowManager = mapFragment.infoWindowManager();
        infoWindowManager.setHideOnFling(true);
        toolbar.setTitle(getString(R.string.title_confirm_ride));
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public void showCancelAlert(CancelResponse msg) {
        AlertUtil.showToast(ConfirmationActivity.this, msg.getMsg());
    }

    @Override
    public void showSuccessAlert(ConfirmationResponse msg) {
        if (msg.getSuccess() == 2) {
            Dialog successDialog = new Dialog(this);
            successDialog.setCancelable(false);
            successDialog.setContentView(R.layout.dialog_layout_success);
            successDialog.findViewById(R.id.tv_request_sent).setVisibility(View.GONE);
            successDialog.findViewById(R.id.tv_confirmation).setVisibility(View.VISIBLE);
            TextView tvMessage = (TextView) successDialog.findViewById(R.id.tv_success_message);
            tvMessage.setText(getString(R.string.cancel_request));
            TextView tvDone = (TextView) successDialog.findViewById(R.id.tv_done);
            tvDone.setText("No");
            TextView tvCancel = (TextView) successDialog.findViewById(R.id.tv_cancel);
            tvCancel.setVisibility(View.VISIBLE);
            tvCancel.setText("Yes");
            tvCancel.setTextColor(ContextCompat.getColor(ConfirmationActivity.this, R.color.black));
            tvCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    successDialog.dismiss();
                    presenter.callCancelPresenter(msg.getData().getRide_id(), msg.getData().getUser_id());
                }
            });
            tvDone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    successDialog.dismiss();
                }
            });
            successDialog.show();
            return;
        }
        Dialog successDialog = new Dialog(this);
        successDialog.setCancelable(false);
        successDialog.setContentView(R.layout.dialog_layout_success);
        TextView tvMessage = (TextView) successDialog.findViewById(R.id.tv_success_message);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(rideTime);
        SimpleDateFormat df = new SimpleDateFormat("hh:mm aa");
        String rideTimeToSend = df.format(calendar.getTime());
        tvMessage.setText(getString(R.string.dialog_success_message) + " " + rideTimeToSend);
        TextView tvDone = (TextView) successDialog.findViewById(R.id.tv_done);
        tvDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                successDialog.dismiss();
                linearLayoutConfirmBtn.setVisibility(View.GONE);
                broadcastIntent("requested");
                openHome();
            }
        });
        successDialog.show();
    }

    @Override
    public void onMapReady(GoogleMap map) {
        googleMap = map;
        googleMap.setOnCameraMoveStartedListener(reason -> {
            if (mapFragment.getView() != null) {
                ImageView ivLocation = ((View) mapFragment.getView().findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
                ivLocation.setVisibility(View.VISIBLE);
            }
        });

        if (mapFragment.getView() != null) {
            ImageView ivLocation = ((View) mapFragment.getView().findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
            ivLocation.setOnClickListener(v -> ivLocation.setVisibility(View.GONE));
            ivLocation.setImageResource(R.drawable.ic_location);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) ivLocation.getLayoutParams();
            params.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
            params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
            params.setMargins(0, 0, 100, 50);
        }

        if (dropMarker != null) {
            dropMarker.remove();
        }
        tvDropAddress.setText(dropAddress.getAddressLine1());

        dropMarker = googleMap.addMarker(new MarkerOptions()
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker))
                .position(dropAddress.getPosition())
                .title("Parking Spot Location")
                .snippet(String.valueOf(dropAddress.getAddressLine1()))
                .flat(true));

        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(dropMarker.getPosition(), 15f), 2000, null);

        if (pickupMarker != null) {
            pickupMarker.remove();
        }
        tvPickupAddress.setText(pickUpAddress.getAddressLine1());

        pickupMarker = googleMap.addMarker(new MarkerOptions()
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker))
                .position(pickUpAddress.getPosition())
                .title("Pickup Location")
                .snippet(String.valueOf(pickUpAddress.getAddressLine1()))
                .flat(true));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(pickupMarker.getPosition(), 15f), 2000, null);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                openHome();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void openHome() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }


    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        presenter.destroy();
        ((BaseApplication) getApplication()).releaseConfirmationComponent();
    }

    public void broadcastIntent(String action) {
        Intent intent = new Intent("unique_name");
        intent.putExtra("message", action);
        this.sendBroadcast(intent);
    }

}
