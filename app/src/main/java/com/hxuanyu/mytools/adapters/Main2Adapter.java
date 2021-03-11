package com.hxuanyu.mytools.adapters;

import android.app.WallpaperManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hxuanyu.mytools.R;
import com.hxuanyu.mytools.activity.Main2Activity;
import com.hxuanyu.mytools.beans.Item;
import com.hxuanyu.mytools.beans.MainItem;
import com.hxuanyu.mytools.utils.MyToast;

import java.util.List;


/**
 * Recycler adapter
 *
 * @author Qiushui
 */
public class Main2Adapter extends RecyclerView.Adapter<Main2Adapter.ViewHolder> {

    private List<MainItem> items;
    private final Context context;
    private static final int ITEM_COUNT = 10;
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    public Main2Adapter(List<MainItem> itemList, Context context) {
        this.context = context;
        this.items = itemList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.rv_header,parent,false);

            ViewHolder holder = new ViewHolder(view);
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int position = holder.getAdapterPosition();
                    MainItem item = items.get(position);
                    item.getItemInter().onLongClick();
                    return true;
                }
            });
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getAdapterPosition();
                    MainItem item = items.get(position);
                    item.getItemInter().onClick();
                }
            });
            return holder;
        }

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rv_item,parent,false);

        ViewHolder holder = new ViewHolder(view);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                MainItem item = items.get(position);
                MyToast.show(v.getContext(),item.getItemName());
                item.getItemInter().onClick();
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                int position = holder.getAdapterPosition();
                MainItem item = items.get(position);
                item.getItemInter().onLongClick();
                return true;
            }
        });

        return holder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MainItem mainItem = items.get(position);
        holder.itemName.setText(mainItem.getItemName());
        holder.itemInfo.setText(mainItem.getItemInfo());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER;
        } else {
            return TYPE_ITEM;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemName;
        TextView itemInfo;
        View itemView;
        ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            itemName = itemView.findViewById(R.id.item_name_tv);
            itemInfo = itemView.findViewById(R.id.item_info_tv);
        }
    }
}
