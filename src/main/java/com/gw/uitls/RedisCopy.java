package com.gw.uitls;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.log4j.Logger;

import redis.clients.jedis.Jedis;

public class RedisCopy {
	private Logger logger = Logger.getLogger(RedisCopy.class);
	private Jedis jedisSrc;
	private Jedis jedisDes;
	
	

	
	/**
	 * constructor
	 */
	public RedisCopy() {
		// TODO Auto-generated constructor stub
		jedisSrc = new Jedis("10.15.144.81", 10012);

		jedisDes = new Jedis("10.15.107.117", 19000);
	}
	
	/**
	 * @Description 遍历参数指定key pattern
	 * @param key
	 * @return_type void
	 * @author Eason
	 * @date 2017年9月18日 上午10:55:45  
	 */
	public void travelKey(String pattern) {
		Set<String> set =jedisSrc.keys(pattern);
		if (set.isEmpty()) {
			logger.info("no keys like " + pattern);
		}else {
			logger.info("the total of keys like "+ pattern +" is " + set.size());
			for (String strKey : set) {
				switch (jedisSrc.type(strKey)) {
				case "string":
					System.out.println(strKey + ":string");
					logger.info(strKey + ":string");
//					jedisDes.del(strKey);
					copyString(strKey);
					break;
				case "hash":
					copyHash(strKey);
					System.out.println(strKey + ":hash");
					break;
				case "list":
					System.out.println(strKey + ":list");
					break;
				case "set":
					System.out.println(strKey + ":set");
					break;
				case "sortedset":
					System.out.println(strKey + ":sortedset");
					break;

				default:
					break;
				}
			}
		}
	}
	
	/**
	 * @Description TODO
	 * @param key
	 * @return_type void
	 * @author Eason
	 * @date 2017年9月18日 下午12:22:18  
	 */
	public void copyString(String key) {
		String strValue = jedisSrc.get(key);
		jedisDes.del(key);
		jedisDes.set(key, strValue);
		//验证两redis存入的value是否一样，从client看，存入的值有区别
//		System.out.println(jedisSrc.get(key).equals(strValue));
	}
	
	public void copyHash(String key) {
		Map<String, String> mapSrc = jedisSrc.hgetAll(key);
		for(Map.Entry<String, String> entry : mapSrc.entrySet()){
			jedisDes.hset(key, entry.getKey(), entry.getValue());
		}
	}
	
	public void scanKeys() {
//		jedisDes.hscan(key, cursor);
	}
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		RedisCopy redisCopy = new RedisCopy();

		redisCopy.travelKey("c8*");
		redisCopy.travelKey("c9*");
		redisCopy.travelKey("d0*");
		redisCopy.travelKey("d1*");
		redisCopy.travelKey("d2*");
		redisCopy.travelKey("d3*");
		redisCopy.travelKey("d4*");
		redisCopy.travelKey("d5*");
		redisCopy.travelKey("d6*");
		redisCopy.travelKey("d7*");
		redisCopy.travelKey("d8*");
		redisCopy.travelKey("d9*");
	}

}
