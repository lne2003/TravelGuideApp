package com.example.travelguide;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import android.view.Window;
import android.view.WindowManager;

public class LoginPage extends AppCompatActivity {

    private EditText usernameInput, passwordInput;
    private Button loginButton;

    // Hard-coded credentials for demonstration purposes
    private final String validUsername = "user@example.com";
    private final String validPassword = "password123";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginpage);

        // Change the status bar color
        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.your_color));

        // Initialize the views
        usernameInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        loginButton = findViewById(R.id.loginButton);

        // Set a click listener on the login button
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameInput.getText().toString().trim();
                String password = passwordInput.getText().toString().trim();

                // Check if the fields are empty
                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginPage.this, "Please enter both fields", Toast.LENGTH_SHORT).show();
                } else {
                    // Check the credentials
                    if (username.equals(validUsername) && password.equals(validPassword)) {
                        Toast.makeText(LoginPage.this, "Login Successful", Toast.LENGTH_SHORT).show();

                        // Navigate to HomePage
                        Intent intent = new Intent(LoginPage.this, HomePage.class);
                        startActivity(intent);
                        finish(); // Finish the current activity
                    } else {
                        // Show an error message for incorrect password
                        passwordInput.setError("Invalid password. Please try again.");
                        passwordInput.requestFocus(); // Focus on the password field
                    }
                }
            }
        });
    }
}
