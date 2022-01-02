package com.example.pingxixi;

import android.app.Application;

import org.xutils.x;

public class MyApplication extends Application {
    public String selectallurl="http://39.108.61.227:80/json/listjson.php";
    public String selectbypageurl="http://39.108.61.227:80/json/listjsonpage.php";
    public String inserturl="http://39.108.61.227:80/json/insert.php";
    public String selectwsurl="http://39.108.61.227:80/json/selectws.php";
    public String selectbyidurl="http://39.108.61.227:80/json/select.php";
    public String deletebyidurl="http://39.108.61.227:80/json/delete.php";
    public String updatebyidurl="http://39.108.61.227:80/json/update.php";
    public String imagebaseurl="http://39.108.61.227:80/picture/";
    public String selectuser="http://39.108.61.227:80/json/su.php";
    public String sv="http://39.108.61.227:80/json/sv.php";
    public String sui = "http://39.108.61.227:80/json/sui.php";
    public String uu = "http://39.108.61.227:80/json/uu.php";

    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        //x.Ext.setDebug(BuildConfig.DEBUG); // 是否输出debug日志, 开启debug会影响性能.
    }
}