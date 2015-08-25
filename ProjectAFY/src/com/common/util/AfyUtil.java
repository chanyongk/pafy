package com.common.util;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

public class AfyUtil {
	private static String DEFAULT_ENCODING = "UTF-8";
	private static String[] stamp_array = new String[]{"yyyy-MM-dd HH:mm:ss.SSS","yyyy-MM-dd HH:mm:ss","yyyy-MM-dd HH:mm","yyyy-MM-dd HH","yyyy-MM-dd","yyyyMMddHHmmssSSS","yyyyMMddHHmmss","yyyyMMddHHmm","yyyyMMddHH","yyyyMMdd"};
	/******************************************************************************
	* url 커넥션 설정 후 url에 대한 상태값이 200일때 url 내용을 리턴 그렇지 않을때 null 리턴
	* @param String,int
	* @return InputStream
	* @since 2015-02-04
	* @from afy0817@gmail.com
	******************************************************************************/
	public static InputStream urlCon(String url,int timeout){
		InputStream result = null;
		try{
			URL jsonUrl = new URL(url);
			HttpURLConnection urlCon = (HttpURLConnection)jsonUrl.openConnection();
			urlCon.setRequestMethod("GET");
			urlCon.setUseCaches(false);
			urlCon.setAllowUserInteraction(false);
			urlCon.setConnectTimeout(timeout);
			urlCon.setReadTimeout(timeout);
			urlCon.connect();
			if(urlCon.getResponseCode()==200){
				result = urlCon.getInputStream();
			}
		}catch(Exception e){}
		return result;
	}

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
    	Timestamp timestamp = null;
    	for(int i=0; i<stamp_array.length; i++){
    		try{
    			timestamp = toTimestamp(value, stamp_array[i]);
    		}catch(Exception e){}
    		if(timestamp!=null){break;}		
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
					result.append(str.substring(index1,index2));
					result.append(s2);
					index1 = index2 + s1.length();
					index2 = s.indexOf(s1, index1);
				}
				result.append(str.substring(index1));
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

	public static int toInt(HttpServletRequest request,String value,int revalue){
		return toInt(request.getParameter(value),revalue);
	}

	public static int toInt(String value,int revalue){
		int temp = toInt(value);
		return temp!=0?temp:revalue;
	}

	public static int toInt(HttpServletRequest request,String value){
		return toInt(request.getParameter(value));
	}

	public static int toInt(String value){
		int to_int = 0;
		try{to_int = Integer.parseInt(replaceAll(value,",",""));}catch(Exception e){}
		return to_int;
	}

	public static String toString(HttpServletRequest request,String value,String revalue){
		return toString(toString(request,value),revalue);
	}

	public static String toString(String value,String revalue){
		return toString(value).length()>0?value:revalue;
	}

	public static String[] toStr_array(String value,String split_symbol,int max_array_size){
		String[] str_array = new String[max_array_size];
		for(int i=0;i<max_array_size;i++){
			str_array[i]="";
		}
		String[] temp_array = toStr_array(value,split_symbol);
		for(int i=0;i<temp_array.length;i++){
			try{
				str_array[i]=temp_array[i];
			}catch(Exception e){}
		}
		return str_array;
	}

	public static String[] toStr_array(String value,String split_symbol){
		return replaceAll(value,split_symbol,"##d##i##v##").split("##d##i##v##");
	}

	public static String tagClean(String s){
		if(s==null){return "";}		
		StringBuffer textbuff = new StringBuffer();
		try{
			Matcher m;
			String[] html_array = split(s,"\n");
			for(String str:html_array){
				m = Patterns.IMAGEALT.matcher(str);
				if(m.find()){str = str+m.group(1);}				
				m = Patterns.SCRIPTS.matcher(str);
				str = m.replaceAll("");
				m = Patterns.STYLE.matcher(str);
				str = m.replaceAll("");
				m = Patterns.TAGS.matcher(str);
				str = m.replaceAll("");
				m = Patterns.ENTITY_REFS.matcher(str);
				str = m.replaceAll("");
				m = Patterns.WHITESPACE.matcher(str);
				str = m.replaceAll(" ");
				textbuff.append(str);
			}
		}catch(Exception e){}
		return textbuff.toString();
	}

	private static String[] split(String value,String split_symbol){
		return replaceAll(value,split_symbol,"##d##i##v##").split("##d##i##v##");
	}

	private static interface Patterns{
		@SuppressWarnings("unused")
		public static final Pattern nTAGS = Pattern.compile("<\\w+\\s+[^<]*\\s*>");
		public static final Pattern SCRIPTS = Pattern.compile("<(no)?script[^>]*>.*?</(no)?script>",Pattern.DOTALL);
		public static final Pattern STYLE = Pattern.compile("<style[^>]*>.*</style>",Pattern.DOTALL);
		public static final Pattern TAGS = Pattern.compile("<(\"[^\"]*\"|\'[^\']*\'|[^\'\">])*>");
		public static final Pattern ENTITY_REFS = Pattern.compile("&[^;]+;");
		public static final Pattern WHITESPACE = Pattern.compile("\\s\\s+");
		public static final Pattern IMAGEALT = Pattern.compile("<img[^>]*alt=[\"']?([^>\"']+)[\"']?[^>]*>");
	}

	public static String toParamStrHtml(HttpServletRequest request,String addSymbol,String names){
		return htmlSpecialChars(toParamStr(request,addSymbol,names,"&"));
	}

	public static String toParamStr(HttpServletRequest request,String addSymbol,String names,String start_symbol){
		String[] name_array = names.split(",");
		try{
			StringBuffer param = new StringBuffer();
			for(int i=0;i<name_array.length;i++){
				param.append(toString(request,name_array[i]).equals("")?"":addSymbol+name_array[i]+"="+encode(toString(request,name_array[i])));
			}
			return start_symbol + param.toString().substring(1);
		}catch(Exception e){
			return "";
		}
	}

	public static String toParamValuesAddSymbolStr(HttpServletRequest request,String name,String addSymbol){
		StringBuffer param_value_str = new StringBuffer();
		try{
			String[] param_value_array = request.getParameterValues(name);
			String content_type = request.getContentType();
			String form_type = request.getMethod();
			
			if(param_value_array!=null){
				for(int i=0;i<param_value_array.length;i++){
					param_value_str.append(toStringMethodDivChange(content_type,form_type,param_value_array[i]));
					if(i<param_value_array.length-1){
						param_value_str.append(addSymbol);
					}
				}
			}
		}catch(Exception e){
			param_value_str.append(toString(request.getParameter(name)));
		}
		return param_value_str.toString();
	}

	public static String encode(String value,String encoding){
		String result = "";
		try{result = URLEncoder.encode(value,encoding);}catch (Exception e){e.printStackTrace();}
		return result;
	}
	public static String encode(String value){
		return encode(value,DEFAULT_ENCODING);
	}
}