package com.flipacoin.to_doer.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.flipacoin.to_doer.R;
import com.flipacoin.to_doer.data.Task;
import com.flipacoin.to_doer.database.TaskDataSource;

import java.util.ArrayList;
import java.util.Calendar;

public class AddFragment extends Fragment {

    private Task mTask;
    private boolean flag = true;
    private final String [] levelArray = {"Low", "Mid", "High"};
    private final String [] statusArray = {"Not Completed", "Completed"};


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        int index = -1;
        if (getArguments() != null) {
            index = getArguments().getInt(TaskDetailsFragment.KEY);
            flag = false;
        }

        View view = inflater.inflate(R.layout.fragment_add, container, false);

        final EditText taskName = (EditText) view.findViewById(R.id.taskName);
        DatePicker taskDate = (DatePicker) view.findViewById(R.id.taskDate);
        EditText taskNotes = (EditText) view.findViewById(R.id.taskNotes);
        Spinner taskLevel = (Spinner) view.findViewById(R.id.taskLevel);
        Spinner taskStatus = (Spinner) view.findViewById(R.id.taskStatus);
        mTask = new Task();

        ArrayAdapter<String> levelAdapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_item, levelArray);
        levelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        taskLevel.setAdapter(levelAdapter);

        ArrayAdapter<String> statusAdapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_item, statusArray);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        taskStatus.setAdapter(statusAdapter);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int year = calendar.get(Calendar.YEAR);

        mTask.setDate(String.format("%d-%d-%d",month+1, day, year));

        if (!flag){
            TaskDataSource data = new TaskDataSource(getActivity());
            ArrayList<Task> tasks = data.retrieve();
            mTask = tasks.get(index);
            taskName.setText(mTask.getName());
            taskNotes.setText(mTask.getNotes());
            taskLevel.setSelection(mTask.getLevel());
            taskStatus.setSelection(mTask.getStatus());

            String [] dateArray = mTask.getDate().split("-");
            month = Integer.parseInt(dateArray[0]) - 1;
            day = Integer.parseInt(dateArray[1]);
            year = Integer.parseInt(dateArray[2]);

            //taskDate.updateDate(year, month-1, day);
        }



        taskName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mTask.setName(s.toString());
                if (!flag){
                    TaskDataSource data = new TaskDataSource(getActivity());
                    data.update(mTask, mTask.getName(), null, null, -1, -1);
                }
            }
        });

        taskNotes.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mTask.setNotes(s.toString());

                if (!flag) {
                    TaskDataSource data = new TaskDataSource(getActivity());
                    data.update(mTask, null, null, mTask.getNotes(), -1, -1);
                }

            }
        });


        taskLevel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mTask.setLevel(position);
                if (!flag) {
                    TaskDataSource data = new TaskDataSource(getActivity());
                    data.update(mTask, null, null, null, mTask.getLevel(), -1);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        taskStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mTask.setStatus(position);
                if (!flag) {
                    TaskDataSource data = new TaskDataSource(getActivity());
                    data.update(mTask, null, null, null, -1, mTask.getStatus());
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        taskDate.init(year, month, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                mTask.setDate(String.format("%d-%d-%d", monthOfYear+1, dayOfMonth, year));

                if (!flag) {
                    TaskDataSource data = new TaskDataSource(getActivity());
                    data.update(mTask, null, mTask.getDate(), null, -1, -1);
                }
            }
        });


        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.add_toolbar, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.ok){
            TaskDataSource dataSource = new TaskDataSource(getActivity());
            if (flag && isValid()) {
                dataSource.create(mTask);
            }else if (flag && !isValid()){
                Toast.makeText(getActivity(), "Please enter task name", Toast.LENGTH_SHORT).show();
                return true;
            }
        }
        if (item.getItemId() == R.id.cancel){

        }
        ListFragment fragment = new ListFragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.placeHolder, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

        return super.onOptionsItemSelected(item);
    }

    private boolean isValid(){
        if (mTask.getName().equals(""))
            return false;
        return true;
    }
}
