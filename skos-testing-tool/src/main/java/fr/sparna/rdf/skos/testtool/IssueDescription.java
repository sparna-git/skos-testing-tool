package fr.sparna.rdf.skos.testtool;

import java.util.Map;

/**
 * A multilingual issue description, with multilingual descriptions and labels
 */
public class IssueDescription {
	
	public enum IssueLevel {
		INFO,
		WARNING,
		DANGER
	}
	
	protected String id;
	protected Map<String ,String> description;
	protected Map<String, String> lbconcept;
	protected String link;
	protected boolean checked;
	protected IssueLevel level = IssueLevel.DANGER;
	
	public IssueDescription(
			String id,
			Map<String, String> description,
			Map<String, String> lbconcept,
			String link,
			boolean checked
	) {
		super();
		this.description = description;
		this.id = id;
		this.lbconcept=lbconcept;
		this.link=link;
		this.checked=checked;
	}
	
	public String getDescriptionByLang(String lang) {
		return description.get(lang);
	}
	
	public String getLabelByLang(String lang) {
		return lbconcept.get(lang);
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

	public String getLink() {
		return link;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public IssueLevel getLevel() {
		return level;
	}

	public void setLevel(IssueLevel level) {
		this.level = level;
	}

}
