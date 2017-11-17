package com.novemio.android.components.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView.LayoutParams;
import android.util.AttributeSet;
import android.view.ViewGroup;

/**
 * Created by xix on 11/16/17.
 */

public class LinearLayoutManager extends android.support.v7.widget.LinearLayoutManager {

    public LinearLayoutManager(Context context) {
        super(context);
    }

    public LinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public LinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public LayoutParams generateDefaultLayoutParams() {
        if (getOrientation() == LinearLayoutManager.HORIZONTAL) {
            return new GridLayoutManager.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
        } else {
            return new GridLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

}
