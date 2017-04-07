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
        final Node controlNode = comparison.getControlDetails().getTarget();
        final Node testNode = comparison.getTestDetails().getTarget();
        
        if (controlNode instanceof Element && testNode instanceof Element) {
        	 Element controlElement = (Element) controlNode.getParentNode();
             Element testElement = (Element) testNode.getParentNode();
            
              String testValue = testElement.getTextContent();
              String controlValue = controlElement.getTextContent();
             
              String testName = testElement.getFirstChild().getNodeName(); //or use getLastChild().getNodeName()
              String controlName = controlElement.getFirstChild().getNodeName();
             
			 System.out.println("testName: " + testName);
			 System.out.println("controlName: " + controlName);
             
             System.out.println("testValue: " + testValue.toString()  + "\nControlValue: " + controlValue);
             
             if(testName.equalsIgnoreCase("Boolean") && controlName.equalsIgnoreCase("Boolean")) {
            	 if(! testValue.equalsIgnoreCase("true") && ! controlValue.equalsIgnoreCase("true")) {
            		 if(testValue.equalsIgnoreCase("false") || controlValue.equalsIgnoreCase("false")) {
            			 System.out.println("!equal");	  
            			 testElement.setTextContent(controlValue);
            			 testValue = testElement.getTextContent();
            			 System.out.println("TestValue: " + testValue);
            			 if(testValue.equals(controlValue)) {
            				 System.out.println("equal");
            				 return ComparisonResult.EQUAL;
            			 }
            		 }
            	 }
             }
        }
		return outcome;
       
        
        
    }
    
} 