package com.ryb1kple.helperforyou;

import android.graphics.Typeface;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Random;

public class QuoteParser {

    public static String[] get_quote(AppCompatActivity activity) {
        Random rand = new Random();
        ArrayList<String> generaziya = zitati(activity.getString(R.string.zitati));
        int zislo = rand.nextInt(generaziya.size());
        String stroka = generaziya.get(zislo);
        String[] otdelno = stroka.split("@");
        String gotovaya_zitata = otdelno[0].substring(1).toUpperCase();
        String gotovyi_avtor = otdelno[1].toUpperCase();
        String [] result = {gotovaya_zitata, gotovyi_avtor};
        return result;
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
}
