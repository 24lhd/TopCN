package com.lhd.adaptor;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.NativeExpressAdView;
import com.lhd.activity.Main;
import com.lhd.duong.adaptor.AdaptorResycleViewADS;
import com.lhd.obj.SinhVien;
import com.lhd.tophaui.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by D on 17/03/2017.
 */

public class ListSinhVien extends AdaptorResycleViewADS {
    private final ArrayList<SinhVien> sinhViens;
    private  Main activity;
    private List<Object> listObject;
    class NativeExpressAdViewHolder extends ViewHolderB {
        NativeExpressAdView nativeExpressAdView;
        public NativeExpressAdViewHolder(View view) {
            super(view);
            this.nativeExpressAdView= (NativeExpressAdView) view.findViewById(R.id.ads_navite_to);
        }
    }
    class SinhVienHover extends ViewHolderA{
            TextView id_stt;
            TextView id_ten_sv;
            TextView id_tl;
            TextView id_khoa;
            TextView id_nam;
            TextView id_msv;
            TextView id_lop;
        public SinhVienHover(View itemView) {
            super(itemView);
            this.id_stt= (TextView) itemView.findViewById(R.id.id_stt);
            this.id_ten_sv= (TextView) itemView.findViewById(R.id.id_ten_sv);
            this.id_tl= (TextView) itemView.findViewById(R.id.id_tl);
            this.id_khoa= (TextView) itemView.findViewById(R.id.id_khoa);
            this.id_nam= (TextView) itemView.findViewById(R.id.id_nam);
            this.id_msv= (TextView) itemView.findViewById(R.id.id_msv);
            this.id_lop= (TextView) itemView.findViewById(R.id.id_lop);
        }
    }
    public ListSinhVien(RecyclerView recyclerView,
                        List<Object> listObject,
                        Object doiTuongCanThem,
                        int viTriThem, Main activity, ArrayList<SinhVien> sinhViens) {
        super(recyclerView, listObject, doiTuongCanThem, viTriThem,activity);
        this.listObject = listObject;
        this.activity = activity;
        this.sinhViens = sinhViens;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case B:
                View nativeExpressLayoutView = LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.native_express_ad_to, parent, false);
                return new NativeExpressAdViewHolder(nativeExpressLayoutView);
            default:
            case A:
                View menuItemLayouthView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_sinh_vien, parent, false);
                return new SinhVienHover(menuItemLayouthView);
        }
    }
    @Override
    public void setViewHolderB(ViewHolderB viewHolder, int position) {
        NativeExpressAdViewHolder nativeExpressAdViewHolder= (NativeExpressAdViewHolder) viewHolder;
        nativeExpressAdViewHolder.nativeExpressAdView.loadAd(new AdRequest.Builder().build());
    }
    @Override
    public void setViewHolderA( ViewHolderA viewHolder, int position) {
        SinhVienHover sinhVienHover= (SinhVienHover) viewHolder;
        final SinhVien sinhVien= (SinhVien) getListObject().get(position);
        sinhVienHover.id_stt.setText(""+(sinhViens.indexOf(sinhVien)+1));
        sinhVienHover.id_ten_sv.setText(sinhVien.getTen());

        sinhVienHover.id_lop.setText("Lớp "+sinhVien.getLop());
        sinhVienHover.id_msv.setText("["+sinhVien.getMa()+"]");
        sinhVienHover.id_khoa.setText(sinhVien.getKhoa());
        sinhVienHover.id_tl.setText(sinhVien.getTl());
        sinhVienHover.id_nam.setText(sinhVien.getNam());

        sinhVienHover.id_ten_sv.setTextColor(activity.getBg_app());
        sinhVienHover.id_lop.setTextColor(activity.getColorApp());
        sinhVienHover.id_nam.setTextColor(activity.getTab_select_color());
        sinhVienHover.id_khoa.setTextColor(activity.getTab_select_color());

        sinhVienHover.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.getAdsFull().showADSFull();
                activity.setViewSinhVien(sinhVien);

            }
        });

    }




}