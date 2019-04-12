//
//Name  John Fletcher
//Student Id    S1430395
//Programme of Study    Computing
//
package gcu.mpd.S1430395;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.LinkedList;

public class CustomAdaptor extends ArrayAdapter<Earthquake> {

    private LinkedList<Earthquake> eqs = new LinkedList<Earthquake>();

    public static class ViewHolder {
        private TextView textView;
    }

    public CustomAdaptor(Context context, int textViewResourceId, LinkedList<Earthquake> items) {
        super(context, textViewResourceId, items);
        this.eqs.clear();
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
        final Earthquake item = getItem(position);
        if (item != null) {
            double magnitudeint = getMagnitude(item);
            if (magnitudeint > 2) {
                viewHolder.textView.setBackgroundColor(Color.RED);
            } else if(magnitudeint > 1) {
                viewHolder.textView.setBackgroundColor(Color.YELLOW);
            } else{
                viewHolder.textView.setBackgroundColor(Color.GREEN);
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

        //int count = getCount();
        MainActivity.alist.clear();
        if (search.length() == 0) {
            MainActivity.alist.addAll(eqs);
        }else{
            switch (searchCategory)
            {
                case "Title":
                    MainActivity.alist.clear();
                    for (Earthquake quakes : eqs) {
                        if ((quakes.getTitle().contains(search.toUpperCase()))) {
                            searchedEarthquakes.add(quakes);
                        }
                    }
                    break;
                case "Magnitude":
                    for (Earthquake quakes : eqs) {
                        double magnitude = getMagnitude(quakes);
                        double searchmagnitude = Double.parseDouble(search);
                        double diff = magnitude - searchmagnitude;
                        if (magnitude == searchmagnitude) {
                            MainActivity.alist.add(quakes);
                        }
                    }
                    break;
            }


        }
        MainActivity.alist.addAll(searchedEarthquakes);
        notifyDataSetChanged();
    }


            //MainActivity.



    private double getMagnitude(Earthquake item){
        String text = item.getDescription();
        String[] seperated = text.split(";");
        String magnitude = seperated[4];
        String reducedstring = magnitude.substring(13);
        double magnitudeint = Double.parseDouble(reducedstring);
        return magnitudeint;
    }
}

