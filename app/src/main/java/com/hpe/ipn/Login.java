package com.hpe.ipn;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;


/**
 * Created by ventrapr on 2/21/2017.
 */
public class Login extends Activity{

    EditText Et_UserName , Et_UserPass ;
    String user_name,user_pass;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

}
