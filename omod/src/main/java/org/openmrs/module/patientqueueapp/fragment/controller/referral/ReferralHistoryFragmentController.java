package org.openmrs.module.patientqueueapp.fragment.controller.referral;

import org.openmrs.Encounter;
import org.openmrs.EncounterType;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.api.EncounterService;
import org.openmrs.api.context.Context;
import org.openmrs.module.ehrconfigs.utils.EhrConfigsUtils;
import org.openmrs.module.patientqueueapp.model.ObsSimplifier;
import org.openmrs.parameter.EncounterSearchCriteria;
import org.openmrs.ui.framework.SimpleObject;
import org.openmrs.ui.framework.UiUtils;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.springframework.web.bind.annotation.RequestParam;

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

    public List<SimpleObject> getPatientShrHistory(@RequestParam(value = "activeId", required = false) Patient patient, UiUtils ui) {
        /*Set<Obs> obsSet = null;
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
        }*/
        return null;//SimpleObject.fromCollection(obsSimplifierList, ui, "question", "response", "comments");
    }

    public List<SimpleObject> addPatientLocallyAndRedirect(@RequestParam(value = "activeId", required = false) Patient patient, UiUtils ui) {
        return null;
    }
}
