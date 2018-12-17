package com.montnets.liveroom.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.montnets.liveroom.R;
import com.montnets.liveroom.RequestConstants;
import com.montnets.liveroom.VideoConstants;
import com.montnets.liveroom.activity.VideoActivity;
import com.montnets.liveroom.adapter.LiveAdapter;
import com.montnets.liveroom.bean.LiveList;
import com.montnets.mwlive.net.NetBusiness;
import com.montnets.mwlive.net.OkRespCallBack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LiveListFragment extends Fragment {
    private SwipeRefreshLayout swipeRefreshLayout;
    private LiveAdapter mVideoAdapter;
    private List<LiveList.ObjEntity> list;

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
        tvTitle.setText("直播列表");
        RecyclerView mRecyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.layout_swipe_refresh);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        list = new ArrayList<>();
        mVideoAdapter = new LiveAdapter(getActivity(), list);
        mRecyclerView.setAdapter(mVideoAdapter);
        getLiveListData();
    }

    private void initListener() {
        mVideoAdapter.setOnItemClickListener(new LiveAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                LiveList.ObjEntity item = list.get(position);
                Intent intent = new Intent(getActivity(), VideoActivity.class);
                intent.putExtra(VideoConstants.TYPE, VideoConstants.TYPE_LIVE);
                intent.putExtra(VideoConstants.VIDEO_ID, item.getId());
                startActivity(intent);
            }

        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getLiveListData();
            }
        });
    }

    /**
     * 请求直播列表数据
     */
    private void getLiveListData() {
        NetBusiness.getInstance().okhttpPost(RequestConstants.LIVE_LIST_URL, new HashMap<String, String>(), new OkRespCallBack<LiveList>(LiveList.class) {

            @Override
            public void onSuccess(LiveList response) {
                list = response.getObj();
                mVideoAdapter.refreshList(list);
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onError(int code, String msg) {
                Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
            }
        });
    }


}
