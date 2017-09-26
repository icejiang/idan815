package com.dazhong.idan;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.dazhong.idan.InService.LocationReceiver;

import android.R.integer;
import android.R.string;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
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
//	private TextView routeAuto;
	
	private Bundle mBundle;
	private int mileInt;
	private Double totalPrice;
	private NoteInfo noteInfo;
	private TaskInfo taskInfo;
	private int position;
	private StateInfo myStateInfo;
	private getStateInfo myGetStateInfo;
	
//	private DistanceReceiver distanceReceiver;
	
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
//		tv_addRecord.setOnClickListener(this);
		
//		distanceReceiver = new DistanceReceiver();
//		IntentFilter intentfilter = new IntentFilter();
//		intentfilter.addAction("com.dazhong.idan.distanceCallback");
//		registerReceiver(distanceReceiver, intentfilter);

		
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
//		tv_addRecord = (TextView) findViewById(R.id.tv_add_record);
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
//		routeAuto = (TextView) findViewById(R.id.route_auto);
	}
	
	private void setView(){
		routeID.setText(noteInfo.getNoteID());
		SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String curDate = sDateFormat.format(new Date(System.currentTimeMillis()));
		date.setText(curDate);
		startTime.setText(noteInfo.getServiceBegin());
		endTime.setText(noteInfo.getServiceEnd());
		type.setText(taskInfo.ServiceTypeName());
		int totalMile = Integer.parseInt(noteInfo.getRouteEnd())-Integer.parseInt(noteInfo.getRouteBegin())-(noteInfo.getPauseEnd()-noteInfo.getPauseStart());
		mile.setText(totalMile+"����");
		noteInfo.setDoServiceKms(totalMile);
		noteInfo.setRouteEnd((Integer.parseInt(noteInfo.getRouteBegin())+totalMile)+"");
		DateFormat df = new SimpleDateFormat("HH:mm");
		int hour = 0;
		try {
			Date d1 = df.parse(noteInfo.getServiceBegin());
			Date d2 = df.parse(noteInfo.getServiceEnd());
			long diff = d2.getTime() - d1.getTime();
			if (diff<0){
				diff = diff +24*60*60*1000;
			}
			long min = diff/(1000 * 60);
			hour = (int) (min/60);
			int remainder = (int) (min%60);
			if(remainder>15){
				hour+=1;
			}
			time.setText(hour+"Сʱ");
			noteInfo.setDoServiceTime(hour);

		} catch (ParseException e) {
			e.printStackTrace();
		}
		Double taxRate = 1 + noteInfo.getInvoiceTaxRate(); //˰��
		int serviceMile = noteInfo.getServiceKMs();
		int serviceHour = noteInfo.getServiceTime();
		Double price_all = noteInfo.getActualRentNoTax()*taxRate;
		Double price_extraMile = 0.0;
		Double price_extraTime = 0.0;
		int feeChoice = noteInfo.getFeeChoice();
		if(serviceMile<totalMile){
			extraMile.setText((totalMile - serviceMile)+"����" );
			price_extraMile = reserve2((totalMile - serviceMile)*(taskInfo.getExceedMileFeeNoTax()*taxRate));
			noteInfo.setFeeOverKMs(price_extraMile);
			noteInfo.setOverKMs(totalMile-serviceMile);
			extraMile_price.setText(price_extraMile+"Ԫ");
		}
		if(hour > serviceHour){
			extraTime.setText((hour - serviceHour)+"Сʱ");
			price_extraTime = (hour - serviceHour)*(taskInfo.getExceedTimeFeeNoTax()*taxRate);
			noteInfo.setFeeOverTime(price_extraTime);
			noteInfo.setOverHours(hour-serviceHour);
			extraTime_price.setText(price_extraTime+"Ԫ");
		}
		if(feeChoice == 1){
			price_all += price_extraMile+price_extraTime;
		} else {
			if (price_extraMile >= price_extraTime){
				price_all += price_extraMile;
			} else {
				price_all += price_extraTime;
			}
		}
		Double price_bridge = noteInfo.getFeeBridge();
		if (price_bridge > 0){
			if(noteInfo.getBridgefeetype() == 0){
				price_all += price_bridge*taxRate;
				tv_road.setText(reserve2(price_bridge*taxRate)+"Ԫ");
			} else {
				tv_road.setText(price_bridge+"Ԫ");
			}
		}
		Double price_park = noteInfo.getFeePark();
		if (price_park > 0){
			if(noteInfo.getBridgefeetype() == 0){
				price_all += price_park*taxRate;
				tv_parking.setText(reserve2(price_park*taxRate)+"Ԫ");
			} else {
				tv_parking.setText(price_park+"Ԫ");
			}
		}
		Double price_hotel = noteInfo.getFeeHotel();
		if (price_hotel > 0){
			if (noteInfo.getOutfeetype() == 0){
				price_all += price_hotel*taxRate;
				tv_hotel.setText(reserve2(price_hotel*taxRate)+"Ԫ");
			} else {
				tv_hotel.setText(price_hotel+"Ԫ");
			}
		}
		Double price_meals = noteInfo.getFeeLunch();
		if (price_meals > 0){
			if (noteInfo.getOutfeetype() == 0){
				price_all += price_meals*taxRate;
				tv_meals.setText(reserve2(price_meals*taxRate)+"Ԫ");
			} else {
				tv_meals.setText(price_meals+"Ԫ");
			}
		}
		if (noteInfo.getFeeOther() > 0){
			price_all += noteInfo.getFeeOther()*taxRate;
			tv_other.setText(reserve2(noteInfo.getFeeOther()*taxRate) + "Ԫ");
		}
		if (noteInfo.getFeeBack() > 0) {
			price_all -= noteInfo.getFeeBack();
			tv_alter.setText(-noteInfo.getFeeBack() + "Ԫ");
		}
		noteInfo.setFeeTotal(price_all);
		if(noteInfo.getInvoiceType().equals("SD")){
			int all = 0;
			all = ((int)(price_all/10))*10;
			tv_all.setText(all+"Ԫ");
		} else {
			price_all = reserve2(price_all);
			tv_all.setText(price_all+"Ԫ");
		}
		tv_base.setText(reserve2(noteInfo.getActualRentNoTax()*taxRate)+"Ԫ");
		tv_record.setText(noteInfo.getServiceRoute());
		
//		routeAuto.setText("�Զ���ࣺ"+noteInfo.getRouteAuto());
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
//			unregisterReceiver(distanceReceiver);
			Intent intent3 = new Intent();
			intent3.setClass(getApplicationContext(), SignatureActivity.class);
			startActivity(intent3);
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
					String record = bundle.getString(AddPay.key_record);
					double all = noteInfo.getFeeTotal();
//					double all = 0;
					double taxRate = 1 + noteInfo.getInvoiceTaxRate();
					int bridgeFeeType = noteInfo.getBridgefeetype();
					int outFeeType = noteInfo.getOutfeetype();
					Log.i("jxb", "taxRate = "+taxRate+";  bridgeFeetype = "+bridgeFeeType+";   outFeetype = "+outFeeType);
					if (!record.equals("")){
						tv_record.setText(record);
						noteInfo.setServiceRoute(record);
					}
					if (!road.equals("") && !road.equals(".")) {
						Log.i("jxb", "road = "+road);
						if (bridgeFeeType == 0){
							Double fee = reserve2(Double.valueOf(road)*taxRate);
							all  = all - noteInfo.getFeeBridge()*taxRate + fee;
							tv_road.setText(fee + "Ԫ");
						} else {
							tv_road.setText(Double.valueOf(road) + "Ԫ");
						}
						noteInfo.setFeeBridge(Double.valueOf(road));
					}
					if (!parking.equals("") && !parking.equals(".")) {
						if (bridgeFeeType == 0){
							Double fee = reserve2(Double.valueOf(parking)*taxRate);
							all  = all - noteInfo.getFeePark()*taxRate + fee;
							tv_parking.setText(fee + "Ԫ");
						} else {
							tv_parking.setText(Double.valueOf(parking) + "Ԫ");
						}
						noteInfo.setFeePark(Double.valueOf(parking));
					}
					if (!meals.equals("") && !meals.equals(".")) {
						if (outFeeType == 0){
							Double fee = reserve2(Double.valueOf(meals)*taxRate);
							all  = all - noteInfo.getFeeLunch()*taxRate + fee;
							tv_meals.setText(fee + "Ԫ");
						} else {
							tv_meals.setText(Double.valueOf(meals) + "Ԫ");
						}
						noteInfo.setFeeLunch(Double.valueOf(meals));
					}
					if (!hotel.equals("") && !hotel.equals(".")) {
						if (outFeeType == 0){
							Double fee = reserve2(Double.valueOf(hotel)*taxRate);
							all  = all - noteInfo.getFeeHotel()*taxRate + fee;
							tv_hotel.setText(fee + "Ԫ");
						} else {
							tv_hotel.setText(Double.valueOf(hotel) + "Ԫ");
						}
						noteInfo.setFeeHotel(Double.valueOf(hotel));
					}
					if (!other.equals("") && !other.equals(".")) {
						Double fee = reserve2(Double.valueOf(other)*taxRate);
						all  = all - noteInfo.getFeeOther()*taxRate + fee;
						tv_other.setText(fee + "Ԫ");
						noteInfo.setFeeOther(Double.valueOf(other));
					}
					if (!alter.equals("") && !alter.equals(".")) {
//						all -= Double.valueOf(alter);
						all = all + noteInfo.getFeeBack() - Double.valueOf(alter);
						tv_alter.setText("-"+Double.valueOf(alter) + "Ԫ");
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
						int totalMile = Integer.parseInt(routeEnd)-Integer.parseInt(routeBegin)-(noteInfo.getPauseEnd()-noteInfo.getPauseStart());
						noteInfo.setDoServiceKms(totalMile);
						noteInfo.setRouteEnd((Integer.parseInt(noteInfo.getRouteBegin())+totalMile)+"");
						mile.setText(totalMile+"����");
						int serviceMile = noteInfo.getServiceKMs();
						double lastExtraMilePrice = noteInfo.getFeeOverKMs();
						double extraTimePrice = noteInfo.getFeeOverTime();
						int feeChoice = noteInfo.getFeeChoice();
						double price_extraMile = 0;
						if(serviceMile<totalMile){
							extraMile.setText((totalMile - serviceMile)+"����" );
							price_extraMile = reserve2((totalMile - serviceMile)*(taskInfo.getExceedMileFeeNoTax()*taxRate));
							noteInfo.setFeeOverKMs(price_extraMile);
							noteInfo.setOverKMs(totalMile-serviceMile);
							extraMile_price.setText(price_extraMile+"Ԫ");
						} else {
							noteInfo.setFeeOverKMs(0);
							noteInfo.setOverKMs(0);
							extraMile.setText("0����");
							extraMile_price.setText("0Ԫ");
						}
						if (feeChoice == 1){
							all = all-lastExtraMilePrice+price_extraMile;
						} else {
							if (lastExtraMilePrice >= extraTimePrice){
								if (price_extraMile >= extraTimePrice){
									all = all - lastExtraMilePrice + price_extraMile;
								} else {
									all = all - lastExtraMilePrice + extraTimePrice;
								}
							} else {
								if (price_extraMile >= extraTimePrice){
									all = all - extraTimePrice + price_extraMile;
								} else {
									//do nothing
								}
							}
						}
					}
					noteInfo.setFeeTotal(all);
					if(noteInfo.getInvoiceType().equals("SD")){
						int int_all = ((int)(all/10))*10;
						tv_all.setText(int_all+"Ԫ");
					} else {
						all = reserve2(all);
						tv_all.setText(all+"Ԫ");
					}
					tv_base.setText(reserve2(noteInfo.getActualRentNoTax()*taxRate)+"Ԫ");
					myStateInfo.setCurrentNote(noteInfo);
					myGetStateInfo.setStateinfo(myStateInfo);
				} else {
					
				}
			}
		}
	}

	private Double reserve2(Double x){
		Double result = (double)(Math.round(x*100)/100.0);
		return result;
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
	
	
	/*public class DistanceReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			Bundle bundle = intent.getExtras();
			int content = bundle.getInt("distanceAuto");
			Log.i("jxb", "distance:"+content);
			routeAuto.setText("�Զ���ࣺ"+content);
		}
		
	}*/
	
	
	
	
	
	
	
	
	
	

}
