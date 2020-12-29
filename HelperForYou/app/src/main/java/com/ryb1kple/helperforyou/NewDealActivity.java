package com.ryb1kple.helperforyou;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.VideoView;

import org.w3c.dom.Text;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class NewDealActivity extends Activity {

    String pickedTime;
    String pickedDate;

    Calendar dateAndTime=Calendar.getInstance();
    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.new_deal);
        setInitialDateTime();
        set_settings();
        open();
        video();
        animation();
    }

    @Override
    protected void onResume() {
        super.onResume();
        VideoView video = (VideoView) findViewById(R.id.video);
        video.start();
    }

    public void animation () {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.screen_scale);
        View main = findViewById(R.id.linearLayout2);
        main.startAnimation(anim);
    }

    // отображаем диалоговое окно для выбора даты
    public void on_date_button_clicked(View v) {
        new DatePickerDialog(NewDealActivity.this, d,
                dateAndTime.get(Calendar.YEAR),
                dateAndTime.get(Calendar.MONTH),
                dateAndTime.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    // отображаем диалоговое окно для выбора времени
    public void on_time_button_clicked(View v) {
        new TimePickerDialog(NewDealActivity.this, t,
                dateAndTime.get(Calendar.HOUR_OF_DAY),
                dateAndTime.get(Calendar.MINUTE), true)
                .show();
    }
    // установка начальных даты и времени
    private void setInitialDateTime() {
        Date d = new Date(dateAndTime.getTimeInMillis());
        d.getMonth();

        String s = DateUtils.formatDateTime(this,
                dateAndTime.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR
                        | DateUtils.FORMAT_SHOW_TIME);
        String date = DateUtils.formatDateTime(this,
                dateAndTime.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE);
        String time = DateUtils.formatDateTime(this,
                dateAndTime.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_TIME);

        this.pickedTime = time;
        this.pickedDate = date;

        TextView tv = findViewById(R.id.time);
        TextView tv1 = findViewById(R.id.date);
        tv.setText(pickedTime);
        tv1.setText(pickedDate);
    }
    // установка обработчика выбора времени
    TimePickerDialog.OnTimeSetListener t=new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            dateAndTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
            dateAndTime.set(Calendar.MINUTE, minute);
            setInitialDateTime();

        }
    };

    // установка обработчика выбора даты
    DatePickerDialog.OnDateSetListener d=new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateAndTime.set(Calendar.YEAR, year);
            dateAndTime.set(Calendar.MONTH, monthOfYear);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setInitialDateTime();

        }
    };



    public void set_settings () {
        SharedPreferences settings = getSharedPreferences("settings", MODE_PRIVATE);

        String color = settings.getString("bgcolor", "grey");
        View main = findViewById(R.id.deal);
        switch (color){
            case "aqua":
                main.setBackgroundColor(getResources().getColor(R.color.aqua));
                break;
            case "red":
                main.setBackgroundColor(getResources().getColor(R.color.red));
                break;
            case "brown":
                main.setBackgroundColor(getResources().getColor(R.color.brown));
                break;
            case "grey":
                main.setBackgroundColor(getResources().getColor(R.color.grey));
                break;
        }

        VideoView video = (VideoView) findViewById(R.id.video);
        video.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Intent intent = new Intent(NewDealActivity.this, FullVideoActivity.class);
                VideoView video = (VideoView) findViewById(R.id.video);
                int ms = video.getCurrentPosition();
                intent.putExtra("ms", ms);
                startActivity(intent);
                return false;
            }
        });
    }

    public List<Deal> deal = new ArrayList<>();

    public void save(View view){

        TextView tv = (TextView) findViewById(R.id.editTextTextPersonName);
        String text = tv.getText().toString();

        if (text.length() < 1) {
            Toast.makeText(this, "Введите название дела", Toast.LENGTH_LONG).show();
            return;
        }

        deal.add(new Deal(pickedTime, text, pickedDate));
        boolean result = JSONHelper.exportToJSON(this, deal);
        if(result){
            Toast.makeText(this, "Данные сохранены", Toast.LENGTH_LONG).show();
        }
        finish();

    }

    public void open(){
        deal = JSONHelper.importFromJSON(this);
        if (deal == null) {
            deal = new ArrayList<>();
        }
        //TextView tv = (TextView) findViewById(R.id.);
        String s = "";
        for (Deal i : deal){
            s += i.time + " ";
        }
        //tv.setText(s);
    }

    public void video () {
        VideoView video = (VideoView) findViewById(R.id.video);
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.vid);
        video.setVideoURI(uri);
        video.start();

    }

    public void start_video (View view) {
        VideoView video = (VideoView) view;
        VideoView video_main = (VideoView) findViewById(R.id.video);
        if (video.isPlaying()) {
            Animation click = AnimationUtils.loadAnimation(this, R.anim.video_click);
            video_main.setBackgroundResource(R.drawable.video_stop);
            video.startAnimation(click);
            video.pause();
        }
        else {
            video.start();
            video_main.setBackgroundResource(0);
        }
    }

}