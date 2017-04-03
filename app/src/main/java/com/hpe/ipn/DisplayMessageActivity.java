package com.hpe.ipn;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class DisplayMessageActivity extends Activity implements View.OnClickListener {



    String Et_button1;
    private RadioGroup radioColorGroup;
    private RadioButton radioColorButton;
    private Button vButton , signOut;
    public static final String TAG = "MessageActivity.class";
    private TextView textView1;
    final int delay = 10000;
    int a = 0;
    DatabaseReference databaseReference;
    ProgressDialog progressDialog;
    private  FirebaseAuth.AuthStateListener mAuthListener;
    FirebaseAuth firebaseAuth;
    Button b1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);

        setPageContents("run");

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Page..");
        progressDialog.show();

        vButton = (Button)findViewById(R.id.vote);
        signOut = (Button)findViewById(R.id.email_sign_out_button);
        vButton.setOnClickListener(this);
        signOut.setOnClickListener(this);
        b1 = (Button)findViewById(R.id.refresh_page);
        b1.setOnClickListener(this);

        databaseReference = FirebaseDatabase.getInstance().getReference();

//        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//        StrictMode.setThreadPolicy(policy);

        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.activity_display_message);

        final String message = MainActivity.user_name;
        TextView textView = new TextView(this);
        textView.setTextSize(30);
        textView.setText("Welcome " + message);
        relativeLayout.addView(textView);


    }

        public void setPageContents(String check_poll){

            final RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.activity_display_message);

//         relativeLayout.removeAllViewsInLayout();

//        ViewGroup layout = (ViewGroup) findViewById(R.id.activity_display_message);
//        layout.addView(textView);
            final HashMap<String,Object> rm = new HashMap<>();
            if(check_poll != null) {
                final RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);
                final RelativeLayout.LayoutParams newParams = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);
                databaseReference = FirebaseDatabase.getInstance().getReference();
                databaseReference.addValueEventListener(new ValueEventListener() {
                    PublishQuestion publishQuestion = new PublishQuestion();

                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot child1 : dataSnapshot.getChildren()){
                            publishQuestion.setQuestion_p(child1.child("question_p").getValue(String.class));
                            publishQuestion.setOption1(child1.child("option1").getValue(String.class));
                            publishQuestion.setOption2(child1.child("option2").getValue(String.class));
                            publishQuestion.setOption3(child1.child("option3").getValue(String.class));
                            publishQuestion.setOption4(child1.child("option4").getValue(String.class));

                            if(publishQuestion.getOption1() != null){
                                rm.put("question_p",publishQuestion.getQuestion_p());
                                rm.put("option1",publishQuestion.getOption1());
                                rm.put("option2",publishQuestion.getOption2());
                                rm.put("option3",publishQuestion.getOption3());
                                rm.put("option4",publishQuestion.getOption4());
                            }
                        }

                        String a = rm.get("question_p").toString();
                        String b = rm.get("option1").toString();
                        String c = rm.get("option2").toString();
                        String d = rm.get("option3").toString();
                        String e = rm.get("option4").toString();

                        Log.i(TAG, "onDataChange: String a"+ a);

                        if(a != null && a.length() != 0){
                            int z =250;
                            progressDialog.dismiss();
                            textView1 = new TextView(DisplayMessageActivity.this);
                            layoutParams.addRule(RelativeLayout.BELOW,1);
                            textView1.setLayoutParams(layoutParams);
                            textView1.setText(a);
                            textView1.setTextSize(20);
                            textView1.setPadding(10, z, 40, 100);
                            relativeLayout.addView(textView1);
                            z += 100;
                            radioColorGroup = new RadioGroup(DisplayMessageActivity.this);
                            newParams.addRule(RelativeLayout.BELOW,1);
                            radioColorGroup.setLayoutParams(newParams);
                            RadioButton radioButton = new RadioButton(DisplayMessageActivity.this);
                            radioButton.setText(b);
                            radioColorGroup.setPadding(10,z,40,100);
                            radioColorGroup.addView(radioButton);

                            RadioButton radioButton1 = new RadioButton(DisplayMessageActivity.this);
                            radioButton1.setText(c);
                            radioColorGroup.addView(radioButton1);

                            RadioButton radioButton2 = new RadioButton(DisplayMessageActivity.this);
                            radioButton2.setText(d);
                            radioColorGroup.addView(radioButton2);

                            RadioButton radioButton3 = new RadioButton(DisplayMessageActivity.this);
                            radioButton3.setText(e);
                            radioColorGroup.addView(radioButton3);

                            relativeLayout.addView(radioColorGroup);
                        }else if(a.isEmpty()){
                            progressDialog.dismiss();
                            int z = 250;
                            textView1 = new TextView(DisplayMessageActivity.this);
                            layoutParams.addRule(RelativeLayout.BELOW,1);
                            textView1.setLayoutParams(layoutParams);
                            textView1.setText("No Question to display, Please try refreshing the page");
                            textView1.setTextSize(20);
                            textView1.setPadding(10,z,40,100);
                            relativeLayout.addView(textView1);


                        }else{
                            progressDialog.dismiss();
                            Log.e(TAG,"Something went wrong with Loading..");
                        }


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

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


    @Override
    public void onClick(View v) {
        if(v == signOut){
            a=1;
            finish();
        }else if(v == b1){
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        }else if(v == vButton){
            Log.i("MessageActivity.class", "inside Vote Method");

//        int selectId = -1;
            int selectId = radioColorGroup.getCheckedRadioButtonId();
            Log.i("MessageActivity.class", "Selected ID: "+ selectId);
            if(selectId == -1){
                Toast.makeText(getApplicationContext(),"Please Choose an option",Toast.LENGTH_LONG).show();
            }else{
                FirebaseUser firebaseUser = firebaseAuth.getInstance().getCurrentUser();
                radioColorButton=(RadioButton)findViewById(selectId);
                final String current_user = firebaseUser.getUid();
                Log.i("MessageActivity.class", "voteFor"+ current_user);
                final String voteFor = radioColorButton.getText().toString();
                Log.i("MessageActivity.class", "voteFor"+ voteFor);

                final List<String> u_id = new ArrayList<String>();
                databaseReference = FirebaseDatabase.getInstance().getReference();
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Log.i("MessageActivity.class", "datasnap ");
                        for(DataSnapshot ds : dataSnapshot.getChildren()){
                            UserInfo userInfo = new UserInfo();
                            userInfo.setUserId(ds.child(current_user).child("uid").getValue(String.class));
                            Log.i("MessageActivity.class", "1datasnap "+userInfo.getUserId());

                            String user_infp = userInfo.getUserId();
                            if(user_infp != null && user_infp.equalsIgnoreCase(current_user)){
                                u_id.add(userInfo.getUserId());
                                Log.i(TAG,"USerID"+ userInfo.getUserId());
                                Toast.makeText(DisplayMessageActivity.this,"Already Voted..",Toast.LENGTH_LONG).show();
                                startActivity(new Intent(getApplicationContext(),ResultsActivity.class));
                                return;
                            }else{
                                VoteInfo voteInfo = new VoteInfo(current_user,voteFor);
                                databaseReference = FirebaseDatabase.getInstance().getReference();
                                databaseReference.child("Voting").child(current_user).setValue(voteInfo);
                                Toast.makeText(DisplayMessageActivity.this,"Thank you for voting",Toast.LENGTH_LONG).show();
                                startActivity(new Intent(getApplicationContext(),ResultsActivity.class));
                                return;
                            }
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


//            VotingTask vTask = new VotingTask(this);
//            vTask.execute(method,user,voteFor);


            }
        }

    }
}
