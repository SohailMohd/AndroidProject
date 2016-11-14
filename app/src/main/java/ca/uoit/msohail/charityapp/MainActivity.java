package ca.uoit.msohail.charityapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();
        loginEmail = (EditText)findViewById(R.id.edt_loginEmail);
        loginPassword = (EditText)findViewById(R.id.edt_loginPassword);
        emailError = (TextView)findViewById(R.id.emailError);
        passwordError = (TextView)findViewById(R.id.passwordError);

    }

    public void openRegisterPage(View view){
        Intent regPage = new Intent(this, Register.class);
        startActivity(regPage);
    }

    public void loginVerify(View view){
        String userEmail = loginEmail.getText().toString();
        String userPassword = loginPassword.getText().toString();

        error_email = "Please enter your email!";
        error_emailFormat = "Email not in correct format!";
        error_password = "Please enter your password!";


        if(userEmail.equals("")){
            emailError.setText(error_email);
            return;
        }
        if(!(userEmail).contains("@")){
            emailError.setText(error_emailFormat);
            loginEmail.setText("");
            return;
        }
        if(userPassword.equals("")){
            passwordError.setText(error_password);
            return;
        }
        else {

            openMain = new Intent(this, Login.class);
            progressDialog = ProgressDialog.show(MainActivity.this, "Please wait...", "Processing...", true);

            (firebaseAuth.signInWithEmailAndPassword(userEmail, userPassword))
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

}
