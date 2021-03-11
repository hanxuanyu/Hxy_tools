package com.hxuanyu.mytools.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.hxuanyu.mytools.R;

import com.hxuanyu.mytools.beans.DeliverInfoBean;
import com.hxuanyu.mytools.beans.Item;
import com.hxuanyu.mytools.utils.MyToast;

import java.util.List;

public class DeliverInfoAdapter extends RecyclerView.Adapter<DeliverInfoAdapter.ViewHolder>{
    private List<DeliverInfoBean.ShowapiResBodyBean.DataBean> mItem;
    private Context mContext;
    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView context;
        TextView time;
        View itemView;
        public ViewHolder(View view) {
            super(view);
            itemView = view;
            time=view.findViewById(R.id.deliver_info_time);
            context = view.findViewById(R.id.deliver_info_context);
        }
    }

    public DeliverInfoAdapter(List<DeliverInfoBean.ShowapiResBodyBean.DataBean> mItem, Context mContext) {
        this.mItem = mItem;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_deliver_list,parent,false);

        final ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DeliverInfoBean.ShowapiResBodyBean.DataBean item = mItem.get(position);
        holder.context.setText(item.getContext());
        holder.time.setText(item.getTime());
    }

    @Override
    public int getItemCount() {
        return mItem.size();
    }
}
