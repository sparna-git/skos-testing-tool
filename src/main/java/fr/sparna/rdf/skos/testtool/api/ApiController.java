package fr.sparna.rdf.skos.testtool.api;

import java.net.MalformedURLException;

import javax.servlet.http.HttpServletRequest;

import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.repository.RepositoryException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import fr.sparna.rdf.skos.testtool.SkosTestToolController;

@Controller
public class ApiController {

	private final Logger logger = LoggerFactory.getLogger(SkosTestToolController.class);
	
	@RequestMapping(
			value = {"test"},
			produces={"text/html", "text/plain"}
	)
	public ModelAndView testInHtml(
			//url fichier
			@RequestParam(value="url", required=true) String url,
			//règles séparées par des virgules
			@RequestParam(value="rules", required=false) String rules,
			//language (fr or en) du fichier url à valider en get
			@RequestParam(value="lang", required=false) String lang,
			// Entête text/html vs. text/plain
			@RequestHeader(name="Accept") String accept,
			HttpServletRequest request
	) throws RepositoryException, MalformedURLException {
		logger.info("test in text/html or text/plain : "+"url="+url+",rules="+rules+",lang="+lang);
		return null;
	}
	
	@RequestMapping(
			value = {"test"},
			produces={"application/rdf+xml", "text/turtle"}
	)
	public Model testInRdf(
			//url fichier
			@RequestParam(value="url", required=true) String url,
			//règles séparées par des virgules
			@RequestParam(value="rules", required=false) String rules,
			//language (fr or en) du fichier url à valider en get
			@RequestParam(value="lang", required=false) String lang,
			HttpServletRequest request
	) throws RepositoryException, MalformedURLException {
		logger.info("test in application/rdf+xml or text/turtle : "+"url="+url+",rules="+rules+",lang="+lang);
		return null;
	}
	
}
