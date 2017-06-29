package fr.sparna.validator;

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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import at.ac.univie.mminf.qskos4j.issues.Issue;

@Controller
public class SkosValidatorController {


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
		ValidatorData data = new ValidatorData();
		return new ModelAndView("home", ValidatorData.KEY, data);
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

		long start = System.currentTimeMillis();

		//récupérer la session
		SessionData sessionData=SessionData.retrieve(request.getSession());

		ValidatorData data = new ValidatorData();
		data.setChoiceList(Arrays.asList(choice.split(",")));

		ValidateSkosFile skos=new ValidateSkosFile(choice);	

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

			case HTML :  {
				doResult(skos.getRulesNumber(),lang,qSkosResult,data);
				//fin des tâches
				double timeMilli = new Long(System.currentTimeMillis()-start).doubleValue();
				//récupérer le temps d'éxécution des tâches
				data.setExecutionTime(timeMilli/1000d);
				return new ModelAndView("result", ValidatorData.KEY, data);
			}

			case TEXT:   {
				//générer le rapport
				GenerateReportFile reportfile=new GenerateReportFile(qSkosResult,sessionData.getUserLocale(),data.getFileName());
				response.addHeader("Content-Encoding", "UTF-8");
				reportfile.outputIssuesReport(response.getOutputStream());
				break;
			}
			}
		}catch (Exception e) {
			doError(request,e.getMessage());
			e.printStackTrace();
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
			ValidatorData data = new ValidatorData();
			data.setMsg(message);
			request.setAttribute(ValidatorData.KEY, data);
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
				ValidatorData data
				) throws IOException {
			//récupérer le nombre total des règles à vérifier
			data.setRulesNumber(nbrules-3);
			//récupérer le resultat de la vérification des règles
			Process process = new Process(lang);
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