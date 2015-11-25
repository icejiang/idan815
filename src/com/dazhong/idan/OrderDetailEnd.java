package com.dazhong.idan;

import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class OrderDetailEnd extends Activity implements OnClickListener {
	
	private TextView tv_addPay;
	private Button btn_confirmEnd;
	private TextView tv_road;
	private TextView tv_meals;
	private TextView tv_parking;
	private TextView tv_other;
	private TextView tv_all;
	private TextView tv_mile;
	private TextView tv_print;
	private Bundle mBundle;
	private int mile;
	private int all;
	
	
	public final static int REQUEST_CODE = 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.order_detail_end);
		findView();
		Intent intent = getIntent();
		mile = intent.getIntExtra(OrderDetail.INPUT_KEY, 0);
		tv_mile.setText(mile+"公里");
		tv_addPay.setOnClickListener(this);
		tv_print.setOnClickListener(this);
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
		tv_all = (TextView) findViewById(R.id.tv_all);
		tv_mile = (TextView) findViewById(R.id.tv_mile);
		tv_print = (TextView) findViewById(R.id.tv_print);
//		btn_confirmEnd = (Button) findViewById(R.id.confirm_end);
		
	}
	
	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.add_pay:
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
			Intent intent3 = new Intent();
			intent3.putExtra("MYKEY", mBundle);
			intent3.putExtra("MILE", mile);
			intent3.putExtra("ALL", all);
			intent3.setClass(getApplicationContext(), PrintActivity.class);
			startActivity(intent3);
			break;
		default:
			break;
		}
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		super.onActivityResult(requestCode, resultCode, data);
		
		if(requestCode == REQUEST_CODE){
			if (requestCode == AddPay.RESULT_CODE){
				Bundle bundle = data.getExtras();
				this.mBundle = bundle;
				String road = bundle.getString(AddPay.key_road);
				String meals = bundle.getString(AddPay.key_meals);
				String parking = bundle.getString(AddPay.key_parking);
				String other = bundle.getString(AddPay.key_other);
				int all = 0;
				if(!road.equals("")){
					all = Integer.parseInt(road);
					tv_road.setText(road+"元");
				}
				if(!meals.equals("")){
					all += Integer.parseInt(meals);
					tv_meals.setText(meals+"元");
				}
				if(!parking.equals("")){
					all += Integer.parseInt(parking);
					tv_parking.setText(parking+"元");
				}
				if(!other.equals("")){
					all += Integer.parseInt(other);
					tv_other.setText(other+"元");
				}
				tv_all.setText(all+"元");
				this.all = all;
			}
		}
		
		
		
		
		
		
		
		
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
