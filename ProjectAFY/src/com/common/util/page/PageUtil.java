package com.common.util.page;

public class PageUtil {
	/***************************************************
	@ since : 2015-08-05
	@ from : afy0817@gmail.com
	@ comment : 페이징에 필요한 정보를 HashMap에 담아 리턴
	@ nowPage:현제페이지
	@ totalCnt : 전체 레코드 수,
	@ viewRecord : 1페이지당 보여줄 레코드 수
	@ viewPage : 1그룹당 보여줄 페이지 수
	***************************************************/
	public static PageVo getPageInfo(int nowPage,int totalCnt,int viewRecord,int viewPage){
		PageVo pgVo = new PageVo();
		if(nowPage<1){nowPage = 1;}
		int endPage = nowPage*viewRecord;				
		int totalPage = totalCnt / viewRecord + (totalCnt % viewRecord>0 ? 1 : 0);
		if(nowPage>totalPage){nowPage = totalPage;}
		int endNo = (nowPage/viewPage+(nowPage%viewPage>0?1:0))*viewPage;		
		int startNo = endNo-(viewPage-1);	
		pgVo.setTotalPage(totalPage);//전체 페이지
		pgVo.setNowPage(nowPage);//현제 페이지
		pgVo.setStartPage(endPage-(viewRecord-1));//시작페이지
		pgVo.setEndPage(endPage>totalCnt?totalCnt:endPage);//마지막페이지
		pgVo.setStartNo(startNo);//현재그룹 시작번호
		pgVo.setEndNo(endNo>totalPage?totalPage:endNo);//현재그룹 끝번호
		pgVo.setPrevNo((startNo-viewPage)<1?1:(startNo-viewPage));//이전페이지번호
		pgVo.setNextNo((startNo+viewPage)>totalPage?totalPage:(startNo+viewPage));//다음페이지번호
		return pgVo;
	}

	/*************************************************
	*페이지 버튼을 StringBuffer 형식으로 생성 후 toString 후 리턴
	*************************************************/
	public static String pageBtn(PageVo pgVo,String requestURI,String paramStr){
		StringBuffer btn = new StringBuffer();
		btn.append("<div class=\"pages\">\n");
		btn.append("	<div class=\"pageBtn\">\n");
		btn.append("		<a href=\""+requestURI+"?pageno=1"+paramStr+"\" title=\"첫번째 페이지로\">&lt;&lt;</a>\n");
		btn.append("		<a href=\""+requestURI+"?pageno="+pgVo.getPrevNo()+paramStr+"\" title=\"이전 페이지로\">&lt;</a>\n");
		for(int i = pgVo.getStartNo();i<=pgVo.getEndNo();i++){
			if(pgVo.getNowPage() == i){
				btn.append("		<p class=\"nowPage\">"+i+"</p>\n");
			}else{
				btn.append("		<a href=\""+requestURI+"?pageno="+i+paramStr+"\" title=\""+i+" 페이지로\">"+i+"</a>\n");
			}
		}
		btn.append("		<a href=\""+requestURI+"?pageno="+pgVo.getNextNo()+paramStr+"\" title=\"다음 페이지로\">&gt;</a>\n");
		btn.append("		<a href=\""+requestURI+"?pageno="+pgVo.getTotalPage()+paramStr+"\" title=\"마지막 페이지로\">&gt;&gt;</a>\n");
		btn.append("	</div>\n");
		btn.append("</div>\n");
		return btn.toString();
	}
}
