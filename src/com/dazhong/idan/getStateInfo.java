package com.dazhong.idan;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;

import android.app.Activity;
import android.content.ComponentCallbacks;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;

public class getStateInfo extends Activity {
	private Context context;
	
	public getStateInfo(Context context) {
		super();
		this.context = context;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	public void OutputObject(Map<String, PersonInfo> map) throws Exception {
		// create objects to output
		File extDir = Environment.getExternalStorageDirectory();

		// String filename = "downloadedMusic.mp3";
		// File fullFilename = new File(extDir, filename);
		String path = extDir + "/Person.txt";
		System.out.println(path);
		System.out.println("person is " + map.entrySet().size());

		File saveFile = new File(extDir, "Download/person.txt");
		System.out.println(saveFile.toURI());
//		FileOutputStream fout = new FileOutputStream(saveFile);

		 FileOutputStream fout= context.openFileOutput("person1.txt",Context.MODE_PRIVATE);
		// Context.MODE_PRIVATE);
		System.out.println("path is not  right!");
		// FileOutputStream fo;
		// FileOutputStream ft=new FileOutputSteam()
		// ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(
		// path));
		ObjectOutputStream oos = new ObjectOutputStream(fout);
		for (Map.Entry<String, PersonInfo> entry : map.entrySet()) {
			oos.writeObject(entry.getValue());
			System.out.println(entry.getKey() + "--->" + entry.getValue().toString());
		}
		// create the output stream
		// write object
		// oos.writeObject(p2);
		// flush the stream
		oos.flush();
		// close the stream
		oos.close();
	}

	public void inputObject() throws Exception {
		try {
			File extDir = Environment.getExternalStorageDirectory();
			String path = extDir + "/Person.txt";
			// create inputObjectStream
			FileInputStream fin=context.openFileInput("person1.txt");
//			ObjectInputStream ois = new ObjectInputStream(new FileInputStream("person1.txt"));
			ObjectInputStream ois = new ObjectInputStream(fin);
			// temp object to receive the value of this stream read everytime
			Object obj = null;
			PersonInfo p = null;
			while ((obj = ois.readObject()) != null) {
				p = (PersonInfo) obj;
				// show the object read by the stream
				System.out.println(p.toString());
			}
		} catch (EOFException e) {
			// throw EOFException when read end
			System.err.println("∂¡»°ÕÍ±œ");
		}
	}

}
