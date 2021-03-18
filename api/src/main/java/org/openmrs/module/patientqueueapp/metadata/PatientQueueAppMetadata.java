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

    public static final class _Privilege {
        public static final String OPD_MODULE_APP = "App: patientqueueapp.opdqueue";
        public static final String TRIAGE_MODULE_APP = "App: patientqueueapp.triage";
    }

    public static final class _Role {

        public static final String OPD = "Doctor";

        public static final String TRIAGE = "Nurse";

    }

    /**
     * @see AbstractMetadataBundle#install()
     */
    @Override
    public void install() {
        install(privilege(_Privilege.OPD_MODULE_APP, "Able to access EHR OPD module features"));
        install(privilege(_Privilege.TRIAGE_MODULE_APP, "Able to access EHR triage module features"));

        install(role(_Role.OPD, "Can access Key EHR OPD module App",
                idSet(org.openmrs.module.kenyaemr.metadata.SecurityMetadata._Role.API_PRIVILEGES_VIEW_AND_EDIT),
                idSet(_Privilege.OPD_MODULE_APP, _Privilege.TRIAGE_MODULE_APP)));

        install(role(_Role.TRIAGE, "Can access EHR Triage module App",
                idSet(org.openmrs.module.kenyaemr.metadata.SecurityMetadata._Role.API_PRIVILEGES_VIEW_AND_EDIT),
                idSet(_Privilege.TRIAGE_MODULE_APP)));
    }
}
