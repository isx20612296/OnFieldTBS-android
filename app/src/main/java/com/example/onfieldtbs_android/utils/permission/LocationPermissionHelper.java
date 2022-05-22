package com.example.onfieldtbs_android.utils.permission;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

public class LocationPermissionHelper {
    public static void startPermissionRequest(Fragment fragment, LocationPermission lp, String[] manifest){
        ActivityResultLauncher<String[]> locationPermissionRequest =
                fragment.registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), lp::onGranted);

        locationPermissionRequest.launch(manifest);
    }
}
