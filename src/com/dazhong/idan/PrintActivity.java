package com.dazhong.idan;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import com.dazhong.idan.R.id;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources.NotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class PrintActivity extends Activity {
	public static final int MESSAGE_STATE_CHANGE = 1;
	public static final int MESSAGE_READ = 2;
	public static final int MESSAGE_WRITE = 3;
	public static final int MESSAGE_DEVICE_NAME = 4;
	public static final int MESSAGE_TOAST = 5;

	public Handler mhandler ;
	private BlueToothService mBTService = null;
	private Set<BluetoothDevice> devices;

	private Button print_confirm;
	private TextView tv_base;
	private TextView tv_road;
	private TextView tv_parking;
	private TextView tv_meals;
	private TextView tv_hotel;
	private TextView tv_other;
	private TextView tv_all;
	private TextView tv_beyondMile;
	private TextView tv_beyondTime;
	private TextView tv_dateLast;
	private TextView date;
	private TextView time;
	private TextView type;
	private TextView name;
	private TextView destination;
	private TextView location;
	private TextView serviceTime;
	private TextView serviceMile;
	private TextView extraTime;
	private TextView extraMile;
	private TextView route_id;
	private TextView record;
	private Button bt_home;
	private boolean blueinit;
	private NoteInfo noteInfo;
	private TaskInfo taskInfo;
	private getStateInfo myGetStateInfo;
	private StateInfo myStateInfo;
	private int position;
	private TextView tv_alterPrice;
	private TextView company;
	
	private LinearLayout layout_basic;
	private LinearLayout layout_overmile;
	private LinearLayout layout_overtime;
	private RelativeLayout layout_all;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.printf);
		ActivityControler.addActivity(this);
		try {
			myGetStateInfo = getStateInfo.getInstance(getApplicationContext());
			myStateInfo = myGetStateInfo.getStateinfo();
			myStateInfo.setCurrentState(18);
			noteInfo = myStateInfo.getCurrentNote();
			taskInfo = myStateInfo.getCurrentTask();
			position = myStateInfo.getPosition();
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		blueinit = true;
		findView();
		setData();
		registerBoradcastReceiver();;
		
		bt_home.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mBTService != null) {
					mBTService.DisConnected();
					mBTService = null;
				}
				Intent intent = new Intent();
				intent.setClass(getApplicationContext(), MainActivity.class);
				startActivity(intent);

			}
		});
		print_confirm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.i("jxb", "蓝牙状态：" + mBTService.getState());
				if (mBTService.getState() == mBTService.STATE_CONNECTED) {
					String message = printFile();
					if (message == null) {
						Toast.makeText(
								PrintActivity.this,
								PrintActivity.this.getResources().getString(
										R.string.str_printfail), 2000).show();
						return;
					}
					mBTService.PrintCharacters(message);
//					int k = getInfoValue.InsertNote(noteInfo.toUploadNote());
//					// int k = 2;
//					Log.i("jxb", "k = " + k);
//					if (k == 0 || k == 1) {
//						// do nothing
//					} else {
//						getStateInfo.getInstance(PrintActivity.this)
//								.saveNoteInfo(noteInfo);
//						Log.i("jxb", "上传失败,路单已保存");
//					}
					Toast.makeText(
							PrintActivity.this,
							PrintActivity.this.getResources().getString(
									R.string.str_printok), 2000).show();
				} else {
					Log.i("jxb", noteInfo.toUploadNote());
					Toast.makeText(
							PrintActivity.this,
							PrintActivity.this.getResources().getString(
									R.string.str_printfail), 2000).show();
				}
			}
		});

		// ******************************************************

		try {
			mhandler = new Handler() {
				public void handleMessage(Message msg) {
					super.handleMessage(msg);
					switch (msg.what) {
					case MESSAGE_STATE_CHANGE:// 蓝牙连接状态
						switch (msg.arg1) {
						case BlueToothService.STATE_CONNECTED:// 已经连接
							break;
						case BlueToothService.STATE_CONNECTING:// 正在连接
							break;
						case BlueToothService.STATE_LISTEN:
						case BlueToothService.STATE_NONE:
							break;
						case BlueToothService.SUCCESS_CONNECT:
							Toast.makeText(
									PrintActivity.this,
									PrintActivity.this.getResources()
											.getString(R.string.str_succonnect),
									2000).show();
							break;
						case BlueToothService.FAILED_CONNECT:
							Toast.makeText(
									PrintActivity.this,
									PrintActivity.this.getResources()
											.getString(
													R.string.str_faileconnect),
									2000).show();
							break;
						case BlueToothService.LOSE_CONNECT:
							switch (msg.arg2) {
							case -1:
								Toast.makeText(
										PrintActivity.this,
										PrintActivity.this.getResources()
												.getString(R.string.str_lose),
										2000).show();
								break;
							case 0:
								break;
							}
						}
						break;
					case MESSAGE_READ:
						// sendFlag = false;//缓冲区已满
						break;
					case MESSAGE_WRITE:// 缓冲区未满
						// sendFlag = true;
						break;
					}
				}
			};
			mBTService = new BlueToothService(this, mhandler);
			if (mBTService.HasDevice()) {
				if (!mBTService.IsOpen()) {// 判断蓝牙是否打开
					Intent mIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);  
		            startActivityForResult(mIntent, 1); 
				} else {
					String connAddress = null;
					devices = mBTService.GetBondedDevice();
					if (devices.size() > 0) {
						for (BluetoothDevice device : devices) {
							if (device.getName().equals(
									getStateInfo.getInstance(getApplicationContext()).getStateinfo()
											.getPrinterName()))
								connAddress = device.getAddress();
							Log.i("jxb", "address1 = "+connAddress);
						}
						mBTService.DisConnected();
						Thread.sleep(200);
						Log.i("jxb", "address2 = "+connAddress);
						mBTService.ConnectToDevice(connAddress);
					}

				}
			} else {
				Toast.makeText(
						PrintActivity.this,
						PrintActivity.this.getResources().getString(
								R.string.str_hasnodevice), 2000).show();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			if (mBTService != null) {
				mBTService.DisConnected();
				mBTService = null;
			}
			myStateInfo.setCurrentState(1);
			myGetStateInfo.setStateinfo(myStateInfo);
			Toast.makeText(getApplicationContext(), "打印机连接错误，请重新配置蓝牙！", 2000)
					.show();
		}
	}

	private void registerBoradcastReceiver() {
	    IntentFilter stateChangeFilter = new IntentFilter(
	            BluetoothAdapter.ACTION_STATE_CHANGED);
	    registerReceiver(blueToothStateReceiver, stateChangeFilter);
	}
	
	private BroadcastReceiver blueToothStateReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (BluetoothAdapter.ACTION_STATE_CHANGED.equals(action)) {
				int state = intent
						.getIntExtra(BluetoothAdapter.EXTRA_STATE, -1);
				if (state == BluetoothAdapter.STATE_ON) {
					if(mBTService != null){
					if (mBTService.HasDevice()) {
						String connAddress = null;
						devices = mBTService.GetBondedDevice();
						if (devices.size() > 0) {
							for (BluetoothDevice device : devices) {
								try {
									if (device.getName().equals(getStateInfo.getInstance
											(getApplicationContext()).getStateinfo().getPrinterName()))
										connAddress = device.getAddress();
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
							mBTService.DisConnected();
							try {
								Thread.sleep(200);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							mBTService.ConnectToDevice(connAddress);
						}
					}
				}
				}
			}

		}
	};
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		unregisterReceiver(blueToothStateReceiver);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	public void onBackPressed() {
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (mBTService != null) {
			mBTService.DisConnected();
			mBTService = null;
		}
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PrintActivity.this.finish();
	}

	// ******************************************
	// define print string
	/**
	 * 路单打印格式
	 * */
	private String printFile() {

		double feeIncludeTax = 0;
		double feeTax = 0;
		String messages = null;
		try {
			String mes1 = "\r\n";
			String mes2 = "\r\n\r\n";
			String non = "\t";
			NoteInfo note = myStateInfo.getCurrentNote();
			if (note == null) {
				Toast.makeText(
						PrintActivity.this,
						PrintActivity.this.getResources().getString(
								R.string.error), 2000).show();
				return messages;
			}
			String balanceType = note.getBalanceType();
			double taxOnly = note.getInvoiceTaxRate();
			double taxRate = 1 + taxOnly;
			int bridgeFeeType = note.getBridgefeetype();
			int outFeeType = note.getOutfeetype();
			messages = "";// "--------------------------" + mes;
			messages = messages + non+"  大众汽车租赁有限公司" + mes2;
			messages = messages + "路单号/No.:" + note.getNoteID() + mes2;
			messages = messages + "定单号/OrdNo.:" + note.getPlanID() + mes2;
			messages = messages + "服务日期/Date:" + note.getNoteDate() + mes2;
			messages = messages + "营运车辆/Vehicle No.:" + note.getCarNumber() + mes2;
			messages = messages + "司机/Driver:" + note.getDriverName() + mes2;
			messages = messages + "业务员/Salesman:" + note.getSaleName() + mes2;
			messages = messages + "客户信息/Client:" + mes1;
			messages = messages + note.getCustomerCompany() + mes2;
			messages = messages + "用车客人/Guest Name:" + note.getCustomerName() + mes2;
			messages = messages + "上车时间/Boarding Time:" + note.getServiceBegin() + mes2;
			messages = messages + "下车时间/Alighting Time:"  + note.getServiceEnd() + mes2;
			messages = messages + "上车路码/Boarding Km:"  + note.getRouteBegin() + mes2;
			messages = messages + "下车路码/Alighting Km:"  + note.getRouteEnd() + mes2;
			messages = messages + "上车地址/Boarding Location:" + mes1;
			messages = messages + note.getOnBoardAddress() + mes2;
			messages = messages + "下车地址/Alighting Location:" + mes1;
			messages = messages + note.getLeaveAddress() + mes2;
			messages = messages + "途径地点/Running Record:" + mes1;
			messages = messages + note.getServiceRoute() + mes2;
			messages = messages + "服务里程/Service Km:" + Double.toString(note.getDoServiceKms())+"公里" + mes2;
			if (note.getOverKMs() > 0) {
				messages = messages + "超出里程/Extra Km:" + Integer.toString(note.getOverKMs())+"公里"  + mes2;
			}
			if (note.getFeeOverKMs() > 0) {
				messages = messages + "超里程费/Extra Km Fee:" + note.getFeeOverKMs() +"元" + mes2;
				feeIncludeTax += note.getFeeOverKMs();
			}
			messages = messages + "服务时长/Service Time:" + Double.toString(note.getDoServiceTime())+"小时" + mes2;
			if (note.getOverHours() > 0) {
				messages = messages + "超出时长/Extra Time:" + Integer.toString(note.getOverHours())+"小时" + mes2;
			}
			if (note.getFeeOverTime() > 0) {
				messages = messages + "超时间费/Extra Time Fee：" + note.getFeeOverTime() +"元" + mes2;
				feeIncludeTax += note.getFeeOverTime();
			}
			if (balanceType.equals("001") || balanceType.equals("007")){
				messages = messages + "基本费/Basic Fee:       " + note.getFeePrice() +"元"+ mes2;
			}
			feeIncludeTax += note.getFeePrice();
			feeTax = feeIncludeTax - reserve2(feeIncludeTax/taxRate);
			if (note.getFeeBridge() > 0)
				if (bridgeFeeType == 0) {
					messages = messages + "路桥费/Toll Fee:        " + reserve2(note.getFeeBridge()*taxRate)+"元" + mes2;
					feeTax += reserve2(note.getFeeBridge()*taxOnly);
				} else {
					messages = messages + "路桥费/Toll Fee:        " + note.getFeeBridge()+"元" + mes2;
				}
			if (note.getFeePark() > 0)
				if (bridgeFeeType == 0) {
					messages = messages + "停车费/Parking Fee:     " + reserve2(note.getFeePark()*taxRate)+"元" + mes2;
					feeTax += reserve2(note.getFeePark()*taxOnly);
				} else {
					messages = messages + "停车费/Parking Fee:     " + note.getFeePark()+"元" + mes2;
				}
			if (note.getFeeHotel() > 0)
				if (outFeeType == 0) {
					messages = messages + "住宿费/Hotel Expense:   " + reserve2(note.getFeeHotel()*taxRate)+"元" + mes2;
					feeTax += reserve2(note.getFeeHotel()*taxOnly);
				} else {
					messages = messages + "住宿费/Hotel Expense:   " + note.getFeeHotel()+"元" + mes2;
				}
			if (note.getFeeLunch() > 0)
				if (outFeeType == 0) {
					messages = messages + "餐费/Meal Fee:          " + reserve2(note.getFeeLunch()*taxRate)+"元" + mes2;
					feeTax += reserve2(note.getFeeLunch()*taxOnly);
				} else {
					messages = messages + "餐费/Meal Fee:          " + note.getFeeLunch()+"元" + mes2;
				}
			if (note.getFeeOther() > 0) {
				messages = messages + "其他费/Other Charges:   " + reserve2(note.getFeeOther()*taxRate)+"元" + mes2;
				feeTax += reserve2(note.getFeeOther()*taxOnly);
			}
			if (note.getFeeBack() > 0) {
				messages = messages + "修正费/Adjuested Price: " + -note.getFeeBack()+"元" + mes2;
			}
			if (balanceType.equals("001") || balanceType.equals("007")){
				messages = messages + "(税费/Tax:" + reserve2(feeTax) +"元)" + mes2;
			}
			if(noteInfo.getInvoiceType().equals("SD")){
				int int_all = ((int)(noteInfo.getFeeTotal()/10))*10;
				noteInfo.setFeeTotal(int_all);
				if (balanceType.equals("001") || balanceType.equals("007")){
					messages = messages + "总费用/Total Price:" 	+ Double.toString(int_all)+"元" + mes2;
				}
			} else {
				double all = reserve2(noteInfo.getFeeTotal());
				noteInfo.setFeeTotal(all);
				if (balanceType.equals("001") || balanceType.equals("007")){
					messages = messages + "总费用/Total Price:" 	+ Double.toString(all)+"元" + mes2;
				}
			}
			
			messages = messages + "客户签名/Customer Signature:" + mes2 + mes2 + mes2;
			messages = messages + "___________________________" + mes2 + mes2
					+ mes2;
		} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return messages;
		}
		return messages;
	}

	private void setData() {
		SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String curDate = sDateFormat
				.format(new Date(System.currentTimeMillis()));
		date.setText(curDate);
		time.setText(noteInfo.getServiceBegin() + "-"
				+ noteInfo.getServiceEnd());
		type.setText(taskInfo.ServiceTypeName());
		name.setText(noteInfo.getCustomerName());
		location.setText(noteInfo.getOnBoardAddress());
		destination.setText(noteInfo.getLeaveAddress());
		// int totalMile =
		// Integer.parseInt(noteInfo.getRouteEnd())-Integer.parseInt(noteInfo.getRouteBegin());
		int totalMile = noteInfo.getDoServiceKms();
		int hours = noteInfo.getDoServiceTime();
		serviceMile.setText(noteInfo.getDoServiceKms() + "公里");
		serviceTime.setText(noteInfo.getDoServiceTime() + "小时");
		int serviceMile = noteInfo.getServiceKMs();
		int serviceHour = noteInfo.getServiceTime();
		Log.i("jxb", "serviceMile = " + serviceMile);
		if (serviceMile < totalMile) {
			extraMile.setText((totalMile - serviceMile) + "公里");
			double price_extraMile = noteInfo.getFeeOverKMs();
			tv_beyondMile.setText(price_extraMile + "元");
		}
		if (hours > serviceHour) {
			extraTime.setText((hours - serviceHour) + "小时");
			double price_extraTime = noteInfo.getFeeOverTime();
			tv_beyondTime.setText(price_extraTime + "元");
		}
		double taxRate = 1 + noteInfo.getInvoiceTaxRate();
		tv_base.setText(noteInfo.getFeePrice() + "元");
		if(noteInfo.getBridgefeetype() == 0){
			tv_road.setText(reserve2(noteInfo.getFeeBridge()*taxRate) + "元");
			tv_parking.setText(reserve2(noteInfo.getFeePark()*taxRate) + "元");
		} else {
			tv_road.setText(noteInfo.getFeeBridge() + "元");
			tv_parking.setText(noteInfo.getFeePark() + "元");
		}
		if(noteInfo.getOutfeetype() == 0 ){
			tv_meals.setText(reserve2(noteInfo.getFeeLunch()*taxRate) + "元");
			tv_hotel.setText(reserve2(noteInfo.getFeeHotel()*taxRate) + "元");
		} else {
			tv_meals.setText(noteInfo.getFeeLunch() + "元");
			tv_hotel.setText(noteInfo.getFeeHotel() + "元");
		}
		tv_other.setText(reserve2(noteInfo.getFeeOther()*taxRate) + "元");
		tv_alterPrice.setText(-noteInfo.getFeeBack()+"元");
		tv_all.setText(noteInfo.getFeeTotal() + "元");
		if(noteInfo.getInvoiceType().equals("SD")){
			int int_all = ((int)(noteInfo.getFeeTotal()/10))*10;
			tv_all.setText(int_all+"元");
		} else {
			double all = reserve2(noteInfo.getFeeTotal());
			tv_all.setText(all+"元");
		}
		tv_dateLast.setText(curDate);
		route_id.setText(noteInfo.getNoteID());
		record.setText(noteInfo.getServiceRoute());
		company.setText(noteInfo.getCustomerCompany());
		String balanceType = noteInfo.getBalanceType();
		if (balanceType.equals("001") || balanceType.equals("007")
				|| balanceType.equals("015") || balanceType.equals("016")) {
		} else {
			layout_basic.setVisibility(View.GONE);
			layout_overmile.setVisibility(View.GONE);
			layout_overtime.setVisibility(View.GONE);
			layout_all.setVisibility(View.GONE);
		}
	}

	private void findView() {
		print_confirm = (Button) findViewById(R.id.print_confirm);
		tv_base = (TextView) findViewById(R.id.tv_base_print);
		tv_road = (TextView) findViewById(R.id.tv_road_print);
		tv_parking = (TextView) findViewById(R.id.tv_parking_print);
		tv_meals = (TextView) findViewById(R.id.tv_meals_print);
		tv_hotel = (TextView) findViewById(R.id.tv_hotel_print);
		tv_other = (TextView) findViewById(R.id.tv_other_print);
		tv_all = (TextView) findViewById(R.id.tv_all_print);
		serviceMile = (TextView) findViewById(R.id.print_serviceMile);
		serviceTime = (TextView) findViewById(R.id.print_serviceTime);
		date = (TextView) findViewById(R.id.print_date);
		time = (TextView) findViewById(R.id.print_time);
		type = (TextView) findViewById(R.id.print_type);
		name = (TextView) findViewById(R.id.print_name);
		location = (TextView) findViewById(R.id.print_location);
		destination = (TextView) findViewById(R.id.print_destination);
		extraMile = (TextView) findViewById(R.id.print_extraMile);
		extraTime = (TextView) findViewById(R.id.print_extraTime);
		tv_dateLast = (TextView) findViewById(R.id.print_date_last);
		tv_beyondMile = (TextView) findViewById(R.id.tv_beyond_mile);
		tv_beyondTime = (TextView) findViewById(R.id.tv_beyond_time);
		route_id = (TextView) findViewById(R.id.print_routeID);
		bt_home = (Button) findViewById(R.id.print_mainactivity);
		record = (TextView) findViewById(R.id.print_record);
		tv_alterPrice = (TextView) findViewById(R.id.tv_alter_price);
		company = (TextView) findViewById(R.id.print_company);
		layout_basic = (LinearLayout) findViewById(R.id.layout0_basic);
		layout_all = (RelativeLayout) findViewById(R.id.layout_all_print);
		layout_overmile = (LinearLayout) findViewById(R.id.beyond_mile);
		layout_overtime = (LinearLayout) findViewById(R.id.beyond_time);
	}
	
	private Double reserve2(Double x){
		Double result = (double)(Math.round(x*100)/100.0);
		return result;
	}
	
	public boolean isNetworkAvailable(Activity activity)
    {
        Context context = activity.getApplicationContext();
        // 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        
        if (connectivityManager == null)
        {
            return false;
        }
        else
        {
            // 获取NetworkInfo对象
            NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();
            
            if (networkInfo != null && networkInfo.length > 0)
            {
                for (int i = 0; i < networkInfo.length; i++)
                {
                    System.out.println(i + "===状态===" + networkInfo[i].getState());
                    System.out.println(i + "===类型===" + networkInfo[i].getTypeName());
                    // 判断当前网络状态是否为连接状态
                    if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {

		}

		return true;

	}

	
	
}
