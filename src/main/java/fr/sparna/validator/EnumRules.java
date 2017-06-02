package fr.sparna.validator;

public enum EnumRules {
	
	
	oc("Orphan Concepts"), oilt("Omitted or Invalid Language Tags"),
	
	uc("Undocumented Concepts"), ilc("Incomplete Language Coverage"),
	
	dcc("Disconnected Concept Clusters"), ml("Missing Labels"),
	
	el("Empty Labels"), anr("Ambiguous Notation References"),
	
	rrc("Reflexively Related Concepts"),strc("Solely Transitively Related Concepts"),
	
	urc("Unidirectionally Related Concepts"), var("Valueless Associative Relations"),
	
	hr("Hierarchical Redundancy"), mc("Mapping Clashes"),
	
	mri("Mapping Relations Misuse"), rc("Relation Clashes"),
	
	usr("Undefined SKOS Resources"),mol("Missing Out-Links"),
	
	husv("HTTP URI Scheme Violation"), tchbc("Top Concepts Having Broader Concepts"),
	
	otc("Omitted Top Concepts"),
	ncl("No Common Languages"), dlv("Disjoint Labels Violation"),
	
	ipl("Inconsistent Preferred Labels"),
	
	ol("Overlapping Labels"),ucil("Unprintable Characters in Labels");
	
	
	protected String label;

	
	private EnumRules(String label){
		
		this.label=label;
	}
	
	public String getLabel() {
		return label;
	}

}
