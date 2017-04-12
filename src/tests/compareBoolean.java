package tests;

import org.junit.Assert;
import org.junit.Test;
import org.xmlunit.builder.DiffBuilder;
import org.xmlunit.builder.Input;
import org.xmlunit.diff.DefaultNodeMatcher;
import org.xmlunit.diff.Diff;
import org.xmlunit.diff.DifferenceEvaluators;
import org.xmlunit.diff.ElementSelectors;


public class compareBoolean {
	@Test
	public void controlDiff() {
		
		//test to prove there's a difference in both xmls
		Diff myDiff = DiffBuilder.compare(Input.fromFile("xml/WorkGroup/clean_workgroup.xml"))         
		              .withTest(Input.fromFile("xml/WorkGroup/diff_workgroup.xml"))
		              .checkForSimilar()
		              .build();

		Assert.assertFalse(myDiff.toString(), myDiff.hasDifferences());
	}
	
	@Test
	public void ignoreNode() {
		
		//test to ignore Application node in xmls to prove there similar
		Diff myDiff2 = DiffBuilder.compare(Input.fromFile("xml/WorkGroup/clean_workgroup.xml"))                        
	            .withTest(Input.fromFile("xml/WorkGroup/diff_workgroup.xml"))
	            .ignoreComments()
	            .ignoreWhitespace()                 
	            .withNodeFilter(node -> !node.getNodeName().equals("Identity"))
	            .build();

		Assert.assertFalse(myDiff2.toString(), myDiff2.hasDifferences());
		
	}
	
	@Test
	public void ignoreAttribute() {
		//test shows how to ignore one attribute
				Diff myDiff4 = DiffBuilder.compare(Input.fromFile("xml/WorkGroup/clean_workgroup.xml"))                         
			            .withTest(Input.fromFile("xml/WorkGroup/diff_workgroup.xml"))
			            .ignoreComments()
			            .ignoreWhitespace()                 
			            .withAttributeFilter(a -> !"created".equals(a.getName()))
			            .build();

				Assert.assertFalse(myDiff4.toString(), myDiff4.hasDifferences());
		
	}
	
	@Test
	public void ignoreAttributesAndOrder() {
		// test that ignores specific attributes in order to pass but diff.xml has aggregated which includes extra map keys to ignore
				Diff myDiff3 = DiffBuilder.compare(Input.fromFile("xml/WorkGroup/clean_workgroup.xml"))                
			            .withTest(Input.fromFile("xml/WorkGroup/diff_workgroup.xml"))
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
	public void IgnoreAttDiffEvaluator()  {
		//test that creates a class to customise what one calls a similar result
		Diff myDiff5 = DiffBuilder.compare(Input.fromFile("xml/WorkGroup/clean_workgroup.xml"))                         
	            .withTest(Input.fromFile("xml/WorkGroup/diff_workgroup.xml"))
	            .withAttributeFilter(a -> !("created".equals(a.getName()) || "id".equals(a.getName()) || "modified".equals(a.getName()) ))
	            
	            .ignoreComments()
	            .ignoreWhitespace()
	            .normalizeWhitespace()
	            .checkForSimilar()
	            .withDifferenceEvaluator(new IIQDifferenceEvaluator())
	            .withNodeFilter(new IIQNodeFilter())
	            .build();

		Assert.assertFalse(myDiff5.toString(), myDiff5.hasDifferences());
		
	}
	
	@Test
	public void IgnoreAttDiffEvaluatorOrder()  {
		//test that creates a class to customise what one calls a similar result
		Diff myDiff5 = DiffBuilder.compare(Input.fromFile("xml/WorkGroup/clean_workgroup.xml"))                         
	            .withTest(Input.fromFile("xml/WorkGroup/diff_workgroup_order.xml"))
	            .withAttributeFilter(a -> !("created".equals(a.getName()) || "id".equals(a.getName()) || "modified".equals(a.getName()) ))
	            .withNodeMatcher(new DefaultNodeMatcher(ElementSelectors.byNameAndAllAttributes,ElementSelectors.Default))
	            .ignoreComments()
	            .ignoreWhitespace()
	            .normalizeWhitespace()
	            .checkForSimilar()
	            .withDifferenceEvaluator(
	                    DifferenceEvaluators.chain(new IIQDifferenceEvaluator(), new IIQChildListEvaluator()))
	            .build();

		Assert.assertFalse(myDiff5.toString(), myDiff5.hasDifferences());
		
	}
	
	@Test
	public void IgnoreAttDiffEvaluatorOrderSpace()  {
		//test that creates a class to customise what one calls a similar result
		Diff myDiff5 = DiffBuilder.compare(Input.fromFile("xml/WorkGroup/clean_workgroup.xml"))                         
	            .withTest(Input.fromFile("xml/WorkGroup/diff_workgroup_order_space.xml"))
	            .withAttributeFilter(a -> !("created".equals(a.getName()) || "id".equals(a.getName()) || "modified".equals(a.getName()) ))
	            .withNodeMatcher(new DefaultNodeMatcher(ElementSelectors.byNameAndAllAttributes,ElementSelectors.Default))
	            .ignoreComments()
	            .ignoreWhitespace()
	            .normalizeWhitespace()
	            .checkForSimilar()
	            .withDifferenceEvaluator(
	                    DifferenceEvaluators.chain(new IIQDifferenceEvaluator(), new IIQChildListEvaluator()))
	            .build();

		Assert.assertFalse(myDiff5.toString(), myDiff5.hasDifferences());
		
	}
	
	@Test
	public void IgnoreAttDiffEvaluatorOrderdifftrue()  {
		//test that creates a class to customise what one calls a similar result
		Diff myDiff5 = DiffBuilder.compare(Input.fromFile("xml/WorkGroup/clean_workgroup.xml"))                         
	            .withTest(Input.fromFile("xml/WorkGroup/diff_workgroup_order_true.xml"))
	            .withAttributeFilter(a -> !("created".equals(a.getName()) || "id".equals(a.getName()) || "modified".equals(a.getName()) ))
	            .withNodeMatcher(new DefaultNodeMatcher(ElementSelectors.byNameAndAllAttributes,ElementSelectors.Default))
	            .ignoreComments()
	            .ignoreWhitespace()
	            .normalizeWhitespace()
	            .checkForSimilar()
	            .withDifferenceEvaluator(
	                    DifferenceEvaluators.chain(new IIQDifferenceEvaluator(), new IIQChildListEvaluator()))
	            .build();

		Assert.assertFalse(myDiff5.toString(), myDiff5.hasDifferences());
		
	}
	
	@Test
	public void IgnoreAttDiffEvaluatorOrderTrue()  {
		//test that creates a class to customise what one calls a similar result
		Diff myDiff5 = DiffBuilder.compare(Input.fromFile("xml/WorkGroup/clean_workgroup.xml"))                         
	            .withTest(Input.fromFile("xml/WorkGroup/diff_workgroup_order_True.xml"))
	            .withAttributeFilter(a -> !("created".equals(a.getName()) || "id".equals(a.getName()) || "modified".equals(a.getName()) ))
	            .withNodeMatcher(new DefaultNodeMatcher(ElementSelectors.byNameAndAllAttributes,ElementSelectors.Default))
	            .ignoreComments()
	            .ignoreWhitespace()
	            .normalizeWhitespace()
	            .checkForSimilar()
	            .withDifferenceEvaluator(
	                    DifferenceEvaluators.chain(new IIQDifferenceEvaluator(), new IIQChildListEvaluator()))
	            .build();

		Assert.assertFalse(myDiff5.toString(), myDiff5.hasDifferences());
		
	}
	
	@Test
	public void IgnoreAttDiffEvaluatorOrderHi()  {
		//test that creates a class to customise what one calls a similar result
		Diff myDiff5 = DiffBuilder.compare(Input.fromFile("xml/WorkGroup/clean_workgroup.xml"))                         
	            .withTest(Input.fromFile("xml/WorkGroup/diff_workgroup_order_hi.xml"))
	            .withAttributeFilter(a -> !("created".equals(a.getName()) || "id".equals(a.getName()) || "modified".equals(a.getName()) ))
	            .withNodeMatcher(new DefaultNodeMatcher(ElementSelectors.byNameAndAllAttributes,ElementSelectors.Default))
	            .ignoreComments()
	            .ignoreWhitespace()
	            .normalizeWhitespace()
	            .checkForSimilar()
	            .withDifferenceEvaluator(
	                    DifferenceEvaluators.chain(new IIQDifferenceEvaluator(), new IIQChildListEvaluator()))
	            .build();

		Assert.assertFalse(myDiff5.toString(), myDiff5.hasDifferences());
		
	}
	
	@Test
	public void IgnoreAttDiffEvaluatorKey()  {
		//test that creates a class to customise what one calls a similar result
		Diff myDiff5 = DiffBuilder.compare(Input.fromFile("xml/WorkGroup/clean_workgroup.xml"))                         
	            .withTest(Input.fromFile("xml/WorkGroup/diff_workgroup_key.xml"))
	            .withAttributeFilter(a -> !("created".equals(a.getName()) || "id".equals(a.getName()) || "modified".equals(a.getName()) ))
	            .withNodeMatcher(new DefaultNodeMatcher(ElementSelectors.byNameAndAllAttributes,ElementSelectors.Default))
	            .ignoreComments()
	            .ignoreWhitespace()
	            .normalizeWhitespace()
	            .checkForSimilar()
	            .withDifferenceEvaluator(
	                    DifferenceEvaluators.chain(new IIQDifferenceEvaluator(), new IIQChildListEvaluator()))
	            .build();

		Assert.assertFalse(myDiff5.toString(), myDiff5.hasDifferences());
		
	}
	
	@Test
	public void IgnoreAttDiffEvaluatorOrderKey()  {
		//test that creates a class to customise what one calls a similar result
		Diff myDiff5 = DiffBuilder.compare(Input.fromFile("xml/WorkGroup/clean_workgroup.xml"))                         
	            .withTest(Input.fromFile("xml/WorkGroup/diff_workgroup_order_key.xml"))
	            .withAttributeFilter(a -> !("created".equals(a.getName()) || "id".equals(a.getName()) || "modified".equals(a.getName()) ))
	            .withNodeMatcher(new DefaultNodeMatcher(ElementSelectors.byNameAndAllAttributes,ElementSelectors.Default))
	            .ignoreComments()
	            .ignoreWhitespace()
	            .normalizeWhitespace()
	            .checkForSimilar()
	            .withDifferenceEvaluator(
	                    DifferenceEvaluators.chain(new IIQDifferenceEvaluator(), new IIQChildListEvaluator()))
	            .build();

		Assert.assertFalse(myDiff5.toString(), myDiff5.hasDifferences());
		
	}
	
	@Test
	public void IgnoreAttDiffEvaluatorCleanClose()  {
		//test that creates a class to customise what one calls a similar result
		Diff myDiff5 = DiffBuilder.compare(Input.fromFile("xml/WorkGroup/clean_workgroup_close.xml"))                         
	            .withTest(Input.fromFile("xml/WorkGroup/diff_workgroup.xml"))
	            .withAttributeFilter(a -> !("created".equals(a.getName()) || "id".equals(a.getName()) || "modified".equals(a.getName()) ))
	            
	            .ignoreComments()
	            .ignoreWhitespace()
	            .normalizeWhitespace()
	            .checkForSimilar()
	            .withDifferenceEvaluator(new IIQDifferenceEvaluator())
	            .build();

		Assert.assertFalse(myDiff5.toString(), myDiff5.hasDifferences());
		
	}
	
	@Test
	public void IgnoreAttDiffEvaluatorOrderCleanClose()  {
		//test that creates a class to customise what one calls a similar result
		Diff myDiff5 = DiffBuilder.compare(Input.fromFile("xml/WorkGroup/clean_workgroup_close.xml"))                         
	            .withTest(Input.fromFile("xml/WorkGroup/diff_workgroup_order.xml"))
	            .withAttributeFilter(a -> !("created".equals(a.getName()) || "id".equals(a.getName()) || "modified".equals(a.getName()) ))
	            .withNodeMatcher(new DefaultNodeMatcher(ElementSelectors.byNameAndAllAttributes,ElementSelectors.Default))
	            .ignoreComments()
	            .ignoreWhitespace()
	            .normalizeWhitespace()
	            .checkForSimilar()
	            .withDifferenceEvaluator(
	                    DifferenceEvaluators.chain(new IIQDifferenceEvaluator(), new IIQChildListEvaluator()))
	            .build();

		Assert.assertFalse(myDiff5.toString(), myDiff5.hasDifferences());
		
	}
	
	@Test
	public void IgnoreAttDiffEvaluatorOrderSpaceCleanClose()  {
		//test that creates a class to customise what one calls a similar result
		Diff myDiff5 = DiffBuilder.compare(Input.fromFile("xml/WorkGroup/clean_workgroup_close.xml"))                         
	            .withTest(Input.fromFile("xml/WorkGroup/diff_workgroup_order_space.xml"))
	            .withAttributeFilter(a -> !("created".equals(a.getName()) || "id".equals(a.getName()) || "modified".equals(a.getName()) ))
	            .withNodeMatcher(new DefaultNodeMatcher(ElementSelectors.byNameAndAllAttributes,ElementSelectors.Default))
	            .ignoreComments()
	            .ignoreWhitespace()
	            .normalizeWhitespace()
	            .checkForSimilar()
	            .withDifferenceEvaluator(
	                    DifferenceEvaluators.chain(new IIQDifferenceEvaluator(), new IIQChildListEvaluator()))
	            .build();

		Assert.assertFalse(myDiff5.toString(), myDiff5.hasDifferences());
		
	}
	
	@Test
	public void IgnoreAttDiffEvaluatorOrdertrueCleanClose()  {
		//test that creates a class to customise what one calls a similar result
		Diff myDiff5 = DiffBuilder.compare(Input.fromFile("xml/WorkGroup/clean_workgroup_close.xml"))                         
	            .withTest(Input.fromFile("xml/WorkGroup/diff_workgroup_order_true.xml"))
	            .withAttributeFilter(a -> !("created".equals(a.getName()) || "id".equals(a.getName()) || "modified".equals(a.getName()) ))
	            .withNodeMatcher(new DefaultNodeMatcher(ElementSelectors.byNameAndAllAttributes,ElementSelectors.Default))
	            .ignoreComments()
	            .ignoreWhitespace()
	            .normalizeWhitespace()
	            .checkForSimilar()
	            .withDifferenceEvaluator(
	                    DifferenceEvaluators.chain(new IIQDifferenceEvaluator(), new IIQChildListEvaluator()))
	            .build();

		Assert.assertFalse(myDiff5.toString(), myDiff5.hasDifferences());
		
	}
	
	@Test
	public void IgnoreAttDiffEvaluatorOrderTrueCleanClose()  {
		//test that creates a class to customise what one calls a similar result
		Diff myDiff5 = DiffBuilder.compare(Input.fromFile("xml/WorkGroup/clean_workgroup_close.xml"))                         
	            .withTest(Input.fromFile("xml/WorkGroup/diff_workgroup_order_True.xml"))
	            .withAttributeFilter(a -> !("created".equals(a.getName()) || "id".equals(a.getName()) || "modified".equals(a.getName()) ))
	            .withNodeMatcher(new DefaultNodeMatcher(ElementSelectors.byNameAndAllAttributes,ElementSelectors.Default))
	            .ignoreComments()
	            .ignoreWhitespace()
	            .normalizeWhitespace()
	            .checkForSimilar()
	            .withDifferenceEvaluator(
	                    DifferenceEvaluators.chain(new IIQDifferenceEvaluator(), new IIQChildListEvaluator()))
	            .build();

		Assert.assertFalse(myDiff5.toString(), myDiff5.hasDifferences());
		
	}
	
	@Test
	public void IgnoreAttDiffEvaluatorOrderHiCleanClose()  {
		//test that creates a class to customise what one calls a similar result
		Diff myDiff5 = DiffBuilder.compare(Input.fromFile("xml/WorkGroup/clean_workgroup_close.xml"))                         
	            .withTest(Input.fromFile("xml/WorkGroup/diff_workgroup_order_hi.xml"))
	            .withAttributeFilter(a -> !("created".equals(a.getName()) || "id".equals(a.getName()) || "modified".equals(a.getName()) ))
	            .withNodeMatcher(new DefaultNodeMatcher(ElementSelectors.byNameAndAllAttributes,ElementSelectors.Default))
	            .ignoreComments()
	            .ignoreWhitespace()
	            .normalizeWhitespace()
	            .checkForSimilar()
	            .withDifferenceEvaluator(
	                    DifferenceEvaluators.chain(new IIQDifferenceEvaluator(), new IIQChildListEvaluator()))
	            .build();

		Assert.assertFalse(myDiff5.toString(), myDiff5.hasDifferences());
		
	}
	
	@Test
	public void IgnoreAttDiffEvaluatorKeyCleanClose()  {
		//test that creates a class to customise what one calls a similar result
		Diff myDiff5 = DiffBuilder.compare(Input.fromFile("xml/WorkGroup/clean_workgroup_close.xml"))                         
	            .withTest(Input.fromFile("xml/WorkGroup/diff_workgroup_key.xml"))
	            .withAttributeFilter(a -> !("created".equals(a.getName()) || "id".equals(a.getName()) || "modified".equals(a.getName()) ))
	            .withNodeMatcher(new DefaultNodeMatcher(ElementSelectors.byNameAndAllAttributes,ElementSelectors.Default))
	            .ignoreComments()
	            .ignoreWhitespace()
	            .normalizeWhitespace()
	            .checkForSimilar()
	            .withDifferenceEvaluator(
	                    DifferenceEvaluators.chain(new IIQDifferenceEvaluator(), new IIQChildListEvaluator()))
	            .build();

		Assert.assertFalse(myDiff5.toString(), myDiff5.hasDifferences());
		
	}
	
	@Test
	public void IgnoreAttDiffEvaluatorOrderKeyCleanClose()  {
		//test that creates a class to customise what one calls a similar result
		Diff myDiff5 = DiffBuilder.compare(Input.fromFile("xml/WorkGroup/clean_workgroup_close.xml"))                         
	            .withTest(Input.fromFile("xml/WorkGroup/diff_workgroup_order_key.xml"))
	            .withAttributeFilter(a -> !("created".equals(a.getName()) || "id".equals(a.getName()) || "modified".equals(a.getName()) ))
	            .withNodeMatcher(new DefaultNodeMatcher(ElementSelectors.byNameAndAllAttributes,ElementSelectors.Default))
	            .ignoreComments()
	            .ignoreWhitespace()
	            .normalizeWhitespace()
	            .checkForSimilar()
	            .withDifferenceEvaluator(
	                    DifferenceEvaluators.chain(new IIQDifferenceEvaluator(), new IIQChildListEvaluator()))
	            .build();

		Assert.assertFalse(myDiff5.toString(), myDiff5.hasDifferences());
		
	}
	
	@Test
	public void IgnoreAttDiffEvaluatortrueBoolean()  {
		//test that creates a class to customise what one calls a similar result
		Diff myDiff5 = DiffBuilder.compare(Input.fromFile("xml/WorkGroup/clean_workgroup_true_boolean.xml"))                         
	            .withTest(Input.fromFile("xml/WorkGroup/diff_workgroup.xml"))
	            .withAttributeFilter(a -> !("created".equals(a.getName()) || "id".equals(a.getName()) || "modified".equals(a.getName()) ))
	            
	            .ignoreComments()
	            .ignoreWhitespace()
	            .normalizeWhitespace()
	            .checkForSimilar()
	            .withDifferenceEvaluator(new IIQDifferenceEvaluator())
	            .build();

		Assert.assertFalse(myDiff5.toString(), myDiff5.hasDifferences());
		
	}
	
	@Test
	public void IgnoreAttDiffEvaluatorOrdertrueBoolean()  {
		//test that creates a class to customise what one calls a similar result
		Diff myDiff5 = DiffBuilder.compare(Input.fromFile("xml/WorkGroup/clean_workgroup_true_boolean.xml"))                         
	            .withTest(Input.fromFile("xml/WorkGroup/diff_workgroup_order.xml"))
	            .withAttributeFilter(a -> !("created".equals(a.getName()) || "id".equals(a.getName()) || "modified".equals(a.getName()) ))
	            .withNodeMatcher(new DefaultNodeMatcher(ElementSelectors.byNameAndAllAttributes,ElementSelectors.Default))
	            .ignoreComments()
	            .ignoreWhitespace()
	            .normalizeWhitespace()
	            .checkForSimilar()
	            .withDifferenceEvaluator(
	                    DifferenceEvaluators.chain(new IIQDifferenceEvaluator(), new IIQChildListEvaluator()))
	            .build();

		Assert.assertFalse(myDiff5.toString(), myDiff5.hasDifferences());
		
	}
	
	@Test
	public void IgnoreAttDiffEvaluatorOrderSpacetrueBoolean()  {
		//test that creates a class to customise what one calls a similar result
		Diff myDiff5 = DiffBuilder.compare(Input.fromFile("xml/WorkGroup/clean_workgroup_true_boolean.xml"))                         
	            .withTest(Input.fromFile("xml/WorkGroup/diff_workgroup_order_space.xml"))
	            .withAttributeFilter(a -> !("created".equals(a.getName()) || "id".equals(a.getName()) || "modified".equals(a.getName()) ))
	            .withNodeMatcher(new DefaultNodeMatcher(ElementSelectors.byNameAndAllAttributes,ElementSelectors.Default))
	            .ignoreComments()
	            .ignoreWhitespace()
	            .normalizeWhitespace()
	            .checkForSimilar()
	            .withDifferenceEvaluator(
	                    DifferenceEvaluators.chain(new IIQDifferenceEvaluator(), new IIQChildListEvaluator()))
	            .build();

		Assert.assertFalse(myDiff5.toString(), myDiff5.hasDifferences());
		
	}
	
	@Test
	public void IgnoreAttDiffEvaluatorOrdertruetrueBoolean()  {
		//test that creates a class to customise what one calls a similar result
		Diff myDiff5 = DiffBuilder.compare(Input.fromFile("xml/WorkGroup/clean_workgroup_true_boolean.xml"))                         
	            .withTest(Input.fromFile("xml/WorkGroup/diff_workgroup_order_true.xml"))
	            .withAttributeFilter(a -> !("created".equals(a.getName()) || "id".equals(a.getName()) || "modified".equals(a.getName()) ))
	            .withNodeMatcher(new DefaultNodeMatcher(ElementSelectors.byNameAndAllAttributes,ElementSelectors.Default))
	            .ignoreComments()
	            .ignoreWhitespace()
	            .normalizeWhitespace()
	            .checkForSimilar()
	            .withDifferenceEvaluator(
	                    DifferenceEvaluators.chain(new IIQDifferenceEvaluator(), new IIQChildListEvaluator()))
	            .build();

		Assert.assertFalse(myDiff5.toString(), myDiff5.hasDifferences());
		
	}
	
	@Test
	public void IgnoreAttDiffEvaluatorOrderTruetrueBoolean()  {
		//test that creates a class to customise what one calls a similar result
		Diff myDiff5 = DiffBuilder.compare(Input.fromFile("xml/WorkGroup/clean_workgroup_true_boolean.xml"))                         
	            .withTest(Input.fromFile("xml/WorkGroup/diff_workgroup_order_True.xml"))
	            .withAttributeFilter(a -> !("created".equals(a.getName()) || "id".equals(a.getName()) || "modified".equals(a.getName()) ))
	            .withNodeMatcher(new DefaultNodeMatcher(ElementSelectors.byNameAndAllAttributes,ElementSelectors.Default))
	            .ignoreComments()
	            .ignoreWhitespace()
	            .normalizeWhitespace()
	            .checkForSimilar()
	            .withDifferenceEvaluator(
	                    DifferenceEvaluators.chain(new IIQDifferenceEvaluator(), new IIQChildListEvaluator()))
	            .build();

		Assert.assertFalse(myDiff5.toString(), myDiff5.hasDifferences());
		
	}
	
	@Test
	public void IgnoreAttDiffEvaluatorOrderHitrueBoolean()  {
		//test that creates a class to customise what one calls a similar result
		Diff myDiff5 = DiffBuilder.compare(Input.fromFile("xml/WorkGroup/clean_workgroup_true_boolean.xml"))                         
	            .withTest(Input.fromFile("xml/WorkGroup/diff_workgroup_order_hi.xml"))
	            .withAttributeFilter(a -> !("created".equals(a.getName()) || "id".equals(a.getName()) || "modified".equals(a.getName()) ))
	            .withNodeMatcher(new DefaultNodeMatcher(ElementSelectors.byNameAndAllAttributes,ElementSelectors.Default))
	            .ignoreComments()
	            .ignoreWhitespace()
	            .normalizeWhitespace()
	            .checkForSimilar()
	            .withDifferenceEvaluator(
	                    DifferenceEvaluators.chain(new IIQDifferenceEvaluator(), new IIQChildListEvaluator()))
	            .build();

		Assert.assertFalse(myDiff5.toString(), myDiff5.hasDifferences());
		
	}
	
	@Test
	public void IgnoreAttDiffEvaluatorKeytrueBoolean()  {
		//test that creates a class to customise what one calls a similar result
		Diff myDiff5 = DiffBuilder.compare(Input.fromFile("xml/WorkGroup/clean_workgroup_true_boolean.xml"))                         
	            .withTest(Input.fromFile("xml/WorkGroup/diff_workgroup_key.xml"))
	            .withAttributeFilter(a -> !("created".equals(a.getName()) || "id".equals(a.getName()) || "modified".equals(a.getName()) ))
	            .withNodeMatcher(new DefaultNodeMatcher(ElementSelectors.byNameAndAllAttributes,ElementSelectors.Default))
	            .ignoreComments()
	            .ignoreWhitespace()
	            .normalizeWhitespace()
	            .checkForSimilar()
	            .withDifferenceEvaluator(
	                    DifferenceEvaluators.chain(new IIQDifferenceEvaluator(), new IIQChildListEvaluator()))
	            .build();

		Assert.assertFalse(myDiff5.toString(), myDiff5.hasDifferences());
		
	}
	
	@Test
	public void IgnoreAttDiffEvaluatorOrderKeytrueBoolean()  {
		//test that creates a class to customise what one calls a similar result
		Diff myDiff5 = DiffBuilder.compare(Input.fromFile("xml/WorkGroup/clean_workgroup_true_boolean.xml"))                         
	            .withTest(Input.fromFile("xml/WorkGroup/diff_workgroup_order_key.xml"))
	            .withAttributeFilter(a -> !("created".equals(a.getName()) || "id".equals(a.getName()) || "modified".equals(a.getName()) ))
	            .withNodeMatcher(new DefaultNodeMatcher(ElementSelectors.byNameAndAllAttributes,ElementSelectors.Default))
	            .ignoreComments()
	            .ignoreWhitespace()
	            .normalizeWhitespace()
	            .checkForSimilar()
	            .withDifferenceEvaluator(
	                    DifferenceEvaluators.chain(new IIQDifferenceEvaluator(), new IIQChildListEvaluator()))
	            .build();

		Assert.assertFalse(myDiff5.toString(), myDiff5.hasDifferences());
		
	}
	
	@Test
	public void IgnoreAttDiffEvaluatortrue()  {
		//test that creates a class to customise what one calls a similar result
		Diff myDiff5 = DiffBuilder.compare(Input.fromFile("xml/WorkGroup/clean_workgroup_true.xml"))                         
	            .withTest(Input.fromFile("xml/WorkGroup/diff_workgroup.xml"))
	            .withAttributeFilter(a -> !("created".equals(a.getName()) || "id".equals(a.getName()) || "modified".equals(a.getName()) ))
	            
	            .ignoreComments()
	            .ignoreWhitespace()
	            .normalizeWhitespace()
	            .checkForSimilar()
	            .withDifferenceEvaluator(new IIQDifferenceEvaluator())
	            .build();

		Assert.assertFalse(myDiff5.toString(), myDiff5.hasDifferences());
		
	}
	
	@Test
	public void IgnoreAttDiffEvaluatorOrdertrue()  {
		//test that creates a class to customise what one calls a similar result
		Diff myDiff5 = DiffBuilder.compare(Input.fromFile("xml/WorkGroup/clean_workgroup_true.xml"))                         
	            .withTest(Input.fromFile("xml/WorkGroup/diff_workgroup_order.xml"))
	            .withAttributeFilter(a -> !("created".equals(a.getName()) || "id".equals(a.getName()) || "modified".equals(a.getName()) ))
	            .withNodeMatcher(new DefaultNodeMatcher(ElementSelectors.byNameAndAllAttributes,ElementSelectors.Default))
	            .ignoreComments()
	            .ignoreWhitespace()
	            .normalizeWhitespace()
	            .checkForSimilar()
	            .withDifferenceEvaluator(
	                    DifferenceEvaluators.chain(new IIQDifferenceEvaluator(), new IIQChildListEvaluator()))
	            .build();

		Assert.assertFalse(myDiff5.toString(), myDiff5.hasDifferences());
		
	}
	
	@Test
	public void IgnoreAttDiffEvaluatorOrderSpacetrue()  {
		//test that creates a class to customise what one calls a similar result
		Diff myDiff5 = DiffBuilder.compare(Input.fromFile("xml/WorkGroup/clean_workgroup_true.xml"))                         
	            .withTest(Input.fromFile("xml/WorkGroup/diff_workgroup_order_space.xml"))
	            .withAttributeFilter(a -> !("created".equals(a.getName()) || "id".equals(a.getName()) || "modified".equals(a.getName()) ))
	            .withNodeMatcher(new DefaultNodeMatcher(ElementSelectors.byNameAndAllAttributes,ElementSelectors.Default))
	            .ignoreComments()
	            .ignoreWhitespace()
	            .normalizeWhitespace()
	            .checkForSimilar()
	            .withDifferenceEvaluator(
	                    DifferenceEvaluators.chain(new IIQDifferenceEvaluator(), new IIQChildListEvaluator()))
	            .build();

		Assert.assertFalse(myDiff5.toString(), myDiff5.hasDifferences());
		
	}
	
	@Test
	public void IgnoreAttDiffEvaluatorOrderDifftruetrue()  {
		//test that creates a class to customise what one calls a similar result
		Diff myDiff5 = DiffBuilder.compare(Input.fromFile("xml/WorkGroup/clean_workgroup_true.xml"))                         
	            .withTest(Input.fromFile("xml/WorkGroup/diff_workgroup_order_true.xml"))
	            .withAttributeFilter(a -> !("created".equals(a.getName()) || "id".equals(a.getName()) || "modified".equals(a.getName()) ))
	            .withNodeMatcher(new DefaultNodeMatcher(ElementSelectors.byNameAndAllAttributes,ElementSelectors.Default))
	            .ignoreComments()
	            .ignoreWhitespace()
	            .normalizeWhitespace()
	            .checkForSimilar()
	            .withDifferenceEvaluator(
	                    DifferenceEvaluators.chain(new IIQDifferenceEvaluator(), new IIQChildListEvaluator()))
	            .build();

		Assert.assertFalse(myDiff5.toString(), myDiff5.hasDifferences());
		
	}
	
	@Test
	public void IgnoreAttDiffEvaluatorOrderTruetrue()  {
		//test that creates a class to customise what one calls a similar result
		Diff myDiff5 = DiffBuilder.compare(Input.fromFile("xml/WorkGroup/clean_workgroup_true.xml"))                         
	            .withTest(Input.fromFile("xml/WorkGroup/diff_workgroup_order_True.xml"))
	            .withAttributeFilter(a -> !("created".equals(a.getName()) || "id".equals(a.getName()) || "modified".equals(a.getName()) ))
	            .withNodeMatcher(new DefaultNodeMatcher(ElementSelectors.byNameAndAllAttributes,ElementSelectors.Default))
	            .ignoreComments()
	            .ignoreWhitespace()
	            .normalizeWhitespace()
	            .checkForSimilar()
	            .withDifferenceEvaluator(
	                    DifferenceEvaluators.chain(new IIQDifferenceEvaluator(), new IIQChildListEvaluator()))
	            .build();

		Assert.assertFalse(myDiff5.toString(), myDiff5.hasDifferences());
		
	}
	
	@Test
	public void IgnoreAttDiffEvaluatorOrderHitrue()  {
		//test that creates a class to customise what one calls a similar result
		Diff myDiff5 = DiffBuilder.compare(Input.fromFile("xml/WorkGroup/clean_workgroup_true.xml"))                         
	            .withTest(Input.fromFile("xml/WorkGroup/diff_workgroup_order_hi.xml"))
	            .withAttributeFilter(a -> !("created".equals(a.getName()) || "id".equals(a.getName()) || "modified".equals(a.getName()) ))
	            .withNodeMatcher(new DefaultNodeMatcher(ElementSelectors.byNameAndAllAttributes,ElementSelectors.Default))
	            .ignoreComments()
	            .ignoreWhitespace()
	            .normalizeWhitespace()
	            .checkForSimilar()
	            .withDifferenceEvaluator(
	                    DifferenceEvaluators.chain(new IIQDifferenceEvaluator(), new IIQChildListEvaluator()))
	            .build();

		Assert.assertFalse(myDiff5.toString(), myDiff5.hasDifferences());
		
	}
	
	@Test
	public void IgnoreAttDiffEvaluatorKeytrue()  {
		//test that creates a class to customise what one calls a similar result
		Diff myDiff5 = DiffBuilder.compare(Input.fromFile("xml/WorkGroup/clean_workgroup_true.xml"))                         
	            .withTest(Input.fromFile("xml/WorkGroup/diff_workgroup_key.xml"))
	            .withAttributeFilter(a -> !("created".equals(a.getName()) || "id".equals(a.getName()) || "modified".equals(a.getName()) ))
	            .withNodeMatcher(new DefaultNodeMatcher(ElementSelectors.byNameAndAllAttributes,ElementSelectors.Default))
	            .ignoreComments()
	            .ignoreWhitespace()
	            .normalizeWhitespace()
	            .checkForSimilar()
	            .withDifferenceEvaluator(
	                    DifferenceEvaluators.chain(new IIQDifferenceEvaluator(), new IIQChildListEvaluator()))
	            .build();

		Assert.assertFalse(myDiff5.toString(), myDiff5.hasDifferences());
		
	}
	
	@Test
	public void IgnoreAttDiffEvaluatorOrderKeytrue()  {
		//test that creates a class to customise what one calls a similar result
		Diff myDiff5 = DiffBuilder.compare(Input.fromFile("xml/WorkGroup/clean_workgroup_true.xml"))                         
	            .withTest(Input.fromFile("xml/WorkGroup/diff_workgroup_order_key.xml"))
	            .withAttributeFilter(a -> !("created".equals(a.getName()) || "id".equals(a.getName()) || "modified".equals(a.getName()) ))
	            .withNodeMatcher(new DefaultNodeMatcher(ElementSelectors.byNameAndAllAttributes,ElementSelectors.Default))
	            .ignoreComments()
	            .ignoreWhitespace()
	            .normalizeWhitespace()
	            .checkForSimilar()
	            .withDifferenceEvaluator(
	                    DifferenceEvaluators.chain(new IIQDifferenceEvaluator(), new IIQChildListEvaluator()))
	            .build();

		Assert.assertFalse(myDiff5.toString(), myDiff5.hasDifferences());
		
	}
	
	@Test
	public void IgnoreAttDiffEvaluatorCleanTrue()  {
		//test that creates a class to customise what one calls a similar result
		Diff myDiff5 = DiffBuilder.compare(Input.fromFile("xml/WorkGroup/clean_workgroup_True.xml"))                         
	            .withTest(Input.fromFile("xml/WorkGroup/diff_workgroup.xml"))
	            .withAttributeFilter(a -> !("created".equals(a.getName()) || "id".equals(a.getName()) || "modified".equals(a.getName()) ))
	            
	            .ignoreComments()
	            .ignoreWhitespace()
	            .normalizeWhitespace()
	            .checkForSimilar()
	            .withDifferenceEvaluator(new IIQDifferenceEvaluator())
	            .build();

		Assert.assertFalse(myDiff5.toString(), myDiff5.hasDifferences());
		
	}
	
	@Test
	public void IgnoreAttDiffEvaluatorOrderCleanTrue()  {
		//test that creates a class to customise what one calls a similar result
		Diff myDiff5 = DiffBuilder.compare(Input.fromFile("xml/WorkGroup/clean_workgroup_True.xml"))                         
	            .withTest(Input.fromFile("xml/WorkGroup/diff_workgroup_order.xml"))
	            .withAttributeFilter(a -> !("created".equals(a.getName()) || "id".equals(a.getName()) || "modified".equals(a.getName()) ))
	            .withNodeMatcher(new DefaultNodeMatcher(ElementSelectors.byNameAndAllAttributes,ElementSelectors.Default))
	            .ignoreComments()
	            .ignoreWhitespace()
	            .normalizeWhitespace()
	            .checkForSimilar()
	            .withDifferenceEvaluator(
	                    DifferenceEvaluators.chain(new IIQDifferenceEvaluator(), new IIQChildListEvaluator()))
	            .build();

		Assert.assertFalse(myDiff5.toString(), myDiff5.hasDifferences());
		
	}
	
	@Test
	public void IgnoreAttDiffEvaluatorOrderSpaceCleanTrue()  {
		//test that creates a class to customise what one calls a similar result
		Diff myDiff5 = DiffBuilder.compare(Input.fromFile("xml/WorkGroup/clean_workgroup_True.xml"))                         
	            .withTest(Input.fromFile("xml/WorkGroup/diff_workgroup_order_space.xml"))
	            .withAttributeFilter(a -> !("created".equals(a.getName()) || "id".equals(a.getName()) || "modified".equals(a.getName()) ))
	            .withNodeMatcher(new DefaultNodeMatcher(ElementSelectors.byNameAndAllAttributes,ElementSelectors.Default))
	            .ignoreComments()
	            .ignoreWhitespace()
	            .normalizeWhitespace()
	            .checkForSimilar()
	            .withDifferenceEvaluator(
	                    DifferenceEvaluators.chain(new IIQDifferenceEvaluator(), new IIQChildListEvaluator()))
	            .build();

		Assert.assertFalse(myDiff5.toString(), myDiff5.hasDifferences());
		
	}
	
	@Test
	public void IgnoreAttDiffEvaluatorOrderdifftrueCleanTrue()  {
		//test that creates a class to customise what one calls a similar result
		Diff myDiff5 = DiffBuilder.compare(Input.fromFile("xml/WorkGroup/clean_workgroup_True.xml"))                         
	            .withTest(Input.fromFile("xml/WorkGroup/diff_workgroup_order_true.xml"))
	            .withAttributeFilter(a -> !("created".equals(a.getName()) || "id".equals(a.getName()) || "modified".equals(a.getName()) ))
	            .withNodeMatcher(new DefaultNodeMatcher(ElementSelectors.byNameAndAllAttributes,ElementSelectors.Default))
	            .ignoreComments()
	            .ignoreWhitespace()
	            .normalizeWhitespace()
	            .checkForSimilar()
	            .withDifferenceEvaluator(
	                    DifferenceEvaluators.chain(new IIQDifferenceEvaluator(), new IIQChildListEvaluator()))
	            .build();

		Assert.assertFalse(myDiff5.toString(), myDiff5.hasDifferences());
		
	}
	
	@Test
	public void IgnoreAttDiffEvaluatorOrderTrueCleanTrue()  {
		//test that creates a class to customise what one calls a similar result
		Diff myDiff5 = DiffBuilder.compare(Input.fromFile("xml/WorkGroup/clean_workgroup_True.xml"))                         
	            .withTest(Input.fromFile("xml/WorkGroup/diff_workgroup_order_True.xml"))
	            .withAttributeFilter(a -> !("created".equals(a.getName()) || "id".equals(a.getName()) || "modified".equals(a.getName()) ))
	            .withNodeMatcher(new DefaultNodeMatcher(ElementSelectors.byNameAndAllAttributes,ElementSelectors.Default))
	            .ignoreComments()
	            .ignoreWhitespace()
	            .normalizeWhitespace()
	            .checkForSimilar()
	            .withDifferenceEvaluator(
	                    DifferenceEvaluators.chain(new IIQDifferenceEvaluator(), new IIQChildListEvaluator()))
	            .build();

		Assert.assertFalse(myDiff5.toString(), myDiff5.hasDifferences());
		
	}
	
	@Test
	public void IgnoreAttDiffEvaluatorOrderHiCleanTrue()  {
		//test that creates a class to customise what one calls a similar result
		Diff myDiff5 = DiffBuilder.compare(Input.fromFile("xml/WorkGroup/clean_workgroup_True.xml"))                         
	            .withTest(Input.fromFile("xml/WorkGroup/diff_workgroup_order_hi.xml"))
	            .withAttributeFilter(a -> !("created".equals(a.getName()) || "id".equals(a.getName()) || "modified".equals(a.getName()) ))
	            .withNodeMatcher(new DefaultNodeMatcher(ElementSelectors.byNameAndAllAttributes,ElementSelectors.Default))
	            .ignoreComments()
	            .ignoreWhitespace()
	            .normalizeWhitespace()
	            .checkForSimilar()
	            .withDifferenceEvaluator(
	                    DifferenceEvaluators.chain(new IIQDifferenceEvaluator(), new IIQChildListEvaluator()))
	            .build();

		Assert.assertFalse(myDiff5.toString(), myDiff5.hasDifferences());
		
	}
	
	@Test
	public void IgnoreAttDiffEvaluatorKeyCleanTrue()  {
		//test that creates a class to customise what one calls a similar result
		Diff myDiff5 = DiffBuilder.compare(Input.fromFile("xml/WorkGroup/clean_workgroup_True.xml"))                         
	            .withTest(Input.fromFile("xml/WorkGroup/diff_workgroup_key.xml"))
	            .withAttributeFilter(a -> !("created".equals(a.getName()) || "id".equals(a.getName()) || "modified".equals(a.getName()) ))
	            .withNodeMatcher(new DefaultNodeMatcher(ElementSelectors.byNameAndAllAttributes,ElementSelectors.Default))
	            .ignoreComments()
	            .ignoreWhitespace()
	            .normalizeWhitespace()
	            .checkForSimilar()
	            .withDifferenceEvaluator(
	                    DifferenceEvaluators.chain(new IIQDifferenceEvaluator(), new IIQChildListEvaluator()))
	            .build();

		Assert.assertFalse(myDiff5.toString(), myDiff5.hasDifferences());
		
	}
	
	@Test
	public void IgnoreAttDiffEvaluatorOrderKeyCleanTrue()  {
		//test that creates a class to customise what one calls a similar result
		Diff myDiff5 = DiffBuilder.compare(Input.fromFile("xml/WorkGroup/clean_workgroup_True.xml"))                         
	            .withTest(Input.fromFile("xml/WorkGroup/diff_workgroup_order_key.xml"))
	            .withAttributeFilter(a -> !("created".equals(a.getName()) || "id".equals(a.getName()) || "modified".equals(a.getName()) ))
	            .withNodeMatcher(new DefaultNodeMatcher(ElementSelectors.byNameAndAllAttributes,ElementSelectors.Default))
	            .ignoreComments()
	            .ignoreWhitespace()
	            .normalizeWhitespace()
	            .checkForSimilar()
	            .withDifferenceEvaluator(
	                    DifferenceEvaluators.chain(new IIQDifferenceEvaluator(), new IIQChildListEvaluator()))
	            .build();

		Assert.assertFalse(myDiff5.toString(), myDiff5.hasDifferences());
		
	}
	

	@Test
	public void IgnoreAttDiffEvaluatorCleanHi()  {
		//test that creates a class to customise what one calls a similar result
		Diff myDiff5 = DiffBuilder.compare(Input.fromFile("xml/WorkGroup/clean_workgroup_hi.xml"))                         
	            .withTest(Input.fromFile("xml/WorkGroup/diff_workgroup.xml"))
	            .withAttributeFilter(a -> !("created".equals(a.getName()) || "id".equals(a.getName()) || "modified".equals(a.getName()) ))
	            
	            .ignoreComments()
	            .ignoreWhitespace()
	            .normalizeWhitespace()
	            .checkForSimilar()
	            .withDifferenceEvaluator(new IIQDifferenceEvaluator())
	            .build();

		Assert.assertFalse(myDiff5.toString(), myDiff5.hasDifferences());
		
	}
	
	@Test
	public void IgnoreAttDiffEvaluatorOrderCleanHi()  {
		//test that creates a class to customise what one calls a similar result
		Diff myDiff5 = DiffBuilder.compare(Input.fromFile("xml/WorkGroup/clean_workgroup_hi.xml"))                         
	            .withTest(Input.fromFile("xml/WorkGroup/diff_workgroup_order.xml"))
	            .withAttributeFilter(a -> !("created".equals(a.getName()) || "id".equals(a.getName()) || "modified".equals(a.getName()) ))
	            .withNodeMatcher(new DefaultNodeMatcher(ElementSelectors.byNameAndAllAttributes,ElementSelectors.Default))
	            .ignoreComments()
	            .ignoreWhitespace()
	            .normalizeWhitespace()
	            .checkForSimilar()
	            .withDifferenceEvaluator(
	                    DifferenceEvaluators.chain(new IIQDifferenceEvaluator(), new IIQChildListEvaluator()))
	            .build();

		Assert.assertFalse(myDiff5.toString(), myDiff5.hasDifferences());
		
	}
	
	@Test
	public void IgnoreAttDiffEvaluatorOrderSpaceCleanHi()  {
		//test that creates a class to customise what one calls a similar result
		Diff myDiff5 = DiffBuilder.compare(Input.fromFile("xml/WorkGroup/clean_workgroup_hi.xml"))                         
	            .withTest(Input.fromFile("xml/WorkGroup/diff_workgroup_order_space.xml"))
	            .withAttributeFilter(a -> !("created".equals(a.getName()) || "id".equals(a.getName()) || "modified".equals(a.getName()) ))
	            .withNodeMatcher(new DefaultNodeMatcher(ElementSelectors.byNameAndAllAttributes,ElementSelectors.Default))
	            .ignoreComments()
	            .ignoreWhitespace()
	            .normalizeWhitespace()
	            .checkForSimilar()
	            .withDifferenceEvaluator(
	                    DifferenceEvaluators.chain(new IIQDifferenceEvaluator(), new IIQChildListEvaluator()))
	            .build();

		Assert.assertFalse(myDiff5.toString(), myDiff5.hasDifferences());
		
	}
	
	@Test
	public void IgnoreAttDiffEvaluatorOrderdifftrueCleanHi()  {
		//test that creates a class to customise what one calls a similar result
		Diff myDiff5 = DiffBuilder.compare(Input.fromFile("xml/WorkGroup/clean_workgroup_hi.xml"))                         
	            .withTest(Input.fromFile("xml/WorkGroup/diff_workgroup_order_true.xml"))
	            .withAttributeFilter(a -> !("created".equals(a.getName()) || "id".equals(a.getName()) || "modified".equals(a.getName()) ))
	            .withNodeMatcher(new DefaultNodeMatcher(ElementSelectors.byNameAndAllAttributes,ElementSelectors.Default))
	            .ignoreComments()
	            .ignoreWhitespace()
	            .normalizeWhitespace()
	            .checkForSimilar()
	            .withDifferenceEvaluator(
	                    DifferenceEvaluators.chain(new IIQDifferenceEvaluator(), new IIQChildListEvaluator()))
	            .build();

		Assert.assertFalse(myDiff5.toString(), myDiff5.hasDifferences());
		
	}
	
	@Test
	public void IgnoreAttDiffEvaluatorOrderTrueCleanHi()  {
		//test that creates a class to customise what one calls a similar result
		Diff myDiff5 = DiffBuilder.compare(Input.fromFile("xml/WorkGroup/clean_workgroup_hi.xml"))                         
	            .withTest(Input.fromFile("xml/WorkGroup/diff_workgroup_order_True.xml"))
	            .withAttributeFilter(a -> !("created".equals(a.getName()) || "id".equals(a.getName()) || "modified".equals(a.getName()) ))
	            .withNodeMatcher(new DefaultNodeMatcher(ElementSelectors.byNameAndAllAttributes,ElementSelectors.Default))
	            .ignoreComments()
	            .ignoreWhitespace()
	            .normalizeWhitespace()
	            .checkForSimilar()
	            .withDifferenceEvaluator(
	                    DifferenceEvaluators.chain(new IIQDifferenceEvaluator(), new IIQChildListEvaluator()))
	            .build();

		Assert.assertFalse(myDiff5.toString(), myDiff5.hasDifferences());
		
	}
	
	@Test
	public void IgnoreAttDiffEvaluatorOrderHiCleanHi()  {
		//test that creates a class to customise what one calls a similar result
		Diff myDiff5 = DiffBuilder.compare(Input.fromFile("xml/WorkGroup/clean_workgroup_hi.xml"))                         
	            .withTest(Input.fromFile("xml/WorkGroup/diff_workgroup_order_hi.xml"))
	            .withAttributeFilter(a -> !("created".equals(a.getName()) || "id".equals(a.getName()) || "modified".equals(a.getName()) ))
	            .withNodeMatcher(new DefaultNodeMatcher(ElementSelectors.byNameAndAllAttributes,ElementSelectors.Default))
	            .ignoreComments()
	            .ignoreWhitespace()
	            .normalizeWhitespace()
	            .checkForSimilar()
	            .withDifferenceEvaluator(
	                    DifferenceEvaluators.chain(new IIQDifferenceEvaluator(), new IIQChildListEvaluator()))
	            .build();

		Assert.assertFalse(myDiff5.toString(), myDiff5.hasDifferences());
		
	}
	
	@Test
	public void IgnoreAttDiffEvaluatorKeyCleanHi()  {
		//test that creates a class to customise what one calls a similar result
		Diff myDiff5 = DiffBuilder.compare(Input.fromFile("xml/WorkGroup/clean_workgroup_hi.xml"))                         
	            .withTest(Input.fromFile("xml/WorkGroup/diff_workgroup_key.xml"))
	            .withAttributeFilter(a -> !("created".equals(a.getName()) || "id".equals(a.getName()) || "modified".equals(a.getName()) ))
	            .withNodeMatcher(new DefaultNodeMatcher(ElementSelectors.byNameAndAllAttributes,ElementSelectors.Default))
	            .ignoreComments()
	            .ignoreWhitespace()
	            .normalizeWhitespace()
	            .checkForSimilar()
	            .withDifferenceEvaluator(
	                    DifferenceEvaluators.chain(new IIQDifferenceEvaluator(), new IIQChildListEvaluator()))
	            .build();

		Assert.assertFalse(myDiff5.toString(), myDiff5.hasDifferences());
		
	}
	
	@Test
	public void IgnoreAttDiffEvaluatorOrderKeyCleanHi()  {
		//test that creates a class to customise what one calls a similar result
		Diff myDiff5 = DiffBuilder.compare(Input.fromFile("xml/WorkGroup/clean_workgroup_hi.xml"))                         
	            .withTest(Input.fromFile("xml/WorkGroup/diff_workgroup_order_key.xml"))
	            .withAttributeFilter(a -> !("created".equals(a.getName()) || "id".equals(a.getName()) || "modified".equals(a.getName()) ))
	            .withNodeMatcher(new DefaultNodeMatcher(ElementSelectors.byNameAndAllAttributes,ElementSelectors.Default))
	            .ignoreComments()
	            .ignoreWhitespace()
	            .normalizeWhitespace()
	            .checkForSimilar()
	            .withDifferenceEvaluator(
	                    DifferenceEvaluators.chain(new IIQDifferenceEvaluator(), new IIQChildListEvaluator()))
	            .build();

		Assert.assertFalse(myDiff5.toString(), myDiff5.hasDifferences());
		
	}
	
	

}
