package com.montnets.liveroom.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.montnets.liveroom.R;

import java.util.List;

public class IMAdapter extends RecyclerView.Adapter<IMAdapter.VideoHolder> {
    private Context context;
    private List<String> list;

    public IMAdapter(Context context, List<String> list){
        this.context = context;
        this.list = list;
    }

    public void addItem(String msg){
        list.add(msg);
        notifyItemInserted(list.size());
    }

    public void refershList(List<String> list){
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public VideoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_item_chat, parent, false);
        return new IMAdapter.VideoHolder(view);
    }

    @Override
    public void onBindViewHolder(VideoHolder holder, final int position) {
        holder.tvMsgItem.setText(list.get(position));
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
        public TextView tvMsgItem;

        public VideoHolder(View itemView) {
            super(itemView);
            tvMsgItem = (TextView) itemView.findViewById(R.id.tv_chat_item);
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
