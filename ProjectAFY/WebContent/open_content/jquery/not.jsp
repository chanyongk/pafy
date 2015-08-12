<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="/open_content/inc/header.jsp">
	<jsp:param value="jquery not" name="title"/>
</jsp:include>
</head>
<body>
<div>
	<img style="width:712px;height:492px;" src="/ProjectAllForYou/open_content/image/toon_off.png" id="img1" />
	<img style="width:712px;height:492px;display:none;" src="/ProjectAllForYou/open_content/image/toon_on.png" id="img2" />
</div>
<input type="button" value="turnOn" id="turnOn">
<script type="text/javascript">
var nowSt = "off";
$("#turnOn").on("click",function(){
	if(nowSt=="off"){
		$("div>").not("#img1").css("display","block");
		$("#img1").css("display","none");
		nowSt="on";
	}else{
		$("div>").not("#img2").css("display","block");
		$("#img2").css("display","none");
		nowSt="off";
	}
});
</script>
</body>
</html>