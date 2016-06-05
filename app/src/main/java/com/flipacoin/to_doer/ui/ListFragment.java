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
import android.widget.AdapterView;
import android.widget.ListView;

import com.flipacoin.to_doer.R;
import com.flipacoin.to_doer.adapter.TaskAdapter;
import com.flipacoin.to_doer.data.Task;
import com.flipacoin.to_doer.database.TaskDataSource;

import java.util.ArrayList;

public class ListFragment extends Fragment{

    private ArrayList<Task> mTasks;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        setHasOptionsMenu(true);

        View view = inflater.inflate(R.layout.fragment_list, container, false);
        ListView listView = (ListView) view.findViewById(android.R.id.list);

        TaskDataSource dataSource = new TaskDataSource(getActivity());
        mTasks = dataSource.retrieve();

        Log.i("fragmentZ", mTasks.size()+"");

        TaskAdapter adapter = new TaskAdapter(getActivity(), mTasks);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                TaskDetailsFragment fragment = new TaskDetailsFragment();

                Bundle bundle = new Bundle();
                bundle.putInt(TaskDetailsFragment.KEY, position);
                fragment.setArguments(bundle);

                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.placeHolder, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        return view;

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.listview_toolbar, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.addTask){

            AddFragment addFragment = new AddFragment();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.placeHolder, addFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            return true;
        }



        return super.onOptionsItemSelected(item);
    }
}
