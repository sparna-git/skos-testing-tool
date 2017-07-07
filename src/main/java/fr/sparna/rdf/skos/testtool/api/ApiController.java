package fr.sparna.rdf.skos.testtool.api;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.model.ValueFactory;
import org.eclipse.rdf4j.model.impl.LinkedHashModel;
import org.eclipse.rdf4j.model.impl.SimpleValueFactory;
import org.eclipse.rdf4j.repository.RepositoryException;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.eclipse.rdf4j.rio.Rio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import at.ac.univie.mminf.qskos4j.issues.Issue;
import fr.sparna.rdf.skos.testtool.ExecuteQSkos;
import fr.sparna.rdf.skos.testtool.GenerateReportFile;
import fr.sparna.rdf.skos.testtool.IssueConverter;
import fr.sparna.rdf.skos.testtool.IssueDescription;
import fr.sparna.rdf.skos.testtool.ReportDisplay;
import fr.sparna.rdf.skos.testtool.SkosTestToolController;
import fr.sparna.rdf.skos.testtool.TestToolConfig;

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
			// format de retour
			@RequestParam(value="format", required=false) String format,
			// Entête language
			@RequestHeader(name="Accept-Language") String headerlang,
			// Entête text/html vs. text/plain
			@RequestHeader(name="Accept") String accept,
			HttpServletRequest request,
			HttpServletResponse response
			) throws RepositoryException, IOException {
		logger.info("test in text/html or text/plain : "+"url="+url+",rules="+rules+",lang="+lang);

		long start = System.currentTimeMillis();
		URL baseURL = new URL("http://"+request.getServerName()+((request.getServerPort() != 80)?":"+request.getServerPort():"")+request.getContextPath());
		ReportDisplay data = new ReportDisplay();
		List<IssueDescription>issuelist=TestToolConfig.getInstance().getApplicationData().getIssueDescriptions();
		
		List<String>list=new ArrayList<String>();
		String r="";
		
		//default rules
		if(rules == null || rules.equals("default")){
			for (IssueDescription element : issuelist) {
				if(element.isChecked()){
					list.add(element.getId());
					r=r+element.getId()+",";
				}
			}
			rules=r;
		//all rules
		} else if(rules.equals("all")) {
			for (IssueDescription element : issuelist) {
				list.add(element.getId());
				r=r+element.getId()+",";
			}
			rules=r;
		}
		rules=rules.substring(0, rules.lastIndexOf(","));
		data.setChoiceList(list);
		ExecuteQSkos skos=new ExecuteQSkos(rules);	
		Collection<Issue> qSkosResult = null;

		// if unspecified lang, take the one in the header
		if(lang==null){
			lang=headerlang;
		}
		
		if(lang.startsWith("fr")){
			lang="fr";
		} else {
			lang="en";
		}
		
		if(format==null){
			format=accept;
		}
		String urlconvert=baseURL.toString()+"/test?url="+url+"&rules="+rules+"&format="+format;
		data.setUrlconvert(urlconvert);
		try {
			URL dataUrl = new URL(url);
			InputStream in = new DataInputStream(new BufferedInputStream(dataUrl.openStream()));
			qSkosResult = skos.validate(in, Rio.getWriterFormatForFileName(url).orElse(RDFFormat.RDFXML));
			data.setFileName(url);

			if(format.equals("txt")||format.equals("text/plain")){
				//générer le rapport text
				GenerateReportFile reportfile=new GenerateReportFile(qSkosResult,lang,data);
				response.setContentType("text/plain; charset=utf-8");
				reportfile.outputIssuesReport(response.getOutputStream());
			}else{
				//générer le rapport html
				doResult(skos.getRulesNumber(),lang,qSkosResult,data);
				//fin des tâches
				double timeMilli = new Long(System.currentTimeMillis()-start).doubleValue();
				//récupérer le temps d'éxécution des tâches
				data.setExecutionTime(timeMilli/1000d);
				return new ModelAndView("result", ReportDisplay.KEY, data);
			}

		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(
			value = {"test"},
			produces={"application/rdf+xml", "text/turtle"}
			)
	// @ResponseBody est nécessaire si on veut que l'objet retourné soit sérialisé par un 
	// MessageConverter. Sinon c'est une view qui sera recherchée par Spring.
	@ResponseBody
	public Model testInRdf(
			//url fichier
			@RequestParam(value="url", required=true) String url,
			//règles séparées par des virgules
			@RequestParam(value="rules", required=false) String rules,
			//language (fr or en) du fichier url à valider en get
			@RequestParam(value="lang", required=false) String lang,
			HttpServletRequest request,
			HttpServletResponse response
			) throws RepositoryException, UnsupportedEncodingException, IOException {
		logger.info("test in application/rdf+xml or text/turtle : "+"url="+url+",rules="+rules+",lang="+lang);
		Model m = new LinkedHashModel();
		ValueFactory factory= SimpleValueFactory.getInstance();
		List<IssueDescription>issuelist=TestToolConfig.getInstance().getApplicationData().getIssueDescriptions();
		List<String>list=new ArrayList<String>();
		
		String r="";
		//default rules
		if(rules==null || rules.equals("default")){
			for (IssueDescription element : issuelist) {
				if(element.isChecked()){
					list.add(element.getId());
					r=r+element.getId()+",";
				}
			}
			rules=r;

		}else if(rules.equals("all")){
			//all rules
			for (IssueDescription element : issuelist) {
				list.add(element.getId());
				r=r+element.getId()+",";
			}
			rules=r;
		}
		//remove the last coma
		rules=rules.substring(0, rules.lastIndexOf(","));
		//execute qskos
		ExecuteQSkos skos=new ExecuteQSkos(rules);
		URL dataUrl = new URL(url);
		InputStream in = new DataInputStream(new BufferedInputStream(dataUrl.openStream()));
		Collection<Issue> qSkosResult = skos.validate(in, Rio.getWriterFormatForFileName(url).orElse(RDFFormat.RDFXML));
		//generate rdf file with qdv
		DQVReport dqv=new DQVReport(url,lang,m,factory);
		return dqv.dqvout(qSkosResult);
	}

	/**
	 * 
	 * @param nbrules
	 * @param lang
	 * @param qSkosResult
	 * @param data
	 * @throws IOException
	 */

	private void doResult(
			Integer nbrules, 
			String lang, 
			Collection<Issue> qSkosResult, 
			ReportDisplay data
			) throws IOException {
		//récupérer le nombre total des règles à vérifier
		data.setRulesNumber(nbrules-3);
		//récupérer le resultat de la vérification des règles
		IssueConverter process = new IssueConverter(lang);
		data.setErrorList(process.createReport(qSkosResult));
		//récupérer les règles non vérifiées
		data.setRulesFail(process.getRulesFail());
		//récupérer le nombre de collection, concept et conceptscheme
		data.setAllcollections(process.getAllcollection());
		data.setAllconcepts(process.getAllconcepts());
		data.setAllconceptschemes(process.getAllconceptscheme());
		String issuedDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
		data.setIssueDate(issuedDate);
	}
}
