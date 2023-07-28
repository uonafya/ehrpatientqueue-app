package org.openmrs.module.patientqueueapp.fragment.controller;

import org.apache.commons.lang3.StringUtils;
import org.openmrs.Encounter;
import org.openmrs.Patient;
import org.openmrs.Provider;
import org.openmrs.api.ProviderService;
import org.openmrs.api.context.Context;
import org.openmrs.module.ehrconfigs.metadata.EhrCommonMetadata;
import org.openmrs.module.hospitalcore.util.DateUtils;
import org.openmrs.module.hospitalcore.util.HospitalCoreUtils;
import org.openmrs.parameter.EncounterSearchCriteria;
import org.openmrs.ui.framework.SimpleObject;
import org.openmrs.ui.framework.UiUtils;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class ProviderWorkLoadFragmentController {
    public void controller(FragmentModel model) {
        ProviderService providerService = Context.getProviderService();
        Map<Integer, String> userDetails = new HashMap<Integer, String>();
        for (Provider provider : providerService.getAllProviders()) {
            Integer providerId = provider.getProviderId();
            String names = provider.getName();

            //populate the map
            userDetails.put(providerId, names);
        }
        model.addAttribute("providers", userDetails);
    }

    public SimpleObject fetchEncountersForProviderEntry(
            @RequestParam(value = "fromDate", required = false) String fromDate,
            @RequestParam(value = "toDate", required = false) String toDate,
            @RequestParam(value = "provider", required = false) Provider provider, UiUtils uiUtils) {
        Date startDate = null;
        Date endDate = null;
        int totalPatientsSeen=  0;

        if (StringUtils.isNotBlank(fromDate) && StringUtils.isNotBlank(toDate)) {
            startDate = DateUtils.getDateFromString(fromDate, "dd/MM/yyyy");
            endDate = DateUtils.getDateFromString(toDate, "dd/MM/yyyy");
        }

        List<Encounter> patientEncounters = Context.getEncounterService().getEncounters(new EncounterSearchCriteria(null, null, startDate,
                endDate, null, null, Arrays.asList(
                Context.getEncounterService().getEncounterTypeByUuid(EhrCommonMetadata._EhrEncounterTypes.OPDENCOUNTER)), Arrays.asList(HospitalCoreUtils.getProvider(Context.getAuthenticatedUser().getPerson())), null,
                null, false));
        Set<Patient> patientSet = new HashSet<Patient>();
        Set<Patient> malePatientSet = new HashSet<Patient>();
        Set<Patient> femalePatientSet = new HashSet<Patient>();

        int totalPatients = 0;
        int malePatients = 0;
        int femalePatients = 0;
        for(Encounter encounter : patientEncounters) {
              patientSet.add(encounter.getPatient());
        }
        if (!patientSet.isEmpty())  {
            totalPatients=patientSet.size();
            for(Patient patient : patientSet){
                if(patient.getGender().equals("M"))   {
                    malePatientSet.add(patient);
                }
                else {
                    femalePatientSet.add(patient);
                }

            }
        }
        if(!malePatientSet.isEmpty()){
            malePatients = malePatientSet.size();
        }
        if(!femalePatientSet.isEmpty()){
            femalePatients = femalePatientSet.size();
        }
        SimpleObject simpleObject = new SimpleObject();
        simpleObject.put("totalPatients",totalPatients);
        simpleObject.put("femalePatients",femalePatients);
        simpleObject.put("malePatients",malePatients);
        return simpleObject;
        
    }
}
