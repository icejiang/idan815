package com.dazhong.idan;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class PersonalInfo extends Activity{

	private TextView name;
	private TextView company;
	private TextView workNum;
	private TextView post;
	private TextView team;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.personal_info);
		
		findView();
		try {
			PersonInfo personInfo = getInfoValue.getPersonInfo(MainActivity.EMPLOYEEID);
//			PersonInfo personinfo = new PersonInfo(MainActivity.EMPLOYEEID,
//					MainActivity.WORKNUMBER, MainActivity.USERNAME);
			name.setText(personInfo.getName());
			company.setText(personInfo.getCompany());
			workNum.setText(personInfo.getWorkNum());
			post.setText(personInfo.getPosition());
			team.setText(personInfo.getTeam());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	private void findView(){
		name = (TextView) findViewById(R.id.tv_personalName);
		company = (TextView) findViewById(R.id.tv_personalCompany);
		workNum = (TextView) findViewById(R.id.tv_personalworkNum);
		post = (TextView) findViewById(R.id.tv_personalPost);
		team = (TextView) findViewById(R.id.tv_personalType);
	}
}
