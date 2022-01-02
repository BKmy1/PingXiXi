package com.example.pingxixi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.material.tabs.TabLayout;


import java.util.ArrayList;

//入口页面

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    AutoCompleteTextView txtIsbn;
    String[] isbns={"1111111", "2222222", "ccccaaaa","ddddaaaa"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setTitle("首页");
//        initSearch();
    }

    /**
     * 初始化顶部搜索框
     */
//    public void initSearch(){
//        //搜索框内容
//        txtIsbn = findViewById(R.id.isbn);
//        //搜索按钮
//        Button search = findViewById(R.id.search);
//        ArrayAdapter<String> isbnAdapter=new ArrayAdapter<String>(this,R.layout.list_item,isbns);
//        txtIsbn.setAdapter(isbnAdapter);
//
//    }

    @Override
    public void onClick(View view) {

    }
}