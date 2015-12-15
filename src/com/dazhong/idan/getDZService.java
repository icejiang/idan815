package com.dazhong.idan;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.xmlpull.v1.XmlPullParser;

import android.os.Environment;
import android.util.Xml;

public abstract class getDZService {
	// public static function
	public static String getInfoValue(String sInfo, String sValueBegin,
			String sValueEnd) {
		if (sInfo.length() > sValueBegin.length() + sValueEnd.length())
			return (sInfo.substring(
					sInfo.indexOf(sValueBegin) + sValueBegin.length(),
					sInfo.indexOf(sValueEnd)));
		return null;
	}

	public static String getInfoValue(String sInfo, String sValue) {
		String sInf = null;
		String sBegin = null;
		String sEnd = null;
		if (sInfo == null || sValue == null
				|| sInfo.length() < 2 * sValue.length() - 1)
			return null;
		sBegin = "<" + sValue + ">";
		sEnd = "</" + sValue + ">";
		sInf = sInfo.substring(sInfo.indexOf(sBegin) + sBegin.length(),
				sInfo.indexOf(sEnd));
		if (sInf == null || sInf.length() == 0)
			sInf = "";
		return sInf;
	}

	public static List<String> getInfoValue(String sInfo) {
		List<String> listValues = null;
		if (sInfo == null)
			return null;
		try {
			String sBegin = "<Root>";
			String sEnd = "</Root>";
			String sInfo1 = null;
			String[] sSplit = sInfo.split(sEnd);
			listValues = new ArrayList<String>();
			for (int i = 0; i < sSplit.length; i++) {
				sInfo1 = sSplit[i];
//				System.out.println(sInfo1);
				if (sInfo1.indexOf(sBegin) !=-1) {
					sInfo1 = sInfo1.substring(sInfo1.indexOf(sBegin)
							+ sBegin.length());
					listValues.add(sInfo1);
				}
				sInfo1 = null;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listValues;
	}

	public static String getServiceConnect(String mobile1, String mobile2,
			String mobile3, String sCode) throws Exception {
		String soap = readSoap(sCode);
		soap = soap.replaceAll("\\$mobile1", mobile1);
		soap = soap.replaceAll("\\$mobile2", mobile2);
		soap = soap.replaceAll("\\$mobile3", mobile3);
		byte[] entity = soap.getBytes("utf-8");
//		String path = MainActivity.SERVICEADRRESS;
		String path =iDanApp.getInstance().getSERVICEADRRESS();
		HttpURLConnection conn = (HttpURLConnection) new URL(path)
				.openConnection();
		conn.setConnectTimeout(5000);
		conn.setRequestMethod("POST");
		conn.setDoOutput(true);
		conn.setRequestProperty("Content-Type",
				"application/soap+xml;charset=utf-8");
		conn.setRequestProperty("Content-Length", String.valueOf(entity.length));
		conn.getOutputStream().write(entity);
		if (conn.getResponseCode() == 200) {
			return parseSoap(conn.getInputStream(), sCode);
		}
		return null;
	}

	public static String getServiceConnect(String mobile1, String mobile2,
			String sCode) throws Exception {
		String soap = readSoap(sCode);
		soap = soap.replaceAll("\\$mobile1", mobile1);
		soap = soap.replaceAll("\\$mobile2", mobile2);
		byte[] entity = soap.getBytes("utf-8");
		String path =iDanApp.getInstance().getSERVICEADRRESS();
		HttpURLConnection conn = (HttpURLConnection) new URL(path)
				.openConnection();
		conn.setConnectTimeout(5000);
		conn.setRequestMethod("POST");
		conn.setDoOutput(true);
		conn.setRequestProperty("Content-Type",
				"application/soap+xml;charset=utf-8");
		conn.setRequestProperty("Content-Length", String.valueOf(entity.length));
		conn.getOutputStream().write(entity);
		if (conn.getResponseCode() == 200) {
			return parseSoap(conn.getInputStream(), sCode);
		}
		System.out.println("Davis say get internet info fail");
		return null;
	}

	public static String getServiceConnect(String mobile, String sCode)
			throws Exception {
		String soap = readSoap(sCode);
		soap = soap.replaceAll("\\$mobile", mobile);
		byte[] entity = soap.getBytes("utf-8");
		String path = iDanApp.getInstance().getSERVICEADRRESS();
		HttpURLConnection conn = (HttpURLConnection) new URL(path)
				.openConnection();
		conn.setConnectTimeout(5000);
		conn.setRequestMethod("POST");
		conn.setDoOutput(true);
		conn.setRequestProperty("Content-Type",
				"application/soap+xml;charset=utf-8");
		conn.setRequestProperty("Content-Length", String.valueOf(entity.length));
		conn.getOutputStream().write(entity);
		if (conn.getResponseCode() == 200) {
			return parseSoap(conn.getInputStream(), sCode);
		}
		return null;
	}

	public static String getServiceConnect(String sCode) throws Exception {
		String soap = readSoap(sCode);
		byte[] entity = soap.getBytes("utf-8");
		String path = iDanApp.getInstance().getSERVICEADRRESS();
		HttpURLConnection conn = (HttpURLConnection) new URL(path)
				.openConnection();
		conn.setConnectTimeout(5000);
		conn.setRequestMethod("POST");
		conn.setDoOutput(true);
		conn.setRequestProperty("Content-Type",
				"application/soap+xml;charset=utf-8");
		conn.setRequestProperty("Content-Length", String.valueOf(entity.length));
		conn.getOutputStream().write(entity);
		if (conn.getResponseCode() == 200) {
			return parseSoap(conn.getInputStream(), sCode);
		}
		return null;
	}

	public static String getAddress(String mobile, String sCode)
			throws Exception {
		String soap = readSoap(sCode);
		soap = soap.replaceAll("\\$mobile", mobile);
		byte[] entity = soap.getBytes("utf-8");
		String path =iDanApp.getInstance().getSERVICEADRRESS();
		HttpURLConnection conn = (HttpURLConnection) new URL(path)
				.openConnection();
		conn.setConnectTimeout(5000);
		conn.setRequestMethod("POST");
		conn.setDoOutput(true);
		conn.setRequestProperty("Content-Type",
				"application/soap+xml;charset=utf-8");
		conn.setRequestProperty("Content-Length", String.valueOf(entity.length));
		conn.getOutputStream().write(entity);
		if (conn.getResponseCode() == 200) {
			return parseSoap(conn.getInputStream(), sCode);
		}
		return null;
	}

	public static String getAddress(String sCode) throws Exception {
		String soap = readSoap(sCode);
		byte[] entity = soap.getBytes("utf-8");
		String path = iDanApp.getInstance().getSERVICEADRRESS();
		HttpURLConnection conn = (HttpURLConnection) new URL(path)
				.openConnection();
		conn.setConnectTimeout(5000);
		conn.setRequestMethod("POST");
		conn.setDoOutput(true);
		conn.setRequestProperty("Content-Type",
				"application/soap+xml;charset=utf-8");
		conn.setRequestProperty("Content-Length", String.valueOf(entity.length));
		conn.getOutputStream().write(entity);
		if (conn.getResponseCode() == 200) {
			return parseSoap(conn.getInputStream(), sCode);
		}
		return null;
	}

	public static String parseXML(InputStream responseStream, String strresult)
			throws Exception {
		XmlPullParser parser = Xml.newPullParser();
		parser.setInput(responseStream, "UTF-8");
		int event = parser.getEventType();
		while (event != XmlPullParser.END_DOCUMENT) {
			switch (event) {
			case XmlPullParser.START_TAG:
				if (strresult.equals(parser.getName())) {
					return parser.nextText();
				}
				break;
			}
			event = parser.next();
		}
		return null;
	}

	private static String parseSoap(InputStream xml, String scode)
			throws Exception {
		String sEnd = scode + "Result";
		XmlPullParser pullParser = Xml.newPullParser();
		pullParser.setInput(xml, "UTF-8");
		int event = pullParser.getEventType();
		while (event != XmlPullParser.END_DOCUMENT) {
			switch (event) {
			case XmlPullParser.START_TAG:
				if (sEnd.equals(pullParser.getName())) {
					return pullParser.nextText();
				}
				break;
			}
			event = pullParser.next();
		}
		return null;
	}

	private static String readSoap(String scode) throws Exception {
		InputStream inStream = null;
		String sPath = scode + ".xml";
		inStream = getDZService.class.getClassLoader().getResourceAsStream(
				sPath);
		byte[] data = read(inStream);
		return new String(data);

	}

	public static byte[] read(InputStream inStream) throws Exception {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[4096];
		int len = 0;
		while ((len = inStream.read(buffer)) != -1) {
			outStream.write(buffer, 0, len);
		}
		inStream.close();
		return outStream.toByteArray();
	}

	public static byte[] toByteArray(InputStream input) throws Exception {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		byte[] buffer = new byte[4096];
		int n = 0;
		while (-1 != (n = input.read(buffer))) {
			output.write(buffer, 0, n);
		}
		return output.toByteArray();
	}
}
