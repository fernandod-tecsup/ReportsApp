package com.diaz.reportsapp.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.diaz.reportsapp.R;
import com.diaz.reportsapp.repositories.UserRepositories;

public class RegistrerActivity extends AppCompatActivity {

    private EditText fullnameInput;
    private EditText emailInput;
    private EditText passwordnput;
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrer);

        fullnameInput = findViewById(R.id.fullname_input);
        emailInput = findViewById(R.id.email_input);
        passwordnput = findViewById(R.id.password_input);
        registerButton = findViewById(R.id.register_button);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callRegistrer();
            }
        });
    }

    private void callRegistrer() {
        try {
            String fullname = fullnameInput.getText().toString();
            String email = emailInput.getText().toString();
            String password = passwordnput.getText().toString();

            if (fullname.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Ingrese todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }
            UserRepositories.create(fullname, email, password);
            Toast.makeText(this, "Registro satisfactorio", Toast.LENGTH_SHORT).show();
            finish();
        } catch (Exception e) {
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
