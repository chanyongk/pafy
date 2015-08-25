package programs.sns;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.common.util.AfyUtil;

public class TistoryJson {
	/******************************************************************************
	* urlCon 에서 받아온 값이 null 이 아닐때 받아온 값(json형식) 파싱 후 리턴
	* @param String,int
	* @return <HashMap<String,String>>
	* @since 2014-11-13
	* @afy0817 : afy0817@gmail.com
	******************************************************************************/
	public static HashMap<String,String> getTistoryContent(String url, int timeout){
		HashMap<String, String> result = null;
		InputStream urlContent = AfyUtil.urlCon(url,timeout);
		if(urlContent!=null){result=tistoryContentMap(urlContent,"UTF-8");}
		return result;
	}

	/******************************************************************************
	* urlCon 에서 받아온 값이 null 이 아닐때 받아온 값(json형식) 파싱 후 리턴
	* @param String,int
	* @return List<HashMap<String,String>>
	* @since 2014-11-13
	* @afy0817 : afy0817@gmail.com
	******************************************************************************/
	public static List<HashMap<String,String>> getTistoryList(String url, int timeout){
		List<HashMap<String, String>> result = null;
		InputStream urlContent = AfyUtil.urlCon(url,timeout);
		if(urlContent!=null){result=tistoryListMap(urlContent,"UTF-8");}
		return result;
	}

	/******************************************************************************
	* urlCon 에서 받아온 값(Json형식)을 받아온 인코딩 값으로 파싱 후 List<HashMap<String,String>> 으로 리턴
	* @param InputStream,String
	* @return List<HashMap<String,String>>
	* @since 2014-11-13
	* @afy0817 : afy0817@gmail.com
	******************************************************************************/
	public static List<HashMap<String,String>> tistoryListMap(InputStream jsonCon,String encode){
		List<HashMap<String, String>> tistoryList = new ArrayList<HashMap<String,String>>();
		JSONParser parser = new JSONParser();
		StringBuilder sb = new StringBuilder();
		String line;
		try{
			BufferedReader br = new BufferedReader(new InputStreamReader(jsonCon,encode));
			while((line=br.readLine()) != null){
				sb.append(line);
			}
			br.close();
			System.out.println(sb.toString());
			JSONObject postsObj = (JSONObject)((JSONObject)((JSONObject)((JSONObject)parser.parse(sb.toString())).get("tistory")).get("item")).get("posts");
			JSONArray postArr = (JSONArray)postsObj.get("post");
			for(int i = 0; i < 3; i++){
				HashMap<String,String> tistoryMap = new HashMap<String,String>();
				if(((JSONObject)postArr.get(i)).get("id")!=null){
					tistoryMap.put("id",AfyUtil.toString(((JSONObject)postArr.get(i)).get("id")));
					tistoryList.add(tistoryMap);
				}
			}
		}catch(Exception e){
			System.out.println(e.toString());
		}
		return tistoryList;
	}

	/******************************************************************************
	* urlCon 에서 받아온 값(Json형식)을 받아온 인코딩 값으로 파싱 후 List<HashMap<String,String>> 으로 리턴
	* @param InputStream,String
	* @return List<HashMap<String,String>>
	* @since 2014-11-13
	* @afy0817 : afy0817@gmail.com
	******************************************************************************/
	public static HashMap<String,String> tistoryContentMap(InputStream jsonCon,String encode){
		HashMap<String, String> tistoryMap = new HashMap<String,String>();
		JSONParser parser = new JSONParser();
		StringBuilder sb = new StringBuilder();
		String line;
		try{
			BufferedReader br = new BufferedReader(new InputStreamReader(jsonCon,encode));
			while((line=br.readLine()) != null){
				sb.append(line);
			}
			br.close();
			System.out.println(sb.toString());
			JSONObject contentObj = (JSONObject)((JSONObject)((JSONObject)parser.parse(sb.toString())).get("tistory")).get("item");
			if((String)contentObj.get("title")!=null){
				tistoryMap.put("title",(String)contentObj.get("title"));
				tistoryMap.put("content",(String)contentObj.get("content"));
				tistoryMap.put("postUrl",(String)contentObj.get("postUrl"));
				tistoryMap.put("date",(String)contentObj.get("date"));
			}
		}catch(Exception e){
			System.out.println("에러 : "+e.toString());
		}
		return tistoryMap;
	}
}
