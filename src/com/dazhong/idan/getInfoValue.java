package com.dazhong.idan;

public abstract class getInfoValue {

	// 获取用户信息
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

	// 用户登陆
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
