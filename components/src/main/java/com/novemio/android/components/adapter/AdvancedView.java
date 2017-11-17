package com.novemio.android.components.adapter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by xix on 4/9/17.
 */

public abstract class AdvancedView <T>extends FrameLayout implements HasViews,AdapterItemView<T> {
    public AdvancedView(Context context) {
        this(context, null);
    }

    public AdvancedView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public AdvancedView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(getContext(),getLayoutId(),this);
        onViewChanged(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public AdvancedView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        inflate(context,getLayoutId(),this);
        onViewChanged(this);
    }


    @Override
    public View findViewByID(int id) {
        return super.findViewById(id);
    }

    protected  void onViewChanged(HasViews view){

    }

    protected abstract  int getLayoutId();






}
