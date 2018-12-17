package com.montnets.liveroom.upnp;

import com.montnets.liveroom.upnp.callback.SetAVTransportURI;
import com.montnets.mwlive.base.LogUtil;

import org.fourthline.cling.model.action.ActionInvocation;
import org.fourthline.cling.model.message.UpnpResponse;
import org.fourthline.cling.model.meta.Device;
import org.fourthline.cling.model.meta.Service;
import org.fourthline.cling.model.types.UDAServiceType;
import org.fourthline.cling.model.types.UnsignedIntegerFourBytes;

import java.util.ArrayList;
import java.util.List;

public class DLNAManager {
    private static final String TAG = "DLNAManager";
//    private static volatile DLNAManager mInstance;
//    public static final int SUCCESS = 0;
//    public static final int FAILURE = 1;
    private Service serviceAVT;
    private Service serviceRC;
    private UpnpServiceBiz upnpBiz;
    private UnsignedIntegerFourBytes instanceId;

//    public static DLNAManager getInstance() {
//        if (mInstance == null) {
//            synchronized (DLNAManager.class) {
//                if (mInstance == null) {
//                    mInstance = new DLNAManager();
//                }
//            }
//        }
//        return mInstance;
//    }

    public DLNAManager(){
        upnpBiz = UpnpServiceBiz.newInstance();
        upnpBiz.addListener(upnpListener);
    }

    UpnpRegistryListener upnpListener = new UpnpRegistryListener() {
        @Override
        public void deviceAdded(Device device) {

        }

        @Override
        public void deviceRemoved(Device device) {

        }
    };

    /**
     * 查找upnp设备
     */
    public void searchDevices() {
        if (upnpBiz != null) {
            // 清空list中数据
            upnpBiz.removeAllRemoteDevices();
            upnpBiz.search();
        }
    }

    public List<Device> getDevices(){
        List<Device> data = new ArrayList<>(
                UpnpServiceBiz.newInstance().getDevices(new UDAServiceType("AVTransport")));
        return data;
    }

    public void transToTV(String url, Device device){
        serviceAVT = device.findService(new UDAServiceType("AVTransport", 1));
        serviceRC = device.findService(new UDAServiceType("RenderingControl", 1));
        instanceId = new UnsignedIntegerFourBytes(0);

//        DIDLContent didlContent = new DIDLContent();
//        didlContent.addItem(item);
//        DIDLParser parser = new DIDLParser();

//        try {
//            metadata = parser.generate(didlContent);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        String metadata = "";
        upnpBiz.execute(new SetAVTransportURI(instanceId, serviceAVT, url,
                metadata) {

            @Override
            public void failure(ActionInvocation invocation,
                                UpnpResponse operation, String defaultMsg) {
                LogUtil.d(TAG, "setPlayUri failure:" + defaultMsg);
            }

            @Override
            public void onSuccess(String defaultMsg) {
                LogUtil.d(TAG, "SetAVTransportURI successed:" + defaultMsg);
//                Message msg = Message.obtain(handler);
//                msg.what = MediaControlWhat.SET_AVTRANSPORT_URI;
//                msg.arg1 = SUCCESS;
//                msg.sendToTarget();
            }
        });
    }

    public void stop(){
        if (upnpBiz != null) {
            upnpBiz.removeListener(upnpListener);
            upnpBiz = null;
        }
    }
}
