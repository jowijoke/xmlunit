package tests;

import javax.xml.soap.Node;

import org.custommonkey.xmlunit.Difference;
import org.custommonkey.xmlunit.DifferenceConstants;
import org.custommonkey.xmlunit.DifferenceListener;

class IgnoreIDsDifferenceListener implements DifferenceListener {
    private static final int[] IGNORE_VALUES = new int[] {
            DifferenceConstants.ATTR_VALUE.getId(),
    };
	private static final int RETURN_ACCEPT_DIFFERENCE = 0;
	private static final int RETURN_IGNORE_DIFFERENCE_NODES_IDENTICAL = 0;

    private boolean isIgnoredDifference(Difference difference) {
        int differenceId = difference.getId();
        for (int element : IGNORE_VALUES) {
            if (differenceId == element) {
                return true;
            }
        }
        return false;
    }

    @Override
	public int differenceFound(Difference difference) {
        if (isIgnoredDifference(difference)) {
            return RETURN_IGNORE_DIFFERENCE_NODES_IDENTICAL;
        } else {
            return RETURN_ACCEPT_DIFFERENCE;
        }
    }

    public void skippedComparison(Node control, Node test) {
    }

	@Override
	public void skippedComparison(org.w3c.dom.Node arg0, org.w3c.dom.Node arg1) {
	}
}
