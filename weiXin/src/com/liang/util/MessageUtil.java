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
	//�¼�
	public static final String EVENT ="event";
	public static final String SUBSCRIBE ="subscribe"; //��ע�¼�
	public static final String UNSUBSCRIBE ="unsubscribe"; //ȡ����ע�¼�
	public static final String CLICK ="CLICK";
	public static final String VIEW ="VIEW";

	/**
	 * ����xml��ת��Ϊmap
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
		//���ڵ�
		Element root = doc.getRootElement();
		//�õ���Ԫ�ص����нڵ�
		@SuppressWarnings("unchecked")
		List<Element> list = root.elements();
		
		for (Element element : list) {
			map.put(element.getName(), element.getText());
		}
		in.close();
		return map;
	}
	
	/**
	 * ����Ϣ����ת��Ϊxml
	 * @param testMessage
	 * @return
	 */
	public static String textMessageToXml(TextMessage textMessage) {
		XStream st = new XStream();
		//�ı���ڵ�
		st.alias("xml", textMessage.getClass());
		return st.toXML(textMessage);
	}
	
	/**
	 * ���˵�
	 * @return
	 */
	public static String menuText() {
		StringBuffer menu = new StringBuffer();
		menu.append("��ӭ���Ĺ�ע,�밴�ղ˵���ʾ���в�����\n\n");
		menu.append("�ظ�1�鿴:666\n");
		menu.append("�ظ�2�鿴:888\n");
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
		str.append("1�Ľ���");
		return str.toString();
	}
}
