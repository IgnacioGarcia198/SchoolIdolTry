package com.example.nacho.schoolidoltry4;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.example.nacho.schoolidoltry4.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Created by nacho on 20/03/2015.
 */
public class MyActivity extends FragmentActivity {
    protected String TAG = "TKT";
    //protected static boolean checked = true;
    void changeScreen(Class c) {
        Intent intent = new Intent(this,c);
        startActivity(intent);
    }


    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }

    protected String pullString(String key) {
        // ARCHIVO DE CLAVES: ACCEDER
        Context context = this;
        SharedPreferences sharedPref = context.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        // LEER

        return sharedPref.getString(key, "");
    }

    protected void pushString (String key, String value) {
        // ARCHIVO DE CLAVES: ACCEDER
        Context context = this;
        SharedPreferences sharedPref = context.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        // EDITAR
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, value);
        editor.commit();
    }

    protected boolean pullBoolean(String key) {
        // ARCHIVO DE CLAVES: ACCEDER
        Context context = this;
        SharedPreferences sharedPref = context.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        // LEER

        return sharedPref.getBoolean(key, false);
    }

    protected void pushBoolean (String key, Boolean value) {
        // ARCHIVO DE CLAVES: ACCEDER
        Context context = this;
        SharedPreferences sharedPref = context.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        // EDITAR
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(key, value);
        editor.commit();

    }

    protected int pullInt(String key) {
        // ARCHIVO DE CLAVES: ACCEDER
        Context context = this;
        SharedPreferences sharedPref = context.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        // LEER

        return sharedPref.getInt(key, 0);
    }

    protected void pushInt (String key, int value) {
        // ARCHIVO DE CLAVES: ACCEDER
        Context context = this;
        SharedPreferences sharedPref = context.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        // EDITAR
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(key, value);
        editor.commit();

    }

    boolean isExternalStorage(File file) {
        String route = file.toString();
        String[] folders = route.split("/");
        for(int i = 0; i < folders.length; i ++) {
            if(folders[i].equals("sdcard")) {
                return true;
            }
        }
        return false;
    }

    boolean isExternalStorage(String route) {
        String[] folders = route.split("/");
        for(int i = 0; i < folders.length; i ++) {
            if(folders[i].equals("sdcard")) {
                return true;
            }
        }
        return false;
    }

    /**
     * This function as it is is only meant to write files in the private directory of the application (by the moment)
     * @param text
     * @param file
     */
    protected void writeToFile(String text, String file) {
        //if((!isExternalStorage(file)) || (isExternalStorage(file) && (externalStoragePermissions() == 2))) {
            try {
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput(file, Context.MODE_PRIVATE));
                outputStreamWriter.write(text);
                outputStreamWriter.close();
            } catch (IOException e) {
                Log.e("Exception", "File write failed: " + e.toString());
            }
        //}
    }

    protected void writeToFile(String text, File file) {
        try {
            FileOutputStream fOut = new FileOutputStream(file);
            OutputStreamWriter myOutWriter =
                    new OutputStreamWriter(fOut);
            myOutWriter.write(text);
            myOutWriter.close();
            fOut.close();
/*            Toast.makeText(getBaseContext(),
                    "Done writing SD 'mysdfile.txt'",
                    Toast.LENGTH_SHORT).show();*/
        }
        catch (Exception e) {
            /*Toast.makeText(getBaseContext(), e.getMessage(),
                    Toast.LENGTH_SHORT).show();*/
        }
    }


    protected String readFromFile(String file) {

        String ret = "";
        if((!isExternalStorage(file)) || (isExternalStorage(file) && (externalStoragePermissions() > 0))) {
            try {
                InputStream inputStream = openFileInput(file);

                if (inputStream != null) {
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    String receiveString = "";
                    StringBuilder stringBuilder = new StringBuilder();

                    while ((receiveString = bufferedReader.readLine()) != null) {
                        stringBuilder.append(receiveString);
                    }

                    inputStream.close();
                    ret = stringBuilder.toString();
                }
            } catch (FileNotFoundException e) {
                Log.e("login activity", "File not found: " + e.toString());
            } catch (IOException e) {
                Log.e("login activity", "Can not read file: " + e.toString());
            }
        }
        return ret;
    }

    protected String readFromFile(File file) {
        // write on SD card file data in the text box
        StringBuilder aBuffer = new StringBuilder();
        try {
            FileInputStream fIn = new FileInputStream(file);
            BufferedReader myReader = new BufferedReader(
                    new InputStreamReader(fIn));
            String aDataRow = "";
            while ((aDataRow = myReader.readLine()) != null) {
                aBuffer.append(aDataRow);
                aBuffer.append("\n");
            }
            myReader.close();
            fIn.close();

        }
        catch (Exception e) {

        }
        return aBuffer.toString();
    }

    public byte externalStoragePermissions() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return 2;
        }
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return 1;
        }
        return 0;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.greetinglayout);
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Log.d(TAG, this.getLocalClassName() + " onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG,this.getLocalClassName() + " onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Log.d(TAG,this.getLocalClassName() + " onResume");
        //sacaValores();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG,this.getLocalClassName() + " onRestart");
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //Log.d(TAG,this.getLocalClassName() + " onStop");
        //sacaValores();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG,this.getLocalClassName() + " onDestroy");
    }
}
