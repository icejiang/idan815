package com.dazhong.idan;

public abstract class getInfoValue {

	/** 
	 * 获取用户信息
	 * 参数：用户的系统编号 
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
	 * 用户登陆
	 * 参数：账号，密码
	 * 返回：True 成功，false 失败
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
			int i=Integer.parseInt(getDZService.getInfoValue(sInfo, "Result"));
			 if(i<0)
			 {
				 return false;
			 }
			MainActivity.USERNAME = getDZService.getInfoValue(sInfo,
					"DriverName");
			MainActivity.EMPLOYEEID = getDZService.getInfoValue(sInfo,
					"EmployeeID");
			MainActivity.WORKNUMBER = getDZService.getInfoValue(sInfo,
					"WorkCardNo");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
