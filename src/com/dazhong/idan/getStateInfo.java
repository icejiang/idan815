package com.dazhong.idan;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.os.Environment;
import android.renderscript.Type;

public class getStateInfo {
	private static Context context=null;
	private StateInfo Stateinfo;
	private List<TaskInfo> Tasks;
	private List<NoteInfo> Notes;

	private static volatile getStateInfo instance = null;

	public static getStateInfo getInstance(Context whoseContext) {
		context=whoseContext;
		if (instance == null) {
			instance=new getStateInfo(context);
		}
		return instance;
	}

	/**
	 * 获取当前状态信息
	 * */
	public StateInfo getStateinfo() throws Exception {
		Map<String, Object> map;
		map = inputObjects("stateinfo");
		if (map == null)
			return null;
		System.out.println("state is ok");
		for (Map.Entry<String, Object> entry : map.entrySet())
			Stateinfo = (StateInfo) entry.getValue();
		return Stateinfo;
	}

	/**
	 * 更新当前状态
	 * */
	public void setStateinfo(StateInfo stateinfo) {
		Map<String, Object> map = new HashMap<String, Object>();
		Object obj = stateinfo;
		try {
			map.put("stateinfo", obj);
			OutputObjects(map, "stateinfo");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("update state error");
		}
		Stateinfo = stateinfo;
	}

	/**
	 * 获取 调度单列表
	 * */
	public List<TaskInfo> getTasks() {
		return Tasks;
	}

	/**
	 * 设置 调度单列表
	 * */
	public void setTasks(List<TaskInfo> tasks) {
		Tasks = tasks;
	}

	/**
	 * 获取 历史路单列表
	 * */
	public List<NoteInfo> getNotes() {
		return Notes;
	}

	/**
	 * 设置 历史路单列表
	 * */
	public void setNotes(List<NoteInfo> notes) {
		Notes = notes;
	}

	protected getStateInfo(Context context) {
		super();
		this.context = context;
	}

	protected void OutputObject(Map<String, Object> map) throws Exception {
		// create objects to output
		File extDir = Environment.getExternalStorageDirectory();
		String path = extDir + "/Person.txt";
		File saveFile = new File(extDir, "Download/person.txt");
		System.out.println(saveFile.toURI());
		FileOutputStream fout = context.openFileOutput("person1.txt",
				Context.MODE_PRIVATE);
		ObjectOutputStream oos = new ObjectOutputStream(fout);
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			oos.writeObject(entry.getValue());
			System.out.println(entry.getValue().toString());
		}
		oos.flush();
		oos.close();
	}

	protected void OutputObjects(Map<String, Object> map, String objectName)
			throws Exception {
		// create objects to output
		FileOutputStream fout = context.openFileOutput(objectName,
				Context.MODE_PRIVATE);
		ObjectOutputStream oos = new ObjectOutputStream(fout);
		for (Map.Entry<String, Object> entry : map.entrySet())
			oos.writeObject(entry.getValue());
		oos.flush();
		oos.close();
	}

	protected void inputObject() throws Exception {
		try {
			File extDir = Environment.getExternalStorageDirectory();
			String path = extDir + "/Person.txt";
			FileInputStream fin = context.openFileInput("person1.txt");
			ObjectInputStream ois = new ObjectInputStream(fin);
			Object obj = null;
			PersonInfo p = null;
			Class cc;
			while ((obj = ois.readObject()) != null) {
				p = (PersonInfo) obj;
				System.out.println(p.toString());
			}
			ois.close();
		} catch (EOFException e) {
			// throw EOFException when read end
			System.err.println("读取完毕");
		}
	}

	protected Map<String, Object> inputObjects(String objectName)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			FileInputStream fin = context.openFileInput(objectName);
			ObjectInputStream ois = new ObjectInputStream(fin);
			// temp object to receive the value of this stream read everytime
			Object obj = null;
			int i = 0;
			while ((obj = ois.readObject()) != null) {
				// show the object read by the stream
				i = i + 1;
				map.put(String.valueOf(i), obj);
			}
		} catch (EOFException e) {
			// throw EOFException when read end
			System.err.println("读取完毕");

		}
		return map;
	}
}
