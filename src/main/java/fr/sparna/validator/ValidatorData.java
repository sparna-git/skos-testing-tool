package fr.sparna.validator;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

public class ValidatorData {


	protected List<SkosError> errorList;

	protected List<String> choiceList;

	protected String msg;

	protected Integer rulesNumber;

	protected Integer rulesFail;

	protected Double executionTime;
	
	protected Long allconcepts;
	
	protected Long allconceptschemes;
	
	protected Long allcollections;
	
	protected String fileName;
	
	protected String issueDate;

	public static final String KEY = ValidatorData.class.getCanonicalName();

	public List<String> getChoiceList() {
		return choiceList;
	}

	public void setChoiceList(List<String> choiceList) {
		this.choiceList = choiceList;
	}

	public List<SkosError> getErrorList() {
		return errorList;
	}

	public void setErrorList(List<SkosError> errorList) {
		this.errorList = errorList;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Integer getRulesNumber() {
		return rulesNumber;
	}

	public void setRulesNumber(Integer ruleNumber) {
		this.rulesNumber = ruleNumber;
	}

	public Integer getRulesFail() {
		return rulesFail;
	}

	public void setRulesFail(Integer rulesFail) {
		this.rulesFail = rulesFail;
	}

	public Double getExecutionTime() {
		return executionTime;
	}
	
	public String getExecutionTimeString() {
		NumberFormat formatter = new DecimalFormat("#0.00");
		return formatter.format(this.getExecutionTime());
	}

	public void setExecutionTime(Double executionTime) {
		this.executionTime = executionTime;
	}

	public Long getAllconcepts() {
		return allconcepts;
	}

	public void setAllconcepts(Long allconcepts) {
		this.allconcepts = allconcepts;
	}

	public Long getAllconceptschemes() {
		return allconceptschemes;
	}

	public void setAllconceptschemes(Long allconceptscheme) {
		this.allconceptschemes = allconceptscheme;
	}

	public Long getAllcollections() {
		return allcollections;
	}

	public void setAllcollections(Long allcollection) {
		this.allcollections = allcollection;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String filename) {
		this.fileName = filename;
	}

	public String getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(String issuedate) {
		this.issueDate = issuedate;
	}

	
	

}
