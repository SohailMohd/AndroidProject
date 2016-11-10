package ca.uoit.msohail.charityapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    EditText loginEmail;
    EditText loginPassword;
    EditText loginUsername;
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();
        loginEmail = (EditText)findViewById(R.id.edt_loginEmail);
        loginUsername = (EditText)findViewById(R.id.edt_loginUsername);
        loginPassword = (EditText)findViewById(R.id.edt_loginPassword);
    }

    public void openRegisterPage(View view){
        Intent regPage = new Intent(this, Register.class);
        startActivity(regPage);
    }

    public void loginVerify(View view){
    /*    EditText userLabel = (EditText)findViewById(R.id.lbl_userLogin);
        EditText passLabel = (EditText)findViewById(R.id.lbl_passLogin);

        String userCheck = userLabel.getText().toString();
        String passCheck = passLabel.getText().toString();
        Intent showProfile = new Intent(this, Login.class);
        Toast verifyLogin = new Toast(this);

        if(userCheck.equals("Sohail") && passCheck.equals("12345")){

            verifyLogin.makeText(this, "Login verified", verifyLogin.LENGTH_LONG).show();


            startActivity(showProfile);
        }
        else {
            verifyLogin.makeText(this, "Username or password incorrect, try again", verifyLogin.LENGTH_LONG).show();
        }
        */
        progressDialog = ProgressDialog.show(MainActivity.this, "Please wait...", "Processing...", true);

        (firebaseAuth.signInWithEmailAndPassword(loginEmail.getText().toString(), loginPassword.getText().toString()))
                .addOnCompleteListener(new OnCompleteListener <AuthResult>(){

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            progressDialog.dismiss();

                            Toast.makeText(MainActivity.this, "Sucessfully registered", Toast.LENGTH_LONG).show();
                        }
                        else {
                            Toast.makeText(MainActivity.this, "Login incorrect, try again", Toast.LENGTH_LONG).show();
                        }
                    }
                });


    }



}
