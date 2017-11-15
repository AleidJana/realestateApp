package com.example.lama.realestateapp;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements OnItemSelectedListener{
Button b;
Button b2;
Spinner stateSpinner;
EditText e1;
EditText e2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar menu=getSupportActionBar();
        menu.setDisplayShowHomeEnabled(true);
        menu.setLogo(R.drawable.ic_action_name);
        menu.setDisplayUseLogoEnabled(true);
        /////////////////////
        e1=(EditText)findViewById(R.id.StreetText);
        String simple = "Enter Street ";
        String colored = "*";
        SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.append(simple);
        int start = builder.length();
        builder.append(colored);
        int end = builder.length();
        builder.setSpan(new ForegroundColorSpan(Color.RED), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        e1.setHint(builder);
        /////////////////////
        e2=(EditText)findViewById(R.id.CityText);
        String simple2 = "Enter City ";
        String colored2 = "*";
        SpannableStringBuilder builder2 = new SpannableStringBuilder();
        builder2.append(simple2);
        int start2 = builder2.length();
        builder2.append(colored2);
        int end2 = builder2.length();
        builder2.setSpan(new ForegroundColorSpan(Color.RED), start2, end2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        e2.setHint(builder2);
        //////////////////////
        //spinner
        stateSpinner = (Spinner)findViewById(R.id.stateSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.states, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stateSpinner.setAdapter(adapter);
        b2= (Button)findViewById(R.id.btn2);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                e1.setText("");
                e2.setText("");
            }
        });
        b= (Button)findViewById(R.id.btn1);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(e1.getText().length()==0) {
                    e1.setError("Street field is required!");
                }else if(e2.getText().length()==0)
                {
                    e2.setError("City field is required!");
                }else {
                    String address = e1.getText().toString();
                    String citytext = e2.getText().toString();
                    String state = stateSpinner.getSelectedItem().toString();
                    new HttpRequst().execute(address,citytext,state);


                }



            }
        });


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        stateSpinner.getItemAtPosition(position).toString();
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    public String[] ParseXML(String xmlString) {
        String text=null;
        String []res=new String[6];
        try {
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(true);
                XmlPullParser parser = factory.newPullParser();
                parser.setInput(new StringReader(xmlString));
                int eventType = parser.getEventType();
                while (eventType != XmlPullParser.END_DOCUMENT) {
                    String tagname = parser.getName();
                    switch (eventType) {
                        case XmlPullParser.START_TAG:
                          if (tagname.equalsIgnoreCase("address")) {

                           }
                            break;

                        case XmlPullParser.TEXT:

                             text = parser.getText();
                            break;

                        case XmlPullParser.END_TAG:

                           if (tagname.equalsIgnoreCase("code")) {
                               if(Integer.parseInt(text)!=0) {
                                   return res = null;
                               }

                            }  else if (tagname.equalsIgnoreCase("street")) {
                               res[0]=text;
                           } else if (tagname.equalsIgnoreCase("zipcode")) {
                               res[1]=text;
                            } else if (tagname.equalsIgnoreCase("city")) {
                               res[2]=text;
                            } else if (tagname.equalsIgnoreCase("state")) {
                               res[3]=text;
                            } else if (tagname.equalsIgnoreCase("latitude")) {
                               res[4]=text;
                            } else if (tagname.equalsIgnoreCase("longitude")) {
                               res[5]=text;
                           }
                            break;
                        default:
                            break;
                    }
                    eventType = parser.next();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return res;

    }

    class HttpRequst extends AsyncTask<String,Void,String>{
        @Override
        protected String doInBackground(String... urls) {

            try {
                URL url = new URL("http://www.zillow.com/webservice/GetSearchResults.htm?zws-id=X1-ZWz18wlnzn0miz_7oq0o&address="+urls[0]+"+Bigelow+Ave&citystatezip="+urls[1]+"%2C+"+urls[2]);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
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
            }
            return null;
        }


        @Override
        protected void onPostExecute(String s) {


            Bundle b=new Bundle();
            String[] result =ParseXML(s);
            if (result!=null) {
                b.putStringArray("info", result);
                Intent i = new Intent(MainActivity.this, SecoundActivity.class);
                i.putExtras(b);
                startActivity(i);
            }
            else {
                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                alertDialog.setTitle("Error");
                alertDialog.setMessage("please enter the address correctly");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }
        }

    }

}

