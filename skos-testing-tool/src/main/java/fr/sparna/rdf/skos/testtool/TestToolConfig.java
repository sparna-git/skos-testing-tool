package fr.sparna.rdf.skos.testtool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class TestToolConfig {
	
private Logger log = LoggerFactory.getLogger(this.getClass().getName());
	
	// singleton instance
	private static TestToolConfig instance;
	
	@Autowired
	protected ApplicationData applicationData;
	
	
	/**
	 * Singleton private constructor
	 */
	private TestToolConfig() {
	}
	
	/**
	 * Singleton Access
	 */
	public static TestToolConfig getInstance() {
		if(instance == null) {
			instance = new TestToolConfig();
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
