package com.example.silvanott.timer;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Die Hauptklasse die beim Start ausgeführt wird und die, die Stopuhr verwaltet
 */
public class MainActivity extends AppCompatActivity {

    public static TextView stopwatch;
    private Stopwatch stoptime = new Stopwatch();
    private Thread th;
    private Button resetlap;

    NumberPicker nphour,npmin,npsec;
    ImageView hour,minute,second;

    private static int RESULT_LOAD_IMG = 1;
    String imgDecodableString;

    /**
     * Die OnCreate Methode die beim erstellen aufgerufen wird
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager viewPager = (ViewPager) findViewById(R.id.container);
        viewPager.setAdapter(new CustomPagerAdapter(this));
        viewPager.setOffscreenPageLimit(3);

        resetlap = (Button) findViewById(R.id.button3);

        verifyStoragePermissions(this);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        th = new Thread(){
            @Override
            public void run(){
                stoptime = new Stopwatch();
                while(!Thread.interrupted()) {
                    stopwatch = (TextView) findViewById(R.id.time);
                    setTime(stopwatch, stoptime.getTime());
                }
            }
        };
    }
    //---------------------------------------------------------------------------
    //-----------------------------Stopwatch-------------------------------------
    //---------------------------------------------------------------------------

    /**
     * die Funktion die aufgerufen wird wenn man bei der Stopwatch auf den Start / Stop button drückt
     * @param v die -View die gedrückt wird
     */
    public void startStop(View v) {
        TextView tv = (TextView) v;
        if(tv.getText().equals("start")){
            try {
                stoptime.startTime();
                tv.setText("stop");
                resetlap = (Button) findViewById(R.id.button3);
                resetlap.setText("lap");
                if (th.getState().name().equals("TERMINATED")) {
                    th = new Thread() {
                        @Override
                        public void run() {
                            while (!Thread.interrupted()) {
                                stopwatch = (TextView) findViewById(R.id.time);
                                setTime(stopwatch, stoptime.getTime());
                            }
                        }
                    };
                }
                if (th.isInterrupted() || !th.isAlive()) {
                    th.start();
                }
            }catch(Exception e){
                stoptime.resetTime();
                th.interrupt();
            }

        }else if(tv.getText().equals("stop")){
            stoptime.stopTime();
            tv.setText("start");
            resetlap = (Button) findViewById(R.id.button3);
            resetlap.setText("reset");
            if(!th.isInterrupted() || th.isAlive()) {
                th.interrupt();
            }
        }

    }

    /**
     * die Funktion die aufgerufen wird wenn man bei der Stopwatch auf den Reset / Lap button drückt
     * @param v die View die gerdrückt wurde
     */
    public void resetLap(View v) {
        TextView tv = (TextView) v;
        TextView lap = (TextView) findViewById(R.id.lap);
        if(tv.getText().equals("reset")){
            stoptime.resetTime();
            stopwatch = (TextView) findViewById(R.id.time);
            setTime();
            setPointer(hour,minute,second,0L);
            stopwatch.setText("00:00:00.000");
            lap.setText("");
        }
        if(tv.getText().equals("lap")){
            lap.setText(stoptime.lap()+"\n"+lap.getText());
        }
    }

    /**
     * setzt die Zeit der Stopwatch
     * @param tv das TextView in dem die Zeit angezeigt wird
     * @param s die Zeit als String
     */
    private void setTime(final TextView tv,final String s){
        runOnUiThread(new Runnable() {
            public void run() {
                setTime();
                setPointer(hour,minute,second,stoptime.time);
                tv.setText(s);
            }
        });
    }

    //------------------------------------------------------------------------
    //-----------------------------Timer--------------------------------------
    //------------------------------------------------------------------------

    /**
     * die Funktion die aufgerufen wird wenn man beim Timer auf den Start / Stop button drückt
     * @param v die View die gedrückt wurde
     */
    public void stasto(View v){
        Button b = (Button) v;
        if(b.getText().equals("Start")){
            try {
                npVisibility(false);
                b.setText("Stop");
                CustomPagerAdapter.th = new Thread() {
                    @Override
                    public void run() {
                        CustomPagerAdapter.time = new Timer(CustomPagerAdapter.getZeit());
                        CustomPagerAdapter.ringing = true;
                        while (!Thread.interrupted()) {
                            CustomPagerAdapter.timer = (TextView) findViewById(R.id.textView);
                            CustomPagerAdapter.setTimer(CustomPagerAdapter.timer, CustomPagerAdapter.time.getTime());
                        }
                    }
                };
                if (CustomPagerAdapter.th.isInterrupted() || !CustomPagerAdapter.th.isAlive()) {
                    CustomPagerAdapter.th.start();
                }
            }catch(Exception e){

            }

        }else if(b.getText().equals("Stop") ){
            CustomPagerAdapter.setZeit(CustomPagerAdapter.time.time);
            b.setText("Start");
            if(!CustomPagerAdapter.th.isInterrupted() || CustomPagerAdapter.th.isAlive()) {
                CustomPagerAdapter.stopth();
            }
        }


    }

    /**
     * die Funktion die aufgerufen wird wenn man beim Timer auf den Reset button drückt
     * @param v die View die gedrückt wurde
     */
    public void reset(View v){
        Button b = (Button) findViewById(R.id.button4);
        if(b.getText().equals("Start")) {
            CustomPagerAdapter.timer.setText("00:00:00.000");
            CustomPagerAdapter.stopth();
            npVisibility(true);
            CustomPagerAdapter.time.resetTime();
            CustomPagerAdapter.setZeit(0L);
            ImageView hour = (ImageView) findViewById(R.id.imageView7);
            ImageView minute = (ImageView) findViewById(R.id.imageView6);
            ImageView second = (ImageView) findViewById(R.id.imageView5);
            setPointer(hour,minute,second,0L);
        }
    }

    //---------------------------------------------------------------------------
    //--------------------------Settings-----------------------------------------
    //---------------------------------------------------------------------------

    /**
     * die Funktion die aufgerufen wird wenn man bei den Settings auf den Load Image button drückt
     * @param v die View die gedrückt wurde
     */
    public void loadImage(View v){
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
    }
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    /**
     * Funktion die nach der Berechtigung fragt
     * @param activity
     */
    public static void verifyStoragePermissions(Activity activity) {
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    /**
     * Funktion um das Bild aus der Galerie zu holen und anzuzeigen
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {
            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK
                    && null != data) {

                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imgDecodableString = cursor.getString(columnIndex);
                cursor.close();
                ConstraintLayout con = (ConstraintLayout) findViewById(R.id.main_content);
                ImageView imgView = (ImageView) findViewById(R.id.imageView8);
                imgView.setImageBitmap(BitmapFactory
                        .decodeFile(imgDecodableString));
                BitmapDrawable op = new BitmapDrawable(getResources(), BitmapFactory.decodeFile(imgDecodableString));
                con.setBackground(op);
                con.getBackground().setAlpha(70);
            } else {
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }
    }

    /**
     * unterbricht den Thread
     */
    @Override
    public void onDestroy() {
        th.interrupt();
        CustomPagerAdapter.th.interrupt();
        super.onDestroy();
    }

    /**
     * setzt die Sichtbarkeit der Numberpicker und Uhr beim Timer
     * @param enable
     */
    public void npVisibility(boolean enable){
        nphour = (NumberPicker) findViewById(R.id.numberPicker3);
        npmin = (NumberPicker) findViewById(R.id.numberPicker2);
        npsec = (NumberPicker) findViewById(R.id.numberPicker4);
        TextView text = (TextView) findViewById(R.id.textView);
        ImageView clock = (ImageView) findViewById(R.id.imageViewTimer);
        ImageView hour = (ImageView) findViewById(R.id.imageView7);
        ImageView minute = (ImageView) findViewById(R.id.imageView6);
        ImageView second = (ImageView) findViewById(R.id.imageView5);
        if(enable) {
            nphour.setVisibility(View.VISIBLE);
            npmin.setVisibility(View.VISIBLE);
            npsec.setVisibility(View.VISIBLE);
            clock.setVisibility(View.INVISIBLE);
            hour.setVisibility(View.INVISIBLE);
            minute.setVisibility(View.INVISIBLE);
            second.setVisibility(View.INVISIBLE);
            text.setVisibility(View.INVISIBLE);
        }else{
            nphour.setVisibility(View.INVISIBLE);
            npmin.setVisibility(View.INVISIBLE);
            npsec.setVisibility(View.INVISIBLE);
            clock.setVisibility(View.VISIBLE);
            hour.setVisibility(View.VISIBLE);
            minute.setVisibility(View.VISIBLE);
            second.setVisibility(View.VISIBLE);
            text.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Setzt die Zeiger der Uhr
     * @param phour das ImageView des Stundenzeigers
     * @param pmin das ImageView des Minutenzeigers
     * @param psec das imageView des Sekundenzeigers
     * @param time die Zeit als Long
     */
    public void setPointer(ImageView phour,ImageView pmin,ImageView psec,Long time){
        psec.setRotation(time/10 % 6000*0.06f);
        pmin.setRotation((time/1000 % 3600*0.1f));
        phour.setRotation(time/60000 % 720*0.5f);
    }

    /**
     * Setzt die ImageViews der Zeiger auf die Variablen hour,minute,second
     */
    public void setTime(){
        hour = (ImageView) findViewById(R.id.imageView4);
        minute = (ImageView) findViewById(R.id.imageView3);
        second = (ImageView) findViewById(R.id.imageView2);
    }
}
