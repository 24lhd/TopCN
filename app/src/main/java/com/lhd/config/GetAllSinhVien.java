package com.lhd.config;

import android.util.Log;

import com.lhd.obj.SinhVien;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.ArrayList;

/**
 * Created by D on 08/03/2017.
 */

public class GetAllSinhVien {
    public void getAllSinhVien() {
        int khoaHienTai=2;
        String he="31";
        int loi=0;
        for (String khoa:countDownKhoa(khoaHienTai))
            for (String nganh:getNganh())
                for (int i =0; i < 600; i++){
                    String msv="";
                    if (i<10) msv="00"+i;
                    else if (i<100) msv="0"+i;
                    else msv=""+i;
                    String ma=""+khoa+""+he+nganh+msv;
                    String link="https://dttc.haui.edu.vn/vn/s/sinh-vien/bang-mon-bat-buoc?action=p1&p=1&ps=500&exp=rownb&dir=1&s=" +
                            ma+
                            "&osk=774fd5732173ddd410660a22bbcc0efc&idx=-1&view=sinh-vien%252Fbang-mon-bat-buoc";
                    SinhVien sinhVien=null;
                    Document doc = null;
                    try {
                        doc = Jsoup.connect(link).get();
                    } catch (Exception e) {
                        Log.e("faker","IOException");
                        continue;
                    }
                    if (doc.select("b.sName").isEmpty()){
                        loi=loi+1;
                        Log.e("faker","null "+ma+"break");
                        if (loi>200){
                            loi=0;
                            break;
                        }
                        continue;
                    }
//
//                        sinhVien=new SinhVien(ma,
//                                doc.select("b").get(0).text(),
//                                doc.select("b").get(1).text(),
//                                doc.select("b").get(2).text(),
//                                doc.select("b").get(5).text(),
//                                doc.select("b").get(3).text());
//                        Log.e("faker",sinhVien.toString());
//                        duongSQLite.getDuongSQLite().insertByColumValue("sinhvien","`ten`,`lop`,`khoa`,`tl`,`nam`,`masv` ",
//                                "'"+sinhVien.getTen()+"',"+
//                                        "'"+sinhVien.getLop()+"',"+
//                                        "'"+sinhVien.getKhoa()+"',"+
//                                        "'"+sinhVien.getTbttl()+"',"+
//                                        "'"+sinhVien.getSvNam()+"',"+
//                                        "'"+sinhVien.getMsv()+"'");
//                        parserSinhVien(ma,
//                                doc.select("b").get(0).text(),
//                                doc.select("b").get(1).text(),
//                                doc.select("b").get(2).text(),
//                                doc.select("b").get(5).text(),
//                                doc.select("b").get(3).text());
                }
    }
    private ArrayList<String> countDownKhoa(int i) {
        ArrayList<String> strings=new ArrayList<>();
        for (int j = i; j >0; j--) {
            if (j<10) strings.add("0"+j);
            else strings.add(""+j);
        }
        return strings;
    }
    public ArrayList<String> getNganh() {
        ArrayList<String> strings=new ArrayList<>();
        for (int i = 10; i < 1000; i+=10) {
            if (i<100) strings.add("0"+i);
            else strings.add(""+i);
        }
        return strings;
    }

}
