package fr.sparna.validator.test;

import static org.junit.Assert.fail;

import java.io.File;

import org.junit.Test;
import org.openrdf.repository.RepositoryException;

import fr.sparna.validator.ValidateSkosFile;

public class ValidateSkosFileTest {

	@Test
	public void testValidate() throws RepositoryException {
		ValidateSkosFile skos=new ValidateSkosFile();
		skos.validate(new File("/home/clarvie/test.rdf"));
	}

}
