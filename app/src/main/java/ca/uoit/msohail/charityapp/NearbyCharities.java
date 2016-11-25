package ca.uoit.msohail.charityapp;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by 100485582 on 11/19/2016.
 */

public class NearbyCharities extends Activity {

    final String filename = "https://everydayhero.com/api/v2/charities.json?campaign_ids=au-3707,au-1493";

    NearbyCharity_ArrayAdapter charityAdapter;
    ListView listView;
    List<NearbyCharityInfo> storeCharity;


    String json_string;
    JSONObject jsonObject;
    JSONArray jsonArray;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nearby_charities);

        storeCharity = new ArrayList<>();
    }

    @Override
    protected void onStart() {
        super.onStart();

        findNearbyCharities nearbyCharities = new findNearbyCharities();
        nearbyCharities.execute(filename);


        LocalBroadcastManager.getInstance(this).registerReceiver(receiveBroadcast, new IntentFilter("TaskResult"));


//        displayCharityList(storeCharity);
    }

    private void displayCharityList(List<NearbyCharityInfo> storeCharity) {
        ListView viewCharityList = (ListView)findViewById(R.id.lstCharities);
        viewCharityList.setAdapter(new NearbyCharity_ArrayAdapter(this, storeCharity));
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
        finish();
    }

    class findNearbyCharities extends AsyncTask<String, String, String> {

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
                Toast.makeText(NearbyCharities.this, "JSON string not found", Toast.LENGTH_LONG).show();
            }
            else {
                try {
                    jsonObject = new JSONObject(json_string);
                    jsonArray = jsonObject.getJSONArray("charities");
                    int i = 0;
                    String[] addresses =  {""};
                    String name, descrip, address, phone, website;
                    while(i < jsonArray.length()){
                        JSONObject obj = jsonArray.getJSONObject(i);
                        name = obj.getString("name");
                        descrip = obj.getString("description");
                        //address = obj.getString("street_address");
                        //phone = obj.getString("telephone");
                        website = obj.getString("url");

                        NearbyCharityInfo charityInfo = new NearbyCharityInfo(name, descrip, website);
                        storeCharity.add(charityInfo);
                        i++;
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            ListView viewCharityList = (ListView)findViewById(R.id.lstCharities);
            viewCharityList.setAdapter(new NearbyCharity_ArrayAdapter(NearbyCharities.this, storeCharity));

            Intent sendIntent = new Intent("TaskResult");
            sendIntent.putExtra("charity list", result);

            LocalBroadcastManager.getInstance(NearbyCharities.this).sendBroadcast(sendIntent);
        }
    }


}

