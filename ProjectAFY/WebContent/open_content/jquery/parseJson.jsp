<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html lang="ko">
	<head>		
		<title>강남의료관광</title>
		<meta charset=utf-8 />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<link rel="stylesheet" type="text/css" href="/open_content/ko/css/master.css" />
		<script src="http://code.jquery.com/jquery.js"></script>
		<script type="text/javascript">
			$.ajax({
				url: "https://graph.facebook.com/gangnammedicaltourcenter/feed?fields=actions,message,object_id&limit=5&access_token=CAAVRgnhd6QwBAO5sOeQKIqoUqliWjeqQW6pqY91OEeB1gjZAYgMbZBRlCk9ZCKYMiYVZAS7Nf3pXw5hmRB49yCauEumx3ZB9SIDcTop7zZAdKOZAG445QdIbU0VqXNRr3cswZCZAQ7gTP0ZAiiY5GCinZBDXXqhPiF6lxs1ROLaiR0tVahZC4Btoyn62qK0Hlz7QWZBUUw8vU9ZBZCIR7BuNP7dHhTr",
				dataType: "jsonp",//크로스브라우징(브라우저별로 Json 파싱에러때문에 생긴 데이터 타입)
				timeout:10000,//30초 동안 반응이 없을때 실패
				success:function(json){//성공
					var item = json.data;//item = json파일안의 data 하위 컨텐츠
					try{//데이터를 정상적으로 가져왔을때
						for (var i=0; i<item.length; i++){
							if(item[i].message!=null){
								$("#sns_content"+i).html("<a title=\"새창\" target=\"_blank\" href=\""+item[i].actions[1].link+"\">"+item[i].message.replace("\\","%")+"</a>");//내용
							}
							if(item[i].object_id!=null){//올린사진이 있을때(URL에서 긁어온사진 포함X)
								$("#sns_photo"+i).append("<img id=\"fb_picture"+i+"\" alt=\"Facebook Image"+i+"\" src=\"http://graph.facebook.com/"+item[i].object_id+"/picture?type=normal\" />");
							}//이미지

							$("#sns_date"+i).append(item[i].created_time.substring(0,10));//작성일 ex)2014-10-21
						}
					}catch(err){//데이터를 정상적으로 가져오지 못했을때
						$("#ErrMsg").html(err);//ErrMsg 엘리먼트에 에러메세지 기록
						$("#facebook_server").css("display","none");//페이스북 서버타입을 가리고
						$("#gangnam_server").css("display","block");//강남서버타입을 보여줌
					}
				},fail:function(){//실패
					$("#facebook_server").css("display","none");//페이스북 서버타입을 가리고
					$("#gangnam_server").css("display","block");//강남서버타입을 보여줌
				}
			});
		</script>
	</head>
	<body class="sns" style="background-color:transparent; overflow-x:hidden;">
		<div  id="ErrMsg"></div>	
		<div class="sns_con">
			<div class="textcont" id="facebook_server">
				<%for(int i = 0; i<5; i++){%>
				<p class="writer">
					<img src="https://fbcdn-sphotos-b-a.akamaihd.net/hphotos-ak-xpa1/v/t1.0-9/1014360_204378219764314_641889396_n.png?oh=df933fd3789faff80266b23480c8a597&oe=546761B4&__gda__=1417802695_a2108041b3f1f59d6bedbd184cd06f89" width="32" alt="강남의료관광 페이스북"/>
					<a href="https://www.facebook.com/gangnammedicaltourcenter" target="_blank" title="새창">
						<span class="name" id="sns_name<%=i%>">Gangnam Medical Tour Center</span><br />
						<span class="date" id="sns_date<%=i%>"></span>
					</a>
				</p>
				<p class="content" id="sns_content<%=i%>"></p>
				<p class="photo" id="sns_photo<%=i%>"></p>
				<hr/>
				<hr/>
				<%}%>
			</div>
			<div class="textcont" id="gangnam_server" style="display:none;">데이터 못가져옴...ㅠㅠ</div>
		</div>
	</body>
</html>