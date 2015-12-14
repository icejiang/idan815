package com.dazhong.idan;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Application;

public class iDanApp {
	private String SERVICEADRRESS = "http://192.168.75.200:8084/DriverService.asmx";
	private String USERNAME = "";// 用户名称
	private String WORKNUMBER = "";// 工号
	private String EMPLOYEEID = "";// 系统代码
	private StateInfo stateInfo = null;// 状态管理
	private List<TaskInfo> tasklist = null;
	private static iDanApp instance = null;

	// public static List<Activity> activities = new ArrayList<Activity>();
	//
	// public static void addActivity(Activity activity) {
	// activities.add(activity);
	// }
	//
	// public static void removeActivity(Activity activity) {
	// activities.remove(activity);
	// }
	//
	// public static void finishAll() {
	// for (Activity activity : activities) {
	// if (!activity.isFinishing()) {
	// activity.finish();
	// }
	// }
	//
	// }

	/**
	 * 获取Service地址
	 * */
	public String getSERVICEADRRESS() {
		return SERVICEADRRESS;
	}

	/**
	 * 设置Service地址
	 * */
	public void setSERVICEADRRESS(String sERVICEADRRESS) {
		SERVICEADRRESS = sERVICEADRRESS;
	}

	/**
	 * 获取用户名称
	 * */
	public String getUSERNAME() {
		return USERNAME;
	}

	/**
	 * 设置用户名称
	 * */
	public void setUSERNAME(String uSERNAME) {
		USERNAME = uSERNAME;
	}

	/**
	 * 获取用户工号
	 * */
	public String getWORKNUMBER() {
		return WORKNUMBER;
	}

	/**
	 * 设置用户工号
	 * */
	public void setWORKNUMBER(String wORKNUMBER) {
		WORKNUMBER = wORKNUMBER;
	}

	/**
	 * 获取用户编号
	 * */
	public String getEMPLOYEEID() {
		return EMPLOYEEID;
	}

	/**
	 * 设置用户编号
	 * */
	public void setEMPLOYEEID(String eMPLOYEEID) {
		EMPLOYEEID = eMPLOYEEID;
	}

	/**
	 * 获取当前状态
	 * */
	public StateInfo getStateInfo() {
		return stateInfo;
	}

	/**
	 * 设置当前状态
	 * */
	public void setStateInfo(StateInfo stateInfo) {
		this.stateInfo = stateInfo;
	}

	/**
	 * 获取调度列表
	 * */
	public List<TaskInfo> getTasklist() {
		return tasklist;
	}

	/**
	 * 设置调度列表
	 * */
	public void setTasklist(List<TaskInfo> tasklist) {
		this.tasklist = tasklist;
	}

//	@Override
//	public void onCreate() {
//		// TODO Auto-generated method stub
//		this.setSERVICEADRRESS("http://192.168.75.200:8084/DriverService.asmx");
//		super.onCreate();
//	}

	protected iDanApp() {
		super();
//		instance=this;
		// TODO Auto-generated constructor stub
	}

	public synchronized static iDanApp getInstance() {
		if (instance == null) {
			instance = new iDanApp();
		}
		return instance;
	}

}
