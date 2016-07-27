package org.codepay.common.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

public class XmlUtil {
	// 读取XML中某个节点中所有元素，存到MAP中
	public static Map<String, String> readXMLToMap(String xmlString, String execp) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			Document document = DocumentHelper.parseText(xmlString);
			List<Element> list = document.selectNodes(execp);
			for (Element o : list) {
				for (Iterator iterator = o.attributeIterator(); iterator.hasNext();) {
					Attribute attribute = (Attribute) iterator.next();
					map.put(attribute.getName(), attribute.getValue());
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return map;
	}

	// 读取XML中某个节点中值，存到MAP中
	public static Map<String, String> readXMLElementToMap(String xmlString, String execp) throws DocumentException {
		Map<String, String> map = new HashMap<String, String>();
		Document document = DocumentHelper.parseText(xmlString);
		List<Element> list = document.selectNodes(execp);
		for (Element o : list) {
			for (Iterator iterator = o.elementIterator(); iterator.hasNext();) {
				Element element = (Element) iterator.next();
				map.put(element.getName(), element.getText());
			}
		}
		return map;
	}

	// 读取XML中某个节点中所有元素，存到List中
	public static List<Map<String, String>> readXMLToList(String xmlString, String execp) {
		List<Map<String, String>> ret = new ArrayList<Map<String, String>>();
		try {
			Document document = DocumentHelper.parseText(xmlString);
			List<Element> list = document.selectNodes(execp);
			for (Element o : list) {
				Map<String, String> map = new HashMap<String, String>();
				for (Iterator iterator = o.attributeIterator(); iterator.hasNext();) {
					Attribute attribute = (Attribute) iterator.next();
					map.put(attribute.getName(), attribute.getValue());
				}
				ret.add(map);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ret;
	}

	// 读取XML中某个节点下所有元素值，存到List中
	public static List<Map<String, String>> readXMLNodeToList(String xmlString, String execp) {
		List<Map<String, String>> ret = new ArrayList<Map<String, String>>();
		try {
			Document document = DocumentHelper.parseText(xmlString);
			List<Element> list = document.selectNodes(execp);
			for (Element o : list) {
				Map<String, String> map = new HashMap<String, String>();
				for (Iterator iterator = o.elementIterator(); iterator.hasNext();) {
					Element element = (Element) iterator.next();
					map.put(element.getName(), element.getText());
				}
				ret.add(map);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ret;
	}

	/**
	 * 读取XML下某个节点的所有属性值到map中 格式:"<?xml version=\"1.0\"
	 * encoding=\"UTF-8\"?><root> <MessageInfo shareType=\"1\" salesMode=\"1\"
	 * lotteryType=\"0\" periodNumber=\"\"/> </root>";
	 * 
	 * @param xmlString
	 * @param nodeName
	 * @return
	 */
	public static Map<String, String> readXML2Map(String xmlString, String nodeName) {
		HashMap<String, String> map = new HashMap();
		try {
			Document doc = (Document) DocumentHelper.parseText(xmlString);

			List<Element> elements = doc.selectNodes(nodeName);
			for (Iterator iter = (Iterator) elements.iterator(); iter.hasNext();) {
				Element elemt = (Element) iter.next();
				List attrs = elemt.attributes();
				for (int i = 0; i < attrs.size(); i++) {
					Attribute attr = (Attribute) attrs.get(i);
					map.put(attr.getName(), attr.getText());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * 读取XML下某个节点的某个属性值(只能有一个MessageInfo节点) 格式:"<?xml version=\"1.0\"
	 * encoding=\"UTF-8\"?><root> <MessageInfo shareType=\"1\" salesMode=\"1\"
	 * lotteryType=\"0\" periodNumber=\"\"/> </root>";
	 * 
	 * @param xmlString
	 * @param nodeName
	 * @return
	 */
	public static String getProperty(String xmlString, String nodeName, String attrName) {
		Map map = readXML2Map(xmlString, nodeName);
		if (map.containsKey(attrName)) {
			return (String) map.get(attrName);
		}
		return null;
	}

	/**
	 * 读取XML下某个节点的所有属性值到list中 格式:"<?xml version=\"1.0\"
	 * encoding=\"UTF-8\"?><root> <MessageInfo shareType=\"1\" salesMode=\"1\"
	 * lotteryType=\"0\" periodNumber=\"\"/> </root>";
	 * 
	 * @param xmlString
	 * @param nodeName
	 * @return
	 */
	public static List<Map<String, String>> readXMLNode2List(String xmlString, String nodeName) {
		List<Map<String, String>> ret = new ArrayList<Map<String, String>>();
		try {
			Document document = DocumentHelper.parseText(xmlString);
			List<Element> elements = document.selectNodes(nodeName);
			for (Iterator iter = (Iterator) elements.iterator(); iter.hasNext();) {
				Element elemt = (Element) iter.next();
				Map<String, String> map = new HashMap<String, String>();
				List attrs = elemt.attributes();
				for (int i = 0; i < attrs.size(); i++) {
					Attribute attr = (Attribute) attrs.get(i);
					map.put(attr.getName(), attr.getText());
				}
				ret.add(map);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ret;
	}

	public static void main(String[] args) {

		String   msg = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><configuration><job><service_name>job1_service_name</service_name><cron_type>job1_cron_type</cron_type><expression>job1_expression</expression><cmd>job1_cmd</cmd><job_handler>com.zhangyue.apollo.tools.common.quartz.QuartzJob</job_handler></job><job><service_name>job2_service_name</service_name><cron_type>job2_cron_type</cron_type><expression>job2_expression</expression><cmd>job2_cmd</cmd><job_handler>com.zhangyue.apollo.tools.common.quartz.QuartzJob</job_handler></job></configuration>";

		System.out.println(readXMLNodeToList(msg, "configuration/job"));
	 
	}

	/**
	 * 读文件
	 * 
	 * @param filePath
	 * @return
	 */
	public static List<String> readFile(String filePath) {
		List<String> para = new ArrayList<String>();
		File file = new File(filePath);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String temp = null;

			while ((temp = reader.readLine()) != null) {

				para.add(temp);
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
		return para;
	}

	public static String updateChild(String xmlString, String childName, String childValue) {
		Document document = null;
		try {
			
		     document = DocumentHelper.parseText(xmlString);
			List<Element> childelements = document.selectNodes(childName);
			for (Iterator childs = childelements.iterator(); childs.hasNext();) {
				Element everyone = (Element) childs.next();
				everyone.setText(childValue); // 修改该元素值
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return document.asXML();
	}
	/**
	 * 将符合XML格式的字符串转换为XML文档
	 * @param s
	 * @return
	 */
	public static Document string2Document(String s){
	      Document doc = null;
	      try{
	           doc = DocumentHelper.parseText(s);
	      }catch(Exception ex){            
	           ex.printStackTrace();
	      }
	      return doc;
	 }
	
	/**
	    * doc2XmlFile
	    * 将Document对象保存为一个xml文件到本地
	    * @return true:保存成功  flase:失败
	    * @param filename 保存的文件名
	    * @param document 需要保存的document对象
	    */
	   public static boolean doc2XmlFile(Document document,String filename)
	   {
	      boolean flag = true;
	      try{
	            /* 将document中的内容写入文件中 */
	            //默认为UTF-8格式，指定为"GB2312"
	            OutputFormat format = OutputFormat.createPrettyPrint();
	            format.setEncoding("GB2312");
	            XMLWriter writer = new XMLWriter(new FileWriter(new File(filename)),format);
	            writer.write(document);
	            writer.close();            
	        }catch(Exception ex){
	            flag = false;
	            ex.printStackTrace();
	        }
	        return flag;      
	   }
	   
	   /**
	    * 创建xml
	    * @param obj	泛型对象
	    * @param entityPropertys	泛型对象的List集合
	    * @param XMLPath	XML文件的路径及文件名
	    * @param encode		XML自定义编码类型
	    * @return
	    */
	   public static boolean createXmlDocument(Class<?> clazz, List<?> entityPropertys,String XMLPath, String encode){
		   boolean flag = true;
		   try{
			   XMLWriter writer = null;// 声明写XML的对象
			   OutputFormat format = OutputFormat.createPrettyPrint();
	           format.setEncoding(encode);  // 设置XML文件的编码格式 UTF-8
	           File file = new File(XMLPath);//获得文件 
	           if (file.exists()) {     
	               file.delete();
	           }
	           // 创建xml文件    
	           Document document = DocumentHelper.createDocument();
	           String rootname = clazz.getSimpleName();  //获得简单类名
	           Element root = document.addElement(rootname + "s");//添加根节点
	           Field[] properties = clazz.getDeclaredFields();//获得实体类的所有属性
	           //赋值
	           for (Object obj : entityPropertys) {//递归实体
	               Element element = root.addElement(rootname);            //二级节点
	               for (int i = 0; i < properties.length; i++){
	                   //反射get方法
	                   Method meth = obj.getClass().getMethod("get"+ properties[i].getName().substring(0, 1).toUpperCase() + properties[i].getName().substring(1));     
	                   //为二级节点添加属性，属性值为对应属性的值
	                   element.addElement(properties[i].getName()).setText(meth.invoke(obj).toString());     
	               }
	           }
	           writer = new XMLWriter(new FileWriter(file), format);  
	           writer.write(document);     
	           writer.close();
		   }catch(Exception ex){
	            flag = false;
	            ex.printStackTrace();
	        }
	        return flag;    
	   }
}
