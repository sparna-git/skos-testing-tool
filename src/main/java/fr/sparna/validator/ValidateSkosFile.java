package fr.sparna.validator;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Collection;

import org.openrdf.OpenRDFException;
import org.openrdf.model.impl.URIImpl;
import org.openrdf.model.vocabulary.RDF;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.sail.SailRepository;
import org.openrdf.rio.RDFFormat;
import org.openrdf.sail.inferencer.fc.ForwardChainingRDFSInferencer;
import org.openrdf.sail.memory.MemoryStore;

import at.ac.univie.mminf.qskos4j.QSkos;
import at.ac.univie.mminf.qskos4j.issues.Issue;
import at.ac.univie.mminf.qskos4j.progress.ConsoleProgressMonitor;
import at.ac.univie.mminf.qskos4j.util.vocab.SkosOntology;



public class ValidateSkosFile {

	public void validate(File f) throws RepositoryException {
		// load RDF in a Repository
		Repository r = new SailRepository(new ForwardChainingRDFSInferencer(new MemoryStore()));
		r.initialize();

		// load some data in the repository
		RepositoryConnection c = r.getConnection();
		try {

			c.add(
					f, 
					RDF.NAMESPACE,
					RDFFormat.forFileName(f.getName())
					);

			c.add(
					new URL(SkosOntology.SKOS_ONTO_URI),
					SkosOntology.SKOS_BASE_URI,
					RDFFormat.RDFXML,
					new URIImpl(SkosOntology.SKOS_ONTO_URI));

			// instantiation
			QSkos qskos = new QSkos();
			// qskos.setProgressMonitor(new ConsoleProgressMonitor());
			qskos.setRepositoryConnection(c);

			// the fun part
			// qskos.getAllIssues()
			Collection<Issue> issues = qskos.getIssues("oc, oilt, uc");
			issues.stream().forEach(i -> { 
				try {
					System.out.println(i.getName()+" : "+i.getResult().occurrenceCount());
				} catch(OpenRDFException ore) {
					ore.printStackTrace();
				} 
			});

		} catch(OpenRDFException ore) {
			ore.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try { c.close(); } catch(Exception ignore) {}
		}
	}

	
}
