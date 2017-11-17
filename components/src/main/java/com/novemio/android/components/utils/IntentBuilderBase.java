package com.novemio.android.components.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;

public  class IntentBuilderBase<T> {

        private final Context context;
        private final Intent intent;

        public IntentBuilderBase(Context context, Class<T> clazz) {
            this(context, new Intent(context, clazz));
        }

        public IntentBuilderBase(Context context, Intent intent) {
            this.context = context;
            this.intent = intent;
        }


        public void start(){
            context.startActivity(intent);
        }

        protected void putParcelable(String name, Parcelable parcelable){
            intent.putExtra(name,parcelable);
        }

    protected void putInteger(String name, Integer integer){
        intent.putExtra(name,integer);
    }

    }