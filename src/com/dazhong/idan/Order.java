package com.dazhong.idan;

import android.widget.TextView;

public class Order {
	
	private String time;
	private String id;
	private String type;
	private String name;
	private String nubmer;
	private String location;
	
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNubmer() {
		return nubmer;
	}
	public void setNubmer(String nubmer) {
		this.nubmer = nubmer;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	
	public Order(String time, String id, String type, String name,
			String nubmer, String location) {
		super();
		this.time = time;
		this.id = id;
		this.type = type;
		this.name = name;
		this.nubmer = nubmer;
		this.location = location;
	}
	
	

}
