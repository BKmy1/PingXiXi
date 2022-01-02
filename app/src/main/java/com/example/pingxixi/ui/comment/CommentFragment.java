package com.example.pingxixi.ui.comment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.bumptech.glide.Glide;
import com.example.pingxixi.MyApplication;
import com.example.pingxixi.R;
import com.example.pingxixi.ui.supermarket.SuperMarketCopyFragment;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CommentFragment extends Fragment {
    View view;
    private List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
    private List<HashMap<String, Object>> mData = new ArrayList<HashMap<String, Object>>();
    private RecyclerView recyclerView;
    private MyRecyclerViewAdapter adapter;
    String url = new MyApplication().selectwsurl;

    public CommentFragment() {}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        System.out.println("comment正在执行！！！");
        getActivity().setTitle("聊天");
        view = inflater.inflate(R.layout.fragment_comment, container, false);
//        一定要执行了getData()方法才会去查询到数据
        getData();
        System.out.println("执行了getData()方法");
        recyclerView = view.findViewById(R.id.recycler_view1);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
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
                Log.i("CommentFragment", result);
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
            public TextView name;
            public TextView id;
            public TextView time;
            public TextView content;

            public ViewHolder(View itemView) {
                super(itemView);
                imageView = (ImageView) itemView.findViewById(R.id.comment_img);
                title = (TextView) itemView.findViewById(R.id.comment_title);
                id = itemView.findViewById(R.id.comment_id);
                name = itemView.findViewById(R.id.comment_name);
                content = itemView.findViewById(R.id.comment_content);
                time = itemView.findViewById(R.id.comment_time);
            }
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.item_copy, parent, false);
            final ViewHolder holder = new ViewHolder(view);
            return holder;
        }

        //将数据绑定到控件上
        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Glide.with(getActivity()).load(mData.get(position).get("img").toString()).asBitmap().placeholder(R.mipmap.ic_launcher).into(holder.imageView);
            holder.title.setText((String) mData.get(position).get("title"));
            holder.id.setText((String)mData.get(position).get("id"));
            holder.name.setText((String)mData.get(position).get("name"));
            holder.content.setText((String)mData.get(position).get("content"));
            holder.time.setText((String)mData.get(position).get("createtime"));
            System.out.println(mData.size());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //进行修改操作等
                }
            });
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }

    }
}