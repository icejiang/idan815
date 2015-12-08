package com.dazhong.idan;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

public class getBlueTooth {
	private Context context;

	public getBlueTooth() {
		// TODO Auto-generated constructor stub

		// Intent intent = new Intent();
		// intent.setClass(getApplicationContext(), BlueToothManage.class);
		// startActivity(intent);

		BlueToothManage btm = new BlueToothManage();
		Handler mhandler = new Handler() {
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				switch (msg.what) {
				case BlueToothManage.MESSAGE_STATE_CHANGE:// 蓝牙连接状态
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
								context,
								context.getResources().getString(
										R.string.str_succonnect), 2000).show();

						break;
					case BlueToothService.FAILED_CONNECT:
						Toast.makeText(
								context,
								context.getResources().getString(
										R.string.str_faileconnect), 2000)
								.show();
						break;
					case BlueToothService.LOSE_CONNECT:
						switch (msg.arg2) {
						case -1:
							Toast.makeText(
									context,
									context.getResources()
											.getString(R.string.str_lose), 2000)
									.show();
							break;
						case 0:
							break;
						}
					}
					break;
				case BlueToothManage.MESSAGE_READ:
					// sendFlag = false;//缓冲区已满
					break;
				case BlueToothManage.MESSAGE_WRITE:// 缓冲区未满
					// sendFlag = true;
					break;

				}
			}
		};
		BlueToothService mBTService = new BlueToothService(context, mhandler);
		mBTService.ConnectToDevice("00:14:03:05:73:B6");
		 Toast.makeText(
		 context,"connecting printer",
		 9000).show();
		if (mBTService.getState() != mBTService.STATE_CONNECTED) {
			System.out.println("no connected printer!");
			 Toast.makeText( context,"no connected printer!",
			 2000).show();
			// return;
		}
		Toast.makeText(context, "connect printer", 2000).show();

		String message = "davis say ok \r\n" + "this is line 2\r\n"
				+ "here level 3\r" + "now is ?\n" + "look!\r\n";
		mBTService.PrintCharacters(message + "\r\n");

	}
}