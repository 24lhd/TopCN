package com.lhd.duong;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

/**
 * Created by D on 15/01/2017.
 */

public class Communication {
    public static void showToast(Context context,String sms) {
        Toast.makeText(context,sms,Toast.LENGTH_SHORT).show();
    }
    public static void showToastCenter(Context context, String sms) {
        Toast toast = Toast.makeText(context,sms, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
    public void showSnack(View view, String sms, String nameAction, int colorAction, View.OnClickListener onClickListener) {
        Snackbar snackbar=Snackbar.make(view,sms,Snackbar.LENGTH_SHORT);
        snackbar.setAction(nameAction,onClickListener);
        snackbar.setActionTextColor(colorAction);
        snackbar.show();
    }

}
