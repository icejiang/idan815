package com.dazhong.idan;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity{
	
	private ListView mListView;
	private List<Order> mList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.business_list);
		findView();
		addData();
		MyAdapter mAdapter = new MyAdapter(this);
		mListView.setAdapter(mAdapter);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent();
				intent.setClass(getApplicationContext(), OrderDetail.class);
				startActivity(intent);
				
			}
		});
//		mAdapter.notifyDataSetChanged();
	}
	
	
	
	private void findView(){
		mListView = (ListView) findViewById(R.id.listView_main);
	}
	
	private void addData(){
		mList = new ArrayList<Order>();
		mList.add(new Order("2015/11/11 11:11", "SR20151111000001", "机场接机", "王先生", "13838385438", "虹桥机场T1航站楼"));
		mList.add(new Order("2015/11/11 22:22", "SR20151111000002", "市用", "李先生", "13838385438", "人民广场"));
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
				holder.id = (TextView) convertView.findViewById(R.id.item_id);
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
			holder.id.setText(mOrder.getId());
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
		public TextView id;  
		public TextView type;  
		public TextView name;  
		public TextView nubmer;  
		public TextView location;  
	}
}
