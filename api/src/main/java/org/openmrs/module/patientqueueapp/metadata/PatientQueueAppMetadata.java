/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.patientqueueapp.metadata;

import org.openmrs.module.metadatadeploy.bundle.AbstractMetadataBundle;
import org.openmrs.module.metadatadeploy.bundle.Requires;
import org.openmrs.module.patientqueueapp.PatientQueueConstants;
import org.springframework.stereotype.Component;

import static org.openmrs.module.metadatadeploy.bundle.CoreConstructors.idSet;
import static org.openmrs.module.metadatadeploy.bundle.CoreConstructors.privilege;
import static org.openmrs.module.metadatadeploy.bundle.CoreConstructors.role;

/**
 * Implementation of access control to the app.
 */
@Component
@Requires(org.openmrs.module.kenyaemr.metadata.SecurityMetadata.class)
public class PatientQueueAppMetadata extends AbstractMetadataBundle {

    /**
     * Application IDs
     */
    public static final String APP_OPD = PatientQueueConstants.MODULE_ID + ".opd.opdqueue";
    public static final String APP_TRIAGE = PatientQueueConstants.MODULE_ID + ".app.triage";

    /**
     * @see AbstractMetadataBundle#install()
     */
    @Override
    public void install() {
        String[] appIds = {
                APP_OPD, APP_TRIAGE
        };
        // Ensure a privilege exists for each app. App framework does create these but not always before this
        // bundle is installed
        for (String appId : appIds) {
            install(privilege(app(appId), "Access to the " + appId + " app"));
        }

    }

    /**
     * Creates an app privilege from an app ID
     * @param appId the app ID
     * @return the privilege
     */
    protected String app(String appId) {
        return "App: " + appId;
    }
}
