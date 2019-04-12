//
//Name  John Fletcher
//Student Id    S1430395
//Programme of Study    Computing
//
package gcu.mpd.bgsdatastarter;

import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.Serializable;
import java.util.ArrayList;

public class MapsActivity2 extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps2);
        text = (TextView) findViewById(R.id.keyText);
        Spannable span = new SpannableString("Magnitude key:\nRed = above 2 Yellow = between 1 and 2 and Green = below 1" );
        span.setSpan(new ForegroundColorSpan(Color.RED),15,28,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        span.setSpan(new ForegroundColorSpan(Color.YELLOW),29,53,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        span.setSpan(new ForegroundColorSpan(Color.GREEN),58,73,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        text.setText(span);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        getIntent();


        // Add a marker in Sydney and move the camera
        Bundle extras = getIntent().getExtras();
        float zoom = 5.0f;
        for(int i =0;i<extras.size()/3;i++) {
            String title = extras.getString(i + "Title");
            LatLng marker = extras.getParcelable(i + "Value");
            double temp = extras.getDouble(i+"Magnitude");
            String[] seperated = title.split("\n");

            if(temp>2.0) {
                mMap.addMarker(new MarkerOptions().position(marker).title(seperated[0]).snippet(seperated[1] + seperated[2]).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
            }else if(temp>1.0){
                mMap.addMarker(new MarkerOptions().position(marker).title(seperated[0]).snippet(seperated[1] + seperated[2]).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
            }else{
                mMap.addMarker(new MarkerOptions().position(marker).title(seperated[0]).snippet(seperated[1] + seperated[2]).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
            }
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(marker,zoom));
        }

    }
}
