package fr.sparna.validator;

import java.util.Collection;

import javax.servlet.http.HttpSession;

import org.springframework.web.multipart.MultipartFile;

import at.ac.univie.mminf.qskos4j.issues.Issue;

public class SessionData {

	protected String userLocale;
	
	protected Collection<Issue> qSkosResult;

	protected ValidateSkosFile skosfile;
	
	protected String source;
	
	protected String baseurl;
	
	public String getUserLocale() {
		return userLocale;
	}
	
	public void setUserLocale(String userLocale) {
		this.userLocale = userLocale;
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
	
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public ValidateSkosFile getSkosfile() {
		return skosfile;
	}

	public void setSkosfile(ValidateSkosFile skosfile) {
		this.skosfile = skosfile;
	}

	public String getBaseurl() {
		return baseurl;
	}

	public void setBaseurl(String baseurl) {
		this.baseurl = baseurl;
	}
	
	
}
