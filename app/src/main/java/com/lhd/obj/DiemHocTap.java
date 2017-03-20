package com.lhd.obj;

import com.google.gson.Gson;

/**
 * Created by D on 08/03/2017.
 */

public class DiemHocTap {
   private String maMon;
    private String tenMon;
    private String soTin;
    private String thang10;
    private String thangChu;

    public DiemHocTap(String maMon, String tenMon, String soTin, String thang10, String thangChu) {
        this.maMon = maMon;
        this.tenMon = tenMon;
        this.soTin = soTin;
        this.thang10 = thang10;
        this.thangChu = thangChu;
    }

    public String getMaMon() {
        return maMon;
    }

    public String getTenMon() {
        return tenMon;
    }

    public String getSoTin() {
        return soTin;
    }

    public String getThang10() {
        return thang10;
    }

    public String getThangChu() {
        return thangChu;
    }
    public static DiemHocTap getObjByJSON(String json,Class<DiemHocTap> diemHocTapClass) {
        Gson gson = new Gson();
        return gson.fromJson(json,diemHocTapClass);
    }

    @Override
    public String toString() {
        return "DiemHocTap{" +
                "maMon='" + maMon + '\'' +
                ", tenMon='" + tenMon + '\'' +
                ", soTin='" + soTin + '\'' +
                ", thang10='" + thang10 + '\'' +
                ", thangChu='" + thangChu + '\'' +
                '}';
    }
}
