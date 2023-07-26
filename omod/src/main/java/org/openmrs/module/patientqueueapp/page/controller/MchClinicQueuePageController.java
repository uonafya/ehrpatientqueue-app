package org.openmrs.module.patientqueueapp.page.controller;

import org.openmrs.Concept;
import org.openmrs.api.context.Context;
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
@AppPage(PatientQueueConstants.APP_MCH_CLINIC)
public class MchClinicQueuePageController {
    private static final String MCH_CLINIC_CONCEPT_UUID = "1acb3707-9e03-40e3-b157-ce28451c3fd0";//mch clinic
    private static final String MCH_IMMUNIZATION_CONCEPT_NAME = "f00b4314-cec5-4ce7-b0cd-c43e8deea664";//MCH Immunization
    public String get(
            PageModel model,
            HttpSession session,
            PageRequest pageRequest,
            UiUtils ui
    ) {

        Concept mchClinicConcept = Context.getConceptService().getConceptByUuid(MCH_CLINIC_CONCEPT_UUID);
        Concept mchImmunizationConcept = Context.getConceptService().getConceptByUuid(MCH_IMMUNIZATION_CONCEPT_NAME);
        Integer mchClinicConceptId = mchClinicConcept.getConceptId();
        Integer mchExaminationConceptId = mchImmunizationConcept.getConceptId();
        model.addAttribute("mchConceptId",mchClinicConceptId);
        model.addAttribute("mchImmunizationConceptId",mchExaminationConceptId);
        model.addAttribute("mchQueueRoles", PatientQueueUtils.getMchappUserRoles(ui, "Clinic"));
        model.addAttribute("date", new Date());
        model.addAttribute("fptabIncludedInPNC", Context.getAdministrationService().getGlobalProperty("fptab.includedInPNC"));
        return null;
    }
}
