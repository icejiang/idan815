package com.dazhong.idan;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class PasswordAlter extends Activity {

	private ImageView iv_return;
	private ImageView iv_home;
	private Button bt_confirm;
	private EditText et_orgpsw;
	private EditText et_newpsw;
	private EditText et_newpswconfirm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.password_alter);
		ActivityControler.addActivity(this);

		iv_return = (ImageView) findViewById(R.id.return_pswAlter);
		iv_home = (ImageView) findViewById(R.id.home_pswAlter);
		bt_confirm = (Button) findViewById(R.id.bt_pswconfirm);
		et_orgpsw = (EditText) findViewById(R.id.et_orgpsw);
		et_newpsw = (EditText) findViewById(R.id.et_newpsw);
		et_newpswconfirm = (EditText) findViewById(R.id.et_newpswconfirm);

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
		bt_confirm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					if (et_orgpsw.getText().toString().length() < 3) {
						Toast.makeText(getApplicationContext(),
								"原密码录入格式错误，请重新输入！", 2000).show();
						return;
					}
					if (et_newpsw.getText().toString().length() < 3) {
						Toast.makeText(getApplicationContext(),
								"新密码录入格式错误，请重新输入！", 2000).show();
						return;
					}
					if (et_newpswconfirm.getText().toString().length() < 3
							|| !et_newpsw.getText().toString()
									.equals(et_newpsw.getText().toString())) {
						Toast.makeText(getApplicationContext(),
								"确认密码输入错误，请重新录入！", 2000).show();
						return;
					}
					String account = null;
					String passwd = null;
					account = getStateInfo.getInstance(getApplicationContext())
							.getStateinfo().getUserAccount();
					passwd = getStateInfo.getInstance(getApplicationContext())
							.getStateinfo().getUserPSW();
					if (account == null || account.length() < 5) {
						Toast.makeText(getApplicationContext(), R.string.error,
								2000).show();
						return;
					}
//					if (passwd.equals(et_orgpsw.getText().toString())) {
//						Toast.makeText(getApplicationContext(), "密码错误，请重新输入！",
//								2000).show();
//						return;
//					}
					int pwdupdate = 0;
					pwdupdate = getInfoValue.updatePassWord(account, et_orgpsw
							.getText().toString(), et_newpsw.getText()
							.toString());
					System.out.println("return value si " + pwdupdate);
					if (pwdupdate == -2) {
						Toast.makeText(getApplicationContext(), "更新密码失败，请重试！",
								2000).show();
						return;
					}
					if (pwdupdate == -1) {
						Toast.makeText(getApplicationContext(),
								"原始密码错误，请确认后重新输入！", 2000).show();
						return;
					}
					if (pwdupdate == 0) {
						Toast.makeText(getApplicationContext(), "密码更新成功！", 2000).show();
						StateInfo stateinfo=getStateInfo.getInstance(getApplicationContext()).getStateinfo();
						stateinfo.setUserPSW(passwd);
						stateinfo.setCurrentState(56);
						getStateInfo.getInstance(getApplicationContext()).setStateinfo(stateinfo);
						return;
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Toast.makeText(getApplicationContext(), R.string.error,
							2000).show();
					return;
				}

			}
		});
	}

	private void showLeftMenu(SlidingMenu menu) {
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
