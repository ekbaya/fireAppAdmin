package com.example.fireappadmin.services;

import com.example.fireappadmin.models.Alert;
import com.google.firebase.database.DatabaseError;

import java.util.List;

public interface AlertsListener {
    interface LoadAlertsListener{
        void onAlertsReceived(List<Alert> alertList);
        void onDatabaseError(DatabaseError error);
    }
}
