package fr.sparna.validator;

import java.util.List;

import fr.sparna.validator.IssueDescription.IssueLevel;

public class SkosError {
	
	protected String ruleName;
	
	protected String state; 
	
	protected String id;
	
	protected String description;
	
	protected String weblink;
	
	protected String number;
	
	protected List<String> errorList;
	
	protected boolean success;
	
	protected IssueLevel level;
	
	public String getCssClass() {
		if(success) {
			return "success";
		} else {
			switch (level) {
			case DANGER:
				return "danger";
			case INFO:
				return "info";
			case WARNING:
				return "warning";
			default:
				return "danger";			
			}
		}
	}
	
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getWeblink() {
		return weblink;
	}
	public void setWeblink(String weblink) {
		this.weblink = weblink;
	}
	public String getRuleName() {
		return ruleName;
	}
	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}	
	public List<String> getErrorList() {
		return errorList;
	}
	public void setErrorList(List<String> errorList) {
		this.errorList = errorList;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public IssueLevel getLevel() {
		return level;
	}
	public void setLevel(IssueLevel level) {
		this.level = level;
	}

}
