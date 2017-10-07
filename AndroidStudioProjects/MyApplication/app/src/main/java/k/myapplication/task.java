package k.myapplication;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Created by KSHITIJA PANDYA on 01-10-2017.
 */

public class task {
    public String task;
    public Calendar cal;
    public int id;

    public task() {
        this.task="";
        this.cal=Calendar.getInstance();
    }

    public task(String task,int id,Calendar cal) {
        this.task = task;
        this.id=id;
        this.cal=cal;
    }
}
