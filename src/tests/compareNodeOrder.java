package tests;

import org.junit.Assert;
import org.junit.Test;
import org.xmlunit.builder.DiffBuilder;
import org.xmlunit.builder.Input;
import org.xmlunit.diff.DefaultNodeMatcher;
import org.xmlunit.diff.Diff;
import org.xmlunit.diff.ElementSelectors;


public class compareNodeOrder {
	
	final String clean = "<?xml version='1.0' encoding='UTF-8'?>\n" + 
			"<!DOCTYPE Workflow PUBLIC \"sailpoint.dtd\" \"sailpoint.dtd\">\n" + 
			"<Workflow configForm=\"Provisioning Workflow Config Form\" created=\"1488811533902\" handler=\"sailpoint.api.StandardWorkflowHandler\" id=\"ff8081815aa414bc015aa415264e0046\" libraries=\"Identity,Role,PolicyViolation,LCM,BatchRequest\" name=\"LCM Provisioning\" taskType=\"LCM\" type=\"LCMProvisioning\">\n" + 
			"  <Variable input=\"true\" name=\"identityName\">\n" + 
			"    <Description>The name of the identity being updated.</Description>\n" + 
			"  </Variable>\n" + 
			"  <Variable initializer=\"false\" input=\"true\" name=\"endOnManualWorkItems\">\n" + 
			"    <Description>Option to skip requests with manual work items.</Description>\n" + 
			"  </Variable>\n" + 
			"  <Variable initializer=\"false\" input=\"true\" name=\"endOnProvisioningForms\">\n" + 
			"    <Description>Option to skip requests with provisioning forms.</Description>\n" + 
			"  </Variable>\n" + 
			"  <Variable initializer=\"script:(identityDisplayName != void) ? identityDisplayName : resolveDisplayName(identityName)\" input=\"true\" name=\"identityDisplayName\">\n" + 
			"    <Description>\n" + 
			"      The displayName of the identity being updated.\n" + 
			"      Query for this using a projection query and fall back to the name.\n" + 
			"    </Description>\n" + 
			"  </Variable>\n" + 
			"  <Variable input=\"true\" name=\"plan\">\n" + 
			"    <Description>The provisioning plan ready to execute.</Description>\n" + 
			"  </Variable>\n" + 
			"  <Variable input=\"true\" name=\"flow\">\n" + 
			"    <Description>\n" + 
			"      The name of the LCM flow that launched this workflow.\n" + 
			"\n" + 
			"      This is one of these three values:\n" + 
			"\n" + 
			"      AccountsRequest\n" + 
			"      EntitlementsRequest\n" + 
			"      RolesRequest\n" + 
			"    </Description>\n" + 
			"  </Variable>\n" + 
			"  <Variable editable=\"true\" initializer=\"false\" name=\"optimisticProvisioning\">\n" + 
			"    <Description>\n" + 
			"      Set to true to enable optimistic provisioning.  This will cause\n" + 
			"      changes to the entitlements compiled from role assignments to be\n" + 
			"      applied immediately to the identity cube rather than waiting\n" + 
			"      for the next refresh/reaggregation after the provisioning system\n" + 
			"      completes the request.\n" + 
			"    </Description>\n" + 
			"  </Variable>\n" + 
			"  <Variable editable=\"true\" initializer=\"true\" name=\"foregroundProvisioning\">\n" + 
			"    <Description>\n" + 
			"      Normally provisioning is done in a step that uses the \"background\"\n" + 
			"      option to force the workflow to be suspend and be resumed in a\n" + 
			"      background task thread.  This prevents the browser session from\n" + 
			"      hanging since provision can sometimes take a long time.  For demos\n" + 
			"      and testing it can be better to do this in the foreground so that\n" + 
			"      provisioning will have been performed when control is returned to the\n" + 
			"      user.  This prevents having to run the Perform Maintenance task to\n" + 
			"      see the results of the request.\n" + 
			"    </Description>\n" + 
			"  </Variable>\n" + 
			"  <Variable input=\"true\" name=\"batchRequestItemId\">\n" + 
			"    <Description>\n" + 
			"      Used by the batch interface to record back individual request item status. The specific item id for the individual request in the batch file.\n" + 
			"    </Description>\n" + 
			"  </Variable>\n" + 
			"  <Variable editable=\"true\" name=\"doRefresh\">\n" + 
			"    <Description>\n" + 
			"      Set to true to cause an identity refresh after the changes in the plan\n" + 
			"      have been provisioned.  This is normally off, you might want this on\n" + 
			"      if you want modification of identity or link attributes to result in\n" + 
			"      an immediate re-evaluation of assigned and detected roles.\n" + 
			"    </Description>\n" + 
			"  </Variable>\n" + 
			"  <Variable initializer=\"user,requester\" input=\"true\" name=\"notificationScheme\">\n" + 
			"    <Description>\n" + 
			"     A string that specifies who should be notified when the request has been complete.\n" + 
			"     The value can be null or a csv of one or more of the following options.\n" + 
			"\n" + 
			"     none or null\n" + 
			"       disable notifications\n" + 
			"\n" + 
			"     user\n" + 
			"       Identity that is being update will be notified.\n" + 
			"\n" + 
			"     manager\n" + 
			"       The manager of the Identity that is being updated will be notified.\n" + 
			"\n" + 
			"     requester\n" + 
			"       The person that has requested the update will be notified.\n" + 
			"    </Description>\n" + 
			"  </Variable>\n" + 
			"  <Variable initializer=\"LCM User Notification\" input=\"true\" name=\"userEmailTemplate\">\n" + 
			"    <Description>\n" + 
			"     The email template to use for user notification.\n" + 
			"    </Description>\n" + 
			"  </Variable>\n" + 
			"  <Variable initializer=\"LCM Requester Notification\" input=\"true\" name=\"requesterEmailTemplate\">\n" + 
			"    <Description>\n" + 
			"     The email template to use for requester notification.\n" + 
			"    </Description>\n" + 
			"  </Variable>\n" + 
			"  <Variable initializer=\"LCM Manager Notification\" input=\"true\" name=\"managerEmailTemplate\">\n" + 
			"    <Description>\n" + 
			"     The email template to use for manager notification.\n" + 
			"    </Description>\n" + 
			"  </Variable>\n" + 
			"  <Variable input=\"true\" name=\"securityOfficerEmailTemplate\">\n" + 
			"    <Description>\n" + 
			"     The email template to use for security officer notification.\n" + 
			"    </Description>\n" + 
			"  </Variable>\n" + 
			"  <Variable initializer=\"parallel\" input=\"true\" name=\"approvalMode\">\n" + 
			"    <Description>\n" + 
			"        A string that specifies how we should handle the approvals.\n" + 
			"\n" + 
			"        By default this is serial since most of these request with\n" + 
			"        the exception of manager transfers will have only one approver.\n" + 
			"\n" + 
			"        parallel\n" + 
			"        Approvals are processed concurrently and there must be consensus,\n" + 
			"        we wait for all approvers to approve.  The first approver that\n" + 
			"        rejects terminates the entire approval.\n" + 
			"\n" + 
			"        parallelPoll\n" + 
			"        Approvals are processed concurrently but consensus is not required.\n" + 
			"        All approvals will be processed, we don't stop if there are any\n" + 
			"        rejections.\n" + 
			"\n" + 
			"        serial\n" + 
			"        Approvals are processed one at a time and there must be consensus.\n" + 
			"        The first approver that rejects terminates the entire approval.\n" + 
			"\n" + 
			"        serialPoll\n" + 
			"        Approvals are processed in order but consensus is not required.\n" + 
			"        All approvals will be processed, we don't stop if there are any\n" + 
			"        rejections.  In effect we are \"taking a poll\" of the approvers.\n" + 
			"\n" + 
			"        any\n" + 
			"        Approvals are processed concurrently, the first approver to\n" + 
			"        respond makes the decision for the group.\n" + 
			"    </Description>\n" + 
			"  </Variable>\n" + 
			"  <Variable initializer=\"owner\" input=\"true\" name=\"approvalScheme\">\n" + 
			"    <Description>\n" + 
			"      A csv string that specifies how approval items should be generated\n" + 
			"      for the incoming request.\n" + 
			"\n" + 
			"      The value can be \"none\", in which case approvals are disabled.\n" + 
			"\n" + 
			"      The value can also be a combination of any of the values below\n" + 
			"      in any order, separated by commas. The order in which they are\n" + 
			"      specified is the order in which they are processed:\n" + 
			"\n" + 
			"      owner\n" + 
			"        The object owner gets the approval item.\n" + 
			"        For Role approvals this is the Role object owner.\n" + 
			"        For Entitlement approvals this is the Entitlement object owner.\n" + 
			"\n" + 
			"      manager\n" + 
			"        The manager gets the approval item.\n" + 
			"\n" + 
			"      securityOfficer\n" + 
			"        The identity in the variable securityOfficerName gets the approval item.\n" + 
			"\n" + 
			"      identity\n" + 
			"        The identities/workgroups in the variable approvingIdentities get the approval item.\n" + 
			"    </Description>\n" + 
			"  </Variable>\n" + 
			"  <Variable initializer=\"LCM Identity Update Approval\" input=\"true\" name=\"approvalEmailTemplate\">\n" + 
			"    <Description>\n" + 
			"     The email template to use for approval notifications.\n" + 
			"    </Description>\n" + 
			"  </Variable>\n" + 
			"  <Variable initializer=\"Normal\" input=\"true\" name=\"workItemPriority\">\n" + 
			"    <Description>\n" + 
			"       The String version of a WorkItem.Priority. This variable is\n" + 
			"       used to set the priority on all of the workitems generated\n" + 
			"       as part of this workflow and also set on the IdentityRequest\n" + 
			"       object.\n" + 
			"    </Description>\n" + 
			"  </Variable>\n" + 
			"  <Variable input=\"true\" name=\"securityOfficerName\">\n" + 
			"    <Description>\n" + 
			"       The name of the identity that will be sent approvals\n" + 
			"       during security officer approvals.\n" + 
			"    </Description>\n" + 
			"  </Variable>\n" + 
			"  <Variable initializer=\"spadmin\" input=\"true\" name=\"fallbackApprover\">\n" + 
			"    <Description>\n" + 
			"      A String that specifies the name of the Identity that will\n" + 
			"      be assigned any approvals where the owner of the approver\n" + 
			"      can't be resolved. Example if the scheme is \"owner\" and the\n" + 
			"      application doesn't specify and owner.\n" + 
			"    </Description>\n" + 
			"  </Variable>\n" + 
			"  <Variable initializer=\"continue\" input=\"true\" name=\"policyScheme\">\n" + 
			"    <Description>\n" + 
			"      A String that specifies how policy checks effect the overall\n" + 
			"      process.\n" + 
			"\n" + 
			"      none - disabled policy checking\n" + 
			"\n" + 
			"      continue -  continue if policy violations are found\n" + 
			"\n" + 
			"      interactive -  allow requester to remove request items which are causing violations\n" + 
			"\n" + 
			"      fail -  this option will cause the workflow to terminate immediately if any policy violations are found.\n" + 
			"              Note that the requester will not be notified that the workflow has terminated.\n" + 
			"    </Description>\n" + 
			"  </Variable>\n" + 
			"  <Variable initializer=\"false\" input=\"true\" name=\"enableRetryRequest\">\n" + 
			"    <Description>\n" + 
			"      When set to true it will disable the workflow retry loop and let the\n" + 
			"      Provision step launch requests to handle the retries.  Enabling\n" + 
			"      this flag will enable some older functionality.\n" + 
			"    </Description>\n" + 
			"  </Variable>\n" + 
			"  <Variable input=\"true\" name=\"policiesToCheck\">\n" + 
			"    <Description>\n" + 
			"      A List of policies that should be checked. If this list is\n" + 
			"      empty all violations will be checked. Used in combination\n" + 
			"      with policyScheme.\n" + 
			"    </Description>\n" + 
			"  </Variable>\n" + 
			"  <Variable initializer=\"LCM\" input=\"true\" name=\"source\">\n" + 
			"    <Description>\n" + 
			"      String version of sailpoint.object.Source to indicate\n" + 
			"      where the request originated.  Defaults to LCM.\n" + 
			"    </Description>\n" + 
			"  </Variable>\n" + 
			"  <Variable initializer=\"false\" name=\"trace\">\n" + 
			"    <Description>\n" + 
			"      Used for debugging this workflow and when set to true trace\n" + 
			"      will be sent to stdout.\n" + 
			"    </Description>\n" + 
			"  </Variable>\n" + 
			"  <Variable name=\"approvalSet\">\n" + 
			"    <Description>\n" + 
			"       This attributes is set during the \"Build Approval Set\" step,\n" + 
			"       which builds this list by going through the ProvisioningPlan\n" + 
			"       to build the line items that need to be approved,\n" + 
			"\n" + 
			"       This variable includes all ApprovalItems that are part of\n" + 
			"       the request process and is updated during the AfterScript\n" + 
			"       of the approval process by assimilating the decisions\n" + 
			"       and comments from the Approvals copy of the ApprovalItem.\n" + 
			"    </Description>\n" + 
			"  </Variable>\n" + 
			"  <Variable initializer=\"true\" name=\"allowRequestsWithViolations\">\n" + 
			"    <Description>\n" + 
			"      If this variable is set to true, requesters will be able to proceed past\n" + 
			"      the Policy Violation Review form without taking any action on\n" + 
			"      policy violations resulting from the request. This is only relevant\n" + 
			"      if policyScheme=interactive.\n" + 
			"    </Description>\n" + 
			"  </Variable>\n" + 
			"  <Variable initializer=\"true\" name=\"requireViolationReviewComments\">\n" + 
			"    <Description>\n" + 
			"      If true, requesters will be required to enter in comments if they\n" + 
			"      proceed with a request that will result in policy violations. This\n" + 
			"      is only relevant if policyScheme=interactive.\n" + 
			"    </Description>\n" + 
			"  </Variable>\n" + 
			"  <Variable name=\"project\">\n" + 
			"    <Description>\n" + 
			"      ProvisioningProject which is just a compiled version of the ProvisioningPlan.\n" + 
			"    </Description>\n" + 
			"  </Variable>\n" + 
			"  <Variable name=\"policyViolations\">\n" + 
			"    <Description>\n" + 
			"       List of policy violations that were found during our initial policy scan.\n" + 
			"       This list is passed into each work item so the approvers can see\n" + 
			"       pending violations.\n" + 
			"    </Description>\n" + 
			"  </Variable>\n" + 
			"  <Variable name=\"identityRequestId\" output=\"true\">\n" + 
			"    <Description>\n" + 
			"       The sequence id of the Identity request object which is stored in\n" + 
			"       the name field of the identity request and auto-incremented.\n" + 
			"    </Description>\n" + 
			"  </Variable>\n" + 
			"  <Variable name=\"violationReviewDecision\">\n" + 
			"    <Description>\n" + 
			"       Decision made by the user in the Policy Violation Review step.\n" + 
			"       This may be one of three choices:\n" + 
			"\n" + 
			"       -ignore:   User is ignoring the violations and letting the request continue. If\n" + 
			"                   requireViolationReviewComments=true the user will be required to enter\n" + 
			"                   comments indicating why they are allowing the violations.\n" + 
			"\n" + 
			"       -remediate: Indicates that the user removed the request items that were causing the\n" + 
			"                   violations\n" + 
			"\n" + 
			"       -cancel:   Indicates that the user decided to abandon the request, terminating the workflow.\n" + 
			"\n" + 
			"     </Description>\n" + 
			"  </Variable>\n" + 
			"  <Variable name=\"workItemComments\">\n" + 
			"    <Description>\n" + 
			"      Global comments accumulated during the workflow which should be shared\n" + 
			"      with other approvals. When a new approval is created, the comments in this\n" + 
			"      list will be added to the work item.\n" + 
			"    </Description>\n" + 
			"  </Variable>\n" + 
			"  <Variable input=\"true\" name=\"ticketManagementApplication\">\n" + 
			"    <Description>\n" + 
			"      Name of the application that can handle ticket requests.\n" + 
			"      When non-null the Manage Ticket Steps will be visited to open\n" + 
			"      tickets during the workflow lifecycle.\n" + 
			"    </Description>\n" + 
			"  </Variable>\n" + 
			"  <Variable name=\"ticketId\">\n" + 
			"    <Description>\n" + 
			"      The id of the ticket that is generated by the ticketingManagementApplication.\n" + 
			"      This is typically generated on the \"open\" call, and then used in subsequent\n" + 
			"      calls.  It is also stored on the IdentityRequest object under the\n" + 
			"      externalTicketId variable.\n" + 
			"    </Description>\n" + 
			"  </Variable>\n" + 
			"  <Variable input=\"true\" name=\"managerElectronicSignature\">\n" + 
			"    <Description>\n" + 
			"       The name of the electronic signature object that should be used when workitems\n" + 
			"       are completed by a manager.\n" + 
			"    </Description>\n" + 
			"  </Variable>\n" + 
			"  <Variable input=\"true\" name=\"ownerElectronicSignature\">\n" + 
			"    <Description>\n" + 
			"       The name of the electronic signature object that should be used when workitems\n" + 
			"       are completed by object owners.\n" + 
			"    </Description>\n" + 
			"  </Variable>\n" + 
			"  <Variable input=\"true\" name=\"securityOfficerElectronicSignature\">\n" + 
			"    <Description>\n" + 
			"       The name of the electronic signature object that should be used when workitems\n" + 
			"       are completed by the security officer.\n" + 
			"    </Description>\n" + 
			"  </Variable>\n" + 
			"  <Variable input=\"true\" name=\"approvingIdentities\">\n" + 
			"    <Description>\n" + 
			"      List of identities and/or workgroups names/ids that should be involved in the approval\n" + 
			"      process.\n" + 
			"    </Description>\n" + 
			"  </Variable>\n" + 
			"  <Variable input=\"true\" name=\"identityElectronicSignature\">\n" + 
			"    <Description>\n" + 
			"      The name of the electronic signature object that should be used when workitems\n" + 
			"      are completed by identities and/or workgroups.\n" + 
			"    </Description>\n" + 
			"  </Variable>\n" + 
			"  <Variable input=\"true\" name=\"identityEmailTemplate\">\n" + 
			"    <Description>\n" + 
			"      Name of the email template to use when notifying the identities/workgroups of pending approvals.\n" + 
			"    </Description>\n" + 
			"  </Variable>\n" + 
			"  <Variable initializer=\"true\" input=\"true\" name=\"filterRejects\">\n" + 
			"    <Description>True to filter rejected items when running in Serial/SerialPoll mode.</Description>\n" + 
			"  </Variable>\n" + 
			"  <Variable initializer=\"false\" input=\"true\" name=\"setPreviousApprovalDecisions\">\n" + 
			"    <Description>True to pre-populate approval decisions from previous approvals.</Description>\n" + 
			"  </Variable>\n" + 
			"  <Variable name=\"approvalSplitPoint\">\n" + 
			"    <Description>\n" + 
			"          Variable to determine when to split into parallel processing.\n" + 
			"          This should map to a configured approvalScheme. We will process all schemes up until\n" + 
			"          the approvalSplitPoint in the Pre Split approvals, and the remaining schemes after\n" + 
			"          we split the items. If this is not specified, we will not split the Provisioning\n" + 
			"          project, and process the entire project as a whole.\n" + 
			"      </Description>\n" + 
			"  </Variable>\n" + 
			"  <Variable name=\"splitPlans\">\n" + 
			"    <Description>\n" + 
			"          List of ProvisioningPlan that is generated from the splitPlans step if approvalSplitPoint is set.\n" + 
			"      </Description>\n" + 
			"  </Variable>\n" + 
			"  <Variable name=\"splitProjects\">\n" + 
			"    <Description>\n" + 
			"          Variable to store the returns if approvalSplitPoint is set. This will contain a List&lt;ProvisioningProject>\n" + 
			"      </Description>\n" + 
			"  </Variable>\n" + 
			"  <Variable name=\"splitApprovalSet\">\n" + 
			"    <Description>\n" + 
			"          Variable to store the list of approvalSets returned from the split subprocess if approvalSplitPoint is set.\n" + 
			"      </Description>\n" + 
			"  </Variable>\n" + 
			"  <Variable name=\"splitWorkItemComments\">\n" + 
			"    <Description>\n" + 
			"          Variable to store the list of WorkItem comments returned from the split subprocess if approvalSplitPoint is set.\n" + 
			"      </Description>\n" + 
			"  </Variable>\n" + 
			"  <RuleLibraries>\n" + 
			"    <Reference class=\"sailpoint.object.Rule\" id=\"ff8081815aa41390015aa4143248018b\" name=\"LCM Workflow Library\"/>\n" + 
			"  </RuleLibraries>\n" + 
			"  <Step icon=\"Start\" name=\"Start\" posX=\"25\" posY=\"10\">\n" + 
			"    <Transition to=\"Initialize\"/>\n" + 
			"  </Step>\n" + 
			"  <Step icon=\"Task\" name=\"Initialize\" posX=\"134\" posY=\"10\">\n" + 
			"    <Arg name=\"flow\" value=\"ref:flow\"/>\n" + 
			"    <Arg name=\"formTemplate\" value=\"Identity Update\"/>\n" + 
			"    <Arg name=\"identityName\" value=\"ref:identityName\"/>\n" + 
			"    <Arg name=\"identityDisplayName\" value=\"ref:identityDisplayName\"/>\n" + 
			"    <Arg name=\"launcher\" value=\"ref:launcher\"/>\n" + 
			"    <Arg name=\"optimisticProvisioning\" value=\"ref:optimisticProvisioning\"/>\n" + 
			"    <Arg name=\"plan\" value=\"ref:plan\"/>\n" + 
			"    <Arg name=\"priority\" value=\"ref:workItemPriority\"/>\n" + 
			"    <Arg name=\"policiesToCheck\" value=\"ref:policiesToCheck\"/>\n" + 
			"    <Arg name=\"policyScheme\" value=\"ref:policyScheme\"/>\n" + 
			"    <Arg name=\"source\" value=\"ref:source\"/>\n" + 
			"    <Arg name=\"trace\" value=\"ref:trace\"/>\n" + 
			"    <Arg name=\"requireViolationReviewComments\" value=\"ref:requireViolationReviewComments\"/>\n" + 
			"    <Arg name=\"allowRequestsWithViolations\" value=\"ref:allowRequestsWithViolations\"/>\n" + 
			"    <Arg name=\"enableRetryRequest\" value=\"ref:enableRetryRequest\"/>\n" + 
			"    <Arg name=\"batchRequestItemId\" value=\"ref:batchRequestItemId\"/>\n" + 
			"    <Arg name=\"endOnProvisioningForms\" value=\"ref:endOnProvisioningForms\"/>\n" + 
			"    <Arg name=\"endOnManualWorkItems\" value=\"ref:endOnManualWorkItems\"/>\n" + 
			"    <Description>\n" + 
			"      Call the standard subprocess to initialize the request, this includes\n" + 
			"      auditing, building the approvalset, compiling the plan into\n" + 
			"       project and checking policy violations.\n" + 
			"    </Description>\n" + 
			"    <Return name=\"project\" to=\"project\"/>\n" + 
			"    <Return name=\"approvalSet\" to=\"approvalSet\"/>\n" + 
			"    <Return name=\"policyViolations\" to=\"policyViolations\"/>\n" + 
			"    <Return name=\"identityRequestId\" to=\"identityRequestId\"/>\n" + 
			"    <Return name=\"violationReviewDecision\" to=\"violationReviewDecision\"/>\n" + 
			"    <Return merge=\"true\" name=\"workItemComments\" to=\"workItemComments\"/>\n" + 
			"    <WorkflowRef>\n" + 
			"      <Reference class=\"sailpoint.object.Workflow\" id=\"ff8081815aa414bc015aa41520e7003f\" name=\"Identity Request Initialize\"/>\n" + 
			"    </WorkflowRef>\n" + 
			"    <Transition to=\"Exit On Manual Work Items\" when=\"script:(isTrue(endOnManualWorkItems) &amp;&amp; (project.getUnmanagedPlan() != null))\"/>\n" + 
			"    <Transition to=\"Exit On Provisioning Form\" when=\"script:(isTrue(endOnProvisioningForms) &amp;&amp; (project.hasQuestions()))\"/>\n" + 
			"    <Transition to=\"Exit On Policy Violation\" when=\"script:(&quot;cancel&quot;.equals(violationReviewDecision) || ((size(policyViolations) > 0 ) &amp;&amp; (policyScheme.equals(&quot;fail&quot;))))\"/>\n" + 
			"    <Transition to=\"Create Ticket\"/>\n" + 
			"  </Step>\n" + 
			"  <Step condition=\"script:(ticketManagementApplication != null)\" icon=\"Task\" name=\"Create Ticket\" posX=\"381\" posY=\"6\">\n" + 
			"    <Arg name=\"action\" value=\"open\"/>\n" + 
			"    <Arg name=\"source\" value=\"ref:source\"/>\n" + 
			"    <Arg name=\"priority\" value=\"ref:workItemPriority\"/>\n" + 
			"    <Arg name=\"project\" value=\"ref:project\"/>\n" + 
			"    <Arg name=\"ticketManagementApplication\" value=\"ref:ticketManagementApplication\"/>\n" + 
			"    <Arg name=\"identityRequestId\" value=\"ref:identityRequestId\"/>\n" + 
			"    <Arg name=\"ticketDataGenerationRule\" value=\"\"/>\n" + 
			"    <Arg name=\"trace\" value=\"ref:trace\"/>\n" + 
			"    <Description>\n" + 
			"      Call a subprocess to create a ticket in the ticketManagementApplication is non-null.\n" + 
			"      You can specify a specific 'ticketDataGenerationRule' here or you can also specify\n" + 
			"      it on the application.  It'll be read from the argument first and fall back to the '\n" + 
			"      application config.\n" + 
			"    </Description>\n" + 
			"    <Return name=\"ticketId\" to=\"externalTicketId\"/>\n" + 
			"    <WorkflowRef>\n" + 
			"      <Reference class=\"sailpoint.object.Workflow\" id=\"ff8081815aa41390015aa4142e21017f\" name=\"Manage Ticket\"/>\n" + 
			"    </WorkflowRef>\n" + 
			"    <Transition to=\"Pre Split Approve\" when=\"script:(!isNull(approvalSplitPoint) &amp;&amp; csvToList(approvalScheme).contains(approvalSplitPoint))\"/>\n" + 
			"    <Transition to=\"Approve and Provision\"/>\n" + 
			"  </Step>\n" + 
			"  <Step condition=\"script:((flow == null) ||  (!&quot;UnlockAccount&quot;.equals(flow)))\" icon=\"Task\" name=\"Pre Split Approve\" posX=\"518\" posY=\"63\">\n" + 
			"    <Arg name=\"approvalMode\" value=\"ref:approvalMode\"/>\n" + 
			"    <Arg name=\"approvalScheme\" value=\"ref:approvalScheme\">\n" + 
			"      <Script>\n" + 
			"        <Source>\n" + 
			"              import java.util.List;\n" + 
			"              import java.util.ArrayList;\n" + 
			"              import java.util.Iterator;\n" + 
			"              import sailpoint.tools.Util;\n" + 
			"\n" + 
			"              List schemes = Util.csvToList(approvalScheme);\n" + 
			"              List preSchemes = new ArrayList&lt;String>();\n" + 
			"              for (String s : Util.safeIterable(schemes)) {\n" + 
			"                if (s.equals(approvalSplitPoint)) {\n" + 
			"                    break;\n" + 
			"                } else {\n" + 
			"                    preSchemes.add(s);\n" + 
			"                }\n" + 
			"              }\n" + 
			"              return Util.listToCsv(preSchemes);\n" + 
			"          </Source>\n" + 
			"      </Script>\n" + 
			"    </Arg>\n" + 
			"    <Arg name=\"approvalSet\" value=\"ref:approvalSet\"/>\n" + 
			"    <Arg name=\"approvalAssignmentRule\"/>\n" + 
			"    <Arg name=\"approvingIdentities\" value=\"ref:approvingIdentities\"/>\n" + 
			"    <Arg name=\"approvalSplitPoint\" value=\"ref:approvalSplitPoint\"/>\n" + 
			"    <Arg name=\"fallbackApprover\" value=\"ref:fallbackApprover\"/>\n" + 
			"    <Arg name=\"filterRejects\" value=\"ref:filterRejects\"/>\n" + 
			"    <Arg name=\"flow\" value=\"ref:flow\"/>\n" + 
			"    <Arg name=\"identityName\" value=\"ref:identityName\"/>\n" + 
			"    <Arg name=\"identityDisplayName\" value=\"ref:identityDisplayName\"/>\n" + 
			"    <Arg name=\"identityElectronicSignature\" value=\"ref:identityElectronicSignature\"/>\n" + 
			"    <Arg name=\"identityEmailTemplate\" value=\"ref:identityEmailTemplate\"/>\n" + 
			"    <Arg name=\"identityRequestId\" value=\"ref:identityRequestId\"/>\n" + 
			"    <Arg name=\"launcher\" value=\"ref:launcher\"/>\n" + 
			"    <Arg name=\"managerEmailTemplate\" value=\"ref:approvalEmailTemplate\"/>\n" + 
			"    <Arg name=\"managerElectronicSignature\" value=\"ref:managerElectronicSignature\"/>\n" + 
			"    <Arg name=\"ownerEmailTemplate\" value=\"ref:approvalEmailTemplate\"/>\n" + 
			"    <Arg name=\"ownerElectronicSignature\" value=\"ref:ownerElectronicSignature\"/>\n" + 
			"    <Arg name=\"project\" value=\"ref:project\"/>\n" + 
			"    <Arg name=\"plan\" value=\"ref:plan\"/>\n" + 
			"    <Arg name=\"policyViolations\" value=\"ref:policyViolations\"/>\n" + 
			"    <Arg name=\"priority\" value=\"ref:workItemPriority\"/>\n" + 
			"    <Arg name=\"securityOfficerName\" value=\"ref:securityOfficerName\"/>\n" + 
			"    <Arg name=\"securityOfficerEmailTemplate\" value=\"ref:approvalEmailTemplate\"/>\n" + 
			"    <Arg name=\"securityOfficerElectronicSignature\" value=\"ref:securityOfficerElectronicSignature\"/>\n" + 
			"    <Arg name=\"setPreviousApprovalDecisions\" value=\"ref:setPreviousApprovalDecisions\"/>\n" + 
			"    <Arg name=\"trace\" value=\"ref:trace\"/>\n" + 
			"    <Arg name=\"workItemReminderTemplate\"/>\n" + 
			"    <Arg name=\"workItemHoursBetweenReminders\"/>\n" + 
			"    <Arg name=\"workItemMaxReminders\"/>\n" + 
			"    <Arg name=\"workItemEscalationTemplate\"/>\n" + 
			"    <Arg name=\"workItemHoursTillEscalation\"/>\n" + 
			"    <Arg name=\"workItemEscalationRule\"/>\n" + 
			"    <Arg name=\"workItemComments\"/>\n" + 
			"    <Description>\n" + 
			"      Call to our standard subprocess to handle the default approvals for\n" + 
			"      manager, owner and security officer.\n" + 
			"    </Description>\n" + 
			"    <Return name=\"approvalSet\"/>\n" + 
			"    <Return name=\"workItemComments\"/>\n" + 
			"    <Return name=\"project\"/>\n" + 
			"    <WorkflowRef>\n" + 
			"      <Reference class=\"sailpoint.object.Workflow\" id=\"ff8081815aa41390015aa41441a401a8\" name=\"Provisioning Approval Subprocess\"/>\n" + 
			"    </WorkflowRef>\n" + 
			"    <Transition to=\"Split Plan\"/>\n" + 
			"  </Step>\n" + 
			"  <Step action=\"call:splitProvisioningPlan\" icon=\"Task\" name=\"Split Plan\" posX=\"618\" posY=\"63\" resultVariable=\"splitPlans\">\n" + 
			"    <Arg name=\"project\" value=\"ref:project\"/>\n" + 
			"    <Description>\n" + 
			"          Step to split the provisioning project into individual projects for each item requested.\n" + 
			"          This will only run if the approvalSplitPoint is configured.\n" + 
			"      </Description>\n" + 
			"    <Transition to=\"Approve and Provision Split\"/>\n" + 
			"  </Step>\n" + 
			"  <Step icon=\"Task\" name=\"Approve and Provision Split\" posX=\"783\" posY=\"63\">\n" + 
			"    <Arg name=\"approvalMode\" value=\"ref:approvalMode\"/>\n" + 
			"    <Arg name=\"approvalScheme\" value=\"ref:approvalScheme\">\n" + 
			"      <Script>\n" + 
			"        <Source>\n" + 
			"              import java.util.List;\n" + 
			"              import java.util.Iterator;\n" + 
			"              import sailpoint.tools.Util;\n" + 
			"              List schemes = Util.csvToList(approvalScheme);\n" + 
			"              Iterator it = schemes.iterator();\n" + 
			"              while (it.hasNext()) {\n" + 
			"                String s = it.next();\n" + 
			"                if (!s.equals(approvalSplitPoint)) {\n" + 
			"                    it.remove();\n" + 
			"                } else {\n" + 
			"                    break;\n" + 
			"                }\n" + 
			"              }\n" + 
			"              return Util.listToCsv(schemes);\n" + 
			"            </Source>\n" + 
			"      </Script>\n" + 
			"    </Arg>\n" + 
			"    <Arg name=\"approvalSet\" value=\"ref:approvalSet\"/>\n" + 
			"    <Arg name=\"approvalSplitPoint\" value=\"ref:approvalSplitPoint\"/>\n" + 
			"    <Arg name=\"approvalAssignmentRule\"/>\n" + 
			"    <Arg name=\"approvingIdentities\" value=\"ref:approvingIdentities\"/>\n" + 
			"    <Arg name=\"fallbackApprover\" value=\"ref:fallbackApprover\"/>\n" + 
			"    <Arg name=\"foregroundProvisioning\" value=\"ref:foregroundProvisioning\"/>\n" + 
			"    <Arg name=\"formTemplate\" value=\"Identity Update\"/>\n" + 
			"    <Arg name=\"filterRejects\" value=\"ref:filterRejects\"/>\n" + 
			"    <Arg name=\"flow\" value=\"ref:flow\"/>\n" + 
			"    <Arg name=\"identityName\" value=\"ref:identityName\"/>\n" + 
			"    <Arg name=\"identityDisplayName\" value=\"ref:identityDisplayName\"/>\n" + 
			"    <Arg name=\"identityElectronicSignature\" value=\"ref:identityElectronicSignature\"/>\n" + 
			"    <Arg name=\"identityEmailTemplate\" value=\"ref:identityEmailTemplate\"/>\n" + 
			"    <Arg name=\"identityName\" value=\"ref:identityName\"/>\n" + 
			"    <Arg name=\"identityRequestId\" value=\"ref:identityRequestId\"/>\n" + 
			"    <Arg name=\"launcher\" value=\"ref:launcher\"/>\n" + 
			"    <Arg name=\"managerEmailTemplate\" value=\"ref:approvalEmailTemplate\"/>\n" + 
			"    <Arg name=\"managerElectronicSignature\" value=\"ref:managerElectronicSignature\"/>\n" + 
			"    <Arg name=\"manualActionsEmailTemplate\" value=\"Pending Manual Changes\"/>\n" + 
			"    <Arg name=\"optimisticProvisioning\" value=\"ref:optimisticProvisioning\"/>\n" + 
			"    <Arg name=\"ownerEmailTemplate\" value=\"ref:approvalEmailTemplate\"/>\n" + 
			"    <Arg name=\"ownerElectronicSignature\" value=\"ref:ownerElectronicSignature\"/>\n" + 
			"    <Arg name=\"project\" value=\"ref:project\"/>\n" + 
			"    <Arg name=\"plan\" value=\"ref:plan\"/>\n" + 
			"    <Arg name=\"policyScheme\" value=\"ref:policyScheme\"/>\n" + 
			"    <Arg name=\"policyViolations\" value=\"ref:policyViolations\"/>\n" + 
			"    <Arg name=\"priority\" value=\"ref:workItemPriority\"/>\n" + 
			"    <Arg name=\"securityOfficerName\" value=\"ref:securityOfficerName\"/>\n" + 
			"    <Arg name=\"securityOfficerEmailTemplate\" value=\"ref:approvalEmailTemplate\"/>\n" + 
			"    <Arg name=\"securityOfficerElectronicSignature\" value=\"ref:securityOfficerElectronicSignature\"/>\n" + 
			"    <Arg name=\"setPreviousApprovalDecisions\" value=\"ref:setPreviousApprovalDecisions\"/>\n" + 
			"    <Arg name=\"splitProvisioning\" value=\"true\"/>\n" + 
			"    <Arg name=\"source\" value=\"ref:source\"/>\n" + 
			"    <Arg name=\"ticketManagementApplication\" value=\"ref:ticketManagementApplication\"/>\n" + 
			"    <Arg name=\"trace\" value=\"ref:trace\"/>\n" + 
			"    <Arg name=\"workItemReminderTemplate\" value=\"ref:workItemReminderTemplate\"/>\n" + 
			"    <Arg name=\"workItemHoursBetweenReminders\" value=\"ref:workItemHoursBetweenReminders\"/>\n" + 
			"    <Arg name=\"workItemMaxReminders\" value=\"ref:workItemMaxReminders\"/>\n" + 
			"    <Arg name=\"workItemEscalationTemplate\" value=\"ref:workItemEscalationTemplate\"/>\n" + 
			"    <Arg name=\"workItemHoursTillEscalation\" value=\"ref:workItemHoursTillEscalation\"/>\n" + 
			"    <Arg name=\"workItemEscalationRule\" value=\"ref:workItemEscalationRule\"/>\n" + 
			"    <Arg name=\"workItemComments\" value=\"ref:workItemComments\"/>\n" + 
			"    <Description>\n" + 
			"          Call the Approve and Provision Subprocess for each Provisioning Project generated in the Split Plan step.\n" + 
			"      </Description>\n" + 
			"    <Replicator arg=\"plan\" items=\"splitPlans\"/>\n" + 
			"    <Return name=\"project\" to=\"splitProjects\"/>\n" + 
			"    <Return name=\"workItemComments\" to=\"splitWorkItemComments\"/>\n" + 
			"    <Return name=\"approvalSet\" to=\"splitApprovalSet\"/>\n" + 
			"    <WorkflowRef>\n" + 
			"      <Reference class=\"sailpoint.object.Workflow\" id=\"ff8081815aa414bc015aa41525f50045\" name=\"Approve and Provision Subprocess\"/>\n" + 
			"    </WorkflowRef>\n" + 
			"    <Transition to=\"Assimilate Splits\"/>\n" + 
			"  </Step>\n" + 
			"  <Step action=\"call:joinLCMProvWorkflowSplits\" icon=\"Task\" name=\"Assimilate Splits\" posX=\"914\" posY=\"63\">\n" + 
			"    <Arg name=\"splitProjects\" value=\"ref:splitProjects\"/>\n" + 
			"    <Arg name=\"splitWorkItemComments\" value=\"ref:splitWorkItemComments\"/>\n" + 
			"    <Arg name=\"splitApprovalSet\" value=\"ref:splitApprovalSet\"/>\n" + 
			"    <Description>\n" + 
			"          Assimilate all projects returned from the split into the global project.\n" + 
			"      </Description>\n" + 
			"    <Transition to=\"Refresh Identity\"/>\n" + 
			"  </Step>\n" + 
			"  <Step icon=\"Task\" name=\"Approve and Provision\" posX=\"699\" posY=\"7\">\n" + 
			"    <Arg name=\"approvalMode\" value=\"ref:approvalMode\"/>\n" + 
			"    <Arg name=\"approvalScheme\" value=\"ref:approvalScheme\"/>\n" + 
			"    <Arg name=\"approvalSet\" value=\"ref:approvalSet\"/>\n" + 
			"    <Arg name=\"approvalSplitPoint\" value=\"ref:approvalSplitPoint\"/>\n" + 
			"    <Arg name=\"approvalAssignmentRule\"/>\n" + 
			"    <Arg name=\"approvingIdentities\" value=\"ref:approvingIdentities\"/>\n" + 
			"    <Arg name=\"fallbackApprover\" value=\"ref:fallbackApprover\"/>\n" + 
			"    <Arg name=\"foregroundProvisioning\" value=\"ref:foregroundProvisioning\"/>\n" + 
			"    <Arg name=\"formTemplate\" value=\"Identity Update\"/>\n" + 
			"    <Arg name=\"filterRejects\" value=\"ref:filterRejects\"/>\n" + 
			"    <Arg name=\"flow\" value=\"ref:flow\"/>\n" + 
			"    <Arg name=\"identityName\" value=\"ref:identityName\"/>\n" + 
			"    <Arg name=\"identityDisplayName\" value=\"ref:identityDisplayName\"/>\n" + 
			"    <Arg name=\"identityElectronicSignature\" value=\"ref:identityElectronicSignature\"/>\n" + 
			"    <Arg name=\"identityEmailTemplate\" value=\"ref:identityEmailTemplate\"/>\n" + 
			"    <Arg name=\"identityName\" value=\"ref:identityName\"/>\n" + 
			"    <Arg name=\"identityRequestId\" value=\"ref:identityRequestId\"/>\n" + 
			"    <Arg name=\"launcher\" value=\"ref:launcher\"/>\n" + 
			"    <Arg name=\"managerEmailTemplate\" value=\"ref:approvalEmailTemplate\"/>\n" + 
			"    <Arg name=\"managerElectronicSignature\" value=\"ref:managerElectronicSignature\"/>\n" + 
			"    <Arg name=\"manualActionsEmailTemplate\" value=\"Pending Manual Changes\"/>\n" + 
			"    <Arg name=\"optimisticProvisioning\" value=\"ref:optimisticProvisioning\"/>\n" + 
			"    <Arg name=\"ownerEmailTemplate\" value=\"ref:approvalEmailTemplate\"/>\n" + 
			"    <Arg name=\"ownerElectronicSignature\" value=\"ref:ownerElectronicSignature\"/>\n" + 
			"    <Arg name=\"project\" value=\"ref:project\"/>\n" + 
			"    <Arg name=\"plan\" value=\"ref:plan\"/>\n" + 
			"    <Arg name=\"policyScheme\" value=\"ref:policyScheme\"/>\n" + 
			"    <Arg name=\"policyViolations\" value=\"ref:policyViolations\"/>\n" + 
			"    <Arg name=\"priority\" value=\"ref:workItemPriority\"/>\n" + 
			"    <Arg name=\"securityOfficerName\" value=\"ref:securityOfficerName\"/>\n" + 
			"    <Arg name=\"securityOfficerEmailTemplate\" value=\"ref:approvalEmailTemplate\"/>\n" + 
			"    <Arg name=\"securityOfficerElectronicSignature\" value=\"ref:securityOfficerElectronicSignature\"/>\n" + 
			"    <Arg name=\"setPreviousApprovalDecisions\" value=\"ref:setPreviousApprovalDecisions\"/>\n" + 
			"    <Arg name=\"source\" value=\"ref:source\"/>\n" + 
			"    <Arg name=\"splitProvisioning\" value=\"false\"/>\n" + 
			"    <Arg name=\"ticketManagementApplication\" value=\"ref:ticketManagementApplication\"/>\n" + 
			"    <Arg name=\"trace\" value=\"ref:trace\"/>\n" + 
			"    <Arg name=\"workItemReminderTemplate\" value=\"ref:workItemReminderTemplate\"/>\n" + 
			"    <Arg name=\"workItemHoursBetweenReminders\" value=\"ref:workItemHoursBetweenReminders\"/>\n" + 
			"    <Arg name=\"workItemMaxReminders\" value=\"ref:workItemMaxReminders\"/>\n" + 
			"    <Arg name=\"workItemEscalationTemplate\" value=\"ref:workItemEscalationTemplate\"/>\n" + 
			"    <Arg name=\"workItemHoursTillEscalation\" value=\"ref:workItemHoursTillEscalation\"/>\n" + 
			"    <Arg name=\"workItemEscalationRule\" value=\"ref:workItemEscalationRule\"/>\n" + 
			"    <Arg name=\"workItemComments\" value=\"ref:workItemComments\"/>\n" + 
			"    <Description>\n" + 
			"          Finish any remaining approvals and provision.\n" + 
			"      </Description>\n" + 
			"    <Return name=\"project\" to=\"project\"/>\n" + 
			"    <Return name=\"approvalSet\" to=\"approvalSet\"/>\n" + 
			"    <Return name=\"workItemComments\" to=\"workItemComments\"/>\n" + 
			"    <WorkflowRef>\n" + 
			"      <Reference class=\"sailpoint.object.Workflow\" id=\"ff8081815aa414bc015aa41525f50045\" name=\"Approve and Provision Subprocess\"/>\n" + 
			"    </WorkflowRef>\n" + 
			"    <Transition to=\"Refresh Identity\"/>\n" + 
			"  </Step>\n" + 
			"  <Step action=\"call:refreshIdentity\" condition=\"ref:doRefresh\" icon=\"Task\" name=\"Refresh Identity\" posX=\"1028\" posY=\"7\">\n" + 
			"    <Arg name=\"identityName\" value=\"ref:identityName\"/>\n" + 
			"    <Arg name=\"correlateEntitlements\" value=\"true\"/>\n" + 
			"    <Description>\n" + 
			"      Add arguments as necessary to enable refresh features.  Typically you\n" + 
			"      only want this to correlate roles.  Don't ask for provisioning  since that\n" + 
			"      can result in provisioning policies that need to be presented and it's\n" + 
			"      too late for that.  This is only to get role detection and exception\n" + 
			"      entitlements in the cube.\n" + 
			"    </Description>\n" + 
			"    <Transition to=\"Notify\"/>\n" + 
			"  </Step>\n" + 
			"  <Step icon=\"Task\" name=\"Notify\" posX=\"1131\" posY=\"7\">\n" + 
			"    <Arg name=\"approvalScheme\" value=\"ref:approvalScheme\"/>\n" + 
			"    <Arg name=\"approvalSet\" value=\"ref:approvalSet\"/>\n" + 
			"    <Arg name=\"flow\" value=\"ref:flow\"/>\n" + 
			"    <Arg name=\"identityDisplayName\" value=\"ref:identityDisplayName\"/>\n" + 
			"    <Arg name=\"identityName\" value=\"ref:identityName\"/>\n" + 
			"    <Arg name=\"launcher\" value=\"ref:launcher\"/>\n" + 
			"    <Arg name=\"notificationScheme\" value=\"ref:notificationScheme\"/>\n" + 
			"    <Arg name=\"userEmailTemplate\" value=\"ref:userEmailTemplate\"/>\n" + 
			"    <Arg name=\"requesterEmailTemplate\" value=\"ref:requesterEmailTemplate\"/>\n" + 
			"    <Arg name=\"managerEmailTemplate\" value=\"ref:managerEmailTemplate\"/>\n" + 
			"    <Arg name=\"plan\" value=\"ref:plan\"/>\n" + 
			"    <Arg name=\"policyViolations\" value=\"ref:policyViolations\"/>\n" + 
			"    <Arg name=\"priority\" value=\"ref:workItemPriority\"/>\n" + 
			"    <Arg name=\"securityOfficerEmailTemplate\" value=\"ref:securityOfficerEmailTemplate\"/>\n" + 
			"    <Arg name=\"securityOfficerName\" value=\"ref:securityOfficerName\"/>\n" + 
			"    <Arg name=\"trace\" value=\"ref:trace\"/>\n" + 
			"    <Arg name=\"source\" value=\"ref:source\"/>\n" + 
			"    <Description>\n" + 
			"      Call the standard subprocess that will notify the various\n" + 
			"      actors based on notification scheme.\n" + 
			"    </Description>\n" + 
			"    <WorkflowRef>\n" + 
			"      <Reference class=\"sailpoint.object.Workflow\" id=\"ff8081815aa414bc015aa415222d0041\" name=\"Identity Request Notify\"/>\n" + 
			"    </WorkflowRef>\n" + 
			"    <Transition to=\"end\"/>\n" + 
			"  </Step>\n" + 
			"  <Step action=\"call:addMessage\" name=\"Exit On Policy Violation\" posX=\"201\" posY=\"253\">\n" + 
			"    <Arg name=\"message\" value=\"Failed due to policy violation(s)\"/>\n" + 
			"    <Arg name=\"type\" value=\"Error\"/>\n" + 
			"    <Transition to=\"end\"/>\n" + 
			"  </Step>\n" + 
			"  <Step action=\"call:addMessage\" name=\"Exit On Manual Work Items\" posX=\"364\" posY=\"107\">\n" + 
			"    <Arg name=\"message\" value=\"Failed due to manual work item(s)\"/>\n" + 
			"    <Arg name=\"type\" value=\"Error\"/>\n" + 
			"    <Transition to=\"end\"/>\n" + 
			"  </Step>\n" + 
			"  <Step action=\"call:addMessage\" name=\"Exit On Provisioning Form\" posX=\"280\" posY=\"178\">\n" + 
			"    <Arg name=\"message\" value=\"Failed due to provisioning form\"/>\n" + 
			"    <Arg name=\"type\" value=\"Error\"/>\n" + 
			"    <Transition to=\"end\"/>\n" + 
			"  </Step>\n" + 
			"  <Step catches=\"complete\" icon=\"Catches\" name=\"Finalize\" posX=\"1058\" posY=\"308\">\n" + 
			"    <Arg name=\"approvalSet\" value=\"ref:approvalSet\"/>\n" + 
			"    <Arg name=\"batchRequestItemId\" value=\"ref:batchRequestItemId\"/>\n" + 
			"    <Arg name=\"identityRequestId\" value=\"ref:identityRequestId\"/>\n" + 
			"    <Arg name=\"project\" value=\"ref:project\"/>\n" + 
			"    <Arg name=\"priority\" value=\"ref:workItemPriority\"/>\n" + 
			"    <Arg name=\"ticketManagementApplication\" value=\"ref:ticketManagementApplication\"/>\n" + 
			"    <Arg name=\"trace\" value=\"ref:trace\"/>\n" + 
			"    <Description>\n" + 
			"      Call the standard subprocess that can audit/finalize the request.\n" + 
			"    </Description>\n" + 
			"    <WorkflowRef>\n" + 
			"      <Reference class=\"sailpoint.object.Workflow\" id=\"ff8081815aa414bc015aa41523b10043\" name=\"Identity Request Finalize\"/>\n" + 
			"    </WorkflowRef>\n" + 
			"  </Step>\n" + 
			"  <Step icon=\"Stop\" name=\"end\" posX=\"1131\" posY=\"253\"/>\n" + 
			"</Workflow>\n" + 
			"";
	
	
	final String diff = "<?xml version='1.0' encoding='UTF-8'?>\n" + 
			"<!DOCTYPE Workflow PUBLIC \"sailpoint.dtd\" \"sailpoint.dtd\">\n" + 
			"<Workflow configForm=\"Provisioning Workflow Config Form\" created=\"1488811533902\" handler=\"sailpoint.api.StandardWorkflowHandler\" id=\"ff8081815aa414bc015aa415264e0046\" libraries=\"Identity,Role,PolicyViolation,LCM,BatchRequest\" name=\"LCM Provisioning\" taskType=\"LCM\" type=\"LCMProvisioning\">\n" + 
			"  <Variable input=\"true\" name=\"identityName\">\n" + 
			"    <Description>The name of the identity being updated.</Description>\n" + 
			"  </Variable>\n" +
			"  <Variable initializer=\"script:(identityDisplayName != void) ? identityDisplayName : resolveDisplayName(identityName)\" input=\"true\" name=\"identityDisplayName\">\n" + 
			"    <Description>\n" + 
			"      The displayName of the identity being updated.\n" + 
			"      Query for this using a projection query and fall back to the name.\n" + 
			"    </Description>\n" + 
			"  </Variable>\n" + 
			"  <Variable initializer=\"false\" input=\"true\" name=\"endOnManualWorkItems\">\n" + 
			"    <Description>Option to skip requests with manual work items.</Description>\n" + 
			"  </Variable>\n" + 
			"  <Variable initializer=\"false\" input=\"true\" name=\"endOnProvisioningForms\">\n" + 
			"    <Description>Option to skip requests with provisioning forms.</Description>\n" + 
			"  </Variable>\n" + 
			
			"  <Variable input=\"true\" name=\"plan\">\n" + 
			"    <Description>The provisioning plan ready to execute.</Description>\n" + 
			"  </Variable>\n" + 
			"  <Variable input=\"true\" name=\"flow\">\n" + 
			"    <Description>\n" + 
			"      The name of the LCM flow that launched this workflow.\n" + 
			"\n" + 
			"      This is one of these three values:\n" + 
			"\n" + 
			"      AccountsRequest\n" + 
			"      EntitlementsRequest\n" + 
			"      RolesRequest\n" + 
			"    </Description>\n" + 
			"  </Variable>\n" + 
			"  <Variable editable=\"true\" initializer=\"false\" name=\"optimisticProvisioning\">\n" + 
			"    <Description>\n" + 
			"      Set to true to enable optimistic provisioning.  This will cause\n" + 
			"      changes to the entitlements compiled from role assignments to be\n" + 
			"      applied immediately to the identity cube rather than waiting\n" + 
			"      for the next refresh/reaggregation after the provisioning system\n" + 
			"      completes the request.\n" + 
			"    </Description>\n" + 
			"  </Variable>\n" + 
			"  <Variable editable=\"true\" initializer=\"true\" name=\"foregroundProvisioning\">\n" + 
			"    <Description>\n" + 
			"      Normally provisioning is done in a step that uses the \"background\"\n" + 
			"      option to force the workflow to be suspend and be resumed in a\n" + 
			"      background task thread.  This prevents the browser session from\n" + 
			"      hanging since provision can sometimes take a long time.  For demos\n" + 
			"      and testing it can be better to do this in the foreground so that\n" + 
			"      provisioning will have been performed when control is returned to the\n" + 
			"      user.  This prevents having to run the Perform Maintenance task to\n" + 
			"      see the results of the request.\n" + 
			"    </Description>\n" + 
			"  </Variable>\n" + 
			"  <Variable input=\"true\" name=\"batchRequestItemId\">\n" + 
			"    <Description>\n" + 
			"      Used by the batch interface to record back individual request item status. The specific item id for the individual request in the batch file.\n" + 
			"    </Description>\n" + 
			"  </Variable>\n" + 
			"  <Variable editable=\"true\" name=\"doRefresh\">\n" + 
			"    <Description>\n" + 
			"      Set to true to cause an identity refresh after the changes in the plan\n" + 
			"      have been provisioned.  This is normally off, you might want this on\n" + 
			"      if you want modification of identity or link attributes to result in\n" + 
			"      an immediate re-evaluation of assigned and detected roles.\n" + 
			"    </Description>\n" + 
			"  </Variable>\n" + 
			"  <Variable initializer=\"user,requester\" input=\"true\" name=\"notificationScheme\">\n" + 
			"    <Description>\n" + 
			"     A string that specifies who should be notified when the request has been complete.\n" + 
			"     The value can be null or a csv of one or more of the following options.\n" + 
			"\n" + 
			"     none or null\n" + 
			"       disable notifications\n" + 
			"\n" + 
			"     user\n" + 
			"       Identity that is being update will be notified.\n" + 
			"\n" + 
			"     manager\n" + 
			"       The manager of the Identity that is being updated will be notified.\n" + 
			"\n" + 
			"     requester\n" + 
			"       The person that has requested the update will be notified.\n" + 
			"    </Description>\n" + 
			"  </Variable>\n" + 
			"  <Variable initializer=\"LCM User Notification\" input=\"true\" name=\"userEmailTemplate\">\n" + 
			"    <Description>\n" + 
			"     The email template to use for user notification.\n" + 
			"    </Description>\n" + 
			"  </Variable>\n" + 
			"  <Variable initializer=\"LCM Requester Notification\" input=\"true\" name=\"requesterEmailTemplate\">\n" + 
			"    <Description>\n" + 
			"     The email template to use for requester notification.\n" + 
			"    </Description>\n" + 
			"  </Variable>\n" + 
			"  <Variable initializer=\"LCM Manager Notification\" input=\"true\" name=\"managerEmailTemplate\">\n" + 
			"    <Description>\n" + 
			"     The email template to use for manager notification.\n" + 
			"    </Description>\n" + 
			"  </Variable>\n" + 
			"  <Variable input=\"true\" name=\"securityOfficerEmailTemplate\">\n" + 
			"    <Description>\n" + 
			"     The email template to use for security officer notification.\n" + 
			"    </Description>\n" + 
			"  </Variable>\n" + 
			"  <Variable initializer=\"parallel\" input=\"true\" name=\"approvalMode\">\n" + 
			"    <Description>\n" + 
			"        A string that specifies how we should handle the approvals.\n" + 
			"\n" + 
			"        By default this is serial since most of these request with\n" + 
			"        the exception of manager transfers will have only one approver.\n" + 
			"\n" + 
			"        parallel\n" + 
			"        Approvals are processed concurrently and there must be consensus,\n" + 
			"        we wait for all approvers to approve.  The first approver that\n" + 
			"        rejects terminates the entire approval.\n" + 
			"\n" + 
			"        parallelPoll\n" + 
			"        Approvals are processed concurrently but consensus is not required.\n" + 
			"        All approvals will be processed, we don't stop if there are any\n" + 
			"        rejections.\n" + 
			"\n" + 
			"        serial\n" + 
			"        Approvals are processed one at a time and there must be consensus.\n" + 
			"        The first approver that rejects terminates the entire approval.\n" + 
			"\n" + 
			"        serialPoll\n" + 
			"        Approvals are processed in order but consensus is not required.\n" + 
			"        All approvals will be processed, we don't stop if there are any\n" + 
			"        rejections.  In effect we are \"taking a poll\" of the approvers.\n" + 
			"\n" + 
			"        any\n" + 
			"        Approvals are processed concurrently, the first approver to\n" + 
			"        respond makes the decision for the group.\n" + 
			"    </Description>\n" + 
			"  </Variable>\n" + 
			"  <Variable initializer=\"owner\" input=\"true\" name=\"approvalScheme\">\n" + 
			"    <Description>\n" + 
			"      A csv string that specifies how approval items should be generated\n" + 
			"      for the incoming request.\n" + 
			"\n" + 
			"      The value can be \"none\", in which case approvals are disabled.\n" + 
			"\n" + 
			"      The value can also be a combination of any of the values below\n" + 
			"      in any order, separated by commas. The order in which they are\n" + 
			"      specified is the order in which they are processed:\n" + 
			"\n" + 
			"      owner\n" + 
			"        The object owner gets the approval item.\n" + 
			"        For Role approvals this is the Role object owner.\n" + 
			"        For Entitlement approvals this is the Entitlement object owner.\n" + 
			"\n" + 
			"      manager\n" + 
			"        The manager gets the approval item.\n" + 
			"\n" + 
			"      securityOfficer\n" + 
			"        The identity in the variable securityOfficerName gets the approval item.\n" + 
			"\n" + 
			"      identity\n" + 
			"        The identities/workgroups in the variable approvingIdentities get the approval item.\n" + 
			"    </Description>\n" + 
			"  </Variable>\n" + 
			"  <Variable initializer=\"LCM Identity Update Approval\" input=\"true\" name=\"approvalEmailTemplate\">\n" + 
			"    <Description>\n" + 
			"     The email template to use for approval notifications.\n" + 
			"    </Description>\n" + 
			"  </Variable>\n" + 
			"  <Variable initializer=\"Normal\" input=\"true\" name=\"workItemPriority\">\n" + 
			"    <Description>\n" + 
			"       The String version of a WorkItem.Priority. This variable is\n" + 
			"       used to set the priority on all of the workitems generated\n" + 
			"       as part of this workflow and also set on the IdentityRequest\n" + 
			"       object.\n" + 
			"    </Description>\n" + 
			"  </Variable>\n" + 
			"  <Variable input=\"true\" name=\"securityOfficerName\">\n" + 
			"    <Description>\n" + 
			"       The name of the identity that will be sent approvals\n" + 
			"       during security officer approvals.\n" + 
			"    </Description>\n" + 
			"  </Variable>\n" + 
			"  <Variable initializer=\"spadmin\" input=\"true\" name=\"fallbackApprover\">\n" + 
			"    <Description>\n" + 
			"      A String that specifies the name of the Identity that will\n" + 
			"      be assigned any approvals where the owner of the approver\n" + 
			"      can't be resolved. Example if the scheme is \"owner\" and the\n" + 
			"      application doesn't specify and owner.\n" + 
			"    </Description>\n" + 
			"  </Variable>\n" + 
			"  <Variable initializer=\"continue\" input=\"true\" name=\"policyScheme\">\n" + 
			"    <Description>\n" + 
			"      A String that specifies how policy checks effect the overall\n" + 
			"      process.\n" + 
			"\n" + 
			"      none - disabled policy checking\n" + 
			"\n" + 
			"      continue -  continue if policy violations are found\n" + 
			"\n" + 
			"      interactive -  allow requester to remove request items which are causing violations\n" + 
			"\n" + 
			"      fail -  this option will cause the workflow to terminate immediately if any policy violations are found.\n" + 
			"              Note that the requester will not be notified that the workflow has terminated.\n" + 
			"    </Description>\n" + 
			"  </Variable>\n" + 
			"  <Variable initializer=\"false\" input=\"true\" name=\"enableRetryRequest\">\n" + 
			"    <Description>\n" + 
			"      When set to true it will disable the workflow retry loop and let the\n" + 
			"      Provision step launch requests to handle the retries.  Enabling\n" + 
			"      this flag will enable some older functionality.\n" + 
			"    </Description>\n" + 
			"  </Variable>\n" + 
			"  <Variable input=\"true\" name=\"policiesToCheck\">\n" + 
			"    <Description>\n" + 
			"      A List of policies that should be checked. If this list is\n" + 
			"      empty all violations will be checked. Used in combination\n" + 
			"      with policyScheme.\n" + 
			"    </Description>\n" + 
			"  </Variable>\n" + 
			"  <Variable initializer=\"LCM\" input=\"true\" name=\"source\">\n" + 
			"    <Description>\n" + 
			"      String version of sailpoint.object.Source to indicate\n" + 
			"      where the request originated.  Defaults to LCM.\n" + 
			"    </Description>\n" + 
			"  </Variable>\n" + 
			"  <Variable initializer=\"false\" name=\"trace\">\n" + 
			"    <Description>\n" + 
			"      Used for debugging this workflow and when set to true trace\n" + 
			"      will be sent to stdout.\n" + 
			"    </Description>\n" + 
			"  </Variable>\n" + 
			"  <Variable name=\"approvalSet\">\n" + 
			"    <Description>\n" + 
			"       This attributes is set during the \"Build Approval Set\" step,\n" + 
			"       which builds this list by going through the ProvisioningPlan\n" + 
			"       to build the line items that need to be approved,\n" + 
			"\n" + 
			"       This variable includes all ApprovalItems that are part of\n" + 
			"       the request process and is updated during the AfterScript\n" + 
			"       of the approval process by assimilating the decisions\n" + 
			"       and comments from the Approvals copy of the ApprovalItem.\n" + 
			"    </Description>\n" + 
			"  </Variable>\n" + 
			"  <Variable initializer=\"true\" name=\"allowRequestsWithViolations\">\n" + 
			"    <Description>\n" + 
			"      If this variable is set to true, requesters will be able to proceed past\n" + 
			"      the Policy Violation Review form without taking any action on\n" + 
			"      policy violations resulting from the request. This is only relevant\n" + 
			"      if policyScheme=interactive.\n" + 
			"    </Description>\n" + 
			"  </Variable>\n" + 
			"  <Variable initializer=\"true\" name=\"requireViolationReviewComments\">\n" + 
			"    <Description>\n" + 
			"      If true, requesters will be required to enter in comments if they\n" + 
			"      proceed with a request that will result in policy violations. This\n" + 
			"      is only relevant if policyScheme=interactive.\n" + 
			"    </Description>\n" + 
			"  </Variable>\n" + 
			"  <Variable name=\"project\">\n" + 
			"    <Description>\n" + 
			"      ProvisioningProject which is just a compiled version of the ProvisioningPlan.\n" + 
			"    </Description>\n" + 
			"  </Variable>\n" + 
			"  <Variable name=\"policyViolations\">\n" + 
			"    <Description>\n" + 
			"       List of policy violations that were found during our initial policy scan.\n" + 
			"       This list is passed into each work item so the approvers can see\n" + 
			"       pending violations.\n" + 
			"    </Description>\n" + 
			"  </Variable>\n" + 
			"  <Variable name=\"identityRequestId\" output=\"true\">\n" + 
			"    <Description>\n" + 
			"       The sequence id of the Identity request object which is stored in\n" + 
			"       the name field of the identity request and auto-incremented.\n" + 
			"    </Description>\n" + 
			"  </Variable>\n" + 
			"  <Variable name=\"violationReviewDecision\">\n" + 
			"    <Description>\n" + 
			"       Decision made by the user in the Policy Violation Review step.\n" + 
			"       This may be one of three choices:\n" + 
			"\n" + 
			"       -ignore:   User is ignoring the violations and letting the request continue. If\n" + 
			"                   requireViolationReviewComments=true the user will be required to enter\n" + 
			"                   comments indicating why they are allowing the violations.\n" + 
			"\n" + 
			"       -remediate: Indicates that the user removed the request items that were causing the\n" + 
			"                   violations\n" + 
			"\n" + 
			"       -cancel:   Indicates that the user decided to abandon the request, terminating the workflow.\n" + 
			"\n" + 
			"     </Description>\n" + 
			"  </Variable>\n" + 
			"  <Variable name=\"workItemComments\">\n" + 
			"    <Description>\n" + 
			"      Global comments accumulated during the workflow which should be shared\n" + 
			"      with other approvals. When a new approval is created, the comments in this\n" + 
			"      list will be added to the work item.\n" + 
			"    </Description>\n" + 
			"  </Variable>\n" + 
			"  <Variable input=\"true\" name=\"ticketManagementApplication\">\n" + 
			"    <Description>\n" + 
			"      Name of the application that can handle ticket requests.\n" + 
			"      When non-null the Manage Ticket Steps will be visited to open\n" + 
			"      tickets during the workflow lifecycle.\n" + 
			"    </Description>\n" + 
			"  </Variable>\n" + 
			"  <Variable name=\"ticketId\">\n" + 
			"    <Description>\n" + 
			"      The id of the ticket that is generated by the ticketingManagementApplication.\n" + 
			"      This is typically generated on the \"open\" call, and then used in subsequent\n" + 
			"      calls.  It is also stored on the IdentityRequest object under the\n" + 
			"      externalTicketId variable.\n" + 
			"    </Description>\n" + 
			"  </Variable>\n" + 
			"  <Variable input=\"true\" name=\"managerElectronicSignature\">\n" + 
			"    <Description>\n" + 
			"       The name of the electronic signature object that should be used when workitems\n" + 
			"       are completed by a manager.\n" + 
			"    </Description>\n" + 
			"  </Variable>\n" + 
			"  <Variable input=\"true\" name=\"ownerElectronicSignature\">\n" + 
			"    <Description>\n" + 
			"       The name of the electronic signature object that should be used when workitems\n" + 
			"       are completed by object owners.\n" + 
			"    </Description>\n" + 
			"  </Variable>\n" + 
			"  <Variable input=\"true\" name=\"securityOfficerElectronicSignature\">\n" + 
			"    <Description>\n" + 
			"       The name of the electronic signature object that should be used when workitems\n" + 
			"       are completed by the security officer.\n" + 
			"    </Description>\n" + 
			"  </Variable>\n" + 
			"  <Variable input=\"true\" name=\"approvingIdentities\">\n" + 
			"    <Description>\n" + 
			"      List of identities and/or workgroups names/ids that should be involved in the approval\n" + 
			"      process.\n" + 
			"    </Description>\n" + 
			"  </Variable>\n" + 
			"  <Variable input=\"true\" name=\"identityElectronicSignature\">\n" + 
			"    <Description>\n" + 
			"      The name of the electronic signature object that should be used when workitems\n" + 
			"      are completed by identities and/or workgroups.\n" + 
			"    </Description>\n" + 
			"  </Variable>\n" + 
			"  <Variable input=\"true\" name=\"identityEmailTemplate\">\n" + 
			"    <Description>\n" + 
			"      Name of the email template to use when notifying the identities/workgroups of pending approvals.\n" + 
			"    </Description>\n" + 
			"  </Variable>\n" + 
			"  <Variable initializer=\"true\" input=\"true\" name=\"filterRejects\">\n" + 
			"    <Description>True to filter rejected items when running in Serial/SerialPoll mode.</Description>\n" + 
			"  </Variable>\n" + 
			"  <Variable initializer=\"false\" input=\"true\" name=\"setPreviousApprovalDecisions\">\n" + 
			"    <Description>True to pre-populate approval decisions from previous approvals.</Description>\n" + 
			"  </Variable>\n" + 
			"  <Variable name=\"approvalSplitPoint\">\n" + 
			"    <Description>\n" + 
			"          Variable to determine when to split into parallel processing.\n" + 
			"          This should map to a configured approvalScheme. We will process all schemes up until\n" + 
			"          the approvalSplitPoint in the Pre Split approvals, and the remaining schemes after\n" + 
			"          we split the items. If this is not specified, we will not split the Provisioning\n" + 
			"          project, and process the entire project as a whole.\n" + 
			"      </Description>\n" + 
			"  </Variable>\n" + 
			"  <Variable name=\"splitPlans\">\n" + 
			"    <Description>\n" + 
			"          List of ProvisioningPlan that is generated from the splitPlans step if approvalSplitPoint is set.\n" + 
			"      </Description>\n" + 
			"  </Variable>\n" + 
			"  <Variable name=\"splitProjects\">\n" + 
			"    <Description>\n" + 
			"          Variable to store the returns if approvalSplitPoint is set. This will contain a List&lt;ProvisioningProject>\n" + 
			"      </Description>\n" + 
			"  </Variable>\n" + 
			"  <Variable name=\"splitApprovalSet\">\n" + 
			"    <Description>\n" + 
			"          Variable to store the list of approvalSets returned from the split subprocess if approvalSplitPoint is set.\n" + 
			"      </Description>\n" + 
			"  </Variable>\n" + 
			"  <Variable name=\"splitWorkItemComments\">\n" + 
			"    <Description>\n" + 
			"          Variable to store the list of WorkItem comments returned from the split subprocess if approvalSplitPoint is set.\n" + 
			"      </Description>\n" + 
			"  </Variable>\n" + 
			"  <RuleLibraries>\n" + 
			"    <Reference class=\"sailpoint.object.Rule\" id=\"ff8081815aa41390015aa4143248018b\" name=\"LCM Workflow Library\"/>\n" + 
			"  </RuleLibraries>\n" + 
			"  <Step icon=\"Start\" name=\"Start\" posX=\"25\" posY=\"10\">\n" + 
			"    <Transition to=\"Initialize\"/>\n" + 
			"  </Step>\n" + 
			"  <Step icon=\"Task\" name=\"Initialize\" posX=\"134\" posY=\"10\">\n" + 
			"    <Arg name=\"flow\" value=\"ref:flow\"/>\n" + 
			"    <Arg name=\"formTemplate\" value=\"Identity Update\"/>\n" + 
			"    <Arg name=\"identityName\" value=\"ref:identityName\"/>\n" + 
			"    <Arg name=\"identityDisplayName\" value=\"ref:identityDisplayName\"/>\n" + 
			"    <Arg name=\"launcher\" value=\"ref:launcher\"/>\n" + 
			"    <Arg name=\"optimisticProvisioning\" value=\"ref:optimisticProvisioning\"/>\n" + 
			"    <Arg name=\"plan\" value=\"ref:plan\"/>\n" + 
			"    <Arg name=\"priority\" value=\"ref:workItemPriority\"/>\n" + 
			"    <Arg name=\"policiesToCheck\" value=\"ref:policiesToCheck\"/>\n" + 
			"    <Arg name=\"policyScheme\" value=\"ref:policyScheme\"/>\n" + 
			"    <Arg name=\"source\" value=\"ref:source\"/>\n" + 
			"    <Arg name=\"trace\" value=\"ref:trace\"/>\n" + 
			"    <Arg name=\"requireViolationReviewComments\" value=\"ref:requireViolationReviewComments\"/>\n" + 
			"    <Arg name=\"allowRequestsWithViolations\" value=\"ref:allowRequestsWithViolations\"/>\n" + 
			"    <Arg name=\"enableRetryRequest\" value=\"ref:enableRetryRequest\"/>\n" + 
			"    <Arg name=\"batchRequestItemId\" value=\"ref:batchRequestItemId\"/>\n" + 
			"    <Arg name=\"endOnProvisioningForms\" value=\"ref:endOnProvisioningForms\"/>\n" + 
			"    <Arg name=\"endOnManualWorkItems\" value=\"ref:endOnManualWorkItems\"/>\n" + 
			"    <Description>\n" + 
			"      Call the standard subprocess to initialize the request, this includes\n" + 
			"      auditing, building the approvalset, compiling the plan into\n" + 
			"       project and checking policy violations.\n" + 
			"    </Description>\n" + 
			"    <Return name=\"project\" to=\"project\"/>\n" + 
			"    <Return name=\"approvalSet\" to=\"approvalSet\"/>\n" + 
			"    <Return name=\"policyViolations\" to=\"policyViolations\"/>\n" + 
			"    <Return name=\"identityRequestId\" to=\"identityRequestId\"/>\n" + 
			"    <Return name=\"violationReviewDecision\" to=\"violationReviewDecision\"/>\n" + 
			"    <Return merge=\"true\" name=\"workItemComments\" to=\"workItemComments\"/>\n" + 
			"    <WorkflowRef>\n" + 
			"      <Reference class=\"sailpoint.object.Workflow\" id=\"ff8081815aa414bc015aa41520e7003f\" name=\"Identity Request Initialize\"/>\n" + 
			"    </WorkflowRef>\n" + 
			"    <Transition to=\"Exit On Manual Work Items\" when=\"script:(isTrue(endOnManualWorkItems) &amp;&amp; (project.getUnmanagedPlan() != null))\"/>\n" + 
			"    <Transition to=\"Exit On Provisioning Form\" when=\"script:(isTrue(endOnProvisioningForms) &amp;&amp; (project.hasQuestions()))\"/>\n" + 
			"    <Transition to=\"Exit On Policy Violation\" when=\"script:(&quot;cancel&quot;.equals(violationReviewDecision) || ((size(policyViolations) > 0 ) &amp;&amp; (policyScheme.equals(&quot;fail&quot;))))\"/>\n" + 
			"    <Transition to=\"Create Ticket\"/>\n" + 
			"  </Step>\n" + 
			"  <Step condition=\"script:(ticketManagementApplication != null)\" icon=\"Task\" name=\"Create Ticket\" posX=\"381\" posY=\"6\">\n" + 
			"    <Arg name=\"action\" value=\"open\"/>\n" + 
			"    <Arg name=\"source\" value=\"ref:source\"/>\n" + 
			"    <Arg name=\"priority\" value=\"ref:workItemPriority\"/>\n" + 
			"    <Arg name=\"project\" value=\"ref:project\"/>\n" + 
			"    <Arg name=\"ticketManagementApplication\" value=\"ref:ticketManagementApplication\"/>\n" + 
			"    <Arg name=\"identityRequestId\" value=\"ref:identityRequestId\"/>\n" + 
			"    <Arg name=\"ticketDataGenerationRule\" value=\"\"/>\n" + 
			"    <Arg name=\"trace\" value=\"ref:trace\"/>\n" + 
			"    <Description>\n" + 
			"      Call a subprocess to create a ticket in the ticketManagementApplication is non-null.\n" + 
			"      You can specify a specific 'ticketDataGenerationRule' here or you can also specify\n" + 
			"      it on the application.  It'll be read from the argument first and fall back to the '\n" + 
			"      application config.\n" + 
			"    </Description>\n" + 
			"    <Return name=\"ticketId\" to=\"externalTicketId\"/>\n" + 
			"    <WorkflowRef>\n" + 
			"      <Reference class=\"sailpoint.object.Workflow\" id=\"ff8081815aa41390015aa4142e21017f\" name=\"Manage Ticket\"/>\n" + 
			"    </WorkflowRef>\n" + 
			"    <Transition to=\"Pre Split Approve\" when=\"script:(!isNull(approvalSplitPoint) &amp;&amp; csvToList(approvalScheme).contains(approvalSplitPoint))\"/>\n" + 
			"    <Transition to=\"Approve and Provision\"/>\n" + 
			"  </Step>\n" + 
			"  <Step condition=\"script:((flow == null) ||  (!&quot;UnlockAccount&quot;.equals(flow)))\" icon=\"Task\" name=\"Pre Split Approve\" posX=\"518\" posY=\"63\">\n" + 
			"    <Arg name=\"approvalMode\" value=\"ref:approvalMode\"/>\n" + 
			"    <Arg name=\"approvalScheme\" value=\"ref:approvalScheme\">\n" + 
			"      <Script>\n" + 
			"        <Source>\n" + 
			"              import java.util.List;\n" + 
			"              import java.util.ArrayList;\n" + 
			"              import java.util.Iterator;\n" + 
			"              import sailpoint.tools.Util;\n" + 
			"\n" + 
			"              List schemes = Util.csvToList(approvalScheme);\n" + 
			"              List preSchemes = new ArrayList&lt;String>();\n" + 
			"              for (String s : Util.safeIterable(schemes)) {\n" + 
			"                if (s.equals(approvalSplitPoint)) {\n" + 
			"                    break;\n" + 
			"                } else {\n" + 
			"                    preSchemes.add(s);\n" + 
			"                }\n" + 
			"              }\n" + 
			"              return Util.listToCsv(preSchemes);\n" + 
			"          </Source>\n" + 
			"      </Script>\n" + 
			"    </Arg>\n" + 
			"    <Arg name=\"approvalSet\" value=\"ref:approvalSet\"/>\n" + 
			"    <Arg name=\"approvalAssignmentRule\"/>\n" + 
			"    <Arg name=\"approvingIdentities\" value=\"ref:approvingIdentities\"/>\n" + 
			"    <Arg name=\"approvalSplitPoint\" value=\"ref:approvalSplitPoint\"/>\n" + 
			"    <Arg name=\"fallbackApprover\" value=\"ref:fallbackApprover\"/>\n" + 
			"    <Arg name=\"filterRejects\" value=\"ref:filterRejects\"/>\n" + 
			"    <Arg name=\"flow\" value=\"ref:flow\"/>\n" + 
			"    <Arg name=\"identityName\" value=\"ref:identityName\"/>\n" + 
			"    <Arg name=\"identityDisplayName\" value=\"ref:identityDisplayName\"/>\n" + 
			"    <Arg name=\"identityElectronicSignature\" value=\"ref:identityElectronicSignature\"/>\n" + 
			"    <Arg name=\"identityEmailTemplate\" value=\"ref:identityEmailTemplate\"/>\n" + 
			"    <Arg name=\"identityRequestId\" value=\"ref:identityRequestId\"/>\n" + 
			"    <Arg name=\"launcher\" value=\"ref:launcher\"/>\n" + 
			"    <Arg name=\"managerEmailTemplate\" value=\"ref:approvalEmailTemplate\"/>\n" + 
			"    <Arg name=\"managerElectronicSignature\" value=\"ref:managerElectronicSignature\"/>\n" + 
			"    <Arg name=\"ownerEmailTemplate\" value=\"ref:approvalEmailTemplate\"/>\n" + 
			"    <Arg name=\"ownerElectronicSignature\" value=\"ref:ownerElectronicSignature\"/>\n" + 
			"    <Arg name=\"project\" value=\"ref:project\"/>\n" + 
			"    <Arg name=\"plan\" value=\"ref:plan\"/>\n" + 
			"    <Arg name=\"policyViolations\" value=\"ref:policyViolations\"/>\n" + 
			"    <Arg name=\"priority\" value=\"ref:workItemPriority\"/>\n" + 
			"    <Arg name=\"securityOfficerName\" value=\"ref:securityOfficerName\"/>\n" + 
			"    <Arg name=\"securityOfficerEmailTemplate\" value=\"ref:approvalEmailTemplate\"/>\n" + 
			"    <Arg name=\"securityOfficerElectronicSignature\" value=\"ref:securityOfficerElectronicSignature\"/>\n" + 
			"    <Arg name=\"setPreviousApprovalDecisions\" value=\"ref:setPreviousApprovalDecisions\"/>\n" + 
			"    <Arg name=\"trace\" value=\"ref:trace\"/>\n" + 
			"    <Arg name=\"workItemReminderTemplate\"/>\n" + 
			"    <Arg name=\"workItemHoursBetweenReminders\"/>\n" + 
			"    <Arg name=\"workItemMaxReminders\"/>\n" + 
			"    <Arg name=\"workItemEscalationTemplate\"/>\n" + 
			"    <Arg name=\"workItemHoursTillEscalation\"/>\n" + 
			"    <Arg name=\"workItemEscalationRule\"/>\n" + 
			"    <Arg name=\"workItemComments\"/>\n" + 
			"    <Description>\n" + 
			"      Call to our standard subprocess to handle the default approvals for\n" + 
			"      manager, owner and security officer.\n" + 
			"    </Description>\n" + 
			"    <Return name=\"approvalSet\"/>\n" + 
			"    <Return name=\"workItemComments\"/>\n" + 
			"    <Return name=\"project\"/>\n" + 
			"    <WorkflowRef>\n" + 
			"      <Reference class=\"sailpoint.object.Workflow\" id=\"ff8081815aa41390015aa41441a401a8\" name=\"Provisioning Approval Subprocess\"/>\n" + 
			"    </WorkflowRef>\n" + 
			"    <Transition to=\"Split Plan\"/>\n" + 
			"  </Step>\n" + 
			"  <Step action=\"call:splitProvisioningPlan\" icon=\"Task\" name=\"Split Plan\" posX=\"618\" posY=\"63\" resultVariable=\"splitPlans\">\n" + 
			"    <Arg name=\"project\" value=\"ref:project\"/>\n" + 
			"    <Description>\n" + 
			"          Step to split the provisioning project into individual projects for each item requested.\n" + 
			"          This will only run if the approvalSplitPoint is configured.\n" + 
			"      </Description>\n" + 
			"    <Transition to=\"Approve and Provision Split\"/>\n" + 
			"  </Step>\n" + 
			"  <Step icon=\"Task\" name=\"Approve and Provision Split\" posX=\"783\" posY=\"63\">\n" + 
			"    <Arg name=\"approvalMode\" value=\"ref:approvalMode\"/>\n" + 
			"    <Arg name=\"approvalScheme\" value=\"ref:approvalScheme\">\n" + 
			"      <Script>\n" + 
			"        <Source>\n" + 
			"              import java.util.List;\n" + 
			"              import java.util.Iterator;\n" + 
			"              import sailpoint.tools.Util;\n" + 
			"              List schemes = Util.csvToList(approvalScheme);\n" + 
			"              Iterator it = schemes.iterator();\n" + 
			"              while (it.hasNext()) {\n" + 
			"                String s = it.next();\n" + 
			"                if (!s.equals(approvalSplitPoint)) {\n" + 
			"                    it.remove();\n" + 
			"                } else {\n" + 
			"                    break;\n" + 
			"                }\n" + 
			"              }\n" + 
			"              return Util.listToCsv(schemes);\n" + 
			"            </Source>\n" + 
			"      </Script>\n" + 
			"    </Arg>\n" + 
			"    <Arg name=\"approvalSet\" value=\"ref:approvalSet\"/>\n" + 
			"    <Arg name=\"approvalSplitPoint\" value=\"ref:approvalSplitPoint\"/>\n" + 
			"    <Arg name=\"approvalAssignmentRule\"/>\n" + 
			"    <Arg name=\"approvingIdentities\" value=\"ref:approvingIdentities\"/>\n" + 
			"    <Arg name=\"fallbackApprover\" value=\"ref:fallbackApprover\"/>\n" + 
			"    <Arg name=\"foregroundProvisioning\" value=\"ref:foregroundProvisioning\"/>\n" + 
			"    <Arg name=\"formTemplate\" value=\"Identity Update\"/>\n" + 
			"    <Arg name=\"filterRejects\" value=\"ref:filterRejects\"/>\n" + 
			"    <Arg name=\"flow\" value=\"ref:flow\"/>\n" + 
			"    <Arg name=\"identityName\" value=\"ref:identityName\"/>\n" + 
			"    <Arg name=\"identityDisplayName\" value=\"ref:identityDisplayName\"/>\n" + 
			"    <Arg name=\"identityElectronicSignature\" value=\"ref:identityElectronicSignature\"/>\n" + 
			"    <Arg name=\"identityEmailTemplate\" value=\"ref:identityEmailTemplate\"/>\n" + 
			"    <Arg name=\"identityName\" value=\"ref:identityName\"/>\n" + 
			"    <Arg name=\"identityRequestId\" value=\"ref:identityRequestId\"/>\n" + 
			"    <Arg name=\"launcher\" value=\"ref:launcher\"/>\n" + 
			"    <Arg name=\"managerEmailTemplate\" value=\"ref:approvalEmailTemplate\"/>\n" + 
			"    <Arg name=\"managerElectronicSignature\" value=\"ref:managerElectronicSignature\"/>\n" + 
			"    <Arg name=\"manualActionsEmailTemplate\" value=\"Pending Manual Changes\"/>\n" + 
			"    <Arg name=\"optimisticProvisioning\" value=\"ref:optimisticProvisioning\"/>\n" + 
			"    <Arg name=\"ownerEmailTemplate\" value=\"ref:approvalEmailTemplate\"/>\n" + 
			"    <Arg name=\"ownerElectronicSignature\" value=\"ref:ownerElectronicSignature\"/>\n" + 
			"    <Arg name=\"project\" value=\"ref:project\"/>\n" + 
			"    <Arg name=\"plan\" value=\"ref:plan\"/>\n" + 
			"    <Arg name=\"policyScheme\" value=\"ref:policyScheme\"/>\n" + 
			"    <Arg name=\"policyViolations\" value=\"ref:policyViolations\"/>\n" + 
			"    <Arg name=\"priority\" value=\"ref:workItemPriority\"/>\n" + 
			"    <Arg name=\"securityOfficerName\" value=\"ref:securityOfficerName\"/>\n" + 
			"    <Arg name=\"securityOfficerEmailTemplate\" value=\"ref:approvalEmailTemplate\"/>\n" + 
			"    <Arg name=\"securityOfficerElectronicSignature\" value=\"ref:securityOfficerElectronicSignature\"/>\n" + 
			"    <Arg name=\"setPreviousApprovalDecisions\" value=\"ref:setPreviousApprovalDecisions\"/>\n" + 
			"    <Arg name=\"splitProvisioning\" value=\"true\"/>\n" + 
			"    <Arg name=\"source\" value=\"ref:source\"/>\n" + 
			"    <Arg name=\"ticketManagementApplication\" value=\"ref:ticketManagementApplication\"/>\n" + 
			"    <Arg name=\"trace\" value=\"ref:trace\"/>\n" + 
			"    <Arg name=\"workItemReminderTemplate\" value=\"ref:workItemReminderTemplate\"/>\n" + 
			"    <Arg name=\"workItemHoursBetweenReminders\" value=\"ref:workItemHoursBetweenReminders\"/>\n" + 
			"    <Arg name=\"workItemMaxReminders\" value=\"ref:workItemMaxReminders\"/>\n" + 
			"    <Arg name=\"workItemEscalationTemplate\" value=\"ref:workItemEscalationTemplate\"/>\n" + 
			"    <Arg name=\"workItemHoursTillEscalation\" value=\"ref:workItemHoursTillEscalation\"/>\n" + 
			"    <Arg name=\"workItemEscalationRule\" value=\"ref:workItemEscalationRule\"/>\n" + 
			"    <Arg name=\"workItemComments\" value=\"ref:workItemComments\"/>\n" + 
			"    <Description>\n" + 
			"          Call the Approve and Provision Subprocess for each Provisioning Project generated in the Split Plan step.\n" + 
			"      </Description>\n" + 
			"    <Replicator arg=\"plan\" items=\"splitPlans\"/>\n" + 
			"    <Return name=\"project\" to=\"splitProjects\"/>\n" + 
			"    <Return name=\"workItemComments\" to=\"splitWorkItemComments\"/>\n" + 
			"    <Return name=\"approvalSet\" to=\"splitApprovalSet\"/>\n" + 
			"    <WorkflowRef>\n" + 
			"      <Reference class=\"sailpoint.object.Workflow\" id=\"ff8081815aa414bc015aa41525f50045\" name=\"Approve and Provision Subprocess\"/>\n" + 
			"    </WorkflowRef>\n" + 
			"    <Transition to=\"Assimilate Splits\"/>\n" + 
			"  </Step>\n" + 
			"  <Step action=\"call:joinLCMProvWorkflowSplits\" icon=\"Task\" name=\"Assimilate Splits\" posX=\"914\" posY=\"63\">\n" + 
			"    <Arg name=\"splitProjects\" value=\"ref:splitProjects\"/>\n" + 
			"    <Arg name=\"splitWorkItemComments\" value=\"ref:splitWorkItemComments\"/>\n" + 
			"    <Arg name=\"splitApprovalSet\" value=\"ref:splitApprovalSet\"/>\n" + 
			"    <Description>\n" + 
			"          Assimilate all projects returned from the split into the global project.\n" + 
			"      </Description>\n" + 
			"    <Transition to=\"Refresh Identity\"/>\n" + 
			"  </Step>\n" + 
			"  <Step icon=\"Task\" name=\"Approve and Provision\" posX=\"699\" posY=\"7\">\n" + 
			"    <Arg name=\"approvalMode\" value=\"ref:approvalMode\"/>\n" + 
			"    <Arg name=\"approvalScheme\" value=\"ref:approvalScheme\"/>\n" + 
			"    <Arg name=\"approvalSet\" value=\"ref:approvalSet\"/>\n" + 
			"    <Arg name=\"approvalSplitPoint\" value=\"ref:approvalSplitPoint\"/>\n" + 
			"    <Arg name=\"approvalAssignmentRule\"/>\n" + 
			"    <Arg name=\"approvingIdentities\" value=\"ref:approvingIdentities\"/>\n" + 
			"    <Arg name=\"fallbackApprover\" value=\"ref:fallbackApprover\"/>\n" + 
			"    <Arg name=\"foregroundProvisioning\" value=\"ref:foregroundProvisioning\"/>\n" + 
			"    <Arg name=\"formTemplate\" value=\"Identity Update\"/>\n" + 
			"    <Arg name=\"filterRejects\" value=\"ref:filterRejects\"/>\n" + 
			"    <Arg name=\"flow\" value=\"ref:flow\"/>\n" + 
			"    <Arg name=\"identityName\" value=\"ref:identityName\"/>\n" + 
			"    <Arg name=\"identityDisplayName\" value=\"ref:identityDisplayName\"/>\n" + 
			"    <Arg name=\"identityElectronicSignature\" value=\"ref:identityElectronicSignature\"/>\n" + 
			"    <Arg name=\"identityEmailTemplate\" value=\"ref:identityEmailTemplate\"/>\n" + 
			"    <Arg name=\"identityName\" value=\"ref:identityName\"/>\n" + 
			"    <Arg name=\"identityRequestId\" value=\"ref:identityRequestId\"/>\n" + 
			"    <Arg name=\"launcher\" value=\"ref:launcher\"/>\n" + 
			"    <Arg name=\"managerEmailTemplate\" value=\"ref:approvalEmailTemplate\"/>\n" + 
			"    <Arg name=\"managerElectronicSignature\" value=\"ref:managerElectronicSignature\"/>\n" + 
			"    <Arg name=\"manualActionsEmailTemplate\" value=\"Pending Manual Changes\"/>\n" + 
			"    <Arg name=\"optimisticProvisioning\" value=\"ref:optimisticProvisioning\"/>\n" + 
			"    <Arg name=\"ownerEmailTemplate\" value=\"ref:approvalEmailTemplate\"/>\n" + 
			"    <Arg name=\"ownerElectronicSignature\" value=\"ref:ownerElectronicSignature\"/>\n" + 
			"    <Arg name=\"project\" value=\"ref:project\"/>\n" + 
			"    <Arg name=\"plan\" value=\"ref:plan\"/>\n" + 
			"    <Arg name=\"policyScheme\" value=\"ref:policyScheme\"/>\n" + 
			"    <Arg name=\"policyViolations\" value=\"ref:policyViolations\"/>\n" + 
			"    <Arg name=\"priority\" value=\"ref:workItemPriority\"/>\n" + 
			"    <Arg name=\"securityOfficerName\" value=\"ref:securityOfficerName\"/>\n" + 
			"    <Arg name=\"securityOfficerEmailTemplate\" value=\"ref:approvalEmailTemplate\"/>\n" + 
			"    <Arg name=\"securityOfficerElectronicSignature\" value=\"ref:securityOfficerElectronicSignature\"/>\n" + 
			"    <Arg name=\"setPreviousApprovalDecisions\" value=\"ref:setPreviousApprovalDecisions\"/>\n" + 
			"    <Arg name=\"source\" value=\"ref:source\"/>\n" + 
			"    <Arg name=\"splitProvisioning\" value=\"false\"/>\n" + 
			"    <Arg name=\"ticketManagementApplication\" value=\"ref:ticketManagementApplication\"/>\n" + 
			"    <Arg name=\"trace\" value=\"ref:trace\"/>\n" + 
			"    <Arg name=\"workItemReminderTemplate\" value=\"ref:workItemReminderTemplate\"/>\n" + 
			"    <Arg name=\"workItemHoursBetweenReminders\" value=\"ref:workItemHoursBetweenReminders\"/>\n" + 
			"    <Arg name=\"workItemMaxReminders\" value=\"ref:workItemMaxReminders\"/>\n" + 
			"    <Arg name=\"workItemEscalationTemplate\" value=\"ref:workItemEscalationTemplate\"/>\n" + 
			"    <Arg name=\"workItemHoursTillEscalation\" value=\"ref:workItemHoursTillEscalation\"/>\n" + 
			"    <Arg name=\"workItemEscalationRule\" value=\"ref:workItemEscalationRule\"/>\n" + 
			"    <Arg name=\"workItemComments\" value=\"ref:workItemComments\"/>\n" + 
			"    <Description>\n" + 
			"          Finish any remaining approvals and provision.\n" + 
			"      </Description>\n" + 
			"    <Return name=\"project\" to=\"project\"/>\n" + 
			"    <Return name=\"approvalSet\" to=\"approvalSet\"/>\n" + 
			"    <Return name=\"workItemComments\" to=\"workItemComments\"/>\n" + 
			"    <WorkflowRef>\n" + 
			"      <Reference class=\"sailpoint.object.Workflow\" id=\"ff8081815aa414bc015aa41525f50045\" name=\"Approve and Provision Subprocess\"/>\n" + 
			"    </WorkflowRef>\n" + 
			"    <Transition to=\"Refresh Identity\"/>\n" + 
			"  </Step>\n" + 
			"  <Step action=\"call:refreshIdentity\" condition=\"ref:doRefresh\" icon=\"Task\" name=\"Refresh Identity\" posX=\"1028\" posY=\"7\">\n" + 
			"    <Arg name=\"identityName\" value=\"ref:identityName\"/>\n" + 
			"    <Arg name=\"correlateEntitlements\" value=\"true\"/>\n" + 
			"    <Description>\n" + 
			"      Add arguments as necessary to enable refresh features.  Typically you\n" + 
			"      only want this to correlate roles.  Don't ask for provisioning  since that\n" + 
			"      can result in provisioning policies that need to be presented and it's\n" + 
			"      too late for that.  This is only to get role detection and exception\n" + 
			"      entitlements in the cube.\n" + 
			"    </Description>\n" + 
			"    <Transition to=\"Notify\"/>\n" + 
			"  </Step>\n" + 
			"  <Step icon=\"Task\" name=\"Notify\" posX=\"1131\" posY=\"7\">\n" + 
			"    <Arg name=\"approvalSet\" value=\"ref:approvalSet\"/>\n" + 
			"    <Arg name=\"approvalScheme\" value=\"ref:approvalScheme\"/>\n" + 
			"    <Arg name=\"flow\" value=\"ref:flow\"/>\n" + 
			"    <Arg name=\"identityDisplayName\" value=\"ref:identityDisplayName\"/>\n" + 
			"    <Arg name=\"identityName\" value=\"ref:identityName\"/>\n" + 
			"    <Arg name=\"launcher\" value=\"ref:launcher\"/>\n" + 
			"    <Arg name=\"notificationScheme\" value=\"ref:notificationScheme\"/>\n" + 
			"    <Arg name=\"userEmailTemplate\" value=\"ref:userEmailTemplate\"/>\n" + 
			"    <Arg name=\"requesterEmailTemplate\" value=\"ref:requesterEmailTemplate\"/>\n" + 
			"    <Arg name=\"managerEmailTemplate\" value=\"ref:managerEmailTemplate\"/>\n" + 
			"    <Arg name=\"plan\" value=\"ref:plan\"/>\n" + 
			"    <Arg name=\"policyViolations\" value=\"ref:policyViolations\"/>\n" + 
			"    <Arg name=\"priority\" value=\"ref:workItemPriority\"/>\n" + 
			"    <Arg name=\"securityOfficerEmailTemplate\" value=\"ref:securityOfficerEmailTemplate\"/>\n" + 
			"    <Arg name=\"securityOfficerName\" value=\"ref:securityOfficerName\"/>\n" + 
			"    <Arg name=\"trace\" value=\"ref:trace\"/>\n" + 
			"    <Arg name=\"source\" value=\"ref:source\"/>\n" + 
			"    <Description>\n" + 
			"      Call the standard subprocess that will notify the various\n" + 
			"      actors based on notification scheme.\n" + 
			"    </Description>\n" + 
			"    <WorkflowRef>\n" + 
			"      <Reference class=\"sailpoint.object.Workflow\" id=\"ff8081815aa414bc015aa415222d0041\" name=\"Identity Request Notify\"/>\n" + 
			"    </WorkflowRef>\n" + 
			"    <Transition to=\"end\"/>\n" + 
			"  </Step>\n" + 
			"  <Step action=\"call:addMessage\" name=\"Exit On Policy Violation\" posX=\"201\" posY=\"253\">\n" + 
			"    <Arg name=\"message\" value=\"Failed due to policy violation(s)\"/>\n" + 
			"    <Arg name=\"type\" value=\"Error\"/>\n" + 
			"    <Transition to=\"end\"/>\n" + 
			"  </Step>\n" + 
			"  <Step action=\"call:addMessage\" name=\"Exit On Manual Work Items\" posX=\"364\" posY=\"107\">\n" + 
			"    <Arg name=\"message\" value=\"Failed due to manual work item(s)\"/>\n" + 
			"    <Arg name=\"type\" value=\"Error\"/>\n" + 
			"    <Transition to=\"end\"/>\n" + 
			"  </Step>\n" + 
			"  <Step action=\"call:addMessage\" name=\"Exit On Provisioning Form\" posX=\"280\" posY=\"178\">\n" + 
			"    <Arg name=\"message\" value=\"Failed due to provisioning form\"/>\n" + 
			"    <Arg name=\"type\" value=\"Error\"/>\n" + 
			"    <Transition to=\"end\"/>\n" + 
			"  </Step>\n" + 
			"  <Step catches=\"complete\" icon=\"Catches\" name=\"Finalize\" posX=\"1058\" posY=\"308\">\n" + 
			"    <Arg name=\"approvalSet\" value=\"ref:approvalSet\"/>\n" + 
			"    <Arg name=\"batchRequestItemId\" value=\"ref:batchRequestItemId\"/>\n" + 
			"    <Arg name=\"identityRequestId\" value=\"ref:identityRequestId\"/>\n" + 
			"    <Arg name=\"project\" value=\"ref:project\"/>\n" + 
			"    <Arg name=\"priority\" value=\"ref:workItemPriority\"/>\n" + 
			"    <Arg name=\"ticketManagementApplication\" value=\"ref:ticketManagementApplication\"/>\n" + 
			"    <Arg name=\"trace\" value=\"ref:trace\"/>\n" + 
			"    <Description>\n" + 
			"      Call the standard subprocess that can audit/finalize the request.\n" + 
			"    </Description>\n" + 
			"    <WorkflowRef>\n" + 
			"      <Reference class=\"sailpoint.object.Workflow\" id=\"ff8081815aa414bc015aa41523b10043\" name=\"Identity Request Finalize\"/>\n" + 
			"    </WorkflowRef>\n" + 
			"  </Step>\n" + 
			"  <Step icon=\"Stop\" name=\"end\" posX=\"1131\" posY=\"253\"/>\n" + 
			"</Workflow>\n" + 
			"";

	@Test
	public void controlDiff() {
		
		//test to prove there's a difference in both xmls
		Diff myDiff = DiffBuilder.compare(Input.fromString(clean))
		              .withTest(Input.fromString(diff))
		              .build();

		Assert.assertFalse(myDiff.toString(), myDiff.hasDifferences());
	}
	
	@Test
	public void ignoreNode() {
		
		//test to ignore Application node in xmls to prove there similar
		Diff myDiff2 = DiffBuilder.compare(Input.fromString(clean))                
	            .withTest(Input.fromString(diff))
	            .ignoreComments()
	            .ignoreWhitespace()                 
	            .withNodeFilter(node -> !node.getNodeName().equals("Workflow"))
	            .build();

		Assert.assertFalse(myDiff2.toString(), myDiff2.hasDifferences());
		
	}
	
	@Test
	public void ignoreAttribute() {
		//test shows how to ignore one attribute
				Diff myDiff4 = DiffBuilder.compare(Input.fromString(clean))                
			            .withTest(Input.fromString(diff))
			            .ignoreComments()
			            .ignoreWhitespace()                 
			            .withAttributeFilter(a -> !"created".equals(a.getName()))
			            .build();

				Assert.assertFalse(myDiff4.toString(), myDiff4.hasDifferences());
		
	}
	
	@Test
	public void ignoreAttributesAndNodeOrder() {
		// test that ignores specific attributes in order to pass but diff.xml has aggregated which includes extra map keys to ignore
				Diff myDiff3 = DiffBuilder.compare(Input.fromString(clean))                
			            .withTest(Input.fromString(diff))
			            .ignoreComments()
			            .ignoreWhitespace()
			            .checkForSimilar()
			            //need to ignore aggregate keys to pass test
			            .withAttributeFilter(a -> !("created".equals(a.getName()) || "id".equals(a.getName()) || "modified".equals(a.getName()) ))
			            .withNodeMatcher(new DefaultNodeMatcher(ElementSelectors.byNameAndAllAttributes,ElementSelectors.byNameAndText))
			            .build();

				Assert.assertFalse(myDiff3.toString(), myDiff3.hasDifferences());
						
	}
	
	
	@Test
	public void IgnoreAttDiffEvaluator() {
		//test that creates a class to customise what one calls a similar result
		Diff myDiff5 = DiffBuilder.compare(Input.fromString(clean))                
	            .withTest(Input.fromString(diff))
	            .ignoreComments()
	            .ignoreWhitespace()
	            .withAttributeFilter(a -> !("created".equals(a.getName()) || "id".equals(a.getName()) || "modified".equals(a.getName()) ))	       
	            .build();

		Assert.assertFalse(myDiff5.toString(), myDiff5.hasDifferences());
		
	}
	
	
	

}
