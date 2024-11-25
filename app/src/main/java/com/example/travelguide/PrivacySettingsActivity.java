package com.example.travelguide;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class PrivacySettingsActivity extends BaseActivity {

    private EditText newPasswordEditText, confirmPasswordEditText;
    private Button saveButton;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_settings);

        // Initialize Firebase
        mAuth = FirebaseAuth.getInstance();

        // Initialize UI elements
        newPasswordEditText = findViewById(R.id.newPasswordEditText);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);
        saveButton = findViewById(R.id.saveButton);

        // Set the click listener for the save button
        saveButton.setOnClickListener(v -> saveNewPassword());
    }

    private void saveNewPassword() {
        String newPassword = newPasswordEditText.getText().toString().trim();
        String confirmPassword = confirmPasswordEditText.getText().toString().trim();

        if (newPassword.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "Please fill in both fields.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            Toast.makeText(this, "Passwords do not match.", Toast.LENGTH_SHORT).show();
            return;
        }

        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            // Update password in Firebase Authentication
            user.updatePassword(newPassword)
                    .addOnSuccessListener(aVoid -> Toast.makeText(this, "Password updated successfully.", Toast.LENGTH_SHORT).show())
                    .addOnFailureListener(e -> Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
        } else {
            Toast.makeText(this, "User not logged in.", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected int getSelectedMenuItemId() {
        return R.id.nav_home; // Adjust based on the context
    }
}
