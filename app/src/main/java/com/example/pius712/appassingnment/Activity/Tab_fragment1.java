package com.example.pius712.appassingnment.Activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pius712.appassingnment.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Tab_fragment1 extends Fragment {
    private FirebaseAuth mAuth;
    FirebaseUser mUser;
    TextView textView;
    FirebaseDatabase database;
    DatabaseReference mRef;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tab1,container,false);
        database = FirebaseDatabase.getInstance();
        mRef = database.getReference();
        mAuth = FirebaseAuth.getInstance();

        mUser = mAuth.getCurrentUser();
        textView= v.findViewById(R.id.uId);
//        String userId = mRef.child("user").child(mAuth.getUid()).child("uid");
        textView.setText(mUser.getEmail());

        return v;
    }
}

