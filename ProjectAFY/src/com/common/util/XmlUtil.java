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

	public static HashMap<String,String> getXmlData2DepthHash(String xml_path){
		return getXmlData2DepthHash(getXmlNodeList(xml_path));
	}	
	public static Vector<HashMap<String,String>> getXmlData3DepthHashList(String xml_path){
		return getXmlData3DepthHashList(getXmlNodeList(xml_path));	
	}	
	public static Vector<HashMap<String,String>> getXmlData4DepthHashList(String xml_path){
		return getXmlData4DepthHashList(getXmlNodeList(xml_path));	
	}
	public static Vector<HashMap<String,String>> getXmlData5DepthHashList(String xml_path){
		return getXmlData5DepthHashList(getXmlNodeList(xml_path));	
	}
	public static HashMap<String,String> getXmlDataFromString2DepthHash(String xml_str){
		return getXmlData2DepthHash(getXmlNodeList(getStringToDocument(xml_str)));
	}
	public static Vector<HashMap<String,String>> getXmlDataFromString3DepthHashList(String xml_str){
		return getXmlData3DepthHashList(getXmlNodeList(getStringToDocument(xml_str)));	
	}
	public static Vector<HashMap<String,String>> getXmlDataFromString4DepthHashList(String xml_str){
		return getXmlData4DepthHashList(getXmlNodeList(getStringToDocument(xml_str)));	
	}

	private static NodeList getXmlNodeList(String xml_path){
		NodeList nodelist = null;
		try{
			String pharm_source_xml = xml_path;
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance(); 
			DocumentBuilder parser = factory.newDocumentBuilder(); 
			Document doc = parser.parse(pharm_source_xml); 
			Element rootElement = doc.getDocumentElement(); 
			nodelist = rootElement.getChildNodes(); 
		}catch(Exception e){}
		return nodelist;	
	}

	private static NodeList getXmlNodeList(Document doc){
		NodeList nodelist = null;
		try{
			Element rootElement = doc.getDocumentElement(); 
			nodelist = rootElement.getChildNodes(); 
		}catch(Exception e){}
		return nodelist;	
	}

	private static HashMap<String,String> getXmlData2DepthHash(NodeList nodelist){
		HashMap<String,String> xmldatahash = new HashMap<String,String>();
		try{
			xmldatahash = getXmlDataHash(nodelist);
		}catch(Exception e){}
		return xmldatahash;	
	}

	private static Vector<HashMap<String,String>> getXmlData3DepthHashList(NodeList nodelist){
		Vector<HashMap<String,String>> xmldatalist = new Vector<HashMap<String,String>>(); 
		try{
			NodeList node1lvChildList = nodelist; 
			for(int i=0; i<node1lvChildList.getLength(); i++) { 
				Node node2lv = node1lvChildList.item(i); 
				NodeList node2lvChildList = node2lv.getChildNodes();
				if (node2lvChildList.getLength() > 0) {
					xmldatalist.add(getXmlDataHash(node2lvChildList));
				}
			}
		}catch(Exception e){}
		return xmldatalist;	
	}

	private static Vector<HashMap<String,String>> getXmlData4DepthHashList(NodeList noteList){
		Vector<HashMap<String,String>> xmldatalist = new Vector<HashMap<String,String>>(); 
		try{
			NodeList node1lvChildList = noteList; 
			for(int i=0; i<node1lvChildList.getLength(); i++) { 
				Node node2lv = node1lvChildList.item(i); 
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

	private static Vector<HashMap<String,String>> getXmlData5DepthHashList(NodeList noteList){
		Vector<HashMap<String,String>> xmldatalist = new Vector<HashMap<String,String>>(); 
		try{
			NodeList node1lvChildList = noteList; 
			for(int i=0; i<node1lvChildList.getLength(); i++) { 
				Node node2lv = node1lvChildList.item(i); 
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