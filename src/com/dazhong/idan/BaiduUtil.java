package com.dazhong.idan;

import android.content.Intent;

import com.baidu.trace.OnStartTraceListener;
import com.baidu.trace.OnStopTraceListener;

public class BaiduUtil {

	private myApplication trackApplication;
	private Intent serviceIntent = null;
	
	/**
     * �����켣����
     */
    public void startTrace(myApplication trackApp,OnStartTraceListener startTraceListener) {
    	trackApplication = trackApp;
        // ͨ���켣����ͻ���client�����켣����
        trackApp.getClient().startTrace(trackApp.getTrace(), startTraceListener);

        if (!MonitorService.isRunning) {
            // ��������service
        	MonitorService.isCheck = true;
        	MonitorService.isRunning = true;
//            startMonitorService();
            serviceIntent = new Intent(trackApp,
            		MonitorService.class);
            trackApp.startService(serviceIntent);
        }
    }
    
    /**
     * ֹͣ�켣����
     */
    public void stopTrace(myApplication trackApp,OnStopTraceListener stopTraceListener) {

        // ֹͣ����service
    	MonitorService.isCheck = false;
    	MonitorService.isRunning = false;

        // ͨ���켣����ͻ���clientֹͣ�켣����
        trackApp.getClient().stopTrace(trackApp.getTrace(), stopTraceListener);

        if (null != serviceIntent) {
            trackApp.stopService(serviceIntent);
        }
    }
	
}
