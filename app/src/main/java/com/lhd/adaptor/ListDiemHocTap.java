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
import com.lhd.obj.DiemHocTap;
import com.lhd.tophaui.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by D on 17/03/2017.
 */

public class ListDiemHocTap extends AdaptorResycleViewADS {
    private final ArrayList<DiemHocTap> diemHocTaps;
    private Main activity;
    private List<Object> listObject;
    class NativeExpressAdViewHolder extends ViewHolderB {
        NativeExpressAdView nativeExpressAdView;
        public NativeExpressAdViewHolder(View view) {
            super(view);
            this.nativeExpressAdView= (NativeExpressAdView) view.findViewById(R.id.ads_navite_to);
        }
    }
    class DiemHocTapHover extends ViewHolderA{
          TextView id_stt_diem;
          TextView id_ten_mon;
          TextView id_so_tin;
          TextView id_diem_chu;
          TextView id_diem_so;
          TextView id_ma_mon;
        public DiemHocTapHover(View itemView) {
            super(itemView);
            this.id_stt_diem= (TextView) itemView.findViewById(R.id.id_stt_diem);
            this.id_ten_mon= (TextView) itemView.findViewById(R.id.id_ten_mon);
            this.id_so_tin= (TextView) itemView.findViewById(R.id.id_so_tin);
            this.id_diem_chu= (TextView) itemView.findViewById(R.id.id_diem_chu);
            this.id_diem_so= (TextView) itemView.findViewById(R.id.id_diem_so);
            this.id_ma_mon= (TextView) itemView.findViewById(R.id.id_ma_mon);
        }
    }
    public ListDiemHocTap(RecyclerView recyclerView,
                        List<Object> listObject,
                        Object doiTuongCanThem,
                        int viTriThem, Main activity, ArrayList<DiemHocTap> diemHocTaps) {
        super(recyclerView, listObject, doiTuongCanThem, viTriThem,activity);
        this.listObject = listObject;
        this.activity = activity;
        this.diemHocTaps = diemHocTaps;
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
                View menuItemLayouthView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_diem, parent, false);
                return new DiemHocTapHover(menuItemLayouthView);
        }
    }
    @Override
    public void setViewHolderB(ViewHolderB viewHolder, int position) {
        NativeExpressAdViewHolder nativeExpressAdViewHolder= (NativeExpressAdViewHolder) viewHolder;
        nativeExpressAdViewHolder.nativeExpressAdView.loadAd(new AdRequest.Builder().build());
    }
    @Override
    public void setViewHolderA( ViewHolderA viewHolder, int position) {
        DiemHocTapHover diemHocTapHover= (DiemHocTapHover) viewHolder;
        DiemHocTap diemHocTap= (DiemHocTap) getListObject().get(position);
        diemHocTapHover.id_stt_diem.setText(""+(diemHocTaps.indexOf(diemHocTap)+1));
        diemHocTapHover.id_ten_mon.setText(diemHocTap.getTenMon());
        diemHocTapHover.id_diem_chu.setText(diemHocTap.getThangChu());
        diemHocTapHover.id_diem_so.setText(diemHocTap.getThang10());
        diemHocTapHover.id_ma_mon.setText("["+diemHocTap.getMaMon()+"]");
        diemHocTapHover.id_so_tin.setText(diemHocTap.getSoTin());


        diemHocTapHover.id_ten_mon.setTextColor(activity.getBg_app());
        diemHocTapHover.id_so_tin.setTextColor(activity.getTab_select_color());
        diemHocTapHover.id_diem_so.setTextColor(activity.getColorApp());



        diemHocTapHover.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater=LayoutInflater.from(activity);

            }
        });
//
    }
}
