package com.dazhong.idan;

import java.io.Serializable;

import android.util.Log;

public class NoteInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -113091942551031103L;
	/**
	 * 
	 */
	// private static final long serialVersionUID = 1L;
	/**
	 * 路单日期
	 * */
	private String NoteDate;
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
	 * 业务员姓名
	 */
	private String SaleName;
	/**
	 * 停车费
	 * */
	private double FeePark = 0;
	/**
	 * 路桥费
	 */
	private double FeeBridge = 0;
	/**
	 * 住宿费
	 */
	private double FeeHotel = 0;
	/**
	 * 餐费
	 */
	private double FeeLunch = 0;
	/**
	 * 其它费用
	 */
	private double FeeOther = 0;
	/**
	 * 修正费
	 * */
	private double FeeBack=0;
	/**
	 * 超里程费
	 */
	private double FeeOverKMs = 0;
	/**
	 * 超时间费
	 */
	private double FeeOverTime = 0;
	/**
	 * 销售报价
	 */
	private double FeePrice = 0;
	/**
	 * 总金额
	 */
	private double FeeTotal = 0;
	/**
	 * 下车地址
	 */
	private String LeaveAddress = "";
	/**
	 * 路单编号
	 */
	private String NoteID;
	/**
	 * 上车地址
	 */
	private String OnBoardAddress = "";
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
	private String ServiceBegin = "";
	/**
	 * 服务结束时间
	 */
	private String ServiceEnd = "";
	/**
	 * 服务里程
	 */
	private int ServiceKMs = 0;
	/**
	 * 服务时间
	 */
	private int ServiceTime = 0;
	/**
	 * 超公里
	 * */
	private int OverKMs = 0;
	/**
	 * 超小时
	 * */
	private int OverHours = 0; // 超15分钟算一个小时
	/**
	 * 收费选择
	 * */
	private int FeeChoice;
	/**
	 * 服务轨迹
	 * */
	private String ServiceRoute = "";
	/**
	 * 结算的超公里或超小时的费用
	 * */
	private double FeeOverCal = 0;
	/**
	 * 客人的公司名称
	 * */
	private String CustomerCompany = "";
	/**
	 * 客人名称
	 * */
	private String CustomerName = "";
/**
 *  实际服务里程
 * */
	private int DoServiceKms;
		/**
	 * 实际服务时间
	 * */
	private int DoServiceTime;
	/**
	 * 调度单序号
	 * */
	private String TaskID;
	/**
	 * 业务类型
	 * */
	private String ServiceType;
	/**
	 * 业务类型名称
	 * */
	private String ServiceTypeName;
	
	private int outfeetype;
	private int bridgefeetype;
	private double invoiceTaxRate;
	private String balanceType;
	private String balanceTypeName;
	private String invoiceType;
	
	public String getSaleName() {
		return SaleName;
	}
	public void setSaleName(String saleName) {
		SaleName = saleName;
	}
	/**
	 * 结算方式
	 * */
	public String getInvoiceType() {
		return invoiceType;
	}
	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}
	
	/**
	 * 结算方式
	 * */
	public String getBalanceType() {
		return balanceType;
	}
	public void setBalanceType(String balanceType) {
		this.balanceType = balanceType;
	}
	
	/**
	 * 结算方式名称
	 * */
	public String getBalanceTypeName() {
		return balanceTypeName;
	}
	public void setBalanceTypeName(String balanceTypeName) {
		this.balanceTypeName = balanceTypeName;
	}
	
	/**
	 * 税率
	 * */
	public double getInvoiceTaxRate() {
		return invoiceTaxRate;
	}
	public void setInvoiceTaxRate(double invoiceTaxRate) {
		this.invoiceTaxRate = invoiceTaxRate;
	}
	
	/**
	 * 住宿费是否计入总费用 0-计入 1-不计
	 * */
	public int getOutfeetype() {
		return outfeetype;
	}
	public void setOutfeetype(int outfeetype) {
		this.outfeetype = outfeetype;
	}
	/**
	 * 路桥费是否计入总费用 0-计入 1-不计
	 * */
	public int getBridgefeetype() {
		return bridgefeetype;
	}
	public void setBridgefeetype(int bridgefeetype) {
		this.bridgefeetype = bridgefeetype;
	}
	
	public String getServiceType() {
		return ServiceType;
	}

	public void setServiceType(String serviceType) {
		ServiceType = serviceType;
	}

	public String getServiceTypeName() {
		return ServiceTypeName;
	}

	public void setServiceTypeName(String serviceTypeName) {
		ServiceTypeName = serviceTypeName;
	}

	public String getTaskID() {
		return TaskID;
	}

	public void setTaskID(String taskID) {
		TaskID = taskID;
	}

	public String getNoteDate() {
		return NoteDate;
	}

	public void setNoteDate(String noteDate) {
		NoteDate = noteDate;
	}

	public String getServiceRoute() {
		return ServiceRoute;
	}

	public void setServiceRoute(String serviceRoute) {
		ServiceRoute = serviceRoute;
	}

	public double getFeeOverCal() {
		return FeeOverCal;
	}

	public void setFeeOverCal(double feeOverCal) {
		FeeOverCal = feeOverCal;
	}

	public int getFeeChoice() {
		return FeeChoice;
	}

	public void setFeeChoice(int feeChoice) {
		FeeChoice = feeChoice;
	}

	public int getOverKMs() {
		return OverKMs;
	}

	public void setOverKMs(int overKMs) {
		OverKMs = overKMs;
	}

	public int getOverHours() {
		return OverHours;
	}

	public void setOverHours(int overHours) {
		OverHours = overHours;
	}

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

	public int getServiceKMs() {
		return ServiceKMs;
	}

	public void setServiceKMs(int serviceKMs) {
		ServiceKMs = serviceKMs;
	}

	public int getServiceTime() {
		return ServiceTime;
	}

	public void setServiceTime(int serviceTime) {
		ServiceTime = serviceTime;
	}

	public String getCustomerCompany() {
		return CustomerCompany;
	}

	public void setCustomerCompany(String customerCompany) {
		CustomerCompany = customerCompany;
	}

	public String getCustomerName() {
		return CustomerName;
	}

	public void setCustomerName(String customerName) {
		CustomerName = customerName;
	}

	public double getFeePark() {
		return FeePark;
	}

	public void setFeePark(double feePark) {
		FeePark = feePark;
	}
	public int getDoServiceKms() {
		return DoServiceKms;
	}

	public void setDoServiceKms(int doServiceKms) {
		DoServiceKms = doServiceKms;
	}

	public int getDoServiceTime() {
		return DoServiceTime;
	}

	public void setDoServiceTime(int doServiceTime) {
		DoServiceTime = doServiceTime;
	}
	public double getFeeBack() {
		return FeeBack;
	}

	public void setFeeBack(double feeBack) {
		FeeBack = feeBack;
	}

	public NoteInfo() {

	}

	public NoteInfo(String carID, String carNumber, String driverID,
			String driverName, double feeBridge, double feeHotel,
			double feeLunch, double feeOther, double feeOverKMs,
			double feeOverTime, double feePrice, double feeTotal,
			String leaveAddress, String noteID, String onBoardAddress,
			String planID, String routeBegin, String routeEnd,
			String serviceBegin, String serviceEnd, int serviceKMs,
			int serviceTime, int overKMs, int overHours, int feeChoice,
			double feeOverCal, String serviceRoute, String customerCompany,
			String customerName, double feePark,int doservicekms,int doservicetime,
			String taskID,double feeback,String servicetype,String servicetypename) {
		super();
		CarID = carID;
		CarNumber = carNumber;
		DriverID = driverID;
		DriverName = driverName;
		FeeBridge = feeBridge;
		FeeHotel = feeHotel;
		FeeLunch = feeLunch;
		FeeOther = feeOther;
		FeeOverKMs = feeOverKMs;
		FeeOverTime = feeOverTime;
		FeePrice = feePrice;
		FeeTotal = feeTotal;
		LeaveAddress = leaveAddress;
		NoteID = noteID;
		OnBoardAddress = onBoardAddress;
		PlanID = planID;
		RouteBegin = routeBegin;
		RouteEnd = routeEnd;
		ServiceBegin = serviceBegin;
		ServiceEnd = serviceEnd;
		ServiceKMs = serviceKMs;
		ServiceTime = serviceTime;
		OverKMs = overKMs;
		OverHours = overHours;
		FeeChoice = feeChoice;
		FeeOverCal = feeOverCal;
		ServiceRoute = serviceRoute;
		CustomerCompany = customerCompany;
		CustomerName = customerName;
		FeePark = feePark;
		DoServiceKms=doservicekms;
		DoServiceTime=doservicetime;
		FeeBack=feeback;
		TaskID=taskID;
		ServiceType=servicetype;
		ServiceTypeName=servicetypename;
	}

	/**
	 * 上传到服务器的格式
	 * */
	public String toUploadNote() {
		Log.i("jxb", "NoteInfo [" + NoteID + "," + TaskID + "," + CarID + ","
				+ CarNumber + "," + DriverID + "," + DriverName + ","
				+ FeeBridge + "," + FeeHotel + "," + FeeLunch + "," + FeeOther
				+ "," + FeeOverKMs + "," + FeeOverTime + "," + FeePrice + ","
				+ FeeTotal + "," + LeaveAddress.replace(",", "$$") + ","
				+ OnBoardAddress.replace(",", "$$") + "," + RouteBegin + ","
				+ RouteEnd + "," + ServiceBegin + "," + ServiceEnd + ","
				+ DoServiceKms + "," + DoServiceTime + "," + OverKMs + ","
				+ OverHours + "," + FeeChoice + "," + FeeOverCal + ","
				+ ServiceRoute.replace(",", "$$") + "," 
				+ FeePark+","+NoteDate.replaceAll("-", "") + "]");
		return "NoteInfo [" + NoteID + "," + TaskID + "," + CarID + ","
				+ CarNumber + "," + DriverID + "," + DriverName + ","
				+ FeeBridge + "," + FeeHotel + "," + FeeLunch + "," + FeeOther
				+ "," + FeeOverKMs + "," + FeeOverTime + "," + FeePrice + ","
				+ FeeTotal + "," + LeaveAddress.replace(",", "$$") + ","
				+ OnBoardAddress.replace(",", "$$") + "," + RouteBegin + ","
				+ RouteEnd + "," + ServiceBegin + "," + ServiceEnd + ","
				+ DoServiceKms + "," + DoServiceTime + "," + OverKMs + ","
				+ OverHours + "," + FeeChoice + "," + FeeOverCal + ","
				+ ServiceRoute.replace(",", "$$") + "," 
				+ FeePark+","+NoteDate.replaceAll("-", "") + "]";
	}

	@Override
	public String toString() {
		return "NoteInfo [" + NoteID + "," + PlanID + "," + CarID + ","
				+ CarNumber + "," + DriverID + "," + DriverName + ","
				+ FeeBridge + "," + FeeHotel + "," + FeeLunch + "," + FeeOther
				+ "," + FeeOverKMs + "," + FeeOverTime + "," + FeePrice + ","
				+ FeeTotal + "," + LeaveAddress.replace(",", "$$") + ","
				+ OnBoardAddress.replace(",", "$$") + "," + RouteBegin + ","
				+ RouteEnd + "," + ServiceBegin + "," + ServiceEnd + ","
				+ ServiceKMs + "," + ServiceTime + "," + OverKMs + ","
				+ OverHours + "," + FeeChoice + "," + FeeOverCal + ","
				+ ServiceRoute + "," + CustomerCompany + ","
				+ CustomerName+","+FeePark+","+DoServiceKms+","+DoServiceTime+","
				+NoteDate+","+FeeBack+","+TaskID+","+ServiceType+","+ServiceTypeName
				+ "]";
	}

}
