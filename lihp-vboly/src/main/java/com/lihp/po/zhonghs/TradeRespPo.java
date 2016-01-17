package com.lihp.po.zhonghs;

import java.io.Serializable;

public class TradeRespPo implements Serializable{

	private static final long serialVersionUID = -3973481742914815973L;
	private boolean success;
	private String info;
	private String new_oid;
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public String getNew_oid() {
		return new_oid;
	}
	public void setNew_oid(String new_oid) {
		this.new_oid = new_oid;
	}
}
