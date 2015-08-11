<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="programs.sns.TistoryJson" %>
<%@ page import="java.util.*" %>
<%
String access_token = "23d6cf1381f261eb5c1773b6a2dead63_9d2f6cfea6fda12bb7a6b6eb83aa1349";
String targetUrl = "pafy";
String output = "json";
String sort = "date";
String errMsg = "";
String listUrl = "https://www.tistory.com/apis/post/list?access_token="+access_token+"&targetUrl="+targetUrl+"&output="+output+"&sort="+sort;
List<HashMap<String,String>> tistoryList = TistoryJson.getTistoryList(listUrl,3000);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
for(int i = 0; i < tistoryList.size(); i++){
	HashMap<String,String> tistoryMap = tistoryList.get(i);
	String readUrl = "https://www.tistory.com/apis/post/read?access_token="+access_token+"&targetUrl="+targetUrl+"&postId="+tistoryMap.get("id")+"&output="+output;
	HashMap<String,String> tistoryContent = TistoryJson.getTistoryContent(readUrl,3000);
%>
	<%=tistoryContent.get("title")%><br/>
	<%=tistoryContent.get("content")%><br/>
	<hr/>
<%
}
%>
</body>
</html>