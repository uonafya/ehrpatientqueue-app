package org.openmrs.module.patientqueueapp.page.controller;

import org.openmrs.Concept;
import org.openmrs.api.context.Context;
import org.openmrs.module.appui.UiSessionContext;
import org.openmrs.module.kenyaui.annotation.AppPage;
import org.openmrs.module.patientqueueapp.PatientQueueConstants;
import org.openmrs.module.referenceapplication.ReferenceApplicationWebConstants;
import org.openmrs.ui.framework.UiUtils;
import org.openmrs.ui.framework.page.PageModel;
import org.openmrs.ui.framework.page.PageRequest;

import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 *
 */
@AppPage(PatientQueueConstants.APP_PAC)
public class PacRoomQueuePageController {
    private static final String PAC_ROOM_CONCEPT_UUID = "072ffde2-6515-41c9-9969-1b303241ba56";
    public String get(
            UiSessionContext sessionContext,
            PageModel model,
            HttpSession session,
            PageRequest pageRequest,
            UiUtils ui
    ) {
        /*pageRequest.getSession().setAttribute(ReferenceApplicationWebConstants.SESSION_ATTRIBUTE_REDIRECT_URL,ui.thisUrl());
        sessionContext.requireAuthentication();*/
        Concept maternityPacRoomConcept = Context.getConceptService().getConceptByUuid(PAC_ROOM_CONCEPT_UUID);
        Integer maternityPacRoomConceptId = maternityPacRoomConcept.getConceptId();
        model.addAttribute("maternityPacRoomConceptId",maternityPacRoomConceptId);
        model.addAttribute("date", new Date());
        return null;
    }
}
