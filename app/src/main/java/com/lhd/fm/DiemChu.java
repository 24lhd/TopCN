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

public class DiemChu extends Fragment {
    private String diemchu="<!DOCTYPE html> <html> <head>  " +
            "            <meta charset=utf-8><title></title> <style type=text/css>  " +
            "            *{ margin: auto; text-align: center; background: white;}  " +
            "            table{ width: 100%; } " +
            "            th { background-color: #42A5F5; color: white; padding: 5px;font-size: small;}  " +
            "            td{padding: 5px;background-color: #f2f2f2;color: red;} </style> </head> <body>  " +
            "            <table> <tr> <th>Điểm chữ </th> <th>Bắt đầu</th> <th>Kết thúc</th>" +
            " </tr> <tr> <td>A</td> <td>8.5</td> <td>10</td> </tr> <tr> <td>B+</td> <td>7.7</td> <td>8.4</td>" +
            " </tr> <tr> <td>B</td> <td>7.0</td> <td>7.6</td> </tr> <tr> <td>C+</td> <td>6.2</td>" +
            " <td>6.9</td> </tr> <tr> <td>C</td> <td>5.5</td> <td>6.1</td> </tr> <tr> <td>D+</td> " +
            "<td>4.7</td> <td>5.4</td> </tr> <tr> <td>D</td> <td>4.0</td> <td>4.6</td> </tr> <tr>" +
            " <td>F</td> <td>0</td> <td>3.9</td> </tr> </table></body> </html>";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        WebView webView=new WebView(getActivity());
        webView.loadDataWithBaseURL(null,diemchu, "text/html", "utf-8",null);
        return webView;
    }
}
