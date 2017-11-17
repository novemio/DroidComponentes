package com.novemio.android.components.mvp;

import android.app.Dialog;
import android.content.DialogInterface;
import android.support.annotation.StringRes;

/**
 * View (implemented as interface) in MVP contains functions that Presenter uses to communicate with Activity/Fragment
 * Direction of communication: Presenter -> Activity/Fragment
 * <p>
 * MvpView contains most common functions for Activities.
 * BaseActivity implements MvpView.
 */
public interface MvpView {

    void onError(@StringRes int resId);

    void onError(String message);

    boolean isNetworkConnected();

    void hideKeyboard();

    void showProgressDialog(String message);

    void showProgressDialog(String message, DialogInterface.OnCancelListener onCancelListener);

    void dismissProgressDialog();

    void showDialog(Dialog dialog);

    void showDialog(Dialog dialog, DialogInterface.OnCancelListener onCancelListener);

    void dismissDialog(Dialog dialog);

    void dismissAllDialogs();
}

