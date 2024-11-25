package com.example.travelguide;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class DestinationsActivity extends BaseActivity {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private FirebaseFirestore db;
    private List<Destination> destinationList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destinations);

        recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progressBar);

        db = FirebaseFirestore.getInstance();
        destinationList = new ArrayList<>();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new DestinationsAdapter(destinationList, this));

        fetchDestinations();
    }

    private void fetchDestinations() {
        progressBar.setVisibility(View.VISIBLE);
        CollectionReference destinationsRef = db.collection("destinations");

        destinationsRef.get().addOnCompleteListener(task -> {
            progressBar.setVisibility(View.GONE);
            if (task.isSuccessful()) {
                List<DocumentSnapshot> docs = task.getResult().getDocuments();
                for (DocumentSnapshot doc : docs) {
                    destinationList.add(new Destination(
                            doc.getString("name"),
                            doc.getString("description"),
                            doc.getString("imageUrl"),
                            null,
                            null,
                            doc.getString("weather"),
                            doc.getId()
                    ));
                }
                recyclerView.getAdapter().notifyDataSetChanged();
            }
        });
    }

    @Override
    protected int getSelectedMenuItemId() {
        return R.id.nav_home; // Adjust based on the context
    }
}
