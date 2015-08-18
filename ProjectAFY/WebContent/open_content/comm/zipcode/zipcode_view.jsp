<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@
page import="java.util.*"%><%@
page import="com.common.util.*"%><%
request.setCharacterEncoding("UTF-8");
@SuppressWarnings("unchecked")
List<Map<String,String>> zipcodeList = (List<Map<String,String>>)request.getAttribute("zipcodeList");
PageVo pageMap = (PageVo)request.getAttribute("pageMap");
String page_btn_str = (String)request.getAttribute("page_btn_str");
String srchwrd = (String)request.getAttribute("srchwrd");
String addr1 = (String)request.getAttribute("addr1");
String addr2 = (String)request.getAttribute("addr2");
String zipcd = (String)request.getAttribute("zipcd");
int total = (Integer)request.getAttribute("total");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<title>우편번호 검색<%=srchwrd.length()>0?" - "+srchwrd:"" %></title>
		<link rel="stylesheet" type="text/css" href="<%=(request.getRequestURL()+"").replaceAll("zipcode_view.jsp", "")%>css/style.css" />
		<script type="text/javascript" src="<%=(request.getRequestURL()+"").replaceAll("zipcode_view.jsp", "")%>js/jquery-1.7.2.min.js"></script>
	</head>
	<body>
		<div>
			<div class="wait" style="display:none">
				<div class="icon">
					<span class="text">데이터 로딩중입니다.</span>
				</div>
			</div>
			<p class="txt padding_l20">
				<img src="<%=(request.getRequestURL()+"").replaceAll("zipcode_view.jsp", "")%>images/street_txt.gif" alt=" 도로명, 건물명, 지번에 대해 통합검색이 가능합니다. (예: 반포대로 58, 국립중앙박물관, 삼성동 25)" />
			</p>
			<form action="<%=(request.getRequestURL()+"").replaceAll("zipcode_view.jsp", "")%>zipcode.jsp" method="get" onsubmit="return fnSearch(this);">
				<p>
					<input type="hidden" name="addr1" value="<%=addr1 %>" />
					<input type="hidden" name="addr2" value="<%=addr2 %>" />
					<input type="hidden" name="zipcd" value="<%=zipcd %>" />
				</p>
				<div class="input">
					<input type="text" value="<%=srchwrd%>" name="srchwrd" id="srchwrd" />
					<input type="image" src="<%=(request.getRequestURL()+"").replaceAll("zipcode_view.jsp", "")%>images/btn_street.gif" alt="검색" />
				</div>
			</form>
			<%if(zipcodeList.size()>0){%>
				<p class="now_page" style="text-align:right; margin-right:30px;">
					<%=pageMap.getNowPage()%>/<span><%=pageMap.getTotalPage() %></span><br />
					총 <span><%=total%></span>건의 데이터가 검색되었습니다.
				</p>
				<div class="result">
					<ul class="list">
						<%for(Map<String,String> zipMap : zipcodeList){%>
							<li>
								<p class="address">
									<a href="#" onclick="return fnFormSet('<%=zipMap.get("zip_cd") %>','<%=zipMap.get("addr1") %>','<%=zipMap.get("addr2") %>');">
										<span>도로명</span> : [<%=zipMap.get("zip_cd")%>] <%=zipMap.get("addr")%><br />
										<span>지&nbsp;&nbsp;&nbsp;번</span> : [<%=zipMap.get("zip_cd")%>] <%=zipMap.get("oldaddr")%>
									</a>
								</p>
								<p class="btn">
									<a href="#" onclick="return fnFormSet('<%=zipMap.get("zip_cd") %>','<%=zipMap.get("addr1") %>','<%=zipMap.get("addr2") %>');">
										<img alt="선택" src="<%=(request.getRequestURL()+"").replaceAll("zipcode_view.jsp", "")%>images/btn_select.gif" />
									</a>
								</p>
							</li>
						<%}%>
					</ul>
				</div>
				<%if(total>10){%>
					<%=page_btn_str%>
				<%}%>
			<%}else if(srchwrd.length()>0 && zipcodeList.size()==0){%>
				<div class="no_address">검색 결과가 없습니다.</div>
			<%}%>
		</div>

		<script type="text/javascript">
			$('.pages>.pageBtn>a').click(function(){
				$('.wait').css("display","block");
			});

			function fnSearch(frm){
				$('.wait').css("display","block");
				return true;
			}

			function fnFormSet(zipcd, addr1, addr2){
				if(parent.opener.document){
					var pod = parent.opener.document;
					pod.getElementById("<%=zipcd%>").value = zipcd;			//우편번호 왼쪽  3자리
					pod.getElementById("<%=addr1%>").value = addr1;
					pod.getElementById("<%=addr2%>").value = addr2;
				}
				self.close();
			}
			window.resizeTo("616", "800");
		</script>
	</body>
</html>