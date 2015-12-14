package com.dazhong.idan;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class InService extends Activity {
	
	private Button btn_end;
	private int input_end;
	private int position;
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
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.in_service);
		ActivityControler.addActivity(this);
		Intent intent = getIntent();
		position = intent.getIntExtra(OrderDetail.INPUT_KEY, 0);
		input_start = intent.getStringExtra("input_start");
		taskInfo = MainActivity.tasklist.get(position);
		noteInfo = new NoteInfo();
//		noteInfo = (NoteInfo) intent.getSerializableExtra(OrderDetail.INPUT_KEY);
		findView();
		setData();
		putDataIntoNote();
		
		btn_end.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
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
							if (input_end < Integer.parseInt(input_start)){
								Toast.makeText(getApplicationContext(), "结束路码小于起始路码，请确认输入", Toast.LENGTH_SHORT).show();
							} else {
								SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
								Date curDate = new Date(System.currentTimeMillis());
								String str = formatter.format(curDate);
								noteInfo.setServiceEnd(str);
								noteInfo.setRouteEnd(input);
								Intent intent = new Intent();
								intent.putExtra("TYPE", position);
								intent.putExtra(INPUT_TOTAL_KEY, noteInfo);
								intent.setClass(InService.this, OrderDetailEnd.class);
								startActivity(intent);
							}
						}
					}
				}).show();
			}
		});
	}
	
	
	private void findView(){
		
		btn_end = (Button) findViewById(R.id.btn_end);
		
		date = (TextView) findViewById(R.id.service_date);
		onboard = (TextView) findViewById(R.id.service_onboard);
		type = (TextView) findViewById(R.id.service_type);
		placeName = (TextView) findViewById(R.id.service_placename);
		placeNum = (TextView) findViewById(R.id.service_placenum);
		name = (TextView) findViewById(R.id.service_name);
		number = (TextView) findViewById(R.id.service_number);
		location = (TextView) findViewById(R.id.service_location);
		destination = (TextView) findViewById(R.id.service_destination);
		
	}
	
	private void setData(){
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
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
	}
	
	private void putDataIntoNote(){
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
	}
	
	
	
	
	
	
}
