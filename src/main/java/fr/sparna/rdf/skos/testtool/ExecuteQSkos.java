package fr.sparna.rdf.skos.testtool;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Iterator;

import org.eclipse.rdf4j.RDF4JException;
import org.eclipse.rdf4j.model.ValueFactory;
import org.eclipse.rdf4j.model.vocabulary.RDF;
import org.eclipse.rdf4j.query.QueryLanguage;
import org.eclipse.rdf4j.query.Update;
import org.eclipse.rdf4j.repository.Repository;
import org.eclipse.rdf4j.repository.RepositoryConnection;
import org.eclipse.rdf4j.repository.sail.SailRepository;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.eclipse.rdf4j.rio.Rio;
import org.eclipse.rdf4j.sail.inferencer.fc.ForwardChainingRDFSInferencer;
import org.eclipse.rdf4j.sail.memory.MemoryStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.ac.univie.mminf.qskos4j.QSkos;
import at.ac.univie.mminf.qskos4j.issues.Issue;
import at.ac.univie.mminf.qskos4j.util.vocab.SkosOntology;



public class ExecuteQSkos {
	private final Logger logger = LoggerFactory.getLogger(ExecuteQSkos.class);
	
	protected String rules;

	protected Integer rulesNumber;
	
	private ValueFactory factory = null;
	
	//protected long collectionNumber;
	
	public ExecuteQSkos(String rules) {
		super();
		this.rules = rules;
	}


	/**
	 * Validate a File
	 * @param f
	 * @return
	 * @throws RDF4JException
	 * @throws IOException
	 */
	@SuppressWarnings("rawtypes")
	public Collection<Issue> validate(File f) throws  IOException {		
		try(FileInputStream fis = new FileInputStream(f)) {
			RDFFormat format = Rio.getParserFormatForFileName(f.getName()).orElse(RDFFormat.RDFXML);
			return validate(fis, format);
		}
	}
	
	/**
	 * Validate an InputStream
	 * 
	 * @param input
	 * @param format
	 * @return
	 * @throws RDF4JException
	 * @throws IOException
	 */
	@SuppressWarnings("rawtypes")
	public Collection<Issue> validate(InputStream input, RDFFormat format) throws IOException {
		
		// load RDF in a Repository
		Repository r = new SailRepository(new ForwardChainingRDFSInferencer(new MemoryStore()));
		r.initialize();
		factory=r.getValueFactory();
		// load some data in the repository
		try(RepositoryConnection c = r.getConnection();) {

			logger.info("Loading data in the repository...");
			c.add(
					input, 
					RDF.NAMESPACE,
					format
					);

			logger.info("Loading SKOS ontology...");
			
			InputStream skosInputStream = this.getClass().getClassLoader().getResourceAsStream("skos.rdf");			
			c.add(
					skosInputStream,
					SkosOntology.SKOS_BASE_IRI,
					RDFFormat.RDFXML,
					factory.createIRI(SkosOntology.SKOS_ONTO_IRI));
			
			logger.info("Infer SKOS-XL...");
			//prendre en compte les libellés skos-xl
			String update1 = "PREFIX skos:<http://www.w3.org/2004/02/skos/core#>"
							+"PREFIX skosxl:<http://www.w3.org/2008/05/skos-xl#>"
							+ "INSERT {	?x skos:prefLabel ?y} "
								+ "WHERE {?x skosxl:prefLabel/skosxl:literalForm ?y}";

			Update u1=c.prepareUpdate(QueryLanguage.SPARQL, update1);

			u1.execute();
			
			String update2 = "PREFIX skos:<http://www.w3.org/2004/02/skos/core#>"
							+"PREFIX skosxl:<http://www.w3.org/2008/05/skos-xl#>"
							+ "INSERT {	?x skos:altLabel ?y} "
								+ "WHERE {?x skosxl:altLabel/skosxl:literalForm ?y}";

			Update u2=c.prepareUpdate(QueryLanguage.SPARQL, update2);
			u2.execute();
			
			logger.info("Run qSKOS...");
			return runQSkos(c);

		}
	}
	
	@SuppressWarnings("rawtypes")
	private Collection<Issue> runQSkos(RepositoryConnection c) throws  IOException {
		
		// instantiation
		QSkos qskos = new QSkos();
		qskos.setRepositoryConnection(c);
		
		//cs(allconceptscheme), cc(all collection), ac(find all concepts)
		Collection<Issue> issues = qskos.getIssues(rules+",cs,cc,ac");
		
		int issueNumber = 0;
		Iterator<Issue> issueIt = issues.iterator();
		while (issueIt.hasNext()) {
			Issue issue = issueIt.next();
			issueNumber++;
			logger.info("Processing issue " + issueNumber + " of " + issues.size() + " (" + issue.getIssueDescriptor().getName() + ")");
			issue.getResult();
		}
		this.rulesNumber=issueNumber;
		//this.collectionNumber=getCollectionNumber(c);
		logger.info("Validation complete!");
		return issues;
	}

	public String getRules() {
		return rules;
	}

	public Integer getRulesNumber() {
		return rulesNumber;
	}
	
	
}
