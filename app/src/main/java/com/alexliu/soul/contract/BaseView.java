package com.alexliu.soul.contract;

/**
 * Created by Allen Liu on 2016/7/4.
 */
public interface BaseView<T> {
    void bindPresenter(T preenter);
}
