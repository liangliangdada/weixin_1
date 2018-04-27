package com.liang.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.liang.entity.TextMessage;
import com.thoughtworks.xstream.XStream;

public class MessageUtil {
	
	public static final String TEXT ="text";
	public static final String IMAGE ="image";
	public static final String VOICE ="voice";
	public static final String VIDEO ="video";
	public static final String SHORT_VIDEO ="shortvideo";
	public static final String LOCATION ="location";
	public static final String LINK ="link";
	//事件
	public static final String EVENT ="event";
	public static final String SUBSCRIBE ="subscribe"; //关注事件
	public static final String UNSUBSCRIBE ="unsubscribe"; //取消关注事件
	public static final String CLICK ="CLICK";
	public static final String VIEW ="VIEW";

	/**
	 * 解析xml并转化为map
	 * @param req
	 * @return
	 * @throws IOException
	 * @throws DocumentException
	 */
	public static Map<String,String> XmlToMap(HttpServletRequest req) throws IOException, DocumentException{
		Map<String,String> map = new HashMap<>();
		SAXReader reader = new SAXReader();
		
		InputStream in  = req.getInputStream();
		Document  doc  = reader.read(in);
		//根节点
		Element root = doc.getRootElement();
		//得到根元素的所有节点
		@SuppressWarnings("unchecked")
		List<Element> list = root.elements();
		
		for (Element element : list) {
			map.put(element.getName(), element.getText());
		}
		in.close();
		return map;
	}
	
	/**
	 * 将消息对象转化为xml
	 * @param testMessage
	 * @return
	 */
	public static String textMessageToXml(TextMessage textMessage) {
		XStream st = new XStream();
		//改变根节点
		st.alias("xml", textMessage.getClass());
		return st.toXML(textMessage);
	}
	
	/**
	 * 主菜单
	 * @return
	 */
	public static String menuText() {
		StringBuffer menu = new StringBuffer();
		menu.append("欢迎您的关注,请按照菜单提示进行操作：\n\n");
		menu.append("回复1查看:666\n");
		menu.append("回复2查看:888\n");
		return menu.toString();
	}
	
	public static String  initText(String toUserName,String fromUserName,String content ) {
		TextMessage text = new TextMessage();
		text.setContent(content);
		text.setCreateTime(new Date().getTime());
		text.setFromUserName(toUserName);
		text.setToUserName(fromUserName);
		text.setMsgType(TEXT);
		return textMessageToXml(text);
	}
	
	public static String firstMenu() {
		StringBuffer str = new StringBuffer();
		str.append("1的介绍");
		return str.toString();
	}
}
