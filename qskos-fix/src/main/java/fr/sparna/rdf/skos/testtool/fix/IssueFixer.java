package fr.sparna.rdf.skos.testtool.fix;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.rdf4j.repository.RepositoryConnection;

import at.ac.univie.mminf.qskos4j.issues.Issue;
import at.ac.univie.mminf.qskos4j.result.Result;

public class IssueFixer {
	
	public List<FixProposal> fix(Issue issue, RepositoryConnection connection) {
		List<FixProposal> proposals = new ArrayList<FixProposal>();
		List<FixPolicy<Issue<? extends Result<?>>>> policies = getApplicableFixPolicies(issue);
		// no associated policy = no fix proposals
		for (FixPolicy<Issue<? extends Result<?>>> aPolicy : policies) {
			FixProposal aProposal = aPolicy.getFixProposal(issue, connection);
			proposals.add(aProposal);
		}
		return proposals;
	}
	
	public static List<FixPolicy<Issue<? extends Result<?>>>> getApplicableFixPolicies(Issue<? extends Result<?>> issue) {
		List<FixPolicy<Issue<? extends Result<?>>>> policies = new ArrayList<FixPolicy<Issue<? extends Result<?>>>>();
		for (FixPolicy fixPolicy : FixPolicyRegistry.getInstance().getAll()) {
			if(fixPolicy.applicableFor(issue.getClass())) {
				policies.add(fixPolicy);
			}
		}
		return policies;
	}
}
