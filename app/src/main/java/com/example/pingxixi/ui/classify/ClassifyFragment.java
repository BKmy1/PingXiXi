package com.example.pingxixi.ui.classify;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pingxixi.R;
import com.example.pingxixi.ui.ComputerFragment;
import com.example.pingxixi.ui.FoodFragment;
import com.example.pingxixi.ui.FruitFragment;
import com.example.pingxixi.ui.PhoneFragment;
import com.example.pingxixi.ui.SupermarketFragment;
import com.example.pingxixi.ui.computer.ComputerCopyFragment;
import com.example.pingxixi.ui.food.FoodCopyFragment;
import com.example.pingxixi.ui.fruit.FruitCopyFragment;
import com.example.pingxixi.ui.phone.PhoneCopyFragment;
import com.example.pingxixi.ui.recommend.RecommendFragment;
import com.example.pingxixi.ui.supermarket.SuperMarketCopyFragment;
import com.example.pingxixi.viewPageFragment;

import java.util.ArrayList;

import q.rorbin.verticaltablayout.VerticalTabLayout;
import cn.youngkaaa.yviewpager.YViewPager;

public class ClassifyFragment extends Fragment {
    private View view;
    private VerticalTabLayout mTab;
    private YViewPager mVp;
    private ArrayList<String> list;
    private ArrayList<Fragment> fragments;

    public ClassifyFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        System.out.println("classify正在执行！！");
        getActivity().setTitle("分类");
        view = inflater.inflate(R.layout.fragment_classify, container, false);
        mTab = (VerticalTabLayout) view.findViewById(R.id.tab);
        mVp = (YViewPager) view.findViewById(R.id.vp);

        list = new ArrayList<>();
        list.add("推荐");
        list.add("食品");
        list.add("手机");
        list.add("电脑");
        list.add("百货");
        list.add("水果");
        fragments = new ArrayList<Fragment>();
        fragments.add(new RecommendFragment());
        fragments.add(new FoodCopyFragment());
        fragments.add(new PhoneCopyFragment());
        fragments.add(new ComputerCopyFragment());
        fragments.add(new SuperMarketCopyFragment());
        fragments.add(new FruitCopyFragment());
        viewPageFragment.FragmentViewPagerAdapter myadapter = new viewPageFragment.FragmentViewPagerAdapter(getChildFragmentManager(), fragments, list);
        mVp.setAdapter(myadapter);
        mTab.setupWithViewPager(mVp);
        return view;
    }

}
