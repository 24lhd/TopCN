package com.lhd.fm;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lhd.activity.Main;
import com.lhd.duong.Communication;
import com.lhd.duong.Conections;
import com.lhd.obj.He;
import com.lhd.obj.Khoa;
import com.lhd.obj.Lop;
import com.lhd.obj.Nganh;
import com.lhd.tophaui.R;

import java.util.ArrayList;


/**
 * Created by D on 15/03/2017.
 */

public class FrameThemChucNang extends Fragment {
    private LayoutInflater inflater;
    private View rootView;
    private Main main;
    private ArrayList<He> hes;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private TabLayout tabLayout;
    private ViewPager mViewPager;
    private String content;
    public static final String KEY_CONTENT="key content";
    private ArrayList<Nganh> nganhs;
    private ArrayList<Khoa> khoas;
    private TextView tvOff;
    private LinearLayout linearLayout;
    private ArrayList<Lop> lops;

    public TabLayout getTabLayout() {
        return tabLayout;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.inflater=inflater;
        main = (Main) getActivity();
        rootView = inflater.inflate(R.layout.frame_top_frament, container, false);
        initView();
        return rootView;
    }
    private void initView() {
        hes= main.getHes();
        nganhs= main.getNganhs();
        khoas= main.getKhoas();
        lops= main.getLops();
        linearLayout= (LinearLayout) rootView.findViewById(R.id.layout_frame_top_fragment);
        tabLayout= (TabLayout) rootView.findViewById(R.id.tab_layout_fm_top);
        mViewPager = (ViewPager) rootView.findViewById(R.id.view_pager_frame_top_fragment);
        tvOff= (TextView) rootView.findViewById(R.id.tv_frame_top_fragment_off);
        mSectionsPagerAdapter = new SectionsPagerAdapter(main.getSupportFragmentManager());
        mViewPager.setAdapter(mSectionsPagerAdapter);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setTabTextColors(ColorStateList.valueOf(Color.WHITE));
        tabLayout.setSelectedTabIndicatorColor(Color.WHITE);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setBackgroundColor(main.getTab_select_color());
        tabLayout.setupWithViewPager(mViewPager);
        try {
            setViewOnline();
        }catch (Exception e){
            setViewOffline();
            main.setFail(true);
        }


    }

    private void setViewOffline() {
        tvOff.setVisibility(View.VISIBLE);
        tvOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Conections.isOnline(main))
                    main.initViewIntro();
//                setViewOnline();
                else Communication.showToast(main,"Bạn chưa bật kết nối internet!");
            }
        });
        linearLayout.setVisibility(View.GONE);
    }

    private void setViewOnline() {
        linearLayout.setVisibility(View.VISIBLE);
        tvOff.setVisibility(View.GONE);
        tabLayout.setBackgroundColor(main.getTab_select_color());
        tabLayout.getTabAt(0).select();
        mViewPager.setCurrentItem(0);
    }

    public class SectionsPagerAdapter extends FragmentStatePagerAdapter {
        public SectionsPagerAdapter(android.support.v4.app.FragmentManager fm) {
            super(fm);
        }
        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            switch (position){
                case 1:
                    return new GioHocLyThuyet();
                case 2:
                    return new DiemChu();
               default:
                    return new ThongBaoDtttc();
            }
        }
        @Override
        public int getCount() {
                return 3;
        }
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position){

                case 1:
                    return "Giờ học lý thuyết";
                case 2:
                    return "Bảng điểm chữ";
                default:
                    return "Thông báo từ DTTC";
            }
        }
    }
}
