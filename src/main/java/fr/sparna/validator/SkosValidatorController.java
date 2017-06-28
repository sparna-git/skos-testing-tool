package fr.sparna.validator;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.rdf4j.repository.RepositoryException;
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
	@RequestMapping(value = "result")
	public ModelAndView uploadResult(
			@RequestParam(value="source", required=false) String sourceString,
			@RequestParam(value="url", required=false) String url,
			@RequestParam(value="file", required=false) MultipartFile file,
			@RequestParam(value="rulesChoice", required=false) String choice,
			HttpServletRequest request
			) throws RepositoryException, MalformedURLException {

		long start = System.currentTimeMillis();

		//récupérer la session
		SessionData sessionData=SessionData.retrieve(request.getSession());

		ValidatorData data = new ValidatorData();

		List<String> list=Arrays.asList(choice.split("[,]"));

		data.setChoiceList(list);

		ValidateSkosFile skos=new ValidateSkosFile(choice);	

		Collection<Issue> qSkosResult = null;
		try {
			switch(SOURCE_TYPE.valueOf(sourceString.toUpperCase())) {

			case FILE : {
				qSkosResult = skos.validate(file.getInputStream(), Rio.getWriterFormatForFileName(file.getOriginalFilename()).get());
				System.out.println(file.getOriginalFilename());
				sessionData.setFileName(file.getOriginalFilename());
				break;
			}

			case URL: {
				if(url.isEmpty()) {
					return doErrorConvert(request, "Uploaded link file is empty");
				}
				try {
					URL dataUrl = new URL(url);
					InputStream in = new DataInputStream(new BufferedInputStream(dataUrl.openStream()));
					qSkosResult = skos.validate(in, Rio.getWriterFormatForFileName(url).get());
					sessionData.setFileName(url);
				} catch (Exception e) {
					e.printStackTrace();
					return doErrorConvert(request, e.getMessage()); 
				}
			}
			}
			data.setFileName(sessionData.getFileName());
			// store qSkos result in the session
			sessionData.setqSkosResult(qSkosResult);
			//récupérer le nombre total des règles à vérifier
			data.setRulesNumber(skos.getRulesNumber());
			//récupérer le resultat de la vérification des règles
			Process process = new Process(sessionData.getUserLocale());
			data.setErrorList(process.createReport(sessionData.getqSkosResult()));
			//récupérer les règles non vérifiées
			data.setRulesFail(process.getRulesFail());
			//récupérer le nombre de collection, concept et conceptscheme
			data.setAllcollections(process.getAllcollection());
			data.setAllconcepts(process.getAllconcepts());
			data.setAllconceptschemes(process.getAllconceptscheme());
			String issuedDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
			data.setIssueDate(issuedDate);
			//fin des tâches
			double timeMilli = new Long(System.currentTimeMillis()-start).doubleValue();
			//récupérer le temps d'éxécution des tâches
			data.setExecutionTime(timeMilli/1000d);

		} catch (Exception e) {

			e.printStackTrace();
			System.out.println(e.getCause());
			System.out.println(e.getMessage());

			return doErrorConvert(request, e.getMessage()); 
		}
		return new ModelAndView("result", ValidatorData.KEY, data);
	}


	@RequestMapping(value = "validate",  method=RequestMethod.GET)
	public ModelAndView getResult(
			@RequestParam(value="data", required=true) String url,
			//règles séparées par des virgules
			@RequestParam(value="rules", required=false) String choice,
			//language (fr or en)
			@RequestParam(value="lang", required=false) String lang,
			HttpServletRequest request
			) throws RepositoryException, MalformedURLException {

			long start = System.currentTimeMillis();
	
			ValidatorData data = new ValidatorData();
			
			List<String> list=Arrays.asList(choice.split("[,]"));
			
			data.setChoiceList(list);
	
			ValidateSkosFile skos=new ValidateSkosFile(choice);	
			
			Collection<Issue> qSkosResult = null;
			
			try {
				try {
					URL dataUrl = new URL(url);
					InputStream in = new DataInputStream(new BufferedInputStream(dataUrl.openStream()));
					qSkosResult = skos.validate(in, Rio.getWriterFormatForFileName(url).get());
				} catch (Exception e) {
					e.printStackTrace();
					return doErrorConvert(request, e.getMessage()); 
				}

			data.setFileName(url);
			//récupérer le nombre total des règles à vérifier
			data.setRulesNumber((skos.getRulesNumber()-3));
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
			//fin des tâches
			double timeMilli = new Long(System.currentTimeMillis()-start).doubleValue();
			//récupérer le temps d'éxécution des tâches
			data.setExecutionTime(timeMilli/1000d);

		} catch (Exception e) {

			e.printStackTrace();
			return doErrorConvert(request, e.getMessage()); 
		}

		return new ModelAndView("result", ValidatorData.KEY, data);
	}

	/**
	 * @throws OpenRDFException 
	 * @throws IOException 
	 */
	@RequestMapping(value = "report.txt")
	public void textReport(
			HttpServletRequest request,
			HttpServletResponse response
			) throws IOException{

		//récupérer la session
		SessionData sessionData=SessionData.get(request.getSession());
		//générer le rapport
		GenerateReportFile report=new GenerateReportFile(sessionData.getqSkosResult(),sessionData.getUserLocale());
		response.addHeader("Content-Encoding", "UTF-8");
		report.outputIssuesReport(response.getOutputStream());

	}

	/**
	 * 
	 * @param request
	 * @param message
	 * @return
	 */
	protected ModelAndView doErrorConvert(
			HttpServletRequest request,
			String message
			) {
		ValidatorData data = new ValidatorData();
		data.setMsg(message);
		request.setAttribute(ValidatorData.KEY, data);
		return new ModelAndView("home");
	}
	
}