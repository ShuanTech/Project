package com.shuan.Project.Utils;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;


public class Helper implements Methods {

    public Typeface tf;
    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;


    @Override
    public Typeface roboto(Context context) {
        tf = Typeface.createFromAsset(context.getAssets(), "fonts/roboto.ttf");
        return tf;
    }

    @Override
    public Typeface droid(Context context) {
        tf = Typeface.createFromAsset(context.getAssets(), "fonts/droid.ttf");
        return tf;
    }

    @Override
    public String getTimeAgo(Context mContext, String created) {
        String val = null;

        Calendar cal = Calendar.getInstance();
        TimeZone tz = cal.getTimeZone();

        Log.d("Time zone: ", tz.getDisplayName());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(tz);
        try {
            Date dt=sdf.parse(created);

            //val=sdf.format(dt).toString();

            long time= dt.getTime();
            Date dat=new Date();
            long now = dat.getTime();

            if (time < 1000000000000L) {
                // if timestamp given in seconds, convert to millis
                time *= 1000;
            }

            final long diff = now - time;
            if (diff < MINUTE_MILLIS) {
                val= "just now";
            } else if (diff < 2 * MINUTE_MILLIS) {
                val= "a minute ago";
            } else if (diff < 50 * MINUTE_MILLIS) {
                val= diff / MINUTE_MILLIS + " minutes ago";
            } else if (diff < 90 * MINUTE_MILLIS) {
                val= "an hour ago";
            } else if (diff < 24 * HOUR_MILLIS) {
                val= diff / HOUR_MILLIS + " hours ago";
            } else if (diff < 48 * HOUR_MILLIS) {
                val= "yesterday";
            } else {
                val= diff / DAY_MILLIS + " days ago";
            }


        } catch (ParseException e) {
            e.printStackTrace();
        }

        return val;
    }


}
