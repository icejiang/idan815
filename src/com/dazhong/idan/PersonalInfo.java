package com.dazhong.idan;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class PersonalInfo extends Activity{

	private TextView name;
	private TextView company;
	private TextView workNum;
	private TextView post;
	private TextView team;
	private ImageView iv_return;
	private ImageView iv_home;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.personal_info);
		
		findView();
		final SlidingMenu menu = new SlidingMenu(this);
		showLeftMenu(menu);
		try {
			PersonInfo personInfo = getInfoValue.getPersonInfo(iDanApp.getInstance().getEMPLOYEEID());
			name.setText(personInfo.getName());
			company.setText(personInfo.getCompany());
			workNum.setText(personInfo.getWorkNum());
			post.setText(personInfo.getPosition());
			team.setText(personInfo.getTeam());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		iv_return.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				menu.showMenu();
			}
		});
		iv_home.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				 Intent intent = new Intent();
				 intent.setClass(getApplicationContext(), MainActivity.class);
				 startActivity(intent);

			}
		});
	}
	
	
	
	private void findView(){
		name = (TextView) findViewById(R.id.tv_personalName);
		company = (TextView) findViewById(R.id.tv_personalCompany);
		workNum = (TextView) findViewById(R.id.tv_personalworkNum);
		post = (TextView) findViewById(R.id.tv_personalPost);
		team = (TextView) findViewById(R.id.tv_personalType);
		iv_return = (ImageView) findViewById(R.id.return_person);
		iv_home = (ImageView) findViewById(R.id.home_personal);
	}
	
	private void showLeftMenu(SlidingMenu menu){
		MenuLeftFragment menuLayout = new MenuLeftFragment(
				getApplicationContext());
		menu.setMode(SlidingMenu.LEFT);
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		menu.setShadowWidthRes(R.dimen.shadow_width);
		menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		menu.setFadeDegree(0.35f);
		menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
		menu.setMenu(R.layout.left_menu);
	}
}
