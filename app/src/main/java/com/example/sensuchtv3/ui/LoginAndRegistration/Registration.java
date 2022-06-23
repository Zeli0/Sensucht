package com.example.sensuchtv3.ui.LoginAndRegistration;


import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sensuchtv3.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Registration extends AppCompatActivity implements View.OnClickListener{
    private FirebaseAuth mAuth;

    private EditText userName, userEmail, userPassword;

    private Button RegisterUserButt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mAuth = FirebaseAuth.getInstance();

        RegisterUserButt = (Button) findViewById(R.id.RegisterUser);
        RegisterUserButt.setOnClickListener(this);

        userEmail = (EditText) findViewById(R.id.UserEmailReg);
        userName = (EditText) findViewById(R.id.UserName);
        userPassword = (EditText) findViewById(R.id.UserPasswordReg);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.RegisterUser:
                registerUser();
                break;
        }
    }

    private void registerUser() {
        String name = userName.getText().toString().trim();
        String email = userEmail.getText().toString().trim();
        String password = userPassword.getText().toString().trim();

        if (name.isEmpty()){
            userName.setError("Name is Required!");
            userName.requestFocus();
            return;
        }
        if (email.isEmpty()){
            userEmail.setError("Email is Required!");
            userEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            userEmail.setError("Please Provide Valid Email!");
            userEmail.requestFocus();
            return;
        }

        if (password.isEmpty()){
            userPassword.setError("Password is Required!");
            userPassword.requestFocus();
            return;
        }

        if (password.length() < 6){
            userPassword.setError("Password must be more than 6 characters!");
            userPassword.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            User user = new User(name, email);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Toast.makeText(Registration.this, "Account has been registered", Toast.LENGTH_SHORT).show();

                                            }
                                            else{
                                                Toast.makeText(Registration.this, "User registration has failed!", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        }
                        else{
                            Toast.makeText(Registration.this, "Registration has failed!", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }
}













