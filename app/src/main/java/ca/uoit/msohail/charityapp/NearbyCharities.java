package ca.uoit.msohail.charityapp;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.GoogleMap;

import org.w3c.dom.Text;

/**
 * Created by 100485582 on 11/19/2016.
 */

public class NearbyCharities extends FragmentActivity implements GoogleApiClient.OnConnectionFailedListener {

    private GoogleApiClient googleApiClient;
    private int placesRequestCode = 3;
    TextView placeName;
    TextView placeAddress;
    private double lat;
    private double lng;

    private GoogleMap googleMap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nearby_charities);

        placeName = (TextView)findViewById(R.id.txt_placeName);
        placeAddress = (TextView)findViewById(R.id.txt_placeAddress);

        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, this)
                .build();


        PlacePicker.IntentBuilder placesIntent = new PlacePicker.IntentBuilder();

        try {
            startActivityForResult(placesIntent.build(this), placesRequestCode);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }/* */

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == requestCode){
            if(resultCode == RESULT_OK){
                Place place = PlacePicker.getPlace(this, data);

                lat = place.getLatLng().latitude;
                lng = place.getLatLng().longitude;
                //String info = place.getAddress();

                placeName.setText(place.getName());
                placeAddress.setText(place.getAddress());
            }
        }
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this, "Connection to Google Places api failed", Toast.LENGTH_LONG).show();
    }

    public void returnBtn(View view) {
        finish();
    }
}

