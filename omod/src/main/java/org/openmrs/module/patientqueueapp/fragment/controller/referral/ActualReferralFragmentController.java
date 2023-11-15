package org.openmrs.module.patientqueueapp.fragment.controller.referral;

import org.openmrs.Concept;
import org.openmrs.ConceptAnswer;
import org.openmrs.Location;
import org.openmrs.Patient;
import org.openmrs.api.context.Context;
import org.openmrs.module.hospitalcore.HospitalCoreService;
import org.openmrs.ui.framework.SimpleObject;
import org.openmrs.ui.framework.UiUtils;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ActualReferralFragmentController {

    public void controller(FragmentModel model, @RequestParam(value = "patientId", required = false) Patient patient) {
        Concept referralReasonCoded = Context.getConceptService().getConceptByUuid("1887AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        model.addAttribute("referralReason", getReferralReasons(referralReasonCoded));
    }

    private List<Concept> getReferralReasons(Concept concept) {
        Collection<ConceptAnswer> conceptAnswerList =  concept.getAnswers();
        List<Concept> conceptList = new ArrayList<Concept>();
        for(ConceptAnswer conceptAnswer : conceptAnswerList) {
            conceptList.add(conceptAnswer.getAnswerConcept());
        }
        return conceptList;
    }

    public List<SimpleObject> getEncounterLocation(@RequestParam(value = "q", required = false) String name, UiUtils ui) {
        HospitalCoreService hospitalCoreService = Context.getService(HospitalCoreService.class);
        List<Location> locationList = hospitalCoreService.getLocationsBasedOnNameOrMflCode(name);
        return SimpleObject.fromCollection(locationList, ui, "locationId", "name", "uuid");
    }

    public void savePatientReferral(@RequestParam(value = "referralType", required = false) String referralType,
                                    @RequestParam(value = "referralCommunityUnit", required = false) String referralCommunityUnit,
                                    @RequestParam(value = "referralCommunityName", required = false) String referralCommunityName,
                                    @RequestParam(value = "referralFacilityLocation", required = false) String referralFacilityLocation,
                                    @RequestParam(value = "referralReason", required = false) String referralReason,
                                    @RequestParam(value = "referralNotes", required = false) String referralNotes
                                    ){



    }
}
