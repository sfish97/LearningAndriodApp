package edu.asu.bsse.sfishbou.lab7;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.ContentUris;
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
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, DeleteDialogFragment.DeleteDialogListener{

    private Spinner places_Spinner;

    private CheckBox calcDistance_CheckBox;
    private TextView secondPlace_TextView;
    private Spinner secondPlaces_Spinner;

    private ArrayList<String> placesList;
    private String selectedPlace;
    private String selectedSecondPlace;

    private boolean type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        places_Spinner = findViewById(R.id.places_Spinner);
        secondPlaces_Spinner = findViewById(R.id.secondPlaces_Spinner);
        calcDistance_CheckBox = findViewById(R.id.calcGreatDistance);
        secondPlace_TextView = findViewById(R.id.selectSecondPlace_TextView);

        //Hide the Second TextView/Spinner
        secondPlace_TextView.setVisibility(View.INVISIBLE);
        secondPlaces_Spinner.setVisibility(View.INVISIBLE);


        init();
    }

    public void init(){
        //Call DB to get list of places
        try{
            PlacesDB db = new PlacesDB(this);
            SQLiteDatabase placesDB = db.openDB();
            Cursor cur = placesDB.rawQuery("select name from places;", new String[]{});

            placesList = new ArrayList<String>();
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

        secondPlaces_Spinner.setAdapter(adapter);
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

                DialogFragment deleteDialog = new DeleteDialogFragment();
                deleteDialog.show(getSupportFragmentManager(), "DeleteDialog");

                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    public void deletePlace(){
        String delete1 = "delete from places where name=?;";
        String delete2 = "delete from placeDescription where name=?;";
        try{
            PlacesDB db = new PlacesDB((Context)this);
            SQLiteDatabase placesDB = db.openDB();
            placesDB.execSQL(delete1, new String[]{selectedPlace});
            placesDB.execSQL(delete2, new String[]{selectedPlace});
            placesDB.close();
            db.close();
        }catch(Exception e){
            android.util.Log.w(this.getClass().getSimpleName(), "deletePlace()...ERROR DELETEING");
        }
        init();
        checkDB();

    }

    // AdapterView.OnItemSelectedListener method. Called when spinner selection Changes
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(parent.getId() == R.id.places_Spinner){
            selectedPlace = places_Spinner.getSelectedItem().toString();
        }
        else{
            selectedSecondPlace = secondPlaces_Spinner.getSelectedItem().toString();
        }
    }

    // AdapterView.OnItemSelectedListener method. Called when spinner selection Changes
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        android.util.Log.d(this.getClass().getSimpleName(),"In onNothingSelected: No item selected");

    }

    @Override
    public void onResume(){
        super.onResume();

        //Re-fill the spinner
        init();

        android.util.Log.d(getClass().getSimpleName(), "onResume()");
    }


    public void checkDB(){
        try{
            PlacesDB db = new PlacesDB((Context)this);
            SQLiteDatabase placesDB = db.openDB();
            Cursor cur = placesDB.rawQuery("select * from places;", new String[]{});

            String name = "";
            while (cur.moveToNext()){
                name = cur.getString(0);
                android.util.Log.w(this.getClass().getSimpleName(), "Place value: " + name);
            }
            cur.close();
            placesDB.close();
            db.close();
        }catch(Exception e){
            android.util.Log.w(this.getClass().getSimpleName(), "checkDB(...): ERROR CHECKING PLACES IN DB");
        }

        try{
            PlacesDB db = new PlacesDB((Context)this);
            SQLiteDatabase placesDB = db.openDB();
            Cursor cur = placesDB.rawQuery("select name,description,category,addressTitle,addressStreet,elevation,latitude,longitude from placeDescription;", new String[]{});

            String namey = "";
            String desc = "";
            String cate = "";
            String titl = "";
            String stre = "";
            String elev = "";
            String lati = "";
            String logf = "";
            while (cur.moveToNext()){
                namey = cur.getString(0);
                desc = cur.getString(1);
                cate = cur.getString(2);
                titl = cur.getString(3);
                stre = cur.getString(4);
                elev = cur.getString(5);
                lati = cur.getString(6);
                logf = cur.getString(7);
                android.util.Log.w(this.getClass().getSimpleName(), "***************");
                android.util.Log.w(this.getClass().getSimpleName(), "Name: " + namey);
                android.util.Log.w(this.getClass().getSimpleName(), "Desc: " + desc);
                android.util.Log.w(this.getClass().getSimpleName(), "Cate: " + cate);
                android.util.Log.w(this.getClass().getSimpleName(), "Title: " + titl);
                android.util.Log.w(this.getClass().getSimpleName(), "Street: " + stre);
                android.util.Log.w(this.getClass().getSimpleName(), "Ele: " + elev);
                android.util.Log.w(this.getClass().getSimpleName(), "Lat: " + lati);
                android.util.Log.w(this.getClass().getSimpleName(), "Long: " + logf);
                android.util.Log.w(this.getClass().getSimpleName(), "***************");
            }
            cur.close();
            placesDB.close();
            db.close();
        }catch(Exception e){
            android.util.Log.w(this.getClass().getSimpleName(), "checkDB(...): ERROR CHECKING PLACES IN DB");
        }
    }

    public void viewPlaceDescription_onClick(View view){
        android.util.Log.w(this.getClass().getSimpleName(), "View Place Description Button Clicked");

        Intent intent = new Intent(this, ViewDescriptionActivity.class);
        intent.putExtra("SELECTED_PLACE", selectedPlace);
        startActivity(intent);
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        deletePlace();
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) { }

    public void onCheckBoxClicked(View view){
        //Get if the view is checked
        boolean isChecked = ((CheckBox) view).isChecked();

        if(isChecked){
            secondPlace_TextView.setVisibility(View.VISIBLE);
            secondPlaces_Spinner.setVisibility(View.VISIBLE);
        }
        else{
            secondPlace_TextView.setVisibility(View.INVISIBLE);
            secondPlaces_Spinner.setVisibility(View.INVISIBLE);
        }
    }
}