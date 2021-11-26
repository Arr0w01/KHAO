package com.example.khao;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.khao.ui.home.HomeFragment;
import com.example.khao2.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static com.example.khao.ui.home.HomeFragment.CurrentLocation;
import static com.example.khao.ui.home.HomeFragment.SubLocation;
import static com.example.khao.ui.home.HomeFragment.adapter3;

public class MainActivity extends AppCompatActivity implements PaymentResultListener {

    private static final int REQUEST_CODE = 1;
    FloatingActionButton fab;
    public static double latitude;
    public static double longitude;
    public static String pinCode;
    CoordinatorLayout coordinatorLayout;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Checkout.preload(getApplicationContext());

        locationRequest();
//        checkInternet();
//        noNet = findViewById(R.id.noNet);

        fab = findViewById(R.id.floatingActionButton);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 Intent intent = new Intent(getBaseContext() ,MapsActivity.class);
                 startActivity(intent);
//            showBottomSheet();
//            locationRequest();
            }
        });


        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_cart, R.id.navigation_favourite, R.id.navigation_profile)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
        navView.setBackground(null);


    }

    public void locationRequest() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}
                    , REQUEST_CODE);
        } else {

            final LocationManager manager = (LocationManager) getBaseContext().getSystemService(Context.LOCATION_SERVICE);
            if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new LocationListener() {
                    @Override
                    public void onLocationChanged(@NonNull Location location) {
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                        try {
                            Geocoder geo = new Geocoder(getApplicationContext(), Locale.getDefault());
                            List<Address> addresses = null;
                            try {
                                addresses = geo.getFromLocation(latitude, longitude, 1);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            if (addresses.isEmpty()) {
                                 CurrentLocation.setText("Waiting for location");
                            } else {

                                 CurrentLocation.setText(addresses.get(0).getSubLocality());
                                SubLocation.setText(addresses.get(0).getAddressLine(0));
                                adapter3.notifyDataSetChanged();
                                pinCode =  addresses.get(0).getPostalCode();
                            }
                        } catch (Exception e) {
//
                        }
                    }
                });
            } else {
                Toast.makeText(this, "Turn on Gps", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);



            }
//

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//
                final LocationManager manager = (LocationManager) getBaseContext().getSystemService(Context.LOCATION_SERVICE);
                if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new LocationListener() {
                        @Override
                        public void onLocationChanged(@NonNull Location location) {
                             latitude = location.getLatitude();
                             longitude = location.getLongitude();
                            try {
                                Geocoder geo = new Geocoder(getApplicationContext(), Locale.getDefault());
                                List<Address> addresses = null;
                                try {
                                    addresses = geo.getFromLocation(latitude, longitude, 1);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                if (addresses.isEmpty()) {
                                    CurrentLocation.setText("Waiting for Location");
                                } else {
                                    CurrentLocation.setText(addresses.get(0).getAdminArea());
                                    SubLocation.setText(addresses.get(0).getAddressLine(0));
                                }
                            } catch (Exception e) {
//
                            }
                        }
                    });
                }else {
                    Toast.makeText(this, "Turn on Gps", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(intent);


                }
            }else {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE);
            }
        }
    }

//    private void showBottomSheet(){
//        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this,R.style.BottomSheetDialog);
//        bottomSheetDialog.setContentView(R.layout.map_bottomsheet);
//        bottomSheetDialog.show();
//        bottomSheetDialog.getWindow().findViewById(R.id.bsMain).setBackgroundResource(android.R.color.transparent);
//    }

    @Override
    public void onPaymentSuccess(String s) {
        Toast.makeText(this, ""+s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(this, ""+s, Toast.LENGTH_SHORT).show();
    }

//    public void checkInternet(){
//
//        ConnectivityManager comMg = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo netInfo = comMg.getActiveNetworkInfo();
//        if (netInfo != null &&  netInfo.isConnectedOrConnecting()){
//
//        }else {
//            Toast.makeText(this, "No Internet Connection!", Toast.LENGTH_SHORT).show();
//        }
//    }





}