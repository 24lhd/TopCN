package com.lhd.obj;

/**
 * Created by D on 15/03/2017.
 */

public class Lop {
    private String lop;
    private String khoa;

    @Override
    public String toString() {
        return "Lop{" +
                "lop='" + lop + '\'' +
                ", khoa='" + khoa + '\'' +
                '}';
    }

    public String getLop() {
        return lop;
    }

    public String getKhoa() {
        return khoa;
    }

    public Lop(String lop, String khoa) {

        this.lop = lop;
        this.khoa = khoa;
    }
}
