package com.example.nacho.schoolidoltry4;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import static com.example.nacho.schoolidoltry4.GlobalFunctions.*;
/**
 * Created by nacho on 24/04/2015.
 */
public class MatchEvent extends Event {
    protected MatchEvent(String name, int typeEv, int goal, String date, Context context) {
        super(name, typeEv, goal, date);
        String s = eventType + " " + eventMode + " " + currentPoints + " " + eventGoal + "\n" + date;
        String s1 = context.getFilesDir().toString() + "/" + eventName + EVENT_EXT;
        Toast.makeText(context, s1, Toast.LENGTH_SHORT).show();
        eventFile = new File(context.getFilesDir().toString() + "/" + eventName + EVENT_EXT);
        writeToFile(s, eventFile);
        //writeToFile(eventName, "current_event.txt");
        pushString("current_event_file", eventFile.toString(), context);
        if(configValues == null) {
            Toast.makeText(context, "cargando...", Toast.LENGTH_SHORT).show();
            loadConfig(context);
        }
        else {
            Toast.makeText(context, "length: " + configValues.length, Toast.LENGTH_SHORT).show();
        }
    }

    protected MatchEvent(File file, String name, int mode, int points, int goal, String date, Context context) {
        super(name, 0, goal, date);
        eventFile = file;
        currentPoints = points;
        eventMode = mode;
    }

    @Override
    public void saveEvent() {
        String s = eventType + " " + eventMode + " " + currentPoints + " " + eventGoal + "\n" + endDate;
        writeToFile(s, eventFile);
    }

    protected void loadConfig(Context context) {
        String s = readLineValuesWithDefault(new File(context.getString(R.string.matchEventConfigFile)), "65 138 200 300");
        String[] ss = s.split(" ");
        configValues = new int[4];
        for (int i = 0; i < 4; i++) {
            configValues[i] = Integer.parseInt(ss[i]);
        }
    }

    @Override
    public void readEventValues(Context context) {
        /*if(configValues == null) {
            loadConfig(context);
        }*/
        try {
            FileInputStream fIn = new FileInputStream(eventFile);
            if ( fIn != null ) {
                BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(fIn));
                String s = bufferedReader.readLine();
                Toast.makeText(context, "valores recuperados: " + s, Toast.LENGTH_SHORT).show();
                String[] ss = s.split(" ");
                String fname = eventFile.getName();
                eventName = fname.substring(0, fname.lastIndexOf("."));
                eventType = Integer.parseInt(ss[0]);
                eventMode = Integer.parseInt(ss[1]);
                currentPoints = Integer.parseInt(ss[2]);
                eventGoal = Integer.parseInt(ss[3]);
                endDate = bufferedReader.readLine();
                Toast.makeText(context, "retrieved date: " + endDate, Toast.LENGTH_SHORT).show();
                bufferedReader.close();
                fIn.close();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }
    }

    @Override
    public void calculateSongs() {
        double res = 0;//Math.ceil((double)((eventGoal - currentPoints) / pointsGained));
        if(res > 0.0) {
            nSongs = (int) res;
        }
        else {
            nSongs = 0;
        }

        Date d = null;
        /*Toast.makeText(getApplicationContext(), date, Toast.LENGTH_SHORT).show();
        Toast.makeText(getApplicationContext(), formatter.toPattern(), Toast.LENGTH_SHORT).show();
        Date da = Calendar.getInstance().getTime();

        Toast.makeText(getApplicationContext(), formatter.format(da), Toast.LENGTH_SHORT).show();*/
        try {
            //Toast.makeText(getApplicationContext(), "fecha : " + date, Toast.LENGTH_SHORT).show();
            d = formatter.parse(endDate);//catch exception
            //Toast.makeText(getApplicationContext(), "fecha : " + date, Toast.LENGTH_SHORT).show();
            //Toast.makeText(getApplicationContext(), "fecha obtenida: " + formatter.format(d), Toast.LENGTH_SHORT).show();
        } catch (ParseException e) {
            //Toast.makeText(getApplicationContext(), "mierda", Toast.LENGTH_SHORT).show();

            e.printStackTrace();
        }
        Calendar endDate = Calendar.getInstance();
        endDate.setTime(d);
        Calendar today = Calendar.getInstance();
        long diff = endDate.getTimeInMillis() - today.getTimeInMillis();
        int days = (int) (diff / (24 * 60 * 60 * 1000));
        songsDay = (int) Math.ceil((double)nSongs / days);
    }

    @Override
    public void updateValues(int position, Context context) {
        eventMode = position;
        pointsGained = configValues[position];
        calculateSongs();
        rankUp(context);
    }

    public void updateCfg(){
        pointsGained = configValues[eventMode];
    }
}
