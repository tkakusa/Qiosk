package com.example.haileyhultquist.qiosk;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

public class SignUpActivity extends AppCompatActivity {

    private Button goButtonWorker;
    private Button goButtonEmployer;
    private TextView loginLink;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        setTitle("Sign Up");

        final Context context = this;

        goButtonWorker = (Button) findViewById(R.id.submit_worker);
        goButtonEmployer = (Button) findViewById(R.id.submit_employer);
        loginLink = (TextView) findViewById(R.id.link_login);

        goButtonWorker.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent detailIntent = new Intent(context, UserActivity.class);
                detailIntent.putExtra("userID", 6);
                detailIntent.putExtra("userType", "worker");
                startActivity(detailIntent);
            }
        });



        goButtonEmployer.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent detailIntent = new Intent(context, UserActivity.class);
                detailIntent.putExtra("userID", 6);
                detailIntent.putExtra("userType", "employer");
                startActivity(detailIntent);
            }
        });

        loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginIntent = new Intent(context, SignInActivity.class);
                startActivity(loginIntent);
            }
        });

    }
}
