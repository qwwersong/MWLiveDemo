package com.montnets.liveroom.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 视频观看详情页-简介tab
 * Created by songlei on 2018/12/10.
 */
public class IntroductionFragment extends Fragment {

    public static IntroductionFragment getInstance() {
        IntroductionFragment introductionFragment = new IntroductionFragment();
        return introductionFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
