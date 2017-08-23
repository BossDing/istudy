package com.xiaoyetan;

import com.jfinal.kit.PropKit;

import java.util.HashMap;
import java.util.Map;

/**
 * 常量配置 读取配置文件类
 */
public class Const {

	
	/**
	 * 保存全局属性值
	 */
	private static Map<String, String> map = new HashMap<String,String>();
	
	/**
	 * 获取配置
	 */
	public static String getValue(String key) {
		String value = PropKit.use("config.properties").get(key);
		return value;
	}

}
