package ca.uoit.msohail.charityapp;

import android.app.Activity;
import android.app.Application;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

/**
 * Created by 100485582 on 10/10/2016.
 */

public class Register  extends Activity {

    EditText registerUsername;
    EditText registerPassword;
    EditText registerEmail;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);

        registerUsername = (EditText)findViewById(R.id.registerUsername);
        registerPassword = (EditText)findViewById(R.id.registerPassword);
        registerEmail = (EditText)findViewById(R.id.registerEmail);




    }


    public void storeUserInfo(View view){


        FirebaseAuth firebaseAuth;

        firebaseAuth = FirebaseAuth.getInstance();



        String username = registerUsername.getText().toString();
        String password = registerPassword.getText().toString();
        String email = registerEmail.getText().toString();

        Toast.makeText(this, username, Toast.LENGTH_LONG).show();

        Log.i("CharityApp: ", username);
        if(TextUtils.isEmpty(username)){
            Toast.makeText(this, "Need to enter username", Toast.LENGTH_LONG).show();

        }
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Need to enter email", Toast.LENGTH_LONG).show();
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Need to enter a password", Toast.LENGTH_LONG).show();
        }
        else {
            progressDialog = ProgressDialog.show(Register.this, "Please wait...", "Processing...", true);
           /* progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Please wait, the user is being registered");
            progressDialog.show();*/

            (firebaseAuth.createUserWithEmailAndPassword(email, password))
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>(){

                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressDialog.dismiss();

                            if (task.isSuccessful()){
                                Toast.makeText(Register.this, "Sucessfully registered", Toast.LENGTH_LONG).show();
                                finish();
                            }
                            else{
                                Toast.makeText(Register.this, "Did not sucessfully register", Toast.LENGTH_LONG).show();
                            }

                        }
                    });
        /* */

        }
        //finish();

    }

}
