package com.example.nacho.schoolidoltry4;

import android.os.CountDownTimer;
import android.os.Bundle;

import com.example.nacho.schoolidoltry4.R;
//import GlobalFunctions.*;

public class HomeScreen extends MyActivity {
    private boolean checked = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /*requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);*/

        /*if(this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE)) {
            this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        }*/
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);


       /* Button btn1 = (Button)findViewById(R.id.btn1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeScreen();
            }
        });*/
        new CountDownTimer(3000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }
            @Override
            public void onFinish() {
                checked = pullBoolean("checked");
                if(checked) {
                    changeScreen(MainScreen.class);
                }
                else {
                    changeScreen(GreetingScreen.class);
                }
            }
        }.start();

    }

    /*void sacaValores() {
        // ARCHIVO DE CLAVES: ACCEDER
        Context context = this;
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);

        // EDITAR
        //SharedPreferences.Editor editor = sharedPref.edit();
        //editor.putBoolean("checkedGreet", false);
        //editor.commit();

        // LEER
        checked = sharedPref.getBoolean("checkedGreet", checked);
        Log.d("valor checked: " + checked, TAG);
    }*/

    /*void changeScreen(Class c) {
        Intent intent = new Intent(this,c);
        startActivity(intent);
    }*/

}
