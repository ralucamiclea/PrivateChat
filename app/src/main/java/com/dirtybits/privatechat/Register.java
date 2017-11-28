package com.dirtybits.privatechat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Register extends AppCompatActivity {

    EditText username, password1, password2;
    Button registerButton;
    TextView login;
    String user, pass1, pass2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = (EditText)findViewById(R.id.username);
        password1 = (EditText)findViewById(R.id.password1);
        password2 = (EditText)findViewById(R.id.password2);
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
                pass1 = password1.getText().toString();
                pass2 = password2.getText().toString();

                if(user.equals("")){
                    username.setError("Please enter a username.");
                }
                else if(pass1.equals("")){
                    password1.setError("Please enter a password.");
                }
                else if(!pass1.equals(pass2)){
                    password2.setError("Passwords must match.");
                }
                else if(!user.matches("[A-Za-z0-9]+")){
                    username.setError("Only letters or numbers allowed.");
                }
                else if(user.length()<6){
                    username.setError("Username must be at least 6 characters long");
                }
                else if(pass1.length()<6){
                    password1.setError("at least 6 characters long");
                }
                else {
                    //TODO: register user in database
                }
            }
        });
    }
}
