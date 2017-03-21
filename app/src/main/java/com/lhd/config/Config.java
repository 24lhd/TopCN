package com.lhd.config;

import com.google.gson.Gson;
import com.lhd.duong.ChucNangPhu;
import com.lhd.duong.http.DuongHTTP;
import com.lhd.obj.He;
import com.lhd.obj.Khoa;
import com.lhd.obj.Lop;
import com.lhd.obj.Nganh;
import com.lhd.obj.SinhVien;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;


/**
 * Created by D on 07/03/2017.
 */

public class Config {
    public static final String DATABASE_NAME = "topcn";
    public static final String APP = "https://topcongnghiep.herokuapp.com";

    public Config() {

    }

    /**

     * lick post 1 sinh viên lên server
     */
    public static final String GET_HE = APP +
            "/api/he";
    public static final String GET_NGANH = APP +
            "/api/nganh";
    public static final String GET_LUOT_TRUY_CAP = APP +
            "/api/luottruycap";
    public static final String GET_KHOA = APP +
            "/api/khoa";
    public  String getLinkMonHocBatBuoc(String msv) {
        return "https://dttc.haui.edu.vn/vn/s/sinh-vien/bang-mon-bat-buoc?action=p1&p=1&ps=500&exp=rownb&dir=1&s=" + msv;
    }

    public static final String GET_LOP = APP +
            "/api/lop";
    public static final String GET_SV = APP +
            "/api/sv/";

    public  String getLinkMonHocConThieu(String msv) {
        return "https://dttc.haui.edu.vn/vn/s/sinh-vien/bang-mon-con-thieu?action=p3&p=1&ps=500&exp=SubjectName&dir=1&s=" + msv;
    }

    public  String getLinkMonHocTuChon(String msv) {
        return "https://dttc.haui.edu.vn/vn/s/sinh-vien/bang-mon-tu-chon?action=p2&p=1&ps=500&exp=rownb&dir=1&s=" + msv;
    }

    /**
     * laasy top 100 sinh theo tóp hệ gồm mã hệ là hệ gì và năm mấy
     * @param mahe
     * @param nam
     * @return
     */
    public  String getLinkTopHe(String mahe, String nam) {
        if (nam.equals("")) nam="0";
        return APP + "/api/tophe/"+mahe+"/"+nam;
    }
    public  String getLinkTopNganh(String mangang, String nam) {
        if (nam.equals("")) nam="0";
        return  APP + "/api/topnganh/"+mangang+"/"+nam;
    }
    public  String getLinkTopKhoa(String k,String nbatdau, String nam) {
        if (nam.equals("")) nam="0";
        return APP + "/api/topkhoa/"+k+"/"+nbatdau+"/"+nam;
    }
    public  String getLinkTopLop(String lop,String khoa ) {
            return APP + "/api/toplop/"+urlencode(lop)+"/"+urlencode(khoa);
    }

    public  String urlencode(String original) {
        try {
            return URLEncoder.encode(original, "utf-8").replace("+", "%20").replace("*", "%2A").replace("%7E", "~");}
        catch(UnsupportedEncodingException e) {}
        return null;
    }
    public  ArrayList<Nganh> getNganhByJson(String jsonNganhs) {

        try {ArrayList<Nganh> nganhs=new ArrayList<>();
            JSONObject jsonObjectNganhs = new JSONObject(jsonNganhs);
            JSONArray jsonArrayNganhs = jsonObjectNganhs.getJSONArray("data");
            for (int i = 0; i < jsonArrayNganhs.length(); i++) {
                JSONObject jsonObject = jsonArrayNganhs.getJSONObject(i);
                Nganh nganh = new Nganh(jsonObject.getString("manganh"),jsonObject.getString("nam"));
                nganhs.add(nganh);
            }
            return nganhs;
        } catch (Exception e) {}
        return null;

    }

    public  ArrayList<Khoa> getKhoaByJson(String jsonKhoas) {
        try {ArrayList<Khoa> khoas=new ArrayList<>();
            JSONObject jsonObjectKhoas=new JSONObject(jsonKhoas);
            JSONArray jsonArrayKhoa=jsonObjectKhoas.getJSONArray("data");
            for (int i = 0; i < jsonArrayKhoa.length(); i++) {
                JSONObject jsonO=jsonArrayKhoa.getJSONObject(i);
                Khoa khoa=new Khoa(jsonO.getString("k"),jsonO.getString("nbatdau"),jsonO.getString("nam"));
                khoas.add(khoa);
            }
            return khoas;
        } catch (Exception e) {}
        return null;
    }

    public  ArrayList<Lop> getLopByJson(String jsonLop) {
        try {ArrayList<Lop> lops=new ArrayList<>();
            JSONObject jsonObjectKhoas=new JSONObject(jsonLop);
            JSONArray jsonArrayKhoa=jsonObjectKhoas.getJSONArray("data");
            for (int i = 0; i < jsonArrayKhoa.length(); i++) {
                JSONObject jsonO=jsonArrayKhoa.getJSONObject(i);
                Lop lop=new Lop(jsonO.getString("lop"),jsonO.getString("khoa"));
                lops.add(lop);
            }
            return lops;
        } catch (Exception e) {}
        return null;
    }

    public  ArrayList<He> getHesByJson(String jsonHes) {
        ArrayList<He> hes = new ArrayList<>();
        try {
            JSONObject jsonObjectHes = new JSONObject(jsonHes);
            int jsonObjectHesStatust = jsonObjectHes.getInt("status");
            JSONArray jsonArrayHes = jsonObjectHes.getJSONArray("data");
            if (jsonObjectHesStatust != 1) {
                ChucNangPhu.showLog("getHesByJson fail true khong lay duoc du liẹu");
                return null;
            }
            for (int i = 0; i < jsonArrayHes.length(); i++) {
                JSONObject jsonObject = jsonArrayHes.getJSONObject(i);
                He he = new He(jsonObject.getString("mahe"), jsonObject.getString("tenhe"), jsonObject.getString("nam"));
                hes.add(he);
            }
            return hes;
        } catch (JSONException e) {
        }
        return null;
    }
    public  ArrayList<SinhVien> getArraySinhVienByJson(String jsonTop) {
        ArrayList<SinhVien> sinhViens = new ArrayList<>();
        try {
            Gson gson=new Gson();
            JSONObject jsonObject= new JSONObject(jsonTop);
            int jsonObjectHesStatust = jsonObject.getInt("status");
            String jsonObjectHesMSG = jsonObject.getString("msg");
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            if (jsonObjectHesStatust != 1) {
                ChucNangPhu.showLog("getHesByJson fail true khong lay duoc du liẹu");
                return null;
            }
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                SinhVien sinhVien= gson.fromJson(object.toString(),SinhVien.class);
                sinhViens.add(sinhVien);
            }
            return sinhViens;
        } catch (JSONException e) {
        }
        return null;
    }
    /**
     * post thông tin của sinh viên lên server
     * @param sinhVien
     * @throws Exception
     */
    public  void postToServer(final SinhVien sinhVien) throws Exception{
        final Gson gson=new Gson();
        final String str=gson.toJson(sinhVien);

        new Thread(){
            @Override
            public void run() {
                try {

                    (new DuongHTTP()).postHTTP(Config.APP+"/sv/",str);// post len server
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }

}
