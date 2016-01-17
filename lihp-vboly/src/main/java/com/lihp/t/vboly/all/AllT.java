package com.lihp.t.vboly.all;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.lihp.po.vboly.VBolyPo;
import com.lihp.process.vboly.AllVbolyProcess;
import com.lihp.process.vboly.VbolyProcess;

public class AllT {
	protected static Logger consoleLogger = LogManager.getLogger("LOG_CONSOLE");
	private static Scanner s = null;
	public static void main(String[] args) {
		String username="lihp0417";
		String pwd="lihp0417";
		String itemIds="ALL";
//		if(args==null || args.length==0){
//			s = new Scanner(System.in);
//			System.out.println("用户名:");
//			username = s.next();
//			System.out.println("密码:");
//			pwd = s.next();
//			System.out.println("商品ID(多个逗号分隔):");
//			itemIds = s.next();
//		}else{
//			if(args.length>=3){
//				itemIds=args[2];
//				username=args[0];
//				pwd=args[1];
//			}else{
//				consoleLogger.info("参数错误!!!");
//				System.exit(0);
//			}
//		}
		if("".equals(username) || "".equals(pwd) || "".equals(itemIds)){
			consoleLogger.info("参数错误!!!");
			System.exit(0);
		}
		if("ALL".equals(itemIds)){
			VBolyPo po = new VBolyPo();
			po.setItemId("ALL");
			po.setUsername(username);
			po.setPwd(pwd);
			AllVbolyProcess v = new AllVbolyProcess(po);
			v.executeAll();
		}else{
			String[] ids = itemIds.split(",");
			for (String id : ids) {
				VBolyPo po = new VBolyPo();
				po.setItemId(id);
				po.setUsername(username);
				po.setPwd(pwd);
				VbolyProcess v = new VbolyProcess(po);
				v.execute();
			}
		}
	}
	
	 private static void getStrings() {
	        String str = "rrwerqq84461376qqasfdasdfrrwerqq84461377qqasfdasdaa654645aafrrwerqq84461378qqasfdaa654646aaasdfrrwerqq84461379qqasfdasdfrrwerqq84461376qqasfdasdf";
	        Pattern p = Pattern.compile("qq(.*?)qq");
	        Matcher m = p.matcher(str);
	        ArrayList<String> strs = new ArrayList<String>();
	        while (m.find()) {
	            strs.add(m.group(1));            
	        } 
	        for (String s : strs){
	            System.out.println(s);
	        }        
	    }
	
}
