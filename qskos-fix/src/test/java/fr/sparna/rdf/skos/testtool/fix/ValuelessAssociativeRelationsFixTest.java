package fr.sparna.rdf.skos.testtool.fix;

import java.io.IOException;

import org.eclipse.rdf4j.RDF4JException;
import org.eclipse.rdf4j.repository.RepositoryConnection;
import org.junit.Before;
import org.junit.Test;

import at.ac.univie.mminf.qskos4j.issues.relations.ValuelessAssociativeRelations;
import at.ac.univie.mminf.qskos4j.util.vocab.RepositoryBuilder;
import junit.framework.Assert;

public class ValuelessAssociativeRelationsFixTest {

    private ValuelessAssociativeRelations valuelessAssociativeRelations;

    @Before
    public void setUp() throws RDF4JException, IOException {
        valuelessAssociativeRelations = new ValuelessAssociativeRelations();
        valuelessAssociativeRelations.setRepositoryConnection(new RepositoryBuilder().setUpFromTestResource("redundantAssociativeRelations.rdf").getConnection());
    }

    @Test
    public void testRedundantAssociativeRelationsCount() throws RDF4JException, IOException {
    	FixPolicy policy = new ValuelessAssociativeRelationsFixPolicy1();
    	RepositoryConnection connection = new RepositoryBuilder().setUpFromTestResource("redundantAssociativeRelations.rdf").getConnection();
        FixProposal fixProposal = policy.getFixProposal(valuelessAssociativeRelations, connection);
        System.out.println(fixProposal.printDiff());
        Assert.assertEquals(6, fixProposal.getDeletedStatements().size());
    }
}
