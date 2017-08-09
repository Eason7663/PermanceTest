package com.cc.PermanceTest;

import org.apache.log4j.Logger;

import com.gw.uitls.RemoteExecuteCommand;

public class RemoteExecuteTest {
	
	private static Logger logger = Logger.getLogger(RemoteExecuteTest.class);
	
	private RemoteExecuteCommand rec = null;
	public RemoteExecuteTest(String ip, String username, String password){
		rec = new RemoteExecuteCommand(ip, username, password);
	}
	
	public void startJmeter(String jmeterStart) {
		String result = rec.execute(jmeterStart);
		logger.info("启动jmeter:" +  jmeterStart);
		logger.info(result);
		
	}
	public static void main(String[] args) {
		RemoteExecuteTest ret=new RemoteExecuteTest("10.15.107.181", "root","111111");
		//执行命令
//		System.out.println(rec.execute("ifconfig"));
//		logger.info("执行命令ifconfig");
		//执行脚本
//		System.out.println(rec.execute("sh /opt/testEason/test.sh start"));
		//这个方法与上面最大的区别就是，上面的方法，不管执行成功与否都返回，
		//这个方法呢，如果命令或者脚本执行错误将返回空字符串
//		System.out.println(rec.executeSuccess("ifconfig"));
		ret.startJmeter("sh /opt/testEason/jmeter-agent.sh stop");
		ret.startJmeter("sh /opt/testEason/jmeter-agent.sh status");
		
	}

}
