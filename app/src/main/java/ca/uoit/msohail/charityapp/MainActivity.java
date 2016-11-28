package ca.uoit.msohail.charityapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SoundEffectConstants;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    EditText loginEmail;
    EditText loginPassword;
    EditText loginUsername;
    TextView emailError;
    TextView passwordError;
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;
    String error_email;
    String error_emailFormat;
    String error_password;
    Intent openMain;

    int signinCode = 5;
    GoogleApiClient googleApi;
    TextView googleUsr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();
        loginEmail = (EditText)findViewById(R.id.edt_loginEmail);
        loginPassword = (EditText)findViewById(R.id.edt_loginPassword);
        emailError = (TextView)findViewById(R.id.emailError);
        passwordError = (TextView)findViewById(R.id.passwordError);
     //   googleUsr = (TextView)findViewById(R.id.google_displayName);

        /*GoogleSignInOptions gSigninOpt =  new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleApi = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gSigninOpt)
                .build();
        */
    }

    public void openRegisterPage(View view){
        Intent regPage = new Intent(this, Register.class);
        view.playSoundEffect(SoundEffectConstants.CLICK);
        startActivity(regPage);
    }


    public void loginVerify(View view){
        String userEmail = loginEmail.getText().toString();
        view.playSoundEffect(SoundEffectConstants.CLICK);
        String userPassword = loginPassword.getText().toString();
        final MediaPlayer errorSoundMP = MediaPlayer.create(this, R.raw.error_sound_effect);

        error_email = "Please enter your email!";
        error_emailFormat = "Email not in correct format!";
        error_password = "Please enter your password!";


        if(userEmail.equals("")){
            errorSoundMP.start();
            emailError.setText(error_email);
            return;
        }
        if(!(userEmail).contains("@")){
            errorSoundMP.start();
            emailError.setText(error_emailFormat);
            loginEmail.setText("");
            return;
        }
        if(userPassword.equals("")){
            errorSoundMP.start();
            passwordError.setText(error_password);
            return;
        }
        else {

            openMain = new Intent(this, CharityOptions.class);
            progressDialog = ProgressDialog.show(MainActivity.this, "Please wait...", "Processing...", true);

            firebaseAuth.signInWithEmailAndPassword(userEmail, userPassword)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                progressDialog.dismiss();
                                Toast.makeText(MainActivity.this, "Login successful", Toast.LENGTH_LONG).show();
                                loginEmail.setText("");
                                loginPassword.setText("");
                                emailError.setText("");
                                passwordError.setText("");
                                startActivity(openMain);
                            } else {
                                errorSoundMP.start();
                                Toast.makeText(MainActivity.this, "Login not verified, try again", Toast.LENGTH_LONG).show();
                                progressDialog.dismiss();
                                loginEmail.setText("");
                                loginPassword.setText("");
                                emailError.setText("");
                                passwordError.setText("");

                            }
                        }

                    });
        }


    }



    /*public void googleSignIn(View view) {
        Log.i("Google Btn", "Sign In button clicked");
        Intent signinIntent = Auth.GoogleSignInApi.getSignInIntent(googleApi);
        //startActivity(signinIntent);
        startActivityForResult(signinIntent, signinCode);
    }*/

/*    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == signinCode){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            setUserInfo(result);

        }
        else{
            Log.i("Request Code ", "Code does not match");
        }
    }

    private void setUserInfo(GoogleSignInResult result) {
        Log.i("User Info", "Set the result text");
        if(result.isSuccess()) {
            Log.i("Result Success", "inside on Activity for result");
            GoogleSignInAccount usrAccount = result.getSignInAccount();
       //     googleUsr.setText("User: " + usrAccount.getDisplayName());
        }
        else{

        }



    } */

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


}
