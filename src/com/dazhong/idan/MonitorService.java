package com.dazhong.idan;

import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

public class MonitorService extends Service {

	private myApplication trackApp = null;

    protected static boolean isCheck = false;

    protected static boolean isRunning = false;

    private static final String SERVICE_NAME = "com.baidu.trace.LBSTraceService";

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        System.out.println("MonitorService onCreate");
        trackApp = (myApplication) getApplication();
        super.onCreate();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // TODO Auto-generated method stub

        System.out.println("MonitorService onStartCommand");

        new Thread() {

            @Override
            public void run() {
                // TODO Auto-generated method stub

                while (isCheck) {
                    try {
                        Thread.sleep(5 * 1000);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        System.out.println("thread sleep failed");
                    }

                    if (!isServiceWork(getApplicationContext(), SERVICE_NAME)) {
                        System.out.println("�켣������ֹͣ�������켣����");
                        if (null != trackApp.getClient() && null != trackApp.getTrace()) {
                            // trackApp.getClient().startTrace(trackApp.getTrace());
                        }
                    } else {
                        System.out.println("�켣������������");
                    }

                }
            }

        }.start();

        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * �ж�ĳ�������Ƿ��������еķ���
     * 
     * @param mContext
     * @param serviceName �ǰ���+��������������磺com.baidu.trace.LBSTraceService��
     * @return true�����������У�false�������û����������
     */
    public boolean isServiceWork(Context mContext, String serviceName) {
        boolean isWork = false;
        ActivityManager myAM = (ActivityManager) mContext
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningServiceInfo> myList = myAM.getRunningServices(80);
        if (myList.size() <= 0) {
            return false;
        }
        for (int i = 0; i < myList.size(); i++) {
            String mName = myList.get(i).service.getClassName().toString();
            if (mName.equals(serviceName)) {
                isWork = true;
                break;
            }
        }
        return isWork;
    }

}
