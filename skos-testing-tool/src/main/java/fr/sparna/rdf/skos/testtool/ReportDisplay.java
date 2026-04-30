package fr.sparna.rdf.skos.testtool;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

/**
 * Data used to display the report result page
 * @author thomas
 *
 */
public class ReportDisplay {


	protected List<IssueResultDisplay> errorList;

	protected List<String> choiceList;

	protected Integer rulesNumber;

	protected Integer rulesFail;

	protected Double executionTime;
	
	protected Long allconcepts;
	
	protected Long allconceptschemes;
	
	protected Long allcollections;
	
	protected String fileName;
	
	protected String issueDate;
	
	protected String urlconvert;

	public static final String KEY = ReportDisplay.class.getCanonicalName();


	public List<String> getChoiceList() {
		return choiceList;
	}

	public void setChoiceList(List<String> choiceList) {
		this.choiceList = choiceList;
	}

	public List<IssueResultDisplay> getErrorList() {
		return errorList;
	}

	public void setErrorList(List<IssueResultDisplay> errorList) {
		this.errorList = errorList;
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

	public String getUrlconvert() {
		return urlconvert;
	}

	public void setUrlconvert(String urlconvert) {
		this.urlconvert = urlconvert;
	}

	
	

}
