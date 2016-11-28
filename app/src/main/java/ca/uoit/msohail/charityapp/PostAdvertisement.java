package ca.uoit.msohail.charityapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.SoundEffectConstants;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

/**
 * Created by 100485582 on 11/22/2016.
 */

//this class retrieves values from the textfields from post advertisement activity
//and sends the values via intent to view post class

public class PostAdvertisement extends Activity {

    EditText title;
    EditText message;
    Intent viewPost;
    Intent retriveRadio;
    RadioButton foodBtn;
    RadioButton travelBtn;
    RadioButton eduBtn;
    String postCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_advertisements);

        title = (EditText)findViewById(R.id.titlePost);
        message = (EditText)findViewById(R.id.messagePost);
        foodBtn = (RadioButton)findViewById(R.id.radioFood);
        travelBtn = (RadioButton)findViewById(R.id.radioTravel);
        eduBtn = (RadioButton)findViewById(R.id.radioEducation);

    }


    public void postAdToView(View view) {
        String postTitle = title.getText().toString();
        String postMessage = message.getText().toString();
        view.playSoundEffect(SoundEffectConstants.CLICK);
        Toast.makeText(this, "Title "+ postTitle, Toast.LENGTH_LONG).show();
        viewPost = new Intent(this, ViewAdvertisements.class);
        viewPost.putExtra("PostTitle", postTitle);
        viewPost.putExtra("PostCategory", postCategory);
        viewPost.putExtra("PostMessage", postMessage);
        startActivity(viewPost);

        finish();
    }


    public void selectFood(View view) {
        postCategory = foodBtn.getText().toString();

    }

    public void selectTravel(View view) {
        postCategory = travelBtn.getText().toString();
    }

    public void selectEdu(View view) {
        postCategory = eduBtn.getText().toString();
    }
}
