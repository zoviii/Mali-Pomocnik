package com.example.littlehelper;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Signin extends AppCompatActivity implements View.OnClickListener {

    EditText textInputEditTextFullname, textInputEditTextUsername, textInputEditTextPassword, textInputEditTextEmail;
    Button buttonSignup, buttonalreadyhaveacc;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        mAuth = FirebaseAuth.getInstance();
        textInputEditTextFullname = (EditText) findViewById(R.id.fullname);
        textInputEditTextUsername = (EditText) findViewById(R.id.user);
        textInputEditTextPassword = (EditText) findViewById(R.id.password_register);
        textInputEditTextEmail = (EditText) findViewById(R.id.email);
        buttonSignup = (Button) findViewById(R.id.registerbutton);
        buttonSignup.setOnClickListener(this);
        buttonalreadyhaveacc = (Button) findViewById(R.id.alreadyhavetext);
        buttonalreadyhaveacc.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.alreadyhavetext:
                startActivity(new Intent(this, com.example.littlehelper.Login.class));
                break;
            case R.id.registerbutton:
                startActivity(new Intent(this, com.example.littlehelper.Login.class));
                registerUser();
                break;
        }

    }

    private void registerUser() {
        String email = textInputEditTextEmail.getText().toString().trim();
        String password = textInputEditTextPassword.getText().toString().trim();
        String username = textInputEditTextUsername.getText().toString().trim();
        String fullname = textInputEditTextFullname.getText().toString().trim();


        if (fullname.isEmpty()) {
            textInputEditTextFullname.setError("Full name is required");
            textInputEditTextFullname.requestFocus();
            return;
        }

        if (username.isEmpty()) {
            textInputEditTextFullname.setError("username required");
            textInputEditTextFullname.requestFocus();
            return;
        }

        if (email.isEmpty()) {
            textInputEditTextEmail.setError("email is required");
            textInputEditTextEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            textInputEditTextEmail.setError("please provide a valid email");
            textInputEditTextEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            textInputEditTextPassword.setError("password is required");
            textInputEditTextPassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            textInputEditTextPassword.setError("password is less than 6 characters");
            textInputEditTextPassword.requestFocus();
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            User user = new User(username, fullname, email);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if (task.isSuccessful()) {
                                        Toast.makeText(getApplicationContext(), "Registered successfull", Toast.LENGTH_LONG).show();

                                    } else {
                                        Toast.makeText(getApplicationContext(), "Register failed", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });

                        } else {
                            Toast.makeText(getApplicationContext(), "Register failed, try again!", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}



