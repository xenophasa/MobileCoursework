//
//Name  John Fletcher
//Student Id    S1430395
//Programme of Study    Computing
//

// Update the package name to include your Student Identifier
package gcu.mpd.bgsdatastarter;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.ListResourceBundle;

import gcu.mpd.bgsdatastarter.R;

public class MainActivity extends AppCompatActivity implements OnClickListener {
    private Handler mHandler = new Handler();
    public static LinkedList<Earthquake> alist = new LinkedList<>();
    private ListView rawDataDisplay;
    private TextView listTextView;
    private Thread repeatTask;
    private Button startButton;
    private CustomAdaptor adapter;
    private Button searchButton;
    private Button mapButton;
    //private EditText searchable;
    private String result ="";
    private String url1 = "";
    private String urlSource = "http://quakes.bgs.ac.uk/feeds/MhSeismology.xml";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*setContentView(R.layout.dialog_search);
        searchable = (EditText) findViewById(R.id.searchText);*/
        setContentView(R.layout.activity_main);
        // Set up the raw links to the graphical components
        rawDataDisplay = (ListView) findViewById(R.id.rawDataDisplay);
        listTextView = (TextView) findViewById(R.id.listTextView);
        startButton = (Button) findViewById(R.id.startButton);
        startButton.setOnClickListener(this);
        searchButton = (Button) findViewById(R.id.searchButton);
        mapButton = (Button) findViewById(R.id.mapButton);
        //searchable = (EditText) findViewById(R.id.searchText);


        // More Code goes here
    }

    public void startRepeating(View v){

    }

    public void onClick(View aview) {
        switch (aview.getId())
        {
            case R.id.startButton:
                //mHandler = new Handler();
                //startProgress();
                mHandler = new Handler();
                //new aTask().execute(urlSource);
                Toast.makeText(this,"Please wait while the data is loaded.",Toast.LENGTH_SHORT).show();
                Runnable refresh = new Runnable() {
                    @Override
                    public void run() {
                        new aTask().execute(urlSource);
                        mHandler.postDelayed(this,100000);
                    }
                };
                mHandler.postDelayed(refresh,1000);
                ViewGroup parentView = (ViewGroup) aview.getParent();
                startButton.setVisibility(aview.GONE);
                searchButton.setOnClickListener(this);
                searchButton.setVisibility(aview.VISIBLE);
                mapButton.setOnClickListener(this);
                mapButton.setVisibility(aview.VISIBLE);
                break;
            case R.id.searchButton:
                final Dialog dialog = new Dialog(this);
                dialog.setContentView(R.layout.dialog_search);

                final EditText searchable = (EditText) dialog.findViewById(R.id.searchText);
                Button searchButton = (Button) dialog.findViewById(R.id.searcbutton);
                Button resetData = (Button) dialog.findViewById(R.id.refreshButton);
                final Spinner spinner =  dialog.findViewById(R.id.searchspinner);
                ArrayAdapter<CharSequence> spinnervalues = ArrayAdapter.createFromResource(this,R.array.searchoptions,android.R.layout.simple_spinner_item);
                spinnervalues.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(spinnervalues);
                searchButton.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.filter(searchable.getText().toString(), spinner.getSelectedItem().toString());
                        dialog.dismiss();
                    }
                });
                resetData.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.filter("","");
                        dialog.dismiss();
                    }
                });
                dialog.show();

                        break;
            case R.id.mapButton:
                Intent intent = new Intent(this,MapsActivity2.class);
                Earthquake e = null;
                for(int i =0;i<alist.size();i++){
                    e = alist.get(i);
                    String[] seperated = e.getDescription().split(";");


                    String temp = seperated[4].substring(13);
                    double magnitude = Double.parseDouble(temp);
                    String title = e.getSimpleString();
                    LatLng value = e.getLatLong();
                    intent.putExtra(i + "Title",title);
                    intent.putExtra(i + "Value",value);
                    intent.putExtra(i+"Magnitude",magnitude);
                }
                startActivity(intent);
                break;
        }
    }



    private class aTask extends AsyncTask<String,Void,LinkedList<Earthquake>>{

        private String url;


        @Override
        protected LinkedList<Earthquake> doInBackground(String... strings) {

            url = strings[0];
            result = "";
                URL aurl;
                URLConnection yc;
                BufferedReader in = null;
                String inputLine = "";


                Log.e("MyTag", "in run");

                try {
                    Log.e("MyTag", "in try");
                    aurl = new URL(url);
                    yc = aurl.openConnection();
                    in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
                    //
                    // Throw away the first 2 header lines before parsing
                    //
                    //
                    //
                    while ((inputLine = in.readLine()) != null) {
                        result = result + inputLine;
                        Log.e("MyTag", inputLine);

                    }
                    in.close();
                } catch (IOException ae) {
                    Log.e("MyTag", ae.toString());
                }
                        try {
                            alist.clear();
                            alist = (LinkedList) parseData();
                        } catch (IOException ae) {
                            Log.e("Mytag", ae.toString());

                        } catch (XmlPullParserException xml) {
                            Log.e("Mytag", xml.toString());
                        }
                        return alist;
                    }

                    @Override
                    protected void onPostExecute(LinkedList<Earthquake> alist) {
                        adapter = new CustomAdaptor(getApplicationContext(),R.id.textView,alist);
                        rawDataDisplay.setAdapter(adapter);
                    }

                    public LinkedList parseData() throws XmlPullParserException, IOException {
                        String temp;
                        Earthquake earthquake = null;
                        LinkedList<Earthquake> eqList = null;
                        //eqList.clear();
                        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                        factory.setNamespaceAware(true);
                        XmlPullParser xpp = factory.newPullParser();

                        xpp.setInput(new StringReader(result));

                        int eventtype = xpp.getEventType();
                        while (eventtype != XmlPullParser.END_DOCUMENT) {
                            if(eventtype == XmlPullParser.START_TAG){
                                Log.e("Mytag","I have found a start tag");
                                if(xpp.getName().equalsIgnoreCase("channel")){
                                    Log.e("MYtag","I have instansiated the linked list");
                                    eqList = new LinkedList<Earthquake>();
                                }
                                else if (xpp.getName().equalsIgnoreCase("item")) {
                                    Log.e("MyTag","i have created an earthquake object");
                                    earthquake = new Earthquake();

                                }
                                else if(xpp.getName().equalsIgnoreCase("title")){
                                    temp = xpp.nextText();
                                    Log.e("Mytag","Title: " + temp);
                                    if (earthquake != null) {
                                        earthquake.setTitle(temp);
                                    }
                                }
                                else if(xpp.getName().equalsIgnoreCase("description")) {
                                    temp = xpp.nextText();
                                    Log.e("Mytag", "Description: " + temp);
                                    if (earthquake != null) {
                                        earthquake.setDescription(temp);
                                    }
                                }
                                else if(xpp.getName().equalsIgnoreCase("pubDate")){
                                    temp = xpp.nextText();
                                    Log.e("Mytag","Date: " + temp);
                                    if (earthquake != null) {
                                        earthquake.setDate(temp);
                                    }
                                }
                                else if(xpp.getName().equalsIgnoreCase("category")){
                                    temp = xpp.nextText();
                                    Log.e("Mytag","Category: " + temp);
                                    if (earthquake != null) {
                                        earthquake.setCategory(temp);
                                    }
                                }
                                else if(xpp.getName().equalsIgnoreCase("lat")){
                                    temp = xpp.nextText();
                                    Log.e("Mytag","Lat: " + temp);
                                    if (earthquake != null) {
                                        earthquake.setGeolat(temp);
                                    }
                                }
                                else if(xpp.getName().equalsIgnoreCase("long")){
                                    temp = xpp.nextText();
                                    Log.e("Mytag","Long: " + temp);
                                    if (earthquake != null) {
                                        earthquake.setGeolong(temp);
                                    }
                                }
                            }
                            else if(eventtype == XmlPullParser.END_TAG){
                                if (xpp.getName().equalsIgnoreCase("item")){
                                    earthquake.setLatLong();
                                    eqList.add(earthquake);
                                    Log.e("Mytag","Earthquake is : " + earthquake.toString());
                                }
                            }
                            eventtype = xpp.next();
                        }
                        return eqList;
                    }
                }
        }
