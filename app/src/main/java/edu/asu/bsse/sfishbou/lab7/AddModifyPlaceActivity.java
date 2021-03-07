package edu.asu.bsse.sfishbou.lab7;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddModifyPlaceActivity extends AppCompatActivity {

    private EditText name;
    private EditText description;
    private EditText category;
    private EditText addressTitle;
    private EditText addressStreet;
    private EditText elevation;
    private EditText latitude;
    private EditText longitude;
    private Button mainButton;

    private String selectedPlace;
    private boolean type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_modify_place);

        Intent intent = getIntent();
        type = intent.getExtras().getBoolean("TYPE");

        this.name = (EditText)findViewById(R.id.name_EditTextBox);
        this.description = (EditText)findViewById(R.id.description_EditTextBox);
        this.category = (EditText)findViewById(R.id.category_EditTextBox);
        this.addressTitle = (EditText)findViewById(R.id.addressTitle_EditTextBox);
        this.addressStreet = (EditText)findViewById(R.id.addressStreet_EditTextBox);
        this.elevation = (EditText)findViewById(R.id.elevation_EditTextBox);
        this.latitude = (EditText)findViewById(R.id.latitude_EditTextBox);
        this.longitude = (EditText)findViewById(R.id.longitude_EditTextBox);
        this.mainButton = (Button)findViewById(R.id.mainButton);

        this.selectedPlace = intent.getExtras().getString("SELECTED_PLACE");
        if(type){ //Adding
            this.mainButton.setText("Add Place");
        }
        else{  //Modify, disable the Name so it can't be changed, and fill in the boxes
            this.mainButton.setText("Modify Place");
            this.name.setText(selectedPlace);
            this.name.setEnabled(false);
            fillTheBoxes();
        }

    }


    public void fillTheBoxes(){
        try{
            PlacesDB db = new PlacesDB((Context)this);
            SQLiteDatabase placesDB = db.openDB();
            Cursor cur = placesDB.rawQuery("select name,description,category,addressTitle,addressStreet,elevation,latitude,longitude from placeDescription where name=?;", new String[]{this.selectedPlace});

            while (cur.moveToNext()){
                this.name.setText(cur.getString(0));
                this.description.setText(cur.getString(1));
                this.category.setText(cur.getString(2));
                this.addressTitle.setText(cur.getString(3));
                this.addressStreet.setText(cur.getString(4));
                this.elevation.setText(cur.getString(5));
                this.latitude.setText(cur.getString(6));
                this.longitude.setText(cur.getString(7));
            }
            cur.close();
            placesDB.close();
            db.close();
        }catch(Exception e){
            android.util.Log.w(this.getClass().getSimpleName(), "checkDB(...): ERROR CHECKING PLACES IN DB");
        }
    }

    public void addPlaceToDB(String nameVal, String descVal, String categoryVal, String addressTitleVal,
                             String addressStreetVal, float elevationVal, float latVal, float longVal){
        try{
            PlacesDB db = new PlacesDB((Context)this);
            SQLiteDatabase placesDB = db.openDB();

            //Put the name in the 'places' table
            ContentValues values = new ContentValues();
            values.put("name", nameVal);
            placesDB.insert("places", null, values);

            //Put the description in the 'placeDescription' table
            values.put("description", descVal);
            values.put("category", categoryVal);
            values.put("addressTitle", addressTitleVal);
            values.put("addressStreet", addressStreetVal);
            values.put("elevation", elevationVal);
            values.put("latitude", latVal);
            values.put("longitude", longVal);
            placesDB.insert("placeDescription", null, values);

            placesDB.close();
            db.close();
            //checkDB();

            android.util.Log.w(this.getClass().getSimpleName(), "addPlaceToDB(...): Added place to DB");
        }catch(Exception e){
            android.util.Log.w(this.getClass().getSimpleName(), "addPlaceToDB(...): ERROR ADDING PLACE TO DB");
        }
    }

    public void modifyPlaceToDB(String nameVal, String descVal, String categoryVal, String addressTitleVal,
                             String addressStreetVal, float elevationVal, float latVal, float longVal){

        try{
            PlacesDB db = new PlacesDB((Context)this);
            SQLiteDatabase placesDB = db.openDB();

            //Update the table
            ContentValues values = new ContentValues();
            values.put("name", nameVal);
            values.put("description", descVal);
            values.put("category", categoryVal);
            values.put("addressTitle", addressTitleVal);
            values.put("addressStreet", addressStreetVal);
            values.put("elevation", elevationVal);
            values.put("latitude", latVal);
            values.put("longitude", longVal);

            placesDB.update("placeDescription", values, "name=?", new String[]{this.name.getText().toString()});

            placesDB.close();
            db.close();
        }catch(Exception ex){
            android.util.Log.w(this.getClass().getSimpleName(), "addPlaceToDB(...): ERROR MODIFYING PLACE TO DB");
        }
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

    public void mainButton_OnClick(View view){
        android.util.Log.w(this.getClass().getSimpleName(), "Main Button Clicked");

        //Get the values
        String nameVal = name.getText().toString();
        String descVal = description.getText().toString();
        String categoryVal = category.getText().toString();
        String addressTitleVal = addressTitle.getText().toString();
        String addressStreetVal = addressStreet.getText().toString();
        float elevationVal = Float.parseFloat(elevation.getText().toString());
        float latVal = Float.parseFloat(latitude.getText().toString());
        float longVal = Float.parseFloat(longitude.getText().toString());

        //Add or Modify the database with the values
        if(type){
            addPlaceToDB(nameVal, descVal, categoryVal, addressTitleVal,
                    addressStreetVal, elevationVal, latVal, longVal);
        }
        else{
            modifyPlaceToDB(nameVal, descVal, categoryVal, addressTitleVal,
                    addressStreetVal, elevationVal, latVal, longVal);
        }
        //Go back to the main screen
        finish();
    }
}