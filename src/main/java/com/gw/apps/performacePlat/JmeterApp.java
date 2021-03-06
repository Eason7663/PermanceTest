package com.gw.apps.performacePlat;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.gw.uitls.RemoteExecuteCommand;

public class JmeterApp {
	private JmeterCluster jCluster;
		
	//constructor
	public JmeterApp() {
		// TODO Auto-generated constructor stub
		
	}
	//constructor
	public JmeterApp(String strPath) {
		// TODO Auto-generated constructor stub
		jCluster = new JmeterCluster(strPath);
	}

	public void startSlave(){
		ExecutorService es = Executors.newFixedThreadPool(4);
	}


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String cmd = "cd /opt/dzhyun&&pwd";
		ExecutorService es = Executors.newFixedThreadPool(4);
		RemoteExecuteCommand ret1 = new RemoteExecuteCommand("10.15.107.148", "root","111111");
		ret1.setCmd(cmd);
		
		RemoteExecuteCommand ret2 = new RemoteExecuteCommand("10.15.144.80", "root","111111");
		ret2.setCmd(cmd);
		
		RemoteExecuteCommand ret3 = new RemoteExecuteCommand("10.15.144.81", "root","111111");
		ret3.setCmd(cmd);
		Future<?> task = es.submit(ret1);
		es.submit(ret2);
		es.submit(ret3);
		es.shutdown();
	}

}
