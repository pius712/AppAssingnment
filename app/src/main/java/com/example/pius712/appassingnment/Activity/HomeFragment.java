package com.example.pius712.appassingnment.Activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.pius712.appassingnment.HomeRecyclerAdapter;
import com.example.pius712.appassingnment.R;
import com.example.pius712.appassingnment.object.Obj_lost_info;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.firebase.ui.auth.AuthUI.TAG;


public class HomeFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Obj_lost_info lostInfo;
    private ArrayList<Obj_lost_info> lostInfoList;
    private StorageReference mReference;
    private FirebaseStorage mDatabase;
    private ChildEventListener mChild;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, null);
        mRecyclerView = view.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);


//
//        mDatabase = FirebaseStorage.getInstance("gs://appassignment-dfed1.appspot.com");
//        mReference = mDatabase.getReference();

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        upDateData();

    }

    @Override
    public void onResume() {
        super.onResume();


    }

    //    private void initDatabase(){
//        mChild = new ChildEventListener() {
//            @Override
//            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//            }
//
//            @Override
//            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//            }
//
//            @Override
//            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        };
//        mReference.addChildEventListener(mChild);
//    }


    public void upDateData() {
        lostInfoList = new ArrayList<>();
        FirebaseDatabase.getInstance().getReference().child("user").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get user information
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {

                    Obj_lost_info lostInfo = dsp.getValue(Obj_lost_info.class);


                    lostInfoList.add(lostInfo);

                    Log.e("DATA",""+lostInfo.seekerID);

                }
                Log.e("DATA", ""+lostInfoList.get(0));
                Log.e("DATA", ""+lostInfoList.get(1));
                mAdapter = new HomeRecyclerAdapter(lostInfoList);
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
//        lostInfoList = new ArrayList<>();
//        lostInfoList.add(new Obj_lost_info(name, stt, desc, num));
    }
//    public void update(){
//        lostInfoList = new ArrayList<>();
//        lostInfoList.add(new Obj_lost_info("KIM","PHONE","01021141158","ㅇㅇ"));
//    }


    public void includesForDownloadFiles() throws IOException {

        mDatabase = FirebaseStorage.getInstance("gs://appassignment-dfed1.appspot.com");
        mReference = mDatabase.getReference();

        // [START download_via_url]
        mReference.child("users/me/profile.png").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // Got the download URL for 'users/me/profile.png'
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });
        // [END download_via_url]
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        mReference.removeEventListener(mChild);
//    }
    }

}