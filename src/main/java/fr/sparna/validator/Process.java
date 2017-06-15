package fr.sparna.validator;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
<<<<<<< HEAD
=======
import java.util.Set;
import java.util.stream.Collectors;
>>>>>>> d5219b1f73d64c75d1a649ee5cd545e9467b6e72

import org.openrdf.OpenRDFException;
import org.openrdf.model.Resource;
import org.openrdf.model.URI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.ac.univie.mminf.qskos4j.issues.Issue;
import at.ac.univie.mminf.qskos4j.issues.Issue.IssueType;
import at.ac.univie.mminf.qskos4j.issues.labels.util.EmptyLabelsResult;
import at.ac.univie.mminf.qskos4j.issues.labels.util.LabelConflict;
import at.ac.univie.mminf.qskos4j.issues.labels.util.LabelConflictsResult;
import at.ac.univie.mminf.qskos4j.issues.language.IncompleteLangCovResult;
import at.ac.univie.mminf.qskos4j.issues.language.OmittedOrInvalidLanguageTagsResult;
import at.ac.univie.mminf.qskos4j.issues.language.util.NoCommonLanguagesResult;
import at.ac.univie.mminf.qskos4j.issues.relations.UnidirectionallyRelatedConceptsResult;
import at.ac.univie.mminf.qskos4j.result.CollectionResult;
import at.ac.univie.mminf.qskos4j.result.Result.ReportFormat;

public class Process {
<<<<<<< HEAD

	List<SkosError> resultList=new ArrayList<SkosError>();
	private static final String FILENAME = "report.txt";
	private final Logger logger = LoggerFactory.getLogger(Process.class);
	
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
=======
	private final Logger logger = LoggerFactory.getLogger(Process.class);
	
	protected List<SkosError> resultList = new ArrayList<SkosError>();
	// language used to generate the report
	protected String lang;
	// RepositoryConnection
	
	public Process(String lang) {
		super();
		this.lang = lang;
>>>>>>> d5219b1f73d64c75d1a649ee5cd545e9467b6e72
	}

	@SuppressWarnings("rawtypes")
	public List<SkosError> createReport(Collection<Issue> issues) throws IOException, OpenRDFException {
<<<<<<< HEAD
		List<IssueDescription> DescList =ValidatorConfig.getInstance().applicationData.getDescList();
		
	
=======

		// for each issue...
>>>>>>> d5219b1f73d64c75d1a649ee5cd545e9467b6e72
		for (Issue issue : issues) {

			if(issue.getType()==IssueType.ANALYTICAL){
				
				logger.info("********---------"+issue.getName()+"-----------*********\n\n"+
							"************************Description********************************\n\n"
											+issue.getDescription()+"\n\n"
							+"***************************Data***************************************\n\n"+
								issue.getResult().getData().toString()+"\n\n"
							+"**********************************************************************\n\n"
						);
				SkosError error=new SkosError();
				error.setId(issue.getId());
				
				String stateText = "";
				if (issue.getResult().isProblematic()) {
					stateText = "FAIL";
					error.setSuccess(false);				
					String occurrenceCount = Long.toString(issue.getResult().occurrenceCount());
					error.setNumber(occurrenceCount);
					stateText += " (" +occurrenceCount+ ")";
				} else {
					stateText = "OK";
					error.setSuccess(true);
				}
				error.setState(stateText);
				
				// find issue 's description in user language
				IssueDescription desc = ValidatorConfig.getInstance().getApplicationData().findIssueDescriptionById(issue.getId());
				error.setDescription(desc.getDescriptionByLang(lang));
				error.setRuleName(desc.getLabelByLang(lang));

				// TODO : store also in IssueDescription
				if(issue.getWeblink()==null){
					error.setWeblink("");
				}else{
					error.setWeblink(issue.getWeblink().stringValue());
				}
				
<<<<<<< HEAD

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
=======
				System.out.println(issue.getId()+" : "+issue.getResult().getClass().getName());
				
				// store messages only if problematic
				// some Issues like "Common Languages" return the correct languages even if there is no issue, and we don't want to display it in this case
				
				if(issue.getResult().isProblematic()) {
					List<String> messages=new ArrayList<String>();
					
					/*-----------Incomplete language coverage----------*/
					if(issue.getResult() instanceof IncompleteLangCovResult){
						IncompleteLangCovResult res=(IncompleteLangCovResult) issue.getResult();
						res.getData().entrySet().stream().forEach(entry->{
							StringBuffer buffer = new StringBuffer();
							buffer.append("<a href=\""+entry.getKey().toString()+"\" target=\"_blank\">"+entry.getKey().toString()+"</a> : ");
							entry.getValue().stream().forEach(string -> buffer.append(string+ " "));
							buffer.append("\n");
							messages.add(buffer.toString());
						});
					}
					/*-----------------Unidirectional related concept----*/
					else if(issue.getResult() instanceof UnidirectionallyRelatedConceptsResult){
						UnidirectionallyRelatedConceptsResult res=(UnidirectionallyRelatedConceptsResult) issue.getResult();
						res.getData().entrySet().stream().forEach(entry->{
							StringBuffer buffer = new StringBuffer();
							entry.getKey().getElements().stream().forEach(aResource -> {
								buffer.append("<a href=\""+aResource.toString()+"\" target=\"_blank\">"+aResource.toString()+"</a>");
								buffer.append(", ");
							});
							buffer.delete(buffer.length()-2, buffer.length());
							buffer.append(" : ");
							buffer.append(entry.getValue());
							buffer.append("\n");
							messages.add(buffer.toString());
						});
>>>>>>> d5219b1f73d64c75d1a649ee5cd545e9467b6e72
					}
					/*-------------------Omitted or Invalid Language Tags------------*/
					else if(issue.getResult() instanceof OmittedOrInvalidLanguageTagsResult){
						OmittedOrInvalidLanguageTagsResult res=(OmittedOrInvalidLanguageTagsResult) issue.getResult();
						res.getData().entrySet().stream().forEach(entry->{
							StringBuffer buffer = new StringBuffer();
							buffer.append("<a href=\""+entry.getKey().toString()+"\" target=\"_blank\">"+entry.getKey().toString()+"</a> : ");
							entry.getValue().stream().forEach(aLiteral -> {
								buffer.append(aLiteral.toString()+", ");
							});
							buffer.delete(buffer.length()-2, buffer.length());
							messages.add(buffer.toString());
						});
					}
					/*-------------------Empty labels--------------------------------*/
					else if(issue.getResult() instanceof EmptyLabelsResult){
						EmptyLabelsResult res=(EmptyLabelsResult) issue.getResult();
						res.getData().entrySet().stream().forEach(entry->{
							StringBuffer buffer = new StringBuffer();
							buffer.append("<a href=\""+entry.getKey().toString()+"\" target=\"_blank\">"+entry.getKey().toString()+"</a> : ");
							entry.getValue().stream().forEach(aLabelType -> {
								buffer.append(aLabelType.toString()+", ");
							});
							buffer.delete(buffer.length()-2, buffer.length());
							messages.add(buffer.toString());						
						});
					}
					else if(issue.getResult() instanceof NoCommonLanguagesResult) {
						NoCommonLanguagesResult res=(NoCommonLanguagesResult) issue.getResult();
						res.getData().stream().forEach(aString->{
							messages.add(aString);
						});
					}
					/*--------------------Collection Result-------------------------*/
					else if(issue.getResult() instanceof CollectionResult<?>) {
						CollectionResult collectionResult = (CollectionResult)issue.getResult();
						Collection<?> collection = (Collection<?>)collectionResult.getData();
						collection.forEach(item-> {
							System.out.println("  "+issue.getId()+" : "+item.getClass().getName());
							if(item instanceof URI) {
								messages.add("<a href=\""+item.toString()+"\" target=\"_blank\">"+item.toString()+"</a>");
							} else if(item instanceof LabelConflict) {
								messages.add(item.toString());		
								// TODO : improve class LabelConflict to get our hand on the inner LabeledResource to get the label
	//							LabelConflict aConflict = (LabelConflict)item;
	//							StringBuffer buffer = new StringBuffer();	//							
	//							buffer.append("<ul>\n");
	//							aConflict.getAffectedResources().stream().forEach(anAffectedResource-> {
	//								buffer.append("<li>"+anAffectedResource.toString()+"</li>");
	//							});
	//							buffer.append("</ul>\n");
	//							messages.add(buffer.toString());
							} else {
								messages.add(item.toString());
							}
						}					
						);
					} else {
						System.out.println("Unknown result type "+issue.getResult().getClass().getName());
					}
					
					error.setErrorList(messages);
				}
<<<<<<< HEAD
				
				error.setErrorList(uri);
=======
>>>>>>> d5219b1f73d64c75d1a649ee5cd545e9467b6e72
				resultList.add(error);
				
			}
		}
		return resultList;
	}
}
