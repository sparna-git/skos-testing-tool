package fr.sparna.rdf.skos.testtool;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LangFilter implements Filter {

	private Logger log = LoggerFactory.getLogger(this.getClass().getName());
	
	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}
	
	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(
			ServletRequest request,
			ServletResponse response,
			FilterChain chain)
	throws IOException, ServletException {
		 // Check type request.
        if (request instanceof HttpServletRequest) {
            // Cast back to HttpServletRequest.
            HttpServletRequest httpRequest = (HttpServletRequest) request;

            // Parse HttpServletRequest.
            HttpServletRequest parsedRequest = filterRequest(httpRequest);

            // Continue with filter chain.
            chain.doFilter(parsedRequest, response);
        } else {
            // Not a HttpServletRequest.
            chain.doFilter(request, response);
        }
		
	}

	private HttpServletRequest filterRequest(HttpServletRequest request) {
		
		SessionData sessionData = SessionData.get(request.getSession());

		// si on n'a pas de session...
		if(sessionData == null) {
			// on en créé une et on la stocke
			sessionData = new SessionData();
			sessionData.store(request.getSession());
			
			// on détermine la langue :
			String langToSet;
			// on prend le parametre de lang si il y en a un...
			if(request.getParameter("lang") != null) {
				log.debug("Detected 'lang' param. Will set a new user locale.");
				langToSet = request.getParameter("lang");
			} else {
				// sinon on prend la langue par défaut du navigateur
				String userLanguage = request.getLocale().getLanguage();
				if(userLanguage.startsWith("fr")) {
					langToSet = "fr";
				} else {
					langToSet = "en";
				}
			}
			sessionData.setUserLocale(langToSet);
			
		} else {
			// si on a une session et que le parametre est renvoyé,
			// alors on l'enregistre dans la session
			if(request.getParameter("lang") != null) {
				log.debug("Detected 'lang' param. Will set a new user locale.");
				sessionData.setUserLocale(request.getParameter("lang"));
			} 
		}
		
		return request;
	}
}
