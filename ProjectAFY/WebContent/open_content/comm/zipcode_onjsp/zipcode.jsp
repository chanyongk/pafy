<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@
page import="java.net.*"%><%@
page import="java.util.*" %><%@
page import="java.util.regex.*"%><%@
page import="javax.xml.parsers.*"%><%@
page import="org.w3c.dom.*"%><%!
private static String pageEncoding = "UTF-8";

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

private static String replaceAll(String str,String s1,String s2){
	String s = str.toLowerCase();
	s1 = s1.toLowerCase();
	s2 = s2.toLowerCase();
	StringBuffer result = new StringBuffer();
	int index1 = 0;
	int index2 = s.indexOf(s1);
	while(index2>=0){
		result.append(str.substring(index1,index2));
		result.append(s2);
		index1 = index2 + s1.length();
		index2 = s.indexOf(s1,index1);
	}
	result.append(str.substring(index1));
	return result.toString();
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

public static String toString(HttpServletRequest request,String value){
	return toStringMethodDivChange(request,toParamValuesAddSymbolStr(request,value,","));
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

public static String toString(String value){
	return(value==null||value.trim().equals("")||value.equals("null"))?"":value;
}

private static String toStringMethodDivChange(HttpServletRequest request,String value){
	return toStringMethodDivChange(request.getContentType(),request.getMethod(),value);
}

private static String toStringMethodDivChange(String content_type,String form_type,String value){
	if(toString(content_type).indexOf("multipart/form-data")>-1){
		return toString(value);
	}else if(form_type.equals("GET")){
		return toString(value);
	}else{
		return toString(value);
	}
}
public static String encode(String value){
	String result = "";
	try{result = URLEncoder.encode(value,pageEncoding);}catch (Exception e){e.printStackTrace();}
	return result;	
}

private static String htmlSpecialChars(String s){
	StringBuffer buffer = new StringBuffer();
	StringTokenizer st = new StringTokenizer(s,"&\"<>",true);
	String token;
	while(st.hasMoreTokens()){
		token = st.nextToken();
		switch(token.charAt(0)){
		case '&': buffer.append("&amp;");break;
		case '\"': buffer.append("&quot;");break;
		case '<': buffer.append("&lt;");break;
		case '>': buffer.append("&gt;");break;
		default: buffer.append(token);
		}
	}
	return buffer.toString();
}

public static List<HashMap<String,String>> getXmlData3DepthHashList(String xml_path){
	return getXmlData3DepthHashList(getXmlNodeList(xml_path));	
}

private static NodeList getXmlNodeList(String xml_path){
	NodeList nodelist = null;
	try{
		String pharm_source_xml = xml_path;
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder parser = factory.newDocumentBuilder();
		Document doc = parser.parse(pharm_source_xml);
		Element rootElement = doc.getDocumentElement();
		nodelist = rootElement.getChildNodes();
	}catch(Exception e){}
	return nodelist;	
}

private static List<HashMap<String,String>> getXmlData3DepthHashList(NodeList nodelist){
	List<HashMap<String,String>> xmldatalist = new ArrayList<HashMap<String,String>>();
	try{
		NodeList node1lvChildList = nodelist;
		for(int i=0;i<node1lvChildList.getLength();i++){
			Node node2lv = node1lvChildList.item(i);
			NodeList node2lvChildList = node2lv.getChildNodes();
			if(node2lvChildList.getLength()>0){
				xmldatalist.add(getXmlDataHash(node2lvChildList));
			}
		}
	}catch(Exception e){}
	return removeEmptyXmlData(xmldatalist);	
}

private static List<HashMap<String,String>> removeEmptyXmlData(List<HashMap<String,String>> xmldatalist){
	if(xmldatalist!=null && xmldatalist.size()>0){
		for(int i=xmldatalist.size();i>0;i--){
			if(xmldatalist.get(i-1)==null||xmldatalist.get(i-1).isEmpty()){
				xmldatalist.remove(i-1);
			}
		}	
	}
	return xmldatalist;
}

private static HashMap<String,String> getXmlDataHash(NodeList nodeList){
	HashMap<String,String> xmldatahash = new HashMap<String,String>();
	try{
		for(int i=0;i<nodeList.getLength();i++){
			Node node = nodeList.item(i);
			if(node.getNodeType() == Node.ELEMENT_NODE){
				String value_str = "";
				try{value_str = node.getFirstChild().getNodeValue();}catch(Exception e){}
				if(value_str==null||value_str.equals("null")){value_str = "";}
				xmldatahash.put(node.getNodeName(),value_str);
			}
		}
	}catch(Exception e){}
	return xmldatahash;	
}

/***************************************************
@ since : 2015-08-05
@ from : afy0817@gmail.com
@ comment : 페이징에 필요한 정보를 HashMap에 담아 리턴
@ nowPage:현제페이지
@ totalCnt : 전체 레코드 수,
@ viewRecord : 1페이지당 보여줄 레코드 수
@ viewPage : 1그룹당 보여줄 페이지 수
***************************************************/
public HashMap<String,Integer> pageMap(int nowPage,int totalCnt,int viewRecord,int viewPage){
	HashMap<String,Integer> pageMap = new HashMap<String,Integer>();
	if(nowPage<1){nowPage = 1;}
	int endPage = nowPage*viewRecord;				
	int startPage = endPage-(viewRecord-1);
	if(endPage>totalCnt){endPage = totalCnt;}
	int totalPage = totalCnt / viewRecord + (totalCnt % viewRecord>0 ? 1 : 0);
	if(nowPage>totalPage){nowPage = totalPage;}
	int nowGroup = nowPage/viewPage+(nowPage%viewPage>0?1:0);
	int endNo = nowGroup*viewPage;		
	int startNo = endNo-(viewPage-1);	
	if(endNo>totalPage){endNo=totalPage;}
	int prevNo = startNo-viewPage;
	int nextNo = startNo+viewPage;
	if(prevNo<1){prevNo=1;}
	if(nextNo>totalPage){nextNo=totalPage;}
	pageMap.put("totalPage",totalPage);//전체 페이지
	pageMap.put("nowPage",nowPage);//현제 페이지
	pageMap.put("startPage",startPage);//시작페이지
	pageMap.put("endPage",endPage);//마지막페이지
	pageMap.put("startNo",startNo);//현재그룹 시작번호
	pageMap.put("endNo",endNo);//현재그룹 끝번호
	pageMap.put("prevNo",prevNo);//이전페이지번호
	pageMap.put("nextNo",nextNo);//다음페이지번호
	return pageMap;
}
public String pageBtn(HashMap<String,Integer> pageMap,String requestURI,String paramStr){
	StringBuffer btn = new StringBuffer();
	btn.append("<div class=\"pages\">\n");
	btn.append("	<div class=\"pageBtn\">\n");
	btn.append("		<a href=\""+requestURI+"?pageno=1"+paramStr+"\" title=\"첫번째 페이지로\">&lt;&lt;</a>\n");
	btn.append("		<a href=\""+requestURI+"?pageno="+pageMap.get("prevNo")+paramStr+"\" title=\"이전 페이지로\">&lt;</a>\n");
	for(int i = pageMap.get("startNo");i<=pageMap.get("endNo");i++){
		if(pageMap.get("nowPage") == i){
			btn.append("		<p class=\"nowPage\">"+i+"</p>\n");
		}else{
			btn.append("		<a href=\""+requestURI+"?pageno="+i+paramStr+"\" title=\""+i+" 페이지로\">"+i+"</a>\n");
		}
	}
	btn.append("		<a href=\""+requestURI+"?pageno="+pageMap.get("nextNo")+paramStr+"\" title=\"다음 페이지로\">&gt;</a>\n");
	btn.append("		<a href=\""+requestURI+"?pageno="+pageMap.get("totalPage")+paramStr+"\" title=\"마지막 페이지로\">&gt;&gt;</a>\n");
	btn.append("	</div>\n");
	btn.append("</div>\n");
	return btn.toString();
}
%><%
request.setCharacterEncoding("UTF-8");
List<Map<String,String>> zipcodeList = new ArrayList<Map<String,String>>();
String page_btn_str = "";

// business 로직 start ------------------------------------------------------------
String zipcode_key = "g7KRHw5S%2FxE9aH8YhCuktmAGzzFuQ3ecsQrKVhBdEyeSeB5Zj82NMQG1iA7Jdt5rRMVoUDCZluUpA5inxpjKsQ%3D%3D";
String zipcode_url = "http://openapi.epost.go.kr/postal/retrieveNewAdressAreaCdSearchAllService/retrieveNewAdressAreaCdSearchAllService/getNewAddressListAreaCdSearchAll";
String srchwrd = tagClean(toString(request, "srchwrd",""));
int listsz = toInt(request, "listsz");
int pgno = toInt(request, "pageno",1);
if(listsz<5 || listsz>50){listsz = 8;}
int total = 0;
HashMap<String,Integer> pageMap = new HashMap<String,Integer>();
// form return field name start ---
String addr1 = tagClean(toString(request, "addr1", "addr1"));
String addr2 = tagClean(toString(request, "addr2", "addr2"));
String zipcd = tagClean(toString(request, "zipcd", "zipcd"));
// form return field name end ---

if(srchwrd.length()>0){
	// xml 파싱 1. start ------
	List<HashMap<String,String>> roadXmlList = getXmlData3DepthHashList(
		zipcode_url
		+"?ServiceKey="+zipcode_key
		+"&srchwrd="+encode(srchwrd)
		+"&currentPage="+pgno
		+"&countPerPage="+listsz
	);
	// xml 파싱 1. end ------
	// xml 파싱 2. 필요 데이터만 start ------
	if(roadXmlList.size()>1){
		total = toInt(roadXmlList.get(0).get("totalCount"));
		pageMap = pageMap(pgno,total,listsz,10);
		page_btn_str = pageBtn(pageMap,request.getRequestURI(),toParamStrHtml(request, "&", "addr1,addr2,zipcd,srchwrd"));
	}			
	if(roadXmlList.size()>1){
		for(int i=1; i<roadXmlList.size();i++){
			HashMap<String,String> roadMap = roadXmlList.get(i);
			String zipcode = toString(roadMap.get("zipNo"),"");
			String roadaddr = toString(roadMap.get("lnmAdres"),"");
			String oldaddr = toString(roadMap.get("rnAdres"),"");
			if(zipcode.length()>0){						
				String roadaddr1 = "";
				String roadaddr2 = "";
				if(roadaddr.indexOf("번길 ")>-1){
					String[] roadaddr_arr = toStr_array(roadaddr, "번길 ", 2);
					roadaddr1 = (roadaddr_arr[0]+"번길").trim();
					roadaddr2 = roadaddr_arr[1].trim();
				}else if(roadaddr.indexOf("길 ")>-1){
					String[] roadaddr_arr = toStr_array(roadaddr, "길 ", 2);
					roadaddr1 = (roadaddr_arr[0]+"길").trim();
					roadaddr2 = roadaddr_arr[1].trim();
				}else if(roadaddr.indexOf("로 ")>-1){
					String[] roadaddr_arr = toStr_array(roadaddr, "로 ", 2);
					roadaddr1 = (roadaddr_arr[0]+"로").trim();
					roadaddr2 = roadaddr_arr[1].trim();
				}else if(roadaddr.indexOf("(")>-1){
					String[] roadaddr_arr = toStr_array(roadaddr, "(", 2);
					roadaddr1 = roadaddr_arr[0].trim();
					roadaddr2 = "(" + roadaddr_arr[1].trim();
				}else{
					roadaddr1 = roadaddr;
				}					
				Map<String,String> zipmap = new HashMap<String,String>();
				zipmap.put("zip_cd", zipcode);
				zipmap.put("addr", roadaddr);
				zipmap.put("addr1", roadaddr1);
				zipmap.put("addr2", roadaddr2);
				zipmap.put("oldaddr", oldaddr);
				zipcodeList.add(zipmap);				
			}
		}
	}
	// xml 파싱 2. 필요 데이터만 end ------
}
request.setAttribute("zipcodeList",zipcodeList);
request.setAttribute("pageMap",pageMap);
request.setAttribute("page_btn_str", page_btn_str);
request.setAttribute("srchwrd", srchwrd);
request.setAttribute("addr1", addr1);
request.setAttribute("addr2", addr2);
request.setAttribute("zipcd", zipcd);
request.setAttribute("total", total);
request.getRequestDispatcher("zipcode_view.jsp").forward(request, response);
%>
