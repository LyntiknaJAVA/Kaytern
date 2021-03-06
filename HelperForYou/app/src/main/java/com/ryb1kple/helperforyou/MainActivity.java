package com.ryb1kple.helperforyou;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewParent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import org.w3c.dom.Document;

import java.lang.reflect.Array;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.zip.Inflater;


public class MainActivity extends AppCompatActivity {

    View view;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        first_zitata();
        fonts();
        hidden_btn = hide_btn();
        set_settings();
        animation_load();
        import_deals();
    }

    @Override
    protected void onResume() {
        super.onResume();
        import_deals();
    }

    public void animation () {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.screen_scale);
        View main = findViewById(R.id.main_main);
        main.startAnimation(anim);
    }

    public void screen () {
        View screen = findViewById(R.id.linearLayout2);
        screen.setBackgroundColor(getResources().getColor(R.color.grey));
    }

    Button hidden_btn;
    public Button hide_btn () {
        LinearLayout buttons = findViewById(R.id.buttons);
        Button grey_btn = findViewById(R.id.grey_btn);
        SharedPreferences sp = getSharedPreferences("MY_SETTINGS", Context.MODE_PRIVATE);
        boolean hasVisited = sp.getBoolean("hasVisited", false);
        if (!hasVisited) {
            buttons.removeView(grey_btn);
        }
        SharedPreferences.Editor e = sp.edit();
        e.putBoolean("hasVisited", true);
        e.commit();
        hidden_btn = grey_btn;
        return grey_btn;
    }

    public void on_new_deal_button_click (View view) {
        anim = AnimationUtils.loadAnimation(this, R.anim.screen_scale);
        anim_back = AnimationUtils.loadAnimation(this, R.anim.scale_screen_back);
        View main = findViewById(R.id.main_main);
        main.startAnimation(anim_back);
        Handler handler2 = new Handler();
        handler2.postDelayed(new Runnable() {
            public void run() {
                Intent intent = new Intent(MainActivity.this, NewDealActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
                //LayoutInflater lay = getLayoutInflater();
                //final View main = lay.inflate(R.layout.new_deal, null, false);
                //main.startAnimation(anim);
            }
        }, 100);
    }

    public void set_settings () {
        //getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        SharedPreferences settings = getSharedPreferences("settings", MODE_PRIVATE);
        String color = settings.getString("bgcolor", "grey");
        View main = (View) findViewById(R.id.container);
        LinearLayout buttons = findViewById(R.id.buttons);
        switch (color) {
            case "aqua":
                main.setBackgroundColor(getResources().getColor(R.color.aqua));
                Button aqua_btn = findViewById(R.id.aqua_btn);
                buttons.removeView(aqua_btn);
                hidden_btn = aqua_btn;
                break;
            case "red":
                main.setBackgroundColor(getResources().getColor(R.color.red));
                Button red_btn = findViewById(R.id.red_btn);
                buttons.removeView(red_btn);
                hidden_btn = red_btn;
                break;
            case "brown":
                main.setBackgroundColor(getResources().getColor(R.color.brown));
                Button brown_btn = findViewById(R.id.brown_btn);
                buttons.removeView(brown_btn);
                hidden_btn = brown_btn;
                break;
            case "grey":
                main.setBackgroundColor(getResources().getColor(R.color.grey));
                Button grey_btn = findViewById(R.id.grey_btn);
                buttons.removeView(grey_btn);
                hidden_btn = grey_btn;
                break;
        }
    }

    public void on_color_buttons_click (View view) {
        LinearLayout buttons = findViewById(R.id.buttons);
        int btn_pos = buttons.indexOfChild(view);
        buttons.addView(hidden_btn, btn_pos); //проблема с добавлением кнопки
        buttons.removeView(view);
        hidden_btn = (Button) view;
        View main = findViewById(R.id.container);

        SharedPreferences settings = getSharedPreferences("settings", MODE_PRIVATE);
        SharedPreferences.Editor ed = settings.edit();

        switch (view.getId()) {
            case R.id.aqua_btn:
                main.setBackgroundColor(getResources().getColor(R.color.aqua));
                ed.putString("bgcolor", "aqua");
                break;
            case R.id.red_btn:
                main.setBackgroundColor(getResources().getColor(R.color.red));
                ed.putString("bgcolor", "red");
                break;
            case R.id.brown_btn:
                main.setBackgroundColor(getResources().getColor(R.color.brown));
                ed.putString("bgcolor", "brown");
                break;
            case R.id.grey_btn:
                main.setBackgroundColor(getResources().getColor(R.color.grey));
                ed.putString("bgcolor", "grey");
                break;
        }
        ed.apply();
    }

    public void fonts() {
        Typeface shrift = Typeface.createFromAsset(getAssets(), "fonts/Nunito-Bold.ttf");
        TextView text = findViewById(R.id.up);
        TextView text1 = findViewById(R.id.down);
        text.setTypeface(shrift);
        text1.setTypeface(shrift);
    }

    public void first_zitata() {
        TextView zitata = findViewById(R.id.zitata);
        TextView avtor = findViewById(R.id.avtor);
        Typeface shrift = Typeface.createFromAsset(getAssets(), "fonts/Nunito-Bold.ttf");
        Typeface shrift2 = Typeface.createFromAsset(getAssets(), "fonts/Nunito-ExtraBold.ttf");
        zitata.setTypeface(shrift);
        avtor.setTypeface(shrift2);
        zamena_zitati_button_click(zitata);
    }

    public void zamena_zitati_button_click(View view) {
        String [] text = QuoteParser.get_quote(this);
        TextView tview = findViewById(R.id.zitata);
        tview.setText(text[0]);
        TextView tview1 = findViewById(R.id.avtor);
        tview1.setText(text[1]);
        Typeface shrift = Typeface.createFromAsset(getAssets(), "fonts/Nunito-Bold.ttf");
        Typeface shrift2 = Typeface.createFromAsset(getAssets(), "fonts/Nunito-ExtraBold.ttf");
        tview.setTypeface(shrift);
        tview1.setTypeface(shrift2);
    }

    public static ArrayList<String> zitati(String text) {
        // String text = "zit.@imya\nzit1.@imya1\nzit2.@imya2";
        ArrayList<String> spisok = new ArrayList<>();
        String[] zitata = text.split("\n");
        for (String i : zitata) {
            spisok.add(i);
        }
        return spisok;
    }
    Animation anim, anim_back;
    public void on_settings_button_click(View view) {
        //view.setClickable(false);
        anim = AnimationUtils.loadAnimation(this, R.anim.screen_scale);
        anim_back = AnimationUtils.loadAnimation(this, R.anim.scale_screen_back);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                View main = findViewById(R.id.main_main);
                main.startAnimation(anim_back);
                //View settings = findViewById(R.id.settings);
                //settings.startAnimation(anim);
            }
        }, 0);
        Handler handler2 = new Handler();
        handler2.postDelayed(new Runnable() {
            public void run() {
                Intent intent = new Intent(MainActivity.this, Settings_activity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        }, 0);
    }

    public void animation_load () {

            ImageView load_img = (ImageView) findViewById(R.id.load_img);
            load_img.setBackgroundResource(R.drawable.animation_load);
            final AnimationDrawable animation = (AnimationDrawable) load_img.getBackground();
            animation.start();

    }

    public List<Deal> deal = new ArrayList<>();
    public void import_deals () {
            deal = JSONHelper.importFromJSON(this);
            if (deal == null) {
                deal = new ArrayList<>();
            }

            if (deal.size() == 1) {
                LayoutInflater lay = getLayoutInflater();
                LinearLayout sv = findViewById (R.id.scroll);
                sv.removeAllViews();
                Deal last_child = deal.get(0);
                RelativeLayout view1 = (RelativeLayout) lay.inflate(R.layout.deal_layout, null, false);
                LinearLayout ll = (LinearLayout) view1.getChildAt(0);
                TextView text = (TextView) ll.getChildAt(0);
                TextView date = (TextView) ll.getChildAt(1);
                text.setText(last_child.text);
                date.setText("Вы запланировали это на " + last_child.date + "в" + last_child.time);
                if (last_child.dateAndTime.get(Calendar.MONTH) == new Date(System.currentTimeMillis()).getMonth()) {
                    date.setText("Завтра");
                }
            }
            else if (deal.size() > 1) {
                LayoutInflater lay = getLayoutInflater();
                LinearLayout sv = findViewById (R.id.scroll);
                sv.removeAllViews();
                for (Deal i:deal) {
                    RelativeLayout view1 = (RelativeLayout) lay.inflate(R.layout.deal_layout, null, false);
                    LinearLayout ll = (LinearLayout) view1.getChildAt(0);
                    TextView text = (TextView) ll.getChildAt(0);
                    TextView date = (TextView) ll.getChildAt(1);
                    text.setText(i.text);
                    date.setText("Вы запланировали это на " + i.date + " в " + i.time);
                    // Сделать через среду разработки JAVA
                    if (i.dateAndTime.get(Calendar.MONTH) == new Date(System.currentTimeMillis()).getMonth() && i.dateAndTime.get(Calendar.DATE) == new Date(System.currentTimeMillis()).getDate() ) {
                        date.setText("Вы запланировали это на завтра в " + i.time);
                    }
                    // Сделать через среду разработки JAVA
                    sv.addView(view1);
                }
                //for (Deal i:deal) {
                //Date date_max = new Date(System.currentTimeMillis());
                //if (i.dateAndTime.getTime().after(date_max) == true) {
                // deal.set(0, i);
                //date_max == i.dateAndTime.getTime();
                //}
                //}
            }

        TextView d1 = findViewById(R.id.up);
        TextView d2 = findViewById(R.id.down);
        int size = deal.size();
        if (size == 1) {
            d1.setText("Отлично, Вы добавили первое дело!");
            d2.setText("Не останавливайтесь на достигнутом!");
        }
        else if (size <= 4 && size > 1) {
            d1.setText("Ух ты, да Вы растёте на глазах!");
            d2.setText("Правильно рассчитывайте время на каждое дело!");
        }
        else if (size > 4) {
            d1.setText("Вы уже занятой человек!");
            d2.setText("У Вас всё получится!");
        }
        else {
            d1.setText("Пока дел нет.");
            d2.setText("Не будьте беззаботными :)!");
        }
    }

    public void test (View view) {
        LayoutInflater lay = getLayoutInflater();
        LinearLayout sv = findViewById (R.id.scroll);
        RelativeLayout view1 = (RelativeLayout) lay.inflate(R.layout.deal_layout, null, true);
        LinearLayout ll = (LinearLayout) view1.getChildAt(0);
        TextView text = (TextView) ll.getChildAt(0);
        TextView date = (TextView) ll.getChildAt(1);
        TextView time = (TextView) ll.getChildAt(2);
        text.setText("ffgffg");
        date.setText("dgdddgdgdgdgg");
        time.setText("ff");
        sv.addView(view1);
    }

    public void editing_deal (View view) {
        Intent intent = new Intent(MainActivity.this, NewDealActivity.class);
        LinearLayout vw = (LinearLayout) view.getParent().getParent().getParent();
        String head = ((TextView) vw.getChildAt(0)).getText().toString();
        for (Deal i:deal) {
            if (i.text == head) {
                intent.putExtra("text", i.text);
                intent.putExtra("date", i.date);
                intent.putExtra("time", i.time);
                intent.putExtra("dateAndTime", i.dateAndTime);
                startActivity(intent);
            }
        }
    }

    public void delete_deal (View view) {
        LinearLayout vw = (LinearLayout) view.getParent().getParent().getParent();
        RelativeLayout vw_1 = (RelativeLayout) view.getParent().getParent().getParent().getParent();
        String head = ((TextView) vw.getChildAt(0)).getText().toString();
        LinearLayout sw = findViewById(R.id.scroll);
        for (int i = 0; i < deal.size(); i++) {
            if (deal.get(i).text == head) {
                deal.remove(i);
                sw.removeView(vw_1);
                break;
            }
        }
        boolean result = JSONHelper.exportToJSON(this, deal);
    }


}