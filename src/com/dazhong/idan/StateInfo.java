package com.dazhong.idan;

import java.io.Serializable;

/**
 * @author davis
 * 
 */

public class StateInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7161724473272133612L;
	/*
	 * 当前状态 0:登陆失败 1:登录成功 2:上车路码 3:下车路码 11:准备服务 12:输入开始路码 13:服务中 14:驻车 15:输入结束路码
	 * 16:结算中 17:输入费用 18:打印预览 51:历史路单 52:调度明细 53:路单明细 54:调度明细 55:个人信息 56:修改密码
	 * 101:退出系统
	 */
	private int CurrentState;
	// 当前用户
	private PersonInfo CurrentPerson;
	// 当前调度单
	private TaskInfo CurrentTask;
	/* 当前路单 * */
	private NoteInfo CurrentNote;
	/* 调度页码 */
	private int PageOfTask;
	/* 历史路单页码 */
	private int PageOfNoteHistory;
	/* 最后输入的路码 */
	private String CurrentKMS;
	/* 当日路单次数 */
	private int TimeOfTaskOneDay;
	/* 今日上车时间 */
	private String TimeInCar;
	/* 今日下车时间 */
	private String TimeOffCar;
	/* 记录日期 */
	private String Today;
	// 当日上车路码
	private String BeginKMsOfToday;
	// 当日下车路码
	private String EndKMsOfToday;
	// 当前登录的状态，true 登录状态，false 登出状态
	private boolean CurrentLogin;

	/**获取当前登录状态
	 * */
	public boolean isCurrentLogin() {
		return CurrentLogin;
	}

	/**
	 * 设置当前登录状态
	 * */
	public void setCurrentLogin(boolean login) {
		CurrentLogin = login;
	}

	/**
	 * 获取当前状态 参数如下：
	 *  0:登陆失败 
	 *  1:登录成功 
	 *  2:上车路码 
	 *  3:下车路码
	 *  11:准备服务
	 *  12:输入开始路码 
	 *  13:服务中
	 *  14:驻车
	 *  15:输入结束路码 
	 *  16:结算中 
	 *  17:输入费用 
	 *  18:打印预览 
	 *  51:历史路单 
	 *  52:调度明细 
	 *  53:路单明细 
	 *  54:调度明细 
	 *  55:个人信息
	 *  56:修改密码 
	 *  101:退出系统
	 */
	public int getCurrentState() {
		return CurrentState;
	}

	/**
	 * 设置当前状态 参数如下：
	 *  0:登陆失败 
	 *  1:登录成功 
	 *  2:上车路码 
	 *  3:下车路码 
	 *  11:准备服务 
	 *  12:输入开始路码 
	 *  13:服务中 
	 *  14:驻车
	 *  15:输入结束路码 
	 *  16:结算中 
	 *  17:输入费用 
	 *  18:打印预览 
	 *  51:历史路单 
	 *  52:调度明细 
	 *  53:路单明细 
	 *  54:调度明细 
	 *  55:个人信息
	 *  56:修改密码 
	 *  101:退出系统
	 */
	public void setCurrentState(int currentState) {
		CurrentState = currentState;
	}

	public StateInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public StateInfo(int currentState, PersonInfo currentPerson,
			TaskInfo currentTask, NoteInfo currentNote, int pageOfTask,
			int pageOfNoteHistory, String currentKMS, int timeOfTaskOneDay,
			String timeInCar, String timeOffCar, String today,
			String beginKMsOfToday, String endKMsOfToday) {
		super();
		CurrentState = currentState;
		CurrentPerson = currentPerson;
		CurrentTask = currentTask;
		CurrentNote = currentNote;
		PageOfTask = pageOfTask;
		PageOfNoteHistory = pageOfNoteHistory;
		CurrentKMS = currentKMS;
		TimeOfTaskOneDay = timeOfTaskOneDay;
		TimeInCar = timeInCar;
		TimeOffCar = timeOffCar;
		Today = today;
		BeginKMsOfToday = beginKMsOfToday;
		EndKMsOfToday = endKMsOfToday;
	}

	@Override
	public String toString() {
		return "StateInfo [CurrentState=" + CurrentState + ", CurrentPerson="
				+ CurrentPerson + ", CurrentTask=" + CurrentTask
				+ ", CurrentNote=" + CurrentNote + ", PageOfTask=" + PageOfTask
				+ ", PageOfNoteHistory=" + PageOfNoteHistory + ", CurrentKMS="
				+ CurrentKMS + ", TimeOfTaskOneDay=" + TimeOfTaskOneDay
				+ ", TimeInCar=" + TimeInCar + ", TimeOffCar=" + TimeOffCar
				+ ", Today=" + Today + ", BeginKMsOfToday=" + BeginKMsOfToday
				+ ", EndKMsOfToday=" + EndKMsOfToday + "]";
	}

	/** 获取当前用户 */
	public PersonInfo getCurrentPerson() {
		return CurrentPerson;
	}

	/** 设置当前用户 */
	public void setCurrentPerson(PersonInfo currentPerson) {
		CurrentPerson = currentPerson;
	}

	/** 获取当前调度单 */
	public TaskInfo getCurrentTask() {
		return CurrentTask;
	}

	/** 设置当前调度单 */
	public void setCurrentTask(TaskInfo currentTask) {
		CurrentTask = currentTask;
	}

	/** 获取当前路单 */
	public NoteInfo getCurrentNote() {
		return CurrentNote;
	}

	/** 设置当前路单 */
	public void setCurrentNote(NoteInfo currentNote) {
		CurrentNote = currentNote;
	}

	/**
	 * 获取 浏览调度列表的当前页码！ 每天第一次开机时置为第一页 每页保留50条记录
	 * */
	public int getPageOfTask() {
		return PageOfTask;
	}

	/**
	 * 设置 浏览调度列表的当前页码！ 每天第一次开机时置为第一页 每页保留50条记录
	 * */
	public void setPageOfTask(int pageOfTask) {
		PageOfTask = pageOfTask;
	}

	/**
	 * 浏览 浏览历史路单列表的当前页码！ 每天第一次开机时置为第一页 每页保留50条记录
	 * */
	public int getPageOfNoteHistory() {
		return PageOfNoteHistory;
	}

	/**
	 * 设置 浏览历史路单列表的当前页码！ 每天第一次开机时置为第一页 每页保留50条记录
	 * */
	public void setPageOfNoteHistory(int pageOfNoteHistory) {
		PageOfNoteHistory = pageOfNoteHistory;
	}

	/** 获取最近输入的路码记录 */
	public String getCurrentKMS() {
		return CurrentKMS;
	}

	/** 设置最近输入的路码记录 */
	public void setCurrentKMS(String currentKMS) {
		CurrentKMS = currentKMS;
	}

	/** 获取当日执行路单的次数，最大不能超过999次 */
	public int getTimeOfTaskOneDay() {
		return TimeOfTaskOneDay;
	}

	/** 设置当日执行路单的次数，最大不能超过999次 */
	public void setTimeOfTaskOneDay(int timeOfTaskOneDay) {
		TimeOfTaskOneDay = timeOfTaskOneDay;
	}

	/** 获取当天第一次上车的时间，以HH：MM方式保存 */
	public String getTimeInCar() {
		return TimeInCar;
	}

	/** 设置当天第一次上车的时间，以HH：MM方式保存 */
	public void setTimeInCar(String timeInCar) {
		TimeInCar = timeInCar;
	}

	/** 获取当天最后一次下车的时间，以HH：MM方式保存 */
	public String getTimeOffCar() {
		return TimeOffCar;
	}

	/** 设置当天最后一次下车的时间，以HH：MM方式保存 */
	public void setTimeOffCar(String timeOffCar) {
		TimeOffCar = timeOffCar;
	}

	/** 获取当天的日期信息，以yyyy-mm-dd方式保存 */
	public String getToday() {
		return Today;
	}

	/** 设置当天的日期信息，以yyyy-mm-dd方式保存 */
	public void setToday(String today) {
		Today = today;
	}

	/** 获取当天的上车路码 */
	public String getBeginKMsOfToday() {
		return BeginKMsOfToday;
	}

	/** 设置当天的上车路码 */
	public void setBeginKMsOfToday(String beginKMsOfToday) {
		BeginKMsOfToday = beginKMsOfToday;
	}

	/** 获取当天的下车路码 */
	public String getEndKMsOfToday() {
		return EndKMsOfToday;
	}

	/** 设置当天的下车路码 */
	public void setEndKMsOfToday(String endKMsOfToday) {
		EndKMsOfToday = endKMsOfToday;
	}

}
