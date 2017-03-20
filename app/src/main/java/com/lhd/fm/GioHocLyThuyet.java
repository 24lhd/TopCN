package com.lhd.fm;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

/**
 * Created by D on 16/03/2017.
 */

public class GioHocLyThuyet extends Fragment {
    private String UIFromHTML="<!DOCTYPEhtml><html> " +
            "            <head> " +
            "            <meta charset=utf-8/> " +
            "            <style type=text/css media=screen> " +
            "            *{ " +
            "            margin:0;padding: 0; " +
            "            text-align: center; " +
            "            } " +
            "            table{ " +
            "            width:100%;border:2px solid white;text-align:center;border-collapse:collapse;background-color:#E8F5E9; " +
            "            } " +
            "            th { background-color: #42A5F5; color: white; padding: 5px;font-size: small;} " +
            "            td{padding: 5px;background-color: #f2f2f2;color: red;} " +
            "            </style> " +
            "            </head> " +
            "            <body> " +
            "            <table border=1px><tr><th>Tiết</th><th>Giờ Học</th></tr><tr><td>1</td><td>7h00 - 7h45</td> " +
            "            </tr><tr><td>2</td><td>7h50 - 8h35</td></tr><tr><td>3</td><td>8h40 - 9h35</td></tr> " +
            "            <tr><td>4</td><td>9h35 - 10h20</td></tr><tr><td>5</td><td>10h35 - 11h10</td></tr><tr><td>6</td><td>11h15 - 12h00</td></tr><tr><td>7</td><td>12h30 - 13h15</td></tr><tr><td>8</td><td>13h30 - 14h05</td></tr><tr><td>9</td><td>14h10 - 14h55</td></tr><tr><td>10</td><td>15h05 - 15h50</td></tr><tr><td>11</td><td>15h55 - 16h40</td></tr><tr><td>12</td><td>16h45 - 17h30</td></tr><tr><td>13</td><td>18h00 - 18h45</td></tr><tr><td>14</td><td>18h45 - 19h30</td></tr><tr><td>15</td><td>19h45 - 20h30</td></tr><tr><td>16</td><td>20h30 - 21h15</td></tr> " +
            "            </table> " +
            "            </body> " +
            "            </html>";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        WebView webView=new WebView(getActivity());
        webView.loadDataWithBaseURL(null,UIFromHTML, "text/html", "utf-8",null);
        return webView;
    }
}
