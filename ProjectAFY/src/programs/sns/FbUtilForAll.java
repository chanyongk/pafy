package programs.sns;

import java.io.*;
import java.net.*;
import java.util.*;
import org.json.simple.*;
import org.json.simple.parser.*;

public class FbUtilForAll{
	/*AfyUtil을 쓰지 않고 만든 페이스북 유틸*/
	/******************************************************************************
	* url 커넥션 설정 후 url에 대한 상태값이 200일때 url 내용을 리턴 그렇지 않을때 null 리턴
	* @param String,int
	* @return InputStream
	* @since 2014-11-13
	* @afy0817 : afy0817@gmail.com
	******************************************************************************/
	public static InputStream urlCon(String url,int timeout){
		InputStream result = null;
		try{
			URL jsonUrl = new URL(url);
			HttpURLConnection urlCon = (HttpURLConnection)jsonUrl.openConnection();
			urlCon.setRequestMethod("GET");
			urlCon.setUseCaches(false);
			urlCon.setAllowUserInteraction(false);
			urlCon.setConnectTimeout(timeout);
			urlCon.setReadTimeout(timeout);
			urlCon.connect();
			if(urlCon.getResponseCode()==200){
				result = urlCon.getInputStream();
			}
		}catch(Exception e){
		}
		return result;
	}

	/******************************************************************************
	* urlCon 에서 받아온 값이 null 이 아닐때 받아온 값(json형식) 파싱 후 리턴
	* @param String,int
	* @return List<HashMap<String,String>>
	* @since 2014-11-13
	* @afy0817 : afy0817@gmail.com
	******************************************************************************/
	public static List<HashMap<String,String>> getFacebookCon(String url, int timeout){
		List<HashMap<String, String>> result = null;
		if(urlCon(url,timeout)!=null){result=fbArr(urlCon(url,timeout),"UTF-8");}
		return result;
	}

	/******************************************************************************
	* urlCon 에서 받아온 값(Json형식)을 받아온 인코딩 값으로 파싱 후 List<HashMap<String,String>> 으로 리턴
	* @param InputStream,String
	* @return List<HashMap<String,String>>
	* @since 2014-11-13
	* @afy0817 : afy0817@gmail.com
	******************************************************************************/
	public static List<HashMap<String,String>> fbArr(InputStream jsonCon,String encode){
		List<HashMap<String, String>> fblist = new ArrayList<HashMap<String,String>>();
		JSONParser parser = new JSONParser();
		StringBuilder sb = new StringBuilder();
		String line;
		try{
			BufferedReader br = new BufferedReader(new InputStreamReader(jsonCon,encode));
			while((line=br.readLine()) != null){sb.append(line);}br.close();
			JSONObject postsObj = (JSONObject)((JSONObject)parser.parse(sb.toString())).get("posts");
			JSONArray dataArr = (JSONArray)postsObj.get("data");
			for(int i = 0; i < dataArr.size(); i++){
				HashMap<String,String> fbArr = new HashMap<String,String>();
				String[] linkArr = (((JSONObject)dataArr.get(i)).get("id")+"").split("_");
				if(((JSONObject)dataArr.get(i)).get("message")!=null){
				fbArr.put("time",((JSONObject)dataArr.get(i)).get("created_time")+"");
				fbArr.put("link","https://www.facebook.com/"+linkArr[0]+"/posts/"+linkArr[1]);
				fbArr.put("message",((JSONObject)dataArr.get(i)).get("message")+"");
				if(((JSONObject)dataArr.get(i)).get("object_id")!=null){
					fbArr.put("image","http://graph.facebook.com/"+((JSONObject) dataArr.get(i)).get("object_id")+"/picture?type=normal");
				}else{
					fbArr.put("image","noImg");
				}
					fblist.add(fbArr);
				}
			}
		}catch(Exception e){}
		return fblist;
	}
}