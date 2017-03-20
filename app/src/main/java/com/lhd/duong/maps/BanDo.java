package com.lhd.duong.maps;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by D on 18/02/2017.
 */

public class BanDo {
    /**
     * cài đặt bản đồ hiển thị chế độ vễ tinh
     * @param googleMap một đối tượng google map
     */
    public void setBanDoVeTinh(GoogleMap googleMap) {
        googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
    }

    /**
     * cài đặt bản đồ ban đầu
     * @param googleMap
     */
    public void caiDatBanDo(GoogleMap googleMap) {
        UiSettings uiSettings = googleMap.getUiSettings();
        uiSettings.setMyLocationButtonEnabled(false); // không sử dụng buttom my location mặc định của máy
        uiSettings.setMapToolbarEnabled(false); // không sử dụng toolbar hỗ trợ của gg map để bật ứng dụng bản đò
        googleMap.setMyLocationEnabled(true); // sử dụng vị trí của máy
    }
    /**
     * cài đặt bản đồ hiển thị chế độ giao thông
     * @param googleMap một đối tượng google map
     */
    public void setBanDoGiaoThong(GoogleMap googleMap) {
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }

    /**
     * vị trí đã được bật
     * @param context ngữ cảnh gọi
     * @return đúng - bật , sai - tắt
     */
    public  boolean isLocationIsEnable(Context context) {
        LocationManager lm = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch(Exception ex) {}
        return gps_enabled;
    }

    /**
     * kiểm tra đã bật vị trí của máy, và thông báo cửa sổ bật GPS nếu vị trí chwua dc bật thì finish ngữ cảnh
     * @param context ngữ cảnh
     * @param title tiêu đề của thông báo
     * @param msg nội dung
     */
    public  void checkLocationIsEnable(final Context context, String title, String msg) {
        if(!isLocationIsEnable(context)) {
            AlertDialog.Builder turnOnLoactionDialog=new AlertDialog.Builder(context);
            turnOnLoactionDialog.setTitle(title);
            turnOnLoactionDialog.setMessage(msg);
            turnOnLoactionDialog.setCancelable(false);
            turnOnLoactionDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    moCuaSoBatGPS(context);
                }
            });
            turnOnLoactionDialog.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ((Activity) context).finish();
                }
            });
            turnOnLoactionDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
//                    if(!isLocationIsEnable(context))  ((Activity) context).finish();
                }
            });
            turnOnLoactionDialog.show();
        }
    }

    /**
     * Di chuyển tới một vị trí trong bản đồ
     * @param googleMap đối tượng GoogleMap
     * @param location vị trí cần di chuyển tới
     * @param sizeZoom độ zoom của bản đồ khi di chuyển tới
     */
    public void diChuyenToiViTri(GoogleMap googleMap, Location location, int sizeZoom) {
        if (location instanceof Location){
            LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, sizeZoom);// camera xem vị trí với tham số là kinh vĩ độ và độ zôm
            googleMap.animateCamera(cameraUpdate);
        }
    }
    /**
     * bật activity bật vị trí
     * @param context
     */
    public void moCuaSoBatGPS(Context context) {
        Intent myIntent = new Intent( Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(myIntent);
    }


    /**
     * Vẽ và thêm một điểm marker trên bản đồ
     * @param googleMap bản đồ
     * @param lat kinh độ
     * @param lng vĩ độ
     * @param hue hình ảnh của điểm marker, dùng BitmapDescriptorFactory.fromResource(R.drawable.your_drawable)
     *            để custem hoặc dùng mặc định BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)
     * @param title tên cửa tiêu đề
     * @param snippet địa chỉ
     * @return
     */
    public Marker veVaThemMotMarker(GoogleMap googleMap,double lat, double lng, BitmapDescriptor hue, String title, String snippet){
        //định nghĩa điểm ảnh
        // mỗi maker chỉ hiện thị một điểm ảnh
        LatLng latLng = new LatLng(lat,lng);//tạo kinh vĩ
        MarkerOptions markerOptions = new MarkerOptions(); // khởi tạo đối tượng định nghĩa một điểm trên bản đồ
        markerOptions.position(latLng); // vị trí
        markerOptions.icon(hue);// hình ảnh
        markerOptions.title(title); // tên
        markerOptions.snippet(snippet); // địa chỉ
        return googleMap.addMarker(markerOptions); // add vào gg map
    }

    /**
     * lấy địa chỉ qua vị trí GPS
     * @param lat
     * @param lng
     * @param context
     * @return trả về tên vị trí của kinh vĩ độ truyền vào
     * @throws NullPointerException
     */
    public String layDiaChiQuaViTri(Context context,double lat, double lng) throws NullPointerException{
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
            return "Chưa xác định được";
        }
    }
}
