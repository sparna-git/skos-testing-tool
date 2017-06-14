package fr.sparna.validator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.openrdf.OpenRDFException;
import org.openrdf.model.Resource;
import org.openrdf.model.impl.URIImpl;
import org.openrdf.model.vocabulary.RDF;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.sail.SailRepository;
import org.openrdf.rio.RDFFormat;
import org.openrdf.rio.Rio;
import org.openrdf.sail.inferencer.fc.ForwardChainingRDFSInferencer;
import org.openrdf.sail.memory.MemoryStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.ac.univie.mminf.qskos4j.QSkos;
import at.ac.univie.mminf.qskos4j.issues.Issue;
import at.ac.univie.mminf.qskos4j.progress.ConsoleProgressMonitor;
import at.ac.univie.mminf.qskos4j.progress.StreamProgressMonitor;
import at.ac.univie.mminf.qskos4j.util.vocab.SkosOntology;



public class ValidateSkosFile {
	protected List<SkosError> errorList;
	private final Logger logger = LoggerFactory.getLogger(ValidateSkosFile.class);
	protected String rules;
	protected File f;


	public ValidateSkosFile(String rules, File f) {
		super();
		this.rules = rules;
		this.f = f;
	}

	public List<SkosError> validate() throws RepositoryException {

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

			// instantiation
			QSkos qskos = new QSkos();
			qskos.setRepositoryConnection(c);

			@SuppressWarnings("rawtypes")
			//el, anr, otc, tchbc, hr, rr, ldsi, mrm, dlv,
			Collection<Issue> issues = qskos.getIssues(rules);


			Process process=new Process();
			process.processIssues(issues, logger);
			errorList=process.createReport(issues);

		} catch(OpenRDFException ore) {
			ore.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try { c.close(); } catch(Exception ignore) {}
		}

		return errorList;
	}

	public String getRules() {
		return rules;
	}

	public File getF() {
		return f;
	}




}
