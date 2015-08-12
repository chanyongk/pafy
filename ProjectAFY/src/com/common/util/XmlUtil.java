package com.common.util;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
public class XmlUtil {
	private static XmlUtil instance = new XmlUtil();
	
	public static XmlUtil getInstance(){
		return instance;
	}
	private XmlUtil(){}

	
	/** 
	 * @method : getXmlDataBetweenDepthHash
	 * @class : XmlUtil
	 * @comment : xml path > HashMap
	 * @author : Dev_Young
	 * @date : 2011. 3. 4. 오후 4:46:43
	 * @return : HashMap<Integer,Object>
	 */
	public static HashMap<Integer,Object> getXmlDataBetweenDepthHash(String xml_path, int start_depth, int end_depth){
		HashMap<Integer,Object> xmldatahash = new HashMap<Integer,Object>();
		
		if(start_depth>=2 && start_depth<=end_depth && 4<=end_depth){
			NodeList nodelist = getXmlNodeList(xml_path);

			if(nodelist!=null){
				
				for(int i=start_depth; i<=end_depth; i++){
					if(i==2){
						xmldatahash.put(i,XmlUtil.getXmlData2DepthHash(nodelist));
					}else if(i==3){
						xmldatahash.put(i,XmlUtil.getXmlData3DepthHashList(nodelist));
					}else if(i==4){
						xmldatahash.put(i,XmlUtil.getXmlData4DepthHashList(nodelist));
					}
				}
			}
		}
		return xmldatahash;
	}
	
	/**
	 * @method : getXmlDataFromStringBetweenDepthHash
	 * @class : XmlUtil
	 * @comment : xml string > HashMap
	 * @author : Dev_Young
	 * @date : 2012. 4. 4. 오전 9:25:45
	 * @return : HashMap<Integer,Object>
	 */
	public static HashMap<Integer,Object> getXmlDataFromStringBetweenDepthHash(String xml_str, int start_depth, int end_depth){
		HashMap<Integer,Object> xmldatahash = new HashMap<Integer,Object>();
		
		if(start_depth>=2 && start_depth<=end_depth && 4<=end_depth){
			NodeList nodelist = getXmlNodeList(getStringToDocument(xml_str));

			if(nodelist!=null){
				
				for(int i=start_depth; i<=end_depth; i++){
					if(i==2){
						xmldatahash.put(i,XmlUtil.getXmlData2DepthHash(nodelist));
					}else if(i==3){
						xmldatahash.put(i,XmlUtil.getXmlData3DepthHashList(nodelist));
					}else if(i==4){
						xmldatahash.put(i,XmlUtil.getXmlData4DepthHashList(nodelist));
					}
				}
			}
		}
		return xmldatahash;
	}
	
	/**
	 * @method : getXmlData2DepthHash
	 * @class : XmlUtil
	 * @comment : 
	 * @author : Dev_Young
	 * @date : 2011. 2. 18. 오후 5:34:58
	 * @return : HashMap<String,String>
	 */
	public static HashMap<String,String> getXmlData2DepthHash(String xml_path){
		return getXmlData2DepthHash(getXmlNodeList(xml_path));
	}	
	/**
	 * @method : getXmlData3DepthHashList
	 * @class : XmlUtil
	 * @comment : 
	 * @author : Dev_Young
	 * @date : 2011. 2. 18. 오후 5:23:05
	 * @return : Vector<HashMap<String,String>>
	 */
	public static Vector<HashMap<String,String>> getXmlData3DepthHashList(String xml_path){
		return getXmlData3DepthHashList(getXmlNodeList(xml_path));	
	}	
	/**
	 * @method : getXmlData4DepthHashList
	 * @class : XmlUtil
	 * @comment : 
	 * @author : Dev_Young
	 * @date : 2011. 2. 18. 오후 5:32:34
	 * @return : Vector<HashMap<String,String>>
	 */
	public static Vector<HashMap<String,String>> getXmlData4DepthHashList(String xml_path){
		return getXmlData4DepthHashList(getXmlNodeList(xml_path));	
	}
	
	/**
	 * @method : getXmlData4DepthHashList
	 * @class : XmlUtil
	 * @comment : 
	 * @author : Dev_Young
	 * @date : 2011. 2. 18. 오후 5:32:34
	 * @return : Vector<HashMap<String,String>>
	 */
	public static Vector<HashMap<String,String>> getXmlData5DepthHashList(String xml_path){
		return getXmlData5DepthHashList(getXmlNodeList(xml_path));	
	}
	
	/**
	 * @method : getXmlDataFromString2DepthHash
	 * @class : XmlUtil
	 * @comment : 
	 * @author : Dev_Young
	 * @date : 2012. 4. 4. 오전 9:28:09
	 * @return : HashMap<String,String>
	 */
	public static HashMap<String,String> getXmlDataFromString2DepthHash(String xml_str){
		return getXmlData2DepthHash(getXmlNodeList(getStringToDocument(xml_str)));
	}
	/**
	 * @method : getXmlDataFromString3DepthHashList
	 * @class : XmlUtil
	 * @comment : 
	 * @author : Dev_Young
	 * @date : 2012. 4. 4. 오전 9:29:02
	 * @return : Vector<HashMap<String,String>>
	 */
	public static Vector<HashMap<String,String>> getXmlDataFromString3DepthHashList(String xml_str){
		return getXmlData3DepthHashList(getXmlNodeList(getStringToDocument(xml_str)));	
	}
	/**
	 * @method : getXmlDataFromString4DepthHashList
	 * @class : XmlUtil
	 * @comment : 
	 * @author : Dev_Young
	 * @date : 2012. 4. 4. 오전 9:29:27
	 * @return : Vector<HashMap<String,String>>
	 */
	public static Vector<HashMap<String,String>> getXmlDataFromString4DepthHashList(String xml_str){
		return getXmlData4DepthHashList(getXmlNodeList(getStringToDocument(xml_str)));	
	}
	/**
	 * @method : getXmlNodeList
	 * @class : XmlUtil
	 * @comment : 
	 * @author : Dev_Young
	 * @date : 2011. 2. 19. 오후 9:28:45
	 * @return : NodeList
	 */
	private static NodeList getXmlNodeList(String xml_path){
		NodeList nodelist = null;
		try{
			String pharm_source_xml = xml_path;
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance(); 
			// XML DOM Parser를 생성 
			DocumentBuilder parser = factory.newDocumentBuilder(); 
			// XML 내용이 담긴 문자열을 읽어들여서 DOM을 구성 
			Document doc = parser.parse(pharm_source_xml); 
			// root Element 를 구한다. 
			Element rootElement = doc.getDocumentElement(); 
			// root Element의 모든 자식 노드를 구한다. 
			nodelist = rootElement.getChildNodes(); 
		}catch(Exception e){}
		return nodelist;	
	}
	
	/**
	 * @method : getXmlNodeList
	 * @class : XmlUtil
	 * @comment : 
	 * @author : Dev_Young
	 * @date : 2012. 4. 4. 오전 9:22:20
	 * @return : NodeList
	 */
	private static NodeList getXmlNodeList(Document doc){
		NodeList nodelist = null;
		try{
			Element rootElement = doc.getDocumentElement(); 
			nodelist = rootElement.getChildNodes(); 
		}catch(Exception e){}
		return nodelist;	
	}
	
	/**
	 * @method : getXmlData2DepthHash
	 * @class : XmlUtil
	 * @comment : 
	 * @author : Dev_Young
	 * @date : 2011. 3. 4. 오후 4:43:31
	 * @return : HashMap<String,String>
	 */
	private static HashMap<String,String> getXmlData2DepthHash(NodeList nodelist){
		HashMap<String,String> xmldatahash = new HashMap<String,String>();
		try{
			xmldatahash = getXmlDataHash(nodelist);
		}catch(Exception e){}
		return xmldatahash;	
	}
	/**
	 * @method : getXmlData3DepthHashList
	 * @class : XmlUtil
	 * @comment : 
	 * @author : Dev_Young
	 * @date : 2011. 3. 4. 오후 4:43:34
	 * @return : Vector<HashMap<String,String>>
	 */
	private static Vector<HashMap<String,String>> getXmlData3DepthHashList(NodeList nodelist){
		
		Vector<HashMap<String,String>> xmldatalist = new Vector<HashMap<String,String>>(); 
		try{
			NodeList node1lvChildList = nodelist; 
			for(int i=0; i<node1lvChildList.getLength(); i++) { 
				Node node2lv = node1lvChildList.item(i); 
				// 자식 노드가 요소일 경우에만 실행한다. 
				NodeList node2lvChildList = node2lv.getChildNodes();
			
				if (node2lvChildList.getLength() > 0) {
					xmldatalist.add(getXmlDataHash(node2lvChildList));
				}
			}
		}catch(Exception e){}
		return xmldatalist;	
	}
	
	/**
	 * @method : getXmlData4DepthHashList
	 * @class : XmlUtil
	 * @comment : 
	 * @author : Dev_Young
	 * @date : 2011. 3. 4. 오후 4:43:36
	 * @return : Vector<HashMap<String,String>>
	 */
	private static Vector<HashMap<String,String>> getXmlData4DepthHashList(NodeList noteList){

		Vector<HashMap<String,String>> xmldatalist = new Vector<HashMap<String,String>>(); 
		try{
			NodeList node1lvChildList = noteList; 
			for(int i=0; i<node1lvChildList.getLength(); i++) { 
				Node node2lv = node1lvChildList.item(i); 
				// 자식 노드가 요소일 경우에만 실행한다. 
				NodeList node2lvChildList = node2lv.getChildNodes();
			
				if (node2lvChildList.getLength() > 0) {
					
					for(int j=0; j<node2lvChildList.getLength(); j++) {
						Node node3lv = node2lvChildList.item(j);
						NodeList node3lvChildList = node3lv.getChildNodes();
						
						if(node3lvChildList.getLength()>0){
							xmldatalist.add(getXmlDataHash(node3lvChildList));
						}
					}
				}
			}
		}catch(Exception e){}
		return xmldatalist;	
	}
	
	/**
	 * @method : getXmlData4DepthHashList
	 * @class : XmlUtil
	 * @comment : 
	 * @author : Dev_Young
	 * @date : 2011. 3. 4. 오후 4:43:36
	 * @return : Vector<HashMap<String,String>>
	 */
	private static Vector<HashMap<String,String>> getXmlData5DepthHashList(NodeList noteList){

		Vector<HashMap<String,String>> xmldatalist = new Vector<HashMap<String,String>>(); 
		try{
			NodeList node1lvChildList = noteList; 
			for(int i=0; i<node1lvChildList.getLength(); i++) { 
				Node node2lv = node1lvChildList.item(i); 
				// 자식 노드가 요소일 경우에만 실행한다. 
				NodeList node2lvChildList = node2lv.getChildNodes();
			
				if (node2lvChildList.getLength() > 0) {
					
					for(int j=0; j<node2lvChildList.getLength(); j++) {
						Node node3lv = node2lvChildList.item(j);
						NodeList node3lvChildList = node3lv.getChildNodes();
						
						if(node3lvChildList.getLength()>0){
							for(int x=0; x<node3lvChildList.getLength(); x++) {
								Node node4lv = node3lvChildList.item(x);
								NodeList node4lvChildList = node4lv.getChildNodes();
								if(node4lvChildList.getLength()>0){
									xmldatalist.add(getXmlDataHash(node4lvChildList));
								}
							}
						}
					}
				}
			}
		}catch(Exception e){}
		return xmldatalist;	
	}


	/**
	 * @method : getXmlDataHash
	 * @class : XmlUtil
	 * @comment : 
	 * @author : Dev_Young
	 * @date : 2011. 3. 4. 오후 4:43:39
	 * @return : HashMap<String,String>
	 */
	private static HashMap<String,String> getXmlDataHash(NodeList nodeList){
		HashMap<String,String> xmldatahash = new HashMap<String,String>();
		try{
			for(int i=0; i<nodeList.getLength(); i++) { 
				Node node = nodeList.item(i); 
				if(node.getNodeType() == Node.ELEMENT_NODE) {
					String value_str = "";
					try {
						value_str = node.getFirstChild().getNodeValue();
					} catch(Exception e){}
					if(value_str==null || value_str.equals("null")){
						value_str = "";
					}
					xmldatahash.put(node.getNodeName(), value_str);
				}
			}
		}catch(Exception e){}
		return xmldatahash;	
	}
	
	/**
	 * @method : getStringToDocument
	 * @class : XmlUtil
	 * @comment : String -> Document
	 * @author : Dev_Young
	 * @date : 2012. 4. 4. 오전 9:20:24
	 * @return : Document
	 */
	private static Document getStringToDocument(String str){
		Document doc = null;
		try{
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder builder = factory.newDocumentBuilder();
			StringReader userdataReader = new StringReader(str);
	        InputSource inputSource = new InputSource(userdataReader);
	        doc = builder.parse(inputSource);
	 	}catch(Exception ex){}
		return doc;
	}
}
