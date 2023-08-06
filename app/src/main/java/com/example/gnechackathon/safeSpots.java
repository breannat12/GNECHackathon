package com.example.gnechackathon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;

public class safeSpots extends AppCompatActivity implements OnMapReadyCallback {

    private final int FINE_PERMISSION_CODE = 1;
    private GoogleMap myMap;
    private SearchView mapSearchView;
    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;

    Button policeStationButton;
    Button hospitalButton;

    private boolean policeOn = false;
    private boolean hospitalOn = false;

    Marker policeMarker1;
    Marker policeMarker2;
    Marker policeMarker3;

    Marker hospitalMarker1;
    Marker hospitalMarker2;
    Marker hospitalMarker3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_safe_spots);

        mapSearchView = findViewById(R.id.mapSearch);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        getLastLocation();

        mapSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                String location = mapSearchView.getQuery().toString();
                List<Address> addressList = null;

                if (location != null) {
                    Geocoder geocoder = new Geocoder(safeSpots.this);

                    try {
                        addressList = geocoder.getFromLocationName(location, 1);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    Address address = addressList.get(0);
                    LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                    myMap.addMarker(new MarkerOptions().position(latLng).title(location));
                    myMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
                }

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        policeStationButton = findViewById(R.id.policeStationButton);
        policeStationButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if (policeOn == false) {
                    float hue = 240; // Blue color
                    BitmapDescriptor markerIcon = BitmapDescriptorFactory.defaultMarker(hue);
                    LatLng police1 = new LatLng(32.9592113, -117.1228005);
                    policeMarker1 =myMap.addMarker(new MarkerOptions()
                            .position(police1)
                            .title("San Diego Police Department")
                            .icon(markerIcon));

                    LatLng police2 = new LatLng(32.7494320804, -117.081239424);
                    policeMarker2 = myMap.addMarker(new MarkerOptions()
                            .position(police2)
                            .title("San Diego Police Department")
                            .icon(markerIcon));
                    LatLng police3 = new LatLng(32.9474838, -117.2379002);
                    policeMarker3 = myMap.addMarker(new MarkerOptions()
                            .position(police3)
                            .title("San Diego Police Department")
                            .icon(markerIcon));

                    policeOn = true;
                }
                else if (policeOn == true) {
                    policeMarker1.remove();
                    policeMarker2.remove();
                    policeMarker3.remove();
                    policeOn = false;
                }
            }

        });

        hospitalButton = findViewById(R.id.hospitalButton);
        hospitalButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if (hospitalOn == false) {
                    float hue = 120; // Blue color
                    BitmapDescriptor markerIcon = BitmapDescriptorFactory.defaultMarker(hue);
                    LatLng hospital1 = new LatLng(32.751545, -117.1605);
                    hospitalMarker1 =myMap.addMarker(new MarkerOptions()
                            .position(hospital1)
                            .title("Scripps Mercy Hospital San Diego")
                            .icon(markerIcon));

                    LatLng hospital2 = new LatLng(32.799493, -117.153945);
                    hospitalMarker2 = myMap.addMarker(new MarkerOptions()
                            .position(hospital2)
                            .title("Sharp Memorial Hospital")
                            .icon(markerIcon));
                    LatLng hospital3 = new LatLng(32.777243, -117.057335);
                    hospitalMarker3 = myMap.addMarker(new MarkerOptions()
                            .position(hospital3)
                            .title("Alvarado Hospital Medical Center")
                            .icon(markerIcon));

                    hospitalOn = true;
                }
                else if (hospitalOn == true) {
                    hospitalMarker1.remove();
                    hospitalMarker2.remove();
                    hospitalMarker3.remove();
                    hospitalOn = false;
                }
            }

        });
    }

    private void getLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, FINE_PERMISSION_CODE);
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location != null) {
                    currentLocation = location;

                    SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
                    mapFragment.getMapAsync(safeSpots.this);
                }
            }
        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        myMap = googleMap;

        LatLng sydney = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
        myMap.addMarker(new MarkerOptions().position(sydney).title("My Location"));
        myMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 10));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == FINE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getLastLocation();
            }
            else {
                Toast.makeText(this, "location permission denied, please allow permission", Toast.LENGTH_SHORT).show();
            }
        }
    }
}