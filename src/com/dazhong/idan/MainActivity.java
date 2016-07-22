package com.dazhong.idan;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.zip.Inflater;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import android.R.color;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
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
	private TextView tv_title;
	private StateInfo stateinfo;
	private List<TaskInfo> tasklist = null;
	private SlidingMenu menu;
	private MyAdapter mAdapter;
	private NoteInfo pauseNote;
	private static final int MSG_SET_ALIAS = 1001;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i("jxb", "create");
		setContentView(R.layout.business_list);
		idanapp = iDanApp.getInstance();
		tasklist = idanapp.getTasklist();
		JPushInterface.init(getApplicationContext());
		try {
			stateinfo = getStateInfo.getInstance(getApplicationContext())
					.getStateinfo();
			pauseNote = stateinfo.getPauseNote();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(this.getApplicationContext(), R.string.error, 2000);
		}
		mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_ALIAS, stateinfo.getUserAccount()));
		ActivityControler.addActivity(this);

		findView();
		uploadNotes();
		tv_title.setText(iDanApp.getInstance().getUSERNAME());
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
		tv_addStart.setVisibility(View.VISIBLE);
		tv_addEnd.setVisibility(View.GONE);
		if(stateinfo.getIsOutDoor()){
			tv_addStart.setVisibility(View.VISIBLE);
			tv_addEnd.setVisibility(View.GONE);
		} else {
			tv_addStart.setVisibility(View.GONE);
			tv_addEnd.setVisibility(View.VISIBLE);
		}
		
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
						.setTitle("请填写出场路码")
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
												myStateInfo
														.setBeginKMsOfToday(input);
												myStateInfo.setIsOutDoor(false);
												myGetStateInfo
														.setStateinfo(myStateInfo);
												tv_addStart
														.setVisibility(View.GONE);
												tv_addEnd
														.setVisibility(View.VISIBLE);
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
					.setTitle("请填写进场路码")
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
											myStateInfo.setEndKMsOfToday(input);
											myStateInfo.setIsOutDoor(true);
											myGetStateInfo
													.setStateinfo(myStateInfo);
											tv_addEnd.setVisibility(View.GONE);
											tv_addStart
													.setVisibility(View.VISIBLE);
										} catch (Exception e1) {
											// TODO Auto-generated catch block
											e1.printStackTrace();
										}
									}
									Log.i("jxb", "TimeOfTaskOneDay = "+stateinfo.getTimeOfTaskOneDay());
//									CarID().length() = "+stateinfo.getCurrentTask().CarID().length());
									if (stateinfo.getCurrentNote()!=null) {
										int iR;
										String routecode = "[";
										routecode = routecode
												+ stateinfo.getCurrentPerson().getPersonID() + ",";
										routecode = routecode + stateinfo.getCurrentTask().CarID()
												+ ",";
										routecode = routecode
												+ stateinfo.getCurrentNote().getNoteDate()
												.replaceAll("-", "") + ",";
										routecode = routecode + stateinfo.getBeginKMsOfToday() + ",";
										routecode = routecode + stateinfo.getEndKMsOfToday();
										routecode = routecode + "]";
										Log.i("jxb", "routecode = "+routecode);
										iR = getInfoValue.UploadRouteCode(routecode);
										if (iR == 0)
											Toast.makeText(getApplicationContext(), "路码上传成功！", 2000).show();
										else
											Toast.makeText(getApplicationContext(), "上传路码出错！", 2000).show();
										
									}
								}
							}).show();
		}

	};

	private void checkVersion(Context context){
		String serviceVer = getInfoValue.getVersion("123");
		String curVersion = FileUtil.getInstance().getVersion(context);
		Log.i("jxb", "serviceVersion = "+serviceVer+"   curVersion = "+curVersion);
		if(!serviceVer.equals(curVersion) && !serviceVer.equals("")){
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
		Log.i("jxb", "resume");
//		menu.toggle();
		setStatus();
		try {
			stateinfo = getStateInfo.getInstance(getApplicationContext())
					.getStateinfo();
			pauseNote = stateinfo.getPauseNote();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		refreshTasks();
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
		tv_title = (TextView) findViewById(R.id.tv_titleMain);
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

	private void uploadNotes(){
		ArrayList<NoteInfo> saveNotes = getStateInfo.getInstance(MainActivity.this).getNoteInfo();
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
				Log.i("jxb", "k(广播) = "+k);
			}
			if (k == 0 || k == 1){
				getStateInfo.getInstance(MainActivity.this).deleteFile();
			} 
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

		@SuppressLint("NewApi")
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
				holder.company = (TextView) convertView.findViewById(R.id.item_company);
				holder.isDone = (TextView) convertView.findViewById(R.id.item_isDone);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			TaskInfo taskInfo = tasklist.get(position);
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
			holder.type.setText(taskInfo.ServiceTypeName());
			holder.company.setText(taskInfo.getCustomerCompany());
			String isUpdate = taskInfo.getIsUpdate();
			int readmark = taskInfo.getReadmark();
			Log.i("jxb", "readmark = "+readmark+"  isupdate = "+isUpdate);
			if (taskInfo.getRouteNoteCount() > 0){
				holder.isDone.setVisibility(View.VISIBLE);
				holder.isDone.setText("已完成");
				holder.isDone.setBackgroundColor(Color.parseColor("#009944"));
			} else if (null != pauseNote && pauseNote.getTaskID().equals(taskInfo.TaskID())) {
				holder.isDone.setVisibility(View.VISIBLE);
				holder.isDone.setText("暂停中");
				holder.isDone.setBackgroundColor(Color.parseColor("#eb6100"));
				
//				holder.isDone.setBackground(getResources().getColor(R.color.colorYellow));
			} else if (readmark == 1) {
				holder.isDone.setVisibility(View.GONE);
			} else if (readmark == 0) {
				holder.isDone.setVisibility(View.VISIBLE);
				holder.isDone.setBackground(getResources().getDrawable(R.drawable.bg_red));
				if (isUpdate.equals("0")){
					holder.isDone.setText("新增");
				} else {
					holder.isDone.setText("修改");
				}
			} else {
				holder.isDone.setVisibility(View.GONE);
			}
			return convertView;
		}

	}

	static class ViewHolder {
		public TextView time;
		public TextView date;
		public TextView type;
		public TextView name;
		public TextView location;
		public TextView company;
		public TextView isDone;
	}
	
	private final Handler mHandler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case MSG_SET_ALIAS:
				Log.d("jxb", "Set alias in handler.");
				JPushInterface.setAliasAndTags(getApplicationContext(), (String) msg.obj, null, mAliasCallback);
				break;
				
//	            case MSG_SET_TAGS:
//	                Log.d("jxb", "Set tags in handler.");
//	                JPushInterface.setAliasAndTags(getApplicationContext(), null, (Set<String>) msg.obj, mTagsCallback);
//	                break;
				
			default:
				Log.i("jxb", "Unhandled msg - " + msg.what);
			}
		}
	};
	
	private final TagAliasCallback mAliasCallback = new TagAliasCallback() {

        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs ;
            switch (code) {
            case 0:
                logs = "Set tag and alias success";
                Log.i("jxb", logs);
                break;
                
            case 6002:
                logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
                Log.i("jxb", logs);
                mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_ALIAS, alias), 1000 * 60);
                break;
            
            default:
                logs = "Failed with errorCode = " + code;
                Log.e("jxb", logs);
            }
            
//            ExampleUtil.showToast(logs, getApplicationContext());
        }
	    
	};
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		
		if (keyCode == KeyEvent.KEYCODE_BACK) {
	        Intent home = new Intent(Intent.ACTION_MAIN);
	        home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	        home.addCategory(Intent.CATEGORY_HOME);
	        startActivity(home);
	        return true;
	    }
	    return super.onKeyDown(keyCode, event);
		
	};
	
}
