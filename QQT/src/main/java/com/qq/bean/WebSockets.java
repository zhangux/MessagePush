package com.qq.bean;

public class WebSockets {
	private Integer type;
	private String username;
	private String msg;

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public WebSockets(Integer type, String username, String msg) {
		this.type = type;
		this.username = username;
		this.msg = msg;
	}
}
