package vn.loitp.uizavideov3.view.dlg.playlistfolder;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;

import java.util.List;

import loitp.core.R;
import vn.loitp.core.utilities.LLog;
import vn.loitp.core.utilities.LUIUtil;
import vn.loitp.restapi.uiza.model.v3.metadata.getdetailofmetadata.Data;
import vn.loitp.uizavideo.view.util.UizaData;

/**
 * Created by loitp on 5/2/2018.
 */

public class UizaDialogPlaylistFolder extends Dialog {
    private final String TAG = getClass().getSimpleName();
    private Activity activity;
    private AlertDialog dialog;
    private boolean isLandscape;
    //private Gson gson = new Gson();
    //private ProgressBar progressBar;
    //private TextView tvMsg;
    private RecyclerView recyclerView;
    private AdapterPlaylistFolder adapterPlaylistFolder;

    private List<Data> dataList;
    private int currentPositionOfDataList;

    private CallbackPlaylistFolder callbackPlaylistFolder;

    public UizaDialogPlaylistFolder(Activity activity, boolean isLandscape, List<Data> dataList, int currentPositionOfDataList, CallbackPlaylistFolder callbackPlaylistFolder) {
        super(activity);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.activity = activity;
        this.isLandscape = isLandscape;
        this.dataList = dataList;
        this.currentPositionOfDataList = currentPositionOfDataList;
        this.callbackPlaylistFolder = callbackPlaylistFolder;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.v3_dialog_list_playlist_folder);

        //progressBar = (ProgressBar) findViewById(R.id.pb);
        //LUIUtil.setColorProgressBar(progressBar, ContextCompat.getColor(activity, R.color.colorPrimary));

        //tvMsg = (TextView) findViewById(R.id.tv_msg);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        findViewById(R.id.bt_exit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        setupUI();
    }

    private void setupUI() {
        recyclerView.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false));

        //recyclerView.setItemAnimator(new DefaultItemAnimator());
        //LLog.d(TAG, "--------> " + widthRecyclerView + " x " + heightRecyclerView);
        adapterPlaylistFolder = new AdapterPlaylistFolder(activity, dataList, currentPositionOfDataList, new CallbackPlaylistFolder() {
            @Override
            public void onClickItem(Data data, int position) {
                LLog.d(TAG, "onClickItem position: " + position);
                if (UizaData.getInstance().isSettingPlayer()) {
                    return;
                }
                dismiss();
                if (callbackPlaylistFolder != null) {
                    callbackPlaylistFolder.onClickItem(data, position);
                }
            }

            @Override
            public void onDismiss() {
                if (callbackPlaylistFolder != null) {
                    callbackPlaylistFolder.onDismiss();
                }
            }
        });
        recyclerView.setAdapter(adapterPlaylistFolder);
        LUIUtil.setPullLikeIOSHorizontal(recyclerView);
        recyclerView.smoothScrollToPosition(currentPositionOfDataList);
    }
}