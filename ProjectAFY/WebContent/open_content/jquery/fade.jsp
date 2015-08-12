<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="/open_content/inc/header.jsp">
	<jsp:param value="jquery fade" name="title"/>
</jsp:include>
</head>
<body>
<img src="/ProjectAllForYou/open_content/image/afy.jpg">
<input id="btn" type="button" onclick="imgFade();" value="fadeOut">
<script type="text/javascript">
var nowSt = "on";
function imgFade(){
	if(nowSt=="on"){
		$("img").fadeOut();
		$("#btn").attr("value","fadeIn");
		nowSt = "off";
	}else{
		$("img").fadeIn();
		$("#btn").attr("value","fadeOut");
		nowSt = "on";
	}
}
</script>
</body>
</html>