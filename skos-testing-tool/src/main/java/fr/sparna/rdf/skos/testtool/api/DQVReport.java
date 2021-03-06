package fr.sparna.rdf.skos.testtool.api;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.eclipse.rdf4j.RDF4JException;
import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Literal;
import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.model.Value;
import org.eclipse.rdf4j.model.ValueFactory;
import org.eclipse.rdf4j.model.impl.SimpleValueFactory;
import org.eclipse.rdf4j.model.vocabulary.RDFS;
import org.eclipse.rdf4j.model.vocabulary.XMLSchema;
import at.ac.univie.mminf.qskos4j.issues.Issue;
import at.ac.univie.mminf.qskos4j.issues.labels.util.EmptyLabelsResult;
import at.ac.univie.mminf.qskos4j.issues.language.IncompleteLangCovResult;
import at.ac.univie.mminf.qskos4j.issues.language.OmittedOrInvalidLanguageTagsResult;
import at.ac.univie.mminf.qskos4j.issues.relations.UnidirectionallyRelatedConceptsResult;
import at.ac.univie.mminf.qskos4j.result.CollectionResult;
import at.ac.univie.mminf.qskos4j.util.IssueDescriptor.IssueType;
import fr.sparna.rdf.skos.testtool.IssueDescription;
import fr.sparna.rdf.skos.testtool.ReportDisplay;
import fr.sparna.rdf.skos.testtool.TestToolConfig;

/**
 * Generate the qdv report
 * @author clarvie
 *
 */
public class DQVReport {

	protected String computedOn;

	protected String lang;

	protected Model model;
	
	protected ValueFactory f;
	
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd  HH:mm:ss");
	Date date = new Date();
	String dates = dateFormat.format(date);
	String key = UUID.randomUUID().toString();

	public DQVReport(String computedOn, String lang, Model model,ValueFactory f) {
		super();
		this.computedOn = computedOn;
		this.lang=lang;
		this.model=model;
		this.f=f;
	}
	
	public Model dqvout(Collection<Issue> qSkosResult) throws IOException{
		
		for (Issue issue : qSkosResult) {
			if(issue.getIssueDescriptor().getType()==IssueType.ANALYTICAL){
				writeDQVReport(issue);
			}
		}
		return this.model;
	}

	/**
	 * 
	 * @param issue
	 * @throws IOException
	 * @throws RDF4JException
	 */
	private void writeDQVReport(Issue issue)
			throws IOException, RDF4JException
	{

		
		IssueDescription desc = TestToolConfig.getInstance().getApplicationData().findIssueDescriptionById(issue.getIssueDescriptor().getId());
		String ndqv= "http://www.w3.org/ns/dqv#";
		String nex="http://w3id.org/quality/qskos/";

		IRI ptype=  f.createIRI("http://www.w3.org/1999/02/22-rdf-syntax-ns#type");
		IRI pcomputedOn = f.createIRI(ndqv+"computedOn");
		IRI pdate = f.createIRI("http://purl.org/dc/terms/date");
		IRI pvalue = f.createIRI(ndqv+"value");
		IRI pisMeasurementOf = f.createIRI(ndqv+"isMeasurementOf");

		IRI measure= f.createIRI(nex+issue.getIssueDescriptor().getName().replace(" ", "")+"#"+key);
		model.add(measure, ptype, f.createIRI(ndqv+"QualityMeasurement"));

		if (this.computedOn.startsWith("http://") || this.computedOn.startsWith("https://"))model.add(measure, pcomputedOn,f.createIRI(this.computedOn));
		else {
			Value datasetPath = f.createLiteral(this.computedOn, XMLSchema.STRING);
			model.add(measure, pcomputedOn,datasetPath);
		}

		Value ldate = f.createLiteral(dates, XMLSchema.DATETIME);
		model.add(measure, pdate, ldate);
		int i = new Integer(0);
		String res ;
		Value lval;
		StringBuffer buffer = new StringBuffer();
		try {
			long r = issue.getResult().occurrenceCount();
			if (issue.getResult().isProblematic()){
				res = Long.toString(issue.getResult().occurrenceCount());
				IRI dataProperty= f.createIRI("http://purl.org/dc/terms/relation");

				for(IRI st : getAlldata(issue)){
					model.add(measure, dataProperty,st);
				}
			}
			else
				res = "0";
			lval = f.createLiteral( res , XMLSchema.INTEGER);
		}catch (java.lang.UnsupportedOperationException e) {
			// in case the issue is not associated with a list of elements having the problem the methods occurenceCount is not defined. That happen with the issue "No Common Languages:"
			// then we  encode the result as a boolean, plus a annotation explaining .
			if (issue.getResult().isProblematic()) lval = f.createLiteral( "true" , XMLSchema.BOOLEAN);
			else lval = f.createLiteral( "false" , XMLSchema.BOOLEAN);
		} 
		model.add(measure, pvalue, lval);

		IRI uriDimension= f.createIRI(nex+"numOf"+issue.getIssueDescriptor().getName().replace(" ", ""));
		model.add(measure, pisMeasurementOf, uriDimension);

		Literal uriName= f.createLiteral("rule "+desc.getId()+" checked on "+computedOn+" at date "+dates);
		model.add(measure, RDFS.LABEL, uriName);


	}
	/**
	 * 
	 * @param issue
	 * @return
	 */
	public 	List<IRI> getAlldata(Issue issue){
		List<IRI> messages=new ArrayList<IRI>();


		if(issue.getResult() instanceof CollectionResult<?>) {
			CollectionResult collectionResult = (CollectionResult)issue.getResult();
			Collection<?> collection = (Collection<?>)collectionResult.getData();

			collection.forEach(item-> {

				if(item instanceof IRI) {
					messages.add((IRI)item);
				} else if (item instanceof Collection) {
					// collections inside a collection, case of "disconnected concept clusters"
					Collection c = (Collection)item;								
					c.stream().forEach(elmt -> {
						if(elmt instanceof IRI) {
							messages.add((IRI)elmt);
						} 
					});
				} 
			});
		}
		if(issue.getResult() instanceof OmittedOrInvalidLanguageTagsResult){
			OmittedOrInvalidLanguageTagsResult res=(OmittedOrInvalidLanguageTagsResult) issue.getResult();
			res.getData().entrySet().stream().forEach(entry->{
				StringBuffer buffer = new StringBuffer();
				messages.add((IRI)entry.getKey());
			});
		}
		/*-------------------Empty labels--------------------------------*/
		else if(issue.getResult() instanceof EmptyLabelsResult){
			EmptyLabelsResult res=(EmptyLabelsResult) issue.getResult();
			res.getData().entrySet().stream().forEach(entry->{
				messages.add((IRI)entry.getKey());					
			});
		}
		if(issue.getResult() instanceof IncompleteLangCovResult){
			IncompleteLangCovResult res=(IncompleteLangCovResult) issue.getResult();
			res.getData().entrySet().stream().forEach(entry->{
				messages.add((IRI)entry.getKey());
			});

		}else if(issue.getResult() instanceof UnidirectionallyRelatedConceptsResult){
			UnidirectionallyRelatedConceptsResult res=(UnidirectionallyRelatedConceptsResult) issue.getResult();
			res.getData().entrySet().stream().forEach(entry->{
				entry.getKey().getElements().stream().forEach(aResource ->{
					messages.add((IRI)aResource);
				});
			});
		}
		return messages;
	}

}
