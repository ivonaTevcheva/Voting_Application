package com.example.voting_application.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.voting_application.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private Button button, login_btn;
    private EditText userPassword, userEmail;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        button = (Button) findViewById(R.id.signup_btn);
        login_btn = (Button) findViewById(R.id.login_btn);
        userEmail = findViewById(R.id.user_email);
        userPassword = findViewById(R.id.user_password);
        mAuth = FirebaseAuth.getInstance();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openRegistration();
            }
        });

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = userEmail.getText().toString().trim();
                String password = userPassword.getText().toString().trim();

                if (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches() && !TextUtils.isEmpty(password)) {

                    loginUser(email, password);

                } else {
                    Toast.makeText(LoginActivity.this, "Please enter your credentials", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void loginUser(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser firebaseUser = mAuth.getCurrentUser();
                    if (firebaseUser.getDisplayName().equals("user")) {
                        //GOTO USER HOMEPAGE INTENT
                        Toast.makeText(LoginActivity.this, "User signed in as normal user", Toast.LENGTH_SHORT).show();

                    } else {
                        //GOTO ADMINISTRATOR HOMEPAGE INTENT
                        Toast.makeText(LoginActivity.this, "User signed in as administrator", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "User login failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void openRegistration() {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }
}