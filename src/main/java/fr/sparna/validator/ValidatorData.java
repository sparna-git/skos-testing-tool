package fr.sparna.validator;

import java.util.ArrayList;
import java.util.List;

public class ValidatorData {


	protected List<SkosError> errorList;

	protected List<String> choiceList;

	protected String msg;

	protected Integer rulesNumber;

	protected Integer rulesFail;

	protected Long executionTime;

	public static final String KEY = ValidatorData.class.getCanonicalName();



	public void extractAndSetChoice(String choice){
		List<String> list=new ArrayList<String>();
		for(String ch:choice.split("[-]")){
			list.add(ch);
		}
		this.choiceList=list;
	}

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

	public Long getExecutionTime() {
		return executionTime;
	}

	public void setExecutionTime(Long executionTime) {
		this.executionTime = executionTime;
	}



}
