package com.example.haileyhultquist.qiosk;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import activity.NavigationActivity;

public class SignInActivity extends AppCompatActivity {

    private Button goButton;
    private EditText usernameText;
    private EditText passwordText;
    private TextView signUpLink;

    private ServerRestClientUsage serverRestClientUsage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);


        setContentView(R.layout.activity_sign_in);

        setTitle("Sign In");

        final Context context = this;

        goButton = (Button) findViewById(R.id.submit);
        usernameText = (EditText) findViewById(R.id.username);
        passwordText = (EditText) findViewById(R.id.password);
        signUpLink = (TextView) findViewById(R.id.link_signup);

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

                            //startActivity(detailIntent);

                            Intent intent = new Intent(context, NavigationActivity.class);
                            intent.putExtra("userType", "employer");
                            intent.putExtra("key", key);
                            startActivity(intent);
                        } else {
                            // Try as an employee
                            serverRestClientUsage.loginWorker(context, username, password, new ServerRestClientUsage.Callback<String>() {
                                @Override
                                public void onResponse(String s) throws JSONException {

                                    if (!s.equals("")) {

                                        String key = s;

                                        Intent detailIntent = new Intent(context, UserActivity.class);

                                        detailIntent.putExtra("userType", "worker");
                                        detailIntent.putExtra("key", key);

                                        //startActivity(detailIntent);

                                        Intent intent = new Intent(context, NavigationActivity.class);
                                        intent.putExtra("userType", "worker");
                                        intent.putExtra("key", key);
                                        startActivity(intent);
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
        signUpLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signupIntent = new Intent(context, SignUpActivity.class);
                startActivity(signupIntent);
            }
        });
    }

}
