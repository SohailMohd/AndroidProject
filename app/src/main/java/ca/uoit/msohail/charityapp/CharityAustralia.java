package ca.uoit.msohail.charityapp;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.SoundEffectConstants;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.ArrayList;

/**
 * Created by 100485582 on 11/26/2016.
 */

public class CharityAustralia extends Activity{

    final String filename = "https://everydayhero.com/api/v2/charities.json?campaign_ids=au-3707,au-1493";

    CharityAustralia_ArrayAdapter charityAdapter;
    ListView listView;
    List<CharityAustraliaInfo> storeCharity;


    String json_string;
    JSONObject jsonObjectData;
    JSONObject jsonObject;
    JSONArray jsonArray;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.australia_charities);

        storeCharity = new ArrayList<>();
    }

    @Override
    protected void onStart() {
        super.onStart();

        findCharities charities = new findCharities();
        charities.execute(filename);


        LocalBroadcastManager.getInstance(this).registerReceiver(receiveBroadcast, new IntentFilter("TaskResult"));


//        displayCharityList(storeCharity);
    }

    private void displayCharityList(List<CharityAustraliaInfo> storeCharity) {
        ListView viewCharityList = (ListView)findViewById(R.id.lstCharities);
        viewCharityList.setAdapter(new CharityAustralia_ArrayAdapter(this, storeCharity));
    }

    @Override
    protected void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiveBroadcast);
    }

    private BroadcastReceiver receiveBroadcast = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String info = intent.getStringExtra("charity list");


        }
    };

    public void returnToOptions(View view) {
        view.playSoundEffect(SoundEffectConstants.CLICK);
        finish();
    }

    class findCharities extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... charitiesURL) {
            HttpURLConnection httpURLConnection = null;
            String line = "";
            try {
                URL url = new URL(charitiesURL[0]);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.connect();

                InputStream stream = httpURLConnection.getInputStream();
                InputStreamReader inputReader = new InputStreamReader(stream);

                BufferedReader bufferReader = new BufferedReader(inputReader);
                StringBuffer strBuffer = new StringBuffer();

                while((line = bufferReader.readLine()) != null){
                    strBuffer.append(line);
                }

                //need to parse JSON
                return strBuffer.toString();

            }  catch (IOException e) {
                e.printStackTrace();
            }

            httpURLConnection.disconnect();

            return null;

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            json_string = result;

            if(json_string == null){
                Toast.makeText(CharityAustralia.this, "No data retrieved", Toast.LENGTH_LONG).show();
            }
            else {
                try {
                    jsonObjectData = new JSONObject(json_string);
                    jsonArray = jsonObjectData.getJSONArray("charities");
                    //jsonObject = jsonObjectData.getJSONObject("charity");
                    int i = 0;
                    String[] addresses =  {""};
                    String name, descrip, address, phone, website;

                    /*name = "hello: "+jsonObject.getJSONObject("title");
                    descrip = "WOrld: "+jsonObject.getJSONObject("activities");
                    website = "site "+jsonObject.getJSONObject("website");
                    Log.i("Charity name ", name);
                    Toast.makeText(CharityAustralia.this, "Name: " + name, Toast.LENGTH_LONG).show();
                    CharityAustraliaInfo charityInfo = new CharityAustraliaInfo(name, descrip, website);
                    storeCharity.add(charityInfo); */

                    while(i < jsonArray.length()){
                        JSONObject obj = jsonArray.getJSONObject(i);
                        name = obj.getString("name");
                        descrip = obj.getString("description");
                        website = obj.getString("url");

                        CharityAustraliaInfo charityInfo = new CharityAustraliaInfo(name, descrip, website);
                        storeCharity.add(charityInfo);
                        i++;
                    }/**/


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            ListView viewCharityList = (ListView)findViewById(R.id.lstCharities);
            viewCharityList.setAdapter(new CharityAustralia_ArrayAdapter(CharityAustralia.this, storeCharity));

            Intent sendIntent = new Intent("TaskResult");
            sendIntent.putExtra("charity list", result);

            LocalBroadcastManager.getInstance(CharityAustralia.this).sendBroadcast(sendIntent);
        }
    }
}
