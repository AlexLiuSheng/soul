package com.alexliu.rxjavademo.utils;

import android.content.Context;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;


import rx.Observable;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Allen Liu on 2016/5/27.
 */
public class ThreadTransformer<S>  implements Observable.Transformer<S,S> {
    private  ProgressBar pb;
    private Context context;
    public ThreadTransformer(ProgressBar pb,Context context) {
        this.pb=pb;
        this.context=context;
    }

    @Override
    public Observable<S> call(Observable<S> tObservable) {
        return tObservable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).unsubscribeOn(AndroidSchedulers.mainThread()).onErrorReturn(new Func1<Throwable, S>() {
            @Override
            public S call(Throwable throwable) {
                pb.setVisibility(View.GONE);
                Toast.makeText(context,throwable.getMessage(),Toast.LENGTH_SHORT).show();
                return null;
            }
        }).doOnSubscribe(() -> {
            pb.setVisibility(View.VISIBLE);
        }).subscribeOn(AndroidSchedulers.mainThread());
    }
}
