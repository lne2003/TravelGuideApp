package com.example.travelguide;

import android.os.Bundle;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.ArrayList;
import java.util.List;

public class DestinationsActivity extends AppCompatActivity {

    private static final String TAG = "DestinationsActivity";
    private RecyclerView destinationsRecyclerView;
    private DestinationsAdapter destinationsAdapter;
    private List<Destination> destinationList;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destinations);

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();

        // Initialize the RecyclerView
        destinationsRecyclerView = findViewById(R.id.destinationsRecyclerView);
        destinationsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize the destination list
        destinationList = new ArrayList<>();

        // Fetch data from Firestore
        fetchDestinationsFromFirestore();
    }

    private void fetchDestinationsFromFirestore() {
        db.collection("destinations").get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        if (task.getResult() != null) {
                            Log.d(TAG, "Number of documents fetched: " + task.getResult().size());
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                // Extract data from the document
                                String documentId = document.getId();
                                String name = document.getString("name");
                                String description = document.getString("description");
                                String imageUrl = document.getString("imageUrl");
                                GeoPoint position = document.getGeoPoint("position");
                                List<String> restaurants = (List<String>) document.get("restaurants");
                                String weather = document.getString("weather");

                                // Create a new Destination object with all the required parameters
                                if (name != null && description != null && imageUrl != null && position != null && restaurants != null && weather != null) {
                                    Destination destination = new Destination(name, description, imageUrl, position, restaurants, weather, documentId);
                                    destinationList.add(destination);
                                } else {
                                    Log.e(TAG, "Missing data in document: " + documentId);
                                }
                            }

                            if (destinationList.isEmpty()) {
                                Log.e(TAG, "No data found in the destinations collection");
                            } else {
                                // Set up the adapter
                                destinationsAdapter = new DestinationsAdapter(destinationList, DestinationsActivity.this);
                                destinationsRecyclerView.setAdapter(destinationsAdapter);
                            }
                        } else {
                            Log.e(TAG, "Task result is null");
                        }
                    } else {
                        Log.e(TAG, "Error getting documents: ", task.getException());
                    }
                });
    }
}
