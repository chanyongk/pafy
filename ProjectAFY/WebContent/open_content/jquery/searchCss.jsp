<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="/open_content/inc/header.jsp"><jsp:param value="jquery serchCss" name="title"/></jsp:include>
</head>
<body>
<p>
  Once upon a time there was a man
  who lived in a pizza parlor. This
  man just loved pizza and ate it all
  the time.  He went on to be the
  happiest man in the world.  The end.
</p>
<script>
var words = $( "p" ).first().text().split(" ");
var text = words.join( "</span> <span>" );
$( "p" ).first().html( "<span>" + text + "</span>" );
$( "span" ).on( "click", function() {
  $( this ).css( "background-color", "yellow" );
});
</script>
</body>
</html>