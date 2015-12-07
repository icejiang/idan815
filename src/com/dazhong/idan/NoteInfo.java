package com.dazhong.idan;

import java.io.Serializable;

public class NoteInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -113091942551031103L;
	/**
	 * 
	 */
//	private static final long serialVersionUID = 1L;
	/**
	 * 车辆代码
	 */
	private String CarID;
	/**
	 * 车牌号码
	 */
	private String CarNumber;
	/**
	 * 司机账号
	 */
	private String DriverID;
	/**
	 * 司机姓名
	 */
	private String DriverName;
	/**
	 * 路桥费
	 */
	private double FeeBridge;
	/**
	 * 住宿费
	 */
	private double FeeHotel;
	/**
	 * 餐费
	 */
	private double FeeLunch;
	/**
	 * 其它费用
	 */
	private double FeeOther;
	/**
	 * 超里程费
	 */
	private double FeeOverKMs;
	/**
	 * 超时间费
	 */
	private double FeeOverTime;
	/**
	 * 销售报价
	 */
	private double FeePrice;
	/**
	 * 总金额
	 */
	private double FeeTotal;
	/**
	 * 下车地址
	 */
	private String LeaveAddress;
	/**
	 * 路单编号
	 */
	private String NoteID;
	/**
	 * 上车地址
	 */
	private String OnBoardAddress;
	/**
	 * 调度单号
	 */
	private String PlanID;
	/**
	 * 开始路码
	 */
	private String RouteBegin;
	/**
	 * 结束路码
	 */
	private String RouteEnd;
	/**
	 * 服务开始时间
	 */
	private String ServiceBegin;
	/**
	 * 服务结束时间
	 */
	private String ServiceEnd;
	/**
	 * 服务里程
	 */
	private double ServiceKMs;
	/**
	 * 服务时间
	 */
	private double ServiceTime;

	public String getCarID() {
		return CarID;
	}

	public void setCarID(String carID) {
		CarID = carID;
	}

	public String getCarNumber() {
		return CarNumber;
	}

	public void setCarNumber(String carNumber) {
		CarNumber = carNumber;
	}

	public String getDriverID() {
		return DriverID;
	}

	public void setDriverID(String driverID) {
		DriverID = driverID;
	}

	public String getDriverName() {
		return DriverName;
	}

	public void setDriverName(String driverName) {
		DriverName = driverName;
	}

	public double getFeeBridge() {
		return FeeBridge;
	}

	public void setFeeBridge(double feeBridge) {
		FeeBridge = feeBridge;
	}

	public double getFeeHotel() {
		return FeeHotel;
	}

	public void setFeeHotel(double feeHotel) {
		FeeHotel = feeHotel;
	}

	public double getFeeLunch() {
		return FeeLunch;
	}

	public void setFeeLunch(double feeLunch) {
		FeeLunch = feeLunch;
	}

	public double getFeeOther() {
		return FeeOther;
	}

	public void setFeeOther(double feeOther) {
		FeeOther = feeOther;
	}

	public double getFeeOverKMs() {
		return FeeOverKMs;
	}

	public void setFeeOverKMs(double feeOverKMs) {
		FeeOverKMs = feeOverKMs;
	}

	public double getFeeOverTime() {
		return FeeOverTime;
	}

	public void setFeeOverTime(double feeOverTime) {
		FeeOverTime = feeOverTime;
	}

	public double getFeePrice() {
		return FeePrice;
	}

	public void setFeePrice(double feePrice) {
		FeePrice = feePrice;
	}

	public double getFeeTotal() {
		return FeeTotal;
	}

	public void setFeeTotal(double feeTotal) {
		FeeTotal = feeTotal;
	}

	public String getLeaveAddress() {
		return LeaveAddress;
	}

	public void setLeaveAddress(String leaveAddress) {
		LeaveAddress = leaveAddress;
	}

	public String getNoteID() {
		return NoteID;
	}

	public void setNoteID(String noteID) {
		NoteID = noteID;
	}

	public String getOnBoardAddress() {
		return OnBoardAddress;
	}

	public void setOnBoardAddress(String onBoardAddress) {
		OnBoardAddress = onBoardAddress;
	}

	public String getPlanID() {
		return PlanID;
	}

	public void setPlanID(String planID) {
		PlanID = planID;
	}

	public String getRouteBegin() {
		return RouteBegin;
	}

	public void setRouteBegin(String routeBegin) {
		RouteBegin = routeBegin;
	}

	public String getRouteEnd() {
		return RouteEnd;
	}

	public void setRouteEnd(String routeEnd) {
		RouteEnd = routeEnd;
	}

	public String getServiceBegin() {
		return ServiceBegin;
	}

	public void setServiceBegin(String serviceBegin) {
		ServiceBegin = serviceBegin;
	}

	public String getServiceEnd() {
		return ServiceEnd;
	}

	public void setServiceEnd(String serviceEnd) {
		ServiceEnd = serviceEnd;
	}

	public double getServiceKMs() {
		return ServiceKMs;
	}

	public void setServiceKMs(double serviceKMs) {
		ServiceKMs = serviceKMs;
	}

	public double getServiceTime() {
		return ServiceTime;
	}

	public void setServiceTime(double serviceTime) {
		ServiceTime = serviceTime;
	}

	public NoteInfo() {

	}

}
