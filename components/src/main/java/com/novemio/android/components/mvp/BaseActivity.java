package com.novemio.android.components.mvp;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import com.novemio.android.components.utils.NetworkUtils;
import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import javax.inject.Inject;

/**
 * Created by xix on 27/03/17.
 */

public abstract class BaseActivity<V extends MvpView> extends AppCompatActivity
        implements MvpView, BaseFragment.Callback, HasSupportFragmentInjector {
    
    // Dialogs:
    private ProgressDialog progressDialog;
    private ArrayList<Dialog> dialogArrayList;
    @Nullable private MvpPresenter<V> basePresenter;
    @Nullable private WeakReference<V> preseterView;
    @Inject DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;
    
    private FragmentManager fragmentManager;
    
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        injectMembers();
        dialogArrayList = new ArrayList<Dialog>(1);
        fragmentManager = getSupportFragmentManager();
    }
    
    protected abstract int getLayoutId();
    
    @Override public void onPostCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
        initView();
    }
    
    protected abstract void initView();
    
    protected void setPresenter(MvpPresenter<V> presenter, V mvpView) {
        basePresenter = presenter;
        basePresenter.bindView(mvpView);
        preseterView = new WeakReference<>(mvpView);
    }
    
    protected void injectMembers() {
        AndroidInjection.inject(this);
    }
    
    @Override protected void onStart() {
        super.onStart();
        if (basePresenter != null && preseterView != null && preseterView.get() != null) {
            basePresenter.bindView(preseterView.get());
        }
    }
    
    @Override protected void onStop() {
        
        if (basePresenter != null) {
            basePresenter.unbindView();
        }
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        while (dialogArrayList.size() > 0) {
            Dialog dialog = dialogArrayList.remove(dialogArrayList.size() - 1);
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
        }
        super.onStop();
    }
    
    @Override protected void onDestroy() {
        super.onDestroy();
        if (basePresenter != null) {
            basePresenter.destroy();
        }
    }
    
    @TargetApi(Build.VERSION_CODES.M) public void requestPermissionsSafely(String[] permissions, int requestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, requestCode);
        }
    }
    
    @TargetApi(Build.VERSION_CODES.M) public boolean hasPermission(String permission) {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M || checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
    }
    
    @Override public void showProgressDialog(String message) {
        progressDialog = ProgressDialog.show(this, "", message, true);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
    }
    
    @Override public void showProgressDialog(String message, DialogInterface.OnCancelListener onCancelListener) {
        progressDialog = ProgressDialog.show(this, "", message, true, true, onCancelListener);
        progressDialog.setCanceledOnTouchOutside(true);
    }
    
    @Override public void dismissProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
    
    @Override public void showDialog(Dialog dialog) {
        this.dialogArrayList.add(dialog);
        if (!dialog.isShowing()) {
            dialog.show();
        }
    }
    
    @Override public void showDialog(Dialog dialog, DialogInterface.OnCancelListener onCancelListener) {
        this.dialogArrayList.add(dialog);
        dialog.setOnCancelListener(onCancelListener);
        dialog.show();
    }
    
    @Override public void dismissDialog(Dialog dialog) {
        if (dialog != null && dialog.isShowing()) {
            if (dialogArrayList.contains(dialog)) {
                dialogArrayList.remove(dialog);
            }
            dialog.dismiss();
        }
    }
    
    @Override public void dismissAllDialogs() {
        while (dialogArrayList.size() > 0) {
            Dialog dialog = dialogArrayList.remove(dialogArrayList.size() - 1);
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
                return;
            }
        }
    }
    
    public void replaceFragment(@IdRes int containerViewId, Fragment fragment) {
        replaceFragment(containerViewId, fragment, true, false);
    }
    
    public void replaceFragment(@IdRes int containerViewId, Fragment fragment, boolean addToBackStack, boolean popBackStack) {
        if (popBackStack) {
            fragmentManager.popBackStack();
        }
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(containerViewId, fragment);
        if (addToBackStack) {
            transaction.addToBackStack(fragment.getClass().getName());
        }
        transaction.commitAllowingStateLoss();
    }
    
    @Override public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }
    
    @Override public void onError(String message) {
        if (message != null) {
            showSnackBar(message);
        } else {
            showSnackBar("Unknown error");
        }
    }
    
    protected void showSnackBar(String message) {
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT);
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        snackbar.show();
    }
    
    @Override public void onError(@StringRes int resId) {
        onError(getString(resId));
    }
    
    @Override public boolean isNetworkConnected() {
        return NetworkUtils.isNetworkConnected(getApplicationContext());
    }
    
    public void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
