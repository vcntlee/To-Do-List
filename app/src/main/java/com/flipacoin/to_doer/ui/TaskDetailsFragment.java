package com.flipacoin.to_doer.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.flipacoin.to_doer.R;
import com.flipacoin.to_doer.data.Task;
import com.flipacoin.to_doer.database.TaskDataSource;

import java.util.ArrayList;

public class TaskDetailsFragment extends Fragment {

    public static final String KEY = "TASK_DETAILS";
    private Task mTarget;
    private int mPosition;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        int index = getArguments().getInt(KEY);
        mPosition = index;
        TaskDataSource data = new TaskDataSource(getActivity());
        ArrayList<Task> tasks = data.retrieve();

        Log.i(KEY, tasks.get(index).getName());

        mTarget = tasks.get(index);

        setHasOptionsMenu(true);


        View view = inflater.inflate(R.layout.fragment_task_details, container, false);

        TextView taskName = (TextView) view.findViewById(R.id.taskName);
        TextView taskDate = (TextView) view.findViewById(R.id.taskDate);
        TextView taskNotes = (TextView) view.findViewById(R.id.taskNotes);
        TextView taskLevel = (TextView) view.findViewById(R.id.taskLevel);
        TextView taskStatus = (TextView) view.findViewById(R.id.taskStatus);

        taskName.setText(mTarget.getName());
        taskDate.setText(mTarget.getDate());
        taskNotes.setText(mTarget.getNotes());
        taskLevel.setText(mTarget.getLevel()+"");
        taskStatus.setText(mTarget.getStatus()+"");


        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.details_toolbar, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.editTask){

            AddFragment fragment = new AddFragment();

            Bundle bundle = new Bundle();
            bundle.putInt(KEY, mPosition);
            fragment.setArguments(bundle);

            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.placeHolder, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();


            return true;
        }
        if (item.getItemId() == R.id.deleteTask){
            TaskDataSource data = new TaskDataSource(getActivity());
            data.delete(mTarget);

            ListFragment listFragment = new ListFragment();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.placeHolder, listFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

        }

        return super.onOptionsItemSelected(item);
    }
}
