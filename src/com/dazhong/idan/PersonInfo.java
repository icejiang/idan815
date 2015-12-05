package com.dazhong.idan;

import java.io.Serializable;

//用户信息
public class PersonInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
/**用户代码*/
private String PersonID;//用户代码
private String WorkNum;//工号
private String PhoneNum;//手机号码
private String Name;//姓名
private String Gender;//性别
private String Position;//岗位
private String Team;//车队
private String Company;//公司

public PersonInfo() {
	super();
}
public PersonInfo(String personID, String workNum, String phoneNum,
		String name, String gender, String position, String team, String company) {
	super();
	PersonID = personID;
	WorkNum = workNum;
	PhoneNum = phoneNum;
	Name = name;
	Gender = gender;
	Position = position;
	Team = team;
	Company = company;
}
public PersonInfo(String personID,String workNum,String name){
	PersonID=personID;
	WorkNum=workNum;
	Name=name;
}
@Override
public String toString() {
	return  PersonID + "," + WorkNum
			+ "," + PhoneNum + "," + Name + ","
			+ Gender + "," + Position + "," + Team
			+ "," + Company + ";";
}
/**获取用户代码*/
public String getPersonID() {
	return PersonID;
}
/**设置用户代码*/
public void setPersonID(String personID) {
	PersonID = personID;
}
/**获取用户工号*/
public String getWorkNum() {
	return WorkNum;
}
/**设置用户工号*/
public void setWorkNum(String workNum) {
	WorkNum = workNum;
}
/**获取用户手机号码*/
public String getPhoneNum() {
	return PhoneNum;
}
/**设置用户手机号码*/
public void setPhoneNum(String phoneNum) {
	PhoneNum = phoneNum;
}
/**获取用户姓名*/
public String getName() {
	return Name;
}
/**获取用户姓名*/
public void setName(String name) {
	Name = name;
}
/**获取用户性别*/
public String getGender() {
	return Gender;
}
/**获取用户性别*/
public void setGender(String gender) {
	Gender = gender;
}
/**获取用户岗位*/
public String getPosition() {
	return Position;
}
/**设置用户岗位*/
public void setPosition(String position) {
	Position = position;
}
/**获取用户所在团队*/
public String getTeam() {
	return Team;
}
/**设置用户的团队*/
public void setTeam(String team) {
	Team = team;
}
/**获取用户所在的公司*/
public String getCompany() {
	return Company;
}
/**设置用户的公司*/
public void setCompany(String company) {
	Company = company;
}

}
