package com.directparking.app.ui.parkingslots;

import com.directparking.app.data.user.UserManager;
import com.directparking.app.network.RestService;

/**
 * Created by root on 24/7/18.
 */

public class ParkingSlotInteractorimpl implements ParkingSlotInteractor {

    private RestService restService;
    private UserManager userManager;

    public ParkingSlotInteractorimpl(RestService restService, UserManager userManager) {
        this.restService = restService;
        this.userManager = userManager;
    }

}
