package com.dazhong.idan;

import java.util.ArrayList;
import java.util.List;

import com.dazhong.idan.MainActivity.MyAdapter;
import com.dazhong.idan.MainActivity.ViewHolder;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class OrderHistory extends Activity {

	private ListView mListView;
	private ImageView iv_return;
	private ImageView iv_home;
	private List<NoteInfo> noteList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.order_history);
		ActivityControler.addActivity(this);
		mListView = (ListView) findViewById(R.id.listview_history);
		iv_return = (ImageView) findViewById(R.id.return_history);
		iv_home = (ImageView) findViewById(R.id.home_history);
		try {
			String id = getStateInfo.getInstance(getApplicationContext())
					.getStateinfo().getCurrentPerson().getPersonID();
			Log.i("jxb", "size = "+id);
			noteList = getInfoValue.getNotes(id, 0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		MyAdapter mAdapter = new MyAdapter(this);
		mListView.setAdapter(mAdapter);
		final SlidingMenu menu = new SlidingMenu(this);
		showLeftMenu(menu);
		iv_return.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				menu.showMenu();
			}
		});
		iv_home.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				 Intent intent = new Intent();
				 intent.setClass(getApplicationContext(), MainActivity.class);
				 startActivity(intent);

			}
		});
		
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				NoteInfo noteInfo = noteList.get(position);
				Intent intent = new Intent();
				intent.putExtra("NOTEINFO", noteInfo);
				intent.setClass(getApplicationContext(), HistoryDetail.class);
				startActivity(intent);
			}
		});
		
		
	}
	
	
	private void showLeftMenu(SlidingMenu menu){
		MenuLeftFragment menuLayout = new MenuLeftFragment(
				getApplicationContext());
		menu.setMode(SlidingMenu.LEFT);
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		menu.setShadowWidthRes(R.dimen.shadow_width);
		menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		menu.setFadeDegree(0.35f);
		menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
		menu.setMenu(R.layout.left_menu);
	}
	
class MyAdapter extends BaseAdapter{
		
		private LayoutInflater mInflater = null;
		private Context mContext;
		
		public MyAdapter(Context context) {
			this.mContext = context;
			this.mInflater = LayoutInflater.from(context);
		}
		
		@Override
		public int getCount() {
			if (noteList != null) {
				return noteList.size();
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
			if(convertView == null){
				holder = new ViewHolder();
				convertView = mInflater.inflate(R.layout.history_item, null);
				holder.date = (TextView) convertView.findViewById(R.id.history_date);
				holder.id = (TextView) convertView.findViewById(R.id.history_id);
				holder.sum = (TextView) convertView.findViewById(R.id.history_sum);
				convertView.setTag(holder);
			}else {
				holder = (ViewHolder) convertView.getTag();
			}
			NoteInfo noteInfo = noteList.get(position);
			holder.date.setText(noteInfo.getNoteDate());
			holder.id.setText(noteInfo.getNoteID());
			holder.sum.setText(noteInfo.getFeeTotal()+"Ԫ");
			return convertView;
		}
		
		
	}
	static class ViewHolder  
	{  
		public TextView date;  
		public TextView id;  
		public TextView sum;  
	}
	
}
