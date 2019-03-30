package gcu.mpd.bgsdatastarter;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import javax.security.auth.Subject;

import static android.util.Half.EPSILON;

public class CustomAdaptor extends ArrayAdapter<Earthquake> {

    private LinkedList<Earthquake> eqs = new LinkedList<Earthquake>();

    public static class ViewHolder {
        private TextView textView;
    }

    public CustomAdaptor(Context context, int textViewResourceId, LinkedList<Earthquake> items) {
        super(context, textViewResourceId, items);
        this.eqs.addAll(items);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(this.getContext())
                    .inflate(R.layout.activity_listview, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.textView = (TextView) convertView.findViewById(R.id.textView);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        System.out.println("This is the count of earthquakes: ");
        final Earthquake item = getItem(position);
        if (item != null) {
            double magnitudeint = getMagnitude(item);
            if (magnitudeint < 1) {
                viewHolder.textView.setBackgroundColor(Color.GREEN);
            } else {
                viewHolder.textView.setBackgroundColor(Color.RED);
            }
            viewHolder.textView.setText(String.format(item.getSimpleString()));
        }
        convertView.setTag(viewHolder);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item.getExpanded()) {
                    viewHolder.textView.setText(String.format(item.getSimpleString()));
                    item.setExpanded();
                } else {
                    viewHolder.textView.setText(String.format(item.toString()));
                    item.setExpanded();
                }
            }
        });
        return convertView;

    }

    public void filter(String search, String searchCategory) {
        LinkedList<Earthquake> searchedEarthquakes = new LinkedList<>();
        System.out.println("I have made it to the filter method");

        //int count = getCount();
        MainActivity.alist.clear();
        if (search.length() == 0) {
            System.out.println("The string was empty");
            System.out.println("This is what is in eq: " + eqs.toString());
            MainActivity.alist.addAll(eqs);
        }else{
            System.out.println("The string contained the following: " + search);
        for (Earthquake quakes : eqs) {
            switch (searchCategory)
            {
                case "Title":
                    if ((quakes.getTitle().contains(search))) {
                        System.out.println("I am in the title of the case");
                        //System.out.println("Inside the if inside the for");
                        MainActivity.alist.add(quakes);
                    }
                    break;
                case "Magnitude":
                    System.out.println("I am in the magnitude of the case");
                    double magnitude = getMagnitude(quakes);
                    double searchmagnitude = Double.parseDouble(search);
                    double diff = magnitude - searchmagnitude;
                    System.out.println("This is the difference: " + diff);
                    if (magnitude == searchmagnitude) {
                        //System.out.println("Inside the if inside the for");
                        MainActivity.alist.add(quakes);
                    }
                    break;
            }


        }}

            notifyDataSetChanged();
            //MainActivity.


    }

    private double getMagnitude(Earthquake item){
        String text = item.getDescription();
        String[] seperated = text.split(";");
        String magnitude = seperated[4];
        String reducedstring = magnitude.substring(13);
        //System.out.println("This is the substring");
        System.out.println(reducedstring);
        double magnitudeint = Double.parseDouble(reducedstring);
        return magnitudeint;
    }
}

