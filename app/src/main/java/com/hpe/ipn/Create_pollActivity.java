package com.hpe.ipn;

import android.app.ListFragment;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by ventrapr on 3/28/2017.
 */

public class Create_pollActivity extends ListFragment {



    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        final List<SaveQuestion> saveQuestion = new ArrayList<SaveQuestion>();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("savedQ").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                for(DataSnapshot child : children){
                    SaveQuestion saveQuestion1 = child.getValue(SaveQuestion.class);
                    saveQuestion.add(saveQuestion1);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        ArrayAdapter<SaveQuestion> saveQ = new ArrayAdapter<SaveQuestion>(getActivity(),R.layout.activity_polls_replace, saveQuestion);
        setListAdapter(saveQ);

    }
}
