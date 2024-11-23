package com.example.travelguide;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class DestinationsActivity extends AppCompatActivity {

    private static final String TAG = "DestinationsActivity";
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private FirebaseFirestore db;
    private DestinationsAdapter destinationsAdapter;
    private List<Destination> destinationList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destinations);

        recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progressBar);

        db = FirebaseFirestore.getInstance();
        destinationList = new ArrayList<>();
        destinationsAdapter = new DestinationsAdapter(destinationList, this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(destinationsAdapter);

        fetchDestinations();
    }

    private void fetchDestinations() {
        progressBar.setVisibility(View.VISIBLE);

        CollectionReference destinationsRef = db.collection("destinations");
        destinationsRef.get().addOnCompleteListener(task -> {
            progressBar.setVisibility(View.GONE);
            if (task.isSuccessful()) {
                QuerySnapshot querySnapshot = task.getResult();
                if (querySnapshot != null && !querySnapshot.isEmpty()) {
                    destinationList.clear();
                    for (DocumentSnapshot document : querySnapshot) {
                        String name = document.getString("name");
                        String description = document.getString("description");
                        String imageUrl = document.getString("imageUrl");
                        String weather = document.getString("weather");
                        String documentId = document.getId();

                        Destination destination = new Destination(name, description, imageUrl, null, null, weather, documentId);
                        destinationList.add(destination);
                    }
                    destinationsAdapter.notifyDataSetChanged();
                } else {
                    Log.e(TAG, "No data found in the destinations collection");
                    Toast.makeText(this, "No destinations available", Toast.LENGTH_SHORT).show();
                }
            } else {
                Log.e(TAG, "Failed to fetch data", task.getException());
                Toast.makeText(this, "Failed to fetch data.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}