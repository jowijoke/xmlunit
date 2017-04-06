package tests;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xmlunit.diff.Comparison;
import org.xmlunit.diff.ComparisonResult;
import org.xmlunit.diff.DifferenceEvaluator;


class IIQDifferenceEvaluator implements DifferenceEvaluator {


    public IIQDifferenceEvaluator() {
        
    }

    /*@Override
    public ComparisonResult evaluate(Comparison comparison, ComparisonResult outcome) {
        if (outcome == ComparisonResult.EQUAL) return outcome; // only evaluate differences.
        
        final Node testNode = comparison.getTestDetails().getTarget();


        
        if ( testNode instanceof Element) {
           
            Element test = (Element) testNode
      
            
            final String testValue = test.getTextContent();
            final String testName = test.getFirstChild().getNodeName();
            
      

            if(testName.equalsIgnoreCase("Boolean")) {
           	 System.out.println("found boolean");
           	 if(testValue.equalsIgnoreCase("false")) {
           		 System.out.println("similar2"); 
           		 return ComparisonResult.SIMILAR;
           	 }
            }
            
                 if(testValue.equalsIgnoreCase("false")) {
            		 System.out.println("similar");
            		 return ComparisonResult.SIMILAR;
            	 }
                 
                 
            	
     
            }
        
       
        
        return outcome;
    }*/
    
    //difference here is i included controlNode & testElement*****.getParentNode()***
    @Override
    public ComparisonResult evaluate(Comparison comparison, ComparisonResult outcome) {
        if (outcome == ComparisonResult.EQUAL) return outcome; // only evaluate differences.
        final Node controlNode = comparison.getControlDetails().getTarget();
        final Node testNode = comparison.getTestDetails().getTarget();
        
        if (controlNode instanceof Element && testNode instanceof Element) {
        	 Element controlElement = (Element) controlNode.getParentNode();
             Element testElement = (Element) testNode.getParentNode();
            
             final String testValue = testElement.getTextContent();
             final String controlValue = controlElement.getTextContent();
             
             System.out.println("testValue: " + testValue  + "\nControlValue: " + controlValue);
             
            if ( testValue.equalsIgnoreCase("false")) {
            	 //if(testValue.equalsIgnoreCase("false")) {
            		 System.out.println("boolean");
            		 return ComparisonResult.SIMILAR;
            	// }
            	
            	// will evaluate this difference as similar
            }
        }
       
        
        return outcome;
    }
    
} 