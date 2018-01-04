package com.dazhong.idan;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.json.JSONException;
import org.json.JSONObject;

import android.R.anim;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;

import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.utils.UrlSafeBase64;

public class PictureUtil {

	
	// ��ţ��̨��key
//	 private static String AccessKey ="TH2TPSkdkFovEk-8M9GTXkUtNNCKSXXBSqyaI_Kb";
	private static String AccessKey = "OI-CyZOrXiYINs8pBFc2BRs29aQcJQG3XHHu7X5Y";
	// ��ţ��̨��secret
//	 private static String SecretKey ="CbswH01wNB6ax29-GxzziEi0QqoBqsBM3TPn1dWy";
	private static String SecretKey = "jeEylWPul9tVQbFGNLHgMX77YRls_EesQuIPpcqS";
	private static final String MAC_NAME = "HmacSHA1";
	private static final String ENCODING = "UTF-8";
	// unixʱ���:2065-12-31 00:00:00
	private static long delayTimes = 3029414400l;
//	 private String spaceName = "mytest";
	private String spaceName = "driverapp";
	private static final String rootDir = Environment.getExternalStorageDirectory() + File.separator + "DZpicture/";
	private static final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 1;  
	
	
	/**�����ͼ�ķ���*/
	public boolean saveScreen(Activity activity,View view,String noteId){
		//�ж�sdcard�Ƿ����
		if(!Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
			return false;
		}
		
		if (ContextCompat.checkSelfPermission(activity, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
	              != PackageManager.PERMISSION_GRANTED) {
	          //����WRITE_EXTERNAL_STORAGEȨ��
	          ActivityCompat.requestPermissions(activity, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
	                  WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
	      }
		File file  = new File(rootDir);
		if(!file.exists()){
			file.mkdir();
		}
		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache();
		Bitmap bitmap = view.getDrawingCache();
		String id = noteId.replace("*", "");
		Log.i("jxb", "id = "+id);
		try {
			bitmap.compress(CompressFormat.JPEG, 100, new FileOutputStream(new File(rootDir + id + ".jpg")));
			return true;
		} catch (FileNotFoundException e) {
//			e.printStackTrace();
			Log.i("jxb", "ͼƬ�������"+ e);
			return false;
		}finally{
			view.setDrawingCacheEnabled(false);
			bitmap = null;
		}
	}
	
	
	public void uploadExistPic() {
		File file = new File(rootDir);
		if (!file.exists()) {
			Log.i("jxb", "ͼƬ�ļ��в�����");
//			return;
		}
		File[] subFile = file.listFiles();
		if (subFile == null || subFile.length == 0) {
			Log.i("jxb", "ͼƬ�ļ���Ϊ��");
			return;
		}
		try {
			// 1:��һ�ַ�ʽ �����ϴ�����
			JSONObject _json = new JSONObject();
			_json.put("deadline", delayTimes);// ��Чʱ��Ϊһ��Сʱ
			_json.put("scope", spaceName);
			String _encodedPutPolicy = UrlSafeBase64.encodeToString(_json
					.toString().getBytes());
			byte[] _sign = HmacSHA1Encrypt(_encodedPutPolicy, SecretKey);
			String _encodedSign = UrlSafeBase64.encodeToString(_sign);
			final String _uploadToken = AccessKey + ':' + _encodedSign + ':'
					+ _encodedPutPolicy;
			Log.i("jxb", "taken = "+_uploadToken);
			UploadManager uploadManager = new UploadManager();
			for (int iFileLenth = 0; iFileLenth < subFile.length; iFileLenth++) {
				if (!subFile[iFileLenth].isDirectory()) {
					String fileName = subFile[iFileLenth].getName();
					if (fileName.trim().toLowerCase().endsWith(".jpg")) {
						uploadManager.put(rootDir + fileName,
								fileName.substring(0, fileName.length() - 4),
								_uploadToken, new UpCompletionHandler() {
									@Override
									public void complete(String key,
											ResponseInfo info,
											JSONObject response) {
										if (info.isOK()) {
											Log.i("jxb", "existUpload Success");
											try {
												String name = response
														.getString("key");
												File file = new File(rootDir
														+ name + ".jpg");
												file.delete();
												Log.i("jxb", "delete picture name = " + name);
											} catch (JSONException e) {
												e.printStackTrace();
											}
										} else {
											Log.i("jxb", "existUpload Fail");
										}
									}

								}, null);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	
	/**
     * �ϴ�
     *
     * @param spaceName bucketName������
     * @param path   �ϴ��ļ���·����ַ
     * @param name   ָ����ţ�����ϵ��ļ������� null
     */
    public void uploadPic(final String spaceName, final String path, final String name) {
        try {
            // 1:��һ�ַ�ʽ �����ϴ�����
            JSONObject _json = new JSONObject();
            _json.put("deadline", delayTimes);// ��Чʱ��Ϊһ��Сʱ
            _json.put("scope", spaceName);
            String _encodedPutPolicy = UrlSafeBase64.encodeToString(_json
                    .toString().getBytes());
            byte[] _sign = HmacSHA1Encrypt(_encodedPutPolicy, SecretKey);
            String _encodedSign = UrlSafeBase64.encodeToString(_sign);
            final String _uploadToken = AccessKey + ':' + _encodedSign + ':'
                    + _encodedPutPolicy;
            UploadManager uploadManager = new UploadManager();
            uploadManager.put(path, name, _uploadToken,
                    new UpCompletionHandler() {
                        @Override
                        public void complete(String key, ResponseInfo info,
                                             JSONObject response) {
                       	 if (info.isOK()){
                       		Log.i("jxb", "Upload Success");
                       		try {
								String name = response.getString("key");
								File file = new File(rootDir+name+".jpg");
								file.delete();
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
                       	 } else {
                       		Log.i("jxb", "Upload Fail");
                       	 }
                        }

						
                    }, null);
        } catch (Exception e) {
        	Log.i("jxb", "�ϴ�ͼƬ����"+e.toString());
            e.printStackTrace();
        }
    }
    
    /**
     * ʹ�� HMAC-SHA1 ǩ�������Զ�encryptText����ǩ��
     *
     * @param encryptText ��ǩ�����ַ���
     * @param encryptKey  ��Կ
     * @return
     * @throws Exception
     */
    public byte[] HmacSHA1Encrypt(String encryptText, String encryptKey)
            throws Exception {
        byte[] data = encryptKey.getBytes(ENCODING);
        // ���ݸ������ֽ����鹹��һ����Կ,�ڶ�����ָ��һ����Կ�㷨������
        SecretKey secretKey = new SecretKeySpec(data, MAC_NAME);
        // ����һ��ָ�� Mac �㷨 �� Mac ����
        Mac mac = Mac.getInstance(MAC_NAME);
        // �ø�����Կ��ʼ�� Mac ����
        mac.init(secretKey);
        byte[] text = encryptText.getBytes(ENCODING);
        // ��� Mac ����
        return mac.doFinal(text);
    }
    
    /**
     * ͨ��key��ȡ�ϴ�����Դ�ļ���ȫ·��
     *
     * @param name
     * @param spaceName
     * @return
     */
    /*public static String getFileUrl(String spaceName, String name) {
        String url = HdUtils.transDomai2Zone(spaceName);
        StringBuilder sb = new StringBuilder();
        sb.append(url);
        try {
            //1:����URL
            String encode = URLEncoder.encode(name, "UTF-8");
            sb.append(encode);
            //2:Ϊurl���Ϲ���ʱ��  unixʱ��
            sb.append("?e=" + delayTimes);//delayTimes = 1451491200
            //3:��1 2 �������url����hmac-sha1ǩ�� secrect
            String s = sb.toString();
            byte[] bytes = HmacSHA1Encrypt(s, SecretKey);
            String sign = UrlSafeBase64.encodeToString(bytes);
            //4:��accsesskey ��������
            sb.append("&token=" + AccessKey + ":" + sign);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
	}*/
	
}
