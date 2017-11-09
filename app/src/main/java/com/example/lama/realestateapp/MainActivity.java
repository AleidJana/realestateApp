package com.example.lama.realestateapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;


import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;

import java.io.BufferedReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements OnItemSelectedListener{
Button b;
Spinner stateSpinner;
String state = "Choose State";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar menu=getSupportActionBar();
        menu.setDisplayShowHomeEnabled(true);
        menu.setLogo(R.drawable.ic_action_name);
        menu.setDisplayUseLogoEnabled(true);
        //spinner
        stateSpinner = (Spinner) findViewById(R.id.stateSpinner);
        stateSpinner.setOnItemClickListener((AdapterView.OnItemClickListener) this);
//http://yuhaoaws-env.elasticbeanstalk.com

        b= (Button)findViewById(R.id.btn1);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String address = ((EditText)findViewById(R.id.StreetText)).getText().toString();
                String citytext = ((EditText)findViewById(R.id.CityText)).getText().toString();
                //  Spinner boxState=(Spinner) findViewById(R.id.stateSpinner);
                //  String state = boxState.getSelectedItem().toString();


                //validate inputs
                boolean flag = false;
                if(address.equals("") || citytext.equals("")){
                    flag = true;}

                if(flag)
                    return;//change to popup message

                final  ArrayList<NameValuePair> params=new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("streetAddress", address));
                params.add(new BasicNameValuePair("city", citytext));
                params.add(new BasicNameValuePair("state", state));
                String paramString = URLEncodedUtils.format(params, "utf-8");
		        //String url = "http://yuhaoaws-env.elasticbeanstalk.com/?"+paramString;
		        new HttpRequst().execute(paramString);

                Intent i = new Intent(MainActivity.this, SecoundActivity.class);
               // i.putExtra("key", array1);
                startActivity(i);
            }// end on click
        });


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        state = stateSpinner.getItemAtPosition(position).toString();
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

}

class HttpRequst extends AsyncTask<String,Void,String>{

    @Override
    protected String doInBackground(String... urls) {

        String url2=urls[0];
        String response = null;
    /*    try {
            URL url = new URL("http://www.zillow.com/webservice/GetSearchResults.htm?zws-id=<X1-ZWz18wlnzn0miz_7oq0o>&address=2114+Bigelow+Ave&citystatezip=Seattle%2C+WA
");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
           BufferedReader br = new BufferedReader(new inputStreamReader(con.getInputStream()));

            StringBuffer sb = new StringBuffer();
            String line;
            while((line=br.readLine())!=null){
                sb.append(line);
                break;
            }
            br.close();
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();

        }*/
        return null;
    }

    @Override
    protected void onPostExecute(String s) {

        if(s!=null){

          //      JSONArray jsonArray = new JSONArray(s);
          //    String
          //  jsonArray = new JSONArray(s);


        }//null condition

        super.onPostExecute(s);
    }
}