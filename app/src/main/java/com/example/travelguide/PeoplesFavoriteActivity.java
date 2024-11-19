package com.example.travelguide;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class PeoplesFavoriteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peoples_favorite);

        TextView peoplesFavoriteTextView = findViewById(R.id.peoplesFavoriteTextView);
        peoplesFavoriteTextView.setText("People's favorite places coming soon!");
    }
}
