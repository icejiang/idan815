package com.dazhong.idan;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

public class FileUtil {

	
//	public synchronized static FileUtil getInstance() {
//		if (instance == null) {
//			instance = new FileUtil();
//		}
//		return instance;
//	}
	private volatile static FileUtil instance;  
	  
    private FileUtil() {  
    }  
    public static FileUtil getInstance() {  
        if (instance == null) {  
            synchronized (FileUtil.class) {  
                if (instance == null) {  
                	instance = new FileUtil();  
                }  
            }  
        }  
        return instance;  
    } 
	
    public String getVersion(Context context) {
    	try {
        	 PackageManager manager = context.getPackageManager();
        	 PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
        	 String version = info.versionName;
        	 return version;
        } catch (Exception e) {
        	e.printStackTrace();
        	return "";
        }
    }
    
    
	public void putValue(Context context,String key,String value){
		SharedPreferences mySharedPreferences= context.getSharedPreferences("myData", Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = mySharedPreferences.edit(); 
		editor.putString(key, value); 
		editor.commit(); 
	} 
	
	public String getValue(Context context,String key){
		SharedPreferences sharedPreferences= context.getSharedPreferences("myData", Activity.MODE_PRIVATE); 
		String value =sharedPreferences.getString(key, ""); 
		return value;
	}
	
	
}
