<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!doctype html>
<html lang="en">
<head>
	<meta charset="utf-8">
	<jsp:include page="/open_content/inc/header.jsp">
	<jsp:param value="jquery dialog" name="title"/>
	</jsp:include>
	<link rel="stylesheet" href="http://code.jquery.com/ui/1.11.2/themes/smoothness/jquery-ui.css">
	<script>
	$(function(){
		$("#popup_content").dialog({
			modal:true,
			height:200,
			width:200
		});
	});
	function closePopup(){
		$("#popup_content").dialog("close");
	};
	
  </script>
</head>
<body>
<div id="pop"></div>
<div id="popup_content" title="layer popup" style="background-color: skyblue;width:500px;heigth:500px">
	<input type="button" value="닫기" onclick="closePopup();">
</div>
</body>
</html>