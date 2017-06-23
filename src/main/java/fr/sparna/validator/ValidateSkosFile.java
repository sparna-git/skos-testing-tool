package fr.sparna.validator;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Collection;
import java.util.Iterator;

import org.openrdf.OpenRDFException;
import org.openrdf.model.Statement;
import org.openrdf.model.impl.URIImpl;
import org.openrdf.model.vocabulary.RDF;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryResult;
import org.openrdf.repository.sail.SailRepository;
import org.openrdf.rio.RDFFormat;
import org.openrdf.rio.Rio;
import org.openrdf.sail.inferencer.fc.ForwardChainingRDFSInferencer;
import org.openrdf.sail.memory.MemoryStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.ac.univie.mminf.qskos4j.QSkos;
import at.ac.univie.mminf.qskos4j.issues.Issue;
import at.ac.univie.mminf.qskos4j.util.vocab.SkosOntology;



public class ValidateSkosFile {
	private final Logger logger = LoggerFactory.getLogger(ValidateSkosFile.class);
	
	protected String rules;

	protected Integer rulesNumber;
	
	//protected long collectionNumber;
	
	public ValidateSkosFile(String rules) {
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
	public Collection<Issue> validate(File f) throws OpenRDFException, IOException {

		// load RDF in a Repository
		Repository r = new SailRepository(new ForwardChainingRDFSInferencer(new MemoryStore()));
		r.initialize();

		// load some data in the repository
		RepositoryConnection c = r.getConnection();
		try {

			c.add(
					f, 
					RDF.NAMESPACE,
					Rio.getParserFormatForFileName(f.getName())
					);

			c.add(
					new URL(SkosOntology.SKOS_ONTO_URI),
					SkosOntology.SKOS_BASE_URI,
					RDFFormat.RDFXML,
					new URIImpl(SkosOntology.SKOS_ONTO_URI));

			return runQSkos(c);

		} finally {
			try { c.close(); } catch(Exception ignore) {}
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
	public Collection<Issue> validate(InputStream input, RDFFormat format) throws OpenRDFException, IOException {
		
		// load RDF in a Repository
		Repository r = new SailRepository(new ForwardChainingRDFSInferencer(new MemoryStore()));
		r.initialize();

		// load some data in the repository
		RepositoryConnection c = r.getConnection();
		try {

			c.add(
					input, 
					RDF.NAMESPACE,
					format
					);

			c.add(
					new URL(SkosOntology.SKOS_ONTO_URI),
					SkosOntology.SKOS_BASE_URI,
					RDFFormat.RDFXML,
					new URIImpl(SkosOntology.SKOS_ONTO_URI));

			return runQSkos(c);

		} finally {
			try { c.close(); } catch(Exception ignore) {}
		}
	}
	
	@SuppressWarnings("rawtypes")
	private Collection<Issue> runQSkos(RepositoryConnection c) throws OpenRDFException, IOException {
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
			logger.info("Processing issue " + issueNumber + " of " + issues.size() + " (" + issue.getName() + ")");
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
