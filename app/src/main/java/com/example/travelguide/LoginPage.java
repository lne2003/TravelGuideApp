package com.example.travelguide;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginPage extends AppCompatActivity {

    private EditText emailInput, passwordInput;
    private Button loginButton;
    private TextView forgotPasswordText; // Declare the TextView
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!NetworkUtils.isNetworkAvailable(this)) {
            Intent offlineIntent = new Intent(this, OfflineActivity.class);
            startActivity(offlineIntent);
            finish();
            return;
        }
        setContentView(R.layout.loginpage);

        // Initialize Firebase Auth and Firestore
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // Initialize input fields and button
        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        loginButton = findViewById(R.id.loginButton);

        // Initialize forgotPasswordText
        forgotPasswordText = findViewById(R.id.forgotPasswordText);

        // Set click listener for the Login button
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailInput.getText().toString().trim();
                String password = passwordInput.getText().toString().trim();

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginPage.this, "Please enter both email and password", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Authenticate the user with Firebase Authentication
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(LoginPage.this, task -> {
                            if (task.isSuccessful()) {
                                FirebaseUser user = mAuth.getCurrentUser();
                                if (user != null) {
                                    Toast.makeText(LoginPage.this, "Login successful", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(LoginPage.this, HomePage.class);
                                    startActivity(intent);
                                    finish();
                                }
                            } else {
                                Toast.makeText(LoginPage.this, "Login failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        // Set click listener for the Forgot Password text
        forgotPasswordText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to ForgotPasswordActivity
                Intent intent = new Intent(LoginPage.this, ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });
    }
}
