package programs.youtube;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.common.util.AfyUtil;

public class YoutubePars{
	/******************************************************************************
	* urlCon 에서 받아온 값이 null 이 아닐때 받아온 값(Jsonc형식) 파싱 후 리턴
	* @param String,int
	* @return List<YoutubeVo>
	* @since 2015-02-04
	* @from afy0817@gmail.com
	******************************************************************************/
	public static List<YoutubeVo> getYoutubeCon(){
		List<YoutubeVo> result = null;
		String jsonLink = "https://www.googleapis.com/youtube/v3/activities?part=snippet&channelId=your-channel-id&maxResults=50&key=your-key";
		InputStream urlContent = AfyUtil.urlCon(jsonLink,30000);
		if(urlContent!=null){result=ytList(urlContent);}
		return result;
	}

	/******************************************************************************
	* urlCon 에서 받아온 값(Jsonc형식)을 받아온 인코딩 값으로 파싱 후 List<YoutubeVo> 으로 리턴
	* @param InputStream,String
	* @return List<YoutubeVo>
	* @since 2014-11-13
	* @afy0817 : afy0817@gmail.com
	******************************************************************************/
	public static List<YoutubeVo> ytList(InputStream jsonCon){
		List<YoutubeVo> result = new ArrayList<YoutubeVo>();
		String pageToken = "";
		try{
			String line;
			JSONParser parser = new JSONParser();
			StringBuilder sb = new StringBuilder();
			BufferedReader br =new BufferedReader(new InputStreamReader(jsonCon,"UTF-8"));
			while((line=br.readLine())!=null){sb.append(line);}br.close();
			JSONObject obj = (JSONObject)parser.parse(sb.toString());
			JSONArray itemArr = (JSONArray)obj.get("items");
			pageToken = obj.get("nextPageToken")+"";
			if(itemArr.size()>0||itemArr!=null){
				for(int i = 0; i < itemArr.size(); i++){
					if(((JSONObject)itemArr.get(i)).get("snippet")!=null &&((JSONObject)((JSONObject)itemArr.get(i)).get("snippet")).get("title")!=null){
						JSONObject snippet = (JSONObject)((JSONObject)itemArr.get(i)).get("snippet");
						if(snippet.get("type").equals("upload")){
							YoutubeVo ytVo = new YoutubeVo();
							String yt_date = AfyUtil.replaceAll(snippet.get("publishedAt")+"","T", " ");
							ytVo.setYt_title(AfyUtil.toString(snippet.get("title")));
							ytVo.setYt_content(AfyUtil.toString(snippet.get("description")));
							ytVo.setYt_date(AfyUtil.toTimestamp(AfyUtil.toDateFormat(AfyUtil.replaceAll(yt_date,".000Z",""), "yyyy-MM-dd HH:mm:ss")));
							ytVo.setYt_img(((JSONObject)((JSONObject)snippet.get("thumbnails")).get("high")).get("url")+"");
							ytVo.setYt_id(AfyUtil.replaceAll(AfyUtil.replaceAll(((JSONObject)((JSONObject)snippet.get("thumbnails")).get("high")).get("url")+"", "https://i.ytimg.com/vi/", ""), "/hqdefault.jpg", ""));
							result.add(ytVo);
						}
					}
				}
			}
		}catch(Exception e){}
		if(pageToken!=null && !pageToken.equals("null") && pageToken.length()>0){//다음페이지가 있을시 재귀호출 없을시 끝
			String nextLink = "https://www.googleapis.com/youtube/v3/activities?part=snippet&channelId=yout-channel-id&maxResults=50&key=your-key&pageToken="+pageToken;
			InputStream nextCon = AfyUtil.urlCon(nextLink,3000);
			List<YoutubeVo> nextList = ytList(nextCon);
			if(nextList.size()>0){result.addAll(nextList);}
		}
		return result;
	}
}
