package com.gw.redis.operator;

import com.gw.redis.RedisMsgPubSubListener;

import redis.clients.jedis.Jedis;

/**
 * @Description 订阅channel
 * @author Eason
 * 
 */
public class TestSubscribe {
	/**
	 * @Description TODO
	 * @param args
	 * @return_type void
	 * @author Eason
	 * @date 2017年11月6日 下午1:28:04  
	 */
	public static void main(String args[]) {
        Jedis jedis = new Jedis("10.15.107.145",7000);  
        RedisMsgPubSubListener listener = new RedisMsgPubSubListener();  
        jedis.subscribe(listener, "redisChatTest");  
        jedis.psubscribe(listener, args);
	}
}
