package com.dazhong.idan;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class OrderDetail extends Activity {
	
	private TextView tv_start;
	private TextView tv_finish;
//	private int input_start;
//	private int input_end;
	private int position;
	public static String INPUT_KEY = "INPUT";
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
	private ImageView iv_return;
	private LinearLayout flightLayout;
	private StateInfo myStateInfo;
	private getStateInfo myGetStateInfo;
	private NoteInfo noteInfo;
	private TaskInfo taskInfo;
	private TextView titleid;
	private TextView outFeeType;
	private TextView bridgeFeetype;
	private TextView company;
	private TextView startRoute;
	private TextView addStart;
	private RelativeLayout addLayout;
	private TextView tv_pause;
	private NoteInfo pauseNote;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.order_detail);
		ActivityControler.addActivity(this);
		position = getIntent().getIntExtra(MainActivity.POSITION, 0);
		if (iDanApp.getInstance().getTasklist().size() != 0){
			taskInfo = iDanApp.getInstance().getTasklist().get(position);
		} else {
			Intent intent = new Intent();
			intent.setClass(getApplicationContext(), MainActivity.class);
			startActivity(intent);
		}
		getInfoValue.setTaskRead(iDanApp.getInstance().getEMPLOYEEID(), taskInfo.TaskID());
		findview();
		setData();
		try {
			myGetStateInfo = getStateInfo.getInstance(getApplicationContext());
			myStateInfo = myGetStateInfo.getStateinfo();
			pauseNote = myStateInfo.getPauseNote();
			myStateInfo.setCurrentState(11);
			myStateInfo.setCurrentTask(taskInfo);
			myStateInfo.setPosition(position);
			myGetStateInfo.setStateinfo(myStateInfo);
		} catch (Exception e1) {	
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (taskInfo.getRouteNoteCount()>0){
			addLayout.setVisibility(View.GONE);
			tv_start.setVisibility(View.GONE);
			tv_finish.setVisibility(View.VISIBLE);
			tv_pause.setVisibility(View.GONE);
		} else if (null != pauseNote && pauseNote.getTaskID().equals(taskInfo.TaskID())){
			addLayout.setVisibility(View.GONE);
			tv_start.setVisibility(View.GONE);
			tv_finish.setVisibility(View.GONE);
			tv_pause.setVisibility(View.VISIBLE);
		} else {
			addLayout.setVisibility(View.VISIBLE);
			tv_start.setVisibility(View.VISIBLE);
			tv_finish.setVisibility(View.GONE);
			tv_pause.setVisibility(View.GONE);
		}
		tv_start.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.i("jxb", startRoute.getText().toString());
				if (startRoute.getText().toString().equals("")){
					
				final EditText editText = new EditText(OrderDetail.this);
				editText.setInputType(InputType.TYPE_CLASS_NUMBER);
				new AlertDialog.Builder(OrderDetail.this)
						.setTitle("请填写起始路码")
						.setView(editText)
						.setPositiveButton(
								"确认并开始业务",
								new android.content.DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {

										String input = editText.getText()
												.toString();
										if (input.equals("")) {
											Toast.makeText(
													getApplicationContext(),
													"路码不能为空",
													Toast.LENGTH_SHORT).show();
										} else {
											myStateInfo.setCurrentKMS(input);
											noteInfo = new NoteInfo();
											noteInfo.setRouteBegin(input);
											putDataIntoNote();
											myStateInfo.setCurrentNote(noteInfo);
											myGetStateInfo.setStateinfo(myStateInfo);
											Intent intent = new Intent();
											intent.setClass(OrderDetail.this,
													InService.class);
											startActivity(intent);
										}
									}
								}).show();
				} else {
					String input = startRoute.getText().toString();
					Log.i("jxb", "input = "+startRoute.getText().toString());
					myStateInfo.setCurrentKMS(input);
					noteInfo = new NoteInfo();
					noteInfo.setRouteBegin(input);
					putDataIntoNote();
					myStateInfo.setCurrentNote(noteInfo);
					myGetStateInfo.setStateinfo(myStateInfo);
					Intent intent = new Intent();
					intent.setClass(OrderDetail.this,
							InService.class);
					startActivity(intent);
				}

			}
		});
		tv_pause.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				final EditText editText = new EditText(OrderDetail.this);
				editText.setInputType(InputType.TYPE_CLASS_NUMBER);
				new AlertDialog.Builder(OrderDetail.this)
						.setTitle("请填写当前路码")
						.setView(editText)
						.setPositiveButton(
								"继续本次业务",
								new android.content.DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										String input = editText.getText().toString();
										if (input.equals("")) {
											Toast.makeText(
													getApplicationContext(),
													"路码不能为空",
													Toast.LENGTH_SHORT).show();
										} else {
											pauseNote.setPauseEnd(Integer.parseInt(input));
											myStateInfo.setCurrentNote(pauseNote);
											myStateInfo.setPauseNote(null);
											myGetStateInfo.setStateinfo(myStateInfo);
											Intent intent = new Intent();
											intent.setClass(OrderDetail.this, InService.class);
											startActivity(intent);
										}
									}

								}).show();

			}
		});
		
		addStart.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.i("jxb", "click");
				final EditText editText = new EditText(OrderDetail.this);
				editText.setInputType(InputType.TYPE_CLASS_NUMBER);
				new AlertDialog.Builder(OrderDetail.this)
						.setTitle("请填写起始路码")
						.setView(editText)
						.setPositiveButton(
								"确认",
								new android.content.DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {

										String input = editText.getText()
												.toString();
										if (input.equals("")) {
											Toast.makeText(
													getApplicationContext(),
													"路码不能为空",
													Toast.LENGTH_SHORT).show();
										} else {
//											myStateInfo.setCurrentKMS(input);
//											noteInfo = new NoteInfo();
//											noteInfo.setRouteBegin(input);
//											putDataIntoNote();
//											myStateInfo.setCurrentNote(noteInfo);
//											myGetStateInfo.setStateinfo(myStateInfo);
//											Intent intent = new Intent();
//											intent.setClass(OrderDetail.this,
//													InService.class);
//											startActivity(intent);
											
											startRoute.setText(input);
										}
									}
								}).show();

			}
		});
		iv_return.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(getApplicationContext(), MainActivity.class);
				startActivity(intent);
			}
		});
		placeNum.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				callNum(placeNum.getText().toString());
			}});
		number.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				callNum(number.getText().toString());
			}});
		dispatcherNum.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				callNum(dispatcherNum.getText().toString());
			}});
		salesmanNum.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				callNum(salesmanNum.getText().toString());
			}});
	}
	
	private void callNum(String phoneno){
		if(phoneno!=null && !"".equals(phoneno.trim())){
			Intent intent=new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+phoneno));
			startActivity(intent);
		}
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
		titleid = (TextView) findViewById(R.id.detail_titleid);
		iv_return = (ImageView) findViewById(R.id.return_detail);
		outFeeType = (TextView) findViewById(R.id.detail_outfeetype);
		bridgeFeetype = (TextView) findViewById(R.id.detail_bridgefeetype);
		tv_start = (TextView) findViewById(R.id.tv_start);
		tv_finish = (TextView) findViewById(R.id.tv_finish);
		company = (TextView) findViewById(R.id.detail_company);
		startRoute = (TextView) findViewById(R.id.detail_startRoute);
		addStart = (TextView) findViewById(R.id.detail_addStart);
		addLayout = (RelativeLayout) findViewById(R.id.detail_addLayout);
		tv_pause = (TextView) findViewById(R.id.tv_pause);
	}
	
	private void setData(){
		titleid.setText(taskInfo.TaskCode());
		service_start.setText(taskInfo.ServiceBegin());
		service_end.setText(taskInfo.ServiceEnd());
		onboardTime.setText(taskInfo.OnboardTime());
		type.setText(taskInfo.ServiceTypeName());
		company.setText(taskInfo.getCustomerCompany());
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
		if(taskInfo.getBridgefeetype() == 1){
			bridgeFeetype.setText("是");
		} else {
			bridgeFeetype.setText("否");
		}
		if(taskInfo.getOutfeetype() == 1){
			outFeeType.setText("是");
		} else {
			outFeeType.setText("否");
		}
	}
	
	private void putDataIntoNote(){
		int taskAccount = myStateInfo.getTimeOfTaskOneDay()+1;
		myStateInfo.setTimeOfTaskOneDay(taskAccount);
		String account = "";
		if(taskAccount<10){
			account = "00"+taskAccount;
		} else if (taskAccount<100) {
			account = "0"+taskAccount;
		} else {
			account = taskAccount+"";
		}
		SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMdd");
		String today = sDateFormat.format(new Date(System.currentTimeMillis()));
		String noteId = "DZ"+today+taskInfo.DriverID()+account;
		noteInfo.setNoteID(noteId);
		noteInfo.setNoteDate(today);
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
		Date curDate = new Date(System.currentTimeMillis());
		String str = formatter.format(curDate);
		noteInfo.setServiceBegin(str);
		noteInfo.setCarID(taskInfo.CarID());
		noteInfo.setCarNumber(taskInfo.CarNumber());
		noteInfo.setDriverID(taskInfo.DriverID());
		noteInfo.setDriverName(taskInfo.DriverName());
//		noteInfo.setFeeBridge(taskInfo.);
		noteInfo.setFeePrice(taskInfo.SalePrice());
		noteInfo.setSaleName(taskInfo.Salesman());
		// id/code
		noteInfo.setPlanID(taskInfo.TaskCode());
		noteInfo.setOnBoardAddress(taskInfo.PickupAddress());
		noteInfo.setLeaveAddress(taskInfo.LeaveAddress());
//		noteInfo.setRouteEnd(routeEnd);
		noteInfo.setFeeChoice(taskInfo.SalePriceCal());
		noteInfo.setServiceKMs(taskInfo.SaleKMs());
		noteInfo.setServiceTime(taskInfo.SaleTime());
		noteInfo.setServiceKMs(taskInfo.SaleKMs());
		noteInfo.setServiceTime(taskInfo.SaleTime());
		noteInfo.setCustomerName(taskInfo.Customer());
		noteInfo.setTaskID(taskInfo.TaskID());
		noteInfo.setServiceTypeName(taskInfo.ServiceTypeName());
		noteInfo.setOutfeetype(taskInfo.getOutfeetype());
		noteInfo.setBridgefeetype(taskInfo.getBridgefeetype());
		noteInfo.setInvoiceTaxRate(taskInfo.getInvoiceTaxRate());
		if (!today.equals(taskInfo.ServiceEnd())){
			noteInfo.setFeeBridge(taskInfo.getFeeBridge());
			noteInfo.setFeeHotel(taskInfo.SaleHotelFee());
		}
		noteInfo.setInvoiceType(taskInfo.InvoiceType());
		noteInfo.setCustomerCompany(taskInfo.getCustomerCompany());
		noteInfo.setBalanceType(taskInfo.getBalancetype());
	}
	
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			Intent intent = new Intent();
			intent.setClass(getApplicationContext(), MainActivity.class);
			startActivity(intent);
		}
		return super.onKeyDown(keyCode, event);
	}
	
	
}
