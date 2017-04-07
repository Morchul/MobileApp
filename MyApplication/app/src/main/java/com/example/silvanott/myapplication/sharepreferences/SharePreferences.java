package com.example.silvanott.myapplication.sharepreferences;


import android.content.SharedPreferences;

/**
 * Created by Silvan Ott on 30.01.2017.
 */

/**
 * Die SharePreferences Klass in der das SharedPreferences administriert wird
 */
public class SharePreferences {

    public static final int SETTINGS_MODE = 0;
    public static final String SETTINGS_FILE = "CalculatorSettings";
    public SharedPreferences Settings;
    public SharedPreferences.Editor SettingsEditor;

    /**
     * gibt den Farbwert als int zurück
     * @param key welcher ausgelesen werden soll
     * @return gibt den Farbton (r,g,b) als int zurück
     */
    public int getColor(String key){
        return Settings.getInt(key,255);
    }

    /**
     * speichert den Farbwert als int
     * @param key wo gespeichert werden soll
     * @param value was gespeichert werden soll
     */
    public void saveColor(String key, int value){
        SettingsEditor.putInt(key,value);
        SettingsEditor.commit();
    }

    /**
     * gibt die Orientierung des Advanced Rechners zurück
     * @return true = Vertikal, false = Horizontal
     */
    public boolean getOrientation(){
        return Settings.getBoolean("keyorientation",true);
    }

    /**
     * speichert die Orientierung des Advanced Rechners
     * @param value was gespeichert werden soll
     */
    public void saveOrientation(boolean value){
        SettingsEditor.putBoolean("keyorientation",value);
        SettingsEditor.commit();
    }
}
