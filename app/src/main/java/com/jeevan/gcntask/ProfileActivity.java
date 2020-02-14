package com.jeevan.gcntask;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.common.api.Response;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.jeevan.R;
import com.jeevan.flashchatnewfirebase.ChatListAdapter;
import com.jeevan.gcntask.Model.Data;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;


public class ProfileActivity extends AppCompatActivity {

//variable
private TextView tprofilename;
    private TextView tphoneno;
    private TextView tofficepost;
    private TextView tofficedepartment;




    //variable
    private String profilename;
    private String phoneno;
    private String officepost;
    private String officedepartment;


    // update variable
    private Button updprof;
    private String mprofilename;
    private String mphoneno;
    private String mofficepost;
    private String mofficedepartment;


    //for image

     ImageView idprof;
     String Document_img1="";

 ImageButton imgb;

    //Firebase
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        mAuth = FirebaseAuth.getInstance();

        FirebaseUser mUser = mAuth.getCurrentUser();
        String id = mUser.getUid();


        mDatabase = FirebaseDatabase.getInstance().getReference().child("Profiles").child(id);
        mDatabase.keepSynced(true);






        updprof = findViewById(R.id.update_prof);
        updprof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateData();


            }
        });





    }






    public void updateData() {

        AlertDialog.Builder myDialog = new AlertDialog.Builder(ProfileActivity.this);
        LayoutInflater inflater = LayoutInflater.from(ProfileActivity.this);
        final View myview = inflater.inflate(R.layout.updateprofile, null);
        myDialog.setView(myview);
        final AlertDialog dialog = myDialog.create();

//        idprof = findViewById(R.id.profileimage);
//
//        imgb = findViewById(R.id.imgbutton);
//        imgb.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d("Check","To check if it is working or not");
//            }
//        });




        updprof = myview.findViewById(R.id.saveprofile);


        updprof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                final EditText profilename = myview.findViewById(R.id.name_profile);
                final EditText phoneno = myview.findViewById(R.id.phn_no);
                final EditText officepost =myview.findViewById(R.id.post);
                final EditText officedepartment = myview.findViewById(R.id.department);

                mprofilename = profilename.getText().toString().trim();
                mphoneno = phoneno.getText().toString().trim();
                mofficepost = officepost.getText().toString().trim();
                 mofficedepartment = officedepartment.getText().toString().trim();
                String id = mDatabase.push().getKey();

                Data data = new Data(mprofilename, mphoneno, mofficepost,mofficedepartment, id);
                mDatabase.child(id).setValue(data);
                Toast.makeText(getApplicationContext(), "Successfully Inserted!!", Toast.LENGTH_SHORT).show();


                dialog.dismiss();




            }
        });


        dialog.show();





    }







//        private void selectImage() {
//        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };
//        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
//        builder.setTitle("Add Photo!");
//        builder.setItems(options, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int item) {
//                if (options[item].equals("Take Photo"))
//                {
//                    Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                    startActivityForResult(takePicture, 0);
//                }
//                else if (options[item].equals("Choose from Gallery"))
//                {
//                    Intent pickPhoto = new Intent(Intent.ACTION_PICK,
//                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                    startActivityForResult(pickPhoto , 1);
//                }
//                else if (options[item].equals("Cancel")) {
//                    dialog.dismiss();
//                }
//            }
//        });
//        builder.show();
//    }
//    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
//        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
//        switch(requestCode) {
//            case 0:
//                if(resultCode == RESULT_OK){
//                    Uri selectedImage = imageReturnedIntent.getData();
//                    idprof.setImageURI(selectedImage);
//                }
//
//                break;
//            case 1:
//                if(resultCode == RESULT_OK){
//                    Uri selectedImage = imageReturnedIntent.getData();
//                    idprof.setImageURI(selectedImage);
//                }
//                break;
//        }
//    }




}