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

        //SOAPlocStatsAsynchTask soapLocATask = new SOAPlocStatsAsynchTask();
        //soapLocATask.execute(  );
        //parseXML();

        retrieveJSONonlineLoc( 3 );

        Intent intent = new Intent(this, Stats4.class);
        //EditText editText = (EditText) findViewById(R.id.editText);
        //String message = editText.getText().toString();
        //intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);

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
        //envelope.dotNet = true;
        envelope.setOutputSoapObject( soapObject );



        //HttpsTransportSE httpsTransportSE = new HttpsTransportSE( wsdl_url );
        HttpTransportSE httpTransportSE = new HttpTransportSE( wsdl_url );
        try {
            httpTransportSE.debug = true;
            httpTransportSE.call( soap_action, envelope );

            SoapObject obj = (SoapObject) envelope.bodyIn;
            resp = obj.getProperty( 0 ).toString();
            System.out.print( resp );
            Log.e( "SOAP", "" + resp );

            handleSOAPstring( resp );

            /*if(envelope != null){
                SoapObject soapResponse = (SoapObject) envelope.bodyIn;

                //SoapObject objectR = (SoapObject) soapObject.getProperty( 0 );

                SoapObject soapLocData = (SoapObject) soapResponse.getProperty( "LocListe" );

                Log.e( "SOAP", "response: " + soapResponse );

                Log.e( "SOAP", "property 1: " + soapLocData.getProperty( "loc_id_global" ));
                Log.e( "SOAP", "property 2: " + soapLocData.getProperty( "users_id_global" ));
                Log.e( "SOAP", "property 3: " + soapLocData.getProperty( "lat" ));
                Log.e( "SOAP", "property 4: " + soapLocData.getProperty( "lng" ));
                Log.e( "SOAP", "property 5: " + soapLocData.getProperty( "speed" ));
                Log.e( "SOAP", "property 6: " + soapLocData.getProperty( "accel" ));
            }else
            {
                Log.d("WS", "Response Envelop Error");
            }*/





        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }

    }



    public void handleSOAPstring(String soapResp){

        String regex_Loc = "Loc";
        String regex_Loc_first = "[Loc";
        String regex_loc_id = "loc_id_global";
        String regex_users_id = "users_id_global";
        String regex_lat = "lat";
        String regex_lng = "lng";
        String regex_speed = "speed";
        String regex_accel = "accel";
        String regex_semicolon = ";";
        String regex_bracket_open = "[";
        String regex_bracket_close = "]";
        String regex_curly_open = "{";
        String regex_curly_close = "}";
        String add_quote = "\"";

        /*String[] split_Loc = soapResp.split( regex_Loc );

        //Log.e( "SOAP", "String Split 'LOC': " + split_Loc );


        String loc_add_quote = resp.replaceAll( regex_Loc, "" );
        loc_add_quote = loc_add_quote.replace( "[", "{\"Loc\":[" );
        loc_add_quote = loc_add_quote.replaceAll( regex_loc_id, "\"" + regex_loc_id + "\"" );
        loc_add_quote = loc_add_quote.replaceAll( regex_users_id, "\"" + regex_users_id + "\"" );
        loc_add_quote = loc_add_quote.replaceAll( regex_lat, "\"lat\"" );
        loc_add_quote = loc_add_quote.replaceAll( regex_lng, "\"lng\"" );
        loc_add_quote = loc_add_quote.replaceAll( regex_speed, "\"speed\"" );
        loc_add_quote = loc_add_quote.replaceAll( regex_accel, "\"accel\"" );
        loc_add_quote = loc_add_quote.replace( "]", "]}" );
        loc_add_quote = loc_add_quote.replaceAll( ";", "," );
        loc_add_quote = loc_add_quote.replaceAll( " ", "" );
        loc_add_quote = loc_add_quote.replaceAll( ",}", "}" );
        loc_add_quote = loc_add_quote.replaceAll( "=", ":" );

        //loc_add_quote = loc_add_quote.replaceAll( regex_Loc_first, add_quote + regex_Loc + add_quote );

        Log.e( "SOAP", "with quotes" + loc_add_quote);*/



        /*for (String split_loc_for : split_Loc){

            String[] split_loc_id = split_loc_for.split( regex_loc_id );



        }*/




        /*for (String split_Loc_for : split_Loc){



        }*/




        /*for (String split_loc_for : split_Loc){

            String[] split_loc_id = split_loc_for.split( regex_loc_id );

            Log.e( "SOAP", "split_loc_for = " + split_loc_for + " ///// split_loc_id = " + split_loc_id);

            for (String split_loc_id_for : split_loc_id){

                String[] split_users_id = split_loc_id_for.split( regex_users_id );

                Log.e( "SOAP", "split_loc_for = " + split_loc_for + " ///// split_loc_id = " + split_loc_id + " ///// split_users_id = " + split_users_id);

            }

        }*/





        /*for (String split_Loc_loop : split_Loc){

            String[] split_loc_id = split_Loc_loop.split( regex_loc_id );

            for (String split_loc_id_loop : split_loc_id){

                String[] split_users_id = split_loc_id_loop.split( regex_users_id );

                for (String split_users_id_loop : split_users_id){

                    String[] split_lat = split_users_id_loop.split( regex_lat );

                    for (String split_lat_loop : split_lat){

                        String[] split_lng = split_lat_loop.split( regex_lat );

                        for (String split_lng_loop : split_lng){

                            String[] split_speed = split_lng_loop.split( regex_lat );

                            for (String split_speed_loop : split_speed){

                                String[] split_accel = split_speed_loop.split( regex_lat );

                                for (String split_accel_loop : split_accel){

                                    String[] split_accel_data = split_accel_loop.split( regex_semicolon );

                                    Log.e( "SOAP", "loc_id: " + split_loc_id_loop + "/ users_id: " + split_users_id_loop + "/ lat: " + split_lat_loop + "/ lng: " + split_lng_loop + "/ speed: " + split_speed_loop + "/ accel: " + split_accel + "/ accel_semicolon: " + split_accel_data );

                                }
                            }

                        }

                    }

                }

            }

        }*/




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
