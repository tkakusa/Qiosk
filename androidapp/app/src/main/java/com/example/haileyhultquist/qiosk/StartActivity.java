package com.example.haileyhultquist.qiosk;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StartActivity extends AppCompatActivity {

    private Button signInButton;
    private Button signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        final Context context = this;

        signInButton = (Button) findViewById(R.id.signInButton);
        signUpButton = (Button) findViewById(R.id.signUpButton);

        signInButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent detailIntent = new Intent(context, SignInActivity.class);

                startActivity(detailIntent);
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent detailIntent = new Intent(context, SignUpActivity.class);

                startActivity(detailIntent);
            }
        });
    }
}
