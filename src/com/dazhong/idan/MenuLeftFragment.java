package com.dazhong.idan;

import java.util.Arrays;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

public class MenuLeftFragment extends LinearLayout{

	private Context mContext;
	private ListView mListView;
	private List<String> mDatas = Arrays
            .asList("������Ϣ", "��ӡ����","��ʷ����","�޸�����","���汾","�˳�");
    private ListAdapter mAdapter;
    private UpdateManager updateManager;
	
	public MenuLeftFragment(Context context) {
		super(context);
		this.mContext = context;
		
	}
	
	public MenuLeftFragment(Context context,AttributeSet attrs) {
		super(context);
		this.mContext = context;
		LayoutInflater.from(context).inflate(R.layout.leftmenu_fragment, this);
		mListView = (ListView) findViewById(R.id.left_menu_listview);
		mAdapter = new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1, mDatas);
	    mListView.setAdapter(mAdapter);
	    mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				switch (position) {
				case 0:
					Intent intent = new Intent();
					intent.setClass(mContext, PersonalInfo.class);
					mContext.startActivity(intent);
					break;
				case 1:
					Intent intent4 = new Intent();
					intent4.setClass(mContext, BlueToothManage.class);
					mContext.startActivity(intent4);
					break;
				case 2:
					Intent intent2 = new Intent();
					intent2.setClass(mContext, OrderHistory.class);
					mContext.startActivity(intent2);
					break;
				case 3:
					Intent intent3 = new Intent();
					intent3.setClass(mContext, PasswordAlter.class);
					mContext.startActivity(intent3);
					break;
				case 4:
//					PictureUtil util = new PictureUtil();
//					util.uploadExistPic();
					String serviceVer = getInfoValue.getVersion("123");
					String curVersion = FileUtil.getInstance().getVersion(mContext);
					Log.i("jxb", "serviceVersion = "+serviceVer+"   curVersion = "+curVersion);
					updateManager = new UpdateManager(mContext);
					if(!serviceVer.equals(curVersion)){
						new AlertDialog.Builder(mContext).setTitle(getResources().getString(R.string.version_havenew)).
							setPositiveButton(getResources().getString(R.string.str_sure), new android.content.DialogInterface.OnClickListener() {
								
								@Override
								public void onClick(DialogInterface dialog, int which) {
//									Intent it = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.dz-zc.com/dzapp.apk"));   
//									it.setClassName("com.android.browser", "com.android.browser.BrowserActivity");   
//									mContext.startActivity(it);
									updateManager.showDownloadDialog();
								}
							}).setNegativeButton(getResources().getString(R.string.str_cancel), new android.content.DialogInterface.OnClickListener() {
								
								@Override
								public void onClick(DialogInterface dialog, int which) {
									// TODO Auto-generated method stub
									
								}
							}).show();
					} else {
						new AlertDialog.Builder(mContext).setTitle("��ǰ�汾"+curVersion+",�����������°汾").
							setPositiveButton(getResources().getString(R.string.str_sure), new android.content.DialogInterface.OnClickListener() {
								
								@Override
								public void onClick(DialogInterface dialog, int which) {
									// TODO Auto-generated method stub
								}
							}).show();
					}
					break;
				case 5:
					try {
						getStateInfo myGetStateInfo = getStateInfo.getInstance(mContext);
						StateInfo myStateInfo = myGetStateInfo.getStateinfo();
						myStateInfo.setCurrentState(101);
						myGetStateInfo.setStateinfo(myStateInfo);
						
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					ActivityControler.finishAll();
					System.exit(0);
					break;
				default:
					break;
				}
				
			}
		});
	}
	
}
