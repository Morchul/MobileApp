package com.example.silvanott.timer;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * Created by silvan.ott on 22.03.2017.
 */

/**
 * Timer Klasse die die Zeit des Timers berechnet
 */
public class Timer {
    Long start;

    public Long time = 0L;
    boolean reset = false;
    public Timer(Long time){start = System.currentTimeMillis()+time;}
    public Timer(){}

    /**
     * berechnet die Zeit des Timers und gibt sie zurück
     * @return die Zeit als String
     */
    public synchronized String getTime(){
        Long now = System.currentTimeMillis();
        time = start-now;
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SSS");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        String strDate = sdf.format(time);
        if(time <= 0){
            return "00:00:00.000";
        }
        return strDate;
    }

    /**
     * setzt die Zeit zurück
     */
    public synchronized void resetTime(){
        reset= true;
    }
}
