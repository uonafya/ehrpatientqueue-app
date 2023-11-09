package org.openmrs.module.patientqueueapp.page.controller.referral;

import org.openmrs.Patient;
import org.openmrs.module.kenyaui.annotation.AppPage;
import org.openmrs.module.patientqueueapp.PatientQueueConstants;
import org.openmrs.ui.framework.page.PageModel;
import org.springframework.web.bind.annotation.RequestParam;

@AppPage(PatientQueueConstants.APP_PATIENT_REFERRAL)
public class ReferPatientPageController {

    public void controller(PageModel model, @RequestParam(value = "patientId", required = false) Patient patient) {

    }
}
