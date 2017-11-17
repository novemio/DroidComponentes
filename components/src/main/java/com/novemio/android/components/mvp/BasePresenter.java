package com.novemio.android.components.mvp;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import java.lang.ref.WeakReference;

/**
 * Presenter in MVP is the middle man between View and Model.
 * <p>
 * BasePresenter is base class for all Presenters.
 * It contains reference to the View (Activity/Fragment).
 */
public abstract class BasePresenter<V extends MvpView> implements MvpPresenter<V> {

    protected WeakReference<V> view;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    /**
     * bindView binds View to Presenter
     */
    public void bindView(V view) {
        if(this.view!=null){
            this.view.clear();
        }
        this.view = new WeakReference<>(view);
    }

    /**
     * unbindView unbinds View from Presenter
     */
    public void unbindView() {
        this.view = null;
        clearSubscriptions();
    }

    /**
     * Presenter uses getView to get attached View
     */
    public V getView() {
        if (view == null) {
            return null;
        } else {
            return view.get();
        }
    }

    public void destroy() {
        clearSubscriptions();
    }

    /**
     * Add a disposable to a DisposableList that will be unsubscribed when user calls the destroy() method.
     *
     * @param disposable to be added to the DisposableList.
     */
    protected void addDisposable(Disposable disposable) {
        if (disposable != null) {
            compositeDisposable.add(disposable);
        }
    }

    //
    protected void removeDisposable(Disposable disposable) {
        if (disposable != null) {
            compositeDisposable.remove(disposable);
        }
    }

    protected void clearSubscriptions(){
        compositeDisposable.clear();
    }

}
