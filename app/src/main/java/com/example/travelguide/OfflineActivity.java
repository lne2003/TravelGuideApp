package com.example.travelguide;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class OfflineActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline);

        Button retryButton = findViewById(R.id.retryButton);
        retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retry network connection
                if (NetworkUtils.isNetworkAvailable(OfflineActivity.this)) {
                    Intent intent = new Intent(OfflineActivity.this, LoginSignupPage.class);
                    startActivity(intent);
                    finish();
                } else {
                    // Show a message if still offline
                    retryButton.setText("Still Offline. Try Again?");
                }
            }
        });
    }
}
