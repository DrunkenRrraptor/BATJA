package com.example.robs.batja_v1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.List;

public class MainMenu extends AppCompatActivity implements View.OnClickListener {

    int arg;
    DatabaseManagement dbm;
    private RequestQueue requestQuestJSONIncoming;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main_menu );

        dbm = new DatabaseManagement( this );

        requestQuestJSONIncoming = Volley.newRequestQueue( this );



        Button button_menu_track = (Button) findViewById( R.id.button_menu_track );
        Button button_menu_map_now = (Button) findViewById( R.id.button_menu_mapNow );
        Button button_menu_map_today = (Button) findViewById( R.id.button_menu_mapToday );
        Button button_menu_map_hist = (Button) findViewById( R.id.button_menu_mapHist );
        button_menu_track.setOnClickListener( this );
        button_menu_map_now.setOnClickListener( this );
        button_menu_map_today.setOnClickListener( this );
        button_menu_map_hist.setOnClickListener( this );

        //Button button_menu_track = (Button) findViewById( R.id.button_menu_track );
        //button_menu_track.setOnClickListener( this );

    }


    @Override
    public void onClick(View v) {


        switch (v.getId()){

            case R.id.button_menu_track:
                buttonMenuTrackOnClickHandler();
                break;
            case R.id.button_menu_mapNow:
                buttonMenuMapNowOnClickHandler();
                break;
            case R.id.button_menu_mapToday:
                buttonMenuMapTodayOnClickHandler();
                break;
            case R.id.button_menu_mapHist:
                buttonMenuMapHistOnClickHandler();
                break;

                default: break;


        }

    }



    private void buttonMenuMapNowOnClickHandler() {

        arg = 0;

        retrieveJSONonlineLoc(arg);

        Intent intent = new Intent(this, MapsActivity.class);
        //EditText editText = (EditText) findViewById(R.id.editText);
        //String message = editText.getText().toString();
        //intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);

    }

    private void buttonMenuMapTodayOnClickHandler() {


        arg = 1;

        retrieveJSONonlineLoc(arg);

        Intent intent = new Intent(this, MapsActivity.class);
        //EditText editText = (EditText) findViewById(R.id.editText);
        //String message = editText.getText().toString();
        //intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);

    }

    private void buttonMenuMapHistOnClickHandler() {

        arg = 2;

        retrieveJSONonlineLoc(arg);

        Intent intent = new Intent(this, MapsActivity.class);
        //EditText editText = (EditText) findViewById(R.id.editText);
        //String message = editText.getText().toString();
        //intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);

    }

    private void buttonMenuTrackOnClickHandler() {

        Intent intent = new Intent(this, Main3Activity.class);
        //EditText editText = (EditText) findViewById(R.id.editText);
        //String message = editText.getText().toString();
        //intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);

    }





    private void retrieveJSONonlineLoc(int arg){

        String urlJSONloc = "";

        urlJSONloc = "https://ieslamp.technikum-wien.at/2018-bvu-sys-teamb/batja/query_loc_moc_full.php";

        /*
        if(arg == 0)
            urlJSONloc = Constants.URL_15MIN_NOW;
        else if(arg == 1)
            //urlJSONloc = Constants.URL_TODAY;
            urlJSONloc = Constants.URL_15MIN_NOW;
        else if(arg == 2)
            //urlJSONloc = Constants.URL_HIST;
            urlJSONloc = Constants.URL_15MIN_NOW;
        */



        JsonObjectRequest requestloc = new JsonObjectRequest( Request.Method.GET, urlJSONloc, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("loc");

                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject loc = jsonArray.getJSONObject(i);

                                int loc_id_global = loc.getInt("loc_id_global");
                                int users_id_global = loc.getInt("users_id_global");
                                //String sys_date = loc.getString("sys_date");
                                double lat = loc.getDouble( "lat" );
                                double lng = loc.getDouble( "lng" );
                                double speed = loc.getDouble( "speed" );
                                double accel = loc.getDouble( "accel" );


                                //textViewJSONOutput.append(String.valueOf(users_id_global) + ", " + users_name + ", " + users_password + "\n\n");

                                //dbm.addUserFromJSON( users_id_global, users_name, users_password );

                                GPS_Class gps_class = new GPS_Class( loc_id_global, users_id_global,
                                        lat, lng, speed, accel);

                                dbm.addLocationFromJSON( gps_class );



                            }


                            List<GPS_Class> gps_listHelp;
                            gps_listHelp = dbm.fetch_gps();

                            for (int h = 0; h < gps_listHelp.size(); h++){

                                //textViewJSONOutput.setText( user_listHelp.get( h ).getUsers_id_global() + " " + user_listHelp.get( h ).getUsers_name() + " " + user_listHelp.get( h ).getUsers_password());
                                //textViewJSONOutput.append( user_listHelp.get( h ).getUsers_id_global() + " " + user_listHelp.get( h ).getUsers_name() + " " + user_listHelp.get( h ).getUsers_password() + "\n");


                            }


                            /*List<User_Class> user_listHelp;
                            user_listHelp = dbm.fetch_users();

                            for (int h = 0; h < user_listHelp.size(); h++){

                                textViewJSONOutput.setText( user_listHelp.get( h ).getUsers_name());

                            }*/


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




        requestQuestJSONIncoming.add(requestloc);





    }



}
