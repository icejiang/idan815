package com.dazhong.idan;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class PrintActivity extends Activity {
	
	private TextView print_confirm;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.printf);
		findView();
		print_confirm.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				
			}
		});
		
		
	}
	
	
	private void findView(){
		print_confirm = (TextView) findViewById(R.id.print_confirm);
	}
}
