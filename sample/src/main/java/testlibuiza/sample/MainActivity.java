package testlibuiza.sample;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import testlibuiza.R;
import testlibuiza.sample.api.TestAPIActivity;
import testlibuiza.sample.uizavideo.TestUizaVideoIMActivity;
import testlibuiza.sample.uizavideo.TestUizaVideoIMActivityRl;
import vn.loitp.core.base.BaseActivity;
import vn.loitp.core.utilities.LActivityUtil;
import vn.loitp.uizavideo.view.floatview.FloatingUizaVideoService;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findViewById(R.id.bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, TestAPIActivity.class);
                startActivity(intent);
                LActivityUtil.tranIn(activity);
            }
        });
        findViewById(R.id.bt_uiza_video).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uizaVideo();
            }
        });
        findViewById(R.id.bt_uiza_video_rl).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uizaVideoRl();
            }
        });
        findViewById(R.id.bt_uiza_float_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.startService(new Intent(activity, FloatingUizaVideoService.class));
                //onBackPressed();
            }
        });
    }

    @Override
    protected boolean setFullScreen() {
        return false;
    }

    @Override
    protected String setTag() {
        return getClass().getSimpleName();
    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.activity_main;
    }

    private void uizaVideo() {
        Intent intent = new Intent(activity, TestUizaVideoIMActivity.class);
        startActivity(intent);
        LActivityUtil.tranIn(activity);
    }

    private void uizaVideoRl() {
        Intent intent = new Intent(activity, TestUizaVideoIMActivityRl.class);
        startActivity(intent);
        LActivityUtil.tranIn(activity);
    }
}