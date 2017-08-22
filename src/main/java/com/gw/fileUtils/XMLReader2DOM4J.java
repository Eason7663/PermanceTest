package com.gw.fileUtils;

import java.io.*;   
import java.util.*;

import javax.net.ssl.SSLException;

import org.dom4j.*;   
import org.dom4j.io.*;
  
public class XMLReader2DOM4J {
	private String strPath;
	private ArrayList<Slave> slaveList = new ArrayList<>();
	private Map<String, String> command;
	
	//constructor

	public XMLReader2DOM4J(String strPath) {
		  try {   
			    File f = new File(strPath);   
			    SAXReader reader = new SAXReader();   
			    Document doc = reader.read(f);   
			    Element root = doc.getRootElement();
			    ArrayList<Slave> slaveList = new ArrayList<>();
				Slave slave = new Slave();
			    
				for (Iterator iterator = root.element("Servers").elements("Slave").iterator(); iterator.hasNext();) {
					Element ele = (Element) iterator.next();

					slave.setIP(ele.elementText("ip"));
					slave.setUsername(ele.elementText("username"));
					slave.setPassword(ele.elementText("password"));
					slaveList.add(slave);
//					System.out.println(slave);
				}

				System.out.println(slaveList);

			   
			   } catch (Exception e) {   	
			    e.printStackTrace();   
			   }  
	}
	


	private class Slave{
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
	
public static void main(String arge[]) {   
//  try {   
//    File f = new File(".//conf//jmeterAddr.xml");   
//    SAXReader reader = new SAXReader();   
//    Document doc = reader.read(f);   
//    Element root = doc.getRootElement();   
//    Element foo;
//   for (Iterator i = root.element("Servers").elementIterator("Slave"); i.hasNext();) {   
//     foo = (Element) i.next();
//     System.out.println(foo.elementText("ip"));
//    }  
//   } catch (Exception e) {   	
//    e.printStackTrace();   
//   }   
	XMLReader2DOM4J jmeterAddr = new XMLReader2DOM4J(".//conf//jmeterAddr.xml");
	System.out.println(jmeterAddr.slaveList);
}   
}
