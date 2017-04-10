package tests;

import javax.xml.bind.Element;

import org.w3c.dom.Node;
import org.xmlunit.util.Nodes;
import org.xmlunit.util.Predicate;

public class AggregateFilter implements Predicate<Node> {

	@Override
	public boolean test(Node n) {
		return !(n instanceof Element &&
	            "acctAggregationEnd".equals(Nodes.getQName(n).getLocalPart()));
	
	}

}
