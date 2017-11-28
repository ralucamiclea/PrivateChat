package com.dirtybits.privatechat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Register extends AppCompatActivity {

    EditText username, password;
    Button registerButton;
    TextView login;
    String user, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);
        registerButton = (Button)findViewById(R.id.registerButton);
        login = (TextView)findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Register.this, Login.class));
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user = username.getText().toString();
                pass = password.getText().toString();

                if(user.equals("")){
                    username.setError("Please enter a username.");
                }
                else if(pass.equals("")){
                    password.setError("Please enter a password.");
                }
                else if(!user.matches("[A-Za-z0-9]+")){
                    username.setError("Only letters or numbers allowed.");
                }
                else if(user.length()<6){
                    username.setError("Username must be at least 6 characters long");
                }
                else if(pass.length()<6){
                    password.setError("at least 6 characters long");
                }
                else {
                    //TODO: register user in database
                }
            }
        });
    }
}
