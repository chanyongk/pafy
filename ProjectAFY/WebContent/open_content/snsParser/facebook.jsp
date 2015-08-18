<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@
page import="programs.facebook.*"%><%@
page import="java.io.*"%><%@
page import="java.util.*"%><%@
page import="org.json.simple.parser.*"%><%@
page import="org.json.simple.*"%><%
if(1==0){//Facebook img dir all Remove
	FacebookUtil.deleteFiles();
}
List<FacebookVo> fblist = FacebookUtil.getFacebookCon();
%>
<html>
<head>
<title>페이스북</title>
</html>
<%if(fblist!=null){%>
<div>
	<%for(FacebookVo fbVo : fblist){%>
	<dl>
		<dt>
			<a title="새창열림" href="https://www.facebook.com/afy0817" target="_blank">ProjectAllForYou</a>
		</dt>
		<dd>
			<a href="<%=fbVo.getFb_url()%>">
				<%if(!fbVo.getFb_img().equals("noImg")){%>
					<img src="<%=fbVo.getFb_img()%>"/><br/>
				<%}%>
				<%=fbVo.getFb_content()%> (<%=fbVo.getReg_dt()%>)
			</a>
		</dd>
	</dl>
	<%}%>
</div>
<%}else{%>
데이터가 없따
<%}%>