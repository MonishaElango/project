package com.hertzai.hevolve.models;

import androidx.annotation.Nullable;

public class EventDayModel {

    private int dayOfMonth, month, year;


    public EventDayModel(int dayOfMonth, int month, int year) {
        this.dayOfMonth = dayOfMonth;
        this.month = month;
        this.year = year;
    }

    public int getDayOfMonth() {
        return dayOfMonth;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof EventDayModel) {
            return ((EventDayModel) obj).getDayOfMonth() == dayOfMonth && ((EventDayModel) obj).getMonth() == month && ((EventDayModel) obj).getYear() == year;
        }else{
            return super.equals(obj);
        }
    }
}
