package com.example.haileyhultquist.qiosk;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JobViewActivity extends AppCompatActivity {

    // NEED TO HAVE
    // Title, userType, status, previous

    private String title;
    private String userType;
    private String status; // Wposted, Winprogress, Eposted, Einprogress, Wpending
    private String previous;
    private String key;
    private String PK;

    private Button acceptButton;
    private Button completeButton;
    private Button cancelButton;
    private Button backButton;

    private ServerRestClientUsage serverRestClientUsage;

    private TextView titleTextView;
    private TextView descriptionTextView;
    private TextView addressTextView;
    private TextView payTextView;
    private TextView employerTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_view2);

        title = this.getIntent().getExtras().getString("title");
        userType = this.getIntent().getExtras().getString("userType");
        status = this.getIntent().getExtras().getString("status");
        previous = this.getIntent().getExtras().getString("previous");
        key = this.getIntent().getExtras().getString("key");
        PK = this.getIntent().getExtras().getString("pk");

        titleTextView = (TextView) findViewById(R.id.job_title);
        descriptionTextView = (TextView) findViewById(R.id.job_description);
        addressTextView= (TextView) findViewById(R.id.job_address);
        payTextView = (TextView) findViewById(R.id.job_pay);
        employerTextView = (TextView) findViewById(R.id.posted_by);

        serverRestClientUsage = new ServerRestClientUsage();
        serverRestClientUsage.getAJob(key, PK, new ServerRestClientUsage.Callback<String>() {
            @Override
            public void onResponse(String s) throws JSONException {
                //JSONObject jsonObject = new JSONObject(s);
                JSONArray j = new JSONArray(s);
                JSONObject jsonObject = j.getJSONObject(0);
                titleTextView.setText(jsonObject.getString("title"));
                descriptionTextView.setText(jsonObject.getString("description"));
                addressTextView.setText(jsonObject.getString("address"));
                payTextView.setText("Payment rate: " + jsonObject.getString("payment"));
                employerTextView.setText("Posted by employer number " + jsonObject.getString("employer"));
            }
        });

        // API

        TextView titleTextView = (TextView)findViewById(R.id.job_title);
        titleTextView.setText(title);

        acceptButton = (Button)findViewById(R.id.accept);
        completeButton = (Button)findViewById(R.id.complete);
        cancelButton = (Button)findViewById(R.id.cancel);
        backButton = (Button)findViewById(R.id.back);

        LinearLayout ll = (LinearLayout)findViewById(R.id.ll);

        if (status.equals("Wposted")) {
            ll.removeView(completeButton);
            ll.removeView(cancelButton);
        } else if (status.equals("Winprogress")) {
            ll.removeView(acceptButton);
        } else if (status.equals("Eposted")) {
            ll.removeView(acceptButton);
            ll.removeView(completeButton);
        } else if (status.equals("Einprogress")) {
            ll.removeView(acceptButton);
        }  else if (status.equals("Wpending")) {
            ll.removeView(acceptButton);
            ll.removeView(completeButton);
        }

        final Context context = this;
        acceptButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // API

                Intent detailIntent = new Intent(context, JobAcceptedActivity.class);

                detailIntent.putExtra("userID", 7);
                detailIntent.putExtra("userType", userType);

                startActivity(detailIntent);
            }
        });

        completeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // API

                Intent detailIntent = new Intent(context, JobCompletedActivity.class);

                serverRestClientUsage = new ServerRestClientUsage();
                serverRestClientUsage.getAJob(key, PK, new ServerRestClientUsage.Callback<String>() {
                    @Override
                    public void onResponse(String s) throws JSONException {
                        Log.d("searchforthis", "done");
                    }
                });

                detailIntent.putExtra("userID", 7);
                detailIntent.putExtra("userType", userType);
                detailIntent.putExtra("key", key);

                startActivity(detailIntent);
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // API

                Intent detailIntent = new Intent(context, JobCompletedActivity.class);

                detailIntent.putExtra("userID", 7);
                detailIntent.putExtra("userType", userType);
                detailIntent.putExtra("key", key);

                startActivity(detailIntent);
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // API

                Intent detailIntent;
                if (previous.equals("job_board")) {
                    detailIntent = new Intent(context, JobListingActivity.class);
                } else {
                    detailIntent = new Intent(context, UserActivity.class);
                }

                detailIntent.putExtra("key", key);
                detailIntent.putExtra("userType", userType);

                startActivity(detailIntent);
            }
        });
    }
}
