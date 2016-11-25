package ca.uoit.msohail.charityapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

/**
 * Created by 100485582 on 11/22/2016.
 */

//this class retrieves values from the textfields from post advertisement activity
//and sends the values via intent to view post class

public class PostAdvertisement extends Activity {

    EditText title;
    EditText message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_advertisements);

        title = (EditText)findViewById(R.id.titlePost);
        message = (EditText)findViewById(R.id.messagePost);
    }


    public void postAdToView(View view) {
        String postTitle = title.getText().toString();
        String postMessage = message.getText().toString();

        Intent viewPost = new Intent(this, ViewAdvertisements.class);
        viewPost.putExtra("Post Title", postTitle);
        viewPost.putExtra("Post Message", postMessage);
        startActivity(viewPost);

        finish();
    }
}
