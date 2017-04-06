package tests;

import org.junit.Assert;
import org.junit.Test;
import org.xmlunit.builder.DiffBuilder;
import org.xmlunit.builder.Input;
import org.xmlunit.diff.DefaultNodeMatcher;
import org.xmlunit.diff.Diff;
import org.xmlunit.diff.ElementSelectors;


public class compareBoolean {
	
	final String clean = "<?xml version='1.0' encoding='UTF-8'?>\n" + 
			"<!DOCTYPE Identity PUBLIC \"sailpoint.dtd\" \"sailpoint.dtd\">\n" + 
			"<Identity created=\"1488811526518\" id=\"ff8081815aa414bc015aa4150976001d\" name=\"SelfRegistrationWorkGroup\" protected=\"true\" workgroup=\"true\">\n" + 
			"  <Attributes>\n" + 
			"    <Map>\n" + 
			"      <entry key=\"displayName\" value=\"SelfRegistrationWorkGroup\"/>\n" + 
			"      <entry key=\"systemUser\">\n" + 
			"        <value>\n" + 
			"          <Boolean> </Boolean>\n" + 
			"        </value>\n" + 
			"      </entry>\n" + 
			"    </Map>\n" + 
			"  </Attributes>\n" + 
			"</Identity>";
	
	
	final String diff = "<?xml version='1.0' encoding='UTF-8'?>\n" + 
			"<!DOCTYPE Identity PUBLIC \"sailpoint.dtd\" \"sailpoint.dtd\">\n" + 
			"<Identity created=\"1488793252923\" id=\"ff8081815aa2fdde015aa2fe343b001d\" modified=\"1490624020752\" name=\"SelfRegistrationWorkGroup\" protected=\"true\" workgroup=\"true\">\n" + 
			"  <Attributes>\n" + 
			"    <Map>\n" + 
			"      <entry key=\"systemUser\">\n" + 
			"        <value>\n" + 
			"          <Boolean>false </Boolean>\n" + 
			"        </value>\n" + 
			"      </entry>\n" + 
			"      <entry key=\"displayName\" value=\"SelfRegistrationWorkGroup\"/>\n" + 
			"    </Map>\n" + 
			"  </Attributes>\n" + 
			"</Identity>";

	@Test
	public void controlDiff() {
		
		//test to prove there's a difference in both xmls
		Diff myDiff = DiffBuilder.compare(Input.fromString(clean))
		              .withTest(Input.fromString(diff))
		              .checkForSimilar()
		              .build();

		Assert.assertFalse(myDiff.toString(), myDiff.hasDifferences());
	}
	
	@Test
	public void ignoreNode() {
		
		//test to ignore Application node in xmls to prove there similar
		Diff myDiff2 = DiffBuilder.compare(Input.fromString(clean))                
	            .withTest(Input.fromString(diff))
	            .ignoreComments()
	            .ignoreWhitespace()                 
	            .withNodeFilter(node -> !node.getNodeName().equals("Identity"))
	            .build();

		Assert.assertFalse(myDiff2.toString(), myDiff2.hasDifferences());
		
	}
	
	@Test
	public void ignoreAttribute() {
		//test shows how to ignore one attribute
				Diff myDiff4 = DiffBuilder.compare(Input.fromString(clean))                
			            .withTest(Input.fromString(diff))
			            .ignoreComments()
			            .ignoreWhitespace()                 
			            .withAttributeFilter(a -> !"created".equals(a.getName()))
			            .build();

				Assert.assertFalse(myDiff4.toString(), myDiff4.hasDifferences());
		
	}
	
	@Test
	public void ignoreAttributes() {
		// test that ignores specific attributes in order to pass but diff.xml has aggregated which includes extra map keys to ignore
				Diff myDiff3 = DiffBuilder.compare(Input.fromString(clean))                
			            .withTest(Input.fromString(diff))
			            .ignoreComments()
			            .ignoreWhitespace()                 
			            //need to ignore aggregate keys to pass test
			            .withAttributeFilter(a -> !("created".equals(a.getName()) || "id".equals(a.getName()) || "modified".equals(a.getName()) ))
			            .build();

				Assert.assertFalse(myDiff3.toString(), myDiff3.hasDifferences());
						
	}
	
	@Test
	public void IgnoreAttDiffEvaluator() {
		//test that creates a class to customise what one calls a similar result
		Diff myDiff5 = DiffBuilder.compare(Input.fromString(clean))                
	            .withTest(Input.fromString(diff))
	            .ignoreComments()
	            .ignoreWhitespace()
	            .checkForSimilar()
	            .withAttributeFilter(a -> !("created".equals(a.getName()) || "id".equals(a.getName()) || "modified".equals(a.getName()) )
)	            .withDifferenceEvaluator(new IIQDifferenceEvaluator())
	            .withNodeMatcher(new DefaultNodeMatcher(ElementSelectors.byNameAndAllAttributes,ElementSelectors.Default))
	            .build();

		Assert.assertFalse(myDiff5.toString(), myDiff5.hasDifferences());
		
	}
	
	
	

}
