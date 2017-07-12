package com.example.haileyhultquist.qiosk;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class JobAddedActivity extends AppCompatActivity {

    private Button goButton;
    private String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_added);

        key = this.getIntent().getExtras().getString("key");

        final Context context = this;

        goButton = (Button) findViewById(R.id.goButton);

        goButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent detailIntent = new Intent(context, UserActivity.class);
                detailIntent.putExtra("userType", "employer");
                detailIntent.putExtra("key", key);
                startActivity(detailIntent);
            }
        });
    }
}
