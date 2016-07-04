package com.alexliu.soul.contract;

import android.widget.ProgressBar;

import com.alexliu.soul.bean.IpBean;
import com.alexliu.soul.presenter.BasePresenter;

/**
 * Created by Allen Liu on 2016/7/4.
 */
public interface IpInfoContract  {
    interface Presenter extends BasePresenter{
       void loadData();
    }
    interface  View extends  BaseView<Presenter>{
        ProgressBar getLoadingView();
        void dimissLoading();
        void refreshIpInfo(IpBean ipBean);
        String getIpNum();
    }
}
