package com.example.fireappadmin.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fireappadmin.R;
import com.example.fireappadmin.adapters.AlertAdapter;
import com.example.fireappadmin.controllers.AlertAPI;
import com.example.fireappadmin.models.Alert;
import com.example.fireappadmin.services.AlertsListener;
import com.google.firebase.database.DatabaseError;

import java.util.List;

public class OpenFragment extends Fragment implements AlertsListener.LoadAlertsListener {
    private RecyclerView alerts_recyclerview;
    private ProgressBar progressBar;
    private AlertAPI alertAPI;
    private AlertAdapter alertAdapter;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_open, container, false);

        alerts_recyclerview = root.findViewById(R.id.alerts_recyclerview);
        progressBar = root.findViewById(R.id.progressBar);

        //setting its properties
        alerts_recyclerview.setHasFixedSize(true);
        alerts_recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));

        alertAPI = new AlertAPI(getActivity());
        alertAPI.setLoadAlertsListener(this);
        alertAPI.loadOpenAlerts();
        return root;
    }

    @Override
    public void onAlertsReceived(List<Alert> alertList) {
        if (alertList.size() != 0){
            alertAdapter = new AlertAdapter(getActivity(), alertList);
            //set adapter to recyclerView
            alerts_recyclerview.setAdapter(alertAdapter);
            progressBar.setVisibility(View.GONE);
            alerts_recyclerview.setVisibility(View.VISIBLE);
        }
        else {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(getActivity(), "There are no open alerts", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onDatabaseError(DatabaseError error) {
        progressBar.setVisibility(View.GONE);
        Toast.makeText(getActivity(), "Error loading alerts, "+error.getMessage(), Toast.LENGTH_SHORT).show();

    }
}