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
import android.view.KeyEvent;
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
	private TextView extraMile_price;
	private TextView extraTime_price;
	private TextView routeID;
	private TextView tv_alter;
	
	private Bundle mBundle;
	private int mileInt;
	private Double totalPrice;
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
		
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.route_note);
		ActivityControler.addActivity(this);
		try {
			myGetStateInfo = getStateInfo.getInstance(getApplicationContext());
			myStateInfo = myGetStateInfo.getStateinfo();
			myStateInfo.setCurrentState(16);
			noteInfo = myStateInfo.getCurrentNote();
			taskInfo = myStateInfo.getCurrentTask();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		findView();
		setView();
		myStateInfo.setCurrentNote(noteInfo);
		myGetStateInfo.setStateinfo(myStateInfo);
		tv_addPay.setOnClickListener(this);
		tv_print.setOnClickListener(this);
		tv_addRecord.setOnClickListener(this);

		
	}
	
	private void findView(){
		tv_addPay = (TextView) findViewById(R.id.add_pay);
		tv_meals = (TextView) findViewById(R.id.tv_meals);
		tv_other = (TextView) findViewById(R.id.tv_other);
		tv_road = (TextView) findViewById(R.id.tv_road);
		tv_parking = (TextView) findViewById(R.id.tv_parking);
		tv_base = (TextView) findViewById(R.id.tv_base);
		tv_hotel = (TextView) findViewById(R.id.tv_hotel1);
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
		extraMile_price = (TextView) findViewById(R.id.tv_extraMile);
		extraTime_price = (TextView) findViewById(R.id.tv_extraTime);
		routeID = (TextView) findViewById(R.id.route_titleID);
		tv_alter = (TextView) findViewById(R.id.tv_alter);
		
	}
	
	private void setView(){
		routeID.setText(noteInfo.getNoteID());
		SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String curDate = sDateFormat.format(new Date(System.currentTimeMillis()));
		date.setText(curDate);
		startTime.setText(noteInfo.getServiceBegin());
		endTime.setText(noteInfo.getServiceEnd());
		type.setText(taskInfo.ServiceTypeName());
		int totalMile = Integer.parseInt(noteInfo.getRouteEnd())-Integer.parseInt(noteInfo.getRouteBegin());
		mile.setText(totalMile+"公里");
		noteInfo.setDoServiceKms(totalMile);
		DateFormat df = new SimpleDateFormat("HH:mm");
		int hours = 0;
		try {
			Date d1 = df.parse(noteInfo.getServiceBegin());
			Date d2 = df.parse(noteInfo.getServiceEnd());
			long diff = d2.getTime() - d1.getTime();
			long hour = diff/(1000* 60 * 60);
			hours = Integer.parseInt(Long.toString(hour));
			time.setText(Long.toString(hour)+"小时");
			noteInfo.setDoServiceTime(hours);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		int serviceMile = noteInfo.getServiceKMs();
		int serviceHour = noteInfo.getServiceTime();
		Double price_all = noteInfo.getFeePrice();
		Double price_extraMile = 0.0;
		Double price_extraTime = 0.0;
		int feeChoice = noteInfo.getFeeChoice();
		if(serviceMile<totalMile){
			extraMile.setText((totalMile - serviceMile)+"公里" );
			price_extraMile = (totalMile - serviceMile)*(taskInfo.SalePricePerKM());
			noteInfo.setFeeOverKMs(price_extraMile);
			noteInfo.setOverKMs(totalMile-serviceMile);
			extraMile_price.setText(price_extraMile+"元");
		}
		if(hours > serviceHour){
			extraTime.setText((hours - serviceHour)+"小时");
			price_extraTime = (hours - serviceHour)*(taskInfo.SalePricePerHour());
			noteInfo.setFeeOverTime(price_extraTime);
			noteInfo.setOverKMs(hours-serviceHour);
			extraTime_price.setText(price_extraTime+"元");
		}
		if(feeChoice == 1){
			price_all += price_extraMile+price_extraTime;
		} else {
			if(price_extraMile>price_extraTime && price_extraTime!=0){
				price_all+=price_extraTime;
			} else if(price_extraMile>price_extraTime){
				price_all+=price_extraMile;
			} else if(price_extraTime>price_extraMile && price_extraMile!=0){
				price_all+=price_extraMile;
			} else {
				price_all+=price_extraTime;
			}
		}
		noteInfo.setFeeTotal(price_all);
		tv_base.setText(noteInfo.getFeePrice()+"元");
		tv_all.setText(price_all+"元");
	}
	
	
	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.add_pay:
			Intent intent1 = new Intent();
			intent1.setClass(getApplicationContext(), AddPay.class);
			startActivityForResult(intent1, REQUEST_CODE);
			break;
		case R.id.tv_print:
			Intent intent3 = new Intent();
			intent3.setClass(getApplicationContext(), PrintActivity.class);
			startActivity(intent3);
			break;
		case R.id.tv_add_record:
			final EditText editText = new EditText(OrderDetailEnd.this);
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
									noteInfo.setServiceRoute(input);
									myStateInfo.setCurrentNote(noteInfo);
									myGetStateInfo.setStateinfo(myStateInfo);
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
					String alter = bundle.getString(AddPay.key_alter);
					String routeOn = bundle.getString(AddPay.key_routeOn);
					String routeOff = bundle.getString(AddPay.key_routeOff);
					double all = noteInfo.getFeeTotal();
					if (!road.equals("")) {
						all += Double.valueOf(road);
						tv_road.setText(Double.valueOf(road) + "元");
						noteInfo.setFeeBridge(Double.valueOf(road));
					}
					if (!meals.equals("")) {
						all += Double.valueOf(meals);
						tv_meals.setText(Double.valueOf(meals) + "元");
						noteInfo.setFeeLunch(Double.valueOf(meals));
					}
					if (!parking.equals("")) {
						all += Double.valueOf(parking);
						tv_parking.setText(Double.valueOf(parking) + "元");
						noteInfo.setFeePark(Double.valueOf(parking));
					}
					if (!other.equals("")) {
						all += Double.valueOf(other);
						tv_other.setText(Double.valueOf(other) + "元");
						noteInfo.setFeeOther(Double.valueOf(other));
					}
					if (!hotel.equals("")) {
						all += Double.valueOf(hotel);
						tv_hotel.setText(Double.valueOf(hotel) + "元");
						noteInfo.setFeeHotel(Double.valueOf(hotel));
					}
					if (!alter.equals("")) {
						all -= Double.valueOf(alter);
						tv_alter.setText("-"+Double.valueOf(alter) + "元");
						noteInfo.setFeeBack(Double.valueOf(alter));
					}
					if (!routeOn.equals("") || !routeOff.equals("")) {
						String routeBegin = noteInfo.getRouteBegin();
						String routeEnd = noteInfo.getRouteEnd();
						if(!routeOn.equals("")){
							routeBegin = routeOn;
							noteInfo.setRouteBegin(routeBegin);
						}
						if(!routeOff.equals("")){
							routeEnd = routeOff;
							noteInfo.setRouteEnd(routeEnd);
						}
						int totalMile = Integer.parseInt(routeEnd)-Integer.parseInt(routeBegin);
						noteInfo.setDoServiceKms(totalMile);
						mile.setText(totalMile+"公里");
						int serviceMile = noteInfo.getServiceKMs();
						double lastExtraMilePrice = noteInfo.getFeeOverKMs();
						double price_extraMile = 0.0;
						if(serviceMile<totalMile){
							extraMile.setText((totalMile - serviceMile)+"公里" );
							price_extraMile = (totalMile - serviceMile)*(taskInfo.SalePricePerKM());
							noteInfo.setFeeOverKMs(price_extraMile);
							noteInfo.setOverKMs(totalMile-serviceMile);
							extraMile_price.setText(price_extraMile+"元");
						} else {
							noteInfo.setFeeOverKMs(0);
							noteInfo.setOverKMs(0);
							extraMile_price.setText("0元");
						}
						all = all-lastExtraMilePrice+price_extraMile;
					}
					tv_all.setText(all + "元");
					noteInfo.setFeeTotal(all);
					myStateInfo.setCurrentNote(noteInfo);
					myGetStateInfo.setStateinfo(myStateInfo);
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
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {

		}

		return true;

	}
	
	
	
	
	
	
	
	
	
	
	
	
	

}
