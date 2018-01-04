package com.dazhong.idan;

import java.io.File;
import java.util.ArrayList;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Environment;
import android.util.Log;

public class NetWorkReceiver extends BroadcastReceiver {

	private static final String rootDir = Environment.getExternalStorageDirectory()+File.separator+"DZpicture/";
//	private String spaceName = "mytest"; //储存空间名
	private String spaceName = "driverapp";
	
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		Log.i("jxb", "111111111111------");
		PictureUtil util = new PictureUtil();
		ConnectivityManager connManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connManager!=null) { 
			
			if (connManager.getActiveNetworkInfo()!= null&&connManager.getActiveNetworkInfo().isAvailable()){
				Log.i("jxb", "网络可用");
				ArrayList<NoteInfo> saveNotes = getStateInfo.getInstance(context).getNoteInfo();
				if (saveNotes == null ){
					Log.i("jxb", "saveNOtes 空");
					return;
				}
				if (saveNotes.size() == 0 ){
					Log.i("jxb", "saveNOtes 0");
					return;
				}
				if (saveNotes != null && saveNotes.size() != 0){
					int k = 100;
					for (int i = 0 ; i < saveNotes.size() ; i++){
						k = getInfoValue.InsertNote(saveNotes.get(i).toUploadNote());
						util.uploadPic(spaceName, rootDir+ saveNotes.get(i).getNoteID() + ".jpg", saveNotes.get(i).getNoteID());
						Log.i("jxb", "k(广播) = "+k);
					}
					if (k == 0 || k == 1){
						getStateInfo.getInstance(context).deleteFile();
					} 
				}
			} else {
				Log.i("jxb", "网络不可用");
			}
		}
	}

}
