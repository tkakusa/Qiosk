package com.example.haileyhultquist.qiosk;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONException;

public class JobCompletedActivity extends AppCompatActivity {

    private Button goButton;
    private String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_completed);

        final Context context = this;

        final String userType = this.getIntent().getExtras().getString("userType");
        key = this.getIntent().getExtras().getString("key");

        goButton = (Button) findViewById(R.id.goButton);

        goButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent detailIntent = new Intent(context, UserActivity.class);

                detailIntent.putExtra("userType", userType);
                detailIntent.putExtra("key", key);

                startActivity(detailIntent);
            }
        });



    }
}
