package com.lhd.task;


import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

import com.lhd.config.Config;
import com.lhd.duong.http.DuongHTTP;
import com.lhd.obj.SinhVien;

import org.json.JSONObject;


/**
 * Created by D on 07/03/2017.
 */

public class HTTPGetSinhVien extends AsyncTask<String,Void,SinhVien> {
    private Handler handler;
private DuongHTTP duongHTTP;
    public HTTPGetSinhVien(  Handler handler) {
        this.handler = handler;
        duongHTTP=new DuongHTTP();
    }

    @Override
    protected SinhVien doInBackground( String... strings) {
        try {

            JSONObject jsonObject=new JSONObject(duongHTTP.getHTTP(Config.GET_SV+strings[0]));
        } catch (Exception e) {}
        return null;
    }
    @Override
    protected void onPostExecute(SinhVien sinhVien) {
        if (sinhVien instanceof SinhVien){
            Message message=new Message();
            message.obj=sinhVien;
            handler.sendMessage(message);
        }else{
            handler.sendEmptyMessage(2);
        }

    }
}