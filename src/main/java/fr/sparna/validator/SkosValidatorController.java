package fr.sparna.validator;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;

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
		if(lang!=null) {
			SessionData sessionData = new SessionData();
			sessionData.setUserLocale(lang);
			sessionData.store(request.getSession());
		} else {
			SessionData sessionData = new SessionData();
			String userLanguage = request.getLocale().getLanguage();
			if(userLanguage.startsWith("fr")) {
				sessionData.setUserLocale("fr");
			} else {
				sessionData.setUserLocale("en");
			}
			sessionData.store(request.getSession());
		}
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
			@RequestParam(value="file", required=false) MultipartFile file,
			@RequestParam(value="rulesChoice", required=false) String choice,
			@RequestParam(value="lang", required=false) String lang,
			HttpServletRequest request
			) throws RepositoryException, MalformedURLException {

		ValidatorData data = new ValidatorData();
		Collection<Issue> qSkosResult;
		SessionData sessionData=SessionData.retrieve(request.getSession());
		URL baseURL = new URL("http://"+request.getServerName()+((request.getServerPort() != 80)?":"+request.getServerPort():"")+request.getContextPath());
		sessionData.setBaseUrl(baseURL.toString());
		
		if(lang!=null) {
			sessionData.setUserLocale(lang);
		}
		if(choice!=null){
			data.extractAndSetChoice(choice);
		}	
		try {
			if(sessionData.getqSkosResult()!=null){
				qSkosResult=sessionData.getqSkosResult();
			}else{
				ValidateSkosFile skos=new ValidateSkosFile(choice.replaceAll("-", ","));			
				qSkosResult = skos.validate(file.getInputStream(), Rio.getWriterFormatForFileName(file.getOriginalFilename()));
				sessionData.setqSkosResult(qSkosResult);
			}
			Process process = new Process(sessionData.getUserLocale());
			data.setErrorList(process.createReport(qSkosResult));
		} catch (Exception e) {
			
			e.printStackTrace();
			data.setMsg(e.getMessage());
			return new ModelAndView("home", ValidatorData.KEY, data);
		}
		return new ModelAndView("result", ValidatorData.KEY, data);
	}


}