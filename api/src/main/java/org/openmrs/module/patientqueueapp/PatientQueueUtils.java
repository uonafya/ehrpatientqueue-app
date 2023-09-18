package org.openmrs.module.patientqueueapp;

import org.openmrs.Concept;
import org.openmrs.EncounterType;
import org.openmrs.Role;
import org.openmrs.api.context.Context;
import org.openmrs.ui.framework.SimpleObject;
import org.openmrs.ui.framework.UiUtils;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class PatientQueueUtils {
    public static final String MCH_TRIAGE_USER_ANC_QUEUE = "MCH traige user for ANC";
    public static final String MCH_TRIAGE_USER_PNC_QUEUE = "MCH traige user for PNC";
    public static final String MCH_TRIAGE_USER_FP_QUEUE = "MCH traige user for FP";
    public static final String MCH_TRIAGE_USER_CWC_QUEUE = "MCH traige user for CWC";

    public static final String MCH_CLINIC_USER_ANC_QUEUE = "MCH Clinic user for ANC";
    public static final String MCH_CLINIC_USER_PNC_QUEUE = "MCH Clinic user for PNC";
    public static final String MCH_CLINIC_USER_FP_QUEUE = "MCH Clinic user for FP";
    public static final String MCH_CLINIC_USER_CWC_QUEUE = "MCH Clinic user for CWC";
    public static final String MCH_IMMUNIZATION_CWC_QUEUE = "CWC Immunization Queue";

    public static final String EXAM_ROOM_CONCEPT_UUID = "1acb3707-9e03-40e3-b157-ce28451c3fd0";//  MCH clinic
    public static final String IMMUNIZATION_ROOM_CONCEPT_UUID = "f00b4314-cec5-4ce7-b0cd-c43e8deea664"; //MCH Immunization
    public static final String FP_ROOM_CONCEPT_UUID = "f33fc3a9-eae4-4410-95f7-a649192c63e9"; //created new concept

    public static List<SimpleObject> getMchappUserRoles(UiUtils ui, String clinic) {
        List<Role> roles = new ArrayList<Role>(Context.getAuthenticatedUser().getAllRoles());
        List<Role> mchRoles = new ArrayList<Role>();

        for (Role role : roles) {
            if (clinic.equals("Triage")){
                if (role.getRole().equals(MCH_TRIAGE_USER_ANC_QUEUE) || role.getRole().equals(MCH_TRIAGE_USER_PNC_QUEUE) || role.getRole().equals(MCH_TRIAGE_USER_CWC_QUEUE) || role.getRole().equals(MCH_TRIAGE_USER_FP_QUEUE)) {
                    mchRoles.add(role);
                }
            }
            else if (clinic.equals("Clinic")){
                if (role.getRole().equals(MCH_CLINIC_USER_ANC_QUEUE) || role.getRole().equals(MCH_CLINIC_USER_PNC_QUEUE) || role.getRole().equals(MCH_CLINIC_USER_CWC_QUEUE) || role.getRole().equals(MCH_CLINIC_USER_FP_QUEUE) || role.getRole().equals(MCH_IMMUNIZATION_CWC_QUEUE)) {
                    mchRoles.add(role);
                }
            }
        }
        return SimpleObject.fromCollection(mchRoles, ui, "role", "description", "uuid");
    }


    public static final Concept dateOfDeathQuestion = Context.getConceptService().getConceptByUuid("1543AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
    public static final Concept causeOfDeathQuestion = Context.getConceptService().getConceptByUuid("1599AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
    public static final Concept causeOfDeathNonCodedQuestion = Context.getConceptService().getConceptByUuid("160218AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
    public static final EncounterType deathEncounterType = Context.getEncounterService().getEncounterTypeByUuid("bf484793-1734-4f57-a6f1-b866545ca8df");

}
