package com.cc.common.Utils;

import java.security.MessageDigest;

public class MD5Encode {
	
	public static String encryption(String plainText) {
	       String re_md5 = new String();
	        try {
	            MessageDigest md = MessageDigest.getInstance("MD5");
	            md.update(plainText.getBytes("UTF-8"));
	            byte b[] = md.digest();
	            int i;
	            StringBuffer buf = new StringBuffer();
	            for (int offset = 0; offset < b.length; offset++) {
	                i = b[offset];
	                if (i < 0)
	                    i += 256;
	                if (i < 16)
	                    buf.append("0");
	                buf.append(Integer.toHexString(i));
	            }
	            re_md5 = buf.toString(); 
	        } catch (Exception e) {
	            e.printStackTrace();
	        } 
	        return re_md5;
	    } 

	
	public static void main(String[] args){
		String str="测试Ab76";
		String result=encryption(str);
		System.out.println(result);
	}

}
