package com.lihp.constant;

/**
 * @author lihp
 */
public class VbolyConstant {
	/** 返回前台显示描述信息 */
	public static final String TEXT_HOPE = "还有机会";
	public static final String TEXT_BUYING = "我要抢购";
	public static final String TEXT_NOBUY = "待上线";
	public static final String TEXT_NOBUY_1 = "即將上线";
	public static final String TEXT_ADD = "待追加";
	public static final String TEXT_BUYED = "已抢购";
	public static final String TEXT_END = "已结束";
	public static final String TEXT_END_1 = "活动结束";
	public static final String TEXT_OVER = "已抢完";
	public static final String TEXT_BUYED_1 = "填写订单号";
	public static final String TEXT_SHIELD = "已屏蔽";

	//抢购成功包含的描述信息
	public static final String TEXT_BUY_SUCCESS_1 = "抢购成功";
	public static final String TEXT_BUY_SUCCESS_2 = "已经抢购";

	
	// 默认初始化WebClient个数
	public static final Integer DEF_INIT_WEBCLIENT_COUNT = 5;

	// 抢购阀值（秒） 【越小抢的越早】
	public static final int BUY_MIN_SECOND_DEF = 9;
	// 缓冲时间（秒）【获取商品的响应时间】 小于抢购阀值
	public static final int BUY_BUFFER_SECOND_DEF = 8;

	// 还有机会默认刷新时间间隔（毫秒）
	public static final int HOPE_REFRESH_MILLIS_DEF = 1000;
	// 抢购间隔（毫秒）
	public static final int BUY_INTERVAL_MILLIS_DEF = 200;

	/** 众划算-刷新页面返回状态 */
	// 即将上线
	public static final String ZHONGHS_STATE_NOBUY = "5";
	// 我要抢购
	public static final String ZHONGHS_STATE_BUYING = "20";
	// 还有机会
	public static final String ZHONGHS_STATE_HOPE = "24";
	// 已结束
	public static final String ZHONGHS_STATE_BUYEND = "22";

	/** 程序判断状态 */
	// 已抢购
	public static final int COMMON_STATE_BUYED = -999;
	// 我要抢购
	public static final int COMMON_STATE_BUYING = -998;
	// 还有机会
	public static final int COMMON_STATE_HOPE = -997;
	// 已结束
	public static final int COMMON_STATE_END = -996;
	// 已抢完
	public static final int COMMON_STATE_OVER = -995;
	
	// 待追加|即将上线|下次追加时间|剩余时间。如果没有倒计时时间，默认间隔时间
	public static final int DEF_INTERVAL_SECOND = 120;
}
