package com.example.haileyhultquist.qiosk;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class JobAcceptedActivity extends AppCompatActivity {

    private Button goButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_accepted);

        final Context context = this;

        goButton = (Button) findViewById(R.id.goButton);

        goButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent detailIntent = new Intent(context, UserActivity.class);

                detailIntent.putExtra("userType", "worker");

                startActivity(detailIntent);
            }
        });
    }
}
