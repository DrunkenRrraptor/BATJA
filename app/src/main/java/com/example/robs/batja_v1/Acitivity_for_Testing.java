package com.example.robs.batja_v1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Acitivity_for_Testing extends AppCompatActivity {

    private Button buttonParseJSON;
    public static TextView textViewJSONOutput;
    private RequestQueue requestQuestJSONIncoming;

    DatabaseManagement dbm;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_acitivity_for__testing );

        textViewJSONOutput = findViewById( R.id.test_json_text );
        buttonParseJSON = findViewById( R.id.test_json_button );

        dbm = new DatabaseManagement( this );


        requestQuestJSONIncoming = Volley.newRequestQueue( this );
        buttonParseJSON.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Fetch_JSON_users json_encoded_users = new Fetch_JSON_users();
                //json_encoded_users.execute(  );

                /*List<User_Class> users_list_copy = new ArrayList<>(  );
                users_list_copy = json_encoded_users.returnJSONusers();

                for (int i = 0; i < json_encoded_users.users_list.size(); i++){

                    textViewJSONOutput.setText( users_list_copy.get( i ).getUsers_name());

                }*/


                retrieveJSONonlineUser();







                /*Fetch_JSON fetch_json = new Fetch_JSON();

                List<User_Class> users_list;

                users_list = fetch_json.jsonParse_users();

                for (int i = 0; i < users_list.size(); i++) {

                    //textViewJSONOutput.setText( users_list.get( i ).getUsers_name );
                    textViewJSONOutput.append( users_list.get( i ).getUsers_name() );

                }*/


                //String json_data = "";

                //json_data = textViewJSONOutput.getText();


            }

        } );

    }

    private void retrieveJSONonlineUser(){

        String urlJSONUsers = Constants.URL_USERS;

        JsonObjectRequest requestUsers = new JsonObjectRequest(Request.Method.GET, urlJSONUsers, null,
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


                            List<User_Class> user_listHelp;
                            user_listHelp = dbm.fetch_users();

                            for (int h = 0; h < user_listHelp.size(); h++){

                                //textViewJSONOutput.setText( user_listHelp.get( h ).getUsers_id_global() + " " + user_listHelp.get( h ).getUsers_name() + " " + user_listHelp.get( h ).getUsers_password());
                                textViewJSONOutput.append( user_listHelp.get( h ).getUsers_id_global() + " " + user_listHelp.get( h ).getUsers_name() + " " + user_listHelp.get( h ).getUsers_password() + "\n");


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




        requestQuestJSONIncoming.add(requestUsers);





    }



}


/*try {

                            //DatabaseManagement dbm = DatabaseManagement.getInstance( getApplicationContext() );
                            //DatabaseManagement dbm = new DatabaseManagement( this );

                            JSONArray jsonArray = response.getJSONArray( "users" );

                            for (int i = 0; i < jsonArray.length(); i++){

                                JSONObject users = jsonArray.getJSONObject( i );
                                String users_id_global = users.getString( "users_id_global" );
                                String users_name = users.getString( "users_name" );
                                String users_password = users.getString( "users_password" );

                                textViewJSONOutput.append(String.valueOf(users_id_global) + ", " + users_name + ", " + users_password + "\n\n");

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }*/
