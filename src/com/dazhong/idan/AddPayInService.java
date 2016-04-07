package com.dazhong.idan;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class AddPayInService extends Activity {

	private EditText et_road;
	private EditText et_eat;
	private EditText et_parking;
	private EditText et_other;
	private EditText et_hotel;
	private EditText et_routeOn;
	private EditText et_record;
	private TextView bt_save;
	private ImageView iv_return;
	private getStateInfo myGetStateInfo;
	private StateInfo myStateInfo;
	private NoteInfo noteInfo;
	public final static int RESULT_CODE=2;
	public final static String key_road = "ROAD";
	public final static String key_meals = "MEALS";
	public final static String key_parking = "PARKING";
	public final static String key_other = "OTHER";
	public final static String key_hotel = "HOTEL";
	public final static String key_routeOn = "ROUTEON";
	public final static String key_record = "RECORD";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addpay_service);
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
		et_record.setText(noteInfo.getServiceRoute());
		if (noteInfo.getFeeBridge() != 0){
			et_road.setText(noteInfo.getFeeBridge()+"");
		}
		if (noteInfo.getFeePark() != 0){
			et_parking.setText(noteInfo.getFeePark()+"");
		}
		if (noteInfo.getFeeLunch() != 0){
			et_eat.setText(noteInfo.getFeeLunch()+"");
		}
		if (noteInfo.getFeeHotel() != 0){
			et_hotel.setText(noteInfo.getFeeHotel()+"");
		}
		if (noteInfo.getFeeOther() != 0){
			et_other.setText(noteInfo.getFeeOther()+"");
		}
		bt_save.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String routeOn = et_routeOn.getText().toString();
				Bundle bundle = new Bundle();
				bundle.putString(key_road, et_road.getText().toString());
				bundle.putString(key_meals, et_eat.getText().toString());
				bundle.putString(key_parking, et_parking.getText().toString());
				bundle.putString(key_other, et_other.getText().toString());
				bundle.putString(key_hotel, et_hotel.getText().toString());
				bundle.putString(key_routeOn, routeOn);
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
				// TODO Auto-generated method stub
				finish();
			}
		});
		
	}
	
	
	private void findView(){
		iv_return = (ImageView) findViewById(R.id.return_service);
		bt_save = (TextView) findViewById(R.id.bt_save_service);
		et_road = (EditText) findViewById(R.id.paysv_road);
		et_eat = (EditText) findViewById(R.id.paysv_eat);
		et_parking = (EditText) findViewById(R.id.paysv_parking);
		et_hotel = (EditText) findViewById(R.id.paysv_hotel);
		et_other = (EditText) findViewById(R.id.paysv_other);
		et_record = (EditText) findViewById(R.id.paysv_record);
		et_routeOn = (EditText) findViewById(R.id.paysv_route_on);
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
