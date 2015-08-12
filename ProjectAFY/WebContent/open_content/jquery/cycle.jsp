<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="/open_content/inc/header.jsp"><jsp:param value="jquery serchCss" name="title"/></jsp:include>
</head>
<body>
<div id="shuffle">
	<img style="width:712px;height:492px;" src="/ProjectAllForYou/open_content/image/toon_off.png" id="img1" />
	<img style="width:712px;height:492px;" src="/ProjectAllForYou/open_content/image/toon_on.png" id="img2" />
</div>
<script type="text/javascript">
$('#shuffle').cycle({ 
    fx:     'shuffle', 
    easing: 'easeOutBack', 
    delay:  -4000 
});
</script>
</body>
</html>