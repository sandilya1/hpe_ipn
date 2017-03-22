package com.hpe.ipn;

import android.app.Activity;
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



public class ResultsActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        String result =  results_ack();
        String exp_res = setChart(result);
        if (result.isEmpty()){
            Toast.makeText(getApplicationContext(),"No Result",Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(getApplicationContext(),exp_res,Toast.LENGTH_LONG).show();
        }

        Button button = (Button)findViewById(R.id.refresh);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String result = results_ack();
                   String exp_res = setChart(result);
                if(result.isEmpty()){
                    Toast.makeText(getApplicationContext(),"No Result",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getApplicationContext(),exp_res,Toast.LENGTH_LONG).show();
                    ViewGroup vg = (ViewGroup) findViewById (R.id.barchart);
                    vg.removeAllViews();
                    vg.refreshDrawableState();
                }
            }
        });

    }

    public String setChart(String result){
        BarChart barChart = (BarChart) findViewById(R.id.barchart);
        ArrayList<BarEntry> entries = new ArrayList<>();
        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(result);

            for (int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                int res = jsonObject.getInt("count");
                entries.add(new BarEntry(i, res));
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
        bardataset.setLabel("Vote");

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
            jsonArray = new JSONArray(result);

            for (int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String res = jsonObject.getString("color");
                es[i] = res;
                System.out.print(res);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(es != null){

            l.setExtra(ColorTemplate.COLORFUL_COLORS, new String[]{ es[0],es[1],es[2],es[3]});
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

              String results_url = "http://10.0.2.2:1202/webApp/vote_results.php"  ;
            try{

                URL url = new URL(results_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String response = "", line ="" ;
                while((line=bufferedReader.readLine())!= null){
                    response += line;
                    Log.i("VotingTask.class", "doInBackground: result is "+response);
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                return response;

            }catch (MalformedURLException e){
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
    }
}
