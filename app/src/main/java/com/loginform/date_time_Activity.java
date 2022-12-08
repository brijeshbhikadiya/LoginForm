package com.loginform;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class date_time_Activity extends AppCompatActivity {

    EditText dateedit,timeedit;
    Calendar calendar;

    int iHour,iMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_time);

        dateedit=findViewById(R.id.date_select);
        timeedit=findViewById(R.id.time_select);

        TimePickerDialog.OnTimeSetListener timeclick=new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                iHour = i;
                iMinute = i1;

                String sAMPM;
                if (iHour > 12) {
                    iHour -= 12;
                    sAMPM = "PM";
                } else if (iHour == 12) {
                    sAMPM = "PM";
                } else if (iHour == 0) {
                    iHour = 12;
                    sAMPM = "AM";
                } else {
                    sAMPM = "AM";
                }

                String sMinute;
                if (iMinute < 10) {
                    sMinute = "0" + iMinute;
                }
                else
                {
                    sMinute= String.valueOf(iMinute);
                }
                timeedit.setText(iHour + ":" + sMinute+ sAMPM);
            }
        };

        timeedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new TimePickerDialog(date_time_Activity.this,timeclick,iHour,iMinute,false).show();
            }
        });

        calendar =Calendar.getInstance();

        DatePickerDialog.OnDateSetListener dateclick=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                calendar.set(Calendar.YEAR,i);
                calendar.set(Calendar.MONTH,i1);
                calendar.set(Calendar.DAY_OF_MONTH,i2);

                SimpleDateFormat dateFormat=new SimpleDateFormat("dd-mm-yyyy",Locale.getDefault());
                dateedit.setText(dateFormat.format(calendar.getTime()));
            }
        };

            dateedit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DatePickerDialog datedialog=new DatePickerDialog(date_time_Activity.this,dateclick,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
                    datedialog.getDatePicker().setMinDate(System.currentTimeMillis());
                    //datedialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                    datedialog.show();
                }
            });


    }
}