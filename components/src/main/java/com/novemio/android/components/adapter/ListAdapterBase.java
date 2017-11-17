package com.novemio.android.components.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xix on 20.5.16..
 */
public abstract class ListAdapterBase<T, V extends View> extends BaseAdapter {

    protected List<T> items = new ArrayList<>();

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public T getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public V getView(int position, View convertView, ViewGroup parent) {
        return bindView(position, convertView, parent);
    }

    protected abstract V bindView(int position, View convertView, ViewGroup parent);

    public void setCollection(List<T> collection) {
        items = collection;
        notifyDataSetChanged();
    }
}
