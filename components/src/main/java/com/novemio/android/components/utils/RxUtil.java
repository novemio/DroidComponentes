package com.novemio.android.components.utils;

import io.reactivex.FlowableTransformer;
import io.reactivex.ObservableTransformer;
import io.reactivex.SingleTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by xix on 7/18/17.
 */

public class RxUtil {

    /**
     * Get {@link ObservableTransformer} that transforms the source observable to subscribe in
     * the io thread and observe on the Android's UI thread.
     *
     * Because it doesn't interact with the emitted items it's safe ignore the unchecked casts.
     *
     * @return {@link ObservableTransformer}
     */
    private static final ObservableTransformer ioToMainThreadSchedulerTransformer =
            observable -> observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());


    private static final ObservableTransformer ioToComputationThreadSchedulerTransformer =
            observable -> observable.subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.computation());


    private static final FlowableTransformer ioToMainThreadSchedulerTransformerFlowable =
            flowable -> flowable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());

    private static final ObservableTransformer ioToMainThreadSchedulerTransformerObservable =
            observable -> observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());


    private static final SingleTransformer ioToMainThreadSchedulerTransformerSingle =
            single -> single.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());

    private static final SingleTransformer computationToMainThreadSchedulerTransformerSingle =
            single -> single.subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread());

    private static final FlowableTransformer computationToMainThreadSchedulerTransformerFlowable =
            single -> single.subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread());

    @SuppressWarnings("unchecked")
    public static <T> ObservableTransformer<T, T> applyIOToMainThreadSchedulers() {
        return ioToMainThreadSchedulerTransformer;
    }

    @SuppressWarnings("unchecked")
    public static <T> ObservableTransformer<T, T> applyIOToComputationThreadSchedulers() {
        return ioToComputationThreadSchedulerTransformer;
    }

    public static <T> FlowableTransformer<T, T> applyIOToMainThreadSchedulersFlowable() {
        return ioToMainThreadSchedulerTransformerFlowable;
    }

    public static <T> ObservableTransformer<T, T> applyIOToMainThreadSchedulersObservable() {
        return ioToMainThreadSchedulerTransformerObservable;
    }

    public static <T> SingleTransformer<T, T> applyIOToMainThreadSchedulersSingle() {

        return ioToMainThreadSchedulerTransformerSingle;
    }

    public static <T> SingleTransformer<T, T> applyComputationToMainThreadSchedulersSingle() {

        return computationToMainThreadSchedulerTransformerSingle;
    }

    public static <T> FlowableTransformer<T, T> applyComputationToMainThreadSchedulersFlowable() {
        return computationToMainThreadSchedulerTransformerFlowable;
    }
}
