package com.hpe.ipn;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends Activity {

    ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth;
    Context ctx;
    EditText Et_UserName , Et_UserPass ;
    static String user_name;
    String user_pass;
    public static final String EXTRA_MESSAGE = "Example";
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        progressDialog = new ProgressDialog(this);
        setContentView(R.layout.activity_main);


        firebaseAuth = FirebaseAuth.getInstance();


        Et_UserName = (EditText) findViewById(R.id.user_name);
        Et_UserPass = (EditText) findViewById(R.id.user_pass);

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d("MainActivity.class", "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d("MainActivity.class", "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };
    }

//    public void userLogin(View view){
//        Intent intent = new Intent(this,Login.class);
//        startActivity(intent);
//    }

    @Override
    public void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            firebaseAuth.removeAuthStateListener(mAuthListener);
        }
    }

    public void logIn(View view){


        user_name = Et_UserName.getText().toString();
        user_pass = Et_UserPass.getText().toString();
        final String sec= "admin@hpe.com" ;
//        String method = "login" ;
//        BackgroundTask backgroundTask = new BackgroundTask(this);
//        backgroundTask.execute(method,user_name,user_pass);
        if(user_name.isEmpty() || user_pass.isEmpty() || user_pass.equals("") || user_name.equals("") || user_name == null || user_pass == null){
            Log.i("MainActivity.class", "logIn: Empty user and pass");
            Toast.makeText(getApplicationContext(),"Please Enter UserId and Password",Toast.LENGTH_LONG).show();
        }else{

            progressDialog.setMessage("Logging In..");
            progressDialog.show();


            firebaseAuth.signInWithEmailAndPassword(user_name,user_pass)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressDialog.dismiss();
                            Log.i("MainActivity.class", "onComplete: logged In");
                            if(task.isSuccessful() && user_name.equalsIgnoreCase("admin@hpe.com")){
                                startActivity(new Intent(getApplicationContext(),PollsActivity.class));
                            }else if(task.isSuccessful()){
                                startActivity(new Intent(getApplicationContext(),DisplayMessageActivity.class));
                            }else{
                                signInAnonymous();
                                Toast.makeText(getApplicationContext(),"Logging in Anonymously",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }


    }

    public void signInAnonymous(){

        user_name = Et_UserName.getText().toString();
        user_pass = Et_UserPass.getText().toString();
//        String method = "login" ;
//        BackgroundTask backgroundTask = new BackgroundTask(this);
//        backgroundTask.execute(method,user_name,user_pass);
        progressDialog.setMessage("Logging In..");
        progressDialog.show();

        firebaseAuth.signInAnonymously()
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        Log.d("MainActivity.class", "signInAnonymously:onComplete:" + task.isSuccessful());
                        if (!task.isSuccessful()) {
                            Log.w("MainActivity.class", "signInAnonymously", task.getException());
                        }else{
                            startActivity(new Intent(getApplicationContext(),DisplayMessageActivity.class));
                        }
                    }
                });
    }

}
