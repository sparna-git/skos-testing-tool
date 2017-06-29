package fr.sparna.rdf.skos.testtool;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.rdf4j.repository.RepositoryException;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.eclipse.rdf4j.rio.Rio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import at.ac.univie.mminf.qskos4j.issues.Issue;

@Controller
public class SkosTestToolController {

	private final Logger logger = LoggerFactory.getLogger(SkosTestToolController.class);

	private enum SOURCE_TYPE {
		FILE,
		URL
	}
	private enum REPORT_TYPE {
		HTML,
		TEXT
	}
	/**
	 * 
	 * @param lang
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "home")
	public ModelAndView upload(
			@RequestParam(value="lang", required=false) String lang,
			HttpServletRequest request
			) throws IOException{
		HomeDisplay data = new HomeDisplay();
		return new ModelAndView("home", HomeDisplay.KEY, data);
	}

	/**
	 * 
	 * @param file
	 * @param choice
	 * @param lang
	 * @param request
	 * @return
	 * @throws RepositoryException
	 * @throws MalformedURLException
	 */
	@RequestMapping(value = {"result","validate"})
	public ModelAndView uploadResult(
			//source : utl ou html
			@RequestParam(value="source", required=true) String sourceString,
			//url fichier
			@RequestParam(value="url", required=false) String url,
			//soumission fichier via formulaire
			@RequestParam(value="file", required=false) MultipartFile file,
			//règles séparées par des virgules
			@RequestParam(value="rules", required=true) String choice,
			//type de rapport : html ou text
			@RequestParam(value="report", required=false) String report,
			//language (fr or en) du fichier url à valider en get
			@RequestParam(value="lang", required=false) String lang,
			HttpServletRequest request,
			HttpServletResponse response
			) throws RepositoryException, MalformedURLException {

		logger.info("validate : "+"source="+sourceString+",url="+url+",choice="+choice+",report="+report+",lang="+lang);
		
		long start = System.currentTimeMillis();

		//récupérer la session
		SessionData sessionData=SessionData.retrieve(request.getSession());

		ReportDisplay data = new ReportDisplay();
		data.setChoiceList(Arrays.asList(choice.split(",")));

		ExecuteQSkos skos=new ExecuteQSkos(choice);	

		Collection<Issue> qSkosResult = null;
		if(lang==null){
			lang=sessionData.getUserLocale();
		}

		try {
			switch(SOURCE_TYPE.valueOf(sourceString.toUpperCase())) {

			case FILE : {
				qSkosResult = skos.validate(file.getInputStream(), Rio.getWriterFormatForFileName(file.getOriginalFilename()).orElse(RDFFormat.RDFXML));
				data.setFileName(file.getOriginalFilename());
				break;
			}

			case URL: {
				if(url.isEmpty()){
					return doError(request,"url empty (vide)"); 
				}
				try {
					URL dataUrl = new URL(url);
					InputStream in = new DataInputStream(new BufferedInputStream(dataUrl.openStream()));
					qSkosResult = skos.validate(in, Rio.getWriterFormatForFileName(url).orElse(RDFFormat.RDFXML));

					data.setFileName(url);

				} catch (Exception e) {
					e.printStackTrace();
					return doError(request, e.getMessage()); 
				}
			}
			}

			switch(REPORT_TYPE.valueOf(report.toUpperCase())){

			case TEXT:   {
				//générer le rapport
				GenerateReportFile reportfile=new GenerateReportFile(qSkosResult,sessionData.getUserLocale(),data.getFileName());
				response.setContentType("text/plain; charset=utf-8");
				reportfile.outputIssuesReport(response.getOutputStream());
				break;
			}
			
			case HTML :
			default : {
				doResult(skos.getRulesNumber(),lang,qSkosResult,data);
				//fin des tâches
				double timeMilli = new Long(System.currentTimeMillis()-start).doubleValue();
				//récupérer le temps d'éxécution des tâches
				data.setExecutionTime(timeMilli/1000d);
				return new ModelAndView("result", ReportDisplay.KEY, data);
			}
			
			}
		}catch (Exception e) {
			doError(request,e.getMessage());
		}
		return null;
}

		/**
		 * 
		 * @param request
		 * @param message
		 * @return
		 */
		protected ModelAndView doError(
				HttpServletRequest request,
				String message
				) {
			HomeDisplay data = new HomeDisplay();
			data.setMsg(message);
			request.setAttribute(HomeDisplay.KEY, data);
			return new ModelAndView("home");
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