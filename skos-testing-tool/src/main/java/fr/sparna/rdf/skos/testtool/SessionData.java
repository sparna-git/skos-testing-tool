package fr.sparna.rdf.skos.testtool;

import javax.servlet.http.HttpSession;

/**
 * Data stored in the user session
 * @author thomas
 *
 */
public class SessionData {

	protected String userLocale;
	
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

	
	
}
