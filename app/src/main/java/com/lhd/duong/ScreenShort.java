package com.lhd.duong;

import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

/**
 * Created by D on 15/01/2017.
 */

public class ScreenShort {
    public  File takeSreenShortByView(View viewInput, Context context) {
        Date now = new Date();
        ByteArrayOutputStream bytearrayoutputstream;
        Bitmap bitmap;
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);
        File file;
        bytearrayoutputstream = new ByteArrayOutputStream();
        FileOutputStream fileoutputstream;
        View view=viewInput;
        view.setDrawingCacheEnabled(true);
        bitmap = view.getDrawingCache();
        bitmap.compress(Bitmap.CompressFormat.PNG, 60, bytearrayoutputstream);
        file = new File( Environment.getExternalStorageDirectory() + "/SreenShort/"+now.toString().trim()+".png");
        file.getParentFile().mkdirs();
        try{
            file.createNewFile();
            fileoutputstream = new FileOutputStream(file);
            fileoutputstream.write(bytearrayoutputstream.toByteArray());
            fileoutputstream.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
        Toast.makeText(context,""+file.getPath(),Toast.LENGTH_SHORT).show();
        addImageToGallery(file.getPath(),context);
        return file;
    }
    public static void addImageToGallery(final String filePath, final Context context) {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        values.put(MediaStore.MediaColumns.DATA, filePath);
        context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
    }
    public void shareImageByFile(File file,Context context){
        Uri uri = Uri.fromFile(file);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_SUBJECT, "");
        intent.putExtra(Intent.EXTRA_TEXT, "");
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            context.startActivity(Intent.createChooser(intent, "Chia sẻ ảnh chụp"));
//            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(context, "Không tìm thấy ứng dụng để mở file", Toast.LENGTH_SHORT).show();
        }
    }
    public static void shareText(Context context, String sms ,String title) {
        Toast.makeText(context,sms,Toast.LENGTH_SHORT).show();
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, title);
        sharingIntent.putExtra(Intent.EXTRA_TEXT, sms);
        context.startActivity(Intent.createChooser(sharingIntent, title));
    }
}
