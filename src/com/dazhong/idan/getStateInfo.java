package com.dazhong.idan;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.R.integer;
import android.content.Context;
import android.os.Environment;
import android.renderscript.Type;
import android.util.Log;

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
	
	public void saveNoteInfo(NoteInfo noteInfo){
		File file = new File(Environment.getExternalStorageDirectory().toString()+File.separator+"note.dat");
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		FileInputStream fileInputStream = null;
		ObjectInputStream objectInputStream = null;
		ArrayList<NoteInfo> newNoteInfos = new ArrayList<NoteInfo>();
		if (file.exists()) {
			try {
				fileInputStream = new FileInputStream(file);
				objectInputStream = new ObjectInputStream(fileInputStream);
				ArrayList<NoteInfo> savedNoteInfos = (ArrayList<NoteInfo>) objectInputStream
						.readObject();
				if (savedNoteInfos != null && savedNoteInfos.size() != 0) {
					for (int i = 0; i < savedNoteInfos.size(); i++) {
						newNoteInfos.add(savedNoteInfos.get(i));
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if (objectInputStream != null) {
					try {
						objectInputStream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}  
				if (fileInputStream != null) {
					try {
						fileInputStream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		} else {
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		newNoteInfos.add(noteInfo);
		FileOutputStream fileOutputStream = null;
		ObjectOutputStream objectOutputStream = null;
		try {
			fileOutputStream = new FileOutputStream(file.toString());
			objectOutputStream = new ObjectOutputStream(fileOutputStream);
			objectOutputStream.writeObject(newNoteInfos);
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			if (objectOutputStream!=null) {
				try {
					objectOutputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (fileOutputStream!=null) {
				try {
					fileOutputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
//    	FileInputStream fileInputStream = null;
	
	}
	
	public ArrayList<NoteInfo> getNoteInfo(){
		FileInputStream fileInputStream = null;
		ObjectInputStream objectInputStream = null;
		ArrayList<NoteInfo> savedNoteInfos = new ArrayList<NoteInfo>();
		File file = new File(Environment.getExternalStorageDirectory().toString()+File.separator+"note.dat");
		if (file.exists()){
			try {
				fileInputStream = new FileInputStream(file.toString());
				objectInputStream = new ObjectInputStream(fileInputStream);
				savedNoteInfos = (ArrayList<NoteInfo>)objectInputStream.readObject();
//				Log.i("jxb", savedNoteInfos.toString());
				Log.i("jxb", "保存数量 = "+savedNoteInfos.size());
				if(savedNoteInfos!= null){
					for (int i = 0; i < savedNoteInfos.size(); i++) {
						Log.i("jxb","取出的数据:" + savedNoteInfos.get(i).toString()+"\n");
					}
				} else {
					return null;
				}
			} catch (StreamCorruptedException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}finally{
				if (objectInputStream!=null) {
					try {
						objectInputStream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if (fileInputStream!=null) {
					try {
						fileInputStream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return savedNoteInfos;
	}
	
	
	public void deleteFile(){
		File file = new File(Environment.getExternalStorageDirectory().toString()+File.separator+"note.dat");
		file.delete();
	}
	
	
	
	
	
	
}
