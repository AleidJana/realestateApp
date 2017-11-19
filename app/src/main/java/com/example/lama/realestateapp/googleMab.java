package com.example.lama.realestateapp;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class googleMab extends AppCompatActivity implements OnMapReadyCallback {
    GoogleMap mMap;
    String[] array;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Bundle b=this.getIntent().getExtras();
        array=b.getStringArray("info");

        if(googleServiecesAVilable()){
            //Toast.makeText(this, "perfect !!!", Toast.LENGTH_LONG).show();
            setContentView(R.layout.activity_google_mab);
            initMap();



        }else {
            // No google maps layout problem

        }
    }

    private void initMap(){
        MapFragment mapFragment =(MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    public boolean googleServiecesAVilable() {
        GoogleApiAvailability api = GoogleApiAvailability.getInstance();
        int result = api.isGooglePlayServicesAvailable(this);
        if (result == ConnectionResult.SUCCESS){
            return true;}
        else  if(api.isUserResolvableError(result)){
            Dialog dialog =api.getErrorDialog(this, result , 0);
            dialog.show();


        }else
            Toast.makeText(this, "Can't connect to play services", Toast.LENGTH_LONG).show();
        return false ;


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        goLocation(Float.parseFloat(array[4]),Float.parseFloat(array[5]) , 15 );

    }

    private void goLocation(double lat, double lng ,float zoom) {
        LatLng location = new LatLng(lat, lng);
        CameraUpdate update = CameraUpdateFactory.newLatLng(location);
        mMap.moveCamera(update);
        // Add a marker in Sydney, Australia, and move the camera.
        LatLng sydney = new LatLng(Float.parseFloat(array[4]),Float.parseFloat(array[5]));
        //.title("Marker in Sydney")
        mMap.addMarker(new MarkerOptions().position(sydney));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.animateCamera( CameraUpdateFactory.zoomTo( 17.0f ) );
    }
}
