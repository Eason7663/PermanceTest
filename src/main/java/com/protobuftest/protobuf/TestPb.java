package com.protobuftest.protobuf;
 
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.List;
 
import com.google.protobuf.InvalidProtocolBufferException;
import com.protobuftest.protobuf.PersonProbuf;
import com.protobuftest.protobuf.PersonProbuf.Person;
import com.protobuftest.protobuf.PersonProbuf.Person.PhoneNumber;
import com.sun.org.apache.bcel.internal.generic.NEW;

import redis.clients.jedis.Jedis;
 
public class TestPb {
 
/**
 * @param args
 */
public static void main(String[] args) {
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
	Jedis jedis = new Jedis("10.15.208.121", 7000);
//	jedis.auth("admin");
	byte[] key = new String("PERSON2").getBytes();
	jedis.set("PERSON1", "PERSON1");
	System.out.println(jedis.get("PERSON1"));
	jedis.set(key, buf);
	if (jedis.exists(key)) {
		jedis.echo(key);
	};
//	Iterator<String> keys = jedis.keys("9t0*").iterator();
//	while (keys.hasNext()) {
//		String string = (String) keys.next();
//		System.out.println(string);
//		Iterator<String> iter = jedis.hkeys(string).iterator();  
//	    while (iter.hasNext()) {  
//	        String key1 = iter.next();  
//	        System.out.println(key1 + ":" + jedis.hmget(string, key1));  
//	    } 
//	}

	
	
	jedis.close();
	
	
	
	
	
	
	
	
	
	
 
	try {
		Person person2 = PersonProbuf.Person.parseFrom(buf);
		System.out.println(person2.getName() + ", " + person2.getEmail());
		List<PhoneNumber> lstPhones = person2.getPhoneList();
		for (PhoneNumber phoneNumber : lstPhones) {
			System.out.println(phoneNumber.getNumber());
		}
	} catch (InvalidProtocolBufferException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
// 
//	try {
//		System.out.println(new String(buf, "GB2312"));
//	} catch (UnsupportedEncodingException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}
 
}
 
}