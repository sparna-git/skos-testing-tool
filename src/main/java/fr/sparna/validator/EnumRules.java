package fr.sparna.validator;

public enum EnumRules {


	oc("Orphan Concepts","https://github.com/cmader/qSKOS/wiki/Quality-Issues#Orphan_Concepts"),
	el("Empty Labels","https://github.com/cmader/qSKOS/wiki/Quality-Issues#Empty_Labels"),
	ml("Missing Labels","https://github.com/cmader/qSKOS/wiki/Quality-Issues#Missing_Labels"), 
	uc("Undocumented Concepts","https://github.com/cmader/qSKOS/wiki/Quality-Issues#Undocumented_Concepts"),
	ilc("Incomplete Language Coverage","https://github.com/cmader/qSKOS/wiki/Quality-Issues#Incomplete_Language_Coverage"),
	oilt("Omitted or Invalid Language Tags","https://github.com/cmader/qSKOS/wiki/Quality-Issues#Omitted_or_Invalid_Language_Tags"),

	dcc("Disconnected Concept Clusters","https://github.com/cmader/qSKOS/wiki/Quality-Issues#Disconnected_Concept_Clusters"),
	hr("Hierarchical Redundancy","https://github.com/cmader/qSKOS/wiki/Quality-Issues#Hierarchical_Redundancy"),

	anr("Ambiguous Notation References","https://github.com/cmader/qSKOS/wiki/Quality-Issues#Ambiguous_Notation_References"),

	rrc("Reflexively Related Concepts","https://github.com/cmader/qSKOS/wiki/Quality-Issues#Reflexively_Related_Concepts"),
	strc("Solely Transitively Related Concepts","https://github.com/cmader/qSKOS/wiki/Quality-Issues#Solely_Transitively_Related_Concepts"),

	urc("Unidirectionally Related Concepts","https://github.com/cmader/qSKOS/wiki/Quality-Issues#Unidirectionally_Related_Concepts"), 
	var("Valueless Associative Relations","https://github.com/cmader/qSKOS/wiki/Quality-Issues#Valueless_Associative_Relations"),

	mri("Mapping Relations Misuse","https://github.com/cmader/qSKOS/wiki/Quality-Issues#Mapping_Relations_Misuse"), 
	rc("Relation Clashes","https://github.com/cmader/qSKOS/wiki/Quality-Issues#Relation_Clashes"),

	usr("Undefined SKOS Resources","https://github.com/cmader/qSKOS/wiki/Quality-Issues#Undefined_SKOS_Resources"),
	mol("Missing Out-Links","https://github.com/cmader/qSKOS/wiki/Quality-Issues#Missing_OutLinks"),

	husv("HTTP URI Scheme Violation","https://github.com/cmader/qSKOS/wiki/Quality-Issues#HTTP_URI_Scheme_Violation"), 
	tchbc("Top Concepts Having Broader Concepts","https://github.com/cmader/qSKOS/wiki/Quality-Issues#Top Concepts_Having_Broader_Concepts"),

	otc("Omitted Top Concepts","https://github.com/cmader/qSKOS/wiki/Quality-Issues#Omitted_Top_Concepts"),
	ncl("No Common Languages","https://github.com/cmader/qSKOS/wiki/Quality-Issues#No_Common_Language"), 

	ipl("Inconsistent Preferred Labels","https://github.com/cmader/qSKOS/wiki/Quality-Issues#Inconsistent_Preferred_Labels"),
	dlv("Disjoint Labels Violation","https://github.com/cmader/qSKOS/wiki/Quality-Issues#Disjoint_Labels_Violation"),

	ol("Overlapping Labels","https://github.com/cmader/qSKOS/wiki/Quality-Issues#Overlapping_Labels"),
	ucil("Unprintable Characters in Labels","https://github.com/cmader/qSKOS/wiki/Quality-Issues#Unprintable_Characters_in_Labels"),
	bl("Broken Links","https://github.com/cmader/qSKOS/wiki/Quality-Issues#Broken_Links");

	protected String label;
	
	protected String link;


	private EnumRules(String label,String link){

		this.label=label;
		this.link=link;
		
	}

	public String getLabel() {
		return label;
	}

	public String getLink() {
		return link;
	}

	
}
