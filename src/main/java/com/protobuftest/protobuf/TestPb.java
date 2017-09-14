package com.protobuftest.protobuf;
 
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.List;

import com.dzhyun.proto.Dzhyun;
import com.dzhyun.proto.Dzhyun.BusResponse;
import com.dzhyun.proto.Dzhyun.BusResponse.StackData;
import com.dzhyun.proto.Dzhyun.MiniFrame;
import com.dzhyun.proto.DzhyunStockpool;
import com.dzhyun.proto.DzhyunStockpool.StkPool;
import com.dzhyun.proto.DzhyunStockpool.StkPoolOuput;
import com.dzhyun.proto.DzhyunStockpoolincome;
import com.dzhyun.proto.DzhyunStockpoolincome.StockPoolInfo;
import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import com.protobuftest.protobuf.PersonProbuf;
import com.protobuftest.protobuf.PersonProbuf.Person;
import com.protobuftest.protobuf.PersonProbuf.Person.PhoneNumber;
import com.sun.org.apache.bcel.internal.generic.NEW;

import redis.clients.jedis.Jedis;
 
public class TestPb {
public void test9t0() {
	// TODO Auto-generated method stub
		PersonProbuf.Person.Builder builder = PersonProbuf.Person.newBuilder();
		builder.setEmail("kkk@email.com");
		builder.setId(1);
		builder.setName("TestName");
		builder.addPhone(PersonProbuf.Person.PhoneNumber.newBuilder().setNumber("131111111").setType(PersonProbuf.Person.PhoneType.MOBILE));
		builder.addPhone(PersonProbuf.Person.PhoneNumber.newBuilder().setNumber("011111").setType(PersonProbuf.Person.PhoneType.HOME));
	 
		Person person = builder.build();
		byte[] buf = person.toByteArray();
		
		///////////////
		//个人测试
//		Jedis jedis = new Jedis("10.15.107.145", 7000);
		//云平台
		Jedis jedis = new Jedis("10.15.208.121", 7000);
//		jedis.auth("admin");
//		byte[] key = new String("PERSON2").getBytes();
//		jedis.set("PERSON1", "PERSON1");
//		System.out.println(jedis.get("PERSON1"));
//		jedis.set(key, buf);
//		if (jedis.exists(key)) {
//			System.out.println("KEY = " + key);
//		};
		Iterator<String> keys = jedis.keys("9t0*").iterator();
		while (keys.hasNext()) {
			String string = (String) keys.next();
			System.out.println(string);
//			Iterator<String> iter = jedis.hkeys(string).iterator();  
//		    while (iter.hasNext()) {  
//		        String key1 = iter.next();  
//		        System.out.println(key1 + ":" + jedis.hmget(string, key1));  
//		    } 
		}

		
		
		
		
		//反序列化
//		buf = jedis.get(key);
//		jedis.close();
//		try {
			try {
				BusResponse stackData = Dzhyun.BusResponse.parseFrom(buf);
				stackData.toString();
			} catch (InvalidProtocolBufferException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//			Person person2 = PersonProbuf.Person.parseFrom(buf);
//			System.out.println(person2.getName() + ", " + person2.getEmail());
//			List<PhoneNumber> lstPhones = person2.getPhoneList();
//			for (PhoneNumber phoneNumber : lstPhones) {
//				System.out.println(phoneNumber.getNumber());
//			}
//		} catch (InvalidProtocolBufferException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	// 
//		try {
//			System.out.println(new String(buf, "GB2312"));
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
}

public static void test9s0() throws UnsupportedEncodingException {
	Jedis jedis = new Jedis("10.15.208.121", 7000);
//	System.out.println(jedis.lrange("9s0多周期共振策略", 0, 0));
	try {
		StockPoolInfo stockPoolnfo = DzhyunStockpoolincome.StockPoolInfo.parseFrom(jedis.lrange("9s0多周期共振策略", 0, 0).get(0).getBytes("utf-8"));
		stockPoolnfo.getTotalIncome();
		System.out.println(stockPoolnfo.getName());
		System.out.println(stockPoolnfo.getTotalIncome());
		System.out.println(stockPoolnfo.getRatio());
	} catch (InvalidProtocolBufferException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}finally {
		jedis.close();
	}

}

public static void testAq0() throws IOException {
	@SuppressWarnings("resource")
	Jedis jedis2 = new Jedis("10.15.89.120", 7000);
	System.out.println(jedis2.lrange("Aq0板块热点轮动策略", 1,1).get(0));
//	try {
//		FileWriter fWriter = new FileWriter(new File(".//dat//test.txt"));
//		fWriter.write(jedis2.lrange("Aq0板块热点轮动策略", 1,1).get(0));
//		fWriter.close();
//	} catch (IOException e1) {
//		// TODO Auto-generated catch block
//		e1.printStackTrace();
//	}
	

	
//	System.out.println(jedis2.lrange("Aq0板块热点轮动策略", 1,1).get(0).getBytes("utf-8").length);
	try {
		FileInputStream fi = new FileInputStream(".//dat//test.txt");
		byte[] bb = jedis2.lrange("Aq0板块热点轮动策略", 1, 1).get(0).getBytes("utf-8");
		fi.read(bb);
//		StkPoolOuput stockPool = DzhyunStockpool.StkPoolOuput.parseFrom(jedis.lrange("Aq0板块热点轮动策略", 0, 0).get(0).getBytes("utf-8"));
//		byte[] bb = jedis2.lrange("Aq0板块热点轮动策略", 1, 1).get(0).getBytes("utf-8");
		StkPoolOuput stockPool = DzhyunStockpool.StkPoolOuput.parseFrom(bb);
		
//		System.out.println(stockPool.getStkCount());
		System.out.println(stockPool.getCeWenShiJian());
		
//		stockPool.getPooldataList().get(0).getStk(0).getObj();
	} catch (InvalidProtocolBufferException e) {
		 //TODO Auto-generated catch block
		e.printStackTrace();
	}finally {
		jedis2.close();
	}
}


/**
 * @param args
 */
public static void main(String[] args) {
	try {
		TestPb.testAq0();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
 
}
 
}