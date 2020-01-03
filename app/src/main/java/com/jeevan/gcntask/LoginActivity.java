package com.jeevan.gcntask;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {



    private EditText mEmail;
    private EditText mPassword;
    private FirebaseAuth mAuth;

    private ProgressDialog mDialog;
    sessionManagement session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDialog = new ProgressDialog(this);
        mEmail = findViewById((R.id.email));
        mPassword = findViewById(R.id.password);


        // TODO: Grab an instance of FirebaseAuth
        mAuth = FirebaseAuth.getInstance();





    }

    // Executed when Sign in button pressed

    public void signIn(View v){
        attemptLogin();

    }


    // Executed when Register button pressed
    public void signUp(View v){
        startActivity(new Intent(getApplicationContext(),RegistrationActivity.class));
    }




    // TODO: Complete the attemptLogin() method
    private void attemptLogin(){
        final String email = mEmail.getText().toString();
        String password = mPassword.getText().toString();

       if(TextUtils.isEmpty(email)){
            mEmail.setError("Email is Required");
       }

       if(TextUtils.isEmpty(password)){
           mPassword.setError("Password is Required");

       }
        mDialog.setMessage("Progressing..");
        mDialog.show();
        // Toast.makeText(this, "Progressing",Toast.LENGTH_SHORT).show();





        // TODO: Use FirebaseAuth to sign in with email & password
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(!task.isSuccessful()){

                    alertDialog("Problem Signing in");
                    mDialog.dismiss();

                }
                else{
                    session.createLoginSession(email);
                    Toast.makeText(getApplicationContext(),"Successful!!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
                   mDialog.dismiss();

                }



            }
        });




    }

    private void alertDialog(String message){
        new AlertDialog.Builder(this)
                .setTitle("Oops")
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }



}










