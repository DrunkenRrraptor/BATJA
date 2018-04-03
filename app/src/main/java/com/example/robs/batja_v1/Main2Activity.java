package com.example.robs.batja_v1;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener {


    //AlertDialog.Builder missingTextAlert = new AlertDialog.Builder(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main2 );



        Button button1Log = (Button) findViewById( R.id.buttonLog );
        Button button2New = (Button) findViewById( R.id.buttonNew );
        button1Log.setOnClickListener( this );
        button2New.setOnClickListener( this );

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.buttonLog:
                button1LogOnClickHandler();
                break;
            case R.id.buttonNew:
                button2NewOnClickHandler();
                break;

            default: break;


        }




    }

    public void button1LogOnClickHandler () {

        //Toast.makeText( this, "Button 'Log In' works", Toast.LENGTH_SHORT ).show();

        EditText textUserName = (EditText) findViewById( R.id.textName );
        EditText textUserPassword = (EditText) findViewById( R.id.textPwd );

        String userName = textUserName.getText().toString();
        String userPassword = textUserPassword.getText().toString();

        Toast toast1 = Toast.makeText(this, "Name: " + userName + " , PWD: " + userPassword, Toast.LENGTH_SHORT);
        toast1.show();

        if(userName.isEmpty() || userPassword.isEmpty())
        {
            showAlert(new String("Fucking moron, either ya name or password are empty. Like mee ol' rum."));
        }

    }

    public void button2NewOnClickHandler () {

        Toast.makeText( this, "Button 'New Here' works", Toast.LENGTH_SHORT ).show();

        EditText textUserName = (EditText) findViewById( R.id.textName );
        EditText textUserPassword = (EditText) findViewById( R.id.textPwd );

        String userName = textUserName.getText().toString();
        String userPassword = textUserPassword.getText().toString();

    }

    public void showAlert(String message){

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(message);
                alertDialogBuilder.setPositiveButton("got it",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                Toast.makeText(Main2Activity.this,"You clicked yes button",Toast.LENGTH_LONG).show();
                            }
                        });

        /*AlertDialog.Builder alertDialog1 = new AlertDialog.Builder(this);

        alertDialog1.setMessage( message )
                .setPositiveButton(  )*/

        /*alertDialog1.setPositiveButton( "Got it",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        Toast.makeText( this, "you clicked something bloody stupid", Toast.LENGTH_LONG ).show();
                    }
                } );*/


    }

}
