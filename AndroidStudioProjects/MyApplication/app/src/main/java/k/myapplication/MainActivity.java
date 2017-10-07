package k.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TimeFormatException;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;

import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.text.DateFormat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    CustomAdaptor customAdaptor;
    ArrayList<task> tasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listview);
        tasks = new ArrayList<>();
        customAdaptor = new CustomAdaptor(this, tasks);
        listView.setAdapter(customAdaptor);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
         Taskopenhelper openHelper=Taskopenhelper.getInstance(getApplicationContext());
        SQLiteDatabase db = openHelper.getReadableDatabase();


        Cursor cursor = db.query(Contract.TASK_TABLE_NAME,null,null,null,null,null,null);
        db.delete(Contract.TASK_TABLE_NAME,Contract.TASK_ID + " = ?",new String[]{"1"});
        while (cursor.moveToNext()){

            String title = cursor.getString(cursor.getColumnIndex(Contract.TASK_TITLE));
           // int amount = cursor.getInt(cursor.getColumnIndex(Contract.EXPENSE_AMOUNT));
            int id = cursor.getInt(cursor.getColumnIndex(Contract.TASK_ID));
            String b=(String)cursor.getString(cursor.getColumnIndex(Contract.TASK_DATE));
           // String c=(String)cursor.getString(cursor.getColumnIndex(Contract.TASK_TIME));
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy HH:mm:ss");
            try{ cal.setTime(sdf.parse(b+":00"));}catch (java.text.ParseException e){}


            task t1 = new task(title,id,cal);
            tasks.add(t1);


        }

        cursor.close();
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, final long id) {

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Delete Task");
                builder.setMessage("Are you sure?");
                builder.setCancelable(false);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        task a=new task();
                        a=tasks.get(position);
                        int x=a.id;

                        tasks.remove(position);
                        Taskopenhelper openHelper=Taskopenhelper.getInstance(getApplicationContext());
                        SQLiteDatabase db=openHelper.getWritableDatabase();
                        Cursor cursor = db.query(Contract.TASK_TABLE_NAME,null,null,null,null,null,null);
                        String y[]={x+""};

                        db.delete(Contract.TASK_TABLE_NAME,Contract.TASK_ID+"= ?",y);
                        customAdaptor.notifyDataSetChanged();
                        cursor.close();

                    }

                });
                builder.setNegativeButton("No", null);

                AlertDialog dialog = builder.create();
                dialog.show();
                return true;
            }});




                final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view == fab) {
                    Intent intent = new Intent(MainActivity.this, add.class);
                    startActivityForResult(intent, 0);
                }
            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==0)
        {
            if(resultCode==1)
            {
                task t=new task();
                long id=data.getLongExtra("ID",-1);
                t.task=data.getStringExtra("TASK");
                String b=data.getStringExtra("DATE");
                String c=data.getStringExtra("TIME");
                Calendar cal = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy HH:mm:ss");
               try{ cal.setTime(sdf.parse(b+" "+c+":00"));}catch (java.text.ParseException e){}

                Taskopenhelper openHelper = Taskopenhelper.getInstance(getApplicationContext());
                SQLiteDatabase db = openHelper.getReadableDatabase();
                String x[]={id+""};

                Cursor cursor=db.query(Contract.TASK_TABLE_NAME,null,Contract.TASK_ID+"= ?",x,null,null,null);
                if(cursor.moveToFirst()){
                    String title = cursor.getString(cursor.getColumnIndex(Contract.TASK_TITLE));
                    //int amount = cursor.getInt(cursor.getColumnIndex(Contract.EXPENSE_AMOUNT));
                    task t2 = new task(title,(int)id,cal);
                    tasks.add(t2);
                    customAdaptor.notifyDataSetChanged();
                }

            }
        }

        super.onActivityResult(requestCode, resultCode, data);


    }
}