package com.gw.uitls;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Random;

import javax.crypto.Cipher;

import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.gw.fileUtils.fileReaders;

import sun.misc.BASE64Encoder;


public class EncryptHelper {
	final static int MaxKeyIvLength = 16;
	
	
	/**
	 * @Description 利用imtoken生成key
	 * @param imtoken
	 * @return
	 * @throws UnsupportedEncodingException
	 * @return_type byte[]
	 * @author Eason
	 * @date 2017年11月16日 下午2:04:27  
	 */
	private static byte[] GetkeyBytes(String imtoken) throws UnsupportedEncodingException{
		byte[] bytes = imtoken.getBytes("utf-8");
		byte[] keyBytes = new byte[MaxKeyIvLength];
		//如果bytes.length不够16位，则用0补齐
		int length = Math.min(bytes.length, keyBytes.length);
		keyBytes = Arrays.copyOf(bytes, length);
		//取反
        for (int i = 0; i < length; i++)
        {
            keyBytes[i] = (byte)~keyBytes[i];
        }
        for (int i = length; i < keyBytes.length; i++)
        {
            keyBytes[i] = 0;
        }
        return keyBytes;
	}

	/**
	 * @Description 生成AES/CBC需要的随机向量
	 * @return
	 * @return_type String
	 * @author Eason
	 * @date 2017年11月16日 下午2:40:11  
	 */
//	public static String GetIv() {
//		String str = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
//		StringBuilder iv = new StringBuilder();
//		Random random = new Random();
//		System.out.println(str.length());
//		System.out.println(random.nextInt(str.length()));
//		for (int i = 0; i < MaxKeyIvLength; i++) {
//
//			int index = random.nextInt(str.length());
//			System.out.println(index);
//			iv.append(str.substring(index,index+1));
//		}
//		return iv.toString();
//	}
	
	/**
	 * @Description 生成AES/CBC需要的随机向量
	 * @return
	 * @return_type String
	 * @author Eason
	 * @throws Exception 
	 * @date 2017年11月16日 下午2:40:11  
	 */
	public static String GetIv() throws Exception {
		String str = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
//		StringBuilder iv = new StringBuilder();
		byte[] strByte = str.getBytes("UTF-8");
		byte[] ivByte = new byte[MaxKeyIvLength];
		Random random = new Random();
//		System.out.println(str.length());
//		System.out.println(random.nextInt(str.length()));
		for (int i = 0; i < MaxKeyIvLength; i++) {

//			int index = random.nextInt(str.length());
//			System.out.println(index);
//			iv.append(str.substring(index,index+1));
			ivByte[i] = strByte[random.nextInt(str.length())];
		}
		System.out.println("GetIv 生成的向量：" + new String(ivByte, "UTF-8"));
		return new String(ivByte, "UTF-8");
	}
	
    /**
    *
    * 十六进制转换字符串
    */

   public static byte[] hexStr2Bytes(String hexStr) {
       System.out.println("in len :" + hexStr.length());
       String str = "0123456789ABCDEF";
       char[] hexs = hexStr.toCharArray();
       byte[] bytes = new byte[hexStr.length() / 2];
       int n;
       for (int i = 0; i < bytes.length; i++) {
           n = str.indexOf(hexs[2 * i]) * 16;
           n += str.indexOf(hexs[2 * i + 1]);
           bytes[i] = (byte) (n & 0xff);
       }
       System.out.println("out len :" + bytes.length);
       System.out.println("ddd" + Arrays.toString(bytes));
       return bytes;
   }
	
    /**
     * bytes转换成十六进制字符串
     */ 
    public static String byte2HexStr(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1)
                hs = hs + "0" + stmp;
            else
                hs = hs + stmp;
            // if (n<b.length-1) hs=hs+":";
        }
        return hs.toUpperCase();
    }
	
    /**
     * 合并byte数组
     */
    public static byte[] unitByteArray(byte[] byte1,byte[] byte2){
        byte[] unitByte = new byte[byte1.length + byte2.length];
        System.arraycopy(byte1, 0, unitByte, 0, byte1.length);
        System.arraycopy(byte2, 0, unitByte, byte1.length, byte2.length);
        return unitByte;
    }
    
    
	   public static String encrypt(String imtoken,  String content) {
	        try {
	        	String key = GetkeyBytes(imtoken).toString();
	        	String initVector = GetIv();
	        	System.out.println("iv = " + initVector);
	            System.out.println("key:\t" + Arrays.toString(key.getBytes("UTF-8")));
	            System.out.println("iv:\t" + Arrays.toString(initVector.getBytes("UTF-8")));

	            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
	            //生成加密解密需要的key
	            SecretKeySpec skeySpec = new SecretKeySpec(GetkeyBytes(imtoken), "AES");
	            //根据指定算法AES自成密码器 算法/模式/填充
	            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
//	            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
	            //初始化密码器，第一个参数为加密(Encrypt_mode)或者解密解密(Decrypt_mode)操作，第二个参数为使用的KEY
	            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
	            //获取加密内容的字节数组(这里要设置为utf-8)不然内容中如果有中文和英文混合中文就会解密为乱码
	            byte [] byte_encode=content.getBytes("UTF-8");
	            //根据密码器的初始化方式--加密：将数据加密
	            byte[] encrypted = cipher.doFinal(byte_encode);
//	            System.out.println(Arrays.toString(encrypted));
	            System.out.println("加密前initVector：" + initVector);
	            byte[] AES_encrypted =unitByteArray(initVector.getBytes("UTF-8"), encrypted);
	            String AES_encode=new String(new BASE64Encoder().encode(AES_encrypted));
	            return AES_encode;
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }

	        return null;
	    }

	public static void main(String[] args) {
//		System.out.println("hello");

//		String content = "{a1234242134,C:13251465468WEFASDF,B:ASDFQERTFERT}";
		try {
			String imtoken = "M5V_1i0TUeRvOlm1qWW90O1F9MulpR0=";
			String content = fileReaders.ReadFile("c:\\content.txt");
			String newContent = content.replaceAll("\"", "\\\"");
			System.out.println(newContent);
			System.out.println(EncryptHelper.encrypt(imtoken, content));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		
		
	}

}