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
     * �켣����
     */
    private Trace trace = null;

    /**
     * �켣����ͻ���
     */
    private LBSTraceClient client = null;

    /**
     * ӥ�۷���ID�������ߴ�����ӥ�۷����Ӧ�ķ���ID
     */
    private int serviceId = 128056;

    /**
     * entity��ʶ
     */
    private String entityName = "";

    /**
     * �켣�������ͣ�0 : ������socket�����ӣ� 1 : ����socket�����ӵ����ϴ�λ�����ݣ�2 : ����socket�����Ӳ��ϴ�λ�����ݣ�
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
        // �ֻ�IMEI��
//        entityName = telephonyManager.getDeviceId();
        entityName = "xiaomi";
        // �ɼ�����
        int gatherInterval = 5;
        // �������
        int packInterval = 15;

        // ��ʼ���켣����ͻ���
        client = new LBSTraceClient(mContext);

        // ��ʼ���켣����
        trace = new Trace(mContext, serviceId, entityName, traceType);

        // ���òɼ��ʹ������
        client.setInterval(gatherInterval, packInterval);
        
        // ���ö�λģʽ
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
