package com.montnets.liveroom;

import android.app.Application;

import com.montnets.liveroom.upnp.UpnpServiceBiz;
import com.montnets.mwlive.LiveRoom;
import com.montnets.mwlive.LiveRoomConfig;

/**
 * Created by songlei on 2018/11/17.
 */
public class SampleApplication extends Application {
    private static SampleApplication app;

    public static SampleApplication getApp() {
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        LiveRoomConfig liveRoomConfig = new LiveRoomConfig();
        LiveRoomConfig.sAppContext = this;
        liveRoomConfig.key = "762454314ef4e11ba488b9960d48e5d9";
        LiveRoom.getInstance().initConfig(liveRoomConfig, new LiveRoom.OnConfigListener() {
            @Override
            public void onSuccess() {
                //校验key成功
            }

            @Override
            public void onError(String msg) {
                //校验key失败
            }
        });
        LiveRoom.getInstance().debug(true);
    }

    @Override
    public void onTerminate() {
        UpnpServiceBiz.newInstance().closeUpnpService(this);
        super.onTerminate();
    }

}
