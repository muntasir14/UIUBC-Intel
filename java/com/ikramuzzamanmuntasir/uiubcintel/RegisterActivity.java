package com.ikramuzzamanmuntasir.uiubcintel;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {

    EditText emailEditText;
    EditText passwordEditText;
    EditText confirmPasswordEditText;
    Button registerButton;
    Button registerPageHaveAnotherAccountButton;
    ProgressBar registerProgress;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        emailEditText = findViewById(R.id.registerPageEmailEditText);
        passwordEditText = findViewById(R.id.registerPagePasswordEditText);
        confirmPasswordEditText = findViewById(R.id.registerPageConfirmPasswordEditText);

        registerButton = findViewById(R.id.registerPageRegisterButton);
        registerPageHaveAnotherAccountButton = findViewById(R.id.registerPageHaveAnotherAccountButton);
        registerProgress = findViewById(R.id.registerPageRegProgress);

        registerPageHaveAnotherAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String confirmPassword = confirmPasswordEditText.getText().toString();

                if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(confirmPassword)) {

                    if(password.equals(confirmPassword)){

                        registerProgress.setVisibility(View.VISIBLE);
                        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if(task.isSuccessful()){

                                    Intent toSetupActivity = new Intent(RegisterActivity.this, SetupActivity.class);
                                    startActivity(toSetupActivity);
                                    finish();
                                }
                                else{

                                    String errorMessage = task.getException().getMessage();
                                    Toast.makeText(RegisterActivity.this, "Error: " + errorMessage, Toast.LENGTH_LONG).show();
                                }
                                registerProgress.setVisibility(View.INVISIBLE);
                            }
                        });
                    }else{

                        Toast.makeText(RegisterActivity.this, "Password and confirm password didn't match!", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser != null){

            sendToMain();
        }
    }

    private void sendToMain() {

        Intent toMainActivity = new Intent(RegisterActivity.this, MainActivity.class);
        startActivity(toMainActivity);
        finish();
    }
}
