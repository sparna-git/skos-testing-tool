package fr.sparna.validator;

import java.util.Collection;

import javax.servlet.http.HttpSession;

import org.springframework.web.multipart.MultipartFile;

import at.ac.univie.mminf.qskos4j.issues.Issue;

public class SessionData {

	protected String userLocale;
	
	protected Collection<Issue> qSkosResult;
	
	protected String fileName;
	
	public String getUserLocale() {
		return userLocale;
	}
	
	public void setUserLocale(String userLocale) {
		this.userLocale = userLocale;
	}

	public static SessionData get(HttpSession session) {
		return (SessionData)session.getAttribute(SessionData.class.getName());
	}
	
	public static SessionData retrieve(HttpSession session) {
		SessionData data = (SessionData)session.getAttribute(SessionData.class.getName());
		if(data == null) {
			data = new SessionData();
			data.store(session);
		}
		return data;
	}
	
	public void store(HttpSession session) {
		session.setAttribute(SessionData.class.getName(), this);
	}

	public Collection<Issue> getqSkosResult() {
		return qSkosResult;
	}

	public void setqSkosResult(Collection<Issue> qSkosResult) {
		this.qSkosResult = qSkosResult;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	
}
