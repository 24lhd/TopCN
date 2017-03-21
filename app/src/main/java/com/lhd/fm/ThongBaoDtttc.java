package com.lhd.fm;

import android.os.Handler;
import android.os.Message;

import com.lhd.adaptor.ThongBaoDTTCAdaptor;
import com.lhd.obj.ItemNotiDTTC;
import com.lhd.task.ParserNotiDTTC;

import java.util.ArrayList;

/**
 * Created by D on 12/15/2016.
 */

public class ThongBaoDtttc extends Frame {
    private ArrayList<ItemNotiDTTC> itemNotiDTTCs;
    public void checkDatabase() {
        showProgress();
        itemNotiDTTCs=dulieu.getNotiDTTC();
        if (!itemNotiDTTCs.isEmpty()){
            setRecyclerView();
        }else loadData();
    }
    public void startParser() {
        ParserNotiDTTC parserNotiDTTC=new ParserNotiDTTC(handler);
        parserNotiDTTC.execute();
    }


    public void setRecyclerView() {
        objects=new ArrayList<>();
        showRecircleView();
        objects.addAll(itemNotiDTTCs);
        ThongBaoDTTCAdaptor adapterNoti=new ThongBaoDTTCAdaptor(objects,recyclerView,this,itemNotiDTTCs);
        recyclerView.setAdapter(adapterNoti);
    }
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            try{
                itemNotiDTTCs= (ArrayList<ItemNotiDTTC>) msg.obj;
                setRecyclerView();
                if (!itemNotiDTTCs.isEmpty()){
                    dulieu.deleteItemNotiDTTC();
                    for (ItemNotiDTTC itemNotiDTTC:itemNotiDTTCs)
                        dulieu.insertItemNotiDTTC(itemNotiDTTC);
                }
            }catch (NullPointerException e){
                startParser();
            }
        }
    };
}
