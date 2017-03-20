package com.lhd.task;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.lhd.config.Config;
import com.lhd.db.DuLieu;
import com.lhd.duong.http.DuongHTTP;
import com.lhd.obj.DiemHocTap;
import com.lhd.obj.SinhVien;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import static com.lhd.config.Config.GET_SV;
import static com.lhd.duong.ChucNangPhu.getJSONByObj;

/**
 * Created by D on 07/03/2017.
 *
 * 1 luông con để lấy thông tin của sinh viên đồng thời post lên api qua phương thức post của okhttp
 */

public class ParserSinhVien extends AsyncTask<String, Void,SinhVien>{
        private Context context;
        private DuLieu duongSQLite;
        private Handler handler;
        private DuongHTTP duongHTTP;
    Config config;
        public ParserSinhVien(Handler handler, Context context) {
            this.handler=handler;
            this.duongSQLite=new DuLieu(context);
            this.context=context;
            duongHTTP=new DuongHTTP();
            config=new Config();

        }
        @Override
        protected SinhVien doInBackground(String... params) {
            Document docMonHocBatBuoc = null;
            Document docLinkMonHocConThieu = null;
            Document docLinkMonHocTuChon = null;
            try {
                docMonHocBatBuoc = Jsoup.connect(config.getLinkMonHocBatBuoc(params[0])).get();
                docLinkMonHocConThieu = Jsoup.connect(config.getLinkMonHocConThieu(params[0])).get();
                docLinkMonHocTuChon = Jsoup.connect(config.getLinkMonHocTuChon(params[0])).get();
                /**
                 * lấy thông tin cá nhân ở trang docMonHocBatBuoc
                 */
                String msv=params[0];
                /**
                 * xóa hết dữ liệu điểm học tập của  sinh viên theo mã trước khi cài
                 */
                duongSQLite.deleteItemDiemHocTap(msv);
                /**
                 * lấy thông tin kết quả học tập ở docMonHocBatBuoc học phần bắt buộc và chèn vào csdl
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
                 * lấy thông tin kết quả học tập ở docLinkMonHocConThieu môn còn thiếu và chèn vào csdl
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
                 * lấy thông tin kết quả học tập ở docLinkMonHocTuChon môn tự chọn và chèn vào csdl
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
                /**
                 * lấy thông tin của sinh viên
                 */
                String ten= docMonHocBatBuoc.select("b").get(0).text() ;
                String lop=docMonHocBatBuoc.select("b").get(1).text();
                String khoa= docMonHocBatBuoc.select("b").get(2).text();
                String tl =   docMonHocBatBuoc.select("b").get(3).text();
                String nam= docMonHocBatBuoc.select("b").get(5).text();
                /**
                 * đọc thông tin từ sinh viên và lọc các dữ liệu và put lên server update
                 */
                SinhVien sinhVien=parserSinhVien(msv,ten,lop,khoa,tl,nam);
                if (!(sinhVien instanceof SinhVien)) sinhVien=config.getArraySinhVienByJson((new DuongHTTP()).getHTTP(GET_SV)).get(0);
                return sinhVien;
            } catch (Exception e) {
                Log.e("faker"," TaskQLCL IOException");
            }
            return null;
        }
        @Override
        protected void onPostExecute(SinhVien sinhVien) {
            if (sinhVien instanceof SinhVien){
                Message message=new Message();
                message.obj=sinhVien;
                handler.sendMessage(message);
                try {
                    config.postToServer(sinhVien);
                } catch (Exception e) {
                }
            }

            else{
                handler.sendEmptyMessage(2);
            }
            Log.e("faker"," TaskQLCL onPostExecute");
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
        String khoaBanDau=khoa;
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
        return new SinhVien(msv,ten,lop,k,tl,nam,nbatdau,nketthuc,mahe,manganh,khoaBanDau);
    }
}
