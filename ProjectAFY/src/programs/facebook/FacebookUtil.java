package programs.facebook;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.common.util.AfyUtil;

public class FacebookUtil {
	private static String savePath = "D:/temp/";//Save directory
	private static Boolean isSave = false;//Save status
	private static String appId = "user-app-id";
	private static String fbSecretKey = "user-secret-key";
	private static String timelineJson = "https://graph.facebook.com/v2.2/afy0817?fields=picture,likes,posts.limit(10){message,link,object_id}&access_token="+appId+"|"+fbSecretKey;
	private static int timeout = 3000;
	private static File file = new File(savePath);

	/******************************************************************************
	* @Warning Delete all the files in savePath
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
	* Save the url's image file
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
	* urlCon 에서 받아온 값이 null 이 아닐때 받아온 값(json형식) 파싱 후 리턴
	* @return List<HashMap<String,String>>
	* @since 2014-11-13
	* @afy0817 : afy0817@gmail.com
	******************************************************************************/
	public static List<FacebookVo> getFacebookCon(){
		List<FacebookVo> result = null;
		InputStream urlContent = AfyUtil.urlCon(timelineJson,timeout);
		if(urlContent!=null){result=fbVo(urlContent,"UTF-8");}
		return result;
	}

	/******************************************************************************
	* urlCon 에서 받아온 값(Json형식)을 받아온 인코딩 값으로 파싱 후 List<HashMap<String,String>> 으로 리턴
	* @param InputStream,String,boolean
	* @return List<HashMap<String,String>>
	* @since 2014-11-13
	* @afy0817 : afy0817@gmail.com
	******************************************************************************/
	public static List<FacebookVo> fbVo(InputStream jsonCon,String encode){
		return fbVo(jsonCon,encode,isSave);
	}

	public static List<FacebookVo> fbVo(InputStream jsonCon,String encode, boolean imgSave){
		List<FacebookVo> fblist = new ArrayList<FacebookVo>();
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
				FacebookVo fbVo = new FacebookVo();
				String[] linkArr = AfyUtil.toArray(AfyUtil.toString(((JSONObject)dataArr.get(i)).get("id")),"_");
				if(((JSONObject)dataArr.get(i)).get("message")!=null){
					fbVo.setReg_dt(AfyUtil.toTimestamp(AfyUtil.toString(((JSONObject)dataArr.get(i)).get("created_time")),"yyyy-MM-dd"));
					fbVo.setFb_url(AfyUtil.toString("https://www.facebook.com/"+linkArr[0]+"/posts/"+linkArr[1]));
					fbVo.setFb_content(AfyUtil.toString(((JSONObject)dataArr.get(i)).get("message")));
					if(((JSONObject)dataArr.get(i)).get("object_id")!=null){
						if(imgSave){
							if(saveImg(savePath, imgUrl, imgFileNm)){
								fbVo.setFb_img(savePath+imgFileNm);
							}else{
								fbVo.setFb_img("noImg");
							}
						}else{
							fbVo.setFb_img(imgUrl);
						}
					}else{
						fbVo.setFb_img("noImg");
					}
					fblist.add(fbVo);
				}
			}
		}catch(Exception e){}
		return fblist;
	}
}