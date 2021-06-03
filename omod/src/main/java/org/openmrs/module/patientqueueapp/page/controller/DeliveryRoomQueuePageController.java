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
    private static final String DELIVERY_ROOM_CONCEPT_UUID = "dff8dd54-e086-4ceb-bf47-09100354c9c2";
    public String get(
            UiSessionContext sessionContext,
            PageModel model,
            HttpSession session,
            PageRequest pageRequest,
            UiUtils ui
    ) {
        Concept maternityDeliveryRoomConcept = Context.getConceptService().getConceptByUuid(DELIVERY_ROOM_CONCEPT_UUID);
        Integer maternityDeliveryRoomConceptId = maternityDeliveryRoomConcept.getConceptId();
        model.addAttribute("maternityDeliveryRoomConceptId",maternityDeliveryRoomConceptId);
        model.addAttribute("date", new Date());
        return null;
    }
}
