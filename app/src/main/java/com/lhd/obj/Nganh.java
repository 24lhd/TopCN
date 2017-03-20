package com.lhd.obj;

/**
 * Created by D on 12/03/2017.
 */

public class Nganh{
    private String manganh;
    private String nam;

    @Override
    public String toString() {
        return "Nganh{" +
                "manganh='" + manganh + '\'' +
                ", nam='" + nam + '\'' +
                '}';
    }

    public String getManganh() {
        return manganh;
    }

    public String getNam() {
        return nam;
    }

    public Nganh(String manganh, String nam) {

        this.manganh = manganh;
        this.nam = nam;
    }
}
