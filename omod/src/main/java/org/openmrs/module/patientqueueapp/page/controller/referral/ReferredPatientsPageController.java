package org.openmrs.module.patientqueueapp.page.controller.referral;

import com.fasterxml.jackson.core.JsonProcessingException;
import ca.uhn.fhir.parser.IParser;
import org.hl7.fhir.r4.model.ServiceRequest;
import org.openmrs.Location;
import org.openmrs.api.context.Context;
import org.openmrs.module.kenyaemrIL.programEnrollment.ExpectedTransferInPatients;
import org.openmrs.module.kenyaemrIL.util.ILUtils;
import org.openmrs.module.kenyaui.KenyaUiUtils;
import org.openmrs.module.kenyaui.annotation.AppPage;
import org.openmrs.module.patientqueueapp.PatientQueueConstants;
import org.openmrs.ui.framework.SimpleObject;
import org.openmrs.ui.framework.UiUtils;
import org.openmrs.ui.framework.annotation.SpringBean;
import org.openmrs.ui.framework.page.PageModel;
import org.openmrs.module.kenyaemrIL.api.KenyaEMRILService;
import org.openmrs.module.kenyaemrIL.api.shr.FhirConfig;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@AppPage(PatientQueueConstants.APP_PATIENT_REFERRAL)
public class ReferredPatientsPageController {
    public void controller(@SpringBean KenyaUiUtils kenyaUi, UiUtils ui, PageModel model) throws JsonProcessingException, ParseException {
        KenyaEMRILService ilService = Context.getService(KenyaEMRILService.class);
        FhirConfig fhirConfig = Context.getRegisteredComponents(FhirConfig.class).get(0);

// Filter all community referrals with active referral status


        // Filter all community referrals with completed referral status



        //model.put("activeReferralListSize", activeReferrals.size());
        //model.put("completedReferralList", ui.toJson(completedReferrals));
        //model.put("completedReferralListSize", completedReferrals.size());
    }
}
