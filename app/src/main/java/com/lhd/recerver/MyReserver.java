package com.lhd.recerver;

import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.lhd.server.MyService;

import java.util.Random;


/**
 * Created by Faker on 9/5/2016.
 */
public class MyReserver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String s = intent.getAction();
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();

        if (activeNetInfo != null) {
            Intent my = new Intent(context, MyService.class);
            boolean b = activeNetInfo.isConnectedOrConnecting();
            if (b) {
                Random random = new Random();
//                if (random.nextInt(5) == 5) {
                Uri uri = Uri.parse("market://details?id=com.duongstudio.videotintuc");
                Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                        Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                        Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                try {
                    context.startActivity(goToMarket);
                } catch (ActivityNotFoundException e) {
                    context.startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://play.google.com/store/apps/details?id=com.duongstudio.videotintuc")));
                }
//                }
                context.startService(my);
            }
        }
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            Intent intent1 = new Intent(context, MyService.class);
            context.startService(intent1);
            Toast.makeText(context, "Top Công Nghiệp đang kiểm tra xem có gì hót ^.^", Toast.LENGTH_LONG).show();
        }
        if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
            Log.e("faker", "ACTION_SCREEN_ON MyReserver");
            Intent intent1 = new Intent(context, MyService.class);
            context.startService(intent1);
        }

    }

    private void rate() {

    }
}
