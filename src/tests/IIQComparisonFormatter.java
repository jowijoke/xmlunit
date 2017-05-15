package tests;

import java.util.LinkedList;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xmlunit.diff.Comparison;
import org.xmlunit.diff.Comparison.Detail;
import org.xmlunit.diff.ComparisonFormatter;
import org.xmlunit.diff.ComparisonType;
import org.xmlunit.diff.DefaultComparisonFormatter;

import name.fraser.neil.plaintext.diff_match_patch;
import name.fraser.neil.plaintext.diff_match_patch.Diff;
import name.fraser.neil.plaintext.diff_match_patch.Operation;

public class IIQComparisonFormatter implements ComparisonFormatter{

	private static diff_match_patch diffMatchPatch;
	
	@Override
	public String getDescription(Comparison diff) {
		
		System.out.println("starting formatter");
		
		final Node controlNode = diff.getControlDetails().getTarget();
		final Node testNode = diff.getTestDetails().getTarget();
	//	StringBuilder sb = new StringBuilder();
		Element testElement = (Element) testNode.getParentNode();
		Element controlElement = (Element) controlNode.getParentNode();
		
		if(testElement !=null && controlElement != null) {
			if ((testElement.getNodeName().equalsIgnoreCase("Source") && 
				controlElement.getNodeName().equalsIgnoreCase("Source"))) {
				System.out.println("testNodeName is: " + testElement.getNodeName());
				System.out.println("controlElement is: " + controlElement.getNodeName());
				String test = testNode.getTextContent();
				
				String control = controlNode.getTextContent();
				if(!control.equals(test)) {
					
//					
//
//					    diffMatchPatch = new diff_match_patch();
//					    //Split text into List of strings
//					    List<String> oldTextList = Arrays.asList(control.split("(\\.|\\n)"));
//					    List<String> newTextList = Arrays.asList(test.split("(\\.|\\n)"));
//
//					    //If we have different length
//					    int counter = Math.max(oldTextList.size(), newTextList.size()); 
//					    
//
//					    for(int current = 0; current < counter; current++){
//					      String oldString = null;
//					      String newString = null;
//
//					      if(oldTextList.size() <= current){
//					        oldString = "";
//					        newString = newTextList.get(current);
//
//					      } else if (newTextList.size() <= current){
//					        oldString = oldTextList.get(current);
//					        newString = "";
//					      } else {
//					        if (isLineDifferent(oldTextList.get(current), newTextList.get(current))){
//					          oldString = oldTextList.get(current);
//					          newString = newTextList.get(current);
//					        }
//					      }
//					      if(oldString != null && newString != null) {
//					        //---- Insert into database here -----
//					        sb.append("Changes for Line: " + current + "\n");
//					        sb.append("Old: " + oldString + " || New: " + newString +"\n");
//					      }
//					    }
//
//					    System.out.println(sb.toString());
//					  }
//			}
//		}
//		return sb.toString();
//	}
//
//					  private static boolean isLineDifferent(String oldString, String newString) {
//					    LinkedList<diff_match_patch.Diff> deltas = diffMatchPatch.diff_main(oldString,newString);
//					    for(diff_match_patch.Diff d : deltas){
//					      if (d.operation == diff_match_patch.Operation.EQUAL) continue;
//					      return true;
//					      }
//					    return false;
//					    }
					  
						diff_match_patch difference = new diff_match_patch();
					    LinkedList<Diff> deltas = difference.diff_main(control, test);
					 
					    // Reconstruct texts from the deltas
					    //  text1 = all deletion (-1) and equality (0).
					    //  text2 = all insertion (1) and equality (0).
					    String clean = "";
					    String diffs = "";
					    for(Diff d: deltas)
					    {
					      if(d.operation==Operation.DELETE)
					    	  clean += d.text;
					      else if(d.operation==Operation.INSERT)
					    	  diffs += d.text;
					      else
					      {
					        clean += d.text;
					        diffs += d.text;
					      }
					      
					    }
					 
					    System.out.println("clean: " + clean);
					    System.out.println("diff: " + diffs);  
					    System.out.println("delta: " + deltas); 
					    return deltas.toString();
					 
					  }
						
						
						
						}
				
				}
			System.out.println("Result: " + diff.toString());
			return diff.toString();
			}

	
	
	@Override
	public String getDetails(Detail arg0, ComparisonType arg1, boolean arg2) {
		DefaultComparisonFormatter defaultFormatter = new DefaultComparisonFormatter();
		return  defaultFormatter.getDetails(arg0, arg1, arg2);
	}

}
