package com.example.onfieldtbs_android.ui.views;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.onfieldtbs_android.IncidenceDetailActivity;
import com.example.onfieldtbs_android.R;
import com.example.onfieldtbs_android.databinding.FragmentMapBinding;
import com.example.onfieldtbs_android.models.Incidence;
import com.example.onfieldtbs_android.service.api.ApiClient;
import com.example.onfieldtbs_android.service.api.RetrofitCallBack;
import com.example.onfieldtbs_android.service.google.GoogleLocationServices;
import com.example.onfieldtbs_android.ui.viewModels.CompanyViewModel;
import com.example.onfieldtbs_android.utils.permission.LocationPermissionHelper;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Objects;


public class MapFragment extends Fragment implements OnMapReadyCallback {

    private static final int MAX_DISTANCE_INCIDENCE = 2000; // 2 km
    private FragmentMapBinding binding;
    private GoogleMap mMap;
    Location myCurrentLocation;
    CompanyViewModel companyViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMapBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        myCurrentLocation = new Location("");
        companyViewModel =  new ViewModelProvider(requireActivity()).get(CompanyViewModel.class);

        // Maps
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        setMapFragment();


        // get the current location and create a marker in the map
        if(!isLocationPermissionGranted()){
            askForPermission();
        }

        // Fetch de companies
        companyViewModel.getCompaniesInfo();
    }



    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        setMarkerOfCurrentPosition();
    }


    // METHODS

    private void setMapFragment() {
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.fm_map);
        Objects.requireNonNull(mapFragment).getMapAsync(this);
    }

    private boolean isLocationPermissionGranted() {
        return ActivityCompat.checkSelfPermission(requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    private void askForPermission() {
        // PERMISSIONS
        String[] requirePermission = new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        };
        // Verify the permission
        LocationPermissionHelper.startPermissionRequest(this, isGranted -> {
            Boolean fineLocationGranted = isGranted.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false);
            Boolean coarseLocationGranted = isGranted.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false);
            if (Boolean.TRUE.equals(fineLocationGranted) && Boolean.TRUE.equals(coarseLocationGranted)) {
                setMarkerOfCurrentPosition();
            } else {
                Toast.makeText(getContext(), "La aplicacion no tiene los permisos necesarios", Toast.LENGTH_LONG).show();
            }
        }, requirePermission);
    }

    private void setMarkerOfCurrentPosition() {
        GoogleLocationServices.getCoordinate(getContext(), (latitude, longitude) -> {
            LatLng currentPosition = new LatLng(latitude, longitude);
            mMap.addMarker(new MarkerOptions()
                            .position(currentPosition)
                            .title("Tu posicion Actual")
                            .icon(BitmapDescriptorFactory.fromBitmap(getBitmapFromImage(getResources(), R.drawable.user_profile)))
            ).showInfoWindow();
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentPosition, 14f));
            myCurrentLocation.setLatitude(latitude);
            myCurrentLocation.setLongitude(longitude);
            createMarkers(myCurrentLocation);
        });
    }
    private void createMarkers( Location currentLocation){
        companyViewModel.readCompanies().observe(getViewLifecycleOwner(), companiesInfo -> {
            companiesInfo.forEach(comp ->{
                double latitude = comp.getLocation().getLat();
                double longitude = comp.getLocation().getLng();
                LatLng position = new LatLng(latitude, longitude);
                String firstIncidence = comp.getIncidencesList().stream().findFirst().orElse("Not Incidences");
                if(createAndroidLocation(latitude, longitude).distanceTo(currentLocation) < MAX_DISTANCE_INCIDENCE){
                    mMap.addMarker( new MarkerOptions()
                            .position(position)
                            .title(comp.getCompanyName())
                            .snippet(comp.getAddress() + "\n:" + firstIncidence)
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                }
                mMap.setOnInfoWindowClickListener(marker -> {
                    String incidencesId = marker.getSnippet().split(":")[1];
                    Intent intent = new Intent(requireContext(), IncidenceDetailActivity.class);
                    Bundle bundle = new Bundle();
                    ApiClient.getApi().getIncidenceById(incidencesId).enqueue((RetrofitCallBack<Incidence>) (call, response) -> {
                        Incidence incidence = response.body();
                        bundle.putSerializable("incidence", incidence);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    });

                });
            });
        });
    }

    private Location createAndroidLocation(double latitude, double longitude){
        Location location = new Location("");
        location.setLatitude(latitude);
        location.setLongitude(longitude);
        return location;
    }

    public static Bitmap getBitmapFromImage(Resources res, int imageResource){
        Bitmap.Config conf = Bitmap.Config.ARGB_8888;
        Bitmap bmp = Bitmap.createBitmap(150, 150, conf);
        Canvas canvas = new Canvas(bmp);
        Paint color = new Paint();
        color.setColor(Color.WHITE);
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inMutable = true;
        Bitmap imageBitmap=BitmapFactory.decodeResource(res,imageResource,opt);
        Bitmap resized = Bitmap.createScaledBitmap(imageBitmap, 90, 90, true);
        canvas.drawBitmap(resized, 40, 40, color);
        return bmp;
    }

}