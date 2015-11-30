package com.dazhong.idan;

import java.util.Stack;

import android.app.Activity;
import android.util.Log;

public class ScreenManager {

	
	private static Stack<Activity> activityStack;
	private static ScreenManager instance;

	private ScreenManager() {
	}

	public static ScreenManager getScreenManager() {
		if (instance == null) {
			instance = new ScreenManager();
		}
		return instance;
	}

	//yichu
	public void popActivity() {
		Activity activity = activityStack.lastElement();
		if (activity != null) {
			activity.finish();
			activity = null;
		}
	}

	public void popActivity(Activity activity) {
		if (activity != null) {
			activity.finish();
			Log.i("jxb", "remove current activity:" + activity.getClass().getSimpleName());  
			activityStack.remove(activity);
			activity = null;
		}
	}

	//∑µªÿ’ª∂•activity
	public Activity currentActivity() {
		if (activityStack == null || activityStack.size() == 0)  
        {  
            return null;  
        } 
		Activity activity = activityStack.lastElement();
		Log.i("jxb", "get current activity:" + activity.getClass().getSimpleName());
		return activity;
	}

	//tianjia
	public void pushActivity(Activity activity) {
		if (activityStack == null) {
			activityStack = new Stack<Activity>();
		}
		Log.i("jxb", "push stack activity:" + activity.getClass().getSimpleName());
		activityStack.add(activity);
	}

	public void popAllActivityExceptOne(Class cls) {
		while (true) {
			Activity activity = currentActivity();
			if (activity == null) {
				break;
			}
			if (activity.getClass().equals(cls)) {
				break;
			}
			popActivity(activity);
		}
	}
	
}
