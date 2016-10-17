package com.example.iway.codersinc.Contests;

import android.app.ProgressDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.iway.codersinc.DataAdapter;
import com.example.iway.codersinc.DataModel;
import com.example.iway.codersinc.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IWAY on 19-09-2016.
 */
public class CurrentContests extends Fragment {

    private String TAG = CurrentContests.class.getSimpleName();
    RecyclerView rv;
    ProgressDialog pdialog;
    public DataAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    static View v;
    private static ArrayList<DataModel> data;
    public static DatabaseHandler db;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        v = inflater.inflate(R.layout.currentcontests_layout, container, false);
        data = new ArrayList<>();
        db=new DatabaseHandler(getActivity());
        rv = (RecyclerView) v.findViewById(R.id.tab1_recycler_view);
        rv.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(layoutManager);
        rv.setItemAnimator(new DefaultItemAnimator());
        adapter = new DataAdapter(data,getActivity());
        rv.setAdapter(adapter);
       /* new GetContacts().execute();*/
        //c.moveToFirst();
        showRecords();
        return v;
    }
    public void showRecords() {
        // Reading all contacts
        Log.d("Reading: ", "Reading all contacts..");
        List<Contact> contacts = db.getAllContacts();
        for (Contact cn : contacts) {

            String typ=cn.getType();
            String link=cn.getLink();
            String type=cn.getType();
            if(typ.equals("present")) {
                DataModel dm = new DataModel(cn.getSite(), cn.getName(), cn.getBegindate(), cn.getBegin(), cn.getEnddate(), cn.getEnd(),link,type);
                data.add(dm);
            }
            else
            {
                break;
            }

        }
        adapter.notifyDataSetChanged();
    }
}