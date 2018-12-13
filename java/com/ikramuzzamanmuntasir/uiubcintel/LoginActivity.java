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

public class LoginActivity extends AppCompatActivity {

    EditText emailEditText;
    EditText passwordEditText;
    Button loginButton;
    Button createAnotherAccountButton;
    ProgressBar loginProgressBar;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        emailEditText = findViewById(R.id.registerPageEmailEditText);
        passwordEditText = findViewById(R.id.registerPageConfirmPasswordEditText);
        loginButton = findViewById(R.id.loginButton);
        loginProgressBar = findViewById(R.id.loginProgressBar);
        createAnotherAccountButton = findViewById(R.id.createAnotherAccountButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)){

                    loginProgressBar.setVisibility(View.VISIBLE);
                    mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()){

                                goToMainActivity();

                            } else {

                                String error = task.getException().getMessage();
                                Toast.makeText(LoginActivity.this, error, Toast.LENGTH_LONG).show();
                            }
                            loginProgressBar.setVisibility(View.INVISIBLE);
                        }
                    });
                }
            }
        });



        createAnotherAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                goToRegisterActivity();
            }
        });


    }

    private void goToRegisterActivity() {

        Intent gotoCreateAccount = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(gotoCreateAccount);
    }


    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser != null){

            goToMainActivity();
        }
    }

    private void goToMainActivity() {

        Intent toMainActivity = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(toMainActivity);
        finish();
    }
}
