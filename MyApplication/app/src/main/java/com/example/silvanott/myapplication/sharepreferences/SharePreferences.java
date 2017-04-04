package com.example.silvanott.myapplication.sharepreferences;


import android.content.SharedPreferences;

/**
 * Created by Silvan Ott on 30.01.2017.
 */

public class SharePreferences {

    public static final int SETTINGS_MODE = 0;
    public static final String SETTINGS_FILE = "CalculatorSettings";
    public SharedPreferences Settings;
    public SharedPreferences.Editor SettingsEditor;
    public int getColor(String key){
        return Settings.getInt(key,255);
    }
    public void saveColor(String key, int value){
        SettingsEditor.putInt(key,value);
        SettingsEditor.commit();
    }
    public boolean getOrientation(){
        return Settings.getBoolean("keyorientation",true);
    }
    public void saveOrientation(boolean value){
        SettingsEditor.putBoolean("keyorientation",value);
        SettingsEditor.commit();
    }
}
