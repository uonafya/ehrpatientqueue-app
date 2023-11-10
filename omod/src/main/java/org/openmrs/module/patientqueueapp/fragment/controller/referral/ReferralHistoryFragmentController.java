package org.openmrs.module.patientqueueapp.fragment.controller.referral;

import org.openmrs.Encounter;
import org.openmrs.EncounterType;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.api.EncounterService;
import org.openmrs.api.context.Context;
import org.openmrs.parameter.EncounterSearchCriteria;
import org.openmrs.ui.framework.SimpleObject;
import org.openmrs.ui.framework.UiUtils;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.springframework.web.bind.annotation.RequestParam;

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
        if(encounter != null) {
            obsSet = new HashSet<Obs>(encounter.getAllObs());
        }
        return SimpleObject.fromCollection(obsSet, ui, "concept_id");
    }
}
