package com.example.silvanott.timer;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * Created by silvan.ott on 22.03.2017.
 */

public class Timer {
    Long start;

    public Long time = 0L;
    boolean reset = false,stop = false;
    public Timer(Long time){start = System.currentTimeMillis()+time;}
    public Timer(){}

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
    public synchronized void startTime(int hour,int min,int sec){
        if(reset) {
            reset = false;
            time = -351L;
            start = System.currentTimeMillis()+(1000*60L*60*hour)+(1000*60*min)+(sec*1000);
            Log.d("Test reset","Test reset " + start + "\n" + System.currentTimeMillis());
        }
        if(stop) {
            start = System.currentTimeMillis() + time;
            stop = false;
        }
    }
    public synchronized void stopTime(){
        stop = true;
    }
    public synchronized void resetTime(){
        reset= true;
    }
}
