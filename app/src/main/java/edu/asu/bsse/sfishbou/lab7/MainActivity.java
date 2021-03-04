package edu.asu.bsse.sfishbou.lab7;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private Spinner places_Spinner;
    private ArrayList<String> placesList;
    private String selectedPlace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        placesList = new ArrayList<String>();

        //DELETE THIS
        placesList.add("ASU WEST");
        placesList.add("POLY");
        // DELETE THIS

        //Places Spinner Initialization
        places_Spinner = (Spinner) findViewById(R.id.places_Spinner);
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
                PlacesDB db = new PlacesDB((Context)this);
                return true;

            case R.id.action_modifyPlace:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                android.util.Log.d(this.getClass().getSimpleName(), "Modify clicked");
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