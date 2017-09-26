package com.dazhong.idan;

import android.content.Intent;

import com.baidu.trace.OnStartTraceListener;
import com.baidu.trace.OnStopTraceListener;

public class BaiduUtil {

	private myApplication trackApplication;
	private Intent serviceIntent = null;
	
	/**
     * 开启轨迹服务
     */
    public void startTrace(myApplication trackApp,OnStartTraceListener startTraceListener) {
    	trackApplication = trackApp;
        // 通过轨迹服务客户端client开启轨迹服务
        trackApp.getClient().startTrace(trackApp.getTrace(), startTraceListener);

        if (!MonitorService.isRunning) {
            // 开启监听service
        	MonitorService.isCheck = true;
        	MonitorService.isRunning = true;
//            startMonitorService();
            serviceIntent = new Intent(trackApp,
            		MonitorService.class);
            trackApp.startService(serviceIntent);
        }
    }
    
    /**
     * 停止轨迹服务
     */
    public void stopTrace(myApplication trackApp,OnStopTraceListener stopTraceListener) {

        // 停止监听service
    	MonitorService.isCheck = false;
    	MonitorService.isRunning = false;

        // 通过轨迹服务客户端client停止轨迹服务
        trackApp.getClient().stopTrace(trackApp.getTrace(), stopTraceListener);

        if (null != serviceIntent) {
            trackApp.stopService(serviceIntent);
        }
    }
	
}
