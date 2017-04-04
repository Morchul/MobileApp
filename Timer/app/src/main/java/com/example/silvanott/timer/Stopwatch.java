package com.example.silvanott.timer;

import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.TimeZone;
import java.util.Timer;

/**
 * Created by silvan.ott on 15.02.2017.
 */

public class Stopwatch {

    Long start = System.currentTimeMillis();

    Long lapstart = System.currentTimeMillis();

    public Long time = 0L,lap = 0L;
    boolean reset = false,stop = false;

    public String getTime(){
        Long now = System.currentTimeMillis();
            time = now-start;
            lap = now-lapstart;
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SSS");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        String strDate = sdf.format(time);
        return strDate;
    }
    public void startTime(){
        if(reset) {
            start = System.currentTimeMillis();
            lapstart = System.currentTimeMillis();
            reset = false;
            time = 0L;
            lap = 0L;
        }
        if(stop){
            start = System.currentTimeMillis()-time;
            lapstart = System.currentTimeMillis()-lap;
            stop = false;
        }
    }
    public void stopTime(){
        stop = true;
    }
    public void resetTime(){
        reset= true;
    }

    public String lap(){
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SSS");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        String strDate = sdf.format(lap);
        lapstart = System.currentTimeMillis();
        return strDate;
    }
}
