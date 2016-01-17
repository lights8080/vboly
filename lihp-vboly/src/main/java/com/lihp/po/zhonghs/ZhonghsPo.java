package com.lihp.po.zhonghs;

import java.io.Serializable;

import com.lihp.po.vboly.BasePo;

public class ZhonghsPo extends BasePo implements Serializable {

	private static final long serialVersionUID = -3400297278094497451L;

	private String itemId;
	private String sign;
	private String t;
	
	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getT() {
		return t;
	}

	public void setT(String t) {
		this.t = t;
	}
}
