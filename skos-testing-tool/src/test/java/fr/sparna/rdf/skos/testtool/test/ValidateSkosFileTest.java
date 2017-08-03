package fr.sparna.rdf.skos.testtool.test;

import java.io.File;

import org.junit.Ignore;
import org.junit.Test;

import fr.sparna.rdf.skos.testtool.ExecuteQSkos;

public class ValidateSkosFileTest {

	@Test
	@Ignore
	public void testValidate() throws Exception {
		ExecuteQSkos skos=new ExecuteQSkos("ilc");
		skos.validate(new File("/home/clarvie/test.rdf"));
	}

}
