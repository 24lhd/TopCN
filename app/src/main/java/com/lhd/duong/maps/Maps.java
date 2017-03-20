package com.lhd.duong.maps;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by D on 15/01/2017.
 */

public class Maps {
    protected String en="en";
    protected String vi="vi";
    public static String getNameByLocation(Context context,double lat, double lng) throws NullPointerException{
        //tìm kiếm vị trí
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(lat,lng,1);// getfromlocation trả vể list nên cần tạo 1 list
            if (addresses.size()==0){
                return "";
            }
            String name = addresses.get(0).getAddressLine(0);
            if (addresses.get(0).getAddressLine(1) instanceof String){
                name +=" - " +addresses.get(0).getAddressLine(1);
            }
            if (addresses.get(0).getAddressLine(2) instanceof String){
                name +=" - " +addresses.get(0).getAddressLine(2);
            }
            return name;
        } catch (IOException e) {
            return "";
        }
    }
    public void startActivityToTurnOnLocation(Context context) {
        Intent myIntent = new Intent( Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(myIntent);
    }
    /**- Khoảng cách giữa 2 vị trí
     * @param location1 vị trí 1
     * @param location2 vị trí 2
     * @returnthe trả về khoảng cách 2 vị trí đơn vị mét
     */
    public float distanceTwoLocation(Location location1, Location location2) {
        return location1.distanceTo(location2);
    }
    /**
     * Tìm kiếm vị trí bằng tên vị trí
     * @param context ngữ cảnh gọi
     * @param start vị trí bắt đầu
     * @param end vị trí kết thúc
     */
    public void searchLocationByName(Context context,String start, String end) {
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        Location locationStart = null,locationEnd = null;
        try {
            List<Address> addressesStart = geocoder.getFromLocationName(start,1);
            List<Address> addressesEnd = geocoder.getFromLocationName(end,1);
            if (addressesStart.size()>0){
                Address addStart = addressesStart.get(0);
                locationStart = new Location("");
                locationStart.setLatitude(addStart.getLatitude());
                locationStart.setLongitude(addStart.getLongitude());
            }
            if (addressesEnd.size()>0){
                Address addEnd = addressesEnd.get(0);
                locationEnd = new Location("");
                locationEnd.setLatitude(addEnd.getLatitude());
                locationEnd.setLongitude(addEnd.getLongitude());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (locationStart==null||locationEnd==null){
            Toast.makeText(context,"Vị trí không thoả mã",Toast.LENGTH_LONG).show();
            return;
        }else {
            Toast.makeText(context,"Vị trí  thoả mã",Toast.LENGTH_LONG).show();
        }
    }

    /**
     * kiem tra xem vi tri da duoc bat hay chua
     * @param context 1 ngu canh goi
     * @return
     */
    public boolean isLocationIsEnable(Context context) {
        LocationManager lm = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;
        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch(Exception ex) {}
        return gps_enabled;
    }
    public Marker drawMarker(GoogleMap googleMap,double lat, double lng, BitmapDescriptor iconMarker, String title, String diaChi){
        LatLng latLng = new LatLng(lat,lng);//tạo kinh vĩ
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.icon(iconMarker);
        markerOptions.title(title);
        markerOptions.snippet(diaChi);
        return googleMap.addMarker(markerOptions);
    }
    public Polyline drawPolyBetweenTwoLocation(GoogleMap googleMap,LatLng latLngA, LatLng latLngB,int color,int width) {
        PolylineOptions polylineOptions = new PolylineOptions();
        polylineOptions.color(color);
        polylineOptions.width(width);
        polylineOptions.add(latLngA,latLngB);
        return googleMap.addPolyline(polylineOptions);
    }
    public void setMapVeTinh(GoogleMap googleMap) {
        googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
    }
    public void setMapGiaoThong(GoogleMap googleMap) {
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }
    public void moveToLocation(GoogleMap googleMap,Location location,int sizeZoom) {
        if (location instanceof Location){
            LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, sizeZoom);
            googleMap.animateCamera(cameraUpdate);
        }
    }
    public Polyline drawRoad(GoogleMap googleMap, ArrayList<StepsGoLocation> stepses, ArrayList<Marker> arrMarkerFlags, int color, int width, int icon){
        ArrayList<LatLng> latLngs=new ArrayList<>();
        if (arrMarkerFlags!=null)
            for (Marker marker:arrMarkerFlags)
                marker.remove();
        arrMarkerFlags=new ArrayList<>();
        for (StepsGoLocation stepsGoLocation : stepses) {
            latLngs.add(new LatLng(Double.parseDouble(stepsGoLocation.getStart_locationLatSteps()),
                    Double.parseDouble(stepsGoLocation.getStart_locationLngSteps())));
            Marker marker=drawMarker(googleMap,Double.parseDouble(stepsGoLocation.getStart_locationLatSteps()),
                    Double.parseDouble(stepsGoLocation.getStart_locationLngSteps()),
                    BitmapDescriptorFactory.fromResource(icon),
                    stepsGoLocation.getHtml_instructions(),
                    stepsGoLocation.getTravel_mode());
            marker.setTag(stepsGoLocation);
            arrMarkerFlags.add(marker);
        }
        PolylineOptions polylineOptions = new PolylineOptions();
        polylineOptions.color(color);
        polylineOptions.width(width);
        polylineOptions.addAll(latLngs);
        return googleMap.addPolyline(polylineOptions);
    }

    /**
     * lay du lieu api cua Google maps ve ve duong di cua 2 location
     * @param locationStart vi tri bat dau
     * @param locationEnd vi tri ket thuc
     * @param mode phuong tien di
     * @param handler 1 doi tuong de nhan ket qua tra ve
     */
    public void parserGoogleMapAPI(Location locationStart, Location locationEnd, String mode,Handler handler){
        AsynGetLatLng asynGetLatLng=new AsynGetLatLng(handler,mode);
        asynGetLatLng.execute(locationStart,locationEnd);
    }
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            GoogleMapAPI googleMapAPI= (GoogleMapAPI) msg.obj;
//            if (googleMapAPI!=null){
//                drawRoad(googleMapAPI.getItemSteps(), getResources().getColor(R.color.colorPrimary), 5);
//                setGoogleMapAPIToPanel(googleMapAPI);
//            }else if (msg.what==0){
//                progressDialog.dismiss();
//                Toast.makeText(NavigationActivity.this,"Không tìm thấy đường đi",Toast.LENGTH_SHORT).show();
//            }
        }
    };

}
