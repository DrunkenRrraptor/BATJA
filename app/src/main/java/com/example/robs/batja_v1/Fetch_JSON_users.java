package com.example.robs.batja_v1;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Robs on 28.05.18.
 */

public class Fetch_JSON_users extends AsyncTask <Void, Void, Void> {

    String data = "";
    List<User_Class> users_list = new ArrayList<>(  );

    @Override
    protected Void doInBackground(Void... voids) {

        URL url_users = null;
        try {

            url_users = new URL( "https://ieslamp.technikum-wien.at/2018-bvu-sys-teamb/batja/query_users.php" );
            HttpURLConnection httpURLConnection = (HttpURLConnection) url_users.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader( new InputStreamReader( inputStream ) );
            String line = "";
            while (line != null){

                line = bufferedReader.readLine();
                data = data + line;

            }

            JSONArray ja = new JSONArray( data );

            for (int i = 0; i < ja.length(); i++){

                JSONObject jo = (JSONObject) ja.get( i );

                User_Class element = new User_Class(  );
                element.setUsers_id_global( jo.getInt( "users_id_global" ) );
                element.setUsers_name( jo.getString( "users_name" ) );
                element.setUsers_password( jo.getString( "users_password" ) );

                users_list.add( element );

            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute( aVoid );

        Acitivity_for_Testing.textViewJSONOutput.setText( this.data );
        /*for (int i = 0; i < users_list.size(); i++) {

            Acitivity_for_Testing.textViewJSONOutput.setText(
                    Integer.toString( users_list.get( i ).getUsers_id_global() ) + " " +
                    users_list.get( i ).getUsers_name() + " " +
                    users_list.get( i ).getUsers_password() + "\n");

        }*/

        //returnJSONusers();

    }


    public List<User_Class> returnJSONusers(){


        return users_list;


    }

}
