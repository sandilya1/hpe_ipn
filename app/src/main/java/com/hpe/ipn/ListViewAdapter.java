package com.hpe.ipn;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.hpe.ipn.R.id.res_texts;

/**
 * Created by ventrapr on 4/2/2017.
 */
public class ListViewAdapter extends BaseAdapter{
    Context mContext;
    LayoutInflater inflater;
    private List<Users> usersList = null;
    private ArrayList<Users> users;

    public  ListViewAdapter(Context context,List<Users> usersList){
        mContext = context;
        this.usersList = usersList;
        inflater = LayoutInflater.from(mContext);
        this.users = new ArrayList<Users>();
        this.users.addAll(usersList);
    }

    @Override
    public int getCount() {
        return usersList.size();
    }

    @Override
    public Users getItem(int position) {
        return usersList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final ExpandableListAdapter.ViewHolder holder;
        if(convertView == null){
            holder = new ExpandableListAdapter.ViewHolder();
            convertView = inflater.inflate(R.layout.assign_admin_child_list,null);
            holder.title = (TextView) convertView.findViewById(res_texts);
            convertView.setTag(holder);
        }else {
            holder = (ExpandableListAdapter.ViewHolder)convertView.getTag();
        }

        holder.title.setText(usersList.get(position).getUserName());
        return convertView;
    }

    public void filter(String charText){
        charText = charText.toLowerCase(Locale.getDefault());
        usersList.clear();
        if(charText.length()==0){
            usersList.addAll(users);
        }else{
            for(Users wp: users){
                if(wp.getUserName().toLowerCase(Locale.getDefault()).contains(charText)){
                    usersList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
}

