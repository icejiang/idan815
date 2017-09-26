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
	 * ·������
	 * */
	private String NoteDate;
	/**
	 * ��������
	 */
	private String CarID;
	/**
	 * ���ƺ���
	 */
	private String CarNumber;
	/**
	 * ˾���˺�
	 */
	private String DriverID;
	/**
	 * ˾������
	 */
	private String DriverName;
	/**
	 * ҵ��Ա����
	 */
	private String SaleName;
	/**
	 * ͣ����
	 * */
	private double FeePark = 0;
	/**
	 * ·�ŷ�
	 */
	private double FeeBridge = 0;
	/**
	 * ס�޷�
	 */
	private double FeeHotel = 0;
	/**
	 * �ͷ�
	 */
	private double FeeLunch = 0;
	/**
	 * ��������
	 */
	private double FeeOther = 0;
	/**
	 * ������
	 * */
	private double FeeBack=0;
	/**
	 * ����̷�
	 */
	private double FeeOverKMs = 0;
	/**
	 * ��ʱ���
	 */
	private double FeeOverTime = 0;
	/**
	 * ���۱���
	 */
	private double FeePrice = 0;
	/**
	 * �ܽ��
	 */
	private double FeeTotal = 0;
	/**
	 * �³���ַ
	 */
	private String LeaveAddress = "";
	/**
	 * ·�����
	 */
	private String NoteID;
	/**
	 * �ϳ���ַ
	 */
	private String OnBoardAddress = "";
	/**
	 * ���ȵ���
	 */
	private String PlanID;
	/**
	 * ��ʼ·��
	 */
	private String RouteBegin;
	/**
	 * ����·��
	 */
	private String RouteEnd;
	/**
	 * ����ʼʱ��
	 */
	private String ServiceBegin = "";
	/**
	 * �������ʱ��
	 */
	private String ServiceEnd = "";
	/**
	 * �������
	 */
	private int ServiceKMs = 0;
	/**
	 * ����ʱ��
	 */
	private int ServiceTime = 0;
	/**
	 * ������
	 * */
	private int OverKMs = 0;
	/**
	 * ��Сʱ
	 * */
	private int OverHours = 0; // ��15������һ��Сʱ
	/**
	 * �շ�ѡ��
	 * */
	private int FeeChoice;
	/**
	 * ����켣
	 * */
	private String ServiceRoute = "";
	/**
	 * ����ĳ������Сʱ�ķ���
	 * */
	private double FeeOverCal = 0;
	/**
	 * ���˵Ĺ�˾����
	 * */
	private String CustomerCompany = "";
	/**
	 * ��������
	 * */
	private String CustomerName = "";
/**
 *  ʵ�ʷ������
 * */
	private int DoServiceKms;
		/**
	 * ʵ�ʷ���ʱ��
	 * */
	private int DoServiceTime;
	/**
	 * ���ȵ����
	 * */
	private String TaskID;
	/**
	 * ҵ������
	 * */
	private String ServiceType;
	/**
	 * ҵ����������
	 * */
	private String ServiceTypeName;
	/**
	 * ��ʼ��ͣʱ��·��
	 * */
	private int pauseStart = 0;
	/**
	 * ������ͣʱ��·��
	 * */
	private int pauseEnd = 0;
	private int outfeetype;
	private int bridgefeetype;
	private double invoiceTaxRate;
	private String balanceType;
	private String balanceTypeName;
	private String invoiceType;
	private double actualRentNoTax;
	private double exceedMileFeeNoTax;
	private double exceedTimeFeeNoTax;
	
	public double getActualRentNoTax() {
		return actualRentNoTax;
	}

	public void setActualRentNoTax(double actualRentNoTax) {
		this.actualRentNoTax = actualRentNoTax;
	}

	public double getExceedMileFeeNoTax() {
		return exceedMileFeeNoTax;
	}

	public void setExceedMileFeeNoTax(double exceedMileFeeNoTax) {
		this.exceedMileFeeNoTax = exceedMileFeeNoTax;
	}

	public double getExceedTimeFeeNoTax() {
		return exceedTimeFeeNoTax;
	}

	public void setExceedTimeFeeNoTax(double exceedTimeFeeNoTax) {
		this.exceedTimeFeeNoTax = exceedTimeFeeNoTax;
	}

	/**
	 * �Ƿ���ͣ��
	 * */
	private boolean hasPaused = false;
	/**
	 * ǩ����ַ
	 * */
	private String pictureAddress;
	/**
	 * ·���Ƿ����
	 * */
	private String routeCheck = "";
	/**
	 * ���ȵ���
	 * */
	private String taskCode;
	/**
	 * ��ʼʱ��
	 * */
	private int startTime;
	/**
	 * �ٶȲ���·��
	 * */
	private int routeAuto = -1;
	
	public int getStartTime() {
		return startTime;
	}

	public void setStartTime(int startTime) {
		this.startTime = startTime;
	}

	public int getRouteAuto() {
		return routeAuto;
	}

	public void setRouteAuto(int routeAuto) {
		this.routeAuto = routeAuto;
	}
	
	public String getTaskCode() {
		return taskCode;
	}

	public void setTaskCode(String taskCode) {
		this.taskCode = taskCode;
	}

	public String getRouteCheck() {
		return routeCheck;
	}

	public void setRouteCheck(String routeCheck) {
		this.routeCheck = routeCheck;
	}

	public String getPictureAddress() {
		return pictureAddress;
	}

	public void setPictureAddress(String pictureAddress) {
		this.pictureAddress = pictureAddress;
	}
	
	public boolean isHasPaused() {
		return hasPaused;
	}

	public void setHasPaused(boolean hasPaused) {
		this.hasPaused = hasPaused;
	}

	public int getPauseEnd(){
	  return this.pauseEnd;
	}
	  
	public int getPauseStart(){
	  return this.pauseStart;
	}
	
	public void setPauseEnd(int paramInt){
	  this.pauseEnd = paramInt;
	}
	  
	public void setPauseStart(int paramInt){
	  this.pauseStart = paramInt;
	}
	  
	public String getSaleName() {
		return SaleName;
	}
	public void setSaleName(String saleName) {
		SaleName = saleName;
	}
	/**
	 * ���㷽ʽ
	 * */
	public String getInvoiceType() {
		return invoiceType;
	}
	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}
	
	/**
	 * ���㷽ʽ
	 * */
	public String getBalanceType() {
		return balanceType;
	}
	public void setBalanceType(String balanceType) {
		this.balanceType = balanceType;
	}
	
	/**
	 * ���㷽ʽ����
	 * */
	public String getBalanceTypeName() {
		return balanceTypeName;
	}
	public void setBalanceTypeName(String balanceTypeName) {
		this.balanceTypeName = balanceTypeName;
	}
	
	/**
	 * ˰��
	 * */
	public double getInvoiceTaxRate() {
		return invoiceTaxRate;
	}
	public void setInvoiceTaxRate(double invoiceTaxRate) {
		this.invoiceTaxRate = invoiceTaxRate;
	}
	
	/**
	 * ס�޷��Ƿ�����ܷ��� 0-���� 1-����
	 * */
	public int getOutfeetype() {
		return outfeetype;
	}
	public void setOutfeetype(int outfeetype) {
		this.outfeetype = outfeetype;
	}
	/**
	 * ·�ŷ��Ƿ�����ܷ��� 0-���� 1-����
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
	 * �ϴ����������ĸ�ʽ
	 * */
	public String toUploadNote() {
		String noteString = "NoteInfo [" + NoteID + "," + TaskID + "," + CarID + ","
				+ CarNumber + "," + DriverID + "," + DriverName + ","
				+ FeeBridge + "," + FeeHotel + "," + FeeLunch + "," + FeeOther
				+ "," + FeeOverKMs + "," + FeeOverTime + "," + FeePrice + ","
				+ FeeTotal + "," + LeaveAddress.replace(",", ".") + ","
				+ OnBoardAddress.replace(",", ".") + "," + RouteBegin + ","
				+ RouteEnd + "," + ServiceBegin + "," + ServiceEnd + ","
				+ DoServiceKms + "," + DoServiceTime + "," + OverKMs + ","
				+ OverHours + "," + FeeChoice + "," + FeeOverCal + ","
				+ ServiceRoute.replace(",", ".") + "," 
				+ FeePark+","+NoteDate.replaceAll("-", "")+"," +pictureAddress+","+taskCode
				+","+routeAuto
				+"]";
		Log.i("jxb", "�ϴ�·����"+noteString);
		return noteString.replace("&", "-");
	}

	@Override
	public String toString() {
		return "NoteInfo [" + NoteID + "," + PlanID + "," + CarID + ","
				+ CarNumber + "," + DriverID + "," + DriverName + ","
				+ FeeBridge + "," + FeeHotel + "," + FeeLunch + "," + FeeOther
				+ "," + FeeOverKMs + "," + FeeOverTime + "," + FeePrice + ","
				+ FeeTotal + "," + LeaveAddress.replace(",", ".") + ","
				+ OnBoardAddress.replace(",", ".") + "," + RouteBegin + ","
				+ RouteEnd + "," + ServiceBegin + "," + ServiceEnd + ","
				+ ServiceKMs + "," + ServiceTime + "," + OverKMs + ","
				+ OverHours + "," + FeeChoice + "," + FeeOverCal + ","
				+ ServiceRoute + "," + CustomerCompany + ","
				+ CustomerName+","+FeePark+","+DoServiceKms+","+DoServiceTime+","
				+NoteDate+","+FeeBack+","+TaskID+","+ServiceType+","+ServiceTypeName
				+ "]";
	}

}
