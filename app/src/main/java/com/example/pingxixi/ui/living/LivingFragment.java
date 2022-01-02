package com.example.pingxixi.ui.living;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.view.KeyEvent;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.bumptech.glide.Glide;
import com.example.pingxixi.MyApplication;
import com.example.pingxixi.R;

import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;

import static cn.jzvd.Jzvd.STATE_PAUSE;
import static cn.jzvd.Jzvd.STATE_PLAYING;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LivingFragment extends Fragment {
    private View view;
    PlayerAdapter adapter;
    RecyclerView recyclerView;
    private List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
    private List<HashMap<String, Object>> mData = new ArrayList<HashMap<String, Object>>();
    String url = new MyApplication().sv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        System.out.println("living正在执行");
        getActivity().setTitle("视频播放");
        //        一定要执行了getData()方法才会去查询到数据
        getData();
        view = inflater.inflate(R.layout.fragment_living, container, false);
        adapter = new PlayerAdapter(getContext());
        recyclerView = view.findViewById(R.id.recycler_video);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        System.out.println("11111");
        recyclerView.setAdapter(adapter);
        System.out.println("2222222");
        recyclerView.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(View view) {
            }

            @Override
            public void onChildViewDetachedFromWindow(View view) {
                Jzvd jzvd = view.findViewById(R.id.video);
                if (jzvd != null && Jzvd.CURRENT_JZVD != null &&
                        jzvd.jzDataSource.containsTheUrl(Jzvd.CURRENT_JZVD.jzDataSource.getCurrentUrl())) {
                    if (Jzvd.CURRENT_JZVD != null && Jzvd.CURRENT_JZVD.screen != Jzvd.SCREEN_FULLSCREEN) {
//                        Jzvd.releaseAllVideos();
                    }
                }
            }
        });
        recyclerView.addOnScrollListener(new AutoPlayScrollListener(getActivity()));
        return view;
    }

    public void getData() {
        RequestParams params = new RequestParams(url);
        //get
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("LivingFragment", result);
                list = JSON.parseObject(result,
                        new TypeReference<List<HashMap<String, Object>>>() {
                        });
                System.out.println("go");
                mData.clear();
                mData.addAll(list);
                System.out.println(mData.size());
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

    /**
     * 监听recycleView滑动状态，
     * 自动播放可见区域内的第一个视频
     */
    private static class AutoPlayScrollListener extends RecyclerView.OnScrollListener {
        private int firstVisibleItem = 0;
        private int lastVisibleItem = 0;
        private int visibleCount = 0;
        private Context context;

        public AutoPlayScrollListener(Context context) {
            this.context = context;
        }

        /**
         * 被处理的视频状态标签
         */
        private enum VideoTagEnum {

            /**
             * 自动播放视频
             */
            TAG_AUTO_PLAY_VIDEO,

            /**
             * 暂停视频
             */
            TAG_PAUSE_VIDEO
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            switch (newState) {
                case RecyclerView.SCROLL_STATE_IDLE://停止滚动
                    autoPlayVideo(recyclerView, VideoTagEnum.TAG_AUTO_PLAY_VIDEO);
                default:
//                    autoPlayVideo(recyclerView, VideoTagEnum.TAG_PAUSE_VIDEO);//滑动时暂停视频 需要可以加上
                    break;
            }

        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
            if (layoutManager instanceof LinearLayoutManager) {
                LinearLayoutManager linearManager = (LinearLayoutManager) layoutManager;
                firstVisibleItem = linearManager.findFirstVisibleItemPosition();
                lastVisibleItem = linearManager.findLastVisibleItemPosition();
                visibleCount = lastVisibleItem - firstVisibleItem;
            }

        }

        /**
         * 循环遍历 可见区域的播放器
         * 然后通过 getLocalVisibleRect(rect)方法计算出哪个播放器完全显示出来
         * <p>
         * getLocalVisibleRect相关链接：http://www.cnblogs.com/ai-developers/p/4413585.html
         *
         * @param recyclerView
         * @param handleVideoTag 视频需要进行状态
         */
        private void autoPlayVideo(RecyclerView recyclerView, VideoTagEnum handleVideoTag) {

            for (int i = 0; i < visibleCount; i++) {
                if (recyclerView != null && recyclerView.getChildAt(i) != null && recyclerView.getChildAt(i).findViewById(R.id.video) != null) {
                    MyJzvdStd homeGSYVideoPlayer = (MyJzvdStd) recyclerView.getChildAt(i).findViewById(R.id.video);
                    Rect rect = new Rect();
                    homeGSYVideoPlayer.getLocalVisibleRect(rect);
                    int videoheight = homeGSYVideoPlayer.getHeight();
                    if (rect.top == 0 && rect.bottom == videoheight) {
                        handleVideo(handleVideoTag, homeGSYVideoPlayer);
                        // 跳出循环，只处理可见区域内的第一个播放器
                        break;
                    }
                }
            }
        }

        /**
         * 视频状态处理
         *
         * @param handleVideoTag     视频需要进行状态
         * @param homeGSYVideoPlayer JZVideoPlayer播放器
         */
        private void handleVideo(VideoTagEnum handleVideoTag, MyJzvdStd homeGSYVideoPlayer) {
            switch (handleVideoTag) {
                case TAG_AUTO_PLAY_VIDEO:
                    if ((homeGSYVideoPlayer.state != STATE_PLAYING)) {
                        // 进行播放
                        homeGSYVideoPlayer.startVideo();
                    }
                    break;
                case TAG_PAUSE_VIDEO:
                    if ((homeGSYVideoPlayer.state != STATE_PAUSE)) {
                        // 模拟点击,暂停视频
                        homeGSYVideoPlayer.startButton.performClick();
                    }
                    break;
                default:
                    break;
            }
        }


    }

//    /**
//     * 拦截返回键 返回不退出程序
//     */
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (Jzvd.backPress()) {
//            return true;
//        } else {
//            if (keyCode == KeyEvent.KEYCODE_BACK) {
//                moveTaskToBack(true);
//                return true;
//            }
//        }
//        return super.onKeyDown(keyCode, event);
//    }

    public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.ViewHolder> {
        private Context context;

        public class ViewHolder extends RecyclerView.ViewHolder {
            private MyJzvdStd video;
            private ImageView imageView;

            public ViewHolder(View itemView) {
                super(itemView);
                video = itemView.findViewById(R.id.video);
            }
        }

        public PlayerAdapter(Context context) {
            this.context = context;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            ViewHolder holder = new ViewHolder(LayoutInflater.from(
                    context).inflate(R.layout.video_my, parent,
                    false));
            return holder;
        }

        @SuppressLint("LongLogTag")
        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
//            holder.video.setUp("http://39.108.61.227/video/v1.mp4",
//                    "", JzvdStd.SCREEN_NORMAL);
//            holder.video.setVideoImageDisplayType(1);
//            holder.video.thumbImageView.setScaleType(ImageView.ScaleType.FIT_XY);
////        holder.videoplayer.startVideo();//自动播放 在recyclerview有bug
//            Glide.with(holder.video.getContext()).load("http://39.108.61.227/video/v1.jpg").
//                    into(holder.video.thumbImageView);
            holder.video.setUp((String) mData.get(position).get("url"),"", JzvdStd.SCREEN_NORMAL);
            holder.video.setVideoImageDisplayType(1);
            holder.video.thumbImageView.setScaleType(ImageView.ScaleType.FIT_XY);
            Glide.with(getActivity()).load(mData.get(position).get("image")).placeholder(R.mipmap.ic_launcher).
                    into(holder.video.thumbImageView);


        }

        @Override
        public int getItemCount() {
            return mData==null?0:mData.size();
        }
    }
}