package com.lhd.server;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.lhd.activity.Main;
import com.lhd.db.DuLieu;
import com.lhd.duong.Conections;
import com.lhd.obj.ItemNotiDTTC;
import com.lhd.task.ParserNotiDTTC;
import com.lhd.tophaui.R;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Faker on 9/5/2016.
 */

public class MyService extends Service{
    public static final String TAB_POSITON = "tab_positon";
    public static final String KEY_TAB = "key tab";
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    private DuLieu sqLiteManager;
    private Log log;
    private String id;
    private NotificationCompat.Builder nBuilder;
    private  NotificationManager mNotifyMgr;
    private  PendingIntent resultPendingIntent;


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void showNoti(String title, String content, int index) {
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        long [] l={200,200,200,200};
        Intent resultIntent = new Intent(getApplicationContext(), Main.class);
        Bundle bundle=new Bundle();
        bundle.putInt(TAB_POSITON,index);
        resultIntent.putExtra(KEY_TAB,bundle);
        Random random=new Random();
        resultPendingIntent = PendingIntent.getActivity(getApplicationContext(),  random.nextInt(1000), resultIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        mNotifyMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nBuilder = new NotificationCompat.Builder(getApplicationContext())
                .setSmallIcon(R.drawable.logo_student)
                .setVibrate(l)
                .setSound(alarmSound).setStyle(new NotificationCompat.BigTextStyle().bigText(content)).addAction (android.R.drawable.btn_star,"Xem", resultPendingIntent)
                .setContentTitle(title).setDefaults(Notification.DEFAULT_ALL)
                .setContentText(content)
                .setContentIntent(resultPendingIntent)
                .setColor(getResources().getColor(R.color.colorPrimary))
                .setAutoCancel(true)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC);
        int mNotificationId = random.nextInt(1000);
            mNotifyMgr.notify(mNotificationId, nBuilder.build());
    }
    private Handler handlerNotiQLCL=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            ArrayList<ItemNotiDTTC> itemNotiDTTCsC;
            ArrayList<ItemNotiDTTC> itemNotiDTTCsM;
            try{
                itemNotiDTTCsM = (ArrayList<ItemNotiDTTC>) msg.obj;
                itemNotiDTTCsC=sqLiteManager.getNotiDTTC();
                if (itemNotiDTTCsM.size()>itemNotiDTTCsC.size()&&itemNotiDTTCsM.size()>0){
                    showNoti("Thông báo từ Top Công Nghiệp ","đã cập nhật "+(itemNotiDTTCsM.size()-itemNotiDTTCsC.size())+" thông báo mới",5);
                    sqLiteManager.deleteItemNotiDTTC();
                    for (ItemNotiDTTC itemNotiDTTC:itemNotiDTTCsM) {
                        sqLiteManager.insertItemNotiDTTC(itemNotiDTTC);
                    }
                }else if(itemNotiDTTCsM.size()>0){
                    for (int i = 0; i < itemNotiDTTCsM.size(); i++) {
                        boolean flag=true;
                        for (ItemNotiDTTC itemNotiDTTC:itemNotiDTTCsC) {
                            if (itemNotiDTTCsM.get(i).getTitle().equals(itemNotiDTTC.getTitle())){
                                flag=false;
                            }
                        }
                        if (flag) showNoti("Thông báo từ Top Công Nghiệp",itemNotiDTTCsM.get(i).getTitle(),4);
                    }
                    sqLiteManager.deleteItemNotiDTTC();
                    for (ItemNotiDTTC itemNotiDTTC:itemNotiDTTCsM) {
                        sqLiteManager.insertItemNotiDTTC(itemNotiDTTC);
                    }
                }
//                ParserKetQuaThiTheoMon ketQuaThiTheoMon=new ParserKetQuaThiTheoMon(handler);
//                ketQuaThiTheoMon.execute(id);
            }catch (Exception e){
            }
        }
    };
    BroadcastReceiver myBroadcastOnScrern = new BroadcastReceiver() {
        //When Event is published, onReceive method is called
        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
//            if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
//               if (MainActivity.wifiIsEnable(MyService.this)){
//                         startParser();
//               }
//            }else
            if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
//                Log.e("faker", " ACTION_SCREEN_OFF");
                startParser();
            }
        }
    };

    private void startParser() {
        if (!Conections.isOnline(MyService.this)&&!Conections.isWifiEnable(this))
            return;
//        ParserKetQuaHocTap ketQuaHocTapTheoMon=new ParserKetQuaHocTap(handler);
//        ketQuaHocTapTheoMon.execute(id);

        ParserNotiDTTC parserNotiDTTC=new ParserNotiDTTC(handlerNotiQLCL);
        parserNotiDTTC.execute();



    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        sqLiteManager =new DuLieu(this);
//        Log.e("faker", " onStartCommand");
        registerReceiver(myBroadcastOnScrern, new IntentFilter(Intent.ACTION_SCREEN_OFF));
//        registerReceiver(new MyReserver(), new IntentFilter(Intent.ACTION_SCREEN_ON));
        if (Conections.isWifiEnable(this)&&Conections.isOnline(MyService.this))
            startParser();
//        Toast.makeText(this,"Gà Công Nghiệp đang kiểm tra xem có gì hót ^.^",Toast.LENGTH_LONG).show();
        Intent resultIntent = new Intent(getApplicationContext(), Main.class);
        resultPendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        mNotifyMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.e("faker", " onDestroy");
        Intent intent=new Intent(this,MyService.class);
        startService(intent);
    }
}
