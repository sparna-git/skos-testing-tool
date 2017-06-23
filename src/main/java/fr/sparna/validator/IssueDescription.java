package fr.sparna.validator;

import java.util.Map;

/**
 * A multilingual issue description, with multilingual descriptions and labels
 */
public class IssueDescription {
	
	protected String id;
	protected Map<String ,String> description;
	protected Map<String, String> lbconcept;
	protected String link;
	protected boolean checked;
	
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

}
