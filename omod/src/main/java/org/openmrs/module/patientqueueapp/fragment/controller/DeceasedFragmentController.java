package org.openmrs.module.patientqueueapp.fragment.controller;

import org.openmrs.Encounter;
import org.openmrs.EncounterType;
import org.openmrs.Obs;
import org.openmrs.api.context.Context;
import org.openmrs.module.hospitalcore.HospitalCoreService;
import org.openmrs.module.hospitalcore.util.DateUtils;
import org.openmrs.module.patientqueueapp.DeceasedSimplifier;
import org.openmrs.module.patientqueueapp.PatientQueueUtils;
import org.openmrs.ui.framework.fragment.FragmentModel;

import java.util.ArrayList;
import java.util.List;

public class DeceasedFragmentController {
    public void controller(FragmentModel model) {
        List<DeceasedSimplifier> deceasedSimplifierList = new ArrayList<DeceasedSimplifier>();
        DeceasedSimplifier deceasedSimplifier = null;
        EncounterType deceasedEncounterType = Context.getEncounterService().getEncounterTypeByUuid("bf484793-1734-4f57-a6f1-b866545ca8df");

        for(Encounter encounter : Context.getService(HospitalCoreService.class).getDeadPatientsForEhr(deceasedEncounterType, null, null)) {
            if(encounter.getObs() != null) {
                deceasedSimplifier = new DeceasedSimplifier();
                deceasedSimplifier.setName(encounter.getPatient().getGivenName() + " " + encounter.getPatient().getFamilyName());
                deceasedSimplifier.setEntryTime(DateUtils.getDateFromDateAsString(encounter.getPatient().getDateCreated(), "yyyy-MM-dd"));
                for(Obs obs: encounter.getObs()) {
                    if(obs.getConcept().equals(PatientQueueUtils.dateOfDeathQuestion) && obs.getValueDatetime() != null) {
                            deceasedSimplifier.setdOfDeath((DateUtils.getDateFromDateAsString(obs.getValueDatetime(), "yyyy-MM-dd")));
                        }
                        if(obs.getConcept().equals(PatientQueueUtils.causeOfDeathQuestion) && obs.getValueCoded() != null) {
                            deceasedSimplifier.setCauseOfDeath(obs.getValueCoded().getDisplayString());
                        }
                        if(obs.getConcept().equals(PatientQueueUtils.causeOfDeathNonCodedQuestion) && obs.getValueText() != null) {
                            deceasedSimplifier.setCauseOfDeathNonCodded(obs.getValueText());
                        }
                }
                if(encounter.getPatient().getDead()) {
                    deceasedSimplifier.setStatus("DECEASED");
                }

                deceasedSimplifierList.add(deceasedSimplifier);
            }
        }
        model.addAttribute("allDeceasedAndConfirmedCases", deceasedSimplifierList);
    }
}
