package com.example.pingxixi.ui.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.pingxixi.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class HomeFragment extends Fragment {
    View view;
    public HomeFragment() {}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        BottomNavigationView bnv =view.findViewById(R.id.nav_bottom);
//        bnv.setElevation(0f);     设置底部导航图不遮挡内容
        bnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                NavController navController = Navigation.findNavController(getActivity(),R.id.nav_host_fragment2);
                if (id == R.id.nav_home1){
                    navController.navigate(R.id.navigation_viewpager);
                }
                if(id == R.id.nav_living1){
                    navController.navigate(R.id.navigation_living);
                }
                if(id == R.id.nav_classify1){
                    navController.navigate(R.id.navigation_classify);
                }
                if(id == R.id.nav_comment1){
                    navController.navigate(R.id.navigation_comment);
                }
                if(id == R.id.nav_person1){
                    navController.navigate(R.id.navigation_person);
                }
                return true;
            }
        });

        return view;
    }
}