package com.shuan.Project.fragment;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.app.DialogFragment;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;


@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class DateDialog extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    EditText txtDate;

    public DateDialog() {
    }

    public DateDialog(View view){
        txtDate= (EditText) view;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        final Calendar c=Calendar.getInstance();
        int year=c.get(Calendar.YEAR);
        int month=c.get(Calendar.MONTH);
        int day=c.get(Calendar.DAY_OF_MONTH);
        Calendar minPickerDate = Calendar.getInstance();
        minPickerDate.set(Calendar.YEAR, 1950);
        DatePickerDialog picker= new DatePickerDialog(getActivity(),this,year,month,day);
        picker.getDatePicker().setMinDate(minPickerDate.getTimeInMillis());
        picker.getDatePicker().setMaxDate(c.getTimeInMillis());
        return  picker;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        String date=dayOfMonth+"-"+(monthOfYear+1)+"-"+year;
        txtDate.setText(date);
    }
}
