package com.novemio.android.components.adapter;

import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xix on 20.5.16..
 */
public abstract class RecyclerViewAdapterBase<T, V extends AdvancedView<T>> extends RecyclerView.Adapter<ViewWrapper<V>> {

    private static final String LOG_TAG = RecyclerViewAdapterBase.class.getSimpleName();


    protected List<T> items = new ArrayList<>();
    @Nullable protected OnItemClickListener<T> clickListener;
    @Nullable protected OnItemLongClickListener<T> longClickListener;
    @Nullable protected OnSettingsClickListener<T> settingsClickListener;
    private Handler handler = new Handler();

    @Override
    public final ViewWrapper<V> onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewWrapper<>(onCreateItemView(parent, viewType));
    }

    @Override
    public int getItemCount() {
        return items != null ? items.size() : 0;
    }

    protected abstract V onCreateItemView(ViewGroup parent, int viewType);

    public void setCollection(final List<T> list) {
        items = list;
        notifyDataSetChanged();
    }

    public void setCollection(RecyclerView recyclerView, final List<T> list, int scrollToPosition){
        this.setCollection(list);
        handler.post(() -> {
            if (recyclerView != null) {
                recyclerView.getLayoutManager().scrollToPosition(scrollToPosition);
            }
        });
    }

    public void setSettingsClickListener(@Nullable OnSettingsClickListener<T> settingsClickListener) {
        this.settingsClickListener = settingsClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener<T> listener) {
        this.clickListener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener<T> listener) {
        this.longClickListener = listener;
    }
    // additional methods to manipulate the items

    public T getItem(int position) {
        return items.get(position);
    }

    public List<T> getItems() {
        return items;
    }

    public int getItemIndex(T item) {
        return items.indexOf(item);
    }

    public interface OnItemClickListener<I> {

        void onItemClick(I item);
    }

    public interface OnItemLongClickListener<I> {

        void onItemLongClick(I item);
    }

    public interface OnSettingsClickListener<I> {

        void onSettingsClickListener(I item, View view);
    }


}
