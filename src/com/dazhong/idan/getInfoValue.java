package com.dazhong.idan;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import android.util.Log;

public abstract class getInfoValue {
	/**
	 * 获取当前时间
	 * */
	public static String getNowTime() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Timestamp now = new Timestamp(System.currentTimeMillis());
		String time = format.format(now);
		time = time.substring(11);
		return time;
	}

	/**
	 * 获取当前日期
	 * */
	public static String getNowDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Timestamp now = new Timestamp(System.currentTimeMillis());
		return sdf.format(now);
	}

	/**
	 * 插入路单
	 * */
	public static int InsertNote(String note){
		int iInsertResult=0;
		String sInfo;
		try {
			sInfo=getDZService.getServiceConnect(note, "InsertRouteNote");
			System.out.println(sInfo);
			iInsertResult=Integer.parseInt(sInfo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return iInsertResult;
	}
	/**
	 * 设置用户状态，等待接单
	 * */
	public static boolean ServiceStandby(String userid, String taskid) {
		try {
			String sInfo = getDZService.getServiceConnect(userid, taskid, "0",
					"SetLockDispatch");
			// System.out.println(sInfo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 设置用户状态，服务中
	 * */
	public static boolean ServiceDoing(String userid, String taskid) {
		try {
			String sInfo = getDZService.getServiceConnect(userid, taskid, "1",
					"SetLockDispatch");
			// System.out.println(sInfo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
/**
 * 获取路单历史
 * */
	public static List<NoteInfo> getNotes(String employeeid,int pages) throws Exception {
		List<NoteInfo> listNotes = null;
		List<String> listInfo = null;
		NoteInfo noteinfo = null;
		String sInfo = null;
		try {
//			System.out.println(employeeid);
			sInfo = getDZService.getServiceConnect(employeeid, Integer.toString(pages),
					"GetRouteNoteInfo");
//			 System.out.println(sInfo);
			if (sInfo == null)
				return null;
			listInfo = getDZService.getInfoValue(sInfo);
			if (listInfo == null)
				return null;
			listNotes = new ArrayList<NoteInfo>();
			// System.out.println("info count:" + listInfo.size());
			for (String sinf : listInfo) {
				noteinfo = new NoteInfo();
				noteinfo.setNoteID(getDZService.getInfoValue(sinf, "RouteNoteNo"));
				noteinfo.setPlanID(getDZService.getInfoValue(sinf,
						"DispatchID"));
				noteinfo.setCarID(getDZService.getInfoValue(sinf, "CarID"));
				noteinfo.setCarNumber(getDZService.getInfoValue(sinf,
						"LicenseTag"));
				noteinfo.setDriverID(getDZService
						.getInfoValue(sinf, "DriverID"));
				noteinfo.setDriverName(getDZService.getInfoValue(sinf, "DriverName"));
				noteinfo.setFeeBridge(Double.parseDouble(getDZService.getInfoValue(sinf,
						"BridgeFee")));
				noteinfo.setFeeHotel(Double.parseDouble(getDZService
						.getInfoValue(sinf, "OutFee")));
				noteinfo.setFeeOther(Double.parseDouble(getDZService.getInfoValue(sinf,
						"OtherFee")));
				noteinfo.setFeeOverKMs(Double.parseDouble(getDZService.getInfoValue(sinf,
						"ExceedMileFee")));
				noteinfo.setFeeOverTime(Double.parseDouble(getDZService.getInfoValue(sinf,
						"ExceedTimeFee")));
				noteinfo.setFeePrice(Double.parseDouble(getDZService.getInfoValue(sinf,
						"BaseFee")));
				noteinfo.setFeeTotal(Double.parseDouble(getDZService.getInfoValue(sinf,
						"ActualTotalFee")));
				noteinfo.setOnBoardAddress(getDZService.getInfoValue(sinf,
						"UploadAddress"));
				noteinfo.setLeaveAddress(getDZService.getInfoValue(sinf,
						"LeaveAddress"));
				noteinfo.setRouteBegin(getDZService.getInfoValue(sinf,
						"StartMile"));
				noteinfo.setRouteEnd(getDZService.getInfoValue(sinf,
						"EndMile"));
				noteinfo.setServiceBegin(getDZService.getInfoValue(sinf,
						"StartTime"));
				noteinfo.setServiceEnd(getDZService.getInfoValue(sinf,
						"EndTime"));
				noteinfo.setServiceKMs(Integer.parseInt(getDZService.getInfoValue(sinf,
						"BusinessMile")));
				noteinfo.setServiceTime(Integer.parseInt(getDZService.getInfoValue(sinf,
						"BusinessTime")));
				noteinfo.setOverKMs(Integer.parseInt(getDZService
						.getInfoValue(sinf, "ExceedMile")));
				noteinfo.setOverHours(Integer.parseInt(getDZService.getInfoValue(
						sinf, "ExceedTime")));
				noteinfo.setFeeOverCal(Double.parseDouble(getDZService
						.getInfoValue(sinf, "ExceedSettleType")));
				noteinfo.setServiceRoute(getDZService.getInfoValue(sinf,
						"BusinessRecord"));
				noteinfo.setNoteDate(getDZService.getInfoValue(sinf,
						"RouteNoteDate"));
//				 System.out.println(noteinfo.toString());
				listNotes.add(noteinfo);
			}
			 System.out.println("task count:" + listNotes.size());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return listNotes;
	}
	/**
	 * 设置调度单已读，上传到服务器 成功 true，失败 false
	 * */
	public static boolean setTaskRead(String userid, String taskid) {
		try {
			String sInfo = getDZService.getServiceConnect(userid, taskid,
					"SetReadDispatch");
			// System.out.println(sInfo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 获取调度信息列表
	 * */
	public static List<TaskInfo> getTasks(String employeeid) throws Exception {
		List<TaskInfo> listTasks = null;
		List<String> listInfo = null;
		TaskInfo taskinfo = null;
		String sInfo = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Timestamp now = new Timestamp(System.currentTimeMillis());
		String sf = sdf.format(now);
		// System.out.println(sf);
		try {
			sInfo = getDZService.getServiceConnect(employeeid, sf, "1",
					"GetDispatchInfo");
			// System.out.println(sInfo);
			if (sInfo == null)
				return null;
			listInfo = getDZService.getInfoValue(sInfo);
			if (listInfo == null)
				return null;
			listTasks = new ArrayList<TaskInfo>();
			// System.out.println("info count:" + listInfo.size());
			for (String sinf : listInfo) {
				taskinfo = new TaskInfo();
				taskinfo.setBookman(getDZService.getInfoValue(sinf, "Order"));
				taskinfo.setBooktel(getDZService.getInfoValue(sinf,
						"OrderPhone"));
				taskinfo.setCarid(getDZService.getInfoValue(sinf, "CarID"));
				taskinfo.setCarnumber(getDZService.getInfoValue(sinf,
						"LicenseTag"));
				taskinfo.setCartype(getDZService
						.getInfoValue(sinf, "CarSRName"));
				taskinfo.setCustomer(getDZService.getInfoValue(sinf, "CarUser"));
				taskinfo.setCustomertel(getDZService.getInfoValue(sinf,
						"CarUserPhone"));
				taskinfo.setDriverid(getDZService
						.getInfoValue(sinf, "DriverID"));
				taskinfo.setDrivername(getDZService.getInfoValue(sinf,
						"DriverName"));
				taskinfo.setDriverphone(getDZService.getInfoValue(sinf,
						"DriverPhone"));
				taskinfo.setFrightin(getDZService.getInfoValue(sinf,
						"FlightTime"));
				taskinfo.setFrightout(getDZService.getInfoValue(sinf,
						"FlightTime"));
				taskinfo.setFrightnum(getDZService.getInfoValue(sinf,
						"FlightNo"));
				taskinfo.setInvoicetype(getDZService.getInfoValue(sinf,
						"InvoiceTypeCode"));
				taskinfo.setInvoicetypename(getDZService.getInfoValue(sinf,
						"InvoiceTypeName"));
				taskinfo.setLeaveaddress(getDZService.getInfoValue(sinf,
						"Destination"));
				taskinfo.setMaxload(Integer.parseInt(getDZService.getInfoValue(
						sinf, "SeatNumber")));
				taskinfo.setOnboardtime(getDZService.getInfoValue(sinf,
						"UpLoadTime"));
				taskinfo.setPickupaddress(getDZService.getInfoValue(sinf,
						"UpLoadAddress"));
				taskinfo.setPlanner(getDZService.getInfoValue(sinf,
						"DispatchManName"));
				taskinfo.setPlannerremark(getDZService.getInfoValue(sinf,
						"DispatchManRemarks"));
				taskinfo.setPlannertel(getDZService.getInfoValue(sinf,
						"DispatchManPhone"));
				taskinfo.setSalehotelfee(Double.parseDouble(getDZService
						.getInfoValue(sinf, "OutFee")));
				taskinfo.setSalekms(Integer.parseInt(getDZService.getInfoValue(
						sinf, "AvailableMile")));
				taskinfo.setSaleprice(Double.parseDouble(getDZService
						.getInfoValue(sinf, "ActualRent")));
				taskinfo.setSalepriceperkm(Integer.parseInt(getDZService
						.getInfoValue(sinf, "ExceedMileFee")));
				taskinfo.setSalepriceperhour(Integer.parseInt(getDZService
						.getInfoValue(sinf, "ExceedTimeFee")));
				taskinfo.setSalesman(getDZService.getInfoValue(sinf,
						"EmployeeName"));
				taskinfo.setSalestel(getDZService.getInfoValue(sinf,
						"EmployeePhone"));
				taskinfo.setSalesremark(getDZService.getInfoValue(sinf,
						"BargainRemarks"));
				taskinfo.setSaletime(Integer.parseInt(getDZService
						.getInfoValue(sinf, "AvailableTime")));
				taskinfo.setServicebegin(getDZService.getInfoValue(sinf,
						"StartDate"));
				taskinfo.setServiceend(getDZService.getInfoValue(sinf,
						"EndDate"));
				taskinfo.setServicetype(Integer.parseInt(getDZService
						.getInfoValue(sinf, "JobTypeCode")));
				taskinfo.setServicetypename(getDZService.getInfoValue(sinf,
						"JobTypeName"));
				taskinfo.setTaskcode(getDZService.getInfoValue(sinf,
						"DispatchNo"));
				taskinfo.setTaskcontract(getDZService.getInfoValue(sinf,
						"BargainNo"));
				taskinfo.setTaskid(getDZService
						.getInfoValue(sinf, "DispatchID"));
				taskinfo.setReadmark(Integer.parseInt(getDZService
						.getInfoValue(sinf, "ReadMark")));
				taskinfo.setSalePriceCal(Integer.parseInt(getDZService
						.getInfoValue(sinf, "ExceedSettleType")));
				taskinfo.setSalepricecalname(getDZService.getInfoValue(sinf,
						"ExceedSettleTypeName"));
				taskinfo.setBalancetype(getDZService.getInfoValue(sinf,
						"BalanceTypeName"));
				taskinfo.setBalancetypename(getDZService.getInfoValue(sinf,
						"BalanceTypeName"));
				// System.out.println(taskinfo.toString());
				listTasks.add(taskinfo);
			}
			// System.out.println("task count:" + listTasks.size());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return listTasks;
	}

	/**
	 * 获取用户信息 参数：用户的系统编号
	 **/
	public static PersonInfo getPersonInfo(String employeeid) throws Exception {
		String sInfo = getDZService.getServiceConnect(employeeid,
				"GetEmployeeInfo");
		if (sInfo == null)
			return null;
		PersonInfo personinfo = new PersonInfo();
		personinfo.setName(getDZService.getInfoValue(sInfo, "DriverName"));
		personinfo.setGender(getDZService.getInfoValue(sInfo, "Gender"));
		personinfo.setWorkNum(getDZService.getInfoValue(sInfo, "WorkCardNo"));
		personinfo.setPosition(getDZService.getInfoValue(sInfo, "Post"));
		personinfo.setTeam(getDZService.getInfoValue(sInfo, "Team"));
		personinfo.setCompany(getDZService.getInfoValue(sInfo, "Company"));
		return personinfo;
	}

	/**
	 * 用户登陆 参数：账号，密码 返回：True 成功，false 失败
	 **/
	public static boolean getLogin(String sAccount, String sPassword) {
		if (sAccount.length() < 3 || sPassword.length() < 3 || sAccount == null
				|| sPassword == null)
			return false;
		try {
			String sInfo = getDZService.getServiceConnect(sAccount, sPassword,
					"Signin");
			if (sInfo == null)
				return false;
			int i = Integer
					.parseInt(getDZService.getInfoValue(sInfo, "Result"));
			if (i < 0) {
				return false;
			}
//			System.out.println(sInfo);
			// MainActivity.USERNAME = getDZService.getInfoValue(sInfo,
			// "DriverName");
			iDanApp.getInstance().setUSERNAME(
					getDZService.getInfoValue(sInfo, "DriverName"));
			// MainActivity.EMPLOYEEID = getDZService.getInfoValue(sInfo,
			// "EmployeeID");
			iDanApp.getInstance().setEMPLOYEEID(
					getDZService.getInfoValue(sInfo, "EmployeeID"));
			// MainActivity.WORKNUMBER = getDZService.getInfoValue(sInfo,
			// "WorkCardNo");
			iDanApp.getInstance().setWORKNUMBER(
					getDZService.getInfoValue(sInfo, "WorkCardNo"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
