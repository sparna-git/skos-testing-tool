package fr.sparna.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class ValidatorConfig {
	
private Logger log = LoggerFactory.getLogger(this.getClass().getName());
	
	// singleton instance
	private static ValidatorConfig instance;
	
	@Autowired
	protected ApplicationData applicationData;
	
	
	
	/**
	 * Singleton private constructor
	 */
	private ValidatorConfig() {
	}
	
	/**
	 * Singleton Access
	 */
	public static ValidatorConfig getInstance() {
		if(instance == null) {
			instance = new ValidatorConfig();
		}
		return instance;
	}

	public ApplicationData getApplicationData() {
		return applicationData;
	}
	
	public void setApplicationData(ApplicationData applicationData) {
		this.applicationData = applicationData;
	}


}
