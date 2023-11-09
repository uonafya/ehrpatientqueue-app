package org.openmrs.module.patientqueueapp.page.controller.referral;

import org.openmrs.module.kenyaui.annotation.AppPage;
import org.openmrs.module.patientqueueapp.PatientQueueConstants;
import org.openmrs.ui.framework.UiUtils;
import org.openmrs.ui.framework.page.PageModel;
import org.openmrs.ui.framework.page.PageRequest;

import javax.servlet.http.HttpSession;

@AppPage(PatientQueueConstants.APP_PATIENT_REFERRAL)
public class PatientReferralPageController {

    public String get(
            PageModel model,
            HttpSession session,
            PageRequest pageRequest,
            UiUtils ui
    ) {
       return null;
    }
}
