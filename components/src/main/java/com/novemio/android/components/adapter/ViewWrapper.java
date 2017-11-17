package com.novemio.android.components.adapter;

import android.support.v7.widget.RecyclerView;

public class ViewWrapper<V extends AdvancedView> extends RecyclerView.ViewHolder {

    private V view;

    public ViewWrapper(V itemView) {
        super(itemView);
        view = itemView;
    }

    public V getView() {
        return view;
    }
}