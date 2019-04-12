//
//Name  John Fletcher
//Student Id    S1430395
//Programme of Study    Computing
//
package gcu.mpd.S1430395;

import com.google.android.gms.maps.model.LatLng;

public class Earthquake {
    //private String id;
    private String title;
    private String description;
    private String date;
    private String geolat;
    private String geolong;
    private LatLng latLong;
    private String category;
    private double magnitude;
    private boolean expanded;

    public Earthquake(String title,String description,String date,LatLng latLong, String category){
        this.title = title;
        this.description = description;
        this.date = date;
        this.latLong = latLong;
        this.category = category;
    }

    public Earthquake(){
        title = "";
        description = "";
        date = "";
        latLong = new LatLng(0.0,0.0);
        category = "";
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getDescription(){
        return description;
    }

    public void setDescription(String description){
        this.description = description;

    }

    public String getDate(){
        return date;
    }

    public void setDate(String date){
        this.date = date;
    }

    public String getGeolat(){
        return geolat;
    }

    public void setGeolat(String geolat){
        this.geolat = geolat;
    }

    public String getGeolong(){
        return this.geolong;
    }

    public void setGeolong(String geolong){
        this.geolong = geolong;
    }

    public LatLng getLatLong(){
        return latLong;
    }

    public void setLatLong(){
        double dlat = Double.parseDouble(this.geolat);
        double dlong = Double.parseDouble(this.geolong);
        this.latLong = new LatLng(dlat,dlong);
    }

    public String getCategory(){
        return category;
    }

    public void setCategory(String category){
        this.category = category;
    }

    public void setExpanded(){
        expanded ^= true;
    }

    public boolean getExpanded(){
        return expanded;
    }

    public String getSimpleString(){
        String temp;

        String[] seperated = description.split(";");


        temp = seperated[1].substring(1) + '\n' + date + '\n' +  seperated[4].substring(1);

        return temp;
    }
    @Override
    public String toString(){
        String temp;

        temp = title + "\n " + description + "\n" + date + "\n" + latLong + "\n" + category;

        return temp;
    }
}
