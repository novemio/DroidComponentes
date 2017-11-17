/*
 * Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://mindorks.com/license/apache-v2
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */

package com.novemio.android.components.mvp;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import dagger.android.support.AndroidSupportInjection;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * Created by xix on 27/03/17.
 */

public abstract class BaseFragment<V extends MvpView> extends Fragment implements MvpView {
    
    // Dialogs:
    private ProgressDialog progressDialog;
    private ArrayList<Dialog> dialogs;
    
    // Other:
    private boolean isDestroyedBySystem;
    @Nullable private MvpPresenter<V> basePresenter;
    @Nullable private WeakReference<V> presenterView;
    public Context context;
    private BaseActivity mActivity;
    
    @Override public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);
        dialogs = new ArrayList<Dialog>(2);
    }
    
    @Nullable @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return getLayoutInflater().inflate(getLayoutId(), container, false);
    }
    
    @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        initView();
    }
    
    @Override public void onStart() {
        super.onStart();
        if (basePresenter != null && presenterView != null && presenterView.get() != null) {
            basePresenter.bindView(presenterView.get());
        }
    }
    
    @Override public void onAttach(Context context) {
        inject();
        super.onAttach(context);
        this.context = context;
        if (context instanceof Activity) {
            this.mActivity = (BaseActivity) context;
            mActivity.onFragmentAttached();
        }
    }
    
    protected abstract int getLayoutId();
    
    protected void inject() {
        AndroidSupportInjection.inject(this);
    }
    
    protected abstract void initView();
    
    final protected void initPresenter(MvpPresenter<V> presenter, V v) {
        basePresenter = presenter;
        presenter.bindView(v);
        presenterView = new WeakReference<>(v);
    }
    
    @Override public void onResume() {
        super.onResume();
        isDestroyedBySystem = false;
    }
    
    @Override public void onStop() {
        
        if (basePresenter != null) {
            basePresenter.unbindView();
        }
        
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        while (dialogs.size() > 0) {
            Dialog dialog = dialogs.remove(dialogs.size() - 1);
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
        }
        super.onStop();
    }
    
    @Override public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        isDestroyedBySystem = true;
    }
    
    @Override public void onDestroyView() {
        super.onDestroyView();
        if (basePresenter != null) {
            basePresenter.destroy();
        }
    }
    
    @Override public void onDestroy() {
        super.onDestroy();
        if (!isDestroyedBySystem) {
            if (basePresenter != null) {
                basePresenter.destroy();
            }
        }
    }
    
    @Override public void onError(String message) {
        if (mActivity != null) {
            mActivity.onError(message);
        }
    }
    
    @Override public void onError(@StringRes int resId) {
        if (mActivity != null) {
            mActivity.onError(resId);
        }
    }
    
    @Override public boolean isNetworkConnected() {
        if (mActivity != null) {
            return mActivity.isNetworkConnected();
        }
        return false;
    }
    
    @Override public void onDetach() {
        mActivity = null;
        super.onDetach();
    }
    
    @Override public void hideKeyboard() {
        if (mActivity != null) {
            mActivity.hideKeyboard();
        }
    }
    
    public BaseActivity getBaseActivity() {
        return mActivity;
    }
    
    //public void setUnBinder(Unbinder unBinder) {
    //    mUnBinder = unBinder;
    //}
    
    @Override public void showProgressDialog(String message) {
        if (getActivity() != null) {
            progressDialog = ProgressDialog.show(getActivity(), "", message, true);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setCancelable(false);
        }
    }
    
    @Override public void showProgressDialog(String message, DialogInterface.OnCancelListener onCancelListener) {
        if (getActivity() != null) {
            progressDialog = ProgressDialog.show(getActivity(), "", message, true, true, onCancelListener);
            progressDialog.setCanceledOnTouchOutside(true);
        }
    }
    
    @Override public void dismissProgressDialog() {
        if (getActivity() != null && progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
    
    @Override public void showDialog(Dialog dialog) {
        if (getActivity() != null) {
            this.dialogs.add(dialog);
            dialog.show();
        }
    }
    
    @Override public void showDialog(Dialog dialog, DialogInterface.OnCancelListener onCancelListener) {
        if (getActivity() != null) {
            this.dialogs.add(dialog);
            dialog.setOnCancelListener(onCancelListener);
            dialog.show();
        }
    }
    
    @Override public void dismissAllDialogs() {
        while (dialogs.size() > 0) {
            Dialog dialog = dialogs.remove(dialogs.size() - 1);
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
                return;
            }
        }
    }
    
    @Override public void dismissDialog(Dialog dialog) {
        if (dialogs.contains(dialog)) {
            dialogs.remove(dialog);
        }
        dialog.dismiss();
    }
    
    public interface Callback {
        
        void onFragmentAttached();
        
        void onFragmentDetached(String tag);
    }
}
