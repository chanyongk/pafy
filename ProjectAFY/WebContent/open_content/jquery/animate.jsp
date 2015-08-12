<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<jsp:include page="/open_content/inc/header.jsp">
	<jsp:param value="jquery animate" name="title"/>
</jsp:include>
</head>
<body>
<img id="moveCon" src="/open_content/image/book.gif" style="position: absolute;"/>

<div style="position: fixed;">
<select id="position">
<option value="left">left</option>
<option value="right">right</option>
<option value="top">top</option>
<option value="bottom">bottom</option>
</select>
<input type="text" value="100" name="moveVal" id="moveVal"/>
<input type="button" id="startAnimate" value="move">
<p>aaaa</p>
</div>
<script type="text/javascript">
$("#startAnimate").on("click",function(){
	if($("#position").val()=="left"){
		$("#moveCon").animate({"left": "-="+$("#moveVal").val()+"px"}, "slow");
	}else if($("#position").val()=="right"){
		$("#moveCon").animate({"left": "+="+$("#moveVal").val()+"px"}, "slow");
	}else if($("#position").val()=="top"){
		$("#moveCon").animate({"top": "-="+$("#moveVal").val()+"px"}, "slow");
	}else if($("#position").val()=="bottom"){
		$("#moveCon").animate({"top": "+="+$("#moveVal").val()+"px"}, "slow");
	}
});
</script>
</body>
</html>