package fr.sparna.validator;

import java.util.ArrayList;
import java.util.List;

public class ValidatorData {

	
	protected List<SkosError> errorList;
	
	protected List<String> choiceList;
	
	protected String msg;

	public static final String KEY = ValidatorData.class.getCanonicalName();
	
	
	public void extractAndSetChoice(String choice){
		List<String> list=new ArrayList<String>();
		for(String ch:choice.split("[,]")){
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
	
	
}
