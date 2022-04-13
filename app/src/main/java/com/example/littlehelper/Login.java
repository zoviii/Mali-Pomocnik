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

public class Login extends AppCompatActivity implements View.OnClickListener {

    EditText editTextemail, textInputEditTextPassword;
    Button buttonLogin, singuptext;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        singuptext = (Button) findViewById(R.id.signuptext);
        singuptext.setOnClickListener(this);
        editTextemail = findViewById(R.id.emailLogin);
        textInputEditTextPassword = findViewById(R.id.password);
        buttonLogin =(Button) findViewById(R.id.loginbutton);
        buttonLogin.setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.signuptext:
                startActivity(new Intent(this, Signin.class));
                break;
            case R.id.loginbutton:
                userLogin();
                break;

        }

    }

    private void userLogin() {
        String email=editTextemail.getText().toString().trim();
        String password= textInputEditTextPassword.getText().toString().trim();

        if (email.isEmpty()) {
            editTextemail.setError("email is required");
            editTextemail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextemail.setError("please provide a valid email");
            editTextemail.requestFocus();
            return;
        }


        if (password.isEmpty()) {
            textInputEditTextPassword.setError("password is required");
            textInputEditTextPassword.requestFocus();
            return;
        }
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));

                }
                else{
                    Toast.makeText(getApplicationContext(), "Login failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}