package fr.sparna.rdf.skos.testtool.fix;

import org.eclipse.rdf4j.repository.RepositoryConnection;

import at.ac.univie.mminf.qskos4j.issues.Issue;
import at.ac.univie.mminf.qskos4j.result.Result;

/**
 * A Fix policy for an issue result.
 * 
 * @author thomas.francart@sparna.fr
 *
 * @param <T> The type of result this fix can be computed on;
 */
public abstract class FixPolicy<T extends Issue<? extends Result<?>>> {

	protected Class<? extends Issue<? extends Result<?>>> applicableForIssue;
	
	public FixPolicy(Class<? extends Issue<? extends Result<?>>> applicableForIssue) {
		this.applicableForIssue = applicableForIssue;
	}
	
	public boolean applicableFor(Class<Issue<? extends Result<?>>> issueClass) {
		return issueClass.equals(this.applicableForIssue);
	}
	
	/**
	 * Computes and returns a fix proposal for the given issue result
	 * @param issueResult
	 * @return
	 */
	public abstract FixProposal getFixProposal(T issue, RepositoryConnection connection);
	
}
