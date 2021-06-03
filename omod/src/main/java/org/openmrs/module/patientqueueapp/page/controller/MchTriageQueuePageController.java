package org.openmrs.module.patientqueueapp.page.controller;

import org.openmrs.Concept;
import org.openmrs.api.context.Context;
import org.openmrs.module.appui.UiSessionContext;
import org.openmrs.module.kenyaui.annotation.AppPage;
import org.openmrs.module.patientqueueapp.PatientQueueConstants;
import org.openmrs.module.patientqueueapp.PatientQueueUtils;
import org.openmrs.ui.framework.UiUtils;
import org.openmrs.ui.framework.page.PageModel;
import org.openmrs.ui.framework.page.PageRequest;

import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 *
 */
@AppPage(PatientQueueConstants.APP_MCH_TRIAGE)
public class MchTriageQueuePageController {
    private static final String MCH_TRIAGE_CONCEPT_UUID = "3362c0d1-75c5-495c-939d-3163a1e77791";
    public String get(
            UiSessionContext sessionContext,
            PageModel model,
            HttpSession session,
            PageRequest pageRequest,
            UiUtils ui
    ) {
        Concept mchConcept = Context.getConceptService().getConceptByUuid(MCH_TRIAGE_CONCEPT_UUID);
        model.addAttribute("mchConceptId", mchConcept.getConceptId());
        model.addAttribute("date", new Date());
        model.addAttribute("mchQueueRoles", PatientQueueUtils.getMchappUserRoles(ui, "Triage"));
        return null;
    }
}
