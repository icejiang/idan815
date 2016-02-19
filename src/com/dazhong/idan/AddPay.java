package com.dazhong.idan;

import android.R.integer;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class AddPay extends Activity {

	private EditText et_road;
	private EditText et_eat;
	private EditText et_parking;
	private EditText et_other;
	private EditText et_hotel;
	private EditText et_alter;
	private EditText et_routeOn;
	private EditText et_routeOff;
	private EditText et_record;
	private TextView bt_save;
	private ImageView iv_return;
	public final static String key_road = "ROAD";
	public final static String key_meals = "MEALS";
	public final static String key_parking = "PARKING";
	public final static String key_other = "OTHER";
	public final static String key_hotel = "HOTEL";
	public final static String key_alter = "ALTER";
	public final static String key_routeOn = "ROUTEON";
	public final static String key_routeOff = "ROUTEOFF";
	public final static String key_record = "RECORD";
	public final static int RESULT_CODE=1;
	private getStateInfo myGetStateInfo;
	private StateInfo myStateInfo;
	private NoteInfo noteInfo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_pay);
		ActivityControler.addActivity(this);
		findView();
		try {
			myGetStateInfo = getStateInfo.getInstance(getApplicationContext());
			myStateInfo = myGetStateInfo.getStateinfo();
			noteInfo = myStateInfo.getCurrentNote();
			
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		et_routeOn.setText(noteInfo.getRouteBegin());
		et_routeOff.setText(noteInfo.getRouteEnd());
		et_road.setText(noteInfo.getFeeBridge()+"");
		et_eat.setText(noteInfo.getFeeLunch()+"");
		et_parking.setText(noteInfo.getFeePark()+"");
		et_hotel.setText(noteInfo.getFeeHotel()+"");
		et_other.setText(noteInfo.getFeeOther()+"");
//		et_alter.setText(noteInfo.getFeeBack()+"");
		et_record.setText(noteInfo.getServiceRoute());
		
		bt_save.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {

				String alter = et_alter.getText().toString();
				if (!alter.equals("")&&!(alter == null)){
					double price_all = noteInfo.getFeeTotal();
					if(Double.valueOf(alter)>price_all){
						Toast.makeText(getApplicationContext(), "修正金额大于路单总价,请确认输入", Toast.LENGTH_SHORT).show();
						return;
					}
				}
				String routeOn = et_routeOn.getText().toString();
				String routeOff = et_routeOff.getText().toString();
				if(!routeOff.equals("")&&!(routeOff == null)){
					if(Integer.parseInt(routeOff)<Integer.parseInt(routeOn)){
						Toast.makeText(getApplicationContext(), "输入路码小于上车路码,请确认输入", Toast.LENGTH_SHORT).show();
						return;
					} 
				}
				
				
				Bundle bundle = new Bundle();
				bundle.putString(key_road, et_road.getText().toString());
				bundle.putString(key_meals, et_eat.getText().toString());
				bundle.putString(key_parking, et_parking.getText().toString());
				bundle.putString(key_other, et_other.getText().toString());
				bundle.putString(key_hotel, et_hotel.getText().toString());
				bundle.putString(key_alter, alter);
				bundle.putString(key_routeOn, routeOn);
				bundle.putString(key_routeOff, routeOff);
				bundle.putString(key_record, et_record.getText().toString());
				Intent intent = new Intent();
				intent.putExtras(bundle);
	            setResult(RESULT_CODE, intent);  
	            finish();
			}
		});
		iv_return.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
	
	
	private void findView(){
		
		et_road = (EditText) findViewById(R.id.pay_road);
		et_eat = (EditText) findViewById(R.id.pay_eat);
		et_parking = (EditText) findViewById(R.id.pay_parking);
		et_other = (EditText) findViewById(R.id.pay_other);
		bt_save = (TextView) findViewById(R.id.bt_save);
		et_hotel = (EditText) findViewById(R.id.pay_hotel);
		iv_return = (ImageView) findViewById(R.id.return_addPay);
		et_alter = (EditText) findViewById(R.id.price_alter);
		et_routeOn = (EditText) findViewById(R.id.route_on);
		et_routeOff = (EditText) findViewById(R.id.route_off);
		et_record = (EditText) findViewById(R.id.record);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		if(event.getAction() == MotionEvent.ACTION_DOWN){  
		     if(getCurrentFocus()!=null && getCurrentFocus().getWindowToken()!=null){  
		       manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);  
		     }  
		  }  
		return super.onTouchEvent(event);
	}
	
	
}
