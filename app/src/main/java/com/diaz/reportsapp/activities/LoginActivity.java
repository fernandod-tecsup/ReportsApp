package com.diaz.reportsapp.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.diaz.reportsapp.R;
import com.diaz.reportsapp.models.User;
import com.diaz.reportsapp.repositories.UserRepositories;

public class LoginActivity extends AppCompatActivity {

    private EditText emailInput;
    private EditText passwordInput;
    private Button loginButton;
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailInput = findViewById(R.id.email_input);
        passwordInput = findViewById(R.id.password_input);

        loginButton = findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callLogin();
            }
        });
        registerButton = findViewById(R.id.register_button);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRegistrer();
            }
        });
        verifySession();
    }

    private void verifySession() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        if (sp.getBoolean("islogged",false)){
            goMain();
        }
    }

    private void goMain() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    private void showRegistrer() {
        startActivity(new Intent(this,RegistrerActivity.class));
    }

    private void callLogin() {
        String email = emailInput.getText().toString();
        String password = passwordInput.getText().toString();

        if (email.isEmpty() || password.isEmpty()){
            Toast.makeText(this, "Complete todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }
        User user = UserRepositories.login(email,password);
        if (user == null){
            Toast.makeText(this, "Email y/o password invalido", Toast.LENGTH_SHORT).show();
            return;
        }
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        boolean success = sp.edit()
                .putString("email",email)
                .putLong("id", user.getId())
                .putBoolean("islogged", true)
                .commit();
        goMain();
    }
}
