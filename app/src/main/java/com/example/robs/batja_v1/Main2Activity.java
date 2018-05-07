package com.example.robs.batja_v1;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener {


    //AlertDialog.Builder missingTextAlert = new AlertDialog.Builder(this);

    DatabaseManagement dbm;
    int m;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main2 );

        dbm = new DatabaseManagement( this);


        Button button1Log = (Button) findViewById( R.id.buttonLog );
        Button button2New = (Button) findViewById( R.id.buttonNew );
        Button button4Show = (Button) findViewById( R.id.buttonShow );
        button1Log.setOnClickListener( this );
        button2New.setOnClickListener( this );
        button4Show.setOnClickListener( this );

        dbm.onClearBoth();

    }



    @Override
    public void onClick(View v) {

        EditText textUserName = (EditText) findViewById( R.id.textName );
        EditText textUserPassword = (EditText) findViewById( R.id.textPwd );

        String userName = textUserName.getText().toString();
        String userPassword = textUserPassword.getText().toString();

        switch (v.getId()){

            case R.id.buttonLog:
                button1LogOnClickHandler(userName, userPassword);
                break;
            case R.id.buttonNew:
                button2NewOnClickHandler(userName, userPassword);
                break;
            case R.id.buttonShow:
                button4ShowOnClickHandler();
                break;

            default: break;


        }




    }


    public void button4ShowOnClickHandler(){

        Intent intent = new Intent(this, Main3Activity.class);
        //EditText editText = (EditText) findViewById(R.id.editText);
        //String message = editText.getText().toString();
        //intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);



    }


    public void button1LogOnClickHandler (String userName, String userPassword) {                           // einloggen

        //Toast.makeText( this, "Button 'Log In' works", Toast.LENGTH_SHORT ).show();

        Toast toast1 = Toast.makeText(this, "Name: " + userName + " , PWD: " + userPassword, Toast.LENGTH_SHORT);
        toast1.show();

        if(userName.isEmpty() || userPassword.isEmpty())
        {

            Toast toast3 = Toast.makeText(this, "Y'moron, either ya name or password are empty. Like mee ol' rum.", Toast.LENGTH_SHORT);
            toast3.show();

            //showAlert( "Y'moron, either ya name or password are empty. Like mee ol' rum." );
            // dbm.checkUser( userName, userPassword );
        }

        m = dbm.checkUser( userName, userPassword );


        switch (m){
            case 0: Toast toast4 = Toast.makeText(this, "Ya name and password are wrong.", Toast.LENGTH_SHORT);
                    toast4.show();
                break;
            case 1: Toast toast2 = Toast.makeText(this, "Ya password is wrong.", Toast.LENGTH_SHORT);
                    toast2.show();
                break;
            case 2: Intent intent = new Intent(this, MapsActivity.class);
                    startActivity(intent);
                break;

            default: break;
        }

        //Toast toast2 = Toast.makeText(this, "Message: (blank = log in)" + m, Toast.LENGTH_SHORT);
        //toast2.show();





        Intent intent = new Intent(this, MapsActivity.class);
        //EditText editText = (EditText) findViewById(R.id.editText);
        //String message = editText.getText().toString();
        //intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);


    }

    public void button2NewOnClickHandler (String userName, String userPassword) {                           // registrieren

        //Toast.makeText( this, "Button 'New Here' works", Toast.LENGTH_SHORT ).show();

        dbm.addUser( userName, userPassword );


        if(userName.isEmpty() || userPassword.isEmpty())
        {
            showAlert( "Ya either ya name or password are empty. Like mee ol' rum." );
        } else {
            dbm.addUser( userName, userPassword );
        }


        Intent intent = new Intent(this, MapsActivity.class);
        //EditText editText = (EditText) findViewById(R.id.editText);
        //String message = editText.getText().toString();
        //intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);


        Toast toast1 = Toast.makeText(this, "Name: " + userName + " , PWD: " + userPassword, Toast.LENGTH_SHORT);
        toast1.show();

        //"Ya password happens to be wrong. Try again or create user.";

    }

    public void showAlert(String message){

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setCancelable( true );
        alertDialogBuilder.setTitle( "Alert" );
        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.setPositiveButton( "got it", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();

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
