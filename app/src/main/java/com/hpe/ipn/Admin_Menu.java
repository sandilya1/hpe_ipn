package com.hpe.ipn;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Admin_Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin__menu);

        TextView textView = (TextView)findViewById(R.id.textView5_polling);
        TextView textView1 = (TextView)findViewById(R.id.textView3_assign);


    }

    public void onClick_assign(View v) {

        finish();
        startActivity(new Intent(this,AssignAdmin.class));

    }

    public void onClick_polling(View v){
        finish();
        startActivity(new Intent(this,PollsActivity.class));
    }
}
