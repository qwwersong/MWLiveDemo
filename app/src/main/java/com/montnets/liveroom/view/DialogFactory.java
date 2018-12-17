package com.montnets.liveroom.view;

import android.app.Activity;
import android.app.Dialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.montnets.liveroom.R;
import com.montnets.liveroom.adapter.IMAdapter;

import java.util.List;

/**
 * Created by songlei on 2018/12/17.
 */
public class DialogFactory {

    public static Dialog createDialog(Activity context, List<String> devices, IMAdapter.OnItemClickListener onItemClickListener){
        final Dialog dialog = new Dialog(context, R.style.title_choose_dialog);
        View view = LayoutInflater.from(context).inflate(R.layout.layout_dialog_list, null, false);

        dialog.setContentView(view, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT));
        Window window = dialog.getWindow();
        // 设置显示动画
        window.setWindowAnimations(R.style.main_menu_animstyle);
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.x = 0;
        wl.y = context.getWindowManager().getDefaultDisplay().getHeight();
        // 以下这两句是为了保证按钮可以水平满屏
        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
        wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        // 设置显示位置
        dialog.onWindowAttributesChanged(wl);
        // 设置点击外围解散
        dialog.setCanceledOnTouchOutside(true);

        RecyclerView rlv_devices = (RecyclerView) view.findViewById(R.id.lv_devices);
        rlv_devices.setLayoutManager(new LinearLayoutManager(context));
        ((DefaultItemAnimator) rlv_devices.getItemAnimator()).setSupportsChangeAnimations(false);
        IMAdapter adapter = new IMAdapter(context, devices);
        rlv_devices.setAdapter(adapter);
        adapter.setOnItemClickListener(onItemClickListener);

        return dialog;
    }
}
