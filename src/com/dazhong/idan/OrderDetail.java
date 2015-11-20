package com.dazhong.idan;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class OrderDetail extends Activity {
	
	private Button btn_start;
	private Button btn_end;
	private int input_start;
	private int input_end;
	public static String INPUT_KEY = "INPUT";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.order_detail);
		
		btn_start = (Button) findViewById(R.id.btn_start);
		btn_end = (Button) findViewById(R.id.btn_end);
		btn_start.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final EditText editText = new EditText(OrderDetail.this);
				editText.setInputType(InputType.TYPE_CLASS_NUMBER);
				new AlertDialog.Builder(OrderDetail.this).setTitle("请填写起始路码").
					setView(editText).setPositiveButton("确定并开始业务", new android.content.DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							
							String input = editText.getText().toString();
							if(input.equals("")){
								Toast.makeText(getApplicationContext(), "路码不能为空", Toast.LENGTH_SHORT).show();
							} else {
								input_start = Integer.parseInt(input);
								Log.i("jxb", "起始路码 = "+input_start);
								btn_start.setVisibility(View.GONE);
								btn_end.setVisibility(View.VISIBLE);
							}
						}
					}).show();
				
			}
		});
		
		
		btn_end.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				final EditText editText = new EditText(OrderDetail.this);
				editText.setInputType(InputType.TYPE_CLASS_NUMBER);
				new AlertDialog.Builder(OrderDetail.this).setTitle("请填写结束路码").
				setView(editText).setPositiveButton("填写下一步", new android.content.DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						
						String input = editText.getText().toString();
						if(input.equals("")){
							Toast.makeText(getApplicationContext(), "路码不能为空", Toast.LENGTH_SHORT).show();
						} else {
							input_end = Integer.parseInt(input);
							Log.i("jxb", "结束路码 = "+input_end);
							if (input_end < input_start){
								Toast.makeText(getApplicationContext(), "结束路码小于起始路码，请确认输入", Toast.LENGTH_SHORT).show();
							} else {
								btn_end.setVisibility(View.GONE);
								btn_start.setVisibility(View.VISIBLE);
								Intent intent = new Intent();
								intent.putExtra(INPUT_KEY, input_end-input_start);
								intent.setClass(OrderDetail.this, OrderDetailEnd.class);
								startActivity(intent);
							}
						}
					}
				}).show();
			}
		});
		
		
	}
	
}
