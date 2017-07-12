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

public class SignInActivity extends AppCompatActivity {

    private Button goButton;
    private EditText usernameText;
    private EditText passwordText;

    private ServerRestClientUsage serverRestClientUsage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        setTitle("Sign In");

        final Context context = this;

        goButton = (Button) findViewById(R.id.submit);
        usernameText = (EditText) findViewById(R.id.username);
        passwordText = (EditText) findViewById(R.id.password);

        serverRestClientUsage = new ServerRestClientUsage();

        goButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                final String username = usernameText.getText().toString();
                final String password = passwordText.getText().toString();

                serverRestClientUsage.loginEmployer(context, username, password, new ServerRestClientUsage.Callback<String>() {
                    @Override
                    public void onResponse(String s) throws JSONException {

                        if (!s.equals("")) {

                            String key = s;

                            Intent detailIntent = new Intent(context, UserActivity.class);;

                            detailIntent.putExtra("userType", "employer");
                            detailIntent.putExtra("key", key);

                            startActivity(detailIntent);
                        } else {
                            // Try as an employee
                            serverRestClientUsage.loginWorker(context, username, password, new ServerRestClientUsage.Callback<String>() {
                                @Override
                                public void onResponse(String s) throws JSONException {

                                    if (!s.equals("")) {

                                        String key = s;

                                        Intent detailIntent = new Intent(context, UserActivity.class);;

                                        detailIntent.putExtra("userType", "worker");
                                        detailIntent.putExtra("key", key);

                                        startActivity(detailIntent);
                                    } else {
                                        Toast.makeText(SignInActivity.this, "Login Failed", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        }
                    }
                });



            }
        });
    }

}
