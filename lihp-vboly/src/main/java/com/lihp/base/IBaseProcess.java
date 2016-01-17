package com.lihp.base;

public interface IBaseProcess {
	/**
	 * 登录
	 * 
	 * @return 成功:true 失败:false
	 */
	public abstract boolean login();

	/**
	 * 刷新获得商品剩余时间
	 * 
	 * @return 剩余秒
	 */
	public abstract int reloadItemTimes();

	/**
	 * 抢购
	 * 
	 * @return 成功:true 失败:false
	 */
	public abstract boolean buy();
}
