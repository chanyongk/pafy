<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="/open_content/inc/header.jsp">
	<jsp:param value="jquery dialog" name="title"/>
	</jsp:include>
<style>
	.tMenu{list-style:none;float:left;}
	.tMenu>li{float:left;width:150px;}
	.tSubMenu{display:none;}
</style>
<header>
<a href="a" id="aaa">aa</a>
<div id="bg">
<nav>
	<ul class="tMenu">
		<li class="m"><a href="/">Jquery</a>
			<div id="subm1" class="tSubMenu">
				<ul>
					<li><a href="/">Animate</a></li>
					<li><a href="/">getCss</a></li>
					<li><a href="/">searchCss</a></li>
				</ul>
			</div>
		</li>
		<li class="m"><a href="/">Util</a>
			<div id="subm2" class="tSubMenu">
				<ul>
					<li><a href="/">captcha</a></li>
					<li><a href="/">TTS</a></li>
					<li><a href="/">Recaptcha</a></li>
				</ul>
			</div>
		</li>
		<li class="m"><a href="/">SNS</a>
			<div id="subm3" class="tSubMenu">
				<ul>
					<li><a href="/">Twitter</a></li>
					<li><a href="/">Facebook</a></li>
					<li><a href="/">Blog</a></li>
				</ul>
			</div>
		</li>
	</ul>
</nav>
</div>
<a href="a">aa</a>
</header>
<script type="text/javascript">
$("nav>ul>li").mouseenter(function(){
	if($(this).find("div.tSubMenu").length>0){
		$tSub = $(this).find("div.tSubMenu");
		$tSub.show();
	}
});
	
$("nav>ul>li").mouseleave(function(){
	if($(this).find("div.tSubMenu").length>0){
		$tSub = $(this).find("div.tSubMenu");
		$tSub.hide();
	}
});
$("#aaa").focusout(function(){
	alert('focusOut');
}).focusin(function(){
	alert('focusIn');
});
</script>