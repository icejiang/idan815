package com.dazhong.idan;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class OrderDetail extends Activity {
	
	private Button btn_start;
	private int input_start;
	private int input_end;
	private int position;
	public static String INPUT_KEY = "INPUT";
	private TaskInfo taskInfo;
	private TextView service_start;
	private TextView service_end;
	private TextView onboardTime;
	private TextView type;
	private TextView placeName;
	private TextView placeNum;
	private TextView name;
	private TextView number;
	private TextView location;
	private TextView destination;
	private TextView flightNum;
	private TextView plateNum;
	private TextView carType;
	private TextView serviceTime;
	private TextView serviceMile;
	private TextView dispatcher;
	private TextView dispatcherNum;
	private TextView salesman;
	private TextView salesmanNum;
	private TextView invoice;
	private TextView remark;
	private TextView remark_sales;
	private TextView payments;
	private LinearLayout flightLayout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.order_detail);
		
		position = getIntent().getIntExtra(MainActivity.POSITION, 0);
		taskInfo = MainActivity.tasklist.get(position);
		findview();
		setData();
		btn_start = (Button) findViewById(R.id.btn_start);
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
//								noteInfo.setRouteBegin(input);
								Intent intent = new Intent();
//								putData(noteInfo);
								intent.putExtra("input_start", input);
								intent.putExtra(INPUT_KEY, position);
								intent.setClass(OrderDetail.this, InService.class);
								startActivity(intent);
							}
						}
					}).show();
				
			}
		});
		
		
		/*btn_end.setOnClickListener(new OnClickListener() {
			
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
								Intent intent = new Intent();
								intent.putExtra(INPUT_KEY, input_end-input_start);
								intent.setClass(OrderDetail.this, OrderDetailEnd.class);
								startActivity(intent);
							}
						}
					}
				}).show();
			}
		});*/
		
		
	}
	
	private void findview(){
		
		service_start = (TextView) findViewById(R.id.detail_service_start);
		service_end = (TextView) findViewById(R.id.detail_service_end);
		onboardTime = (TextView) findViewById(R.id.detail_onboard_time);
		type = (TextView) findViewById(R.id.detail_type);
		placeName = (TextView) findViewById(R.id.detail_placename);
		placeNum = (TextView) findViewById(R.id.detail_placenum);
		name = (TextView) findViewById(R.id.detail_name);
		number = (TextView) findViewById(R.id.detail_num);
		location = (TextView) findViewById(R.id.detail_location);
		destination = (TextView) findViewById(R.id.detail_destination);
		flightNum = (TextView) findViewById(R.id.detail_flightNum);
		plateNum = (TextView) findViewById(R.id.detail_plateNum);
		carType = (TextView) findViewById(R.id.detail_carType);
		serviceTime = (TextView) findViewById(R.id.detail_serviceTime);
		serviceMile = (TextView) findViewById(R.id.detail_serviceMile);
		dispatcher = (TextView) findViewById(R.id.detail_dispatcher);
		dispatcherNum = (TextView) findViewById(R.id.detail_dispatcherNum);
		salesman = (TextView) findViewById(R.id.detail_salesman);
		salesmanNum = (TextView) findViewById(R.id.detail_salesmanNum);
		invoice = (TextView) findViewById(R.id.detail_invoice);
		remark = (TextView) findViewById(R.id.detail_remark);
		remark_sales = (TextView) findViewById(R.id.detail_remark_sales);
		flightLayout = (LinearLayout) findViewById(R.id.layout_flight);
		payments = (TextView) findViewById(R.id.payments);
			
	}
	
	private void setData(){
		service_start.setText(taskInfo.ServiceBegin());
		service_end.setText(taskInfo.ServiceEnd());
		onboardTime.setText(taskInfo.OnboardTime());
		type.setText(taskInfo.ServiceTypeName());
		placeName.setText(taskInfo.Bookman());
		placeNum.setText(taskInfo.BookTel());
		name.setText(taskInfo.Customer());
		number.setText(taskInfo.CustomerTel());
		location.setText(taskInfo.PickupAddress());
		destination.setText(taskInfo.LeaveAddress());
		String flightNumber = taskInfo.FrightNum();
		if(flightNumber.equals("")||flightNumber == null){
			flightLayout.setVisibility(View.GONE);
		} else {
			flightLayout.setVisibility(View.VISIBLE);
			flightNum.setText(flightNumber);
		}
		serviceTime.setText(taskInfo.SaleTime()+"小时");
		serviceMile.setText(taskInfo.SaleKMs()+"公里");
		dispatcher.setText(taskInfo.Planner());
		dispatcherNum.setText(taskInfo.PlannerTel());
		salesman.setText(taskInfo.Salesman());
		salesmanNum.setText(taskInfo.SalesmanTel());
		invoice.setText(taskInfo.InvoiceTypeName());
		remark.setText(taskInfo.PlannerRemark());
		remark_sales.setText(taskInfo.SalesRemark());
		plateNum.setText(taskInfo.CarNumber());
		carType.setText(taskInfo.CarType());
		payments.setText(taskInfo.getBalancetypename());
	}
	
	
	
	
}
