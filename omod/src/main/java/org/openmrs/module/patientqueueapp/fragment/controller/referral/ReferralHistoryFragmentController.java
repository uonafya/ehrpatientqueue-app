package org.openmrs.module.patientqueueapp.fragment.controller.referral;

import ca.uhn.fhir.parser.IParser;
import com.google.common.base.Strings;
import org.hl7.fhir.r4.model.CodeableConcept;
import org.hl7.fhir.r4.model.Coding;
import org.hl7.fhir.r4.model.Observation;
import org.hl7.fhir.r4.model.Reference;
import org.hl7.fhir.r4.model.Resource;
import org.hl7.fhir.r4.model.ServiceRequest;
import org.openmrs.Encounter;
import org.openmrs.EncounterType;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.api.EncounterService;
import org.openmrs.api.context.Context;
import org.openmrs.module.ehrconfigs.utils.EhrConfigsUtils;
import org.openmrs.module.kenyaemrIL.api.KenyaEMRILService;
import org.openmrs.module.kenyaemrIL.api.shr.FhirConfig;
import org.openmrs.module.kenyaemrIL.programEnrollment.ExpectedTransferInPatients;
import org.openmrs.module.patientqueueapp.model.ObsSimplifier;
import org.openmrs.parameter.EncounterSearchCriteria;
import org.openmrs.ui.framework.SimpleObject;
import org.openmrs.ui.framework.UiUtils;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ReferralHistoryFragmentController {

    public void controller(FragmentModel model, @RequestParam(value = "patientId", required = false) Patient patient) {

    model.addAttribute("encounters", getEncounters(patient));
    }

    private List<Encounter> getEncounters(Patient patient) {
        EncounterService encounterService = Context.getEncounterService();
        String OPD_ENCOUNTER_UUID = "ba45c278-f290-11ea-9666-1b3e6e848887";
        EncounterType opdEncounterType = encounterService.getEncounterTypeByUuid(OPD_ENCOUNTER_UUID);
        return encounterService.getEncounters(
                new EncounterSearchCriteria(
                        patient,
                        null,
                        null,
                        null,
                        null,
                        null,
                        Arrays.asList(opdEncounterType),
                        null,
                        null,
                        null,
                        false)
        );
    }

    public List<SimpleObject> getObservationPerEncounter(@RequestParam(value = "encounterId", required = false) Encounter encounter, UiUtils ui) {
        Set<Obs> obsSet = null;
        List<ObsSimplifier> obsSimplifierList = new ArrayList<ObsSimplifier>();
        ObsSimplifier obsSimplifier;
        if(encounter != null) {
            obsSet = new HashSet<Obs>(encounter.getAllObs());
            for(Obs obs: obsSet){
                obsSimplifier = new ObsSimplifier();
                obsSimplifier.setQuestion(obs.getConcept().getDisplayString());
                obsSimplifier.setResponse(EhrConfigsUtils.getObsValues(obs));
                if(obs.getComment() != null) {
                    obsSimplifier.setComments(obs.getComment());
                }
                else {
                    obsSimplifier.setComments("");
                }
                obsSimplifierList.add(obsSimplifier);
            }
        }
        return SimpleObject.fromCollection(obsSimplifierList, ui, "question", "response", "comments");
    }

    public SimpleObject getPatientShrHistory(@RequestParam(value = "activeId", required = false) Integer activeId, UiUtils ui) {
        //Update  referral category and reasons
        FhirConfig fhirConfig = Context.getRegisteredComponents(FhirConfig.class).get(0);

        ExpectedTransferInPatients patientReferral = Context.getService(KenyaEMRILService.class).getCommunityReferralsById(activeId);
        IParser parser = fhirConfig.getFhirContext().newJsonParser().setPrettyPrint(true);

        ServiceRequest serviceRequest;
        SimpleObject referralsDetailsObject = null;
        List<SimpleObject> list = new ArrayList<SimpleObject>();
        if (patientReferral != null) {

            serviceRequest = parser.parseResource(ServiceRequest.class, patientReferral.getPatientSummary());

            Set<String> category = new HashSet<String>();
            String referralDate = "";
            if (!serviceRequest.getCategory().isEmpty()) {
                for (CodeableConcept c : serviceRequest.getCategory()) {
                    for (Coding code : c.getCoding()) {
                        category.add(code.getDisplay());
                    }
                }
            }

            List<String> reasons = new ArrayList<String>();

            for (CodeableConcept codeableConcept : serviceRequest.getReasonCode()) {
                if (!codeableConcept.getCoding().isEmpty()) {
                    for (Coding code : codeableConcept.getCoding()) {
                        reasons.add(code.getDisplay());
                    }
                }
            }
            if (serviceRequest.getAuthoredOn() != null) {
                referralDate = new SimpleDateFormat("yyyy-MM-dd").format(serviceRequest.getAuthoredOn());
            }

            if (!serviceRequest.getSupportingInfo().isEmpty()) {
                for (Reference r : serviceRequest.getSupportingInfo()) {
                    SimpleObject object = new SimpleObject();
                    String obsId = r.getReference();
                    System.out.println("OBS ID " + obsId);

                    Resource resource = fhirConfig.fetchFhirResource("Observation", obsId);
                    if (resource == null) {
                        break;
                    }
                    Observation observation = (Observation) resource;

                    List<String> theTest = new ArrayList<String>();
                    List<String> theFindings = new ArrayList<String>();
                    String theTxPlan = "";

                    if (observation.getCode() != null && !observation.getCode().getCoding().isEmpty()) {
                        for (Coding c : observation.getCode().getCoding()) {
                            String display = !Strings.isNullOrEmpty(c.getDisplay()) ? c.getDisplay() : c.getCode();
                            theTest.add(display);
                        }
                    }

                    if (observation.getValue() != null) {
                        CodeableConcept codeableConcept = (CodeableConcept) observation.getValue();
                        if (!codeableConcept.getCoding().isEmpty()) {
                            for (Coding c : codeableConcept.getCoding()) {
                                String display = !Strings.isNullOrEmpty(c.getDisplay()) ? c.getDisplay() : c.getCode();
                                theFindings.add(display);
                            }
                        }
                    }


                    if (!observation.getNote().isEmpty() && !Strings.isNullOrEmpty(observation.getNoteFirstRep().getText())) {
                        System.out.println("OBS NOTE" + observation.getCode().getCodingFirstRep().getCode());
                        theTxPlan = observation.getNoteFirstRep().getText();
                    }

                    object.put("theTests", String.join(" ,", theTest));
                    object.put("theFindings", String.join(" ,", theFindings));
                    object.put("theTxPlan", theTxPlan);
                    list.add(object);
                }
            }

            String note = "";
            if (!serviceRequest.getNote().isEmpty()) {
                note = serviceRequest.getNoteFirstRep().getText();
            }

            referralsDetailsObject = SimpleObject.create("category", String.join(",  ", category),
                    "reasonCode", String.join(", ", reasons),
                    "referralDate", referralDate,
                    "clinicalNote", note,
                    "cancerReferral", list
            );

        }
        return referralsDetailsObject;
    }

    public List<SimpleObject> addPatientLocallyAndRedirect(@RequestParam(value = "activeId", required = false) Patient patient, UiUtils ui) {
        return null;
    }
}
