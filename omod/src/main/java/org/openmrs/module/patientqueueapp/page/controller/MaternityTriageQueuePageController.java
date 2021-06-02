package org.openmrs.module.patientqueueapp.page.controller;

import org.openmrs.Concept;
import org.openmrs.api.context.Context;
import org.openmrs.module.appui.UiSessionContext;
import org.openmrs.module.kenyaui.annotation.AppPage;
import org.openmrs.module.patientqueueapp.PatientQueueConstants;
import org.openmrs.module.patientqueueapp.PatientQueueUtils;
import org.openmrs.module.referenceapplication.ReferenceApplicationWebConstants;
import org.openmrs.ui.framework.UiUtils;
import org.openmrs.ui.framework.page.PageModel;
import org.openmrs.ui.framework.page.PageRequest;

import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 *
 */
@AppPage(PatientQueueConstants.APP_MATERNITY_TRIAGE)
public class MaternityTriageQueuePageController {
    private static final String MATERNITY_TRIAGE_CONCEPT_UUID = "16418d28-de8f-42b6-b3be-9047e896e735";
    public String get(
            UiSessionContext sessionContext,
            PageModel model,
            HttpSession session,
            PageRequest pageRequest,
            UiUtils ui
    ) {
        Concept maternityConcept = Context.getConceptService().getConceptByUuid(MATERNITY_TRIAGE_CONCEPT_UUID);
        Integer maternityConceptId = maternityConcept.getConceptId();
        model.addAttribute("maternityConceptId",maternityConceptId);
        model.addAttribute("date", new Date());
        return null;
    }
}
