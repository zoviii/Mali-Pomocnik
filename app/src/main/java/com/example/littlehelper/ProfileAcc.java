package com.example.littlehelper;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileAcc extends AppCompatActivity {

    Button backtohome;
    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_acc);

        backtohome=findViewById(R.id. backtohome);

        backtohome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        user= FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        final TextView FULLNAME = findViewById(R.id. fullnameempty);
        final TextView EMAIL = findViewById(R.id. emailempty);
        final TextView USER = findViewById(R.id. usernameempty);

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User profile = snapshot.getValue(User.class);
                        if(profile!=null){
                            String fullname = profile.fullname;
                            String email = profile.email;
                            String user = profile.username;


                            FULLNAME.setText(fullname);
                            EMAIL.setText(email);
                            USER.setText(user);

                        }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ProfileAcc.this, "something wrong has happened", Toast.LENGTH_SHORT).show();

            }
        });


    }
}