<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@
page import="programs.sns.*"%><%@
page import="java.io.*"%><%@
page import="java.util.*"%><%@
page import="org.json.simple.parser.*"%><%@
page import="org.json.simple.*"%><%
if(1==0){//저장한 페이스북 경로의 파일 전체삭제
	FacebookJson.deleteFiles();
}
List<HashMap<String,String>> fblist = FacebookJson.getFacebookCon("https://graph.facebook.com/v2.2/afy0817?fields=picture,likes,posts.limit(10){message,link,object_id}&access_token=556185237786210|42a976250600e2f8fe3be94592082514",30000);
%>
<html>
<head>
<title>페이스북</title>
</html>
<%if(fblist!=null){%>
<div>
	<%for(HashMap<String,String> fbArr : fblist){%>
	<dl>
		<dt>
			<a title="새창열림" href="https://www.facebook.com/afy0817" target="_blank">ProjectAllForYou</a>
		</dt>
		<dd>
			<a href="<%=fbArr.get("link")%>">
				<%if(!fbArr.get("image").equals("noImg")){%>
					<img src="<%=fbArr.get("image")%>"/><br/>
				<%}%>
				<%=fbArr.get("message")%> (<%=fbArr.get("time")%>)
			</a>
		</dd>
	</dl>
	<%}%>
</div>
<%}else{%>
데이터가 없따
<%}%>