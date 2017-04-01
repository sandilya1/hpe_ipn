package com.hpe.ipn;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class PollsActivity extends Activity implements View.OnClickListener {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    Button save,publish,signout ;
    EditText question,op1,op2,op3,op4;
    String publish_value = "publishQ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_polls_replace);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                showData(dataSnapshot);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        save = (Button)findViewById(R.id.save);
        save.setOnClickListener(this);
        publish = (Button)findViewById(R.id.publish);
        publish.setOnClickListener(this);
        signout = (Button)findViewById(R.id.button);
        signout.setOnClickListener(this);

    }

    private void showData(DataSnapshot dataSnapshot) {

        HashMap<String,Object> rm = new HashMap<>();

        SaveQuestion saveQuestion = new SaveQuestion();
            for(DataSnapshot child1 : dataSnapshot.getChildren()){

                saveQuestion.setQuestion(child1.child("question").getValue(String.class));
                saveQuestion.setOp1(child1.child("op1").getValue(String.class));
                saveQuestion.setOp2(child1.child("op2").getValue(String.class));
                saveQuestion.setOp3(child1.child("op3").getValue(String.class));
                saveQuestion.setOp4(child1.child("op4").getValue(String.class));

//                String publishQuestion = child1.child("publishQ").child("op1").getValue(String.class);
                    Log.i("PollsActivity.class", "showData: "+ saveQuestion.getOp1());
                if(saveQuestion.getOp1() != null){
                    rm.put("question",saveQuestion.getQuestion());
                    rm.put("op1",saveQuestion.getOp1());
                    rm.put("op2",saveQuestion.getOp2());
                    rm.put("op3",saveQuestion.getOp3());
                    rm.put("op4",saveQuestion.getOp4());
                }
            }

            Log.i("EMps", "showData: "+ rm.get("question"));
        String a = rm.get("question").toString();
        String b = rm.get("op1").toString();
        String c = rm.get("op2").toString();
        String d = rm.get("op3").toString();
        String e = rm.get("op4").toString();

        question = (EditText) findViewById(R.id.question);
        question.setText(a);
        op1 = (EditText)findViewById(R.id.op1);
        op1.setText(b);
        op2 = (EditText)findViewById(R.id.op2);
        op2.setText(c);
        op3 = (EditText)findViewById(R.id.op3);
        op3.setText(d);
        op4 = (EditText)findViewById(R.id.op4);
        op4.setText(e);

    }

    @Override
    public void onClick(View v) {

        if(v == save){

            question = (EditText) findViewById(R.id.question);
            op1 = (EditText)findViewById(R.id.op1);
            op2 = (EditText)findViewById(R.id.op2);
            op3 = (EditText)findViewById(R.id.op3);
            op4 = (EditText)findViewById(R.id.op4);

            String question_s = question.getText().toString();
            String op1_s = op1.getText().toString();
            String op2_s = op2.getText().toString();
            String op3_s = op3.getText().toString();
            String op4_s = op4.getText().toString();

            SaveQuestion saveQuestion = new SaveQuestion(question_s,op1_s,op2_s,op3_s,op4_s,"N");
            databaseReference.child("savedQ").setValue(saveQuestion);

            PublishQuestion publishQuestion = new PublishQuestion("","","","","","");
            databaseReference.child("publishQ").setValue(publishQuestion);




        }else if(v == publish){

            Log.i("PollsActivity.class", "publish: Publish In Progress");

            TextView textView1 = (TextView)findViewById(R.id.question);
            TextView textView2 = (TextView)findViewById(R.id.op1);
            TextView textView3 = (TextView)findViewById(R.id.op2);
            TextView textView4 = (TextView)findViewById(R.id.op3);
            TextView textView5 = (TextView)findViewById(R.id.op4);

            String question = textView1.getText().toString();
            String op1 = textView2.getText().toString();
            String op2 = textView3.getText().toString();
            String op3 = textView4.getText().toString();
            String op4 = textView5.getText().toString();

            PublishQuestion publishQuestion = new PublishQuestion(question,op1,op2,op3,op4,"Y");
            databaseReference.child("publishQ").setValue(publishQuestion);

            SaveQuestion saveQuestion = new SaveQuestion(question,op1,op2,op3,op4,"Y");
            databaseReference.child("savedQ").setValue(saveQuestion);

            databaseReference.child("Voting").removeValue();

            Intent intent = new Intent(getApplicationContext(),ResultsActivity.class);
            startActivity(intent);

        }else if(v == signout){
            finish();
        }

    }
}