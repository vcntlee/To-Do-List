package com.flipacoin.to_doer.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.flipacoin.to_doer.R;
import com.flipacoin.to_doer.data.Task;

import java.util.ArrayList;

public class TaskAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<Task> mTasks;



    public TaskAdapter(Context context, ArrayList<Task> tasks){
        mContext = context;
        mTasks = tasks;
    }


    @Override
    public int getCount() {
        return mTasks.size();
    }

    @Override
    public Object getItem(int position) {
        return mTasks.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item, null);
            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.taskName);
            holder.level = (TextView) convertView.findViewById(R.id.taskLevel);
            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }

        Task task = mTasks.get(position);
        holder.name.setText(task.getName());
        if (task.getLevel() == 0) {
            holder.level.setText("Low");
            holder.level.setTextColor(Color.parseColor("#448AFF"));
        }
        else if(task.getLevel() == 1){
            holder.level.setText("Mid");
            holder.level.setTextColor(Color.parseColor("#AFB42B"));

        }
        else{
            holder.level.setText("High");
            holder.level.setTextColor(Color.parseColor("#D32F2F"));

        }

        if (task.getStatus() == 1){
            holder.name.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            holder.level.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        }


        return convertView;

    }

    private static class ViewHolder{
        TextView name;
        TextView level;

    }
}
