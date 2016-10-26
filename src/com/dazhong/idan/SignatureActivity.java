package com.dazhong.idan;

import java.io.File;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class SignatureActivity extends Activity implements OnClickListener{
	
	private TextView tv_cancel;
	private TextView tv_confirm;
	private ImageView iv_return;
	private SignatureView signaturePaint;
	private NoteInfo noteInfo;
	private getStateInfo myGetStateInfo;
	private StateInfo myStateInfo;
	
//	private String spaceName = "mytest";
	private String spaceName = "driverapp";
	private static final String rootDir = Environment.getExternalStorageDirectory()+File.separator+"zhongxing/";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.signature);
		ActivityControler.addActivity(this);
		findView();
		try {
			myGetStateInfo = getStateInfo.getInstance(getApplicationContext());
			myStateInfo = myGetStateInfo.getStateinfo();
			myStateInfo.setCurrentState(18);
			noteInfo = myStateInfo.getCurrentNote();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		tv_cancel.setOnClickListener(this);
		tv_confirm.setOnClickListener(this);
		iv_return.setOnClickListener(this);
	}
	

	private void findView(){
		tv_confirm = (TextView) findViewById(R.id.tv_confirm);
		tv_cancel = (TextView) findViewById(R.id.tv_cancel);
		signaturePaint = (SignatureView) findViewById(R.id.signature_paint);
		iv_return = (ImageView) findViewById(R.id.return_sign);
	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.tv_cancel:
			signaturePaint.clear();
			break;
		case R.id.tv_confirm:
			
			final EditText editText = new EditText(SignatureActivity.this);
//			LinearLayout.LayoutParams pm = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);  
//			layout.addView(tv, pm);  
//			layout.setGravity(Gravity.CENTER);
			new AlertDialog.Builder(SignatureActivity.this)
					.setTitle("是否确认上传")
					.setPositiveButton(
							"确定",
							new android.content.DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {

									PictureUtil util = new PictureUtil();
									// if (saveScreen(signaturePaint)){
									if (util.saveScreen(signaturePaint,
											noteInfo.getNoteID())) {
										util.uploadPic(spaceName,
												rootDir + noteInfo.getNoteID()
														+ ".jpg",
												noteInfo.getNoteID());
										String address = "http://obxkbrg0h.bkt.clouddn.com/"
												+ noteInfo.getNoteID();
										// String address =
										// "http://o9zlqc13f.bkt.clouddn.com/"+noteInfo.getNoteID();
										noteInfo.setPictureAddress(address);
										int k = getInfoValue
												.InsertNote(noteInfo
														.toUploadNote());
										Log.i("jxb", "k = " + k);
										if (k == 1 || k == 0) {
											// do nothing
										} else {
											getStateInfo.getInstance(
													SignatureActivity.this)
													.saveNoteInfo(noteInfo);
											Log.i("jxb", "上传失败,路单已保存");
										}
										Intent intent = new Intent();
//										intent.setClass(
//												getApplicationContext(),
//												MainActivity.class);
										intent.setClass(getApplicationContext(), PrintActivity.class);
										startActivity(intent);
									} else {
										Toast.makeText(getApplicationContext(),
												"签名保存失败", Toast.LENGTH_SHORT)
												.show();
									}
								}
							})
					.setNegativeButton(
							"取消",
							new android.content.DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
								}
							}).show();
			break;
		case R.id.return_sign:
			SignatureActivity.this.finish();
			break;
		default:
			break;
		}
		
	}
	
}
