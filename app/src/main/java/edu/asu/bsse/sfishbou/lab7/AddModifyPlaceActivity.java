package edu.asu.bsse.sfishbou.lab7;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

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

    public void mainButton_OnClick(View view){
        android.util.Log.w(this.getClass().getSimpleName(), "Main Button Clicked");

    }
}