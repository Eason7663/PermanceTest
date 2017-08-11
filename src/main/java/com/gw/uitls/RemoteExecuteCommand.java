package com.gw.uitls;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.omg.CORBA.TIMEOUT;

import com.sun.org.apache.bcel.internal.generic.RETURN;

import ch.ethz.ssh2.ChannelCondition;
import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.SCPClient;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;
//import ch.ethz.ssh2.SCPOutputStream;

/**
 * 远程执行linux的shell script
 * @author Ickes
 * @since  V0.1
 */
public class RemoteExecuteCommand {
	private static final long TIMEOUT = 5000;
	//字符编码默认是utf-8
	private static String  DEFAULTCHART="UTF-8";
	private Connection conn;
	private String ip;
	private String userName;
	private String userPwd;

	
	public RemoteExecuteCommand(String ip, String userName, String userPwd) {
		this.ip = ip;
		this.userName = userName;
		this.userPwd = userPwd;
	}
	
	
	public RemoteExecuteCommand() {
		
	}
	
	/**
	 * 远程登录linux的主机
	 * @author Ickes
	 * @since  V0.1
	 * @return
	 * 		登录成功返回true，否则返回false
	 */
	public Boolean login(){
		boolean flg=false;
		try {
			conn = new Connection(ip);
			conn.connect();//连接
			flg=conn.authenticateWithPassword(userName, userPwd);//认证
		} catch (IOException e) {
			e.printStackTrace();
		}
		return flg;
	}
	//执行多条命令
	public String executeMutil(){
		 String temp = "";
		try {
			if (login()) {
				OutputStream in = null;
				InputStream out = null;
				Session session = conn.openSession();
				session.requestDumbPTY();
				session.startShell();
				out = session.getStdout();
				in = session.getStdin();
				String shellCommand = "pwd \n";
				in.write(shellCommand.getBytes());  
		        in.flush();  

		        //获取命令执行的结果  
		        if (out.available() > 0) {  
		            byte[] data = new byte[out.available()];  
		            int nLen = out.read(data);  
		              
		            if (nLen < 0) {  
		                throw new Exception("network error.");  
		            }  
		              
		            //转换输出结果并打印出来  
		            temp = new String(data, 0, nLen,"utf-8");  
		        }
		        in.close();
		        out.close();
				session.close();
				conn.close();
			}
		} catch (IOException  e) {
			e.printStackTrace();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {

		}
		return temp;
	}
	/**
	 * @author Ickes
	 * 远程执行shell脚本或者命令
	 * @param cmd
	 * 		即将执行的命令
	 * @return
	 * 		命令执行完后返回的结果值
	 * @since V0.1
	 */
	public String execute(String cmd){
		String result="";
		try {
			if(login()){
				Session session= conn.openSession();//打开一个会话
				session.execCommand(cmd);//执行命令
				result=processStdout(session.getStdout(),DEFAULTCHART);
				//如果标准输出为空，说明脚本执行出错了
				if(StringUtils.isBlank(result)){
					result=processStdout(session.getStderr(),DEFAULTCHART);
				}
				conn.close();
				session.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	//上传文件
	public void putFile(String localFiles, String remoteDirectory) {
		try {
			if (login()) {
				File file = new File(localFiles);
				if (file.isDirectory()) {
					throw new RuntimeException(localFiles + "  is not a file");
				}
				String fileName = file.getName();
				SCPClient scpClient = new SCPClient(conn);
				scpClient.put(fileName,remoteDirectory,"0644");
				conn.close();
			}			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	/**
	 * @author Ickes
	 * 远程执行shll脚本或者命令
	 * @param cmd
	 * 		即将执行的命令
	 * @return
	 * 		命令执行成功后返回的结果值，如果命令执行失败，返回空字符串，不是null
	 * @since V0.1
	 */
	public String executeSuccess(String cmd){
		String result="";
		try {
			if(login()){
				Session session= conn.openSession();//打开一个会话
				session.execCommand(cmd);//执行命令
				result=processStdout(session.getStdout(),DEFAULTCHART);
				conn.close();
				session.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
   /**
	* 解析脚本执行返回的结果集
	* @author Ickes
	* @param in 输入流对象
	* @param charset 编码
	* @since V0.1
	* @return
	* 		以纯文本的格式返回
	*/
	private String processStdout(InputStream in, String charset){
		InputStream stdout = new StreamGobbler(in);
		StringBuffer buffer = new StringBuffer();;
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(stdout,charset));
			String line=null;
			while((line=br.readLine()) != null){
				buffer.append(line+"\n");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return buffer.toString();
	}
	
	public static void setCharset(String charset) {
		DEFAULTCHART = charset;
	}
	public Connection getConn() {
		return conn;
	}
	public void setConn(Connection conn) {
		this.conn = conn;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPwd() {
		return userPwd;
	}
	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}
}
