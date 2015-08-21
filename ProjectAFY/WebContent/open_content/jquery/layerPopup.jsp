<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="/open_content/inc/header.jsp">
	<jsp:param value="jquery layer popup" name="title"/>
	</jsp:include>
<style type="text/css">
	.lp{
		display:none;
		position:absolute;
		top:50px;
		left:50px;
		width:410px;
		height:auto;
		background-color:#fff;
		border:5px solid #3571B5;
		z-index:10;
	}
</style>
<script type="text/javascript">
	function openPopup(layerId){
		var layer = $("#"+layerId);//레이어 이름
		
		if(layer.outerHeight()<$(document).height()){
			layer.css("margin-top","-"+layer.outerHeight()/2+"px");
		}else{
			layer.css("top","0px");
		}
		if(layer.outerWidth()<$(document).width()){
			layer.css("margin-top","-"+layer.outerWidth()/2+"px");
		}else{
			layer.css("left","0px");
		}
		layer.fadeIn();

		layer.find("#close").click(function(){
			layer.fadeOut();
		});
	}
</script>

</head>
<body style="background-color: gray">
<input type="button" onclick="openPopup('lp1');" value="layer 1 open">
<input type="button" onclick="openPopup('lp2');" value="layer 2 open">

<div id="lp1" class="lp">
1번 레이어
<input type="button" id="close" value="layer 1 close">
</div>

<div id="lp2" class="lp">
2번 레이어
<input type="button" id="close" value="layer 2 close">
</div>
</body>
</html>