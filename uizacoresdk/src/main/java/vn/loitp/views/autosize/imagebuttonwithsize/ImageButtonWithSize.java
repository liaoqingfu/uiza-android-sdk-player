package vn.loitp.views.autosize.imagebuttonwithsize;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageButton;

import vn.loitp.core.common.Constants;
import vn.loitp.core.utilities.LLog;
import vn.loitp.core.utilities.LScreenUtil;
import vn.loitp.utils.util.ConvertUtils;

/**
 * Created by LENOVO on 4/19/2018.
 */

public class ImageButtonWithSize extends ImageButton {
    private final String TAG = getClass().getSimpleName();

    public ImageButtonWithSize(Context context) {
        super(context);
        initSizeScreenW();
    }

    public ImageButtonWithSize(Context context, AttributeSet attrs) {
        super(context, attrs);
        initSizeScreenW();
    }

    public ImageButtonWithSize(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initSizeScreenW();
    }

    public ImageButtonWithSize(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initSizeScreenW();
    }

    private int screenWPortrait;
    private int screenWLandscape;

    private void initSizeScreenW() {
        screenWPortrait = LScreenUtil.getScreenWidth();
        screenWLandscape = LScreenUtil.getScreenHeightIncludeNavigationBar(this.getContext());

        int px = ConvertUtils.dp2px(5);
        setPadding(px, px, px, px);
    }

    private boolean isFullScreen;
    private boolean isSetSize;
    private int screenWidth;
    private int size;

    public void onMeasure(int widthSpec, int heightSpec) {
        //super.onMeasure(widthSpec, heightSpec);
        if (isSetSize && isFullScreen == LScreenUtil.isFullScreen(this.getContext())) {
            //LLog.d(TAG, "return isSetSize: " + isSetSize + " -> " + size + "x" + size);
            setMeasuredDimension(size, size);
            return;
        }
        isSetSize = false;
        isFullScreen = LScreenUtil.isFullScreen(this.getContext());

        //LLog.d(TAG, "isFullScreen " + isFullScreen);
        if (isFullScreen) {
            screenWidth = screenWLandscape;
        } else {
            screenWidth = screenWPortrait;
        }
        //LLog.d(TAG, "screenWidth " + screenWidth);
        if (isFullScreen) {
            size = screenWidth / getRatioLand();
        } else {
            size = screenWidth / getRatioPort();
        }
        LLog.d(TAG, "onMeasure: " + size + "x" + size);
        setMeasuredDimension(size, size);
        isSetSize = true;
    }

    private int ratioLand = Constants.NOT_FOUND;
    private int ratioPort = Constants.NOT_FOUND;

    public int getRatioLand() {
        return ratioLand == Constants.NOT_FOUND ? 16 : ratioLand;
    }

    public void setRatioLand(int ratioLand) {
        this.ratioLand = ratioLand;
        requestLayout();
    }

    public int getRatioPort() {
        return ratioPort == Constants.NOT_FOUND ? 12 : ratioPort;
    }

    public void setRatioPort(int ratioPort) {
        this.ratioPort = ratioPort;
        requestLayout();
    }
}
