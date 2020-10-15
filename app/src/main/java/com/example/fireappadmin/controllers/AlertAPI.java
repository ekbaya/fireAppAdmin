package com.example.fireappadmin.controllers;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.fireappadmin.models.Alert;
import com.example.fireappadmin.services.AlertsListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AlertAPI {
    private Context context;
    private DatabaseReference reference;
    private FirebaseDatabase database;
    private List<Alert> alertList;
    private AlertsListener.LoadAlertsListener loadAlertsListener;

    public AlertAPI(Context context) {
        this.context = context;
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Alerts");
        alertList = new ArrayList<>();
    }

    public void loadOpenAlerts(){
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                alertList.clear();
                for (DataSnapshot ds: snapshot.getChildren()){
                    Alert alert = ds.getValue(Alert.class);
                    assert alert != null;
                    if (!alert.getStatus().equalsIgnoreCase("Closed")){
                        alertList.add(alert);
                    }
                }
                loadAlertsListener.onAlertsReceived(alertList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                loadAlertsListener.onDatabaseError(error);
            }
        });
    }

    public AlertsListener.LoadAlertsListener getLoadAlertsListener() {
        return loadAlertsListener;
    }

    public void setLoadAlertsListener(AlertsListener.LoadAlertsListener loadAlertsListener) {
        this.loadAlertsListener = loadAlertsListener;
    }
}
