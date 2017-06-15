package fr.sparna.validator;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;


public class ApplicationData {
	
	protected List<IssueDescription> issueDescriptions;
	
	public ApplicationData() {
		super();	
	}

	public List<IssueDescription> getIssueDescriptions() {
		return issueDescriptions;
	}
	
	@Autowired
	public void setIssueDescriptions(List<IssueDescription> descList) {
		this.issueDescriptions = descList;
	}
	
	/**
	 * Finds an IssueDescription based on its id
	 * @param id
	 * @return
	 */
	public IssueDescription findIssueDescriptionById(String id) {
		for (IssueDescription aDesc : this.issueDescriptions) {
			if(aDesc.id.equals(id)) {
				return aDesc;
			}
		}
		return null;
	}

	public ValidatorConfig getValidatorConfig() {
		return ValidatorConfig.getInstance();
	}

}
