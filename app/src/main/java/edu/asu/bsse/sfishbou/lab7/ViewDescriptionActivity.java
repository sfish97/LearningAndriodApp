package edu.asu.bsse.sfishbou.lab7;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Copyright (c) 2021 Steven Fishbough,
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.

 * @author Steven Fishbough sfishbou@asu.edu
 *         Software Engineering, CIDSE, IAFSE, Arizona State University Polytechnic
 * @version April 11, 2021
 */
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

        MyRecyclerAdapter recyclerAdapter = new MyRecyclerAdapter(this, placeDescriptionList, placeValues);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

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