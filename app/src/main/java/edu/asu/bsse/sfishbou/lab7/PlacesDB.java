package edu.asu.bsse.sfishbou.lab7;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.Nullable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class PlacesDB extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static String dbName = "places";
    private String dbPath;
    private SQLiteDatabase placesDB;
    private final Context context;

    public PlacesDB(Context context) {
        super(context, dbName, null, DATABASE_VERSION);
        this.context = context;

        dbPath = context.getFilesDir().getPath() + "/";

        debugPrint("db path is " + context.getDatabasePath("places"));
        debugPrint("dbPath: " + dbPath);
    }

    /** Checks if the database exists and if it's been initialized or not.
     *
     * @return false: Database file needs to be copied from the assets dir
     */
    private boolean checkDB(){
        SQLiteDatabase checkDB = null;
        boolean placesTableExists = false;

        try{
            String path = dbPath + dbName + ".db";
            File dbFile = new File(path);

            if(dbFile.exists()){
                checkDB = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);

                if(checkDB != null){
                    Cursor tableCheck = checkDB.rawQuery("SELECT name FROM sqlite_master where type = 'table' and name='places';", null);

                    if(tableCheck == null){
                        debugPrint("PlacesDB:checkDB()  check for places table result set is null");
                    }
                    else{
                        tableCheck.moveToNext();
                        debugPrint("PlacesDB:checkDB()  check for places table result set is: " + ((tableCheck.isAfterLast() ? "empty" : (String) tableCheck.getString(0))));
                        placesTableExists = !tableCheck.isAfterLast();
                    }

                    if(placesTableExists){
                        Cursor c = checkDB.rawQuery("SELECT * FROM places", null);
                        c.moveToFirst();

                        while(!c.isAfterLast()){
                            String placeName = c.getString(0);
                            debugPrint("PlacesDB: checkDB()....Places table has PlaceName: " + placeName);
                            c.moveToNext();
                        }
                        placesTableExists = true;
                    }
                }
            }

        }catch(SQLiteException e){
            android.util.Log.w("PlacesDB:checkDB()", e.getMessage());
        }

        if(checkDB != null){
            checkDB.close();
        }

        return placesTableExists;
    }

    public void copyDB() throws IOException{
        try{
            if(!checkDB()){
                //Copy the DB if it doesnt exist in the database directory
                debugPrint("PlacesDB:copyDB() ... checkDB returned false, starting copy...");
                InputStream inputPath = context.getResources().openRawResource(R.raw.places);

                //If the path doesnt exist, create it
                File file = new File(dbPath);
                if(!file.exists()){
                    file.mkdirs();
                }

                String outputPath = dbPath + dbName + ".db";
                OutputStream output = new FileOutputStream(outputPath);
                byte[] buffer = new byte[1024];
                int length;
                while((length = inputPath.read(buffer)) > 0){
                    output.write(buffer, 0, length);
                }

                //Close
                output.flush();
                output.close();
                inputPath.close();
            }
        }catch(IOException e){
            android.util.Log.w("PlacesDB:copyDB()", "ERROR: IOEXCEPTION CALLED!");
        }
    }

    public SQLiteDatabase openDB() throws SQLException{
        String myPath = dbPath + dbName + ".db";
        if(checkDB()){
            placesDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
        }
        else{
            try{
                this.copyDB();
                placesDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
            }catch(Exception e){
                android.util.Log.w(this.getClass().getSimpleName(), "ERROR: UNABLE TO COPY AND OPEN DATABASE!" + e.getMessage() );
            }
        }

        return placesDB;
    }

    @Override
    public synchronized void close(){
        if(placesDB != null){
            placesDB.close();
        }
        super.close();
    }

    private void debugPrint(String str){
        android.util.Log.d(this.getClass().getSimpleName(), str);
    }

    @Override
    public void onCreate(SQLiteDatabase db) { }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { }
}
