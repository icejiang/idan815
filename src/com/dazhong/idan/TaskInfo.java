package com.dazhong.idan;

import java.io.Serializable;

public class TaskInfo  implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5637165045953489815L;
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
	private String invoicetype;
	private String invoicetypename;
	private String leaveaddress;
	private String maxload;
	private String onboardtime;
	private String pickupaddress;
	private String planner;
	private String plannerremark;
	private String plannertel;
	private double salehotelfee;
	private int salekms;
	private double salelunchfee;
	private double saleotherfee;
	private double saleprice;
	private double salepriceperkm;
	private double salepriceperhour;
	private String salesman;
	private String salestel;
	private String salesremark;
	private int saletime;
	private String servicebegin;
	private String serviceend;
	private int servicetype;
	private String servicetypename;
	private String taskcode;
	private String taskid;
	private String taskcontract;
	private int Readmark;
	private int salepricecal;
	private String salepricecalname;
	private String balancetype;
	private String balancetypename;
	private boolean done=false;
	private int outfeetype;
	private int bridgefeetype;
	private double bridgeFee;
	private String customerName;
	private int routeNoteCount;
	private String contacter;
	private String contacterNum;
	private double invoiceTaxRate;
	private double actualRentNoTax;
	private double exceedMileFeeNoTax;
	private double exceedTimeFeeNoTax;
	private String businessSource;
	
	public String getBusinessSource() {
		return businessSource;
	}

	public void setBusinessSource(String bussinessSource) {
		this.businessSource = bussinessSource;
	}

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
	private String textString; //测试用
	
	public String getTextString() {
		return textString;
	}

	public void setTextString(String textString) {
		this.textString = textString;
	}
	/**
	 * 业务是新增还是修改  1-新增 0-修改
	 * */
	private String isUpdate;
	
	public String getIsUpdate(){
		return this.isUpdate;
	}
	
	public void setIsUpdate(String isUpdate){
		this.isUpdate = isUpdate;
	}
	
	/**
	 * 上传次数： >0表示订单已完成
	 * */
	public void setRouteNoteCount(int routeNoteCount){
		this.routeNoteCount = routeNoteCount;
	}
	
	public int getRouteNoteCount(){
		return routeNoteCount;
	}
	
	/**
	 * 客户公司名称
	 * */
	public void setCustomerCompany(String customerName){
		this.customerName = customerName;
	}
	
	public String getCustomerCompany(){
		return customerName;
	}
	
	/**
	 * 路桥费
	 * */
	public double getFeeBridge() {
		return bridgeFee;
	}
	public void setFeeBridge(double bridgeFee) {
		this.bridgeFee = bridgeFee;
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
	 * 住宿费是否计入总费用 0-不计 1-计入
	 * */
	public int getOutfeetype() {
		return outfeetype;
	}
	public void setOutfeetype(int outfeetype) {
		this.outfeetype = outfeetype;
	}
	/**
	 * 路桥费是否计入总费用 0-不计 1-计入
	 * */
	public int getBridgefeetype() {
		return bridgefeetype;
	}
	public void setBridgefeetype(int bridgefeetype) {
		this.bridgefeetype = bridgefeetype;
	}
	/**
	 * 是否已完成
	 * */
	public boolean isDone() {
		return done;
	}
	/**
	 * 设置完成，false 未做，true 已做
	 * */
	public void setDone(boolean done) {
		this.done = done;
	}
	/**
	 * 结算方式
	 * */
	public String getBalancetype() {
		return balancetype;
	}
	public void setBalancetype(String balancetype) {
		this.balancetype = balancetype;
	}
	/**
	 * 结算方式名称
	 * */
	public String getBalancetypename() {
		return balancetypename;
	}
	public void setBalancetypename(String balancetypename) {
		this.balancetypename = balancetypename;
	}
	/**
	 * 超额计费选择的名称，双边或单侧收费
	 */
	public String SalePriceCalName() {
		return salepricecalname;
	}
	public void setSalepricecalname(String salepricecalname) {
		this.salepricecalname = salepricecalname;
	}
	/**
	 * 超额计费选择，双边或单侧收费
	 */
	public int SalePriceCal()
	{
		return salepricecal;
	}
	/**
	 * 超额计费选择，双边或单侧收费
	 */
	public void setSalePriceCal(int salePricecal)
	{
		this.salepricecal=salePricecal;
	}
	/**
	 * 阅读标记  1-已读 0-未读
	 * */
	public int getReadmark() {
		return Readmark;
	}
	public void setReadmark(int readmark) {
		Readmark = readmark;
	}
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
	/*** 航班进港时间*/
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
	/**航班出港时间*/
	public String FrightOut()
	{
		return frightout;
	}
	/**
	 * 发票类型
	 */
	public String InvoiceType()
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
	public String MaxLoad()
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
	 * 联系人
	 */
	public String getContacter()
	{
		return this.contacter;
	}
	public void setContacter(String contacter)
	{
		this.contacter = contacter;
	}
	/**
	 * 联系人电话
	 */
	public String getContacterNum()
	{
		return this.contacterNum;
	}
	public void setContacterNum(String contacterNum)
	{
		this.contacterNum = contacterNum;
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
	public int SaleKMs()
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
	public int SaleTime()
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
	/**
	 * 设置订车人
	 */
	public void setBookman(String bookman) {
		this.bookman = bookman;
	}
	/**
	 * 设置订车人电话
	 */
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
	public void setInvoicetype(String invoicetype) {
		this.invoicetype = invoicetype;
	}
	public void setInvoicetypename(String invoicetypename) {
		this.invoicetypename = invoicetypename;
	}
	public void setLeaveaddress(String leaveaddress) {
		this.leaveaddress = leaveaddress;
	}
	public void setMaxload(String maxload) {
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
	public void setSalekms(int salekms) {
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
	public void setSalepriceperhour(int salepriceperhour) {
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
	public void setSaletime(int saletime) {
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
	public TaskInfo(String bookman, String booktel, String carid,
			String carnumber, String cartype, String customer,
			String customertel, String driverid, String drivername,
			String driverphone, String frightin, String frightnum,
			String frightout, String invoicetype, String invoicetypename,
			String leaveaddress, String maxload, String onboardtime,
			String pickupaddress, String planner, String plannerremark,
			String plannertel, double salehotelfee, int salekms,
			double salelunchfee, double saleotherfee, double saleprice,
			double salepriceperkm, double salepriceperhour, String salesman,
			String salestel, String salesremark, int saletime,
			String servicebegin, String serviceend, int servicetype,
			String servicetypename, String taskcode, String taskid,
			String taskcontract,int readmark,int salepricecal,String salepricecalname,
			String balancetype,String balancetypename) {
		super();
		this.bookman = bookman;
		this.booktel = booktel;
		this.carid = carid;
		this.carnumber = carnumber;
		this.cartype = cartype;
		this.customer = customer;
		this.customertel = customertel;
		this.driverid = driverid;
		this.drivername = drivername;
		this.driverphone = driverphone;
		this.frightin = frightin;
		this.frightnum = frightnum;
		this.frightout = frightout;
		this.invoicetype = invoicetype;
		this.invoicetypename = invoicetypename;
		this.leaveaddress = leaveaddress;
		this.maxload = maxload;
		this.onboardtime = onboardtime;
		this.pickupaddress = pickupaddress;
		this.planner = planner;
		this.plannerremark = plannerremark;
		this.plannertel = plannertel;
		this.salehotelfee = salehotelfee;
		this.salekms = salekms;
		this.salelunchfee = salelunchfee;
		this.saleotherfee = saleotherfee;
		this.saleprice = saleprice;
		this.salepriceperkm = salepriceperkm;
		this.salepriceperhour = salepriceperhour;
		this.salesman = salesman;
		this.salestel = salestel;
		this.salesremark = salesremark;
		this.saletime = saletime;
		this.servicebegin = servicebegin;
		this.serviceend = serviceend;
		this.servicetype = servicetype;
		this.servicetypename = servicetypename;
		this.taskcode = taskcode;
		this.taskid = taskid;
		this.taskcontract = taskcontract;
		this.Readmark=readmark;
		this.salepricecal=salepricecal;
		this.salepricecalname=salepricecalname;
		this.balancetype=balancetype;
		this.balancetypename=balancetypename;
	}
	@Override
	public String toString() {
		return "TaskInfo [" + bookman + "," + booktel
				+ "," + carid + "," + carnumber
				+ "," + cartype + "," + customer
				+ "," + customertel + "," + driverid
				+ "," + drivername + "," + driverphone
				+ "," + frightin + "," + frightnum
				+ "," + frightout + "," + invoicetype
				+ "," + invoicetypename + ","
				+ leaveaddress + "," + maxload + ","
				+ onboardtime + "," + pickupaddress
				+ "," + planner + "," + plannerremark
				+ "," + plannertel + ","
				+ salehotelfee + "," + salekms + ","
				+ salelunchfee + "," + saleotherfee
				+ "," + saleprice + ","
				+ salepriceperkm + "," + salepriceperhour
				+ "," + salesman + "," + salestel
				+ "," + salesremark + "," + saletime
				+ "," + servicebegin + ","
				+ serviceend + "," + servicetype
				+ "," + servicetypename + ","
				+ taskcode + "," + taskid + ","
				+ taskcontract +","+Readmark+","+salepricecal+","+salepricecalname
				+balancetype+","+balancetypename+","+done
				+ "]";
	}

}
