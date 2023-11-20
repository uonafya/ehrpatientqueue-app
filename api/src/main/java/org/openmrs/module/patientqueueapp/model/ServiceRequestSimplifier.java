package org.openmrs.module.patientqueueapp.model;

public class ServiceRequestSimplifier {

    private Integer id;
    private String uuid;
    private String nupi;
    private String dateReferred;
    private String referredFrom;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getNupi() {
        return nupi;
    }

    public void setNupi(String nupi) {
        this.nupi = nupi;
    }

    public String getDateReferred() {
        return dateReferred;
    }

    public void setDateReferred(String dateReferred) {
        this.dateReferred = dateReferred;
    }

    public String getReferredFrom() {
        return referredFrom;
    }

    public void setReferredFrom(String referredFrom) {
        this.referredFrom = referredFrom;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private String givenName;
    private String middleName;
    private String familyName;
    private String birthdate;
    private String gender;

    private String status;

}
