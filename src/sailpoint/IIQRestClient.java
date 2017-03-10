package sailpoint;

import sailpoint.integration.IIQClient;

public class IIQRestClient {
	
public static void main(String[] args) {
	System.out.println("Hello IdentityIQ REST API World.");
	String iiqUrl = "http://localhost:8078/diff";
	String iiqUser = "spadmin";
	String iiqPass = "admin";
	IIQClient iiqClient = null;
	try {
		iiqClient = new IIQClient(iiqUrl, iiqUser, iiqPass);
	} catch (Exception e) {
		System.err.println("ERROR: Failed to build IIQ client!");
		System.err.println(e.getLocalizedMessage());
		e.printStackTrace();
	}
	System.out.println(" - ready to connect to to IIQ at " + iiqUrl);
	// Try some REST functions that call into the IIQ system.
	try {
		String pingResult = iiqClient.ping();
		if (null != pingResult) {
			System.out.println(" - ping returned this: " + pingResult);
		}
		String idString = iiqClient.showIdentity("james.smiths");
		if (null != idString) {
			System.out.println(" - james.smiths returned this: " + idString);
		}
		idString = iiqClient.showIdentity("doesnt.exist");
		if (null != idString) {
			System.out.println(" - doesnt.exist returned this: " + idString);
		}
	} catch (Exception e) {
		System.err.println("ERROR: Failed to communicate with IIQ system!");
		String exceptionMessage = e.getLocalizedMessage();
		if (exceptionMessage.startsWith("401:")) {
			System.err.println("ERROR: Invalid user name or password given.");
		}
		System.err.println(exceptionMessage);
		e.printStackTrace();
	}
	}
}