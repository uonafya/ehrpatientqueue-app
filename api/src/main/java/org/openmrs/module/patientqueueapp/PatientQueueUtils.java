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

    public static final String MCH_CLINIC_USER_ANC_QUEUE = "d13cb3e6-a5ec-4c76-b405-a469aa7c6865";
    public static final String MCH_CLINIC_USER_PNC_QUEUE = "ee748fb3-7244-4b80-9ac3-24175a358a2d";
    public static final String MCH_CLINIC_USER_FP_QUEUE = "da78bc6d-01b8-48b5-bb07-0b6a302fe7f7";
    public static final String MCH_CLINIC_USER_CWC_QUEUE = "43635304-bdfc-4ee9-8053-d79c0d1f1084";
    public static final String MCH_IMMUNIZATION_CWC_QUEUE = "9fbabc78-abc5-4cab-bd02-c16377568bd0";

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
            else if (clinic.equals("Clinic")){
                if (role.getUuid().equals(MCH_CLINIC_USER_ANC_QUEUE) || role.getUuid().equals(MCH_CLINIC_USER_PNC_QUEUE) || role.getUuid().equals(MCH_CLINIC_USER_CWC_QUEUE) || role.getUuid().equals(MCH_CLINIC_USER_FP_QUEUE) || role.getUuid().equals(MCH_IMMUNIZATION_CWC_QUEUE)) {
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
