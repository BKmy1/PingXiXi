package com.example.pingxixi.ui.recyclerview;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.bumptech.glide.Glide;
import com.example.pingxixi.MyApplication;
import com.example.pingxixi.R;
import com.example.pingxixi.SwipeRecyclerView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RecyclerViewFragment extends Fragment {

    private SwipeRecyclerView recyclerView;
    private MyRecycleViewAdapter adapter;
    //
    private List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
    private List<HashMap<String, Object>> mData = new ArrayList<HashMap<String, Object>>();
    private int pageSize = 10;
    String url = new MyApplication().selectbypageurl;

    public static RecyclerViewFragment newInstance() {
        return new RecyclerViewFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recyclerview, container, false);
        recyclerView = (SwipeRecyclerView) view.findViewById(R.id.swipeRecyclerView);
        getActivity().setTitle("首页");
        //set color
        recyclerView.getSwipeRefreshLayout()
                .setColorSchemeColors(getResources().getColor(R.color.black));

        recyclerView.getRecyclerView().setLayoutManager(new GridLayoutManager(getActivity(), 1));
        //recyclerView.getRecyclerView().setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        adapter = new MyRecycleViewAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setOnLoadListener(new SwipeRecyclerView.OnLoadListener() {
            @Override
            public void onRefresh() {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        fetchRData();
                    }
                }, 1000);

            }

            @Override
            public void onLoadMore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        fetchMData();
                    }
                }, 1000);
            }
        });

        //设置自动下拉刷新，切记要在recyclerView.setOnLoadListener()之后调用
        //因为在没有设置监听接口的情况下，setRefreshing(true),调用不到OnLoadListener
        recyclerView.setRefreshing(true);
        return view;
        //其他设置
        //禁止下拉刷新
        // recyclerView.setRefreshEnable(false);
        //禁止加载更多
        //recyclerView.setLoadMoreEnable(false);
        //设置noMore
        // recyclerView.onNoMore("-- end --");
        //设置网络处理
        //recyclerView.onNetChange(true);
        //设置错误信息
        //recyclerView.onError("error");


    }

    public void fetchMData() {
        RequestParams params = new RequestParams(url);
        //get
        params.addQueryStringParameter("offset", String.valueOf(mData.size()));
        System.out.println(mData.size());
        System.out.println(String.valueOf(pageSize));
        params.addQueryStringParameter("pagesize", String.valueOf(pageSize));
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("RecycleViewFragment", result);
                list = JSON.parseObject(result,
                        new TypeReference<List<HashMap<String, Object>>>() {
                        });
                mData.clear();
                mData.addAll(list);
                recyclerView.complete();
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

    public void fetchRData() {
        RequestParams params = new RequestParams(url);
        //get
        params.addQueryStringParameter("offset", String.valueOf(0));
        params.addQueryStringParameter("pagesize", String.valueOf(pageSize));
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("RecycleViewFragment", result);
                list = JSON.parseObject(result,
                        new TypeReference<List<HashMap<String, Object>>>() {
                        });

                mData.clear();
                mData.addAll(list);
                recyclerView.complete();
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

    class MyRecycleViewAdapter extends RecyclerView.Adapter<MyRecycleViewAdapter.ViewHolder> {
        public class ViewHolder extends RecyclerView.ViewHolder {
            public ImageView img;
            public TextView description;
            public TextView goodname;
            public TextView publishtime;

            public ViewHolder(View convertView) {
                super(convertView);
                img = (ImageView) convertView.findViewById(R.id.img);
                description = (TextView) convertView.findViewById(R.id.description);
                goodname = (TextView) convertView.findViewById(R.id.goodname);
                publishtime = (TextView) convertView.findViewById(R.id.publishtime);
            }
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(getActivity()).inflate(R.layout.item, parent, false);

            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
            //picture
            Glide.with(getActivity()).load(mData.get(position).get("picture").toString()).placeholder(R.mipmap.ic_launcher).into(holder.img);
            holder.description.setText((String) mData.get(position).get("description"));
            holder.goodname.setText((String) mData.get(position).get("name"));
            holder.publishtime.setText((String) mData.get(position).get("publishtime"));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                  进行对商品的修改，删除操作
                    NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                    Bundle bundle = new Bundle();
                    bundle.putString("id", (String) mData.get(position).get("id"));
                    navController.navigate(R.id.detailFragment, bundle);
                }
            });
        }

        @Override
        public int getItemCount() {
            return mData == null ? 0 : mData.size();
        }
    }
}
