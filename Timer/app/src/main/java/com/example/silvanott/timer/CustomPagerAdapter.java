package com.example.silvanott.timer;

import android.app.Activity;
import android.content.Context;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

/**
 * Created by silvan.ott on 15.02.2017.
 */

/**
 * CustomPagerAdapter ist für das Swip View und den Timer zuständig
 */
public class CustomPagerAdapter extends PagerAdapter{

    private Context mContext;

    public static Timer time = new Timer();
    public static Thread th;
    public static TextView timer;
    static Activity a;
    private static Long actualtime = 0L;
    static NumberPicker npmin;
    static NumberPicker nphour;
    static NumberPicker npsecond;
    public static boolean ringing = true;

    /**
     * Construktor der das Context setzt
     * @param context
     */
    public CustomPagerAdapter(Context context) {
        mContext = context;
    }

    /**
     * Instanziert die Items
     * @param collection
     * @param position
     * @return gibt das layout Object zurück
     */
    @Override
    public Object instantiateItem(ViewGroup collection, int position) {
        ModelObject modelObject = ModelObject.values()[position];
        LayoutInflater inflater = LayoutInflater.from(mContext);
        ViewGroup layout = (ViewGroup) inflater.inflate(modelObject.getLayoutResId(), collection, false);
        collection.addView(layout);


        if(modelObject.getLayoutResId() == ModelObject.BLUE.getLayoutResId()){
            a = (Activity) mContext;
            npmin = (NumberPicker) a.findViewById(R.id.numberPicker2);
            npmin.setMinValue(0);
            npmin.setMaxValue(59);
            nphour = (NumberPicker) a.findViewById(R.id.numberPicker3);
            nphour.setMinValue(0);
            nphour.setMaxValue(23);
            npsecond = (NumberPicker) a.findViewById(R.id.numberPicker4);
            npsecond.setMinValue(0);
            npsecond.setMaxValue(59);

        }else if(modelObject.getLayoutResId() == ModelObject.GREEN.getLayoutResId()){
            TextView about = (TextView) a.findViewById(R.id.textView2);
            about.setText("© 2017 ICT-Ausbildungszentrum CsBe Bern\n" +
                    "Zieglerstrasse 64, CH-3007 Bern\n" +
                    "\n" +
                    "Developed by:\n" +
                    "\n" +
                    "Silvan Ott\n" +
                    "(Programming)\n" +
                    "E-Mail: angel.alive8991@gmail.com\n" +
                    "\n" +
                    "Enrico Buchs\n" +
                    "(Project initiator)\n" +
                    "http://web4b.ch\n");
        }


        return layout;
    }

    /**
     * zerstört die Items welche nichtmehr angezeigt werden
     * @param collection
     * @param position
     * @param view
     */
    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    /**
     * Gibt die Anzahl der Views zurück
     * @return anzahl Views
     */
    @Override
    public int getCount() {
        return ModelObject.values().length;
    }

    /**
     * Testen ob ein gewisses layout von einem Object ist
     * @param view
     * @param object
     * @return true oder false
     */
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    /**
     * Gibt den Title des Views zurück
     * @param position die Position von welchem der Titel zurückgegeben werden soll
     * @return gibt den titel zurück
     */
    @Override
    public CharSequence getPageTitle(int position) {
        ModelObject customPagerEnum = ModelObject.values()[position];
        return mContext.getString(customPagerEnum.getTitleResId());
    }

    /**
     * Setzt die Zeit des Timers und die Zeiger
     * @param tv das TextView welche die Zeit anzeigt
     * @param s der String mit der Zeit
     */
    public static void setTimer(final TextView tv,final String s){

        a.runOnUiThread(new Runnable() {
            public void run() {
                if(s.equals("00:00:00.000")){
                    stopth();
                    tv.setText("00:00:00.000");
                    setVisibility();
                    Button b = (Button) a.findViewById(R.id.button4);
                    b.setText("Start");
                    CustomPagerAdapter.time.resetTime();
                    actualtime =0L;
                    ring();
                }
                ImageView hour = (ImageView) a.findViewById(R.id.imageView7);
                ImageView minute = (ImageView) a.findViewById(R.id.imageView6);
                ImageView second = (ImageView) a.findViewById(R.id.imageView5);
                second.setRotation(time.time/10 % 6000*0.06f);
                minute.setRotation((time.time/1000 % 3600*0.1f));
                hour.setRotation(time.time/60000 % 720*0.5f);
                tv.setText(s);
            }
        });
    }

    /**
     * Stopt den Thread
     */
    public static void stopth(){
        th.interrupt();
    }

    /**
     * gibt die Zeit zurück
     * @return die Zeit
     */
    public static Long getZeit(){
        if(actualtime == 0L){
            return (1000*60L*60*nphour.getValue())+(1000*60*npmin.getValue())+(1000*npsecond.getValue());
        }
        return actualtime;
    }

    /**
     * setzt die Zeit
     * @param l die Zeit als Long
     */
    public static void setZeit(Long l){
        actualtime = l;
    }

    /**
     * ring funktion die den Klingelton startet
     */
    public static void ring(){
        if(ringing) {
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone r = RingtoneManager.getRingtone(a.getApplicationContext(), notification);
            r.play();
            ringing = false;
        }
    }

    /**
     * Setzt die Sichtbarkeit der Nuberpicker und der Uhr des Timers
     */
    public static void setVisibility(){
        nphour = (NumberPicker) a.findViewById(R.id.numberPicker3);
        npmin = (NumberPicker) a.findViewById(R.id.numberPicker2);
        npsecond = (NumberPicker) a.findViewById(R.id.numberPicker4);
        ImageView clock = (ImageView) a.findViewById(R.id.imageViewTimer);
        ImageView hour = (ImageView) a.findViewById(R.id.imageView7);
        ImageView minute = (ImageView) a.findViewById(R.id.imageView6);
        ImageView second = (ImageView) a.findViewById(R.id.imageView5);
        TextView text = (TextView) a.findViewById(R.id.textView);
        nphour.setVisibility(View.VISIBLE);
        npmin.setVisibility(View.VISIBLE);
        npsecond.setVisibility(View.VISIBLE);
        clock.setVisibility(View.INVISIBLE);
        hour.setVisibility(View.INVISIBLE);
        minute.setVisibility(View.INVISIBLE);
        second.setVisibility(View.INVISIBLE);
        text.setVisibility(View.INVISIBLE);
    }

}
