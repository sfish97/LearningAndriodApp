package edu.asu.bsse.sfishbou.lab7;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
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

    private boolean type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_modify_place);

        Intent intent = getIntent();
        type = intent.getExtras().getBoolean("TYPE");

        name = (EditText)findViewById(R.id.name_EditTextBox);
        description = (EditText)findViewById(R.id.description_EditTextBox);
        category = (EditText)findViewById(R.id.category_EditTextBox);
        addressTitle = (EditText)findViewById(R.id.addressTitle_EditTextBox);
        addressStreet = (EditText)findViewById(R.id.addressStreet_EditTextBox);
        elevation = (EditText)findViewById(R.id.elevation_EditTextBox);
        latitude = (EditText)findViewById(R.id.latitude_EditTextBox);
        longitude = (EditText)findViewById(R.id.longitude_EditTextBox);
        mainButton = (Button)findViewById(R.id.mainButton);

        if(type){ //Adding
            mainButton.setText("Add Place");
        }
        else{  //Modify, disable the Name so it can't be changed
            mainButton.setText("Modify Place");
            name.setText(intent.getExtras().getString("SELECTED_PLACE"));
            name.setEnabled(false);
        }

    }

    public void addPlaceToDB(String nameVal, String descVal, String categoryVal, String addressTitleVal,
                             String addressStreetVal, float elevationVal, float latVal, float longVal){
        try{
            PlacesDB db = new PlacesDB((Context)this);
        }
    }

    public void modifyPlaceToDB(String nameVal, String descVal, String categoryVal, String addressTitleVal,
                             String addressStreetVal, float elevationVal, float latVal, float longVal){

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

        }

        //Go back to the main screen
        finish();

    }
}