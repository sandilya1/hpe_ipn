package com.hpe.ipn;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AdminActivity extends Activity {


    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        expListView = (ExpandableListView) findViewById(R.id.lvExp);

        prepareListData();

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);


        expListView.setAdapter(listAdapter);


        expListView.setOnGroupClickListener(new OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {

                return false;
            }
        });


        expListView.setOnGroupExpandListener(new OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
//                Toast.makeText(getApplicationContext(),
//                        listDataHeader.get(groupPosition) + " Expanded",
//                        Toast.LENGTH_SHORT).show();

                ExpandableListAdapter customList = (ExpandableListAdapter) expListView.getExpandableListAdapter();
                if(customList == null){
                    return;
                }
                for(int i=0; i < customList.getGroupCount() ; i++){
                        if( i !=groupPosition){
                            expListView.collapseGroup(i);
                        }
                }
            }
        });


        expListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
//                Toast.makeText(getApplicationContext(),
//                        listDataHeader.get(groupPosition) + " Collapsed",
//                        Toast.LENGTH_SHORT).show();

            }
        });


        expListView.setOnChildClickListener(new OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, final int childPosition, long id) {

//                final RadioButton cb = (RadioButton)v.findViewById( R.id.check1 );
////                cb.getTag();
//                cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
//                    HashMap<String , String > checkedState = new HashMap<String , String >();
//                    @Override
//                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                        if( cb.isChecked()){cb.toggle();}
//
//                        int groupPosition = Integer.parseInt((String)buttonView.getHint());
//                        ToggleButton toggleButton = checkedState.get(groupPosition);
//                    }
//
//
//                });


//                if(cb.isChecked()){
//                    cb.toggle();
//                }

                return false;
            }

        });
    }

    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        listDataHeader.add("Polls");
        listDataHeader.add("Assign Admin");
        listDataHeader.add("Results");

        List<String> polls = new ArrayList<String>();
        polls.add("Poll 1");
        polls.add("Poll 2");
        polls.add("Poll 3");
        polls.add("Poll 4");

        List<String> assignAdmin = new ArrayList<String>();

        List<String> results = new ArrayList<String>();
        results.add("Result 1");
        results.add("Result 2");
        results.add("Result 3");
        results.add("Result 4");

        listDataChild.put(listDataHeader.get(0), polls);
        listDataChild.put(listDataHeader.get(1), assignAdmin);
        listDataChild.put(listDataHeader.get(2), results);
    }

    public void exitApp(View view){
        finish();
    }



}