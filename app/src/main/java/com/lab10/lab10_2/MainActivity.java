package com.lab10.lab10_2;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;


@RequiresApi(api = Build.VERSION_CODES.N)
public class MainActivity extends AppCompatActivity {

    private LocationManager locationManager;
    private FusedLocationProviderClient fusedLocationProviderClient;
    public TextView lat1, lat2, long1, long2, alt1, alt2, exact1, exact2, bear1, bear2, speed1, speed2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lat1 = findViewById(R.id.tb2);
        long1 = findViewById(R.id.tb4);
        alt1 = findViewById(R.id.tb6);
        exact1 = findViewById(R.id.tb8);
        lat2 = findViewById(R.id.tb10);
        long2 = findViewById(R.id.tb12);
        alt2 = findViewById(R.id.tb14);
        exact2 = findViewById(R.id.tb16);
        bear1 = findViewById(R.id.tb18);
        bear2 = findViewById(R.id.tb22);
        speed1 = findViewById(R.id.tb20);
        speed2 = findViewById(R.id.tb24);
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
    }
    @Override
    protected void onStart() {
        super.onStart();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            ubicacion();
        }
        else {
            askPermission();
        }
    }

    public void ubicacion() {
        Location ubicacion1 = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        Task<Location> ubicaciontask = fusedLocationProviderClient.getLastLocation();
        if(ubicacion1!=null){
            lat1.setText(String.valueOf(ubicacion1.getLatitude()));
            long1.setText(String.valueOf(ubicacion1.getLongitude()));
            alt1.setText(String.valueOf(ubicacion1.getAltitude()));
            exact1.setText(String.valueOf(ubicacion1.getAccuracy()));
            bear1.setText(String.valueOf(ubicacion1.getBearing()));
            speed1.setText(String.valueOf(ubicacion1.getSpeed()));
        }

        ubicaciontask.addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location ubicacion2) {
                if(ubicacion2!=null){
                    lat2.setText(String.valueOf(ubicacion2.getLatitude()));
                    long2.setText(String.valueOf(ubicacion2.getLongitude()));
                    alt2.setText(String.valueOf(ubicacion2.getAltitude()));
                    exact2.setText(String.valueOf(ubicacion2.getAccuracy()));
                    bear2.setText(String.valueOf(ubicacion2.getBearing()));
                    speed2.setText(String.valueOf(ubicacion2.getSpeed()));
                }
            }
        });
    }


    private void askPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION) && ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION)){
                ActivityCompat.requestPermissions(this, new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
                }, 10001);
            }
            else{
                ActivityCompat.requestPermissions(this, new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
                }, 10001);
            }
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 10001) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted
                ubicacion();
            } else {
                //Permission not granted
            }
        }
    }
}