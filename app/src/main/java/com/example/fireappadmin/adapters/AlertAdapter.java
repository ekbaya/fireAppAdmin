package com.example.fireappadmin.adapters;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fireappadmin.R;
import com.example.fireappadmin.models.Alert;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AlertAdapter extends RecyclerView.Adapter<AlertAdapter.MyHolder> {
    private Context context;
    private List<Alert> alertList;

    public AlertAdapter(Context context, List<Alert> alertList) {
        this.context = context;
        this.alertList = alertList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate layout(row_alert.xml)
        View view = LayoutInflater.from(context).inflate(R.layout.row_alert, parent, false);
        return new AlertAdapter.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, final int position) {
        final String latitude = alertList.get(position).getLatitude();
        final String longitude = alertList.get(position).getLongitude();
        final String name = alertList.get(position).getName();
        final String phone = alertList.get(position).getPhone();
        final String status = alertList.get(position).getStatus();
        final String timestamp = alertList.get(position).getTime();

        //Converting timeStamp to dd/mm/yyyy hh:mm am/pm
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        calendar.setTimeInMillis(Long.parseLong(timestamp));
        String time = DateFormat.format("dd/MM/yyyy  hh:mm aa", calendar).toString();

        //set values
        holder.timeTv.setText(time);
        holder.statusTv.setText(status);
        holder.detailsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        holder.navigateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startGoogleMapNavigationActivity(alertList.get(position).getLatitude(), alertList.get(position).getLongitude());
            }
        });
    }

    private void startGoogleMapNavigationActivity(String latitude, String longitude) {
        Uri navigationIntentUri = Uri.parse("google.navigation:q="
                + Double.parseDouble(latitude) + ","
                + Double.parseDouble(longitude));//creating intent with latlong
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, navigationIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");

        /*For completeness, if the user doesn't have the maps app installed then it's going to be a
        good idea to catch the ActivityNotFoundException, then we can start the activity again
        without the maps app restriction, we can be pretty sure that we will never get to the
        Toast at the end since an internet browser is a valid application to launch this url scheme too.*/

        try {
            context.startActivity(mapIntent); // launching maps from a map application
        } catch (ActivityNotFoundException ex) {
            try {
                Intent unrestrictedIntent = new Intent(Intent.ACTION_VIEW, navigationIntentUri); // try to launch map using browser
                context.startActivity(unrestrictedIntent);
            } catch (ActivityNotFoundException innerEx) {
                Toast.makeText(context, "Please install a map application", Toast.LENGTH_LONG).show();// app is not installed and browser failed to launch the map
            }
        }
    }

    @Override
    public int getItemCount() {
        return alertList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{
        TextView timeTv;
        TextView statusTv;
        Button detailsBtn;
        Button navigateBtn;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            timeTv = itemView.findViewById(R.id.timeTv);
            statusTv = itemView.findViewById(R.id.statusTv);
            detailsBtn = itemView.findViewById(R.id.detailsBtn);
            navigateBtn = itemView.findViewById(R.id.navigateBtn);
        }
    }
}
