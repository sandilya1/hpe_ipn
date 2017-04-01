package com.hpe.ipn;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class ResultsActivity extends Activity {

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Log.i("ResultsActivity.class", "doInBackground: Results In Progress");
        final String app_result;
        final List<String> stringList = new ArrayList<String>();

        final JSONArray jsonArray = new JSONArray();
//            databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference("Voting");

        databaseReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    VoteInfo voteInfo =  new VoteInfo();
                    Log.i("ResultsActivity.class", "onDataChange: "+ ds);
                    voteInfo.setVote(ds.child("vote").getValue(String.class));
                    String set_vote = voteInfo.getVote();
                    stringList.add(set_vote);
                    Log.i("ResultsActivity.class", "onDataChange Set: "+ set_vote);
                }
                Set<String> items = new HashSet<String>(stringList);
                HashMap<String,JSONObject> map = new HashMap<String, JSONObject>();
                HashMap<String,String> map_q = new HashMap<String, String>();

                for(String temp: items){
                    Log.i("ResultsActivity.class", "onDataChange: data is :"+ temp +" and frequency : "+ Collections.frequency(stringList,temp));
//                        map_q.put("color",temp);

//                        map_r.put(map_q,map);
                    try {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("color",temp);
                        jsonObject.put("count",""+Collections.frequency(stringList,temp)+"");
                        map.put("Json"+temp,jsonObject);
                        jsonArray.put(map.get("Json"+temp));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                Log.i("ResultsActivity.class","Done "+ jsonArray);

                Gson gson = new Gson();
                String exp_res_p = gson.toJson(map);
                String app = jsonArray.toString();
                Log.i("ResultsActivity.class","msg +"+app);
                String exp_res = setChart(app);
                Log.i("ResultsActivity.class","msg +"+exp_res);
                if (exp_res.isEmpty()){
                    Toast.makeText(getApplicationContext(),"No Result",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getApplicationContext(),exp_res,Toast.LENGTH_LONG).show();
                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
//        String app = jsonObject.toString();
//        Log.i("ResultsActivity.class","JSONObj "+ app);
//        try {
//            jsonArray = new JSONArray("["+app+"]");
//            Log.i("ResultsActivity.class","JSONArray "+ jsonArray);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        String result =  results_ack();

//        if (exp_res.isEmpty()){
//            Toast.makeText(getApplicationContext(),"No Result",Toast.LENGTH_LONG).show();
//        }else{
//            Toast.makeText(getApplicationContext(),exp_res,Toast.LENGTH_LONG).show();
//        }

        Button button = (Button)findViewById(R.id.refresh);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });

    }

    public String setChart(String result){
        BarChart barChart = (BarChart) findViewById(R.id.barchart);
        ArrayList<BarEntry> entries = new ArrayList<>();
        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray("["+result+"]");

            for (int i=0;i<jsonArray.length();i++){
                JSONArray json = jsonArray.getJSONArray(i);
                for(int j=0;j<json.length();j++){
                    JSONObject jsonObject = json.getJSONObject(j);
                    int res = jsonObject.getInt("count");
                    entries.add(new BarEntry(j, res));
                }
//                JSONObject new_one = jsonObject.getJSONObject("nameValuePairs");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        BarDataSet bardataset = new BarDataSet(entries,null );


        ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
        dataSets.add(bardataset);

        BarData data = new BarData(dataSets);
        barChart.setData(data);

        // barChart.setDescription("Set Bar Chart Description");

        bardataset.setColors(ColorTemplate.COLORFUL_COLORS);
//        bardataset.setLabel("Vote");

        Legend l = barChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setForm(Legend.LegendForm.SQUARE);
        l.setFormSize(9f);
        l.setTextSize(11f);
        l.setXEntrySpace(4f);

        String[] es = new String[4];

        try {
            jsonArray = new JSONArray("["+result+"]");

            for (int i=0;i<jsonArray.length();i++){
                JSONArray json = jsonArray.getJSONArray(i);
                for(int j=0;j<json.length();j++){
                    JSONObject jsonObject = json.getJSONObject(j);
                    String res = jsonObject.getString("color");
                    es[j] = res;
                    Log.i("ResultsActivity.class", "setChart: "+res);
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(es != null){

            l.setExtra(ColorTemplate.COLORFUL_COLORS, new String[]{ es[0],es[1],es[2],es[3]});
            Log.i("ResultsActivity.class", "setChart: es "+es[0]);
        }

        XAxis xAxis = barChart.getXAxis();
        xAxis.setTypeface(Typeface.DEFAULT);
        xAxis.setTextSize(12f);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextColor(ColorTemplate.getHoloBlue());
        xAxis.setEnabled(true);
        xAxis.disableGridDashedLine();
        xAxis.setDrawGridLines(false);
        xAxis.setAvoidFirstLastClipping(true);
        xAxis.setLabelCount(4);
        xAxis.setAxisMinimum(0);

        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setTextColor(ColorTemplate.getHoloBlue());
        leftAxis.setAxisMinimum(0);
        leftAxis.setDrawGridLines(false);


        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);

        return "Success";
    }


    public String results_ack(){
            Log.i("ResultsActivity.class", "doInBackground: Results In Progress");
            final String app_result;
            final List<String> stringList = new ArrayList<String>();
            final JSONObject jsonObject = new JSONObject();
             JSONArray jsonArray = null;
//            databaseReference = FirebaseDatabase.getInstance().getReference();
                databaseReference = FirebaseDatabase.getInstance().getReference("Voting");

               databaseReference.addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                   for(DataSnapshot ds : dataSnapshot.getChildren()){
                        VoteInfo voteInfo =  new VoteInfo();
                       Log.i("ResultsActivity.class", "onDataChange: "+ ds);
                       voteInfo.setVote(ds.child("vote").getValue(String.class));
                       String set_vote = voteInfo.getVote();
                       stringList.add(set_vote);
                       Log.i("ResultsActivity.class", "onDataChange Set: "+ set_vote);
                   }
                    Set<String> items = new HashSet<String>(stringList);
                    HashMap<String,Integer> map = new HashMap<String, Integer>();
                    HashMap<String,String> map_q = new HashMap<String, String>();

                    for(String temp: items){
                        Log.i("ResultsActivity.class", "onDataChange: data is :"+ temp +" and frequency : "+ Collections.frequency(stringList,temp));
//                        map_q.put("color",temp);
                        map.put(temp,Collections.frequency(stringList,temp));
                        try {
                            jsonObject.put("color",temp);
                            jsonObject.put("count",""+Collections.frequency(stringList,temp)+"");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
//                        map_r.put(map_q,map);
                    }

                    Log.i("ResultsActivity.class","Done "+ jsonObject);
                    String app = jsonObject.toString();
                    Log.i("ResultsActivity.class","JSONObj "+ app);
                    try {
                        JSONArray jsonArray = new JSONArray("["+app+"]");
                        Log.i("ResultsActivity.class","JSONArray "+ jsonArray);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }

            });

        return null;
    }
}
