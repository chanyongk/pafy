package com.common.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

public class AfyUtil {
	private static String DEFAULT_ENCODING = "UTF-8";
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
	public static String toString(HttpServletRequest request, String value){
		return toStringMethodDivChange(request, toString(request.getParameter(value)));
	}
	public static String toStringHtml(HttpServletRequest request, String value){
		return htmlSpecialChars(toString(request, value));
	}
	public static String toStringYmd(HttpServletRequest request, String value){
		return toStringYmd(toString(request, value));
	}
	private static String toStringMethodDivChange(HttpServletRequest request, String value){
		return toStringMethodDivChange(request.getContentType(), request.getMethod(), value);
	}
	private static String toStringMethodDivChange(String content_type, String form_type, String value){
		if(toString(content_type).indexOf("multipart/form-data")>-1){ 	// multipart/form-data
			return toKor(toString(value));	// Dev
		}else if(form_type.equals("GET")){								// GET
			return toString(value);			// Dev
		}else{															// POST
			return toString(value);			// Dev
		}
	}
	public static String toKor(String value){
		String str = "";
		if(value!=null && !value.equals("")){
			try{
				str = new String(value.getBytes("ISO-8859-1"), DEFAULT_ENCODING);
				//str = new String(value.getBytes("ISO-8859-1"), "UTF-8");
			}catch(java.io.UnsupportedEncodingException e){
				e.printStackTrace();
			}
		}
		return str;
	}
	private static String htmlSpecialChars(String s){
		StringBuffer buffer = new StringBuffer();
		StringTokenizer st = new StringTokenizer(s, "&\"<>", true);
		String token;
		while(st.hasMoreTokens()) {
			token = st.nextToken();
			switch(token.charAt(0)) {
			case '&':	buffer.append("&amp;"); break;
			case '\"':	buffer.append("&quot;"); break;
			case '<':	buffer.append("&lt;"); break;
			case '>':	buffer.append("&gt;"); break;
			default:	buffer.append(token);
			}
		}
		return buffer.toString();
	}
	public static String toStringYmd(String value){
		String cfinput = "";
		try{cfinput = new SimpleDateFormat("yyyy-MM-dd").format(Timestamp.valueOf(value + " 00:00:00"));}catch(Exception e){}
		return value.equals(cfinput)&&cfinput.length()>0 ? toString(value) : "";
	}
}
