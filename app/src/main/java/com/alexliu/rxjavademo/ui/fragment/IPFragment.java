package com.alexliu.rxjavademo.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.alexliu.rxjavademo.R;
import com.alexliu.rxjavademo.bean.IpBean;
import com.alexliu.rxjavademo.retrofit.MyRetrofit;
import com.alexliu.rxjavademo.retrofit.service.ApiService;
import com.alexliu.rxjavademo.ui.BaseFragment;
import com.alexliu.rxjavademo.utils.ThreadTransformer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Allen Liu on 2016/6/2.
 */
public class IPFragment extends BaseFragment {
    @BindView(R.id.et)
    EditText et;
    @BindView(R.id.btn)
    Button btn;
    @BindView(R.id.linear)
    LinearLayout linear;
    @BindView(R.id.tv)
    TextView tv;
    @BindView(R.id.pb)
    ProgressBar pb;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ip, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.btn)
    public void onClick() {
        String ip = et.getText().toString().trim();
        if (!TextUtils.isEmpty(ip)) {
            queryIp(ip);
        }
    }
    private void queryIp(String ip) {
        MyRetrofit.getInstance().create(ApiService.class).getIp(ip).compose(new ThreadTransformer<>(pb,getActivity()))
                .filter((IpBean bean) -> bean != null&&bean.getCode() == 0).subscribe((IpBean bean) -> {
            tv.setText(bean.toString());
            pb.setVisibility(View.GONE);
        });
    }
}
