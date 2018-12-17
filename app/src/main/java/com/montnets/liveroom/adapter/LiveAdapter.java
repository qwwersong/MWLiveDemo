package com.montnets.liveroom.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.montnets.liveroom.R;
import com.montnets.liveroom.VideoConstants;
import com.montnets.liveroom.bean.LiveList;

import java.util.List;

public class LiveAdapter extends RecyclerView.Adapter<LiveAdapter.VideoHolder> {
    private Context context;
    private List<LiveList.ObjEntity> list;

    public LiveAdapter(Context context, List<LiveList.ObjEntity> list){
        this.context = context;
        this.list = list;
    }

    public void refreshList(List<LiveList.ObjEntity> list){
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public VideoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_item_list, parent, false);
        VideoHolder holder = new VideoHolder(view);           //
        return holder;
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(VideoHolder holder, final int position) {
        Glide.with(context).load(list.get(position).getLiveCover()).placeholder(R.mipmap.ic_launcher).into(holder.ivCover);
        holder.tvName.setText(list.get(position).getLiveName());
        switch (list.get(position).getLiveStatus()) {
            case VideoConstants.STATE_PREVIEW:
                holder.tvState.setText("播放状态：预告");
                break;
            case VideoConstants.STATE_PLAYING:
                holder.tvState.setText("播放状态：直播中");
                break;
            case VideoConstants.STATE_FINISH:
                holder.tvState.setText("播放状态：直播结束");
                break;
            case VideoConstants.STATE_ERROR:
                holder.tvState.setText("播放状态：直播异常");
                break;
            case VideoConstants.STATE_OVER_VIEW:
                holder.tvState.setText("播放状态：直播过期");
                break;
        }
        holder.tvTime.setText(list.get(position).getPlanTime());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class VideoHolder extends RecyclerView.ViewHolder{
        public ImageView ivCover;
        public TextView tvName;
        public TextView tvState;
        public TextView tvTime;

        public VideoHolder(View itemView) {
            super(itemView);
            ivCover = (ImageView) itemView.findViewById(R.id.iv_cover);
            tvName = (TextView) itemView.findViewById(R.id.tv_video_name);
            tvState = (TextView) itemView.findViewById(R.id.tv_video_state);
            tvTime = (TextView) itemView.findViewById(R.id.tv_video_time);
        }
    }

    private OnItemClickListener onItemClickListener;
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener{

        void onItemClick(int position);

    }
}
