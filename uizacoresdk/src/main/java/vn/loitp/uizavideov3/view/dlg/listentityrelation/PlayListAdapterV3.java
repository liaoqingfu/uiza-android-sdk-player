package vn.loitp.uizavideov3.view.dlg.listentityrelation;

/**
 * Created by www.muathu@gmail.com on 11/7/2017.
 */

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;

import java.util.List;

import loitp.core.R;
import vn.loitp.core.common.Constants;
import vn.loitp.core.utilities.LAnimationUtil;
import vn.loitp.core.utilities.LImageUtil;
import vn.loitp.restapi.uiza.model.v2.listallentity.Item;
import vn.loitp.uizavideov3.util.UizaUtil;

public class PlayListAdapterV3 extends RecyclerView.Adapter<PlayListAdapterV3.PlayListHolder> {
    private final String TAG = getClass().getSimpleName();
    private List<Item> itemList;
    private Context context;
    private PlayListCallbackV3 playListCallbackV3;

    public class PlayListHolder extends RecyclerView.ViewHolder {
        private TextView tvDuration;
        private TextView tvDuration2;
        private ImageView ivCover;
        private TextView tvName;
        private TextView tvYear;
        private TextView tvRate;
        private TextView tvDescription;
        private LinearLayout rootView;

        public PlayListHolder(View view) {
            super(view);
            rootView = (LinearLayout) view.findViewById(R.id.root_view);
            tvDuration = (TextView) view.findViewById(R.id.tv_duration);
            tvDuration2 = (TextView) view.findViewById(R.id.tv_duration_2);
            tvName = (TextView) view.findViewById(R.id.tv_name);
            tvYear = (TextView) view.findViewById(R.id.tv_year);
            tvRate = (TextView) view.findViewById(R.id.tv_rate);
            tvDescription = (TextView) view.findViewById(R.id.tv_description);
            ivCover = (ImageView) view.findViewById(R.id.iv_cover);
        }
    }

    public PlayListAdapterV3(Context context, List<Item> itemList, PlayListCallbackV3 playListCallbackV3) {
        this.itemList = itemList;
        this.playListCallbackV3 = playListCallbackV3;
        this.context = context;
    }

    @Override
    public PlayListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_playlist, parent, false);
        return new PlayListHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PlayListHolder playListHolder, final int position) {
        final Item item = itemList.get(position);
        //LLog.d(TAG, "onBindViewHolder" + new Gson().toJson(item));

        //playListHolder.tvDuration.setText(item.getDuration());
        UizaUtil.setTextDuration(playListHolder.tvDuration, item.getDuration());
        //LLog.d(TAG, "item.getDuration(): " + item.getDuration());
        playListHolder.tvName.setText(item.getName());

        //TODO
        playListHolder.tvYear.setText("2018");
        UizaUtil.setTextDuration(playListHolder.tvDuration2, item.getDuration());

        //TODO
        playListHolder.tvRate.setText("18+");
        if (item.getShortDescription() == null || item.getShortDescription().isEmpty()) {
            if (item.getDescription() == null || item.getDescription().isEmpty()) {
                playListHolder.tvDescription.setVisibility(View.GONE);
            } else {
                playListHolder.tvDescription.setText(item.getDescription());
                playListHolder.tvDescription.setVisibility(View.VISIBLE);
            }
        } else {
            playListHolder.tvDescription.setText(item.getShortDescription());
        }

        //RelativeLayout.LayoutParams rootLayoutParams = new RelativeLayout.LayoutParams((int) (sizeWRoot / 3.5), sizeHRoot);
        //playListHolder.rootView.setLayoutParams(rootLayoutParams);

        //RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (sizeWRoot / 3.5 / 2));
        //playListHolder.ivCover.setLayoutParams(layoutParams);

        String thumbnail;
        if (item.getThumbnail() == null || item.getThumbnail().isEmpty()) {
            thumbnail = Constants.URL_IMG_THUMBNAIL;
        } else {
            thumbnail = Constants.PREFIXS_SHORT + item.getThumbnail();
        }
        //LLog.d(TAG, "getThumbnail " + thumbnail);
        LImageUtil.load((Activity) context, thumbnail, playListHolder.ivCover);

        playListHolder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //LLog.d(TAG, TAG + " click: " + item.getName() + ", position: " + position);
                LAnimationUtil.play(v, Techniques.Pulse, new LAnimationUtil.Callback() {
                    @Override
                    public void onCancel() {
                        //do nothing
                    }

                    @Override
                    public void onEnd() {
                        if (playListCallbackV3 != null) {
                            playListCallbackV3.onClickItem(item, position);
                        }
                    }

                    @Override
                    public void onRepeat() {
                        //do nothing
                    }

                    @Override
                    public void onStart() {
                        //do nothing
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList == null ? 0 : itemList.size();
    }
}