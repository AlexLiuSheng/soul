package com.example.maomao.rxjavademo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.maomao.rxjavademo.bean.IpBean;
import com.example.maomao.rxjavademo.retrofit.MyRetrofit;
import com.example.maomao.rxjavademo.utils.ThreadTransformer;

import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import rx.android.schedulers.AndroidSchedulers;

public class MainActivity extends AppCompatActivity {
    @ViewInject(R.id.tv)
    private TextView tv;
    @ViewInject(R.id.et)
    private EditText et;
    @ViewInject(R.id.btn)
    private Button btn;
    @ViewInject(R.id.pb)
    private ProgressBar pb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        x.view().inject(this);
    }

    @Event(value = {R.id.btn})
    private void onClick(View view) {
        String ip = et.getText().toString().trim();
        if (!TextUtils.isEmpty(ip)) {
            queryIp(ip);
        }
    }

    private void queryIp(String ip) {
        MyRetrofit.getInstance().create(ApiService.class).getIp(ip).compose(new ThreadTransformer<>(pb,this))
                .filter((IpBean bean) -> bean != null&&bean.getCode() == 0).subscribe((IpBean bean) -> {
            tv.setText(bean.toString());
            pb.setVisibility(View.GONE);
        });
    }

    private void T(String content) {
        Toast.makeText(this, content, Toast.LENGTH_LONG).show();
    }


}
