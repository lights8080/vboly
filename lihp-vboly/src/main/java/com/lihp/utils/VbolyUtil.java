package com.lihp.utils;

import java.util.HashMap;
import java.util.Map;

public class VbolyUtil {

	private static Map<String,String> vbolyMap = new HashMap<String,String>();
	
	private static VbolyUtil vbolyUtil = null;
	private VbolyUtil() {
	}
	
	public static synchronized VbolyUtil getInstance(){
		if(vbolyUtil == null){
			vbolyUtil = new VbolyUtil();
		}
		return vbolyUtil;
	}
	
	public synchronized String getValue(String column){
		return vbolyMap.get(column);
	}
	public synchronized void setValue(String column,String val){
		vbolyMap.put(column,val);
	}
}