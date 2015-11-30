package com.dazhong.idan;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class PrintActivity extends Activity {
	
	private TextView print_confirm;
	private TextView tv_road;
	private TextView tv_parking;
	private TextView tv_meals;
	private TextView tv_other;
	private TextView tv_mile;
	private TextView tv_all;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.printf);
		
		findView();
		setData();
		print_confirm.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(getApplicationContext(), MainActivity.class);
				startActivity(intent);
				
			}
		});
		
		
	}
	
	private void setData() {
		Bundle bundle = getIntent().getBundleExtra("MYKEY");
		int all = getIntent().getIntExtra("ALL", 0);
		int mile = getIntent().getIntExtra("MILE", 0);
		if (bundle != null) {
			String road = bundle.getString(AddPay.key_road);
			String meals = bundle.getString(AddPay.key_meals);
			String parking = bundle.getString(AddPay.key_parking);
			String other = bundle.getString(AddPay.key_other);
			if (!road.equals("")) {
				tv_road.setText(road + "元");
			}
			if (!meals.equals("")) {
				tv_meals.setText(meals + "元");
			}
			if (!parking.equals("")) {
				tv_parking.setText(parking + "元");
			}
			if (!other.equals("")) {
				tv_other.setText(other + "元");
			}
		}
		tv_all.setText(all + "元");
		tv_mile.setText(mile + "公里");
	}
	
	
	private void findView(){
		print_confirm = (TextView) findViewById(R.id.print_confirm);
		tv_road = (TextView) findViewById(R.id.tv_road_print);
		tv_parking = (TextView) findViewById(R.id.tv_parking_print);
		tv_meals = (TextView) findViewById(R.id.tv_meals_print);
		tv_other = (TextView) findViewById(R.id.tv_other_print);
		tv_mile = (TextView) findViewById(R.id.tv_mile_print);
		tv_all = (TextView) findViewById(R.id.tv_all_print);
	}
}
