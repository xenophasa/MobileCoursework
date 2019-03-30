/*
package gcu.mpd.bgsdatastarter;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SearchForm extends AppCompatActivity {

    final Context context =this;
    private Button button;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        */
/*super.onCreate(savedInstanceState);*//*

        setContentView(R.layout.dialog_search);

        LayoutInflater inflater = LayoutInflater.from(context);
        View promtView = inflater.inflate(R.layout.dialog_search,null);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(promtView);

        final EditText search = (EditText) promtView.findViewById(R.id.searchText);

        builder.setTitle("Earthquake search");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                System.out.println("This is the edit text: " + search.getText());
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


}
*/
