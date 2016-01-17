package com.lihp.t.zhonghs;

import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.lihp.po.zhonghs.ZhonghsPo;
import com.lihp.process.zhonghs.ZhonghsProcess;

public class T {

	protected static Logger consoleLogger = LogManager.getLogger("LOG_CONSOLE");
	private static Scanner s = null;

	public static void main(String[] args) {
		String username = "";
		String pwd = "";
		String itemIds = "";
		if (args == null || args.length == 0) {
			s = new Scanner(System.in);
			System.out.println("用户名:");
			username = s.next();
			System.out.println("密码:");
			pwd = s.next();
			System.out.println("商品ID(多个逗号分隔):");
			itemIds = s.next();
		} else {
			if (args.length >= 3) {
				itemIds = args[2];
				username = args[0];
				pwd = args[1];
			} else {
				consoleLogger.info("参数错误!!!");
				System.exit(0);
			}
		}
		if ("".equals(username) || "".equals(pwd) || "".equals(itemIds)) {
			consoleLogger.info("参数错误!!!");
			System.exit(0);
		}
		String[] ids = itemIds.split(",");
		for (String id : ids) {
			ZhonghsPo po = new ZhonghsPo();
			po.setItemId(id);
			po.setUsername(username);
			po.setPwd(pwd);
			ZhonghsProcess v = new ZhonghsProcess(po);
			v.execute();
		}
	}

}
