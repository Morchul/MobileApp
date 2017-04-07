package com.example.silvanott.myapplication;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.silvanott.myapplication.operationen.Calculator;
import com.example.silvanott.myapplication.sharepreferences.SharePreferences;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by silvan.ott on 31.01.2017.
 */

/**
 * die Advanced Klasse welche für den Advanced Rechner zuständig ist
 */
public class Advanced extends AppCompatActivity {

    SharePreferences sp = new SharePreferences();
    Calculator calc = new Calculator();
    public static TextView tx,calculate;
    public static List<Button> buttons = new ArrayList<Button>();
    public static ConstraintLayout coord;

    /**
     * wird ausgeführt wenn die Activity Erstellt wird
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sp.Settings = getSharedPreferences(sp.SETTINGS_FILE,sp.SETTINGS_MODE);
        sp.SettingsEditor = sp.Settings.edit();

        if(sp.getOrientation()){
            setContentView(R.layout.activity_advanced_horizontal);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        else{
            setContentView(R.layout.activity_advanced);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbaradvanced);
        setSupportActionBar(toolbar);
        tx = (TextView) findViewById(R.id.textadvanced);
        calculate = (TextView) findViewById(R.id.textView);
        coord = (ConstraintLayout) findViewById(R.id.constraintadvanced);

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
            if(sp.getColor("keyrb") + sp.getColor("keygb") + sp.getColor("keybb") > 382){
                b.setTextColor(Color.BLACK);
            }
            else{
                b.setTextColor(Color.WHITE);
            }
        }
    }

    /**
     * Wenn das OptionMenu erstellt wird
     * @param menu
     * @return true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * Wenn ein Item des Menues ausgewählt wird
     * @param item das Item das ausgewählt wird
     * @return true
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent settings = new Intent (this, Settings.class);
            startActivity(settings);
            return true;
        }
        if (id == R.id.action_simple) {
            Intent main = new Intent (this, MainActivity.class);
            startActivity(main);
            return true;
        }
        if (id == R.id.action_programmer) {
            Intent programmer = new Intent(this, Programmer.class);
            startActivity(programmer);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Die methode die aufgerufen wird wenn man einen button drückt
     * @param v das View des gedrückten Buttons
     */
    public void click(View v){
        String eingabe = ((Button) v).getText()+"";
        if(calc.test(calc.getLast(),"+-*/", false) && calc.test(eingabe,"x)\u207f",false) ){//|| !calc.getOperator().equals("") && calc.test(eingabe,"+-*/",false) && !calc.getInput()){
            return;
        }
        if(calc.getInput() && (calc.test(calc.getLast(),"+-*/",false) || eingabe.equals("="))){
            calc.advanced(eingabe);
        }
        else {
            switch (eingabe) {
                case "=":   calc.calculate();break;
                case "AC":  calc.allClear();break;
                case "\u232B":   calc.remove(1);break;
                case "+/-": calc.negate();break;
                case "x!":calc.add("!");break;
                case "x\u00b2":calc.add("^2");break;

                case "\u207f\u221a":calc.advanced("√"); break;
                case "x\u207f":calc.advanced("^");break;

                case "sin/asin":
                    Button b = (Button) findViewById(R.id.button54);
                    Button cos = (Button) findViewById(R.id.button57);
                    Button tan = (Button) findViewById(R.id.button63);
                    Button sin = (Button) findViewById(R.id.button53);
                    if (cos.getText().equals("cos")) {
                        cos.setText("acos");
                        sin.setText("asin");
                        tan.setText("atan");
                    } else {
                        cos.setText("cos");
                        sin.setText("sin");
                        tan.setText("tan");
                    }
                    break;

                default:
                    calc.add(eingabe);
            }
        }
        ausgabe();
    }

    /**
     * gibt die Rechnung im TextView aus
     */
    public void ausgabe(){
        tx.setText(calc.getNumber());
        calculate.setText(calc.getCalc());
    }
}
