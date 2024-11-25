package com.example.travelguide;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log; // Import Log for debugging
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

public class SignUpPage extends AppCompatActivity {

    private static final String TAG = "SignUpPage"; // Tag for debugging
    private EditText firstNameInput, surnameInput, emailInput, phoneNumberInput, passwordInput, confirmPasswordInput;
    private Button signupButton;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Check for network availability
        if (!NetworkUtils.isNetworkAvailable(this)) {
            Intent offlineIntent = new Intent(this, OfflineActivity.class);
            startActivity(offlineIntent);
            finish();
            return;
        }
        setContentView(R.layout.signup);

        // Initialize Firebase Auth and Firestore
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // Initialize input fields and button
        firstNameInput = findViewById(R.id.firstNameInput);
        surnameInput = findViewById(R.id.surnameInput);
        emailInput = findViewById(R.id.emailInput);
        phoneNumberInput = findViewById(R.id.phoneNumberInput);
        passwordInput = findViewById(R.id.passwordInput);
        confirmPasswordInput = findViewById(R.id.confirmPasswordInput);
        signupButton = findViewById(R.id.signupButton);

        // Set click listener for the Sign Up button
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = firstNameInput.getText().toString().trim();
                String surname = surnameInput.getText().toString().trim();
                String email = emailInput.getText().toString().trim();
                String phoneNumber = phoneNumberInput.getText().toString().trim();
                String password = passwordInput.getText().toString().trim();
                String confirmPassword = confirmPasswordInput.getText().toString().trim();

                if (firstName.isEmpty() || surname.isEmpty() || email.isEmpty() || phoneNumber.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                    Toast.makeText(SignUpPage.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!password.equals(confirmPassword)) {
                    Toast.makeText(SignUpPage.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Register the user with Firebase Authentication
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignUpPage.this, task -> {
                            if (task.isSuccessful()) {
                                FirebaseUser user = mAuth.getCurrentUser();
                                if (user != null) {
                                    // Save user details in Firestore
                                    Map<String, Object> userData = new HashMap<>();
                                    userData.put("firstName", firstName);
                                    userData.put("surname", surname);
                                    userData.put("email", email);
                                    userData.put("phoneNumber", phoneNumber);

                                    db.collection("users").document(user.getUid())
                                            .set(userData)
                                            .addOnSuccessListener(aVoid -> {
                                                Toast.makeText(SignUpPage.this, "Registration successful", Toast.LENGTH_SHORT).show();
                                                finish(); // Close the Sign Up activity
                                            })
                                            .addOnFailureListener(e -> {
                                                Log.e(TAG, "Failed to save user data", e);
                                                Toast.makeText(SignUpPage.this, "Failed to save user data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                            });
                                }
                            } else {
                                Log.e(TAG, "Registration failed", task.getException());
                                Toast.makeText(SignUpPage.this, "Registration failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }
}
