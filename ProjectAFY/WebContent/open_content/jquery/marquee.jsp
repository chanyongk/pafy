<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<jsp:include page="/open_content/inc/header.jsp">
	<jsp:param value="jquery marquee" name="title"/>
</jsp:include>
<style>
div.noticBg {width:11px;padding:10px;left:1px;top:14px;position:relative;background:#fff url('http://gnmedical.dofnet.kr/open_content/ko/images/main/icon_notice.gif') no-repeat 5px center;}
ul.marquee {
margin-top: 10px;
position: absolute;
border:1px solid #ccc;
padding:5px;
background:#fff;
clear:both;
overflow:hidden;
width: 95%;
height: 20px;
}

ul.marquee li {
position: absolute;
top: -999em;
margin-left:33px;
display: block;
white-space: nowrap;
padding: 3px 5px;
}
span.title {font-weight:bold;background:none;display:inline;}
a:link, a:visited {color:#656565;text-decoration:none;}
</style>
</head>
<body>
<ul class="marquee">
	<li class="txt">
		<a href="/open_content/ko/bbs/bbsMsgDetail.do?bcd=notice_ko&amp;msg_seq=437">
			<span class="title">2014 외국인환자유치활동지원사업 공모</span>
			1. 공모기간 : 2014. 8. 06(수) ~ 8. 19(금) [2주간] 
		</a>
	</li>
	
	<li class="txt">
		<a href="/open_content/ko/bbs/bbsMsgDetail.do?bcd=notice_ko&amp;msg_seq=437">
			<span class="title">Отель «The Designers» Отель, в интерьере котого приняли</span>
			небольшой, но по истине инновационный, совершенно другой отель, тогда приглашаем Вас в отель «The Designers». В каждом уголке отеля чувствуется мастерство 15 молодых корейских дизайнера, которое невозможно скрыть. Иностранц
		</a>
	</li>
</ul>
<div class="noticBg"></div>
<script type="text/javascript">
	$(document).ready(function (){
		$(".marquee").marquee({
			loop: -1,
			init:"init",
			show:"show",
			pauseSpeed:3000,
			pauseOnHover: true
		});
	});
</script>
</body>
</html>