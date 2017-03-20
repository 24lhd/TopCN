package com.lhd.obj;

/**
 * Created by D on 14/03/2017.
 */

public class Khoa {
    private String khoa;
    private String nbatdau;
    private String nam;

    @Override
    public String toString() {
        return "Khoa{" +
                "khoa='" + khoa + '\'' +
                ", nbatdau='" + nbatdau + '\'' +
                ", nam='" + nam + '\'' +
                '}';
    }

    public String getKhoa() {
        return khoa;
    }

    public String getNbatdau() {
        return nbatdau;
    }

    public String getNam() {
        return nam;
    }

    public Khoa(String khoa, String nbatdau, String nam) {

        this.khoa = khoa;
        this.nbatdau = nbatdau;
        this.nam = nam;
    }
}
