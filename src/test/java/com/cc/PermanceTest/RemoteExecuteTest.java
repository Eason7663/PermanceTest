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
		RemoteExecuteCommand ret=new RemoteExecuteCommand("10.15.144.72", "root","111111");
		//执行多条命令可以使用&&，&，|，||等shell的复合命令格式
		System.out.println(ret.execute("cd /opt/dzhyun&&pwd"));
		logger.info("执行命令：\"cd /opt/dzhyun&&pwd\"");
	}

}
