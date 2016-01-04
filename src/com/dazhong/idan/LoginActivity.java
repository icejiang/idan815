package com.dazhong.idan;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {

	private Button btn_login;
	private TextView forgetPSW;
	private EditText logname;
	private EditText password;
	private StateInfo stateinfo;
	private iDanApp idanapp = null;
	private String today;
	private boolean logok = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
				.detectDiskReads().detectDiskWrites().detectNetwork()
				.penaltyLog().build());
		StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
				.detectLeakedSqlLiteObjects().penaltyLog().penaltyDeath()
				.build());
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
		
		idanapp = iDanApp.getInstance();
		ActivityControler.addActivity(this);
		today = getInfoValue.getNowDate();
		// UpdateManager mUpdateManager = new UpdateManager(LoginActivity.this);
		// // 注意此处不能传入getApplicationContext();会报错，因为只有是一个Activity才可以添加窗体
		// mUpdateManager.checkUpdateInfo();
		btn_login = (Button) findViewById(R.id.login);
		logname = (EditText) findViewById(R.id.editText1);
		password = (EditText) findViewById(R.id.editText2);
		forgetPSW = (TextView) findViewById(R.id.forgetPsw);
		btn_login.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String account = logname.getText().toString();
				String psw = password.getText().toString();
				if (account.equals("") || account == null || psw.equals("")
						|| psw == null) {
					Toast.makeText(getApplicationContext(), "输入错误",
							Toast.LENGTH_SHORT).show();
					;
				} else {
					if (getInfoValue.getLogin(account, psw))
					{
						PersonInfo personinfo;
						if (stateinfo == null)
							personinfo = new PersonInfo();
						else
							personinfo = stateinfo.getCurrentPerson();
						try {
							if (personinfo.getPersonID().equals(
									idanapp.getEMPLOYEEID())) {
								if (!getInfoValue.getNowDate().equals(
										stateinfo.getToday())) {
									setNewDayState();
								} else {
									stateinfo.setCurrentLogin(true);
									stateinfo.setCurrentState(1);
								}
							} else {
								personinfo = getInfoValue.getPersonInfo(idanapp
										.getEMPLOYEEID());
								personinfo.setPersonID(idanapp.getEMPLOYEEID());
								FirstLog(personinfo);
							}
							stateinfo.setUserAccount(account);
							stateinfo.setUserPSW(psw);
							idanapp.setTasklist(getInfoValue.getTasks(idanapp
									.getEMPLOYEEID()));
							getStateInfo.getInstance(getApplicationContext()).setStateinfo(stateinfo);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						PageJump();
					} else {
						Toast.makeText(getApplicationContext(), "登录失败",
								Toast.LENGTH_SHORT).show();
						;
					}
				}
				// return;
			}
		});

		logok = getStateRec();
		if (logok)
			PageJump();
		
		forgetPSW.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				new AlertDialog.Builder(LoginActivity.this).setTitle("请拨打客服电话查询，是否立即拨打？").
				setPositiveButton("确定", new android.content.DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Intent intent=new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+"18918896822"));
						startActivity(intent);
						
					}
				}).setNegativeButton("取消", new android.content.DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						
					}
				}).show();
				
			}
		});
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		if(event.getAction() == MotionEvent.ACTION_DOWN){  
		     if(getCurrentFocus()!=null && getCurrentFocus().getWindowToken()!=null){  
		       manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);  
		     }  
		  }  
		return super.onTouchEvent(event);
	}

	private boolean getStateRec() {
		try {
			stateinfo = getStateInfo.getInstance(getApplicationContext())
					.getStateinfo();
			System.out.println(stateinfo.toString());
			if (stateinfo == null)
				return false;
			idanapp.setStateInfo(stateinfo);
			try {
				idanapp.setUSERNAME(stateinfo.getCurrentPerson().getName());
				idanapp.setWORKNUMBER(stateinfo.getCurrentPerson().getWorkNum());
				idanapp.setEMPLOYEEID(stateinfo.getCurrentPerson()
						.getPersonID());
				idanapp.setUSERACCOUNT(stateinfo.getUserAccount());
				idanapp.setUSERPSW(stateinfo.getUserPSW());
				idanapp.setTasklist(getInfoValue.getTasks(stateinfo
						.getCurrentPerson().getPersonID()));

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Toast.makeText(getApplicationContext(), "初始化赋值出错", 2000).show();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}

	private void PageJump() {
		Intent intent;
		// 登陆后，选择显示页面
		switch (stateinfo.getCurrentState()) {
		case 101:
		case 0:
			break;
		case 1:
		case 2:
		case 3:
			intent = new Intent();
			intent.setClass(getApplicationContext(), MainActivity.class);
			startActivity(intent);
			break;
		case 11:
		case 12:
			intent = new Intent();
			intent.setClass(getApplicationContext(), OrderDetail.class);
			startActivity(intent);
			break;
		case 13:
		case 14:
		case 15:
			intent = new Intent();
			intent.setClass(getApplicationContext(), InService.class);
			startActivity(intent);
			break;
		case 16:
			intent = new Intent();
			intent.setClass(getApplicationContext(), OrderDetailEnd.class);
			startActivity(intent);
			break;
		case 17:
			intent = new Intent();
			intent.setClass(getApplicationContext(), AddPay.class);
			startActivity(intent);
			break;
		case 18:
			intent = new Intent();
			intent.setClass(getApplicationContext(),MainActivity.class);// PrintActivity.class);
			startActivity(intent);
			break;
		case 51:
			intent = new Intent();
			intent.setClass(getApplicationContext(), OrderHistory.class);
			startActivity(intent);
			break;
		case 55:
			intent = new Intent();
			intent.setClass(getApplicationContext(), PersonalInfo.class);
			startActivity(intent);
			break;
		case 56:
			intent = new Intent();
			intent.setClass(getApplicationContext(), PasswordAlter.class);
			startActivity(intent);
			break;
		default:
			Toast.makeText(getApplicationContext(), R.string.error, 6000)
					.show();
			break;
		}
	}

	private void setNewDayState() {
		stateinfo.setToday(today);
		stateinfo.setTimeOfTaskOneDay(0);
		stateinfo.setBeginKMsOfToday("0");
		stateinfo.setCurrentState(1);
		stateinfo.setEndKMsOfToday("0");
		stateinfo.setPageOfNoteHistory(0);
		stateinfo.setPageOfTask(1);
		stateinfo.setTimeInCar(getInfoValue.getNowTime());
		stateinfo.setCurrentLogin(true);
		stateinfo.setTimeOffCar("0");
//		getStateInfo gs = getStateInfo.getInstance(getApplicationContext());
//		gs.setStateinfo(stateinfo);
	}

	private void FirstLog(PersonInfo person) {
//		getStateInfo gs;
		try {
//			gs = getStateInfo.getInstance(getApplicationContext());
			if (stateinfo == null)
				stateinfo = new StateInfo();
			stateinfo.setToday(getInfoValue.getNowDate());
			stateinfo.setPageOfNoteHistory(0);
			stateinfo.setPageOfTask(1);
			stateinfo.setCurrentPerson(person);
			stateinfo.setCurrentState(1);
			stateinfo.setCurrentLogin(true);
			stateinfo.setCurrentKMS("0");
			stateinfo.setBeginKMsOfToday("0");
			stateinfo.setEndKMsOfToday("0");
			stateinfo.setTimeOfTaskOneDay(0);
			stateinfo.setUserAccount(idanapp.getUSERACCOUNT());
			stateinfo.setUserPSW(idanapp.getUSERPSW());
			// System.out.println(stateinfo.toString());
//			gs.setStateinfo(stateinfo);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
