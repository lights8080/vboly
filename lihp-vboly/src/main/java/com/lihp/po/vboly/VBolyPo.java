package com.lihp.po.vboly;

import java.io.Serializable;

public class VBolyPo extends BasePo implements Serializable {

	private static final long serialVersionUID = -3400297278094497451L;

	private String  itemId;
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
}
