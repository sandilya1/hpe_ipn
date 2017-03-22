package com.hpe.ipn;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class PollsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_polls_replace);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        String data_check = data_check();

        TextView textView = (TextView)findViewById(R.id.question);
        TextView textView2 = (TextView)findViewById(R.id.op1);
        TextView textView3 = (TextView)findViewById(R.id.op2);
        TextView textView4 = (TextView)findViewById(R.id.op3);
        TextView textView5 = (TextView)findViewById(R.id.op4);

        if(data_check != null){
            try{

                JSONArray jsonArray = new JSONArray(data_check);
                for(int i=0 ; i<jsonArray.length();i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String question = String.valueOf(jsonObject.getString("question"));
                    textView.setText(question);
                    String op1 = String.valueOf(jsonObject.getString("option1"));
                    textView2.setText(op1);
                    String op2 = String.valueOf(jsonObject.getString("option2"));
                    textView3.setText(op2);
                    String op3 = String.valueOf(jsonObject.getString("option3"));
                    textView4.setText(op3);
                    String op4 = String.valueOf(jsonObject.getString("option4"));
                    textView5.setText(op4);
                }

            }catch (Exception e){
                Log.e("PollsActivity.class", "onCreate: "+e);
            }
        }

    }

    private String data_check() {

        String poll_check_url = "http://10.0.2.2:1202/webApp/data_check.php";
        try {
            URL url = new URL(poll_check_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"),8);
            StringBuilder stringBuilder = new StringBuilder();
            String response = "" , line = "";
            while((line=bufferedReader.readLine()) != null ){
                response += line ;
                stringBuilder.append(line + "\n");
                //  Log.i("BackgroundTask.class", "doInBackground: "+response);
            }
            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();
            return stringBuilder.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;

    }

    public void exitApp(View v){
        finish();
    }

    public  void save(View v){

        Log.i("PollsActivity.class", "save: Saving In Progress");

        TextView textView1 = (TextView)findViewById(R.id.question);
        TextView textView2 = (TextView)findViewById(R.id.op1);
        TextView textView3 = (TextView)findViewById(R.id.op2);
        TextView textView4 = (TextView)findViewById(R.id.op3);
        TextView textView5 = (TextView)findViewById(R.id.op4);

        String method = "save_poll" ;
        String question = textView1.getText().toString();
        String op1 = textView2.getText().toString();
        String op2 = textView3.getText().toString();
        String op3 = textView4.getText().toString();
        String op4 = textView5.getText().toString();
        VotingTask vTask = new VotingTask(this);
        vTask.execute(method,question,op1,op2,op3,op4);

    }

    public void publish(View v){

        Log.i("PollsActivity.class", "publish: Publish In Progress");

        TextView textView1 = (TextView)findViewById(R.id.question);
        TextView textView2 = (TextView)findViewById(R.id.op1);
        TextView textView3 = (TextView)findViewById(R.id.op2);
        TextView textView4 = (TextView)findViewById(R.id.op3);
        TextView textView5 = (TextView)findViewById(R.id.op4);

        String method = "publish_poll" ;
        String question = textView1.getText().toString();
        String op1 = textView2.getText().toString();
        String op2 = textView3.getText().toString();
        String op3 = textView4.getText().toString();
        String op4 = textView5.getText().toString();

       // Log.i("PollsActivity.class", "publish: question "+question+" op1 "+ op1+" op2 "+op2);

        VotingTask vTask = new VotingTask(this);
        vTask.execute(method,question,op1,op2,op3,op4);

    }
}
