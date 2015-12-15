package com.dazhong.idan;

import java.util.Arrays;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
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
            .asList("个人信息", "历史订单查询","修改密码","退出");
    private ListAdapter mAdapter;
	
	
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
					Intent intent2 = new Intent();
					intent2.setClass(mContext, OrderHistory.class);
					mContext.startActivity(intent2);
					break;
				case 2:
					Intent intent3 = new Intent();
					intent3.setClass(mContext, PasswordAlter.class);
					mContext.startActivity(intent3);
					break;
				case 3:
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
					break;
				default:
					break;
				}
				
			}
		});
	}
	
}
