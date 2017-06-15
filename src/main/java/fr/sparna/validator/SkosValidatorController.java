package fr.sparna.validator;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.ResourceBundle.Control;
import java.util.spi.ResourceBundleControlProvider;

import org.openrdf.repository.RepositoryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SkosValidatorController {

	@Autowired
	private ApplicationData applicationData;


	@RequestMapping(value = "home")
	public ModelAndView upload(
			@RequestParam(value="lang", required=false) String lang
			) throws IOException{

		ValidatorData data = new ValidatorData();
		ResourceBundle b = ResourceBundle.getBundle(
							"Bundle",
							Locale.getDefault());
					
					
		if(lang!=null){
			Locale.setDefault(new Locale(lang));
		}
		return new ModelAndView("home", ValidatorData.KEY, data);
	}

	@RequestMapping(value = "result")
	public ModelAndView uploadResult(
			@RequestParam(value="file", required=false) MultipartFile file,
			@RequestParam(value="rulesChoice", required=false) String choice
			) throws RepositoryException {

		ValidatorData data = new ValidatorData();
		File newFile=null;

		if(choice!=null){
			data.extractAndSetChoice(choice);
		}		

		if(file!=null){
			try {
				newFile=multipartToFile(file);
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				data.setMsg(e.getMessage());
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				data.setMsg(e.getMessage());
			}
			try {
				ValidateSkosFile skos=new ValidateSkosFile(choice.replaceAll("-", ","),newFile);
				data.setErrorList(skos.validate());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				data.setMsg(e.getMessage());
				return new ModelAndView("home", ValidatorData.KEY, data);
			}

		}
		return new ModelAndView("result", ValidatorData.KEY, data);
	}

	public File multipartToFile(MultipartFile multipart) throws IllegalStateException, IOException 
	{
		File convFile = new File( multipart.getOriginalFilename());
		multipart.transferTo(convFile);
		return convFile;
	}

}
