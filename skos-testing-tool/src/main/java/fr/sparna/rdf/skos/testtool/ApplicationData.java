package fr.sparna.rdf.skos.testtool;

import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;


public class ApplicationData {
	
	protected List<IssueDescription> issueDescriptions;
	
	public ApplicationData() {
		super();	
	}

	public List<IssueDescription> getIssueDescriptions() {
		return issueDescriptions;
	}
	
	@Autowired
	public void setIssueDescriptions(List<IssueDescription> descList) {
		// trier la liste sur le code
		descList.sort(new Comparator<IssueDescription>() {

			@Override
			public int compare(IssueDescription id1, IssueDescription id2) {
				return id1.getId().compareTo(id2.getId());
			}
			
		});
		this.issueDescriptions = descList;
	}
	
	/**
	 * Finds an IssueDescription based on its id
	 * @param id
	 * @return
	 */
	public IssueDescription findIssueDescriptionById(String id) {
		for (IssueDescription aDesc : this.issueDescriptions) {
			if(aDesc.id.equals(id)) {
				return aDesc;
			}
		}
		return null;
	}

	public TestToolConfig getValidatorConfig() {
		return TestToolConfig.getInstance();
	}

}
