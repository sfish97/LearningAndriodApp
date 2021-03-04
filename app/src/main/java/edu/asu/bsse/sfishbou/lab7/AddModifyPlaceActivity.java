package edu.asu.bsse.sfishbou.lab7;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class AddModifyPlaceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_modify_place);
    }

    public void mainButton_OnClick(View view){
        android.util.Log.w(this.getClass().getSimpleName(), "Main Button Clicked");
    }
}