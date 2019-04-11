package com.montnets.liveroom;

import android.app.Application;
import android.content.Context;

import com.montnets.liveroom.upnp.UpnpServiceBiz;
import com.montnets.mwlive.LiveRoom;
import com.montnets.mwlive.LiveRoomConfig;

/**
 * Created by songlei on 2018/11/17.
 */
public class SampleApplication extends Application {
    private static SampleApplication app;
//    private RefWatcher refWatcher;

    public static SampleApplication getApp() {
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
//        refWatcher = setupLeakCanary();
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

//    private RefWatcher setupLeakCanary() {
//        if (LeakCanary.isInAnalyzerProcess(this)) {
//            return RefWatcher.DISABLED;
//        }
//        return LeakCanary.install(this);
//    }

//    public static RefWatcher getRefWatcher(Context context){
//        return app.refWatcher;
//    }

    @Override
    public void onTerminate() {
        UpnpServiceBiz.newInstance().closeUpnpService(this);
        super.onTerminate();
    }

}
