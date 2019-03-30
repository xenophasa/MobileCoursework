
/*  Starter project for Mobile Platform Development in Semester B Session 2018/2019
    You should use this project as the starting point for your assignment.
    This project simply reads the data from the required URL and displays the
    raw data in a TextField
*/

//
// Name                 _________________
// Student ID           _________________
// Programme of Study   _________________
//

// Update the package name to include your Student Identifier
package gcu.mpd.bgsdatastarter;

import android.app.Dialog;
import android.content.DialogInterface;
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
    public static LinkedList<Earthquake> alist = new LinkedList<>();
    private ListView rawDataDisplay;
    private TextView listTextView;
    private Button startButton;
    private CustomAdaptor adapter;
    private Button searchButton;
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
        //searchable = (EditText) findViewById(R.id.searchText);


        // More Code goes here
    }

    public void onClick(View aview) {
        switch (aview.getId())
        {
            case R.id.startButton:
                startProgress();
                ViewGroup parentView = (ViewGroup) aview.getParent();
                startButton.setVisibility(aview.GONE);
                searchButton.setOnClickListener(this);
                searchButton.setVisibility(aview.VISIBLE);
                break;
            case R.id.searchButton:
                final Dialog dialog = new Dialog(this);
                dialog.setContentView(R.layout.dialog_search);

                final EditText searchable = (EditText) dialog.findViewById(R.id.searchText);
                Button searchButton = (Button) dialog.findViewById(R.id.searcbutton);
                final Spinner spinner =  dialog.findViewById(R.id.searchspinner);
                ArrayAdapter<CharSequence> spinnervalues = ArrayAdapter.createFromResource(this,R.array.searchoptions,android.R.layout.simple_spinner_item);
                spinnervalues.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(spinnervalues);
                searchButton.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        System.out.println("This is searchable text: " + searchable.getText());
                        System.out.println("This is the spinner value: " + spinner.getSelectedItem());
                        adapter.filter(searchable.getText().toString(), spinner.getSelectedItem().toString());
                        dialog.dismiss();
                    }
                });
                dialog.show();

                        break;
        }
    }

    public void startProgress() {
        // Run network access on a separate thread;
        new Thread(new Task(urlSource)).start();
    } //

    // Need separate thread to access the internet resource over network
    // Other neater solutions should be adopted in later iterations.
    private class Task implements Runnable {
        private String url;

        public Task(String aurl) {
            url = aurl;
        }

        @Override
        public void run() {

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

            //
            // Now that you have the xml data you can parse it
            //

            // Now update the TextView to display raw XML data
            // Probably not the best way to update TextView
            // but we are just getting started !

            MainActivity.this.runOnUiThread(new Runnable() {
                public void run() {
                    //LinkedList<Earthquake> alist = new LinkedList<>();
                    try {
                        System.out.println("Im in the try");
                        alist = (LinkedList) parseData().clone();
                    } catch (IOException ae) {
                        Log.e("Mytag", ae.toString());

                    } catch (XmlPullParserException xml) {
                        Log.e("Mytag", xml.toString());
                    }
                    System.out.println("I have completed the parse data");
                    System.out.println("Create the iteratpr");
                    //System.out.println("I am in the while loop");
                    //System.out.println(e.next().toString());
                    adapter = new CustomAdaptor(getApplicationContext(),R.id.textView,alist);
                    rawDataDisplay.setAdapter(adapter);


                }

                public LinkedList parseData() throws XmlPullParserException, IOException {
                    String temp;
                    System.out.println("In the parse method ");
                    Earthquake earthquake = null;
                    LinkedList<Earthquake> eqList = null;
                    XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                    factory.setNamespaceAware(true);
                    XmlPullParser xpp = factory.newPullParser();

                    if (result !=null) {
                        System.out.println("This is the result: " + result);
                    }
                    else{
                        System.out.println("Result is null");
                    }
                    xpp.setInput(new StringReader(result));

                    int eventtype = xpp.getEventType();
                    System.out.println("Before the while");
                    while (eventtype != XmlPullParser.END_DOCUMENT) {
                        //System.out.println("in the while");
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
                                System.out.println("This is the inttemp for lat: " + temp);
                                earthquake.setGeolat(temp);
                            }
                        }
                        else if(xpp.getName().equalsIgnoreCase("long")){
                            temp = xpp.nextText();
                            Log.e("Mytag","Long: " + temp);
                            if (earthquake != null) {
                                System.out.println("This is the inttemp for long: " + temp);
                                earthquake.setGeolong(temp);
                            }
                        }
                        else{
                            System.out.println("I did not find an item");
                            //eventtype = xpp.next();
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
            })
            ;
        }


    }
}
