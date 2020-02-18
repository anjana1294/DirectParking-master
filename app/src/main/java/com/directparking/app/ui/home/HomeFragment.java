package com.directparking.app.ui.home;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.TextInputEditText;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.appolica.interactiveinfowindow.InfoWindow;
import com.appolica.interactiveinfowindow.InfoWindowManager;
import com.appolica.interactiveinfowindow.fragment.MapInfoWindowFragment;
import com.directparking.app.BaseApplication;
import com.directparking.app.R;
import com.directparking.app.fcm.BroadCastReceiver;
import com.directparking.app.location.model.LocationData;
import com.directparking.app.ui.base.BaseFragment;
import com.directparking.app.ui.base.HomeView;
import com.directparking.app.ui.chat.messages.MessageActivity;
import com.directparking.app.ui.confrimation.ConfirmationActivity;
import com.directparking.app.ui.home.model.AddressData;
import com.directparking.app.ui.home.model.AfterAcceptActionResponse;
import com.directparking.app.ui.home.model.MyRideData;
import com.directparking.app.ui.home.model.RatingData;
import com.directparking.app.ui.home.model.RideDetailResponse;
import com.directparking.app.ui.home.model.RideLocationData;
import com.directparking.app.ui.login.model.CarData;
import com.directparking.app.ui.login.model.UserData;
import com.directparking.app.ui.map.InfoWindowFragment;
import com.directparking.app.ui.review.post.ReviewActivity;
import com.directparking.app.ui.review.view.ViewReviewActivity;
import com.directparking.app.util.AlertUtil;
import com.directparking.app.util.JobUtil;
import com.directparking.app.util.MapUtil;
import com.directparking.app.util.RxUtils;
import com.directparking.app.util.Util;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.maps.android.PolyUtil;
import com.squareup.picasso.Picasso;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.parceler.Parcels;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import io.hypertrack.smart_scheduler.Job;
import io.hypertrack.smart_scheduler.SmartScheduler;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import pl.charmas.android.reactivelocation2.ReactiveLocationProvider;
import timber.log.Timber;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static com.directparking.app.util.Constants.DROP_ADDRESS;
import static com.directparking.app.util.Constants.DROP_ADDRESS_REQUEST;
import static com.directparking.app.util.Constants.LOCATION_SETTINGS_REQUEST;
import static com.directparking.app.util.Constants.LOCATION_UPDATE_INTERVAL;
import static com.directparking.app.util.Constants.PARKING_NAME_NUMBER;
import static com.directparking.app.util.Constants.PICKUP_ADDRESS;
import static com.directparking.app.util.Constants.PICKUP_ADDRESS_REQUEST;
import static com.directparking.app.util.Constants.PICKUP_TIME;

@SuppressLint("MissingPermission")
public class HomeFragment extends BaseFragment implements HomeView, OnMapReadyCallback,
        OnMarkerClickListener, SmartScheduler.JobScheduledCallback {

    private View rootView;
    private Unbinder unbinder;
    private BottomSheetBehavior sheetBehavior, bottomSheetUserDetails,
            bottomSheetDriverAction, bottomSheetRequestedRideCancel;
    private MapInfoWindowFragment mapFragment;
    private InfoWindowManager infoWindowManager;

    private AddressData pickupAddress;
    private AddressData dropAddress;

    private GoogleMap googleMap;
    private Disposable disposable;
    private Location currentLocation;
    private AlertDialog locationErrorDialog;
    private Observable<Location> locationUpdatesObservable;

    private LatLng pickupPoint;
    private Marker pickupMarker;
    private LatLng dropPoint;
    private Marker dropMarker;
    private Marker driverMarker;

    private Polyline pathLine;

    BottomSheetDialog dialogBottomSheet;
    Toolbar toolbar;
    long rideMilliSeconds;
    Calendar calendarConvert;
    Intent ratingIntent;
    ImageView ivTimePicker;
    TextView tvTimer;
    Button btnConfirm;
    View viewBottomSheetBooking;
    String rideID, riderID, driverID;
    private InterstitialAd mInterstitialAd;
    private int isRider;

    LatLng locationForNavigation, dropLocation;
    boolean isMapTouched = false;
    @Inject
    HomePresenter presenter;

    @Inject
    ReactiveLocationProvider locationProvider;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.layout_bottom_sheet)
    LinearLayout layoutBottomSheet;

    @BindView(R.id.layout_bottom_sheet_user_details)
    LinearLayout layoutBottomSheetUserDetail;

    @BindView(R.id.layout_bottom_sheet_driver_action)
    LinearLayout layoutBottomSheetDriverAction;

    @BindView(R.id.layout_bottom_sheet_ride_cancel)
    LinearLayout layoutBottomSheetRideCancel;


    @BindView(R.id.tv_ride_cancel_time)
    TextView tvRequestedRideTime;

    @BindView(R.id.btn_requested_ride_cancel)
    Button btnRequestedRideCancel;

    @BindView(R.id.tv_pickup_address)
    TextView tvPickupAddress;

    @BindView(R.id.tv_drop_address)
    TextView tvDropAddress;

    @BindView(R.id.et_parking_number_name)
    TextInputEditText etParkingNumberName;

    @BindView(R.id.tv_distance)
    TextView tvDistance;

    @Inject
    Picasso picasso;

    @BindView(R.id.civ_user_profile)
    CircleImageView civUserDetailImage;

    @BindView(R.id.tv_user_name)
    TextView tvUserName;

    @BindView(R.id.tv_car_detail)
    TextView tvCarDetails;

    @BindView(R.id.tv_car_color)
    TextView tvCarColor;

    @BindView(R.id.tv_car_license)
    TextView tvCarLicense;

    @BindView(R.id.tv_droped_address)
    TextView tvDropLocation;

    @BindView(R.id.tv_picked_up)
    TextView tvPickUpLocation;

    @BindView(R.id.tv_rating)
    TextView tvUserRating;

    @BindView(R.id.layout_location_picker)
    CardView cardViewLocationPicker;

    // Driver Side Bottom Sheet Views

    @BindView(R.id.civ_rider_profile)
    CircleImageView civRiderProfile;

    @BindView(R.id.tv_rider_full_name)
    TextView tvRiderFullName;

    @BindView(R.id.tv_rider_pickup_time)
    TextView tvRiderPickupTime;

    @BindView(R.id.btn_driver_ride_start)
    Button btnDriveStart;

    @BindView(R.id.imgMapNavigation)
    ImageView imgMapNavigation;

    @BindView(R.id.btn_user_ride_cancel)
    Button btnUserRideCancel;

    @BindView(R.id.btn_driver_ride_cancel)
    Button btnDriverRideCancel;

    @BindView(R.id.btn_driver_ride_complete)
    Button btnDriverRideComplete;

    @BindView(R.id.viewswitcher_driver_btns)
    ViewSwitcher switcherDriverBtns;

    @BindView(R.id.ratingbar_user_rating)
    RatingBar rtDriverRating;

    @BindView(R.id.iv_call)
    ImageView ivCallToDriver;

    @BindView(R.id.iv_chat)
    ImageView ivChatToDriver;

    @BindView(R.id.iv_driver_chat)
    ImageView ivChatToRider;

    @BindView(R.id.iv_location_marker)
    ImageView locationMarker;

    @BindView(R.id.btn_done)
    Button btn_done;

    LatLng center;
    Boolean isPickupAddress;

    Boolean isMapMove = false;

    RatingData ratingData;

    Boolean isRatingPending = false;

    Callback callback;
    List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG);

    @OnClick({R.id.tv_pickup_address, R.id.tv_drop_address})
    void selectPickupAddress(View view) {
        String parkingLotName = etParkingNumberName.getText().toString();
        if (!TextUtils.isEmpty(parkingLotName)) {
//            showProgressBar();
            Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fields).build(getContext());
            switch (view.getId()) {
                case R.id.tv_pickup_address:
                   /* Intent intent = new Autocomplete.IntentBuilder(
                            AutocompleteActivityMode.FULLSCREEN, fields)
                            .build(getContext());*/
                    // startActivityForResult(builder.build(getActivity()), PICKUP_ADDRESS_REQUEST);
                    showDialog(true);
                    isPickupAddress = true;

                    //  startActivityForResult(intent, PICKUP_ADDRESS_REQUEST);
                    break;

                case R.id.tv_drop_address:
                    showDialog(false);
                    // startActivityForResult(intent, DROP_ADDRESS_REQUEST);
                    isPickupAddress = false;
                    break;
            }
        } else {
            showMessage("Please enter parking lot name or number first");
        }
    }


    void onMapNavigation() {
        Uri gmmIntentUri = Uri.parse("google.navigation:q=" + locationForNavigation.latitude + "," + locationForNavigation.longitude + "&mode=d");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);

    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }


    private BroadcastReceiver mMessageReceiver = new BroadCastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String data = intent.getStringExtra("message");
            mInterstitialAd.loadAd(new AdRequest.Builder().build());

            if (data.equals("canceled") || data.equals("accepted")
                    || data.equals("complete") || data.equals("started")
                    || data.equals("requested")) {

                // String reason = intent.getStringExtra("message");

                if (data.equals("canceled") && intent.hasExtra("reason")) {
                  /*  String reason = intent.getStringExtra("reason");
                    AlertUtil.showAlertDialog(context(), "Ride Cancelled", intent.getStringExtra("body"), "OK");
//*/
                    callback.showCancelReason(intent.getStringExtra("reason"), intent.getStringExtra("body"));

//                    AlertUtil.showActionAlertDialog(context(), "Ride Cancelled", intent.getStringExtra("body"),
//                            "Yes", "No", (dialog, which) -> onMapNavigation());
                }
                if (data.equals("complete")) {
                    JobUtil.removeUpdateLocationJob(context());
                    JobUtil.removeTrackOrderJob(context());
                    removeMarkerPath();
                }
                isRatingPending = true;

                presenter.fetchRideDetails();


            }
        }
    };


    @Override
    public void onResume() {
        getActivity().registerReceiver(mMessageReceiver, new IntentFilter("unique_name"));
        super.onResume();

    }

    @Override
    public void onDestroy() {
        dialogBottomSheet = null;
        getActivity().unregisterReceiver(mMessageReceiver);
        super.onDestroy();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((BaseApplication) getActivity().getApplication()).getHomeComponent().inject(this);

        calendarConvert = Calendar.getInstance();
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity())
                .setTitle("Location error")
                .setMessage("Sorry device location couldn't be accessed, you can't proceed further!")
                .setPositiveButton("Retry", null);

        locationErrorDialog = alertDialogBuilder.create();
        locationErrorDialog.setCanceledOnTouchOutside(false);
        locationErrorDialog.setCancelable(false);
        locationErrorDialog.setOnShowListener(dialog -> {
            Button positiveBtn = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
            positiveBtn.setOnClickListener(view ->
                    getLocationPermission());
        });

        LocationRequest locationRequest = LocationRequest.create()
                .setInterval(LOCATION_UPDATE_INTERVAL)
                .setFastestInterval(LOCATION_UPDATE_INTERVAL)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest locationSettings = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest)
                .setAlwaysShow(true)
                .build();

        locationUpdatesObservable = locationProvider
                .checkLocationSettings(locationSettings)
                .doOnNext(locationSettingsResult -> {
                    Status status = locationSettingsResult.getStatus();
                    if (status.getStatusCode() == LocationSettingsStatusCodes.RESOLUTION_REQUIRED) {
                        try {
                            status.startResolutionForResult(getActivity(), LOCATION_SETTINGS_REQUEST);
                        } catch (IntentSender.SendIntentException e) {
                            Timber.e(e);
                            showMessage("Error opening location settings activity.");
                            showLocationErrorDialogIfNotShowing();
                        }
                    }
                })
                .flatMap(locationSettingsResult -> locationProvider.getUpdatedLocation(locationRequest))
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io());

        mInterstitialAd = new InterstitialAd(context());
        mInterstitialAd.setAdUnitId(getString(R.string.google_admob_unit_id));
        if (!Places.isInitialized()) {
            Places.initialize(context(), "AIzaSyATsAPxtSIoEIMIXANO0H03HnK0Nk4Uo60"); // Create a new Places client instance. PlacesClient placesClient = Places.createClient(this);
        }
    }

   /* @Override
    public void onStop() {
        super.onStop();
        googleMap.clear();
        googleMap=null;
    }*/


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_home, container, false);
        toolbar = getActivity().findViewById(R.id.toolbar);
        btn_done = (Button) rootView.findViewById(R.id.btn_done);

        presenter.fetchRideDetails();

        viewBottomSheetBooking = getLayoutInflater().inflate(R.layout.fragment_booking, null);
        ivTimePicker = (ImageView) viewBottomSheetBooking.findViewById(R.id.iv_timer_picker);
        tvTimer = (TextView) viewBottomSheetBooking.findViewById(R.id.tv_ride_time);
        btnConfirm = (Button) viewBottomSheetBooking.findViewById(R.id.btn_confirmation);

        unbinder = ButterKnife.bind(this, rootView);
        sheetBehavior = BottomSheetBehavior.from(layoutBottomSheet);
        sheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                    int margin = Util.convertDpToPixels(context(), 5);
                    int padding = layoutBottomSheet.getHeight() + margin;
                    googleMap.setPadding(0, 0, 0, padding);
                } else if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                    int margin = Util.convertDpToPixels(context(), 5);
                    int padding = sheetBehavior.getPeekHeight() + margin;
                    googleMap.setPadding(0, 0, 0, padding);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            }
        });

        mapFragment = ((MapInfoWindowFragment) getChildFragmentManager().findFragmentById(R.id.map));
        mapFragment.getMapAsync(this);
        infoWindowManager = mapFragment.infoWindowManager();
        infoWindowManager.setHideOnFling(true);

        ivTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTimerPicker();
            }
        });

        tvTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTimerPicker();
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pickupAddress != null && dropAddress != null) {
                    dialogBottomSheet.dismiss();
                    Intent intent = new Intent(getContext(), ConfirmationActivity.class);
                    intent.putExtra(PICKUP_ADDRESS, Parcels.wrap(pickupAddress));
                    intent.putExtra(DROP_ADDRESS, Parcels.wrap(dropAddress));
                    intent.putExtra(PICKUP_TIME, rideMilliSeconds);
                    intent.putExtra(PARKING_NAME_NUMBER, etParkingNumberName.getText().toString());
                    startActivity(intent);
                    getActivity().finish();
                }
            }
        });


        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPickupAddress) {
                    if (dropPoint != null) {
                        presenter.fetchDirections(pickupPoint, dropPoint);
                        openRideTimingSelectionSheet();
                    } else {
                        Toast.makeText(context(), "Please select Drop location first", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if (pickupPoint != null) {
                        presenter.fetchDirections(pickupPoint, dropPoint);
                        openRideTimingSelectionSheet();
                    } else
                        Toast.makeText(context(), "Please select Pickup location first", Toast.LENGTH_SHORT).show();

                }
            }
        });

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();
                JobUtil.removeUpdateLocationJob(context());
                JobUtil.removeTrackOrderJob(context());
                removeMarkerPath();
//                if (!isRatingPending)
//                    startActivity(ratingIntent);
//                else
                    callback.openReviewScreen(ratingData);

            }

        });
        return rootView;
    }


    private void openTimerPicker() {
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        final Calendar calendar = Calendar.getInstance();
                        Calendar c = Calendar.getInstance();
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendar.set(Calendar.MINUTE, minute);
                        calendar.set(Calendar.SECOND, 0);
                        calendar.set(Calendar.MILLISECOND, 0);
                        if (calendar.getTimeInMillis() >= c.getTimeInMillis()) {

                            //it's after current

                            int hour = hourOfDay % 12;

//                            Toast.makeText(context(), String.format("%02d:%02d %s", hour == 0 ? 12 : hour,
//                                    minute, hourOfDay < 12 ? "am" : "pm"), Toast.LENGTH_SHORT).show();
                            rideMilliSeconds = calendar.getTimeInMillis();
                            Log.e("SelectedSeconds= ", +rideMilliSeconds + "");
                            SimpleDateFormat df = new SimpleDateFormat("hh:mm aa");
                            tvTimer.setText(df.format(calendar.getTime()));
                            btnConfirm.setEnabled(true);
                            btnConfirm.setAlpha(1);
                        } else {

                            //it's before current'

                            Toast.makeText(context(), "Please select a pick up time in future ", Toast.LENGTH_LONG).show();
                            SimpleDateFormat df = new SimpleDateFormat("hh:mm aa");
                            tvTimer.setText(df.format(calendar.getTime()));
                            btnConfirm.setEnabled(false);
                            btnConfirm.setAlpha(0.5f);
                        }
                    }
                }, hour, minute, false);
        timePickerDialog.show();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        presenter.setView(this);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        googleMap = map;

        googleMap.getUiSettings().setZoomControlsEnabled(false);
        LatLng latLng = new LatLng(20.5937, 78.9629);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));

        // Show Sydney on the map.
        googleMap.moveCamera(CameraUpdateFactory
                .newLatLngZoom(new LatLng(-33.87365, 151.20689), 10));

        if (mapFragment.getView() != null) {
            ImageView ivLocation = ((View) mapFragment.getView().findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
            ivLocation.setOnClickListener(v -> ivLocation.setVisibility(View.GONE));
            ivLocation.setImageResource(R.drawable.ic_location);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) ivLocation.getLayoutParams();
            params.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
            params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
            params.setMargins(0, 0, 100, 50);
        }
        getLocationPermission();
    }


    private void getLocationPermission() {
        new RxPermissions(getActivity())
                .request(Manifest.permission.ACCESS_FINE_LOCATION)
                .subscribe(granted -> {
                    if (granted) {
                        dismissLocationErrorDialogIfShowing();

                        if (googleMap != null) {
                            googleMap.setMyLocationEnabled(true);
                            googleMap.getUiSettings().setMyLocationButtonEnabled(true);
                        }
                        requestLocation();
                    } else {
                        showLocationErrorDialogIfNotShowing();
                    }
                });
    }


    protected void showLocationErrorDialogIfNotShowing() {
        if (locationErrorDialog != null && !locationErrorDialog.isShowing()) {
            locationErrorDialog.show();
        }
    }

    protected void dismissLocationErrorDialogIfShowing() {
        if (locationErrorDialog != null && locationErrorDialog.isShowing()) {
            locationErrorDialog.dismiss();
        }
    }

    public void requestLocation() {
        Timber.d("Requesting for location...");

        if (disposable != null) {
            RxUtils.dispose(disposable);
        }

        disposable = locationUpdatesObservable.subscribe(newLocation -> {
            Timber.d("Found location: %s", newLocation);

            if (currentLocation == null) {
                pickupMarker = googleMap.addMarker(new MarkerOptions()
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker))
                        .position(new LatLng(newLocation.getLatitude(), newLocation.getLongitude()))
                        .title("Pickup Location")
                        .flat(true));

                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(pickupMarker.getPosition(), 15f), 2000, null);
            }

            currentLocation = newLocation;
        });
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        final int offset = Util.convertDpToPixels(context(), 20);
        InfoWindow infoWindow = new InfoWindow(marker.getPosition(), new InfoWindow.MarkerSpecification(offset, offset),
                InfoWindowFragment.newInstance(marker.getTitle(), marker.getSnippet(), marker.getPosition()));
        infoWindowManager.toggle(infoWindow, true);

        return true;
    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showMessage(String msg) {
        AlertUtil.showSnackBar(rootView, msg);
    }

    @Override
    public void drawPath(String path) {
        try {
            List<LatLng> fullPath = PolyUtil.decode(path);
            if (fullPath != null && !fullPath.isEmpty()) {
                if (pathLine != null) {
                    pathLine.remove();
                }
                pathLine = googleMap.addPolyline(new PolylineOptions().addAll(fullPath).color(getResources().getColor(R.color.trans_black)));
            }
        } catch (Exception e) {
            Timber.e(e);
            showMessage(e.getMessage());
        }
    }

    @Override
    public void showRideDetails(RideDetailResponse rideDetailResponse) {
        if (rideDetailResponse.getRatingData() != null) {

            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            }
            ratingData = rideDetailResponse.getRatingData();
            if(!isRatingPending)
            callback.openReviewScreen(ratingData);

            return;

        }

        if (rideDetailResponse.getRideLocationData() != null) {
            mInterstitialAd.loadAd(new AdRequest.Builder().build());
            UserData userData = rideDetailResponse.getUserDetails();

            riderID = userData.getUserId();
            RideLocationData rideLocationData = rideDetailResponse.getRideLocationData();
            driverID = rideLocationData.getUser_id();
            rideID = rideLocationData.getId();
            tvDropAddress.setClickable(false);
            tvPickupAddress.setClickable(false);

            LatLng pickUpLocation = new LatLng(rideLocationData.getPick_lat(), rideLocationData.getPick_log());
            dropLocation = new LatLng(rideLocationData.getDrop_lat(), rideLocationData.getDrop_log());


            if (dropMarker != null) {
                dropMarker.remove();
            }
            dropMarker = googleMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker))
                    .position(dropLocation)
                    .title("Drop Location")
                    .flat(true));

            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(dropMarker.getPosition(), 15f), 2000, null);

            try {
                pickupMarker.setPosition(pickUpLocation);
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(pickupMarker.getPosition(), 15f), 2000, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
            presenter.fetchDirections(pickUpLocation, dropLocation);
            if (bottomSheetRequestedRideCancel != null) {
                int state = bottomSheetRequestedRideCancel.getState();
//                bottomSheetRequestedRideCancel.setState(BottomSheetBehavior.STATE_COLLAPSED);
//                bottomSheetRequestedRideCancel.setSkipCollapsed(true);
            }
            layoutBottomSheetRideCancel.setVisibility(View.GONE);
//           // bottomSheetRequestedRideCancel.setState(BottomSheetBehavior.STATE_COLLAPSED);
            etParkingNumberName.setText(rideLocationData.getParking_no());
            etParkingNumberName.setEnabled(false);
            isRider = rideDetailResponse.getIsRider();
//            if (rideLocationData.getStatus().equals("start")) {
//                JobUtil.addTrackOrderJob(context(), 3000, this);
//            }
            if (rideDetailResponse.getIsRider() == 0) {
                toolbar.setTitle(getContext().getString(R.string.title_pickup_arriving));


                layoutBottomSheetUserDetail.setVisibility(View.VISIBLE);
                bottomSheetUserDetails = BottomSheetBehavior.from(layoutBottomSheetUserDetail);
                bottomSheetUserDetails.setHideable(false);
//                int state =bottomSheetUserDetails.getState();

                CarData carData = rideDetailResponse.getCarDetails();
                tvUserName.setText(userData.getFirstName() + " " + userData.getLastName());
                tvDropLocation.setText(rideLocationData.getDrop_address());
                tvPickUpLocation.setText(rideLocationData.getPick_address());
                rtDriverRating.setRating(userData.getRating());
                tvCarDetails.setText("Car Make: " + carData.getCarMake() + " " + carData.getModel());
                tvCarColor.setText("Car Color: " + carData.getColor());
                tvCarLicense.setText("License Plate#: " + carData.getPlateNumber());

                try {
                    picasso.load(userData.getProfile())
                            .placeholder(R.drawable.ic_loading)
                            .error(R.mipmap.ic_launcher)
                            .into(civUserDetailImage);
                } catch (Exception e) {
                    Timber.e(e);
                    picasso.load(R.mipmap.ic_launcher).fit().into(civUserDetailImage);
                }

                layoutBottomSheetUserDetail.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (bottomSheetRequestedRideCancel != null) {
                            bottomSheetUserDetails.setState(BottomSheetBehavior.STATE_EXPANDED);
                        }
                        return false;
                    }
                });
                if (rideLocationData.getStatus().toLowerCase().equals("start"))
                    btnUserRideCancel.setText("TRIP STARTED");
                else
                    btnUserRideCancel.setText("CANCEL REQUEST");

                btnUserRideCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (rideLocationData.getStatus().toLowerCase().equals("start")) {
                        } else
                            showCancelConfirmDialog("cancel", rideLocationData.getId(), rideDetailResponse.getIsRider());
                    }
                });

                ivCallToDriver.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bottomSheetUserDetails.setState(BottomSheetBehavior.STATE_EXPANDED);
                        new RxPermissions(getActivity())
                                .request(Manifest.permission.CALL_PHONE)
                                .subscribe(granted -> {
                                    if (granted) {
                                        getContext().startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + userData.getPhone())));
                                    }
                                });
                    }
                });

                ivChatToDriver.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intentChat = new Intent(context(), MessageActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("userData", userData);
                        bundle.putString("rideId", rideLocationData.getId());
                        intentChat.putExtras(bundle);
                        startActivity(intentChat);
                    }
                });

                tvUserName.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent reviewListIntent = new Intent(context(), ViewReviewActivity.class);
                        reviewListIntent.putExtra("id", userData.getUserId());
                        startActivity(reviewListIntent);
                    }
                });

                presenter.fetchDriverLocation(rideID, driverID);


            } else {
                locationForNavigation = pickUpLocation;
                if (rideLocationData.getStatus().toLowerCase().equals("start")) {
                    switcherDriverBtns.showNext();
                    locationForNavigation = dropLocation;
                    toolbar.setTitle(getContext().getString(R.string.title_on_trip));
                } else {
                    toolbar.setTitle(getContext().getString(R.string.title_pickup_point));

                }
                JobUtil.addUpdateLocationJob(context(), 2000, this);
                tvDropAddress.setText(rideLocationData.getDrop_address());
                tvPickupAddress.setText(rideLocationData.getPick_address());
                layoutBottomSheetDriverAction.setVisibility(View.VISIBLE);
                bottomSheetDriverAction = BottomSheetBehavior.from(layoutBottomSheetDriverAction);
                bottomSheetDriverAction.setHideable(false);

                tvRiderFullName.setText(userData.getFirstName() + " " + userData.getLastName());
                try {
                    calendarConvert.setTimeInMillis(Long.parseLong(rideLocationData.getSchedule_time()));
                    SimpleDateFormat df = new SimpleDateFormat("hh:mm aa");
                    String rideTime = df.format(calendarConvert.getTime());
                    tvRiderPickupTime.setText(rideTime);
                } catch (Exception e) {
                    e.printStackTrace();
                }

//                double s = Util.distance(rideLocationData.getDrop_lat(), rideLocationData.getDrop_log(), rideLocationData.getPick_lat(), rideLocationData.getPick_log());
//                String distanceinMiles = String.valueOf(s);
//                String distanceinMiless = distanceinMiles.length() > 5 ? distanceinMiles.subSequence(0, 5) + " Miles" : distanceinMiles + " Miles";
                tvDistance.setText(rideLocationData.getDistance() + "m");

                try {
                    picasso.load(userData.getProfile())
                            .placeholder(R.drawable.ic_loading)
                            .error(R.mipmap.ic_launcher)
                            .into(civRiderProfile);
                } catch (Exception e) {
                    Timber.e(e);
                    picasso.load(R.mipmap.ic_launcher).fit().into(civRiderProfile);
                }

                btnDriveStart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String msg = "You are now being redirected to drop-off location on google maps";

                        AlertUtil.showMyActionAlertDialog(context(), "Confirmation", msg,
                                "Ok", (dialog, which) ->
                                        presenter.actionAfterAccept("start", rideLocationData.getId(), "", rideDetailResponse.getIsRider()));
                        //   presenter.actionAfterAccept("start", rideLocationData.getId(), "", rideDetailResponse.getIsRider());
                    }
                });


                imgMapNavigation.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        presenter.actionAfterAccept("start", rideLocationData.getId(), "", rideDetailResponse.getIsRider());
                    }
                });

                btnDriverRideCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showCancelConfirmDialog("cancel", rideLocationData.getId(), rideDetailResponse.getIsRider());
                    }
                });

                btnDriverRideComplete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        presenter.actionAfterAccept("complete", rideLocationData.getId(), "", rideDetailResponse.getIsRider());
                    }
                });

                tvRiderFullName.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent reviewListIntent = new Intent(context(), ViewReviewActivity.class);
                        reviewListIntent.putExtra("id", userData.getUserId());
                        startActivity(reviewListIntent);
                    }
                });

                ivChatToRider.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intentChat = new Intent(context(), MessageActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("userData", userData);
                        bundle.putString("rideId", rideLocationData.getId());
                        intentChat.putExtras(bundle);
                        startActivity(intentChat);
                    }
                });
            }
        } else if (rideDetailResponse.getRequestedRide() != null) {
            MyRideData myRideData = rideDetailResponse.getRequestedRide();
            LatLng pickUpLocation = new LatLng(myRideData.getPick_lat(), myRideData.getPick_log());
            LatLng dropLocation = new LatLng(myRideData.getDrop_lat(), myRideData.getDrop_log());
            if (dropMarker != null) {
                dropMarker.remove();
            }
            dropMarker = googleMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker))
                    .position(dropLocation)
                    .title("Drop Location")
                    .flat(true));

            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(dropMarker.getPosition(), 15f), 2000, null);

            try {
                pickupMarker.setPosition(pickUpLocation);
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(pickupMarker.getPosition(), 15f), 2000, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
            presenter.fetchDirections(pickUpLocation, dropLocation);
            etParkingNumberName.setText(myRideData.getParking_no());
            etParkingNumberName.setEnabled(false);
            tvDropAddress.setText(myRideData.getDrop_address());
            tvPickupAddress.setText(myRideData.getPick_address());
            tvDropAddress.setClickable(false);
            tvPickupAddress.setClickable(false);
            layoutBottomSheetRideCancel.setVisibility(View.VISIBLE);
            bottomSheetRequestedRideCancel = BottomSheetBehavior.from(layoutBottomSheetRideCancel);

//            bottomSheetRequestedRideCancel.setHideable(false);

            try {
                calendarConvert.setTimeInMillis(Long.parseLong(myRideData.getSchedule_time()));
                SimpleDateFormat df = new SimpleDateFormat("hh:mm aa");
                String rideTime = df.format(calendarConvert.getTime());
                tvRequestedRideTime.setText(rideTime);
            } catch (Exception e) {
                e.printStackTrace();
            }
            btnRequestedRideCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String msg = "Are you sure you want to cancel this ride request ?";

                    AlertUtil.showActionAlertDialog(context(), "Cancellation", msg,
                            "Yes", "No", (dialog, which) ->
                                    presenter.callCancelPresenter(myRideData.getId()));
                    //  presenter.callCancelPresenter(myRideData.getId());
                }
            });
        } else {
            removeMarkerPath();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        hideProgressBar();

        switch (requestCode) {
            case LOCATION_SETTINGS_REQUEST:
                switch (resultCode) {
                    case RESULT_OK:
                        Timber.d("User enabled location");
                        final LocationSettingsStates states = LocationSettingsStates.fromIntent(data);
                        if (states.isGpsPresent() && states.isGpsUsable()) {
                            requestLocation();
                        } else {
                            showLocationErrorDialogIfNotShowing();
                        }
                        break;
                    case RESULT_CANCELED:
                        Timber.d("User cancelled enabling location.");
                        showLocationErrorDialogIfNotShowing();
                        break;
                }
                break;
            case PICKUP_ADDRESS_REQUEST:
                if (requestCode == PICKUP_ADDRESS_REQUEST) {
                    if (resultCode == RESULT_OK) {
                        // Place pickupPlace = PlacePicker.getPlace(getContext(), data);
                        Place pickupPlace = Autocomplete.getPlaceFromIntent(data);
                        String pickAddress = "", pickupName = "";
                        if (!TextUtils.isEmpty(pickupPlace.getAddress()))
                            pickAddress = pickupPlace.getAddress().toString();
                        if (!TextUtils.isEmpty(pickupPlace.getName()))
                            pickupName = pickupPlace.getName().toString();

                        pickupPoint = pickupPlace.getLatLng();

                        tvPickupAddress.setText(pickupName + " " + pickAddress);

                        tvPickupAddress.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
                        try {
                            pickupMarker.setSnippet(String.valueOf(pickAddress));
                            pickupMarker.setPosition(pickupPlace.getLatLng());
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(pickupMarker.getPosition(), 15f), 2000, null);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        if (dropPoint != null) {
                            presenter.fetchDirections(pickupPoint, dropPoint);
                            openRideTimingSelectionSheet();
                        }

                        pickupAddress = new AddressData.Builder()
                                .setAddressLine1(pickupName + " " + String.valueOf(pickAddress))
                                .setLat(pickupPlace.getLatLng().latitude)
                                .setLng(pickupPlace.getLatLng().longitude)
                                .build();

                    }
                }
                break;
            case DROP_ADDRESS_REQUEST:
                if (resultCode == RESULT_OK) {
                    Place dropPlace = Autocomplete.getPlaceFromIntent(data);

                    String dropLocAddress = "", dropName = "";
                    if (!TextUtils.isEmpty(dropPlace.getAddress()))
                        dropLocAddress = dropPlace.getAddress().toString();
                    if (!TextUtils.isEmpty(dropPlace.getName()))

                        dropName = dropPlace.getName().toString();

                    dropPoint = dropPlace.getLatLng();
                    tvDropAddress.setText(dropName + " " + dropLocAddress);
                    tvDropAddress.setTextColor(ContextCompat.getColor(getContext(), R.color.black));

                    if (dropMarker != null) {
                        dropMarker.remove();
                    }
                    dropMarker = googleMap.addMarker(new MarkerOptions()
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker))
                            .position(dropPlace.getLatLng())
                            .title("Drop Location")
                            .snippet(String.valueOf(dropLocAddress))
                            .flat(true));

                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(dropMarker.getPosition(), 15f), 2000, null);

                    if (pickupPoint != null) {
                        presenter.fetchDirections(pickupPoint, dropPoint);
                        openRideTimingSelectionSheet();
                    }

                    dropAddress = new AddressData.Builder()
                            .setAddressLine1(dropName + " " + String.valueOf(dropLocAddress))
                            .setLat(dropPlace.getLatLng().latitude)
                            .setLng(dropPlace.getLatLng().longitude)
                            .build();
                }
                break;
        }
    }

    private void openRideTimingSelectionSheet() {
        Calendar now = Calendar.getInstance();
        now.add(Calendar.MINUTE, 5);
        rideMilliSeconds = now.getTimeInMillis();

        Log.e("TImeZOne", now.getTimeZone() + "");
        SimpleDateFormat df = new SimpleDateFormat("hh:mm aa");
        tvTimer.setText(df.format(now.getTime()));

        if (dialogBottomSheet == null) {
            dialogBottomSheet = new BottomSheetDialog(getContext());
            dialogBottomSheet.setContentView(viewBottomSheetBooking);
            dialogBottomSheet.setCanceledOnTouchOutside(true);
            dialogBottomSheet.setCancelable(false);
        }

        dialogBottomSheet.show();
        btn_done.setVisibility(View.GONE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        dialogBottomSheet = null;
        unbinder.unbind();
        presenter.destroy();
        RxUtils.dispose(disposable);
        JobUtil.removeUpdateLocationJob(context());
        JobUtil.removeTrackOrderJob(context());
//        googleMap.clear();
//        googleMap = null;

    }

    @Override
    public void onDetach() {
        super.onDetach();
        dialogBottomSheet = null;
//        googleMap.clear();
//        googleMap = null;

    }

    void showCancelConfirmDialog(String action, String rideId, int isRider) {
        Dialog successDialog = new Dialog(getContext());
        successDialog.setCancelable(false);
        successDialog.setContentView(R.layout.dialog_cancel_reason_layout);
        ViewSwitcher viewSwitcherCancel = (ViewSwitcher) successDialog.findViewById(R.id.viewswitcher_dialog);
        Animation in = AnimationUtils.loadAnimation(getContext(), android.R.anim.slide_in_left);
        Animation out = AnimationUtils.loadAnimation(getContext(), android.R.anim.slide_out_right);

        viewSwitcherCancel.setInAnimation(in);
        viewSwitcherCancel.setOutAnimation(out);
        TextInputEditText etCancelReason = (TextInputEditText) successDialog.findViewById(R.id.et_cancel_reason);

        TextView tvYes = (TextView) successDialog.findViewById(R.id.tv_yes);
        tvYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewSwitcherCancel.showNext();
            }
        });

        TextView tvNo = (TextView) successDialog.findViewById(R.id.tv_no);
        tvNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                successDialog.dismiss();
            }
        });

        TextView tvCancelReason = (TextView) successDialog.findViewById(R.id.tv_ride_reason_cancel);
        tvCancelReason.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                successDialog.dismiss();
            }
        });

        TextView tvSendReason = (TextView) successDialog.findViewById(R.id.tv_ride_reason_send);
        tvSendReason.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cancelReason = etCancelReason.getText().toString();
                if (cancelReason == "" || cancelReason.isEmpty())
                    etCancelReason.setError(getContext().getString(R.string.empty_cancellation_reason_msg));
                else {
                    presenter.actionAfterAccept(action, rideId, cancelReason, isRider);
                    successDialog.dismiss();
                }
            }
        });
        successDialog.show();
    }

    @Override
    public void showViewAfterAccept(AfterAcceptActionResponse data, String action, int isRider) {
        if (action == "start" && isRider == 0) {
            // JobUtil.addTrackOrderJob(context(), 3000, this);
            presenter.fetchDriverLocation(rideID, driverID);
        }

        if (action == "start" && isRider == 1) {
            switcherDriverBtns.showNext();
            locationForNavigation = dropLocation;
            toolbar.setTitle(getContext().getString(R.string.title_on_trip));
            onMapNavigation();
            return;
        } else if (action == "cancel" && isRider == 0) {
            layoutBottomSheetUserDetail.setVisibility(View.GONE);
            bottomSheetUserDetails.setState(BottomSheetBehavior.STATE_COLLAPSED);

        } else if (action == "complete" && isRider == 1) {
            layoutBottomSheetDriverAction.setVisibility(View.GONE);
            bottomSheetDriverAction.setState(BottomSheetBehavior.STATE_COLLAPSED);

            if (ratingData == null)
                ratingData = new RatingData();
            ratingData.setRide_id(rideID);
            ratingData.setArrivaltime(data.getTotalTime());
            ratingData.setIsRider(isRider+"");
            ratingData.setAccept_user_id(riderID);


//            ratingIntent = new Intent(context(), ReviewActivity.class);
//            ratingIntent.putExtra("rideId", rideID);
//            ratingIntent.putExtra("totalTime", data.getTotalTime());
//            ratingIntent.putExtra("isRider", isRider + "");
//            ratingIntent.putExtra("relatedId", riderID);

            if (mInterstitialAd.isLoaded()) {
                AlertUtil.showMyActionAlertDialog(context(), "Ride Completed", "Thank you for completing your" +
                                " trip with Direct Parking!"
                        , "Ok", (dialog, which) ->
                                mInterstitialAd.show());
            } else {
                Log.d("TAG", "The interstitial wasn't loaded yet.");
            }
            JobUtil.removeUpdateLocationJob(context());
            JobUtil.removeTrackOrderJob(context());

        } else if (action == "cancel" && isRider == 1) {
            layoutBottomSheetDriverAction.setVisibility(View.GONE);
            bottomSheetDriverAction.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
        JobUtil.removeUpdateLocationJob(context());
        JobUtil.removeTrackOrderJob(context());
        removeMarkerPath();
        tvDropAddress.setClickable(true);
        tvPickupAddress.setClickable(true);
        etParkingNumberName.setText("");
        etParkingNumberName.setEnabled(true);
        tvPickupAddress.setText(getContext().getString(R.string.hint_pickup_location));
        tvDropAddress.setText(getContext().getString(R.string.hint_drop_location));
        toolbar.setTitle(getContext().getString(R.string.title_request_ride));
        tvDropAddress.setTextColor(ContextCompat.getColor(getContext(), R.color.primary_text_color));
        tvPickupAddress.setTextColor(ContextCompat.getColor(getContext(), R.color.primary_text_color));
        AlertUtil.showSnackBar(rootView, data.getMsg());
    }

    private void removeMarkerPath() {
        if (pathLine != null) {
            pathLine.remove();
        }
        if (dropMarker != null) {
            dropMarker.remove();
        }

        if (pickupMarker != null) {
            pickupMarker.remove();
        }

        if (driverMarker != null) {
            driverMarker.remove();
        }

        dropPoint = null;
        pickupPoint = null;
    }

    @Override
    public void hideBottomView() {
        removeMarkerPath();
        layoutBottomSheetUserDetail.setVisibility(View.GONE);
        layoutBottomSheetDriverAction.setVisibility(View.GONE);
        layoutBottomSheetRideCancel.setVisibility(View.GONE);
        etParkingNumberName.setText("");
        etParkingNumberName.setEnabled(true);
        tvDropAddress.setClickable(true);
        tvPickupAddress.setClickable(true);
        tvPickupAddress.setText(getContext().getString(R.string.hint_pickup_location));
        tvDropAddress.setText(getContext().getString(R.string.hint_drop_location));
        toolbar.setTitle(getContext().getString(R.string.title_request_ride));
        tvDropAddress.setTextColor(ContextCompat.getColor(getContext(), R.color.primary_text_color));
        tvPickupAddress.setTextColor(ContextCompat.getColor(getContext(), R.color.primary_text_color));

    }

    @Override
    public void showDriverLocation(LocationData location) {
        Timber.d("Found location: (%f,%f)", location.getLat(), location.getLng());

        LatLng position = new LatLng(location.getLat(), location.getLng());

        if (driverMarker == null) {
            driverMarker = googleMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_car))
                    .rotation(location.getBearing())
                    .position(position).flat(true));
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(position, 18f), 2000, null);
            JobUtil.addTrackOrderJob(context(), 2000, this);
        } else {
            MapUtil.animateMarker(driverMarker, location);
        }
    }

    @Override
    public void destroyJobSchedule() {
        JobUtil.removeUpdateLocationJob(context());
        JobUtil.removeTrackOrderJob(context());
    }

    @Override
    public void onJobScheduled(Context context, Job job) {
        Timber.d("Update location job scheduled");

        if (currentLocation != null && isRider == 1) {
            Timber.d("Sending driver location...");
            LocationData locationData = new LocationData();
            locationData.setLat(currentLocation.getLatitude());
            locationData.setLng(currentLocation.getLongitude());
            locationData.setBearing(currentLocation.getBearing());
            presenter.updateLocation(locationData);
        } else if (isRider == 0) {
            presenter.fetchDriverLocation(rideID, driverID);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Callback)
            callback = (Callback) context;
    }

    public interface Callback {
        void openReviewScreen(RatingData ratingData);

        void showCancelReason(String reason, String isRider);
    }

    private void initCameraIdle(boolean isPickupAddress) {
        googleMap.setOnCameraMoveStartedListener(reason -> {
            if (reason == GoogleMap.OnCameraMoveStartedListener.REASON_GESTURE) {
                isMapMove = true;
            } else if (reason == GoogleMap.OnCameraMoveStartedListener
                    .REASON_API_ANIMATION) {
            } else if (reason == GoogleMap.OnCameraMoveStartedListener
                    .REASON_DEVELOPER_ANIMATION) {
            }

            if (mapFragment.getView() != null) {
                ImageView ivLocation = ((View) mapFragment.getView().findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
                ivLocation.setVisibility(View.VISIBLE);
            }
        });

        googleMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                if (mapFragment.getView() != null && isMapMove) {
                    center = googleMap.getCameraPosition().target;
                    getAddressFromLocation(center.latitude, center.longitude, isPickupAddress);
                }
            }
        });

        googleMap.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
            @Override
            public void onCameraMove() {
                googleMap.clear();
            }
        });
    }


    private void getAddressFromLocation(double latitude, double longitude, boolean isPickupAddress) {
        isMapMove = false;
        Geocoder geocoder = new Geocoder(getContext(), Locale.ENGLISH);
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses.size() > 0) {
                Address fetchedAddress = addresses.get(0);
                Log.e("Address Data", fetchedAddress.toString());
                StringBuilder strAddress = new StringBuilder();
                LatLng latLng = new LatLng(latitude, longitude);

                String subLocality = "", featureName = "", locality = "", postalCode = "";
                if (!TextUtils.isEmpty(fetchedAddress.getFeatureName()))
                    featureName = fetchedAddress.getFeatureName();
                if (!TextUtils.isEmpty(fetchedAddress.getSubLocality()))
                    subLocality = fetchedAddress.getSubLocality();
                if (!TextUtils.isEmpty(fetchedAddress.getLocality()))
                    locality = fetchedAddress.getLocality();
                if (!TextUtils.isEmpty(fetchedAddress.getPostalCode()))
                    postalCode = fetchedAddress.getPostalCode();


                strAddress.append(featureName).
                        append(" ").append(subLocality).append(" ").append(locality)
                        .append(" ").append(postalCode);

                if (isPickupAddress) {
                    pickupPoint = latLng;
                    tvPickupAddress.setText(strAddress.toString());
                    tvPickupAddress.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
                    pickupMarker.setSnippet(String.valueOf(strAddress.toString()));
                    pickupMarker.setPosition(pickupPoint);
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(pickupMarker.getPosition(), 15f), 2000, null);
                    pickupAddress = new AddressData.Builder()
                            .setAddressLine1(strAddress.toString())
                            .setLat(latitude)
                            .setLng(longitude)
                            .build();

                } else {
                    dropPoint = latLng;
                    tvDropAddress.setText(strAddress.toString());
                    tvDropAddress.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
//                    dropMarker.setSnippet(String.valueOf(strAddress.toString()));
//                    dropMarker.setPosition(dropPoint);
//                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(dropMarker.getPosition(), 15f), 2000, null);
                    dropAddress = new AddressData.Builder()
                            .setAddressLine1(strAddress.toString() + " ")
                            .setLat(latitude)
                            .setLng(longitude)
                            .build();
                }
            } else {
                if (this.isPickupAddress)
                    tvPickupAddress.setText("Searching Current Address");
                else
                    tvDropAddress.setText("Searching Current Address");

            }

        } catch (IOException e) {
            e.printStackTrace();
            printToast("Could not get address..!");
        }
    }

    private void printToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }


    public void showDialog(boolean isPickupAddress) {
        final String[] names = {"Enter your location", "Set location on map"};
        final Dialog dialog = new Dialog(getActivity());

        dialog.setContentView(R.layout.activity_list_home);
        ListView lv = (ListView) dialog.findViewById(R.id.listView1);
        ArrayAdapter adapter = new ArrayAdapter(getActivity(), R.layout.dialog_custom_list_home, R.id.tv_list, names);
        lv.setAdapter(adapter);
        dialog.setCancelable(true);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dialog.dismiss();
                if (names[position].equals("Enter your location")) {
                    Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fields).build(getContext());
                    locationMarker.setVisibility(View.GONE);
                    if (isPickupAddress)
                        startActivityForResult(intent, PICKUP_ADDRESS_REQUEST);
                    else
                        startActivityForResult(intent, DROP_ADDRESS_REQUEST);
                } else if (names[position].equals("Set location on map")) {
                    btn_done.setVisibility(View.VISIBLE);
                    initCameraIdle(isPickupAddress);
                    locationMarker.setVisibility(View.VISIBLE);
                }

            }
        });

        dialog.show();

    }
}