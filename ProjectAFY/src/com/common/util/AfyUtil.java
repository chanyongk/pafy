package com.common.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;

public class AfyUtil {
	public static String[] toArray(String str, String delim){
		StringTokenizer st = new StringTokenizer(str, delim);
		String[] result = new String[st.countTokens()];
		int i = 0;
		while(st.hasMoreTokens()) {
			result[i++] = st.nextToken();
		}
		return result;
	}
	public static String toString(String value){
		return (value==null || value.trim().equals("") || value.equals("null"))? "" : value;
	}
	public static String toString(Object value){
		return toString(value+"");
	}
	
	public static Timestamp toTimestamp(String value) throws Exception{
    	String[] stamp_array = new String[]{
    			"yyyy-MM-dd HH:mm:ss.SSS",
    			"yyyy-MM-dd HH:mm:ss",
    			"yyyy-MM-dd HH:mm",
    			"yyyy-MM-dd HH",
    			"yyyy-MM-dd",
    			"yyyyMMddHHmmssSSS",
    			"yyyyMMddHHmmss",
    			"yyyyMMddHHmm",
    			"yyyyMMddHH",
    			"yyyyMMdd"
    		};	
    	Timestamp timestamp = null;
    	for(int i=0; i<stamp_array.length; i++){
    		try{
    			timestamp = toTimestamp(value, stamp_array[i]);
    		}catch(Exception e){}
    		if(timestamp!=null){
    			break;
    		}		
    	}
    	return timestamp;
    }
	public static Timestamp toTimestamp(String value, String type) throws Exception{
    	try{
    		return new java.sql.Timestamp(new SimpleDateFormat(type).parse(value).getTime());
    	}catch(Exception e){
    		return null;
    	}
    }
	public static String replaceAll( String str, String s1, String s2 ){
		str = toString(str);
		if(s1!=null && s2!=null){
			StringBuffer result = new StringBuffer();
			if(s1.length()>0){
				
				String s = str.toLowerCase();
				s1 = s1.toLowerCase();
				s2 = s2.toLowerCase();
		
				int index1 = 0;
				int index2 = s.indexOf(s1);
		
				while(index2 >= 0) {
					result.append( str.substring(index1, index2) );
					result.append( s2 );
					index1 = index2 + s1.length();
					index2 = s.indexOf(s1, index1);
				}
				result.append( str.substring( index1 ) );
			}else{
				result.append(str);
			}
			return result.toString();
		}else{
			return str;
		}		
	}
	public static String toDateFormat (String value, String type){
    	try{
    		return value==null ? "" : new SimpleDateFormat(type).format(toDate(value));
    	}catch(Exception e){
    		return "";
    	}
    }
	public static Date toDate(String value) throws Exception{
    	String[] stamp_array = new String[]{
    			"yyyy-MM-dd HH:mm:ss.SSS",
    			"yyyy-MM-dd HH:mm:ss",
    			"yyyy-MM-dd HH:mm",
    			"yyyy-MM-dd HH",
    			"yyyy-MM-dd",
    			"yyyyMMddHHmmssSSS",
    			"yyyyMMddHHmmss",
    			"yyyyMMddHHmm",
    			"yyyyMMddHH",
    			"yyyyMMdd"
    		};	
    	Date date = null;
    	for(int i=0; i<stamp_array.length; i++){
    		try{
    			date = toDate(value, stamp_array[i]);
    		}catch(Exception e){}
    		if(date!=null){
    			break;
    		}		
    	}
    	return date;
    }
	public static Date toDate(String value, String type) throws Exception{
    	try{
    		return new Date(new SimpleDateFormat(type).parse(value).getTime());
    	}catch(Exception e){
    		return null;
    	}
    } 
}
