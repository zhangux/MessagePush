package com.qdzl.bean;

import java.util.Map;

public class RequestData {
	private Integer act;
	private Map<String, String> data;

	public Integer getAct() {
		return act;
	}

	public void setAct(Integer act) {
		this.act = act;
	}

	public Map<String, String> getData() {
		return data;
	}

	public void setData(Map<String, String> data) {
		this.data = data;
	}

	public RequestData() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RequestData(Integer act, Map<String, String> data) {
		super();
		this.act = act;
		this.data = data;
	}

}
