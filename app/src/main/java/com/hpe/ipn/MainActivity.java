package com.hpe.ipn;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends Activity {



    EditText Et_UserName , Et_UserPass ;
    static String user_name;
    String user_pass;
    public static final String EXTRA_MESSAGE = "Example";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Et_UserName = (EditText) findViewById(R.id.user_name);
        Et_UserPass = (EditText) findViewById(R.id.user_pass);
    }

//    public void userLogin(View view){
//        Intent intent = new Intent(this,Login.class);
//        startActivity(intent);
//    }

    public void logIn(View view){


        user_name = Et_UserName.getText().toString();
        user_pass = Et_UserPass.getText().toString();
        String method = "login" ;
        BackgroundTask backgroundTask = new BackgroundTask(this);
        backgroundTask.execute(method,user_name,user_pass);

    }

//    public void sendMessage(View view){
//        Intent intent = new Intent(this, DisplayMessageActivity.class);
//        EditText editText = (EditText) findViewById(R.id.user_name);
//        String message = editText.getText().toString();
//        intent.putExtra(MainActivity.EXTRA_MESSAGE,message);
//        startActivity(intent);
//    }
//
//    public void logInSuccess(){
//
//        sendMessage(Et_UserName);
//
//    }
}
