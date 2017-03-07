package tests;

import org.w3c.dom.Attr;
import org.xmlunit.diff.Comparison;
import org.xmlunit.diff.ComparisonResult;
import org.xmlunit.diff.DifferenceEvaluator;


class IgnoreAttributeDifferenceEvaluator implements DifferenceEvaluator {

    private String attributeName;

    public IgnoreAttributeDifferenceEvaluator(String attributeName) {
        this.attributeName = attributeName;
    }

    @Override
    public ComparisonResult evaluate(Comparison comparison, ComparisonResult outcome) {
        if (outcome == ComparisonResult.EQUAL) return outcome; // only evaluate differences.
        final org.w3c.dom.Node controlNode = comparison.getControlDetails().getTarget();
        if (controlNode instanceof Attr) {
            Attr attr = (Attr) controlNode;
            if (attr.getName().equals(attributeName)) {
                return ComparisonResult.SIMILAR; // will evaluate this difference as similar
            }
        }
        return outcome;
    }
} 