package com.dazhong.idan;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	// 系统接口地址
//	public static String SERVICEADRRESS = "http://192.168.75.200:8084/DriverService.asmx";
//	public static String USERNAME = "";// 用户名称
//	public static String WORKNUMBER = "";// 工号
//	public static String EMPLOYEEID = "";// 系统代码
//	public static StateInfo stateInfo = null;// 状态管理
	// ************************************************
	public static String POSITION = "POSITON";
	private iDanApp idanapp;
	private TaskInfo curTask = null;
	private ListView mListView;
	private TextView tv_name;
	private ImageView iv_return;
	private StateInfo stateinfo;
	private List<TaskInfo> tasklist = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
				.detectDiskReads().detectDiskWrites().detectNetwork()
				.penaltyLog().build());

		StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
				.detectLeakedSqlLiteObjects().penaltyLog().penaltyDeath()
				.build());
		super.onCreate(savedInstanceState);
		setContentView(R.layout.business_list);
		idanapp =iDanApp.getInstance();
		stateinfo=idanapp.getStateInfo();
		tasklist=idanapp.getTasklist();
		ActivityControler.addActivity(this);
//System.out.println(idanapp.getSERVICEADRRESS());

		findView();
		tv_name.setText(idanapp.getUSERNAME());
		MyAdapter mAdapter = new MyAdapter(this);
		mListView.setAdapter(mAdapter);
		mListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// tasklist.get(position).setReadmark(0);
				curTask = tasklist.get(position);
//				curTask=iDanApp.getInstance().getTasklist().get(position);
				curTask.setReadmark(0);
				Intent intent = new Intent();
				intent.putExtra(POSITION, position);
				intent.setClass(getApplicationContext(), OrderDetail.class);
				startActivity(intent);
			}
		});
		// configure the SlidingMenu
		MenuLeftFragment menuLayout = new MenuLeftFragment(
				getApplicationContext());
		final SlidingMenu menu = new SlidingMenu(this);
		menu.setMode(SlidingMenu.LEFT);
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		menu.setShadowWidthRes(R.dimen.shadow_width);
		menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		menu.setFadeDegree(0.35f);
		/**
		 * SLIDING_WINDOW will include the Title/ActionBar in the content
		 * section of the SlidingMenu, while SLIDING_CONTENT does not.
		 */
		menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
		menu.setMenu(R.layout.left_menu);
		iv_return.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				menu.showMenu();
			}
		});

	}

	private void PageJump() {
		Intent intent;
		// 登陆后，选择显示页面
		 switch (88) {
//		switch (stateinfo.getCurrentState()) {
		case 101:
			intent = new Intent();
			intent.setClass(getApplicationContext(), LoginActivity.class);
			startActivity(intent);
			break;
		case 0:
			intent = new Intent();
			intent.setClass(getApplicationContext(), LoginActivity.class);
			startActivity(intent);
			break;
		case 1:
			break;
		case 2:
		case 3:
		case 4:
		case 5:
		case 11:
			intent = new Intent();
			intent.setClass(getApplicationContext(), OrderDetail.class);
			startActivity(intent);
			break;
		case 12:
			intent = new Intent();
			intent.setClass(getApplicationContext(), OrderDetail.class);
			startActivity(intent);
			break;
		case 13:
		case 14:
		case 15:
		case 16:
		case 17:
		case 18:
			intent = new Intent();
			intent.setClass(getApplicationContext(), PrintActivity.class);
			startActivity(intent);
			break;
		default:
			Toast.makeText(getApplicationContext(), R.string.error, 6000)
					.show();
			break;
		}
	}

	private boolean getStateRec() {
		try {
			// get state sample
			// getStateInfo gs = new getStateInfo(getApplicationContext());
			stateinfo = getStateInfo.getInstance(getApplicationContext())
					.getStateinfo();
			// System.out.println(stateinfo.toString());
			if (stateinfo == null)
				return false;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}

	private void findView() {
		mListView = (ListView) findViewById(R.id.listView_main);
		tv_name = (TextView) findViewById(R.id.tv_titleName);
		iv_return = (ImageView) findViewById(R.id.return_main);
	}

	class MyAdapter extends BaseAdapter {

		private LayoutInflater mInflater = null;
		private Context mContext;

		public MyAdapter(Context context) {

			this.mContext = context;
			this.mInflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {

			if (tasklist != null) {
				return tasklist.size();
			} else {
				return 0;
			}
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = mInflater.inflate(R.layout.order_item, null);
				holder.date = (TextView) convertView
						.findViewById(R.id.item_date);
				holder.location = (TextView) convertView
						.findViewById(R.id.item_location);
				holder.name = (TextView) convertView
						.findViewById(R.id.item_name);
				holder.nubmer = (TextView) convertView
						.findViewById(R.id.item_number);
				holder.time = (TextView) convertView
						.findViewById(R.id.item_time);
				holder.type = (TextView) convertView
						.findViewById(R.id.item_type);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			TaskInfo taskInfo = tasklist.get(position);
			Log.i("jxb", "readMark = " + taskInfo.getReadmark());
			String ServiceDate = null;
			String serviceBegin = taskInfo.ServiceBegin();
			String serviceEnd = taskInfo.ServiceEnd();
			if (serviceBegin.equals(serviceEnd)) {
				ServiceDate = serviceBegin;
			} else {
				ServiceDate = serviceBegin + "-" + serviceEnd;
			}
			holder.time.setText(taskInfo.OnboardTime());
			holder.date.setText(ServiceDate);
			holder.location.setText(taskInfo.PickupAddress());
			holder.name.setText(taskInfo.Customer());
			holder.nubmer.setText(taskInfo.CustomerTel());
			holder.type.setText(taskInfo.ServiceTypeName());

			return convertView;
		}

	}

	static class ViewHolder {
		public TextView time;
		public TextView date;
		public TextView type;
		public TextView name;
		public TextView nubmer;
		public TextView location;
	}
}
