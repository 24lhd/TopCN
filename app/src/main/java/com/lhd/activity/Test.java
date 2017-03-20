package com.lhd.activity;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.lhd.db.DuLieu;
import com.lhd.duong.http.DuongHTTP;
import com.lhd.obj.DiemHocTap;
import com.lhd.obj.SinhVien;
import com.mancj.slideup.SlideUp;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import static com.lhd.duong.ChucNangPhu.getJSONByObj;

/**
 * Created by D on 19/02/2017.
 */

public class Test extends Activity {
    private SlideUp slideUp;
    private View dim;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        TaskQLCL taskQLCL=new TaskQLCL(null,new DuLieu(this),this);
//        taskQLCL.execute("0941260041");

    }
    class TaskQLCL extends AsyncTask<String, Void,SinhVien> {
        private Context context;
        private DuLieu duongSQLite;
        private Handler handler;
        private DuongHTTP duongHTTP;

        public TaskQLCL(Handler handler, DuLieu duongSQLite, Context context) {
            this.handler=handler;
            this.duongSQLite=duongSQLite;
            this.context=context;
            duongHTTP=new DuongHTTP();
        }
        @Override
        protected SinhVien doInBackground(String... params) {
            Document docMonHocBatBuoc = null;
            Document docLinkMonHocConThieu = null;
            Document docLinkMonHocTuChon = null;
            try {
//                docMonHocBatBuoc = Jsoup.connect(Config.getLinkMonHocBatBuoc(params[0])).get();
//                docLinkMonHocConThieu = Jsoup.connect(Config.getLinkMonHocConThieu(params[0])).get();
//                docLinkMonHocTuChon = Jsoup.connect(Config.getLinkMonHocTuChon(params[0])).get();
                /**
                 * lấy thông tin cá nhân ở trang docMonHocBatBuoc
                 */
                String msv=params[0];
                /**
                 * xóa hết dữ liệu điểm học tập của  sinh viên theo mã trước khi cài
                 */
                duongSQLite.deleteItemDiemHocTap(msv);
                String ten= docMonHocBatBuoc.select("b").get(0).text() ;
                String lop=docMonHocBatBuoc.select("b").get(1).text();
                String khoa= docMonHocBatBuoc.select("b").get(2).text();
                String tl =   docMonHocBatBuoc.select("b").get(3).text();
                String nam= docMonHocBatBuoc.select("b").get(5).text();
                /**
                 * đọc thông tin từ sinh viên và lọc các dữ liệu
                 */
                Gson gson=new Gson();


                String str=gson.toJson(parserSinhVien(msv,ten,lop,khoa,tl,nam));
                duongHTTP.putHTTP("https://topcongnghiep.herokuapp.com/update/"+msv,str);
                /**
                 * lấy thông tin kết quả học tập ở docMonHocBatBuoc
                 */
                Elements trs=docMonHocBatBuoc.select("tr.RowStyle");
                for (Element tr :trs) {
                    Elements td=tr.select("td");
                    duongSQLite.insertItemDiemHocTap(msv,
                            getJSONByObj(new DiemHocTap(td.get(0).text(),
                                    td.get(1).text(),
                                    td.get(3).text(),
                                    td.get(4).text(),
                                    td.get(5).text())));
                }
                /**
                 * lấy thông tin kết quả học tập ở docLinkMonHocConThieu môn còn thiếu
                 */
                 trs=docLinkMonHocConThieu.select("tr.RowStyle");
                for (Element tr :trs) {
                    Elements td=tr.select("td");
                    duongSQLite.insertItemDiemHocTap(msv,
                            getJSONByObj(new DiemHocTap(td.get(0).text(),
                                    td.get(1).text(),
                                    td.get(2).text(),
                                    "|",
                                    "|")));
                }
                /**
                 * lấy thông tin kết quả học tập ở docLinkMonHocTuChon môn tự chọn
                 */
                 trs=docLinkMonHocTuChon.select("tr.RowStyle");
                for (Element tr :trs) {
                    Elements td=tr.select("td");
                    duongSQLite.insertItemDiemHocTap(msv,
                            getJSONByObj(new DiemHocTap(td.get(0).text(),
                                    td.get(1).text(),
                                    td.get(3).text(),
                                    td.get(4).text(),
                                    td.get(5).text())));
                }

            } catch (Exception e) {
                Log.e("faker"," TaskQLCL IOException");
            }
            return null;
        }
        @Override
        protected void onPostExecute(SinhVien itemNotiDTTCs) {
            Log.e("faker"," TaskQLCL onPostExecute");
        }
    }


    /**
     * chuyển từ các dữ liệu cố sẵn của nahf trường thành dữ liệu mình dùng, tách các dữ liệu cần lấy
     * @param msv
     * @param ten
     * @param lop
     * @param khoa
     * @param tl
     * @param nam
     * @return
     */
    private SinhVien parserSinhVien(String msv, String ten, String lop, String khoa, String tl, String nam) {
        khoa = khoa.replace(")", "");
        khoa= khoa.replace("(", " ");
        khoa = khoa.replace("-", " ");
        khoa = khoa.replace("  ", " ");
        khoa = khoa.replace("  ", " ");
        String[] dm = khoa.split(" ");
        String k=dm[0];
        String nbatdau=dm[1];
        String nketthuc=dm[2];
        String[] cut = msv.split("");
        String manganh = cut[5] + cut[6] + cut[7] + "";
        String mahe = cut[3] + cut[4] + "";
         nam = nam.replace("Sinh viên năm:", "").trim();
         tl = tl.replace("Điểm TBC TL:", "").trim();
        return new SinhVien(msv,ten,lop,k,tl,nam,nbatdau,nketthuc,mahe,manganh,khoa);
    }

}
