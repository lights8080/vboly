package com.lihp.t;

import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.lihp.po.vboly.VBolyPo;
import com.lihp.po.zhonghs.ZhonghsPo;
import com.lihp.process.vboly.VbolyProcess;
import com.lihp.process.zhonghs.ZhonghsProcess;
public class T {
	protected static Logger consoleLogger = LogManager.getLogger("LOG_CONSOLE");
	private static Scanner s = null;
	public static void main(String[] args) {
		String type = "";
		String username="";
		String pwd="";
		String itemIds="";
		if(args==null || args.length==0){
			s = new Scanner(System.in);
			System.out.println("选择环境(1-vboly,2-zhonghs):");
			type = s.next();
			System.out.println("用户名:");
			username = s.next();
			System.out.println("密码:");
			pwd = s.next();
			System.out.println("商品ID(多个逗号分隔):");
			itemIds = s.next();
		}else{
			if(args.length>=4){
				type=args[0];
				itemIds=args[3];
				username=args[1];
				pwd=args[2];
			}else{
				consoleLogger.info("参数错误!!!");
				System.exit(0);
			}
		}
		if("".equals(type) || "".equals(username) || "".equals(pwd) || "".equals(itemIds)){
			consoleLogger.info("参数错误!!!");
			System.exit(0);
		}

		String[] ids = itemIds.split(",");
		if("1".equals(type)){
			for (String id : ids) {
				VBolyPo po = new VBolyPo();
				po.setItemId(id);
				po.setUsername(username);
				po.setPwd(pwd);
				VbolyProcess v = new VbolyProcess(po);
				v.execute();
			}
		}else if("2".equals(type)){
			for (String id : ids) {
				ZhonghsPo po = new ZhonghsPo();
				po.setItemId(id);
				po.setUsername(username);
				po.setPwd(pwd);
				ZhonghsProcess v = new ZhonghsProcess(po);
				v.execute();
			}
		}else{
			System.out.println("请正确选择环境!!!");
			consoleLogger.info("请正确选择环境!!!");
		}
	}
}
