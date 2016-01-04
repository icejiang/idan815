package com.dazhong.idan;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	// 系统接口地址
	// public static String SERVICEADRRESS =
	// "http://192.168.75.200:8084/DriverService.asmx";
	// public static String USERNAME = "";// 用户名称
	// public static String WORKNUMBER = "";// 工号
	// public static String EMPLOYEEID = "";// 系统代码
	// public static StateInfo stateInfo = null;// 状态管理
	// ************************************************
	public static String POSITION = "POSITON";
	private iDanApp idanapp;
	private TaskInfo curTask = null;
	private ListView mListView;
	private ImageView iv_return;
	private ImageView iv_refresh;
	private TextView tv_addStart;
	private TextView tv_addEnd;
	private StateInfo stateinfo;
	private List<TaskInfo> tasklist = null;
	private SlidingMenu menu;
	private MyAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.business_list);
		idanapp = iDanApp.getInstance();
		tasklist = idanapp.getTasklist();
		try {
			stateinfo = getStateInfo.getInstance(getApplicationContext())
					.getStateinfo();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(this.getApplicationContext(), R.string.error, 2000);
		}
		ActivityControler.addActivity(this);

		findView();
		mAdapter = new MyAdapter(this);
		mListView.setAdapter(mAdapter);
		mListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// tasklist.get(position).setReadmark(0);
				curTask = tasklist.get(position);
				// curTask=iDanApp.getInstance().getTasklist().get(position);
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
		// final SlidingMenu
		menu = new SlidingMenu(this);
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
		
		checkVersion(this);
		
		iv_return.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				menu.showMenu();
				// refreshTasks();
			}
		});
		iv_refresh.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				refreshTasks();
			}
		});
		tv_addStart.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final EditText editText = new EditText(MainActivity.this);
				editText.setInputType(InputType.TYPE_CLASS_NUMBER);
				new AlertDialog.Builder(MainActivity.this)
						.setTitle("请填写上班路码")
						.setView(editText)
						.setPositiveButton(
								"确定",
								new android.content.DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {

										String input = editText.getText()
												.toString();
										if (input.equals("")) {
											Toast.makeText(
													getApplicationContext(),
													"路码不能为空",
													Toast.LENGTH_SHORT).show();
										} else {
											try {
												getStateInfo myGetStateInfo = getStateInfo
														.getInstance(getApplicationContext());
												StateInfo myStateInfo = myGetStateInfo
														.getStateinfo();
												if (Integer.parseInt(input) < Integer.parseInt(myStateInfo
														.getCurrentKMS())) {
													Toast.makeText(
															getApplicationContext(),
															"输入路码小于历史路码,请确认输入",
															Toast.LENGTH_SHORT)
															.show();
													Log.i("jxb",
															"历史路码 = "
																	+ myStateInfo
																			.getCurrentKMS());
												} else {
													myStateInfo
															.setCurrentKMS(input);
													myGetStateInfo
															.setStateinfo(myStateInfo);
													tv_addStart
															.setVisibility(View.GONE);
													tv_addEnd
															.setVisibility(View.VISIBLE);
												}
											} catch (Exception e1) {
												// TODO Auto-generated catch
												// block
												e1.printStackTrace();
											}
										}
									}
								}).show();

			}
		});
		tv_addEnd.setOnClickListener(myClickListener);
		stateinfo.setCurrentState(1);
		getStateInfo.getInstance(getApplicationContext()).setStateinfo(stateinfo);

	}

	private OnClickListener myClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			final EditText editText = new EditText(MainActivity.this);
			editText.setInputType(InputType.TYPE_CLASS_NUMBER);
			new AlertDialog.Builder(MainActivity.this)
					.setTitle("请填写下班路码")
					.setView(editText)
					.setPositiveButton(
							"确定",
							new android.content.DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {

									String input = editText.getText()
											.toString();
									if (input.equals("")) {
										Toast.makeText(getApplicationContext(),
												"路码不能为空", Toast.LENGTH_SHORT)
												.show();
									} else {
										try {
											getStateInfo myGetStateInfo = getStateInfo
													.getInstance(getApplicationContext());
											StateInfo myStateInfo = myGetStateInfo
													.getStateinfo();
											if (Integer.parseInt(input) < Integer
													.parseInt(myStateInfo
															.getCurrentKMS())) {
												Toast.makeText(
														getApplicationContext(),
														"输入路码小于历史路码,请确认输入",
														Toast.LENGTH_SHORT)
														.show();
												Log.i("jxb",
														"历史路码 = "
																+ myStateInfo
																		.getCurrentKMS());
											} else {
												myStateInfo
														.setCurrentKMS(input);
												myStateInfo
														.setEndKMsOfToday(input);
												myGetStateInfo
														.setStateinfo(myStateInfo);
												tv_addEnd
														.setVisibility(View.GONE);
												tv_addStart
														.setVisibility(View.VISIBLE);
											}
										} catch (Exception e1) {
											// TODO Auto-generated catch block
											e1.printStackTrace();
										}
									}
								}
							}).show();
			if (stateinfo.getTimeOfTaskOneDay() > 0
					&& stateinfo.getCurrentTask().CarID().length() > 2) {
				int iR;
				String routecode = "[";
				routecode = routecode
						+ stateinfo.getCurrentPerson().getPersonID() + ",";
				routecode = routecode + stateinfo.getCurrentTask().CarID()
						+ ",";
				routecode = routecode
						+ stateinfo.getCurrentNote().getNoteDate()
								.replaceAll("-", "") + ",";
//				routecode=routecode+"20010101"+",";
				routecode = routecode + stateinfo.getBeginKMsOfToday() + ",";
				routecode = routecode + stateinfo.getEndKMsOfToday();
				routecode = routecode + "]";
//				System.out.println(routecode);
				iR = getInfoValue.UploadRouteCode(routecode);
				if (iR == 0)
					Toast.makeText(getApplicationContext(), "路码上传成功！", 2000);
				else
					Toast.makeText(getApplicationContext(), "上传路码出错！", 2000);

			}
		}

	};

	private void checkVersion(Context context){
		String serviceVer = getInfoValue.getVersion("123");
		String curVersion = FileUtil.getInstance().getVersion(context);
		Log.i("jxb", "serviceVersion = "+serviceVer+"   curVersion = "+curVersion);
		if(!serviceVer.equals(curVersion)){
			new AlertDialog.Builder(MainActivity.this).setTitle("程序有更新,是否立即更新？").
				setPositiveButton("确定", new android.content.DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Intent it = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.dz-zc.com/dzapp.apk"));   
						it.setClassName("com.android.browser", "com.android.browser.BrowserActivity");   
						MainActivity.this.startActivity(it);
						
					}
				}).setNegativeButton("取消", new android.content.DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						
					}
				}).show();
		}
	}

	public void onBackPressed() {
		ActivityControler.finishAll();
	}

	private void setStatus(){
		try {
			getStateInfo myGetStateInfo = getStateInfo.getInstance(getApplicationContext());
			StateInfo myStateInfo = myGetStateInfo.getStateinfo();
			myStateInfo.setCurrentState(1);
			myGetStateInfo.setStateinfo(myStateInfo);
			
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
//		menu.toggle();
		setStatus();
	}

	private boolean getStateRec() {
		try {
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
		iv_return = (ImageView) findViewById(R.id.return_main);
		iv_refresh = (ImageView) findViewById(R.id.main_refresh);
		tv_addStart = (TextView) findViewById(R.id.main_addStart);
		tv_addEnd = (TextView) findViewById(R.id.main_addEnd);
	}

	private void refreshTasks() {
		try {
			idanapp.setTasklist(getInfoValue.getTasks(stateinfo
					.getCurrentPerson().getPersonID()));
			tasklist = idanapp.getTasklist();
			mAdapter.notifyDataSetChanged();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
//			holder.nubmer.setText(taskInfo.CustomerTel());
//			holder.nubmer.setVisibility(View.GONE);
			holder.type.setText(taskInfo.ServiceTypeName());

			return convertView;
		}

	}

	static class ViewHolder {
		public TextView time;
		public TextView date;
		public TextView type;
		public TextView name;
		public TextView location;
	}
}
