package ca.uoit.msohail.charityapp;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.SoundEffectConstants;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 100485582 on 11/22/2016.
 */

//connects with gridview layout
    // retrives and displays user's post
    //

public class ViewAdvertisements extends Activity {

    GridView gridViewAds;
    EditText searchField;
    TextView searchSuggestion;
    List<String> userSearches;
    final String filename = "SearchTerms.txt";
    private String[] advertisements;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_advertisements);

        advertisements = getResources().getStringArray(R.array.advertisements);
        //need to get extra from the post intent and place it into this advertisements array

        advertisements[0] = showMyPosts();

        userSearches = new ArrayList<>();

        createUpdateGridView(advertisements);

    }

    private String showMyPosts() {
        String myTitle ="", myMessage = "", myCategory = "", myPost = "";

        if(getIntent().getStringExtra("PostTitle") == null ||
                getIntent().getStringExtra("PostMessage") == null ||
                getIntent().getStringExtra("PostCategory") == null){
            String replaceTitle = "No post by you. Click to add post";
            myPost = "My Post: " + replaceTitle;
        }
        else {
            myTitle = getIntent().getStringExtra("PostTitle") + ": ";
            myCategory = getIntent().getStringExtra("PostCategory");
            myMessage = getIntent().getStringExtra("PostMessage");

            myPost = myCategory + " - " + myTitle + myMessage +"\n\n";
        }
        return myPost;
    }

    private void createUpdateGridView(String[] advertisements) {

        gridViewAds = (GridView)findViewById(R.id.adsGridView);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, advertisements);

        gridViewAds.setAdapter(adapter);

        gridViewAds.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), ((TextView) view).getText(), Toast.LENGTH_LONG).show();
                String itemRef = ((TextView) view).getText().toString();
                if(itemRef.contains("My Post:")){
                    Intent openPostSection = new Intent(ViewAdvertisements.this, PostAdvertisement.class);
                    startActivity(openPostSection);
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        searchField = (EditText)findViewById(R.id.edt_searchItems);
        searchSuggestion = (TextView)findViewById(R.id.search_suggestions);
        userSearches = readFromFile(filename);

        //while user is typing, compare user input with the the search terms in array list
        //if match then place suggestions
        searchField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                advertisements = getResources().getStringArray(R.array.advertisements);
                advertisements[0] = showMyPosts();
                createUpdateGridView(advertisements);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String word;
                StringBuffer concat = new StringBuffer();
                word = concat.append(s).toString();
                Log.i("Type Word", word);
                if(word.contains(" ")){

                    for (int k=0; k < userSearches.size(); k++){
                        if(userSearches.get(k).contains(word)){
                            searchSuggestion.setText(userSearches.get(k));
                            break;
                        }
                    }

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }


    public void searchAds(View view) {
        view.playSoundEffect(SoundEffectConstants.CLICK);

        String searchItem = searchField.getText().toString();
        searchSuggestion.setText("");
        searchField.setText("");

        //if category search item is in the posts
        for(int i = 0; i < advertisements.length; i++){
            if(advertisements[i].contains(searchItem)){

            } else{
                advertisements[i] = "";
            }
        }

        createUpdateGridView(advertisements);

        writeToFile(filename, searchItem);
    }

    //reading from text file, which contains saved searches
    private ArrayList<String> readFromFile(String filename){

        try {
            FileInputStream fileInputStream = openFileInput(filename);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuffer stringBuffer = new StringBuffer();
            ArrayList<String> readResults = new ArrayList<>();
            String line;
            while((line = bufferedReader.readLine()) != null){
                stringBuffer.append(line + " ");
                readResults.add(line);
            }
            Log.i("Text from file" , stringBuffer.toString());
            return readResults;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //Writes the user searches and saves to file for later retrieval
    private void writeToFile(String filename, String searchItem) {

        try {
            FileOutputStream outputStream = openFileOutput(filename, MODE_APPEND);
            outputStream.write((searchItem + "\n").getBytes());

            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void returnToOptions(View view) {
        view.playSoundEffect(SoundEffectConstants.CLICK);
        finish();
    }
}
