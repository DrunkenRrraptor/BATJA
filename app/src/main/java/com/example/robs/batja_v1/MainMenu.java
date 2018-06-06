package com.example.robs.batja_v1;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.ksoap2.transport.HttpsTransportSE;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class MainMenu extends AppCompatActivity implements View.OnClickListener {

    int arg;
    DatabaseManagement dbm = DatabaseManagement.getInstance( this );
    private RequestQueue requestQuestJSONIncoming;
    String resp = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main_menu );


        requestQuestJSONIncoming = Volley.newRequestQueue( this );


        Button button_menu_track = (Button) findViewById( R.id.button_menu_track );
        Button button_menu_map_now = (Button) findViewById( R.id.button_menu_mapNow );
        Button button_menu_map_today = (Button) findViewById( R.id.button_menu_mapToday );
        Button button_menu_map_hist = (Button) findViewById( R.id.button_menu_mapHist );
        Button button_menu_map_big_bang = (Button) findViewById( R.id.button_menu_map_big_bang );
        Button button_menu_stats = (Button) findViewById( R.id.button_menu_stats );
        button_menu_track.setOnClickListener( this );
        button_menu_map_now.setOnClickListener( this );
        button_menu_map_today.setOnClickListener( this );
        button_menu_map_hist.setOnClickListener( this );
        button_menu_map_big_bang.setOnClickListener( this );
        button_menu_stats.setOnClickListener( this );

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
            case R.id.button_menu_map_big_bang:
                buttonMenuMapBigBang();
                break;
            case R.id.button_menu_stats:
                buttonMenuStatsOnClickHandler();
                break;

            default: break;


        }

    }


    private void buttonMenuMapNowOnClickHandler() {

        arg = 0;

        retrieveJSONonlineLoc( arg );

        Intent intent = new Intent(this, MapsActivity.class);
        //EditText editText = (EditText) findViewById(R.id.editText);
        //String message = editText.getText().toString();
        //intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);

    }

    private void buttonMenuMapTodayOnClickHandler() {


        arg = 1;

        retrieveJSONonlineLoc( arg );

        Intent intent = new Intent(this, MapsActivity.class);
        //EditText editText = (EditText) findViewById(R.id.editText);
        //String message = editText.getText().toString();
        //intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);

    }

    private void buttonMenuMapHistOnClickHandler() {

        arg = 2;

        retrieveJSONonlineLoc( arg );

        Intent intent = new Intent(this, MapsActivity.class);
        //EditText editText = (EditText) findViewById(R.id.editText);
        //String message = editText.getText().toString();
        //intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);

    }

    private void buttonMenuMapBigBang() {

        arg = 3;

        retrieveJSONonlineLoc( arg );

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

        //dbm.onClearBoth();

        String urlJSONloc = "";

        //urlJSONloc = "https://ieslamp.technikum-wien.at/2018-bvu-sys-teamb/batja/query_loc_moc_full.php";


        if(arg == 0)
            urlJSONloc = Constants.URL_1H_NOW;
        else if(arg == 1)
            //urlJSONloc = Constants.URL_TODAY;
            urlJSONloc = Constants.URL_TODAY;
        else if(arg == 2)
            //urlJSONloc = Constants.URL_HIST;
            urlJSONloc = Constants.URL_HIST;
        else if (arg == 3)
            urlJSONloc = Constants.URL_FULL;



        JsonObjectRequest requestloc = new JsonObjectRequest( Request.Method.GET, urlJSONloc, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("loc");

                            Log.e( "JSON", "json array length " + jsonArray.length());

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


                            /*List<GPS_Class> gps_listHelp;
                            gps_listHelp = dbm.fetch_gps();

                            for (int h = 0; h < gps_listHelp.size(); h++){

                                //textViewJSONOutput.setText( user_listHelp.get( h ).getUsers_id_global() + " " + user_listHelp.get( h ).getUsers_name() + " " + user_listHelp.get( h ).getUsers_password());
                                //textViewJSONOutput.append( user_listHelp.get( h ).getUsers_id_global() + " " + user_listHelp.get( h ).getUsers_name() + " " + user_listHelp.get( h ).getUsers_password() + "\n");


                            }*/


                            /*List<User_Class> user_listHelp;
                            user_listHelp = dbm.fetch_users();

                            for (int h = 0; h < user_listHelp.size(); h++){

                                textViewJSONOutput.setText( user_listHelp.get( h ).getUsers_name());

                            }*/


                        } catch (JSONException e) {

                            e.printStackTrace();
                            Log.e( "VLY-LOC", "error in volley loc - json exception" );

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                error.printStackTrace();
                Log.e( "VLY-LOC", "error in volley loc - error response" );

            }
        });


        requestQuestJSONIncoming.add(requestloc);

    }



    private void buttonMenuStatsOnClickHandler() {

        SOAPlocStatsAsynchTask soapLocATask = new SOAPlocStatsAsynchTask();
        soapLocATask.execute(  );
        //parseXML();

    }


    class SOAPlocStatsAsynchTask extends AsyncTask{

        @Override
        protected Object doInBackground(Object[] objects) {
            retrieveSOAPlocStats();
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute( o );
        }
    }





    // SOAP client: retrieving loc-data from the user for stats

    public void retrieveSOAPlocStats(){

        String wsdl_url = "https://ieslamp.technikum-wien.at/2018-bvu-sys-teamb/batja/soap_service_stats.php?wsdl";
        String soap_action = "https://ieslamp.technikum-wien.at/2018-bvu-sys-teamb/batja/soap_service_stats.php/getlocinfo";
        String name_space = "https://ieslamp.technikum-wien.at/2018-bvu-sys-teamb/batja/soap_service_stats.php";
        String method_name = "getlocinfo";

        SoapObject soapObject = new SoapObject( name_space, method_name );
        soapObject.addProperty( "users_id_global", dbm.getUser_logged().getUsers_id_global() );

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope( SoapEnvelope.VER11 );
        envelope.dotNet = true;
        envelope.setOutputSoapObject( soapObject );

        //HttpsTransportSE httpsTransportSE = new HttpsTransportSE( wsdl_url );
        HttpTransportSE httpTransportSE = new HttpTransportSE( wsdl_url );
        try {
            httpTransportSE.call( soap_action, envelope );

            SoapObject obj = (SoapObject) envelope.bodyIn;
            resp = obj.getProperty( 0 ).toString();
            System.out.print( resp );
            Log.e( "SOAP", "" + resp );

        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }

    }


    /*public void parseXML(){


        try {
            SAXParserFactory saxFactory = SAXParserFactory.newInstance();
            SAXParser saxParser = saxFactory.newSAXParser(  );
            saxParser.parse( IOUtils.toInputStream( resp ), new DefaultHandler(){
                ArrayList<String> list = new ArrayList<>(  );
                String msg = "";
                @Override
                public void characters(char[] ch, int start, int length) throws SAXException {
                    super.characters( ch, start, length );
                    msg = new String( ch, start, length );
                }

                @Override
                public void endElement(String uri, String localName, String qName) throws SAXException {
                    super.endElement( uri, localName, qName );

                    if (qName.equals( "loc_id_global" )){
                        list.add( msg );
                    }
                    if (qName.equals( "return" )){
                        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>( MainMenu.this, android.R.layout.simple_list_item_single_choice );
                    }
                }
            } );

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }*/








}
