package com.liang.util;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;

public class CheckUitl {

	private static final String  token = "liangdada";
	
	public static boolean checkSignature(String signature,String timestamp,String nonce) {
		//ÅÅÐò
		ArrayList<String> list=new ArrayList<String>();
		list.add(nonce);
		list.add(timestamp);
		list.add(token);
		Collections.sort(list);
		//Éú³É×Ö·û´®
		StringBuffer str = new StringBuffer();
		for (String string : list) {
			str.append(string);
		}
		//¼ÓÃÜ Sha1
		String temp = getSha1(str.toString());
		return temp.equals(signature);
	}
	
	public static String getSha1(String str){
        if(str==null||str.length()==0){
            return null;
        }
        char hexDigits[] = {'0','1','2','3','4','5','6','7','8','9',
                'a','b','c','d','e','f'};
        try {
            MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
            mdTemp.update(str.getBytes("UTF-8"));

            byte[] md = mdTemp.digest();
            int j = md.length;
            char buf[] = new char[j*2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
                buf[k++] = hexDigits[byte0 & 0xf];      
            }
            return new String(buf);
        } catch (Exception e) {
            // TODO: handle exception
            return null;
        }
    }
	
	
}
