package com.ikramuzzamanmuntasir.uiubcintel;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    Toolbar mainToolbar;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        mainToolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(mainToolbar);

        getSupportActionBar().setTitle("UIUBC Intel");
    }


    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if(currentUser == null){

            sendToLoginPage();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.action_logout_button:

                logout();
                return true;
            case R.id.action_settings_button:

                goToSetupActivity();
                return true;

                default:
                    return false;

        }


    }

    private void goToSetupActivity() {

        Intent toSetupActivity = new Intent(MainActivity.this, SetupActivity.class);
        startActivity(toSetupActivity);
    }

    private void logout() {

        mAuth.signOut();
        sendToLoginPage();
    }

    private void sendToLoginPage() {

        Intent sendToLogin = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(sendToLogin);
        finish();
    }
}
