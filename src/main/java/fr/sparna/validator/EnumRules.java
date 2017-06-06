package fr.sparna.validator;

public enum EnumRules {


	oc("Orphan Concepts"), el("Empty Labels"),
	ml("Missing Labels"), uc("Undocumented Concepts"),

	ilc("Incomplete Language Coverage"),oilt("Omitted or Invalid Language Tags"),

	dcc("Disconnected Concept Clusters"), hr("Hierarchical Redundancy"),

	anr("Ambiguous Notation References"),

	rrc("Reflexively Related Concepts"),strc("Solely Transitively Related Concepts"),

	urc("Unidirectionally Related Concepts"), var("Valueless Associative Relations"),

	mri("Mapping Relations Misuse"), rc("Relation Clashes"),

	usr("Undefined SKOS Resources"),mol("Missing Out-Links"),

	husv("HTTP URI Scheme Violation"), tchbc("Top Concepts Having Broader Concepts"),

	otc("Omitted Top Concepts"),ncl("No Common Languages"), 

	ipl("Inconsistent Preferred Labels"),dlv("Disjoint Labels Violation"),

	ol("Overlapping Labels"),ucil("Unprintable Characters in Labels");


	protected String label;


	private EnumRules(String label){

		this.label=label;
	}

	public String getLabel() {
		return label;
	}

}
