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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main2 );

        dbm = new DatabaseManagement( this );


        Button button1Log = (Button) findViewById( R.id.buttonLog );
        Button button2New = (Button) findViewById( R.id.buttonNew );
        Button button3 = (Button) findViewById( R.id.button2 );
        button1Log.setOnClickListener( this );
        button2New.setOnClickListener( this );
        button3.setOnClickListener( this );


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
            case R.id.button2:
                button3ConClickHandler();
                break;

            default: break;


        }




    }


    public void button3ConClickHandler(){

       // dbm.deleteTable( "users" );


    }

    public void button1LogOnClickHandler (String userName, String userPassword) {                           // einloggen

        //Toast.makeText( this, "Button 'Log In' works", Toast.LENGTH_SHORT ).show();

        //Toast toast1 = Toast.makeText(this, "Name: " + userName + " , PWD: " + userPassword, Toast.LENGTH_SHORT);
        //toast1.show();
        String m;
        m = dbm.checkUser( userName, userPassword );

        //Toast toast2 = Toast.makeText(this, "Message: (blank = log in)" + m, Toast.LENGTH_SHORT);
        //toast2.show();



        if(userName.isEmpty() || userPassword.isEmpty())
        {
            showAlert( "Y'moron, either ya name or password are empty. Like mee ol' rum." );
           // dbm.checkUser( userName, userPassword );
        }

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
            showAlert( "Y'moron, either ya name or password are empty. Like mee ol' rum." );
        } else {
            dbm.addUser( userName, userPassword );
        }


        Intent intent = new Intent(this, MapsActivity.class);
        //EditText editText = (EditText) findViewById(R.id.editText);
        //String message = editText.getText().toString();
        //intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);


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
