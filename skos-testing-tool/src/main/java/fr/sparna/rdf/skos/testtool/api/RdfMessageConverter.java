package fr.sparna.rdf.skos.testtool.api;

import java.io.IOException;

import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Literal;
import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.model.Statement;
import org.eclipse.rdf4j.model.ValueFactory;
import org.eclipse.rdf4j.model.impl.LinkedHashModel;
import org.eclipse.rdf4j.model.impl.SimpleValueFactory;
import org.eclipse.rdf4j.model.vocabulary.SKOS;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.eclipse.rdf4j.rio.Rio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import at.ac.univie.mminf.qskos4j.util.vocab.SkosOntology;

public class RdfMessageConverter extends AbstractHttpMessageConverter<Model>{

	protected Logger log = LoggerFactory.getLogger(this.getClass().getName());
	
	protected RDFFormat rdfFormat;
	
	public RdfMessageConverter(String mimeType) {
		super();
		this.rdfFormat = Rio.getWriterFormatForMIMEType(mimeType).orElse(RDFFormat.RDFXML);
		log.debug("Built RdfMessageConverter with contentType "+this.rdfFormat.getDefaultMIMEType());
	}
	
	@Override
	protected Model readInternal(Class<? extends Model> c, HttpInputMessage in)
	throws IOException, HttpMessageNotReadableException {
		
		Model m = new LinkedHashModel();
		
		return m;
	}

	@Override
	protected boolean supports(Class<?> c) {
		return true;
	}

	@Override
	protected void writeInternal(Model m, HttpOutputMessage out) throws IOException, HttpMessageNotWritableException {
		log.info("RdfMessageConverter called");
		Rio.write(m, out.getBody(), this.rdfFormat);
	}	
	
}

