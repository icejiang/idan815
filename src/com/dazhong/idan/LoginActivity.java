package com.dazhong.idan;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {

	private Button btn_login;
	private TextView textView;
	private EditText logname;
	private EditText password;
	private StateInfo stateinfo;
	private iDanApp idanapp = null;
	private String today;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
				.detectDiskReads().detectDiskWrites().detectNetwork()
				.penaltyLog().build());

		StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
				.detectLeakedSqlLiteObjects().penaltyLog().penaltyDeath()
				.build());
		// ApplicationInfo appInfo = LoginActivity.this.getApplicationInfo();
		// int appFlags = appInfo.flags;
		// if ((appFlags & ApplicationInfo.FLAG_DEBUGGABLE) != 0) {
		// // Do StrictMode setup here
		// // StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
		// // .detectLeakedSqlLiteObjects()
		// // .penaltyLog()
		// // .penaltyDeath()
		// // .build());
		// StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
		// .detectDiskReads().detectDiskWrites().detectNetwork()
		// .penaltyLog().build());
		//
		// }
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
					// if (getInfoValue.getLogin("15821151093", "1234abcd"))
					// 13061613032 1234abcd1
					// 13918878436 1234abcd2
					{
						PersonInfo personinfo;
						try {
							personinfo = getInfoValue.getPersonInfo(idanapp
									.getEMPLOYEEID());
							personinfo.setPersonID(idanapp.getEMPLOYEEID());
							// textView.setText(personinfo.toString());
							System.out.println(personinfo.toString());
							FirstLog(personinfo);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						if (getStateRec())
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

		if (getStateRec())
			PageJump();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private boolean getStateRec() {
		try {
			// get state sample
			// getStateInfo gs = new getStateInfo(getApplicationContext());
			stateinfo = getStateInfo.getInstance(getApplicationContext())
					.getStateinfo();
			System.out.println(stateinfo.toString());
			if (stateinfo == null)
				return false;
			idanapp.setStateInfo(stateinfo);
			if (!today.equals(stateinfo.getToday()))
				setNewDayState();
			try {
				// System.out.println(stateinfo.getCurrentPerson().getPersonID());
				idanapp.setUSERNAME(stateinfo.getCurrentPerson().getName());
				idanapp.setWORKNUMBER(stateinfo.getCurrentPerson().getWorkNum());
				idanapp.setEMPLOYEEID(stateinfo.getCurrentPerson()
						.getPersonID());
				idanapp.setTasklist(getInfoValue.getTasks(stateinfo
						.getCurrentPerson().getPersonID()));
				// if (idanapp.getTasklist() == null)
				// System.out.println("davis say tasklist is null");
				// else
				// System.out.println("davis say "
				// + idanapp.getTasklist().size());

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
		// switch (88) {
		// System.out.println(stateinfo.getCurrentState());
		switch (stateinfo.getCurrentState()) {
		case 101:
			// intent = new Intent();
			// intent.setClass(getApplicationContext(), LoginActivity.class);
			// startActivity(intent);
			break;
		case 0:
			// intent = new Intent();
			// intent.setClass(getApplicationContext(), LoginActivity.class);
			// startActivity(intent);
			break;
		case 1:
			intent = new Intent();
			intent.setClass(getApplicationContext(), MainActivity.class);
			startActivity(intent);
			break;
		case 2:
		case 3:
		case 4:
		case 5:
		case 11:
			intent = new Intent();
			intent.setClass(getApplicationContext(), OrderDetail.class);
			startActivity(intent);
			break;
		case 12:
			intent = new Intent();
			intent.setClass(getApplicationContext(), OrderDetail.class);
			startActivity(intent);
			break;
		case 13:
		case 14:
		case 15:
		case 16:
		case 17:
		case 18:
			intent = new Intent();
			intent.setClass(getApplicationContext(), PrintActivity.class);
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
		stateinfo.setCurrentKMS("");
		stateinfo.setTimeOfTaskOneDay(0);
		stateinfo.setBeginKMsOfToday("");
		stateinfo.setCurrentState(1);
		stateinfo.setEndKMsOfToday("");
		stateinfo.setPageOfNoteHistory(1);
		stateinfo.setPageOfTask(1);
		stateinfo.setTimeInCar(getInfoValue.getNowTime());
		stateinfo.setTimeOffCar("");
		getStateInfo gs = getStateInfo.getInstance(getApplicationContext());
		gs.setStateinfo(stateinfo);
	}

	private void FirstLog(PersonInfo person) {
		getStateInfo gs;
		try {
			gs = getStateInfo.getInstance(getApplicationContext());
			stateinfo = new StateInfo();
			stateinfo.setToday(getInfoValue.getNowDate());
			stateinfo.setPageOfNoteHistory(1);
			stateinfo.setPageOfTask(1);
			stateinfo.setCurrentPerson(person);
			stateinfo.setCurrentState(1);
			stateinfo.setCurrentLogin(true);
			stateinfo.setTimeOfTaskOneDay(0);
			// System.out.println(stateinfo.toString());
			gs.setStateinfo(stateinfo);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// show test
	// public void showTest(View v) {
	// try {
	// // init state,user first login
	// // if (getInfoValue.getLogin("15821151093", "1234abcd"))
	// // // textView.setText(MainActivity.USERNAME);
	// // {
	// // PersonInfo personinfo = new PersonInfo(MainActivity.EMPLOYEEID,
	// // MainActivity.WORKNUMBER, MainActivity.USERNAME);
	// // textView.setText(personinfo.toString());
	// // stateinfo = new StateInfo();
	// // stateinfo.setToday("2015-12-05");
	// // stateinfo.setPageOfNoteHistory(1);
	// // stateinfo.setPageOfTask(1);
	// // stateinfo.setTimeInCar("08:12");
	// // stateinfo.setTimeOffCar("19:25");
	// // stateinfo.setBeginKMsOfToday("2135");
	// // stateinfo.setEndKMsOfToday("2236");
	// // stateinfo.setCurrentKMS("2236");
	// // stateinfo.setCurrentPerson(personinfo);
	// // stateinfo.setCurrentState(1);
	// // stateinfo.setCurrentNote(null);
	// // stateinfo.setCurrentLogin(true);
	// // // update state sample
	// // // getStateInfo gs = new getStateInfo(getApplicationContext());
	// // getStateInfo gs = getStateInfo
	// // .getInstance(getApplicationContext());
	// // gs.setStateinfo(stateinfo);
	// // } else
	// // textView.setText("no info");
	// // if(getInfoValue.ServiceDoing("147889", "2"))
	// // textView.setText("service doing remark");
	// // if(getInfoValue.setTaskRead("147889", "2")){
	// // textView.setText("lock now");
	// // }
	// // if(getInfoValue.ServiceStandby("147889", "2"))
	// // textView.setText("service standby remark");
	//
	// Intent intent = new Intent();
	// // intent.setClass(getApplicationContext(), PrintActivity.class);
	// intent.setClass(getApplicationContext(), BlueToothManage.class);
	// startActivity(intent);
	// } catch (Exception e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// Toast.makeText(getApplicationContext(), R.string.error, 1).show();
	// }
	// }

}
