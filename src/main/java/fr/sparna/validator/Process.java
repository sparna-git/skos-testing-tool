package fr.sparna.validator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.openrdf.OpenRDFException;
import org.openrdf.model.Resource;
import org.openrdf.model.URI;
import org.slf4j.Logger;

import at.ac.univie.mminf.qskos4j.issues.Issue;
import at.ac.univie.mminf.qskos4j.issues.Issue.IssueType;
import at.ac.univie.mminf.qskos4j.issues.labels.EmptyLabeledResources;
import at.ac.univie.mminf.qskos4j.issues.labels.util.EmptyLabelsResult;
import at.ac.univie.mminf.qskos4j.issues.labels.util.LabelConflict;
import at.ac.univie.mminf.qskos4j.issues.labels.util.LabelConflictsResult;
import at.ac.univie.mminf.qskos4j.issues.language.IncompleteLangCovResult;
import at.ac.univie.mminf.qskos4j.issues.language.OmittedOrInvalidLanguageTagsResult;
import at.ac.univie.mminf.qskos4j.issues.relations.UnidirectionallyRelatedConceptsResult;
import at.ac.univie.mminf.qskos4j.result.CollectionResult;

public class Process {

	List<SkosError> resultList=new ArrayList<SkosError>();

	@SuppressWarnings("rawtypes")
	public void processIssues(Collection<Issue> issues, Logger logger) throws OpenRDFException{
		int issueNumber = 0;
		Iterator<Issue> issueIt = issues.iterator();
		while (issueIt.hasNext()) {
			Issue issue = issueIt.next();
			issueNumber++;
			logger.info("Processing issue " + issueNumber + " of " + issues.size() + " (" + issue.getName() + ")");
			issue.getResult();

		}

		logger.info("Report complete!");
	}

	@SuppressWarnings("rawtypes")
	public String prepareOccurrenceText(Issue issue,SkosError error) throws OpenRDFException {
		String occurrenceText = "";
		if (issue.getResult().isProblematic()) {
			occurrenceText = "FAIL";
			error.setSuccess(false);
			try {
				String occurrenceCount = Long.toString(issue.getResult().occurrenceCount());
				error.setNumber(occurrenceCount);
				occurrenceText += " (" +occurrenceCount+ ")";
			}
			catch (UnsupportedOperationException e) {
				// ignore this
			}
		}
		else {
			occurrenceText = "OK (no potential problems found)";
			error.setSuccess(true);
		}
		return occurrenceText;
	}

	@SuppressWarnings("rawtypes")
	public List<SkosError> createReport(Collection<Issue> issues) throws IOException, OpenRDFException {

		for (Issue issue : issues) {
			

			if(issue.getType()==IssueType.ANALYTICAL){
				
				SkosError error=new SkosError();
				List<String> uri=new ArrayList<String>();
				error.setRuleName(issue.getName());
				error.setState(prepareOccurrenceText(issue,error));
				error.setDescription(issue.getDescription());
				error.setId(issue.getId());
				
				if(issue.getWeblink()==null){
					error.setWeblink("");
				}else{
					error.setWeblink(issue.getWeblink().stringValue());
				}
				

				/*-----------Incomplete language coverage----------*/
				if(issue.getResult() instanceof IncompleteLangCovResult){
					IncompleteLangCovResult res=(IncompleteLangCovResult) issue.getResult();
					res.getData().keySet().stream().forEach(action->{
						uri.add(action.stringValue());

					});
				}
				/*-----------------Unidirectional related concept----*/
				if(issue.getResult() instanceof UnidirectionallyRelatedConceptsResult){
					UnidirectionallyRelatedConceptsResult res=(UnidirectionallyRelatedConceptsResult) issue.getResult();
					res.getData().keySet().stream().forEach(action->{
						uri.add(action.toString());

					});
				}
				/*-------------------Omitted or Invalid Language Tags------------*/
				if(issue.getResult() instanceof OmittedOrInvalidLanguageTagsResult){
					OmittedOrInvalidLanguageTagsResult res=(OmittedOrInvalidLanguageTagsResult) issue.getResult();
					res.getData().keySet().stream().forEach(action->{
						uri.add(action.stringValue());

					});
				}
				/*-------------------Empty labels--------------------------------*/
				if(issue.getResult() instanceof EmptyLabelsResult){

					EmptyLabelsResult res=(EmptyLabelsResult) issue.getResult();
					System.out.println("map :"+res.getData());
					res.getData().keySet().stream().forEach(action->{
						uri.add(action.stringValue());
					});
				}
				/*--------------------Collection Result-------------------------*/
				if(issue.getResult() instanceof CollectionResult) {


					if(issue.getResult().getData() instanceof URI){
						Collection<URI> preciseResult = (Collection<URI>)issue.getResult().getData();
						for (URI res : preciseResult) {
							uri.add(res.stringValue());

						}
					}
					if(issue.getResult().getData() instanceof Resource){
						Collection<Resource> preciseResult = (Collection<Resource>)issue.getResult().getData();	
						for (Resource res : preciseResult) {
							uri.add(res.stringValue());


						}
					}

					if(issue.getResult().getData() instanceof HashSet){
						HashSet preciseResult = (HashSet)issue.getResult().getData();
						preciseResult.stream().forEach(action->{
							uri.add(action.toString());
						});

					}

					if(issue.getResult().getData() instanceof ArrayList){
						ArrayList preciseResult = (ArrayList)issue.getResult().getData();
						preciseResult.stream().forEach(action->{
							uri.add(action.toString());
						});

					}
					
				}
				error.setErrorList(uri);
				resultList.add(error);
			}
		
			

		}
		return resultList;
	}
}
