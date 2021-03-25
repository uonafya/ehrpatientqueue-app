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
@AppPage(PatientQueueConstants.APP_DELIVERY_ROOM)
public class DeliveryRoomQueuePageController {
    private static final String DELIVERY_ROOM_CONCEPT_UUID = "a6aacbbe-c897-456e-9818-48e45761ab02";
    public String get(
            UiSessionContext sessionContext,
            PageModel model,
            HttpSession session,
            PageRequest pageRequest,
            UiUtils ui
    ) {
        /*pageRequest.getSession().setAttribute(ReferenceApplicationWebConstants.SESSION_ATTRIBUTE_REDIRECT_URL,ui.thisUrl());
        sessionContext.requireAuthentication();*/
        Concept maternityDeliveryRoomConcept = Context.getConceptService().getConceptByUuid(DELIVERY_ROOM_CONCEPT_UUID);
        Integer maternityDeliveryRoomConceptId = maternityDeliveryRoomConcept.getConceptId();
        model.addAttribute("maternityDeliveryRoomConceptId",maternityDeliveryRoomConceptId);
        model.addAttribute("date", new Date());
        return null;
    }
}
