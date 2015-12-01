package com.dazhong.idan;

public class TaskInfo {
	private String bookman;
	private String booktel;
	private String carid;
	private String carnumber;
	private String cartype;
	private String customer;
	private String customertel;
	private String driverid;
	private String drivername;
	private String driverphone;
	private String frightin;
	private String frightnum;
	private String frightout;
	private int invoicetype;
	private String invoicetypename;
	private String leaveaddress;
	private int maxload;
	private String onboardtime;
	private String pickupaddress;
	private String planner;
	private String plannerremark;
	private String plannertel;
	private double salehotelfee;
	private double salekms;
	private double salelunchfee;
	private double saleotherfee;
	private double saleprice;
	private double salepriceperkm;
	private double salepriceperhour;
	private String salesman;
	private String salestel;
	private String salesremark;
	private double saletime;
	private String servicebegin;
	private String serviceend;
	private int servicetype;
	private String servicetypename;
	private String taskcode;
	private String taskid;
	private String taskcontract;
	
	/**
	 * 订车人
	 */
	public String Bookman()
	{
		return bookman;
	}
	/**
	 * 订车人电话
	 */
	public String BookTel()
	{
		return booktel;
	}
	/**
	 * 车辆代码
	 */
	public String CarID()
	{
		return carid;
	}
	/**
	 * 车牌号码
	 */
	public String CarNumber()
	{
		return carnumber;
	}
	/**
	 * 车型
	 */
	public String CarType()
	{
	return cartype;
	}
	/**
	 * 客人名称
	 */
	public String Customer()
	{
		return customer;
	}
	/**
	 * 客人电话
	 */
	public String CustomerTel()
	{
		return customertel;
	}
	/**
	 * 司机代码
	 */
	public String DriverID()
	{
		return driverid;
	}
	/**
	 * 司机名称
	 */
	public String DriverName()
	{
		return drivername;
	}
	/**
	 * 司机手机号码
	 */
	public String DriverPhone()
	{
		return driverphone;
	}
	// 航班进港时间
	public String FrightIn()
	{
		return frightin;
	}
	/**
	 * 航班号
	 */
	public String FrightNum()
	{
		return frightnum;
	}
	//航班出港时间
	public String FrightOut()
	{
		return frightout;
	}
	/**
	 * 发票类型
	 */
	public int InvoiceType()
	{
		return invoicetype;
	}
	/**
	 * 发票类型名称
	 */
	public String InvoiceTypeName()
	{
		return invoicetypename;
	}
	/**
	 * 下车地址
	 */
	public String LeaveAddress()
	{
		return leaveaddress;
	}
	/**
	 * 最大载客数
	 */
	public int MaxLoad()
	{
		return maxload;
	}
	/**
	 * 上车时间
	 */
	public String OnboardTime()
	{
		return onboardtime;
	}
	/**
	 * 上车地址
	 */
	public String PickupAddress()
	{
		return pickupaddress;
	}
	/**
	 * 调度人
	 */
	public String Planner()
	{
		return planner;
	}
	/**
	 * 调度员备注
	 */
	public String PlannerRemark()
	{
	return plannerremark;
	}
	/**
	 * 调度电话
	 */
	public String PlannerTel()
	{
		return plannertel;
	}
	/**
	 * 住宿费
	 */
	public double SaleHotelFee()
	{
		return salehotelfee;
	}
	/**
	 * 售卖的服务里程
	 */
	public double SaleKMs()
	{
		return salekms;
	}
	/**
	 * 餐费
	 */
	public double SaleLunchFee()
	{
		return salelunchfee;
	}
	/**
	 * 其它费用
	 */
	public double SaleOtherFee()
	{
		return saleotherfee;
	}
	/**
	 * 卖价
	 */
	public double SalePrice()
	{
		return saleprice;
	}
	/**
	 * 超公里的单价
	 */
	public double SalePricePerKM()
	{
		return salepriceperkm;
	}
	/**
	 * 超小时的单价
	 */
	public double SalePricePerHour()
	{
		return salepriceperhour;
	}
	/**
	 * 销售
	 */
	public String Salesman()
	{
		return salesman;
	}
	/**
	 * 销售电话
	 */
	public String SalesmanTel()
	{
		return salestel;
	}
	/**
	 * 业务员备注
	 */
	public String SalesRemark()
	{
		return salesremark;
	}
	/**
	 * 售卖的服务时间
	 */
	public double SaleTime()
	{
		return saletime;
	}
	/**
	 * 服务开始日期
	 */
	public String ServiceBegin()
	{
		return servicebegin;
	}
	/**
	 * 服务结束日期
	 */
	public String ServiceEnd()
	{
		return serviceend;
	}
	/**
	 * 业务类型
	 */
	public int ServiceType()
	{
		return servicetype;
	}
	/**
	 * 服务类型名字
	 */
	public String ServiceTypeName()
	{
		return servicetypename;
	}
	/**
	 * 调度单编号
	 */
	public String TaskCode()
	{
		return taskcode;
	}
	/**
	 * 服务对应的合同编号
	 */
	public String TaskContract()
	{
		return taskcontract;
	}
	
	/**
	 * 调度单序号
	 */
	public String TaskID()
	{
		return taskid;
	}
	
	public void setBookman(String bookman) {
		this.bookman = bookman;
	}
	public void setBooktel(String booktel) {
		this.booktel = booktel;
	}
	public void setCarid(String carid) {
		this.carid = carid;
	}
	public void setCarnumber(String carnumber) {
		this.carnumber = carnumber;
	}
	public void setCartype(String cartype) {
		this.cartype = cartype;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	public void setCustomertel(String customertel) {
		this.customertel = customertel;
	}
	public void setDriverid(String driverid) {
		this.driverid = driverid;
	}
	public void setDrivername(String drivername) {
		this.drivername = drivername;
	}
	public void setDriverphone(String driverphone) {
		this.driverphone = driverphone;
	}
	public void setFrightin(String frightin) {
		this.frightin = frightin;
	}
	public void setFrightnum(String frightnum) {
		this.frightnum = frightnum;
	}
	public void setFrightout(String frightout) {
		this.frightout = frightout;
	}
	public void setInvoicetype(int invoicetype) {
		this.invoicetype = invoicetype;
	}
	public void setInvoicetypename(String invoicetypename) {
		this.invoicetypename = invoicetypename;
	}
	public void setLeaveaddress(String leaveaddress) {
		this.leaveaddress = leaveaddress;
	}
	public void setMaxload(int maxload) {
		this.maxload = maxload;
	}
	public void setOnboardtime(String onboardtime) {
		this.onboardtime = onboardtime;
	}
	public void setPickupaddress(String pickupaddress) {
		this.pickupaddress = pickupaddress;
	}
	public void setPlanner(String planner) {
		this.planner = planner;
	}
	public void setPlannerremark(String plannerremark) {
		this.plannerremark = plannerremark;
	}
	public void setPlannertel(String plannertel) {
		this.plannertel = plannertel;
	}
	public void setSalehotelfee(double salehotelfee) {
		this.salehotelfee = salehotelfee;
	}
	public void setSalekms(double salekms) {
		this.salekms = salekms;
	}
	public void setSalelunchfee(double salelunchfee) {
		this.salelunchfee = salelunchfee;
	}
	public void setSaleotherfee(double saleotherfee) {
		this.saleotherfee = saleotherfee;
	}
	public void setSaleprice(double saleprice) {
		this.saleprice = saleprice;
	}
	public void setSalepriceperkm(double salepriceperkm) {
		this.salepriceperkm = salepriceperkm;
	}
	public void setSalepriceperhour(double salepriceperhour) {
		this.salepriceperhour = salepriceperhour;
	}
	public void setSalesman(String salesman) {
		this.salesman = salesman;
	}
	public void setSalestel(String salestel) {
		this.salestel = salestel;
	}
	public void setSalesremark(String salesremark) {
		this.salesremark = salesremark;
	}
	public void setSaletime(double saletime) {
		this.saletime = saletime;
	}
	public void setServicebegin(String servicebegin) {
		this.servicebegin = servicebegin;
	}
	public void setServiceend(String serviceend) {
		this.serviceend = serviceend;
	}
	public void setServicetype(int servicetype) {
		this.servicetype = servicetype;
	}
	public void setServicetypename(String servicetypename) {
		this.servicetypename = servicetypename;
	}
	public void setTaskcode(String taskcode) {
		this.taskcode = taskcode;
	}
	public void setTaskid(String taskid) {
		this.taskid = taskid;
	}
	public void setTaskcontract(String taskcontract) {
		this.taskcontract = taskcontract;
	}


	public TaskInfo(){

	}

}
