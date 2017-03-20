package com.lhd.duong.maps;

import android.location.Location;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by D on 15/01/2017.
 */

public class AsynGetLatLng extends AsyncTask<Location, Void, String> {
    private Handler handler;
    private String mode;
    public AsynGetLatLng(Handler handler, String mode) {
        this.handler=handler;
        this.mode=mode;
    }

    @Override
    protected void onPostExecute(String result) {
        GoogleMapAPI googleMapAPI=null;
        try {
            JSONObject json = new JSONObject(result);
            String status = null;
            status = json.getString("status");
            if (status.equals("OK")) {
                JSONArray object = json.getJSONArray("routes");
                JSONObject item = object.getJSONObject(0);
                String copyrights = item.getString("copyrights");

                String summary = item.getString("summary");
//                Log.e("faker", summary);
                JSONArray legs = item.getJSONArray("legs");

                JSONObject objectLegs = legs.getJSONObject(0);

                JSONObject distance = objectLegs.getJSONObject("distance");
                String distanceText = distance.getString("text");
                String distanceValue = distance.getString("value");
//                Log.e("faker", distanceText + " " + distanceValue);

                JSONObject duration = objectLegs.getJSONObject("duration");
                String durationText = duration.getString("text");
                String durationValue = duration.getString("value");
//                Log.e("faker", durationText + " " + durationValue);

                String end_address = objectLegs.getString("end_address");
                String start_address = objectLegs.getString("start_address");
//                Log.e("faker", end_address + " " + start_address);

                JSONObject start_location = objectLegs.getJSONObject("start_location");
                String start_locationLat = start_location.getString("lat");
                String start_locationLng = start_location.getString("lng");
//                Log.e("faker", start_locationLat + " " + start_locationLng);

                JSONObject end_location = objectLegs.getJSONObject("end_location");
                String end_locationLat = end_location.getString("lat");
                String end_locationLng = end_location.getString("lng");
//                Log.e("faker", end_locationLat + " " + end_locationLng);

                JSONArray steps = objectLegs.getJSONArray("steps");

                ArrayList<StepsGoLocation> arrStepses =new ArrayList<>();
                for (int i = 0; i < steps.length(); i++) {
                    JSONObject StepsLocations=steps.getJSONObject(i);
                    JSONObject distanceSteps = StepsLocations.getJSONObject("distance");
                    String distanceTextSteps = distanceSteps.getString("text");
                    String distanceValueSteps = distanceSteps.getString("value");
//                    Log.e("faker", distanceTextSteps + " " + distanceValueSteps);

                    JSONObject durationSteps = StepsLocations.getJSONObject("duration");
                    String durationTextSteps = durationSteps.getString("text");
                    String durationValueSteps = durationSteps.getString("value");
//                    Log.e("faker", durationTextSteps + " " + durationValueSteps);

                    JSONObject start_locationSteps = StepsLocations.getJSONObject("start_location");
                    String start_locationLatSteps = start_locationSteps.getString("lat");
                    String start_locationLngSteps = start_locationSteps.getString("lng");
//                    Log.e("faker", start_locationLatSteps + " " + start_locationLngSteps);

                    JSONObject end_locationSteps = StepsLocations.getJSONObject("end_location");
                    String end_locationLatSteps = end_locationSteps.getString("lat");
                    String end_locationLngSteps = end_locationSteps.getString("lng");
//                    Log.e("faker", end_locationLatSteps + " " + end_locationLngSteps);

                    String html_instructions= Html.fromHtml((String) StepsLocations.getString("html_instructions")).toString();
                    html_instructions=html_instructions.replace("\n"," ");
//                    Log.e("faker", html_instructions);

                    String travel_mode=StepsLocations.getString("travel_mode");
//                    Log.e("faker", travel_mode);
                    arrStepses.add(new StepsGoLocation(distanceTextSteps,distanceValueSteps,durationTextSteps,durationValueSteps,
                            start_locationLatSteps,start_locationLngSteps,end_locationLatSteps,end_locationLngSteps,html_instructions,travel_mode));
                }
                googleMapAPI=new GoogleMapAPI(status,copyrights,
                        summary,distanceText,distanceValue,durationText,durationValue,
                        end_address,start_address,start_locationLat,start_locationLng,end_locationLat,end_locationLng, arrStepses);
                Message message=new Message();
                message.obj=googleMapAPI;
                handler.sendMessage(message);
            } else {
                handler.sendEmptyMessage(0);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
//            PolylineOptions options = new PolylineOptions();
//            options.color(Color.RED);;
//            options.width(10);
//            options.addAll(latLngs);
//            if (poLyLine!=null){
//                poLyLine.remove();
//            }
//            poLyLine = mGoogleMap.addPolyline(options);
    }
    @Override
    protected String doInBackground(Location... params) {

        String diemDau = ""+params[0].getLatitude()+","+params[0].getLongitude();
        diemDau = diemDau.replace(" ", "+");
        String diemCuoi = ""+params[1].getLatitude()+","+params[1].getLongitude();
        diemCuoi = diemCuoi.replace(" ", "+");
        String vi = "vi";
        String link = "https://maps.googleapis.com/maps/api/directions/json?origin="
                + diemDau + "&destination="
                + diemCuoi + "&avoid=tolls|highways|ferries&mode="
                + mode + "&language="
                + vi;
        String result = "";
        try {
            // Create a URL for the desired page
//                Log.e("faker","Rẽ \u003cb\u003etrái\u003c/b\u003e tại Công Ty Tnhh Nano An Phát vào \u003cb\u003eNgô Quyền\u003c/b\u003e");
            URL url = new URL(link);
            Log.e("faker", link);
            // Read all the text returned by the server
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            String str = null;
            while ((str = in.readLine()) != null) {
                // str is one line of text; readLine() strips the newline character(s)
                result = result + str;

            }
            in.close();
            return result;

        } catch (MalformedURLException e) {
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}