package com.dazhong.idan;

import java.lang.ref.WeakReference;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.widget.Toast;
import cn.jpush.android.api.JPushInterface;

import com.baidu.trace.LBSTraceClient;
import com.baidu.trace.LocationMode;
import com.baidu.trace.Trace;

public class myApplication extends Application {
	
	private static myApplication instance;

    public static myApplication getInstance() {
        return instance;
    }
	private Context mContext = null;

    /**
     * 轨迹服务
     */
    private Trace trace = null;

    /**
     * 轨迹服务客户端
     */
    private LBSTraceClient client = null;

    /**
     * 鹰眼服务ID，开发者创建的鹰眼服务对应的服务ID
     */
    private int serviceId = 128056;

    /**
     * entity标识
     */
    private String entityName = "";

    /**
     * 轨迹服务类型（0 : 不建立socket长连接， 1 : 建立socket长连接但不上传位置数据，2 : 建立socket长连接并上传位置数据）
     */
    private int traceType = 2;
    
    private TrackHandler mHandler = null;
	
		@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		JPushInterface.init(getApplicationContext());
		JPushInterface.setDebugMode(true);
		
		instance = this;
        mContext = getApplicationContext();
        TelephonyManager telephonyManager = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
        // 手机IMEI码
//        entityName = telephonyManager.getDeviceId();
        entityName = "xiaomi";
        // 采集周期
        int gatherInterval = 5;
        // 打包周期
        int packInterval = 15;

        // 初始化轨迹服务客户端
        client = new LBSTraceClient(mContext);

        // 初始化轨迹服务
        trace = new Trace(mContext, serviceId, entityName, traceType);

        // 设置采集和打包周期
        client.setInterval(gatherInterval, packInterval);
        
        // 设置定位模式
        client.setLocationMode(LocationMode.High_Accuracy);
        
        mHandler = new TrackHandler(this);
	}

	static class TrackHandler extends Handler {
        WeakReference<myApplication> trackApp;

        TrackHandler(myApplication trackApplication) {
            trackApp = new WeakReference<myApplication>(trackApplication);
        }

        @Override
        public void handleMessage(Message msg) {
            Toast.makeText(trackApp.get().mContext, (String) msg.obj, Toast.LENGTH_SHORT).show();
        }
    }
	
	public Context getmContext() {
        return mContext;
    }

    public Trace getTrace() {
        return trace;
    }

    public LBSTraceClient getClient() {
        return client;
    }

    public int getServiceId() {
        return serviceId;
    }

    public String getEntityName() {
        return entityName;
    }
    
    public Handler getmHandler() {
        return mHandler;
    }
	
}
