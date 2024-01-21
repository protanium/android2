package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class LoginActivity extends AppCompatActivity {

    Button signupButton;

    Button loginButton;

    TextView emailText;
    TextView passwordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        signupButton = findViewById(R.id.signup_button_loginact);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });

        emailText = findViewById(R.id.email_text_loginact);
        passwordText = findViewById(R.id.password_text_loginact);

        loginButton = findViewById(R.id.login_button_logginact);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Manager.db.signin(emailText.getText().toString(), passwordText.getText().toString(), result -> {
                    if(result){
                        Intent intent = new Intent(LoginActivity.this, HasLoggedInActivity.class);
                        startActivity(intent);
                    }else {
                        Toast.makeText(LoginActivity.this, "Başarısız", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}