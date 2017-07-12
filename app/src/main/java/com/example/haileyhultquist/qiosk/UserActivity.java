package com.example.haileyhultquist.qiosk;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class UserActivity extends AppCompatActivity {

    // NEED TO HAVE
    // userType, key

    private ListView ipJobsView;
    private ListView pendingJobsView;
    private String userType;
    private TextView nameTextView;
    private TextView balanceTextView;
    private String key;

    private TextView userTypeView;
    private Button goToJobs_addJob_button;
    private Button logOutButton;

    private String status;

    private ArrayList<Job> ipJobsArrayList;
    private ArrayList<Job> pendingJobsArrayList;

    private ServerRestClientUsage serverRestClientUsage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        setTitle("My Profile");

        nameTextView = (TextView)findViewById(R.id.name);
        balanceTextView = (TextView)findViewById(R.id.balance);

        goToJobs_addJob_button = (Button)findViewById(R.id.gotojobs);
        userTypeView = (TextView) findViewById(R.id.userType);
        logOutButton = (Button)findViewById(R.id.log_out);

        userType = this.getIntent().getExtras().getString("userType");
        key = this.getIntent().getExtras().getString("key");

        ipJobsView = (ListView) findViewById(R.id.ip_job_list);
        pendingJobsView = (ListView) findViewById(R.id.pending_job_list);

        serverRestClientUsage = new ServerRestClientUsage();
        serverRestClientUsage.getUserData(key, new ServerRestClientUsage.Callback<String>() {
            @Override
            public void onResponse(String s) throws JSONException {
                JSONObject jsonObject = new JSONObject(s);
                nameTextView.setText(jsonObject.getString("firstName") + " " + jsonObject.getString("lastName"));
                balanceTextView.setText("Account balance: " + jsonObject.getString("accountBalance"));
            }
        });

        final Context context = this;

        if (userType.equals("worker")) {
            status = "W";
        } else {
            status = "E";
            serverRestClientUsage.getJobs(key, new ServerRestClientUsage.Callback<String>() {
                @Override
                public void onResponse(String s) throws JSONException {
                    ipJobsArrayList = new ArrayList<Job>();
                    JSONArray jsonArray = new JSONArray(s);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject j = ((JSONObject)(jsonArray.get(i)));
                        if (!j.getString("status").equals("open")) {
                            continue;
                        }
                        ipJobsArrayList.add(new Job(j.getString("title"),
                                j.getString("description"),
                                j.getString("payment"),
                                j.getString("pk")));
                    }
                    JobAdapter adapter = new JobAdapter(context, ipJobsArrayList);
                    ipJobsView.setAdapter(adapter);
                }
            });
        }

        /*
        ipJobsArrayList = new ArrayList<Job>();
        ipJobsArrayList.add(new Job("Paint the wall", "My house", 19));
        ipJobsArrayList.add(new Job("Change my car's tire", "Building Q", 15));
        ipJobsArrayList.add(new Job("Mow my lawn", "The Villas", 5));
        JobAdapter adapter = new JobAdapter(this, ipJobsArrayList);
        ipJobsView.setAdapter(adapter);*/

        pendingJobsArrayList = new ArrayList<Job>();
        pendingJobsArrayList.add(new Job("Do my homework", "The library", "10", "10"));
        JobAdapter adapter2 = new JobAdapter(this, pendingJobsArrayList);
        pendingJobsView.setAdapter(adapter2);

        ipJobsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, android.view.View view, int position, long id) {
                Job selectedJob = ipJobsArrayList.get(position);

                Intent detailIntent = new Intent(context, JobViewActivity.class);

                detailIntent.putExtra("title", selectedJob.getTitle());
                detailIntent.putExtra("userType", userType);
                status += "inprogress";
                detailIntent.putExtra("status", status);
                detailIntent.putExtra("previous", "user");
                detailIntent.putExtra("key", key);
                detailIntent.putExtra("pk", ipJobsArrayList.get(position).getPK());

                startActivity(detailIntent);
            }
        });

        pendingJobsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, android.view.View view, int position, long id) {
                Job selectedJob = pendingJobsArrayList.get(position);

                Intent detailIntent = new Intent(context, JobViewActivity.class);

                detailIntent.putExtra("title", selectedJob.getTitle());
                detailIntent.putExtra("userType", userType);
                status += "pending";
                detailIntent.putExtra("status", status);
                detailIntent.putExtra("previous", "user");
                detailIntent.putExtra("key", key);
                detailIntent.putExtra("pk", ipJobsArrayList.get(position).getPK());

                startActivity(detailIntent);
            }
        });

        if (userType.equals("employer")) {

            goToJobs_addJob_button.setText("Add a job");
            userTypeView.setText("Employer");

            goToJobs_addJob_button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // API

                    Intent detailIntent = new Intent(context, AddJobActivity.class);
                    detailIntent.putExtra("key", key);
                    startActivity(detailIntent);

                }
            });


        } else {
            goToJobs_addJob_button.setText("Go to job board");
            userTypeView.setText("WORKER");

            goToJobs_addJob_button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // API

                    Intent detailIntent = new Intent(context, JobListingActivity.class);
                    detailIntent.putExtra("key", key);
                    startActivity(detailIntent);

                }
            });
        }

        logOutButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // API
                Intent detailIntent = new Intent(context, StartActivity.class);
                startActivity(detailIntent);

            }
        });
    }
}
