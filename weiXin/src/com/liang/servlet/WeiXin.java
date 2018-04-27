package com.liang.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.DocumentException;

import com.liang.entity.TextMessage;
import com.liang.util.CheckUitl;
import com.liang.util.MessageUtil;

public class WeiXin  extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	 @Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		 String signature = req.getParameter("signature");
		 String timestamp = req.getParameter("timestamp");
		 String nonce = req.getParameter("nonce");
		 String echostr = req.getParameter("echostr");
		 PrintWriter out = resp.getWriter();
		 if(CheckUitl.checkSignature(signature, timestamp, nonce)) {
			 out.println(echostr);
		 }
		 
	 }
	 
	 @Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		 req.setCharacterEncoding("UTF-8");
		 resp.setCharacterEncoding("UTF-8");
		 PrintWriter out = resp.getWriter();
		 try {
			Map<String,String> map = MessageUtil.XmlToMap(req);
			String  fromUserName = map.get("FromUserName");
			String  toUserName = map.get("ToUserName");
			String  msgType = map.get("MsgType");
			String  content = map.get("Content");
			String msg = null;
			
			if(MessageUtil.TEXT.equals(msgType)) {
				if("1".equals(content)) {
					msg = MessageUtil.initText(toUserName, fromUserName, MessageUtil.firstMenu());
				}else if("2".equals(content)) {
					msg = MessageUtil.initText(toUserName, fromUserName, "2的介绍：");
				}else if("?".equals(content)) {
					msg = MessageUtil.initText(toUserName, fromUserName,MessageUtil.menuText());
				}else {
					msg = MessageUtil.initText(toUserName, fromUserName, "输入有误,回复？查看菜单..");
				}
			}else if(MessageUtil.EVENT.equals(msgType)) {
				String eventType = map.get("Event");
				//关注事件
				if(MessageUtil.SUBSCRIBE.equals(eventType)) {
					msg = MessageUtil.initText(toUserName, fromUserName, MessageUtil.menuText());
				}
			}
			out.println(msg);
		} catch (DocumentException e) {
			e.printStackTrace();
		}finally {
			out.close();
		}
	 }
}
