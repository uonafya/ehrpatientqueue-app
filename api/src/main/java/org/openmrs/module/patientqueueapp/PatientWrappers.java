package org.openmrs.module.patientqueueapp;

import org.openmrs.Patient;
import org.openmrs.PatientIdentifier;
import org.openmrs.Person;
import org.openmrs.api.context.Context;

import java.io.Serializable;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PatientWrappers extends Patient implements Serializable {
    private Date lastVisitTime;
    private String wrapperIdentifier, formartedVisitDate;
    private static final Logger log = Logger.getLogger(PatientWrappers.class.getName());

    public PatientWrappers(Date lastVisitTime) {
        this.lastVisitTime = lastVisitTime;
    }

    public PatientWrappers(Patient person, Date lastVisitTime) {
        super(person);
        this.lastVisitTime = lastVisitTime;
        this.wrapperIdentifier = patientIdentifierValue(person);
    }

    public PatientWrappers(Integer patientId, Date lastVisitTime) {
        super(patientId);
        this.lastVisitTime = lastVisitTime;
    }

    public Date getLastVisitTime() {
        return lastVisitTime;
    }

    public String getFormartedVisitDate() {

        Format formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        try {
            formartedVisitDate = formatter.format(lastVisitTime);
        } catch (Exception e) {
            if (lastVisitTime != null) {
                formartedVisitDate = lastVisitTime.toString();
            } else {
                formartedVisitDate = "N/A";
            }
            log.log(Level.SEVERE, e.toString(), e);

        }

        return formartedVisitDate;
    }

    public void setLastVisitTime(Date lastVisitTime) {
        this.lastVisitTime = lastVisitTime;
    }

    public String getWrapperIdentifier() {
        return wrapperIdentifier;
    }

    public void setWrapperIdentifier(String wrapperIdentifier) {
        this.wrapperIdentifier = wrapperIdentifier;
    }

    public void setFormartedVisitDate(String formartedVisitDate) {
        this.formartedVisitDate = formartedVisitDate;
    }

    private String patientIdentifierValue(Patient patient) {
        String identifier = "";
        String clinicalNumberUuid = "b4d66522-11fc-45c7-83e3-39a1af21ae0d";
        List<PatientIdentifier> patientIdentifiersList = new ArrayList<PatientIdentifier>(patient.getIdentifiers());
        if (!patientIdentifiersList.isEmpty()) {
            identifier = patientIdentifiersList.get(0).getIdentifier();
            for (PatientIdentifier patientIdentifier : patientIdentifiersList) {
                if (patientIdentifier.getIdentifierType().equals(
                        Context.getPatientService().getPatientIdentifierTypeByUuid(clinicalNumberUuid))) {
                    identifier = patientIdentifier.getIdentifier();
                    break;
                }
            }
        }
        return identifier;
    }

}