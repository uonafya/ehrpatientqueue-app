package org.openmrs.module.patientqueueapp.fragment.controller;

import org.openmrs.api.context.Context;
import org.openmrs.module.hospitalcore.HospitalCoreService;
import org.openmrs.module.hospitalcore.model.CertifiedDeceasedList;
import org.openmrs.module.hospitalcore.util.DateUtils;
import org.openmrs.module.hospitalcore.util.MorgueUtils;
import org.openmrs.module.patientqueueapp.DeceasedSimplifier;
import org.openmrs.ui.framework.fragment.FragmentModel;

import java.util.ArrayList;
import java.util.List;

public class DeceasedFragmentController {
    public void controller(FragmentModel model) {
        List<DeceasedSimplifier> deceasedSimplifierList = new ArrayList<DeceasedSimplifier>();
        DeceasedSimplifier deceasedSimplifier = null;
       HospitalCoreService hospitalCoreService = Context.getService(HospitalCoreService.class);

        for(CertifiedDeceasedList certifiedDeceasedList : hospitalCoreService.getAllCertifiedDeceasedList()) {
            if(certifiedDeceasedList != null) {
                deceasedSimplifier = new DeceasedSimplifier();
                deceasedSimplifier.setName(certifiedDeceasedList.getPatient().getGivenName()+" "+certifiedDeceasedList.getPatient().getFamilyName());
                deceasedSimplifier.setEntryTime(DateUtils.getDateFromDateAsString(certifiedDeceasedList.getEntryDateAndTime(), "yyyy-MM-dd hh:mm"));
                deceasedSimplifier.setdOfDeath((DateUtils.getDateFromDateAsString(certifiedDeceasedList.getDateOfDeath(), "yyyy-MM-dd hh:mm")));
                deceasedSimplifier.setCauseOfDeath(certifiedDeceasedList.getCauseOfDeath().getDisplayString());
                deceasedSimplifier.setStatus(MorgueUtils.getStringValue(certifiedDeceasedList.getStatus()));
                deceasedSimplifier.setNotes(certifiedDeceasedList.getNotes());
                deceasedSimplifierList.add(deceasedSimplifier);
            }
        }
        model.addAttribute("allDeceasedAndConfirmedCases", deceasedSimplifierList);
    }
}
