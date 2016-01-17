package com.lihp.process.zhonghs;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gargoylesoftware.htmlunit.HttpMethod;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.util.NameValuePair;
import com.lihp.base.BaseProcess;
import com.lihp.common.CommonUtils;
import com.lihp.common.codec.UnicodeConverter;
import com.lihp.common.date.DateUtil;
import com.lihp.constant.VbolyConstant;
import com.lihp.po.zhonghs.TradeRespPo;
import com.lihp.po.zhonghs.ZhonghsPo;

public class ZhonghsProcess extends BaseProcess {
	private ZhonghsPo zhonghsPo;

	public ZhonghsProcess() {
	}

	public ZhonghsProcess(ZhonghsPo zhonghsPo) {
		this.zhonghsPo = zhonghsPo;
	}

	@Override
	public boolean login() {

		boolean b = false;
		consoleLogger.info("正在登陆zhonghs...");
		long beginT = System.currentTimeMillis();
		logger.info("登录-开始");
		WebClient webClient = this.getWebClient();
		try {
			URL url = new URL("http://login.zhonghuasuan.com/tologin");
			WebRequest request = new WebRequest(url, HttpMethod.POST);
			Page page = webClient.getPage(request);
			List<NameValuePair> requestParameters = new ArrayList<NameValuePair>();
			requestParameters.add(new NameValuePair("account", zhonghsPo
					.getUsername()));
			requestParameters.add(new NameValuePair("password", zhonghsPo
					.getPwd()));
			requestParameters.add(new NameValuePair("to",
					"http://www.zhonghuasuan.com/"));
			requestParameters.add(new NameValuePair("token", CommonUtils
					.getRandomString(32)));
			requestParameters.add(new NameValuePair("remember", "1"));
			request.setRequestParameters(requestParameters);
			page = webClient.getPage(request);
			String context = page.getWebResponse().getContentAsString();
			logger.debug("context:{}", context);
			JSONObject parseObject = JSON.parseObject(context);
			String status = (String) parseObject.get("state");
			if ("SUCCESS".equals(status)) {
				consoleLogger.info("登录成功");
				logger.info("登录成功");
				this.bindMyCookies(webClient);
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

	@Override
	public int reloadItemTimes() {
		long beginT = System.currentTimeMillis();
		logger.debug("{} 刷新商品-开始", zhonghsPo.getItemId());
		zhonghsPo.setItemId(zhonghsPo.getItemId());
		StringBuffer strBuf = new StringBuffer();
		strBuf.append("http://detail.zhonghuasuan.com/");
		strBuf.append(zhonghsPo.getItemId());
		strBuf.append(".html");
		int s = VbolyConstant.COMMON_STATE_HOPE;
		WebClient webClient = this.getWebClient();
		try {
			URL url = new URL(strBuf.toString());
			logger.debug("URL1:{}", url.toString());
			WebRequest request = new WebRequest(url, HttpMethod.GET);
			HtmlPage page = webClient.getPage(request);
			String context = page.getWebResponse().getContentAsString();
			logger.debug("context1:{}", page.getTitleText());
			String buy_sign = searchValue(context, "var buy_sign = '", "';");
			String buy_sign_time = searchValue(context,
					"var buy_sign_time = '", "';");
			zhonghsPo.setSign(buy_sign);
			zhonghsPo.setT(buy_sign_time);
			logger.debug("buy_sign:{},buy_sign_time:{}", buy_sign,
					buy_sign_time);
			strBuf = new StringBuffer();
			strBuf.append("http://detail.zhonghuasuan.com/data/");
			strBuf.append(zhonghsPo.getItemId());
			strBuf.append("?sign=");
			strBuf.append(zhonghsPo.getSign());
			strBuf.append("&t=");
			strBuf.append(zhonghsPo.getT());
			url = new URL(strBuf.toString());
			logger.debug("URL2:{}", url.toString());
			request = new WebRequest(url, HttpMethod.GET);
			page = webClient.getPage(request);
			context = searchValue(page.getWebResponse().getContentAsString(),
					"var goods = ", ";");
			logger.debug("context2:{}", context);
			JSONObject parseObject = JSON.parseObject(context);
			String state = (String) parseObject.get("state");
			logger.debug("state:{}", state);
			String desc =state;
			Integer second = 0;
			if (VbolyConstant.ZHONGHS_STATE_HOPE.equals(state)) {//还有机会
				s = VbolyConstant.COMMON_STATE_HOPE;
				desc = VbolyConstant.TEXT_HOPE;
			} else if (VbolyConstant.ZHONGHS_STATE_BUYING.equals(state)) {//我要抢购
				s = VbolyConstant.COMMON_STATE_BUYING;
				desc = VbolyConstant.TEXT_BUYING;
			} else if (VbolyConstant.ZHONGHS_STATE_NOBUY.equals(state)) {//即将上线
				Integer nowtime = (Integer) parseObject.get("nowtime");
				Object objstarttime = parseObject.get("starttime");
				Integer starttime = 0 ;
				if(objstarttime instanceof String){
					starttime = Integer.valueOf((String) parseObject.get("starttime"));
				}else if(objstarttime instanceof Integer){
					starttime = (Integer) parseObject.get("starttime");
				}
				desc = VbolyConstant.TEXT_NOBUY;
				second = starttime - nowtime;
				s = second;
			} else if (VbolyConstant.ZHONGHS_STATE_BUYEND.equals(state)) {//已结束
				s = VbolyConstant.COMMON_STATE_END;
				desc = VbolyConstant.TEXT_END;
				logger.debug("{} 线程结束", zhonghsPo.getItemId());
			} else {
				s = VbolyConstant.COMMON_STATE_HOPE;
			}
			consoleLogger.info("刷新商品ID:{},状态:{},剩余时间：{}", zhonghsPo.getItemId(),
					desc,second);
			logger.info("刷新商品ID:{},状态:{},剩余时间：{}", zhonghsPo.getItemId(), desc,second);
		} catch (Exception e) {
			logger.error("异常:", e);
			consoleLogger.error("发生错误");
		} finally {
			this.getWebClientManager().bindWebClient(webClient);
		}
		logger.debug("{} 刷新商品-结束 耗时{}毫秒", zhonghsPo.getItemId(),
				System.currentTimeMillis() - beginT);
		return s;
	}

	@Override
	public boolean buy() {
		logger.debug("{} 抢购-开始", zhonghsPo.getItemId());
		StringBuffer strBuf = new StringBuffer();
		strBuf.append("http://trade.zhonghuasuan.com/?gid=");
		strBuf.append(zhonghsPo.getItemId());
		strBuf.append("&sign=");
		strBuf.append(zhonghsPo.getSign());
		strBuf.append("&t=");
		strBuf.append(zhonghsPo.getT());
		strBuf.append("&callback=_jqjsp");
		strBuf.append("&_");
		strBuf.append(DateUtil.getMillis(new Date()));
		boolean status = false;
		WebClient webClient = this.getWebClient();
		try {
			URL url = new URL(strBuf.toString());
			logger.debug("URL:{}", url.toString());
			WebRequest request = new WebRequest(url, HttpMethod.GET);
			HtmlPage page = webClient.getPage(request);
			String context = page.getWebResponse().getContentAsString();
			context = UnicodeConverter.unicodeToUtf8(searchValue(context,
					"_jqjsp(", ");"));
			logger.debug("context:{}", context);
			TradeRespPo tradeRespPo = JSON.parseObject(context,
					TradeRespPo.class);
			// status = true;
			status = tradeRespPo.isSuccess();
			String desc = "";
			String errcode = "";
			if (!status) {
				String info = tradeRespPo.getInfo();
				JSONObject parseObject = JSON.parseObject(info);
				desc = (String) parseObject.get("errtxt");
				errcode = (String) parseObject.get("errcode");
				if (errcode.equals("FREQUENCY")) {
					// 您抢购过于频繁，请先休息一下
					desc="您抢购过于频繁，请先休息一下";
				} else if (errcode.equals("STATE_ERROR")) {
					// 当前状态下无法进行抢购
					desc="当前状态下无法进行抢购";
				} else if (errcode.equals("BUY_REPEAT")) {
					// 禁止重复抢购
					desc="禁止重复抢购";
					status = true;
				}
			} else {
				// 抢购成功
				desc = "抢购成功.newOid："+tradeRespPo.getNew_oid();
			}
			consoleLogger.info("抢购商品ID:{}, 状态:{}, 描述:{}",
					zhonghsPo.getItemId(), status, desc);
			logger.info("抢购商品ID:{}, 状态:{}, 描述:{}",
					zhonghsPo.getItemId(), status, desc);
		} catch (Exception e) {
			logger.error("异常:", e);
			consoleLogger.error("发生错误");
			System.exit(0);
		} finally {
			this.getWebClientManager().bindWebClient(webClient);
		}
		logger.debug("{} 抢购-结束", zhonghsPo.getItemId());
		return status;
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

		String substring = context.substring(context.indexOf(beginStr));
		return substring
				.substring(beginStr.length(), substring.indexOf(endStr));
	}

}
