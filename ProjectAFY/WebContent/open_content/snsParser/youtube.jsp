<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.common.util.*" %>
<%@ page import="programs.youtube.*" %>
<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.net.*" %>
<%
int maxResults = 50;
int timeOut = 3000;
String encode = "UTF-8";
String part="contentDetails,snippet";
String channelId = "UCZQPmPXngA0smYk6BOyb1rw";
String key = "AIzaSyDY98G3a33QZqHCvHTqRtDLP4dEDA1MsEA";
String jsonLink = "https://www.googleapis.com/youtube/v3/activities?part="+part+"&channelId="+channelId+"&maxResults="+maxResults+"&key="+key;
List<YoutubeVo> ytList = YoutubePars.getYoutubeCon();
%>
<%for(YoutubeVo ytVo : ytList){%>
<%=ytVo.getYt_title() %><br/>
<%}%>
