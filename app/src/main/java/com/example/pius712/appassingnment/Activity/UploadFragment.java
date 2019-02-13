package com.example.pius712.appassingnment.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.pius712.appassingnment.R;
import com.example.pius712.appassingnment.object.Obj_lost_info;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;


public class UploadFragment extends Fragment {
    private FirebaseDatabase database;
    private DatabaseReference mRef;
    private FirebaseAuth mAuth;
    private FirebaseStorage storage;
    private DatabaseReference mDatabase;
    private FragmentActivity myContext;
    TextView seekerID;
    EditText stuff;
    EditText phoneNum;
    EditText description;

    private static int RESULT_LOAD_IMAGE = 1;

    private UploadTask mUploadTask;
    private String imageFilePath;
    private Obj_lost_info lostInfo;
    private AlertDialog.Builder dialog;
//    private ImageView image1;




    @SuppressLint("WrongViewCast")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        database = FirebaseDatabase.getInstance();
        mRef = database.getReference();
        storage = FirebaseStorage.getInstance();
        mAuth = FirebaseAuth.getInstance();
        StorageReference storageRef = storage.getReference();

//        /*권한*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},0);
        }



    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_upload, null);
        stuff = view.findViewById(R.id.stuff);
        seekerID = view.findViewById(R.id.seekerID);
        phoneNum = view.findViewById(R.id.phoneNum);
        description = view.findViewById(R.id.description);

//        image1 = view.findViewById(R.id.image1);

        seekerID.setText(mAuth.getCurrentUser().getEmail());

//        view.findViewById(R.id.btn1).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                show(1);
//            }
//        });

        view.findViewById(R.id.rstbtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirmDialog();
            }
        });


        return view;
    }

    //버튼 클릭시 dialog
//    void show(final int imageNum) {
//        new AlertDialog.Builder(getContext()).setTitle("업로드할 이미지 선택")
//                .setNegativeButton("사진촬영", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        Toast.makeText(getContext(), "아직 지원하지 않는 기능입니다.", Toast.LENGTH_SHORT).show();
//                    }
//                })
//                .setPositiveButton("앨범선택", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        loadImagefromGallery(imageNum);
//                    }
//                })
//                .setNeutralButton("취소", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//                })
//                .show();
//    }
//
    //최종 확인 버튼 클릭시 dialog
    private void showConfirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("업로드 ");

        builder.setMessage("업로드 하시겠습니까?");
        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.dismiss();
//                startProgress();
                writeNewUser();
                HomeFragment newFragment = new HomeFragment();

                myContext.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, newFragment)
                        .commit();


            }
        });
        builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    //    갤러리에서 이미지 불러오기
//    public void loadImagefromGallery(int imageNum) {
//        //Intent 생성
//        Intent intent = new Intent(Intent.ACTION_PICK);
//        intent.setType("image/*");
//        startActivityForResult(intent, RESULT_LOAD_IMAGE);
//
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (resultCode == RESULT_OK) {
//            try {
//                final Uri imageUri = data.getData();
//                final InputStream imageStream = getContext().getContentResolver().openInputStream(imageUri);
//                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
//                image1.setImageBitmap(selectedImage);
//                imageFilePath = getPath(data.getData());
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//                Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_LONG).show();
//            }
//
//        } else {
//            Toast.makeText(getContext(), "You haven't picked Image", Toast.LENGTH_LONG).show();
//        }
//    }

    public void writeNewUser() {
        dialog = new AlertDialog.Builder(getContext());
//        Uri image = Uri.fromFile(new File(imageFilePath));
        try {
            String userid = erase(mAuth.getCurrentUser().getEmail());


            String userstuff = stuff.getText().toString();
            String userspec = description.getText().toString();
            String userphone = phoneNum.getText().toString();
//        String imageURI=image.toString();
            lostInfo = new Obj_lost_info(userstuff, userid, userphone, userspec);
//        lostInfo = new Obj_lost_info(userstuff,userid,userphone,userspec,imageURI);

            mDatabase = FirebaseDatabase.getInstance().getReference();
            mDatabase.child("user").child(userid).setValue(lostInfo);

            Toast.makeText(getActivity(), "성공",Toast.LENGTH_SHORT).show();
        }catch(Exception e){
            Toast.makeText(getActivity(), "실패",Toast.LENGTH_SHORT).show();
        }
        }
    public String erase(String id){
        int index = id.indexOf('@');
        return id.substring(0,index);

    }

//
//    public void fileUpload(){
//        lostInfo.seekerID = mAuth.getCurrentUser().getEmail();
//        lostInfo.stuff = stuff.getText().toString();
//        lostInfo.description = description.getText().toString();
//
//        FirebaseStorage storage = FirebaseStorage.getInstance("gs://appassignment-dfed1.appspot.com");
////위에서 생성한 FirebaseStorage 를 참조하는 storage를 생성한다
//        StorageReference storageRef = storage.getReference();
////        StorageReference mountainsRef = storageRef.child("mountains.jpg");
////        StorageReference mountainImagesRef = storageRef.child("images/mountains.jpg");
////        mountainsRef.getName().equals(mountainImagesRef.getName());    // true
////        mountainsRef.getPath().equals(mountainImagesRef.getPath());    // false
//
//        Uri file = Uri.fromFile(new File(imageFilePath));
////        includesForMetadata_custom();
//        StorageReference riversRef = storageRef.child("images/"+ lostInfo.description + "/" + lostInfo.stuff + "/" +lostInfo.seekerID +file.getLastPathSegment());
//
//
//        UploadTask uploadTask = riversRef.putFile(file);
//
//
//
//        uploadTask.addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception exception) {
//                // Handle unsuccessful uploads
//            }
//        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
//                // ...
//            }
//        });
//    }


//    public void includesForMetadata_custom() {
//        // [START custom_metadata]
//        StorageMetadata metadata = new StorageMetadata.Builder()
//                .setContentType()
//                .setCustomMetadata("description", lostInfo.description)
//                .setCustomMetadata("stuff", lostInfo.stuff)
//                .setCustomMetadata("phone", lostInfo.phone)
//                .setCustomMetadata("seekerID", lostInfo.seekerID)
//                .build();
//        // [END custom_metadata]
//    }

//    public String getPath(Uri uri){
//        String[] proj= {MediaStore.Images.Media.DATA};
//        android.support.v4.content.CursorLoader cursorLoader= new android.support.v4.content.CursorLoader(getContext(),uri,proj,null,null,null);
//        Cursor cursor = cursorLoader.loadInBackground();
//        int index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//        cursor.moveToFirst();
//        return cursor.getString(index);
//
//    }


    @Override
    public void onAttach(Activity activity) {
        myContext=(FragmentActivity) activity;
        super.onAttach(activity);
    }
}