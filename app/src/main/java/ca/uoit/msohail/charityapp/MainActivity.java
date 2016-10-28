package ca.uoit.msohail.charityapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void openRegisterPage(View view){
        Intent regPage = new Intent(this, Register.class);
        startActivity(regPage);
    }

    public void loginVerify(View view){
        EditText userLabel = (EditText)findViewById(R.id.lbl_userLogin);
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



    }



}
