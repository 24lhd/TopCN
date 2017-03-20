package com.lhd.fm;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

import com.lhd.activity.Main;
import com.lhd.adaptor.ListDiemHocTap;
import com.lhd.obj.DiemHocTap;
import com.lhd.obj.SinhVien;
import com.lhd.task.ParserSinhVien;

import java.util.ArrayList;

import static com.lhd.activity.Main.SINH_VIEN;
import static com.lhd.duong.Conections.isOnline;

/**
 * Created by D on 17/03/2017.
 */

public class KetQuaHocTapSinhVien extends Frame {
    private ArrayList<DiemHocTap> diemHocTaps;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            try {
//                SinhVien sinhVien = (SinhVien) msg.obj;
                if (msg.obj instanceof SinhVien) {
                    geted = true;
                    checkDatabase();
                    showRecircleView();
                    return;
                }
            } catch (NullPointerException e) {
//                startParser();
            }
        }
    };
    private boolean geted;
    private Main main;
    private String msv;
    private SinhVien sinhVien;

    @Override
    protected void startParser() {
        showProgress();
        ParserSinhVien parserSinhVien = new ParserSinhVien(handler, getActivity());
        parserSinhVien.execute(msv);
    }

    @Override
    public void setRecyclerView() {
        ArrayList<Object> objects = new ArrayList<>();
        objects.addAll(diemHocTaps);
        ListDiemHocTap listDiemHocTap = new ListDiemHocTap(recyclerView, objects, null,Main.ADS_INDEX_ITEM, (Main) getActivity(), diemHocTaps);
        recyclerView.setAdapter(listDiemHocTap);
        showRecircleView();
    }

    @Override
    public void checkDatabase() {
        showProgress();
        main = (Main) getActivity();
        sinhVien= (SinhVien) getArguments().getSerializable(SINH_VIEN);
        msv = sinhVien.getMa();
        if (isOnline(getActivity()) && geted == false) {
            loadData();
            return;
        }
       (new LoadData()).execute();
    }
    class LoadData extends AsyncTask<Void,Void,Void> {
        @Override
        protected Void doInBackground(Void... params) {
            diemHocTaps = dulieu.getListDiemHocTap(msv);
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            if (diemHocTaps != null) {
                setRecyclerView();
            } else loadData();
        }
    }
}