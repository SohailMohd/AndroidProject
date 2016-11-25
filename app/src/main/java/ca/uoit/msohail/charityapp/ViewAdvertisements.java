package ca.uoit.msohail.charityapp;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
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
    //1. retrives and displays user's post
    //3..allows for searching ads by category - this uses a spinner menu style layout
    //items in menu will be retrieved from sql database. User's searches will be saved
    //in sql database, then thse will be displayed  in spinner

public class ViewAdvertisements extends Activity {

    GridView gridViewAds;
    EditText searchField;
    List<String> userSearches;
    final String filename = "SearchTerms.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_advertisements);

        String [] advertisements = getResources().getStringArray(R.array.advertisements);
        //need to get extra from the post intent and place it into this advertisiments array
        gridViewAds = (GridView)findViewById(R.id.adsGridView);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, advertisements);

        gridViewAds.setAdapter(adapter);

        gridViewAds.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), ((TextView) view).getText(), Toast.LENGTH_LONG).show();
            }
        });

        userSearches = new ArrayList<>();
    }

    @Override
    protected void onStart() {
        super.onStart();



        searchField = (EditText)findViewById(R.id.edt_searchItems);

        userSearches = readFromFile(filename);
        //while user is typing, compare user input with the the search terms in array list
        //if match then place suggestions
    }


    public void searchAds(View view) {
        String searchItem = searchField.getText().toString();

        //userSearches.add(searchItem);
        /*for (int i=0; i < userSearches.size(); i++){
            Log.i("Items in array", userSearches.get(i));
        }*/

        writeToFile(filename, searchItem);
    }

    private ArrayList<String> readFromFile(String filename){

        try {
            FileInputStream fileInputStream = openFileInput(filename);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuffer stringBuffer = new StringBuffer();
            ArrayList<String> readResults = new ArrayList<>();
            String line;
            while((line = bufferedReader.readLine()) != null){
                //stringBuffer.append(line + " ");
                readResults.add(line);
            }
            //Log.i("Text from file" , stringBuffer.toString());
            return readResults;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

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
        finish();
    }
}
