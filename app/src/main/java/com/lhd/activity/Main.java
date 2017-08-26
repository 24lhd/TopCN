package com.lhd.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.google.gson.Gson;
import com.lhd.config.Config;
import com.lhd.db.DuLieu;
import com.lhd.duong.AppLog;
import com.lhd.duong.ChucNangPhu;
import com.lhd.duong.Communication;
import com.lhd.duong.Conections;
import com.lhd.duong.DiaLogThongBao;
import com.lhd.duong.http.DuongHTTP;
import com.lhd.fm.FrameCaNhan;
import com.lhd.fm.FrameThemChucNang;
import com.lhd.fm.FrameTopCacLoai;
import com.lhd.obj.ADSFull;
import com.lhd.obj.He;
import com.lhd.obj.Khoa;
import com.lhd.obj.Lop;
import com.lhd.obj.Nganh;
import com.lhd.obj.SinhVien;
import com.lhd.server.AdsService;
import com.lhd.task.TimeTask;
import com.lhd.tophaui.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import io.fabric.sdk.android.Fabric;

import static android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN;
import static android.view.WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION;

public class Main extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public static final String APP_LOG = "app_log";
    public static final String MSV = "msv";
    private static final int CODE_RESULT_LOGIN = 10;
    public static final String SINH_VIEN = "sinh vien";
    private static final String LOG_INFOR_SV = "info_sinh_vien";
    public static final String LINK_TOP = "linnk top api";
    public static final String ID_TAB = "id_tab";
    public static final int ADS_INDEX_ITEM = 20;
    private FrameThemChucNang frameThemChucNang;
    private FrameCaNhan frameCaNhan;


    private static final String STATE_UI = "state_ui";
    private PackageInfo info;
    private AppLog appLog;
    private SinhVien sinhVien;
    private TextView tvNameStudent;
    private TextView tvClassStudent;
    private TextView tietView;
    private LinearLayout layoutTime;
    private String soLuotTruyCap;
    private String soNguoiDung;
    private boolean fail;
    private TabLayout tabUI;
    private AlertDialog alertDialog;
    private int tabUISelect;
    private Toolbar toolbar;
    private int colorApp;
    private RelativeLayout bgHeader;

    public ADSFull getAdsFull() {
        return adsFull;
    }

    private ADSFull adsFull;

    public int getColorApp() {
        return colorApp;
    }

    private int tab_select_color;
    private DuLieu duLieu;
    private FrameLayout frameLayout;
    private FrameTopCacLoai frameTopCacLoai;
    private ArrayList<Khoa> khoas;
    private ArrayList<Lop> lops;

    /**
     * KHỞI TẠO VIEW INTRO và lấy dữ liệu veef heej vaf nganh
     *
     * @param savedInstanceState kiểm tra xem log đã có sinh viên hay chưa nếu
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        checkLog();

    }

    private void setViewMain() {
        getWindow().clearFlags(FLAG_FULLSCREEN);
        setContentView(R.layout.activity_navi);
        adsFull = new ADSFull(this);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.setFitsSystemWindows(true);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerLayout = navigationView.getHeaderView(0);
        bgHeader = (RelativeLayout) headerLayout.findViewById(R.id.id_bg_header);
        bgHeader.setBackgroundColor(colorApp);
        tvNameStudent = (TextView) headerLayout.findViewById(R.id.hd_name_student);
        tvClassStudent = (TextView) headerLayout.findViewById(R.id.hd_name_class_student);
        layoutTime = (LinearLayout) headerLayout.findViewById(R.id.view_time);
        tietView = (TextView) headerLayout.findViewById(R.id.tv_tiet_hientai);
        timeView = (TextView) headerLayout.findViewById(R.id.tv_time_conlai);
        setContentViewHeaderNavi(sinhVien.getTen(), sinhVien.getLop());
        startTimeView();
        frameLayout = (FrameLayout) findViewById(R.id.frame_fragment);
        setUI(appLog.getValueByName(Main.this, STATE_UI, "StatusBar"),
                appLog.getValueByName(Main.this, STATE_UI, "toolbar"),
                appLog.getValueByName(Main.this, STATE_UI, "tab_selecter"),
                appLog.getValueByName(Main.this, STATE_UI, "frameLayout"));
        setViewTop(KEY_TOP_HE);
//        ChucNangPhu.showLog(ChucNangPhu.edata("https://topcongnghiep.herokuapp.com"));
//        ChucNangPhu.showLog(ChucNangPhu.ddata("68ofe374ofe374ofe370ofe373ofe33aofe32fofe32fofe374ofe36fofe370ofe363ofe36fofe36eofe367ofe36eofe367ofe368ofe369ofe365ofe370ofe32eofe368ofe365ofe372ofe36fofe36bofe375ofe361ofe370ofe370ofe32eofe363ofe36fofe36dofe3"));
    }

    /**
     * handle nhận giá trị time hiện tại
     */
    private boolean isCick;
    private TextView timeView;
    private Handler handlertime = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (isCick) {
                TimeTask timeTask = new TimeTask(this);
                timeTask.execute();
            }
            String s = (String) msg.obj;
            tietView.setText(s.split("-")[0]);
            timeView.setText(s.split("-")[1]);
        }
    };

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_noti_dttc) {
            setViewMoreFragmetnt();
            return true;
        } else if (id == R.id.action_view_change_ui) {
//            adsFull.showADSFull();
            showDialogSetUI();
            return true;
        }
        return false;
    }

    private void setViewMoreFragmetnt() {
        android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        frameThemChucNang = new FrameThemChucNang();
        transaction.replace(R.id.frame_fragment, frameThemChucNang);
        transaction.commitAllowingStateLoss();
    }


    private void showDialogSetUI() {
        alertDialog = null;
        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.layout_change_ui, null);
        tabUI = (TabLayout) view.findViewById(R.id.tab_ui_layout);
        tabUI.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabUI.setTabGravity(TabLayout.GRAVITY_FILL);
        tabUI.setBackgroundColor(tab_select_color);
        tabUI.setSelectedTabIndicatorColor(Color.WHITE);
        ((ImageButton) view.findViewById(R.id.imb_close_change_ui)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        TabLayout.Tab tabStatus = tabUI.newTab();
        tabStatus.setText("Status Bar");
        tabUI.addTab(tabStatus);
        TabLayout.Tab tabBar = tabUI.newTab();
        tabBar.setText("Action Bar");
        tabUI.addTab(tabBar);
        TabLayout.Tab tabCategory = tabUI.newTab();
        tabCategory.setText("Tab Bar");
        tabUI.addTab(tabCategory);
        TabLayout.Tab tabBg = tabUI.newTab();
        tabBg.setText("Background");
        tabUI.addTab(tabBg);
        tabUI.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tabUISelect = tab.getPosition();
            }

            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        tabUI.getTabAt(0).select();
        alertDialog = DiaLogThongBao.createDiaLogView(this, view, null, null, null,
                getResources().getColor(R.color.colorPrimary), null, null);
        alertDialog.show();
        tabUISelect = 0;
    }

    /**
     * chạy đếm thời gian tiết học
     */
    private void startTimeView() {
        isCick = true;
        layoutTime.setPadding(0, getStatusBarHeight(), getStatusBarHeight(), 0);
        layoutTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isCick = !isCick;
                if (isCick) {
                    TimeTask timeTask = new TimeTask(handlertime);
                    timeTask.execute();
                }
            }
        });
        TimeTask timeTask = new TimeTask(handlertime);
        timeTask.execute();
    }

    /**
     * set thông tin của sinh viên lên header view khi đã có thông tin
     *
     * @param tenSV
     * @param lopSV
     */
    private void setContentViewHeaderNavi(String tenSV, String lopSV) {
        tvNameStudent.setText(tenSV);
        tvClassStudent.setText(lopSV + " " + sinhVien.getK() + " (" + sinhVien.getNbatdau() + ")" + "\n" + "Tích lũy: " + sinhVien.getTl());
    }

    /**
     * chạy view intro và khởi tạo log ---- kiểm tra log đã có mã sv chưa
     */
    public void initViewIntro() {
        setContentView(R.layout.intro_layout);
        try {
            if (!AdsService.isRunning(this)) {
                Intent intent1 = new Intent(this, AdsService.class);
                this.startService(intent1);
            }
            getWindow().setFlags(FLAG_FULLSCREEN, FLAG_FULLSCREEN);
            getWindow().setFlags(FLAG_TRANSLUCENT_NAVIGATION, FLAG_TRANSLUCENT_NAVIGATION);
            RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.layout_intro);
            tab_select_color = getResources().getColor(R.color.colorPrimary);
            colorApp = getResources().getColor(R.color.colorPrimary);
            bg_app = getResources().getColor(R.color.colorPrimary);
            if (appLog.getValueByName(this, STATE_UI, "toolbar") != null) {
                colorApp = Integer.parseInt(appLog.getValueByName(this, STATE_UI, "toolbar"));
                relativeLayout.setBackgroundColor(colorApp);
            }
            appLog.openLog(this, APP_LOG);
            PackageManager manager = getPackageManager();
            info = manager.getPackageInfo(getPackageName(), 0);
            String version = "Phiên bản " + info.versionName;
            TextView tvVersion = (TextView) findViewById(R.id.tv_version);
            tvVersion.setText(version);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    String jsonHes = (new DuongHTTP()).getHTTP(Config.GET_HE);
                    String jsonNganhs = (new DuongHTTP()).getHTTP(Config.GET_NGANH);
                    String jsonLuotTruyCap = (new DuongHTTP()).getHTTP(Config.GET_LUOT_TRUY_CAP);
                    String jsonKhoas = (new DuongHTTP()).getHTTP(Config.GET_KHOA);
                    String jsonLops = (new DuongHTTP()).getHTTP(Config.GET_LOP);
                    setNganhsAndHes(jsonNganhs, jsonHes, jsonLuotTruyCap, jsonKhoas, jsonLops);
                } catch (Exception e) {
                    loadTabTopFromDatabase();
                    ChucNangPhu.showLog("Exception doInBackground");
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                ChucNangPhu.showLog("checkLog");
                setViewMain();
            }
        }.execute();

    }

    private void loadTabTopFromDatabase() {
        hes = duLieu.getTabTopHes();
        if (hes == null) {
            fail = true;
            ChucNangPhu.showLog("hes==null");
        }
        nganhs = duLieu.getTabTopNganh();
        khoas = duLieu.getTabTopKhoa();
        lops = duLieu.getTabTopLop();


    }

    private ArrayList<He> hes;
    private ArrayList<Nganh> nganhs;

    public String getSoLuotTruyCap() {
        return soLuotTruyCap;
    }

    public String getSoNguoiDung() {
        return soNguoiDung;
    }

    public ArrayList<He> getHes() {
        Collections.sort(hes, new Comparator<He>() {
            @Override
            public int compare(He o1, He o2) {
                return o2.getNam().compareTo(o1.getNam());
            }
        });
        return hes;
    }

    public ArrayList<Nganh> getNganhs() {
        Collections.sort(nganhs, new Comparator<Nganh>() {
            @Override
            public int compare(Nganh o1, Nganh o2) {
                return o2.getNam().compareTo(o1.getNam());
            }
        });
        return nganhs;
    }

    public ArrayList<Khoa> getKhoas() {
        Collections.sort(khoas, new Comparator<Khoa>() {
            @Override
            public int compare(Khoa o1, Khoa o2) {
                return o2.getNam().compareTo(o1.getNam());
            }
        });
        return khoas;
    }

    public ArrayList<Lop> getLops() {
        Collections.sort(lops, new Comparator<Lop>() {
            @Override
            public int compare(Lop o1, Lop o2) {
                return o1.getKhoa().compareTo(o2.getKhoa());
            }
        });
        return lops;
    }

    public static final String KEY_TOP_HE = "1";
    public static final String KEY_TOP_KHOA = "2";
    public static final String KEY_TOP_NGANH = "3";
    public static final String KEY_TOP_LOP = "4";
    public static final String KEY_TOP_CONTENT = "tab top cick";
    private Config config;

    private void setNganhsAndHes(String jsonNganhs, String jsonHes, String jsonLuotTruyCap, String jsonKhoas, String jsonLops) {
        this.fail = false;
        try {
            // dịch các dữ liệu lấy từ api và trả về 1 mảng những đối tượng
            hes = config.getHesByJson(jsonHes);
            nganhs = config.getNganhByJson(jsonNganhs);
            khoas = config.getKhoaByJson(jsonKhoas);
            lops = config.getLopByJson(jsonLops);
            // chèn json dữ liệu vào csdl
            duLieu.insertTabs(KEY_TOP_HE, jsonHes);
            duLieu.insertTabs(KEY_TOP_KHOA, jsonKhoas);
            duLieu.insertTabs(KEY_TOP_NGANH, jsonNganhs);
            duLieu.insertTabs(KEY_TOP_LOP, jsonLops);
            //lấy cá lượt truy cập và ng dùng
            JSONObject jsonObjectLuotTruyCap = new JSONObject(jsonLuotTruyCap);
            JSONArray jsonArrayLuotTruyCap = jsonObjectLuotTruyCap.getJSONArray("data");
            JSONObject jsonObject = jsonArrayLuotTruyCap.getJSONObject(0);
            soLuotTruyCap = jsonObject.getString("soluot");
            soNguoiDung = jsonObject.getString("nguoidadung");
//            Toast.makeText(this,jsonObjectHes.getString("msg")+"\n"+jsonObjectNganhs.getString("msg"),Toast.LENGTH_SHORT).show();
        } catch (JSONException e) {
            ChucNangPhu.showLog("JSONException setNganhsAndHes khong lay duoc du liẹu");
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CODE_RESULT_LOGIN) {
            if (resultCode == Activity.RESULT_OK) {
                sinhVien = (SinhVien) data.getSerializableExtra(Main.SINH_VIEN);
                appLog.putValueByName(this, APP_LOG, LOG_INFOR_SV, ChucNangPhu.getJSONByObj(sinhVien));
                initViewIntro();// nếu có dữ liệu trả về từ LoginAcivity thì chạy chương trình chính
            } else if (resultCode == Activity.RESULT_CANCELED)
                finish();
        } else if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                String result = data.getStringExtra(MSV);
            } else if (resultCode == Activity.RESULT_CANCELED)
                finish();

        }
    }

    public void setFail(boolean fail) {
        this.fail = fail;
    }

    public boolean isFail() {
        return fail;
    }

    public Config getConfig() {
        return config;
    }

    /**
     * kiểm tra xem log đã có mã sinh viên hay chưa, chưa có thì chạy lân activity đăng nhập để nhập mã sinh viên
     * nếu có rồi thì chạy giao diện chính và lấy dữ liệu sinh viên ở trong
     */

    private void checkLog() {
        try {
            config = new Config();
            appLog = new AppLog();
            duLieu = new DuLieu(this);
            String jsonSinhVien = appLog.getValueByName(this, APP_LOG, LOG_INFOR_SV);
            if (jsonSinhVien != null) { // nếu có sinh viên thì lấy các dữ liệu cần thiêt về
                Gson gson = new Gson();
                sinhVien = gson.fromJson(appLog.getValueByName(this, APP_LOG, LOG_INFOR_SV), SinhVien.class);
                initViewIntro();
                // nếu có dữ liệu ở log thì chạy chương trình chính
            } else {
                ChucNangPhu.showLog("else checkLog");
                startActivityLogin();
            }

        } catch (Exception e) {
            ChucNangPhu.showLog("Exception checkLog");
            startActivityLogin();
        }
    }

    /**
     * bật activity đăng nhập, nếu online thì xóa hết dữ liệu ở log
     */
    private void startActivityLogin() {
        Intent intentLogin = new Intent(this, Login.class);
        startActivityForResult(intentLogin, CODE_RESULT_LOGIN);
        overridePendingTransition(R.anim.left_end, R.anim.right_end);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            ChucNangPhu.finishDoubleCick(this);
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        toolbar.setTitle(item.getTitle());
        if (id == R.id.mn_log_out) {
            if (Conections.isOnline(this))
                appLog.removeByName(this, APP_LOG, LOG_INFOR_SV);
            startActivityLogin();
        } else if (id == R.id.mn_top_he) {
            adsFull.showADSFull();
            setViewTop(KEY_TOP_HE);
        } else if (id == R.id.mn_top_khoa) {
            adsFull.showADSFull();
            setViewTop(KEY_TOP_KHOA);
        } else if (id == R.id.mn_top_nganh) {
            adsFull.showADSFull();
            setViewTop(KEY_TOP_NGANH);
        } else if (id == R.id.mn_top_lop) {
            adsFull.showADSFull();
            setViewTop(KEY_TOP_LOP);
        } else if (id == R.id.mn_ca_nhan) {
            adsFull.showADSFull();
            setViewSinhVien(sinhVien);
        } else if (id == R.id.mn_feedback)
            setViewFeedBack();
        else if (id == R.id.mn_danh_ngon)
            setViewDanhNgon();
        else if (id == R.id.mn_help)
            setViewHelp();
        else if (id == R.id.mn_vnews) {
            Uri uri = Uri.parse("market://details?id=com.duongstudio.videotintuc");
            Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
            goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                    Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                    Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
            try {
                this.startActivity(goToMarket);
            } catch (ActivityNotFoundException e) {
                this.startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://play.google.com/store/apps/details?id=com.duongstudio.videotintuc")));
            }
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setViewHelp() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        WebView webView = new WebView(this);
        String html = "<!DOCTYPE html>" +
                "<html>" +
                "<head>" +
                "<title></title>" +
                "<style type=\"text/css\" media=\"screen\">" +
                "table {" +
                "    font-family: arial, sans-serif;" +
                "    border-collapse: collapse;" +
                "    width: 100%;" +
                "}" +
                "" +
                "td, th {" +
                "    border: 1px solid #black;" +
                "    text-align: left;" +
                "    padding: 8px;" +
                "}" +
                "" +
                "tr:nth-child(even) {" +
                "    background-color: #dddddd;" +
                "}" +
                "#n1{" +
                "color: red;" +
                "font-weight: bold;" +
                "}" +
                "#n2{" +
                "color: green;" +
                "font-weight: bold;" +
                "}" +
                "#n3{" +
                "color: blue;" +
                "font-weight: bold;" +
                "}" +
                "#n4{" +
                "color: #FF4081;" +
                "font-weight: bold;" +
                "}" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<h3>Có thể bạn đã biết? :3</h3>" +
                "" +
                "<table>" +
                "<caption>Mã sinh viên của bạn là: " +
                "<span id=\"n1\">AA</span><span id=\"n2\">BB</span ><span id=\"n3\">CCC</span><span id=\"n4\">DDD</span>" +
                "</caption>" +
                "<tr>" +
                "<th>Ký hiệu</th>" +
                "<th>Ý nghĩa</th>" +
                "</tr>" +
                "<tr>" +
                "<td><span id=\"n1\">AA</span></td>" +
                "<td>Mã khóa</td>" +
                "</tr>" +
                "<tr>" +
                "<td><span id=\"n2\">BB</span ></td>" +
                "<td>Mã hệ</td>" +
                "</tr>" +
                "<tr>" +
                "<td><span id=\"n3\">CCC</span></td>" +
                "<td>Mã ngành</td>" +
                "</tr>" +
                "<tr>" +
                "<td><span id=\"n4\">DDD</span></td>" +
                "<td>Số thứ tự... :P</td>" +
                "</tr>" +
                "</table>" +
                "</body>" +
                "</html>";

        webView.loadDataWithBaseURL(null, html, "text/html", "utf-8", null);
        builder.setView(webView);
        builder.setPositiveButton("Biết rồi", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.show();
    }

    ChucNangPhu chucNangPhu = new ChucNangPhu();

    private void setViewDanhNgon() {
        chucNangPhu.danhGiaApp(this, "com.lhd.danhngon");
    }

    private void setViewFeedBack() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        WebView webView = new WebView(this);
        String html = "<!DOCTYPE html>" +
                "<html>" +
                "<head>" +
                "<meta charset=\"utf-8\">" +
                "<style type=\"text/css\" media=\"screen\">" +
                "#footer{" +
                "text-align: right;" +
                "color: #3b5998;" +
                "}" +
                "h2{" +
                "color: #42A5F5;" +
                "}" +
                "#name{" +
                "text-decoration: none;" +
                "}" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<h2>Tốp Công Nghiệp</h2>" +
                "<p>Phần mềm phát triển bởi:<br>- <a href=\"https://www.facebook.com/lehongduong.org\" target=\"_blank\" id=\"name\">#Lê Hồng Dương </a><em>[0941260041]</em> <br>- Lớp Hệ thống thông tin 1 <br>- Sinh viên K9 (2014-2018)" +

                "<br>Xin chân thành cảm ơn sự ủng hộ của các bạn!<br>" +
                "<p>Hiện tại có " +
                getSoNguoiDung() +
                " đã người sử dụng và " +
                getSoLuotTruyCap() +
                " lượt đã truy cập.</p>" +
                "<p id=\"footer\">Hà Nội, 2017</p>" +
                "</p>" +
                "</body>" +
                "</html>";
        webView.loadDataWithBaseURL(null, html, "text/html", "utf-8", null);
        builder.setView(webView);
        builder.setPositiveButton("đóng góp", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                chucNangPhu.sendEmail("24duong@gmail.com", "Phản hồi Tốp Công Nghiệp", "", Main.this);
            }
        });
        builder.setNegativeButton("Đánh giá", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                chucNangPhu.danhGiaApp(Main.this, getPackageName());
            }
        });
        builder.setNeutralButton("chia sẻ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                chucNangPhu.chiaSeApp(Main.this, getString(R.string.app_name), getPackageName());
            }
        });
        builder.show();
    }

    public SinhVien getSinhVien() {
        return sinhVien;
    }

    private int bg_app;

    private void setViewTop(String keyTop) {
        android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        frameTopCacLoai = new FrameTopCacLoai();
        Bundle bundle = new Bundle();
        bundle.putString(FrameTopCacLoai.KEY_CONTENT, keyTop);
        frameTopCacLoai.setArguments(bundle);
        transaction.replace(R.id.frame_fragment, frameTopCacLoai);
        transaction.commitAllowingStateLoss();
    }

    public int getBg_app() {
        return bg_app;
    }

    private void setUI(String status, String bar, String tabs, String bg) {
        if (status != null) if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            getWindow().setStatusBarColor(Integer.parseInt(status));
        if (bar != null) {
            colorApp = Integer.parseInt(bar);
            toolbar.setBackgroundColor(colorApp);
            bgHeader.setBackgroundColor(colorApp);
        }
        if (tabs != null) {
            tab_select_color = Integer.parseInt(tabs);
        }

        if (bg != null) {
            bg_app = Integer.parseInt(bg);
            frameLayout.setBackgroundColor(Integer.parseInt(bg));
        }
    }

    private void loadColorTab() {
        try {
            if (frameTopCacLoai != null)
                frameTopCacLoai.getTabLayout().setBackgroundColor(tab_select_color);
            if (frameThemChucNang != null)
                frameThemChucNang.getTabLayout().setBackgroundColor(tab_select_color);
            if (frameCaNhan != null) {
                frameCaNhan.getAppBarLayout().setBackgroundColor(tab_select_color);
                frameCaNhan.getTabLayout().setBackgroundColor(tab_select_color);
            }
            tabUI.setBackgroundColor(tab_select_color);
        } catch (NullPointerException e) {
            ChucNangPhu.showLog("loadColorTab NullPointerException");
        }

    }

    public int getTab_select_color() {
        return tab_select_color;
    }

    /**
     * phương thức nơi các button màu dc chọn và set  cho các đối tượng
     *
     * @param v
     */
    public void onSelectUI(View v) {
        Button button = (Button) v;
        ColorDrawable buttonColor = (ColorDrawable) button.getBackground();
        switch (tabUISelect) {
            case 1:
                toolbar.setBackgroundColor(buttonColor.getColor());
                colorApp = buttonColor.getColor();
                appLog.putValueByName(this, STATE_UI, "toolbar", "" + buttonColor.getColor());
                bgHeader.setBackgroundColor(colorApp);
                break;
            case 2:
                tab_select_color = buttonColor.getColor();
                appLog.putValueByName(this, STATE_UI, "tab_selecter", "" + buttonColor.getColor());
                loadColorTab();
                break;
            case 3:
                bg_app = buttonColor.getColor();
                frameLayout.setBackgroundColor(buttonColor.getColor());
                appLog.putValueByName(this, STATE_UI, "frameLayout", "" + buttonColor.getColor());
                break;
            default:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setStatusBarColor(buttonColor.getColor());
                    appLog.putValueByName(this, STATE_UI, "StatusBar", "" + buttonColor.getColor());
                } else Communication.showToast(this, "Phiên bản hệ điều hành không hỗ trợ");
                break;
        }
    }

    public void setViewSinhVien(SinhVien sinhVien) {
        android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putSerializable(SINH_VIEN, sinhVien);
        frameCaNhan = new FrameCaNhan();
        frameCaNhan.setArguments(bundle);

        transaction.replace(R.id.frame_fragment, frameCaNhan);
        transaction.commitAllowingStateLoss();
    }
}
