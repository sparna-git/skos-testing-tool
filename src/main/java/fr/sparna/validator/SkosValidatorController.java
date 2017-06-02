package fr.sparna.validator;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.openrdf.repository.RepositoryException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SkosValidatorController {



	@RequestMapping(value = "home")
	public ModelAndView upload(
			
			) throws IOException{

		ValidatorData data = new ValidatorData();
		
		return new ModelAndView("home", ValidatorData.KEY, data);
	}
	
	@RequestMapping(value = "result")
	public ModelAndView uploadResult(
			@RequestParam(value="file", required=false) MultipartFile file,
			@RequestParam(value="rulesChoice", required=false) String choice
			) throws IOException, RepositoryException {

		ValidatorData data = new ValidatorData();
		data.extractAndSetChoice(choice);
		
		File newFile=null;
		
		if(file!=null){
			newFile=multipartToFile(file);
			ValidateSkosFile skos=new ValidateSkosFile(choice,newFile);
			data.setErrorList(skos.validate());

		}else{
			
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
