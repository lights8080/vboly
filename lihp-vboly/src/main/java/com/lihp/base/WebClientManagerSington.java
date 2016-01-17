package com.lihp.base;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.util.Cookie;

public class WebClientManagerSington {

	private WebClient webClient = new WebClient(BrowserVersion.CHROME);
	private List<Cookie> cookies = new ArrayList<Cookie>();

	private static WebClientManagerSington wClientManager = null;
	private boolean isLogin;

	private WebClientManagerSington() {
		webClient.getOptions().setCssEnabled(true);
		webClient.getOptions().setJavaScriptEnabled(false);
		webClient.getOptions().setThrowExceptionOnScriptError(false);
		webClient.getOptions().setThrowExceptionOnFailingStatusCode(true);
	}

	public synchronized static WebClientManagerSington getInstance() {
		if (wClientManager == null) {
			wClientManager = new WebClientManagerSington();
		}
		return wClientManager;
	}

	public WebClient getWebClient() {
		return webClient;
	}

	public void setWebClient(WebClient webClient) {
		this.webClient = webClient;
	}

	public List<Cookie> getCookies() {
		return this.cookies;
	}
	
	public synchronized void bindCookies() {
		this.cookies.clear();
		Set<Cookie> cookies = webClient.getCookieManager().getCookies();
		for (Cookie cookie : cookies) {
			this.cookies.add(cookie);
		}
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
	public boolean isLogin(){
		return isLogin;
	}
}
