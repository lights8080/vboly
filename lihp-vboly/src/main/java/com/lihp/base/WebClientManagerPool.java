package com.lihp.base;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.CookieManager;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.util.Cookie;
import com.lihp.constant.VbolyConstant;

public class WebClientManagerPool {

	private List<WebClient> webClients = new ArrayList<WebClient>();
	private List<Cookie> cookies = new ArrayList<Cookie>();

	private static WebClientManagerPool wClientManager = null;
	private boolean isLogin;

	private static Logger logger = LogManager
			.getLogger(WebClientManagerPool.class);

	private WebClientManagerPool() {
		// webClient.getOptions().setCssEnabled(true);
		// webClient.getOptions().setJavaScriptEnabled(false);
		// webClient.getOptions().setThrowExceptionOnScriptError(false);
		// webClient.getOptions().setThrowExceptionOnFailingStatusCode(true);
		logger.debug("初始化信息");
		WebClient webClient = null;
		for (int i = 0; i < VbolyConstant.DEF_INIT_WEBCLIENT_COUNT; i++) {
			webClient = newWebClient();
			webClients.add(webClient);
		}
		logger.debug("初始化 {} 个WebClient实例成功", webClients.size());

	}

	private WebClient newWebClient() {
		WebClient webClient = new WebClient(BrowserVersion.CHROME);
		webClient.getOptions().setCssEnabled(true);
		webClient.getOptions().setJavaScriptEnabled(false);
		webClient.getOptions().setThrowExceptionOnScriptError(false);
		webClient.getOptions().setThrowExceptionOnFailingStatusCode(true);
		return webClient;
	}

	public synchronized static WebClientManagerPool getInstance() {
		if (wClientManager == null) {
			wClientManager = new WebClientManagerPool();
		}
		return wClientManager;
	}

	public synchronized WebClient getWebClient() {

		WebClient webClient = null;
		if (webClients.size() > 0) {
			webClient = webClients.get(0);
			webClients.remove(0);
		} else {
			webClient = newWebClient();
			logger.debug("新增1个WebClient实例成功");
			if (cookies != null && cookies.size() > 0) {
				// 手动设置cookie
				CookieManager cookieManager = webClient.getCookieManager();
				for (Cookie cookie : cookies) {
					// logger.debug("Domain={},{}={}", cookie.getDomain(),
					// cookie.getName(), cookie.getValue());
					cookieManager.addCookie(cookie);
				}
				logger.debug("绑定Cookie成功");
			}

		}
		return webClient;
	}

	public List<Cookie> getCookies() {
		return this.cookies;
	}

	public void bindWebClient(WebClient webClient) {
		webClients.add(webClient);
	}

	public synchronized void bindCookies(WebClient webClient) {

		logger.debug("绑定Cookie信息到所以HttpClient");
		cookies.clear();
		Set<Cookie> cookieArr = webClient.getCookieManager().getCookies();
		for (Cookie cookie : cookieArr) {
			// logger.debug("Domain={},{}={}", cookie.getDomain(),
			// cookie.getName(), cookie.getValue());
			cookies.add(cookie);
		}
		for (WebClient hc : webClients) {
			// 手动设置cookie
			CookieManager cookieManager = hc.getCookieManager();
			for (Cookie cookie : cookieArr) {
				cookieManager.addCookie(cookie);
			}
		}
		logger.debug("绑定Cookie成功 CookieSize:{}", cookies.size());
	}

	public String getCookie(String cookieName) {
		if (cookies != null && cookies.size() > 0) {
			for (Cookie cookie : cookies) {
				if (cookieName.equals(cookie.getName())) {
					return cookie.getValue();
				}
			}
		}
		return "";
	}

	public void setLogin(boolean isLogin) {
		this.isLogin = isLogin;
	}

	public boolean isLogin() {
		return isLogin;
	}
}
