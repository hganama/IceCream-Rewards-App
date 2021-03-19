package com.ksu.icecreamrewardsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

public class LoginActivity extends AppCompatActivity {

    Button loginButton;
    EditText username, password;
    FirebaseRemoteConfig mFirebaseRemoteConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginButton = findViewById(R.id.button);
        username = findViewById(R.id.editText2);
        password = findViewById(R.id.editText3);

        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();

        mFirebaseRemoteConfig.fetch(1000)
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "Fetch Succeeded",
                                    Toast.LENGTH_SHORT).show();

                            // After config data is successfully fetched, it must be activated before newly fetched
                            // values are returned.
                            mFirebaseRemoteConfig.activateFetched();
                        } else {
                            Toast.makeText(LoginActivity.this, "Fetch Failed",
                                    Toast.LENGTH_SHORT).show();
                        }
                        //displayWelcomeMessage();
                    }
                });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String usernameString = username.getText().toString();
                String passwordString = password.getText().toString();

                if(TextUtils.isEmpty(usernameString)) {
                    username.setError("Username is required!");
                }

                if(TextUtils.isEmpty(passwordString)) {
                    password.setError("Password is required!");
                }

                String firebaseUsername = mFirebaseRemoteConfig.getString("username");
                String firebasePassword = mFirebaseRemoteConfig.getString("password");

                if(firebaseUsername.equals(usernameString) && firebasePassword.equals(passwordString)) {
                    Toast.makeText(LoginActivity.this, "Login successful",
                            Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                } else {
                    Toast.makeText(LoginActivity.this, "Login failed",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });


    }




}
