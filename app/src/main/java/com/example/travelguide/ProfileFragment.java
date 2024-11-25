package com.example.travelguide;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ProfileFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Check for offline mode
        if (!NetworkUtils.isNetworkAvailable(getContext())) {
            Toast.makeText(getContext(), "No internet connection.", Toast.LENGTH_SHORT).show();
            // Inflate and return the offline layout
            return inflater.inflate(R.layout.fragment_offline_profile, container, false);
        }

        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        // Setup logout button functionality
        Button logoutButton = rootView.findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(v -> {
            // Redirect to LoginSignupPage on logout
            Intent intent = new Intent(getActivity(), LoginSignupPage.class);
            startActivity(intent);
            if (getActivity() != null) {
                getActivity().finish();
            }
        });

        return rootView;
    }
}
