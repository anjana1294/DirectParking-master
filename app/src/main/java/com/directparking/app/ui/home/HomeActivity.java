package com.directparking.app.ui.home;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.NavigationView.OnNavigationItemSelectedListener;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager.OnBackStackChangedListener;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.text.TextUtils;
import android.text.style.AlignmentSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.SparseArray;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.directparking.app.BuildConfig;
import com.directparking.app.R;
import com.directparking.app.network.ApiEndpoints;
import com.directparking.app.ui.aboutus.AboutUsFragment;
import com.directparking.app.ui.accept.AcceptFragment;
import com.directparking.app.ui.base.HomeBase;
import com.directparking.app.ui.custom.SpannyText;
import com.directparking.app.ui.history.HistoryFragment;
import com.directparking.app.ui.home.model.RatingData;
import com.directparking.app.ui.login.model.UserData;
import com.directparking.app.ui.logout.LogoutDialog;
import com.directparking.app.ui.notification.NotificationFragment;
import com.directparking.app.ui.parkingslots.ParkingSlotFragment;
import com.directparking.app.ui.password.change.ChangePasswordFragment;
import com.directparking.app.ui.profile.edit.EditProfileFragment;
import com.directparking.app.ui.profile.view.MyProfileFragment;
import com.directparking.app.ui.review.post.ReviewActivity;
import com.directparking.app.ui.settings.SettingsFragment;
import com.directparking.app.util.AlertUtil;
import com.directparking.app.util.NotificationType;
import com.google.gson.Gson;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnLongClick;
import de.hdodenhof.circleimageview.CircleImageView;
import timber.log.Timber;

import static com.directparking.app.util.Constants.ABOUT_US_FRAGMENT;
import static com.directparking.app.util.Constants.ACCEPT_REQUEST_FRAGMENT;
import static com.directparking.app.util.Constants.CHANGE_PASSWORD_FRAGMENT;
import static com.directparking.app.util.Constants.EDIT_PROFILE_FRAGMENT;
import static com.directparking.app.util.Constants.HISTORY_FRAGMENT;
import static com.directparking.app.util.Constants.HOME_FRAGMENT;
import static com.directparking.app.util.Constants.INT_CONST_FOR_ABOUT_US_FRAGMENT;
import static com.directparking.app.util.Constants.INT_CONST_FOR_ACCEPT_RIDE_FRAGMENT;
import static com.directparking.app.util.Constants.INT_CONST_FOR_CHANGE_PASSWORD_FRAGMENT;
import static com.directparking.app.util.Constants.INT_CONST_FOR_EDIT_PROFILE_FRAGMENT;
import static com.directparking.app.util.Constants.INT_CONST_FOR_HISTORY_FRAGMENT;
import static com.directparking.app.util.Constants.INT_CONST_FOR_HOME_FRAGMENT;
import static com.directparking.app.util.Constants.INT_CONST_FOR_MY_PROFILE_FRAGMENT;
import static com.directparking.app.util.Constants.INT_CONST_FOR_NOTIFICATION_FRAGMENT;
import static com.directparking.app.util.Constants.INT_CONST_FOR_PARKING_SLOT_FRAGMENT;
import static com.directparking.app.util.Constants.INT_CONST_FOR_SETTINGS_FRAGMENT;
import static com.directparking.app.util.Constants.LOCATION_SETTINGS_REQUEST;
import static com.directparking.app.util.Constants.LOGOUT_DIALOG;
import static com.directparking.app.util.Constants.MY_PROFILE_FRAGMENT;
import static com.directparking.app.util.Constants.NOTIFICATION_FRAGMENT;
import static com.directparking.app.util.Constants.PARKING_SLOT_FRAGMENT;
import static com.directparking.app.util.Constants.SETTINGS_FRAGMENT;

public class HomeActivity extends HomeBase implements OnNavigationItemSelectedListener,
        OnBackStackChangedListener, EditProfileFragment.Callback, AcceptFragment.Callback,
        MyProfileFragment.Callback, SettingsFragment.Callback, HomeFragment.Callback {

    private View navHeaderView;
    private Fragment currentFragment;
    private MenuItem drawerSelectedMenuItem;
    private android.support.v7.app.ActionBarDrawerToggle drawerToggle;
    private SparseArray<Fragment> fragmentStore = new SparseArray<>();

    @BindView(R.id.toolbar)
    Toolbar toolbar;

 /*   @OnLongClick(R.id.toolbar)
    boolean toggleDebugFab() {
        if (BuildConfig.DEBUG) {
            if (debugFab.getVisibility() == View.VISIBLE) {
                debugFab.hide();
            } else {
                debugFab.show();
            }
        }
        return true;
    }*/

    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @BindView(R.id.nav_view)
    NavigationView navView;

/*
    @BindView(R.id.debug_fab)
    FloatingActionButton debugFab;
*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        getSupportFragmentManager().addOnBackStackChangedListener(this);

        initView();

        showProfileDetail();


        drawerToggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.nav_drawer_open, R.string.nav_drawer_close) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);

                if (drawerSelectedMenuItem != null) {
                    switch (drawerSelectedMenuItem.getItemId()) {
                        case R.id.nav_menu_home:
                            showHomeScreen();
                            break;
                        case R.id.nav_menu_aboutus:
                            showAboutUsScreen();
                            break;
                        case R.id.nav_menu_parking:
                            showParkingSlot();
                            break;
                        case R.id.nav_menu_accept_request:
                            showAcceptRequest();
                            break;
//                        case R.id.nav_menu_notifications:
//                            showNotifications();
//                            break;
                        case R.id.nav_menu_settings:
//                            showPasswordChange();
                            showSettingsScreen();
                            break;
                        case R.id.nav_menu_logout:
                            LogoutDialog.newInstance().show(getSupportFragmentManager(), LOGOUT_DIALOG);
                            break;
                    }
                    drawerSelectedMenuItem = null;
                }
            }
        };
        drawerLayout.addDrawerListener(drawerToggle);
        navView.setNavigationItemSelectedListener(this);
        navView.setItemIconTintList(null);
        navView.inflateMenu(R.menu.menu_nav_home);
        showLandingFragment();
    }

    private void showPasswordChange() {
        currentFragment = fragmentStore.get(INT_CONST_FOR_CHANGE_PASSWORD_FRAGMENT);
        if (currentFragment == null) {
            currentFragment = ChangePasswordFragment.newInstance();
            fragmentStore.put(INT_CONST_FOR_CHANGE_PASSWORD_FRAGMENT, currentFragment);
        }
        fragmentTransition(currentFragment, CHANGE_PASSWORD_FRAGMENT);
    }

    private void showMyProfile() {
        currentFragment = fragmentStore.get(INT_CONST_FOR_MY_PROFILE_FRAGMENT);
        if (currentFragment == null) {
            currentFragment = MyProfileFragment.newInstance();
            fragmentStore.put(INT_CONST_FOR_MY_PROFILE_FRAGMENT, currentFragment);
        }
        fragmentTransition(currentFragment, MY_PROFILE_FRAGMENT);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    private void initView() {
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
        }

      /*  if (BuildConfig.DEBUG) {
            debugFab.setOnClickListener(view -> {
                PopupMenu popup = new PopupMenu(drawerLayout.getContext(), view);
                popup.inflate(R.menu.menu_api_endpoint);

                if (ApiEndpoints.isReleaseMode(apiEndpoint.get())) {
                    popup.getMenu().findItem(R.id.menu_release).setChecked(true);
                }

                popup.setOnMenuItemClickListener(item -> {
                    switch (item.getItemId()) {
                        case R.id.menu_mock:
                            setEndpointAndRelaunch(ApiEndpoints.MOCK.url);
                            return true;
                        case R.id.menu_release:
                            setEndpointAndRelaunch(ApiEndpoints.RELEASE.url);
                            return true;
                        default:
                            return false;
                    }
                });
                popup.show();
            });
        }
*/
        navHeaderView = navView.getHeaderView(0);
        navHeaderView.findViewById(R.id.layout_profile).setOnClickListener(view -> {
            drawerLayout.closeDrawer(GravityCompat.START);
            showMyProfile();
        });
//        navHeaderView.findViewById(R.id.nav_tv_edit).setOnClickListener(view -> {
//            drawerLayout.closeDrawer(GravityCompat.START);
//            showEditProfile();
//        });
    }

    private void showLandingFragment() {
        if (getIntent().hasExtra("type")) {
            try {
                String notificationType = getIntent().getStringExtra("type");
                if (notificationType.equals(NotificationType.NEW_REQUEST)) {
                    showAcceptRequest();
                } else if (notificationType.equals(NotificationType.REQUEST_CANCEL)) {
                    showHomeScreen();
                    showRideCancelResonPopup(getIntent().getStringExtra("reason"), getIntent().getStringExtra("body"));
                } else {
                    showHomeScreen();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else
            showHomeScreen();
    }

    private void showRideCancelResonPopup(String reason, String body) {
        String msg;
        String cancel="\nWould you like to resend a new ride request?";

        if (!reason.endsWith("."))
            reason = reason + ".";
//        msg = "Reason: " + reason + "\nWould you like to search for a new ride request?";
        if (body.contains("rider")) {
             cancel="\nWould you like to search for a new ride request?";
             msg = "Reason: " + reason + cancel;

        AlertUtil.showActionAlertDialog(this, "Rider Cancelled Ride Request", msg,
                "Yes", "No", (dialog, which) ->
                        showAcceptRequest());
    }  else{

            String cancelReason="Reason: "+reason +cancel;
            AlertUtil.showAlertDialog(context(), "Driver Cancelled Ride Request", cancelReason, "OK");
        }

    }

    private void showHomeScreen() {
        currentFragment = fragmentStore.get(INT_CONST_FOR_HOME_FRAGMENT);
        if (currentFragment == null) {
            currentFragment = HomeFragment.newInstance();
            fragmentStore.put(INT_CONST_FOR_HOME_FRAGMENT, currentFragment);
        }
        fragmentTransition(currentFragment, HOME_FRAGMENT);
    }

    private void showAboutUsScreen() {
        currentFragment = fragmentStore.get(INT_CONST_FOR_ABOUT_US_FRAGMENT);
        if (currentFragment == null) {
            currentFragment = new AboutUsFragment();
            fragmentStore.put(INT_CONST_FOR_ABOUT_US_FRAGMENT, currentFragment);
        }
        fragmentTransition(currentFragment, ABOUT_US_FRAGMENT);
    }

    private void showAcceptRequest() {
        currentFragment = fragmentStore.get(INT_CONST_FOR_ACCEPT_RIDE_FRAGMENT);
        if (currentFragment == null) {
            currentFragment = AcceptFragment.newInstance();
            fragmentStore.put(INT_CONST_FOR_ACCEPT_RIDE_FRAGMENT, currentFragment);
        }
        fragmentTransition(currentFragment, ACCEPT_REQUEST_FRAGMENT);
    }

    private void showParkingSlot() {
        currentFragment = fragmentStore.get(INT_CONST_FOR_PARKING_SLOT_FRAGMENT);
        if (currentFragment == null) {
            currentFragment = ParkingSlotFragment.newInstance();
            fragmentStore.put(INT_CONST_FOR_PARKING_SLOT_FRAGMENT, currentFragment);
        }
        fragmentTransition(currentFragment, PARKING_SLOT_FRAGMENT);
    }

    private void showEditProfile() {
        currentFragment = fragmentStore.get(INT_CONST_FOR_EDIT_PROFILE_FRAGMENT);
        if (currentFragment == null) {
            currentFragment = EditProfileFragment.newInstance();
            fragmentStore.put(INT_CONST_FOR_EDIT_PROFILE_FRAGMENT, currentFragment);
        }
        fragmentTransition(currentFragment, EDIT_PROFILE_FRAGMENT);
    }

    private void showNotifications() {
//        navView.setCheckedItem(R.id.nav_menu_notifications);

        currentFragment = fragmentStore.get(INT_CONST_FOR_NOTIFICATION_FRAGMENT);
        if (currentFragment == null) {
            currentFragment = NotificationFragment.newInstance();
            fragmentStore.put(INT_CONST_FOR_NOTIFICATION_FRAGMENT, currentFragment);
        }
        fragmentTransition(currentFragment, NOTIFICATION_FRAGMENT);
    }

    private void showSettingsScreen() {
        currentFragment = fragmentStore.get(INT_CONST_FOR_SETTINGS_FRAGMENT);
        if (currentFragment == null) {
            currentFragment = new SettingsFragment();
            fragmentStore.put(INT_CONST_FOR_SETTINGS_FRAGMENT, currentFragment);
        }
        fragmentTransition(currentFragment, SETTINGS_FRAGMENT);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem drawerSelectedMenuItem) {
        this.drawerSelectedMenuItem = drawerSelectedMenuItem;
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackStackChanged() {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.layout_main);
        if (currentFragment instanceof HomeFragment) {
            navView.setCheckedItem(R.id.nav_menu_home);
        }
//        else if (currentFragment instanceof NotificationFragment) {
//            navView.setCheckedItem(R.id.nav_menu_notifications);
//        }
    }

    @Override
    public void showProfileDetail() {
        CircleImageView civNavProfile = navHeaderView.findViewById(R.id.civ_nav_profile);
        TextView navTvName = navHeaderView.findViewById(R.id.nav_tv_name);
        TextView navTvRating = navHeaderView.findViewById(R.id.nav_tv_rating);

        String jsonData = userManager.getUserData();
        UserData userData = new Gson().fromJson(jsonData, UserData.class);

        if (userData != null) {
            navTvName.setText(String.format("%s %s", userData.getFirstName(), userData.getLastName()));

            if (!TextUtils.isEmpty(userData.getRating() + "")) {
                SpannyText spanny = new SpannyText(userData.getRating() + "")
                        .append(" ")
                        .append("★", new RelativeSizeSpan(0.8f), new ForegroundColorSpan(Color.BLACK),
                                new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER));
                navTvRating.setText(spanny);
            }
        }

        try {
            if (userManager.getProfilePath() != null) {
                picasso.load(userManager.getProfilePath())
                        .memoryPolicy(MemoryPolicy.NO_CACHE)
                        .networkPolicy(NetworkPolicy.NO_CACHE)
                        .placeholder(R.drawable.ic_loading)
                        .error(R.drawable.ic_default_profile)
                        .into(civNavProfile);
            }
        } catch (Exception e) {
            Timber.e(e);
            picasso.load(R.drawable.ic_default_profile).fit().into(civNavProfile);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case LOCATION_SETTINGS_REQUEST:
                Fragment homeFragment = getSupportFragmentManager().findFragmentByTag(HOME_FRAGMENT);
                if (homeFragment != null && homeFragment.isAdded()) {
                    homeFragment.onActivityResult(requestCode, resultCode, data);
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void openHomeScreen() {
        showHomeScreen();
    }

    @Override
    public void showUserHistory() {
        currentFragment = fragmentStore.get(INT_CONST_FOR_HISTORY_FRAGMENT);
        if (currentFragment == null) {
            currentFragment = new HistoryFragment();
            fragmentStore.put(INT_CONST_FOR_HISTORY_FRAGMENT, currentFragment);
        }
        fragmentTransition(currentFragment, HISTORY_FRAGMENT);


    }

    @Override
    public void openChangePasswordScreen() {
        showPasswordChange();
    }

    @Override
    public void openEditProfileScreen() {
        showEditProfile();
    }

    @Override
    public void openReviewScreen(RatingData ratingData) {
            Intent intent = new Intent(this, ReviewActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("rideId", ratingData.getRide_id());
        intent.putExtra("totalTime", ratingData.getArrivaltime());
        intent.putExtra("isRider", ratingData.getIsRider());
        intent.putExtra("relatedId", ratingData.getAccept_user_id());
        startActivity(intent);
        finish();
    }

    @Override
    public void showCancelReason(String reason, String isRider) {
        showRideCancelResonPopup(reason, isRider);
    }
}