package com.lihp.base;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.lihp.constant.VbolyConstant;

public class ReloadTimes implements Runnable {

	protected static Logger logger = LogManager.getLogger(ReloadTimes.class);
	public ReloadTimes(BaseProcess process) {
		this.process = process;
	}

	private BaseProcess process;

	public BaseProcess getProcess() {
		return process;
	}

	public void setProcess(BaseProcess process) {
		this.process = process;
	}

	@Override
	public void run() {
		int second = process.reloadItemTimes();
		logger.debug("剩余时间:{}秒",second);
		try {
			if (second == VbolyConstant.COMMON_STATE_BUYED) {// 已抢购
			} else if (second == VbolyConstant.COMMON_STATE_END) {// 已结束
			} else if (second == VbolyConstant.COMMON_STATE_OVER) {// 已抢完
			} else if (second == VbolyConstant.COMMON_STATE_HOPE) {// 还有机会
				Thread.sleep(VbolyConstant.HOPE_REFRESH_MILLIS_DEF);
				new Thread(this).start();
			} else if (second == VbolyConstant.COMMON_STATE_BUYING) {// 我要抢购
				new Thread(process).start();
			} else if (second <= VbolyConstant.BUY_MIN_SECOND_DEF) {// 抢购倒计时（秒）
				new Thread(process).start();
			} else if (second >= 600) {// 待上线 剩余时间
				Thread.sleep(600000 - VbolyConstant.BUY_BUFFER_SECOND_DEF * 1000);
				new Thread(this).start();
			} else if (second >= 300) {
				Thread.sleep(300000 - VbolyConstant.BUY_BUFFER_SECOND_DEF * 1000);
				new Thread(this).start();
			} else if (second >= (VbolyConstant.BUY_MIN_SECOND_DEF
					+ VbolyConstant.BUY_BUFFER_SECOND_DEF)) {
				Thread.sleep((second - VbolyConstant.BUY_BUFFER_SECOND_DEF) * 1000);
				new Thread(this).start();
			} else {
				Thread.sleep(VbolyConstant.HOPE_REFRESH_MILLIS_DEF);
				new Thread(this).start();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
