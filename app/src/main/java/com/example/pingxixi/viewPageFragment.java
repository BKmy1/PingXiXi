package com.example.pingxixi;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.viewpager.widget.ViewPager;


import com.example.pingxixi.R;
import com.example.pingxixi.ui.ComputerFragment;
import com.example.pingxixi.ui.FoodFragment;
import com.example.pingxixi.ui.FruitFragment;
import com.example.pingxixi.ui.PhoneFragment;
import com.example.pingxixi.ui.SupermarketFragment;
import com.example.pingxixi.ui.recyclerview.RecyclerViewFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class viewPageFragment extends Fragment {
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private List<String> titles;
    private List<Fragment> fragments;
    public static viewPageFragment  newInstance() {
        return new viewPageFragment ();
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // TODO: Use the ViewModel

        View view=inflater.inflate(R.layout.fragment_view_page, container, false);
        tabLayout = (TabLayout) view.findViewById(R.id.tabs2);
        viewPager = (ViewPager) view.findViewById(R.id.viewpage);
        //Fragment
        fragments = new ArrayList<>();
        RecyclerViewFragment recyclerViewFragment = new RecyclerViewFragment();
        fragments.add(recyclerViewFragment);
        ComputerFragment computerFragment=new  ComputerFragment ();
        fragments.add(computerFragment);
        FoodFragment foodFragment=new  FoodFragment();
        fragments.add(foodFragment);
        FruitFragment fruitFragment=new  FruitFragment();
        fragments.add(fruitFragment);
        PhoneFragment phoneFragment=new PhoneFragment();
        fragments.add(phoneFragment);
        SupermarketFragment supermarketFragment=new  SupermarketFragment();
        fragments.add(supermarketFragment);
        //Titles
        titles = new ArrayList<>();
        titles.add("推荐");
        titles.add("电脑");
        titles.add("食物");
        titles.add("水果");
        titles.add("手机");
        titles.add("超市");
        //
        FragmentViewPagerAdapter adapter = new FragmentViewPagerAdapter(getChildFragmentManager(), fragments, titles);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        return  view;
    }

    public static class FragmentViewPagerAdapter extends FragmentPagerAdapter
    {

        private List<Fragment> fragments;
        private List<String> titles;

        public FragmentViewPagerAdapter(FragmentManager manager, List<Fragment> fragments, List<String> titles) {
            super(manager);
            this.fragments = fragments;
            this.titles = titles;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
    }

}


