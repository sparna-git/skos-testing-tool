package fr.sparna.rdf.skos.testtool.fix;

import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.model.Statement;
import org.eclipse.rdf4j.model.impl.LinkedHashModel;
import org.eclipse.rdf4j.repository.RepositoryConnection;
import org.eclipse.rdf4j.rio.helpers.StatementCollector;

/**
 * A proposal for fixing an Issue in the data, consisting of a set of triples to add, and a set of triples to delete.
 * @author thomas.francart@sparna.fr
 */
public class FixProposal {

	protected Model addedStatements = new LinkedHashModel();
	protected Model deletedStatements = new LinkedHashModel();	
	
	/**
	 * Helper method to gather the statements to be added in this fix proposal
	 * @param c
	 */
	public void collectAddedStatements(StatementCollector c) {
		if(this.addedStatements != null) {
			this.addedStatements = new LinkedHashModel();
		}
		this.addedStatements.addAll(c.getStatements());
	}
	
	/**
	 * Helper method to gather the statements to be deleted in this fix proposal, by making sure each collected statement
	 * actually exists in the repository (to avoid suggesting statements to be deleted that actually don't exist)
	 * @param c
	 * @param connection
	 */
	public void collectDeletedStatements(StatementCollector c, RepositoryConnection connection) {
		if(this.deletedStatements == null) {
			this.deletedStatements = new LinkedHashModel();
		}
		this.deletedStatements.addAll(c.getStatements());
		// keep only the statements that are actually present in the Repository
		this.deletedStatements.removeIf((Statement s) -> !connection.hasStatement(s, false));
	}
	
	public void setAddedStatements(Model addedStatements) {
		this.addedStatements = addedStatements;
	}

	public void setDeletedStatements(Model deletedStatements) {
		this.deletedStatements = deletedStatements;
	}

	public Model getAddedStatements() {
		return addedStatements;
	}
	
	public Model getDeletedStatements() {
		return deletedStatements;
	}
	
	public String printDiff() {
		StringBuffer buffer = new StringBuffer();
		if(this.addedStatements != null) {
			this.addedStatements.stream().forEach((Statement s) ->
				buffer.append("+ "+s+" \n")
			);
		}
		if(this.deletedStatements != null) {
			this.deletedStatements.stream().forEach((Statement s) ->
			buffer.append("- "+s+" \n")
			);
		}
		return buffer.toString();
	}
	
}
