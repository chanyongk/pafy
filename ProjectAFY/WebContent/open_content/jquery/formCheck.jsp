<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="/open_content/inc/header.jsp"><jsp:param value="form check" name="title"/></jsp:include>
</head>
<body>
<form id="dateChk" name="dateChk" action="this" method="post" onsubmit="frmChk();" enctype="multipart/form-data">
<input type="text" id="dateArea" />
<input type="submit" id="chkSubmit">
</form>
<script type="text/javascript">
var nowDate = 20150106;
function frmChk(){
	if($('#dateArea').val()>nowDate){
		confirm('aaa');
	}
}
</script>
</body>
</html>