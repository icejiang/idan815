package com.dazhong.idan;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class HistoryDetail extends Activity {

	private TextView titleID;
	private TextView date;
	private TextView pickupTime;
	private TextView leaveTime;
	private TextView type;
	private TextView mile;
	private TextView time;
	private TextView extraMile;
	private TextView extraTime;
	private TextView record;
	private TextView cost_base;
	private TextView cost_bridge;
	private TextView cost_parking;
	private TextView cost_meal;
	private TextView cost_hotel;
	private TextView cost_extraMile;
	private TextView cost_extraTime;
	private TextView cost_other;
	private TextView cost_alter;
	private TextView cost_all;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.history_detail);
		
		
		
		
	}
	
	
	private void findView(){
		titleID = (TextView) findViewById(R.id.history_id);
		date = (TextView) findViewById(R.id.history_date);
		pickupTime = (TextView) findViewById(R.id.history_startTime);
		leaveTime = (TextView) findViewById(R.id.history_endTime);
		type = (TextView) findViewById(R.id.history_type);
		mile = (TextView) findViewById(R.id.history_mile);
		time = (TextView) findViewById(R.id.history_time);
		extraMile = (TextView) findViewById(R.id.history_extraMile);
		extraTime = (TextView) findViewById(R.id.history_extraTime);
		record = (TextView) findViewById(R.id.history_record);
		cost_base = (TextView) findViewById(R.id.history_base);
		cost_bridge = (TextView) findViewById(R.id.history_road);
		cost_parking = (TextView) findViewById(R.id.history_parking);
		cost_meal = (TextView) findViewById(R.id.history_meals);
		cost_hotel = (TextView) findViewById(R.id.history_hotel1);
		cost_extraMile = (TextView) findViewById(R.id.history_extraMilePrice);
		cost_extraTime = (TextView) findViewById(R.id.history_extraTimePrice);
		cost_other = (TextView) findViewById(R.id.history_other);
		cost_alter = (TextView) findViewById(R.id.history_alter);
		cost_all = (TextView) findViewById(R.id.history_all);
		
		
		
		
		
		
		
		
	}
	
}
