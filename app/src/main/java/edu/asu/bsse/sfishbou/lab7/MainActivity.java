package edu.asu.bsse.sfishbou.lab7;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.opengl.Visibility;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, DeleteDialogFragment.DeleteDialogListener{

    private Spinner places_Spinner;

    private CheckBox calcDistance_CheckBox;
    private TextView firstPlace_TextView;
    private TextView secondPlace_TextView;
    private Spinner secondPlaces_Spinner;
    private Button viewDescription_Button;

    private TextView distanceText_TextView,
                     distanceValue_TextView,
                     initalHeadingText_TextView,
                     initalHeadingValue_TextView;

    private ArrayList<String> placesList;
    private String selectedPlace;
    private String selectedSecondPlace;

    private boolean type;
    private boolean isCalcSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firstPlace_TextView = findViewById(R.id.selectPlace_TextView);
        places_Spinner = findViewById(R.id.places_Spinner);
        secondPlaces_Spinner = findViewById(R.id.secondPlaces_Spinner);
        calcDistance_CheckBox = findViewById(R.id.calcGreatDistance);
        secondPlace_TextView = findViewById(R.id.selectSecondPlace_TextView);
        distanceText_TextView = findViewById(R.id.distanceText_TextView);
        distanceValue_TextView = findViewById(R.id.distanceValue_TextView);
        initalHeadingText_TextView = findViewById(R.id.initalHeadingText_TextView);
        initalHeadingValue_TextView = findViewById(R.id.initialHeadingValue_TextView);
        viewDescription_Button = findViewById(R.id.viewPlace_Button);
        isCalcSelected = false;

        //Hide the Second TextView/Spinner
        switchCalcViews();
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
        secondPlaces_Spinner.setOnItemSelectedListener(this);
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

    @Override
    public boolean onPrepareOptionsMenu (Menu menu) {
        menu.findItem(R.id.action_addPlace).setEnabled(!isCalcSelected);
        menu.findItem(R.id.action_modifyPlace).setEnabled(!isCalcSelected);
        menu.findItem(R.id.action_deletePlace).setEnabled(!isCalcSelected);

        return true;
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
    }


    // AdapterView.OnItemSelectedListener method. Called when spinner selection Changes
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(parent.getId() == R.id.places_Spinner){
            selectedPlace = places_Spinner.getSelectedItem().toString();
            android.util.Log.w(this.getClass().getSimpleName(), "FIRST ITEM IS " + places_Spinner.getSelectedItem().toString());
        }
        if(parent.getId() == R.id.secondPlaces_Spinner){
            selectedSecondPlace = secondPlaces_Spinner.getSelectedItem().toString();
            android.util.Log.w(this.getClass().getSimpleName(), "SECOND ITEM IS " + secondPlaces_Spinner.getSelectedItem().toString());
        }

        if(isCalcSelected){
            calculateGreatDistance();
        }

    }

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
        invalidateOptionsMenu();
        //Get if the view is checked
        isCalcSelected = ((CheckBox) view).isChecked();

        if(isCalcSelected){
            calculateGreatDistance();
            viewDescription_Button.setEnabled(false);

            //Change the `Select a Place` text to Select Starting/End Place
            firstPlace_TextView.setText(R.string.select_firstPlaceTV);
        }
        else{
            viewDescription_Button.setEnabled(true);
            firstPlace_TextView.setText(R.string.select_placesTV);
        }
        switchCalcViews();
    }

    public void switchCalcViews(){
        int type = isCalcSelected ? View.VISIBLE : View.INVISIBLE;

        secondPlace_TextView.setVisibility(type);
        secondPlaces_Spinner.setVisibility(type);
        distanceText_TextView.setVisibility(type);
        distanceValue_TextView.setVisibility(type);
        initalHeadingText_TextView.setVisibility(type);
        initalHeadingValue_TextView.setVisibility(type);
    }

    public void calculateGreatDistance(){
        double[] latAndLong1 = getLatAndLong(1); //[latitude1, longitude1]
        double[] latAndLong2 = getLatAndLong(2);; //[latitude2, longitude2]

        int distanceVal = getDistance(latAndLong1, latAndLong2);
        int initialHeading = getInitalHeading(latAndLong1, latAndLong2);
        String direction = "";
        if (initialHeading >= 0 && initialHeading < 90)
            direction = "(N)";
        else if (initialHeading >= 90 && initialHeading < 180)
            direction = "(E)";
        else if(initialHeading >= 180 && initialHeading < 270)
            direction = "(S)";
        else if(initialHeading >= 270 && initialHeading < 360)
            direction = "(W)";

        android.util.Log.w(this.getClass().getSimpleName(), "Values "+ this.selectedPlace + '\n' + latAndLong1[0] + '\n' + latAndLong1[1] + '\n' + this.selectedSecondPlace + '\n' + latAndLong2[0] + '\n' + latAndLong2[1] + '\n');
        android.util.Log.w(this.getClass().getSimpleName(), "DISTANCE: "+ distanceVal);
        distanceValue_TextView.setText(distanceVal + " meters.");
        initalHeadingValue_TextView.setText(initialHeading + "\u00B0" + " " + direction);
    }

    public double[] getLatAndLong(int indicator){
        double[] values = new double[2];

        String place;
        if(indicator == 1){
            place = selectedPlace;
        }
        else{
            place = selectedSecondPlace;
        }

        //Get the lat and long of the
        try{
            PlacesDB db = new PlacesDB((Context)this);
            SQLiteDatabase placesDB = db.openDB();
            Cursor cur = placesDB.rawQuery("select latitude,longitude from placeDescription where name=?;", new String[]{place});

            while (cur.moveToNext()){
                values[0] = cur.getDouble(0);
                values[1] = cur.getDouble(1);
            }
            cur.close();
            placesDB.close();
            db.close();
        }catch(Exception e){
            android.util.Log.w(this.getClass().getSimpleName(), "checkDB(...): ERROR CHECKING PLACES IN DB");
        }

        return values;
    }

    public int getDistance(double[] latAndLong1, double[] latAndLong2){
        double earthRadius = 6371e3;
        double latRadian1 = latAndLong1[0] * Math.PI/180;
        double latRadian2 = latAndLong2[0] * Math.PI/180;
        double changeLat = (latAndLong2[0] - latAndLong1[0]) * Math.PI/180;
        double changeLong = (latAndLong2[1] - latAndLong1[1]) * Math.PI/180;

        double a = Math.sin(changeLat/2) * Math.sin(changeLat/2) +
                Math.cos(latRadian1) * Math.cos(latRadian2) *
                        Math.sin(changeLong/2) * Math.sin(changeLong/2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

        return (int)(earthRadius * c);
    }

    public int getInitalHeading(double[] latAndLong1, double[] latAndLong2){
        double lat1 = latAndLong1[0] * Math.PI / 180;
        double lat2 = latAndLong2[0] * Math.PI / 180;
        double long1 = latAndLong1[1] * Math.PI / 180;
        double long2 = latAndLong2[1] * Math.PI / 180;

        double changeLong = long2 - long1;
        double y = Math.sin(changeLong) * Math.cos(lat1);
        double x = (Math.cos(lat1)*Math.sin(lat2)) -
                    (Math.sin(lat1)*Math.cos(lat2)*Math.cos(changeLong));
        double θ = Math.atan2(y, x);
        double brng = (θ*180/Math.PI + 360) % 360;

        return (int)brng;

    }
}