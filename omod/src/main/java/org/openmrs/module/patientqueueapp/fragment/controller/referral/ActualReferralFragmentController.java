package org.openmrs.module.patientqueueapp.fragment.controller.referral;

import org.apache.commons.lang3.StringUtils;
import org.openmrs.Concept;
import org.openmrs.ConceptAnswer;
import org.openmrs.Encounter;
import org.openmrs.EncounterType;
import org.openmrs.Location;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.api.EncounterService;
import org.openmrs.api.context.Context;
import org.openmrs.module.ehrconfigs.utils.EhrConfigsUtils;
import org.openmrs.module.hospitalcore.HospitalCoreService;
import org.openmrs.module.hospitalcore.model.EhrReferralComponent;
import org.openmrs.module.hospitalcore.util.HospitalCoreUtils;
import org.openmrs.module.kenyaemr.api.KenyaEmrService;
import org.openmrs.ui.framework.SimpleObject;
import org.openmrs.ui.framework.UiUtils;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class ActualReferralFragmentController {

    public void controller(FragmentModel model, @RequestParam(value = "patientId", required = false) Patient patient) {
        Concept referralReasonCoded = Context.getConceptService().getConceptByUuid("1887AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        model.addAttribute("referralReason", getReferralReasons(referralReasonCoded));
        model.addAttribute("patient", patient.getPatientId());
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
                                    @RequestParam(value = "referralReason", required = false) Concept referralReason,
                                    @RequestParam(value = "referralNotes", required = false) String referralNotes,
                                    @RequestParam(value = "patient", required = false) Patient patient
                                    ){

        EncounterService encounterService = Context.getEncounterService();
        HospitalCoreService hospitalCoreService = Context.getService(HospitalCoreService.class);
        EncounterType referralEncounterType = encounterService.getEncounterTypeByUuid("ee366157-d40b-4204-8de8-c24262c65b5a");
        ////////referral type 160481AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA
        //////community unit code 8c55b6c9-f2c8-45a7-9d5e-cc900a8fa8f7
        /////community unit name 3806ee11-4f21-4176-82da-1a27be1aeaaf
        ////facility referred to 159495AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA
        //reason for referral 1887AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA
        //referral notes 159395AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA
        EhrReferralComponent ehrReferralComponent;
        Obs referralCommunityUnitCodeObs = null;
        Obs referralCommunityNameObs = null;
        Obs referralFacilityLocationObs = null;
        Obs referralReasonObs = null;
        Obs referralNotesObs = null;
        if(referralType != null) {
            ehrReferralComponent = new EhrReferralComponent();
            ehrReferralComponent.setReferralType(referralType);
            //add an encounter creation and attach obs to it
            Encounter referralEncounter = new Encounter();
            referralEncounter.setPatient(patient);
            referralEncounter.setEncounterType(referralEncounterType);
            referralEncounter.setCreator(Context.getAuthenticatedUser());
            referralEncounter.setProvider(HospitalCoreUtils.getDefaultEncounterRole(),HospitalCoreUtils.getProvider(Context.getAuthenticatedUser().getPerson()));
            referralEncounter.setEncounterDatetime(new Date());
            referralEncounter.setLocation(Context.getService(KenyaEmrService.class).getDefaultLocation());
            referralEncounter.setVisit(EhrConfigsUtils.getLastVisitForPatient(patient));
            referralEncounter.setDateCreated(new Date());

            //start building observations referral type
            Obs referralTypeObs = new Obs();
            referralTypeObs.setPerson(patient);
            referralTypeObs.setObsDatetime(new Date());
            referralTypeObs.setEncounter(referralEncounter);
            referralTypeObs.setLocation(Context.getService(KenyaEmrService.class).getDefaultLocation());
            referralTypeObs.setDateCreated(new Date());
            referralTypeObs.setCreator(Context.getAuthenticatedUser());
            referralTypeObs.setConcept(Context.getConceptService().getConceptByUuid("160481AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"));
            referralTypeObs.setValueCoded(referralType(referralType));

            if(StringUtils.isNotBlank(referralCommunityUnit)) {
                ehrReferralComponent.setReferralCommunityUnit(referralCommunityUnit);
                //referral community unit code
                referralCommunityUnitCodeObs = new Obs();
                referralCommunityUnitCodeObs.setPerson(patient);
                referralCommunityUnitCodeObs.setObsDatetime(new Date());
                referralCommunityUnitCodeObs.setEncounter(referralEncounter);
                referralCommunityUnitCodeObs.setLocation(Context.getService(KenyaEmrService.class).getDefaultLocation());
                referralCommunityUnitCodeObs.setDateCreated(new Date());
                referralCommunityUnitCodeObs.setCreator(Context.getAuthenticatedUser());
                referralCommunityUnitCodeObs.setConcept(Context.getConceptService().getConceptByUuid("8c55b6c9-f2c8-45a7-9d5e-cc900a8fa8f7"));
                referralCommunityUnitCodeObs.setValueText(referralCommunityUnit);
            }
            if(StringUtils.isNotBlank(referralCommunityName)){
                ehrReferralComponent.setReferralCommunityName(referralCommunityName);
                referralCommunityNameObs = new Obs();
                referralCommunityNameObs.setPerson(patient);
                referralCommunityNameObs.setObsDatetime(new Date());
                referralCommunityNameObs.setEncounter(referralEncounter);
                referralCommunityNameObs.setLocation(Context.getService(KenyaEmrService.class).getDefaultLocation());
                referralCommunityNameObs.setDateCreated(new Date());
                referralCommunityNameObs.setCreator(Context.getAuthenticatedUser());
                referralCommunityNameObs.setConcept(Context.getConceptService().getConceptByUuid("3806ee11-4f21-4176-82da-1a27be1aeaaf"));
                referralCommunityNameObs.setValueText(referralCommunityName);
            }
            if(StringUtils.isNotBlank(referralFacilityLocation)) {
                ehrReferralComponent.setReferralFacilityLocation(referralFacilityLocation);
                referralFacilityLocationObs = new Obs();
                referralFacilityLocationObs.setPerson(patient);
                referralFacilityLocationObs.setObsDatetime(new Date());
                referralFacilityLocationObs.setEncounter(referralEncounter);
                referralFacilityLocationObs.setLocation(Context.getService(KenyaEmrService.class).getDefaultLocation());
                referralFacilityLocationObs.setDateCreated(new Date());
                referralFacilityLocationObs.setCreator(Context.getAuthenticatedUser());
                referralFacilityLocationObs.setConcept(Context.getConceptService().getConceptByUuid("159495AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"));
                referralFacilityLocationObs.setValueText(Context.getLocationService().getLocation(referralFacilityLocation).getUuid());
            }
            if(referralReason != null) {
                ehrReferralComponent.setReferralReason(referralReason);
                referralReasonObs = new Obs();
                referralReasonObs.setPerson(patient);
                referralReasonObs.setObsDatetime(new Date());
                referralReasonObs.setEncounter(referralEncounter);
                referralReasonObs.setLocation(Context.getService(KenyaEmrService.class).getDefaultLocation());
                referralReasonObs.setDateCreated(new Date());
                referralReasonObs.setCreator(Context.getAuthenticatedUser());
                referralReasonObs.setConcept(Context.getConceptService().getConceptByUuid("1887AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"));
                referralReasonObs.setValueCoded(referralReason);
            }
            if(StringUtils.isNotBlank(referralNotes)) {
                ehrReferralComponent.setReferralNotes(referralNotes);
                referralNotesObs = new Obs();
                referralNotesObs.setPerson(patient);
                referralNotesObs.setObsDatetime(new Date());
                referralNotesObs.setEncounter(referralEncounter);
                referralNotesObs.setLocation(Context.getService(KenyaEmrService.class).getDefaultLocation());
                referralNotesObs.setDateCreated(new Date());
                referralNotesObs.setCreator(Context.getAuthenticatedUser());
                referralNotesObs.setConcept(Context.getConceptService().getConceptByUuid("159395AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"));
                referralNotesObs.setValueText(referralNotes);
            }
            ehrReferralComponent.setCreatedOn(new Date());
            ehrReferralComponent.setCreatorBy(Context.getAuthenticatedUser());
            ehrReferralComponent.setPatient(patient);

            //save the component and persist
            hospitalCoreService.createEhrReferralComponent(ehrReferralComponent);



            //add this obs to the encounter
            referralEncounter.addObs(referralTypeObs);
            referralEncounter.addObs(referralCommunityUnitCodeObs);
            referralEncounter.addObs(referralCommunityNameObs);
            referralEncounter.addObs(referralFacilityLocationObs);
            referralEncounter.addObs(referralReasonObs);
            referralEncounter.addObs(referralNotesObs);

            //referralEncounter.setObs(); //supply the observations with this line
            //save the encounter
            encounterService.saveEncounter(referralEncounter);


        }

    }

    private Concept referralType(String value) {
        if(value.equals("facility")){
            return Context.getConceptService().getConceptByUuid("1537AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        }
        else if(value.equals("community")){
            return Context.getConceptService().getConceptByUuid("4fcf003e-71cf-47a5-a967-47d24aa61092");
        }
        else {
            return  null;
        }
    }
}
