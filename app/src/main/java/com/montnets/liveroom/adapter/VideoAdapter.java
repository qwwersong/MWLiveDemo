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
import com.montnets.liveroom.bean.VideoList;

import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoHolder> {
    private Context context;
    private List<VideoList.ObjEntity> list;

    public VideoAdapter(Context context, List<VideoList.ObjEntity> list){
        this.context = context;
        this.list = list;
    }

    public void refreshList(List<VideoList.ObjEntity> list){
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public VideoAdapter.VideoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_item_list, parent, false);
        VideoAdapter.VideoHolder holder = new VideoAdapter.VideoHolder(view);
        return holder;
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(VideoAdapter.VideoHolder holder, final int position) {
        Glide.with(context).load(list.get(position).getVideoCover()).placeholder(R.mipmap.ic_launcher).into(holder.ivCover);
        holder.tvName.setText(list.get(position).getVideoName());
        holder.tvState.setText("观看人次：" + String.format("%d", list.get(position).getTotalViews()));
        holder.tvTime.setText(list.get(position).getCreateTime());
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

    private VideoAdapter.OnItemClickListener onItemClickListener;
    public void setOnItemClickListener(VideoAdapter.OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener{

        void onItemClick(int position);

    }
}
