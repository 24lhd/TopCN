package com.lhd.duong;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.File;

/**
 * Created by D on 15/01/2017.
 */

public class ChucNangPhu {
    private AlertDialog.Builder builder;

    /**
     * trả về string json từ 1 đoói tượng java truyền vào
     * @param o
     */
    public static String getJSONByObj(Object o) {
        Gson gson = new Gson();
        return gson.toJson(o);
    }

    /**
     * Phương thức tải file .apk từ 1 url về máy và cài đặt khi đã tải xong
     * @param activity đối tượng gọi cần 1 Activity
     * @param appName Tên file .apk được tải về
     * @param urlApp link url chứa file apk
     * @param content nội dung của trạng thái update
     * @param title       title cua thong báo
     */
    public void downloadAndInstallAppLication(final Activity activity, String appName, String urlApp, String content, String title) {
        String destination = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/";
        final String fileName = appName;
        destination += fileName;
        final Uri uri = Uri.parse("file://" + destination);
        File file = new File(destination);
        if (file.exists())
            file.delete();
        String url = urlApp;
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setDescription(content);
        request.setTitle(title);
        request.setDestinationUri(uri);
        DownloadManager manager = (DownloadManager) activity.getSystemService(Context.DOWNLOAD_SERVICE);
        long downloadId = manager.enqueue(request);
        BroadcastReceiver onComplete = new BroadcastReceiver() {
            public void onReceive(Context ctxt, Intent intent) {
                Intent install = new Intent(Intent.ACTION_VIEW);
                install.setDataAndType(Uri.fromFile(new File(Environment.getExternalStorageDirectory() + "/download/" + fileName)), "application/vnd.android.package-archive");
                install.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                activity.startActivity(install);
                activity.unregisterReceiver(this);
                activity.finish();
            }
        };
        activity.registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    public void sendEmail(String mail,String title,String content,Context context) {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL  , new String[]{mail});
        i.putExtra(Intent.EXTRA_SUBJECT, title);
        i.putExtra(Intent.EXTRA_TEXT   , content);
        try {
            context.startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (ActivityNotFoundException ex) {
            Toast.makeText(context, "Bạn chưa cài ứng dụng Emails.", Toast.LENGTH_SHORT).show();
        }
    }
    private static boolean exit=false;
    public static void finishDoubleCick(Activity context) {
        if (exit==false){
            Toast.makeText(context,"Nhấn back 1 phát nữa để thoát. ",Toast.LENGTH_SHORT).show();
        }
        exit=!exit;
        (new Handler()).postDelayed(new Runnable() {
            @Override
            public void run() {
                exit=!exit;
            }
        },500);
        if (!exit){
            context.finish();
        }
    }
    public static void showLog(String log) {
        Log.e("Lê Hồng Dương",log);
    }
    public static String edata(String str) {
        String en="";
        char [] arrstr= str.toCharArray();
        for (int i = 0; i < str.length(); i++) en=en+Integer.toHexString((int) arrstr[i])+"ofe3";
        return en;
    }
    public static String ddata(String str) {
        String de="";
        String [] arrstr= str.split("ofe3");
        for (int i = 0; i < arrstr.length; i++) de=de+(char) Integer.parseInt(arrstr[i], 16);
        return de;
    }
    public static String d(String str) {
        String de="";
        String [] arrstr= str.split("ofe3");
        for (int i = 0; i < arrstr.length; i++) de=de+(char) Integer.parseInt(arrstr[i], 16);
        return de;
    }

    /**
     * chia sẻ app qua tên app và tên gói
     * @param context
     * @param appName
     * @param pagkegName
     */
    public void chiaSeApp(Context context,String appName,String pagkegName) {
        try {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_SUBJECT, "Link tải phần mềm "+appName);
            String sAux = "Ứng dụng "+appName+" ";
            sAux = sAux + "https://play.google.com/store/apps/details?id="+pagkegName;
            i.putExtra(Intent.EXTRA_TEXT, sAux);
            context.startActivity(Intent.createChooser(i, "Chia sẻ bằng"));
        } catch(Exception e) {}
    }

    /**
     * đánh giá app thông qua gói
     * @param context
     * @param pagkegName
     */
    public void danhGiaApp(Context context,String pagkegName) {
        Uri uri = Uri.parse("market://details?id=" + pagkegName);
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            context.startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + pagkegName)));
        }
    }

    /**
     * xem thêm các ứng dụng khác
     */
    public void moreApp(Context context) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("market://search?q=pub:Duong Le Hong"));
            context.startActivity(intent);
        }catch (Exception e){
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("http://play.google.com/store/search?q=pub:Duong Le Hong"));
            context.startActivity(intent);
        }
    }

    /**
     * gửi ý kiến phản hồi đế gmail
     * @param context activity
     * @param email địa chỉ mail
     * @param content nội dung
     * @param appName tên app
     */
    public void yKenPhanHoi(final Activity context, final String email, String content, final String appName) {
        builder = new AlertDialog.Builder(context);
        final EditText editText=new EditText(context);
        builder.setTitle(content);
        editText.setHint("Ý kiến đóng góp");
        builder.setPositiveButton("Gửi", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL  , new String[]{email});
                i.putExtra(Intent.EXTRA_SUBJECT, "Phản hồi "+appName);
                i.putExtra(Intent.EXTRA_TEXT   , editText.getText().toString());
                try {
                    context.startActivity(Intent.createChooser(i, "Send mail..."));
                } catch (ActivityNotFoundException ex) {
                    Toast.makeText(context, "Bạn chưa cài ứng dụng Emails.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.setView(editText);
        builder.show();
    }
}
