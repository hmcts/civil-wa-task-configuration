package uk.gov.hmcts.civil.taskconfiguration.dmnga;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.camunda.bpm.dmn.engine.DmnDecisionTableResult;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.impl.VariableMapImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import uk.gov.hmcts.civil.taskconfiguration.DmnDecisionTable;
import uk.gov.hmcts.civil.taskconfiguration.DmnDecisionTableBaseUnitTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class CamundaGaSubmissionTaskWaInitiationTest extends DmnDecisionTableBaseUnitTest {

    String rawListingForHearingString = "{\"claimFee\": {\"code\": \"FEE0205\", \"version\": \"4\", \"calculatedAmountInPence\": \"8000\"}, \"issueDate\": \"2023-06-26\", \"orderType\": \"DECIDE_DAMAGES\", \"applicant1\": {\"type\": \"COMPANY\", \"flags\": {\"partyName\": \"Test Inc\", \"roleOnCase\": \"Applicant 1\"}, \"partyID\": \"15487493-bd6e-49\", \"partyName\": \"Test Inc\", \"companyName\": \"Test Inc\", \"primaryAddress\": {\"County\": \"Kent\", \"Country\": \"United Kingdom\", \"PostCode\": \"RG4 7AA\", \"PostTown\": \"Reading\", \"AddressLine1\": \"Flat 2 - applicant\", \"AddressLine2\": \"Caversham House 15-17\", \"AddressLine3\": \"Church Road\"}, \"partyTypeDisplayValue\": \"Company\"}, \"respondent1\": {\"type\": \"INDIVIDUAL\", \"flags\": {\"partyName\": \"Sir John Doe\", \"roleOnCase\": \"Respondent 1\"}, \"partyID\": \"8597196f-f426-49\", \"partyName\": \"Sir John Doe\", \"primaryAddress\": {\"County\": \"Kent\", \"Country\": \"United Kingdom\", \"PostCode\": \"RG4 7AA\", \"PostTown\": \"Reading\", \"AddressLine1\": \"Flat 2 - respondent\", \"AddressLine2\": \"Caversham House 15-17\", \"AddressLine3\": \"Church Road\"}, \"individualTitle\": \"Sir\", \"individualLastName\": \"Doe\", \"individualFirstName\": \"John\", \"partyTypeDisplayValue\": \"Individual\"}, \"respondent2\": {\"type\": \"ORGANISATION\", \"flags\": {\"partyName\": \"Second Defendant\", \"roleOnCase\": \"Respondent 2\"}, \"partyID\": \"6ce7a978-0904-4c\", \"partyName\": \"Second Defendant\", \"primaryAddress\": {\"PostCode\": \"NR5 9LL\", \"PostTown\": \"Second Town\", \"AddressLine1\": \"123 Second Close\"}, \"organisationName\": \"Second Defendant\", \"partyTypeDisplayValue\": \"Organisation\"}, \"locationName\": \"Barnet Civil and Family Centre\", \"addApplicant2\": \"No\", \"claimInterest\": \"No\", \"submittedDate\": \"2023-06-26T09:22:28\", \"totalInterest\": 0, \"SearchCriteria\": {\"SearchParties\": [{\"id\": \"7624d37b-8483-4e35-801f-b5e24278f7bd\", \"value\": {\"Name\": \"Sir John Doe\", \"PostCode\": \"RG4 7AA\", \"AddressLine1\": \"Flat 2 - respondent\", \"EmailAddress\": \"civilunspecified@gmail.com\"}}, {\"id\": \"2b6701df-b15c-4322-9305-8150a0162f62\", \"value\": {\"Name\": \"Second Defendant\", \"PostCode\": \"NR5 9LL\", \"AddressLine1\": \"123 Second Close\", \"EmailAddress\": \"civilunspecified@gmail.com\"}}, {\"id\": \"85257536-8af3-4346-8e64-936fb1105c3d\", \"value\": {\"Name\": \"Test Inc\", \"PostCode\": \"RG4 7AA\", \"AddressLine1\": \"Flat 2 - applicant\", \"EmailAddress\": \"hmcts.civil+organisation.1.solicitor.1@gmail.com\"}}], \"OtherCaseReferences\": [{\"id\": \"df0e5c1d-202c-4d95-ad88-d4e1c0e41292\", \"value\": \"000MC055\"}]}, \"addRespondent2\": \"Yes\", \"detailsOfClaim\": \"Test details of claim\", \"fastTrackNotes\": {\"date\": \"2023-06-27\", \"input\": \"string\"}, \"fastTrackTrial\": {\"type\": [\"DOCUMENTS\"], \"date1\": \"2023-06-27\", \"date2\": \"2023-06-27\", \"input1\": \"string\", \"input2\": \"string\", \"input3\": \"string\"}, \"businessProcess\": {\"status\": \"FINISHED\", \"camundaEvent\": \"CREATE_SDO\"}, \"fastTrackMethod\": \"fastTrackMethodTelephoneHearing\", \"gaDraftDocStaff\": [{\"id\": \"82e37cfe-2737-4114-9fa5-d4ca84e3fe03\", \"value\": {\"createdBy\": \"Civil\", \"documentLink\": {\"category_id\": \"applications\", \"document_url\": \"http://dm-store:8080/documents/95f2105e-cf0f-4043-a953-2faf136283df\", \"document_filename\": \"Draft_application_2023-06-26 09:23:10.pdf\", \"document_binary_url\": \"http://dm-store:8080/documents/95f2105e-cf0f-4043-a953-2faf136283df/binary\"}, \"documentName\": \"Draft_application_2023-06-26 09:23:10.pdf\", \"documentSize\": 44185, \"documentType\": \"GENERAL_APPLICATION_DRAFT\", \"createdDatetime\": \"2023-06-26T09:23:10\"}}], \"respondent1Copy\": {\"type\": \"INDIVIDUAL\", \"flags\": {\"partyName\": \"Sir John Doe\", \"roleOnCase\": \"Respondent 1\"}, \"partyID\": \"8597196f-f426-49\", \"partyName\": \"Sir John Doe\", \"primaryAddress\": {\"County\": \"Kent\", \"Country\": \"United Kingdom\", \"PostCode\": \"RG4 7AA\", \"PostTown\": \"Reading\", \"AddressLine1\": \"Flat 2 - respondent\", \"AddressLine2\": \"Caversham House 15-17\", \"AddressLine3\": \"Church Road\"}, \"individualTitle\": \"Sir\", \"individualLastName\": \"Doe\", \"individualFirstName\": \"John\", \"partyTypeDisplayValue\": \"Individual\"}, \"smallClaimsNotes\": {\"input\": \"This order has been made without hearing. Each party has the right to apply to have this Order set aside or varied. Any such application must be received by the Court (together with the appropriate fee) by 4pm on 3 July 2023\"}, \"timelineOfEvents\": [{\"id\": \"689a7382-3aa6-4cb3-8ad6-63f2792a5d63\", \"value\": {\"timelineDate\": \"2021-02-01\", \"timelineDescription\": \"event 1\"}}], \"totalClaimAmount\": 1500, \"smallClaimsMethod\": \"smallClaimsMethodInPerson\", \"CaseAccessCategory\": \"SPEC_CLAIM\", \"calculatedInterest\": \" | Description | Amount | \\n |---|---| \\n | Claim amount | £ 1500 | \\n | Interest amount | £ 0 | \\n | Total amount | £ 1500 |\", \"claimAmountBreakup\": [{\"id\": \"e52acaf0-4638-4b7d-8a8c-814f9dfcedf6\", \"value\": {\"claimAmount\": \"150000\", \"claimReason\": \"amount reason\"}}], \"gaDraftDocClaimant\": [{\"id\": \"82e37cfe-2737-4114-9fa5-d4ca84e3fe03\", \"value\": {\"createdBy\": \"Civil\", \"documentLink\": {\"category_id\": \"applications\", \"document_url\": \"http://dm-store:8080/documents/95f2105e-cf0f-4043-a953-2faf136283df\", \"document_filename\": \"Draft_application_2023-06-26 09:23:10.pdf\", \"document_binary_url\": \"http://dm-store:8080/documents/95f2105e-cf0f-4043-a953-2faf136283df/binary\"}, \"documentName\": \"Draft_application_2023-06-26 09:23:10.pdf\", \"documentSize\": 44185, \"documentType\": \"GENERAL_APPLICATION_DRAFT\", \"createdDatetime\": \"2023-06-26T09:23:10\"}}], \"paymentTypePBASpec\": \"PBAv3\", \"responseClaimTrack\": \"SMALL_CLAIM\", \"showConditionFlags\": [\"CAN_ANSWER_RESPONDENT_2\", \"BOTH_RESPONDENTS_DISPUTE\", \"SOMEONE_DISPUTES\", \"CAN_ANSWER_RESPONDENT_1\"], \"smallClaimsHearing\": {\"input1\": \"The hearing of the claim will be on a date to be notified to you by a separate notification. The hearing will have a time estimate of\", \"input2\": \"The claimant must by no later than 4 weeks before the hearing date, pay the court the required hearing fee or submit a fully completed application for Help with Fees. \\nIf the claimant fails to pay the fee or obtain a fee exemption by that time the claim will be struck without further order.\"}, \"applicant1DQHearing\": {\"unavailableDatesRequired\": \"No\"}, \"drawDirectionsOrder\": {\"judgementSum\": \"20.0\"}, \"fastTrackCreditHire\": {\"date1\": \"2023-06-27\", \"date2\": \"2023-06-27\", \"date3\": \"2023-06-27\", \"date4\": \"2023-06-27\", \"input1\": \"string\", \"input2\": \"string\", \"input3\": \"string\", \"input4\": \"string\", \"input5\": \"string\", \"input6\": \"string\", \"input7\": \"string\", \"input8\": \"string\"}, \"generalApplications\": [{\"id\": \"cfea9f9e-ac04-4f57-9de9-bbf3cac0c422\", \"value\": {\"caseLink\": {\"CaseReference\": \"1687767784213052\"}, \"isMultiParty\": \"Yes\", \"locationName\": \"Barnet Civil and Family Centre\", \"generalAppType\": {\"types\": [\"SUMMARY_JUDGEMENT\", \"EXTEND_TIME\"]}, \"businessProcess\": {\"status\": \"FINISHED\", \"camundaEvent\": \"CREATE_GENERAL_APPLICATION_CASE\", \"processInstanceId\": \"ab6d6908-13fa-11ee-a28c-0242ac120002\"}, \"isCcmccLocation\": \"No\", \"CaseAccessCategory\": \"SPEC_CLAIM\", \"applicantPartyName\": \"Test Inc\", \"claimant1PartyName\": \"Test Inc\", \"defendant1PartyName\": \"Sir John Doe\", \"defendant2PartyName\": \"Second Defendant\", \"generalAppPBADetails\": {\"fee\": {\"code\": \"FEE0443\", \"version\": \"2\", \"calculatedAmountInPence\": \"10800\"}}, \"civilServiceUserRoles\": {\"id\": \"e286ef3b-3766-4a22-888d-b81cfe544deb\", \"email\": \"hmcts.civil+organisation.1.solicitor.1@gmail.com\"}, \"caseManagementCategory\": {\"value\": {\"code\": \"Civil\", \"label\": \"Civil\"}, \"list_items\": [{\"id\": \"2fd349fd-5b7e-4260-836e-481c8beb3fe8\", \"value\": {\"code\": \"Civil\", \"label\": \"Civil\"}}]}, \"caseManagementLocation\": {\"region\": \"2\", \"siteName\": \"Barnet Civil and Family Centre\", \"baseLocation\": \"000000\"}, \"gaApplicantDisplayName\": \"Test Inc - Claimant\", \"generalAppDateDeadline\": \"2023-07-01T16:00:00\", \"generalAppApplnSolicitor\": {\"id\": \"e286ef3b-3766-4a22-888d-b81cfe544deb\", \"email\": \"hmcts.civil+organisation.1.solicitor.1@gmail.com\", \"surname\": \"Claimant-solicitor\", \"forename\": \"hmcts.civil+organisation.1.solicitor.1@gmail.com\", \"organisationIdentifier\": \"Q1KOKP2\"}, \"generalAppDetailsOfOrder\": \"Test Order details\", \"generalAppHearingDetails\": {\"hearingYesorNo\": \"No\", \"HearingDuration\": \"MINUTES_15\", \"judgeRequiredYesOrNo\": \"No\", \"trialRequiredYesOrNo\": \"No\", \"HearingDetailsEmailID\": \"update@gh.com\", \"vulnerabilityQuestion\": \"Test Test\", \"HearingDetailsTelephoneNumber\": \"07446778166\", \"ReasonForPreferredHearingType\": \"sdsd\", \"vulnerabilityQuestionsYesOrNo\": \"Yes\", \"HearingPreferencesPreferredType\": \"IN_PERSON\", \"unavailableTrialRequiredYesOrNo\": \"No\"}, \"generalAppReasonsOfOrder\": \"Test reason for order\", \"generalAppSuperClaimType\": \"SPEC_CLAIM\", \"parentClaimantIsApplicant\": \"Yes\", \"generalAppInformOtherParty\": {\"isWithNotice\": \"No\", \"reasonsForWithoutNotice\": \"Test 123\"}, \"generalAppStatementOfTruth\": {\"name\": \"John Doe\", \"role\": \"Test Solicitor\"}, \"generalAppUrgencyRequirement\": {\"generalAppUrgency\": \"No\"}, \"generalAppRespondentAgreement\": {\"hasAgreed\": \"No\"}, \"generalAppSubmittedDateGAspec\": \"2023-06-26T09:23:03\", \"generalAppRespondentSolicitors\": [{\"id\": \"cfe5da54-a25c-4866-9ada-c77dfb41dcb2\", \"value\": {\"id\": \"65173a08-84c3-46ba-b58c-f8e839682a84\", \"email\": \"civilunspecified@gmail.com\", \"organisationIdentifier\": \"79ZRSOU\"}}]}}], \"legacyCaseReference\": \"000MC055\", \"solicitorReferences\": {\"applicantSolicitor1Reference\": \"Applicant reference\", \"respondentSolicitor1Reference\": \"Respondent reference\", \"respondentSolicitor2Reference\": \"Respondent reference\"}, \"applicant1DQLanguage\": {\"court\": \"ENGLISH\", \"evidence\": \"ENGLISH\", \"documents\": \"ENGLISH\"}, \"claimantGaAppDetails\": [{\"id\": \"51da01e1-5d8c-4347-9a41-3014bf81f662\", \"value\": {\"caseLink\": {\"CaseReference\": \"1687767784213052\"}, \"caseState\": \"Listed for a Hearing\", \"generalApplicationType\": \"Summary judgment, Extend time\", \"generalAppSubmittedDateGAspec\": \"2023-06-26T09:23:03\"}}], \"defenceRouteRequired\": \"DISPUTES_THE_CLAIM\", \"disposalHearingNotes\": {\"date\": \"2023-07-03\", \"input\": \"This Order has been made without a hearing. Each party has the right to apply to have this Order set aside or varied. Any such application must be uploaded to the Digital Portal together with the appropriate fee, by 4pm on\"}, \"fastTrackCostsToggle\": [\"SHOW\"], \"fastTrackHearingTime\": {\"dateTo\": \"2024-01-22\", \"dateFrom\": \"2023-11-27\", \"helpText1\": \"If either party considers that the time estimate is insufficient, they must inform the court within 7 days of the date of this order.\", \"helpText2\": \"Not more than seven nor less than three clear days before the trial, the claimant must file at court and serve an indexed and paginated bundle of documents which complies with the requirements of Rule 39.5 Civil Procedure Rules and which complies with requirements of PD32. The parties must endeavour to agree the contents of the bundle before it is filed. The bundle will include a case summary and a chronology.\", \"dateToToggle\": [\"SHOW\"]}, \"fastTrackTrialToggle\": [\"SHOW\"], \"hearingOrderDocStaff\": [{\"id\": \"c96021d5-81a7-4926-a4bc-7aa580ac2fa3\", \"value\": {\"createdBy\": \"Civil\", \"documentLink\": {\"category_id\": \"applications\", \"document_url\": \"http://dm-store:8080/documents/7741775c-d822-4959-b135-85e9bd72cda7\", \"document_filename\": \"Hearing_order_for_application_2023-06-26 09:23:41.pdf\", \"document_binary_url\": \"http://dm-store:8080/documents/7741775c-d822-4959-b135-85e9bd72cda7/binary\"}, \"documentName\": \"Hearing_order_for_application_2023-06-26 09:23:41.pdf\", \"documentSize\": 36425, \"documentType\": \"HEARING_ORDER\", \"createdDatetime\": \"2023-06-26T09:23:41\"}}], \"respondent1DQHearing\": {\"unavailableDatesRequired\": \"No\"}, \"smallClaimsDocuments\": {\"input1\": \"Each party must upload to the Digital Portal copies of all documents which they wish the court to consider when reaching its decision not less than 14 days before the hearing.\", \"input2\": \"The court may refuse to consider any document which has not been uploaded to the Digital Portal by the above date.\"}, \"caseNameHmctsInternal\": \"Test Inc v Sir John Doe and Second Defendant\", \"claimIssuedPBADetails\": {\"fee\": {\"code\": \"FEE0205\", \"version\": \"4\", \"calculatedAmountInPence\": \"8000\"}, \"applicantsPbaAccounts\": {\"value\": {\"code\": \"91fca730-13fa-11ee-91f1-d3b5d9925574\", \"label\": \"PBA0088192\"}, \"list_items\": [{\"code\": \"91fca730-13fa-11ee-91f1-d3b5d9925574\", \"label\": \"PBA0088192\"}, {\"code\": \"91fca731-13fa-11ee-91f1-d3b5d9925574\", \"label\": \"PBA0078095\"}]}, \"serviceRequestReference\": \"2022-1655915218557\"}, \"claimNotificationDate\": \"2023-06-26T09:22:37\", \"disposalHearingBundle\": {\"input\": \"At least 7 days before the disposal hearing, the claimant must file and serve\"}, \"fastTrackMethodToggle\": [\"SHOW\"], \"respondent1DQLanguage\": {\"court\": \"ENGLISH\", \"evidence\": \"ENGLISH\", \"documents\": \"ENGLISH\"}, \"smallClaimsCreditHire\": {\"date1\": \"2023-07-24\", \"date2\": \"2023-08-07\", \"date3\": \"2023-08-21\", \"date4\": \"2023-09-04\", \"input1\": \"If impecuniosity is alleged by the claimant and not admitted by the defendant, the claimant's disclosure as ordered earlier in this Order must include:\\na) Evidence of all income from all sources for a period of 3 months prior to the commencement of hire until the earlier of:\\n      i) 3 months after cessation of hire\\n     ii) the repair or replacement of the claimant's vehicle\\nb) Copies of all bank, credit card, and saving account statements for a period of 3 months prior to the commencement of hire until the earlier of:\\n     i) 3 months after cessation of hire\\n     ii) the repair or replacement of the claimant's vehicle\\nc) Evidence of any loan, overdraft or other credit facilities available to the claimant.\", \"input2\": \"The claimant must upload to the Digital Portal a witness statement addressing\\na) the need to hire a replacement vehicle; and\\nb) impecuniosity\", \"input3\": \"A failure to comply with the paragraph above will result in the claimant being debarred from asserting need or relying on impecuniosity as the case may be at the final hearing, save with permission of the Trial Judge.\", \"input4\": \"The parties are to liaise and use reasonable endeavours to agree the basic hire rate no later than 4pm on\", \"input5\": \"If the parties fail to agree rates subject to liability and/or other issues pursuant to the paragraph above, each party may rely upon written evidence by way of witness statement of one witness to provide evidence of basic hire rates available within the claimant's geographical location, from a mainstream supplier, or a local reputable supplier if none is available.\", \"input6\": \"The defendant's evidence is to be uploaded to the Digital Portal by 4pm on\", \"input7\": \"and the claimant's evidence in reply if so advised to be uploaded by 4pm on\", \"input11\": \"This witness statement is limited to 10 pages per party, including any appendices.\"}, \"applicant1ResponseDate\": \"2023-06-26T09:22:57\", \"caseManagementCategory\": {\"value\": {\"code\": \"Civil\", \"label\": \"Civil\"}, \"list_items\": [{\"id\": \"8c941350-1428-4363-be82-5e37a1b16816\", \"value\": {\"code\": \"Civil\", \"label\": \"Civil\"}}]}, \"caseManagementLocation\": {\"region\": \"2\", \"baseLocation\": \"000000\"}, \"fastTrackJudgesRecital\": {\"input\": \"string\"}, \"fastTrackWitnessOfFact\": {\"date\": \"2023-06-27\", \"input1\": \"string\", \"input2\": \"1\", \"input3\": \"1\", \"input4\": \"string\", \"input5\": \"string\", \"input6\": \"1\", \"input7\": \"string\", \"input8\": \"string\", \"input9\": \"string\"}, \"respondToCourtLocation\": {\"responseCourtCode\": \"121\"}, \"respondent1Represented\": \"Yes\", \"respondent2Represented\": \"Yes\", \"responseClaimWitnesses\": \"10\", \"fastTrackMethodInPerson\": {\"value\": {\"code\": \"9f5817fb-b7b3-4cd1-b5e5-99f33e995996\", \"label\": \"Barnet Civil and Family Centre - St Mary's Court, Regents Park Road - N3 1BQ\"}, \"list_items\": [{\"code\": \"9f5817fb-b7b3-4cd1-b5e5-99f33e995996\", \"label\": \"Barnet Civil and Family Centre - St Mary's Court, Regents Park Road - N3 1BQ\"}, {\"code\": \"ca851924-d649-4c88-a088-2162aff42c23\", \"label\": \"Central London County Court - THOMAS MORE BUILDING, ROYAL COURTS OF JUSTICE, STRAND, LONDON - WC2A 2LL\"}, {\"code\": \"3c714da9-1a08-46a3-910b-d73ad6754356\", \"label\": \"High Wycombe Law Courts - THE LAW COURTS, EASTON STREET - HP11 1LR\"}, {\"code\": \"accb417d-48a5-4feb-9d39-850c5e621be4\", \"label\": \"Liverpool Civil and Family Court - 35, Vernon Street, City Square - L2 2BX\"}]}, \"fastTrackPersonalInjury\": {\"date1\": \"2023-06-27\", \"date2\": \"2023-06-27\", \"input1\": \"string\", \"input2\": \"string\", \"input3\": \"string\"}, \"hearingOrderDocClaimant\": [{\"id\": \"c96021d5-81a7-4926-a4bc-7aa580ac2fa3\", \"value\": {\"createdBy\": \"Civil\", \"documentLink\": {\"category_id\": \"applications\", \"document_url\": \"http://dm-store:8080/documents/7741775c-d822-4959-b135-85e9bd72cda7\", \"document_filename\": \"Hearing_order_for_application_2023-06-26 09:23:41.pdf\", \"document_binary_url\": \"http://dm-store:8080/documents/7741775c-d822-4959-b135-85e9bd72cda7/binary\"}, \"documentName\": \"Hearing_order_for_application_2023-06-26 09:23:41.pdf\", \"documentSize\": 36425, \"documentType\": \"HEARING_ORDER\", \"createdDatetime\": \"2023-06-26T09:23:41\"}}], \"respondToCourtLocation2\": {\"responseCourtLocations\": {\"list_items\": [{\"code\": \"32b1cbc3-3d4b-406e-b58c-cc1ea2a38ce9\", \"label\": \"Barnet Civil and Family Centre - St Mary's Court, Regents Park Road - N3 1BQ\"}, {\"code\": \"c1b6b150-1f5e-4bac-bf22-dd27330780de\", \"label\": \"Central London County Court - THOMAS MORE BUILDING, ROYAL COURTS OF JUSTICE, STRAND, LONDON - WC2A 2LL\"}, {\"code\": \"aad0e24f-0dbe-4c38-969f-33b2b426cc7c\", \"label\": \"High Wycombe Law Courts - THE LAW COURTS, EASTON STREET - HP11 1LR\"}, {\"code\": \"fa59ed15-b2b4-40cc-9018-f43d8c28a169\", \"label\": \"Liverpool Civil and Family Court - 35, Vernon Street, City Square - L2 2BX\"}]}}, \"respondent1ResponseDate\": \"2023-06-26T09:22:44\", \"respondent2ResponseDate\": \"2023-06-26T09:22:44\", \"smallClaimsMethodToggle\": [\"SHOW\"], \"applicant1ClaimWitnesses\": \"10\", \"fastTrackBuildingDispute\": {\"date1\": \"2023-06-27\", \"date2\": \"2023-06-27\", \"input1\": \"string\", \"input2\": \"string\", \"input3\": \"string\", \"input4\": \"string\"}, \"fastTrackSchedulesOfLoss\": {\"date1\": \"2023-06-27\", \"date2\": \"2023-06-27\", \"input1\": \"string\", \"input2\": \"string\", \"input3\": \"string\"}, \"respondent1OrgRegistered\": \"Yes\", \"respondent2OrgRegistered\": \"Yes\", \"respondentResponseIsSame\": \"Yes\", \"smallClaimsHearingToggle\": [\"SHOW\"], \"smallClaimsJudgesRecital\": {\"input\": \"Upon considering the statements of case and the information provided by the parties,\"}, \"speclistYourEvidenceList\": [{\"id\": \"24ceb3c5-4af0-4eb5-b039-065088035df4\", \"value\": {\"evidenceType\": \"CONTRACTS_AND_AGREEMENTS\", \"contractAndAgreementsEvidence\": \"evidence details\"}}], \"claimIssuedPaymentDetails\": {\"status\": \"SUCCESS\", \"reference\": \"13213223\", \"customerReference\": \"Applicant reference\"}, \"claimNotificationDeadline\": \"2023-10-26T23:59:59\", \"fastTrackAddNewDirections\": [{\"id\": \"9209c692-13fa-11ee-91f1-d3b5d9925574\", \"value\": {\"directionComment\": \"string\"}}, {\"id\": \"9209c693-13fa-11ee-91f1-d3b5d9925574\", \"value\": {\"directionComment\": \"string\"}}], \"fastTrackHousingDisrepair\": {\"date1\": \"2023-06-27\", \"date2\": \"2023-06-27\", \"input1\": \"string\", \"input2\": \"string\", \"input3\": \"string\", \"input4\": \"string\"}, \"fastTrackSettlementToggle\": [\"SHOW\"], \"gaDetailsMasterCollection\": [{\"id\": \"d25bd5cd-adda-478e-8b6f-1b9949925e3d\", \"value\": {\"caseLink\": {\"CaseReference\": \"1687767784213052\"}, \"caseState\": \"Listed for a Hearing\", \"generalApplicationType\": \"Summary judgment, Extend time\", \"generalAppSubmittedDateGAspec\": \"2023-06-26T09:23:03\"}}], \"sameSolicitorSameResponse\": \"Yes\", \"smallClaimsMethodInPerson\": {\"value\": {\"code\": \"9f5817fb-b7b3-4cd1-b5e5-99f33e995996\", \"label\": \"Barnet Civil and Family Centre - St Mary's Court, Regents Park Road - N3 1BQ\"}, \"list_items\": [{\"code\": \"9f5817fb-b7b3-4cd1-b5e5-99f33e995996\", \"label\": \"Barnet Civil and Family Centre - St Mary's Court, Regents Park Road - N3 1BQ\"}, {\"code\": \"ca851924-d649-4c88-a088-2162aff42c23\", \"label\": \"Central London County Court - THOMAS MORE BUILDING, ROYAL COURTS OF JUSTICE, STRAND, LONDON - WC2A 2LL\"}, {\"code\": \"3c714da9-1a08-46a3-910b-d73ad6754356\", \"label\": \"High Wycombe Law Courts - THE LAW COURTS, EASTON STREET - HP11 1LR\"}, {\"code\": \"accb417d-48a5-4feb-9d39-850c5e621be4\", \"label\": \"Liverpool Civil and Family Court - 35, Vernon Street, City Square - L2 2BX\"}]}, \"applicant1DQRequestedCourt\": {\"caseLocation\": {\"region\": \"2\", \"baseLocation\": \"000000\"}, \"responseCourtCode\": \"121\", \"reasonForHearingAtSpecificCourt\": \"Reasons\"}, \"applicant1ProceedWithClaim\": \"Yes\", \"applicant1ResponseDeadline\": \"2023-07-24T16:00:00\", \"disposalHearingCostsToggle\": [\"SHOW\"], \"disposalHearingHearingTime\": {\"input\": \"This claim will be listed for final disposal before a judge on the first available date after\", \"dateTo\": \"2023-10-16\"}, \"smallClaimsDocumentsToggle\": [\"SHOW\"], \"specRespondent1Represented\": \"Yes\", \"specRespondent2Represented\": \"Yes\", \"disposalHearingBundleToggle\": [\"SHOW\"], \"disposalHearingMethodToggle\": [\"SHOW\"], \"disposalOrderWithoutHearing\": {\"input\": \"This order has been made without hearing. Each party has the right to apply to have this Order set aside or varied. Any such application must be received by the Court (together with the appropriate fee) by 4pm on 03 July 2023.\"}, \"drawDirectionsOrderRequired\": \"Yes\", \"fastTrackClinicalNegligence\": {\"input1\": \"string\", \"input2\": \"string\", \"input3\": \"string\", \"input4\": \"string\"}, \"multiPartyResponseTypeFlags\": \"FULL_DEFENCE\", \"respondent1DQHearingSupport\": {\"requirements\": [\"DISABLED_ACCESS\", \"HEARING_LOOPS\"]}, \"respondent1DQRequestedCourt\": {\"responseCourtCode\": \"121\"}, \"respondent1ResponseDeadline\": \"2023-07-24T16:00:00\", \"smallClaimsWitnessStatement\": {\"text\": \"A witness statement must: \\na) Start with the name of the case and the claim number;\\nb) State the full name and address of the witness; \\nc) Set out the witness's evidence clearly in numbered paragraphs on numbered pages;\\nd) End with this paragraph: 'I believe that the facts stated in this witness statement are true. I understand that proceedings for contempt of court may be brought against anyone who makes, or causes to be made, a false statement in a document verified by a statement of truth without an honest belief in its truth'.\\ne) be signed by the witness and dated.\\nf) If a witness is unable to read the statement there must be a certificate that it has been read or interpreted to the witness by a suitably qualified person and at the final hearing there must be an independent interpreter who will not be provided by the Court.\\n\\nThe judge may refuse to allow a witness to give evidence or consider any statement of any witness whose statement has not been uploaded to the Digital Portal in accordance with the paragraphs above.\\n\\nA witness whose statement has been uploaded in accordance with the above must attend the hearing. If they do not attend, it will be for the court to decide how much reliance, if any, to place on their evidence.\", \"input1\": \"Each party must upload to the Digital Portal copies of all witness statements of the witnesses upon whose evidence they intend to rely at the hearing not less than 14 days before the hearing.\", \"input2\": \"2\", \"input3\": \"2\", \"input4\": \"For this limitation, a party is counted as a witness.\", \"smallClaimsNumberOfWitnessesToggle\": [\"SHOW\"]}, \"specDisputesOrPartAdmission\": \"Yes\", \"applicant1DQStatementOfTruth\": {\"name\": \"Solicitor name\", \"role\": \"Solicitor role\"}, \"applicant1OrganisationPolicy\": {\"Organisation\": {\"OrganisationID\": \"Q1KOKP2\"}, \"OrgPolicyReference\": \"Claimant policy reference\", \"OrgPolicyCaseAssignedRole\": \"[APPLICANTSOLICITORONE]\"}, \"claimantResponseScenarioFlag\": \"ONE_V_TWO_ONE_LEGAL_REP\", \"disposalHearingJudgesRecital\": {\"input\": \"Upon considering the claim form, particulars of claim, statements of case and Directions questionnaires\"}, \"disposalHearingWitnessOfFact\": {\"date2\": \"2023-07-24\", \"date3\": \"2023-08-07\", \"input3\": \"The claimant must upload to the Digital Portal copies of the witness statements of all witnesses of fact on whose evidence reliance is to be placed by 4pm on\", \"input4\": \"The provisions of CPR 32.6 apply to such evidence.\", \"input5\": \"Any application by the defendant in relation to CPR 32.7 must be made by 4pm on\", \"input6\": \"and must be accompanied by proposed directions for allocation and listing for trial on quantum. This is because cross-examination will cause the hearing to exceed the 30-minute maximum time estimate for a disposal hearing.\"}, \"fastTrackRoadTrafficAccident\": {\"date\": \"2023-06-27\", \"input\": \"string\"}, \"fastTrackWitnessOfFactToggle\": [\"SHOW\"], \"systemGeneratedCaseDocuments\": [{\"id\": \"5bcd8a02-24b5-442d-85d2-4342fa89db6b\", \"value\": {\"createdBy\": \"Civil\", \"documentLink\": {\"category_id\": \"detailsOfClaim\", \"document_url\": \"http://dm-store:8080/documents/ac8cc1d8-2662-4ce6-9ddd-0ca172cf4746\", \"document_filename\": \"sealed_claim_form_000MC055.pdf\", \"document_binary_url\": \"http://dm-store:8080/documents/ac8cc1d8-2662-4ce6-9ddd-0ca172cf4746/binary\"}, \"documentName\": \"sealed_claim_form_000MC055.pdf\", \"documentSize\": 77587, \"documentType\": \"SEALED_CLAIM\", \"createdDatetime\": \"2023-06-26T09:22:35\"}}, {\"id\": \"6aa670fd-4b61-40f0-a5b0-1d2fa003f9bc\", \"value\": {\"createdBy\": \"Civil\", \"documentLink\": {\"category_id\": \"defendant1DefenseDirectionsQuestionnaire\", \"document_url\": \"http://dm-store:8080/documents/0e235cf8-3f63-4897-8ae3-16ae2997d247\", \"document_filename\": \"defendant_response_sealed_form.pdf\", \"document_binary_url\": \"http://dm-store:8080/documents/0e235cf8-3f63-4897-8ae3-16ae2997d247/binary\"}, \"documentName\": \"defendant_response_sealed_form.pdf\", \"documentSize\": 73291, \"documentType\": \"DIRECTIONS_QUESTIONNAIRE\", \"createdDatetime\": \"2023-06-26T09:22:48\"}}, {\"id\": \"b338353d-4083-494e-a18c-2cabb2309d0f\", \"value\": {\"createdBy\": \"Civil\", \"documentLink\": {\"category_id\": \"defendant1DefenseDirectionsQuestionnaire\", \"document_url\": \"http://dm-store:8080/documents/77840f85-f769-4719-8c6a-c788ff948f30\", \"document_filename\": \"000MC055_response_sealed_form.pdf\", \"document_binary_url\": \"http://dm-store:8080/documents/77840f85-f769-4719-8c6a-c788ff948f30/binary\"}, \"documentName\": \"000MC055_response_sealed_form.pdf\", \"documentSize\": 55317, \"documentType\": \"SEALED_CLAIM\", \"createdDatetime\": \"2023-06-26T09:22:49\"}}, {\"id\": \"918d48bf-78d9-49e7-b786-d3617343f224\", \"value\": {\"createdBy\": \"Civil\", \"documentLink\": {\"category_id\": \"directionsQuestionnaire\", \"document_url\": \"http://dm-store:8080/documents/922f7fdc-3a78-4864-bdb8-fc7669d543bd\", \"document_filename\": \"claimant_directions_questionnaire_form_000MC055.pdf\", \"document_binary_url\": \"http://dm-store:8080/documents/922f7fdc-3a78-4864-bdb8-fc7669d543bd/binary\"}, \"documentName\": \"claimant_directions_questionnaire_form_000MC055.pdf\", \"documentSize\": 63408, \"documentType\": \"DIRECTIONS_QUESTIONNAIRE\", \"createdDatetime\": \"2023-06-26T09:22:59\"}}], \"applicant1DQSmallClaimHearing\": {\"unavailableDatesRequired\": \"No\"}, \"disposalHearingMethodInPerson\": {\"value\": {\"code\": \"9f5817fb-b7b3-4cd1-b5e5-99f33e995996\", \"label\": \"Barnet Civil and Family Centre - St Mary's Court, Regents Park Road - N3 1BQ\"}, \"list_items\": [{\"code\": \"9f5817fb-b7b3-4cd1-b5e5-99f33e995996\", \"label\": \"Barnet Civil and Family Centre - St Mary's Court, Regents Park Road - N3 1BQ\"}, {\"code\": \"ca851924-d649-4c88-a088-2162aff42c23\", \"label\": \"Central London County Court - THOMAS MORE BUILDING, ROYAL COURTS OF JUSTICE, STRAND, LONDON - WC2A 2LL\"}, {\"code\": \"3c714da9-1a08-46a3-910b-d73ad6754356\", \"label\": \"High Wycombe Law Courts - THE LAW COURTS, EASTON STREET - HP11 1LR\"}, {\"code\": \"accb417d-48a5-4feb-9d39-850c5e621be4\", \"label\": \"Liverpool Civil and Family Court - 35, Vernon Street, City Square - L2 2BX\"}]}, \"respondent1DQStatementOfTruth\": {\"name\": \"Test\", \"role\": \"Worker\"}, \"respondent1OrganisationPolicy\": {\"Organisation\": {\"OrganisationID\": \"79ZRSOU\"}, \"OrgPolicyCaseAssignedRole\": \"[RESPONDENTSOLICITORONE]\"}, \"respondent2OrganisationPolicy\": {\"Organisation\": {\"OrganisationID\": \"H2156A0\"}, \"OrgPolicyCaseAssignedRole\": \"[RESPONDENTSOLICITORTWO]\"}, \"specClaimResponseTimelineList\": \"MANUAL\", \"applicantSolicitor1PbaAccounts\": {\"value\": {\"code\": \"91fca730-13fa-11ee-91f1-d3b5d9925574\", \"label\": \"PBA0088192\"}, \"list_items\": [{\"code\": \"91fca730-13fa-11ee-91f1-d3b5d9925574\", \"label\": \"PBA0088192\"}, {\"code\": \"91fca731-13fa-11ee-91f1-d3b5d9925574\", \"label\": \"PBA0078095\"}]}, \"applicantSolicitor1UserDetails\": {\"id\": \"e286ef3b-3766-4a22-888d-b81cfe544deb\", \"email\": \"hmcts.civil+organisation.1.solicitor.1@gmail.com\"}, \"disposalHearingMedicalEvidence\": {\"date\": \"2023-07-24\", \"input\": \"The claimant has permission to rely upon the written expert evidence already uploaded to the Digital Portal with the particulars of claim and in addition has permission to rely upon any associated correspondence or updating report which is uploaded to the Digital Portal by 4pm on\"}, \"disposalHearingSchedulesOfLoss\": {\"date2\": \"2023-09-04\", \"date3\": \"2023-09-18\", \"date4\": \"2023-09-18\", \"input2\": \"If there is a claim for ongoing or future loss in the original schedule of losses, the claimant must upload to the Digital Portal an up-to-date schedule of loss by 4pm on\", \"input3\": \"If the defendant wants to challenge this claim, they must send an up-to-date counter-schedule of loss to the claimant by 4pm on\", \"input4\": \"If the defendant want to challenge the sums claimed in the schedule of loss they must upload to the Digital Portal an updated counter schedule of loss by 4pm on\"}, \"drawDirectionsOrderSmallClaims\": \"No\", \"fastTrackDisclosureOfDocuments\": {\"date1\": \"2023-06-25\", \"date2\": \"2023-06-25\", \"date3\": \"2023-06-25\", \"input1\": \"string\", \"input2\": \"string\", \"input3\": \"string\", \"input4\": \"string\"}, \"fastTrackOrderWithoutJudgement\": {\"input\": \"This order has been made without hearing. Each party has the right to apply to have this Order set aside or varied. Any such application must be received by the Court (together with the appropriate fee) by 4pm on 03 July 2023.\"}, \"fastTrackSchedulesOfLossToggle\": [\"SHOW\"], \"fullAdmissionAndFullAmountPaid\": \"No\", \"respondent1DQHearingSmallClaim\": {\"unavailableDatesRequired\": \"No\"}, \"smallClaimsRoadTrafficAccident\": {\"input\": \"Photographs and/or a place of the accident location shall be prepared and agreed by the parties and uploaded to the Digital Portal no later than 14 days before the hearing.\"}, \"specFullDefenceOrPartAdmission\": \"Yes\", \"claimAmountBreakupSummaryObject\": \" | Description | Amount | \\n |---|---| \\n | amount reason | £ 1500.00 |\\n  | **Total** | £ 1500.00 | \", \"fastTrackMethodTelephoneHearing\": \"telephoneTheClaimant\", \"partAdmittedByEitherRespondents\": \"No\", \"responseClaimExpertSpecRequired\": \"No\", \"specDefenceFullAdmittedRequired\": \"No\", \"respondentSolicitor1EmailAddress\": \"civilunspecified@gmail.com\", \"respondentSolicitor2EmailAddress\": \"civilunspecified@gmail.com\", \"specFullAdmissionOrPartAdmission\": \"No\", \"applicant1ClaimExpertSpecRequired\": \"No\", \"disposalHearingQuestionsToExperts\": {\"date\": \"2023-08-07\"}, \"smallClaimsWitnessStatementToggle\": [\"SHOW\"], \"specFullDefenceOrPartAdmission1V1\": \"Yes\", \"applicant1DQVulnerabilityQuestions\": {\"vulnerabilityAdjustmentsRequired\": \"No\"}, \"detailsOfWhyDoesYouDisputeTheClaim\": \"details\", \"disposalHearingClaimSettlingToggle\": [\"SHOW\"], \"disposalHearingWitnessOfFactToggle\": [\"SHOW\"], \"respondent2SameLegalRepresentative\": \"Yes\", \"responseClaimCourtLocationRequired\": \"Yes\", \"responseClaimMediationSpecRequired\": \"No\", \"disposalHearingFinalDisposalHearing\": {\"date\": \"2023-10-16\", \"input\": \"This claim will be listed for final disposal before a judge on the first available date after\"}, \"fastTrackAltDisputeResolutionToggle\": [\"SHOW\"], \"respondent1ClaimResponseTypeForSpec\": \"FULL_DEFENCE\", \"respondent1DQVulnerabilityQuestions\": {\"vulnerabilityAdjustments\": \"test\", \"vulnerabilityAdjustmentsRequired\": \"Yes\"}, \"respondent2ClaimResponseTypeForSpec\": \"FULL_DEFENCE\", \"disposalHearingDisclosureOfDocuments\": {\"date1\": \"2023-09-04\", \"date2\": \"2023-09-04\", \"input1\": \"The parties shall serve on each other copies of the documents upon which reliance is to be placed at the disposal hearing by 4pm on\", \"input2\": \"The parties must upload to the Digital Portal copies of those documents which they wish the court to consider when deciding the amount of damages, by 4pm on\"}, \"disposalHearingMedicalEvidenceToggle\": [\"SHOW\"], \"disposalHearingSchedulesOfLossToggle\": [\"SHOW\"], \"fastTrackDisclosureOfDocumentsToggle\": [\"SHOW\"], \"fastTrackVariationOfDirectionsToggle\": [\"SHOW\"], \"respondent1DetailsForClaimDetailsTab\": {\"type\": \"INDIVIDUAL\", \"flags\": {\"partyName\": \"Sir John Doe\", \"roleOnCase\": \"Respondent 1\"}, \"partyID\": \"8597196f-f426-49\", \"partyName\": \"Sir John Doe\", \"primaryAddress\": {\"County\": \"Kent\", \"Country\": \"United Kingdom\", \"PostCode\": \"RG4 7AA\", \"PostTown\": \"Reading\", \"AddressLine1\": \"Flat 2 - respondent\", \"AddressLine2\": \"Caversham House 15-17\", \"AddressLine3\": \"Church Road\"}, \"individualTitle\": \"Sir\", \"individualLastName\": \"Doe\", \"individualFirstName\": \"John\", \"partyTypeDisplayValue\": \"Individual\"}, \"respondent2DetailsForClaimDetailsTab\": {\"type\": \"ORGANISATION\", \"flags\": {\"partyName\": \"Second Defendant\", \"roleOnCase\": \"Respondent 2\"}, \"partyID\": \"6ce7a978-0904-4c\", \"partyName\": \"Second Defendant\", \"primaryAddress\": {\"PostCode\": \"NR5 9LL\", \"PostTown\": \"Second Town\", \"AddressLine1\": \"123 Second Close\"}, \"organisationName\": \"Second Defendant\", \"partyTypeDisplayValue\": \"Organisation\"}, \"applicantMPClaimMediationSpecRequired\": {\"hasAgreedFreeMediation\": \"Yes\"}, \"applicantSolicitor1PbaAccountsIsEmpty\": \"No\", \"specAoSRespondent2HomeAddressRequired\": \"Yes\", \"defenceAdmitPartPaymentTimeRouteGeneric\": \"IMMEDIATELY\", \"disposalHearingQuestionsToExpertsToggle\": [\"SHOW\"], \"applicantSolicitor1ClaimStatementOfTruth\": {\"name\": \"John Doe\", \"role\": \"Test Solicitor\"}, \"disposalHearingFinalDisposalHearingToggle\": [\"SHOW\"], \"respondentClaimResponseTypeForSpecGeneric\": \"FULL_DEFENCE\", \"disposalHearingDisclosureOfDocumentsToggle\": [\"SHOW\"], \"specApplicantCorrespondenceAddressRequired\": \"No\", \"specPaidLessAmountOrDisputesOrPartAdmission\": \"Yes\", \"specRespondentCorrespondenceAddressRequired\": \"No\", \"specAoSApplicantCorrespondenceAddressRequired\": \"Yes\", \"respondent1ClaimResponsePaymentAdmissionForSpec\": \"DID_NOT_PAY\"}";
    String rawHearingScheduledString = "{\"claimFee\": {\"code\": \"FEE0205\", \"version\": \"4\", \"calculatedAmountInPence\": \"8000\"}, \"issueDate\": \"2023-06-26\", \"orderType\": \"DECIDE_DAMAGES\", \"applicant1\": {\"type\": \"COMPANY\", \"flags\": {\"partyName\": \"Test Inc\", \"roleOnCase\": \"Applicant 1\"}, \"partyID\": \"15487493-bd6e-49\", \"partyName\": \"Test Inc\", \"companyName\": \"Test Inc\", \"primaryAddress\": {\"County\": \"Kent\", \"Country\": \"United Kingdom\", \"PostCode\": \"RG4 7AA\", \"PostTown\": \"Reading\", \"AddressLine1\": \"Flat 2 - applicant\", \"AddressLine2\": \"Caversham House 15-17\", \"AddressLine3\": \"Church Road\"}, \"partyTypeDisplayValue\": \"Company\"}, \"respondent1\": {\"type\": \"INDIVIDUAL\", \"flags\": {\"partyName\": \"Sir John Doe\", \"roleOnCase\": \"Respondent 1\"}, \"partyID\": \"8597196f-f426-49\", \"partyName\": \"Sir John Doe\", \"primaryAddress\": {\"County\": \"Kent\", \"Country\": \"United Kingdom\", \"PostCode\": \"RG4 7AA\", \"PostTown\": \"Reading\", \"AddressLine1\": \"Flat 2 - respondent\", \"AddressLine2\": \"Caversham House 15-17\", \"AddressLine3\": \"Church Road\"}, \"individualTitle\": \"Sir\", \"individualLastName\": \"Doe\", \"individualFirstName\": \"John\", \"partyTypeDisplayValue\": \"Individual\"}, \"respondent2\": {\"type\": \"ORGANISATION\", \"flags\": {\"partyName\": \"Second Defendant\", \"roleOnCase\": \"Respondent 2\"}, \"partyID\": \"6ce7a978-0904-4c\", \"partyName\": \"Second Defendant\", \"primaryAddress\": {\"PostCode\": \"NR5 9LL\", \"PostTown\": \"Second Town\", \"AddressLine1\": \"123 Second Close\"}, \"organisationName\": \"Second Defendant\", \"partyTypeDisplayValue\": \"Organisation\"}, \"locationName\": \"Barnet Civil and Family Centre\", \"addApplicant2\": \"No\", \"claimInterest\": \"No\", \"submittedDate\": \"2023-06-26T09:22:28\", \"totalInterest\": 0, \"SearchCriteria\": {\"SearchParties\": [{\"id\": \"09f119de-8180-4766-9bfa-8ede06aa744d\", \"value\": {\"Name\": \"Sir John Doe\", \"PostCode\": \"RG4 7AA\", \"AddressLine1\": \"Flat 2 - respondent\", \"EmailAddress\": \"civilunspecified@gmail.com\"}}, {\"id\": \"33a78239-29f7-45fc-a1b2-48df8a99116d\", \"value\": {\"Name\": \"Second Defendant\", \"PostCode\": \"NR5 9LL\", \"AddressLine1\": \"123 Second Close\", \"EmailAddress\": \"civilunspecified@gmail.com\"}}, {\"id\": \"43588253-b18c-4771-83c9-465cd486c733\", \"value\": {\"Name\": \"Test Inc\", \"PostCode\": \"RG4 7AA\", \"AddressLine1\": \"Flat 2 - applicant\", \"EmailAddress\": \"hmcts.civil+organisation.1.solicitor.1@gmail.com\"}}], \"OtherCaseReferences\": [{\"id\": \"68047e1f-2733-4e3b-91b2-b7aead1585b1\", \"value\": \"000MC055\"}]}, \"addRespondent2\": \"Yes\", \"detailsOfClaim\": \"Test details of claim\", \"fastTrackNotes\": {\"date\": \"2023-06-27\", \"input\": \"string\"}, \"fastTrackTrial\": {\"type\": [\"DOCUMENTS\"], \"date1\": \"2023-06-27\", \"date2\": \"2023-06-27\", \"input1\": \"string\", \"input2\": \"string\", \"input3\": \"string\"}, \"businessProcess\": {\"status\": \"FINISHED\", \"camundaEvent\": \"CREATE_SDO\"}, \"fastTrackMethod\": \"fastTrackMethodTelephoneHearing\", \"gaDraftDocStaff\": [{\"id\": \"82e37cfe-2737-4114-9fa5-d4ca84e3fe03\", \"value\": {\"createdBy\": \"Civil\", \"documentLink\": {\"category_id\": \"applications\", \"document_url\": \"http://dm-store:8080/documents/95f2105e-cf0f-4043-a953-2faf136283df\", \"document_filename\": \"Draft_application_2023-06-26 09:23:10.pdf\", \"document_binary_url\": \"http://dm-store:8080/documents/95f2105e-cf0f-4043-a953-2faf136283df/binary\"}, \"documentName\": \"Draft_application_2023-06-26 09:23:10.pdf\", \"documentSize\": 44185, \"documentType\": \"GENERAL_APPLICATION_DRAFT\", \"createdDatetime\": \"2023-06-26T09:23:10\"}}], \"respondent1Copy\": {\"type\": \"INDIVIDUAL\", \"flags\": {\"partyName\": \"Sir John Doe\", \"roleOnCase\": \"Respondent 1\"}, \"partyID\": \"8597196f-f426-49\", \"partyName\": \"Sir John Doe\", \"primaryAddress\": {\"County\": \"Kent\", \"Country\": \"United Kingdom\", \"PostCode\": \"RG4 7AA\", \"PostTown\": \"Reading\", \"AddressLine1\": \"Flat 2 - respondent\", \"AddressLine2\": \"Caversham House 15-17\", \"AddressLine3\": \"Church Road\"}, \"individualTitle\": \"Sir\", \"individualLastName\": \"Doe\", \"individualFirstName\": \"John\", \"partyTypeDisplayValue\": \"Individual\"}, \"smallClaimsNotes\": {\"input\": \"This order has been made without hearing. Each party has the right to apply to have this Order set aside or varied. Any such application must be received by the Court (together with the appropriate fee) by 4pm on 3 July 2023\"}, \"timelineOfEvents\": [{\"id\": \"689a7382-3aa6-4cb3-8ad6-63f2792a5d63\", \"value\": {\"timelineDate\": \"2021-02-01\", \"timelineDescription\": \"event 1\"}}], \"totalClaimAmount\": 1500, \"smallClaimsMethod\": \"smallClaimsMethodInPerson\", \"CaseAccessCategory\": \"SPEC_CLAIM\", \"calculatedInterest\": \" | Description | Amount | \\n |---|---| \\n | Claim amount | £ 1500 | \\n | Interest amount | £ 0 | \\n | Total amount | £ 1500 |\", \"claimAmountBreakup\": [{\"id\": \"e52acaf0-4638-4b7d-8a8c-814f9dfcedf6\", \"value\": {\"claimAmount\": \"150000\", \"claimReason\": \"amount reason\"}}], \"gaDraftDocClaimant\": [{\"id\": \"82e37cfe-2737-4114-9fa5-d4ca84e3fe03\", \"value\": {\"createdBy\": \"Civil\", \"documentLink\": {\"category_id\": \"applications\", \"document_url\": \"http://dm-store:8080/documents/95f2105e-cf0f-4043-a953-2faf136283df\", \"document_filename\": \"Draft_application_2023-06-26 09:23:10.pdf\", \"document_binary_url\": \"http://dm-store:8080/documents/95f2105e-cf0f-4043-a953-2faf136283df/binary\"}, \"documentName\": \"Draft_application_2023-06-26 09:23:10.pdf\", \"documentSize\": 44185, \"documentType\": \"GENERAL_APPLICATION_DRAFT\", \"createdDatetime\": \"2023-06-26T09:23:10\"}}], \"paymentTypePBASpec\": \"PBAv3\", \"responseClaimTrack\": \"SMALL_CLAIM\", \"showConditionFlags\": [\"CAN_ANSWER_RESPONDENT_2\", \"BOTH_RESPONDENTS_DISPUTE\", \"SOMEONE_DISPUTES\", \"CAN_ANSWER_RESPONDENT_1\"], \"smallClaimsHearing\": {\"input1\": \"The hearing of the claim will be on a date to be notified to you by a separate notification. The hearing will have a time estimate of\", \"input2\": \"The claimant must by no later than 4 weeks before the hearing date, pay the court the required hearing fee or submit a fully completed application for Help with Fees. \\nIf the claimant fails to pay the fee or obtain a fee exemption by that time the claim will be struck without further order.\"}, \"applicant1DQHearing\": {\"unavailableDatesRequired\": \"No\"}, \"drawDirectionsOrder\": {\"judgementSum\": \"20.0\"}, \"fastTrackCreditHire\": {\"date1\": \"2023-06-27\", \"date2\": \"2023-06-27\", \"date3\": \"2023-06-27\", \"date4\": \"2023-06-27\", \"input1\": \"string\", \"input2\": \"string\", \"input3\": \"string\", \"input4\": \"string\", \"input5\": \"string\", \"input6\": \"string\", \"input7\": \"string\", \"input8\": \"string\"}, \"generalApplications\": [{\"id\": \"cfea9f9e-ac04-4f57-9de9-bbf3cac0c422\", \"value\": {\"caseLink\": {\"CaseReference\": \"1687767784213052\"}, \"isMultiParty\": \"Yes\", \"locationName\": \"Barnet Civil and Family Centre\", \"generalAppType\": {\"types\": [\"SUMMARY_JUDGEMENT\", \"EXTEND_TIME\"]}, \"businessProcess\": {\"status\": \"FINISHED\", \"camundaEvent\": \"CREATE_GENERAL_APPLICATION_CASE\", \"processInstanceId\": \"ab6d6908-13fa-11ee-a28c-0242ac120002\"}, \"isCcmccLocation\": \"No\", \"CaseAccessCategory\": \"SPEC_CLAIM\", \"applicantPartyName\": \"Test Inc\", \"claimant1PartyName\": \"Test Inc\", \"defendant1PartyName\": \"Sir John Doe\", \"defendant2PartyName\": \"Second Defendant\", \"generalAppPBADetails\": {\"fee\": {\"code\": \"FEE0443\", \"version\": \"2\", \"calculatedAmountInPence\": \"10800\"}}, \"civilServiceUserRoles\": {\"id\": \"e286ef3b-3766-4a22-888d-b81cfe544deb\", \"email\": \"hmcts.civil+organisation.1.solicitor.1@gmail.com\"}, \"caseManagementCategory\": {\"value\": {\"code\": \"Civil\", \"label\": \"Civil\"}, \"list_items\": [{\"id\": \"2fd349fd-5b7e-4260-836e-481c8beb3fe8\", \"value\": {\"code\": \"Civil\", \"label\": \"Civil\"}}]}, \"caseManagementLocation\": {\"region\": \"2\", \"siteName\": \"Barnet Civil and Family Centre\", \"baseLocation\": \"000000\"}, \"gaApplicantDisplayName\": \"Test Inc - Claimant\", \"generalAppDateDeadline\": \"2023-07-01T16:00:00\", \"generalAppApplnSolicitor\": {\"id\": \"e286ef3b-3766-4a22-888d-b81cfe544deb\", \"email\": \"hmcts.civil+organisation.1.solicitor.1@gmail.com\", \"surname\": \"Claimant-solicitor\", \"forename\": \"hmcts.civil+organisation.1.solicitor.1@gmail.com\", \"organisationIdentifier\": \"Q1KOKP2\"}, \"generalAppDetailsOfOrder\": \"Test Order details\", \"generalAppHearingDetails\": {\"hearingYesorNo\": \"No\", \"HearingDuration\": \"MINUTES_15\", \"judgeRequiredYesOrNo\": \"No\", \"trialRequiredYesOrNo\": \"No\", \"HearingDetailsEmailID\": \"update@gh.com\", \"vulnerabilityQuestion\": \"Test Test\", \"HearingDetailsTelephoneNumber\": \"07446778166\", \"ReasonForPreferredHearingType\": \"sdsd\", \"vulnerabilityQuestionsYesOrNo\": \"Yes\", \"HearingPreferencesPreferredType\": \"IN_PERSON\", \"unavailableTrialRequiredYesOrNo\": \"No\"}, \"generalAppReasonsOfOrder\": \"Test reason for order\", \"generalAppSuperClaimType\": \"SPEC_CLAIM\", \"parentClaimantIsApplicant\": \"Yes\", \"generalAppInformOtherParty\": {\"isWithNotice\": \"No\", \"reasonsForWithoutNotice\": \"Test 123\"}, \"generalAppStatementOfTruth\": {\"name\": \"John Doe\", \"role\": \"Test Solicitor\"}, \"generalAppUrgencyRequirement\": {\"generalAppUrgency\": \"No\"}, \"generalAppRespondentAgreement\": {\"hasAgreed\": \"No\"}, \"generalAppSubmittedDateGAspec\": \"2023-06-26T09:23:03\", \"generalAppRespondentSolicitors\": [{\"id\": \"cfe5da54-a25c-4866-9ada-c77dfb41dcb2\", \"value\": {\"id\": \"65173a08-84c3-46ba-b58c-f8e839682a84\", \"email\": \"civilunspecified@gmail.com\", \"organisationIdentifier\": \"79ZRSOU\"}}]}}], \"legacyCaseReference\": \"000MC055\", \"solicitorReferences\": {\"applicantSolicitor1Reference\": \"Applicant reference\", \"respondentSolicitor1Reference\": \"Respondent reference\", \"respondentSolicitor2Reference\": \"Respondent reference\"}, \"applicant1DQLanguage\": {\"court\": \"ENGLISH\", \"evidence\": \"ENGLISH\", \"documents\": \"ENGLISH\"}, \"claimantGaAppDetails\": [{\"id\": \"51da01e1-5d8c-4347-9a41-3014bf81f662\", \"value\": {\"caseLink\": {\"CaseReference\": \"1687767784213052\"}, \"caseState\": \"Hearing Scheduled\", \"generalApplicationType\": \"Summary judgment, Extend time\", \"generalAppSubmittedDateGAspec\": \"2023-06-26T09:23:03\"}}], \"defenceRouteRequired\": \"DISPUTES_THE_CLAIM\", \"disposalHearingNotes\": {\"date\": \"2023-07-03\", \"input\": \"This Order has been made without a hearing. Each party has the right to apply to have this Order set aside or varied. Any such application must be uploaded to the Digital Portal together with the appropriate fee, by 4pm on\"}, \"fastTrackCostsToggle\": [\"SHOW\"], \"fastTrackHearingTime\": {\"dateTo\": \"2024-01-22\", \"dateFrom\": \"2023-11-27\", \"helpText1\": \"If either party considers that the time estimate is insufficient, they must inform the court within 7 days of the date of this order.\", \"helpText2\": \"Not more than seven nor less than three clear days before the trial, the claimant must file at court and serve an indexed and paginated bundle of documents which complies with the requirements of Rule 39.5 Civil Procedure Rules and which complies with requirements of PD32. The parties must endeavour to agree the contents of the bundle before it is filed. The bundle will include a case summary and a chronology.\", \"dateToToggle\": [\"SHOW\"]}, \"fastTrackTrialToggle\": [\"SHOW\"], \"hearingOrderDocStaff\": [{\"id\": \"c96021d5-81a7-4926-a4bc-7aa580ac2fa3\", \"value\": {\"createdBy\": \"Civil\", \"documentLink\": {\"category_id\": \"applications\", \"document_url\": \"http://dm-store:8080/documents/7741775c-d822-4959-b135-85e9bd72cda7\", \"document_filename\": \"Hearing_order_for_application_2023-06-26 09:23:41.pdf\", \"document_binary_url\": \"http://dm-store:8080/documents/7741775c-d822-4959-b135-85e9bd72cda7/binary\"}, \"documentName\": \"Hearing_order_for_application_2023-06-26 09:23:41.pdf\", \"documentSize\": 36425, \"documentType\": \"HEARING_ORDER\", \"createdDatetime\": \"2023-06-26T09:23:41\"}}], \"respondent1DQHearing\": {\"unavailableDatesRequired\": \"No\"}, \"smallClaimsDocuments\": {\"input1\": \"Each party must upload to the Digital Portal copies of all documents which they wish the court to consider when reaching its decision not less than 14 days before the hearing.\", \"input2\": \"The court may refuse to consider any document which has not been uploaded to the Digital Portal by the above date.\"}, \"caseNameHmctsInternal\": \"Test Inc v Sir John Doe and Second Defendant\", \"claimIssuedPBADetails\": {\"fee\": {\"code\": \"FEE0205\", \"version\": \"4\", \"calculatedAmountInPence\": \"8000\"}, \"applicantsPbaAccounts\": {\"value\": {\"code\": \"91fca730-13fa-11ee-91f1-d3b5d9925574\", \"label\": \"PBA0088192\"}, \"list_items\": [{\"code\": \"91fca730-13fa-11ee-91f1-d3b5d9925574\", \"label\": \"PBA0088192\"}, {\"code\": \"91fca731-13fa-11ee-91f1-d3b5d9925574\", \"label\": \"PBA0078095\"}]}, \"serviceRequestReference\": \"2022-1655915218557\"}, \"claimNotificationDate\": \"2023-06-26T09:22:37\", \"disposalHearingBundle\": {\"input\": \"At least 7 days before the disposal hearing, the claimant must file and serve\"}, \"fastTrackMethodToggle\": [\"SHOW\"], \"hearingNoticeDocStaff\": [{\"id\": \"150be9a5-885c-4d62-a917-b989be1949e4\", \"value\": {\"createdBy\": \"Civil\", \"documentLink\": {\"category_id\": \"applications\", \"document_url\": \"http://dm-store:8080/documents/72092696-8bb1-4e99-8754-1a719faa5563\", \"document_filename\": \"Application_Hearing_Notice_2023-06-26 09:30:54.pdf\", \"document_binary_url\": \"http://dm-store:8080/documents/72092696-8bb1-4e99-8754-1a719faa5563/binary\"}, \"documentName\": \"Application_Hearing_Notice_2023-06-26 09:30:54.pdf\", \"documentSize\": 53977, \"documentType\": \"HEARING_NOTICE\", \"createdDatetime\": \"2023-06-26T09:30:54\"}}], \"respondent1DQLanguage\": {\"court\": \"ENGLISH\", \"evidence\": \"ENGLISH\", \"documents\": \"ENGLISH\"}, \"smallClaimsCreditHire\": {\"date1\": \"2023-07-24\", \"date2\": \"2023-08-07\", \"date3\": \"2023-08-21\", \"date4\": \"2023-09-04\", \"input1\": \"If impecuniosity is alleged by the claimant and not admitted by the defendant, the claimant's disclosure as ordered earlier in this Order must include:\\na) Evidence of all income from all sources for a period of 3 months prior to the commencement of hire until the earlier of:\\n      i) 3 months after cessation of hire\\n     ii) the repair or replacement of the claimant's vehicle\\nb) Copies of all bank, credit card, and saving account statements for a period of 3 months prior to the commencement of hire until the earlier of:\\n     i) 3 months after cessation of hire\\n     ii) the repair or replacement of the claimant's vehicle\\nc) Evidence of any loan, overdraft or other credit facilities available to the claimant.\", \"input2\": \"The claimant must upload to the Digital Portal a witness statement addressing\\na) the need to hire a replacement vehicle; and\\nb) impecuniosity\", \"input3\": \"A failure to comply with the paragraph above will result in the claimant being debarred from asserting need or relying on impecuniosity as the case may be at the final hearing, save with permission of the Trial Judge.\", \"input4\": \"The parties are to liaise and use reasonable endeavours to agree the basic hire rate no later than 4pm on\", \"input5\": \"If the parties fail to agree rates subject to liability and/or other issues pursuant to the paragraph above, each party may rely upon written evidence by way of witness statement of one witness to provide evidence of basic hire rates available within the claimant's geographical location, from a mainstream supplier, or a local reputable supplier if none is available.\", \"input6\": \"The defendant's evidence is to be uploaded to the Digital Portal by 4pm on\", \"input7\": \"and the claimant's evidence in reply if so advised to be uploaded by 4pm on\", \"input11\": \"This witness statement is limited to 10 pages per party, including any appendices.\"}, \"applicant1ResponseDate\": \"2023-06-26T09:22:57\", \"caseManagementCategory\": {\"value\": {\"code\": \"Civil\", \"label\": \"Civil\"}, \"list_items\": [{\"id\": \"8c941350-1428-4363-be82-5e37a1b16816\", \"value\": {\"code\": \"Civil\", \"label\": \"Civil\"}}]}, \"caseManagementLocation\": {\"region\": \"2\", \"baseLocation\": \"000000\"}, \"fastTrackJudgesRecital\": {\"input\": \"string\"}, \"fastTrackWitnessOfFact\": {\"date\": \"2023-06-27\", \"input1\": \"string\", \"input2\": \"1\", \"input3\": \"1\", \"input4\": \"string\", \"input5\": \"string\", \"input6\": \"1\", \"input7\": \"string\", \"input8\": \"string\", \"input9\": \"string\"}, \"respondToCourtLocation\": {\"responseCourtCode\": \"121\"}, \"respondent1Represented\": \"Yes\", \"respondent2Represented\": \"Yes\", \"responseClaimWitnesses\": \"10\", \"fastTrackMethodInPerson\": {\"value\": {\"code\": \"9f5817fb-b7b3-4cd1-b5e5-99f33e995996\", \"label\": \"Barnet Civil and Family Centre - St Mary's Court, Regents Park Road - N3 1BQ\"}, \"list_items\": [{\"code\": \"9f5817fb-b7b3-4cd1-b5e5-99f33e995996\", \"label\": \"Barnet Civil and Family Centre - St Mary's Court, Regents Park Road - N3 1BQ\"}, {\"code\": \"ca851924-d649-4c88-a088-2162aff42c23\", \"label\": \"Central London County Court - THOMAS MORE BUILDING, ROYAL COURTS OF JUSTICE, STRAND, LONDON - WC2A 2LL\"}, {\"code\": \"3c714da9-1a08-46a3-910b-d73ad6754356\", \"label\": \"High Wycombe Law Courts - THE LAW COURTS, EASTON STREET - HP11 1LR\"}, {\"code\": \"accb417d-48a5-4feb-9d39-850c5e621be4\", \"label\": \"Liverpool Civil and Family Court - 35, Vernon Street, City Square - L2 2BX\"}]}, \"fastTrackPersonalInjury\": {\"date1\": \"2023-06-27\", \"date2\": \"2023-06-27\", \"input1\": \"string\", \"input2\": \"string\", \"input3\": \"string\"}, \"hearingOrderDocClaimant\": [{\"id\": \"c96021d5-81a7-4926-a4bc-7aa580ac2fa3\", \"value\": {\"createdBy\": \"Civil\", \"documentLink\": {\"category_id\": \"applications\", \"document_url\": \"http://dm-store:8080/documents/7741775c-d822-4959-b135-85e9bd72cda7\", \"document_filename\": \"Hearing_order_for_application_2023-06-26 09:23:41.pdf\", \"document_binary_url\": \"http://dm-store:8080/documents/7741775c-d822-4959-b135-85e9bd72cda7/binary\"}, \"documentName\": \"Hearing_order_for_application_2023-06-26 09:23:41.pdf\", \"documentSize\": 36425, \"documentType\": \"HEARING_ORDER\", \"createdDatetime\": \"2023-06-26T09:23:41\"}}], \"respondToCourtLocation2\": {\"responseCourtLocations\": {\"list_items\": [{\"code\": \"32b1cbc3-3d4b-406e-b58c-cc1ea2a38ce9\", \"label\": \"Barnet Civil and Family Centre - St Mary's Court, Regents Park Road - N3 1BQ\"}, {\"code\": \"c1b6b150-1f5e-4bac-bf22-dd27330780de\", \"label\": \"Central London County Court - THOMAS MORE BUILDING, ROYAL COURTS OF JUSTICE, STRAND, LONDON - WC2A 2LL\"}, {\"code\": \"aad0e24f-0dbe-4c38-969f-33b2b426cc7c\", \"label\": \"High Wycombe Law Courts - THE LAW COURTS, EASTON STREET - HP11 1LR\"}, {\"code\": \"fa59ed15-b2b4-40cc-9018-f43d8c28a169\", \"label\": \"Liverpool Civil and Family Court - 35, Vernon Street, City Square - L2 2BX\"}]}}, \"respondent1ResponseDate\": \"2023-06-26T09:22:44\", \"respondent2ResponseDate\": \"2023-06-26T09:22:44\", \"smallClaimsMethodToggle\": [\"SHOW\"], \"applicant1ClaimWitnesses\": \"10\", \"fastTrackBuildingDispute\": {\"date1\": \"2023-06-27\", \"date2\": \"2023-06-27\", \"input1\": \"string\", \"input2\": \"string\", \"input3\": \"string\", \"input4\": \"string\"}, \"fastTrackSchedulesOfLoss\": {\"date1\": \"2023-06-27\", \"date2\": \"2023-06-27\", \"input1\": \"string\", \"input2\": \"string\", \"input3\": \"string\"}, \"hearingNoticeDocClaimant\": [{\"id\": \"150be9a5-885c-4d62-a917-b989be1949e4\", \"value\": {\"createdBy\": \"Civil\", \"documentLink\": {\"category_id\": \"applications\", \"document_url\": \"http://dm-store:8080/documents/72092696-8bb1-4e99-8754-1a719faa5563\", \"document_filename\": \"Application_Hearing_Notice_2023-06-26 09:30:54.pdf\", \"document_binary_url\": \"http://dm-store:8080/documents/72092696-8bb1-4e99-8754-1a719faa5563/binary\"}, \"documentName\": \"Application_Hearing_Notice_2023-06-26 09:30:54.pdf\", \"documentSize\": 53977, \"documentType\": \"HEARING_NOTICE\", \"createdDatetime\": \"2023-06-26T09:30:54\"}}], \"respondent1OrgRegistered\": \"Yes\", \"respondent2OrgRegistered\": \"Yes\", \"respondentResponseIsSame\": \"Yes\", \"smallClaimsHearingToggle\": [\"SHOW\"], \"smallClaimsJudgesRecital\": {\"input\": \"Upon considering the statements of case and the information provided by the parties,\"}, \"speclistYourEvidenceList\": [{\"id\": \"24ceb3c5-4af0-4eb5-b039-065088035df4\", \"value\": {\"evidenceType\": \"CONTRACTS_AND_AGREEMENTS\", \"contractAndAgreementsEvidence\": \"evidence details\"}}], \"claimIssuedPaymentDetails\": {\"status\": \"SUCCESS\", \"reference\": \"13213223\", \"customerReference\": \"Applicant reference\"}, \"claimNotificationDeadline\": \"2023-10-26T23:59:59\", \"fastTrackAddNewDirections\": [{\"id\": \"9209c692-13fa-11ee-91f1-d3b5d9925574\", \"value\": {\"directionComment\": \"string\"}}, {\"id\": \"9209c693-13fa-11ee-91f1-d3b5d9925574\", \"value\": {\"directionComment\": \"string\"}}], \"fastTrackHousingDisrepair\": {\"date1\": \"2023-06-27\", \"date2\": \"2023-06-27\", \"input1\": \"string\", \"input2\": \"string\", \"input3\": \"string\", \"input4\": \"string\"}, \"fastTrackSettlementToggle\": [\"SHOW\"], \"gaDetailsMasterCollection\": [{\"id\": \"d25bd5cd-adda-478e-8b6f-1b9949925e3d\", \"value\": {\"caseLink\": {\"CaseReference\": \"1687767784213052\"}, \"caseState\": \"Hearing Scheduled\", \"generalApplicationType\": \"Summary judgment, Extend time\", \"generalAppSubmittedDateGAspec\": \"2023-06-26T09:23:03\"}}], \"sameSolicitorSameResponse\": \"Yes\", \"smallClaimsMethodInPerson\": {\"value\": {\"code\": \"9f5817fb-b7b3-4cd1-b5e5-99f33e995996\", \"label\": \"Barnet Civil and Family Centre - St Mary's Court, Regents Park Road - N3 1BQ\"}, \"list_items\": [{\"code\": \"9f5817fb-b7b3-4cd1-b5e5-99f33e995996\", \"label\": \"Barnet Civil and Family Centre - St Mary's Court, Regents Park Road - N3 1BQ\"}, {\"code\": \"ca851924-d649-4c88-a088-2162aff42c23\", \"label\": \"Central London County Court - THOMAS MORE BUILDING, ROYAL COURTS OF JUSTICE, STRAND, LONDON - WC2A 2LL\"}, {\"code\": \"3c714da9-1a08-46a3-910b-d73ad6754356\", \"label\": \"High Wycombe Law Courts - THE LAW COURTS, EASTON STREET - HP11 1LR\"}, {\"code\": \"accb417d-48a5-4feb-9d39-850c5e621be4\", \"label\": \"Liverpool Civil and Family Court - 35, Vernon Street, City Square - L2 2BX\"}]}, \"applicant1DQRequestedCourt\": {\"caseLocation\": {\"region\": \"2\", \"baseLocation\": \"000000\"}, \"responseCourtCode\": \"121\", \"reasonForHearingAtSpecificCourt\": \"Reasons\"}, \"applicant1ProceedWithClaim\": \"Yes\", \"applicant1ResponseDeadline\": \"2023-07-24T16:00:00\", \"disposalHearingCostsToggle\": [\"SHOW\"], \"disposalHearingHearingTime\": {\"input\": \"This claim will be listed for final disposal before a judge on the first available date after\", \"dateTo\": \"2023-10-16\"}, \"smallClaimsDocumentsToggle\": [\"SHOW\"], \"specRespondent1Represented\": \"Yes\", \"specRespondent2Represented\": \"Yes\", \"disposalHearingBundleToggle\": [\"SHOW\"], \"disposalHearingMethodToggle\": [\"SHOW\"], \"disposalOrderWithoutHearing\": {\"input\": \"This order has been made without hearing. Each party has the right to apply to have this Order set aside or varied. Any such application must be received by the Court (together with the appropriate fee) by 4pm on 03 July 2023.\"}, \"drawDirectionsOrderRequired\": \"Yes\", \"fastTrackClinicalNegligence\": {\"input1\": \"string\", \"input2\": \"string\", \"input3\": \"string\", \"input4\": \"string\"}, \"multiPartyResponseTypeFlags\": \"FULL_DEFENCE\", \"respondent1DQHearingSupport\": {\"requirements\": [\"DISABLED_ACCESS\", \"HEARING_LOOPS\"]}, \"respondent1DQRequestedCourt\": {\"responseCourtCode\": \"121\"}, \"respondent1ResponseDeadline\": \"2023-07-24T16:00:00\", \"smallClaimsWitnessStatement\": {\"text\": \"A witness statement must: \\na) Start with the name of the case and the claim number;\\nb) State the full name and address of the witness; \\nc) Set out the witness's evidence clearly in numbered paragraphs on numbered pages;\\nd) End with this paragraph: 'I believe that the facts stated in this witness statement are true. I understand that proceedings for contempt of court may be brought against anyone who makes, or causes to be made, a false statement in a document verified by a statement of truth without an honest belief in its truth'.\\ne) be signed by the witness and dated.\\nf) If a witness is unable to read the statement there must be a certificate that it has been read or interpreted to the witness by a suitably qualified person and at the final hearing there must be an independent interpreter who will not be provided by the Court.\\n\\nThe judge may refuse to allow a witness to give evidence or consider any statement of any witness whose statement has not been uploaded to the Digital Portal in accordance with the paragraphs above.\\n\\nA witness whose statement has been uploaded in accordance with the above must attend the hearing. If they do not attend, it will be for the court to decide how much reliance, if any, to place on their evidence.\", \"input1\": \"Each party must upload to the Digital Portal copies of all witness statements of the witnesses upon whose evidence they intend to rely at the hearing not less than 14 days before the hearing.\", \"input2\": \"2\", \"input3\": \"2\", \"input4\": \"For this limitation, a party is counted as a witness.\", \"smallClaimsNumberOfWitnessesToggle\": [\"SHOW\"]}, \"specDisputesOrPartAdmission\": \"Yes\", \"applicant1DQStatementOfTruth\": {\"name\": \"Solicitor name\", \"role\": \"Solicitor role\"}, \"applicant1OrganisationPolicy\": {\"Organisation\": {\"OrganisationID\": \"Q1KOKP2\"}, \"OrgPolicyReference\": \"Claimant policy reference\", \"OrgPolicyCaseAssignedRole\": \"[APPLICANTSOLICITORONE]\"}, \"claimantResponseScenarioFlag\": \"ONE_V_TWO_ONE_LEGAL_REP\", \"disposalHearingJudgesRecital\": {\"input\": \"Upon considering the claim form, particulars of claim, statements of case and Directions questionnaires\"}, \"disposalHearingWitnessOfFact\": {\"date2\": \"2023-07-24\", \"date3\": \"2023-08-07\", \"input3\": \"The claimant must upload to the Digital Portal copies of the witness statements of all witnesses of fact on whose evidence reliance is to be placed by 4pm on\", \"input4\": \"The provisions of CPR 32.6 apply to such evidence.\", \"input5\": \"Any application by the defendant in relation to CPR 32.7 must be made by 4pm on\", \"input6\": \"and must be accompanied by proposed directions for allocation and listing for trial on quantum. This is because cross-examination will cause the hearing to exceed the 30-minute maximum time estimate for a disposal hearing.\"}, \"fastTrackRoadTrafficAccident\": {\"date\": \"2023-06-27\", \"input\": \"string\"}, \"fastTrackWitnessOfFactToggle\": [\"SHOW\"], \"systemGeneratedCaseDocuments\": [{\"id\": \"5bcd8a02-24b5-442d-85d2-4342fa89db6b\", \"value\": {\"createdBy\": \"Civil\", \"documentLink\": {\"category_id\": \"detailsOfClaim\", \"document_url\": \"http://dm-store:8080/documents/ac8cc1d8-2662-4ce6-9ddd-0ca172cf4746\", \"document_filename\": \"sealed_claim_form_000MC055.pdf\", \"document_binary_url\": \"http://dm-store:8080/documents/ac8cc1d8-2662-4ce6-9ddd-0ca172cf4746/binary\"}, \"documentName\": \"sealed_claim_form_000MC055.pdf\", \"documentSize\": 77587, \"documentType\": \"SEALED_CLAIM\", \"createdDatetime\": \"2023-06-26T09:22:35\"}}, {\"id\": \"6aa670fd-4b61-40f0-a5b0-1d2fa003f9bc\", \"value\": {\"createdBy\": \"Civil\", \"documentLink\": {\"category_id\": \"defendant1DefenseDirectionsQuestionnaire\", \"document_url\": \"http://dm-store:8080/documents/0e235cf8-3f63-4897-8ae3-16ae2997d247\", \"document_filename\": \"defendant_response_sealed_form.pdf\", \"document_binary_url\": \"http://dm-store:8080/documents/0e235cf8-3f63-4897-8ae3-16ae2997d247/binary\"}, \"documentName\": \"defendant_response_sealed_form.pdf\", \"documentSize\": 73291, \"documentType\": \"DIRECTIONS_QUESTIONNAIRE\", \"createdDatetime\": \"2023-06-26T09:22:48\"}}, {\"id\": \"b338353d-4083-494e-a18c-2cabb2309d0f\", \"value\": {\"createdBy\": \"Civil\", \"documentLink\": {\"category_id\": \"defendant1DefenseDirectionsQuestionnaire\", \"document_url\": \"http://dm-store:8080/documents/77840f85-f769-4719-8c6a-c788ff948f30\", \"document_filename\": \"000MC055_response_sealed_form.pdf\", \"document_binary_url\": \"http://dm-store:8080/documents/77840f85-f769-4719-8c6a-c788ff948f30/binary\"}, \"documentName\": \"000MC055_response_sealed_form.pdf\", \"documentSize\": 55317, \"documentType\": \"SEALED_CLAIM\", \"createdDatetime\": \"2023-06-26T09:22:49\"}}, {\"id\": \"918d48bf-78d9-49e7-b786-d3617343f224\", \"value\": {\"createdBy\": \"Civil\", \"documentLink\": {\"category_id\": \"directionsQuestionnaire\", \"document_url\": \"http://dm-store:8080/documents/922f7fdc-3a78-4864-bdb8-fc7669d543bd\", \"document_filename\": \"claimant_directions_questionnaire_form_000MC055.pdf\", \"document_binary_url\": \"http://dm-store:8080/documents/922f7fdc-3a78-4864-bdb8-fc7669d543bd/binary\"}, \"documentName\": \"claimant_directions_questionnaire_form_000MC055.pdf\", \"documentSize\": 63408, \"documentType\": \"DIRECTIONS_QUESTIONNAIRE\", \"createdDatetime\": \"2023-06-26T09:22:59\"}}], \"applicant1DQSmallClaimHearing\": {\"unavailableDatesRequired\": \"No\"}, \"disposalHearingMethodInPerson\": {\"value\": {\"code\": \"9f5817fb-b7b3-4cd1-b5e5-99f33e995996\", \"label\": \"Barnet Civil and Family Centre - St Mary's Court, Regents Park Road - N3 1BQ\"}, \"list_items\": [{\"code\": \"9f5817fb-b7b3-4cd1-b5e5-99f33e995996\", \"label\": \"Barnet Civil and Family Centre - St Mary's Court, Regents Park Road - N3 1BQ\"}, {\"code\": \"ca851924-d649-4c88-a088-2162aff42c23\", \"label\": \"Central London County Court - THOMAS MORE BUILDING, ROYAL COURTS OF JUSTICE, STRAND, LONDON - WC2A 2LL\"}, {\"code\": \"3c714da9-1a08-46a3-910b-d73ad6754356\", \"label\": \"High Wycombe Law Courts - THE LAW COURTS, EASTON STREET - HP11 1LR\"}, {\"code\": \"accb417d-48a5-4feb-9d39-850c5e621be4\", \"label\": \"Liverpool Civil and Family Court - 35, Vernon Street, City Square - L2 2BX\"}]}, \"respondent1DQStatementOfTruth\": {\"name\": \"Test\", \"role\": \"Worker\"}, \"respondent1OrganisationPolicy\": {\"Organisation\": {\"OrganisationID\": \"79ZRSOU\"}, \"OrgPolicyCaseAssignedRole\": \"[RESPONDENTSOLICITORONE]\"}, \"respondent2OrganisationPolicy\": {\"Organisation\": {\"OrganisationID\": \"H2156A0\"}, \"OrgPolicyCaseAssignedRole\": \"[RESPONDENTSOLICITORTWO]\"}, \"specClaimResponseTimelineList\": \"MANUAL\", \"applicantSolicitor1PbaAccounts\": {\"value\": {\"code\": \"91fca730-13fa-11ee-91f1-d3b5d9925574\", \"label\": \"PBA0088192\"}, \"list_items\": [{\"code\": \"91fca730-13fa-11ee-91f1-d3b5d9925574\", \"label\": \"PBA0088192\"}, {\"code\": \"91fca731-13fa-11ee-91f1-d3b5d9925574\", \"label\": \"PBA0078095\"}]}, \"applicantSolicitor1UserDetails\": {\"id\": \"e286ef3b-3766-4a22-888d-b81cfe544deb\", \"email\": \"hmcts.civil+organisation.1.solicitor.1@gmail.com\"}, \"disposalHearingMedicalEvidence\": {\"date\": \"2023-07-24\", \"input\": \"The claimant has permission to rely upon the written expert evidence already uploaded to the Digital Portal with the particulars of claim and in addition has permission to rely upon any associated correspondence or updating report which is uploaded to the Digital Portal by 4pm on\"}, \"disposalHearingSchedulesOfLoss\": {\"date2\": \"2023-09-04\", \"date3\": \"2023-09-18\", \"date4\": \"2023-09-18\", \"input2\": \"If there is a claim for ongoing or future loss in the original schedule of losses, the claimant must upload to the Digital Portal an up-to-date schedule of loss by 4pm on\", \"input3\": \"If the defendant wants to challenge this claim, they must send an up-to-date counter-schedule of loss to the claimant by 4pm on\", \"input4\": \"If the defendant want to challenge the sums claimed in the schedule of loss they must upload to the Digital Portal an updated counter schedule of loss by 4pm on\"}, \"drawDirectionsOrderSmallClaims\": \"No\", \"fastTrackDisclosureOfDocuments\": {\"date1\": \"2023-06-25\", \"date2\": \"2023-06-25\", \"date3\": \"2023-06-25\", \"input1\": \"string\", \"input2\": \"string\", \"input3\": \"string\", \"input4\": \"string\"}, \"fastTrackOrderWithoutJudgement\": {\"input\": \"This order has been made without hearing. Each party has the right to apply to have this Order set aside or varied. Any such application must be received by the Court (together with the appropriate fee) by 4pm on 03 July 2023.\"}, \"fastTrackSchedulesOfLossToggle\": [\"SHOW\"], \"fullAdmissionAndFullAmountPaid\": \"No\", \"respondent1DQHearingSmallClaim\": {\"unavailableDatesRequired\": \"No\"}, \"smallClaimsRoadTrafficAccident\": {\"input\": \"Photographs and/or a place of the accident location shall be prepared and agreed by the parties and uploaded to the Digital Portal no later than 14 days before the hearing.\"}, \"specFullDefenceOrPartAdmission\": \"Yes\", \"claimAmountBreakupSummaryObject\": \" | Description | Amount | \\n |---|---| \\n | amount reason | £ 1500.00 |\\n  | **Total** | £ 1500.00 | \", \"fastTrackMethodTelephoneHearing\": \"telephoneTheClaimant\", \"partAdmittedByEitherRespondents\": \"No\", \"responseClaimExpertSpecRequired\": \"No\", \"specDefenceFullAdmittedRequired\": \"No\", \"respondentSolicitor1EmailAddress\": \"civilunspecified@gmail.com\", \"respondentSolicitor2EmailAddress\": \"civilunspecified@gmail.com\", \"specFullAdmissionOrPartAdmission\": \"No\", \"applicant1ClaimExpertSpecRequired\": \"No\", \"disposalHearingQuestionsToExperts\": {\"date\": \"2023-08-07\"}, \"smallClaimsWitnessStatementToggle\": [\"SHOW\"], \"specFullDefenceOrPartAdmission1V1\": \"Yes\", \"applicant1DQVulnerabilityQuestions\": {\"vulnerabilityAdjustmentsRequired\": \"No\"}, \"detailsOfWhyDoesYouDisputeTheClaim\": \"details\", \"disposalHearingClaimSettlingToggle\": [\"SHOW\"], \"disposalHearingWitnessOfFactToggle\": [\"SHOW\"], \"respondent2SameLegalRepresentative\": \"Yes\", \"responseClaimCourtLocationRequired\": \"Yes\", \"responseClaimMediationSpecRequired\": \"No\", \"disposalHearingFinalDisposalHearing\": {\"date\": \"2023-10-16\", \"input\": \"This claim will be listed for final disposal before a judge on the first available date after\"}, \"fastTrackAltDisputeResolutionToggle\": [\"SHOW\"], \"respondent1ClaimResponseTypeForSpec\": \"FULL_DEFENCE\", \"respondent1DQVulnerabilityQuestions\": {\"vulnerabilityAdjustments\": \"test\", \"vulnerabilityAdjustmentsRequired\": \"Yes\"}, \"respondent2ClaimResponseTypeForSpec\": \"FULL_DEFENCE\", \"disposalHearingDisclosureOfDocuments\": {\"date1\": \"2023-09-04\", \"date2\": \"2023-09-04\", \"input1\": \"The parties shall serve on each other copies of the documents upon which reliance is to be placed at the disposal hearing by 4pm on\", \"input2\": \"The parties must upload to the Digital Portal copies of those documents which they wish the court to consider when deciding the amount of damages, by 4pm on\"}, \"disposalHearingMedicalEvidenceToggle\": [\"SHOW\"], \"disposalHearingSchedulesOfLossToggle\": [\"SHOW\"], \"fastTrackDisclosureOfDocumentsToggle\": [\"SHOW\"], \"fastTrackVariationOfDirectionsToggle\": [\"SHOW\"], \"respondent1DetailsForClaimDetailsTab\": {\"type\": \"INDIVIDUAL\", \"flags\": {\"partyName\": \"Sir John Doe\", \"roleOnCase\": \"Respondent 1\"}, \"partyID\": \"8597196f-f426-49\", \"partyName\": \"Sir John Doe\", \"primaryAddress\": {\"County\": \"Kent\", \"Country\": \"United Kingdom\", \"PostCode\": \"RG4 7AA\", \"PostTown\": \"Reading\", \"AddressLine1\": \"Flat 2 - respondent\", \"AddressLine2\": \"Caversham House 15-17\", \"AddressLine3\": \"Church Road\"}, \"individualTitle\": \"Sir\", \"individualLastName\": \"Doe\", \"individualFirstName\": \"John\", \"partyTypeDisplayValue\": \"Individual\"}, \"respondent2DetailsForClaimDetailsTab\": {\"type\": \"ORGANISATION\", \"flags\": {\"partyName\": \"Second Defendant\", \"roleOnCase\": \"Respondent 2\"}, \"partyID\": \"6ce7a978-0904-4c\", \"partyName\": \"Second Defendant\", \"primaryAddress\": {\"PostCode\": \"NR5 9LL\", \"PostTown\": \"Second Town\", \"AddressLine1\": \"123 Second Close\"}, \"organisationName\": \"Second Defendant\", \"partyTypeDisplayValue\": \"Organisation\"}, \"applicantMPClaimMediationSpecRequired\": {\"hasAgreedFreeMediation\": \"Yes\"}, \"applicantSolicitor1PbaAccountsIsEmpty\": \"No\", \"specAoSRespondent2HomeAddressRequired\": \"Yes\", \"defenceAdmitPartPaymentTimeRouteGeneric\": \"IMMEDIATELY\", \"disposalHearingQuestionsToExpertsToggle\": [\"SHOW\"], \"applicantSolicitor1ClaimStatementOfTruth\": {\"name\": \"John Doe\", \"role\": \"Test Solicitor\"}, \"disposalHearingFinalDisposalHearingToggle\": [\"SHOW\"], \"respondentClaimResponseTypeForSpecGeneric\": \"FULL_DEFENCE\", \"disposalHearingDisclosureOfDocumentsToggle\": [\"SHOW\"], \"specApplicantCorrespondenceAddressRequired\": \"No\", \"specPaidLessAmountOrDisputesOrPartAdmission\": \"Yes\", \"specRespondentCorrespondenceAddressRequired\": \"No\", \"specAoSApplicantCorrespondenceAddressRequired\": \"Yes\", \"respondent1ClaimResponsePaymentAdmissionForSpec\": \"DID_NOT_PAY\"}";
    @BeforeAll
    public static void initialization() {
        CURRENT_DMN_DECISION_TABLE = DmnDecisionTable.WA_TASK_INITIATION_CIVIL_GENERALAPPLICATION;
    }

    @ParameterizedTest
    @CsvSource(value = {
        "END_BUSINESS_PROCESS_GASPEC", "TRIGGER_LOCATION_UPDATE","RESPOND_TO_APPLICATION",
        "CHANGE_STATE_TO_AWAITING_JUDICIAL_DECISION"
    })
    void when_urgent_ga_with_consent_order_creation_with_case_loc_local_court(String eventId) {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 10*/
        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("isCcmccLocation", false);
        data.put("generalAppConsentOrder", true);
        data.put("generalAppType", Map.of(
            "types", asList("SUMMARY_JUDGEMENT")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Application for summary judgement"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("ReviewApplication"));
    }

    @ParameterizedTest
    @CsvSource(value = {
        "END_BUSINESS_PROCESS_GASPEC", "TRIGGER_LOCATION_UPDATE","RESPOND_TO_APPLICATION",
        "CHANGE_STATE_TO_AWAITING_JUDICIAL_DECISION"
    })
    void when_urgent_multiple_ga_with_consent_order_creation_with_case_loc_local_court(String eventId) {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 10*/
        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("isCcmccLocation", false);
        data.put("generalAppConsentOrder", true);
        data.put("generalAppType", Map.of(
            "types", asList("SUMMARY_JUDGEMENT","STAY_THE_CLAIM")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Application for multiple types"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("ReviewApplication"));
    }

    @ParameterizedTest
    @CsvSource(value = {
        "END_BUSINESS_PROCESS_GASPEC", "TRIGGER_LOCATION_UPDATE","RESPOND_TO_APPLICATION",
        "CHANGE_STATE_TO_AWAITING_JUDICIAL_DECISION"
    })
    void when_non_urgent_multiple_ga_with_consent_order_creation_with_case_loc_local_court(String eventId) {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 10*/
        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", false
        ));
        data.put("isCcmccLocation", false);
        data.put("generalAppConsentOrder", true);
        data.put("generalAppType", Map.of(
            "types", asList("SUMMARY_JUDGEMENT","STAY_THE_CLAIM")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Application for multiple types"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("ReviewApplication"));
    }

    @ParameterizedTest
    @CsvSource(value = {
        "END_BUSINESS_PROCESS_GASPEC", "TRIGGER_LOCATION_UPDATE","RESPOND_TO_APPLICATION",
        "CHANGE_STATE_TO_AWAITING_JUDICIAL_DECISION"
    })
    void when_non_urgent_ga_with_consent_order_creation_with_case_loc_local_court(String eventId) {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 10*/
        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", false
        ));
        data.put("isCcmccLocation", false);
        data.put("generalAppConsentOrder", true);
        data.put("generalAppType", Map.of(
            "types", asList("SETTLE_OR_DISCONTINUE_CONSENT")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Application for settle or discontinue consent"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("ReviewApplication"));
    }

    @ParameterizedTest
    @CsvSource(value = {
        "END_BUSINESS_PROCESS_GASPEC","RESPOND_TO_APPLICATION",
        "CHANGE_STATE_TO_AWAITING_JUDICIAL_DECISION"
    })
    void when_non_urgent_ga_with_consent_order_creation_with_case_ccmcc_location(String eventId) {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 10*/
        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", false
        ));
        data.put("isCcmccLocation", true);
        data.put("generalAppConsentOrder", true);
        data.put("generalAppType", Map.of(
            "types", asList("SET_ASIDE_JUDGEMENT")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Application for set aside judgement"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("ReviewApplication"));
    }

    @ParameterizedTest
    @CsvSource(value = {
        "END_BUSINESS_PROCESS_GASPEC","RESPOND_TO_APPLICATION",
        "CHANGE_STATE_TO_AWAITING_JUDICIAL_DECISION"
    })
    void when_non_urgent_multiple_ga_with_consent_order_creation_with_case_ccmcc_location(String eventId) {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 10*/
        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", false
        ));
        data.put("isCcmccLocation", true);
        data.put("generalAppConsentOrder", true);
        data.put("generalAppType", Map.of(
            "types", asList("SET_ASIDE_JUDGEMENT","SUMMARY_JUDGEMENT")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Application for multiple types"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("ReviewApplication"));
    }

    @ParameterizedTest
    @CsvSource(value = {
        "END_BUSINESS_PROCESS_GASPEC","RESPOND_TO_APPLICATION",
        "CHANGE_STATE_TO_AWAITING_JUDICIAL_DECISION"
    })
    void when_urgent_multiple_ga_with_consent_order_creation_with_case_ccmcc_location(String eventId) {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 10*/
        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("isCcmccLocation", true);
        data.put("generalAppConsentOrder", true);
        data.put("generalAppType", Map.of(
            "types", asList("SET_ASIDE_JUDGEMENT","SUMMARY_JUDGEMENT")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Application for multiple types"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("ReviewApplication"));
    }

    @ParameterizedTest
    @CsvSource(value = {
        "END_BUSINESS_PROCESS_GASPEC","RESPOND_TO_APPLICATION",
        "CHANGE_STATE_TO_AWAITING_JUDICIAL_DECISION"
    })
    void when_urgent_ga_with_consent_order_creation_with_case_ccmcc_location(String eventId) {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 10*/
        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("isCcmccLocation", false);
        data.put("generalAppConsentOrder", true);
        data.put("generalAppType", Map.of(
            "types", asList("STAY_THE_CLAIM")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Application for stay the claim"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("ReviewApplication"));
    }

    @ParameterizedTest
    @CsvSource(value = {
        "CHANGE_STATE_TO_ADDITIONAL_RESPONSE_TIME_EXPIRED", "TRIGGER_LOCATION_UPDATE"
    })
    void when_change_state_to_addln_response_time_expired_nonurgent_consent_order_app_with_case_loc_local_court(
        String eventId) {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 5*/
        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", false
        ));
        data.put("isCcmccLocation", false);
        data.put("generalAppConsentOrder", true);
        data.put("judicialDecision", Map.of(
            "decision", "MAKE_ORDER_FOR_WRITTEN_REPRESENTATIONS"
        ));
        data.put("generalAppType", Map.of(
            "types", asList("SUMMARY_JUDGEMENT")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", "ADDITIONAL_RESPONSE_TIME_EXPIRED");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList
                       .get(0).get("name"),
                   is("Review summary judgement App - revisited make order for written representations"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("ReviewRevisitedApplication"));
    }

    @ParameterizedTest
    @CsvSource(value = {
        "CHANGE_STATE_TO_ADDITIONAL_RESPONSE_TIME_EXPIRED", "TRIGGER_LOCATION_UPDATE"
    })
    void when_change_state_to_addln_response_time_expired_urgent_multiple_consent_order_app_with_case_loc_local_court(
        String eventId) {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 5*/
        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("isCcmccLocation", false);
        data.put("generalAppConsentOrder", true);
        data.put("judicialDecision", Map.of(
            "decision", "MAKE_ORDER_FOR_WRITTEN_REPRESENTATIONS"
        ));
        data.put("generalAppType", Map.of(
            "types", asList("SUMMARY_JUDGEMENT", "STAY_THE_CLAIM")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", "ADDITIONAL_RESPONSE_TIME_EXPIRED");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList
                       .get(0).get("name"),
                   is("Application for multiple types - revisited make order for written representations"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("ReviewRevisitedApplication"));
    }

    @ParameterizedTest
    @CsvSource(value = {
        "CHANGE_STATE_TO_ADDITIONAL_RESPONSE_TIME_EXPIRED", "TRIGGER_LOCATION_UPDATE"
    })
    void when_change_state_response_nonurgent_multiple_consent_order_app_with_case_loc_local_court(
        String eventId) {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 5*/
        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", false
        ));
        data.put("isCcmccLocation", false);
        data.put("generalAppConsentOrder", true);
        data.put("judicialDecision", Map.of(
            "decision", "MAKE_ORDER_FOR_WRITTEN_REPRESENTATIONS"
        ));
        data.put("generalAppType", Map.of(
            "types", asList("SUMMARY_JUDGEMENT", "STAY_THE_CLAIM")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", "ADDITIONAL_RESPONSE_TIME_EXPIRED");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList
                       .get(0).get("name"),
                   is("Application for multiple types - revisited make order for written representations"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("ReviewRevisitedApplication"));
    }

    @ParameterizedTest
    @CsvSource(value = {
        "CHANGE_STATE_TO_ADDITIONAL_RESPONSE_TIME_EXPIRED", "TRIGGER_LOCATION_UPDATE"
    })
    void when_change_state_addln_response_urgent_consent_order_app_with_case_loc_local_court(
        String eventId) {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 5*/
        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("isCcmccLocation", false);
        data.put("generalAppConsentOrder", true);
        data.put("judicialDecision", Map.of(
            "decision", "MAKE_ORDER_FOR_WRITTEN_REPRESENTATIONS"
        ));
        data.put("generalAppType", Map.of(
            "types", asList("SUMMARY_JUDGEMENT")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", "ADDITIONAL_RESPONSE_TIME_EXPIRED");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList
                       .get(0).get("name"),
                   is("Review summary judgement App - revisited make order for written representations"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("ReviewRevisitedApplication"));
    }

    @ParameterizedTest
    @CsvSource(value = {
        "CHANGE_STATE_TO_ADDITIONAL_RESPONSE_TIME_EXPIRED"
    })
    void when_change_state_addln_response_nonurgent_consent_order_app_with_case_ccmcc_location(
        String eventId) {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 5*/
        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", false
        ));
        data.put("isCcmccLocation", true);
        data.put("generalAppConsentOrder", true);
        data.put("judicialDecision", Map.of(
            "decision", "MAKE_ORDER_FOR_WRITTEN_REPRESENTATIONS"
        ));
        data.put("generalAppType", Map.of(
            "types", asList("SUMMARY_JUDGEMENT")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", "ADDITIONAL_RESPONSE_TIME_EXPIRED");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList
                       .get(0).get("name"),
                   is("Review summary judgement App - revisited make order for written representations"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("ReviewRevisitedApplication"));
    }

    @ParameterizedTest
    @CsvSource(value = {
        "CHANGE_STATE_TO_ADDITIONAL_RESPONSE_TIME_EXPIRED"
    })
    void when_change_state_addln_response_urgent_consent_order_app_with_case_ccmmcc_location(
        String eventId) {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 5*/
        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("isCcmccLocation", true);
        data.put("generalAppConsentOrder", true);
        data.put("judicialDecision", Map.of(
            "decision", "MAKE_ORDER_FOR_WRITTEN_REPRESENTATIONS"
        ));
        data.put("generalAppType", Map.of(
            "types", asList("SUMMARY_JUDGEMENT")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", "ADDITIONAL_RESPONSE_TIME_EXPIRED");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList
                       .get(0).get("name"),
                   is("Review summary judgement App - revisited make order for written representations"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("ReviewRevisitedApplication"));
    }

    @ParameterizedTest
    @CsvSource(value = {
        "CHANGE_STATE_TO_ADDITIONAL_RESPONSE_TIME_EXPIRED"
    })
    void when_change_state_addln_response_nonurgent_multple_consent_order_app_with_case_ccmcc_location(
        String eventId) {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 5*/
        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", false
        ));
        data.put("isCcmccLocation", true);
        data.put("generalAppConsentOrder", true);
        data.put("judicialDecision", Map.of(
            "decision", "MAKE_ORDER_FOR_WRITTEN_REPRESENTATIONS"
        ));
        data.put("generalAppType", Map.of(
            "types", asList("SUMMARY_JUDGEMENT", "STAY_THE_CLAIM")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", "ADDITIONAL_RESPONSE_TIME_EXPIRED");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList
                       .get(0).get("name"),
                   is("Application for multiple types - revisited make order for written representations"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("ReviewRevisitedApplication"));
    }

    @ParameterizedTest
    @CsvSource(value = {
        "CHANGE_STATE_TO_ADDITIONAL_RESPONSE_TIME_EXPIRED"
    })
    void when_change_state_addln_response_urgent_multple_consent_order_app_with_case_ccmcc_location(
        String eventId) {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 5*/
        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("isCcmccLocation", true);
        data.put("generalAppConsentOrder", true);
        data.put("judicialDecision", Map.of(
            "decision", "MAKE_ORDER_FOR_WRITTEN_REPRESENTATIONS"
        ));
        data.put("generalAppType", Map.of(
            "types", asList("SUMMARY_JUDGEMENT", "STAY_THE_CLAIM")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", "ADDITIONAL_RESPONSE_TIME_EXPIRED");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList
                       .get(0).get("name"),
                   is("Application for multiple types - revisited make order for written representations"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("ReviewRevisitedApplication"));
    }

    @ParameterizedTest
    @CsvSource(value = {
        "END_BUSINESS_PROCESS_GASPEC", "TRIGGER_LOCATION_UPDATE"
    })
    void when_urgent_ga_creation_with_case_loc_local_court(String eventId) {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 10*/
        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("isCcmccLocation", false);
        data.put("generalAppConsentOrder", null);
        data.put("generalAppType", Map.of(
            "types", asList("SUMMARY_JUDGEMENT")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Application for summary judgement"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("JudgeDecideOnApplication"));
    }

    @ParameterizedTest
    @CsvSource(value = {
        "END_BUSINESS_PROCESS_GASPEC"
    })
    void when_urgent_ga_creation_with_pre_sdo_refer_legalAdvisor(String eventId) {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 10*/
        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("isCcmccLocation", true);
        data.put("generalAppConsentOrder", null);
        data.put("generalAppType", Map.of(
            "types", asList("STAY_THE_CLAIM")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Application for stay the claim"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("LegalAdvisorDecideOnApplication"));
    }

    @ParameterizedTest
    @CsvSource(value = {
        "END_BUSINESS_PROCESS_GASPEC", "TRIGGER_LOCATION_UPDATE"
    })
    void when_urgent_ga_creation_with_post_sdo_refer_judge(String eventId) {

        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("isCcmccLocation", false);
        data.put("generalAppConsentOrder", null);
        data.put("generalAppType", Map.of(
            "types", asList("STAY_THE_CLAIM")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Application for stay the claim"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("JudgeDecideOnApplication"));
    }

    @ParameterizedTest
    @CsvSource(value = {
        "END_BUSINESS_PROCESS_GASPEC", "TRIGGER_LOCATION_UPDATE"
    })
    void when_urgent_ga_creation_with_case_loc_local_court_multiple_types(String eventId) {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 10*/
        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("isCcmccLocation", false);
        data.put("generalAppConsentOrder", null);
        data.put("generalAppType", Map.of(
            "types", asList("STAY_THE_CLAIM", "STIKE_OUT")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Application for multiple types"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("JudgeDecideOnApplication"));
    }

    @ParameterizedTest
    @CsvSource(value = {
        "END_BUSINESS_PROCESS_GASPEC"
    })

    void when_urgent_ga_creation_with_pre_sdo_multiple_types_refer_legalAdvisor(String eventId) {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 10*/
        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("isCcmccLocation", true);
        data.put("generalAppConsentOrder", null);
        data.put("generalAppSuperClaimType", "SPEC_CLAIM");
        data.put("generalAppType", Map.of(
            "types", asList("EXTEND_TIME", "SET_ASIDE_JUDGEMENT")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Application for multiple types"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("LegalAdvisorDecideOnApplication"));
    }

    @ParameterizedTest
    @CsvSource(value = {
        "END_BUSINESS_PROCESS_GASPEC", "TRIGGER_LOCATION_UPDATE"
    })

    void when_urgent_ga_creation_with_post_sdo_multiple_types_refer_judge(String eventId) {

        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("isCcmccLocation", false);
        data.put("generalAppSuperClaimType", "SPEC_CLAIM");
        data.put("generalAppConsentOrder", null);
        data.put("generalAppType", Map.of(
            "types", asList("EXTEND_TIME", "SET_ASIDE_JUDGEMENT")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Application for multiple types"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("JudgeDecideOnApplication"));
    }


    @ParameterizedTest
    @CsvSource(value = {
        "END_BUSINESS_PROCESS_GASPEC", "TRIGGER_LOCATION_UPDATE"
    })
    void when_non_urgent_ga_creation_with_case_loc_local_court_single_application(String eventId) {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 5*/
        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", false
        ));
        data.put("isCcmccLocation", false);
        data.put("generalAppConsentOrder", null);
        data.put("generalAppType", Map.of(
            "types", asList("SUMMARY_JUDGEMENT")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Application for summary judgement"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("JudgeDecideOnApplication"));
    }

    @ParameterizedTest
    @CsvSource(value = {
        "END_BUSINESS_PROCESS_GASPEC"
    })
    void when_non_urgent_ga_creation_with_pre_sdo_single_application_refer_legalAdvisor(String eventId) {

        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", false
        ));
        data.put("generalAppConsentOrder", null);
        data.put("isCcmccLocation", true);
        data.put("generalAppSuperClaimType", "SPEC_CLAIM");
        data.put("generalAppType", Map.of(
            "types", asList("SET_ASIDE_JUDGEMENT")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Application for set aside judgement"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("LegalAdvisorDecideOnApplication"));
    }

    @ParameterizedTest
    @CsvSource(value = {
        "END_BUSINESS_PROCESS_GASPEC", "TRIGGER_LOCATION_UPDATE"
    })
    void when_non_urgent_ga_creation_with_post_sdo_single_application_refer_judge(String eventId) {

        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", false
        ));
        data.put("isCcmccLocation", false);
        data.put("generalAppConsentOrder", null);
        data.put("generalAppSuperClaimType", "SPEC_CLAIM");
        data.put("generalAppType", Map.of(
            "types", asList("SET_ASIDE_JUDGEMENT")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Application for set aside judgement"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("JudgeDecideOnApplication"));
    }

    @ParameterizedTest
    @CsvSource(value = {
        "END_BUSINESS_PROCESS_GASPEC", "TRIGGER_LOCATION_UPDATE"
    })
    void when_non_urgent_ga_creation_with_case_loc_local_court_multiple_types(String eventId) {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 5*/
        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", false
        ));
        data.put("isCcmccLocation", false);
        data.put("generalAppConsentOrder", null);
        data.put("generalAppType", Map.of(
            "types", asList("STRIKE_OUT", "AMEND_A_STMT_OF_CASE")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Application for multiple types"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("JudgeDecideOnApplication"));
    }

    @ParameterizedTest
    @CsvSource(value = {
        "END_BUSINESS_PROCESS_GASPEC", "TRIGGER_LOCATION_UPDATE"
    })
    void when_non_urgent_ga_creation_with_post_sdo_multiple_types_refer_judge(String eventId) {

        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", false
        ));
        data.put("isCcmccLocation", false);
        data.put("generalAppConsentOrder", null);
        data.put("generalAppType", Map.of(
            "types", asList("STAY_THE_CLAIM", "EXTEND_TIME")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Application for multiple types"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("JudgeDecideOnApplication"));
    }

    @ParameterizedTest
    @CsvSource(value = {
        "END_BUSINESS_PROCESS_GASPEC"
    })
    void when_non_urgent_ga_creation_with_pre_sdo_multiple_types_refer_legalAdvisor(String eventId) {

        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", false
        ));
        data.put("isCcmccLocation", true);
        data.put("generalAppConsentOrder", null);
        data.put("generalAppType", Map.of(
            "types", asList("STAY_THE_CLAIM", "EXTEND_TIME")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Application for multiple types"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("LegalAdvisorDecideOnApplication"));
    }

    @Test
    void when_response_nonurgent_app_with_case_loc_local_court_multiple_types() {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 5*/
        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", false
        ));
        data.put("isCcmccLocation", false);
        data.put("generalAppConsentOrder", null);
        data.put("generalAppType", Map.of(
            "types", asList("STRIKE_OUT", "SUMMARY_JUDGEMENT")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "RESPOND_TO_APPLICATION");
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Application for multiple types"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("JudgeDecideOnApplication"));
    }

    @Test
    void when_response_nonurgent_app_with_pre_sdo_multiple_types_refer_legalAdvisor() {

        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", false
        ));
        data.put("isCcmccLocation", true);
        data.put("generalAppConsentOrder", null);
        data.put("generalAppType", Map.of(
            "types", asList("STAY_THE_CLAIM", "EXTEND_TIME")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "RESPOND_TO_APPLICATION");
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Application for multiple types"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("LegalAdvisorDecideOnApplication"));
    }

    @Test
    void when_response_nonurgent_app_with_post_sdo_multiple_types_refer_judge() {

        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", false
        ));
        data.put("isCcmccLocation", false);
        data.put("generalAppConsentOrder", null);
        data.put("generalAppType", Map.of(
            "types", asList("STAY_THE_CLAIM", "EXTEND_TIME")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "RESPOND_TO_APPLICATION");
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Application for multiple types"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("JudgeDecideOnApplication"));
    }

    @Test
    void when_response_nonurgent_app_with_case_loc_local_court_single_ga_type() {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 5*/
        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", false
        ));
        data.put("isCcmccLocation", false);
        data.put("generalAppType", Map.of(
            "types", asList("STRIKE_OUT")
        ));
        data.put("generalAppConsentOrder", null);
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "RESPOND_TO_APPLICATION");
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Application for strike out"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("JudgeDecideOnApplication"));
    }


    @Test
    void when_response_nonurgent_app_with_pre_sdo_single_ga_type_refer_legalAdvisor() {

        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", false
        ));
        data.put("isCcmccLocation", true);
        data.put("generalAppType", Map.of(
            "types", asList("STAY_THE_CLAIM")
        ));
        data.put("generalAppConsentOrder", null);
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "RESPOND_TO_APPLICATION");
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Application for stay the claim"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("LegalAdvisorDecideOnApplication"));
    }

    @Test
    void when_response_nonurgent_app_with_post_sdo_single_ga_type_refer_judge() {

        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", false
        ));
        data.put("generalAppConsentOrder", null);
        data.put("isCcmccLocation", false);
        data.put("generalAppType", Map.of(
            "types", asList("STAY_THE_CLAIM")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "RESPOND_TO_APPLICATION");
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Application for stay the claim"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("JudgeDecideOnApplication"));
    }


    @Test
    void when_response_urgent_app_with_case_loc_local_court() {

        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("generalAppConsentOrder", null);
        data.put("isCcmccLocation", false);
        data.put("generalAppType", Map.of(
            "types", asList("STRIKE_OUT")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "RESPOND_TO_APPLICATION");
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Application for strike out"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("JudgeDecideOnApplication"));
    }

    @Test
    void when_response_urgent_app_with_post_sdo_refer_judge() {

        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("isCcmccLocation", false);
        data.put("generalAppConsentOrder", null);
        data.put("generalAppType", Map.of(
            "types", asList("STAY_THE_CLAIM")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "RESPOND_TO_APPLICATION");
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Application for stay the claim"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("JudgeDecideOnApplication"));
    }

    @Test
    void when_response_urgent_app_with_pre_sdo_refer_legalAdvisor() {

        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("isCcmccLocation", true);
        data.put("generalAppConsentOrder", null);
        data.put("generalAppType", Map.of(
            "types", asList("STAY_THE_CLAIM")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "RESPOND_TO_APPLICATION");
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Application for stay the claim"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("LegalAdvisorDecideOnApplication"));
    }

    @Test
    void when_response_urgent_app_with_case_loc_local_court_multiple_ga_type() {

        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("isCcmccLocation", false);
        data.put("generalAppConsentOrder", null);
        data.put("generalAppType", Map.of(
            "types", asList("STRIKE_OUT","SUMMARY_JUDGEMENT")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "RESPOND_TO_APPLICATION");
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Application for multiple types"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("JudgeDecideOnApplication"));
    }

    @Test
    void when_response_urgent_app_with_pre_sdo_multiple_ga_type_refer_legalAdvisor() {

        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("isCcmccLocation", true);
        data.put("generalAppConsentOrder", null);
        data.put("generalAppType", Map.of(
            "types", asList("STAY_THE_CLAIM","EXTEND_TIME")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "RESPOND_TO_APPLICATION");
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Application for multiple types"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("LegalAdvisorDecideOnApplication"));
    }

    @Test
    void when_response_urgent_app_with_post_sdo_multiple_ga_type_refer_judge() {

        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("isCcmccLocation", false);
        data.put("generalAppConsentOrder", null);
        data.put("generalAppType", Map.of(
            "types", asList("STAY_THE_CLAIM","EXTEND_TIME")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "RESPOND_TO_APPLICATION");
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Application for multiple types"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("JudgeDecideOnApplication"));
    }

    @Test
    void when_change_state_to_await_judicial_decision_nonurgent_app_with_case_loc_local_court() {

        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", false
        ));
        data.put("isCcmccLocation", false);
        data.put("generalAppConsentOrder", null);
        data.put("generalAppType", Map.of(
            "types", asList("STRIKE_OUT", "SUMMARY_JUDGEMENT")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "CHANGE_STATE_TO_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("taskId"), is("JudgeDecideOnApplication"));
    }

    @Test
    void when_change_state_to_await_judicial_decision_nonurgent_app_with_pre_sdo_multiple_ga_type_refer_legalAdvisor() {

        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", false
        ));
        data.put("isCcmccLocation", true);
        data.put("generalAppConsentOrder", null);
        data.put("generalAppType", Map.of(
            "types", asList("STAY_THE_CLAIM", "EXTEND_TIME")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "CHANGE_STATE_TO_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("taskId"), is("LegalAdvisorDecideOnApplication"));
    }

    @Test
    void when_change_state_to_await_judicial_decision_nonurgent_with_post_sdo_multiple_ga_type_refer_judge() {

        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", false
        ));
        data.put("isCcmccLocation", false);
        data.put("generalAppConsentOrder", null);
        data.put("generalAppType", Map.of(
            "types", asList("STAY_THE_CLAIM", "EXTEND_TIME")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "CHANGE_STATE_TO_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("taskId"), is("JudgeDecideOnApplication"));
    }


    @Test
    void when_change_state_to_await_judicial_decision_nonurgent_app_with_case_loc_local_court_single_ga_type() {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 5*/
        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", false
        ));
        data.put("isCcmccLocation", false);
        data.put("generalAppConsentOrder", null);
        data.put("generalAppType", Map.of(
            "types", asList("SUMMARY_JUDGEMENT")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "CHANGE_STATE_TO_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Application for summary judgement"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("JudgeDecideOnApplication"));
    }

    @Test
    void when_change_state_to_await_judicial_decision_nonurgent_app_pre_sdo_single_ga_type_refer_legalAdvisor() {

        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", false
        ));
        data.put("isCcmccLocation", true);
        data.put("generalAppConsentOrder", null);
        data.put("generalAppType", Map.of(
            "types", asList("STAY_THE_CLAIM")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "CHANGE_STATE_TO_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Application for stay the claim"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("LegalAdvisorDecideOnApplication"));
    }

    @Test
    void when_change_state_to_await_judicial_decision_nonurgent_app_post_sdo_single_ga_type_refer_judge() {

        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", false
        ));
        data.put("isCcmccLocation", false);
        data.put("generalAppConsentOrder", null);
        data.put("generalAppType", Map.of(
            "types", asList("STAY_THE_CLAIM")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "CHANGE_STATE_TO_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Application for stay the claim"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("JudgeDecideOnApplication"));
    }

    @Test
    void when_change_state_to_await_judicial_decision_urgent_app_with_case_loc_local_court() {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 10*/
        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("isCcmccLocation", false);
        data.put("generalAppConsentOrder", null);
        data.put("generalAppType", Map.of(
            "types", asList("SUMMARY_JUDGEMENT")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "CHANGE_STATE_TO_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Application for summary judgement"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("JudgeDecideOnApplication"));
    }

    @Test
    void when_change_state_to_await_judicial_decision_urgent_app_with_pre_sdo_refer_legaAdvisor() {

        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("isCcmccLocation", true);
        data.put("generalAppConsentOrder", null);
        data.put("generalAppType", Map.of(
            "types", asList("STAY_THE_CLAIM")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "CHANGE_STATE_TO_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Application for stay the claim"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("LegalAdvisorDecideOnApplication"));
    }

    @Test
    void when_change_state_to_await_judicial_decision_urgent_app_with_post_sdo_refer_judge() {

        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("isCcmccLocation", false);
        data.put("generalAppConsentOrder", null);
        data.put("generalAppType", Map.of(
            "types", asList("STAY_THE_CLAIM")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "CHANGE_STATE_TO_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Application for stay the claim"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("JudgeDecideOnApplication"));
    }

    @Test
    void when_change_state_to_await_judicial_decision_urgent_app_with_case_loc_local_court_multi_ga_type() {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 10*/
        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("isCcmccLocation", false);
        data.put("generalAppConsentOrder", null);
        data.put("generalAppType", Map.of(
            "types", asList("SUMMARY_JUDGEMENT","STRIKE_OUT")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "CHANGE_STATE_TO_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Application for multiple types"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("JudgeDecideOnApplication"));
    }

    @Test
    void when_change_state_to_await_judicial_decision_urgent_app_with_pre_sdo_multi_ga_type_refer_legalAdvisor() {

        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("isCcmccLocation", true);
        data.put("generalAppConsentOrder", null);
        data.put("generalAppType", Map.of(
            "types", asList("STAY_THE_CLAIM","EXTEND_TIME")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "CHANGE_STATE_TO_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Application for multiple types"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("LegalAdvisorDecideOnApplication"));
    }

    @Test
    void when_change_state_to_await_judicial_decision_urgent_app_with_post_sdo_multi_ga_type_refer_judge() {

        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("isCcmccLocation", false);
        data.put("generalAppConsentOrder", null);
        data.put("generalAppType", Map.of(
            "types", asList("STAY_THE_CLAIM","EXTEND_TIME")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "CHANGE_STATE_TO_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Application for multiple types"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("JudgeDecideOnApplication"));
    }

    @ParameterizedTest
    @CsvSource(value = {
        "CHANGE_STATE_TO_ADDITIONAL_RESPONSE_TIME_EXPIRED", "TRIGGER_LOCATION_UPDATE"
    })
    void when_change_state_to_addln_response_time_expired_nonurgent_app_with_case_loc_local_court(String eventId) {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 5*/
        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", false
        ));
        data.put("isCcmccLocation", false);
        data.put("generalAppConsentOrder", null);
        data.put("judicialDecision", Map.of(
            "decision", "MAKE_ORDER_FOR_WRITTEN_REPRESENTATIONS"
        ));
        data.put("generalAppType", Map.of(
            "types", asList("STRIKE_OUT", "SUMMARY_JUDGEMENT")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", "ADDITIONAL_RESPONSE_TIME_EXPIRED");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList
                       .get(0).get("name"),
                   is("Application for multiple types - revisited make order for written representations"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("JudgeRevisitApplication"));
    }

    @ParameterizedTest
    @CsvSource(value = {
        "CHANGE_STATE_TO_ADDITIONAL_RESPONSE_TIME_EXPIRED"
    })
    void when_change_state_to_addln_response_time_expired_nonurgent_with_pre_sdo_refer_legalAdvisor(String eventId) {

        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", false
        ));
        data.put("isCcmccLocation", true);
        data.put("generalAppConsentOrder", null);
        data.put("judicialDecision", Map.of(
            "decision", "MAKE_ORDER_FOR_WRITTEN_REPRESENTATIONS"
        ));
        data.put("generalAppType", Map.of(
            "types", asList("STAY_THE_CLAIM", "EXTEND_TIME")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", "ADDITIONAL_RESPONSE_TIME_EXPIRED");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList
                       .get(0).get("name"),
                   is("Application for multiple types - revisited make order for written representations"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("LegalAdvisorRevisitApplication"));
    }

    @ParameterizedTest
    @CsvSource(value = {
        "CHANGE_STATE_TO_ADDITIONAL_RESPONSE_TIME_EXPIRED", "TRIGGER_LOCATION_UPDATE"
    })
    void when_change_state_to_addln_response_time_expired_nonurgent_with_post_sdo_refer_Judge(String eventId) {

        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", false
        ));
        data.put("isCcmccLocation", false);
        data.put("generalAppConsentOrder", null);
        data.put("judicialDecision", Map.of(
            "decision", "MAKE_ORDER_FOR_WRITTEN_REPRESENTATIONS"
        ));
        data.put("generalAppType", Map.of(
            "types", asList("STAY_THE_CLAIM", "EXTEND_TIME")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", "ADDITIONAL_RESPONSE_TIME_EXPIRED");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList
                       .get(0).get("name"),
                   is("Application for multiple types - revisited make order for written representations"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("JudgeRevisitApplication"));
    }

    @ParameterizedTest
    @CsvSource(value = {
        "CHANGE_STATE_TO_ADDITIONAL_RESPONSE_TIME_EXPIRED", "TRIGGER_LOCATION_UPDATE"
    })
    void when_change_state_to_addln_response_time_expired_nonurgent_app_with_case_loc_local_court_single_ga_type(
        String eventId) {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 5*/
        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", false
        ));
        data.put("isCcmccLocation", false);
        data.put("generalAppConsentOrder", null);
        data.put("judicialDecision", Map.of(
            "decision", "MAKE_ORDER_FOR_WRITTEN_REPRESENTATIONS"
        ));
        data.put("generalAppType", Map.of(
            "types", asList("STRIKE_OUT")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", "ADDITIONAL_RESPONSE_TIME_EXPIRED");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList
                       .get(0).get("name"),
                   is("strike out App - revisited make order for written representations"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("JudgeRevisitApplication"));
    }

    @ParameterizedTest
    @CsvSource(value = {
        "CHANGE_STATE_TO_ADDITIONAL_RESPONSE_TIME_EXPIRED"
    })
    void when_change_state_to_addln_response_time_expired_nonurgent_app_with_pre_sdo_single_ga_type_refer_LegalAdvisor(
        String eventId) {

        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", false
        ));
        data.put("isCcmccLocation", true);
        data.put("generalAppConsentOrder", null);
        data.put("judicialDecision", Map.of(
            "decision", "MAKE_ORDER_FOR_WRITTEN_REPRESENTATIONS"
        ));
        data.put("generalAppType", Map.of(
            "types", asList("STAY_THE_CLAIM")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", "ADDITIONAL_RESPONSE_TIME_EXPIRED");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList
                       .get(0).get("name"),
                   is("stay the claim App - revisited make order for written representations"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("LegalAdvisorRevisitApplication"));
    }

    @ParameterizedTest
    @CsvSource(value = {
        "CHANGE_STATE_TO_ADDITIONAL_RESPONSE_TIME_EXPIRED", "TRIGGER_LOCATION_UPDATE"
    })
    void when_change_state_to_addln_response_time_expired_nonurgent_with_post_sdo_single_ga_type_refer_judge(
        String eventId) {

        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", false
        ));
        data.put("isCcmccLocation", false);
        data.put("generalAppConsentOrder", null);
        data.put("judicialDecision", Map.of(
            "decision", "MAKE_ORDER_FOR_WRITTEN_REPRESENTATIONS"
        ));
        data.put("generalAppType", Map.of(
            "types", asList("STAY_THE_CLAIM")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", "ADDITIONAL_RESPONSE_TIME_EXPIRED");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList
                       .get(0).get("name"),
                   is("stay the claim App - revisited make order for written representations"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("JudgeRevisitApplication"));
    }

    @ParameterizedTest
    @CsvSource(value = {
        "CHANGE_STATE_TO_ADDITIONAL_RESPONSE_TIME_EXPIRED", "TRIGGER_LOCATION_UPDATE"
    })
    void when_change_state_to_addln_response_time_expired_urgent_app_with_case_loc_local_court(String eventId) {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 10*/
        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("isCcmccLocation", false);
        data.put("generalAppConsentOrder", null);
        data.put("judicialDecision", Map.of(
            "decision", "MAKE_ORDER_FOR_WRITTEN_REPRESENTATIONS"
        ));
        data.put("generalAppType", Map.of(
            "types", asList("SUMMARY_JUDGEMENT")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", "ADDITIONAL_RESPONSE_TIME_EXPIRED");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList
                       .get(0).get("name"),
                   is("summary judgement App - revisited make order for written representations"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("JudgeRevisitApplication"));
    }


    @ParameterizedTest
    @CsvSource(value = {
        "CHANGE_STATE_TO_ADDITIONAL_RESPONSE_TIME_EXPIRED"
    })
    void when_change_state_to_addln_response_time_expired_urgent_app_with_pre_sdo_refer_legalAdvisor(String eventId) {

        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("isCcmccLocation", true);
        data.put("generalAppConsentOrder", null);
        data.put("judicialDecision", Map.of(
            "decision", "MAKE_ORDER_FOR_WRITTEN_REPRESENTATIONS"
        ));
        data.put("generalAppType", Map.of(
            "types", asList("STAY_THE_CLAIM")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", "ADDITIONAL_RESPONSE_TIME_EXPIRED");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList
                       .get(0).get("name"),
                   is("stay the claim App - revisited make order for written representations"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("LegalAdvisorRevisitApplication"));
    }

    @ParameterizedTest
    @CsvSource(value = {
        "CHANGE_STATE_TO_ADDITIONAL_RESPONSE_TIME_EXPIRED", "TRIGGER_LOCATION_UPDATE"
    })
    void when_change_state_to_addln_response_time_expired_urgent_app_with_post_sdo_refer_judge(String eventId) {

        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("isCcmccLocation", false);
        data.put("generalAppConsentOrder", null);
        data.put("judicialDecision", Map.of(
            "decision", "MAKE_ORDER_FOR_WRITTEN_REPRESENTATIONS"
        ));
        data.put("generalAppType", Map.of(
            "types", asList("STAY_THE_CLAIM")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", "ADDITIONAL_RESPONSE_TIME_EXPIRED");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList
                       .get(0).get("name"),
                   is("stay the claim App - revisited make order for written representations"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("JudgeRevisitApplication"));
    }

    @Test
    void when_change_state_to_addln_response_time_expired_urgent_app_with_case_loc_local_court_multiple_ga_types() {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 10*/
        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("isCcmccLocation", false);
        data.put("generalAppConsentOrder", null);
        data.put("judicialDecision", Map.of(
            "decision", "MAKE_ORDER_FOR_WRITTEN_REPRESENTATIONS"
        ));
        data.put("generalAppType", Map.of(
            "types", asList("SUMMARY_JUDGEMENT","STRIKE_OUT")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "CHANGE_STATE_TO_ADDITIONAL_RESPONSE_TIME_EXPIRED");
        inputVariables.putValue("postEventState", "ADDITIONAL_RESPONSE_TIME_EXPIRED");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList
                       .get(0).get("name"),
                   is("Application for multiple types - revisited make order for written representations"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("JudgeRevisitApplication"));
    }

    @Test
    void when_change_state_to_addln_response_time_expired_urgent_with_pre_sdo_multiple_ga_types_refer_legalAdvisor() {

        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("isCcmccLocation", true);
        data.put("generalAppConsentOrder", null);
        data.put("judicialDecision", Map.of(
            "decision", "MAKE_ORDER_FOR_WRITTEN_REPRESENTATIONS"
        ));
        data.put("generalAppType", Map.of(
            "types", asList("STAY_THE_CLAIM","EXTEND_TIME")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "CHANGE_STATE_TO_ADDITIONAL_RESPONSE_TIME_EXPIRED");
        inputVariables.putValue("postEventState", "ADDITIONAL_RESPONSE_TIME_EXPIRED");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList
                       .get(0).get("name"),
                   is("Application for multiple types - revisited make order for written representations"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("LegalAdvisorRevisitApplication"));
    }

    @Test
    void when_change_state_to_addln_response_time_expired_urgent_with_pre_sdo_multiple_ga_types_refer_judge() {

        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("isCcmccLocation", false);
        data.put("generalAppConsentOrder", null);
        data.put("judicialDecision", Map.of(
            "decision", "MAKE_ORDER_FOR_WRITTEN_REPRESENTATIONS"
        ));
        data.put("generalAppType", Map.of(
            "types", asList("STAY_THE_CLAIM","EXTEND_TIME")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "CHANGE_STATE_TO_ADDITIONAL_RESPONSE_TIME_EXPIRED");
        inputVariables.putValue("postEventState", "ADDITIONAL_RESPONSE_TIME_EXPIRED");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList
                       .get(0).get("name"),
                   is("Application for multiple types - revisited make order for written representations"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("JudgeRevisitApplication"));
    }

    @ParameterizedTest
    @CsvSource(value = {
        "CHANGE_STATE_TO_ADDITIONAL_RESPONSE_TIME_EXPIRED", "TRIGGER_LOCATION_UPDATE"
    })
    void when_taskId_then_return_decision_making_work_for_urgent_listed_for_hearing(String eventId) {


        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 10*/
        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("isCcmccLocation", false);
        data.put("generalAppConsentOrder", null);
        data.put("judicialDecision", Map.of(
            "decision", "MAKE_ORDER_FOR_WRITTEN_REPRESENTATIONS"
        ));
        data.put("generalAppType", Map.of(
            "types", asList("SUMMARY_JUDGEMENT","STRIKE_OUT")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", "ADDITIONAL_RESPONSE_TIME_EXPIRED");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList
                       .get(0).get("name"),
                   is("Application for multiple types - revisited make order for written representations"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("JudgeRevisitApplication"));
    }

    @ParameterizedTest
    @CsvSource(value = {
        "CHANGE_STATE_TO_ADDITIONAL_RESPONSE_TIME_EXPIRED"
    })
    void when_taskId_then_return_decision_making_work_for_urgent_listed_for_hearing_refer_legalAdvisor(String eventId) {


        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("isCcmccLocation", true);
        data.put("generalAppConsentOrder", null);
        data.put("judicialDecision", Map.of(
            "decision", "MAKE_ORDER_FOR_WRITTEN_REPRESENTATIONS"
        ));
        data.put("generalAppType", Map.of(
            "types", asList("STAY_THE_CLAIM","EXTEND_TIME")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", "ADDITIONAL_RESPONSE_TIME_EXPIRED");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList
                       .get(0).get("name"),
                   is("Application for multiple types - revisited make order for written representations"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("LegalAdvisorRevisitApplication"));
    }

    @ParameterizedTest
    @CsvSource(value = {
        "CHANGE_STATE_TO_ADDITIONAL_RESPONSE_TIME_EXPIRED", "TRIGGER_LOCATION_UPDATE"
    })
    void when_taskId_then_return_decision_making_work_for_urgent_listed_for_hearing_post_sdo_refer_judge(
        String eventId) {


        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 10*/
        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("isCcmccLocation", false);
        data.put("generalAppConsentOrder", null);
        data.put("judicialDecision", Map.of(
            "decision", "MAKE_ORDER_FOR_WRITTEN_REPRESENTATIONS"
        ));
        data.put("generalAppType", Map.of(
            "types", asList("STAY_THE_CLAIM","EXTEND_TIME")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", "ADDITIONAL_RESPONSE_TIME_EXPIRED");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList
                       .get(0).get("name"),
                   is("Application for multiple types - revisited make order for written representations"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("JudgeRevisitApplication"));
    }

    @Test
    void when_taskId_and_ccmcc_then_return_decision_making_work_for_urgent_listed_for_hearing() {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 10*/
        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("isCcmccLocation", true);
        data.put("generalAppConsentOrder", null);
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<HashMap<String,Object>> typeRef
                = new TypeReference<HashMap<String,Object>>() {};
        try {
            //caseData = mapper.readValue(rawListingForHearingString, typeRef);
            caseData = mapper.readValue(rawHearingScheduledString, typeRef);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "END_JUDGE_BUSINESS_PROCESS_GASPEC");
        inputVariables.putValue("postEventState", "LISTING_FOR_A_HEARING");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
    }

    @Test
    void when_taskId_and_after_ccmcc_then_return_decision_making_work_for_urgent_listed_for_hearing() {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 10*/
        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("isCcmccLocation", false);
        data.put("generalAppConsentOrder", null);
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<HashMap<String,Object>> typeRef
                = new TypeReference<HashMap<String,Object>>() {};
        try {
            //caseData = mapper.readValue(rawListingForHearingString, typeRef);
            caseData = mapper.readValue(rawHearingScheduledString, typeRef);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "END_JUDGE_BUSINESS_PROCESS_GASPEC");
        inputVariables.putValue("postEventState", "LISTING_FOR_A_HEARING");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
    }

    @Test
    void when_taskId_and_after_ccmccC_then_return_decision_making_work_for_nonurgent_listed_for_hearing() {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 10*/
        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", false
        ));
        data.put("isCcmccLocation", false);
        data.put("generalAppConsentOrder", null);
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<HashMap<String,Object>> typeRef
                = new TypeReference<HashMap<String,Object>>() {};
        try {
            //caseData = mapper.readValue(rawListingForHearingString, typeRef);
            caseData = mapper.readValue(rawHearingScheduledString, typeRef);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "END_JUDGE_BUSINESS_PROCESS_GASPEC");
        inputVariables.putValue("postEventState", "LISTING_FOR_A_HEARING");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
    }

    @Test
    void when_taskId_and_ccmcc_then_return_decision_making_work_for_nonurgent_listed_for_hearing() {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 10*/
        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", false
        ));
        data.put("isCcmccLocation", true);
        data.put("generalAppConsentOrder", null);
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<HashMap<String,Object>> typeRef
                = new TypeReference<HashMap<String,Object>>() {};
        try {
            //caseData = mapper.readValue(rawListingForHearingString, typeRef);
            caseData = mapper.readValue(rawHearingScheduledString, typeRef);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "END_JUDGE_BUSINESS_PROCESS_GASPEC");
        inputVariables.putValue("postEventState", "LISTING_FOR_A_HEARING");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
    }

    @Test
    void when_taskId_then_return_decision_making_work_for_Order_Made() {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 10*/
        Map<String, Object> data = new HashMap<>();
        data.put("isCcmccLocation", true);
        data.put("generalAppConsentOrder", null);
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "END_JUDGE_BUSINESS_PROCESS_GASPEC");
        inputVariables.putValue("postEventState", "ORDER_MADE");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Review Application Order"));
    }

    @Test
    void when_stayOrder_deadline_ends_taskId_then_return_making_work_review_applicationOrder_withSdo_urgentAppln() {

        Map<String, Object> data = new HashMap<>();
        data.put("isCcmccLocation", true);
        data.put("generalAppConsentOrder", null);
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "END_SCHEDULER_CHECK_STAY_ORDER_DEADLINE");
        inputVariables.putValue("postEventState", "ORDER_MADE");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Revisit Application for Stay Claim"));
    }

    @Test
    void when_stayOrder_deadline_ends_taskId_then_return_making_work_review_applicationOrder_withOutSdo_urgentAppln() {

        Map<String, Object> data = new HashMap<>();
        data.put("isCcmccLocation", false);
        data.put("generalAppConsentOrder", null);
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "END_SCHEDULER_CHECK_STAY_ORDER_DEADLINE");
        inputVariables.putValue("postEventState", "ORDER_MADE");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Revisit Application for Stay Claim"));
    }

    @Test
    void when_stayOrder_deadline_ends_taskId_then_return_making_work_review_applicationOrder_withOutSdo_nonUrgent() {

        Map<String, Object> data = new HashMap<>();
        data.put("isCcmccLocation", false);
        data.put("generalAppConsentOrder", null);
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", false));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "END_SCHEDULER_CHECK_STAY_ORDER_DEADLINE");
        inputVariables.putValue("postEventState", "ORDER_MADE");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Revisit Application for Stay Claim"));
    }

    @Test
    void when_stayOrder_deadline_ends_taskId_then_return_making_work_review_applicationOrder_withSdo_nonUrgent() {

        Map<String, Object> data = new HashMap<>();
        data.put("isCcmccLocation", true);
        data.put("generalAppConsentOrder", null);
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", false));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "END_SCHEDULER_CHECK_STAY_ORDER_DEADLINE");
        inputVariables.putValue("postEventState", "ORDER_MADE");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Revisit Application for Stay Claim"));
    }

    @Test
    void when_unlessOrder_deadline_ends_taskId_then_return_making_work_review_applicationOrder_withSdo_nonUrgentApp() {

        Map<String, Object> data = new HashMap<>();
        data.put("isCcmccLocation", true);
        data.put("generalAppConsentOrder", null);
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", false));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "END_SCHEDULER_CHECK_UNLESS_ORDER_DEADLINE");
        inputVariables.putValue("postEventState", "ORDER_MADE");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Revisit Application for Unless Order"));
    }

    @Test
    void when_unlessOrder_deadline_ends_taskId_then_return_making_work_review_applicationOrder_withOutSdo_nonUrgent() {

        Map<String, Object> data = new HashMap<>();
        data.put("isCcmccLocation", false);
        data.put("generalAppConsentOrder", null);
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", false));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "END_SCHEDULER_CHECK_UNLESS_ORDER_DEADLINE");
        inputVariables.putValue("postEventState", "ORDER_MADE");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Revisit Application for Unless Order"));
    }

    @Test
    void when_unlessOrder_deadline_ends_taskId_then_return_making_work_review_applicationOrder_withSdo_urgentAppln() {

        Map<String, Object> data = new HashMap<>();
        data.put("isCcmccLocation", true);
        data.put("generalAppConsentOrder", null);
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "END_SCHEDULER_CHECK_UNLESS_ORDER_DEADLINE");
        inputVariables.putValue("postEventState", "ORDER_MADE");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Revisit Application for Unless Order"));
    }

    @Test
    void when_unlessOrder_taskId_then_return_making_work_review_applicationOrder_withOutSdo_urgentAppln() {
        Map<String, Object> data = new HashMap<>();
        data.put("isCcmccLocation", false);
        data.put("generalAppConsentOrder", null);
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "END_SCHEDULER_CHECK_UNLESS_ORDER_DEADLINE");
        inputVariables.putValue("postEventState", "ORDER_MADE");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Revisit Application for Unless Order"));
    }

    @Test
    void when_refer_to_judge_urgent_app_for_await_judicial_decision_ccmc_single_appln() {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 5*/
        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("isCcmccLocation", true);
        data.put("generalAppConsentOrder", null);
        data.put("judicialDecision", Map.of(
            "decision", "MAKE_ORDER_FOR_WRITTEN_REPRESENTATIONS"
        ));
        data.put("generalAppType", Map.of(
            "types", asList("SUMMARY_JUDGEMENT")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "REFER_TO_JUDGE");
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Application for summary judgement"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("JudgeDecideOnApplication"));

    }

    @Test
    void when_refer_to_judge_urgent_app_for_await_judicial_decision_multiple_appln_type() {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 5*/
        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("isCcmccLocation", true);
        data.put("generalAppConsentOrder", null);

        data.put("judicialDecision", Map.of(
            "decision", "MAKE_ORDER_FOR_WRITTEN_REPRESENTATIONS"
        ));
        data.put("generalAppType", Map.of(
            "types", asList("STRIKE_OUT", "SUMMARY_JUDGEMENT")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "REFER_TO_JUDGE");
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Application for multiple types"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("JudgeDecideOnApplication"));
    }

    @Test
    void when_refer_to_judge_non_urgent_app_for_await_judicial_decision_ccmc_single_appln() {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 5*/
        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", false
        ));
        data.put("isCcmccLocation", true);
        data.put("generalAppConsentOrder", null);
        data.put("judicialDecision", Map.of(
            "decision", "MAKE_ORDER_FOR_WRITTEN_REPRESENTATIONS"
        ));
        data.put("generalAppType", Map.of(
            "types", asList("SUMMARY_JUDGEMENT")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "REFER_TO_JUDGE");
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("taskId"), is("JudgeDecideOnApplication"));
        assertThat(workTypeResultList.get(0).get("name"), is("Application for summary judgement"));
    }

    @Test
    void when_refer_to_judge_non_urgent_app_for_await_judicial_decision_multiple_appln_type() {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 5*/
        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", false
        ));
        data.put("isCcmccLocation", true);
        data.put("generalAppConsentOrder", null);
        data.put("judicialDecision", Map.of(
            "decision", "MAKE_ORDER_FOR_WRITTEN_REPRESENTATIONS"
        ));
        data.put("generalAppType", Map.of(
            "types", asList("STRIKE_OUT", "SUMMARY_JUDGEMENT")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "REFER_TO_JUDGE");
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("taskId"), is("JudgeDecideOnApplication"));
        assertThat(workTypeResultList.get(0).get("name"), is("Application for multiple types"));
    }

    @Test
    void when_refer_to_judge_urgent_app_for_await_judicial_decision_ccmcc_location_no() {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 5*/
        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("isCcmccLocation", false);
        data.put("generalAppConsentOrder", null);
        data.put("judicialDecision", Map.of(
            "decision", "MAKE_ORDER_FOR_WRITTEN_REPRESENTATIONS"
        ));
        data.put("generalAppType", Map.of(
            "types", asList("STRIKE_OUT", "SUMMARY_JUDGEMENT")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "REFER_TO_JUDGE");
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
    }

    @Test
    void when_refer_to_judge_urgent_app_for_addln_response_expired_single_type() {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 5*/
        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("isCcmccLocation", true);
        data.put("generalAppConsentOrder", null);
        data.put("judicialDecision", Map.of(
            "decision", "MAKE_ORDER_FOR_WRITTEN_REPRESENTATIONS"
        ));
        data.put("generalAppType", Map.of(
            "types", asList("SUMMARY_JUDGEMENT")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "REFER_TO_JUDGE");
        inputVariables.putValue("postEventState", "ADDITIONAL_RESPONSE_TIME_EXPIRED");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList
                       .get(0).get("name"), is("summary judgement App - "
                                                   + "revisited make order for "
                                                   + "written representations"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("JudgeRevisitApplication"));
    }

    @Test
    void when_refer_to_judge_urgent_app_for_addln_response_expired_multiple_type() {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 5*/
        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("isCcmccLocation", true);
        data.put("generalAppConsentOrder", null);

        data.put("judicialDecision", Map.of(
            "decision", "MAKE_ORDER_FOR_WRITTEN_REPRESENTATIONS"
        ));
        data.put("generalAppType", Map.of(
            "types", asList("STRIKE_OUT", "SUMMARY_JUDGEMENT")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "REFER_TO_JUDGE");
        inputVariables.putValue("postEventState", "ADDITIONAL_RESPONSE_TIME_EXPIRED");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList
                       .get(0).get("name"), is("Application for multiple types - "
                                                   + "revisited make order for "
                                                   + "written representations"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("JudgeRevisitApplication"));
    }

    @Test
    void when_refer_to_judge_nonurgent_app_for_addln_response_expired_single_type() {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 5*/
        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", false
        ));
        data.put("isCcmccLocation", true);
        data.put("generalAppConsentOrder", null);
        data.put("judicialDecision", Map.of(
            "decision", "MAKE_ORDER_FOR_WRITTEN_REPRESENTATIONS"
        ));
        data.put("generalAppType", Map.of(
            "types", asList("SUMMARY_JUDGEMENT")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "REFER_TO_JUDGE");
        inputVariables.putValue("postEventState", "ADDITIONAL_RESPONSE_TIME_EXPIRED");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList
                       .get(0).get("name"), is("summary judgement App - "
                                                   + "revisited make order for "
                                                   + "written representations"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("JudgeRevisitApplication"));
    }

    @Test
    void when_refer_to_judge_non_urgent_app_for_addln_response_expired_multiple_type() {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 5*/
        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", false
        ));
        data.put("isCcmccLocation", true);
        data.put("generalAppConsentOrder", null);

        data.put("judicialDecision", Map.of(
            "decision", "MAKE_ORDER_FOR_WRITTEN_REPRESENTATIONS"
        ));
        data.put("generalAppType", Map.of(
            "types", asList("STRIKE_OUT", "SUMMARY_JUDGEMENT")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "REFER_TO_JUDGE");
        inputVariables.putValue("postEventState", "ADDITIONAL_RESPONSE_TIME_EXPIRED");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList
                       .get(0).get("name"), is("Application for multiple types - "
                                                   + "revisited make order for "
                                                   + "written representations"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("JudgeRevisitApplication"));
    }

    @Test
    void when_nonurgent_app_with_pre_sdo_single_ga_type_refer_to_legalAdvisor_event() {

        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", false
        ));
        data.put("isCcmccLocation", true);
        data.put("generalAppType", Map.of(
            "types", asList("STAY_THE_CLAIM")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "REFER_TO_LEGAL_ADVISOR");
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Application for stay the claim"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("LegalAdvisorDecideOnApplication"));
    }

    @Test
    void when_nonurgent_app_with_pre_sdo_multiple_ga_type_refer_to_legalAdvisor_event() {

        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", false
        ));
        data.put("isCcmccLocation", true);
        data.put("generalAppType", Map.of(
            "types", asList("STAY_THE_CLAIM", "SUMMARY_JUDGEMENT")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "REFER_TO_LEGAL_ADVISOR");
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Application for multiple types"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("LegalAdvisorDecideOnApplication"));
    }

    @Test
    void when_urgent_app_with_pre_sdo_multiple_ga_type_refer_to_legalAdvisor_event() {

        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("isCcmccLocation", true);
        data.put("generalAppType", Map.of(
            "types", asList("STAY_THE_CLAIM", "SUMMARY_JUDGEMENT")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "REFER_TO_LEGAL_ADVISOR");
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Application for multiple types"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("LegalAdvisorDecideOnApplication"));
    }

    @Test
    void when_urgent_app_with_pre_sdo_single_ga_type_refer_to_legalAdvisor_event() {

        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", false
        ));
        data.put("isCcmccLocation", true);
        data.put("generalAppType", Map.of(
            "types", asList("SUMMARY_JUDGEMENT")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "REFER_TO_LEGAL_ADVISOR");
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Application for summary judgement"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("LegalAdvisorDecideOnApplication"));
    }

    @Test
    void when_refer_to_legalAdvisor_nonurgent_app_for_addln_response_expired_single_type() {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 5*/
        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", false
        ));
        data.put("isCcmccLocation", true);
        data.put("generalAppConsentOrder", null);
        data.put("judicialDecision", Map.of(
            "decision", "MAKE_ORDER_FOR_WRITTEN_REPRESENTATIONS"
        ));
        data.put("generalAppType", Map.of(
            "types", asList("SUMMARY_JUDGEMENT")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "REFER_TO_LEGAL_ADVISOR");
        inputVariables.putValue("postEventState", "ADDITIONAL_RESPONSE_TIME_EXPIRED");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList
                       .get(0).get("name"), is("summary judgement App - "
                                                   + "revisited make order for "
                                                   + "written representations"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("LegalAdvisorRevisitApplication"));
    }

    @Test
    void when_refer_to_legalAdvisor_nonurgent_app_for_addln_response_expired_multiple_type() {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 5*/
        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", false
        ));
        data.put("isCcmccLocation", true);
        data.put("generalAppConsentOrder", null);
        data.put("judicialDecision", Map.of(
            "decision", "MAKE_ORDER_FOR_WRITTEN_REPRESENTATIONS"
        ));
        data.put("generalAppType", Map.of(
            "types", asList("SUMMARY_JUDGEMENT", "STAY_THE_CLAIM")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "REFER_TO_LEGAL_ADVISOR");
        inputVariables.putValue("postEventState", "ADDITIONAL_RESPONSE_TIME_EXPIRED");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList
                       .get(0).get("name"), is("Application for multiple types - revisited "
                                                                      + "make order for written representations"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("LegalAdvisorRevisitApplication"));
    }

    @Test
    void when_refer_to_legalAdvisor_urgent_app_for_addln_response_expired_single_type() {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 5*/
        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("isCcmccLocation", true);
        data.put("generalAppConsentOrder", null);
        data.put("judicialDecision", Map.of(
            "decision", "MAKE_ORDER_FOR_WRITTEN_REPRESENTATIONS"
        ));
        data.put("generalAppType", Map.of(
            "types", asList("SUMMARY_JUDGEMENT")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "REFER_TO_LEGAL_ADVISOR");
        inputVariables.putValue("postEventState", "ADDITIONAL_RESPONSE_TIME_EXPIRED");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList
                       .get(0).get("name"), is("summary judgement App - "
                                                   + "revisited make order for "
                                                   + "written representations"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("LegalAdvisorRevisitApplication"));
    }

    @Test
    void when_refer_to_legalAdvisor_urgent_app_for_addln_response_expired_multiple_type() {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 5*/
        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("isCcmccLocation", true);
        data.put("generalAppConsentOrder", null);
        data.put("judicialDecision", Map.of(
            "decision", "MAKE_ORDER_FOR_WRITTEN_REPRESENTATIONS"
        ));
        data.put("generalAppType", Map.of(
            "types", asList("SUMMARY_JUDGEMENT", "STAY_THE_CLAIM")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "REFER_TO_LEGAL_ADVISOR");
        inputVariables.putValue("postEventState", "ADDITIONAL_RESPONSE_TIME_EXPIRED");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList
                       .get(0).get("name"), is("Application for multiple types - revisited "
                                                   + "make order for written representations"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("LegalAdvisorRevisitApplication"));
    }
}
