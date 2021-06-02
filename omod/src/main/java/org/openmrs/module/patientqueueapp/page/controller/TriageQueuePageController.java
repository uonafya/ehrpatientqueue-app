package org.openmrs.module.patientqueueapp.page.controller;

import java.util.*;

import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.openmrs.Concept;
import org.openmrs.ConceptAnswer;
import org.openmrs.User;
import org.openmrs.api.context.Context;
import org.openmrs.module.appframework.domain.AppDescriptor;
import org.openmrs.module.kenyaui.annotation.AppPage;
import org.openmrs.module.patientqueueapp.PatientQueueConstants;
import org.openmrs.module.referenceapplication.ReferenceApplicationWebConstants;
import org.openmrs.ui.framework.UiUtils;
import org.openmrs.ui.framework.page.PageModel;
import org.openmrs.module.hospitalcore.util.ConceptAnswerComparator;
import org.openmrs.module.appui.UiSessionContext;
import org.openmrs.ui.framework.page.PageRequest;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 */
@AppPage(PatientQueueConstants.APP_TRIAGE)
public class TriageQueuePageController {
    public String get(
            @RequestParam("app") AppDescriptor appDescriptor,
            UiSessionContext sessionContext,
            PageModel model,
            HttpSession session,
            PageRequest pageRequest,
            UiUtils ui
            ) {
        pageRequest.getSession().setAttribute(ReferenceApplicationWebConstants.SESSION_ATTRIBUTE_REDIRECT_URL,ui.thisUrl());
        sessionContext.requireAuthentication();
        model.addAttribute("afterSelectedUrl", "/patientdashboardapp/triage.page?patientId={{patientId}}&opdId={{opdId}}&queueId={{queueId}}");
        model.addAttribute("title", "Triage Queue");
        model.addAttribute("date", new Date());
        Concept triageConcept = Context.getConceptService().getConceptByUuid("e8acf3d5-d451-475b-a3b5-37f0ce6a0260");
        List<ConceptAnswer> list = (triageConcept != null
                ? new ArrayList<ConceptAnswer>(triageConcept.getAnswers()) : null);
        if (CollectionUtils.isNotEmpty(list)) {
            Collections.sort(list, new ConceptAnswerComparator());
        }
        model.addAttribute("listOPD", list);
        return null;
    }
}