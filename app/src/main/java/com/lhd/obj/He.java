package com.lhd.obj;

/**
 * Created by D on 12/03/2017.
 */

public class He {
    private String mahe;
    private String tenhe;
    private String nam;

    @Override
    public String toString() {
        return "He{" +
                "mahe='" + mahe + '\'' +
                ", tenhe='" + tenhe + '\'' +
                ", nam='" + nam + '\'' +
                '}';
    }

    public String getMahe() {
        return mahe;
    }

    public String getTenhe() {
        return tenhe;
    }

    public String getNam() {
        return nam;
    }

    public He(String mahe, String tenhe, String nam) {

        this.mahe = mahe;
        this.tenhe = tenhe;
        this.nam = nam;
    }
}
