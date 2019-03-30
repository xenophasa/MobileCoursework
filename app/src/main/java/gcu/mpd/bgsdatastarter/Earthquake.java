package gcu.mpd.bgsdatastarter;

public class Earthquake {
    //private String id;
    private String title;
    private String description;
    private String date;
    private String geolat;
    private String geolong;
    private String latLong;
    private String category;
    private boolean expanded;

    public Earthquake(String title,String description,String date,String latLong, String category){
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
        latLong = "";
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

    public String getLatLong(){
        return latLong;
    }

    public void setLatLong(){
        this.latLong = geolat + "," + geolong;
        System.out.println("This is the geolat: " + getGeolat());
        System.out.println("This is the geolong: " + getGeolong());
        System.out.println("This is the latlong: " + latLong);
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

        temp = title + '\n' + date;

        return temp;
    }
    @Override
    public String toString(){
        String temp;

        temp = title + "\n " + description + "\n" + date + "\n" + latLong + "\n" + category;

        return temp;
    }
}
