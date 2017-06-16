package fr.sparna.validator.test;

import java.io.File;

import org.junit.Ignore;
import org.junit.Test;

import fr.sparna.validator.ValidateSkosFile;

public class ValidateSkosFileTest {

	@Test
	@Ignore
	public void testValidate() throws Exception {
		ValidateSkosFile skos=new ValidateSkosFile("ilc");
		skos.validate(new File("/home/clarvie/test.rdf"));
	}

}
