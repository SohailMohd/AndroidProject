package ca.uoit.msohail.charityapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.SoundEffectConstants;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.net.Inet4Address;

/**
 * Created by 100485582 on 11/17/2016.
 */

public class CharityOptions extends Activity{
    FirebaseDatabase database;
    FirebaseAuth.AuthStateListener authListener;
    String username = "", useremail = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.charity_options);

    }

    @Override
    protected void onStart() {
        super.onStart();

        authListener = new FirebaseAuth.AuthStateListener(){
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if(user != null){
                    username = user.getDisplayName();
                    useremail = user.getEmail();
                    //Log.i("Username", username);
                    //Log.i("Email", useremail);
                }
                else{
                    //Log.i("Signin", "Could not retrieve");
                }
            }


        };
    }

    public void openNearbyCharity(View view) {
        Intent openCharity = new Intent(this, NearbyCharities.class);
        view.playSoundEffect(SoundEffectConstants.CLICK);
        startActivity(openCharity);

    }

    public void openViewAdverisement(View view) {
        Intent openView = new Intent(this, ViewAdvertisements.class);
        view.playSoundEffect(SoundEffectConstants.CLICK);
        startActivity(openView);
    }

    public void openPostAdverisement(View view) {
        Intent openPost = new Intent(this, PostAdvertisement.class);
        view.playSoundEffect(SoundEffectConstants.CLICK);
        startActivity(openPost);
    }

    public void openAustraliaCharity(View view) {
        Intent openAustralia = new Intent(this, CharityAustralia.class);
        view.playSoundEffect(SoundEffectConstants.CLICK);
        startActivity(openAustralia);
    }

    public void logOut(View view) {
        Toast.makeText(this, "Logged out", Toast.LENGTH_LONG).show();
        finish();
    }
}
