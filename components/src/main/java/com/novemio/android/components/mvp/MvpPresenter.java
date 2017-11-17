package com.novemio.android.components.mvp;

/**
 * Created by xix on 27/03/17.
 */


/**
 * Every presenter in the app must either implement this interface or extend BasePresenter
 * indicating the MvpView type that wants to be attached with.
 */
public interface MvpPresenter<V extends MvpView> {

    void bindView(V mvpView);

    void unbindView();

    //void handleApiError(ANError error);

    void destroy();
}
