package com.lihp.base;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.util.Cookie;
import com.lihp.constant.VbolyConstant;

public abstract class BaseProcess implements Runnable,IBaseProcess {

	protected static Logger consoleLogger = LogManager.getLogger("LOG_CONSOLE");
	protected static Logger logger;
	private static int buyCount = 0;//刷购买次数
	
	private WebClientManager webClientManager;

	public BaseProcess() {
		webClientManager = WebClientManager.getInstance();
		logger = LogManager.getLogger(this.getClass().getName());
	}

	public WebClientManager getWebClientManager() {
		return webClientManager;
	}

	public WebClient getWebClient() {
		return webClientManager.getWebClient();
	}

	public void bindMyCookies(WebClient webClient) {
		webClientManager.bindCookies(webClient);
	}

	public List<Cookie> getCookies() {
		List<Cookie> cookies = webClientManager.getCookies();
		return cookies;
	}

	public void execute() {
		if (!webClientManager.isLogin()) {
			webClientManager.setLogin(login());
		}
		new Thread(new ReloadTimes(this)).start();
	}

	public void executeAll() {
		if (!webClientManager.isLogin()) {
			webClientManager.setLogin(login());
		}
		new Thread(new ReloadTimes(this)).start();
	}
	
	public void skipRebuy(){
		buyCount=4;
	}
	
	@Override
	public void run() {
		boolean buy = this.buy();
		if (!buy) {
			try {
				//如果buyCount>3次 重新刷新
				if(buyCount>3){
					buyCount=0;
					new Thread(new ReloadTimes(this)).start();
				}else{
					buyCount++;
					Thread.sleep(VbolyConstant.BUY_INTERVAL_MILLIS_DEF);
					new Thread(this).start();
				}
			} catch (InterruptedException e) {
				logger.error("异常:", e);
			}
		}
		logger.info("抢购线程结束");
	}
}
