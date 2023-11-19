package org.openmrs.module.patientqueueapp.fragment.controller.referral;

import org.openmrs.api.context.Context;
import org.openmrs.module.ehrconfigs.utils.EhrConfigsUtils;
import org.openmrs.module.hospitalcore.HospitalCoreService;
import org.openmrs.module.hospitalcore.model.EhrReferralComponent;
import org.openmrs.module.hospitalcore.model.PatientReferralObject;
import org.openmrs.module.hospitalcore.util.DateUtils;
import org.openmrs.ui.framework.fragment.FragmentModel;

import java.util.ArrayList;
import java.util.List;

public class FromFacilitiesToCommunityReferralsFragmentController {

    public void controller(FragmentModel model) {
        HospitalCoreService hospitalCoreService = Context.getService(HospitalCoreService.class);
        List<PatientReferralObject> patientReferralObjectList = new ArrayList<PatientReferralObject>();
        PatientReferralObject patientReferralObject = null;
        for(EhrReferralComponent ehrReferralComponent : hospitalCoreService.getEhrReferralComponentList("community")) {

            patientReferralObject = new PatientReferralObject();
            patientReferralObject.setPatientIdentifier(EhrConfigsUtils.getPreferredPatientIdentifier(ehrReferralComponent.getPatient()));
            patientReferralObject.setPatientNames(ehrReferralComponent.getPatient().getGivenName()+" "+ehrReferralComponent.getPatient().getFamilyName());
            patientReferralObject.setReferralReason(ehrReferralComponent.getReferralReason().getDisplayString());
            patientReferralObject.setDateCreated(DateUtils.getDateFromDateAsString(ehrReferralComponent.getCreatedOn(), "yyyy-mm-dd hh:mm"));
            patientReferralObject.setCreator(ehrReferralComponent.getCreatorBy().getGivenName()+" "+ehrReferralComponent.getCreatorBy().getFamilyName());
            patientReferralObject.setCommunityUnitCodeReferredTo(ehrReferralComponent.getReferralCommunityUnit());
            patientReferralObject.setCommunityUnitNameReferredTo(ehrReferralComponent.getReferralCommunityName());
            patientReferralObject.setReferralNotes(ehrReferralComponent.getReferralNotes());

            patientReferralObjectList.add(patientReferralObject);
        }

        model.addAttribute("toCommunityReferrals", patientReferralObjectList);
    }
}
