package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SignupActivity extends AppCompatActivity {
    Button loginButton;

    Button signupButton;

    TextView nameText;
    TextView lastNameText;

    TextView emailText;

    TextView passwordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        loginButton = findViewById(R.id.login_button_signupact);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        nameText = findViewById(R.id.name_text);
        lastNameText = findViewById(R.id.lastname_text);
        emailText = findViewById(R.id.email_text_signupact);
        passwordText = findViewById(R.id.password_text_signupact);
        signupButton = findViewById(R.id.signup_button_signupact);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Manager.db.signup(nameText.getText().toString(), lastNameText.getText().toString(),
                        emailText.getText().toString(), passwordText.getText().toString(), result -> {
                    if(result){
                        Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(SignupActivity.this, "Başarısız", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
}