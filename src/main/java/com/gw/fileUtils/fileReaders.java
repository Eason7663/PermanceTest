package com.gw.fileUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;

public class fileReaders {

	/**
	 * @Description TODO
	 * @return
	 * @throws IOException
	 * @return_type String
	 * @author Eason
	 * @date 2017年10月18日 上午9:43:09  
	 */
	public static String ReadFile(String filePath) throws IOException {
        File file = new File(filePath);//定义一个file对象，用来初始化FileReader
        FileReader reader = new FileReader(file);//定义一个fileReader对象，用来初始化BufferedReader
        BufferedReader bReader = new BufferedReader(reader);//new一个BufferedReader对象，将文件内容读取到缓存
        StringBuilder sb = new StringBuilder();//定义一个字符串缓存，将字符串存放缓存中
        String s = "";
        while ((s =bReader.readLine()) != null) {//逐行读取文件内容，不读取换行符和末尾的空格
            sb.append(s + "\n");//将读取的字符串添加换行符后累加存放在缓存中
//            System.out.println(s);
        }
        bReader.close();
        String str = sb.toString();
//        System.out.println(str );
        str.replaceAll(",", "    ");
        System.out.println(str );
		return str;
	}
	
	/**
	 * @Description TODO
	 * @param fileName
	 * @return_type void
	 * @author Eason
	 * @date 2017年10月18日 上午10:33:23  
	 */
	public static ArrayList<String> readFileByChar(String fileName) {
		
		File file = new File(fileName);
        Reader reader = null;
        ArrayList<String> objList = new ArrayList<String>();
        try {
            System.out.println("以字符为单位读取文件内容，一次读一个字节：");
            // 一次读一个字符
            reader = new InputStreamReader(new FileInputStream(file));
            int tempchar;

            StringBuilder stringBuilder = new StringBuilder();

            while ((tempchar = reader.read()) != -1) {
                // 对于windows下，\r\n这两个字符在一起时，表示一个换行。
                // 但如果这两个字符分开显示时，会换两次行。
                // 因此，屏蔽掉\r，或者屏蔽\n。否则，将会多出很多空行。
            	
            	if (tempchar <= Integer.valueOf(90) && tempchar >= Integer.valueOf(48)) {
            		stringBuilder.append((char) tempchar);
				}else{
					if(stringBuilder.toString().length() !=0){
						objList.add(stringBuilder.toString());
						stringBuilder.delete(0, stringBuilder.toString().length());
					}
				}
            	
            	
//                if (((char) tempchar) != '\r') {
////                    System.out.print((char) tempchar);
//                	 if (((char) tempchar) == ' '){
//                		 blankcount++;
////                		 continue;
//                	 }else{
//                		 stringBuilder.append((char) tempchar);
//					}
//                }
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

		return objList;
	}
	
	

	/**
	 * @Description TODO
	 * @param fileName
	 * @return_type void
	 * @author Eason
	 * @date 2017年10月18日 上午10:32:07  
	 */
	public static void readFileByChars(String fileName) {
	     Reader reader = null;
	     try {
	            System.out.println("以字符为单位读取文件内容，一次读多个字节：");
	            // 一次读8个字符
	            char[] tempchars = new char[8];
	            int charread = 0;
	            reader = new InputStreamReader(new FileInputStream(fileName));
	            // 读入多个字符到字符数组中，charread为一次读取字符数
	            while ((charread = reader.read(tempchars)) != -1) {
	                // 同样屏蔽掉\r不显示
	                if ((charread == tempchars.length)
	                        && (tempchars[tempchars.length - 1] != '\r')) {
	                    System.out.print(tempchars);
	                } else {
	                    for (int i = 0; i < charread; i++) {
	                        if (tempchars[i] == '\r') {
	                            continue;
	                        } else {
	                            System.out.println(tempchars[i]);
	                        }
	                    }
	                }
	            }

	        } catch (Exception e1) {
	            e1.printStackTrace();
	        } finally {
	            if (reader != null) {
	                try {
	                    reader.close();
	                } catch (IOException e1) {
	                }
	            }
	        }
	}
	
	public static void writeToFile(ArrayList<String> objList, int count) {
		try {
			if(count > objList.size()){
				count = objList.size();
			}
						
			FileWriter fWriter = new FileWriter("E:\\Work\\PC端项目\\董秘爆料\\测试脚本\\result"+count+".txt");
			StringBuilder stringBuilder = new StringBuilder();
			for (int i = 0; i < count-1; i++) {
				stringBuilder.append(objList.get(i));
				stringBuilder.append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length()-1);
			fWriter.write(stringBuilder.toString());
//			fWriter.write("stringBuilder.toString()");
			System.out.println(stringBuilder.toString());
			fWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

	/**
	 * @Description TODO
	 * @param args
	 * @return_type void
	 * @author Eason
	 * @date 2017年10月18日 上午9:43:03  
	 */
	public static void main(String[] args) throws IOException {
//		readFileByChars("E:\\Work\\PC端项目\\董秘爆料\\测试脚本\\993752.txt");
		ArrayList<String> objList =  readFileByChar("E:\\Work\\PC端项目\\董秘爆料\\测试脚本\\993752.txt");
		writeToFile(objList,80);
		
	}

}
