package testlibuiza.app;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.google.gson.Gson;

import vn.loitp.core.common.Constants;
import vn.loitp.core.utilities.LPref;
import vn.loitp.data.ActivityData;
import vn.loitp.restapi.restclient.RestClientTracking;
import vn.loitp.restapi.restclient.RestClientV2;
import vn.loitp.restapi.uiza.model.v2.auth.Auth;
import vn.loitp.utils.util.Utils;

public class LSApplication extends MultiDexApplication {
    private static LSApplication instance;
    private Gson gson;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        if (gson == null) {
            gson = new Gson();
        }
        Utils.init(this);
        //config activity transition default
        ActivityData.getInstance().setType(Constants.TYPE_ACTIVITY_TRANSITION_SLIDEUP);

        //init uiza
        RestClientV2.init(Constants.URL_DEV_UIZA_VERSION_2_DEMO);
        RestClientTracking.init(Constants.URL_TRACKING_PROD);

        Auth auth = getDummyAuth();
        RestClientV2.addAuthorization(auth.getData().getToken());
        LPref.setAuth(getContext(), auth, gson);
    }

    public Gson getGson() {
        return gson;
    }

    public static LSApplication getInstance() {
        return instance;
    }

    public static Context getContext() {
        return instance.getApplicationContext();
    }

    private Auth getDummyAuth() {
        String json = "{\"code\":200,\"data\":{\"appId\":\"0fa01cc4bc264023850069c3e07a0a38\",\"expired\":\"01/06/2018 10:43:17\",\"token\":\"3fcc5411-399e-4607-9991-6ab5d1c99e6e\"},\"datetime\":\"2018-05-02T10:43:17.180Z\",\"message\":\"ok\",\"name\":\"Resource\",\"type\":\"SUCCESS\",\"version\":2}";
        return gson.fromJson(json, Auth.class);
    }
}
