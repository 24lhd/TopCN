package com.lhd.task;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

import com.lhd.duong.http.DuongHTTP;

import org.json.JSONObject;


/**
 * Created by D on 16/03/2017.
 */

public class GetJSONByLink extends AsyncTask<String, Void,String> {
    private Handler handler;
    private DuongHTTP duongHTTP;
    public GetJSONByLink(Handler handler) {
        this.handler = handler;
        duongHTTP=new DuongHTTP();
    }

    @Override
    protected String doInBackground(String... strings) {
        try {
            JSONObject jsonObject=new JSONObject(duongHTTP.getHTTP(strings[0]));
            return jsonObject.toString();
        } catch (Exception e) {}
        return null;
    }
    @Override
    protected void onPostExecute(String json) {
        if (json instanceof String){
            Message message=new Message();
            message.obj=json;
            handler.sendMessage(message);
        }else{
            handler.sendEmptyMessage(2);
        }

    }
}
