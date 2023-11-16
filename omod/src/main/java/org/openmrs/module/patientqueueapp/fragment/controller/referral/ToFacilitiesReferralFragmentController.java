package org.openmrs.module.patientqueueapp.fragment.controller.referral;

import org.openmrs.api.context.Context;
import org.openmrs.module.hospitalcore.HospitalCoreService;
import org.openmrs.module.hospitalcore.model.EhrReferralComponent;
import org.openmrs.module.hospitalcore.model.PatientReferralObject;
import org.openmrs.ui.framework.fragment.FragmentModel;

import java.util.ArrayList;
import java.util.List;

public class ToFacilitiesReferralFragmentController {

    public void controller(FragmentModel model) {
        HospitalCoreService hospitalCoreService = Context.getService(HospitalCoreService.class);
        List<PatientReferralObject> patientReferralObjectList = new ArrayList<PatientReferralObject>();
        PatientReferralObject patientReferralObject = null;
        for(EhrReferralComponent ehrReferralComponent : hospitalCoreService.getEhrReferralComponentList("facility")) {

        }

        model.addAttribute("toFacilityReferrals", patientReferralObjectList);
    }
}
