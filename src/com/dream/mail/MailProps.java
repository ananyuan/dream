package com.dream.mail;

import java.security.Security;
import java.util.Properties;

public class MailProps {
	private boolean servTypeImap = true;

	private MailServer mailServ;

	private Properties props;

	public MailProps(MailServer mailServ) {
		this.mailServ = mailServ;

		initProps();
	}

	/**
	 * 初始化
	 */
	private void initProps() {

		// 优先使用IMAP, 没有发现IMAP, 才去使用POP3
		if (mailServ.getType().trim().equalsIgnoreCase("IMAP")) {
			setPropsImap();
		} else { // POP3
			setServTypeImap(false);
			setPropsPop3();
		}

	}

	public Properties getProps() {
		return props;
	}

	/**
	 * javax.net.ssl.SSLHandshakeException: sun.security.validator.ValidatorException: 
	 * PKIX path validation failed: java.security.cert.CertPathValidatorException: 
	 * basic constraints check failed: pathLenConstraint violated - 
	 * this cert must be the last cert in the certification path 
	 * 这种错误，查询是证书的问题，在网上百般折腾，后来高人一句话提醒我了（你的url不是公网，服务接入成功了，可以你的开发环境不能），
	 * 我是在自己电脑上新建的工程，没有部署到网络上，你给腾讯发post请求了，可是腾讯接入不到你的本机工程上，所以会出现那个错误了。
	 */
	private void setPropsPop3() {
		props = System.getProperties();

		if (mailServ.isSsl()) {
			Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
			final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";

			props.setProperty("mail.pop3.socketFactory.class", SSL_FACTORY);
			props.setProperty("mail.pop3.socketFactory.fallback", "false");
		}

		props.setProperty("mail.pop3.port", String.valueOf(mailServ.getPop3Port()));
		props.setProperty("mail.pop3.socketFactory.port", String.valueOf(mailServ.getPop3Port()));
	}

	/**
	 * 
	 * @return Properties
	 */
	private void setPropsImap() {
		props = System.getProperties();
		props.setProperty("mail.store.protocol", "imaps");

	}

	public boolean isServTypeImap() {
		return servTypeImap;
	}

	private void setServTypeImap(boolean servTypeImap) {
		this.servTypeImap = servTypeImap;
	}
}
