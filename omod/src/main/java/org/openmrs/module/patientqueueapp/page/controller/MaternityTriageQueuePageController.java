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
    private static final String MATERNITY_TRIAGE_CONCEPT_UUID = "22b005cf-f680-4823-a298-3e62f96efc96";
    public String get(
            UiSessionContext sessionContext,
            PageModel model,
            HttpSession session,
            PageRequest pageRequest,
            UiUtils ui
    ) {
        /*pageRequest.getSession().setAttribute(ReferenceApplicationWebConstants.SESSION_ATTRIBUTE_REDIRECT_URL,ui.thisUrl());
        sessionContext.requireAuthentication();
        Boolean isPriviledged = Context.hasPrivilege("Access Maternity Triage");
        if(!isPriviledged){
            return "redirect: index.htm";
        }*/
        Concept maternityConcept = Context.getConceptService().getConceptByUuid(MATERNITY_TRIAGE_CONCEPT_UUID);
        Integer maternityConceptId = maternityConcept.getConceptId();
        model.addAttribute("maternityConceptId",maternityConceptId);
        model.addAttribute("date", new Date());
        return null;
    }
}
