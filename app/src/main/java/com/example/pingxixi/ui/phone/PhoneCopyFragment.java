package com.example.pingxixi.ui.phone;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.bumptech.glide.Glide;
import com.example.pingxixi.GlideImageLoader;
import com.example.pingxixi.MyApplication;
import com.example.pingxixi.R;
import com.example.pingxixi.ui.FoodFragment;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PhoneCopyFragment extends Fragment {

    private List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
    private List<HashMap<String, Object>> mData = new ArrayList<HashMap<String, Object>>();
    private RecyclerView recyclerView;
    private MyRecyclerViewAdapter adapter;
    String url = new MyApplication().selectallurl;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_phone_copy, container, false);
        List list = new ArrayList();
        list.add("http://39.108.61.227/picture/2.jpg");
        list.add("http://39.108.61.227/picture/3.jpg");
        list.add("http://39.108.61.227/picture/4.jpg");
        list.add("http://39.108.61.227/picture/5.jpg");
        list.add("http://39.108.61.227/picture/6.jpg");
        list.add("http://39.108.61.227/picture/7.jpg");
        list.add("http://39.108.61.227/picture/8.jpg");
        list.add("http://39.108.61.227/picture/9.jpg");
        list.add("http://39.108.61.227/picture/10.jpg");

        Banner banner = (Banner)view.findViewById(R.id.phone_copy_banner);
        //?????????????????????
        banner.setImageLoader(new GlideImageLoader());
        //??????????????????
        banner.setImages(list);
        //banner?????????????????????????????????????????????
        banner.start();

        //??????????????????
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                Toast.makeText(getContext(), "????????????"+((int)position+1)+"???", Toast.LENGTH_SHORT).show();
            }
        });
        //        ??????????????????getData()??????????????????????????????
        getData();
        System.out.println("?????????getData()??????");
        recyclerView = view.findViewById(R.id.phone_copy_recy);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        adapter = new MyRecyclerViewAdapter();
        recyclerView.setAdapter(adapter);
        return view;
    }
    public void getData() {
        RequestParams params = new RequestParams(url);
        //get
        System.out.println(mData.size());
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("PhoneCopyFragment", result);
                list = JSON.parseObject(result,
                        new TypeReference<List<HashMap<String, Object>>>() {
                        });
                mData.clear();
                mData.addAll(list);
                adapter.notifyDataSetChanged();
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
        });

    }

    public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {
        public class ViewHolder extends RecyclerView.ViewHolder {
            public ImageView imageView;
            public TextView title;

            public ViewHolder(View itemView) {
                super(itemView);
                imageView = (ImageView) itemView.findViewById(R.id.iv_image);
                title = (TextView) itemView.findViewById(R.id.tv_title);
            }
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.goods_list, parent, false);
            final ViewHolder holder = new ViewHolder(view);
            return holder;
        }

        //???????????????????????????
        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Glide.with(getActivity()).load(mData.get(position).get("picture").toString()).placeholder(R.mipmap.ic_launcher).into(holder.imageView);
            holder.title.setText((String) mData.get(position).get("description"));
            System.out.println(mData.size());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //?????????????????????
                }
            });
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }

    }
}