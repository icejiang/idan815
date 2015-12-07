package com.dazhong.idan;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {

	private Button btn_login;
	private TextView textView;
	private StateInfo stateinfo;

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

		textView = (TextView) findViewById(R.id.textView1);
		btn_login = (Button) findViewById(R.id.login);
		btn_login.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(getApplicationContext(), MainActivity.class);
				startActivity(intent);
			}
		});
		
		if(getStateRec())
		{
			//set static values
			MainActivity.USERNAME=stateinfo.getCurrentPerson().getName();
			MainActivity.WORKNUMBER=stateinfo.getCurrentPerson().getWorkNum();
			MainActivity.EMPLOYEEID=stateinfo.getCurrentPerson().getPersonID();
			//direct to main form
			Intent intent = new Intent();
			intent.setClass(getApplicationContext(), MainActivity.class);
			startActivity(intent);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private boolean getStateRec()
	{ 
		try {
			//get state sample
//			getStateInfo gs = new getStateInfo(getApplicationContext());
			stateinfo=getStateInfo.getInstance(getApplicationContext()).getStateinfo();
			System.out.println(stateinfo.toString());
			if(stateinfo==null)
				return false;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
	// show test 
	public void showTest(View v) {
		try {
			//init state,user first login
			if (getInfoValue.getLogin("15821151093", "1234abcd"))
			// textView.setText(MainActivity.USERNAME);
			{
				PersonInfo personinfo = new PersonInfo(MainActivity.EMPLOYEEID,
						MainActivity.WORKNUMBER, MainActivity.USERNAME);
				textView.setText(personinfo.toString());
				stateinfo = new StateInfo();
				stateinfo.setToday("2015-12-05");
				stateinfo.setPageOfNoteHistory(1);
				stateinfo.setPageOfTask(1);
				stateinfo.setTimeInCar("08:12");
				stateinfo.setTimeOffCar("19:25");
				stateinfo.setBeginKMsOfToday("2135");
				stateinfo.setEndKMsOfToday("2236");
				stateinfo.setCurrentKMS("2236");
				stateinfo.setCurrentPerson(personinfo);
				stateinfo.setCurrentState(18);
				stateinfo.setCurrentNote(null);
				stateinfo.setCurrentLogin(true);
				//update state sample
//				getStateInfo gs = new getStateInfo(getApplicationContext());
				getStateInfo gs=getStateInfo.getInstance(getApplicationContext());
				gs.setStateinfo(stateinfo);
			} else
				textView.setText("no info");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(getApplicationContext(), R.string.error, 1).show();
		}
	}

}
