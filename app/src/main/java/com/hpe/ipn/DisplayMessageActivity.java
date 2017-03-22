package com.hpe.ipn;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class DisplayMessageActivity extends Activity {



    String Et_button1;
    private RadioGroup radioColorGroup;
    private RadioButton radioColorButton;
    private Button vButton;
    private TextView textView1;
    final int delay = 10000;
    int a = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);

        final Handler h = new Handler();

        final int post_delay = 60000;
        h.postDelayed(new Runnable(){
            public void run() {
                String data_check = check_poll();
                Log.i("DisplayMessageActivity.class", "run: "+ data_check);
                if(a == 1){
                    h.removeCallbacksAndMessages(this);
                }else if(data_check.isEmpty()){
                    h.postDelayed(this, delay);
                    Log.i("DisplayMessageActivity.class", "run: not delayed");
                    setPageContents(null);
                }else{
                    h.postDelayed(this, post_delay);
                    Log.i("DisplayMessageActivity.class", "run: delayed");
                    setPageContents(data_check);
                }
            }
        },delay);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.activity_display_message);

        final String message = MainActivity.user_name;
        TextView textView = new TextView(this);
        textView.setTextSize(30);
        textView.setText("Welcome " + message);
        relativeLayout.addView(textView);


    }


        public void setPageContents(String check_poll){

            RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.activity_display_message);
//         relativeLayout.removeAllViewsInLayout();

//        ViewGroup layout = (ViewGroup) findViewById(R.id.activity_display_message);
//        layout.addView(textView);

            if(check_poll != null){
                try {

                    JSONArray jsonArray = new JSONArray(check_poll);

                    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                            RelativeLayout.LayoutParams.WRAP_CONTENT,
                            RelativeLayout.LayoutParams.WRAP_CONTENT);
                    RelativeLayout.LayoutParams newParams = new RelativeLayout.LayoutParams(
                            RelativeLayout.LayoutParams.WRAP_CONTENT,
                            RelativeLayout.LayoutParams.WRAP_CONTENT);


                    for (int i = 0; i < jsonArray.length(); i++) {
                        int a = 150;
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        textView1 = new TextView(this);
                        String question = String.valueOf(jsonObject.getString("question"));
                        layoutParams.addRule(RelativeLayout.BELOW,1);
                        textView1.setLayoutParams(layoutParams);
                        textView1.setText(question);
                        textView1.setTextSize(20);
                        textView1.setPadding(10, a, 40, 100);
                        relativeLayout.addView(textView1);
                        a += 100;
                        radioColorGroup = new RadioGroup(this);
                        newParams.addRule(RelativeLayout.BELOW,1);
                        radioColorGroup.setLayoutParams(newParams);
                        RadioButton radioButton = new RadioButton(this);
                        String op1 = String.valueOf(jsonObject.getString("option1"));
                        radioButton.setText(op1);
                        radioColorGroup.setPadding(10,a,40,100);
                        radioColorGroup.addView(radioButton);

                        RadioButton radioButton1 = new RadioButton(this);
                        String op2 = String.valueOf(jsonObject.getString("option2"));
                        radioButton1.setText(op2);
                        radioColorGroup.addView(radioButton1);

                        RadioButton radioButton2 = new RadioButton(this);
                        String op3 = String.valueOf(jsonObject.getString("option3"));
                        radioButton2.setText(op3);
                        radioColorGroup.addView(radioButton2);

                        RadioButton radioButton3 = new RadioButton(this);
                        String op4 = String.valueOf(jsonObject.getString("option4"));
                        radioButton3.setText(op4);
                        radioColorGroup.addView(radioButton3);

                        relativeLayout.addView(radioColorGroup);

                        a += 100;
                    }

                } catch (Exception e) {
                    Log.e("DisplayMessageActivity.class", "onCreate: " + e);
                }
            }else{
                relativeLayout.removeView(textView1);
                relativeLayout.removeView(radioColorGroup);
            }

        }

//        radioColorGroup = (RadioGroup) findViewById(R.id.radioGroup);
//        vButton = (Button) findViewById(R.id.vote);
//
//        vButton.setOnClickListener(new View.OnClickListener(){
//
//            public void onClick(View v){
//                int selectId = radioColorGroup.getCheckedRadioButtonId();
//                radioColorButton=(RadioButton)findViewById(selectId);
//                VotingTask vTask = new VotingTask(getApplicationContext());
//                String method = "vote" ;
//                String user = message ;
//                String voteFor = radioColorButton.getText().toString();
//                vTask.execute(method,user,voteFor);
//
//            }
//        });
//    }

    public void vote(View view){
        Log.i("DisplayMessageActivity.class", "inside Vote Method");

//        int selectId = -1;
        int selectId = radioColorGroup.getCheckedRadioButtonId();
        Log.i("DisplayMessageActivity.class", "Selected ID: "+ selectId);
        if(selectId == -1){
            Toast.makeText(getApplicationContext(),"Please Choose an option",Toast.LENGTH_LONG).show();
        }else{
            radioColorButton=(RadioButton)findViewById(selectId);
            String method = "vote" ;
            String user = MainActivity.user_name ;
            String voteFor = radioColorButton.getText().toString();
            VotingTask vTask = new VotingTask(this);
            vTask.execute(method,user,voteFor);
        }


    }

    public void exitApp(View view){
        a=1;
        finish();

    }

    public String check_poll(){
                String poll_check_url = "http://10.0.2.2:1202/webApp/poll_check.php";
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


}
