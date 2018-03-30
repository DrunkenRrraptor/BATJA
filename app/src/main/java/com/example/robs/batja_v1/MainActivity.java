package com.example.robs.batja_v1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button button1;
    private EditText textUserName;
    private EditText textUserPassword;
    String userName;
    String userPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //blub
        setContentView(R.layout.activity_main);

        button1 = (Button) findViewById(R.id.buttonLogin);
        textUserName = (EditText) findViewById(R.id.userName);
        textUserPassword = (EditText) findViewById(R.id.userPWD);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                handleButton1OnClick();

            }
        });

    }

    public void handleButton1OnClick(){

        userName = textUserName.getText().toString();
        userPassword = textUserPassword.getText().toString();

        Toast.makeText(MainActivity.this, "Name: " + userName + " , PWD: " + userPassword, Toast.LENGTH_SHORT);



    }

}
