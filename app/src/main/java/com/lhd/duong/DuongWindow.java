package com.lhd.duong;

import android.app.Activity;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by D on 07/03/2017.
 */

public class DuongWindow {
    public static  void setFullSceen(Activity context) {
        context.requestWindowFeature(Window.FEATURE_NO_TITLE);
        context.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

    }
}
