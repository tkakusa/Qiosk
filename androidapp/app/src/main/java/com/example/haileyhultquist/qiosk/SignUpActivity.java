package com.example.haileyhultquist.qiosk;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONException;

public class SignUpActivity extends AppCompatActivity {

    private Button goButtonWorker;
    private Button goButtonEmployer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        setTitle("Sign Up");

        final Context context = this;

        goButtonWorker = (Button) findViewById(R.id.submit_worker);

        goButtonWorker.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent detailIntent = new Intent(context, UserActivity.class);
                detailIntent.putExtra("userID", 6);
                detailIntent.putExtra("userType", "worker");
                startActivity(detailIntent);
            }
        });

        goButtonEmployer = (Button) findViewById(R.id.submit_employer);

        goButtonEmployer.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent detailIntent = new Intent(context, UserActivity.class);
                detailIntent.putExtra("userID", 6);
                detailIntent.putExtra("userType", "employer");
                startActivity(detailIntent);
            }
        });
    }
}
