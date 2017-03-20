package com.lhd.fm;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.baoyz.widget.PullRefreshLayout;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.NativeExpressAdView;
import com.lhd.db.DuLieu;
import com.lhd.tophaui.R;

import java.util.ArrayList;

import static com.lhd.duong.Conections.isOnline;


/**
 * Created by D on 12/19/2016.
 */

public abstract class Frame extends Fragment {
    protected RecyclerView recyclerView;
    protected TextView tVnull;
    protected ProgressBar progressBar;
    protected PullRefreshLayout pullRefresh;
    protected ArrayList<Object> objects;
    protected DuLieu dulieu;
    public void loadNativeExpressAt( NativeExpressAdView nativeExpressAdView) {
        if (isOnline(getActivity()))
        nativeExpressAdView.loadAd(new AdRequest.Builder().build());
    }
    public LayoutInflater getLayoutInflater() {
        return layoutInflater;
    }
    private LayoutInflater layoutInflater;
    protected abstract void startParser();
    public void cantLoadData() {
        showTextNull();
        tVnull.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isOnline(getContext())){
                    showProgress();
                    loadData();
                }else {
                    final Snackbar snackbar= Snackbar.make(recyclerView, "Vui lòng bật kết nối internet!", Snackbar.LENGTH_SHORT);

                    snackbar.setActionTextColor(getResources().getColor(R.color.colorPrimaryDark));
                    snackbar.setAction("Bật wifi", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
//                            WifiManager wifiManager = (WifiManager) getContext().getSystemService(Context.WIFI_SERVICE);
//                            wifiManager.setWifiEnabled(true);
                            showProgress();
                            snackbar.dismiss();
                           loadData();
                        }
                    });
                    snackbar.show();


                }
            }
        });
    }
    @SuppressLint("NewApi")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layoutInflater=inflater;
        View view=inflater.inflate(R.layout.layout_frame_fragment,container,false);
        initView(view);
        return view;
    }

    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        tVnull.setVisibility(View.GONE);
    }
    public void showTextNull() {
        pullRefresh.setRefreshing(false);
        progressBar.setVisibility(View.GONE);
        tVnull.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }
    public void loadData() {
        if (isOnline(getContext())){
            showProgress();
            startParser();
        }else cantLoadData();

    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pullRefresh.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });
    }

    public void initView(View view) {
        pullRefresh= (PullRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        dulieu=new DuLieu(getContext());
        progressBar= (ProgressBar) view.findViewById(R.id.pg_loading);
        tVnull= (TextView) view.findViewById(R.id.text_null);
        recyclerView= (RecyclerView) view.findViewById(R.id.recle_view);
        recyclerView.setBackgroundColor(getResources().getColor(R.color.bg_rcv));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        checkDatabase();
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }
    public abstract void setRecyclerView();
    public abstract void checkDatabase();
    public void showRecircleView() {
        pullRefresh.setRefreshing(false);
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        tVnull.setVisibility(View.GONE);
    }








}
