package com.dazhong.idan;

import android.R.integer;
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
	private TextView tv_addRecord;
	private TextView tv_record;
	private Bundle mBundle;
	private int mile;
	private int all;
	
	
	public final static int REQUEST_CODE = 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.route_note);
		findView();
		Intent intent = getIntent();
		mile = intent.getIntExtra(InService.INPUT_TOTAL_KEY, 0);
		tv_mile.setText(mile+"公里");
		tv_addPay.setOnClickListener(this);
		tv_print.setOnClickListener(this);
		tv_addRecord.setOnClickListener(this);
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
		tv_addRecord = (TextView) findViewById(R.id.tv_add_record);
		tv_record = (TextView) findViewById(R.id.tv_record);
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
		case R.id.tv_add_record:
			final EditText editText = new EditText(OrderDetailEnd.this);
//			editText.setInputType(InputType.TYPE_CLASS_NUMBER);
			new AlertDialog.Builder(OrderDetailEnd.this)
					.setTitle("请填写营运记录")
					.setView(editText)
					.setPositiveButton(
							"确定",
							new android.content.DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {

									String input = editText.getText()
											.toString();
									tv_record.setText(input);
								}

							}).show();
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
					int all = 0;
					if (!road.equals("")) {
						all = Integer.parseInt(road);
						tv_road.setText(road + "元");
					}
					if (!meals.equals("")) {
						all += Integer.parseInt(meals);
						tv_meals.setText(meals + "元");
					}
					if (!parking.equals("")) {
						all += Integer.parseInt(parking);
						tv_parking.setText(parking + "元");
					}
					if (!other.equals("")) {
						all += Integer.parseInt(other);
						tv_other.setText(other + "元");
					}
					tv_all.setText(all + "元");
					this.all = all;
				}
			}
		}
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
