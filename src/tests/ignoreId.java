package tests;

import org.junit.Assert;
import org.junit.Test;
import org.xmlunit.builder.DiffBuilder;
import org.xmlunit.builder.Input;
import org.xmlunit.diff.ComparisonResult;
import org.xmlunit.diff.ComparisonType;
import org.xmlunit.diff.DefaultNodeMatcher;
import org.xmlunit.diff.Diff;
import org.xmlunit.diff.ElementSelectors;


public class ignoreId {
	
	final String clean = "<?xml version='1.0' encoding='UTF-8'?>\n" + 
			"<!DOCTYPE Rule PUBLIC \"sailpoint.dtd\" \"sailpoint.dtd\">\n" + 
			"<Rule created=\"1488811500205\" id=\"ff8081815aa41390015aa414a2ad02ad\" language=\"beanshell\" name=\"Task Completion Email Rule\" type=\"TaskCompletion\">\n" + 
			"  <Description>\n" + 
			"      Example rule to perform Post Action after Task Completion\n" + 
			"    </Description>\n" + 
			"  <Signature>\n" + 
			"    <Inputs>\n" + 
			"      <Argument name=\"context\">\n" + 
			"        <Description>\n" + 
			"            A sailpoint.api.SailPointContext object \n" + 
			"          </Description>\n" + 
			"      </Argument>\n" + 
			"      <Argument name=\"log\">\n" + 
			"        <Description>\n" + 
			"            The log object associated with the SailPointContext.\n" + 
			"          </Description>\n" + 
			"      </Argument>\n" + 
			"      <Argument name=\"result\">\n" + 
			"        <Description>\n" + 
			"            result of task.\n" + 
			"          </Description>\n" + 
			"      </Argument>\n" + 
			"    </Inputs>\n" + 
			"  </Signature>\n" + 
			"  <Source>\n" + 
			"      \n" + 
			"          import java.util.*;\n" + 
			"          import sailpoint.tools.Util;\n" + 
			"          import sailpoint.tools.GeneralException;\n" + 
			"          import sailpoint.object.Configuration;\n" + 
			"          import sailpoint.object.EmailOptions;\n" + 
			"          import sailpoint.object.EmailTemplate;\n" + 
			"          import sailpoint.object.TaskResult;\n" + 
			"          import sailpoint.object.Identity;\n" + 
			"          import sailpoint.object.TaskDefinition;\n" + 
			"          import sailpoint.api.MessageRepository;\n" + 
			"          import sailpoint.api.Emailer;\n" + 
			"          import sailpoint.api.BasicMessageRepository;\n" + 
			"          import sailpoint.api.ObjectUtil;\n" + 
			"          import sailpoint.api.SailPointContext;\n" + 
			"\n" + 
			"          MessageRepository _errorHandler;\n" + 
			"\n" + 
			"          /**\n" + 
			"           * Method to send email\n" + 
			"          */\n" + 
			"          private void sendEmailOnTaskCompletion(String emailTemplate, ArrayList recipients, TaskResult result, SailPointContext context) {\n" + 
			"              String message = \"\";\n" + 
			"              String status = \"\";\n" + 
			"              TaskDefinition def;\n" + 
			"              Configuration sysConfig;\n" + 
			"\n" + 
			"              def = result.getDefinition();\n" + 
			"              EmailTemplate notifyEmail =  context.getObjectByName(EmailTemplate.class, emailTemplate);\n" + 
			"              if (null == notifyEmail) {\n" + 
			"                  log.error (\"From Task Completion Email Rule: ERROR: could not find email template [ \" + emailTemplate + \"]\");\n" + 
			"                  return;\n" + 
			"              }\n" + 
			"              notifyEmail = (EmailTemplate) notifyEmail.deepCopy(context);\n" + 
			"              if (null == notifyEmail) {\n" + 
			"                  log.error (\"From Task Completion Email Rule: ERROR: failed to deepCopy template [ \" + emailTemplate + \"]\");\n" + 
			"                  return;\n" + 
			"              }\n" + 
			"              // For now, we'll just use a map with a few pre-selected properties.\n" + 
			"              Map mArgs = new HashMap();\n" + 
			" \n" + 
			"              mArgs.put(\"taskResult\", result);\n" + 
			"              mArgs.put(\"taskName\", def.getName());\n" + 
			"              mArgs.put(\"taskDesc\", def.getDescription());\n" + 
			"              if (result.isError()) {\n" + 
			"                  status = \"Error\";\n" + 
			"              }\n" + 
			"              else  if (result.isWarning()) {\n" + 
			"                  status = \"Warning\";\n" + 
			"              }\n" + 
			"              else if (result.isSuccess()) {\n" + 
			"                  status = \"Success\";\n" + 
			"              }\n" + 
			" \n" + 
			"              mArgs.put(\"taskStartTime\", result.getLaunched() );\n" + 
			"              mArgs.put(\"taskEndTime\", result.getCompleted() );\n" + 
			"              mArgs.put(\"status\", status);\n" + 
			"              if (result.getMessages() != null) {\n" + 
			"                  mArgs.put(\"message\", result.getMessages());\n" + 
			"              }\n" + 
			"              mArgs.put (\"resultId\", result.getId());\n" + 
			"\n" + 
			"              EmailOptions ops = new EmailOptions(recipients, mArgs);\n" + 
			"              new Emailer(context, _errorHandler).sendEmailNotification(notifyEmail , ops);\n" + 
			"          }\n" + 
			"\n" + 
			"          private boolean isEmailNotificationEnabled(TaskResult result, Configuration sysConfig) {\n" + 
			"              boolean sendEmail = false;\n" + 
			"              String notifyStr = null;\n" + 
			"\n" + 
			"              TaskDefinition def = result.getDefinition();\n" + 
			"              notifyStr = (String) def.getString(Configuration.TASK_COMPLETION_EMAIL_NOTIFY);\n" + 
			"              if (notifyStr == null) {\n" + 
			"                  notifyStr = sysConfig.getString(Configuration.TASK_COMPLETION_EMAIL_NOTIFY);\n" + 
			"              }\n" + 
			"\n" + 
			"              if (notifyStr != null) {\n" + 
			"                  if (notifyStr.equals(\"Always\") ||\n" + 
			"                      ((notifyStr.equals(\"Failure\")) &amp;&amp; result.isError()) || \n" + 
			"                      ((notifyStr.equals(\"Warning\")) &amp;&amp;\n" + 
			"                       (result.isWarning() || result.isError()))) {\n" + 
			"                      sendEmail = true;\n" + 
			"                  }\n" + 
			"              }\n" + 
			"                      \n" + 
			"              return sendEmail;\n" + 
			"          }\n" + 
			"\n" + 
			"          private Object getIdentityNames(TaskResult result, Configuration sysConfig) {\n" + 
			"              \n" + 
			"              TaskDefinition def = result.getDefinition();\n" + 
			"              Object identityNames = def.getArgument(Configuration.TASK_COMPLETION_RECIPIENTS);\n" + 
			"              if (identityNames == null) {\n" + 
			"                  identityNames = sysConfig.get(Configuration.TASK_COMPLETION_RECIPIENTS);\n" + 
			"              }\n" + 
			"              return identityNames;\n" + 
			"          }\n" + 
			"\n" + 
			"          private String getEmailTemplate(TaskResult result, Configuration sysConfig) {\n" + 
			"              TaskDefinition def = result.getDefinition();\n" + 
			"              String emailTemplate = def.getString(Configuration.TASK_COMPLETION_EMAIL_TEMPLATE);\n" + 
			"              if (emailTemplate == null) {\n" + 
			"                  emailTemplate = sysConfig.getString(Configuration.TASK_COMPLETION_EMAIL_TEMPLATE);\n" + 
			"                  if (emailTemplate == null)\n" + 
			"                      emailTemplate = Configuration.DEFAULT_TASK_COMPLETION_EMAIL_TEMPLATE;\n" + 
			"              }\n" + 
			"              return emailTemplate;\n" + 
			"          }\n" + 
			"\n" + 
			"          private List getEmailAddress (String identityName, SailPointContext context) {\n" + 
			"              Identity identity = context.getObjectByName(Identity.class, identityName);\n" + 
			"              if (identity != null) \n" + 
			"              {\n" + 
			"                  List addresses = ObjectUtil.getEffectiveEmails(context, identity);\n" + 
			"                  if (!Util.isEmpty(addresses)) {\n" + 
			"                      return(addresses);\n" + 
			"                  }\n" + 
			"                  else\n" + 
			"                  {\n" + 
			"                     if(log.isWarnEnabled()) {\n" + 
			"                         log.warn(\"From Task Completion Email Rule: Missing Email Address for Email Recipient: \" + identityName );\n" + 
			"                     }\n" + 
			"                  }\n" + 
			"              }\n" + 
			"              return (null);\n" + 
			"          }\n" + 
			"\n" + 
			"          private ArrayList getEmailRecipient (Object identityNames, SailPointContext context) {\n" + 
			"              List recipients;\n" + 
			"              String val = null;\n" + 
			"              StringTokenizer st = null;\n" + 
			"              if (identityNames != null) {\n" + 
			"                  recipients = new ArrayList ();\n" + 
			"                  // From Task definition, single identity\n" + 
			"                  if (identityNames instanceof String  &amp;&amp; !identityNames.contains(\",\")) {\n" + 
			"                      List addresses = getEmailAddress (identityNames.toString(), context);\n" + 
			"                      if (addresses != null) {\n" + 
			"                         recipients.addAll (addresses);\n" + 
			"                      }\n" + 
			"                  }\n" + 
			"                  // From Task definition, multiple identities\n" + 
			"                  else if (identityNames instanceof String  &amp;&amp; identityNames.contains(\",\") == true) {\n" + 
			"                      List nameList = Util.csvToList(identityNames);\n" + 
			"                      for (String identityName : nameList) {\n" + 
			"                          List addresses = getEmailAddress (identityName, context);\n" + 
			"                          if (addresses != null) {\n" + 
			"                              recipients.addAll (addresses);\n" + 
			"                          }\n" + 
			"                      }  \n" + 
			"                  } \n" + 
			"                  // From system configuration single or multiple identities it comes as list\n" + 
			"                  else if (identityNames instanceof List) {\n" + 
			"                      for (String identityName : identityNames) {\n" + 
			"                          List addresses = getEmailAddress (identityName, context);\n" + 
			"                          if (addresses != null) {\n" + 
			"                              recipients.addAll(getEmailAddress (identityName, context));\n" + 
			"                          }\n" + 
			"                      }\n" + 
			"                  }\n" + 
			"              }\n" + 
			"              return (recipients);\n" + 
			"          }\n" + 
			"\n" + 
			"          // Main\n" + 
			"          Configuration sysConfig = context.getConfiguration();\n" + 
			"          boolean sendEmailNotify = isEmailNotificationEnabled(result, sysConfig);\n" + 
			"    \n" + 
			"          if (sendEmailNotify) {\n" + 
			"              // jsl - why consturct this here, just make it in\n" + 
			"              // sendEmailOnTaskCompletion where it is used?\n" + 
			"              // why do this at all since no one consumes it?\n" + 
			"              _errorHandler = new BasicMessageRepository();\n" + 
			"\n" + 
			"              Object identityNames = getIdentityNames(result, sysConfig);\n" + 
			"              String emailTemplate = getEmailTemplate(result, sysConfig);\n" + 
			"              List recipients = getEmailRecipient(identityNames, context);\n" + 
			"\n" + 
			"              if (recipients != null &amp;&amp; !Util.isEmpty(recipients)) {\n" + 
			"                    // Send Email\n" + 
			"                   sendEmailOnTaskCompletion(emailTemplate, recipients, result, context);\n" + 
			"              }\n" + 
			"              else {\n" + 
			"                  if(log.isWarnEnabled()) {\n" + 
			"                      log.warn(\"From Task Completion Email Rule: Cannot send task completion email Notification. Reason : Missing Email Address for Email Recipients\");\n" + 
			"                  }\n" + 
			"              }\n" + 
			"          }\n" + 
			"    \n" + 
			"    </Source>\n" + 
			"</Rule>\n" + 
			"";
	
	
	final String diff = "<?xml version='1.0' encoding='UTF-8'?>\n" + 
			"<!DOCTYPE Rule PUBLIC \"sailpoint.dtd\" \"sailpoint.dtd\">\n" + 
			"<Rule created=\"1488793225398\" id=\"ff8081815aa2fc29015aa2fdc8b602ad\" language=\"beanshell\" modified=\"1490623944774\" name=\"Task Completion Email Rule\" type=\"TaskCompletion\">\n" + 
			"  <Description>\n" + 
			"      Example rule to perform Post Action after Task Completion\n" + 
			"    </Description>\n" + 
			"  <Signature>\n" + 
			"    <Inputs>\n" + 
			"      <Argument name=\"context\">\n" + 
			"        <Description>\n" + 
			"            A sailpoint.api.SailPointContext object \n" + 
			"          </Description>\n" + 
			"      </Argument>\n" + 
			"      <Argument name=\"log\">\n" + 
			"        <Description>\n" + 
			"            The log object associated with the SailPointContext.\n" + 
			"          </Description>\n" + 
			"      </Argument>\n" + 
			"      <Argument name=\"result\">\n" + 
			"        <Description>\n" + 
			"            result of task.\n" + 
			"          </Description>\n" + 
			"      </Argument>\n" + 
			"    </Inputs>\n" + 
			"  </Signature>\n" + 
			"  <Source>\n" + 
			"      \n" + 
			"          import java.util.*;\n" + 
			"          import sailpoint.tools.Util;\n" + 
			"          import sailpoint.tools.GeneralException;\n" + 
			"          import sailpoint.object.Configuration;\n" + 
			"          import sailpoint.object.EmailOptions;\n" + 
			"          import sailpoint.object.EmailTemplate;\n" + 
			"          import sailpoint.object.TaskResult;\n" + 
			"          import sailpoint.object.Identity;\n" + 
			"          import sailpoint.object.TaskDefinition;\n" + 
			"          import sailpoint.api.MessageRepository;\n" + 
			"          import sailpoint.api.Emailer;\n" + 
			"          import sailpoint.api.BasicMessageRepository;\n" + 
			"          import sailpoint.api.ObjectUtil;\n" + 
			"          import sailpoint.api.SailPointContext;\n" + 
			"\n" + 
			"          MessageRepository _errorHandler;\n" + 
			"\n" + 
			"          /**\n" + 
			"           * Method to send email\n" + 
			"          */\n" + 
			"          private void sendEmailOnTaskCompletion(String emailTemplate, ArrayList recipients, TaskResult result, SailPointContext context) {\n" + 
			"              String message = \"\";\n" + 
			"              String status = \"\";\n" + 
			"              TaskDefinition def;\n" + 
			"              Configuration sysConfig;\n" + 
			"\n" + 
			"              def = result.getDefinition();\n" + 
			"              EmailTemplate notifyEmail =  context.getObjectByName(EmailTemplate.class, emailTemplate);\n" + 
			"              if (null == notifyEmail) {\n" + 
			"                  log.error (\"From Task Completion Email Rule: ERROR: could not find email template [ \" + emailTemplate + \"]\");\n" + 
			"                  return;\n" + 
			"              }\n" + 
			"              notifyEmail = (EmailTemplate) notifyEmail.deepCopy(context);\n" + 
			"              if (null == notifyEmail) {\n" + 
			"                  log.error (\"From Task Completion Email Rule: ERROR: failed to deepCopy template [ \" + emailTemplate + \"]\");\n" + 
			"                  return;\n" + 
			"              }\n" + 
			"              // For now, we'll just use a map with a few pre-selected properties.\n" + 
			"              Map mArgs = new HashMap();\n" + 
			" \n" + 
			"              mArgs.put(\"taskResult\", result);\n" + 
			"              mArgs.put(\"taskName\", def.getName());\n" + 
			"              mArgs.put(\"taskDesc\", def.getDescription());\n" + 
			"              if (result.isError()) {\n" + 
			"                  status = \"Error\";\n" + 
			"              }\n" + 
			"              else  if (result.isWarning()) {\n" + 
			"                  status = \"Warning\";\n" + 
			"              }\n" + 
			"              else if (result.isSuccess()) {\n" + 
			"                  status = \"Success\";\n" + 
			"              }\n" + 
			" \n" + 
			"              mArgs.put(\"taskStartTime\", result.getLaunched() );\n" + 
			"              mArgs.put(\"taskEndTime\", result.getCompleted() );\n" + 
			"              mArgs.put(\"status\", status);\n" + 
			"              if (result.getMessages() != null) {\n" + 
			"                  mArgs.put(\"message\", result.getMessages());\n" + 
			"              }\n" + 
			"              mArgs.put (\"resultId\", result.getId());\n" + 
			"\n" + 
			"              EmailOptions ops = new EmailOptions(recipients, mArgs);\n" + 
			"              new Emailer(context, _errorHandler).sendEmailNotification(notifyEmail , ops);\n" + 
			"          }\n" + 
			"\n" + 
			"          private boolean isEmailNotificationEnabled(TaskResult result, Configuration sysConfig) {\n" + 
			"              boolean sendEmail = false;\n" + 
			"              String notifyStr = null;\n" + 
			"\n" + 
			"              TaskDefinition def = result.getDefinition();\n" + 
			"              notifyStr = (String) def.getString(Configuration.TASK_COMPLETION_EMAIL_NOTIFY);\n" + 
			"              if (notifyStr == null) {\n" + 
			"                  notifyStr = sysConfig.getString(Configuration.TASK_COMPLETION_EMAIL_NOTIFY);\n" + 
			"              }\n" + 
			"\n" + 
			"              if (notifyStr != null) {\n" + 
			"                  if (notifyStr.equals(\"Always\") ||\n" + 
			"                      ((notifyStr.equals(\"Failure\")) &amp;&amp; result.isError()) || \n" + 
			"                      ((notifyStr.equals(\"Warning\")) &amp;&amp;\n" + 
			"                       (result.isWarning() || result.isError()))) {\n" + 
			"                      sendEmail = true;\n" + 
			"                  }\n" + 
			"              }\n" + 
			"                      \n" + 
			"              return sendEmail;\n" + 
			"          }\n" + 
			"\n" + 
			"          private Object getIdentityNames(TaskResult result, Configuration sysConfig) {\n" + 
			"              \n" + 
			"              TaskDefinition def = result.getDefinition();\n" + 
			"              Object identityNames = def.getArgument(Configuration.TASK_COMPLETION_RECIPIENTS);\n" + 
			"              if (identityNames == null) {\n" + 
			"                  identityNames = sysConfig.get(Configuration.TASK_COMPLETION_RECIPIENTS);\n" + 
			"              }\n" + 
			"              return identityNames;\n" + 
			"          }\n" + 
			"\n" + 
			"          private String getEmailTemplate(TaskResult result, Configuration sysConfig) {\n" + 
			"              TaskDefinition def = result.getDefinition();\n" + 
			"              String emailTemplate = def.getString(Configuration.TASK_COMPLETION_EMAIL_TEMPLATE);\n" + 
			"              if (emailTemplate == null) {\n" + 
			"                  emailTemplate = sysConfig.getString(Configuration.TASK_COMPLETION_EMAIL_TEMPLATE);\n" + 
			"                  if (emailTemplate == null)\n" + 
			"                      emailTemplate = Configuration.DEFAULT_TASK_COMPLETION_EMAIL_TEMPLATE;\n" + 
			"              }\n" + 
			"              return emailTemplate;\n" + 
			"          }\n" + 
			"\n" + 
			"          private List getEmailAddress (String identityName, SailPointContext context) {\n" + 
			"              Identity identity = context.getObjectByName(Identity.class, identityName);\n" + 
			"              if (identity != null) \n" + 
			"              {\n" + 
			"                  List addresses = ObjectUtil.getEffectiveEmails(context, identity);\n" + 
			"                  if (!Util.isEmpty(addresses)) {\n" + 
			"                      return(addresses);\n" + 
			"                  }\n" + 
			"                  else\n" + 
			"                  {\n" + 
			"                     if(log.isWarnEnabled()) {\n" + 
			"                         log.warn(\"From Task Completion Email Rule: Missing Email Address for Email Recipient: \" + identityName );\n" + 
			"                     }\n" + 
			"                  }\n" + 
			"              }\n" + 
			"              return (null);\n" + 
			"          }\n" + 
			"\n" + 
			"          private ArrayList getEmailRecipient (Object identityNames, SailPointContext context) {\n" + 
			"              List recipients;\n" + 
			"              String val = null;\n" + 
			"              StringTokenizer st = null;\n" + 
			"              if (identityNames != null) {\n" + 
			"                  recipients = new ArrayList ();\n" + 
			"                  // From Task definition, single identity\n" + 
			"                  if (identityNames instanceof String  &amp;&amp; !identityNames.contains(\",\")) {\n" + 
			"                      List addresses = getEmailAddress (identityNames.toString(), context);\n" + 
			"                      if (addresses != null) {\n" + 
			"                         recipients.addAll (addresses);\n" + 
			"                      }\n" + 
			"                  }\n" + 
			"                  // From Task definition, multiple identities\n" + 
			"                  else if (identityNames instanceof String  &amp;&amp; identityNames.contains(\",\") == true) {\n" + 
			"                      List nameList = Util.csvToList(identityNames);\n" + 
			"                      for (String identityName : nameList) {\n" + 
			"                          List addresses = getEmailAddress (identityName, context);\n" + 
			"                          if (addresses != null) {\n" + 
			"                              recipients.addAll (addresses);\n" + 
			"                          }\n" + 
			"                      }  \n" + 
			"                  } \n" + 
			"                  // From system configuration single or multiple identities it comes as list\n" + 
			"                  else if (identityNames instanceof List) {\n" + 
			"                      for (String identityName : identityNames) {\n" + 
			"                          List addresses = getEmailAddress (identityName, context);\n" + 
			"                          if (addresses != null) {\n" + 
			"                              recipients.addAll(getEmailAddress (identityName, context));\n" + 
			"                          }\n" + 
			"                      }\n" + 
			"                  }\n" + 
			"              }\n" + 
			"              return (recipients);\n" + 
			"          }\n" + 
			"\n" + 
			"          // Main\n" + 
			"          Configuration sysConfig = context.getConfiguration();\n" + 
			"          boolean sendEmailNotify = isEmailNotificationEnabled(result, sysConfig);\n" + 
			"    \n" + 
			"          if (sendEmailNotify) {\n" + 
			"              // jsl - why consturct this here, just make it in\n" + 
			"              // sendEmailOnTaskCompletion where it is used?\n" + 
			"              // why do this at all since no one consumes it?\n" + 
			"              _errorHandler = new BasicMessageRepository();\n" + 
			"\n" + 
			"              Object identityNames = getIdentityNames(result, sysConfig);\n" + 
			"              String emailTemplate = getEmailTemplate(result, sysConfig);\n" + 
			"              List recipients = getEmailRecipient(identityNames, context);\n" + 
			"\n" + 
			"              if (recipients != null &amp;&amp; !Util.isEmpty(recipients)) {\n" + 
			"                    // Send Email\n" + 
			"                   sendEmailOnTaskCompletion(emailTemplate, recipients, result, context);\n" + 
			"              }\n" + 
			"              else {\n" + 
			"                  if(log.isWarnEnabled()) {\n" + 
			"                      log.warn(\"From Task Completion Email Rule: Cannot send task completion email Notification. Reason : Missing Email Address for Email Recipients\");\n" + 
			"                  }\n" + 
			"              }\n" + 
			"          }\n" + 
			"    \n" + 
			"    </Source>\n" + 
			"</Rule>\n" + 
			"";

	@Test
	public void controlDiff() {
		
		//test to prove there's a difference in both xmls
		Diff myDiff = DiffBuilder.compare(Input.fromString(clean))
		              .withTest(Input.fromString(diff))
		              .checkForSimilar()
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
	            .withNodeFilter(node -> !node.getNodeName().equals("Rule"))
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
	public void ignoreAttributes() {
		// test that ignores specific attributes in order to pass but diff.xml has aggregated which includes extra map keys to ignore
				Diff myDiff3 = DiffBuilder.compare(Input.fromString(clean))                
			            .withTest(Input.fromString(diff))
			            .ignoreComments()
			            .ignoreWhitespace() 
			            .checkForSimilar()
			            .normalizeWhitespace()
			            //need to ignore aggregate keys to pass test
			            .withAttributeFilter(a -> !("created".equals(a.getName()) || "id".equals(a.getName()) || "modified".equals(a.getName()) ))
			            .withNodeMatcher(new DefaultNodeMatcher(ElementSelectors.byNameAndAllAttributes,ElementSelectors.Default))
			            .withDifferenceEvaluator(((comparison, outcome) -> {
			                if (outcome == ComparisonResult.DIFFERENT && 
			                    comparison.getType() == ComparisonType.CHILD_NODELIST_SEQUENCE) {
			                       return ComparisonResult.EQUAL;
			                }

			                return outcome;
			            }))
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
	            .withAttributeFilter(a -> !("created".equals(a.getName()) || "id".equals(a.getName()) || "modified".equals(a.getName()) )
)	         
	            .build();

		Assert.assertFalse(myDiff5.toString(), myDiff5.hasDifferences());
		
	}
	
	
	

}
