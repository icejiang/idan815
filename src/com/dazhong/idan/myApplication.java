package com.dazhong.idan;

import cn.jpush.android.api.JPushInterface;
import android.app.Application;

public class myApplication extends Application {
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		JPushInterface.init(getApplicationContext());
		JPushInterface.setDebugMode(true);
	}

}
