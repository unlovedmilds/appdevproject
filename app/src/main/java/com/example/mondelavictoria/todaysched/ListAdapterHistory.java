package com.example.mondelavictoria.todaysched;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Mon Dela Victoria on 9/29/2018.
 */

public class ListAdapterHistory extends BaseAdapter {

    public ArrayList<ClassHistory_Diagram> sched;
    private Context context;
    private  int layout;
    View row;
    ListAdapterHistory .ViewHolder holder;


    public ListAdapterHistory (Context context,int layout,ArrayList<ClassHistory_Diagram> sched) {
        this.context = context;
        this.layout = layout;
        this.sched = sched;
    }
    @Override
    public int getCount() {
        return sched.size();
    }

    @Override
    public ClassHistory_Diagram getItem(int position) {
        return sched.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder{
        TextView tvSubject,tvLocation,tvStartTime,tvEndTime ;


    }

    @Override
    public View getView(final int position, View convertView
            , ViewGroup parent) {



        if(convertView == null)
        {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.history_griview_layout,parent,false);
            holder = new ListAdapterHistory .ViewHolder();




            holder.tvSubject = row.findViewById(R.id.textViewSubject);
            holder.tvLocation = row.findViewById(R.id.textViewLocation);
            holder.tvStartTime = row.findViewById(R.id.textViewStartTime);
            holder.tvEndTime = row.findViewById(R.id.textViewEndTime);

            row.setTag(holder);



        }

        else
        {
            row=convertView;
            holder= (ListAdapterHistory .ViewHolder) row.getTag();

        }
        if( position % 2 == 1){
            row.setBackgroundColor(Color.parseColor("#ffb300"));
        }else{
            row.setBackgroundColor(Color.parseColor("#ffe57f"));
        }
        ClassHistory_Diagram task = sched.get(position);

        holder.tvSubject.setText(task.getSubject());
        holder.tvLocation.setText(task.getLocation());
        holder.tvStartTime.setText(task.getStartTime());
        holder.tvEndTime.setText(task.getEndTime());



        // Get the Layout Parameters for ListView Current Item View
        ViewGroup.LayoutParams params = row.getLayoutParams();

        // Set the height of the Item View
        params.height = 100;
        row.setLayoutParams(params);




        return row;
    }



}
