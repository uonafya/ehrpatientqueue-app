package org.openmrs.module.patientqueueapp;

public class DeceasedSimplifier {

    private String name;
    private String dOfDeath;
    private String causeOfDeath;
    private String entryTime;
    private String status;

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    private String notes;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getdOfDeath() {
        return dOfDeath;
    }

    public void setdOfDeath(String dOfDeath) {
        this.dOfDeath = dOfDeath;
    }

    public String getCauseOfDeath() {
        return causeOfDeath;
    }

    public void setCauseOfDeath(String causeOfDeath) {
        this.causeOfDeath = causeOfDeath;
    }

    public String getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(String entryTime) {
        this.entryTime = entryTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCauseOfDeathNonCodded() {
        return causeOfDeathNonCodded;
    }

    public void setCauseOfDeathNonCodded(String causeOfDeathNonCodded) {
        this.causeOfDeathNonCodded = causeOfDeathNonCodded;
    }

    private String causeOfDeathNonCodded;

    public Integer getPatientId() {
        return patientId;
    }

    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
    }

    private Integer patientId;
}
