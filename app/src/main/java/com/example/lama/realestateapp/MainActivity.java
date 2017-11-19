package com.example.lama.realestateapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.provider.SyncStateContract;
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
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpRequest;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity implements OnItemSelectedListener{
Button b;
Button b2;
Spinner stateSpinner;
EditText e1;
EditText e2;
TableLayout tableLayout;
TableRow tableRow;
SharedPreferences sharedpreferences;
    TextView streettable;
    TextView citytable;
    TextView statetable;

public static final String MyPREFERENCES = "MyPrefs" ;
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
        //////////////////////
        //swipe to delete table

       /* tableRow=(TableRow)findViewById(R.id.tableRow2);

        tableRow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                tableRow.setOnTouchListener(new SwipeDismissTouchListener(
                        tableRow,
                        null,
                        new SwipeDismissTouchListener.DismissCallbacks() {
                            @Override
                            public boolean canDismiss(Object token) {
                                return true;
                            }

                            @Override
                            public void onDismiss(View view, Object token) {
                                new AlertDialog.Builder(MainActivity.this)
                                        .setTitle("Delete")
                                        .setMessage("Do you really want to delete this property")
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                            public void onClick(DialogInterface dialog, int whichButton) {
                                                Toast.makeText(MainActivity.this, "Yaay", Toast.LENGTH_SHORT).show();
                                                tableLayout.removeView(tableRow);
                                            }
                                        })
                                        .setNegativeButton(android.R.string.no, null).show();


                            }
                        }));
            }
        });*/
        //////////////////////
        //retrieve data from shared preferences




        //SHARE PREFERENCES

       sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
       Map<String,?> keys = sharedpreferences.getAll();
        JSONArray arr = new JSONArray();
        for(Map.Entry<String,?> entry : keys.entrySet()){
            JSONObject obj = new JSONObject();
            try {
                obj.put(entry.getKey(), entry.getValue().toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            arr.put(obj);
        }

            tableLayout=(TableLayout)findViewById(R.id.tableLayout);



        String street = null;
        String city = null;
        String state = null;
        TextView street2;
        TextView city2;
        TextView state2;
int j=0;
            for (int i = 0; i <arr.length();i+=3) {

                TableRow row= new TableRow(this);
                TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                row.setLayoutParams(lp);
                street2 = new TextView(this);
                city2 = new TextView(this);
                state2 = new TextView(this);

                try {
                    state=arr.getJSONObject(i).getString("state"+j);
                    city=arr.getJSONObject(i+1).getString("city"+j);
                    street=arr.getJSONObject(i+2).getString("street"+j);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                street2.setText(street);
                city2.setText(city);
                 state2.setText(state);
                row.addView(street2);
                row.addView(city2);
                row.addView(state2);
                j++;

                tableLayout.addView(row);
           }

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
        ProgressDialog loading;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading = ProgressDialog.show(getApplicationContext(), "Please Wait",null, true, true);
        }
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
            if (s!=null) {
                b.putStringArray("info", ParseXML(s));
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

