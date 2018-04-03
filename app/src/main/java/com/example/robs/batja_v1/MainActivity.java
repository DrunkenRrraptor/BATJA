package com.example.robs.batja_v1;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button button1;
    private Button button2;
    private EditText textUserName;
    private EditText textUserPassword;
    String userName;
    String userPassword;

    AlertDialog.Builder missingTextAlert = new AlertDialog.Builder(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //blub
        setContentView(R.layout.activity_main);

        button1 = (Button) findViewById(R.id.buttonLogin);
        button2 = (Button) findViewById(R.id.buttonNewHere);
        textUserName = (EditText) findViewById(R.id.userName);
        textUserPassword = (EditText) findViewById(R.id.userPWD);

        button1.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                button1OnClickHandler();

            }
        } );

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                handleButton2OnClick();

            }
        });

    }


    public void handleButton2OnClick(){

        userName = textUserName.getText().toString();
        userPassword = textUserPassword.getText().toString();

    }

    public void showAlert(String message){

        missingTextAlert.setMessage(message)
                .setPositiveButton("Got it", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create()
                .show();


    }

    public void button1OnClickHandler () {

        userName = textUserName.getText().toString();
        userPassword = textUserPassword.getText().toString();

        Toast toast1 = Toast.makeText(MainActivity.this, "Name: " + userName + " , PWD: " + userPassword, Toast.LENGTH_SHORT);
        toast1.show();

        if(userName.isEmpty() || userPassword.isEmpty())
        {
            showAlert(new String("You forgot either the name or the password"));
        }

    }

}
