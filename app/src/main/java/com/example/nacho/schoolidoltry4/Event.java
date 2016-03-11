package com.example.nacho.schoolidoltry4;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;

import static com.example.nacho.schoolidoltry4.GlobalFunctions.pullInt;
import static com.example.nacho.schoolidoltry4.GlobalFunctions.readLineValuesWithDefault;

/**
 * Created by nacho on 24/04/2015.
 */
public abstract class Event {
    String eventName, endDate;
    int eventGoal, eventType, currentPoints, eventMode, pointsGained, nSongs, songsDay;
    static int rank, experience;
    File eventFile;
    static int[] configValues;
    final String EVENT_EXT = ".evn";
    static SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm");

    public static Event createEvent(String name, int typeEv, int goal, String date, Context context) {
        if(typeEv == 0) {
            TokenEvent tev = new TokenEvent(name, typeEv, goal, date, context);
            return tev;
        }
        else {
            MatchEvent match = new MatchEvent(name, typeEv, goal, date, context);
            return match;
        }
    }

    public static Event loadEvent(File file, Context context) {
        if(rank == 0) {
            rank = pullInt("rank", context);
            experience = pullInt("experience", context);
        }

        Event ev = null;
        try {
            FileInputStream fIn = new FileInputStream(file);
            if ( fIn != null ) {
                BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(fIn));
                String s = bufferedReader.readLine();
                Toast.makeText(context, "valores recuperados: " + s, Toast.LENGTH_SHORT).show();
                String[] ss = s.split(" ");
                int type = Integer.parseInt(ss[0]);
                String fname = file.getName();
                if(type == 0) {
                    ev = new MatchEvent(file, fname.substring(0, fname.lastIndexOf(".")), Integer.parseInt(ss[1]), Integer.parseInt(ss[2]), Integer.parseInt(ss[3]), bufferedReader.readLine(), context);
                    //ev = new TokenEvent(file, fname.substring(0, fname.lastIndexOf(".")), Integer.parseInt(ss[1]), Integer.parseInt(ss[2]), Integer.parseInt(ss[3]), Integer.parseInt(ss[4]), Integer.parseInt(ss[5]), bufferedReader.readLine(), context);
                }
                else {
                    ev = new MatchEvent(file, fname.substring(0, fname.lastIndexOf(".")), Integer.parseInt(ss[1]), Integer.parseInt(ss[2]), Integer.parseInt(ss[3]), bufferedReader.readLine(), context);
                }
                Toast.makeText(context, "retrieved date: " + ev.endDate, Toast.LENGTH_SHORT).show();
                if(ev.configValues == null) {
                    ev.loadConfig(context);
                }
                bufferedReader.close();
                fIn.close();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }
        return ev;
    }

    /*public static Event createEvent(File file, Context context) {
        if(typeEv == 0) {
            TokenEvent tev = new TokenEvent(name, typeEv, goal, date, context);
            return tev;
        }
        else {
            MatchEvent match = new MatchEvent(name, typeEv, goal, date, context);
            return match;
        }
    }*/


    protected Event(String name, int typeEv, int goal, String date) {
        this.eventName = name;
        this.eventType = typeEv;
        this.eventGoal = goal;
        this.endDate = date;
    }

    private Event() {}

    /*public static final void loadEvent(Event event, File file, Context context) {
        if(rank == 0) {
            rank = pullInt("rank", context);
            experience = pullInt("experience", context);
        }
        //event =
        event.eventFile = file;
        event.readEventValues(context);
        if(configValues == null) {
            loadConfig(context);
        }
    }*/

    public abstract void readEventValues(Context context);
// ESTO ES UNA MIERDA MUY GORDDA Y HAY QUE SEPARAR LOADCONFIG PARA CADA CASO O ALGO YA QUE CADA CONFIGURACION ES INDEPENDIENTE DE CADA CLASE... CREO QUE MECAGUENLAPUTANACIONDELOSCOJONAZOASSSSS

    protected abstract void loadConfig(Context context);
    public abstract void calculateSongs();
    public String rankUp(Context context) {return "";}
    //public abstract void initialize();
    public abstract void saveEvent();
    public abstract void updateValues(int position, Context context);
    public  void updateNEValues(int position, Context context) {}
    public void updateCfg(){}



}
