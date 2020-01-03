package com.jeevan.gcntask;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jeevan.gcntask.Model.Data;

import java.text.DateFormat;
import java.util.Date;

public class TodoActivity extends AppCompatActivity {


    private Toolbar mToolbar;
    private FloatingActionButton fabBtn;

    //Firebase
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;


    //recycler
    private FirebaseRecyclerAdapter adapter;
    private RecyclerView mRecyclerView;

    //update input field
    private EditText titleUp;
    private EditText noteUp;
    private Button btnDeleteUp;
    private  Button btnUpdate;


    //variable
    private String title;
    private String note;
    private String post_key;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mToolbar = findViewById(R.id.toolbar_home);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("TO-DO Lists");
        mAuth = FirebaseAuth.getInstance();

        FirebaseUser mUser  = mAuth.getCurrentUser();
        String uId = mUser.getUid();


        mDatabase = FirebaseDatabase.getInstance().getReference().child("TaskNote").child(uId);
        mDatabase.keepSynced(true);

        //recycler...

        mRecyclerView = findViewById(R.id.recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(layoutManager);



        fabBtn = findViewById(R.id.floating_action_button);
        fabBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder myDialog = new AlertDialog.Builder(TodoActivity.this);
                LayoutInflater inflater = LayoutInflater.from (TodoActivity.this);
                View myView  = inflater.inflate(R.layout.custominputfield, null);
                myDialog.setView(myView);

                final AlertDialog dialog = myDialog.create();
                final EditText title = myView.findViewById(R.id.edt_title);
                final EditText note= myView.findViewById(R.id.edt_note);
                Button btnSave = myView.findViewById(R.id.btn_save);
                btnSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String myTitle = title.getText().toString().trim();
                        String mNote = note.getText().toString().trim();
                        if (TextUtils.isEmpty(myTitle)) {
                            title.setError("Required Field...");
                            return;
                        }

                        if (TextUtils.isEmpty(mNote)) {
                            note.setError("Required Field...");
                            return;
                        }

                        String id = mDatabase.push().getKey();
                        String date = DateFormat.getDateInstance().format(new Date());


                        Data data = new Data(myTitle, mNote, date, id);
                        mDatabase.child(id).setValue(data);
                        Toast.makeText(getApplicationContext(), "Successfully Inserted!!", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    

                    }
                });



                dialog.show();



            }
        });



    }




    @Override
    protected void onStart() {
        super.onStart();


        FirebaseRecyclerOptions<Data> options =
                new FirebaseRecyclerOptions.Builder<Data>()
                        .setQuery(mDatabase, Data.class)
                        .build();

        FirebaseRecyclerAdapter adapter = new FirebaseRecyclerAdapter<Data, myViewHolder>(options) {
            @Override
            public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_data, parent, false);

                return new myViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(myViewHolder holder, final int position, final Data model) {
                // Bind the image_details object to the BlogViewHolder
                // ...

                holder.setTitle(model.getTitle());
                holder.setNote(model.getNote());
                holder.setDate(model.getDate());

                holder.myView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        post_key = getRef(position).getKey();
                     title = model.getTitle();
                        note = model.getNote();




                        updateData();
                    }
                });


            }
        };


        adapter.startListening();
        mRecyclerView.setAdapter(adapter);


    }










    public static class myViewHolder extends RecyclerView.ViewHolder{

        View myView;

        public myViewHolder(View itemView){
            super(itemView);
            myView = itemView;


        }


        public void setTitle(String title){
            TextView mTitle = myView.findViewById(R.id.title);
            mTitle.setText(title);
        }


        public void setNote(String note){
            TextView mNote = myView.findViewById(R.id.note);
            mNote.setText(note);
        }


        public  void setDate(String date){
            TextView mDate = myView.findViewById(R.id.date);
            mDate.setText(date);
        }



    }



    public void updateData(){

        AlertDialog.Builder myDialog = new AlertDialog.Builder(TodoActivity.this);
        LayoutInflater inflater = LayoutInflater.from(TodoActivity.this);
        View myview = inflater.inflate(R.layout.updateinputfield, null);
        myDialog.setView(myview);
        final AlertDialog dialog = myDialog.create();

        titleUp = myview.findViewById(R.id.edt_title_upd);
        noteUp = myview.findViewById(R.id.edt_note_upd);


        titleUp.setText(title);
        titleUp.setSelection(title.length());
        noteUp.setText(note);
        noteUp.setSelection(note.length());



        btnDeleteUp = myview.findViewById(R.id.btn_delete);
        btnUpdate = myview.findViewById(R.id.btn_update);


        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                title = titleUp.getText().toString().trim();
                note = noteUp.getText().toString().trim();

                String mDate = DateFormat.getDateInstance().format(new Date());


                Data data = new Data(title, note, mDate, post_key);

                mDatabase.child(post_key).setValue(data);



                dialog.dismiss();

            }
        });


        btnDeleteUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mDatabase.child(post_key).removeValue();


                dialog.dismiss();
            }
        });


        dialog.show();


    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.mainmenu, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected( MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.log_out:
//                mAuth.signOut();
//                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }

}
