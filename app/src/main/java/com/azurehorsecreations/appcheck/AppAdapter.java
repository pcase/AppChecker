package com.azurehorsecreations.appcheck;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pattycase on 8/27/16.
 */
public class AppAdapter extends RecyclerView.Adapter<AppAdapter.ViewHolder> {

    private List<App> mDataList;
    private Context mContext;
    private View packageView;
    private ArrayList<String> permissionsToCheckList;

    public AppAdapter(Context context, List<App> dataList) {
        mDataList = dataList;
        mContext = context;
    }

    private Context getContext() {
        return mContext;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView appIconImageView;
        public TextView appInfoTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            appIconImageView = (ImageView)itemView.findViewById(R.id.package_icon);
            appInfoTextView = (TextView)itemView.findViewById(R.id.package_info);
        }
    }

    @Override
    public AppAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        packageView = inflater.inflate(R.layout.item_package_info, parent, false);
        ViewHolder viewHolder = new ViewHolder(packageView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AppAdapter.ViewHolder viewHolder, int position) {
        App app = mDataList.get(position);
        ImageView appIconImageView = viewHolder.appIconImageView;
        TextView appInfoTextView = viewHolder.appInfoTextView;
        appIconImageView.setImageDrawable(app.getIcon());
        appInfoTextView.setText(app.getName());
        if (app.isSuspicious()) {
            appInfoTextView.setTextColor(Color.RED);
        }
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }
}
