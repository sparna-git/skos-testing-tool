package fr.sparna.validator;

import javax.servlet.http.HttpSession;

public class SessionData {

	protected String userLocale;
	
	protected String baseUrl;
	
	public String getUserLocale() {
		return userLocale;
	}
	
	public void setUserLocale(String userLocale) {
		this.userLocale = userLocale;
	}

	public static SessionData retrieve(HttpSession session) {
		return (SessionData)session.getAttribute(SessionData.class.getName());
	}
	
	public void store(HttpSession session) {
		session.setAttribute(SessionData.class.getName(), this);
	}

	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}
	
	
}
