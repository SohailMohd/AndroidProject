package ca.uoit.msohail.charityapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

/**
 * Created by 100485582 on 10/10/2016.
 */

public class Register  extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
    }


    public void storeUserInfo(View view){

        finish();

    }


}
