package ca.uoit.msohail.charityapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.net.Inet4Address;

/**
 * Created by 100485582 on 11/17/2016.
 */

public class CharityOptions extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.charity_options);
    }

    public void openNearbyCharity(View view) {
        Intent openCharity = new Intent(this, NearbyCharities.class);
        startActivity(openCharity);

    }

    public void openViewAdverisement(View view) {
        Intent openView = new Intent(this, ViewAdvertisements.class);
        startActivity(openView);
    }

    public void openPostAdverisement(View view) {
        Intent openPost = new Intent(this, PostAdvertisement.class);
        startActivity(openPost);
    }
}
