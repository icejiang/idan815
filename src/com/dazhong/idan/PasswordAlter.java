package com.dazhong.idan;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class PasswordAlter extends Activity {
	
	private ImageView iv_return;
	private ImageView iv_home;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.password_alter);
		ActivityControler.addActivity(this);
		
		iv_return = (ImageView) findViewById(R.id.return_pswAlter);
		iv_home = (ImageView) findViewById(R.id.home_pswAlter);
		
		final SlidingMenu menu = new SlidingMenu(this);
		showLeftMenu(menu);
		
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
