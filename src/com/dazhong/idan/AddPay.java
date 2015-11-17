package com.dazhong.idan;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class AddPay extends Activity {

	private EditText et_road;
	private EditText et_eat;
	private EditText et_parking;
	private EditText et_other;
	private Button bt_save;
	private String key_road = "ROAD";
	private String key_meals = "MEALS";
	private String key_parking = "PARKING";
	private String key_other = "OTHER";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_pay);
		
		findView();
		
		bt_save.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {

				Bundle bundle = new Bundle();
				bundle.putString(key_road, et_road.getText().toString());
				bundle.putString(key_meals, et_eat.getText().toString());
				bundle.putString(key_parking, et_parking.getText().toString());
				bundle.putString(key_other, et_other.getText().toString());
				Intent intent = new Intent();
				intent.putExtras(bundle);
				intent.setClass(getApplicationContext(), OrderDetailEnd.class);
				startActivity(intent);
				
			}
		});
	}
	
	
	private void findView(){
		
		et_road = (EditText) findViewById(R.id.pay_road);
		et_eat = (EditText) findViewById(R.id.pay_eat);
		et_parking = (EditText) findViewById(R.id.pay_parking);
		et_other = (EditText) findViewById(R.id.pay_other);
		bt_save = (Button) findViewById(R.id.bt_save);
		
	}
}
