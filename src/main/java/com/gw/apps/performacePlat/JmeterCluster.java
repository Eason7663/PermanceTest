package com.gw.apps.performacePlat;

import java.io.*;   
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLException;

import org.dom4j.*;   
import org.dom4j.io.*;

import com.gw.uitls.RemoteExecuteCommand;

import sun.net.www.content.text.plain;
  
public class JmeterCluster {
	private String strPath;
	private ArrayList<Slave> slaveList = new ArrayList<>();
	private Slave master = new Slave();
	private Map<String, String> command = new HashMap<>();
	
	//constructor

	public JmeterCluster(String strPath) {
		  try {   
			    File f = new File(strPath);   
			    SAXReader reader = new SAXReader();   
			    Document doc = reader.read(f);   
			    Element root = doc.getRootElement();

			    //初始化配置文件中所有slave
				for (Iterator iterator = root.element("Servers").elements("Slave").iterator(); iterator.hasNext();) {
					Element ele = (Element) iterator.next();
					Slave slave = new Slave();
					slave.setIP(ele.elementText("ip"));
					slave.setUsername(ele.elementText("username"));
					slave.setPassword(ele.elementText("password"));
					slaveList.add(slave);
				}
				//初始化配置文件中master
				master.setIP(root.element("Servers").element("Master").elementText("ip"));
				master.setUsername((root.element("Servers").element("Master").elementText("username")));
				master.setPassword((root.element("Servers").element("Master").elementText("password")));
				//初始化配置文件中Command
//				System.out.println(root.element("Command").elementText("StartJmeter"));
				command.put("StartJmeter", root.element("Command").elementText("StartJmeter"));
				command.put("StopJmeter", root.element("Command").elementText("StopJmeter"));
				command.put("StartMaster", root.element("Command").elementText("StartMaster"));
			   
				
			   } catch (Exception e) {   	
			    e.printStackTrace();   
			   }  
	}
	
	//启动所有负载机jmeter
	public void startSlaveJmeter(){
		ExecutorService es = Executors.newFixedThreadPool(4);
		for (Slave slave : slaveList) {
			RemoteExecuteCommand ret = new RemoteExecuteCommand(slave.getIP(), slave.getUserName(), slave.getPassword());
			//启动jmeter CMD
			String str = new String(command.get("StartJmeter"));
			str = str.replace("$ip", ret.getIp());
			System.out.println(str);
			StringBuilder stringBuilder = new StringBuilder("cd /opt/apache-jmeter-2.12/bin; >agent.log; nohup ./jmeter-server -Djava.rmi.server.hostname=");
			stringBuilder.append(ret.getIp());
			stringBuilder.append(" > agent.log 2>&1 &");
			
			
			ret.setCmd(str);
//			ret.setCmd(stringBuilder.toString());
			es.submit(ret);
		}
		es.shutdown();//唯一的影响就是不能再提交任务了，正在执行的任务即使在阻塞着也不会结束，在排队的任务也不会取消。
		//等待所有启动线程执行结束
        try {  
            boolean loop = true;  
            do {    //等待所有任务完成  
                loop = !es.awaitTermination(2, TimeUnit.SECONDS);  //阻塞，直到线程池里所有任务结束
                System.out.println("等待启动线程结束...");
            } while(loop);
            //启动负载机线程已结束
            System.out.println("所有负载机已经启动完毕");
        } catch (InterruptedException e) {  
            e.printStackTrace();  
        } 
	}
	
	//停止所有负载机jmeter
	public void stopSlaveJmeter() {
		//"ps -aef | grep $app |grep -v grep | awk '{print \$2}' | xargs kill -9"
		ExecutorService es = Executors.newFixedThreadPool(4);
		for (Slave slave : slaveList) {
			RemoteExecuteCommand ret = new RemoteExecuteCommand(slave.getIP(), slave.getUserName(), slave.getPassword());
			//停止jmeter shell
//			StringBuilder stringBuilder = new StringBuilder("ps -aef | grep jmeter |grep -v grep | awk '{print $2}' | xargs kill -9");
//			ret.setCmd(stringBuilder.toString());
			ret.setCmd(command.get("StopJmeter"));
			es.submit(ret);
		}
		es.shutdown();
		//等待所有停止线程执行结束
        try {  
            boolean loop = true;  
            do {    //等待所有任务完成  
                loop = !es.awaitTermination(2, TimeUnit.SECONDS);  //阻塞，直到线程池里所有任务结束
                System.out.println("等待停止线程结束...");
            } while(loop); 
            //所有停止线程已经结束
            System.out.println("所有停止线程已经结束");
        } catch (InterruptedException e) {  
            e.printStackTrace();  
        } 
	}
	
	//启动调度机
	public void startMaster() {
		RemoteExecuteCommand ret = new RemoteExecuteCommand(master.getIP(), master.getUsername(), master.getPassword());
		System.out.println(ret.execute(command.get("StartMaster")));
	}
	
	//下载测试报告
	public void	getReport() {
		RemoteExecuteCommand ret = new RemoteExecuteCommand(master.getIP(), master.getUsername(), master.getPassword());
		ret.
	}

 
	public String getStrPath() {
		return strPath;
	}

	public ArrayList<Slave> getSlaveList() {
		return slaveList;
	}

	public Map<String, String> getCommand() {
		return command;
	}

	public void setStrPath(String strPath) {
		this.strPath = strPath;
	}

	public void setSlaveList(ArrayList<Slave> slaveList) {
		this.slaveList = slaveList;
	}

	public void setCommand(Map<String, String> command) {
		this.command = command;
	}



	public Slave getMaster() {
		return master;
	}



	public void setMaster(Slave master) {
		this.master = master;
	}



	public class Slave{
		private String IP;
		private String username;
		private String password;
		public Slave() {
			// TODO Auto-generated constructor stub
		}
		public Slave(String IP, String userName, String password) {
			// TODO Auto-generated constructor stub
			this.IP = IP;
			this.username = userName;
			this.password = password;
		}
		public String getIP() {
			return IP;
		}
		public String getUserName() {
			return username;
		}
		public String getPassword() {
			return password;
		}
		public String getUsername() {
			return username;
		}
		public void setIP(String iP) {
			IP = iP;
		}
		public void setUsername(String username) {
			this.username = username;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		public String toString() {
	 		StringBuilder str = new StringBuilder();
	 		str.append("IP:").append(getIP()).append(",username:").append(getUserName()).append(",password:").append(getPassword());
	 		return str.toString();
	 		
	 	}
		
	}
	
public static void main(String args[]) {   
  
	JmeterCluster jmeterAddr = new JmeterCluster(".//conf//jmeterAddr.xml");
	//启动负载机
	jmeterAddr.startSlaveJmeter();
	//启动调度机
	jmeterAddr.startMaster();
	
	//停止负载机
//	jmeterAddr.stopSlaveJmeter();

	System.exit(0);
}   
}
