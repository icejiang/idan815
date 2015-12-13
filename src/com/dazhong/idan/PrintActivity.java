package com.dazhong.idan;

import java.util.Set;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

public class PrintActivity extends Activity {
	public static final int MESSAGE_STATE_CHANGE = 1;
	public static final int MESSAGE_READ = 2;
	public static final int MESSAGE_WRITE = 3;
	public static final int MESSAGE_DEVICE_NAME = 4;
	public static final int MESSAGE_TOAST = 5;

	public Handler mhandler;
	private BlueToothService mBTService = null;
	private Set<BluetoothDevice> devices;

	private TextView print_confirm;
	private TextView tv_road;
	private TextView tv_parking;
	private TextView tv_meals;
	private TextView tv_other;
	private TextView tv_mile;
	private TextView tv_all;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.printf);

		findView();
		setData();
		print_confirm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mBTService.getState() == mBTService.STATE_CONNECTED) {
					String message = printFile();
					if (message == null)
						return;
					mBTService.PrintCharacters(message);
					Toast.makeText(
							PrintActivity.this,
							PrintActivity.this.getResources().getString(
									R.string.str_printok), 2000).show();
				} else {
					Toast.makeText(
							PrintActivity.this,
							PrintActivity.this.getResources().getString(
									R.string.str_printfail), 2000).show();
				}

				// Intent intent = new Intent();
				// intent.setClass(getApplicationContext(), MainActivity.class);
				// startActivity(intent);

			}
		});

		// ******************************************************
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
								PrintActivity.this.getResources().getString(
										R.string.str_succonnect), 2000).show();
						break;
					case BlueToothService.FAILED_CONNECT:
						Toast.makeText(
								PrintActivity.this,
								PrintActivity.this.getResources().getString(
										R.string.str_faileconnect), 2000)
								.show();
						break;
					case BlueToothService.LOSE_CONNECT:
						switch (msg.arg2) {
						case -1:
							Toast.makeText(
									PrintActivity.this,
									PrintActivity.this.getResources()
											.getString(R.string.str_lose), 2000)
									.show();
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
			Toast.makeText(
					PrintActivity.this,
					PrintActivity.this.getResources().getString(
							R.string.str_devecehasblue), 2000).show();
			if (!mBTService.IsOpen()) {// 判断蓝牙是否打开
				Toast.makeText(
						PrintActivity.this,
						PrintActivity.this.getResources().getString(
								R.string.str_closed), 2000).show();

			} else {
				String connAddress = null;
				devices = mBTService.GetBondedDevice();
				if (devices.size() > 0) {
					for (BluetoothDevice device : devices) {
						if (device.getName().equals(
								MainActivity.stateInfo.getPrinterName()))
							connAddress = device.getAddress();
					}
					mBTService.DisConnected();
					mBTService.ConnectToDevice(connAddress);
				}

			}
		} else {
			Toast.makeText(
					PrintActivity.this,
					PrintActivity.this.getResources().getString(
							R.string.str_hasnodevice), 2000).show();
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();

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
		String messages = null;
		String mes = "\r\n\r\n";
		NoteInfo note = MainActivity.stateInfo.getCurrentNote();
		if (note == null) {
			Toast.makeText(
					PrintActivity.this,
					PrintActivity.this.getResources().getString(R.string.error),
					2000).show();
			return messages;
		}
		messages = "----------------------------------------------------" + mes;
		messages = messages + "路单号码：" + note.getNoteID() + mes;
		messages = messages + "服务日期：" + note.getNoteDate() + mes;
		messages = messages + "营运车辆：" + note.getCarNumber() + mes;
		messages = messages + "用车客人：" + note.getDriverName() + mes;
		messages = messages + "上车时间：" + note.getServiceBegin() + mes;
		messages = messages + "下车时间：" + note.getServiceEnd() + mes;
		messages = messages + "上车地址：" + note.getOnBoardAddress() + mes;
		messages = messages + "下车地址：" + note.getLeaveAddress() + mes;
		messages = messages + "途径地点：" + note.getServiceRoute() + mes;
		messages = messages + "服务里程：" + Double.toString(note.getServiceKMs())
				+ mes;
		messages = messages + "服务时长：" + note.getServiceTime() + mes;
		if (note.getOverHours() > 0)
			messages = messages + "超时服务" + note.getOverHours() + mes;
		if (note.getOverKMs() > 0)
			messages = messages + "超出里程：" + note.getOverKMs() + mes;
		messages = messages + "服务费用：" + note.getFeeTotal() + mes;
		messages = messages + "客户签名" + mes + mes + mes;
		messages = messages + "______________________________________________"
				+ mes + mes + mes;
		return messages;
	}

	private void setData() {
		Bundle bundle = getIntent().getBundleExtra("MYKEY");
		int all = getIntent().getIntExtra("ALL", 0);
		int mile = getIntent().getIntExtra("MILE", 0);
		if (bundle != null) {
			String road = bundle.getString(AddPay.key_road);
			String meals = bundle.getString(AddPay.key_meals);
			String parking = bundle.getString(AddPay.key_parking);
			String other = bundle.getString(AddPay.key_other);
			if (!road.equals("")) {
				tv_road.setText(road + "元");
			}
			if (!meals.equals("")) {
				tv_meals.setText(meals + "元");
			}
			if (!parking.equals("")) {
				tv_parking.setText(parking + "元");
			}
			if (!other.equals("")) {
				tv_other.setText(other + "元");
			}
		}
		tv_all.setText(all + "元");
		tv_mile.setText(mile + "公里");
	}

	private void findView() {
		print_confirm = (TextView) findViewById(R.id.print_confirm);
		tv_road = (TextView) findViewById(R.id.tv_road_print);
		tv_parking = (TextView) findViewById(R.id.tv_parking_print);
		tv_meals = (TextView) findViewById(R.id.tv_meals_print);
		tv_other = (TextView) findViewById(R.id.tv_other_print);
		tv_mile = (TextView) findViewById(R.id.tv_mile_print);
		tv_all = (TextView) findViewById(R.id.tv_all_print);
	}
}
