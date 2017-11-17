package com.novemio.android.components.event;


public abstract class NotificationEvent<D, T extends EventAction> {

    private D data;
    protected T type;

    public NotificationEvent(D data, T type) {
        this.data = data;
        this.type = type;
    }


    public T getEventAction() {
        return type;
    }

    public D getData() {
        return data;
    }


}