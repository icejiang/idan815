package com.dazhong.idan;

import java.util.ArrayList;
import java.util.List;

import com.dazhong.idan.MainActivity.MyAdapter;
import com.dazhong.idan.MainActivity.ViewHolder;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class OrderHistory extends Activity {

	private ListView mListView;
	private List<Order> mList;
	private ImageView iv_return;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.order_history);
		ActivityControler.addActivity(this);
		mListView = (ListView) findViewById(R.id.listview_history);
		iv_return = (ImageView) findViewById(R.id.return_history);
		addData();
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
	}
	
	
	private void addData(){
		mList = new ArrayList<Order>();
		mList.add(new Order("2015/11/11 11:11", "SR20151111000001", "机场接机", "王先生", "13838385438", "虹桥机场T1航站楼"));
		mList.add(new Order("2015/11/11 22:22", "SR20151111000002", "市用", "李先生", "13838385438", "人民广场"));
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
			// TODO Auto-generated method stub
			return mList.size();
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
				convertView = mInflater.inflate(R.layout.order_item, null);
				holder.date = (TextView) convertView.findViewById(R.id.item_date);
				holder.location = (TextView) convertView.findViewById(R.id.item_location);
				holder.name = (TextView) convertView.findViewById(R.id.item_name);
				holder.nubmer = (TextView) convertView.findViewById(R.id.item_number);
				holder.time = (TextView) convertView.findViewById(R.id.item_time);
				holder.type = (TextView) convertView.findViewById(R.id.item_type);
				convertView.setTag(holder);
			}else {
				holder = (ViewHolder) convertView.getTag();
			}
			Order mOrder = mList.get(position);
			holder.time.setText(mOrder.getTime());
			holder.date.setText(mOrder.getId());
			holder.location.setText(mOrder.getLocation());
			holder.name.setText(mOrder.getName());
			holder.nubmer.setText(mOrder.getNubmer());
			holder.type.setText(mOrder.getType()); 
			
			
			
			return convertView;
		}
		
		
	}
	static class ViewHolder  
	{  
		public TextView time;  
		public TextView date;  
		public TextView type;  
		public TextView name;  
		public TextView nubmer;  
		public TextView location;  
	}
	
}
