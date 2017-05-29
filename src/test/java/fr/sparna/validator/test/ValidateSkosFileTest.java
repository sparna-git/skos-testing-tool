package fr.sparna.validator.test;

import static org.junit.Assert;

import org.junit.Test;

import fr.sparna.validator.ValidateSkosFile;

public class ValidateSkosFileTest {

	@Test
	public void testValidate() {
		ValidateSkosFile skos=new ValidateSkosFile();
		skos.validate("/home/clarvie/test.rdf");
	}

}
