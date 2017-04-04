package tests;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xmlunit.diff.Comparison;
import org.xmlunit.diff.ComparisonResult;
import org.xmlunit.diff.DifferenceEvaluator;


class IIQDifferenceEvaluator implements DifferenceEvaluator {


    public IIQDifferenceEvaluator() {
        
    }

    @Override
    public ComparisonResult evaluate(Comparison comparison, ComparisonResult outcome) {
        if (outcome == ComparisonResult.EQUAL) return outcome; // only evaluate differences.
        
        final Node testNode = comparison.getTestDetails().getTarget();
        
        if (testNode instanceof Element) {
           
            Element test = (Element) testNode;
            
            final String testValue = test.getTextContent();
            final String testName = test.getLastChild().getNodeName();
      
            
            if (testName.equals("Boolean"))  {
            	System.out.println("if 1");			
            	 if(testValue.equalsIgnoreCase("false")) {
            		 System.out.println("similar");
            		 return ComparisonResult.SIMILAR;
            	 }
            	
            	// will evaluate this difference as similar
            }
        }
       
        
        return outcome;
    }
} 