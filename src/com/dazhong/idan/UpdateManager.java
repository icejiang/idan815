package com.dazhong.idan;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

public class UpdateManager {

	private ProgressDialog progressDialog;
	private Context context;
	
	//��ȡ�°�APK��Ĭ�ϵ�ַ
    private String apk_path = "http://www.dz-zc.com/dzapp.apk";
    private static final String FILE_PATH = Environment.getExternalStorageDirectory()+File.separator+"zhongxing/";
    private static final String FILE_NAME = FILE_PATH + "dzapp.apk";
    
    public UpdateManager(Context context) {
        this.context = context;
    }
	
    
    /**
     * ��ʾ���ؽ��ȶԻ���
     */
    public void showDownloadDialog() {

        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("��������...");
        progressDialog.setCanceledOnTouchOutside(true);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);

        new downloadAsyncTask().execute();
    }
	
    
    private class downloadAsyncTask extends AsyncTask<Void, Integer, Integer>{

    	@Override
    	protected void onPreExecute() {
    		// TODO Auto-generated method stub
    		Log.i("jxb", "onPreExcute");
    		progressDialog.show();
    	}
    	
		@Override
		protected Integer doInBackground(Void... params) {
			// TODO Auto-generated method stub
			Log.i("jxb", "doInBackground");
			URL url;
			HttpURLConnection connection = null;
			InputStream in = null;
			FileOutputStream out = null;
			try {
				url = new URL(apk_path);
				connection = (HttpURLConnection) url.openConnection();
				in = connection.getInputStream();
				long fileLenth = connection.getContentLength();
				File file_path = new File(FILE_PATH);
				if (!file_path.exists()){
					file_path.mkdir();
				}
				out = new FileOutputStream(new File(FILE_NAME));
				byte[] buffer = new byte[1024*1024];
				int len = 0;
				long readLenth = 0;
				while ((len = in.read(buffer)) != -1){
					out.write(buffer, 0, len);
					readLenth += len;
					int curProgress = (int) (((float)readLenth/fileLenth)*100);
					publishProgress(curProgress);
					if (readLenth >= fileLenth) {
						break;
					}
				}
				out.flush();
				return 1;
				
			} catch (Exception e) {
				e.printStackTrace();
				Log.i("jxb", e.toString());
			} finally {
				if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                if (connection != null) {
                    connection.disconnect();
                }
			}
			return null;
		}
		
		@Override
        protected void onProgressUpdate(Integer... values) {

//            Log.i("jxb", "�첽���½��Ƚ��յ���ֵ��" + values[0]);
            progressDialog.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Integer integer) {

            progressDialog.dismiss();//�رս�����
            //��װӦ��
            installApp();
        }
        
        
    	
    }
    
    /**
     * ��װ�°汾Ӧ��
     */
    private void installApp() {
    	File appFile = new File(FILE_NAME);
    	if (!appFile.exists()) {
    		return;
    	}
    	// ��ת���°汾Ӧ�ð�װҳ��
    	Intent intent = new Intent(Intent.ACTION_VIEW);
    	intent.setDataAndType(Uri.parse("file://" + appFile.toString()), "application/vnd.android.package-archive");
    	context.startActivity(intent);
    }
}
