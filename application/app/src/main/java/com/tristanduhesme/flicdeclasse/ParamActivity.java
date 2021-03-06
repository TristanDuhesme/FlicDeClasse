package com.tristanduhesme.flicdeclasse;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.SimpleTimeZone;




/**
 * Created by Tristan on 20/12/2017.
 */

public class ParamActivity extends Activity {
    private EditText hourEditText;
    private EditText toleranceEditText;
    private EditText dateEditText;

    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;

    private SimpleDateFormat dateFormatter;
    private SimpleTimeZone timeFormatter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_param);
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        timeFormatter = new SimpleTimeZone(0, "hh-mm-ss");

        findViewsById();

        setDateTimeField();
        setHourField();
    }
    private void findViewsById() {
        dateEditText = (EditText) findViewById(R.id.dateEditText);
        dateEditText.setInputType(InputType.TYPE_NULL);
       // dateEditText.requestFocus();
        hourEditText = (EditText) findViewById(R.id.hourEditText);
        hourEditText.setInputType(InputType.TYPE_NULL);
        //hourEditText.requestFocus();
        toleranceEditText = (EditText) findViewById(R.id.toleranceEditText);
        //toleranceEditText.requestFocus();
    }

    private void setDateTimeField() {
        dateEditText.setOnClickListener(showDatePicker);

        Calendar newCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                dateEditText.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }
    private void setHourField() {
        hourEditText.setOnClickListener(showTimePicker);

        Calendar newCalendar = Calendar.getInstance();
        timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                hourEditText.setText(getString(R.string.timeFormat,i,i1));
            }
        },newCalendar.get(Calendar.HOUR), newCalendar.get(Calendar.MINUTE),true);
    }

    private OnClickListener showDatePicker = new OnClickListener() {
        @Override
        public void onClick(View view) {
            if (view == dateEditText) {
                datePickerDialog.show();
            }
        }
    };
    private OnClickListener showTimePicker = new OnClickListener() {
        @Override
        public void onClick(View view) {
            if (view == hourEditText) {
                timePickerDialog.show();
            }
        }
    };

}
