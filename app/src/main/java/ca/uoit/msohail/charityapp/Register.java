package ca.uoit.msohail.charityapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.SoundEffectConstants;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;

import static ca.uoit.msohail.charityapp.R.raw.error_sound_effect;

/**
 * Created by 100485582 on 10/10/2016.
 */

public class Register  extends Activity {

    EditText registerUsername;
    EditText registerPassword;
    EditText registerEmail;
    EditText registerAddress;
    EditText registerPhoneNum;

    MediaPlayer errorSoundMP;


    Set<Integer> idValues = new HashSet<Integer>();
    private final int maxUsers = 20000;

    ProgressDialog progressDialog;
    FirebaseDatabase database;
    FirebaseAuth firebaseAuth;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);

        registerUsername = (EditText)findViewById(R.id.registerUsername);
        registerPassword = (EditText)findViewById(R.id.registerPassword);
        registerEmail = (EditText)findViewById(R.id.registerEmail);
        registerAddress = (EditText)findViewById(R.id.registerAddress);
        registerPhoneNum = (EditText)findViewById(R.id.registerPhonenum);

        database = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        errorSoundMP = MediaPlayer.create(this, R.raw.error_sound_effect);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //need to use shared perferences or serialize/deserialize the integer set of idvalues
    }

    public void storeUserInfo(View view){
        view.playSoundEffect(SoundEffectConstants.CLICK);


        String username = registerUsername.getText().toString();
        String password = registerPassword.getText().toString();
        String email = registerEmail.getText().toString();
        String address = registerAddress.getText().toString();
        String phonenum = registerPhoneNum.getText().toString();

        intent = new Intent(this, MainActivity.class);


        //Log.i("CharityApp: ", email + " " + password);
        if(username.equals("")){
            Toast.makeText(this, "Need to enter username", Toast.LENGTH_LONG).show();
            errorSoundMP.start();
            return;
        }
        if(email.equals("")){
            Toast.makeText(this, "Need to enter email", Toast.LENGTH_LONG).show();
            errorSoundMP.start();
            return;
        }
        if(!email.contains("@")){
            Toast.makeText(this, "Email incorrect format", Toast.LENGTH_LONG).show();
            errorSoundMP.start();
            return;
        }
        if(password.equals("")){
            Toast.makeText(this, "Need to enter password", Toast.LENGTH_LONG).show();
            errorSoundMP.start();
            return;
        }
        if(password.length() < 5){
            Toast.makeText(this, "Passwords need to be at least 6 characters long", Toast.LENGTH_LONG).show();
            errorSoundMP.start();
            return;
        }
        progressDialog = ProgressDialog.show(Register.this, "Please wait...", "Processing...", true);

        //password entered needs to be at least 6 characters in length
        //else will task will not be successful and will fail to register
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressDialog.dismiss();
                            if(task.isSuccessful()){
                                Toast.makeText(Register.this, "Sucessfully registered", Toast.LENGTH_LONG).show();

                                finish();
                            }
                            else{
                                errorSoundMP.start();
                                //password needs to be at least 6 character in length, or user already registered if email and password same
                                Toast.makeText(Register.this, "User already registered", Toast.LENGTH_LONG).show();

                                finish();
                            }

                        }
                    });


        User aNewUser = new User(username, email, phonenum, address);
        DatabaseReference dbRef = database.getReference();

        String userId ="";
        Random rand = new Random();

        int id = rand.nextInt(maxUsers);
        int i = 0;
        //Log.i("random number: ", ""+id);

        userId = "" + id;
        dbRef.child("Users").child(userId).setValue(aNewUser);

        if(id != -1) {

        }
        else {
            Toast.makeText(this, "Database full, can not store user", Toast.LENGTH_LONG).show();
            return;
        }

        dbRef.onDisconnect();

    }

}
