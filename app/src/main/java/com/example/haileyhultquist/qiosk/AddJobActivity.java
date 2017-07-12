package com.example.haileyhultquist.qiosk;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;

public class AddJobActivity extends AppCompatActivity {

    private Button addButton;
    private ServerRestClientUsage serverRestClientUsage;

    private String key;

    private EditText title;
    private EditText description;
    private EditText pay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_job);

        setTitle("Add a Job");

        final Context context = this;
        serverRestClientUsage = new ServerRestClientUsage();

        key = this.getIntent().getExtras().getString("key");

        addButton = (Button) findViewById(R.id.submit);

        title = (EditText) findViewById(R.id.JobTitle);
        description = (EditText) findViewById(R.id.JobAddress);
        pay = (EditText) findViewById(R.id.JobPay);

        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String t = title.getText().toString();
                String d = description.getText().toString();
                String p = pay.getText().toString();

                serverRestClientUsage.postJob(context, key, t, d, p, new ServerRestClientUsage.Callback<String>() {
                    @Override
                    public void onResponse(String s) throws JSONException {
                        Intent detailIntent = new Intent(context, JobAddedActivity.class);
                        detailIntent.putExtra("key", key);
                        startActivity(detailIntent);
                    }
                });

            }
        });
    }
}
