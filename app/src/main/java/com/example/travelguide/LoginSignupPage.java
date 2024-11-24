package com.example.travelguide;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class LoginSignupPage extends AppCompatActivity {

    private Button loginButton, signupButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_signup);
        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.your_color));

        // Initialize the buttons
        loginButton = findViewById(R.id.loginButton);
        signupButton = findViewById(R.id.signupButton);

        // Set click listener for the Login button
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the LoginPage
                Intent intent = new Intent(LoginSignupPage.this, LoginPage.class);
                startActivity(intent);
            }
        });

        // Set click listener for the Sign Up button
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the SignUpPage
                Intent intent = new Intent(LoginSignupPage.this, SignUpPage.class);
                startActivity(intent);
            }
        });
    }
}