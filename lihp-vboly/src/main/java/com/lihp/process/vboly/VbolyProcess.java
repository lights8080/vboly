package com.lihp.process.vboly;

import java.io.Reader;
import java.io.StringReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.HttpMethod;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.util.NameValuePair;
import com.lihp.base.BaseProcess;
import com.lihp.common.codec.UnicodeConverter;
import com.lihp.common.date.DateUtil;
import com.lihp.constant.VbolyConstant;
import com.lihp.po.vboly.VBolyPo;
import com.lihp.utils.VbolyUtil;

public class VbolyProcess extends BaseProcess {

	private VBolyPo vBolyPo;

	public VbolyProcess() {
	}

	public VbolyProcess(VBolyPo vBolyPo) {
		this.vBolyPo = vBolyPo;
	}

	@Override
	public boolean login() {
		boolean b = false;
		consoleLogger.info("正在登陆vboly...");
		long beginT = System.currentTimeMillis();
		logger.info("登录-开始");
		HtmlPage page = null;
		WebClient webClient = this.getWebClient();
		try {
			URL url = new URL("http://login.vboly.com/Login");
			logger.debug("URL:{}", url.toString());
			WebRequest request = new WebRequest(url, HttpMethod.POST);
			List<NameValuePair> requestParameters = new ArrayList<NameValuePair>();
			requestParameters.add(new NameValuePair("username", vBolyPo
					.getUsername()));
			requestParameters.add(new NameValuePair("password", vBolyPo
					.getPwd()));
			requestParameters.add(new NameValuePair("redirect_url", "http://www.vboly.com"));
			request.setRequestParameters(requestParameters);
			request.setCharset("UTF-8");
			page = webClient.getPage(request);
			logger.debug("context:{}", page.getTitleText());
			if (page.asXml().indexOf("登录成功") != -1) {
				consoleLogger.info("登录成功");
				logger.info("登录成功");
				this.bindMyCookies(webClient);
				String authCookie = this.getWebClientManager().getCookie(
						"aMjY_f810_auth");
				this.getWebClientManager().setAuthCookie(authCookie);
				logger.debug("autoCookie:{}", authCookie);
				consoleLogger.info("autoCookie:{}", authCookie);
				b = true;
			} else {
				consoleLogger.info("登录失败");
				logger.info("登录失败");
				System.exit(0);
			}
		} catch (Exception e) {
			logger.error("异常:", e);
			consoleLogger.error("发生错误");
			System.exit(0);
		} finally {
			this.getWebClientManager().bindWebClient(webClient);
		}
		logger.info("登录-结束 耗时 {}毫秒", System.currentTimeMillis() - beginT);
		return b;
	}

	/**
	 * 刷新获得商品剩余时间
	 * 
	 * @return
	 */
	public int reloadItemTimes() {
		long beginT = System.currentTimeMillis();
		logger.debug("{} 刷新商品-开始", vBolyPo.getItemId());
		StringBuffer strBuf = new StringBuffer();
		strBuf.append("http://detail.vboly.com/");
		strBuf.append(vBolyPo.getItemId());
		strBuf.append(".html");
		int s = VbolyConstant.COMMON_STATE_HOPE;
		WebClient webClient = this.getWebClient();
		try {
			URL url = new URL(strBuf.toString());
			logger.debug("URL:{}", url.toString());
			WebRequest request = new WebRequest(url, HttpMethod.GET);
			//设置Request Header Referer
			request.setAdditionalHeader("Referer", strBuf.toString());
			HtmlPage page = webClient.getPage(request);

			String context = page.getWebResponse().getContentAsString();
//			logger.info("context：{}",context);
			
			boolean isWait = true;
			while(isWait){
				if(StringUtils.isEmpty(page.getTitleText())){
					logger.debug("refresh...");
//					Thread.sleep(100);
					page = webClient.getPage(request);
					context = page.getWebResponse().getContentAsString();
				}else{
					isWait = false;
				}
			}
			
			logger.debug("title:{},context:{}", page.getTitleText(),replaceWithBlank(context));
			String desc = "";
			Integer second = 0;
			if (context.indexOf(VbolyConstant.TEXT_HOPE) != -1) {// 还有机会
				s = VbolyConstant.COMMON_STATE_HOPE;
				desc = VbolyConstant.TEXT_HOPE;
			} else if (context.indexOf(VbolyConstant.TEXT_BUYING) != -1) {// 我要抢购
				s = VbolyConstant.COMMON_STATE_BUYING;
				desc = VbolyConstant.TEXT_BUYING;
				String sign = "";
				String hidstamp = "";
				DomElement domLinkBuy = page.getElementById("link_buy");
				if(domLinkBuy!=null){
					sign = searchValue(domLinkBuy.getAttribute("onclick"), "'", "'");
				}
				DomElement docHidstamp = page.getElementById("hidstamp");
				if(docHidstamp!=null){
					hidstamp = docHidstamp.getAttribute("value");
				}
				logger.info("postbuy参数. sign:{},hidstamp:{}",sign,hidstamp);
				//封装下单需要的参数
				if(sign!=null && !"".equals(sign)){
					VbolyUtil.getInstance().setValue("sign"+vBolyPo.getItemId(), sign);
				}
				if(hidstamp!=null && !"".equals(hidstamp)){
					VbolyUtil.getInstance().setValue("hidstamp"+vBolyPo.getItemId(), hidstamp);
				}
			} else if (context.indexOf(VbolyConstant.TEXT_NOBUY) != -1 || context.indexOf(VbolyConstant.TEXT_NOBUY_1) != -1) {// 待上线|即将上线
				DomElement lasttime = page.getElementById("lasttime");
				//如果没有剩余时间，默认1分钟重新扫描
				if(lasttime==null){
					s = VbolyConstant.DEF_INTERVAL_SECOND;
					second = s;
				}else{
					String lasttimeVal = lasttime.getAttribute("value");
					s = Integer.valueOf(lasttimeVal);
					second = s;
				}
				desc = VbolyConstant.TEXT_NOBUY;
			} else if (context.indexOf(VbolyConstant.TEXT_ADD) != -1) {// 待追加
				DomElement lasttime = page.getElementById("lasttime");
				//如果没有剩余时间，默认1分钟重新扫描
				if(lasttime==null){
					s = VbolyConstant.DEF_INTERVAL_SECOND;
					second = s;
				}else{
					String lasttimeVal = lasttime.getAttribute("value");
					s = Integer.valueOf(lasttimeVal);
					second = s;
				}
				desc = VbolyConstant.TEXT_ADD;
			} else if (context.indexOf(VbolyConstant.TEXT_BUYED) != -1 || context.indexOf(VbolyConstant.TEXT_BUYED_1) != -1) {// 已抢购|填写订单号
				s = VbolyConstant.COMMON_STATE_BUYED;
				desc = VbolyConstant.TEXT_BUYED;
				logger.debug("{} 线程结束", vBolyPo.getItemId());
			} else if (context.indexOf(VbolyConstant.TEXT_END) != -1 || context.indexOf(VbolyConstant.TEXT_END_1) != -1) {// 已结束|活动结束
				s = VbolyConstant.COMMON_STATE_END;
				desc = VbolyConstant.TEXT_END;
				logger.debug("{} 线程结束", vBolyPo.getItemId());
			} else if (context.indexOf(VbolyConstant.TEXT_OVER) != -1) {// 已抢完
				DomElement lasttime = page.getElementById("lasttime");
				//如果没有剩余时间，默认1分钟重新扫描
				if(lasttime==null){
					second = VbolyConstant.DEF_INTERVAL_SECOND;
				}else{
					String lasttimeVal = lasttime.getAttribute("value");
					second = Integer.valueOf(lasttimeVal);
				}
				s = VbolyConstant.COMMON_STATE_OVER;
				desc = VbolyConstant.TEXT_OVER;
			}else if(context.indexOf(VbolyConstant.TEXT_SHIELD) != -1){ //已屏蔽
				s = VbolyConstant.DEF_INTERVAL_SECOND;
				desc = VbolyConstant.TEXT_SHIELD;
			} else {
				s = VbolyConstant.COMMON_STATE_HOPE;
			}
			consoleLogger.info("刷新商品ID:{},状态:{},剩余时间：{}", vBolyPo.getItemId(),
					desc, DateUtil.millisToStr(second));
			logger.info("刷新商品ID:{},状态:{},剩余时间：{}", vBolyPo.getItemId(), desc,
					DateUtil.millisToStr(second));
		} catch (FailingHttpStatusCodeException e) {
			logger.error("异常:", e);
			consoleLogger.error("刷新商品ID:{},发生错误:{}",vBolyPo.getItemId(),e.getMessage());
			// 如果发生 503 Service Unavailable 错误多停两秒继续刷新
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		} catch (Exception e) {
			logger.error("异常:", e);
			consoleLogger.error("刷新商品ID:{},发生错误:{}",vBolyPo.getItemId(),e.getMessage());
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		} finally {
			this.getWebClientManager().bindWebClient(webClient);
		}
		logger.debug("{} 刷新商品-结束 耗时{}毫秒", vBolyPo.getItemId(),
				System.currentTimeMillis() - beginT);
		return s;
	}

	@Override
	public boolean buy() {
		String hidstamp = VbolyUtil.getInstance().getValue("hidstamp"+vBolyPo.getItemId());
		String sign = VbolyUtil.getInstance().getValue("sign"+vBolyPo.getItemId());
		
		logger.debug("{} 抢购-开始 hidstamp:{},sign:{}", vBolyPo.getItemId(),hidstamp,sign);
		if(StringUtils.isBlank(hidstamp) || StringUtils.isBlank(sign)){
			this.skipRebuy();
			return false;
		}
		StringBuffer strBuf = new StringBuffer();
		strBuf.append("http://detail.vboly.com/join/postbuy");
		String status = "";
		WebClient webClient = this.getWebClient();
		boolean b = false;
		try {
			URL url = new URL(strBuf.toString());
			logger.debug("URL:{}", url.toString());
			WebRequest request = new WebRequest(url, HttpMethod.POST);
			List<NameValuePair> requestParameters = new ArrayList<NameValuePair>();
			requestParameters
			.add(new NameValuePair("gid", vBolyPo.getItemId()));
			
			
			requestParameters
			.add(new NameValuePair("stamp", hidstamp));
			requestParameters
					.add(new NameValuePair("sign", sign));
			request.setRequestParameters(requestParameters);
			
			Page page = webClient.getPage(request);
			String context = page.getWebResponse().getContentAsString();
			logger.info("gid:{},stamp:{},sign:{}. context:{}",vBolyPo.getItemId(),hidstamp,sign,context);
			// logger.debug("context:{}", context);
			String[] parseXml = this.xmlParse(context);
			status = parseXml[0];
			String message = parseXml[1];
			consoleLogger.info("抢购商品ID:{}, 状态:{}, 描述:{}", vBolyPo.getItemId(),
					status, UnicodeConverter.unicodeToUtf8(message));
			if ("true".equals(status)) {
				logger.debug("{} 抢购成功", vBolyPo.getItemId());
				b = true;
			} else {
				b = false;
			}
		} catch (FailingHttpStatusCodeException e) {
			logger.error("异常:", e);
			consoleLogger.error("发生错误");
		} catch (Exception e) {
			logger.error("异常:", e);
			consoleLogger.error("发生错误");
		} finally {
			this.getWebClientManager().bindWebClient(webClient);
		}
		logger.debug("{} 抢购-结束", vBolyPo.getItemId());
		return b;
	}

	// 解析xml文件
	public String[] xmlParse(String xmlStr) throws Exception {
		Reader in = new StringReader(xmlStr);
		Document doc = (new SAXBuilder()).build(in);
		Element root = doc.getRootElement();// 获得根节点
		Element buyMsgBox = root.getChildren("div").get(0);
		Element h3 = buyMsgBox.getChild("h3");
		String buyMsgBoxClass = buyMsgBox.getAttributeValue("class");
		String status = "false";
		String msg = "";
		if (buyMsgBoxClass.contains("buy_msg_box")) {
			status = "true";
			msg = replaceWithBlank(h3.getText().trim());
		} else if (buyMsgBoxClass.contains("buy_msg_err_box")) {
			msg = replaceWithBlank(h3.getText().trim());
		}
		if(msg.contains(VbolyConstant.TEXT_BUY_SUCCESS_1) || msg.contains(VbolyConstant.TEXT_BUY_SUCCESS_2)){
			status = "true";
		}
		return new String[] { status, msg };
	}
	
	public String replaceWithBlank(String html){
		Pattern p = Pattern.compile("\t|\r|\n");
		Matcher m = p.matcher(html);
		String finishedReplaceStr = m.replaceAll("");
		return finishedReplaceStr;
	}
	
	/**
	 * 获得value
	 * 
	 * @param context
	 * @param beginStr
	 * @param endStr
	 * @return
	 */
	private String searchValue(String context, String beginStr, String endStr) {
		String substring = context.substring(context.indexOf(beginStr)+1);
		return substring
				.substring(0, substring.indexOf(endStr));
	}
}