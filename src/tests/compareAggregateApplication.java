package tests;

import org.junit.Assert;
import org.junit.Test;
import org.xmlunit.builder.DiffBuilder;
import org.xmlunit.builder.Input;
import org.xmlunit.diff.DefaultNodeMatcher;
import org.xmlunit.diff.Diff;
import org.xmlunit.diff.DifferenceEvaluators;
import org.xmlunit.diff.ElementSelectors;


public class compareAggregateApplication {
	@Test
	public void controlDiff() {
		
		//test to prove there's a difference in both xmls
		Diff myDiff = DiffBuilder.compare(Input.fromFile("xml/HR/clean-hr.xml"))         
		              .withTest(Input.fromFile("xml/HR/diff-hr.xml"))
		              .checkForSimilar()
		              .build();

		Assert.assertFalse(myDiff.toString(), myDiff.hasDifferences());
	}
	
	@Test
	public void ignoreNode() {
		
		//test to ignore Application node in xmls to prove there similar
		Diff myDiff2 = DiffBuilder.compare(Input.fromFile("xml/HR/clean-hr.xml"))         
	            .withTest(Input.fromFile("xml/HR/diff-hr.xml"))
	            .ignoreComments()
	            .ignoreWhitespace()                 
	            .withNodeFilter(node -> !node.getNodeName().equals("Application"))
	            .build();

		Assert.assertFalse(myDiff2.toString(), myDiff2.hasDifferences());
		
	}
	
	@Test
	public void ignoreAttribute() {
		//test shows how to ignore one attribute
				Diff myDiff4 = DiffBuilder.compare(Input.fromFile("xml/HR/clean-hr.xml"))         
			            .withTest(Input.fromFile("xml/HR/diff-hr.xml"))
			            .ignoreComments()
			            .ignoreWhitespace()                 
			            .withAttributeFilter(a -> !"created".equals(a.getName()))
			            .build();

				Assert.assertFalse(myDiff4.toString(), myDiff4.hasDifferences());
		
	}
	
	@Test
	public void ignoreAttributesAndOrder() {
		// test that ignores specific attributes in order to pass but diff.xml has aggregated which includes extra map keys to ignore
				Diff myDiff3 = DiffBuilder.compare(Input.fromFile("xml/HR/clean-hr.xml"))         
			            .withTest(Input.fromFile("xml/HR/diff-hr.xml"))
			            //need to ignore aggregate keys to pass test
			            .withAttributeFilter(a -> !("created".equals(a.getName()) || "id".equals(a.getName()) || "modified".equals(a.getName()) ))
			            .withNodeMatcher(new DefaultNodeMatcher(ElementSelectors.byNameAndAllAttributes,ElementSelectors.Default))
			            .ignoreComments()
			            .ignoreWhitespace()
			            .withDifferenceEvaluator(new IIQChildListEvaluator())  
			            .build();

				Assert.assertFalse(myDiff3.toString(), myDiff3.hasDifferences());
						
	}
	
	@Test
	public void IgnoreAttDiffEvaluatorAggegate() throws Exception {
		//test that creates a class to customise what one calls a similar result
		Diff myDiff5 = DiffBuilder.compare(Input.fromFile("xml/HR/clean-hr.xml"))         
	            .withTest(Input.fromFile("xml/HR/diff-hr.xml"))
	            .withAttributeFilter(a -> !("created".equals(a.getName())|| "id".equals(a.getName()) || "modified".equals(a.getName()) ))
	            .withNodeMatcher(new DefaultNodeMatcher(ElementSelectors.byNameAndAllAttributes,ElementSelectors.Default))
	            .withDifferenceEvaluator(
	                    DifferenceEvaluators.chain( new IIQChildListEvaluator(),new IIQDifferenceEvaluator()))
	            .ignoreComments()
	            .withNodeFilter(new IIQNodeFilter())
	            .ignoreWhitespace()
	            .checkForSimilar()
	            .build();
		
		Assert.assertFalse(myDiff5.toString(), myDiff5.hasDifferences());
		
	}
	
	

}
