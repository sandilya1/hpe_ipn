package com.hpe.ipn;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Sandilya on 3/29/2017.
 */
public class AssignAdmin extends AppCompatActivity implements android.widget.SearchView.OnQueryTextListener {
    ListView listView;
    ListViewAdapter adapter;
    android.widget.SearchView searchView;
    String[] list;
    ArrayList<Users> arrayList = new ArrayList<Users>();
    ArrayList<String> user_list = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.assign_admin);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        String res_users = user_list();
        if(res_users.isEmpty()){
            list = new String[]{"No Users Found"};
        }else{
            try{

                JSONArray jsonArray = new JSONArray(res_users);
                for(int i=1;i<=jsonArray.length();i++){
                    System.out.print(i);
                    JSONObject jsonObject = jsonArray.getJSONObject(i-1);
                    String a = String.valueOf(i);
                    if(jsonObject.has(a)){
                        String name = jsonObject.getString(a);
                        list = new String[jsonArray.length()];
                        user_list.add(name);
                        //list[i-1] = name;
                    }

                }

            }catch (Exception e){
                Log.e("AssignAdmin.class", "onCreate: ", e);
            }

        }


        Log.i("AssignAdmin.class", "onCreate: "+ user_list);
        //list = new String[]{"siva","siva1","sandi"};
        list = user_list.toArray(new String[user_list.size()]);
        listView = (ListView)findViewById(R.id.listView_users);
        for (int i=0;i<list.length;i++){
            Users users = new Users(list[i]);
            arrayList.add(users);
        }
        adapter = new ListViewAdapter(this,arrayList);
        listView.setAdapter(adapter);
        searchView = (android.widget.SearchView) findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(this);
    }

    public void assign_admin(View v){

        Log.i("AssignAdmin.class", "assign_admin: ");
    }

    public void exitApp(View v){
        finish();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        String text = newText;
        adapter.filter(text);
        return false;
    }

    public String user_list(){

        /* getting list of users from DB */

        String db_url = "http://10.0.2.2:1202/webApp/list_users.php" ;
        try{
            URL url = new URL(db_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoInput(true);
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"),8);
            StringBuilder stringBuilder = new StringBuilder();
            String response = "" , line = "" ;
            if((line=bufferedReader.readLine())!= null){
                response += line;
                stringBuilder.append(line+" \n");
                Log.i("BackgroundTask.class", "doInBackground: "+response);
                Log.i("BackgroundTask.class", "doInBackground: "+line);
                Log.i("BackgroundTask.class", "user_list: "+ stringBuilder);

            }
            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();
            return stringBuilder.toString();

        }catch (Exception e){
            Log.e("AssignAdmin.class", "user_list: ", e );
        }

        return null;
    }

}
