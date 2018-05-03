package com.qdzl.bean;

import java.io.Serializable;

public class Container implements Serializable {
	private Integer id;
	private Integer type;
	private String explain;
	private String mac;
	private Integer layercount;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getExplain() {
		return explain;
	}

	public void setExplain(String explain) {
		this.explain = explain;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public Integer getLayercount() {
		return layercount;
	}

	public void setLayercount(Integer layercount) {
		this.layercount = layercount;
	}

}
