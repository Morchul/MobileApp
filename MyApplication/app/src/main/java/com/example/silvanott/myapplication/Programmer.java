package com.example.silvanott.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.silvanott.myapplication.operationen.Calculator;
import com.example.silvanott.myapplication.sharepreferences.SharePreferences;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Silvan Ott on 02.04.2017.
 */

public class Programmer extends AppCompatActivity {

    List<Button> bin,dez,hex,okt;
    SharePreferences sp = new SharePreferences();
    public static TextView tx;
    public static List<Button> buttons = new ArrayList<Button>();
    public static ConstraintLayout coord;
    String number = "";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_programmer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        tx = (TextView) findViewById(R.id.text);
        coord = (ConstraintLayout) findViewById(R.id.constraint);
        setSupportActionBar(toolbar);

        sp.Settings = getSharedPreferences(sp.SETTINGS_FILE,sp.SETTINGS_MODE);
        sp.SettingsEditor = sp.Settings.edit();

        for(int i = 0; i < coord.getChildCount(); i++){
            if(coord.getChildAt(i) instanceof Button)
                buttons.add((Button)coord.getChildAt(i));
        }

        coord.setBackgroundColor(Color.rgb(sp.getColor("keyr"), sp.getColor("keyg"), sp.getColor("keyb")));
        if (sp.getColor("keyr") + sp.getColor("keyg") + sp.getColor("keyb") > 382) {
            tx.setTextColor(Color.BLACK);
        } else {
            tx.setTextColor(Color.WHITE);
        }

        for (Button b : buttons){
            b.setBackgroundColor(Color.rgb(sp.getColor("keyrb"), sp.getColor("keygb"), sp.getColor("keybb")));
            if(sp.getColor("keyrb") + sp.getColor("keygb") + sp.getColor("keybb") > 382){
                b.setTextColor(Color.BLACK);
            }
            else{
                b.setTextColor(Color.WHITE);
            }
        }
        Button komma = (Button) findViewById(R.id.button21);
        komma.setEnabled(false);

        bin = new ArrayList<Button>();
        bin.add((Button) findViewById(R.id.button));
        bin.add((Button) findViewById(R.id.button13));
        okt = new ArrayList<Button>();
        okt.add((Button) findViewById(R.id.button5));
        okt.add((Button) findViewById(R.id.button9));
        okt.add((Button) findViewById(R.id.button10));
        okt.add((Button) findViewById(R.id.button11));
        okt.add((Button) findViewById(R.id.button14));
        okt.add((Button) findViewById(R.id.button15));
        dez = new ArrayList<Button>();
        dez.add((Button) findViewById(R.id.button6));
        dez.add((Button) findViewById(R.id.button7));
        hex = new ArrayList<Button>();
        hex.add((Button) findViewById(R.id.button3));
        hex.add((Button) findViewById(R.id.button4));
        hex.add((Button) findViewById(R.id.button8));
        hex.add((Button) findViewById(R.id.button12));
        hex.add((Button) findViewById(R.id.button16));
        hex.add((Button) findViewById(R.id.button22));


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent settings = new Intent (this, Settings.class);
            startActivity(settings);
            return true;
        }
        if (id == R.id.action_advanced) {
            Intent advanced = new Intent (this, Advanced.class);
            startActivity(advanced);
            return true;
        }
        if (id == R.id.action_simple) {
            Intent main = new Intent (this, MainActivity.class);
            startActivity(main);
            return true;
        }


        return super.onOptionsItemSelected(item);
    }
    public void click(View v){
        String input = ((Button) v).getText().toString();
        switch(input){
            case "\u232B": if(number.length()>0){number = number.substring(0,number.length()-1);}
                input=""; break;
            case "AC": number=""; input=""; break;
        }
        number += input;
        setText(number);
    }
    public void setText(String text){
        TextView tv = (TextView) findViewById(R.id.text);
        tv.setText(text);
    }
}
