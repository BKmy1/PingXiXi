package com.example.pingxixi.ui.person;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.bumptech.glide.Glide;
import com.example.pingxixi.MyApplication;
import com.example.pingxixi.R;
import com.example.pingxixi.ui.comment.CommentFragment;

import org.w3c.dom.Text;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PersonFragment extends Fragment {
    View view;
    String username;
    private List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
    private List<HashMap<String, Object>> mData = new ArrayList<HashMap<String, Object>>();
    String url = new MyApplication().selectuser;
    TextView textView;
    ImageView imageView;
    TextView more;

    public PersonFragment() {}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        System.out.println("person正在执行");
        getActivity().setTitle("个人中心");
        view = inflater.inflate(R.layout.fragment_person, container, false);
        username = getActivity().getIntent().getStringExtra("username");
        System.out.println(username);
        imageView = view.findViewById(R.id.user_img);
        textView = view.findViewById(R.id.user_name);
        more = view.findViewById(R.id.more);
        getData();
        return view;
    }

    public void getData(){
        RequestParams params = new RequestParams(url);
        params.addQueryStringParameter("un",username);
        x.http().get(params, new Callback.CacheCallback<String>() {

            @Override
            public void onSuccess(String result) {
                Log.i("PersonFragment", result);
                list = JSON.parseObject(result,
                        new TypeReference<List<HashMap<String, Object>>>() {
                        });
                mData.clear();
                mData.addAll(list);
                System.out.println(mData.size());
                textView.setText((String)mData.get(0).get("name"));
                Glide.with(getActivity()).load(mData.get(0).get("image")).asBitmap().placeholder(R.mipmap.ic_launcher).
                        into(imageView);
                more.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                        Bundle bundle = new Bundle();
                        System.out.println(mData.get(0).get("id"));
                        bundle.putString("id", (String)mData.get(0).get("id"));
                        navController.navigate(R.id.detailPersonFragment);
                    }
                });
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }

            @Override
            public boolean onCache(String result) {
                return false;
            }
        });
    }
}