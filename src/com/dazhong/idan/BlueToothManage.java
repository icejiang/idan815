/**
 * 蓝牙打印管理，用户可以检测打印机的状态，手工连接打印机和打印额外信息
 */
package com.dazhong.idan;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.Set;

import com.dazhong.idan.BlueToothService.OnReceiveDataHandleEvent;

import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author davis
 * 蓝牙打印管理
 */
public class BlueToothManage extends Activity {
	private BlueToothService mBTService = null;
	private String tag = "MainActivity";
	private static final int REQUEST_EX = 1;
	public static final int MESSAGE_STATE_CHANGE = 1;
	public static final int MESSAGE_READ = 2;
	public static final int MESSAGE_WRITE = 3;
	public static final int MESSAGE_DEVICE_NAME = 4;
	public static final int MESSAGE_TOAST = 5;
	private Button checkButton;
	private Button controlButton;
	private Button bt_matches;// 配对蓝牙
	private ListView deviceList;// 设备列表
	private ArrayAdapter<String> mPairedDevicesArrayAdapter = null;// 已配对
	private ArrayAdapter<String> mNewDevicesArrayAdapter = null;// 新搜索列表
	private BluetoothAdapter mBluetoothAdapter = null;
	private Set<BluetoothDevice> devices;
	private Button bt_scan;// 扫描设备	Scan Device
	public Handler handler = null;
	public Handler mhandler;
//	private ProgressDialog progressDialog = null;
	private EditText edit;
	private ViewGroup vg;
	private LinearLayout layout;
	private LinearLayout layoutscan;
//	private Button btnUnicode;//Print by Unicode
	private Button bt_print;// 文字打印 	Print text by GBK
//	private Button bt_image;// 图片打印 	Print image
//	private Spinner spinnerTextSize;// 字体	Chose font-style
//	private Button bt_openpic;// 打开图片目录	Chose Image
	private ImageView iv;// 显示的图片	
//	private Button bt_2d;// 生成二维码	Create Qrcode
//	private Button bt_bar;// 生成条形码	Creaet Barcode
//	private String picPath = "";// 打开图片保存的路径
	private Bitmap btMap = null;// 缓存图片
	private TextView tv_status;
	private Button bt_disconnect;
	private Thread tv_update;
	private boolean tvFlag = true;
	private Thread bt_update = null;
	private boolean updateflag = true;
//	private Button nbt_img;
	private int verson = 72;// 76和80打印机，58打印机 48；
//	private ArrayAdapter<String> adapter;
//	private static String[] arr = { "58", "76" };
//	private static String[] value = { "384", "576" };
//	private static final String m[]={"24*24","48*48","16*16","32*32"};

	public BlueToothManage() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.bluetooth_set);
		layout = (LinearLayout) View.inflate(this,
				R.layout.edittext, null);
		iv = (ImageView) findViewById(R.id.iv_test);
		deviceList = (ListView) findViewById(R.id.lv_device);
		vg = (ViewGroup) deviceList.getParent();
		edit = (EditText) layout.findViewById(R.id.et_input);
		edit.setFocusable(false);
		layout.removeAllViews();
		vg.addView(edit, new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT));

		bt_print = (Button) findViewById(R.id.bt_print);
//		btnUnicode=(Button)findViewById(R.id.btnUnicode);
		layoutscan = (LinearLayout) findViewById(R.id.layoutscan);
		layoutscan.setVisibility(View.GONE);

//		spinnerTextSize=(Spinner)findViewById(R.id.spinner1);
//		adapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, m);
//		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//		spinnerTextSize.setAdapter(adapter);
//		spinnerTextSize.setOnItemSelectedListener(new OnItemSelectedListener()
//		{
//
//			@Override
//			public void onItemSelected(AdapterView<?> arg0, View arg1,
//					int arg2, long arg3) {
//				// TODO Auto-generated method stub
//				byte[] bt = new byte[3];
//				bt[0] = 27;
//				bt[1] = 56;
//				bt[2] = (byte)arg2;// 0,1,2,3//设置字体大小	Setting font-text
//				mBTService.write(bt);
//			}
//
//			@Override
//			public void onNothingSelected(AdapterView<?> arg0) {
//				// TODO Auto-generated method stub
//				
//			}
//			
//		});

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
								BlueToothManage.this,
								BlueToothManage.this.getResources().getString(
										R.string.str_succonnect), 2000).show();
						vg.getChildAt(0).setVisibility(View.GONE);
						vg.getChildAt(1).setVisibility(View.GONE);
						vg.getChildAt(2).setVisibility(View.VISIBLE);
						vg.getChildAt(2).setFocusable(true);
						vg.getChildAt(2).setFocusableInTouchMode(true);

						break;
					case BlueToothService.FAILED_CONNECT:
						Toast.makeText(
								BlueToothManage.this,
								BlueToothManage.this.getResources().getString(
										R.string.str_faileconnect), 2000)
								.show();
						vg.getChildAt(0).setVisibility(View.VISIBLE);
						vg.getChildAt(1).setVisibility(View.VISIBLE);
						vg.getChildAt(2).setVisibility(View.GONE);
						vg.getChildAt(2).setFocusable(false);
						break;
					case BlueToothService.LOSE_CONNECT:
						switch(msg.arg2){
						case -1:
						Toast.makeText(
								BlueToothManage.this,
								BlueToothManage.this.getResources().getString(
										R.string.str_lose), 2000).show();
						vg.getChildAt(0).setVisibility(View.VISIBLE);
						vg.getChildAt(1).setVisibility(View.VISIBLE);
						vg.getChildAt(2).setVisibility(View.GONE);
						vg.getChildAt(2).setFocusable(false);
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

		bt_disconnect = (Button) findViewById(R.id.bt_disconnect);
		bt_disconnect.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (mBTService.getState() == mBTService.STATE_CONNECTED) {
					mBTService.DisConnected();
				}
			}
		});

		mBTService = new BlueToothService(this, mhandler);
		// 点击检查是否有蓝牙设备
		checkButton = (Button) findViewById(R.id.bt_check);
		checkButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mBTService.HasDevice()) {
					Toast.makeText(
							BlueToothManage.this,
							BlueToothManage.this.getResources().getString(
									R.string.str_devecehasblue), 2000).show();
				} else {
					Toast.makeText(
							BlueToothManage.this,
							BlueToothManage.this.getResources().getString(
									R.string.str_hasnodevice), 2000).show();
				}
			}
		});

		// 点击打开或者关闭蓝牙设备
		controlButton = (Button) findViewById(R.id.bt_openclose);
		if (mBTService.IsOpen()) {// 判断蓝牙是否打开
			controlButton.setText(BlueToothManage.this.getResources().getString(
					R.string.str_open));
		}
		controlButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (mBTService.IsOpen()) {// 判断蓝牙是否打开
					if (mBTService.getState() == mBTService.STATE_CONNECTED) {
						mBTService.DisConnected();
					}
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					mBTService.CloseDevice();
				} else {
					mBTService.OpenDevice();
				}

			}
		});

		bt_update = new Thread() {
			public void run() {}
		};
		bt_update.start();
		mPairedDevicesArrayAdapter = new ArrayAdapter<String>(this,
				R.layout.device_name);
		// 查看已配对蓝牙
		bt_matches = (Button) findViewById(R.id.bt_matches);
		bt_matches.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!mBTService.IsOpen()) {
					mBTService.OpenDevice();
					return;
				}
				deviceList.setAdapter(mPairedDevicesArrayAdapter);
				mPairedDevicesArrayAdapter.clear();
				devices = mBTService.GetBondedDevice();
				if (devices.size() > 0) {
					for (BluetoothDevice device : devices) {
						mPairedDevicesArrayAdapter.add(device.getName() + "\n"
								+ device.getAddress());
					}
				} else {
					String noDevices = BlueToothManage.this.getResources()
							.getString(R.string.str_nomatched);
					mPairedDevicesArrayAdapter.add(noDevices);
				}
			}
		});

		mNewDevicesArrayAdapter = new ArrayAdapter<String>(this,
				R.layout.device_name);
		// 扫描所有区设备
		bt_scan = (Button) findViewById(R.id.bt_scan);
		bt_scan.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// 先判断是否正在扫描
				if (!mBTService.IsOpen()) {// 判断蓝牙是否打开
					mBTService.OpenDevice();
					return;
				}
				if (mBTService.GetScanState() == mBTService.STATE_SCANING)
					return;
				vg.getChildAt(0).setVisibility(View.VISIBLE);
				vg.getChildAt(1).setVisibility(View.VISIBLE);
				layoutscan.setVisibility(View.VISIBLE);
				mNewDevicesArrayAdapter.clear();
				devices = mBTService.GetBondedDevice();
				if (devices.size() > 0) {
					for (BluetoothDevice device : devices) {
						mNewDevicesArrayAdapter.add(device.getName() + "\n"
								+ device.getAddress());
					}
				}
				deviceList.setAdapter(mNewDevicesArrayAdapter);
				new Thread() {
					public void run() {
						mBTService.ScanDevice();
					}
				}.start();
			}

		});

		mBTService.setOnReceive(new OnReceiveDataHandleEvent() {
			@Override
			public void OnReceive(BluetoothDevice device) {
				// TODO Auto-generated method stub
				if (device != null) {
					mNewDevicesArrayAdapter.add(device.getName() + "\n"
							+ device.getAddress());
				} else {
					Message msg = new Message();
					msg.what = 1;
					handler.sendMessage(msg);
				}
			}
		});
		deviceList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (!mBTService.IsOpen()) {
					// 判断蓝牙是否打开
					mBTService.OpenDevice();
					return;
				}
				if (mBTService.GetScanState() == mBTService.STATE_SCANING) {
					Message msg = new Message();
					msg.what = 2;
					handler.sendMessage(msg);
				}
				if (mBTService.getState() == mBTService.STATE_CONNECTING) {
					return;
				}
				String info = ((TextView) view).getText().toString();
				String address = info.substring(info.length() - 17);
				Toast.makeText(
						BlueToothManage.this,address,
						2000).show();
				mBTService.DisConnected();
				mBTService.ConnectToDevice(address);

			}
		});

		bt_print.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mBTService.getState() != mBTService.STATE_CONNECTED) {
					Toast.makeText(
							BlueToothManage.this,
							BlueToothManage.this.getResources().getString(
									R.string.str_unconnected), 2000).show();
					return;
				}
				String message = edit.getText().toString();
				mBTService.PrintCharacters(message+"\r\n");
			}
		});
		
		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				switch (msg.what) {
				case 0:
					break;
				case 1:
					mBTService.StopScan();
					layoutscan.setVisibility(View.GONE);
					Toast.makeText(
							BlueToothManage.this,
							BlueToothManage.this.getResources().getString(
									R.string.str_scanover), 2000).show();
					break;
				case 2:
					layoutscan.setVisibility(View.GONE);
					break;
				}
			}
		};

		tv_status = (TextView) findViewById(R.id.tv_status);
		tv_update = new Thread() {
			public void run() {
				while (tvFlag) {
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					tv_status.post(new Runnable() {
						@Override
						public void run() {
							// TODO Auto-generated method stub
							if (mBTService != null) {
								if (mBTService.getState() == mBTService.STATE_CONNECTED) {
									tv_status.setText(BlueToothManage.this
											.getResources().getString(
													R.string.str_connected));
								} else if (mBTService.getState() == mBTService.STATE_CONNECTING) {
									tv_status.setText(BlueToothManage.this
											.getResources().getString(
													R.string.str_connecting));
								} else {
									tv_status.setText(BlueToothManage.this
											.getResources().getString(
													R.string.str_disconnected));
								}
							}
						}
					});
				}
			}
		};
		tv_update.start();
	}

	private static byte charToByte(char c) {
		return (byte) "0123456789ABCDEF".indexOf(c);
	}
	public static Bitmap resizeImage(Bitmap bitmap, int w, int h) {
		Bitmap BitmapOrg = bitmap;
		int width = BitmapOrg.getWidth();
		int height = BitmapOrg.getHeight();
		int newWidth = w;
		int newHeight = h;

		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;
		Matrix matrix = new Matrix();
		matrix.postScale(scaleWidth, scaleWidth);
		Bitmap resizedBitmap = Bitmap.createBitmap(BitmapOrg, 0, 0, width,
				height, matrix, true);
		return resizedBitmap;
	}

	private void sendMessage(String message) {
		if (mBTService.getState() != BlueToothService.STATE_CONNECTED) {
			return;
		}
		if (message.length() > 0) {
			byte[] send;
			try {
				send = message.getBytes("GBK");
			} catch (UnsupportedEncodingException e) {
				send = message.getBytes();
			}
			mBTService.write(send);
		}
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST_EX && resultCode == RESULT_OK
				&& null != data) {
			Uri selectedImage = data.getData();
			ContentResolver cr = this.getContentResolver();
			try {
				btMap = BitmapFactory.decodeStream(cr
						.openInputStream(selectedImage));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (btMap.getHeight() > 384) {
				// btMap = BitmapFactory.decodeFile(picPath);
				btMap = resizeImage(btMap, 384, 384);
				iv.setImageBitmap(btMap);
			} else {
				iv.setImageBitmap(btMap);
			}
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
		if (bt_update != null) {
			updateflag = false;
			bt_update = null;
		}
		if (tv_update != null) {
			tvFlag = false;
			tv_update = null;
		}
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
		System.exit(0);
	}
}
