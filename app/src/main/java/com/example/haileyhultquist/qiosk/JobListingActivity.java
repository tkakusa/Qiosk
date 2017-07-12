package com.example.haileyhultquist.qiosk;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JobListingActivity extends AppCompatActivity {

    private ListView jobListingsView;
    private Button myProfileButton;

    private String key;

    private ArrayList<Job> jobsArrayList;

    private ServerRestClientUsage serverRestClientUsage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_listing);

        final String userType = this.getIntent().getExtras().getString("userType");

        jobListingsView = (ListView) findViewById(R.id.job_listings);

        myProfileButton = (Button) findViewById(R.id.profile_btn);
        key = this.getIntent().getExtras().getString("key");

        serverRestClientUsage = new ServerRestClientUsage();

        final Context context = this;

        serverRestClientUsage.getJobs(key, new ServerRestClientUsage.Callback<String>() {
            @Override
            public void onResponse(String s) throws JSONException {
                jobsArrayList = new ArrayList<Job>();
                JSONArray jsonArray = new JSONArray(s);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject j = ((JSONObject)(jsonArray.get(i)));
                    jobsArrayList.add(new Job(j.getString("title"),
                                        j.getString("description"),
                                        j.getString("payment"),
                                        j.getString("pk")));
                }
                JobAdapter adapter = new JobAdapter(context, jobsArrayList);
                jobListingsView.setAdapter(adapter);
            }
        });


        jobListingsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, android.view.View view, int position, long id) {

                Job selectedJob = jobsArrayList.get(position);

                Intent detailIntent = new Intent(context, JobViewActivity.class);

                detailIntent.putExtra("title", selectedJob.getTitle());
                detailIntent.putExtra("userType", userType);
                detailIntent.putExtra("status", "Wposted");
                detailIntent.putExtra("previous", "job_board");
                detailIntent.putExtra("key", key);
                detailIntent.putExtra("pk", selectedJob.getPK());
                Log.d("searchforthis", "the pk issss " + selectedJob.getPK());

                startActivity(detailIntent);
            }
        });

        myProfileButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent detailIntent = new Intent(context, UserActivity.class);
                detailIntent.putExtra("userType", "worker");
                detailIntent.putExtra("key", key);

                startActivity(detailIntent);
            }
        });
    }
}
