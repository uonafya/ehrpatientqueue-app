package org.openmrs.module.patientqueueapp;

import org.openmrs.Patient;
import org.openmrs.Role;
import org.openmrs.api.context.Context;
import org.openmrs.module.mchapp.api.MchService;
import org.openmrs.ui.framework.SimpleObject;
import org.openmrs.ui.framework.UiUtils;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class PatientQueueUtils {
    public static final String MCH_TRIAGE_USER_ANC_QUEUE = "a46c6cd7-9ce3-4ade-99c8-0e395cab6c57";
    public static final String MCH_TRIAGE_USER_PNC_QUEUE = "20e3222e-9a05-44bc-a95d-7333d85ade82";
    public static final String MCH_TRIAGE_USER_FP_QUEUE = "8fa718ef-550a-4d71-8ff9-a61174e9e65f";
    public static final String MCH_TRIAGE_USER_CWC_QUEUE = "1c54b4e5-ac8a-4ac3-bff2-a87192c9557f";

    public static final String MCH_CLINIC_USER_ANC_QUEUE = "f8ff74bd-e776-4025-a7d5-aa6c40b498a1";
    public static final String MCH_CLINIC_USER_PNC_QUEUE = "2136bf9a-18b7-4179-858f-30c7cba191de";
    public static final String MCH_CLINIC_USER_FP_QUEUE = "e2d5977d-2b92-4b39-b2c9-63bf0d21e8f2";
    public static final String MCH_CLINIC_USER_CWC_QUEUE = "6285f88a-892c-41ca-9154-f127532f858c";
    public static final String MCH_IMMUNIZATION_CWC_QUEUE = "380af934-440b-40e7-a1ba-bc987adaa5fe";

    public static final String EXAM_ROOM_CONCEPT_UUID = "159937AAAAAAAAAAAAAAAAAAAAAAAAAAAAA";//  MCH program
    public static final String IMMUNIZATION_ROOM_CONCEPT_UUID = "5f819604-ca2c-4567-95f3-91b8fafb3305"; //MCH Immunization
    public static final String FP_ROOM_CONCEPT_UUID = "57287737-988c-4fd1-a3a2-0e9d9a7dd15d"; //created new concept

    public static List<SimpleObject> getMchappUserRoles(UiUtils ui, String clinic) {
        List<Role> roles = new ArrayList<Role>(Context.getAuthenticatedUser().getAllRoles());
        List<Role> mchRoles = new ArrayList<Role>();

        for (Role role : roles) {
            if (clinic.equals("Triage")){
                if (role.getUuid().equals(MCH_TRIAGE_USER_ANC_QUEUE) || role.getUuid().equals(MCH_TRIAGE_USER_PNC_QUEUE) || role.getUuid().equals(MCH_TRIAGE_USER_CWC_QUEUE) || role.getUuid().equals(MCH_TRIAGE_USER_FP_QUEUE)) {
                    mchRoles.add(role);
                }
            }
        }
        return SimpleObject.fromCollection(mchRoles, ui, "role", "description", "uuid");
    }

    public static String enrolledMCHProgram(Patient patient)
    {
        MchService mchService = Context.getService(MchService.class);
        if(mchService.enrolledInANC(patient)){
            return("ANC");
        }
        else if(mchService.enrolledInPNC(patient)){
            return("PNC");
        }
        else if(mchService.enrolledInCWC(patient)){
            return("CWC");
        }
        else{
            return("N/A");
        }
    }
}
