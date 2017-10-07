package k.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by KSHITIJA PANDYA on 05-10-2017.
 */

public class Taskopenhelper extends SQLiteOpenHelper {

    private static Taskopenhelper instance;


    public static Taskopenhelper getInstance(Context context) {
        if(instance == null){
            instance = new Taskopenhelper(context);
        }
        return instance;
    }

    private Taskopenhelper(Context context) {
        super(context, "task_db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + Contract.TASK_TABLE_NAME + " ( " +
                Contract.TASK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Contract.TASK_TITLE + " TEXT, " +
                Contract.TASK_DATE + " TEXT)";

        db.execSQL(query);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
