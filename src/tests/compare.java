package tests;

import org.junit.Assert;
import org.junit.Test;
import org.xmlunit.builder.DiffBuilder;
import org.xmlunit.builder.Input;
import org.xmlunit.diff.Diff;


public class compare {
	
	final String clean = "<?xml version='1.0' encoding='UTF-8'?>\n" + 
			"<!DOCTYPE Application PUBLIC \"sailpoint.dtd\" \"sailpoint.dtd\">\n" + 
			"<Application authoritative=\"true\" connector=\"sailpoint.connector.JDBCConnector\" created=\"1488811621803\" featuresString=\"DISCOVER_SCHEMA, PROVISIONING, GROUP_PROVISIONING, SYNC_PROVISIONING\" icon=\"databaseIcon\" id=\"ff8081815aa4157b015aa4167daa001f\" modified=\"1488811640523\" name=\"Human Resources\" profileClass=\"\" type=\"JDBC\">\n" + 
			"  <Attributes>\n" + 
			"    <Map>\n" + 
			"      <entry key=\"SQL\" value=\"select emp.employee_id as empid, emp.emp_firstname as firstname, emp.emp_lastname as lastname, emp.emp_work_email as email,&#xD;&#xA; job.job_title as title, loc.country_code as country, loc.city, mgr.employee_id as mgrId, stat.name as status,&#xD;&#xA; concat(emp.emp_firstname, &apos;.&apos;, emp.emp_lastname) as userid,&#xD;&#xA; dept.name as department,&#xD;&#xA; CASE WHEN emp.termination_id IS NOT NULL or stat.name like &apos;Inactive%&apos; THEN True ELSE False END as term,&#xD;&#xA; ftostart, ftoend&#xD;&#xA; &#xD;&#xA; from hs_hr_employee as emp&#xD;&#xA;&#xD;&#xA; left outer join (select ohrm_leave.emp_number, min(ohrm_leave.date)&#xD;&#xA;  as ftostart, max(ohrm_leave.date) as ftoend&#xD;&#xA;  from ohrm_leave group by ohrm_leave.emp_number) as fto&#xD;&#xA; on fto.emp_number=emp.emp_number&#xD;&#xA; left outer join ohrm_job_title as job&#xD;&#xA; on emp.job_title_code=job.id &#xD;&#xA; left outer join hs_hr_emp_reportto as empmgr&#xD;&#xA; on emp.emp_number=empmgr.erep_sub_emp_number&#xD;&#xA; left outer join hs_hr_employee as mgr&#xD;&#xA; on empmgr.erep_sup_emp_number=mgr.emp_number&#xD;&#xA; left outer join hs_hr_emp_locations as emploc&#xD;&#xA; on emp.emp_number=emploc.emp_number&#xD;&#xA; left outer join ohrm_location as loc&#xD;&#xA; on emploc.location_id=loc.id&#xD;&#xA; left outer join ohrm_employment_status as stat&#xD;&#xA; on emp.emp_status=stat.id&#xD;&#xA; left outer join ohrm_subunit as dept&#xD;&#xA; on emp.work_station=dept.id&#xD;&#xA; left outer join ohrm_emp_termination as term&#xD;&#xA; on emp.emp_number=term.emp_number&#xD;&#xA;\"/>\n" + 
			"      <entry key=\"accountDeprovisionScenario\" value=\"Do Nothing\"/>\n" + 
			"      <entry key=\"accountLOAScenario\" value=\"Disable Account Immediately\"/>\n" + 
			"      <entry key=\"accountNoEntitlementsDeprovisioningPolicy\" value=\"Disable Account Immediately\"/>\n" + 
			"      <entry key=\"aggregationMode\">\n" + 
			"        <value>\n" + 
			"          <Boolean>true</Boolean>\n" + 
			"        </value>\n" + 
			"      </entry>\n" + 
			"      <entry key=\"compositeDefinition\"/>\n" + 
			"      <entry key=\"daysToWaitForDeletion\"/>\n" + 
			"      <entry key=\"daysToWaitForDeletionOnLossOfEntitlements\"/>\n" + 
			"      <entry key=\"deltaTable\" value=\"user_delta\"/>\n" + 
			"      <entry key=\"driverClass\" value=\"com.mysql.jdbc.Driver\"/>\n" + 
			"      <entry key=\"enableOnReactivation\">\n" + 
			"        <value>\n" + 
			"          <Boolean></Boolean>\n" + 
			"        </value>\n" + 
			"      </entry>\n" + 
			"      <entry key=\"getDeltaSQL\" value=\"select emp.employee_id as empid, emp.emp_firstname as firstname, emp.emp_lastname as lastname, emp.emp_work_email as email,&#xD;&#xA; job.job_title as title, loc.country_code as country, loc.city, mgr.employee_id as mgrId, stat.name as status,&#xD;&#xA; concat(emp.emp_firstname, &apos;.&apos;, emp.emp_lastname) as userid,&#xD;&#xA; dept.name as department,&#xD;&#xA; CASE WHEN emp.termination_id IS NOT NULL or stat.name like &apos;Inactive%&apos; THEN True ELSE False END as term,&#xD;&#xA; ftostart, ftoend&#xD;&#xA; &#xD;&#xA; from hs_hr_employee as emp&#xD;&#xA;&#xD;&#xA; left outer join (select ohrm_leave.emp_number, min(ohrm_leave.date)&#xD;&#xA;  as ftostart, max(ohrm_leave.date) as ftoend&#xD;&#xA;  from ohrm_leave group by ohrm_leave.emp_number) as fto&#xD;&#xA; on fto.emp_number=emp.emp_number&#xD;&#xA; left outer join ohrm_job_title as job&#xD;&#xA; on emp.job_title_code=job.id &#xD;&#xA; left outer join hs_hr_emp_reportto as empmgr&#xD;&#xA; on emp.emp_number=empmgr.erep_sub_emp_number&#xD;&#xA; left outer join hs_hr_employee as mgr&#xD;&#xA; on empmgr.erep_sup_emp_number=mgr.emp_number&#xD;&#xA; left outer join hs_hr_emp_locations as emploc&#xD;&#xA; on emp.emp_number=emploc.emp_number&#xD;&#xA; left outer join ohrm_location as loc&#xD;&#xA; on emploc.location_id=loc.id&#xD;&#xA; left outer join ohrm_employment_status as stat&#xD;&#xA; on emp.emp_status=stat.id&#xD;&#xA; left outer join ohrm_subunit as dept&#xD;&#xA; on emp.work_station=dept.id&#xD;&#xA; left outer join ohrm_emp_termination as term&#xD;&#xA; on emp.emp_number=term.emp_number&#xD;&#xA;where emp.employee_id=&apos;$(identity)&apos;\"/>\n" + 
			"      <entry key=\"getObjectSQL\" value=\"select emp.employee_id as empid, emp.emp_firstname as firstname, emp.emp_lastname as lastname, emp.emp_work_email as email,&#xD;&#xA; job.job_title as title, loc.country_code as country, loc.city, mgr.employee_id as mgrId, stat.name as status,&#xD;&#xA; concat(emp.emp_firstname, &apos;.&apos;, emp.emp_lastname) as userid,&#xD;&#xA; dept.name as department,&#xD;&#xA;  CASE WHEN emp.termination_id IS NOT NULL or stat.name like &apos;Inactive%&apos; THEN True ELSE False END as term,&#xD;&#xA; ftostart, ftoend&#xD;&#xA; &#xD;&#xA; from hs_hr_employee as emp&#xD;&#xA;&#xD;&#xA; left outer join (select ohrm_leave.emp_number, min(ohrm_leave.date)&#xD;&#xA;  as ftostart, max(ohrm_leave.date) as ftoend&#xD;&#xA;  from ohrm_leave group by ohrm_leave.emp_number) as fto&#xD;&#xA; on fto.emp_number=emp.emp_number&#xD;&#xA; left outer join ohrm_job_title as job&#xD;&#xA; on emp.job_title_code=job.id &#xD;&#xA; left outer join hs_hr_emp_reportto as empmgr&#xD;&#xA; on emp.emp_number=empmgr.erep_sub_emp_number&#xD;&#xA; left outer join hs_hr_employee as mgr&#xD;&#xA; on empmgr.erep_sup_emp_number=mgr.emp_number&#xD;&#xA; left outer join hs_hr_emp_locations as emploc&#xD;&#xA; on emp.emp_number=emploc.emp_number&#xD;&#xA; left outer join ohrm_location as loc&#xD;&#xA; on emploc.location_id=loc.id&#xD;&#xA; left outer join ohrm_employment_status as stat&#xD;&#xA; on emp.emp_status=stat.id&#xD;&#xA; left outer join ohrm_subunit as dept&#xD;&#xA; on emp.work_station=dept.id&#xD;&#xA; left outer join ohrm_emp_termination as term&#xD;&#xA; on emp.emp_number=term.emp_number&#xD;&#xA;&#xD;&#xA;where emp.emp_firstname=substring_index(&quot;$(identity)&quot;, &apos;.&apos;, 1)&#xD;&#xA;  and emp.emp_lastname=substring_index(&quot;$(identity)&quot;, &apos;.&apos;, -1)\"/>\n" + 
			"      <entry key=\"group.aggregationMode\">\n" + 
			"        <value>\n" + 
			"          <Boolean></Boolean>\n" + 
			"        </value>\n" + 
			"      </entry>\n" + 
			"      <entry key=\"group.isAdvance\">\n" + 
			"        <value>\n" + 
			"          <Boolean></Boolean>\n" + 
			"        </value>\n" + 
			"      </entry>\n" + 
			"      <entry key=\"group.isPermissionEnabled\">\n" + 
			"        <value>\n" + 
			"          <Boolean></Boolean>\n" + 
			"        </value>\n" + 
			"      </entry>\n" + 
			"      <entry key=\"group.mergeRows\">\n" + 
			"        <value>\n" + 
			"          <Boolean></Boolean>\n" + 
			"        </value>\n" + 
			"      </entry>\n" + 
			"      <entry key=\"group.useExecuteQuery\">\n" + 
			"        <value>\n" + 
			"          <Boolean></Boolean>\n" + 
			"        </value>\n" + 
			"      </entry>\n" + 
			"      <entry key=\"groupHierarchyAttribute\" value=\"Child Roles\"/>\n" + 
			"      <entry key=\"isAdvance\">\n" + 
			"        <value>\n" + 
			"          <Boolean></Boolean>\n" + 
			"        </value>\n" + 
			"      </entry>\n" + 
			"      <entry key=\"isPermissionEnabled\">\n" + 
			"        <value>\n" + 
			"          <Boolean></Boolean>\n" + 
			"        </value>\n" + 
			"      </entry>\n" + 
			"      <entry key=\"managerApproval\">\n" + 
			"        <value>\n" + 
			"          <Boolean></Boolean>\n" + 
			"        </value>\n" + 
			"      </entry>\n" + 
			"      <entry key=\"managerCorrelationFilter\">\n" + 
			"        <value>\n" + 
			"          <Filter operation=\"EQ\" property=\"employeeId\" value=\"mgrId\"/>\n" + 
			"        </value>\n" + 
			"      </entry>\n" + 
			"      <entry key=\"mergeRows\">\n" + 
			"        <value>\n" + 
			"          <Boolean></Boolean>\n" + 
			"        </value>\n" + 
			"      </entry>\n" + 
			"      <entry key=\"nativeChangeDetectionAttributeScope\" value=\"entitlements\"/>\n" + 
			"      <entry key=\"nativeChangeDetectionAttributes\"/>\n" + 
			"      <entry key=\"nativeChangeDetectionEnabled\">\n" + 
			"        <value>\n" + 
			"          <Boolean></Boolean>\n" + 
			"        </value>\n" + 
			"      </entry>\n" + 
			"      <entry key=\"nativeChangeDetectionOperations\"/>\n" + 
			"      <entry key=\"ownerApproval\">\n" + 
			"        <value>\n" + 
			"          <Boolean></Boolean>\n" + 
			"        </value>\n" + 
			"      </entry>\n" + 
			"      <entry key=\"partitionMode\">\n" + 
			"        <value>\n" + 
			"          <Boolean></Boolean>\n" + 
			"        </value>\n" + 
			"      </entry>\n" + 
			"      <entry key=\"partitionStatements\"/>\n" + 
			"      <entry key=\"password\" value=\"1:0wjmINZ8uIishsPf84aaIQ==\"/>\n" + 
			"      <entry key=\"passwordAttrName\"/>\n" + 
			"      <entry key=\"provisionRule\" value=\"globalRule\"/>\n" + 
			"      <entry key=\"sysDescriptions\">\n" + 
			"        <value>\n" + 
			"          <Map>\n" + 
			"            <entry key=\"en_US\" value=\"The HR authoritative source&lt;br>\"/>\n" + 
			"          </Map>\n" + 
			"        </value>\n" + 
			"      </entry>\n" + 
			"      <entry key=\"templateApplication\" value=\"JDBC Template\"/>\n" + 
			"      <entry key=\"url\" value=\"jdbc:mysql://172.16.1.105/orangehrm?useOldAliasMetadataBehavior=true\"/>\n" + 
			"      <entry key=\"useExecuteQuery\">\n" + 
			"        <value>\n" + 
			"          <Boolean></Boolean>\n" + 
			"        </value>\n" + 
			"      </entry>\n" + 
			"      <entry key=\"user\" value=\"orangehrm\"/>\n" + 
			"    </Map>\n" + 
			"  </Attributes>\n" + 
			"  <CorrelationRule>\n" + 
			"    <Reference class=\"sailpoint.object.Rule\" id=\"ff8081815aa4157b015aa416169b0014\" name=\"Correlation - EmployeeID\"/>\n" + 
			"  </CorrelationRule>\n" + 
			"  <CreationRule>\n" + 
			"    <Reference class=\"sailpoint.object.Rule\" id=\"ff8081815aa4157b015aa416c3340030\" name=\"Set Unique Username (I18n) with email on Identity Creation\"/>\n" + 
			"  </CreationRule>\n" + 
			"  <Owner>\n" + 
			"    <Reference class=\"sailpoint.object.Identity\" id=\"ff8081815aa41390015aa41417060104\" name=\"spadmin\"/>\n" + 
			"  </Owner>\n" + 
			"  <Schemas>\n" + 
			"    <Schema created=\"1488811640522\" displayAttribute=\"\" groupAttribute=\"\" id=\"ff8081815aa4157b015aa416c6ca0032\" identityAttribute=\"empid\" instanceAttribute=\"\" nativeObjectType=\"account\" objectType=\"account\">\n" + 
			"      <AttributeDefinition name=\"city\" remediationModificationType=\"None\" type=\"string\"/>\n" + 
			"      <AttributeDefinition name=\"country\" remediationModificationType=\"None\" type=\"string\"/>\n" + 
			"      <AttributeDefinition name=\"department\" remediationModificationType=\"None\" type=\"string\"/>\n" + 
			"      <AttributeDefinition name=\"email\" remediationModificationType=\"None\" type=\"string\"/>\n" + 
			"      <AttributeDefinition name=\"empid\" remediationModificationType=\"None\" type=\"string\"/>\n" + 
			"      <AttributeDefinition name=\"firstname\" remediationModificationType=\"None\" type=\"string\"/>\n" + 
			"      <AttributeDefinition name=\"lastname\" remediationModificationType=\"None\" type=\"string\"/>\n" + 
			"      <AttributeDefinition name=\"mgrId\" remediationModificationType=\"None\" type=\"string\"/>\n" + 
			"      <AttributeDefinition name=\"status\" remediationModificationType=\"None\" type=\"string\"/>\n" + 
			"      <AttributeDefinition name=\"term\" remediationModificationType=\"None\" type=\"boolean\"/>\n" + 
			"      <AttributeDefinition name=\"title\" remediationModificationType=\"None\" type=\"string\"/>\n" + 
			"      <AttributeDefinition name=\"userid\" remediationModificationType=\"None\" type=\"string\"/>\n" + 
			"    </Schema>\n" + 
			"  </Schemas>\n" + 
			"  <ApplicationScorecard created=\"1488811640521\" id=\"ff8081815aa4157b015aa416c6c90031\"/>\n" + 
			"</Application>\n" + 
			"";
	
	
	final String diff = "<?xml version='1.0' encoding='UTF-8'?>\n" + 
			"<!DOCTYPE Application PUBLIC \"sailpoint.dtd\" \"sailpoint.dtd\">\n" + 
			"<Application authoritative=\"true\" connector=\"sailpoint.connector.JDBCConnector\" created=\"1488793451491\" featuresString=\"DISCOVER_SCHEMA, PROVISIONING, GROUP_PROVISIONING, SYNC_PROVISIONING\" icon=\"databaseIcon\" id=\"ff8081815aa2feb2015aa3013be2001f\" modified=\"1488897323586\" name=\"Human Resources\" profileClass=\"\" type=\"JDBC\">\n" + 
			"  <Attributes>\n" + 
			"    <Map>\n" + 
			"      <entry key=\"SQL\" value=\"select emp.employee_id as empid, emp.emp_firstname as firstname, emp.emp_lastname as lastname, emp.emp_work_email as email,&#xD;&#xA; job.job_title as title, loc.country_code as country, loc.city, mgr.employee_id as mgrId, stat.name as status,&#xD;&#xA; concat(emp.emp_firstname, &apos;.&apos;, emp.emp_lastname) as userid,&#xD;&#xA; dept.name as department,&#xD;&#xA; CASE WHEN emp.termination_id IS NOT NULL or stat.name like &apos;Inactive%&apos; THEN True ELSE False END as term,&#xD;&#xA; ftostart, ftoend&#xD;&#xA; &#xD;&#xA; from hs_hr_employee as emp&#xD;&#xA;&#xD;&#xA; left outer join (select ohrm_leave.emp_number, min(ohrm_leave.date)&#xD;&#xA;  as ftostart, max(ohrm_leave.date) as ftoend&#xD;&#xA;  from ohrm_leave group by ohrm_leave.emp_number) as fto&#xD;&#xA; on fto.emp_number=emp.emp_number&#xD;&#xA; left outer join ohrm_job_title as job&#xD;&#xA; on emp.job_title_code=job.id &#xD;&#xA; left outer join hs_hr_emp_reportto as empmgr&#xD;&#xA; on emp.emp_number=empmgr.erep_sub_emp_number&#xD;&#xA; left outer join hs_hr_employee as mgr&#xD;&#xA; on empmgr.erep_sup_emp_number=mgr.emp_number&#xD;&#xA; left outer join hs_hr_emp_locations as emploc&#xD;&#xA; on emp.emp_number=emploc.emp_number&#xD;&#xA; left outer join ohrm_location as loc&#xD;&#xA; on emploc.location_id=loc.id&#xD;&#xA; left outer join ohrm_employment_status as stat&#xD;&#xA; on emp.emp_status=stat.id&#xD;&#xA; left outer join ohrm_subunit as dept&#xD;&#xA; on emp.work_station=dept.id&#xD;&#xA; left outer join ohrm_emp_termination as term&#xD;&#xA; on emp.emp_number=term.emp_number&#xD;&#xA;\"/>\n" + 
			"      <entry key=\"accountDeprovisionScenario\" value=\"Do Nothing\"/>\n" + 
			"      <entry key=\"accountLOAScenario\" value=\"Disable Account Immediately\"/>\n" + 
			"      <entry key=\"accountNoEntitlementsDeprovisioningPolicy\" value=\"Disable Account Immediately\"/>\n" + 
			"      <entry key=\"acctAggregationEnd\">\n" + 
			"        <value>\n" + 
			"          <Date>1488897323486</Date>\n" + 
			"        </value>\n" + 
			"      </entry>\n" + 
			"      <entry key=\"acctAggregationStart\">\n" + 
			"        <value>\n" + 
			"          <Date>1488897273169</Date>\n" + 
			"        </value>\n" + 
			"      </entry>\n" + 
			"      <entry key=\"aggregationMode\">\n" + 
			"        <value>\n" + 
			"          <Boolean>true</Boolean>\n" + 
			"        </value>\n" + 
			"      </entry>\n" + 
			"      <entry key=\"compositeDefinition\"/>\n" + 
			"      <entry key=\"daysToWaitForDeletion\"/>\n" + 
			"      <entry key=\"daysToWaitForDeletionOnLossOfEntitlements\"/>\n" + 
			"      <entry key=\"deltaTable\" value=\"user_delta\"/>\n" + 
			"      <entry key=\"driverClass\" value=\"com.mysql.jdbc.Driver\"/>\n" + 
			"      <entry key=\"enableOnReactivation\">\n" + 
			"        <value>\n" + 
			"          <Boolean></Boolean>\n" + 
			"        </value>\n" + 
			"      </entry>\n" + 
			"      <entry key=\"getDeltaSQL\" value=\"select emp.employee_id as empid, emp.emp_firstname as firstname, emp.emp_lastname as lastname, emp.emp_work_email as email,&#xD;&#xA; job.job_title as title, loc.country_code as country, loc.city, mgr.employee_id as mgrId, stat.name as status,&#xD;&#xA; concat(emp.emp_firstname, &apos;.&apos;, emp.emp_lastname) as userid,&#xD;&#xA; dept.name as department,&#xD;&#xA; CASE WHEN emp.termination_id IS NOT NULL or stat.name like &apos;Inactive%&apos; THEN True ELSE False END as term,&#xD;&#xA; ftostart, ftoend&#xD;&#xA; &#xD;&#xA; from hs_hr_employee as emp&#xD;&#xA;&#xD;&#xA; left outer join (select ohrm_leave.emp_number, min(ohrm_leave.date)&#xD;&#xA;  as ftostart, max(ohrm_leave.date) as ftoend&#xD;&#xA;  from ohrm_leave group by ohrm_leave.emp_number) as fto&#xD;&#xA; on fto.emp_number=emp.emp_number&#xD;&#xA; left outer join ohrm_job_title as job&#xD;&#xA; on emp.job_title_code=job.id &#xD;&#xA; left outer join hs_hr_emp_reportto as empmgr&#xD;&#xA; on emp.emp_number=empmgr.erep_sub_emp_number&#xD;&#xA; left outer join hs_hr_employee as mgr&#xD;&#xA; on empmgr.erep_sup_emp_number=mgr.emp_number&#xD;&#xA; left outer join hs_hr_emp_locations as emploc&#xD;&#xA; on emp.emp_number=emploc.emp_number&#xD;&#xA; left outer join ohrm_location as loc&#xD;&#xA; on emploc.location_id=loc.id&#xD;&#xA; left outer join ohrm_employment_status as stat&#xD;&#xA; on emp.emp_status=stat.id&#xD;&#xA; left outer join ohrm_subunit as dept&#xD;&#xA; on emp.work_station=dept.id&#xD;&#xA; left outer join ohrm_emp_termination as term&#xD;&#xA; on emp.emp_number=term.emp_number&#xD;&#xA;where emp.employee_id=&apos;$(identity)&apos;\"/>\n" + 
			"      <entry key=\"getObjectSQL\" value=\"select emp.employee_id as empid, emp.emp_firstname as firstname, emp.emp_lastname as lastname, emp.emp_work_email as email,&#xD;&#xA; job.job_title as title, loc.country_code as country, loc.city, mgr.employee_id as mgrId, stat.name as status,&#xD;&#xA; concat(emp.emp_firstname, &apos;.&apos;, emp.emp_lastname) as userid,&#xD;&#xA; dept.name as department,&#xD;&#xA;  CASE WHEN emp.termination_id IS NOT NULL or stat.name like &apos;Inactive%&apos; THEN True ELSE False END as term,&#xD;&#xA; ftostart, ftoend&#xD;&#xA; &#xD;&#xA; from hs_hr_employee as emp&#xD;&#xA;&#xD;&#xA; left outer join (select ohrm_leave.emp_number, min(ohrm_leave.date)&#xD;&#xA;  as ftostart, max(ohrm_leave.date) as ftoend&#xD;&#xA;  from ohrm_leave group by ohrm_leave.emp_number) as fto&#xD;&#xA; on fto.emp_number=emp.emp_number&#xD;&#xA; left outer join ohrm_job_title as job&#xD;&#xA; on emp.job_title_code=job.id &#xD;&#xA; left outer join hs_hr_emp_reportto as empmgr&#xD;&#xA; on emp.emp_number=empmgr.erep_sub_emp_number&#xD;&#xA; left outer join hs_hr_employee as mgr&#xD;&#xA; on empmgr.erep_sup_emp_number=mgr.emp_number&#xD;&#xA; left outer join hs_hr_emp_locations as emploc&#xD;&#xA; on emp.emp_number=emploc.emp_number&#xD;&#xA; left outer join ohrm_location as loc&#xD;&#xA; on emploc.location_id=loc.id&#xD;&#xA; left outer join ohrm_employment_status as stat&#xD;&#xA; on emp.emp_status=stat.id&#xD;&#xA; left outer join ohrm_subunit as dept&#xD;&#xA; on emp.work_station=dept.id&#xD;&#xA; left outer join ohrm_emp_termination as term&#xD;&#xA; on emp.emp_number=term.emp_number&#xD;&#xA;&#xD;&#xA;where emp.emp_firstname=substring_index(&quot;$(identity)&quot;, &apos;.&apos;, 1)&#xD;&#xA;  and emp.emp_lastname=substring_index(&quot;$(identity)&quot;, &apos;.&apos;, -1)\"/>\n" + 
			"      <entry key=\"group.aggregationMode\">\n" + 
			"        <value>\n" + 
			"          <Boolean></Boolean>\n" + 
			"        </value>\n" + 
			"      </entry>\n" + 
			"      <entry key=\"group.isAdvance\">\n" + 
			"        <value>\n" + 
			"          <Boolean></Boolean>\n" + 
			"        </value>\n" + 
			"      </entry>\n" + 
			"      <entry key=\"group.isPermissionEnabled\">\n" + 
			"        <value>\n" + 
			"          <Boolean></Boolean>\n" + 
			"        </value>\n" + 
			"      </entry>\n" + 
			"      <entry key=\"group.mergeRows\">\n" + 
			"        <value>\n" + 
			"          <Boolean></Boolean>\n" + 
			"        </value>\n" + 
			"      </entry>\n" + 
			"      <entry key=\"group.useExecuteQuery\">\n" + 
			"        <value>\n" + 
			"          <Boolean></Boolean>\n" + 
			"        </value>\n" + 
			"      </entry>\n" + 
			"      <entry key=\"groupHierarchyAttribute\" value=\"Child Roles\"/>\n" + 
			"      <entry key=\"isAdvance\">\n" + 
			"        <value>\n" + 
			"          <Boolean></Boolean>\n" + 
			"        </value>\n" + 
			"      </entry>\n" + 
			"      <entry key=\"isPermissionEnabled\">\n" + 
			"        <value>\n" + 
			"          <Boolean></Boolean>\n" + 
			"        </value>\n" + 
			"      </entry>\n" + 
			"      <entry key=\"managerApproval\">\n" + 
			"        <value>\n" + 
			"          <Boolean></Boolean>\n" + 
			"        </value>\n" + 
			"      </entry>\n" + 
			"      <entry key=\"managerCorrelationFilter\">\n" + 
			"        <value>\n" + 
			"          <Filter operation=\"EQ\" property=\"employeeId\" value=\"mgrId\"/>\n" + 
			"        </value>\n" + 
			"      </entry>\n" + 
			"      <entry key=\"mergeRows\">\n" + 
			"        <value>\n" + 
			"          <Boolean></Boolean>\n" + 
			"        </value>\n" + 
			"      </entry>\n" + 
			"      <entry key=\"nativeChangeDetectionAttributeScope\" value=\"entitlements\"/>\n" + 
			"      <entry key=\"nativeChangeDetectionAttributes\"/>\n" + 
			"      <entry key=\"nativeChangeDetectionEnabled\">\n" + 
			"        <value>\n" + 
			"          <Boolean></Boolean>\n" + 
			"        </value>\n" + 
			"      </entry>\n" + 
			"      <entry key=\"nativeChangeDetectionOperations\"/>\n" + 
			"      <entry key=\"ownerApproval\">\n" + 
			"        <value>\n" + 
			"          <Boolean></Boolean>\n" + 
			"        </value>\n" + 
			"      </entry>\n" + 
			"      <entry key=\"partitionMode\">\n" + 
			"        <value>\n" + 
			"          <Boolean></Boolean>\n" + 
			"        </value>\n" + 
			"      </entry>\n" + 
			"      <entry key=\"partitionStatements\"/>\n" + 
			"      <entry key=\"password\" value=\"1:0wjmINZ8uIishsPf84aaIQ==\"/>\n" + 
			"      <entry key=\"passwordAttrName\"/>\n" + 
			"      <entry key=\"provisionRule\" value=\"globalRule\"/>\n" + 
			"      <entry key=\"sysDescriptions\">\n" + 
			"        <value>\n" + 
			"          <Map>\n" + 
			"            <entry key=\"en_US\" value=\"The HR authoritative source&lt;br>\"/>\n" + 
			"          </Map>\n" + 
			"        </value>\n" + 
			"      </entry>\n" + 
			"      <entry key=\"templateApplication\" value=\"JDBC Template\"/>\n" + 
			"      <entry key=\"url\" value=\"jdbc:mysql://172.16.1.105/orangehrm?useOldAliasMetadataBehavior=true\"/>\n" + 
			"      <entry key=\"useExecuteQuery\">\n" + 
			"        <value>\n" + 
			"          <Boolean></Boolean>\n" + 
			"        </value>\n" + 
			"      </entry>\n" + 
			"      <entry key=\"user\" value=\"orangehrm\"/>\n" + 
			"    </Map>\n" + 
			"  </Attributes>\n" + 
			"  <CorrelationRule>\n" + 
			"    <Reference class=\"sailpoint.object.Rule\" id=\"ff8081815aa2feb2015aa300afc80014\" name=\"Correlation - EmployeeID\"/>\n" + 
			"  </CorrelationRule>\n" + 
			"  <CreationRule>\n" + 
			"    <Reference class=\"sailpoint.object.Rule\" id=\"ff8081815aa40b05015aa40b876e0003\" name=\"Set Unique Username (I18n) with email on Identity Creation\"/>\n" + 
			"  </CreationRule>\n" + 
			"  <Owner>\n" + 
			"    <Reference class=\"sailpoint.object.Identity\" id=\"ff8081815aa2fc29015aa2fd0dae0104\" name=\"spadmin\"/>\n" + 
			"  </Owner>\n" + 
			"  <Schemas>\n" + 
			"    <Schema created=\"1488810904487\" displayAttribute=\"\" groupAttribute=\"\" id=\"ff8081815aa40b05015aa40b8ba70005\" identityAttribute=\"empid\" instanceAttribute=\"\" nativeObjectType=\"account\" objectType=\"account\">\n" + 
			"      <AttributeDefinition name=\"city\" remediationModificationType=\"None\" type=\"string\"/>\n" + 
			"      <AttributeDefinition name=\"country\" remediationModificationType=\"None\" type=\"string\"/>\n" + 
			"      <AttributeDefinition name=\"department\" remediationModificationType=\"None\" type=\"string\"/>\n" + 
			"      <AttributeDefinition name=\"email\" remediationModificationType=\"None\" type=\"string\"/>\n" + 
			"      <AttributeDefinition name=\"empid\" remediationModificationType=\"None\" type=\"string\"/>\n" + 
			"      <AttributeDefinition name=\"firstname\" remediationModificationType=\"None\" type=\"string\"/>\n" + 
			"      <AttributeDefinition name=\"lastname\" remediationModificationType=\"None\" type=\"string\"/>\n" + 
			"      <AttributeDefinition name=\"mgrId\" remediationModificationType=\"None\" type=\"string\"/>\n" + 
			"      <AttributeDefinition name=\"status\" remediationModificationType=\"None\" type=\"string\"/>\n" + 
			"      <AttributeDefinition name=\"term\" remediationModificationType=\"None\" type=\"boolean\"/>\n" + 
			"      <AttributeDefinition name=\"title\" remediationModificationType=\"None\" type=\"string\"/>\n" + 
			"      <AttributeDefinition name=\"userid\" remediationModificationType=\"None\" type=\"string\"/>\n" + 
			"    </Schema>\n" + 
			"  </Schemas>\n" + 
			"  <ApplicationScorecard created=\"1488810904485\" id=\"ff8081815aa40b05015aa40b8ba50004\"/>\n" + 
			"</Application>\n" + 
			"";

	@Test
	public void test() {
		
		//test to prove there's a difference in both xmls
		Diff myDiff = DiffBuilder.compare(Input.fromString(clean))
		              .withTest(Input.fromString(diff))
		              .build();

		Assert.assertFalse(myDiff.toString(), myDiff.hasDifferences());
	}
	
	@Test
	public void test2() {
		//test to ignore Application node in xmls to prove there similar
		Diff myDiff2 = DiffBuilder.compare(Input.fromString(clean))                
	            .withTest(Input.fromString(diff))
	            .ignoreComments()
	            .ignoreWhitespace()                 
	            .withNodeFilter(node -> !node.getNodeName().equals("Application"))
	            .build();

		Assert.assertFalse(myDiff2.toString(), myDiff2.hasDifferences());
		
	}
	
	@Test
	public void test3() {
		// test that ignores specific attributes in order to pass but diff.xml has aggregated which includes extra map keys to ignore
		Diff myDiff3 = DiffBuilder.compare(Input.fromString(clean))                
	            .withTest(Input.fromString(diff))
	            .ignoreComments()
	            .ignoreWhitespace()   
	            //need to ignore aggregate keys to pass test
	            .withAttributeFilter(a -> !("created".equals(a.getName()) || "id".equals(a.getName()) || "modified".equals(a.getName()) ))
	            .build();

		Assert.assertFalse(myDiff3.toString(), myDiff3.hasDifferences());
		
	}
	
	@Test
	public void test4() {
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
	public void test5() {
		//test that creates a class to customise what one calls a similar result
		Diff myDiff5 = DiffBuilder.compare(Input.fromString(clean))                
	            .withTest(Input.fromString(diff))
	            .ignoreComments()
	            .ignoreWhitespace()                 
	            .withDifferenceEvaluator(new IgnoreAttributeDifferenceEvaluator("created"))
	            .checkForSimilar()
	            .build();

		Assert.assertFalse(myDiff5.toString(), myDiff5.hasDifferences());
		
	}
	

}
