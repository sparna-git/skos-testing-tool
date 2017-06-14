package fr.sparna.validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;


public class ApplicationData {

	
	protected List<IssueDescription> descList;
	
	protected String userLocale;
	
	public ApplicationData() {
		super();
	
	}
	
	/*protected void init(){
		List<IssueDescription> DescList=new ArrayList<IssueDescription>();
		Map<String,IssueDescription> descRegistry = readApplicationContext("src/main/webapp/WEB-INF/rules.xml")
										.getBeansOfType(IssueDescription.class);
			for (IssueDescription h: descRegistry.values()) {
				DescList.add(h);
			}
		this.DescList=DescList;

	}*/
	


	public String getUserLocale() {
		this.userLocale = Locale.getDefault().getLanguage();
		return userLocale;
	}

	public List<IssueDescription> getDescList() {
		return descList;
	}
	@Autowired
	public void setDescList(List<IssueDescription> descList) {
		this.descList = descList;
	}

	public ValidatorConfig getValidatorConfig() {
		return ValidatorConfig.getInstance();
	}
	
	protected ApplicationContext readApplicationContext(String path) {
		ApplicationContext appContext = new FileSystemXmlApplicationContext(path);
		return appContext;
	}
}
