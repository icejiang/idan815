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
	private TaskInfo taskInfo;
	private NoteInfo noteInfo;
	private NoteInfo pauseNote;
	public static String INPUT_TOTAL_KEY = "INPUT_TOTAL";
	
	private TextView onboard;
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
	private TextView tv_bridge;
	private TextView tv_parking;
	private TextView tv_meals;
	private TextView tv_hotel;
	private TextView tv_other;
	private TextView tv_record;
	private TextView tv_add;
	private Button bt_pause;
	public final static int REQUEST_CODE = 2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.in_service);
		ActivityControler.addActivity(this);
		Intent intent = getIntent();
		try {
			myGetStateInfo = getStateInfo.getInstance(getApplicationContext());
			myStateInfo = myGetStateInfo.getStateinfo();
			taskInfo = myStateInfo.getCurrentTask();
			noteInfo = myStateInfo.getCurrentNote();
			pauseNote = myStateInfo.getPauseNote();
		} catch (Exception e) {
			e.printStackTrace();
		}
		findView();
		setData();
		myStateInfo.setCurrentState(13);
		myStateInfo.setCurrentNote(noteInfo);
		myGetStateInfo.setStateinfo(myStateInfo);
		if (null != pauseNote || noteInfo.isHasPaused() ){
			bt_pause.setVisibility(View.GONE);
		} else {
			bt_pause.setVisibility(View.VISIBLE);
		}
		
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
							String startKms = noteInfo.getRouteBegin();
							Log.i("jxb", "startKms = "+startKms);
							if (input_end < Integer.parseInt(startKms)){
								Toast.makeText(getApplicationContext(), "结束路码小于起始路码，请确认输入", Toast.LENGTH_SHORT).show();
							} else {
								SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
								Date curDate = new Date(System.currentTimeMillis());
								String str = formatter.format(curDate);
								noteInfo.setServiceEnd(str);
								noteInfo.setRouteEnd(input);
								myStateInfo.setCurrentNote(noteInfo);
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
		
		bt_pause.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {

				final EditText editText = new EditText(InService.this);
				editText.setInputType(InputType.TYPE_CLASS_NUMBER);
				new AlertDialog.Builder(InService.this).setTitle("请填写当前路码").
				setView(editText).setPositiveButton("暂停业务", new android.content.DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						String input = editText.getText().toString();
						if(input.equals("")){
							Toast.makeText(getApplicationContext(), "路码不能为空", Toast.LENGTH_SHORT).show();
						} else {
							noteInfo.setPauseStart(Integer.parseInt(input));
							noteInfo.setHasPaused(true);
							myStateInfo.setPauseNote(noteInfo);
							myGetStateInfo.setStateinfo(myStateInfo);
							Intent intent = new Intent();
							intent.setClass(InService.this, MainActivity.class);
							startActivity(intent);
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
			}
		});
		tv_add.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent1 = new Intent();
				intent1.setClass(getApplicationContext(), AddPayInService.class);
				startActivityForResult(intent1, REQUEST_CODE);
				
			}
		});
	}
	

	private void callNum(String phoneno){
		if(phoneno!=null && !"".equals(phoneno.trim())){
			Intent intent=new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+phoneno));
			startActivity(intent);
		}
	}
	private void findView(){
		
		btn_end = (TextView) findViewById(R.id.btn_end);
		
		onboard = (TextView) findViewById(R.id.service_onboard);
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
		tv_bridge = (TextView) findViewById(R.id.service_bridge);
		tv_parking = (TextView) findViewById(R.id.service_parking);
		tv_meals = (TextView) findViewById(R.id.service_meals);
		tv_hotel = (TextView) findViewById(R.id.service_hotel);
		tv_other = (TextView) findViewById(R.id.service_other);
		tv_record = (TextView) findViewById(R.id.service_record);
		tv_add = (TextView) findViewById(R.id.service_addRecord);
		bt_pause = (Button) findViewById(R.id.bt_pause);
	}
	
	private void setData(){
		noteID.setText(noteInfo.getNoteID());
		onboard.setText(noteInfo.getServiceBegin());
		placeName.setText(taskInfo.Bookman());
		placeNum.setText(taskInfo.BookTel());
		name.setText(taskInfo.Customer());
		number.setText(taskInfo.CustomerTel());
		location.setText(taskInfo.PickupAddress());
		destination.setText(taskInfo.LeaveAddress());
		mileStart.setText(noteInfo.getRouteBegin());
		dispatcher.setText(taskInfo.Planner());
		dispatchNum.setText(taskInfo.PlannerTel());
		salesman.setText(taskInfo.Salesman());
		salesNum.setText(taskInfo.SalesmanTel());
		Double taxRate = 1 + noteInfo.getInvoiceTaxRate();
		Double price_bridge = noteInfo.getFeeBridge();
		if (price_bridge > 0){
			if(noteInfo.getBridgefeetype() == 0){
				tv_bridge.setText(reserve2(price_bridge*taxRate)+"元");
			} else {
				tv_bridge.setText(price_bridge+"元");
			}
		}
		Double price_hotel = noteInfo.getFeeHotel();
		if (price_hotel > 0){
			if (noteInfo.getOutfeetype() == 0){
				tv_hotel.setText(reserve2(price_hotel*taxRate)+"元");
			} else {
				tv_hotel.setText(price_hotel+"元");
			}
		}
		Double price_meals = noteInfo.getFeeLunch();
		if (price_meals > 0) {
			tv_meals.setText(reserve2(price_meals*taxRate)+"元");
		}
		Double price_park = noteInfo.getFeePark();
		if (price_park > 0) {
			tv_parking.setText(reserve2(price_park*taxRate)+"元");
		}
		Double price_other = noteInfo.getFeeOther();
		if (price_other > 0) {
			tv_other.setText(reserve2(price_other*taxRate)+"元");
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST_CODE) {
			if (null != data) {
				Bundle bundle = data.getExtras();
				String road = bundle.getString(AddPayInService.key_road);
				String meals = bundle.getString(AddPayInService.key_meals);
				String parking = bundle.getString(AddPayInService.key_parking);
				String other = bundle.getString(AddPayInService.key_other);
				String hotel = bundle.getString(AddPayInService.key_hotel);
				String routeOn = bundle.getString(AddPayInService.key_routeOn);
				String record = bundle.getString(AddPayInService.key_record);
				int bridgeFeeType = noteInfo.getBridgefeetype();
				int outFeeType = noteInfo.getOutfeetype();
				double taxRate = 1 + noteInfo.getInvoiceTaxRate();
				if (!road.equals("")) {
					if (bridgeFeeType == 0){
						Double fee = reserve2(Double.valueOf(road)*taxRate);
						tv_bridge.setText(fee + "元");
					} else {
						tv_bridge.setText(Double.valueOf(road) + "元");
					}
					noteInfo.setFeeBridge(Double.valueOf(road));
				}
				if (!parking.equals("")) {
					if (bridgeFeeType == 0){
						Double fee = reserve2(Double.valueOf(parking)*taxRate);
						tv_parking.setText(fee + "元");
					} else {
						tv_parking.setText(Double.valueOf(parking) + "元");
					}
					noteInfo.setFeePark(Double.valueOf(parking));
				}
				if (!meals.equals("")) {
					if (outFeeType == 0){
						Double fee = reserve2(Double.valueOf(meals)*taxRate);
						tv_meals.setText(fee + "元");
					} else {
						tv_meals.setText(Double.valueOf(meals) + "元");
					}
					noteInfo.setFeeLunch(Double.valueOf(meals));
				}
				if (!hotel.equals("")) {
					if (outFeeType == 0){
						Double fee = reserve2(Double.valueOf(hotel)*taxRate);
						tv_hotel.setText(fee + "元");
					} else {
						tv_hotel.setText(Double.valueOf(hotel) + "元");
					}
					noteInfo.setFeeHotel(Double.valueOf(hotel));
				}
				if (!other.equals("")) {
					Double fee = reserve2(Double.valueOf(other)*taxRate);
					tv_other.setText(fee + "元");
					noteInfo.setFeeOther(Double.valueOf(other));
				}
				if (!routeOn.equals("")){
					mileStart.setText(routeOn);
					noteInfo.setRouteBegin(routeOn);
				}
				if (!record.equals("")){
					tv_record.setText(record);
					noteInfo.setServiceRoute(record);
				}
				myStateInfo.setCurrentNote(noteInfo);
				myGetStateInfo.setStateinfo(myStateInfo);
			}
		}
		
		
		
	}
	
	private Double reserve2(Double x){
		Double result = (double)(Math.round(x*100)/100.0);
		return result;
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {

		}

		return true;

	}
	
	
	
}
