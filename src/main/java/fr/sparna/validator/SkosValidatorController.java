package fr.sparna.validator;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.openrdf.repository.RepositoryException;
import org.openrdf.rio.Rio;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
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
		SessionData sessionData = SessionData.retrieve(request.getSession());
		if(lang!=null) {
			sessionData.setUserLocale(lang);
		} else {;
			String userLanguage = request.getLocale().getLanguage();
			if(userLanguage.startsWith("fr")) {
				sessionData.setUserLocale("fr");
			} else {
				sessionData.setUserLocale("en");
			}
		}
		System.out.println("default locale : "+Locale.getDefault().getLanguage());
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
			@RequestParam(value="lang", required=false) String lang,
			HttpServletRequest request
			) throws RepositoryException, MalformedURLException {

		long start = System.currentTimeMillis();
		InputStream in = null;
		Collection<Issue> qSkosResult = null;
		ValidateSkosFile skos=null;
		Process process=null;
		SOURCE_TYPE source=null;
		URL urls=null;
		ValidatorData data = new ValidatorData();
		//récupérer la session
		SessionData sessionData=SessionData.retrieve(request.getSession());
		URL baseURL = new URL("http://"+request.getServerName()+((request.getServerPort() != 80)?":"+request.getServerPort():"")+request.getContextPath());
		sessionData.setBaseurl(baseURL.toString());
		//sauvegarde de la source : file ou url dans la session
		if(sourceString!=null){
			source = SOURCE_TYPE.valueOf(sourceString.toUpperCase());
			sessionData.setSource(sourceString.toUpperCase());
		}else{
			source=SOURCE_TYPE.valueOf(sessionData.getSource());
		}
		//sauvegarde de la langue dans la session
		if(lang!=null) {
			sessionData.setUserLocale(lang);
		}
		process = new Process(sessionData.getUserLocale());
		if(choice!=null){
			data.extractAndSetChoice(choice);
			skos=new ValidateSkosFile(choice.replaceAll("-", ","));	
			sessionData.setSkosfile(skos);
		}else{
			skos=sessionData.getSkosfile();
		}

		try {
			if(lang==null){
				switch(source) {

				case FILE :{

					qSkosResult = skos.validate(file.getInputStream(), Rio.getWriterFormatForFileName(file.getOriginalFilename()));
					sessionData.setqSkosResult(qSkosResult);
				}
				break;

				case URL:{

					if(url.isEmpty()) {
						return doErrorConvert(request, "Uploaded link file is empty");
					}
					try {
						urls = new URL(url);
						InputStream urlInputStream = urls.openStream();
						in = new DataInputStream(new BufferedInputStream(urlInputStream));
						qSkosResult = skos.validate(in, Rio.getWriterFormatForFileName("test.rdf"));
						sessionData.setqSkosResult(qSkosResult);
					} catch (Exception e) {
						e.printStackTrace();
						return doErrorConvert(request, e.getMessage()); 
					}

				}
				}
			}
			//récupérer le nombre total des règles à vérifier
			data.setRulesNumber(skos.getRulesNumber());
			//récupérer le resultat de la vérification des règles
			data.setErrorList(process.createReport(sessionData.getqSkosResult()));
			//récupérer les règles non vérifiées
			data.setRulesFail(process.getRulesFail());
			long end=System.currentTimeMillis()-start;
			//récupérer le temps d'éxécution des tâches
			data.setExecutionTime((end/1000));
			//générer le rapport
			GenerateReportFile report=new GenerateReportFile(sessionData.getqSkosResult(),sessionData.getUserLocale());
			report.outputIssuesReport();

		}catch (Exception e) {
			e.printStackTrace();
			return doErrorConvert(request, e.getMessage()); 
		}

		return new ModelAndView("result", ValidatorData.KEY, data);
	}


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