package org.openmrs.module.kenyaemrorderentry.labDataExchange;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang3.StringUtils;
import org.openmrs.Encounter;
import org.openmrs.Obs;
import org.openmrs.Order;
import org.openmrs.Patient;
import org.openmrs.PatientIdentifier;
import org.openmrs.api.context.Context;
import org.openmrs.module.kenyacore.RegimenMappingUtils;
import org.openmrs.module.kenyaemrorderentry.manifest.LabManifestOrder;
import org.openmrs.module.kenyaemrorderentry.util.Utils;
import org.openmrs.ui.framework.SimpleObject;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import static org.openmrs.module.kenyaemrorderentry.labDataExchange.LabOrderDataExchange.getOrderReasonCode;

/**
 * A generic class for implementing system specific web requests
 */
public abstract class LabWebRequest {


    private Integer manifestType; // i.e VL or EID

    public LabWebRequest() {
    }

    public LabWebRequest(Integer orderType) {
        this.manifestType = orderType;
    }
    public abstract boolean checkRequirements();

    public abstract void postSamples(LabManifestOrder manifestOrder, String manifestStatus) throws IOException;

    public abstract void pullResult(List<Integer> orderIds, List<Integer> manifestOrderIds) throws IOException;

    /**
     * Generate cross cutting object for post request
     * @param o
     * @param dateSampleCollected
     * @param dateSampleSeparated
     * @param sampleType
     * @param manifestID
     * @return
     */
    public ObjectNode baselinePostRequestPayload(Order o, Date dateSampleCollected, Date dateSampleSeparated, String sampleType, String manifestID) {
        Patient patient = o.getPatient();
        ObjectNode test = Utils.getJsonNodeFactory().objectNode();

        String dob = patient.getBirthdate() != null ? Utils.getSimpleDateFormat("yyyy-MM-dd").format(patient.getBirthdate()) : "";
        PatientIdentifier cccNumber = patient.getPatientIdentifier(Utils.getUniquePatientNumberIdentifierType());

        Integer entryPointAnswer = null;
        Integer entryPointQuestion = 1855;
        Integer prophylaxisAnswer = null;
        Integer prophylaxisQuestion = 1282;
        String mothersRegimenAnswer = "";
        Integer mothersRegimenQuestion = 1088;
        Integer pcrSampleCodeAnswer = null;
        Integer pcrSampleCodeQuestion = 162084;
        String feedingMethodAnswer = "";
        Integer feedingMethodQuestion = 1151;

        Encounter lastHeiEnrollmentEncounter = Utils.lastEncounter(Context.getPatientService().getPatient(o.getPatient().getPatientId()), Context.getEncounterService().getEncounterTypeByUuid("415f5136-ca4a-49a8-8db3-f994187c3af6"));   //last Hei Enrollement encounter
        Encounter lastHeiCWCFollowupEncounter = Utils.lastEncounter(Context.getPatientService().getPatient(o.getPatient().getPatientId()), Context.getEncounterService().getEncounterTypeByUuid("bcc6da85-72f2-4291-b206-789b8186a021"));   //last Hei CWC Folowup encounter
        if (lastHeiEnrollmentEncounter != null) {
               //Entry point
            for (Obs obs : lastHeiEnrollmentEncounter.getObs()) {
                if (obs.getConcept().getConceptId().equals(entryPointQuestion) && (obs.getValueCoded().getConceptId().equals(160542))) {
                    entryPointAnswer = 2;    //OPD
                }else if(obs.getConcept().getConceptId().equals(entryPointQuestion) && (obs.getValueCoded().getConceptId().equals(160456))) {
                    entryPointAnswer = 3;      //Maternity
                }else if(obs.getConcept().getConceptId().equals(entryPointQuestion) && (obs.getValueCoded().getConceptId().equals(162050))) {
                    entryPointAnswer = 4;      //CCC
                }else if(obs.getConcept().getConceptId().equals(entryPointQuestion) && (obs.getValueCoded().getConceptId().equals(160538))) {
                    entryPointAnswer = 5;      //MCH/PMTCT
                }else if(obs.getConcept().getConceptId().equals(entryPointQuestion) && (obs.getValueCoded().getConceptId().equals(5622))){
                    entryPointAnswer = 6;      //Other
                }
           //Prophylaxis
                if (obs.getConcept().getConceptId().equals(prophylaxisQuestion) && (obs.getValueCoded().getConceptId().equals(80586))) {
                    prophylaxisAnswer = 1;    //AZT for 6 weeks + NVP for 12 weeks
                }else if(obs.getConcept().getConceptId().equals(prophylaxisQuestion) && (obs.getValueCoded().getConceptId().equals(1652))) {
                    prophylaxisAnswer = 2;      //AZT for 6 weeks + NVP for >12 weeks
                }else if(obs.getConcept().getConceptId().equals(prophylaxisQuestion) && (obs.getValueCoded().getConceptId().equals(1149))) {
                    prophylaxisAnswer = 3;      //None
                }else if(obs.getConcept().getConceptId().equals(prophylaxisQuestion) && (obs.getValueCoded().getConceptId().equals(1107))) {
                    prophylaxisAnswer = 4;      //Other
                }
                //pmtct_regimen_of_mother
                if (obs.getConcept().getConceptId().equals(mothersRegimenQuestion) && (obs.getValueCoded().getConceptId().equals(1652))) {
                    mothersRegimenAnswer = "PM3";    //PM3= AZT+3TC+NVP
                }else if(obs.getConcept().getConceptId().equals(mothersRegimenQuestion) && (obs.getValueCoded().getConceptId().equals(160124))) {
                    mothersRegimenAnswer = "PM4";      //AZT+ 3TC+ EFV
                }else if(obs.getConcept().getConceptId().equals(mothersRegimenQuestion) && (obs.getValueCoded().getConceptId().equals(162561))) {
                    mothersRegimenAnswer = "PM5";      //AZT+3TC+ LPV/r
                }else if(obs.getConcept().getConceptId().equals(mothersRegimenQuestion) && (obs.getValueCoded().getConceptId().equals(162565))) {
                    mothersRegimenAnswer = "PM6";     //TDC+3TC+NVP
                }else if(obs.getConcept().getConceptId().equals(mothersRegimenQuestion) && (obs.getValueCoded().getConceptId().equals(164505))) {
                    mothersRegimenAnswer = "PM9";     //TDF+3TC+EFV
                }else if(obs.getConcept().getConceptId().equals(mothersRegimenQuestion) && (obs.getValueCoded().getConceptId().equals(164511))) {
                    mothersRegimenAnswer = "PM10";     //AZT+3TC+ATV/r
                }else if(obs.getConcept().getConceptId().equals(mothersRegimenQuestion) && (obs.getValueCoded().getConceptId().equals(164512))) {
                    mothersRegimenAnswer = "PM11";     //TDF+3TC+ATV/r
                }else if(obs.getConcept().getConceptId().equals(mothersRegimenQuestion) && (obs.getValueCoded().getConceptId().equals(164512))) {
                    mothersRegimenAnswer = "PM11";     //TDF+3TC+ATV/r
                }
            }
        }

        if (lastHeiCWCFollowupEncounter != null) {
            //Entry point
            for (Obs obs : lastHeiEnrollmentEncounter.getObs()) {
                //pcr sample code
                if (obs.getConcept().getConceptId().equals(pcrSampleCodeQuestion) && (obs.getValueCoded().getConceptId().equals(162080))) {
                    pcrSampleCodeAnswer = 1;    //Initial PCR (6week or first contact)
                }else if(obs.getConcept().getConceptId().equals(pcrSampleCodeQuestion) && (obs.getValueCoded().getConceptId().equals(162081))) {
                    pcrSampleCodeAnswer = 2;      //2nd PCR (6 months)
                }else if(obs.getConcept().getConceptId().equals(pcrSampleCodeQuestion) && (obs.getValueCoded().getConceptId().equals(162082))) {
                    pcrSampleCodeAnswer = 3;      //3rd PCR (12months)
                }else if(obs.getConcept().getConceptId().equals(pcrSampleCodeQuestion) && (obs.getValueCoded().getConceptId().equals(162083))) {
                    pcrSampleCodeAnswer = 4;      //Confirmatory PCR and Baseline VL
                }
                // Baby feeding method
                if (obs.getConcept().getConceptId().equals(feedingMethodQuestion) && (obs.getValueCoded().getConceptId().equals(5526))) {
                    feedingMethodAnswer = "EBF";    //Exclusive Breast Feeding
                }else if(obs.getConcept().getConceptId().equals(feedingMethodQuestion) && (obs.getValueCoded().getConceptId().equals(1595))) {
                    feedingMethodAnswer = "ERF";      //Exclusive Replacement Feeding
                }else if(obs.getConcept().getConceptId().equals(feedingMethodQuestion) && (obs.getValueCoded().getConceptId().equals(6046))) {
                    feedingMethodAnswer = "MF";      //MF= Mixed Feeding
                }
            }
        }


        String fullName = "";

        if (patient.getGivenName() != null) {
            fullName += patient.getGivenName();
        }

        if (patient.getMiddleName() != null) {
            fullName += " " + patient.getMiddleName();
        }

        if (patient.getFamilyName() != null) {
            fullName += " " + patient.getFamilyName();
        }

        test.put("dob", dob);
        test.put("patient_name", fullName);
        test.put("sex", patient.getGender().equals("M") ? "1" : patient.getGender().equals("F") ? "2" : "3");
        test.put("datecollected", Utils.getSimpleDateFormat("yyyy-MM-dd").format(dateSampleCollected));
        test.put("sampletype", manifestType.toString());
        test.put("order_no", o.getOrderId().toString());
        test.put("patient_identifier", cccNumber != null ? cccNumber.getIdentifier() : "");
        test.put("lab", "");


        if (manifestType == 1) { // we are using 1 for EID and 2 for VL
            PatientIdentifier heiNumber = patient.getPatientIdentifier(Utils.getHeiNumberIdentifierType());
            test.put("hei_identifier", heiNumber != null ? heiNumber.getIdentifier() : "");
            test.put("prophylaxis", prophylaxisAnswer);
            test.put("pcr_sample_code", pcrSampleCodeAnswer);
            test.put("entry_point", entryPointAnswer);
            test.put("infant_feeding_code", feedingMethodAnswer);
            test.put("age_of_mother", Utils.getMothersAge(patient));
            test.put("ccc_number_of_mother", Utils.getMothersUniquePatientNumber(patient));
            test.put("pmtct_regimen_of_mother", mothersRegimenAnswer);
            test.put("mother_vl_result", ""); // within the last 6 months

        } else if (manifestType == 2) {
            Encounter originalRegimenEncounter = RegimenMappingUtils.getFirstEncounterForProgram(patient, "ARV");
            Encounter currentRegimenEncounter = RegimenMappingUtils.getLastEncounterForProgram(patient, "ARV");
            if (currentRegimenEncounter == null) {
                return test;
            }

            SimpleObject regimenDetails = RegimenMappingUtils.buildRegimenChangeObject(currentRegimenEncounter.getObs(), currentRegimenEncounter);
            String regimenName = (String) regimenDetails.get("regimenShortDisplay");
            String regimenLine = (String) regimenDetails.get("regimenLine");
            String nascopCode = "";
            if (StringUtils.isNotBlank(regimenName )) {
                nascopCode = RegimenMappingUtils.getDrugNascopCodeByDrugNameAndRegimenLine(regimenName, regimenLine);
            }

            if (StringUtils.isBlank(nascopCode) && StringUtils.isNotBlank(regimenLine)) {
                nascopCode = RegimenMappingUtils.getNonStandardCodeFromRegimenLine(regimenLine);
            }

            test.put("justification", o.getOrderReason() != null ? getOrderReasonCode(o.getOrderReason().getUuid()) : "");

            //add to list only if code is found. This is a temp measure to avoid sending messages with null regimen codes
            if (StringUtils.isNotBlank(nascopCode)) {

                test.put("prophylaxis", nascopCode);
                if (patient.getGender().equals("F")) {
                    test.put("pmtct", "3");
                }
                test.put("initiation_date", originalRegimenEncounter != null ? Utils.getSimpleDateFormat("yyyy-MM-dd").format(originalRegimenEncounter.getEncounterDatetime()) : "");
                test.put("dateinitiatedonregimen", currentRegimenEncounter != null ? Utils.getSimpleDateFormat("yyyy-MM-dd").format(currentRegimenEncounter.getEncounterDatetime()) : "");

            }
        }

        return test;
    }

    public abstract ObjectNode completePostPayload(Order o, Date dateSampleCollected, Date dateSampleSeparated, String sampleType, String manifestID);

    public Integer getManifestType() {
        return manifestType;
    }

    public void setManifestType(Integer manifestType) {
        this.manifestType = manifestType;
    }
}