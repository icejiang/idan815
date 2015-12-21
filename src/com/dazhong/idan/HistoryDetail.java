package com.dazhong.idan;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
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
	private ImageView iv_return;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.history_detail);
		
		NoteInfo noteInfo = (NoteInfo) getIntent().getSerializableExtra("NOTEINFO");
		findView();
		setView(noteInfo);
		
		iv_return.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				HistoryDetail.this.finish();
			}
		});
	}
	
	
	private void findView(){
		titleID = (TextView) findViewById(R.id.history_titleID);
		date = (TextView) findViewById(R.id.history_date_detail);
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
		iv_return = (ImageView) findViewById(R.id.return_historydetail);
	}
	
	private void setView(NoteInfo noteInfo){
		titleID.setText(noteInfo.getNoteID());
		date.setText(noteInfo.getNoteDate());
		pickupTime.setText(noteInfo.getServiceBegin());
		leaveTime.setText(noteInfo.getServiceEnd());
		type.setText(noteInfo.getServiceTypeName());
		mile.setText(noteInfo.getDoServiceKms()+"公里");
		time.setText(noteInfo.getDoServiceTime()+"小时");
		extraMile.setText(noteInfo.getOverKMs()+"公里");
		extraTime.setText(noteInfo.getOverHours()+"小时");
		record.setText(noteInfo.getServiceRoute());
		cost_base.setText(noteInfo.getFeePrice()+"元");
		cost_bridge.setText(noteInfo.getFeeBridge()+"元");
		cost_parking.setText(noteInfo.getFeePark()+"元");
		cost_meal.setText(noteInfo.getFeeLunch()+"元");
		cost_hotel.setText(noteInfo.getFeeHotel()+"元");
		cost_extraMile.setText(noteInfo.getFeeOverKMs()+"元");
		cost_extraTime.setText(noteInfo.getFeeOverTime()+"元");
		cost_other.setText(noteInfo.getFeeOther()+"元");
		cost_alter.setText(noteInfo.getFeeBack()+"元");
		cost_all.setText(noteInfo.getFeeTotal()+"元");
		
		
	}
	
}
