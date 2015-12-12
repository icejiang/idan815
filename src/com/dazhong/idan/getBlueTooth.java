package com.dazhong.idan;

import java.util.Set;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.res.Resources.NotFoundException;
import android.os.Handler;
import android.os.Message;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class getBlueTooth {
	private static Context context;
	private BlueToothService mBTService;
	private Handler mhandler;
	private ArrayAdapter<String> mPairedDevicesArrayAdapter = null;
	private Set<BluetoothDevice> devices;
	private ListView deviceList;// 设备列表
	private static volatile getBlueTooth instance = null;
	private String deviceaddress;
	private Thread thdUpdate;

	public String getDeviceaddress() {
		return deviceaddress;
	}

	public void setDeviceaddress(String deviceaddress) {
		this.deviceaddress = deviceaddress;
	}

	public static getBlueTooth getInstance(Context whoseContext) {
		context = whoseContext;
		if (instance == null) {
			instance = new getBlueTooth(context);
		}
		return instance;
	}

	protected getBlueTooth(Context context) {
		this.context = context;
		
		try {
			Toast.makeText(	context,"begin init bluetooth",2000).show();
			Handler mhandler = new Handler() {
				public void handleMessage(Message msg) {
					super.handleMessage(msg);
					switch (msg.what) {
					case 0:
						break;
					case 1:// 蓝牙连接状态
						switch (msg.arg1) {
						case BlueToothService.STATE_CONNECTED:// 已经连接
							break;
						case BlueToothService.STATE_CONNECTING:// 正在连接
							break;
						case BlueToothService.STATE_LISTEN:
						case BlueToothService.STATE_NONE:
							break;
						case BlueToothService.SUCCESS_CONNECT:
							break;
						case BlueToothService.FAILED_CONNECT:
							break;
						case BlueToothService.LOSE_CONNECT:
							switch (msg.arg2) {
							case -1:
								break;
							case 0:
								break;
							}
						}
						break;
					case 2:
						// sendFlag = false;//缓冲区已满
						break;
					case 3:// 缓冲区未满
							// sendFlag = true;
						break;

					}
				}
			};
			mBTService = new BlueToothService(context, mhandler);

			if (CheckBlueTooth())
				Toast.makeText(	context,context.getResources().getString(R.string.str_initdeviceok),2000).show();
			else
				Toast.makeText(
						context,
						context.getResources().getString(
								R.string.str_initdevicefail), 2000).show();
		} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(	context,"begin init bluetooth,failed",2000).show();
		}
	}

	public void PrintNow(String yourwords) {
		Toast.makeText(	context,"bluetooth print",2000).show();
		String message = "davis say ok \r\n" + "this is line 2\r\n"
				+ "here level 3\r" + "now is ?\n" + "look!\r\n";
		mBTService.PrintCharacters(message + "\r\n");
	}

	private boolean CheckBlueTooth() {
		try {
			if (mBTService.HasDevice()) {
				if (!mBTService.IsOpen()) {// 判断蓝牙是否打开
					try {
						mBTService.OpenDevice();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						Toast.makeText(context,
								context.getResources().getString(R.string.error),
								2000).show();
						return false;
					}
				}
				mPairedDevicesArrayAdapter = new ArrayAdapter<String>(context,
						R.layout.device_name);
				deviceList.setAdapter(mPairedDevicesArrayAdapter);
				// mPairedDevicesArrayAdapter.clear();
				devices = mBTService.GetBondedDevice();
				boolean olddevicefind = false;
				if (devices.size() > 0) {
					for (BluetoothDevice device : devices) {
						olddevicefind = false;
						// mPairedDevicesArrayAdapter.add(device.getName() + "\n"
						// + device.getAddress());
						if (device.getName() == "DL58") {
							deviceaddress = device.getAddress();
							olddevicefind = true;
							break;
						}
					}
					if (olddevicefind)
						mBTService.ConnectToDevice(deviceaddress);
					else {
						Toast.makeText(
								context,
								context.getResources().getString(
										R.string.str_noinitdevice), 2000).show();
						return false;
					}
					thdUpdate = new Thread() {
						public void run() {
							while (true) {
								try {
									Thread.sleep(200);
									System.out.println("printer running!");
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
					};
					thdUpdate.start();
				} else {
					String noDevices = context.getResources().getString(
							R.string.str_nomatched);
					mPairedDevicesArrayAdapter.add(noDevices);
				}
			} else {
				Toast.makeText(context,
						context.getResources().getString(R.string.str_hasnodevice),
						2000).show();
				return false;
			}
		} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(	context,"bluetooth check failed",2000).show();
			return false;
		}
		return true;
	}
}