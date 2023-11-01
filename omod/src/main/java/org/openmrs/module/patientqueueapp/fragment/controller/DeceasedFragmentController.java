package org.openmrs.module.patientqueueapp.fragment.controller;

import org.openmrs.api.context.Context;
import org.openmrs.module.hospitalcore.HospitalCoreService;
import org.openmrs.module.hospitalcore.model.CertifiedDeceasedList;
import org.openmrs.module.hospitalcore.util.DateUtils;
import org.openmrs.module.hospitalcore.util.MorgueUtils;
import org.openmrs.module.kenyaemr.api.KenyaEmrService;
import org.openmrs.module.patientqueueapp.DeceasedSimplifier;
import org.openmrs.ui.framework.fragment.FragmentModel;

import java.util.ArrayList;
import java.util.List;

public class DeceasedFragmentController {
    public void controller(FragmentModel model) {
        List<DeceasedSimplifier> deceasedSimplifierList = new ArrayList<DeceasedSimplifier>();
        DeceasedSimplifier deceasedSimplifier = null;
       HospitalCoreService hospitalCoreService = Context.getService(HospitalCoreService.class);
       List<CertifiedDeceasedList> certifiedDeceasedLists = new ArrayList<CertifiedDeceasedList>(hospitalCoreService.getAllCertifiedDeceasedList());

       if(!certifiedDeceasedLists.isEmpty()) {
           for (CertifiedDeceasedList certifiedDeceasedList : certifiedDeceasedLists) {
               if (certifiedDeceasedList != null) {
                   deceasedSimplifier = new DeceasedSimplifier();
                   deceasedSimplifier.setPatientId(certifiedDeceasedList.getPatient().getPatientId());
                   deceasedSimplifier.setName(certifiedDeceasedList.getPatient().getGivenName() + " " + certifiedDeceasedList.getPatient().getFamilyName());
                   deceasedSimplifier.setEntryTime(DateUtils.getDateFromDateAsString(certifiedDeceasedList.getEntryDateAndTime(), "yyyy-MM-dd hh:mm"));
                   deceasedSimplifier.setdOfDeath((DateUtils.getDateFromDateAsString(certifiedDeceasedList.getDateOfDeath(), "yyyy-MM-dd hh:mm")));
                   deceasedSimplifier.setCauseOfDeath(certifiedDeceasedList.getCauseOfDeath().getDisplayString());
                   if(certifiedDeceasedList.getStatus() != null) {
                       deceasedSimplifier.setStatus(MorgueUtils.getStringValue(certifiedDeceasedList.getStatus()));
                   }
                   else {
                       deceasedSimplifier.setStatus(MorgueUtils.getStringValue(99));
                   }
                   deceasedSimplifier.setNotes(certifiedDeceasedList.getNotes());
                   deceasedSimplifierList.add(deceasedSimplifier);
               }
           }
       }
        model.addAttribute("allDeceasedAndConfirmedCases", deceasedSimplifierList);
       model.addAttribute("userLocation", Context.getService(KenyaEmrService.class).getDefaultLocation().getName());
       model.addAttribute("mfl", Context.getService(KenyaEmrService.class).getDefaultLocationMflCode());
    }
}
