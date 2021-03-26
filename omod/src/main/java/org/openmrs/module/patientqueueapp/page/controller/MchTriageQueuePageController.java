package org.openmrs.module.patientqueueapp.page.controller;

import org.apache.commons.collections.CollectionUtils;
import org.openmrs.Concept;
import org.openmrs.ConceptAnswer;
import org.openmrs.User;
import org.openmrs.api.context.Context;
import org.openmrs.module.appframework.domain.AppDescriptor;
import org.openmrs.module.appui.UiSessionContext;
import org.openmrs.module.hospitalcore.util.ConceptAnswerComparator;
import org.openmrs.module.kenyaui.annotation.AppPage;
import org.openmrs.module.patientqueueapp.PatientQueueConstants;
import org.openmrs.module.patientqueueapp.PatientQueueUtils;
import org.openmrs.module.referenceapplication.ReferenceApplicationWebConstants;
import org.openmrs.ui.framework.UiUtils;
import org.openmrs.ui.framework.page.PageModel;
import org.openmrs.ui.framework.page.PageRequest;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 *
 */
@AppPage(PatientQueueConstants.APP_MCH_TRIAGE)
public class MchTriageQueuePageController {
    private static final String MCH_TRIAGE_CONCEPT_UUID = "038ccfbb-ed16-4e25-b5a5-3c975ff86010";
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
