package com.dazhong.idan;

import java.lang.ref.WeakReference;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.baidu.trace.OnStopTraceListener;
import com.baidu.trace.OnTrackListener;

public class InService extends Activity {
	
	private TextView btn_end;
	private int input_end;
//	private NoteInfo noteInfo;
	private TaskInfo taskInfo;
	private NoteInfo noteInfo;
	private NoteInfo pauseNote;
	public static String INPUT_TOTAL_KEY = "INPUT_TOTAL";
	
	private TextView onboard;
	private TextView placeName;
	private TextView placeNum;
	private TextView name;
	private TextView number;
	private TextView location;
	private TextView destination;
	private TextView mileStart;
	private TextView dispatcher;
	private TextView dispatchNum;
	private TextView salesman;
	private TextView salesNum;
	private TextView noteID;
	private StateInfo myStateInfo;
	private getStateInfo myGetStateInfo;
	private int taskAccount;
	private TextView tv_bridge;
	private TextView tv_parking;
	private TextView tv_meals;
	private TextView tv_hotel;
	private TextView tv_other;
	private TextView tv_record;
	private TextView tv_add;
	private TextView contacter;
	private TextView contacterNum;
	private TextView flightNum;
	private TextView remark;
	private TextView company;
	private LinearLayout flightLayout;
	private Button bt_pause;
	public final static int REQUEST_CODE = 2;
	private LocationReceiver myReceiver;
	public LocationClient mLocationClient = null;
    public BDLocationListener myListener = new MyLocationListener();
    private String record_before = "";
	
//    /**
//     * �����켣���������
//     */
//    private OnStopTraceListener stopTraceListener = null;
//    /**
//     * Track������
//     */
//    protected static OnTrackListener trackListener = null;
////    protected RefreshThread refreshThread = null;
//    private BaiduUtil baiduUtil;
//    private myApplication trackApp;
//    private TrackUploadHandler mHandler = null;
//    private Handler handler;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.in_service);
		ActivityControler.addActivity(this);
		try {
			myGetStateInfo = getStateInfo.getInstance(getApplicationContext());
			myStateInfo = myGetStateInfo.getStateinfo();
			taskInfo = myStateInfo.getCurrentTask();
			noteInfo = myStateInfo.getCurrentNote();
			pauseNote = myStateInfo.getPauseNote();
		} catch (Exception e) {
			e.printStackTrace();
		}
//		mHandler = new TrackUploadHandler(this);
//		trackApp = (myApplication) getApplicationContext();
//		baiduUtil = new BaiduUtil();
//		if (null == stopTraceListener) {
//			initOnStopTraceListener();
//		}
//		if (null == trackListener) {
//			initOnTrackListener();
//		}
		findView();
		setData();
		myStateInfo.setCurrentState(13);
		myStateInfo.setCurrentNote(noteInfo);
		myGetStateInfo.setStateinfo(myStateInfo);
		if (null != pauseNote || noteInfo.isHasPaused() ){
			bt_pause.setVisibility(View.GONE);
		} else {
			bt_pause.setVisibility(View.VISIBLE);
		}
		
//		mLocationClient = new LocationClient(getApplicationContext());     //����LocationClient��
//		mLocationClient.registerLocationListener( myListener );    //ע���������
//		initLocation();
//		mLocationClient.start();
//		myReceiver = new LocationReceiver();
//		IntentFilter intentfilter = new IntentFilter();
//		intentfilter.addAction("com.dazhong.idan.myReceiver");
//		registerReceiver(myReceiver, intentfilter);
		
		btn_end.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				myStateInfo.setCurrentState(15);
				myGetStateInfo.setStateinfo(myStateInfo);
				final EditText editText = new EditText(InService.this);
				editText.setInputType(InputType.TYPE_CLASS_NUMBER);
				new AlertDialog.Builder(InService.this).setTitle(getResources().getString(R.string.str_routeend)).
				setView(editText).setPositiveButton(getResources().getString(R.string.str_next), new android.content.DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						
						String input = editText.getText().toString();
						if(input.equals("")){
							Toast.makeText(getApplicationContext(), getResources().getString(R.string.str_notice_emptyroute), Toast.LENGTH_SHORT).show();
						} else {
							input_end = Integer.parseInt(input);
							Log.i("jxb", "����·�� = "+input_end);
							String startKms = noteInfo.getRouteBegin();
							Log.i("jxb", "startKms = "+startKms);
							if (input_end < Integer.parseInt(startKms)){
								Toast.makeText(getApplicationContext(), getResources().getString(R.string.str_notice_lessroute), Toast.LENGTH_SHORT).show();
							} else {
//								mLocationClient.stop();
//								initOnceLocation();
//								mLocationClient.start();
//								int startTime = noteInfo.getStartTime();
//								int stopTime = (int) (System.currentTimeMillis()/1000);
								
								SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
								Date curDate = new Date(System.currentTimeMillis());
								String str = formatter.format(curDate);
								noteInfo.setServiceEnd(str);
								noteInfo.setRouteEnd(input);
//								queryDistance(1, null,startTime,stopTime);
//								baiduUtil.stopTrace(trackApp, stopTraceListener);
								myStateInfo.setCurrentNote(noteInfo);
								myGetStateInfo.setStateinfo(myStateInfo);
//								if (myReceiver != null){
//									unregisterReceiver(myReceiver);
//								}
								Intent intent = new Intent();
								intent.setClass(InService.this, OrderDetailEnd.class);
								startActivity(intent);
							}
						}
					}
				}).show();
			}
		});
		
		bt_pause.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {

				final EditText editText = new EditText(InService.this);
				editText.setInputType(InputType.TYPE_CLASS_NUMBER);
				new AlertDialog.Builder(InService.this).setTitle(getResources().getString(R.string.str_routecontinue)).
				setView(editText).setPositiveButton(getResources().getString(R.string.str_pause), new android.content.DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						String input = editText.getText().toString();
						if(input.equals("")){
							Toast.makeText(getApplicationContext(), getResources().getString(R.string.str_notice_emptyroute), Toast.LENGTH_SHORT).show();
						} else {
							noteInfo.setPauseStart(Integer.parseInt(input));
							noteInfo.setHasPaused(true);
							myStateInfo.setPauseNote(noteInfo);
							myGetStateInfo.setStateinfo(myStateInfo);
							Intent intent = new Intent();
							intent.setClass(InService.this, MainActivity.class);
							startActivity(intent);
						}
						
					}
				}).show();
				
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
		dispatchNum.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				callNum(dispatchNum.getText().toString());
			}});
		salesNum.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				callNum(salesNum.getText().toString());
			}
		});
		contacterNum.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				callNum(contacterNum.getText().toString());
				
			}
		});
		tv_add.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent1 = new Intent();
				intent1.setClass(getApplicationContext(), AddPayInService.class);
				startActivityForResult(intent1, REQUEST_CODE);
				
			}
		});
	}
	

	private void callNum(String phoneno){
		if(phoneno!=null && !"".equals(phoneno.trim())){
			Intent intent=new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+phoneno));
			startActivity(intent);
		}
	}
	private void findView(){
		
		btn_end = (TextView) findViewById(R.id.btn_end);
		
		onboard = (TextView) findViewById(R.id.service_onboard);
		placeName = (TextView) findViewById(R.id.service_placename);
		placeNum = (TextView) findViewById(R.id.service_placenum);
		name = (TextView) findViewById(R.id.service_name);
		number = (TextView) findViewById(R.id.service_number);
		location = (TextView) findViewById(R.id.service_location);
		destination = (TextView) findViewById(R.id.service_destination);
		mileStart = (TextView) findViewById(R.id.service_startMile);
		dispatcher = (TextView) findViewById(R.id.service_dispatcher);
		dispatchNum = (TextView) findViewById(R.id.service_dispatchNum);
		salesman = (TextView) findViewById(R.id.service_salesman);
		salesNum = (TextView) findViewById(R.id.service_salesNum);
		noteID = (TextView) findViewById(R.id.service_noteid);
		tv_bridge = (TextView) findViewById(R.id.service_bridge);
		tv_parking = (TextView) findViewById(R.id.service_parking);
		tv_meals = (TextView) findViewById(R.id.service_meals);
		tv_hotel = (TextView) findViewById(R.id.service_hotel);
		tv_other = (TextView) findViewById(R.id.service_other);
		tv_record = (TextView) findViewById(R.id.service_record);
		tv_add = (TextView) findViewById(R.id.service_addRecord);
		bt_pause = (Button) findViewById(R.id.bt_pause);
		contacter = (TextView) findViewById(R.id.service_contacter);
		contacterNum = (TextView) findViewById(R.id.contacter_number);
		flightNum = (TextView) findViewById(R.id.service_flightNum);
		remark = (TextView) findViewById(R.id.service_remark);
		flightLayout = (LinearLayout) findViewById(R.id.layout_ser_flight);
		company = (TextView) findViewById(R.id.service_company);
	}
	
	private void setData(){
		noteID.setText(noteInfo.getNoteID());
		onboard.setText(noteInfo.getServiceBegin());
		placeName.setText(taskInfo.Bookman());
		placeNum.setText(taskInfo.BookTel());
		name.setText(taskInfo.Customer());
		number.setText(taskInfo.CustomerTel());
		location.setText(taskInfo.PickupAddress());
		destination.setText(taskInfo.LeaveAddress());
		mileStart.setText(noteInfo.getRouteBegin());
		dispatcher.setText(taskInfo.Planner());
		dispatchNum.setText(taskInfo.PlannerTel());
		salesman.setText(taskInfo.Salesman());
		salesNum.setText(taskInfo.SalesmanTel());
		contacter.setText(taskInfo.getContacter());
		contacterNum.setText(taskInfo.getContacterNum());
		company.setText(taskInfo.getCustomerCompany());
		remark.setText(taskInfo.SalesRemark());
		String flightNumber = taskInfo.FrightNum();
		if(flightNumber.equals("")||flightNumber == null){
			flightLayout.setVisibility(View.GONE);
		} else {
			flightLayout.setVisibility(View.VISIBLE);
			flightNum.setText(flightNumber);
		}
		Double taxRate = 1 + noteInfo.getInvoiceTaxRate();
		Double price_bridge = noteInfo.getFeeBridge();
		if (price_bridge > 0){
			if(noteInfo.getBridgefeetype() == 0){
				tv_bridge.setText(reserve2(price_bridge*taxRate)+getResources().getString(R.string.str_yuan));
			} else {
				tv_bridge.setText(price_bridge+getResources().getString(R.string.str_yuan));
			}
		}
		Double price_hotel = noteInfo.getFeeHotel();
		if (price_hotel > 0){
			if (noteInfo.getOutfeetype() == 0){
				tv_hotel.setText(reserve2(price_hotel*taxRate)+getResources().getString(R.string.str_yuan));
			} else {
				tv_hotel.setText(price_hotel+getResources().getString(R.string.str_yuan));
			}
		}
		Double price_meals = noteInfo.getFeeLunch();
		if (price_meals > 0) {
			tv_meals.setText(reserve2(price_meals*taxRate)+getResources().getString(R.string.str_yuan));
		}
		Double price_park = noteInfo.getFeePark();
		if (price_park > 0) {
			tv_parking.setText(reserve2(price_park*taxRate)+getResources().getString(R.string.str_yuan));
		}
		Double price_other = noteInfo.getFeeOther();
		if (price_other > 0) {
			tv_other.setText(reserve2(price_other*taxRate)+getResources().getString(R.string.str_yuan));
		}
	}
	
	private void initLocation(){
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationMode.Hight_Accuracy);//��ѡ��Ĭ�ϸ߾��ȣ����ö�λģʽ���߾��ȣ��͹��ģ����豸
        option.setCoorType("bd09ll");//��ѡ��Ĭ��gcj02�����÷��صĶ�λ�������ϵ
        int span=30*60*1000; //��Сʱһ��
        option.setScanSpan(span);//��ѡ��Ĭ��0��������λһ�Σ����÷���λ����ļ����Ҫ���ڵ���1000ms������Ч��
        option.setIsNeedAddress(true);//��ѡ�������Ƿ���Ҫ��ַ��Ϣ��Ĭ�ϲ���Ҫ
        option.setOpenGps(true);//��ѡ��Ĭ��false,�����Ƿ�ʹ��gps
        option.setLocationNotify(false);//��ѡ��Ĭ��false�������Ƿ�GPS��Чʱ����1S/1��Ƶ�����GPS���
        option.setIsNeedLocationDescribe(true);//��ѡ��Ĭ��false�������Ƿ���Ҫλ�����廯�����������BDLocation.getLocationDescribe��õ�����������ڡ��ڱ����찲�Ÿ�����
        option.setIsNeedLocationPoiList(true);//��ѡ��Ĭ��false�������Ƿ���ҪPOI�����������BDLocation.getPoiList��õ�
        option.setIgnoreKillProcess(false);//��ѡ��Ĭ��true����λSDK�ڲ���һ��SERVICE�����ŵ��˶������̣������Ƿ���stop��ʱ��ɱ��������̣�Ĭ�ϲ�ɱ��  
        option.SetIgnoreCacheException(false);//��ѡ��Ĭ��false�������Ƿ��ռ�CRASH��Ϣ��Ĭ���ռ�
        option.setEnableSimulateGps(false);//��ѡ��Ĭ��false�������Ƿ���Ҫ����GPS��������Ĭ����Ҫ
        mLocationClient.setLocOption(option);
    }
	
	private void initOnceLocation(){
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationMode.Hight_Accuracy);
        option.setCoorType("bd09ll");
        option.setIsNeedAddress(true);
        option.setOpenGps(true);
        option.setIsNeedLocationDescribe(true);
        mLocationClient.setLocOption(option);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST_CODE) {
			if (null != data) {
				Bundle bundle = data.getExtras();
				String road = bundle.getString(AddPayInService.key_road);
				String meals = bundle.getString(AddPayInService.key_meals);
				String parking = bundle.getString(AddPayInService.key_parking);
				String other = bundle.getString(AddPayInService.key_other);
				String hotel = bundle.getString(AddPayInService.key_hotel);
				String routeOn = bundle.getString(AddPayInService.key_routeOn);
				String record = bundle.getString(AddPayInService.key_record);
				int bridgeFeeType = noteInfo.getBridgefeetype();
				int outFeeType = noteInfo.getOutfeetype();
				double taxRate = 1 + noteInfo.getInvoiceTaxRate();
				if (!road.equals("") && !road.equals(".")) {
					if (bridgeFeeType == 0){
						Double fee = reserve2(Double.valueOf(road)*taxRate);
						tv_bridge.setText(fee + getResources().getString(R.string.str_yuan));
					} else {
						tv_bridge.setText(Double.valueOf(road) + getResources().getString(R.string.str_yuan));
					}
					noteInfo.setFeeBridge(Double.valueOf(road));
				}
				if (!parking.equals("") && !parking.equals(".")) {
					if (bridgeFeeType == 0){
						Double fee = reserve2(Double.valueOf(parking)*taxRate);
						tv_parking.setText(fee + getResources().getString(R.string.str_yuan));
					} else {
						tv_parking.setText(Double.valueOf(parking) + getResources().getString(R.string.str_yuan));
					}
					noteInfo.setFeePark(Double.valueOf(parking));
				}
				if (!meals.equals("") && !meals.equals(".")) {
					if (outFeeType == 0){
						Double fee = reserve2(Double.valueOf(meals)*taxRate);
						tv_meals.setText(fee + getResources().getString(R.string.str_yuan));
					} else {
						tv_meals.setText(Double.valueOf(meals) + getResources().getString(R.string.str_yuan));
					}
					noteInfo.setFeeLunch(Double.valueOf(meals));
				}
				if (!hotel.equals("") && !hotel.equals(".")) {
					if (outFeeType == 0){
						Double fee = reserve2(Double.valueOf(hotel)*taxRate);
						tv_hotel.setText(fee + getResources().getString(R.string.str_yuan));
					} else {
						tv_hotel.setText(Double.valueOf(hotel) + getResources().getString(R.string.str_yuan));
					}
					noteInfo.setFeeHotel(Double.valueOf(hotel));
				}
				if (!other.equals("") && !other.equals(".")) {
					Double fee = reserve2(Double.valueOf(other)*taxRate);
					tv_other.setText(fee + getResources().getString(R.string.str_yuan));
					noteInfo.setFeeOther(Double.valueOf(other));
				}
				if (!routeOn.equals("")){
					mileStart.setText(routeOn);
					noteInfo.setRouteBegin(routeOn);
				}
				if (!record.equals("")){
					tv_record.setText(record);
					noteInfo.setServiceRoute(record);
				}
				myStateInfo.setCurrentNote(noteInfo);
				myGetStateInfo.setStateinfo(myStateInfo);
			}
		}
		
		
		
	}
	
	/**
     * ��ʼ��OnStopTraceListener
     *//*
    private void initOnStopTraceListener() {
        // ��ʼ��stopTraceListener
        stopTraceListener = new OnStopTraceListener() {
        	

            // �켣����ֹͣ�ɹ�
            public void onStopTraceSuccess() {
                // TODO Auto-generated method stub
            	Log.i("jxb", "stop1");
//                mHandler.obtainMessage(1, "ֹͣ�켣����ɹ�").sendToTarget();
                trackApp.getClient().onDestroy();
            }

            // �켣����ֹͣʧ�ܣ�arg0 : ������룬arg1 : ��Ϣ���ݣ�����鿴��ο���
            public void onStopTraceFailed(int arg0, String arg1) {
                // TODO Auto-generated method stub
            	Log.i("jxb", "stop2");
//                mHandler.obtainMessage(-1, "ֹͣ�켣����ӿ���Ϣ [������� : " + arg0 + "����Ϣ���� : " + arg1 + "]").sendToTarget();
            }
        };
    }
	
 // ��ѯ���
    private void queryDistance(int processed, String processOption,int start,int stop) {

        // entity��ʶ
        String entityName = trackApp.getEntityName();

        // �Ƿ񷵻ؾ�ƫ��켣��0 : ��1 : �ǣ�
        int isProcessed = processed;

        // ��̲���
        String supplementMode = "driving";

        trackApp.getClient().queryDistance(trackApp.getServiceId(), entityName, isProcessed, processOption,
                supplementMode, start, stop, trackListener);
    }
	
	
    *//**
     * ��ʼ��OnTrackListener
     *//*
    private void initOnTrackListener() {

        trackListener = new OnTrackListener() {

            // ����ʧ�ܻص��ӿ�
            @Override
            public void onRequestFailedCallback(String arg0) {
                // TODO Auto-generated method stub
                trackApp.getmHandler().obtainMessage(0, "track����ʧ�ܻص��ӿ���Ϣ : " + arg0).sendToTarget();
                Log.i("jxb", "track����ʧ�ܻص��ӿ���Ϣ : " + arg0);
                myGetStateInfo = getStateInfo.getInstance(getApplicationContext());
    			try {
					myStateInfo = myGetStateInfo.getStateinfo();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                noteInfo = myStateInfo.getCurrentNote();
                noteInfo.setRouteAuto(-1);
                myStateInfo.setCurrentNote(noteInfo);
				myGetStateInfo.setStateinfo(myStateInfo);
//                Intent intent = new Intent();
//				intent.setClass(InService.this, OrderDetailEnd.class);
//				startActivity(intent);
            }

            // ��ѯ��ʷ�켣�ص��ӿ�
            @Override
            public void onQueryHistoryTrackCallback(String arg0) {
                // TODO Auto-generated method stub
                super.onQueryHistoryTrackCallback(arg0);
//                showHistoryTrack(arg0);
            }

            @Override
            public void onQueryDistanceCallback(String arg0) {
                // TODO Auto-generated method stub
            	Log.i("jxb", "distance callback arg0 = "+arg0);
                try {
                    JSONObject dataJson = new JSONObject(arg0);
                    if (null != dataJson && dataJson.has("status") && dataJson.getInt("status") == 0) {
                        double distance = dataJson.getDouble("distance");
                        DecimalFormat df = new DecimalFormat("#.0");
                        myGetStateInfo = getStateInfo.getInstance(getApplicationContext());
            			try {
							myStateInfo = myGetStateInfo.getStateinfo();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
            			int distanceInt = (int) (distance/1000);
                        noteInfo = myStateInfo.getCurrentNote();
                        noteInfo.setRouteAuto(distanceInt);
                        Log.i("jxb", "�Զ���� = "+noteInfo.getRouteAuto());
                        myStateInfo.setCurrentNote(noteInfo);
						myGetStateInfo.setStateinfo(myStateInfo);
//						Intent intent = new Intent();
//						intent.setClass(InService.this, OrderDetailEnd.class);
//						startActivity(intent);
//                        trackApp.getmHandler().obtainMessage(0, "��� : " + df.format(distance) + "��").sendToTarget();
						
						Intent intent=new Intent();
			            intent.setAction("com.dazhong.idan.distanceCallback");
			    		Bundle bundle=new Bundle();
			    		bundle.putInt("distanceAuto", (int)distance);
			    		intent.putExtras(bundle);
			    		myApplication.getInstance().getApplicationContext().sendBroadcast(intent);
						
                    } else {
                    	Log.i("jxb", "data null or no status");
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    trackApp.getmHandler().obtainMessage(0, "queryDistance�ص���Ϣ : " + arg0).sendToTarget();
                }
            }

            @Override
            public Map<String, String> onTrackAttrCallback() {
                // TODO Auto-generated method stub
                System.out.println("onTrackAttrCallback");
                Log.i("jxb", "onTrackAttrCallback ");
                return null;
            }

        };
    }
	
    
    static class TrackUploadHandler extends Handler {
        WeakReference<InService> trackUpload;

        TrackUploadHandler(InService trackUploadFragment) {
            trackUpload = new WeakReference<InService>(trackUploadFragment);
        }

        @Override
        public void handleMessage(Message msg) {
        	InService tu = trackUpload.get();
            Toast.makeText(tu.trackApp, (String) msg.obj, Toast.LENGTH_LONG).show();

        }
    }*/
	
	private Double reserve2(Double x){
		Double result = (double)(Math.round(x*100)/100.0);
		return result;
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {

		}
		return true;

	}
	
	public class LocationReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			Bundle bundle = intent.getExtras();
			String content = bundle.getString("locationMessage");
			if (record_before.equals(content)) {
				//���ϸ��ص���ͬ
			} else {
				record_before = content;
				String record = noteInfo.getServiceRoute();
				if (record.equals("")) {
					record = content;
				} else {
					record = record + "-" + content;
				}
				noteInfo.setServiceRoute(record);
				myStateInfo.setCurrentNote(noteInfo);
				myGetStateInfo.setStateinfo(myStateInfo);
				tv_record.setText(record);
			}
		}
		
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.i("jxb", "currentnote: "+myStateInfo.getCurrentNote());
	}
	
}
