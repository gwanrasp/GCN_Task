package com.jeevan.gcntask;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrationActivity extends AppCompatActivity {
    private EditText memail;
    private EditText musername;
    private EditText mpassword;
    private EditText mconfirmpass;

    private TextView mlogin_txt;
    private FirebaseAuth mAuth;
    private ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        mDialog = new ProgressDialog(this);
        memail   = findViewById(R.id.email_reg );
        musername = findViewById(R.id.username);
        mpassword = findViewById(R.id.password_reg);
        mconfirmpass = findViewById(R.id.password_conf);
        mlogin_txt = findViewById(R.id.login_txt);
        mlogin_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });


        //get hold of an instance of firebase auth
        mAuth  = FirebaseAuth.getInstance();

    }

    public void register(View V){
        attemptRegistration();
    }

    private void attemptRegistration(){
        //Reset error displayed in the forms
        memail.setError(null);
        mpassword.setError(null);

        //Store values at the time of the login attempt
        String email = memail.getText().toString().trim();
        String password= mpassword.getText().toString().trim();
        String username = musername.getText().toString().trim();


        boolean cancel = false;
        View focusView = null;
        if(TextUtils.isEmpty(password) || !isPasswordValid(password)){
            mpassword.setError("Password too short or does not match");
            focusView = mpassword;
            cancel = true;
        }

        if(TextUtils.isEmpty(username)){
            memail.setError("This field is required");
            focusView = memail;
            cancel = true;

        }





        if(TextUtils.isEmpty(email)){
            memail.setError("This field is required");
            focusView = memail;
            cancel = true;

        }
        else if(!isEmailValid(email)){
            memail.setError("The email address is invalid");
            focusView = memail;
            cancel = true;

        }


        if(cancel){
            focusView.requestFocus();
        }
        else{
            createFirebaseUSer();
        }



    }
    private boolean isEmailValid(String email){
        return email.contains("@");
    }

    private boolean isPasswordValid(String password){
        String confirmpassword = mconfirmpass.getText().toString().trim();
        return  confirmpassword.equals(password) && password.length()>5;

    }


    private void createFirebaseUSer(){
        String email = memail.getText().toString();
        String password = mpassword.getText().toString();
        mDialog.setMessage("Processing......");
        mDialog.show();
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(!task.isSuccessful()){
                    showErrorDialog("User Creation Failed");
                    mDialog.dismiss();
                }
                else{
                    Toast.makeText(getApplicationContext(),"Successful!!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), TodoActivity.class));

                    mDialog.dismiss();
                }
            }
        });

    }
    private void showErrorDialog(String msg){
        new AlertDialog.Builder(this)
                .setTitle("Oops")
                .setMessage(msg)
                .setPositiveButton(android.R.string.ok, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }



}



