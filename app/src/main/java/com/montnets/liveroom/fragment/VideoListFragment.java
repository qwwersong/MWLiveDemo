package com.montnets.liveroom.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.montnets.liveroom.R;
import com.montnets.liveroom.RequestConstants;
import com.montnets.liveroom.VideoConstants;
import com.montnets.liveroom.activity.VideoActivity;
import com.montnets.liveroom.adapter.VideoAdapter;
import com.montnets.liveroom.bean.VideoList;
import com.montnets.liveroom.view.SwipeRefreshView;
import com.montnets.mwlive.net.NetBusiness;
import com.montnets.mwlive.net.OkRespCallBack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VideoListFragment extends Fragment {
    private SwipeRefreshView mSwipeRefreshView;
    private ListView listView;
    private VideoAdapter mVideoAdapter;
    private List<VideoList.ObjBean.ListBean> list;
    private int index = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list, null);
        initView(rootView);
        initListener();
        return rootView;
    }

    private void initView(View rootView) {
        TextView tvTitle = (TextView) rootView.findViewById(R.id.title);
        tvTitle.setText("短视频列表");
//        RecyclerView mRecyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);
        mSwipeRefreshView = (SwipeRefreshView) rootView.findViewById(R.id.layout_swipe_refresh);
        mSwipeRefreshView.measure(0, 0);
        listView = (ListView) rootView.findViewById(R.id.lv);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        list = new ArrayList<>();
        mVideoAdapter = new VideoAdapter(getActivity(), list);
        listView.setAdapter(mVideoAdapter);
//        mRecyclerView.setAdapter(mVideoAdapter);

        // 设置颜色属性的时候一定要注意是引用了资源文件还是直接设置16进制的颜色，因为都是int值容易搞混
        // 设置下拉进度的背景颜色，默认就是白色的
        mSwipeRefreshView.setProgressBackgroundColorSchemeResource(android.R.color.white);
        // 设置下拉进度的主题颜色
        mSwipeRefreshView.setColorSchemeResources(R.color.colorAccent,
                android.R.color.holo_blue_bright, R.color.colorPrimaryDark,
                android.R.color.holo_orange_dark, android.R.color.holo_red_dark, android.R.color.holo_purple);

        mSwipeRefreshView.setItemCount(10);

        getVideoList(false);
    }

    private void initListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                VideoList.ObjBean.ListBean item = list.get(position);
                Intent intent = new Intent(getActivity(), VideoActivity.class);
                intent.putExtra(VideoConstants.TYPE, VideoConstants.TYPE_VIDEO);
                intent.putExtra(VideoConstants.VIDEO_ID, item.getMediaId());
                startActivity(intent);
            }
        });

//        mVideoAdapter.setOnItemClickListener(new VideoAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(int position) {
//                VideoList.ObjBean.ListBean item = list.get(position);
//                Intent intent = new Intent(getActivity(), VideoActivity.class);
//                intent.putExtra(VideoConstants.TYPE, VideoConstants.TYPE_VIDEO);
//                intent.putExtra(VideoConstants.VIDEO_ID, item.getMediaId());
//                startActivity(intent);
//            }
//
//        });

        initEvent();
    }

    private void initEvent() {

        // 下拉时触发SwipeRefreshLayout的下拉动画，动画完毕之后就会回调这个方法
        mSwipeRefreshView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getVideoList(false);
            }
        });


        // 设置下拉加载更多
        mSwipeRefreshView.setOnLoadMoreListener(new SwipeRefreshView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                loadMoreData();
            }
        });
    }

    /**
     * 请求短视频列表数据
     */
    private void getVideoList(final boolean isLoadMore){
        if (!isLoadMore) {
            index = 1;
        }
        Map<String, String> map = new HashMap<>();
        map.put("index", String.valueOf(index));
        map.put("size", "10");
        NetBusiness.getInstance().okhttpPost(RequestConstants.VIDEO_LIST_URL, map, new OkRespCallBack<VideoList>(VideoList.class) {

            @Override
            public void onSuccess(VideoList response) {
                if (isLoadMore) {
                    list.addAll(response.getObj().getList());
                } else {
                    list = response.getObj().getList();
                }
                mVideoAdapter.refreshList(list);
                mSwipeRefreshView.setRefreshing(false);
            }

            @Override
            public void onError(int code, String msg) {
                Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadMoreData() {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                index = index + 1;
                getVideoList(true);
            }
        });
    }


}
