package com.dream.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dream.base.acl.NoNeedLogin;
import com.dream.base.acl.ResultTypeEnum;


@Controller
@RequestMapping("/identify")
public class IdentifyController {


	private static Log log = LogFactory.getLog(IdentifyController.class);
	
	private char mapTable[] = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i',
			'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
			'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8',
			'9' };

	@NoNeedLogin(ResultTypeEnum.json)
	@RequestMapping(value="/getImg/{random}", method = RequestMethod.GET)
	public void getImg(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
       try {
            OutputStream outputStream = response.getOutputStream();
            
            String code = getCertPic(0, 0, outputStream);
            
            session.setAttribute("CERT_CODE", code);
            
            response.setContentType("image/jpeg");//设置显示格式
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            log.error("getImg", e);
        }
	}
	
	
	@NoNeedLogin(ResultTypeEnum.json)  //TODO
	@RequestMapping(value="/checkCert", method = RequestMethod.POST)
	public void checkCert(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
       try {
            OutputStream outputStream = response.getOutputStream();
            
            String code = getCertPic(0, 0, outputStream);
            
            session.setAttribute("CERT_CODE", code);
            
            response.setContentType("image/jpeg");//设置显示格式
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            log.error("getImg", e);
        }
	}
	
	/**
	 * 
	 * @param session session中保存的
	 * @param userInput 用户输入的
	 * @return 验证是否通过
	 */
	public static boolean checkImgCert(HttpSession session, String userInput) {
        String codeInSession = (String)session.getAttribute("CERT_CODE");
        
        if (codeInSession.equalsIgnoreCase(userInput)) {
        	return true;
        }
        
        
        return false;
	}
	
	
	/*
	 * 功能：生成彩色验证码图片 参数wedth为生成图片的宽度，参数height为生成图片的高度，参数os为页面的输出流
	 */
	public String getCertPic(int width, int height, OutputStream os) {
		if (width <= 0)
			width = 60;
		if (height <= 0)
			height = 26;
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		// 获取图形上下文
		Graphics g = image.getGraphics();
		// 设定背景颜色
		g.setColor(new Color(0xDCDCDC));
		g.fillRect(0, 0, width, height);
		// 画边框
		g.setColor(Color.black);
		g.drawRect(0, 0, width - 1, height - 1);
		// 随机产生的验证码
		String strEnsure = "";
		// 4代表4为验证码，如果要产生更多位的验证码，则加大数值
		for (int i = 0; i < 4; ++i) {
			strEnsure += mapTable[(int) (mapTable.length * Math.random())];
		}
		// 将认证码显示到图像中，如果要生成更多位的验证码，增加drawString语句
		g.setColor(Color.black);
		g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
		String str = strEnsure.substring(0, 1);
		g.drawString(str, 8, 17);
		str = strEnsure.substring(1, 2);
		g.drawString(str, 20, 15);
		str = strEnsure.substring(2, 3);
		g.drawString(str, 35, 18);
		str = strEnsure.substring(3, 4);
		g.drawString(str, 45, 15);
		// 随机产生15个干扰点
		Random rand = new Random();
		for (int i = 0; i < 10; i++) {
			int x = rand.nextInt(width);
			int y = rand.nextInt(height);
			g.drawOval(x, y, 1, 1);
		}
		// 释放图形上下文
		g.dispose();
		try {
			// 输出图形到页面
			ImageIO.write(image, "JPEG", os);
		} catch (IOException e) {
			return "";
		}
		return strEnsure;
	}
	
	
}
