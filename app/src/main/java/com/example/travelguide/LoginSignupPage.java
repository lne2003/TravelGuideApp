package com.example.travelguide;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class LoginSignupPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Skip login/signup and redirect to HomePage
        Intent intent = new Intent(LoginSignupPage.this, HomePage.class);
        startActivity(intent);
        finish();
    }
}