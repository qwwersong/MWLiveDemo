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
//    private RefWatcher refWatcher;

    public static SampleApplication getApp() {
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
//        refWatcher = setupLeakCanary();
        app = this;
        LiveRoomConfig.sAppContext = this;
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
