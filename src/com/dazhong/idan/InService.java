package com.dazhong.idan;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class InService extends Activity {
	
	private TextView btn_end;
	private int input_end;
//	private NoteInfo noteInfo;
	private String input_start;
	private TaskInfo taskInfo;
	private NoteInfo noteInfo;
	public static String INPUT_TOTAL_KEY = "INPUT_TOTAL";
	
	private TextView date;
	private TextView onboard;
	private TextView type;
	private TextView placeName;
	private TextView placeNum;
	private TextView name;
	private TextView number;
	private TextView location;
	private TextView destination;
	private TextView mileStart;
	private TextView dispatcher;
	private TextView dispatchNum;
	private TextView salesman;
	private TextView salesNum;
	private TextView noteID;
	private StateInfo myStateInfo;
	private getStateInfo myGetStateInfo;
	private int taskAccount;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.in_service);
		ActivityControler.addActivity(this);
		Intent intent = getIntent();
		input_start = intent.getStringExtra("input_start");
		try {
			myGetStateInfo = getStateInfo.getInstance(getApplicationContext());
			myStateInfo = myGetStateInfo.getStateinfo();
			taskInfo = myGetStateInfo.getStateinfo().getCurrentTask();
		} catch (Exception e) {
			e.printStackTrace();
		}
		noteInfo = new NoteInfo();
		findView();
		setData();
		putDataIntoNote();
		myStateInfo.setCurrentState(13);
		myStateInfo.setCurrentNote(noteInfo);
		myStateInfo.setCurrentKMS(input_start);
		myGetStateInfo.setStateinfo(myStateInfo);
		
		
		btn_end.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				myStateInfo.setCurrentState(15);
				myGetStateInfo.setStateinfo(myStateInfo);
				final EditText editText = new EditText(InService.this);
				editText.setInputType(InputType.TYPE_CLASS_NUMBER);
				new AlertDialog.Builder(InService.this).setTitle("请填写结束路码").
				setView(editText).setPositiveButton("填写下一步", new android.content.DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						
						String input = editText.getText().toString();
						if(input.equals("")){
							Toast.makeText(getApplicationContext(), "路码不能为空", Toast.LENGTH_SHORT).show();
						} else {
							input_end = Integer.parseInt(input);
							Log.i("jxb", "结束路码 = "+input_end);
							String lastKMS = null;
							try {
								lastKMS = getStateInfo.getInstance(InService.this).getStateinfo().getCurrentKMS();
							} catch (Exception e) {
								e.printStackTrace();
							}
							if (input_end < Integer.parseInt(lastKMS)){
								Toast.makeText(getApplicationContext(), "结束路码小于起始路码，请确认输入", Toast.LENGTH_SHORT).show();
							} else {
								SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
								Date curDate = new Date(System.currentTimeMillis());
								String str = formatter.format(curDate);
								noteInfo.setServiceEnd(str);
								noteInfo.setRouteEnd(input);
								myStateInfo.setCurrentNote(noteInfo);
								myStateInfo.setCurrentKMS(input);
								myGetStateInfo.setStateinfo(myStateInfo);
								Intent intent = new Intent();
								intent.setClass(InService.this, OrderDetailEnd.class);
								startActivity(intent);
							}
						}
					}
				}).show();
			}
		});
		placeNum.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				callNum(placeNum.getText().toString());
			}});
		number.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				callNum(number.getText().toString());
			}});
		dispatchNum.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				callNum(dispatchNum.getText().toString());
			}});
		salesNum.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				callNum(salesNum.getText().toString());
			}});
	}
	

	private void callNum(String phoneno){
		if(phoneno!=null && !"".equals(phoneno.trim())){
			Intent intent=new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+phoneno));
			startActivity(intent);
		}
	}
	private void findView(){
		
		btn_end = (TextView) findViewById(R.id.btn_end);
		
		date = (TextView) findViewById(R.id.service_date);
		onboard = (TextView) findViewById(R.id.service_onboard);
		type = (TextView) findViewById(R.id.service_type);
		placeName = (TextView) findViewById(R.id.service_placename);
		placeNum = (TextView) findViewById(R.id.service_placenum);
		name = (TextView) findViewById(R.id.service_name);
		number = (TextView) findViewById(R.id.service_number);
		location = (TextView) findViewById(R.id.service_location);
		destination = (TextView) findViewById(R.id.service_destination);
		mileStart = (TextView) findViewById(R.id.service_startMile);
		dispatcher = (TextView) findViewById(R.id.service_dispatcher);
		dispatchNum = (TextView) findViewById(R.id.service_dispatchNum);
		salesman = (TextView) findViewById(R.id.service_salesman);
		salesNum = (TextView) findViewById(R.id.service_salesNum);
		noteID = (TextView) findViewById(R.id.service_noteid);
	}
	
	private void setData(){
		taskAccount = myStateInfo.getTimeOfTaskOneDay()+1;
		myStateInfo.setTimeOfTaskOneDay(taskAccount);
		String account = "";
		if(taskAccount<10){
			account = "00"+taskAccount;
		} else if (taskAccount<100) {
			account = "0"+taskAccount;
		} else {
			account = taskAccount+"";
		}
		SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMdd");
		String today = sDateFormat.format(new Date(System.currentTimeMillis()));
		String noteId = "DZ"+today+taskInfo.DriverID()+account;
		noteID.setText(noteId);
		noteInfo.setNoteID(noteId);
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
		Date curDate = new Date(System.currentTimeMillis());
		String str = formatter.format(curDate);
		onboard.setText(str);
		noteInfo.setServiceBegin(str);
//		date.setText(taskInfo.OnboardTime());
//		type.setText(noteInfo.get);
		placeName.setText(taskInfo.Bookman());
		placeNum.setText(taskInfo.BookTel());
		name.setText(taskInfo.Customer());
		number.setText(taskInfo.CustomerTel());
		location.setText(taskInfo.PickupAddress());
		destination.setText(taskInfo.LeaveAddress());
		mileStart.setText(input_start);
		dispatcher.setText(taskInfo.Planner());
		dispatchNum.setText(taskInfo.PlannerTel());
		salesman.setText(taskInfo.Salesman());
		salesNum.setText(taskInfo.SalesmanTel());
		
	}
	
	private void putDataIntoNote(){
		SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String curDate = sDateFormat.format(new Date(System.currentTimeMillis()));
		noteInfo.setNoteDate(curDate);
		noteInfo.setCarID(taskInfo.CarID());
		noteInfo.setCarNumber(taskInfo.CarNumber());
		noteInfo.setDriverID(taskInfo.DriverID());
		noteInfo.setDriverName(taskInfo.DriverName());
//		noteInfo.setFeeBridge(taskInfo.);
		noteInfo.setFeePrice(taskInfo.SalePrice());
		// id/code
		noteInfo.setPlanID(taskInfo.TaskCode());
		noteInfo.setOnBoardAddress(taskInfo.PickupAddress());
		noteInfo.setLeaveAddress(taskInfo.LeaveAddress());
		noteInfo.setRouteBegin(input_start);
//		noteInfo.setRouteEnd(routeEnd);
		noteInfo.setFeeChoice(taskInfo.SalePriceCal());
		noteInfo.setServiceKMs(taskInfo.SaleKMs());
		noteInfo.setServiceTime(taskInfo.SaleTime());
		noteInfo.setFeeHotel(taskInfo.SaleHotelFee());
		noteInfo.setServiceKMs(taskInfo.SaleKMs());
		noteInfo.setServiceTime(taskInfo.SaleTime());
		noteInfo.setCustomerName(taskInfo.Customer());
		noteInfo.setTaskID(taskInfo.TaskID());
		noteInfo.setServiceTypeName(taskInfo.ServiceTypeName());
	}
	
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {

		}

		return true;

	}
	
	
	
}
