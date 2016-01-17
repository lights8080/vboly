package com.lihp.process.vboly;

import java.io.Reader;
import java.io.StringReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.util.NameValuePair;
import com.lihp.base.BaseProcess;
import com.lihp.common.codec.UnicodeConverter;
import com.lihp.common.date.DateUtil;
import com.lihp.constant.VbolyConstant;
import com.lihp.po.vboly.VBolyPo;

public class AllVbolyProcess extends BaseProcess {

	private VBolyPo vBolyPo;

	public AllVbolyProcess() {
	}

	public AllVbolyProcess(VBolyPo vBolyPo) {
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
			requestParameters.add(new NameValuePair("redirect_url",
					"http://www.vboly.com"));
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
	 * 根据页码获得所有商品ID
	 * @param pageindex
	 * @return
	 */
	private String [] getItemidsByPageindex(int pageindex) {
		logger.debug("获得所有商品ID. pageIndex:"+pageindex);
		String [] ids = null;
		Set<String> idSet = new HashSet<String>();
		StringBuffer strBuf = new StringBuffer();
		strBuf.append("http://www.vboly.com/list_0_0_"+pageindex+"_99.html");
		WebClient webClient = this.getWebClient();
		try {
			URL url = new URL(strBuf.toString());
			WebRequest request = new WebRequest(url, HttpMethod.GET);
			HtmlPage page = webClient.getPage(request);
			String context = page.getWebResponse().getContentAsString();
			Pattern p = Pattern.compile("http://detail.vboly.com/(.*?)\\.html");
			Matcher m = p.matcher(context);
			while (m.find()) {
				idSet.add(m.group(1));
			}
		} catch (Exception e) {
			logger.error("异常:", e);
		} finally {
			this.getWebClientManager().bindWebClient(webClient);
		}
		ids = idSet.toArray(new String[idSet.size()]);
		logger.debug("获得ID信息:"+ids);
		return ids;
	}

	/**
	 * 获得全部商品总页数
	 * @return
	 */
	private int getSumPage() {
		Integer pageIndex = 1;
		StringBuffer strBuf = new StringBuffer();
		strBuf.append("http://www.vboly.com/list.html");
		WebClient webClient = this.getWebClient();
		try {
			URL url = new URL(strBuf.toString());
			WebRequest request = new WebRequest(url, HttpMethod.GET);
			HtmlPage page = webClient.getPage(request);
			String context = page.getWebResponse().getContentAsString();
			Pattern p = Pattern.compile("共 (\\d+) 页");
			Matcher m = p.matcher(context);
			String group = "1";
			while (m.find()) {
				group = m.group(1);
			}
			pageIndex = Integer.parseInt(group);
		} catch (Exception e) {
			logger.error("异常:", e);
			e.printStackTrace();
		} finally {
			this.getWebClientManager().bindWebClient(webClient);
		}
		logger.debug("所有商品总页数:"+pageIndex);
		consoleLogger.debug("获得商品总页数:"+pageIndex);
		return pageIndex;
	}

	/**
	 * 刷新获得商品剩余时间
	 * @return
	 */
	public int reloadItemTimes() {
		logger.debug("刷新商品-开始");
		int s = 600;
		long beginT = System.currentTimeMillis();
		//获得总页数
		int sumPage = this.getSumPage();
		for (int i = 1; i <= sumPage; i++) {
			String[] ids = this.getItemidsByPageindex(i);
			consoleLogger.info("page:{},商品IDS:{}",i,ids);
			for (String id : ids) {
				StringBuffer strBuf = new StringBuffer();
				strBuf.append("http://detail.vboly.com/");
				strBuf.append(id);
				strBuf.append(".html");
				
				WebClient webClient = this.getWebClient();
				try {
					URL url = new URL(strBuf.toString());
					logger.debug("URL:{}", url.toString());
					WebRequest request = new WebRequest(url, HttpMethod.GET);
					// 设置Request Header Referer
					request.setAdditionalHeader("Referer", strBuf.toString());
					HtmlPage page = webClient.getPage(request);
					String context = page.getWebResponse().getContentAsString();
					boolean isWait = true;
					while (isWait) {
						if (StringUtils.isEmpty(page.getTitleText())) {
							logger.debug("refresh...");
							page = webClient.getPage(request);
							context = page.getWebResponse().getContentAsString();
						} else {
							isWait = false;
						}
					}
					
					logger.debug("title:{},context:{}", page.getTitleText(),
							replaceWithBlank(context));
					String desc = "";
					Integer second = 0;
					if (context.indexOf(VbolyConstant.TEXT_BUYING) != -1) {// 我要抢购
						desc = VbolyConstant.TEXT_BUYING;
						boolean isbuy = this.buy(id);
						if(!isbuy){
							this.buy(id);
						}
					} else {
						desc = VbolyConstant.TEXT_END;
					}
					consoleLogger.info("刷新商品ID:{},状态:{},剩余时间：{}", id,
							desc, DateUtil.millisToStr(second));
					logger.info("刷新商品ID:{},状态:{},剩余时间：{}", id, desc,
							DateUtil.millisToStr(second));
				} catch (FailingHttpStatusCodeException e) {
					logger.error("异常:", e);
					consoleLogger.error("刷新商品ID:{},发生错误:{}", id,
							e.getMessage());
				} catch (Exception e) {
					logger.error("异常:", e);
					consoleLogger.error("刷新商品ID:{},发生错误:{}", id,
							e.getMessage());
				} finally {
					this.getWebClientManager().bindWebClient(webClient);
				}
			}
		}
		logger.debug("刷新商品-结束 耗时{}毫秒",System.currentTimeMillis() - beginT);
		return s;
	}

	@Override
	public boolean buy() {
		return false;
	}
	
	public boolean buy(String id) {
		logger.debug("{} 抢购-开始", id);
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
					.add(new NameValuePair("gid", id));
			request.setRequestParameters(requestParameters);

			Page page = webClient.getPage(request);
			String context = page.getWebResponse().getContentAsString();
			logger.info("gid:{},context:{}", id, replaceWithBlank(context));
			// logger.debug("context:{}", context);
			String[] parseXml = this.xmlParse(context);
			status = parseXml[0];
			String message = parseXml[1];
			consoleLogger.info("抢购商品ID:{}, 状态:{}, 描述:{}", id,
					status, UnicodeConverter.unicodeToUtf8(message));
			if ("true".equals(status)) {
				logger.debug("{} 抢购成功", id);
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
		logger.debug("{} 抢购-结束", id);
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
		if (msg.contains(VbolyConstant.TEXT_BUY_SUCCESS_1)
				|| msg.contains(VbolyConstant.TEXT_BUY_SUCCESS_2)) {
			status = "true";
		}
		return new String[] { status, msg };
	}

	public String replaceWithBlank(String html) {
		Pattern p = Pattern.compile("\t|\r|\n");
		Matcher m = p.matcher(html);
		String finishedReplaceStr = m.replaceAll("");
		return finishedReplaceStr;
	}
}
