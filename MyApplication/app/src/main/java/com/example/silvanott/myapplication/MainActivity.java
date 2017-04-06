package com.example.silvanott.myapplication;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.example.silvanott.myapplication.operationen.Calculator;
import com.example.silvanott.myapplication.sharepreferences.SharePreferences;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    SharePreferences sp = new SharePreferences();
    Calculator calc = new Calculator();
    public static TextView tx,calculate;
    public static List<Button> buttons = new ArrayList<Button>();
    public static ConstraintLayout coord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        tx = (TextView) findViewById(R.id.text);
        calculate = (TextView) findViewById(R.id.textView);
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
            calculate.setTextColor(Color.BLACK);
        } else {
            tx.setTextColor(Color.WHITE);
            calculate.setTextColor(Color.WHITE);
        }

        for (Button b : buttons){
            Drawable d = b.getBackground();
            d.setColorFilter(Color.rgb(sp.getColor("keyrb"), sp.getColor("keygb"), sp.getColor("keybb")), PorterDuff.Mode.ADD);
            //b.setBackgroundColor(Color.rgb(sp.getColor("keyrb"), sp.getColor("keygb"), sp.getColor("keybb")));
            if(sp.getColor("keyrb") + sp.getColor("keygb") + sp.getColor("keybb") > 382){
                b.setTextColor(Color.BLACK);
            }
            else{
                b.setTextColor(Color.WHITE);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void click(View v){

        String eingabe = ((Button) v).getText()+"";

        switch(eingabe){
            case "=": calc.calculate(); break;
            case "AC":calc.allClear(); break;
            case "C": calc.remove(1); break;
            case "+/-": calc.negate(); break;
            default: calc.add(eingabe);
        }
        ausgabe();
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
        if (id == R.id.action_programmer) {
            Intent programmer = new Intent(this, Programmer.class);
            startActivity(programmer);
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    public void ausgabe(){
        tx.setText(calc.getNumber());
        calculate.setText(calc.getCalc());
    }
}
