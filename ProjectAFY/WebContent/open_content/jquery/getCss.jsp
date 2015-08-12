<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<jsp:include page="/open_content/inc/header.jsp"><jsp:param value="jquery getCss" name="title"/></jsp:include>
		<style type="text/css">
		div{height:50px;padding:5px;margin:5px;float:left;}
		</style>
	</head>
	<body>
		<p id="result"></p>
		<div style="background-color:red;width:30px;color:skyblue;">1</div>
		<div style="background-color:yellow;width:100px;color:blue">1</div>
		<div style="background-color:blue;width:50px;color:yellow">1</div>
		<div style="background-color:black;width:70px;color:white">1</div>
		<script type="text/javascript">
		$("div").click(function(){
			var htmlStr = ["선택한 div의 스타일속성 : "];
			var color=$(this).css(["width", "height", "color", "background-color", "margin", "padding", "float"]);
			$.each(color, function(prop, value ){htmlStr.push(prop+" : "+value);});
			$("#result").html(htmlStr.join("<br/>"));
		});
		</script>
	</body>
</html>