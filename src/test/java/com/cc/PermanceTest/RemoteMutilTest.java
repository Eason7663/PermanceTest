package com.cc.PermanceTest;

import com.gw.uitls.SSH2Util;
import com.gw.uitls.ShellUtils;
import com.jcraft.jsch.JSchException;

public class RemoteMutilTest {
	
	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		RemoteExecuteCommand ret = new RemoteExecuteCommand("10.15.144.72", "root", "111111");
//		System.out.println(ret.executeMutil());
//		//执行单个命令
////		System.out.println(ret.execute("ls"));
//		System.out.println("hello");
////		System.exit(0);
//		try {
//			ShellUtils.execCmd("pwd", "root", "111111", "10.15.144.72");
//		} catch (JSchException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
		SSH2Util ssh2Util = new SSH2Util("10.15.144.170","root","111111","/");
//		ssh2Util.uploadFile(".//logs//log.log", "/opt/testEason");
		ssh2Util.execCommand("cd /opt/apache-jmeter-2.12/bin; >agent.log; nohup ./jmeter-server -Djava.rmi.server.hostname=10.15.144.170 > agent.log 2>&1 & ", false);
//		ssh2Util.closeConnection();
		System.exit(0);
	}

}
