package com.gw.uitls;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.slf4j.LoggerFactory;


import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class CogradientImgFileManager{
	private static final Logger log =Logger.getLogger(CogradientImgFileManager.class);
	private static ChannelExec channelExec;
	private static Session session = null;
	private static int timeout = 60000; 
	
	/**
	* 连接远程服务器
	* @param host ip地址
	* @param userName 登录名
	* @param password 密码
	* @param port 端口
	* @throws Exception
	*/
	public static void versouSshUtil(String host,String userName,String password,int port) throws Exception{
		log.info("尝试连接到....host:" + host + ",username:" + userName + ",password:" + password + ",port:" + port);
		JSch jsch = new JSch(); // 创建JSch对象
		session = jsch.getSession(userName, host, port); // 根据用户名，主机ip，端口获取一个Session对象
		session.setPassword(password); // 设置密码
		Properties config = new Properties();
		config.put("StrictHostKeyChecking", "no");
		session.setConfig(config); // 为Session对象设置properties
		session.setTimeout(timeout); // 设置timeout时间
		session.connect(); // 通过Session建立链接
	}
	/**
	* 在远程服务器上执行命令 使用ChannelExec
	* @param cmd 要执行的命令字符串
	* @param charset 编码
	* @throws Exception
	*/
	public static void runCmd(String cmd,String charset) throws Exception{
		//使用ChannelExec
		channelExec = (ChannelExec) session.openChannel("exec");
		channelExec.setCommand(cmd);
		channelExec.setInputStream(null);
//		channelExec.setErrStream(System.err);
		channelExec.connect();
		InputStream in = channelExec.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(in, Charset.forName(charset)));
		
		String buf = null;
		//由于使用BufferedReader的readLine()方法，结果会产生阻塞。
		while ((buf = reader.readLine()) != null && buf.length() > 0){
			System.out.println(buf);
		}
		in.close();
		reader.close();
		
		
		channelExec.disconnect();
	}
	
	//使用ChannelShell,为防止阻塞在
	//待完善...
	
	
	
	//测试代码
	public static void main(String[] args){
		try{
			versouSshUtil("10.15.144.72","root","111111",22);
//			runCmd("cd /opt/testEason","UTF-8");
			runCmd("cd /opt&&cd ./dzhyun/&&pwd","UTF-8");
			
		}catch (Exception e){
			//TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}