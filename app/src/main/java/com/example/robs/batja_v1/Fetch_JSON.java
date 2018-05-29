package com.example.robs.batja_v1;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Robs on 28.05.18.
 */

public class Fetch_JSON implements Response.Listener<JSONObject>{

    private RequestQueue requestQuestJSONIncoming;
    String users_id_global_i;
    String users_name_i;
    String users_password_i;

    List<User_Class> users_List;



    /*public List<User_Class> fetch_users(){

        List<User_Class> users_List = new ArrayList<>(  );




        return users_List;

    }*/



    /*public List<GPS_Class> fetch_gps(){

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date loc_temp_date = null;

        //SQLiteDatabase db = getWritableDatabase();


        List<GPS_Class> gps_list = new ArrayList<>(  );

        String FETCH_GPS_DATA =
                "SELECT * FROM " + Constants.TABLE_GPS + ";";

        Log.e( "DB-GPS", "query: " +  FETCH_GPS_DATA);

        //Cursor c1 = db.rawQuery( FETCH_GPS_DATA, new String[]{} );
        //Cursor c1 = db.rawQuery( FETCH_GPS_DATA, new String[]{""} );

        //Cursor c1 = db.query( Constants.TABLE_GPS, null, null, null,  null, null, null);

        //int count_rows = c1.getCount();

        //Log.e( "DB-GPS", "count rows: " + count_rows );


        /*while(c1.moveToNext()){

            try {
                loc_temp_date = sdf.parse( c1.getString( 2 ) );

                Log.e( "DB-GPS", "TRY: statement fetching gps: " + FETCH_GPS_DATA );
                Log.e( "DB-GPS", "fetching gps data; date was: " + loc_temp_date + c1.getString( 2 ) );


            } catch (ParseException e) {
                e.printStackTrace();

                Log.e( "DB-GPS", "CATCH - ERROR: statement fetching gps: " + FETCH_GPS_DATA );
                Log.e( "DB-GPS", "ERROR in fetching gps data; date was: " + loc_temp_date + c1.getString( 2 ) );

            }

            gps_data.setLoc_id( c1.getInt( 1 ) );
            //gps_data.setLoc_date( loc_temp_date );
            gps_data.setLoc_lat( c1.getFloat( 3 ) );
            gps_data.setLoc_lng( c1.getFloat( 4 ) );
            gps_data.setLoc_speed( c1.getFloat( 5 ) );

            gps_list.add( gps_data );

            Log.e( "DB-GPS", "row 1 data: " + gps_data.getLoc_id() + gps_data.getLoc_lat() + gps_data.getLoc_lng() + gps_data.getLoc_speed());


        }*/

        //c1.moveToFirst();

        //for (int i = 0; i < count_rows; i++){

            //GPS_Class gps_data = new GPS_Class(  );

            /*try {
                loc_temp_date = sdf.parse( c1.getString( 2 ) );

                Log.e( "DB-GPS", "TRY: statement fetching gps: " + FETCH_GPS_DATA );
                Log.e( "DB-GPS", "fetching gps data; date was: " + loc_temp_date + c1.getString( 2 ) );


            } catch (ParseException e) {
                e.printStackTrace();

                Log.e( "DB-GPS", "CATCH - ERROR: statement fetching gps: " + FETCH_GPS_DATA );
                Log.e( "DB-GPS", "ERROR in fetching gps data; date was: " + loc_temp_date + c1.getString( 2 ) );

            }*/

            //Log.e( "DB-GPS", "rows: " + c1.getInt( 0 ) + c1.getFloat( 2 ) + c1.getFloat( 3 ) + c1.getFloat( 4 ) );

            //gps_data.setLoc_id( c1.getInt( 0 ) );
            //gps_data.setLoc_date( loc_temp_date );
            //gps_data.setLoc_lat( c1.getFloat( 2 ) );
            //gps_data.setLoc_lng( c1.getFloat( 3 ) );
            //gps_data.setLoc_speed( c1.getFloat( 4 ) );

            //gps_list.add( gps_data );

            //Log.e( "DB-GPS", "row 1 data: " + gps_data.getLoc_id() + gps_data.getLoc_lat() + gps_data.getLoc_lng() + gps_data.getLoc_speed());

            //c1.moveToNext();

        //}



        //return gps_list;

    //}



    public int checkUser(String userName, String password) {

        int i = 0;

        return i;

    }


    public List<User_Class> jsonParse_users(){

        String urlJSONUsers = "https://ieslamp.technikum-wien.at/2018-bvu-sys-teamb/batja/query_users.php";

        final List<User_Class> users_List = new ArrayList<>(  );

        Log.e( "JSON: ", "fetch users // method" );

        JsonObjectRequest requestUsers = new JsonObjectRequest( Request.Method.GET, urlJSONUsers, null , this, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                error.printStackTrace();

                Log.e( "JSON: ", "fetch users // error" );

            }



        } );

        requestQuestJSONIncoming.add(requestUsers);



        return users_List;


    }


        @Override
        public void onResponse(JSONObject response) {

            try {

                Log.e( "JSON: ", "fetch users // try block" );

                JSONArray jsonArray = response.getJSONArray( "users" );

                for (int i = 0; i < jsonArray.length(); i++){

                    JSONObject users = jsonArray.getJSONObject( i );
                    users_id_global_i = users.getString( "users_id_global" );
                    users_name_i = users.getString( "users_name" );
                    users_password_i = users.getString( "users_password" );


                    //textViewJSONOutput.append(String.valueOf(users_id_global) + ", " + users_name + ", " + users_password + "\n\n");

                    User_Class element = new User_Class( Integer.parseInt( users_id_global_i ), users_name_i, users_password_i );
                    users_List.add(element);

                    Log.e( "JSON: ", element.getUsers_id_global() + element.getUsers_name() + element.getUsers_password() );

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

