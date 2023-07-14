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

import org.apache.commons.lang3.StringUtils;
import org.openmrs.module.kenyaui.annotation.AppPage;
import org.openmrs.module.patientqueueapp.PatientQueueConstants;
import org.openmrs.ui.framework.page.PageModel;
import org.springframework.web.bind.annotation.RequestParam;

@AppPage(PatientQueueConstants.APP_DEATH_CERTIFICATION)
public class DeathReportingPageController {
    public void controller(PageModel model, @RequestParam(required = false, value = "section") String section) {
        String selection = null;

        if (StringUtils.isEmpty(section)) {
            section = "";
        }
        selection = "section-" + section;

        model.addAttribute("section", section);
        model.addAttribute("selection", selection);
    }
}
