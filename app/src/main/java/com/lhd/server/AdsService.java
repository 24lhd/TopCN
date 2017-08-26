package com.lhd.server;

import android.app.ActivityManager;
import android.app.Service;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.Random;

/**
 * Created by d on 8/27/2017.
 */

public class AdsService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_SCREEN_OFF);
        onOffScreen = new OnOffScreen();
        registerReceiver(onOffScreen, intentFilter);
        Log.e("vnews", " onStartCommand");
        return START_STICKY;
    }

    OnOffScreen onOffScreen;

    @Override
    public void onDestroy() {
        unregisterReceiver(onOffScreen);
    }

    public static boolean isRunning(Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager
                .getRunningServices(Integer.MAX_VALUE)) {
            if (AdsService.class.getName().equals(
                    service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    class OnOffScreen extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
                Log.e("onReceive", " ACTION_SCREEN_OFF");
                Log.e("onReceive", " ACTION_SCREEN_OFF isRunning(context) " + isRunning(context));
                boolean isRun = new Random().nextInt(10) == 2;
                Log.e("onReceive", " ACTION_SCREEN_OFF isRun" + isRun);
                if (isRun) {
                    Uri uri = Uri.parse("market://details?id=com.duongstudio.videotintuc");
                    Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                    goToMarket.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    try {
                        context.startActivity(goToMarket);
                    } catch (ActivityNotFoundException e) {
                        context.startActivity(new Intent(Intent.ACTION_VIEW,
                                Uri.parse("http://play.google.com/store/apps/details?id=com.duongstudio.videotintuc")));
                    }
                }
            }
        }
    }
}
