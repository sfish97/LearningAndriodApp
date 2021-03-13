package edu.asu.bsse.sfishbou.lab7;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

public class ViewDescriptionActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private String[] placeDescriptionList;
    private String[] placeValues;
    private String selectedPlace;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_description);

        Intent intent = getIntent();
        this.selectedPlace = intent.getExtras().getString("SELECTED_PLACE");

        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);

        this.placeDescriptionList = getResources().getStringArray(R.array.descriptionList);
        this.placeValues = getPlaceValuesFromDB();

    }

    private String[] getPlaceValuesFromDB(){
        String[] values = new String[8];

        try{
            PlacesDB db = new PlacesDB((Context)this);
            SQLiteDatabase placesDB = db.openDB();
            Cursor cur = placesDB.rawQuery("select name,description,category,addressTitle,addressStreet,elevation,latitude,longitude from placeDescription where name=?;", new String[]{this.selectedPlace});

            while (cur.moveToNext()){
                values[0] = cur.getString(0);
                values[1] = cur.getString(1);
                values[2] = cur.getString(2);
                values[3] = cur.getString(3);
                values[4] = cur.getString(4);
                values[5] = cur.getString(5);
                values[6] = cur.getString(6);
                values[7] = cur.getString(7);
            }
            cur.close();
            placesDB.close();
            db.close();
        }catch(Exception e){
            android.util.Log.w(this.getClass().getSimpleName(), "getPlaceValuesFromDB(...): ERROR CHECKING PLACES IN DB");
        }

        return values;
    }
}