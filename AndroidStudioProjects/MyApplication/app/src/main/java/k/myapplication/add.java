package k.myapplication;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.EditText;

import java.util.Calendar;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;




public class add extends AppCompatActivity implements View.OnClickListener {
    EditText date,time,title;
    Button button;
    private int mYear, mMonth, mDay, mHour, mMinute;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        date =(EditText)findViewById(R.id.editText2);
        time=(EditText)findViewById(R.id.editText3);
        button=(Button)findViewById(R.id.button);
        title=(EditText) findViewById(R.id.editText) ;
        date.setOnClickListener(this);
        button.setOnClickListener(this);
        time.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v==date)
        {
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            time.setVisibility(View.VISIBLE);


                        }
                    }, mYear, mMonth, mDay);

            datePickerDialog.show();
        }
        if (v == time) {

            // Get Current Time
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                            time.setText(hourOfDay + ":" + minute);
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        }
        if (v==button)
        {
            String a=(String)title.getText().toString();
            String b=(String)date.getText().toString();
            String c=(String)time.getText().toString();
            Taskopenhelper openHelper = Taskopenhelper.getInstance(getApplicationContext());
            SQLiteDatabase db = openHelper.getWritableDatabase();

            ContentValues contentValues = new ContentValues();
            contentValues.put(Contract.TASK_TITLE,a);
            contentValues.put(Contract.TASK_DATE,b+" "+c);

//            contentValues.put(Contract.EXPENSE_AMOUNT,amount);

            long id = db.insert(Contract.TASK_TABLE_NAME,null,contentValues);



            Intent intent=new Intent();
            intent.putExtra("ID",id);
            intent.putExtra("TASK",a);
            intent.putExtra("DATE",b);
            intent.putExtra("TIME",c);
            setResult(1,intent);
            finish();

        }

    }
}
