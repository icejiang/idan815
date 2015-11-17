package com.dazhong.idan;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class OrderDetailEnd extends Activity {
	
	private TextView tv_addPay;
	private Button btn_confirmEnd;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.order_detail_end);
		tv_addPay = (TextView) findViewById(R.id.add_pay);
		btn_confirmEnd = (Button) findViewById(R.id.confirm_end);
		
		tv_addPay.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(getApplicationContext(), AddPay.class);
				startActivity(intent);
				
			}
		});
		
		btn_confirmEnd.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(getApplicationContext(), MainActivity.class);
				startActivity(intent);
				
			}
		});
		
	}

}
