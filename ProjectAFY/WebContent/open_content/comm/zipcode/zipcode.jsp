<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@
page import="java.util.*" %><%@
page import="com.common.util.*"%><%@
page import="com.common.util.page.*"%><%
request.setCharacterEncoding("UTF-8");
List<Map<String,String>> zipcodeList = new ArrayList<Map<String,String>>();
String page_btn_str = "";

// business 로직 start ------------------------------------------------------------
String zipcode_key = "g7KRHw5S%2FxE9aH8YhCuktmAGzzFuQ3ecsQrKVhBdEyeSeB5Zj82NMQG1iA7Jdt5rRMVoUDCZluUpA5inxpjKsQ%3D%3D";
String zipcode_url = "http://openapi.epost.go.kr/postal/retrieveNewAdressAreaCdSearchAllService/retrieveNewAdressAreaCdSearchAllService/getNewAddressListAreaCdSearchAll";
String srchwrd = AfyUtil.tagClean(AfyUtil.toString(request, "srchwrd",""));
int listsz = AfyUtil.toInt(request, "listsz");
int pgno = AfyUtil.toInt(request, "pageno",1);
if(listsz<5 || listsz>50){listsz = 8;}
int total = 0;
PageVo pageMap = new PageVo();
// form return field name start ---
String addr1 = AfyUtil.tagClean(AfyUtil.toString(request, "addr1", "addr1"));
String addr2 = AfyUtil.tagClean(AfyUtil.toString(request, "addr2", "addr2"));
String zipcd = AfyUtil.tagClean(AfyUtil.toString(request, "zipcd", "zipcd"));
// form return field name end ---

if(srchwrd.length()>0){
	// xml 파싱 1. start ------
	List<HashMap<String,String>> roadXmlList = XmlUtil.getXmlData3DepthHashList(
		zipcode_url
		+"?ServiceKey="+zipcode_key
		+"&srchwrd="+AfyUtil.encode(srchwrd,"UTF-8")
		+"&currentPage="+pgno
		+"&countPerPage="+listsz
	);
	// xml 파싱 1. end ------
	// xml 파싱 2. 필요 데이터만 start ------
	if(roadXmlList.size()>1){
		total = AfyUtil.toInt(roadXmlList.get(0).get("totalCount"));
		pageMap = PageUtil.getPageInfo(pgno,total,listsz,10);
		page_btn_str = PageUtil.pageBtn(pageMap,request.getRequestURI(),AfyUtil.toParamStrHtml(request, "&", "addr1,addr2,zipcd,srchwrd"));
	}			
	if(roadXmlList.size()>1){
		for(int i=1; i<roadXmlList.size();i++){
			HashMap<String,String> roadMap = roadXmlList.get(i);
			String zipcode = AfyUtil.toString(roadMap.get("zipNo"),"");
			String roadaddr = AfyUtil.toString(roadMap.get("lnmAdres"),"");
			String oldaddr = AfyUtil.toString(roadMap.get("rnAdres"),"");
			if(zipcode.length()>0){						
				String roadaddr1 = "";
				String roadaddr2 = "";
				if(roadaddr.indexOf("번길 ")>-1){
					String[] roadaddr_arr = AfyUtil.toStr_array(roadaddr, "번길 ", 2);
					roadaddr1 = (roadaddr_arr[0]+"번길").trim();
					roadaddr2 = roadaddr_arr[1].trim();
				}else if(roadaddr.indexOf("길 ")>-1){
					String[] roadaddr_arr = AfyUtil.toStr_array(roadaddr, "길 ", 2);
					roadaddr1 = (roadaddr_arr[0]+"길").trim();
					roadaddr2 = roadaddr_arr[1].trim();
				}else if(roadaddr.indexOf("로 ")>-1){
					String[] roadaddr_arr = AfyUtil.toStr_array(roadaddr, "로 ", 2);
					roadaddr1 = (roadaddr_arr[0]+"로").trim();
					roadaddr2 = roadaddr_arr[1].trim();
				}else if(roadaddr.indexOf("(")>-1){
					String[] roadaddr_arr = AfyUtil.toStr_array(roadaddr, "(", 2);
					roadaddr1 = roadaddr_arr[0].trim();
					roadaddr2 = "(" + roadaddr_arr[1].trim();
				}else{
					roadaddr1 = roadaddr;
				}					
				Map<String,String> zipmap = new HashMap<String,String>();
				zipmap.put("zip_cd", zipcode);
				zipmap.put("addr", roadaddr);
				zipmap.put("addr1", roadaddr1);
				zipmap.put("addr2", roadaddr2);
				zipmap.put("oldaddr", oldaddr);
				zipcodeList.add(zipmap);				
			}
		}
	}
	// xml 파싱 2. 필요 데이터만 end ------
}
request.setAttribute("zipcodeList",zipcodeList);
request.setAttribute("pageMap",pageMap);
request.setAttribute("page_btn_str", page_btn_str);
request.setAttribute("srchwrd", srchwrd);
request.setAttribute("addr1", addr1);
request.setAttribute("addr2", addr2);
request.setAttribute("zipcd", zipcd);
request.setAttribute("total", total);
request.getRequestDispatcher("zipcode_view.jsp").forward(request, response);
%>
