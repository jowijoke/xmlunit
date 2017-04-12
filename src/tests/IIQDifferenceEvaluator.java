package tests;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Text;
import org.xmlunit.diff.Comparison;
import org.xmlunit.diff.ComparisonResult;
import org.xmlunit.diff.DifferenceEvaluator;

class IIQDifferenceEvaluator implements DifferenceEvaluator {

	public IIQDifferenceEvaluator() {

	}

	@Override
	public ComparisonResult evaluate(Comparison comparison, ComparisonResult outcome) {
		if (outcome == ComparisonResult.EQUAL)
			return outcome; // only evaluate differences.
		
		final Node controlNode = comparison.getControlDetails().getTarget();
		final Node testNode = comparison.getTestDetails().getTarget();
		
		/*
		 * if two elements are boolean tags & while one boolean's TextContent is null & other is "false".
		 * Both must be considered EQUAL.
		 */
		if (controlNode instanceof Element && testNode instanceof Element) {

			
			String testNodeName = testNode.getNodeName(); 
			String controlNodeName = controlNode.getNodeName();

			if (testNodeName.equalsIgnoreCase("Boolean") && controlNodeName.equalsIgnoreCase("Boolean")) {
				System.out.println("........ELEMENT COMPARISON TEST........");
				
				System.out.println("testNodeName: " + testNodeName);
				System.out.println("controlNodeName: " + controlNodeName);
				
				String testValue = testNode.getTextContent();
				String controlValue = controlNode.getTextContent();

				System.out.println("testValue: " + testValue.toString() + "\nControlValue: " + controlValue);
				if (testValue.equalsIgnoreCase("true") && controlValue.equalsIgnoreCase("true")) {
					return ComparisonResult.EQUAL;// pass test if A="True" while B="true"
					
				}else {
					if (testValue.equalsIgnoreCase("false") || controlValue.equalsIgnoreCase("false")) {
						System.out.println("Both Elements are: EQUAL");

						return ComparisonResult.EQUAL;

					}
				}
			}
		}
		
		/*
		 * This if statement will be executed as long as one boolean text value is null while the other has #text.
		 * Text within Node tags are considered separate nodes. Therefore these nodes are instanceof Text
		 */
		if ((testNode instanceof Text || controlNode instanceof Text) && (testNode == null || controlNode == null)) {
			System.out.println("........TEXT COMPARISON TEST........");
			boolean isBooleanNode = false;//checking if the node is a boolean tag
			
			/*
			 * Either testNode or controlNode is null, 
			 * whichever is not null is checked for the boolean tag.
			 */
			if (testNode != null) {
				
				Element testElement = (Element) testNode.getParentNode();
				if (testElement.getNodeName().equalsIgnoreCase("boolean")) {
					isBooleanNode = true;
					System.out.println("testNodeName is: " + testElement.getNodeName());
				}
			
			}else{
				System.out.println("testNode is: NULL");
			}

			if (controlNode != null) {
				
				System.out.println("controlNode: " + controlNode);
				Element controlElement = (Element) controlNode.getParentNode();
				if (controlElement.getNodeName().equalsIgnoreCase("boolean")) {
					isBooleanNode = true;
					System.out.println("controlElement is: " + controlElement.getNodeName());
				}
				
			}else{
				System.out.println("controlNode is: NULL");
			}

			/*
			 * If isBooleanNode = true, check the values of testNode & controlNode.
			 */
			if (isBooleanNode) {
				System.out.println("Boolean tag present");
				boolean testNodeValue = false;
				boolean controlNodeValue = false;
				
				
				/*
				 * Either testNode or controlNode is null, 
				 * whichever is not null is checked for the boolean value.
				 */
				if (testNode == null) {
					testNodeValue = false; //stating null value within a boolean tag = false
				}else{
					if(testNode.getTextContent().equalsIgnoreCase("false")) {
						testNodeValue = false; //stating string "false" within a boolean tag = false
					}else{
						testNodeValue = true;
					}
				}

				
				
				if (controlNode == null) {
						controlNodeValue = false; //stating null value within a boolean tag = false
				}else{
					if (controlNode.getTextContent().equalsIgnoreCase("false")) {
						controlNodeValue = false; //stating null value within a boolean tag = false
					}else{
						controlNodeValue = true;
					}
				}
				
				
				
				if(testNodeValue == controlNodeValue) {
					System.out.println("both Values equal");
					return ComparisonResult.EQUAL;
				}
				
			}

		}else {
			//Text node A = "True" while B = "true"
		if((testNode instanceof Text || controlNode instanceof Text) && (testNode.getTextContent().equalsIgnoreCase("true")  
				&& controlNode.getTextContent().equalsIgnoreCase("true"))) {
			return ComparisonResult.EQUAL;
		}
		
		}
		return outcome;

	}

}