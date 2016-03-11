package com.example.nacho.schoolidoltry4;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import static com.example.nacho.schoolidoltry4.GlobalFunctions.*;
import com.example.nacho.schoolidoltry4.R;

// IS MISSING:
// ADAPTATION TO SCORE MATCH EVENT IS ONGOING
// SCORE MATCH EVENT CONFIG LAYOUT HAS YET TO BE DONE (ALMOST FINISHED)
// THERE IS A PROBLEM OF CONCEPT FOR BEING THIS MY FIRST APPLICATION IN ANDROID STUDIO:
// - WE NEED ANOTHER TABLE FOR MATCH CONFIG VALUES, UNLESS WE RECYCLE THAT ONE OF TOKENS.
// - THE WHOLE THING IS A MESS BECAUSE WE SHOULD HAVE CREATED A SEPARATE CLASS FOR EVENTS WITH ITS FUNCTIONS AND PROPERTIES, AS EVENTNAME, ETC.
// WE SHOULD CREATE EDITTEXTS IN CONFIG LAYOUTS IN EXECUTION, SO THAT WE DON'T NEED A LOT OF USELESS VARIABLES.


/**
 * Created by nacho on 15/03/2015.
 */
public class MainScreen extends MyActivity {
    // GENERAL CONTROL VARIABLES
    private Event event;
    private static float screenWidth, screenHeight;
    final Context context = this;
    private static final String PARENT_DIR = "..";
    private static final String EVENT_EXT = ".evn";
    //private File currentPath;
    Button homeButton, eventButton, statsButton;
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm");
    //int Event.rank, Event.experience, expMissing;
    // LAYOUT VARIABLES
    LinearLayout homeLay, newEventLay, tokenEventLay, tokenConfigLay, matchEventLay, matchConfigLay, statsLay, pickDateLay;
    LinearLayout currentLay;
    // HOME LAYOUT ELEMENTS
    Button newEventButton, currEventButton, currStatsButton, oldEventButton;
    // STATS LAYOUT ELEMENTS
    EditText rankEd, expEd;
    Button saveRankButton;
    // NEW EVENT LAYOUT ELEMENTS
    EditText newEventNameEd, newEventGoalEd;
    TextView txtDate;
    Spinner eventTypeSP;
    Button pickDateButton, createEventButton;
    // Termporary variables...
    //String eventName, date;
    //File eventFile;
    //int eventGoal, eventType, currentPoints, eventMode;
    // PICK DATE LAYOUT ELEMENTS
    DatePicker datePicker;
    Button pdOkButton;
    // TOKEN EVENT LAYOUT ELEMENTS
    Button cfgTokenButton, chgDateTkButton;
    Spinner evModeTokenSP, NEmodeSP;
    EditText currTokensEd, currPointsTkEd, goalTokenEd;
    TextView neSongsTokenTx,songsDayTokenTx, rankUpTx, finishDateTokenTx;
    // SCORE MATCH EVENT LAYOUT ELEMENTS
    Button cfgMatchButton,chgDateMatchButton;
    Spinner matchSp;
    EditText currPointsMatchEd, goalMatchEd;
    TextView nSongsMatchTx, songsDayMatchTx, finishDateMatchTx;
    // Temporary variables...
    //int currentTokens, nonEventMode;
    //int[] configValues;
    //int tokensGained, price, pointsGained, event.nSongs, event.songsDay, averageMatch;
    int pick;
    //int[] eventValues;
    // TOKEN EVENT CONFIG LAYOUT ELEMENTS
    EditText[] tokenValuesTable, matchValuesTable;
    Button saveCfgTokenButton;
    // TOKEN EVENT CONFIG LAYOUT ELEMENTS
    //EditText matchValueEd;
    Button saveCfgMatchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainscreen_layout);
        //spN =(Spinner) findViewById(R.id.spinnerN);
        //ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.mode, android.R.layout.simple_spinner_item);
        //spN.setAdapter(adapter);

        //========================================
        // GENERAL ELEMENTS
        //=========================================00
        homeButton = (Button) findViewById(R.id.homeBtn);
        eventButton = (Button) findViewById(R.id.eventBtn);
        statsButton = (Button) findViewById(R.id.statsBtn);

        //=======================================
        // LAYOUT VARIABLES
        //=======================================
        homeLay =(LinearLayout) findViewById(R.id.HOME);
        newEventLay =(LinearLayout) findViewById(R.id.NEW_EVENT);
        tokenEventLay =(LinearLayout) findViewById(R.id.TOKEN_EVENT);
        tokenConfigLay =(LinearLayout) findViewById(R.id.TEV_CONFIG);
        matchEventLay =(LinearLayout) findViewById(R.id.MATCH_EVENT);
        matchConfigLay =(LinearLayout) findViewById(R.id.MATCH_CONFIG);
        statsLay =(LinearLayout) findViewById(R.id.STATS);
        pickDateLay =(LinearLayout) findViewById(R.id.PICK_DATE);

        //=====================================
        // HOME LAYOUT ELEMENTS
        //=======================================
        newEventButton = (Button) findViewById(R.id.newEventBtn);
        currEventButton = (Button) findViewById(R.id.currEventBtn);
        currStatsButton = (Button) findViewById(R.id.currStatsBtn);
        oldEventButton = (Button) findViewById(R.id.oldEventBtn);

        //===================================================
        // STATS LAYOUT ELEMENTS
        //=================================================
        rankEd = (EditText) findViewById(R.id.rankEdit);
        expEd = (EditText) findViewById(R.id.experienceEdit);
        saveRankButton = (Button) findViewById(R.id.saveRankBtn);

        //========================================
        // NEW EVENT LAYOUT ELEMENTS
        //=========================================
        newEventNameEd = (EditText) findViewById(R.id.eventNameEd);
        eventTypeSP = (Spinner) findViewById(R.id.spEventType);
        pickDateButton = (Button) findViewById(R.id.pickDateBtn);
        createEventButton = (Button) findViewById(R.id.createEventBtn);
        newEventGoalEd = (EditText) findViewById(R.id.newEventGoal);
        txtDate = (TextView) findViewById(R.id.txDate);

        //====================================
        // PICK DATE LAYOUT ELEMENTS
        //======================================
        datePicker = (DatePicker) findViewById(R.id.datePicker1);
        pdOkButton = (Button) findViewById(R.id.pickDateOkBtn);

        //===================================================
        // TOKEN EVENT LAYOUT ELEMENTS
        //=================================================
        cfgTokenButton = (Button) findViewById(R.id.configTokenBtn);
        evModeTokenSP = (Spinner) findViewById(R.id.spEvTokens);
        NEmodeSP = (Spinner) findViewById(R.id.spNonEv);
        currTokensEd = (EditText) findViewById(R.id.currentTokens);
        currPointsTkEd = (EditText) findViewById(R.id.currentPointsTEv);
        goalTokenEd = (EditText) findViewById(R.id.goalTEV);
        finishDateTokenTx = (TextView) findViewById(R.id.dateFinishTEv);
        neSongsTokenTx = (TextView) findViewById(R.id.nSongsTEV);
        songsDayTokenTx = (TextView) findViewById(R.id.songsDayTEV);
        chgDateTkButton = (Button) findViewById(R.id.changeDateTEvBtn);
        rankUpTx = (TextView) findViewById(R.id.rankUpTx);

        //===================================================
        // SCORE MATCH EVENT LAYOUT ELEMENTS
        //=================================================
        cfgMatchButton = (Button) findViewById(R.id.configMatch);
        matchSp = (Spinner) findViewById(R.id.spinnerMatch);
        currPointsMatchEd = (EditText) findViewById(R.id.currentPointsMatch);
        goalMatchEd = (EditText) findViewById(R.id.goalMatch);
        finishDateMatchTx = (TextView) findViewById(R.id.dateFinishMatch);
        nSongsMatchTx = (TextView) findViewById(R.id.nSongsMatch);
        songsDayMatchTx = (TextView) findViewById(R.id.songsDayMatch);
        chgDateMatchButton = (Button) findViewById(R.id.changeDateMatchBtn);
        //===================================================
        // TOKEN EVENT CONFIG LAYOUT ELEMENTS
        //=================================================
        tokenValuesTable = new EditText[12];
        matchValuesTable = new EditText[4];
        tokenValuesTable[0] = (EditText) findViewById(R.id.ed0);
        tokenValuesTable[1] = (EditText) findViewById(R.id.ed1);
        tokenValuesTable[2] = (EditText) findViewById(R.id.ed2);
        tokenValuesTable[3] = (EditText) findViewById(R.id.ed3);
        tokenValuesTable[4] = (EditText) findViewById(R.id.ed4);
        tokenValuesTable[5] = (EditText) findViewById(R.id.ed5);
        tokenValuesTable[6] = (EditText) findViewById(R.id.ed6);
        tokenValuesTable[7] = (EditText) findViewById(R.id.ed7);
        tokenValuesTable[8] = (EditText) findViewById(R.id.ed8);
        tokenValuesTable[9] = (EditText) findViewById(R.id.ed9);
        tokenValuesTable[10] = (EditText) findViewById(R.id.ed10);
        tokenValuesTable[11] = (EditText) findViewById(R.id.ed11);
        saveCfgTokenButton = (Button) findViewById(R.id.savecfgTEvBtn);

        //===================================================
        // SCORE MATCH EVENT CONFIG LAYOUT ELEMENTS
        //=================================================
       // matchValueEd = (EditText) findViewById(R.id.averageMatch);
        saveCfgMatchButton = (Button) findViewById(R.id.saveCfgMatchBtn);

        // SCREEN THINGS...
        float density = getResources().getDisplayMetrics().density;
        screenWidth = getResources().getDisplayMetrics().widthPixels / density;
        screenHeight = getResources().getDisplayMetrics().widthPixels / density;
        // HERE WE WILL DO SOMETHING TO ADAPT TEXT SIZE AND OTHER SIZES TO SCREEN WIDTH.
        // WO WE NEED A NUMBER AND ADAPT DINAMICALLY THE SCREEN OPERATING WITH THIS NUMBER, USING IT IN ONCREATE, TO SET THE TEXT SIZE BASICALLY, SINCE THE REST OF THE THINGS ARE "WRAP_CONTENT".

        //==============================================================================================================================
        //==============================================================================================================================
        initialize();
        //=======================================================================
        // GENERAL ELEMENTS ACTIONS
        //====================================================================

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), getFilesDir().getParentFile().toString(), Toast.LENGTH_LONG).show();
                openLay(homeLay);
                //if((eventName != null) && (!eventName.equals(""))) {
                //    currEventButton.setText(eventName);
               // }
            }
        });

        eventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // HERE WE MUST LOAD ALL THE CURRENT EVENT VALUES TO THIS SCREEN ELEMENTS (MAKE A LOWER FUNCTION SHARED WITH ALL CASES)
                showEventLay();
            }

        });

        statsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLay(statsLay);
                rankEd.setText("" + Event.rank);
                expEd.setText("" + Event.experience);
            }
        });

        //=====================================================================================
        // HOME LAYOUT ELEMENTS ACTIONS
        //=======================================================================================
        /**
         * When click on NEW EVENT button in home layout, current event (if any) is saved, then it calls openLay().
         */
        newEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // SAVE THE CURRENT EVENT IF ANY
                event.saveEvent();
                openLay(newEventLay);
            }
        });

        currStatsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLay(statsLay);
                rankEd.setText("" + Event.rank);
                expEd.setText("" + Event.experience);
            }
        });

        currEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEventLay();
            }
        });

        oldEventButton.setOnClickListener(new ShowFiles(getFilesDir()));
        //=====================================================================================
        // STATS LAYOUT ELEMENTS ACTIONS
        //=======================================================================================
        /**
         * When click on SAVE VALUES in stats layout, if entered values pass some checks, then are saved in preferences file.
         */
        saveRankButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = rankEd.getText().toString();
                boolean b = false;
                if (str.matches("\\d+")) {
                    Event.rank = Integer.parseInt(str);
                    pushInt("Event.rank", Event.rank);
                    b = true;
                } else {
                    Toast.makeText(getApplicationContext(),"Select a better number for your Event.rank" , Toast.LENGTH_SHORT).show();
                }

                str = expEd.getText().toString();
                if (str.matches("\\d+")) {
                    Event.experience = Integer.parseInt(str);
                    pushInt("Event.experience", Event.experience);
                    if(b) {
                        Toast.makeText(getApplicationContext(),"Values saved" , Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(),"Select a better number for your Event.experience" , Toast.LENGTH_SHORT).show();
                }
            }
        });

        //===============================================================================
        // NEW EVENT LAYOUT ELEMENTS ACTIONS
        //========================================================================

        pickDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pick = 0; // DETERMINES THAT WE ARE OPENING PICK DATE LAYOUT FROM  NEW EVENT LAYOUT
                openLay(pickDateLay);
            }
        });

        /**
         * Assigns event values and saves it initially creating the event file.
         */
        createEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //eventFile = getFilesDir();
                event = Event.createEvent(newEventNameEd.getText().toString(), eventTypeSP.getSelectedItemPosition(), Integer.parseInt(newEventGoalEd.getText().toString()), txtDate.getText().toString(), getApplicationContext());
                /*eventName = newEventNameEd.getText().toString();
                eventType = eventTypeSP.getSelectedItemPosition();
                eventGoal = Integer.parseInt(newEventGoalEd.getText().toString());
                date = txtDate.getText().toString();
                String s = "";
                if(eventType == 0) {
                    s = eventType + " " + eventMode + " " + nonEventMode + " " + currentTokens + " " + currentPoints + " " + eventGoal + "\n" + date;
                }
                else {
                    s = eventType + " " + eventMode + currentPoints + " " + eventGoal + "\n" + date;
                }
                String s1 = getFilesDir().toString() + "/" + eventName + EVENT_EXT;
                Toast.makeText(getApplicationContext(), s1, Toast.LENGTH_SHORT).show();
                eventFile = new File(getFilesDir().toString() + "/" + eventName + EVENT_EXT);
                writeToFile(s, eventFile);
                //writeToFile(eventName, "current_event.txt");
                pushString("current_event_file", eventFile.toString());*/
                if(event != null) {
                    currEventButton.setText(event.eventName);
                }
                //readEventValues(eventFile);
                showEventLay();
            }
        });

        //===============================================================
        // PICK DATE LAYOUT ELEMENTS ACTIONS
        //================================================================

        pdOkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GregorianCalendar gc = new GregorianCalendar(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth());

                TimeZone timeZone = TimeZone.getDefault();
                int desfase = timeZone.getRawOffset();
                Calendar dateFinish = Calendar.getInstance();
                dateFinish.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(), 8,0,0);
                long milisec = dateFinish.getTimeInMillis() + desfase;
                dateFinish.setTimeInMillis(milisec);


                Calendar today = Calendar.getInstance();
                if(today.getTimeInMillis() > milisec) {
                    Toast.makeText(getApplicationContext(),"Select a later date" , Toast.LENGTH_SHORT).show();
                }
                else {
                    Date d = dateFinish.getTime();
                    String s = formatter.format(d);
                    if(pick == 0) { // PICK DATE LAY WAS OPENED FROM NEW EVENT LAY
                        openLay(newEventLay);
                        //returnValuesToNew();
                        //Toast.makeText(getApplicationContext(),"Waiting..." , Toast.LENGTH_SHORT).show();
                        //DateFormat dateFormat = DateFormat.getInstance();
                        //dateFormat.format()
                        //txtDate.setText(dateFormat.format(date));
                        txtDate.setText(s);
                        //newEventNameEd.setText(eventName);
                        //newEventGoalEd.setText(eventGoal);
                        //eventTypeSP.setSelection(eventType);
                        //date = "" + dateFinish.get(Calendar.DATE);
                    }
                    else if(pick == 1) { // PICK DATE LAY WAS OPENED FROM EVENT LAY
                        showEventLay();
                        event.endDate = s;
                        event.calculateSongs();
                        //calcTokenSongs();
                        if(event.eventType == 0) {
                            neSongsTokenTx.setText("" + event.nSongs);
                            songsDayTokenTx.setText("" + event.songsDay);
                            event.rankUp(getApplicationContext());
                            finishDateTokenTx.setText(event.endDate);
                        }
                        else {
                            nSongsMatchTx.setText("" + event.nSongs);
                            songsDayMatchTx.setText("" + event.songsDay);
                            finishDateMatchTx.setText(event.endDate);
                        }
                    }
                }

            }
        });

        //===============================================================================
        // TOKEN EVENT LAYOUT ELEMENTS ACTIONS
        //========================================================================

        cfgTokenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLay(tokenConfigLay);
                for(int i = 0; i < 12; i ++) {
                    tokenValuesTable[i].setText(Integer.toString(Event.configValues[i]));
                }
            }
        });

        evModeTokenSP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //event = (TokenEvent) event;
                /*event.eventMode = position;
                event.price = configValues[3*position+1];
                event.pointsGained = configValues[3*position+2];
                calcTokenSongs();*/
                event.updateValues(position,getApplicationContext());
                neSongsTokenTx.setText("" + event.nSongs);
                songsDayTokenTx.setText("" + event.songsDay);
                //event.rankUp(getApplicationContext());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        NEmodeSP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                /*nonEventMode = position;
                tokensGained = configValues[3*position];
                calcTokenSongs();*/
                event.updateNEValues(position, getApplicationContext());
                neSongsTokenTx.setText("" + event.nSongs);
                songsDayTokenTx.setText("" + event.songsDay);
                //event.rankUp(getApplicationContext());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //currTokensEd.setOn

        //currTokensEd.addTextChangedListener(new MiTextWatcher(new Integer(currentTokens)));
        currTokensEd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String str = s.toString();
                if(str.matches("\\d+")) {
                    //Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
                    TokenEvent tkevent = (TokenEvent) event;
                    tkevent.currentTokens = Integer.parseInt(str);
                    //Toast.makeText(getApplicationContext(), "" + currentTokens + " " + currentPoints + " " + eventGoal, Toast.LENGTH_SHORT).show();
                    tkevent.calculateSongs();
                    neSongsTokenTx.setText("" + event.nSongs);
                    songsDayTokenTx.setText("" + event.songsDay);
                    tkevent.rankUp(getApplicationContext());
                }
                else {
                    neSongsTokenTx.setText("0");
                    songsDayTokenTx.setText("0");
                }
            }
        });

        /*currTokensEd.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //You can identify which key pressed buy checking keyCode value with KeyEvent.KEYCODE_
                if(keyCode != KeyEvent.KEYCODE_DEL){
                    currentTokens = Integer.parseInt(currTokensEd.getText().toString());
                    event.nSongs = calcTokenSongs();
                    neSongsTokenTx.setText("" + event.nSongs);
                }
                return false;
            }
        });*/

        /*currTokensEd.setOnEditorActionListener(new TextView.OnEditorActionListener() {////////////////////////////////////////////////////////////////////////////////////////////////////////
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((actionId == EditorInfo.IME_ACTION_DONE) || (actionId == EditorInfo.IME_ACTION_NEXT)) {
                    currentTokens = Integer.parseInt(currTokensEd.getText().toString());
                    event.nSongs = calcTokenSongs();
                    neSongsTokenTx.setText("" + event.nSongs);
                }
                return false;
            }
        });*/

        currPointsTkEd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String str = s.toString();
                if(str.matches("\\d+")) {
                    //Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
                    //TokenEvent tkevent = (TokenEvent) event;
                    event.currentPoints = Integer.parseInt(str);
                    //Toast.makeText(getApplicationContext(), "" + currentTokens + " " + currentPoints + " " + eventGoal, Toast.LENGTH_SHORT).show();
                    event.calculateSongs();
                    neSongsTokenTx.setText("" + event.nSongs);
                    songsDayTokenTx.setText("" + event.songsDay);
                    event.rankUp(getApplicationContext());
                }
                else {
                    neSongsTokenTx.setText("0");
                    songsDayTokenTx.setText("0");
                }
            }
        });

        /*currPointsTkEd.setOnEditorActionListener(new TextView.OnEditorActionListener() {////////////////////////////////////////////////////////////////////////////////////////////////////////
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((actionId == EditorInfo.IME_ACTION_DONE) || (actionId == EditorInfo.IME_ACTION_NEXT)) {
                    currentPoints = Integer.parseInt(currPointsTkEd.getText().toString());
                    event.nSongs = calcTokenSongs();
                    neSongsTokenTx.setText("" + event.nSongs);
                }
                return false;
            }
        });*/

        goalTokenEd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String str = s.toString();
                if(str.matches("\\d+")) {
                    //Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
                    event.eventGoal = Integer.parseInt(str);
                    //Toast.makeText(getApplicationContext(), "" + currentTokens + " " + currentPoints + " " + eventGoal, Toast.LENGTH_SHORT).show();
                    event.calculateSongs();
                    //Toast.makeText(getApplicationContext(), "" + currentTokens + " " + currentPoints + " " + eventGoal, Toast.LENGTH_SHORT).show();
                    neSongsTokenTx.setText("" + event.nSongs);
                    songsDayTokenTx.setText("" + event.songsDay);
                    event.rankUp(getApplicationContext());
                }
                else {
                    neSongsTokenTx.setText("0");
                    songsDayTokenTx.setText("0");
                }
            }
        });

        /*goalTokenEd.setOnEditorActionListener(new TextView.OnEditorActionListener() {////////////////////////////////////////////////////////////////////////////////////////////////////////
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((actionId == EditorInfo.IME_ACTION_DONE) || (actionId == EditorInfo.IME_ACTION_NEXT)) {
                    eventGoal = Integer.parseInt(goalTokenEd.getText().toString());
                    event.nSongs = calcTokenSongs();
                    neSongsTokenTx.setText("" + event.nSongs);
                }
                return false;
            }
        });*/

        class DateListen implements View.OnClickListener {
            @Override
            public void onClick(View v) {
                pick = 1;
                try {
                    Date d = formatter.parse(event.endDate);
                    Calendar c = Calendar.getInstance();
                    c.setTime(d);
                    datePicker.updateDate(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                openLay(pickDateLay);

            }
        }

        chgDateTkButton.setOnClickListener(new DateListen());

        //===============================================================================
        // SCORE MATCH EVENT LAYOUT ELEMENTS ACTIONS
        //========================================================================

        chgDateMatchButton.setOnClickListener(new DateListen());

        cfgMatchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLay(matchConfigLay);
                for(int i = 0; i < 3; i ++) {
                    tokenValuesTable[i].setText(new Integer(Event.configValues[i]).toString());
                }
            }
        });

        matchSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                /*eventMode = position;
                //price = configValues[3*position+1];
                pointsGained = configValues[3*position+2];
                calcTokenSongs();*/
                event.updateValues(position, getApplicationContext());
                neSongsTokenTx.setText("" + event.nSongs);
                songsDayTokenTx.setText("" + event.songsDay);
                //event.rankUp(getApplicationContext());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        currPointsMatchEd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String str = s.toString();
                if(str.matches("\\d+")) {
                    //Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
                    //TokenEvent tkevent = (TokenEvent) event;
                    event.currentPoints = Integer.parseInt(str);
                    //Toast.makeText(getApplicationContext(), "" + currentTokens + " " + currentPoints + " " + eventGoal, Toast.LENGTH_SHORT).show();
                    event.calculateSongs();
                    neSongsTokenTx.setText("" + event.nSongs);
                    songsDayTokenTx.setText("" + event.songsDay);
                    event.rankUp(getApplicationContext());
                }
                else {
                    neSongsTokenTx.setText("0");
                    songsDayTokenTx.setText("0");
                }
            }
        });

        /*currPointsTkEd.setOnEditorActionListener(new TextView.OnEditorActionListener() {////////////////////////////////////////////////////////////////////////////////////////////////////////
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((actionId == EditorInfo.IME_ACTION_DONE) || (actionId == EditorInfo.IME_ACTION_NEXT)) {
                    currentPoints = Integer.parseInt(currPointsTkEd.getText().toString());
                    event.nSongs = calcTokenSongs();
                    neSongsTokenTx.setText("" + event.nSongs);
                }
                return false;
            }
        });*/

        goalMatchEd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String str = s.toString();
                if(str.matches("\\d+")) {
                    //Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
                    event.eventGoal = Integer.parseInt(str);
                    //Toast.makeText(getApplicationContext(), "" + currentTokens + " " + currentPoints + " " + eventGoal, Toast.LENGTH_SHORT).show();
                    event.calculateSongs();
                    //Toast.makeText(getApplicationContext(), "" + currentTokens + " " + currentPoints + " " + eventGoal, Toast.LENGTH_SHORT).show();
                    neSongsTokenTx.setText("" + event.nSongs);
                    songsDayTokenTx.setText("" + event.songsDay);
                    event.rankUp(getApplicationContext());
                }
                else {
                    neSongsTokenTx.setText("0");
                    songsDayTokenTx.setText("0");
                }
            }
        });

        //===============================================================================
        // TOKEN EVENT CONFIG LAYOUT ELEMENTS ACTIONS
        //========================================================================

        saveCfgTokenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder s = new StringBuilder();
                for(int i = 0; i < Event.configValues.length; i ++) {
                    Event.configValues[i] = Integer.parseInt(tokenValuesTable[i].getText().toString());
                    s.append(Event.configValues[i]); s.append(" ");
                }
                writeToFile(s.toString(), getString(R.string.tokenEventConfigFile));
                event.updateCfg();
//                tokensGained = configValues[3*nonEventMode];
//                price = configValues[3*eventMode+1];
//                pointsGained = configValues[3*eventMode+2];
                showEventLay();
            }
        });

        //===============================================================================
        // MATCH EVENT CONFIG LAYOUT ELEMENTS ACTIONS
        //========================================================================

        saveCfgTokenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder s = new StringBuilder();
                for(int i = 0; i < Event.configValues.length; i ++) {
                    Event.configValues[i] = Integer.parseInt(tokenValuesTable[i].getText().toString());
                    s.append(Event.configValues[i]); s.append(" ");
                }
                writeToFile(s.toString(), getString(R.string.tokenEventConfigFile));
                event.updateCfg();
//                pointsGained = configValues[eventMode];
                showEventLay();
            }
        });

    }
//=======================================================================================================================================================================
//                            FUNCTION DECLARATIONS
//=======================================================================================================================================================================
    /**
     * This function simply changes the layout (LinearLayout) which is set to visible.
     * @param lay The layout variable which is going to become visible
     */
    public void openLay (LinearLayout lay) {
        if(currentLay == null) {
            currentLay = homeLay;
        }
        currentLay.setVisibility(View.GONE);
        lay.setVisibility(View.VISIBLE);
        currentLay = lay;
        //Toast.makeText(getApplicationContext(), "cambio de pantalla hecho" , Toast.LENGTH_SHORT).show();
    }

    /**
     * Changes the visible layout to that of the current event. Values are retrieved from the current event's file (*.evn)
     */
    public void showEventLay() {
        if(event == null) {
            String s = pullString("current_event_file");
            if (s.equals("")) {
                openLay(newEventLay);
                return;
            }
            event = Event.loadEvent(new File(s), this);
        }

        //eventType =

        // else...
        //readEventValues(eventName + ".txt");

        if(event.eventType == 0) {
            TokenEvent tk = (TokenEvent) event;
            evModeTokenSP.setSelection(tk.eventMode);
            NEmodeSP.setSelection(tk.nonEventMode);
            currTokensEd.setText("" + tk.currentTokens);
            currPointsTkEd.setText("" + tk.currentPoints);
            goalTokenEd.setText("" + tk.eventGoal);
            finishDateTokenTx.setText(tk.endDate + "  ");
            openLay(tokenEventLay);
        }
        else {
            matchSp.setSelection(event.eventMode);
            currPointsMatchEd.setText("" + event.currentPoints);
            goalMatchEd.setText("" + event.eventGoal);
            finishDateMatchTx.setText(event.endDate + "  ");
            openLay(tokenEventLay);
            openLay(matchEventLay);
        }
    }

    /**
     * Returns a line from a file, and if the file does not exists, then returns the default text given
     * @param file The file from which we want to read the line
     * @param defaultText The text we want the function to return in case of the file not exists
     * @return
     *//*
    private String readLineValuesWithDefault(String file, String defaultText) {
        String ret = "";

        try {
            InputStream inputStream = openFileInput(file);

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                ret = bufferedReader.readLine();
                inputStream.close();
            }
        }
        catch (FileNotFoundException e) {
            //Log.e("login activity", "File not found: " + e.toString());
            writeToFile(defaultText, file);
            ret = defaultText;
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }
        return ret;
    }

    *//**
     * Reads all the data of an event from a given event file (*.evn)
     * @param file The event file from which we want to read the data
     *//*
    private  void readEventValues(String file) {
        try {
            InputStream inputStream = openFileInput(file);

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String s = bufferedReader.readLine();
                Toast.makeText(getApplicationContext(), "valores recuperados: " + s , Toast.LENGTH_SHORT).show();
                String[] ss = s.split(" ");
                eventName = file.substring(0, file.lastIndexOf("."));
                eventType = Integer.parseInt(ss[0]);
                eventMode = Integer.parseInt(ss[1]);
                if(eventType == 0) {
                    //eventType + " " + eventMode + " " + nonEventMode + " " + currentTokens + " " + currentPoints + " " + eventGoal + " " + date;
                    nonEventMode = Integer.parseInt(ss[2]);
                    currentTokens = Integer.parseInt(ss[3]);
                    currentPoints = Integer.parseInt(ss[4]);
                    eventGoal = Integer.parseInt(ss[5]);
                }
                date = bufferedReader.readLine();
                Toast.makeText(getApplicationContext(), "retrieved date: " + date, Toast.LENGTH_SHORT).show();
                inputStream.close();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }
    }

    *//**
     * Reads all the data of an event from a given event file (*.evn)
     * @param file The event file from which we want to read the data
     *//*
    private void readEventValues(File file) {
        try {
            FileInputStream fIn = new FileInputStream(file);
            if ( fIn != null ) {
                BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(fIn));
                String s = bufferedReader.readLine();
                Toast.makeText(getApplicationContext(), "valores recuperados: " + s , Toast.LENGTH_SHORT).show();
                String[] ss = s.split(" ");
                String fname = file.getName();
                eventName = fname.substring(0, fname.lastIndexOf("."));
                eventType = Integer.parseInt(ss[0]);
                eventMode = Integer.parseInt(ss[1]);
                if(eventType == 0) {
                    //eventType + " " + eventMode + " " + nonEventMode + " " + currentTokens + " " + currentPoints + " " + eventGoal + " " + date;
                    nonEventMode = Integer.parseInt(ss[2]);
                    currentTokens = Integer.parseInt(ss[3]);
                    currentPoints = Integer.parseInt(ss[4]);
                    eventGoal = Integer.parseInt(ss[5]);
                }
                date = bufferedReader.readLine();
                Toast.makeText(getApplicationContext(), "retrieved date: " + date, Toast.LENGTH_SHORT).show();
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

    *//**
     * Calculates the number of non-event songs we have to play in order to achieve our goal in a token collection event, and the number of it per each remaining day of the event.
     * Then assigns that values to variables event.nSongs and event.songsDay, respectively.
      *//*
    private void calcTokenSongs() {
        double res = Math.ceil((double)((eventGoal - currentPoints) * price - currentTokens * pointsGained)/(tokensGained * (pointsGained + price)));
        if(res > 0.0) {
            event.nSongs = (int) res;
        }
        else {
            event.nSongs = 0;
        }

        Date d = null;
        *//*Toast.makeText(getApplicationContext(), date, Toast.LENGTH_SHORT).show();
        Toast.makeText(getApplicationContext(), formatter.toPattern(), Toast.LENGTH_SHORT).show();
        Date da = Calendar.getInstance().getTime();

        Toast.makeText(getApplicationContext(), formatter.format(da), Toast.LENGTH_SHORT).show();*//*
        try {
            //Toast.makeText(getApplicationContext(), "fecha : " + date, Toast.LENGTH_SHORT).show();
            d = formatter.parse(date);//catch exception
            //Toast.makeText(getApplicationContext(), "fecha : " + date, Toast.LENGTH_SHORT).show();
            //Toast.makeText(getApplicationContext(), "fecha obtenida: " + formatter.format(d), Toast.LENGTH_SHORT).show();
        } catch (ParseException e) {
            Toast.makeText(getApplicationContext(), "mierda", Toast.LENGTH_SHORT).show();

            e.printStackTrace();
        }
        Calendar endDate = Calendar.getInstance();
        endDate.setTime(d);
        Calendar today = Calendar.getInstance();
        long diff = endDate.getTimeInMillis() - today.getTimeInMillis();
        int days = (int) (diff / (24 * 60 * 60 * 1000));
        event.songsDay = (int) Math.ceil((double)event.nSongs / days);
    }

    *//**
     * Finds out whether we can or not Event.rank up playing the event song in a token collection event, and the number of event songs in different modes we should play in order to do that.
     * Then, it sets the result as rankUpTx.text in token collection event screen.
     *//*
    private void rankUp() {
        int rup;
        if(Event.rank > 0) {
            if(Event.rank < 34) {
                String[] ss = getString(R.string.rankTable).split(" ");
                rup = Integer.parseInt(ss[Event.rank - 1]);
            }
            else {
                rup = (int) Math.ceil(34.35*Event.rank-551);
            }
            expMissing = rup - Event.experience;
            int se = currentTokens / configValues[1];
            int sn = currentTokens / configValues[4];
            int sh = currentTokens / configValues[7];
            int sex = currentTokens / configValues[10];

            int ne = (int) Math.ceil(expMissing / 12.0);
            int nn = (int) Math.ceil(expMissing / 26.0);
            int nh = (int) Math.ceil(expMissing / 46.0);
            int nex = (int) Math.ceil(expMissing / 83.0);
            if((se >= ne) || (sn >= nn) || (sh >= nh) || (sex >= nex)) {
                String outText = "You can RANK UP playing the event!!\n" +
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
                rankUpTx.setText(outText);
            }

        }
        else {
            rankUpTx.setText("");
        }
    }*/

    /**
     * Simply shows the current event data in a toast
     */
    @Override
    protected void onStop() {
        super.onStop();
        Toast.makeText(getApplicationContext(), "joder parado", Toast.LENGTH_SHORT).show();
        //String s = eventType + " " + eventMode + " " + nonEventMode + " " + currentTokens + " " + currentPoints + " " + eventGoal + "\n" + date;
        //Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
        /*if((eventName != null) && (!eventName.equals(""))) {
            String s = eventType + " " + eventMode + " " + nonEventMode + " " + currentTokens + " " + currentPoints + " " + eventGoal + "\n" + date;
            writeToFile(s, eventName + ".txt");
        }
        super.onPause();*/
        //sacaValores();
    }

    /**
     * Saves the current event in its file and show its data in a toast
     */
    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(getApplicationContext(), "joder pausa", Toast.LENGTH_SHORT).show();
        //String s = eventType + " " + eventMode + " " + nonEventMode + " " + currentTokens + " " + currentPoints + " " + eventGoal + "\n" + date;
        //Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
        /*if ((eventName != null) && (!eventName.equals(""))) {
            String s1 = eventType + " " + eventMode + " " + nonEventMode + " " + currentTokens + " " + currentPoints + " " + eventGoal + "\n" + date;
            writeToFile(s1, eventFile);
        }*/
        event.saveEvent();

        /*if((currentLay != null) && (currentLay.equals(tokenEventLay))) {
            pushString("currentLay", "tokenEvent");
        }*/
        /*if((eventName != null) && (!eventName.equals(""))) {
            String s = eventType + " " + eventMode + " " + nonEventMode + " " + currentTokens + " " + currentPoints + " " + eventGoal + "\n" + date;
            writeToFile(s, eventName + ".txt");
        }
        super.onPause();*/
        //sacaValores();
    }

    /**
     * Loads the LAST EVENT data. This event name is saved in preferences file with the key "current_event".
     * So if we have been seeing an old event in the last run of the apk, we will see the last event anyway.
     */
    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(getApplicationContext(), "joder fin de pausa" , Toast.LENGTH_SHORT).show();

        if(Event.rank == 0) {
            Event.rank = pullInt("Event.rank");
            Event.experience = pullInt("Event.experience");
        }
// BUUUUUUUUUUUUUUUUUUUUFFFF MADRE MÍAAAAAAAAAA LOS CAMBIOS QUE HAY QUE HACER AKI TODAVIA....
        // CREAR FUNCIONES PARA INITIALIZE Y TODAS ESTAS COSAS, PERO CREARLAS EN EVENT!!!
        //openLay(newEventLay);
        if(event == null) {
            String s = pullString("current_event_file");
            if (!s.equals("")) {
                event = Event.loadEvent(new File(s), this);
                if(event != null) {
                    currEventButton.setText(event.eventName);
                }
                openLay(homeLay);
            }
        }
        //else {
         //   readEventValues(eventFile);
        //}
    }

    /**
     * Initializes variables, being called inside "onCreate"
     */
    private void initialize() {

        String s = pullString("current_event_file");
        if (!s.equals("")) {
            event = Event.loadEvent(new File(s), this);
            if(event != null) {
                currEventButton.setText(event.eventName);
            }
        }
    }

    /**
     * An example for an alert dialog with "yes" or "ok" and "no" or "cancel" buttons
     */
    void pepe() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setPositiveButton("ay",
                new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "esto", Toast.LENGTH_SHORT).show();
            }
        });
        alertDialogBuilder.setNegativeButton("uy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "aquello", Toast.LENGTH_SHORT).show();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.setTitle("FIESTA NACIONAL");
        alertDialog.setMessage("La fiesta empieza hoy vamos todos a chingar ehhh");
        alertDialog.setCancelable(true);
        alertDialog.show();
    }


    /**
     * An implementation for View.OnClickListener. This class' aim is to show a custom dialog with a list of event files and subdirectories for the user to choose one to open. This one is meant
     * for files in any place inside the phone, like the memory card, etc.
     * IT DOES NOT WORK PROPERLY SO FAR
     */
    class ShowFiles implements View.OnClickListener {
        final Dialog dialog = new Dialog(context);
        LinearLayout scrollView;
        File currPath, selectedFile;
        TextView selectedView;

        /**
         * Cnostructor for this class. It creates the dialog and assigns variables in its layout
         * @param path The directory from which we start to browse our event
         */
        public ShowFiles(File path) {
            super();
            this.currPath = path;

            dialog.setContentView(R.layout.open_file_layout);
            dialog.setTitle(currPath.getPath());
            scrollView = (LinearLayout) dialog.findViewById(R.id.scrollView);
            Button openFileDialogButton = (Button) dialog.findViewById(R.id.openFileDialogBtn);
            Button cancelOpenButton = (Button) dialog.findViewById(R.id.cancelOpengBtn);
            final Button changeSDButton = (Button) dialog.findViewById(R.id.changeSDBtn);

            openFileDialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(), selectedFile.getName() + " " + selectedFile.toString(), Toast.LENGTH_SHORT).show();
                    event = Event.loadEvent(selectedFile, getApplicationContext());
                    if(event != null) {
                        currEventButton.setText(event.eventName);
                    }
                    dialog.dismiss();
                }
            });

            cancelOpenButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            changeSDButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (changeSDButton.getText().toString().equalsIgnoreCase("SD card")) {
                        changeSDButton.setText("App dir");
                        currPath = Environment.getExternalStorageDirectory();
                        showFiles(currPath);
                    }
                    else {
                        changeSDButton.setText("SD card");
                        currPath = getFilesDir();
                        showFiles(currPath);
                    }
                }
            });
        }

        /**
         * Shows a list of event file names and directories inside the current path.
         */
        public void showFiles(final File path) {
            dialog.setTitle(path.getPath());
            Toast.makeText(getApplicationContext(), "path: " + path.toString() + "\nparent: " + path.getParent(), Toast.LENGTH_SHORT).show();
            ArrayList<File> fileList = getFiles(path, ".evn", getApplicationContext());
            selectedFile = null;
            scrollView.removeAllViews();
            if(selectedView != null) {
                selectedView.setBackgroundColor(Color.TRANSPARENT);
                selectedView = null;
            }

            for (int i = 0; i < fileList.size(); i++) {
                final TextView textView = new TextView(dialog.getContext());
                final File f = fileList.get(i);
                final String s = f.getName();

                textView.setPadding(5, 5, 5, 5);

                //System.out.println(fileList.get(i).getName());

                textView.setText(s);
                if(f.isDirectory()) {
                    textView.setTextColor(Color.parseColor("#FF0000"));
                }

                scrollView.addView(textView);

                textView.setOnClickListener(new View.OnClickListener() {
                    /**
                     * Click on each filename or directory:
                     * - If we click on a filename, it is selected.
                     * - If we click on ".." (parent directory), we go to the parent directory
                     * - If we click on a directory, we go to the files and directories inside it
                     * DOES NOT WORK PROPERLY SO FAR
                     * @param v
                     */
                    @Override
                    public void onClick(View v) {

                        if(selectedView != null) {
                            selectedView.setBackgroundColor(Color.TRANSPARENT);
                        }
                        selectedView = textView;
                        textView.setBackgroundColor(Color.BLUE);
                        if(s.equals(PARENT_DIR)) {
                            //currPath = f.getParentFile();
                            scrollView.removeAllViews();
                            showFiles(path.getParentFile());
                            //showFiles(currPath.getParentFile());
                        }
                        else {
                            if (s.endsWith(EVENT_EXT)) {
                                selectedFile = f;
                            } else {
                                //currPath = f;
                                showFiles(f);
                            }
                        }
                    }
                });
            }
        }

        /**
         * Click on the element which have assigned the whole listener class: We show the dialog with the file list.
         * @param v
         */
        @Override
        public void onClick(View v) {
            event.saveEvent();
            showFiles(currPath);
            dialog.show();
        }
    }

    /**
     * The local version of ShowFiles. We try to get this working first.
     */
    class ShowLocalFiles implements View.OnClickListener {
        final Dialog dialog = new Dialog(context);
        LinearLayout scrollView;
        File currPath, selectedFile;
        TextView selectedView;



        public ShowLocalFiles() {
            super();
            this.currPath = getFilesDir();
            dialog.setContentView(R.layout.open_file_layout);
            dialog.setTitle("Title...");
            scrollView = (LinearLayout) dialog.findViewById(R.id.scrollView);
            Button openFileDialogButton = (Button) dialog.findViewById(R.id.openFileDialogBtn);

            openFileDialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(), selectedFile.getName() + " " + selectedFile.toString(), Toast.LENGTH_SHORT).show();
                    event = Event.loadEvent(selectedFile, getApplicationContext());
                    if(event != null) {
                        currEventButton.setText(event.eventName);
                    }
                    dialog.dismiss();
                    //showEventLay();
                }
            });
        }
        public void showFiles() {
            ArrayList<File> fileList = getLocalFiles(currPath.getName(), ".evn", getApplicationContext());
            selectedFile = null;
            scrollView.removeAllViews();
            if(selectedView != null) {
                selectedView.setBackgroundColor(Color.TRANSPARENT);
                selectedView = null;
            }

            for (int i = 0; i < fileList.size(); i++) {
                final TextView textView = new TextView(dialog.getContext());
                final File f = fileList.get(i);
                final String s = f.getName();

                textView.setPadding(5, 5, 5, 5);

                if(s.endsWith(EVENT_EXT)) {
                    textView.setText(s.substring(0, s.lastIndexOf(".")));
                }
                else {
                    textView.setText(s);
                    if(!s.equals(PARENT_DIR)) {
                        textView.setTextColor(Color.parseColor("#FF0000"));
                    }
                }

                scrollView.addView(textView);

                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(selectedView != null) {
                            selectedView.setBackgroundColor(Color.TRANSPARENT);
                        }
                        selectedView = textView;
                        textView.setBackgroundColor(Color.BLUE);
                        if(s.equals(PARENT_DIR)) {
                            //currPath = f.getParentFile();
                            scrollView.removeAllViews();
                            currPath = currPath.getParentFile();
                            showFiles();
                            //showFiles(currPath.getParentFile());
                        }
                        else {
                            if(s.endsWith(EVENT_EXT)) {
                                selectedFile = f;
                            }
                            else {
                                currPath = f;
                                showFiles();
                            }
                        }
                    }
                });
            }
        }
        @Override
        public void onClick(View v) {
            //String s = eventType + " " + eventMode + " " + nonEventMode + " " + currentTokens + " " + currentPoints + " " + eventGoal + "\n" + date;
            //Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
            if (event != null) {
                event.saveEvent();
                //String s1 = eventType + " " + eventMode + " " + nonEventMode + " " + currentTokens + " " + currentPoints + " " + eventGoal + "\n" + date;
                //writeToFile(s1, eventName + ".evn");
            }
            showFiles();
            dialog.show();
        }
    }


}
