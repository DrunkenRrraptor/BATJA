package com.example.robs.batja_v1;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener {


    //AlertDialog.Builder missingTextAlert = new AlertDialog.Builder(this);

    DatabaseManagement dbm;
    int m;
    private RequestQueue requestQuestJSONIncoming;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main2 );

        //dbm = new DatabaseManagement( this);
        dbm = DatabaseManagement.getInstance( this );

        requestQuestJSONIncoming = Volley.newRequestQueue( this );

        Button buttonJSON = (Button) findViewById( R.id.buttonJSONtest );
        buttonJSON.setOnClickListener( this );

        Button button1Log = (Button) findViewById( R.id.buttonLog );
        Button button2New = (Button) findViewById( R.id.buttonNew );
        //Button button4Show = (Button) findViewById( R.id.buttonShow );
        button1Log.setOnClickListener( this );
        button2New.setOnClickListener( this );
        //button4Show.setOnClickListener( this );

        dbm.onClearBoth();

        retrieveJSONonlineUser();

        //dbm.deleteFromTable( Constants.TABLE_USERS );

    }



    @Override
    public void onClick(View v) {

        EditText textUserName = (EditText) findViewById( R.id.textName );
        EditText textUserPassword = (EditText) findViewById( R.id.textPwd );

        String userName = textUserName.getText().toString();
        String userPassword = textUserPassword.getText().toString();

        if (userName.matches("" ) || userPassword.matches( "" )) {
            if (userName.matches( "" )) {
                Toast.makeText( this, "You did not enter a username", Toast.LENGTH_SHORT ).show();
                //return;
            }
            if (userPassword.matches( "" )) {
                Toast.makeText( this, "You did not enter a password", Toast.LENGTH_SHORT ).show();
                //return;
            }
            //return;
        } else {
            switch (v.getId()){

                case R.id.buttonLog:
                    button1LogOnClickHandler(userName, userPassword);
                    break;
                case R.id.buttonNew:
                    button2NewOnClickHandler(userName, userPassword);
                    break;
                /*case R.id.buttonShow:
                    button4ShowOnClickHandler();
                    break;*/

                case R.id.buttonJSONtest:
                    buttonJSONTestHandler();
                    break;

                default: break;

            }
        }

    }

    public void buttonJSONTestHandler(){

        Intent intent = new Intent(this, Acitivity_for_Testing.class);
        //EditText editText = (EditText) findViewById(R.id.editText);
        //String message = editText.getText().toString();
        //intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);

    }

    /*public void button4ShowOnClickHandler(){

        Intent intent = new Intent(this, Main3Activity.class);
        //EditText editText = (EditText) findViewById(R.id.editText);
        //String message = editText.getText().toString();
        //intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);

    }*/


    public void button1LogOnClickHandler (String userName, String userPassword) {                           // einloggen


        //Toast.makeText( this, "Button 'Log In' works", Toast.LENGTH_SHORT ).show();

        Toast toast1 = Toast.makeText(this, "Name: " + userName + " , PWD: " + userPassword, Toast.LENGTH_SHORT);
        toast1.show();

        /*if(userName.isEmpty() || userPassword.isEmpty())
        {

            Toast toast3 = Toast.makeText(this, "Y'moron, either ya name or password are empty. Like mee ol' rum.", Toast.LENGTH_SHORT);
            toast3.show();

            //showAlert( "Y'moron, either ya name or password are empty. Like mee ol' rum." );
            // dbm.checkUser( userName, userPassword );
        }*/

        m = dbm.checkUser( userName, userPassword );


        switch (m){
            case 0: Toast toast4 = Toast.makeText(this, "Your name and password are both wrong.", Toast.LENGTH_SHORT);
                    toast4.show();
                    break;
            case 1: Toast toast5 = Toast.makeText(this, "Welcome.", Toast.LENGTH_SHORT);
                    Intent intent = new Intent(this, MainMenu.class);
                    toast5.show();
                    startActivity(intent);
                    break;
            case 2: Toast toast2 = Toast.makeText(this, "Your password is wrong.", Toast.LENGTH_SHORT);
                    toast2.show();
                    break;

            default: break;
        }

        //Toast toast2 = Toast.makeText(this, "Message: (blank = log in)" + m, Toast.LENGTH_SHORT);
        //toast2.show();





        /*Intent intent = new Intent(this, MapsActivity.class);
        //EditText editText = (EditText) findViewById(R.id.editText);
        //String message = editText.getText().toString();
        //intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);*/


    }

    public void button2NewOnClickHandler (String userName, String userPassword) {                           // registrieren

        //Toast.makeText( this, "Button 'New Here' works", Toast.LENGTH_SHORT ).show();

        User_Class user = new User_Class( userName, userPassword );

        //dbm.addUser( userName, userPassword );

        postUser( user );

        dbm.onClearBoth();

        retrieveJSONonlineUser();


        /*if(userName.isEmpty() || userPassword.isEmpty())
        {
            showAlert( "Ya either ya name or password are empty. Like mee ol' rum." );
        } else {
            dbm.addUser( userName, userPassword );
        }*/


        /*Intent intent = new Intent(this, MainMenu.class);
        //EditText editText = (EditText) findViewById(R.id.editText);
        //String message = editText.getText().toString();
        //intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);*/

        //Toast toast2 = Toast.makeText(this, "Welcome", Toast.LENGTH_SHORT);
        //toast2.show();

        Toast toast2 = Toast.makeText(this, "Registration successful. Please sign in", Toast.LENGTH_SHORT);
        toast2.show();


        /*Toast toast1 = Toast.makeText(this, "Name: " + userName + " , PWD: " + userPassword, Toast.LENGTH_SHORT);
        toast1.show();*/

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


    private void retrieveJSONonlineUser(){

        String urlJSONUsers = "https://ieslamp.technikum-wien.at/2018-bvu-sys-teamb/batja/query_users.php";

        JsonObjectRequest requestUsers = new JsonObjectRequest( Request.Method.GET, urlJSONUsers, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("users");

                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject users = jsonArray.getJSONObject(i);

                                int users_id_global = users.getInt("users_id_global");
                                String users_name = users.getString("users_name");
                                String users_password = users.getString("users_password");



                                //textViewJSONOutput.append(String.valueOf(users_id_global) + ", " + users_name + ", " + users_password + "\n\n");

                                dbm.addUserFromJSON( users_id_global, users_name, users_password );



                            }

                            List<User_Class> user_listHelp = new ArrayList<>(  );
                            user_listHelp = dbm.fetch_users();

                            for (int h = 0; h < user_listHelp.size(); h++){

                                Log.e("USR-L", "List of Users: (name) " + user_listHelp.get( h ).getUsers_name());
                                //textViewJSONOutput.setText( user_listHelp.get( h ).getUsers_name());

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        requestQuestJSONIncoming.add(requestUsers);

    }



    public void postUser(final User_Class user_ins) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(Constants.URL_POST_USER);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                    conn.setRequestProperty("Accept","application/json");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);

                    JSONObject jsonParam = new JSONObject();

                    jsonParam.put( "users_name", user_ins.getUsers_name() );
                    jsonParam.put( "users_password", user_ins.getUsers_password() );


                    /*
                    jsonParam.put("timestamp", 1488873360);
                    jsonParam.put("uname", gps_ins.);
                    jsonParam.put("message", message.getMessage());
                    jsonParam.put("latitude", 0D);
                    jsonParam.put("longitude", 0D);*/

                    Log.i("JSON", jsonParam.toString());
                    DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                    //os.writeBytes(URLEncoder.encode(jsonParam.toString(), "UTF-8"));
                    os.writeBytes(jsonParam.toString());

                    os.flush();
                    os.close();

                    Log.i("STATUS", String.valueOf(conn.getResponseCode()));
                    Log.i("MSG" , conn.getResponseMessage());

                    conn.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
    }


}
