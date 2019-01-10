package com.montnets.liveroom.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.montnets.liveroom.R;
import com.montnets.liveroom.bean.VideoList;

import java.util.List;

public class VideoAdapter extends BaseAdapter {
    private Context context;
    private List<VideoList.ObjBean.ListBean> list;
    private LayoutInflater inflater;

    public VideoAdapter(Context context, List<VideoList.ObjBean.ListBean> list){
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    public void refreshList(List<VideoList.ObjBean.ListBean> list){
        this.list = list;
        notifyDataSetChanged();
    }

//    @Override
//    public VideoAdapter.VideoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(context).inflate(R.layout.layout_item_list, parent, false);
//        VideoAdapter.VideoHolder holder = new VideoAdapter.VideoHolder(view);
//        return holder;
//    }
//
//    @SuppressLint("DefaultLocale")
//    @Override
//    public void onBindViewHolder(VideoAdapter.VideoHolder holder, final int position) {
//        Glide.with(context).load(list.get(position).getMediaCover()).placeholder(R.mipmap.ic_launcher).into(holder.ivCover);
//        holder.tvName.setText(list.get(position).getMediaName());
//        holder.tvState.setText("观看人次：" + String.format("%d", list.get(position).getTotalViews()));
//        holder.tvTime.setText(list.get(position).getShowTime());
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (onItemClickListener != null) {
//                    onItemClickListener.onItemClick(position);
//                }
//            }
//        });
//    }
//
//    @Override
//    public int getItemCount() {
//        return list.size();
//    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public VideoList.ObjBean.ListBean getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        VideoHolder viewHolder;
        if (convertView == null) {
            viewHolder = new VideoHolder();
            convertView = inflater.inflate(R.layout.layout_item_list, parent, false);
            viewHolder.ivCover = (ImageView) convertView.findViewById(R.id.iv_cover);
            viewHolder.tvName = (TextView) convertView.findViewById(R.id.tv_video_name);
            viewHolder.tvState = (TextView) convertView.findViewById(R.id.tv_video_state);
            viewHolder.tvTime = (TextView) convertView.findViewById(R.id.tv_video_time);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (VideoHolder) convertView.getTag();
        }

        VideoList.ObjBean.ListBean bean = list.get(position);

        Glide.with(context).load(bean.getMediaCover()).placeholder(R.mipmap.ic_launcher).into(viewHolder.ivCover);
        viewHolder.tvName.setText(bean.getMediaName());
        viewHolder.tvState.setText("观看人次：" + String.format("%d", bean.getTotalViews()));
        viewHolder.tvTime.setText(bean.getShowTime());
        return convertView;
    }

    class VideoHolder{
        public ImageView ivCover;
        public TextView tvName;
        public TextView tvState;
        public TextView tvTime;

//        public VideoHolder(View itemView) {
//            super(itemView);
//            ivCover = (ImageView) itemView.findViewById(R.id.iv_cover);
//            tvName = (TextView) itemView.findViewById(R.id.tv_video_name);
//            tvState = (TextView) itemView.findViewById(R.id.tv_video_state);
//            tvTime = (TextView) itemView.findViewById(R.id.tv_video_time);
//        }
    }

//    private VideoAdapter.OnItemClickListener onItemClickListener;
//    public void setOnItemClickListener(VideoAdapter.OnItemClickListener onItemClickListener){
//        this.onItemClickListener = onItemClickListener;
//    }
//
//    public interface OnItemClickListener{
//
//        void onItemClick(int position);
//
//    }
}
