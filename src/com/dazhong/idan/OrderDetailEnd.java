package com.dazhong.idan;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.R.integer;
import android.R.string;
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

public class OrderDetailEnd extends Activity implements OnClickListener {
	
	private TextView tv_addPay;
	private Button btn_confirmEnd;
	private TextView tv_road;
	private TextView tv_meals;
	private TextView tv_parking;
	private TextView tv_other;
	private TextView tv_base;
	private TextView tv_hotel;
	private TextView tv_all;
	private TextView tv_print;
	private TextView tv_addRecord;
	private TextView tv_record;
	private TextView date;
	private TextView startTime;
	private TextView endTime;
	private TextView type;
	private TextView mile;
	private TextView time;
	private TextView extraMile;
	private TextView extraTime;
	
	private Bundle mBundle;
	private int mileInt;
	private int all;
	private NoteInfo noteInfo;
	private TaskInfo taskInfo;
	private int position;
	private StateInfo myStateInfo;
	private getStateInfo myGetStateInfo;
	
	public static String NOTEKEY = "NOTEKEY";
	public static String TASKKEY = "TASKKEY";
	
	public final static int REQUEST_CODE = 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.route_note);
		ActivityControler.addActivity(this);
		try {
			myGetStateInfo = getStateInfo.getInstance(getApplicationContext());
			myStateInfo = myGetStateInfo.getStateinfo();
			myStateInfo.setCurrentState(16);
			myGetStateInfo.setStateinfo(myStateInfo);
			
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		findView();
		Intent intent = getIntent();
		noteInfo = (NoteInfo) intent.getSerializableExtra(InService.INPUT_TOTAL_KEY);
		position = intent.getIntExtra("TYPE",0);
		taskInfo = iDanApp.getInstance().getTasklist().get(position);
		setView();
		tv_addPay.setOnClickListener(this);
		tv_print.setOnClickListener(this);
		tv_addRecord.setOnClickListener(this);
//		btn_confirmEnd.setOnClickListener(this);
//		tv_addPay.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				Intent intent = new Intent();
//				intent.setClass(getApplicationContext(), AddPay.class);
//				startActivityForResult(intent, REQUEST_CODE);
//				
//			}
//		});
//		
//		btn_confirmEnd.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				Intent intent = new Intent();
//				intent.setClass(getApplicationContext(), MainActivity.class);
//				startActivity(intent);
//				
//			}
//		});
		
	}
	
	private void findView(){
		tv_addPay = (TextView) findViewById(R.id.add_pay);
		tv_meals = (TextView) findViewById(R.id.tv_meals);
		tv_other = (TextView) findViewById(R.id.tv_other);
		tv_road = (TextView) findViewById(R.id.tv_road);
		tv_parking = (TextView) findViewById(R.id.tv_parking);
		tv_base = (TextView) findViewById(R.id.tv_base);
		tv_hotel = (TextView) findViewById(R.id.tv_hotel);
		tv_all = (TextView) findViewById(R.id.tv_all);
		tv_print = (TextView) findViewById(R.id.tv_print);
		tv_addRecord = (TextView) findViewById(R.id.tv_add_record);
		tv_record = (TextView) findViewById(R.id.tv_record);
//		btn_confirmEnd = (Button) findViewById(R.id.confirm_end);
		date = (TextView) findViewById(R.id.route_date);
		startTime = (TextView) findViewById(R.id.route_startTime);
		endTime = (TextView) findViewById(R.id.route_endTime);
		type = (TextView) findViewById(R.id.route_type);
		mile = (TextView) findViewById(R.id.route_mile);
		time = (TextView) findViewById(R.id.route_time);
		extraMile = (TextView) findViewById(R.id.route_extraMile);
		extraTime = (TextView) findViewById(R.id.route_extraTime);
		
	}
	
	private void setView(){
//		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
//		Date curDate = new Date(System.currentTimeMillis());
//		String str = formatter.format(curDate);
		SimpleDateFormat sDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd");
		String curDate = sDateFormat.format(new Date(System.currentTimeMillis()));
		date.setText(curDate);
		startTime.setText(noteInfo.getServiceBegin());
		endTime.setText(noteInfo.getServiceEnd());
		type.setText(taskInfo.ServiceTypeName());
		int totalMile = Integer.parseInt(noteInfo.getRouteEnd())-Integer.parseInt(noteInfo.getRouteBegin());
		mile.setText(totalMile+"公里");
//		int timeStart = Integer.parseInt(noteInfo.getServiceBegin());
		DateFormat df = new SimpleDateFormat("HH:mm:ss");
		int hours = 0;
		try {
			Date d1 = df.parse(noteInfo.getServiceBegin());
			Date d2 = df.parse(noteInfo.getServiceEnd());
			long diff = d1.getTime() - d2.getTime();
			long hour = diff/(1000* 60 * 60);
			hours = Integer.parseInt(Long.toString(hour));
			time.setText(Long.toString(hour)+"小时");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int serviceMile = (int) noteInfo.getServiceKMs();
		int serviceHour = (int) noteInfo.getServiceTime();
		if(serviceMile<totalMile){
			extraMile.setText((totalMile - serviceMile)+"公里" );
		}
		if(hours > serviceHour){
			extraTime.setText((hours - serviceHour)+"小时");
		}
		tv_base.setText(noteInfo.getFeePrice()+"");
		tv_all.setText(noteInfo.getFeePrice()+"");
	}
	
	
	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.add_pay:
			myStateInfo.setCurrentState(17);
			myGetStateInfo.setStateinfo(myStateInfo);
			Intent intent1 = new Intent();
			intent1.setClass(getApplicationContext(), AddPay.class);
			startActivityForResult(intent1, REQUEST_CODE);
			break;
//		case R.id.confirm_end:
//			Intent intent2 = new Intent();
//			intent2.setClass(getApplicationContext(), MainActivity.class);
//			startActivity(intent2);
//			break;
		case R.id.tv_print:
			myStateInfo.setCurrentState(18);
			myGetStateInfo.setStateinfo(myStateInfo);
			Intent intent3 = new Intent();
			intent3.putExtra(NOTEKEY, noteInfo);
			intent3.putExtra(TASKKEY, position);
			intent3.setClass(getApplicationContext(), PrintActivity.class);
			startActivity(intent3);
			break;
		case R.id.tv_add_record:
			final EditText editText = new EditText(OrderDetailEnd.this);
//			editText.setInputType(InputType.TYPE_CLASS_NUMBER);
			new AlertDialog.Builder(OrderDetailEnd.this)
					.setTitle("请填写营运记录")
					.setView(editText)
					.setPositiveButton(
							"确定",
							new android.content.DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {

									String input = editText.getText()
											.toString();
									tv_record.setText(input);
								}

							}).show();
			break;
		default:
			break;
		}
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == REQUEST_CODE) {
			if (requestCode == AddPay.RESULT_CODE) {
				if (null!=data) {
					Bundle bundle = data.getExtras();
					this.mBundle = bundle;
					String road = bundle.getString(AddPay.key_road);
					String meals = bundle.getString(AddPay.key_meals);
					String parking = bundle.getString(AddPay.key_parking);
					String other = bundle.getString(AddPay.key_other);
					String hotel = bundle.getString(AddPay.key_hotel);
					int all = 0;
					if (!road.equals("")) {
						all = Integer.parseInt(road);
						tv_road.setText(road + "元");
						noteInfo.setFeeBridge(Double.valueOf(road));
					}
					if (!meals.equals("")) {
						all += Integer.parseInt(meals);
						tv_meals.setText(meals + "元");
						noteInfo.setFeeLunch(Double.valueOf(meals));
					}
					if (!parking.equals("")) {
						all += Integer.parseInt(parking);
						tv_parking.setText(parking + "元");
//						noteInfo.setf
					}
					if (!other.equals("")) {
						all += Integer.parseInt(other);
						tv_other.setText(other + "元");
						noteInfo.setFeeOther(Double.valueOf(other));
					}
					if (!hotel.equals("")) {
						all += Integer.parseInt(hotel);
						tv_hotel.setText(hotel + "元");
						noteInfo.setFeeHotel(Double.valueOf(hotel));
					}
					all = (int) (all+noteInfo.getFeePrice());
					tv_all.setText(all + "元");
					noteInfo.setFeeTotal(all);
					this.all = all;
				}
			}
		}
	}

	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		myStateInfo.setCurrentState(16);
		myGetStateInfo.setStateinfo(myStateInfo);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
