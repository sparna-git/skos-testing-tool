package fr.sparna.validator;

import java.util.Map;

public class IssueDescription {
	
	protected Map<String ,String> description;
	protected String id;
	protected Map<String, String> lbconcept;
	
	
	public IssueDescription(String id, Map<String, String> description, Map<String, String> lbconcept) {
		super();
		this.description = description;
		this.id = id;
		this.lbconcept=lbconcept;
	}
	
	public String getDescriptionById(String Id) {
		return description.get(Id);
	}
	
	public String getLabelById(String Id) {
		return lbconcept.get(Id);
	}
	
	public Map<String, String> getDescription() {
		return description;
	}

	public String getId() {
		return id;
	}

	public Map<String, String> getLbconcept() {
		return lbconcept;
	}

	

	
	
	
	

}
