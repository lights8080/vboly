package com.lihp.po.vboly;

import java.io.Serializable;

public class BasePo implements Serializable {

	private static final long serialVersionUID = 9037616302186076737L;

	private String username;
	private String pwd;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	
}
