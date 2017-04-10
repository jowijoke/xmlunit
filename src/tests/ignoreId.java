package tests;

import org.junit.Assert;
import org.junit.Test;
import org.xmlunit.builder.DiffBuilder;
import org.xmlunit.builder.Input;
import org.xmlunit.diff.ComparisonResult;
import org.xmlunit.diff.ComparisonType;
import org.xmlunit.diff.DefaultNodeMatcher;
import org.xmlunit.diff.Diff;
import org.xmlunit.diff.ElementSelectors;


public class ignoreId {
	
	

	@Test
	public void controlDiff() {
		
		//test to prove there's a difference in both xmls
		Diff myDiff = DiffBuilder.compare(Input.fromFile("xml/Rule/clean_complete_email_rule.xml"))
		              .withTest(Input.fromFile("xml/Rule/diff_complete_email_rule.xml"))
		              .checkForSimilar()
		              .build();

		Assert.assertFalse(myDiff.toString(), myDiff.hasDifferences());
	}
	
	@Test
	public void ignoreNode() {
		
		//test to ignore Application node in xmls to prove there similar
		Diff myDiff2 = DiffBuilder.compare(Input.fromFile("xml/Rule/clean_complete_email_rule.xml"))
	            .withTest(Input.fromFile("xml/Rule/diff_complete_email_rule.xml"))
	            .ignoreComments()
	            .ignoreWhitespace()                 
	            .withNodeFilter(node -> !node.getNodeName().equals("Rule"))
	            .build();

		Assert.assertFalse(myDiff2.toString(), myDiff2.hasDifferences());
		
	}
	
	@Test
	public void ignoreAttribute() {
		//test shows how to ignore one attribute
				Diff myDiff4 = DiffBuilder.compare(Input.fromFile("xml/Rule/clean_complete_email_rule.xml"))
			            .withTest(Input.fromFile("xml/Rule/diff_complete_email_rule.xml"))
			            .ignoreComments()
			            .ignoreWhitespace()                 
			            .withAttributeFilter(a -> !"created".equals(a.getName()))
			            .build();

				Assert.assertFalse(myDiff4.toString(), myDiff4.hasDifferences());
		
	}
	
	@Test
	public void ignoreAttributes() {
		// test that ignores specific attributes in order to pass but diff.xml has aggregated which includes extra map keys to ignore
				Diff myDiff3 = DiffBuilder.compare(Input.fromFile("xml/Rule/clean_complete_email_rule.xml"))
			            .withTest(Input.fromFile("xml/Rule/diff_complete_email_rule.xml"))
			            .ignoreComments()
			            .ignoreWhitespace() 
			            .checkForSimilar()
			            .normalizeWhitespace()
			            //need to ignore aggregate keys to pass test
			            .withAttributeFilter(a -> !("created".equals(a.getName()) || "id".equals(a.getName()) || "modified".equals(a.getName()) ))
			            .withNodeMatcher(new DefaultNodeMatcher(ElementSelectors.byNameAndAllAttributes,ElementSelectors.Default))
			            .withDifferenceEvaluator(((comparison, outcome) -> {
			                if (outcome == ComparisonResult.DIFFERENT && 
			                    comparison.getType() == ComparisonType.CHILD_NODELIST_SEQUENCE) {
			                       return ComparisonResult.EQUAL;
			                }

			                return outcome;
			            }))
			            .build();

				Assert.assertFalse(myDiff3.toString(), myDiff3.hasDifferences());
						
	}
	
	@Test
	public void IgnoreAttDiffEvaluator() {
		//test that creates a class to customise what one calls a similar result
		Diff myDiff5 = DiffBuilder.compare(Input.fromFile("xml/Rule/clean_complete_email_rule.xml"))
	            .withTest(Input.fromFile("xml/Rule/diff_complete_email_rule.xml"))
	            .ignoreComments()
	            .ignoreWhitespace()
	            .withAttributeFilter(a -> !("created".equals(a.getName()) || "id".equals(a.getName()) || "modified".equals(a.getName()) )
)	         
	            .build();

		Assert.assertFalse(myDiff5.toString(), myDiff5.hasDifferences());
		
	}
	
	
	

}
