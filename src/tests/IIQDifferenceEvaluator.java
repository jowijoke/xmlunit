package tests;

import org.w3c.dom.Element;
import org.xmlunit.diff.Comparison;
import org.xmlunit.diff.ComparisonResult;
import org.xmlunit.diff.DifferenceEvaluator;


class IIQDifferenceEvaluator implements DifferenceEvaluator {


    public IIQDifferenceEvaluator() {
        
    }

    @Override
    public ComparisonResult evaluate(Comparison comparison, ComparisonResult outcome) {
        if (outcome == ComparisonResult.EQUAL) return outcome; // only evaluate differences.
        final org.w3c.dom.Node controlNode = comparison.getControlDetails().getTarget();
        if (controlNode instanceof Element) {
            Element Ele = (Element) controlNode;
            //System.out.println("NodeName: " + Ele.getNodeValue());
            System.out.println("NodeName: " + Ele.getFirstChild());
            
            if (Ele.getTagName().equals("Boolean")) {
            	 System.out.println("boolean");
            	
            	// will evaluate this difference as similar
            }
        }
       
        
        return outcome;
    }
} 