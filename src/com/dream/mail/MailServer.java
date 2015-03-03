package com.dream.mail;

public class MailServer {

	
	private String host = "imap.163.com";
	
	private String smtp = "smtp.163.com";
	
	private int smtpPort = 25;
	
	private int pop3Port = 995;
	
	private int imapPort = 143;
	
	private String type = "IMAP";
	
	private boolean ssl = false;


	public String getHost() {
		return host;
	}


	public void setHost(String host) {
		this.host = host;
	}


	public int getSmtpPort() {
		return smtpPort;
	}


	public void setSmtpPort(int smtpPort) {
		this.smtpPort = smtpPort;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public boolean isSsl() {
		return ssl;
	}


	public void setSsl(boolean ssl) {
		this.ssl = ssl;
	}


	public int getPop3Port() {
		return pop3Port;
	}


	public void setPop3Port(int pop3Port) {
		this.pop3Port = pop3Port;
	}


	public String getSmtp() {
		return smtp;
	}


	public void setSmtp(String smtp) {
		this.smtp = smtp;
	}


	public int getImapPort() {
		return imapPort;
	}


	public void setImapPort(int imapPort) {
		this.imapPort = imapPort;
	}
	
	
}
