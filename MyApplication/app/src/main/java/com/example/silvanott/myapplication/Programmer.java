package com.example.silvanott.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
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

    SharePreferences sp = new SharePreferences();
    public static TextView tx;
    public static List<Button> buttons = new ArrayList<Button>();
    public static ConstraintLayout coord;

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
}
