package com.dazhong.idan;

import java.lang.ref.WeakReference;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.baidu.trace.OnEntityListener;
import com.baidu.trace.OnStartTraceListener;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
	private TextView contacter;
	private TextView contacterNum;
	
	 /**
     * 开启轨迹服务监听器
     */
    private OnStartTraceListener startTraceListener = null;
    /**
     * Entity监听器
     */
    private OnEntityListener entityListener = null;
    private TrackUploadHandler mHandler = null;
    private BaiduUtil baiduUtil;
    private myApplication trackApp;
	
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
		mHandler = new TrackUploadHandler(this);
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
		Log.i("jxb", "serviceType = "+taskInfo.ServiceType());
		if (taskInfo.getRouteNoteCount()>0 && taskInfo.ServiceType() != 7){
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
						.setTitle(getResources().getString(R.string.str_routebegin))
						.setView(editText)
						.setPositiveButton(
								getResources().getString(R.string.str_confirmbegin),
								new android.content.DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {

										String input = editText.getText()
												.toString();
										if (input.equals("")) {
											Toast.makeText(
													getApplicationContext(),
													getResources().getString(R.string.str_notice_emptyroute),
													Toast.LENGTH_SHORT).show();
										} else {
											baiduUtil = new BaiduUtil();
											trackApp = (myApplication) getApplicationContext();
											
											initOnStartTraceListener();
//											initOnEntityListener();
											baiduUtil.startTrace(trackApp, startTraceListener);
											trackApp.getClient().queryRealtimeLoc(trackApp.getServiceId(), entityListener);
											int startTime = (int) (System.currentTimeMillis() / 1000);
											
											myStateInfo.setCurrentKMS(input);
											noteInfo = new NoteInfo();
											noteInfo.setRouteBegin(input);
											noteInfo.setStartTime(startTime);
											putDataIntoNote();
											myStateInfo.setCurrentNote(null);
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
						.setTitle(getResources().getString(R.string.str_routecontinue))
						.setView(editText)
						.setPositiveButton(
								getResources().getString(R.string.str_notecontinue),
								new android.content.DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										String input = editText.getText().toString();
										if (input.equals("")) {
											Toast.makeText(
													getApplicationContext(),
													getResources().getString(R.string.str_notice_emptyroute),
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
						.setTitle(getResources().getString(R.string.str_routebegin))
						.setView(editText)
						.setPositiveButton(
								getResources().getString(R.string.str_confirm),
								new android.content.DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {

										String input = editText.getText()
												.toString();
										if (input.equals("")) {
											Toast.makeText(
													getApplicationContext(),
													getResources().getString(R.string.str_notice_emptyroute),
													Toast.LENGTH_SHORT).show();
										} else {
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
		contacterNum.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				callNum(contacterNum.getText().toString());
				
			}
		});
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
		contacter = (TextView) findViewById(R.id.detail_contacter);
		contacterNum = (TextView) findViewById(R.id.detail_contacterNum);
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
		serviceTime.setText(taskInfo.SaleTime()+getResources().getString(R.string.str_hour));
		serviceMile.setText(taskInfo.SaleKMs()+getResources().getString(R.string.str_mile));
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
			bridgeFeetype.setText(getResources().getString(R.string.str_yes));
		} else {
			bridgeFeetype.setText(getResources().getString(R.string.str_no));
		}
		if(taskInfo.getOutfeetype() == 1){
			outFeeType.setText(getResources().getString(R.string.str_yes));
		} else {
			outFeeType.setText(getResources().getString(R.string.str_no));
		}
		contacter.setText(taskInfo.getContacter());
		contacterNum.setText(taskInfo.getContacterNum());
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
//		if (!today.equals(taskInfo.ServiceEnd())){
//			noteInfo.setFeeBridge(taskInfo.getFeeBridge());
//			noteInfo.setFeeHotel(taskInfo.SaleHotelFee());
//		}
		if (!taskInfo.ServiceBegin().equals(taskInfo.ServiceEnd()) && today.equals(taskInfo.ServiceEnd())){
			//do nothing
		} else {
			noteInfo.setFeeBridge(taskInfo.getFeeBridge());
			noteInfo.setFeeHotel(taskInfo.SaleHotelFee());
		}
		noteInfo.setInvoiceType(taskInfo.InvoiceType());
		noteInfo.setCustomerCompany(taskInfo.getCustomerCompany());
		noteInfo.setBalanceType(taskInfo.getBalancetype());
		noteInfo.setTaskCode(taskInfo.TaskCode());
		noteInfo.setActualRentNoTax(taskInfo.getActualRentNoTax());
		noteInfo.setExceedMileFeeNoTax(taskInfo.getExceedMileFeeNoTax());
		noteInfo.setExceedTimeFeeNoTax(taskInfo.getExceedTimeFeeNoTax());
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
	
	
	/**
     * 初始化OnStartTraceListener
     */
    private void initOnStartTraceListener() {
    	
		if (null == startTraceListener) {
			// 初始化startTraceListener
			startTraceListener = new OnStartTraceListener() {

				// 开启轨迹服务回调接口（arg0 : 消息编码，arg1 : 消息内容，详情查看类参考）
				public void onTraceCallback(int arg0, String arg1) {
					// TODO Auto-generated method stub
					 mHandler.obtainMessage(arg0, "开启轨迹服务回调接口消息 [消息编码 : " + arg0 + "，消息内容 : " + arg1 + "]").sendToTarget();
				}

				// 轨迹服务推送接口（用于接收服务端推送消息，arg0 : 消息类型，arg1 : 消息内容，详情查看类参考）
				public void onTracePushCallback(byte arg0, String arg1) {
					// TODO Auto-generated method stub
				}

			};
		}
    }
    
	
	static class TrackUploadHandler extends Handler {
        WeakReference<OrderDetail> trackUpload;

        TrackUploadHandler(OrderDetail trackUploadFragment) {
            trackUpload = new WeakReference<OrderDetail>(trackUploadFragment);
        }

        @Override
        public void handleMessage(Message msg) {
        	OrderDetail tu = trackUpload.get();
//            Toast.makeText(tu.trackApp, (String) msg.obj, Toast.LENGTH_LONG).show();
            Log.i("jxb", (String) msg.obj);

        }
    }
	
}
