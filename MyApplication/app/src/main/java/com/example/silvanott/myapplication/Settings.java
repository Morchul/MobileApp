package com.example.silvanott.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.silvanott.myapplication.sharepreferences.SharePreferences;

/**
 * Created by silvan.ott on 27.01.2017.
 */

public class Settings extends AppCompatActivity {

    SharePreferences sp = new SharePreferences();
    CheckBox cb;
    SeekBar red;
    SeekBar green;
    SeekBar blue;
    SeekBar redb;
    SeekBar greenb;
    SeekBar blueb;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarsettings);
        setSupportActionBar(toolbar);
        TextView about = (TextView) findViewById(R.id.textabout);
        cb = (CheckBox) findViewById(R.id.checkBox);
        sp.Settings = getSharedPreferences(sp.SETTINGS_FILE,sp.SETTINGS_MODE);
        sp.SettingsEditor = sp.Settings.edit();
        if(sp.getOrientation()){cb.setChecked(true);}else{cb.setChecked(false);}
        about.setText("© 2017 ICT-Ausbildungszentrum CsBe Bern\n" +
                "Zieglerstrasse 64, CH-3007 Bern\n" +
                "\n" +
                "Developed by:\n" +
                "\n" +
                "Fabian Azad Kurt\n" +
                "(GUI-Design)\n" +
                "E-Mail: pumba99@outlook.com\n" +
                "\n" +
                "Silvan Ott\n" +
                "(Programming)\n" +
                "E-Mail: angel.alive8991@gmail.com\n" +
                "\n" +
                "Enrico Buchs\n" +
                "(Project initiator)\n" +
                "http://web4b.ch\n");

        red = (SeekBar) findViewById(R.id.seekBar);
        green = (SeekBar) findViewById(R.id.seekBar3);
        blue = (SeekBar) findViewById(R.id.seekBar4);
        redb = (SeekBar) findViewById(R.id.seekBar2);
        greenb = (SeekBar) findViewById(R.id.seekBar5);
        blueb = (SeekBar) findViewById(R.id.seekBar6);

        final Button btn = (Button) findViewById(R.id.button20);
        final Button btnb = (Button) findViewById(R.id.button18);

        red.setProgress(sp.getColor("keyr"));
        green.setProgress(sp.getColor("keyg"));
        blue.setProgress(sp.getColor("keyb"));
        redb.setProgress(sp.getColor("keyrb"));
        greenb.setProgress(sp.getColor("keygb"));
        blueb.setProgress(sp.getColor("keybb"));
        changeButtonColor(btn,true);
        changeButtonColor(btnb,false);

        red.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                changeColor(true);
                btn.setBackgroundColor(Color.rgb(red.getProgress(),green.getProgress(),blue.getProgress()));
                changeButtonColor(btn,true);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                sp.saveColor("keyr",red.getProgress());
            }
        });
        green.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                changeColor(true);
                changeButtonColor(btn,true);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {sp.saveColor("keyg",green.getProgress());}
        });
        blue.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                changeColor(true);
                changeButtonColor(btn,true);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {sp.saveColor("keyb",blue.getProgress());}
        });
        redb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                changeColor(false);
                changeButtonColor(btnb,false);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {sp.saveColor("keyrb",redb.getProgress());}
        });
        greenb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                changeColor(false);
                changeButtonColor(btnb,false);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {sp.saveColor("keygb",greenb.getProgress());}
        });
        blueb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                changeColor(false);
                changeButtonColor(btnb,false);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {sp.saveColor("keybb",blueb.getProgress());}
        });
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

        if (id == R.id.action_simple) {
            Intent simple = new Intent (this, MainActivity.class);
            startActivity(simple);
            return true;
        }
        if (id == R.id.action_advanced) {
            Intent advanced = new Intent(this, Advanced.class);
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

    public void changeColor(boolean a) {
        if (a) {
            MainActivity.coord.setBackgroundColor(Color.rgb(red.getProgress(), green.getProgress(), blue.getProgress()));
            if (red.getProgress() + green.getProgress() + blue.getProgress() > 382) {
                MainActivity.tx.setTextColor(Color.BLACK);
            } else {
                MainActivity.tx.setTextColor(Color.WHITE);
            }
        }
        else{
            for (Button b : MainActivity.buttons){
                b.setBackgroundColor(Color.rgb(redb.getProgress(), greenb.getProgress(), blueb.getProgress()));
                if(redb.getProgress() + greenb.getProgress() + blueb.getProgress() > 382){
                    b.setTextColor(Color.BLACK);
                }
                else{
                    b.setTextColor(Color.WHITE);
                }
            }
        }
    }
    public void changeButtonColor(Button b, boolean background){
        if(background){
            b.setBackgroundColor(Color.rgb(red.getProgress(), green.getProgress(), blue.getProgress()));
            if (red.getProgress() + green.getProgress() + blue.getProgress() > 382) {
                b.setTextColor(Color.BLACK);
            } else {
                b.setTextColor(Color.WHITE);
            }
        }else {
            b.setBackgroundColor(Color.rgb(redb.getProgress(), greenb.getProgress(), blueb.getProgress()));
            if (redb.getProgress() + greenb.getProgress() + blueb.getProgress() > 382) {
                b.setTextColor(Color.BLACK);
            } else {
                b.setTextColor(Color.WHITE);
            }
        }
    }
    public void advancedOrientation(View v){
        boolean checked = ((CheckBox) v).isChecked();
        if(checked){
            sp.saveOrientation(true);
        }else{sp.saveOrientation(false);}
    }
}

