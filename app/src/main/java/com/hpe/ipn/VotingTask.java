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
public class VotingTask extends AsyncTask<String,Void,String> {

    Context ctx;
    VotingTask(Context ctx){
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
        Log.i("VotingTask.class", "doInBackground: Inside Method ");
        String vote_url = "http://10.0.2.2:1202/webApp/vote.php" ;
        String publish_poll_url = "http://10.0.2.2:1202/webApp/publish_poll.php";
        String save_poll_url = "http://10.0.2.2:1202/webApp/save_poll.php";

        String method = params[0];
        Log.i("VotingTask.class", "doInBackground: method is "+ method);
        if(method.equalsIgnoreCase("vote")){
            String user_name = params[1];
            String vote_c = params[2];
            Log.i("VotingTask.class", "doInBackground: Params are "+params[1]+" "+params[2]);
            try {
                URL url = new URL(vote_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                Log.i("VotingTask.class", "doInBackground: connected");
                OutputStream outputStream = httpURLConnection.getOutputStream();
                Log.i("VotingTask.class", "doInBackground: settings ok ");
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                Log.i("VotingTask.class", "doInBackground: reader is fine");
                String data = URLEncoder.encode("user_name","UTF-8")+"="+URLEncoder.encode(user_name,"UTF-8")+"&"+
                        URLEncoder.encode("vote_c","UTF-8")+"="+URLEncoder.encode(vote_c,"UTF-8");
                Log.i("VotingTask.class", "doInBackground: "+data);
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String response = "" , line = "";
                while((line = bufferedReader.readLine()) != null ){
                    response += line ;
                    Log.i("VotingTask.class", "doInBackground: result is "+response);
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

        }else if (method == "save_poll"){
            Log.i("VotingTask.class", "doInBackground: Saving Poll In Progress");
            String question = params[1];
            String op1 = params[2];
            String op2 = params[3];
            String op3 = params[4];
            String op4 = params[5];
            try{

                URL url = new URL(save_poll_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String data = URLEncoder.encode("question","UTF-8")+"="+URLEncoder.encode(question,"UTF-8")+"&"+
                        URLEncoder.encode("op1","UTF-8")+"="+URLEncoder.encode(op1,"UTF-8")+"&"+
                        URLEncoder.encode("op2","UTF-8")+"="+URLEncoder.encode(op2,"UTF-8")+"&"+
                        URLEncoder.encode("op3","UTF-8")+"="+URLEncoder.encode(op3,"UTF-8")+"&"+
                        URLEncoder.encode("op4","UTF-8")+"="+URLEncoder.encode(op4,"UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
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

        }else if(method == "assign_admin"){
            Log.i("VotingTask.class","doInBackground: Assigning Admin");
            String user_name = params[1];
            try{

            }catch(Exception e){
                Log.e("VotingTask.class", "doInBackground: "+ e );
            }
        }
        else if(method == "publish_poll"){
            Log.i("VotingTask.class", "doInBackground: Publishing In Progress");
            String question = params[1];
            String op1 = params[2];
            String op2 = params[3];
            String op3 = params[4];
            String op4 = params[5];

            try{

                URL url = new URL(publish_poll_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String data = URLEncoder.encode("question","UTF-8")+"="+URLEncoder.encode(question,"UTF-8")+"&"+
                        URLEncoder.encode("op1","UTF-8")+"="+URLEncoder.encode(op1,"UTF-8")+"&"+
                        URLEncoder.encode("op2","UTF-8")+"="+URLEncoder.encode(op2,"UTF-8")+"&"+
                        URLEncoder.encode("op3","UTF-8")+"="+URLEncoder.encode(op3,"UTF-8")+"&"+
                        URLEncoder.encode("op4","UTF-8")+"="+URLEncoder.encode(op4,"UTF-8");

                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
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

        }

        return  null;
    }

    @Override
    protected void onPostExecute(String response){
        if(response.equals("Success")){
            Log.i("backgroundTask.class", "onPostExecute: response is "+response);
            Toast.makeText(ctx.getApplicationContext(),"Thanks For Voting",Toast.LENGTH_LONG).show();
            super.onPostExecute(response);
            Intent intent = new Intent();
            intent.setClass(ctx.getApplicationContext(),ResultsActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ctx.startActivity(intent);

        }else if(response.equals("published")){
            Log.i("backgroundTask.class", "onPostExecute: response is "+response);
            Toast.makeText(ctx.getApplicationContext(),"Published Successfully",Toast.LENGTH_LONG).show();
            super.onPostExecute(response);
            Intent intent = new Intent();
            intent.setClass(ctx.getApplicationContext(),ResultsActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ctx.startActivity(intent);

        }else if(response.equals("saved")){
            Log.i("backgroundTask.class", "onPostExecute: response is "+response);
            Toast.makeText(ctx.getApplicationContext(),"Saved Successfully",Toast.LENGTH_LONG).show();
            super.onPostExecute(response);

        }else{
            Log.w("backgroundTask.class", "onPostExecute: response is "+response);
            Toast.makeText(ctx.getApplicationContext(),"Already Voted,Please Try Later..",Toast.LENGTH_LONG).show();
            super.onPostExecute(response);
            Intent intent = new Intent();
            intent.setClass(ctx.getApplicationContext(),ResultsActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ctx.startActivity(intent);
        }
    }

}
