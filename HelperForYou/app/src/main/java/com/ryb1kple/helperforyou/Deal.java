package com.ryb1kple.helperforyou;

import java.util.Calendar;

public class Deal {
    String text;
    String time;
    String date;
    Calendar dateAndTime;
    Deal(String time, String text, String date, Calendar dateAndTime){
        this.text = text;
        this.time = time;
        this.date = date;
        this.dateAndTime = dateAndTime;
    }
}
