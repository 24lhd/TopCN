package com.lhd.duong;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;

/**
 * Created by D on 15/01/2017.
 */

public class DiaLogThongBao {
    private ProgressDialog progressDialog;
    public void hideDialogLoad() {
        progressDialog.dismiss();
    }
    public void showDialogLoad(Context context,String msg) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(msg);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }
    public AlertDialog createDiaLogThongBao(Context context, String title, String msg
            , String nameBtnYes, String nameBtnNo, int colorBtn, View.OnClickListener yesListener){
        AlertDialog.Builder turnOnLoactionDialog=new AlertDialog.Builder(context);
        turnOnLoactionDialog.setTitle(title);
        turnOnLoactionDialog.setMessage(msg);
        turnOnLoactionDialog.setPositiveButton(nameBtnYes,null);
        AlertDialog alertDialog=turnOnLoactionDialog.create();
        Button yes=alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        yes.setTextColor(colorBtn);
        yes.setOnClickListener(yesListener);
        return alertDialog;
    }
    public AlertDialog createDiaLogLuaChon(Context context,String title,String msg
            ,String nameBtnYes,String nameBtnNo,int colorBtn,View.OnClickListener yesListener
            ,View.OnClickListener noListener){
        AlertDialog.Builder turnOnLoactionDialog=new AlertDialog.Builder(context);
        turnOnLoactionDialog.setTitle(title);
        turnOnLoactionDialog.setMessage(msg);
        turnOnLoactionDialog.setPositiveButton(nameBtnYes,null);
        turnOnLoactionDialog.setNeutralButton(nameBtnNo,null);
        AlertDialog alertDialog=turnOnLoactionDialog.create();
        Button yes=alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        Button no=alertDialog.getButton(DialogInterface.BUTTON_NEUTRAL);
        yes.setTextColor(colorBtn);
        no.setTextColor(colorBtn);
        yes.setOnClickListener(yesListener);
        no.setOnClickListener(noListener);
        return alertDialog;
    }
    public static AlertDialog createDiaLogView(Context context, View view, String title
            , String nameBtnYes, String nameBtnNo, int colorBtn, View.OnClickListener yesListener
            , View.OnClickListener noListener){
        AlertDialog.Builder turnOnLoactionDialog=new AlertDialog.Builder(context);
        turnOnLoactionDialog.setTitle(title);
        turnOnLoactionDialog.setPositiveButton(nameBtnYes,null);
        turnOnLoactionDialog.setNeutralButton(nameBtnNo,null);
        turnOnLoactionDialog.setView(view);
        AlertDialog alertDialog=turnOnLoactionDialog.create();
        Button yes=alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        Button no=alertDialog.getButton(DialogInterface.BUTTON_NEUTRAL);
//        yes.setTextColor(colorBtn);
//        no.setTextColor(colorBtn);
//        yes.setOnClickListener(yesListener);
//        no.setOnClickListener(noListener);
        return alertDialog;
    }
    public static AlertDialog createDiaLogCustemView(Context context, View view, String title
            , String nameBtnYes, String nameBtnNo,String nameBtnGim, int colorBtn, View.OnClickListener yesListener
            , View.OnClickListener noListener, View.OnClickListener gimListener){
        AlertDialog.Builder turnOnLoactionDialog=new AlertDialog.Builder(context);
        turnOnLoactionDialog.setTitle(title);
        turnOnLoactionDialog.setPositiveButton(nameBtnYes,null);
        turnOnLoactionDialog.setNeutralButton(nameBtnNo,null);
        turnOnLoactionDialog.setNegativeButton(nameBtnGim,null);
        turnOnLoactionDialog.setView(view);
        AlertDialog alertDialog=turnOnLoactionDialog.create();
//        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        alertDialog.show();
        Button yes=alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        Button no=alertDialog.getButton(DialogInterface.BUTTON_NEUTRAL);
        Button gim=alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        yes.setTextColor(colorBtn);
        gim.setTextColor(colorBtn);
        no.setTextColor(colorBtn);
        yes.setOnClickListener(yesListener);
        no.setOnClickListener(noListener);
        gim.setOnClickListener(noListener);
        return alertDialog;
    }
    public AlertDialog createDiaLogView(Context context,View view,String title,String msg
            ,String nameBtnYes,String nameBtnNo,int colorBtn,View.OnClickListener yesListener){
        AlertDialog.Builder turnOnLoactionDialog=new AlertDialog.Builder(context);
        turnOnLoactionDialog.setTitle(title);
        turnOnLoactionDialog.setMessage(msg);
        turnOnLoactionDialog.setPositiveButton(nameBtnYes,null);
        turnOnLoactionDialog.setNeutralButton(nameBtnNo,null);
        AlertDialog alertDialog=turnOnLoactionDialog.create();

        Button yes=alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        yes.setTextColor(colorBtn);
        yes.setOnClickListener(yesListener);
        alertDialog.setView(view);
        return alertDialog;
    }
    public AlertDialog createDiaLogCustemView(Context context,View view ,
                                              String nameBtnYes,String nameBtnNo,
                                              int colorBtn,View.OnClickListener yesListener,
                                              View.OnClickListener noListener){
        AlertDialog.Builder turnOnLoactionDialog=new AlertDialog.Builder(context);
        turnOnLoactionDialog.setPositiveButton(nameBtnYes,null);
        turnOnLoactionDialog.setNeutralButton(nameBtnNo,null);
        AlertDialog alertDialog=turnOnLoactionDialog.create();

        Button yes=alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        yes.setTextColor(colorBtn);
        yes.setOnClickListener(yesListener);

        Button no=alertDialog.getButton(DialogInterface.BUTTON_NEUTRAL);
        yes.setTextColor(colorBtn);
        no.setOnClickListener(noListener);

        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        alertDialog.setView(view);
        return alertDialog;
    }

    public AlertDialog createDiaLogCustemView(Context context,
                                              View view ,
                                              String nameBtnYes,
                                              String nameBtnNo,
                                              int colorBtn,
                                              View.OnClickListener yesListener){
        AlertDialog.Builder turnOnLoactionDialog=new AlertDialog.Builder(context);
        turnOnLoactionDialog.setPositiveButton(nameBtnYes,null);
        turnOnLoactionDialog.setNeutralButton(nameBtnNo,null);
        AlertDialog alertDialog=turnOnLoactionDialog.create();

        Button yes=alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        yes.setTextColor(colorBtn);
        yes.setOnClickListener(yesListener);

        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        alertDialog.setView(view);
        return alertDialog;
    }

    public AlertDialog createDiaLogCustemView(Context context,View view){
        AlertDialog.Builder turnOnLoactionDialog=new AlertDialog.Builder(context);
        AlertDialog alertDialog=turnOnLoactionDialog.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        alertDialog.setView(view);
        return alertDialog;
    }
}
