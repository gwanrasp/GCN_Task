package com.jeevan.gcntask;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.google.firebase.auth.FirebaseAuth;
import com.jeevan.R;
import com.jeevan.flashchatnewfirebase.MainChatActivity;

public class DashboardActivity extends AppCompatActivity implements View.OnClickListener {

    private CardView mprofilecard, mtodocard, mchatcard, mleavecard, mschedulecard, mdocumentcard;

    private  FirebaseAuth mAuth;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.dashboard);
        //defining cards
        mprofilecard = findViewById(R.id.profile_card);
        mtodocard = findViewById(R.id.todo_card);
        mchatcard = findViewById(R.id.chat_card);
        mleavecard = findViewById(R.id.leave_card);
        mschedulecard = findViewById(R.id.schedule_card);
        mdocumentcard = findViewById(R.id.documents_card);

        //adding click listener
        mprofilecard.setOnClickListener(this);
        mtodocard.setOnClickListener(this);
        mchatcard.setOnClickListener(this);
        mleavecard.setOnClickListener(this);
        mschedulecard.setOnClickListener(this);
        mdocumentcard.setOnClickListener(this);

        mToolbar = findViewById(R.id.toolbar_home);
        setSupportActionBar(mToolbar);
        mAuth = FirebaseAuth.getInstance();


    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()){
            case R.id.profile_card:
                i= new Intent(this, ProfileActivity.class);
                startActivity(i);
                break;


            case R.id.todo_card:
                i= new Intent(this, TodoActivity.class);
                startActivity(i);
                break;


            case R.id.chat_card:
                i= new Intent(this, MainChatActivity.class);
                startActivity(i);
                break;





            default: break;
        }
;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mainmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                mAuth.signOut();
                startActivity(new Intent(getApplicationContext(),com.jeevan.gcntask.LoginActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
