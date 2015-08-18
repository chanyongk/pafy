package programs.sns;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.*;
import java.util.*;

import javax.imageio.ImageIO;

import org.json.simple.*;
import org.json.simple.parser.*;

import com.common.util.*;

public class FacebookJson{
	/******************************Image Save Path******************************/
	private static String savePath = "D:/temp/";
	private static Boolean isSave = false;
	private static File file = new File(savePath);
	/******************************************************************************
	* 해당 디렉토리 파일 전체삭제
	* @param String,String,String
	* @return boolean
	* @since 2015-04-19
	* @afy0817 : afy0817@gmail.com
	******************************************************************************/
	public static void deleteFiles(){
		String[] fileList = file.list();
		for(int i = 0; i < fileList.length; i++) {
			new File(savePath+fileList[i]).delete();
		}
	}

	/******************************************************************************
	* 이미지 URL을 실제 이미지 URL로 변환 후 해당이미지 저장
	* @param String,String,String
	* @return boolean
	* @since 2015-04-19
	* @afy0817 : afy0817@gmail.com
	******************************************************************************/
	public static boolean saveImg(String savePath, String imgPath, String fileNm){
		boolean result = false;
		String file_ext=fileNm.substring(fileNm.lastIndexOf('.')+1,fileNm.length());
		BufferedImage image = null;
		try{
			URL obj = new URL(imgPath);
			HttpURLConnection urlCon = (HttpURLConnection)obj.openConnection();
			urlCon.setReadTimeout(5000);
			int status = urlCon.getResponseCode();
			boolean redirect = false;
			boolean HTTP_OK = status != HttpURLConnection.HTTP_OK;
			boolean status_st = status == HttpURLConnection.HTTP_MOVED_TEMP || status == HttpURLConnection.HTTP_MOVED_PERM || status == HttpURLConnection.HTTP_SEE_OTHER;
			if(HTTP_OK && status_st){redirect = true;}
			String newUrl = urlCon.getHeaderField("Location");
			if(redirect){
				image = ImageIO.read(new URL(newUrl));
				BufferedImage bufferedImage = new BufferedImage(image.getWidth(),image.getHeight(), BufferedImage.TYPE_INT_BGR);
				Graphics2D graphics = (Graphics2D) bufferedImage.getGraphics();
				graphics.setBackground(Color.WHITE);
				graphics.drawImage(image, 0, 0, null);
				ImageIO.write(bufferedImage, file_ext, new File(savePath+fileNm));
				result = true;
			}
		}catch(Exception e){
			result = false;
		}
		return result;
	}

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
	* 
	* @param InputStream,String,boolean
	* @return List<HashMap<String,String>>
	* @since 2014-11-13
	* @afy0817 : afy0817@gmail.com
	******************************************************************************/
	public static List<HashMap<String,String>> fbArr(InputStream jsonCon,String encode){
		return fbArr(jsonCon,encode,isSave);
	}

	public static List<HashMap<String,String>> fbArr(InputStream jsonCon,String encode, boolean imgSave){
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
				String imgUrl = AfyUtil.toString("http://graph.facebook.com/"+((JSONObject) dataArr.get(i)).get("object_id"))+"/picture?type=normal";
				String imgFileNm = AfyUtil.toString(((JSONObject) dataArr.get(i)).get("object_id"))+".jpg";
				HashMap<String,String> fbArr = new HashMap<String,String>();
				String[] linkArr = AfyUtil.toArray(AfyUtil.toString(((JSONObject)dataArr.get(i)).get("id")),"_");
				if(((JSONObject)dataArr.get(i)).get("message")!=null){
				fbArr.put("time",AfyUtil.toString(((JSONObject)dataArr.get(i)).get("created_time")));
				fbArr.put("link",AfyUtil.toString("https://www.facebook.com/"+linkArr[0]+"/posts/"+linkArr[1]));
				fbArr.put("message",AfyUtil.toString(((JSONObject)dataArr.get(i)).get("message")));
				if(((JSONObject)dataArr.get(i)).get("object_id")!=null){
					if(imgSave){
						if(saveImg(savePath, imgUrl, imgFileNm)){
							fbArr.put("image",savePath+imgFileNm);
						}else{
							fbArr.put("image","noImg");
						}
					}else{
						fbArr.put("image",imgUrl);
					}
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