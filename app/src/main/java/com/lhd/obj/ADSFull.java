package com.lhd.obj;

import android.content.Context;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.lhd.tophaui.R;

/**
 * Created by D on 19/03/2017.
 */

public class ADSFull {
    public ADSFull(Context context) {
        mInterstitialAd = new InterstitialAd(context);
        mInterstitialAd.setAdUnitId(context.getString(R.string.ads_full));
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();
//                showADSFull();
            }
        });
        requestNewInterstitial();
    }
    private InterstitialAd mInterstitialAd;;
    public  void showADSFull() {
        if (mInterstitialAd.isLoaded()) mInterstitialAd.show();
    }
    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder().build();
        mInterstitialAd.loadAd(adRequest);
    }

}
