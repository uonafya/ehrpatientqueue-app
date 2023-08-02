/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.patientqueueapp.page.controller.deaths;

import org.openmrs.Encounter;
import org.openmrs.Patient;
import org.openmrs.api.context.Context;
import org.openmrs.module.kenyaui.annotation.AppPage;
import org.openmrs.module.patientqueueapp.PatientQueueUtils;
import org.openmrs.parameter.EncounterSearchCriteria;
import org.openmrs.ui.framework.page.PageModel;
import org.openmrs.module.patientqueueapp.PatientQueueConstants;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;

/**
 * Hope page for the patient death certification app
 */
@AppPage(PatientQueueConstants.APP_DEATH_CERTIFICATION)
public class DeathCertificationPageController {

    public void controller(PageModel model, @RequestParam(value = "patientId", required = false) Patient patient) {
        boolean isAlreadyCertified = false;
        List<Encounter> patientEncounters = Context.getEncounterService().getEncounters( new EncounterSearchCriteria(
        patient, null, null, null, null, null, Arrays.asList(PatientQueueUtils.deathEncounterType), null,
                        null, null, false)
        );
        if(!patientEncounters.isEmpty()) {
            isAlreadyCertified = true;
        }
        model.addAttribute("status", isAlreadyCertified);
        model.addAttribute("patientId", patient.getPatientId());
    }
}
