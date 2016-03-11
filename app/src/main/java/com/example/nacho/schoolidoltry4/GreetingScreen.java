package com.example.nacho.schoolidoltry4;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import com.example.nacho.schoolidoltry4.R;

/**
 * Created by nacho on 15/03/2015.
 */
public class GreetingScreen extends MyActivity {
    CheckBox checkbox;
    Button btnGreet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.greetinglayout);
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Log.d(TAG, "onCreate");
        checkbox =(CheckBox) findViewById(R.id.checkBox);
        btnGreet =(Button) findViewById(R.id.greetButton);
        btnGreet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pushBoolean("checked", checkbox.isChecked());
                changeScreen(MainScreen.class);
            }
        });
    }

    /*void meteValores(boolean b) {
        // ARCHIVO DE CLAVES: ACCEDER
        Context context = this;
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);

        // EDITAR
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean("checkedGreet", b);
        //editor.commit();

        // LEER
        //checked = sharedPref.getBoolean("checkedGreet", checked);
    }*/

    /*void changeScreen(Class c) {
        Intent intent = new Intent(this,c);
        startActivity(intent);
    }*/


}
