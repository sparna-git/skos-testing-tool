package fr.sparna.validator;

import java.util.ArrayList;
import java.util.List;

public class ApplicationData {

	protected List<EnumRules> rulesList;


	
	public ApplicationData() {
		super();
		init();

	}
	
	protected void init(){
		List<EnumRules> list=new ArrayList<EnumRules>();

		for (EnumRules type : EnumRules.values()) {
			list.add(type);
		}
		
		this.setRulesList(list);

	}
	
	public List<EnumRules> getRulesList() {
		return rulesList;
	}
	public void setRulesList(List<EnumRules> rulesList) {
		this.rulesList = rulesList;
	}
	public ValidatorConfig getValidatorConfig() {
		return ValidatorConfig.getInstance();
	}
}
