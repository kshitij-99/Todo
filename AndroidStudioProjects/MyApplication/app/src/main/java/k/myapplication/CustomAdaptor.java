package k.myapplication;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by KSHITIJA PANDYA on 02-10-2017.
 */

public class CustomAdaptor extends ArrayAdapter<task> {
    ArrayList<task> mTasks;
    Context mContext;

    public CustomAdaptor(@NonNull Context context, ArrayList<task> tasks) {
        super(context, 0);

        mContext = context;
        mTasks = tasks;


    }

    @Override
    public int getCount() {
        return mTasks.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.row,null);
            viewHolder = new ViewHolder();
            TextView title = (TextView) convertView.findViewById(R.id.task);
            TextView date = (TextView) convertView.findViewById(R.id.date);


          //  TextView amount = (TextView) convertView.findViewById(R.id.time);
            viewHolder.title = title;
            viewHolder.date=date;
            //viewHolder.amount = amount;

            convertView.setTag(viewHolder);
        }

        viewHolder = (ViewHolder)convertView.getTag();

        task item = mTasks.get(position);
        viewHolder.title.setText(item.task);
        viewHolder.date.setText(item.cal.getTime().toString());

        return convertView;
    }


    static class ViewHolder {

        TextView title;
        TextView date;

    }


}
