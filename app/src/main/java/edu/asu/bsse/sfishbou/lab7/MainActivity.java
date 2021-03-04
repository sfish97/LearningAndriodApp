package edu.asu.bsse.sfishbou.lab7;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private Spinner places_Spinner;
    private ArrayList<String> placesList;
    private String selectedPlace;

    private boolean type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        places_Spinner = (Spinner) findViewById(R.id.places_Spinner);
        placesList = new ArrayList<String>();

        init();
    }

    public void init(){
        //Call DB to get list of places
        try{
            PlacesDB db = new PlacesDB((Context)this);
            SQLiteDatabase placesDB = db.openDB();
            Cursor cur = placesDB.rawQuery("select name from places;", new String[]{});

            while(cur.moveToNext()){
                placesList.add(cur.getString(0));
            }
            cur.close();
            placesDB.close();
            db.close();
        }catch(Exception ex){
            android.util.Log.w(this.getClass().getSimpleName(), "init(): Error getting data from database");
        }
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, placesList);
        places_Spinner.setAdapter(adapter);
        places_Spinner.setOnItemSelectedListener(this);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        android.util.Log.d(this.getClass().getSimpleName(), "called onCreateOptionsMenu()");
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_addPlace:
                // User chose the "Settings" item, show the app settings UI...
                android.util.Log.d(this.getClass().getSimpleName(), "Add clicked");

                type = true;
                Intent addIntent = new Intent(this, AddModifyPlaceActivity.class);
                addIntent.putExtra("TYPE", type);
                startActivity(addIntent);
                return true;

            case R.id.action_modifyPlace:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                android.util.Log.d(this.getClass().getSimpleName(), "Modify clicked");

                type = false;
                Intent modifyIntent = new Intent(this, AddModifyPlaceActivity.class);
                modifyIntent.putExtra("TYPE", type);
                modifyIntent.putExtra("SELECTED_PLACE", selectedPlace);
                startActivity(modifyIntent);

                return true;

            case R.id.action_deletePlace:
                android.util.Log.d(this.getClass().getSimpleName(), "Delete clicked");
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    // AdapterView.OnItemSelectedListener method. Called when spinner selection Changes
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selectedPlace = places_Spinner.getSelectedItem().toString();
        android.util.Log.d(this.getClass().getSimpleName(),"Spinner item "+
                places_Spinner.getSelectedItem().toString() + " selected.");
    }

    // AdapterView.OnItemSelectedListener method. Called when spinner selection Changes
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        android.util.Log.d(this.getClass().getSimpleName(),"In onNothingSelected: No item selected");

    }

}