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
import com.montnets.liveroom.VideoConstants;
import com.montnets.liveroom.bean.LiveList;

import java.util.List;

public class LiveAdapter extends BaseAdapter {
    private Context context;
    private List<LiveList.ObjBean.ListBean> list;
    private LayoutInflater inflater;

    public LiveAdapter(Context context, List<LiveList.ObjBean.ListBean> list){
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    public void refreshList(List<LiveList.ObjBean.ListBean> list){
        this.list = list;
        notifyDataSetChanged();
    }

//    @Override
//    public VideoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(context).inflate(R.layout.layout_item_list, parent, false);
//        VideoHolder holder = new VideoHolder(view);           //
//        return holder;
//    }
//
//    @SuppressLint("DefaultLocale")
//    @Override
//    public void onBindViewHolder(VideoHolder holder, final int position) {
//        Glide.with(context).load(list.get(position).getMediaCover()).placeholder(R.mipmap.ic_launcher).into(holder.ivCover);
//        holder.tvName.setText(list.get(position).getMediaName());
//        switch (list.get(position).getLiveStatus()) {
//            case VideoConstants.STATE_PREVIEW:
//                holder.tvState.setText("播放状态：预告");
//                break;
//            case VideoConstants.STATE_PLAYING:
//                holder.tvState.setText("播放状态：直播中");
//                break;
//            case VideoConstants.STATE_FINISH:
//                holder.tvState.setText("播放状态：直播结束");
//                break;
//            case VideoConstants.STATE_ERROR:
//                holder.tvState.setText("播放状态：直播异常");
//                break;
//            case VideoConstants.STATE_OVER_VIEW:
//                holder.tvState.setText("播放状态：直播过期");
//                break;
//        }
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
    public LiveList.ObjBean.ListBean getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.layout_item_list, parent, false);
            viewHolder.ivCover = (ImageView) convertView.findViewById(R.id.iv_cover);
            viewHolder.tvName = (TextView) convertView.findViewById(R.id.tv_video_name);
            viewHolder.tvState = (TextView) convertView.findViewById(R.id.tv_video_state);
            viewHolder.tvTime = (TextView) convertView.findViewById(R.id.tv_video_time);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        LiveList.ObjBean.ListBean bean = list.get(position);

        Glide.with(context).load(bean.getMediaCover()).placeholder(R.mipmap.ic_launcher).into(viewHolder.ivCover);
        viewHolder.tvName.setText(bean.getMediaName());
        switch (bean.getLiveStatus()) {
            case VideoConstants.STATE_PREVIEW:
                viewHolder.tvState.setText("播放状态：预告");
                break;
            case VideoConstants.STATE_PLAYING:
                viewHolder.tvState.setText("播放状态：直播中");
                break;
            case VideoConstants.STATE_FINISH:
                viewHolder.tvState.setText("播放状态：直播结束");
                break;
            case VideoConstants.STATE_ERROR:
                viewHolder.tvState.setText("播放状态：直播异常");
                break;
            case VideoConstants.STATE_OVER_VIEW:
                viewHolder.tvState.setText("播放状态：直播过期");
                break;
        }
        viewHolder.tvTime.setText(bean.getShowTime());

        return convertView;
    }

    class ViewHolder {
        public ImageView ivCover;
        public TextView tvName;
        public TextView tvState;
        public TextView tvTime;
    }

//    private OnItemClickListener onItemClickListener;
//    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
//        this.onItemClickListener = onItemClickListener;
//    }
//
//    public interface OnItemClickListener{
//
//        void onItemClick(int position);
//
//    }
}
