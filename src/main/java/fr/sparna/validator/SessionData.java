package fr.sparna.validator;

import java.util.Collection;

import javax.servlet.http.HttpSession;

import at.ac.univie.mminf.qskos4j.issues.Issue;

public class SessionData {

	protected String userLocale;
	
	protected String baseUrl;
	
	protected Collection<Issue> qSkosResult;
	
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

	public Collection<Issue> getqSkosResult() {
		return qSkosResult;
	}

	public void setqSkosResult(Collection<Issue> qSkosResult) {
		this.qSkosResult = qSkosResult;
	}
	
	
	
}
