package com.gw.redis.operator;

import redis.clients.jedis.Jedis;

public class TestPublish {

	public static void main(String[] args) {
        try {
        	Jedis jedis = new Jedis("10.15.107.145",7000);  
            jedis.publish("redisChatTest", "publish1");  
			Thread.sleep(5000);
	        jedis.publish("redisChatTest", "publish2");  
	        Thread.sleep(5000);  
	        jedis.publish("redisChatTest", "byebye"); 
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  

	}

}
