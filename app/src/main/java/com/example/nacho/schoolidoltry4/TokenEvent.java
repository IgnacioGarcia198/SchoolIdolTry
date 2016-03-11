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
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;


import static com.example.nacho.schoolidoltry4.GlobalFunctions.*;

/**
 * Created by nacho on 24/04/2015.
 */
public class TokenEvent extends Event {
    int tokensGained, price, nonEventMode, currentTokens;

    protected TokenEvent(String name, int typeEv, int goal, String date, Context context) {
        super(name, typeEv, goal, date);
        String s = eventType + " " + eventMode + " " + nonEventMode + " " + currentTokens + " " + currentPoints + " " + eventGoal + "\n" + date;

        String s1 = context.getFilesDir().toString() + "/" + eventName + EVENT_EXT;
        Toast.makeText(context, "creamos en: " + s1, Toast.LENGTH_SHORT).show();
        eventFile = new File(context.getFilesDir().toString() + "/" + eventName + EVENT_EXT);
        writeToFile(s, eventFile);
        //writeToFile(eventName, "current_event.txt");
        pushString("current_event_file", eventFile.toString(), context);
        if(configValues == null) {
            loadConfig(context);
        }
    }

    protected TokenEvent(File file, String name, int evm, int noevm, int tokens, int points, int goal, String date, Context context) {
        super(name, 0, goal, date);
        eventFile = file;
        currentTokens = tokens;
        currentPoints = points;
        eventMode = evm;
        nonEventMode = noevm;
    }





    /*public static void loadEvent(Event event, File file, Context context) {
        if(rank == 0) {
            rank = pullInt("rank", context);
            experience = pullInt("experience", context);
        }
        //event =
        event.eventFile = file;
        event.readEventValues(context);

        try {
            FileInputStream fIn = new FileInputStream(file);
            if ( fIn != null ) {
                BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(fIn));
                String s = bufferedReader.readLine();
                Toast.makeText(context, "valores recuperados: " + s, Toast.LENGTH_SHORT).show();
                String[] ss = s.split(" ");
                int type = Integer.parseInt(ss[0]);
                String fname = eventFile.getName();
                if(type == 0) {
                    event = new TokenEvent(file, fname.substring(0, fname.lastIndexOf(".")), Integer.parseInt(ss[5]), bufferedReader.readLine(), context);
                }
                else {

                }

                String fname = eventFile.getName();
                eventName = fname.substring(0, fname.lastIndexOf("."));
                eventType = Integer.parseInt(ss[0]);
                eventMode = Integer.parseInt(ss[1]);
                //eventType + " " + eventMode + " " + nonEventMode + " " + currentTokens + " " + currentPoints + " " + eventGoal + " " + date;
                nonEventMode = Integer.parseInt(ss[2]);
                currentTokens = Integer.parseInt(ss[3]);
                currentPoints = Integer.parseInt(ss[4]);
                eventGoal = Integer.parseInt(ss[5]);
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


        if(configValues == null) {
            loadConfig(context);
        }*/
    //}
    protected void loadConfig(Context context) {
        String s = readLineValuesWithDefault(new File(context.getString(R.string.tokenEventConfigFile)), "5 15 65 10 30 138 16 45 200 27 75 280");
        String[] ss = s.split(" ");
        configValues = new int[12];
        for (int i = 0; i < 12; i++) {
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
                //eventType + " " + eventMode + " " + nonEventMode + " " + currentTokens + " " + currentPoints + " " + eventGoal + " " + date;
                nonEventMode = Integer.parseInt(ss[2]);
                currentTokens = Integer.parseInt(ss[3]);
                currentPoints = Integer.parseInt(ss[4]);
                eventGoal = Integer.parseInt(ss[5]);
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
        double res = Math.ceil((double)((eventGoal - currentPoints) * price - currentTokens * pointsGained)/(tokensGained * (pointsGained + price)));
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

    /**
     * Finds out whether we can or not rank up playing the event song in a token collection event, and the number of event songs in different modes we should play in order to do that.
     * Then, it sets the result as rankUpTx.text in token collection event screen.
     */
    @Override
    public String rankUp(Context context) {
        int rup;
        String outText = "";
        if(rank > 0) {
            if(rank < 34) {
                String[] ss = context.getString(R.string.rankTable).split(" ");
                rup = Integer.parseInt(ss[rank - 1]);
            }
            else {
                rup = (int) Math.ceil(34.35*rank-551);
            }
            int expMissing = rup - experience;
            int se = currentTokens / configValues[1];
            int sn = currentTokens / configValues[4];
            int sh = currentTokens / configValues[7];
            int sex = currentTokens / configValues[10];

            int ne = (int) Math.ceil(expMissing / 12.0);
            int nn = (int) Math.ceil(expMissing / 26.0);
            int nh = (int) Math.ceil(expMissing / 46.0);
            int nex = (int) Math.ceil(expMissing / 83.0);
            if((se >= ne) || (sn >= nn) || (sh >= nh) || (sex >= nex)) {
                outText = "You can RANK UP playing the event!!\n" +
                        "Playing:";
                if(se >= ne) {
                    outText += "\n- " + ne + " easy event songs";
                }
                if(sn >= nn) {
                    outText += "\n- " + nn + " normal event songs";
                }
                if(sh >= nh) {
                    outText += "\n- " + nh + " hard event songs";
                }
                if(sex >= nex) {
                    outText += "\n- " + nex + " expert event songs";
                }
            }

        }
        else {
            //return "";
        }
        return outText;
    }

    @Override
    public void saveEvent() {
        String s = eventType + " " + eventMode + " " + nonEventMode + " " + currentTokens + " " + currentPoints + " " + eventGoal + "\n" + endDate;
        writeToFile(s, eventFile);
    }

    @Override
    public void updateValues(int position, Context context) {
        eventMode = position;
        price = configValues[3*position+1];
        pointsGained = configValues[3*position+2];
        calculateSongs();
        rankUp(context);
    }

    public void updateNEValues(int position, Context context){
        nonEventMode = position;
        tokensGained = configValues[3*position];
        calculateSongs();
        rankUp(context);
    }

    public void updateCfg(){
        tokensGained = configValues[3*nonEventMode];
        price = configValues[3*eventMode+1];
        pointsGained = configValues[3*eventMode+2];
    }
}
