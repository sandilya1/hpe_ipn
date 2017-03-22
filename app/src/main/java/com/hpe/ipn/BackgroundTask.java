package com.hpe.ipn;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by ventrapr on 2/21/2017.
 */

public class BackgroundTask extends AsyncTask<String,Void,String> {


    Context ctx ;
    BackgroundTask(Context ctx){
        this.ctx = ctx.getApplicationContext();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }



    @Override
    protected String doInBackground(String... params) {
        String login_url = "http://10.0.2.2:1202/webApp/logIn.php" ;

        String method = params[0];
        if(method.equalsIgnoreCase("login")){
            String user_name = params[1];
            String user_pass = params[2];
            try {
                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String data = URLEncoder.encode("user_name","UTF-8")+"="+URLEncoder.encode(user_name,"UTF-8")+"&"+
                        URLEncoder.encode("user_pass","UTF-8")+"="+URLEncoder.encode(user_pass,"UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                String statCode = httpURLConnection.getResponseMessage();
                System.out.print(statCode);
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String response = "" , line = "";
                while((line=bufferedReader.readLine()) != null ){
                    response += line ;
                    Log.i("BackgroundTask.class", "doInBackground: "+response);
                }
                    bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return  response;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return  null;
    }

    @Override
    protected void onPostExecute(String response) {
        if(response.equals("success")){
            Toast.makeText(ctx,"Success..",Toast.LENGTH_LONG).show();
            super.onPostExecute(response);
            Intent intent_name = new Intent();
            intent_name.setClass(ctx.getApplicationContext(),DisplayMessageActivity.class);
            intent_name.putExtra(MainActivity.EXTRA_MESSAGE,MainActivity.user_name);
            intent_name.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ctx.startActivity(intent_name);

        }else if(response.equals("admin")){
            Toast.makeText(ctx,"Success..",Toast.LENGTH_LONG).show();
            super.onPostExecute(response);
            Intent intent_name1 = new Intent();
            intent_name1.setClass(ctx.getApplicationContext(),PollsActivity.class);
            //intent_name1.setClass(ctx.getApplicationContext(),AssignAdmin.class);
            intent_name1.putExtra(MainActivity.EXTRA_MESSAGE,MainActivity.user_name);
            intent_name1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ctx.startActivity(intent_name1);

        }else{
            Log.i("backgroundTask.class", "onPostExecute: "+response);
            Toast.makeText(ctx,"Please Enter Correct UserId and Password..",Toast.LENGTH_LONG).show();
        }
    }


}
