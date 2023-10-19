package org.openmrs.module.patientqueueapp.fragment.controller;

import org.apache.commons.lang3.StringUtils;
import org.openmrs.Concept;
import org.openmrs.ConceptAnswer;
import org.openmrs.Encounter;
import org.openmrs.EncounterType;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.Provider;
import org.openmrs.User;
import org.openmrs.api.EncounterService;
import org.openmrs.api.ObsService;
import org.openmrs.api.context.Context;
import org.openmrs.module.hospitalcore.HospitalCoreService;
import org.openmrs.module.hospitalcore.PatientQueueService;
import org.openmrs.module.hospitalcore.model.EhrMorgueQueue;
import org.openmrs.module.hospitalcore.model.OpdPatientQueue;
import org.openmrs.module.hospitalcore.model.OpdPatientQueueLog;
import org.openmrs.module.hospitalcore.model.TriagePatientQueue;
import org.openmrs.module.hospitalcore.util.DateUtils;
import org.openmrs.module.hospitalcore.util.HospitalCoreUtils;
import org.openmrs.module.kenyaemr.api.KenyaEmrService;
import org.openmrs.module.patientqueueapp.PatientQueueUtils;
import org.openmrs.ui.framework.SimpleObject;
import org.openmrs.ui.framework.UiUtils;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class PatientQueueFragmentController {
	public void controller(@RequestParam(value = "patientId", required = false) Patient patient, FragmentModel model) {
		model.addAttribute("patientId", patient.getPatientId());
	}

	public SimpleObject getPatientsInMaternityTriageQueue(@RequestParam("maternityConceptId") Integer maternityConceptId,UiUtils ui){
		List<TriagePatientQueue> patientQueues = Context.getService(PatientQueueService.class).listTriagePatientQueue("", maternityConceptId, "", 0, 0);
		List<SimpleObject> patientQueueObject = SimpleObject.fromCollection(patientQueues, ui, "patientName", "patientIdentifier", "age", "sex", "status", "visitStatus","patient.id", "id");
		return SimpleObject.create("data", patientQueueObject);
	}

	public SimpleObject getPatientsInMaternityClinicQueue(@RequestParam("maternityRoomConceptId") Integer maternityRoomConceptId,UiUtils ui){
		List<OpdPatientQueue> patientQueues = Context.getService(PatientQueueService.class).listOpdPatientQueue("", maternityRoomConceptId, "", 0, 0, getProviderIdentifier());
		List<SimpleObject> patientQueueObject = SimpleObject.fromCollection(patientQueues, ui, "patientName", "patientIdentifier", "age", "sex", "status", "visitStatus","patient.id", "id", "referralConcept.conceptId");
		return SimpleObject.create("data", patientQueueObject);
	}

	public SimpleObject getPatientsInMchTriageQueue(@RequestParam("mchConceptId") Integer mchConceptId,UiUtils ui){
		List<TriagePatientQueue> patientQueues = Context.getService(PatientQueueService.class).listTriagePatientQueue("", mchConceptId, "", 0, 0);
		List<SimpleObject> patientQueueObject = new ArrayList<SimpleObject>();

		for (TriagePatientQueue patientQueue : patientQueues) {
			SimpleObject patientInQueue = new SimpleObject();
			patientInQueue.put("patientName", patientQueue.getPatientName());
			patientInQueue.put("patientIdentifier", patientQueue.getPatientIdentifier());
			patientInQueue.put("age",patientQueue.getAge());
			patientInQueue.put("sex",patientQueue.getSex());
			patientInQueue.put("status", patientQueue.getStatus());
			patientInQueue.put("visitStatus", patientQueue.getVisitStatus());
			patientInQueue.put("patientId",patientQueue.getPatient().getId());
			patientInQueue.put("id", patientQueue.getId());
			//patientInQueue.put("clinic",PatientQueueUtils.enrolledMCHProgram(patientQueue.getPatient()));
			if(patientQueue.getReferralConcept()!=null) {
				patientInQueue.put("referralConcept.conceptId", patientQueue.getReferralConcept().getConceptId());
			}
			patientQueueObject.add(patientInQueue);
		}
		return SimpleObject.create("data", patientQueueObject);
	}

	public SimpleObject getPatientsInMchClinicQueue(@RequestParam("mchConceptId") Integer mchConceptId,
													@RequestParam("mchExaminationConceptId") Integer mchExaminationConceptId,UiUtils ui){
		Concept mchExamRoomConcept = Context.getConceptService().getConceptByUuid(PatientQueueUtils.EXAM_ROOM_CONCEPT_UUID);

		Concept mchImmunizationRoomConcept = Context.getConceptService().getConceptByUuid(PatientQueueUtils.IMMUNIZATION_ROOM_CONCEPT_UUID);
		Concept fpRoomConcept = Context.getConceptService().getConceptByUuid(PatientQueueUtils.FP_ROOM_CONCEPT_UUID);

		List<OpdPatientQueue> patientQueues = new ArrayList<OpdPatientQueue>();
		patientQueues.addAll(Context.getService(PatientQueueService.class).listOpdPatientQueue("", mchConceptId, "", 0, 0, getProviderIdentifier()));
		patientQueues.addAll(Context.getService(PatientQueueService.class).listOpdPatientQueue("", fpRoomConcept.getConceptId(), "", 0, 0, getProviderIdentifier()));
		patientQueues.addAll(Context.getService(PatientQueueService.class).listOpdPatientQueue("", mchImmunizationRoomConcept.getConceptId(), "", 0, 0, getProviderIdentifier()));

		Collections.sort(patientQueues, new Comparator<OpdPatientQueue>() {
			public int compare(OpdPatientQueue q1, OpdPatientQueue q2) {
				if(q1.getCreatedOn() != null && q2.getCreatedOn() != null && q1.getCreatedOn().compareTo(q1.getCreatedOn()) != 0) {
					return q1.getCreatedOn().compareTo(q2.getCreatedOn());
				}
				else {
					return q1.getCreatedOn().compareTo(q2.getCreatedOn());
				}
			}
		});

		List<SimpleObject> patientQueueObject = new ArrayList<SimpleObject>();
		for(OpdPatientQueue patientQueue : patientQueues) {
			SimpleObject patientInQueue = new SimpleObject();
			patientInQueue.put("patientName", patientQueue.getPatientName());
			patientInQueue.put("patientIdentifier", patientQueue.getPatientIdentifier());
			patientInQueue.put("age",patientQueue.getAge());
			patientInQueue.put("sex",patientQueue.getSex());
			patientInQueue.put("status", patientQueue.getStatus());
			patientInQueue.put("visitStatus", patientQueue.getVisitStatus());
			patientInQueue.put("patientId",patientQueue.getPatient().getId());
			patientInQueue.put("id", patientQueue.getId());

			Concept oPdConcept = patientQueue.getOpdConcept();
			if (oPdConcept.equals(fpRoomConcept)){
				patientInQueue.put("clinic","FP");
			}
			else if(oPdConcept.equals(mchExamRoomConcept))
			{
				//patientInQueue.put("clinic",PatientQueueUtils.enrolledMCHProgram(patientQueue.getPatient()));
			}

			if(patientQueue.getReferralConcept()!=null) {
				patientInQueue.put("referralConcept.conceptId", patientQueue.getReferralConcept().getConceptId());
			}
			patientQueueObject.add(patientInQueue);
		}
		return SimpleObject.create("data", patientQueueObject);
	}

	public SimpleObject getPatientsInQueue(@RequestParam("opdId") Integer opdId, @RequestParam(value = "query", required = false) String query, UiUtils ui) {
		Concept queueConcept = Context.getConceptService().getConcept(opdId);

		//get the question here from the given answer
		Concept question = getTheQuestionToTheGivenAnswer(queueConcept);


		SimpleObject patientQueueData = null;
		if (question != null && question.equals(Context.getConceptService().getConceptByUuid("e8acf3d5-d451-475b-a3b5-37f0ce6a0260"))) {
			List<TriagePatientQueue> patientQueues = Context.getService(PatientQueueService.class).listTriagePatientQueue(query.trim(), opdId, "", 0, 0);
			List<SimpleObject> patientQueueObject = SimpleObject.fromCollection(patientQueues, ui, "patientName", "patientIdentifier", "age", "sex", "status", "visitStatus","patient.id", "id");
			patientQueueData = SimpleObject.create("data", patientQueueObject, "user", "triageUser");
		} else if (question != null && question.equals(Context.getConceptService().getConceptByUuid("03880388-07ce-4961-abe7-0e58f787dd23"))) {
			List<OpdPatientQueue> patientQueues = Context.getService(PatientQueueService.class).listOpdPatientQueue(query.trim(), opdId, "", 0, 0, getProviderIdentifier());
			List<SimpleObject> patientQueueObject = SimpleObject.fromCollection(patientQueues, ui, "patientName", "patientIdentifier", "age", "sex", "status", "visitStatus","patient.id", "id", "referralConcept.conceptId");
			patientQueueData = SimpleObject.create("data", patientQueueObject, "user", "opdUser");
		} else if(question != null && question.equals(Context.getConceptService().getConceptByUuid("b5e0cfd3-1009-4527-8e36-83b5e902b3ea"))) {
			List<OpdPatientQueue> patientQueues = Context.getService(PatientQueueService.class).listOpdPatientQueue(query.trim(), opdId, "", 0, 0, getProviderIdentifier());
			List<SimpleObject> patientQueueObject = SimpleObject.fromCollection(patientQueues, ui, "patientName", "patientIdentifier", "age", "sex", "status", "visitStatus","patient.id", "id", "referralConcept.conceptId");
			patientQueueData = SimpleObject.create("data", patientQueueObject, "user", "opdUser");
		}
		return patientQueueData;
	}

	public SimpleObject addPatientToQueue(
			@RequestParam("patientId") Integer patientId,
			@RequestParam("opdId") Integer opdId) {
		Patient patient = Context.getPatientService().getPatient(patientId);
		PatientQueueService queueService = Context.getService(PatientQueueService.class);

		List<OpdPatientQueue> matchingPatientsInQueue = queueService.listOpdPatientQueue(patient.getPatientIdentifier().getIdentifier(), opdId, "", 0, 0, getProviderIdentifier());
		OpdPatientQueue patientInQueue = null;
		List<Encounter> existingEncounters = Context.getEncounterService().getEncounters(patient, null, null, null, null, null, null, null, null, false);
		String visitStatus = null;
		if (existingEncounters.size() > 1) {
			visitStatus = "Revisit Patient";
		} else if (existingEncounters.size() == 1) {
			Calendar today = Calendar.getInstance();
			Calendar encounterDate = Calendar.getInstance();
			encounterDate.setTime(existingEncounters.get(0).getEncounterDatetime());
			if (today.get(Calendar.YEAR) == encounterDate.get(Calendar.YEAR) &&
					today.get(Calendar.DAY_OF_YEAR) == encounterDate.get(Calendar.DAY_OF_YEAR)) {
				visitStatus = "New client";
			} else {
				visitStatus = "Revisit Patient";
			}
		} else {
			visitStatus = "New client";
		}
		if (matchingPatientsInQueue.size() == 0) {
			Concept selectedOpdConcept = Context.getConceptService().getConcept(opdId);
			patientInQueue = new OpdPatientQueue();
			patientInQueue.setVisitStatus(visitStatus);
			patientInQueue.setUser(Context.getAuthenticatedUser());
			patientInQueue.setPatient(patient);
			patientInQueue.setCreatedOn(new Date());
			patientInQueue.setBirthDate(patient.getBirthdate());
			patientInQueue.setSex(patient.getGender());
			patientInQueue.setPatientIdentifier(patient.getPatientIdentifier().getIdentifier());
			patientInQueue.setOpdConcept(selectedOpdConcept);
			patientInQueue.setOpdConceptName(selectedOpdConcept.getName().getName());
			if(patient.getMiddleName() != null) {
				patientInQueue.setPatientName(patient.getGivenName() + " " + patient.getFamilyName() + " " + patient.getMiddleName());
			} else {
				patientInQueue.setPatientName(patient.getGivenName() + " " + patient.getFamilyName());
			}
			updatePatientQueueDataFromPreviousVisit(patientInQueue, patient, queueService);
			patientInQueue.setClearedToNextServicePoint(1);
			patientInQueue = queueService.saveOpdPatientQueue(patientInQueue);
		} else {
			patientInQueue = matchingPatientsInQueue.get(0);
		}
		return SimpleObject.create("status", "success", "queueId", patientInQueue.getId());
	}

	private void updatePatientQueueDataFromPreviousVisit(
			OpdPatientQueue patientInQueue, Patient patient,
			PatientQueueService queueService) {
		Encounter queueEncounter = queueService.getLastOPDEncounter(patient);
		if (queueEncounter != null) {
			OpdPatientQueueLog patientQueueLog = queueService.getOpdPatientQueueLogByEncounter(queueEncounter);
			if (patientQueueLog != null) {
				String selectedCategory = patientQueueLog.getCategory();
				String visitStatus = patientQueueLog.getVisitStatus();
				patientInQueue.setTriageDataId(patientQueueLog.getTriageDataId());
				patientInQueue.setCategory(selectedCategory);
				patientInQueue.setVisitStatus(visitStatus);
			}
		}
	}

	private Concept getTheQuestionToTheGivenAnswer(Concept concept) {
		Concept foundQuestion = null;
		Collection<ConceptAnswer> triageAnswers = Context.getConceptService().getConceptByUuid("e8acf3d5-d451-475b-a3b5-37f0ce6a0260").getAnswers(false);
		Collection<ConceptAnswer> opdAnswers = Context.getConceptService().getConceptByUuid("03880388-07ce-4961-abe7-0e58f787dd23").getAnswers(false);
		Collection<ConceptAnswer> specialClinicAnswers = Context.getConceptService().getConceptByUuid("b5e0cfd3-1009-4527-8e36-83b5e902b3ea").getAnswers(false);

		for (ConceptAnswer conceptAnswer1 : triageAnswers) {
			if(conceptAnswer1.getAnswerConcept().equals(concept)) {
				foundQuestion = conceptAnswer1.getConcept();
				break;
			}
		}

		for (ConceptAnswer conceptAnswer2 : opdAnswers) {
			if(conceptAnswer2.getAnswerConcept().equals(concept)) {
				foundQuestion = conceptAnswer2.getConcept();
				break;
			}
		}

		for (ConceptAnswer conceptAnswer3 : specialClinicAnswers) {
			if(conceptAnswer3.getAnswerConcept().equals(concept)) {
				foundQuestion = conceptAnswer3.getConcept();
				break;
			}
		}

		return foundQuestion;
	}

	private String getProviderIdentifier() {
		User user = Context.getAuthenticatedUser();
		String providerIdentifier = "";
		Provider provider = HospitalCoreUtils.getProvider(user.getPerson());
		if(provider != null && !StringUtils.isBlank(provider.getIdentifier())) {
			providerIdentifier = provider.getIdentifier();
		}
		return providerIdentifier;
	}

	public String certifyDeath(
			@RequestParam(value = "deathDate", required = false) String deathDate,
			@RequestParam(value = "diagnosis", required = false) String diagnosis,
			@RequestParam(value = "patientId", required = false) Patient patient,
			@RequestParam(value = "deathNotes", required = false) String deathNotes,
			@RequestParam(value = "referToMorgue", required = false) String referToMorgue
			){
		EncounterService encounterService = Context.getEncounterService();
		KenyaEmrService kenyaEmrService = Context.getService(KenyaEmrService.class);
		if(StringUtils.isNotBlank(deathDate) && StringUtils.isNotBlank(diagnosis) && patient != null) {
			Date dateOfDeath = DateUtils.getDateFromString(deathDate, "yyyy-MM-dd HH:mm");
			Concept causeOfDeathReason = Context.getConceptService().getConceptByName(diagnosis);

			Encounter enc = getEncounter(patient, PatientQueueUtils.deathEncounterType);

			Obs dateOfDeathObs = new Obs();
			dateOfDeathObs.setObsDatetime(new Date());
			dateOfDeathObs.setConcept(PatientQueueUtils.dateOfDeathQuestion);
			dateOfDeathObs.setCreator(Context.getAuthenticatedUser());
			dateOfDeathObs.setValueDatetime(dateOfDeath);
			dateOfDeathObs.setPerson(patient);
			dateOfDeathObs.setDateCreated(new Date());
			dateOfDeathObs.setLocation(kenyaEmrService.getDefaultLocation());
			dateOfDeathObs.setEncounter(enc);
			dateOfDeathObs.setPerson(enc.getPatient());
			//enc.addObs(dateOfDeathObs);
			//save this obs in the DB
			//obsService.saveObs(dateOfDeathObs, "Adding obs");

			Obs causeOfDeathReasonObs = new Obs();
			causeOfDeathReasonObs.setObsDatetime(new Date());
			causeOfDeathReasonObs.setConcept(PatientQueueUtils.causeOfDeathQuestion);
			causeOfDeathReasonObs.setCreator(Context.getAuthenticatedUser());
			causeOfDeathReasonObs.setValueCoded(causeOfDeathReason);
			causeOfDeathReasonObs.setPerson(patient.getPerson());
			causeOfDeathReasonObs.setDateCreated(new Date());
			//causeOfDeathReasonObs.setEncounter(enc);
			causeOfDeathReasonObs.setLocation(kenyaEmrService.getDefaultLocation());
			//enc.addObs(causeOfDeathReasonObs);


			if(StringUtils.isNotBlank(deathNotes)) {
			Obs causeOfDeathReasonNonCodedObs =  new Obs();
			causeOfDeathReasonNonCodedObs.setObsDatetime(new Date());
			causeOfDeathReasonNonCodedObs.setConcept(PatientQueueUtils.causeOfDeathNonCodedQuestion);
			causeOfDeathReasonNonCodedObs.setCreator(Context.getAuthenticatedUser());
			causeOfDeathReasonNonCodedObs.setValueText(deathNotes);
			causeOfDeathReasonNonCodedObs.setPerson(patient);
			causeOfDeathReasonNonCodedObs.setDateCreated(new Date());
			//causeOfDeathReasonNonCodedObs.setEncounter(enc);
			causeOfDeathReasonNonCodedObs.setLocation(kenyaEmrService.getDefaultLocation());
			//enc.addObs(causeOfDeathReasonNonCodedObs);
			}
			//if(enc.getObs() != null) {
				encounterService.saveEncounter(enc);

				patient.setDead(true);
				patient.setDeathDate(dateOfDeath);
				patient.setCauseOfDeath(causeOfDeathReason);
				//Context.getPatientService().savePatient(patient);
				//check if the patient is referred to the morgue
				if (referToMorgue.equals("yes")) {
					EhrMorgueQueue ehrMorgueQueue = new EhrMorgueQueue();
					ehrMorgueQueue.setPatientId(patient.getPatientId());
					ehrMorgueQueue.setCreatedOn(new Date());
					ehrMorgueQueue.setCreatedBy(Context.getAuthenticatedUser().getUserId());
					ehrMorgueQueue.setReasonOfDeath(causeOfDeathReason);
					ehrMorgueQueue.setDateAndTimeOfDeath(dateOfDeath);
					ehrMorgueQueue.setStatus(0);
					if(StringUtils.isNotBlank(deathNotes)){
						ehrMorgueQueue.setNotes(deathNotes);
					}

					//save the object in the
					Context.getService(HospitalCoreService.class).saveEhrMorgueQueue(ehrMorgueQueue);
				}
			//}

		}
	return "Death Certification Done!!";
	}

	private Encounter getEncounter(Patient patient, EncounterType encounterType) {
		Encounter encounter = new Encounter();
		encounter.setLocation(Context.getService(KenyaEmrService.class).getDefaultLocation());
		encounter.setEncounterType(encounterType);
		encounter.setEncounterDatetime(new Date());
		encounter.setProvider(HospitalCoreUtils.getDefaultEncounterRole(), HospitalCoreUtils.getProvider(Context.getAuthenticatedUser().getPerson()));
		encounter.setPatient(patient);
		encounter.setCreator(Context.getAuthenticatedUser());
		encounter.setDateCreated(new Date());

		return encounter;
	}
}
