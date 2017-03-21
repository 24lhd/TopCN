package com.lhd.fm;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lhd.activity.Main;
import com.lhd.duong.Communication;
import com.lhd.duong.Conections;
import com.lhd.obj.SinhVien;
import com.lhd.task.ParserSinhVien;
import com.lhd.tophaui.R;

import static com.lhd.activity.Main.SINH_VIEN;

/**
 * Created by D on 17/03/2017.
 */

public class FrameCaNhan extends Fragment {
    private View rootView;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private TabLayout tabLayout;
    private ViewPager mViewPager;
    private TextView tvOff;
    private LinearLayout linearLayout;
    private SinhVien sinhVien;
    private Main main;
    private String msv;
    private AppBarLayout appBarLayout;
    private EditText etMSV;
    private ImageButton imageButton;

    public AppBarLayout getAppBarLayout() {
        return appBarLayout;
    }

    public TabLayout getTabLayout() {
        return tabLayout;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        sinhVien= (SinhVien) getArguments().getSerializable(SINH_VIEN);
        main=(Main)getActivity();
        rootView = inflater.inflate(R.layout.frame_sinh_vien_frament, container, false);
        initView();
        return rootView;
    }

    private void initView() {
         etMSV= (EditText) rootView.findViewById(R.id.tv_canhan_msv);
        etMSV.setTextColor(main.getTab_select_color());
        etMSV.setHintTextColor(main.getTab_select_color());
         imageButton= (ImageButton) rootView.findViewById(R.id.imb_canhan_msv);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputMSV=etMSV.getText().toString();
                if (inputMSV.length()<10||inputMSV==null) Communication.showToast(main,"Bạn hãy nhập đúng mã sinh viên");
                else setViewSinhVienByMSV(inputMSV);
            }
        });
         appBarLayout= (AppBarLayout) rootView.findViewById(R.id.app_bar_layout_fm_top_sv);
         appBarLayout= (AppBarLayout) rootView.findViewById(R.id.app_bar_layout_fm_top_sv);
        linearLayout = (LinearLayout) rootView.findViewById(R.id.layout_frame_top_fragment_sv);
        tabLayout = (TabLayout) rootView.findViewById(R.id.tab_layout_fm_top_sv);
        mViewPager = (ViewPager) rootView.findViewById(R.id.view_pager_frame_top_fragment_sv);
        tvOff = (TextView) rootView.findViewById(R.id.tv_frame_top_fragment_off_sv);
        mSectionsPagerAdapter = new SectionsPagerAdapter(main.getSupportFragmentManager());
        mViewPager.setAdapter(mSectionsPagerAdapter);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setTabTextColors(ColorStateList.valueOf(Color.WHITE));
        tabLayout.setSelectedTabIndicatorColor(Color.WHITE);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setBackgroundColor(main.getTab_select_color());
        appBarLayout.setBackgroundColor(main.getTab_select_color());
        tabLayout.setupWithViewPager(mViewPager);
        try {
            setViewOnline();
        } catch (Exception e) {
            setViewOffline();
            main.setFail(true);
        }
    }
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            try {
                if (msg.obj instanceof SinhVien) {
                    sinhVien= (SinhVien) msg.obj;
                    mSectionsPagerAdapter = new SectionsPagerAdapter(main.getSupportFragmentManager());
                    mViewPager.setAdapter(mSectionsPagerAdapter);
                    return;
                }else if (Conections.isOnline(getActivity())&& !(msg.obj instanceof SinhVien))Communication.showToast(main,"Không tìm thấy sinh viên đó");
                else if (!Conections.isOnline(getActivity()))Communication.showToast(main,"Không có kết nối Internet");

            } catch (NullPointerException e) {
                if (Conections.isOnline(getActivity()))
               setViewSinhVienByMSV(msv);
            }
        }
    };
    private void setViewSinhVienByMSV(String msv) {
        this.msv=msv;
        main.getAdsFull().showADSFull();
            ParserSinhVien parserSinhVien = new ParserSinhVien(handler, getActivity());
            parserSinhVien.execute(msv);
    }

    private void setViewOffline() {
        tvOff.setVisibility(View.VISIBLE);
        tvOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Conections.isOnline(main))
                    main.initViewIntro();
                else Communication.showToast(main, "Bạn chưa bật kết nối internet!");
            }
        });
        linearLayout.setVisibility(View.GONE);
    }

    private void setViewOnline() {
        setViewSinhVienByMSV(sinhVien.getMa());
        linearLayout.setVisibility(View.VISIBLE);
        tvOff.setVisibility(View.GONE);
        tabLayout.getTabAt(0).select();
        mViewPager.setCurrentItem(0);
    }

    public class SectionsPagerAdapter extends FragmentStatePagerAdapter {
        public SectionsPagerAdapter(android.support.v4.app.FragmentManager fm) {
            super(fm);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            Bundle bundle = new Bundle();

            if (position == 2) {
                TopCacLoai topCacLoaiFragment = new TopCacLoai();
                bundle.putString(Main.LINK_TOP, main.getConfig().getLinkTopLop(sinhVien.getLop(),sinhVien.getKhoa()));
                bundle.putString(Main.ID_TAB, sinhVien.getLop()+sinhVien.getKhoa());
                topCacLoaiFragment.setArguments(bundle);
                return topCacLoaiFragment;
            }
            if (position == 1) {
                KetQuaHocTapSinhVien ketQuaHocTapSinhVien = new KetQuaHocTapSinhVien();
                bundle.putSerializable(SINH_VIEN,sinhVien);
                ketQuaHocTapSinhVien.setArguments(bundle);
                return ketQuaHocTapSinhVien;
            }
            BieuDo bieuDo=new BieuDo();
            bundle.putSerializable(SINH_VIEN,sinhVien);
            bieuDo.setArguments(bundle);
            return bieuDo;

        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if (position == 0) {
                return "Biểu đồ kết quả của\n"+sinhVien.getTen();
            }else if (position == 1) {
                return "Kết quả học tập của \n"+sinhVien.getTen();
            } else if (position == 2) {
                return sinhVien.getLop()+"\n"+sinhVien.getKhoa();
            }
            return "Biểu đồ"+"\n"+"kết quả";
        }
    }
}
