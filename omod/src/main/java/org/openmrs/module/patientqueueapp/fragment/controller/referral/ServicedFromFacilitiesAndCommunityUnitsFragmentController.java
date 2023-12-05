package org.openmrs.module.patientqueueapp.fragment.controller.referral;

import ca.uhn.fhir.parser.IParser;
import org.hl7.fhir.r4.model.ServiceRequest;
import org.openmrs.api.context.Context;
import org.openmrs.module.kenyaemrIL.api.KenyaEMRILService;
import org.openmrs.module.kenyaemrIL.api.shr.FhirConfig;
import org.openmrs.module.kenyaemrIL.programEnrollment.ExpectedTransferInPatients;
import org.openmrs.module.kenyaui.KenyaUiUtils;
import org.openmrs.module.patientqueueapp.model.ServiceRequestSimplifier;
import org.openmrs.ui.framework.SimpleObject;
import org.openmrs.ui.framework.UiUtils;
import org.openmrs.ui.framework.annotation.SpringBean;
import org.openmrs.ui.framework.fragment.FragmentModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ServicedFromFacilitiesAndCommunityUnitsFragmentController {

    public void controller(FragmentModel model, @SpringBean KenyaUiUtils kenyaUi, UiUtils ui) {
        KenyaEMRILService ilService = Context.getService(KenyaEMRILService.class);
        FhirConfig fhirConfig = Context.getRegisteredComponents(FhirConfig.class).get(0);

        List<ExpectedTransferInPatients> completedCommunityReferralsList = ilService.getCommunityReferrals("COMMUNITY","COMPLETED");


        List<SimpleObject> completedReferrals = new ArrayList<SimpleObject>();
        ServiceRequestSimplifier serviceRequestSimplifier = null;
        List<ServiceRequestSimplifier> completedRequestSimplifiers = new ArrayList<ServiceRequestSimplifier>();


        for (ExpectedTransferInPatients expectedTransferInPatients : completedCommunityReferralsList) {
            IParser parser = fhirConfig.getFhirContext().newJsonParser().setPrettyPrint(true);
            ServiceRequest serviceRequest = parser.parseResource(ServiceRequest.class, expectedTransferInPatients.getPatientSummary());
            String requester = "";
            if (serviceRequest.hasRequester()) {
                if (serviceRequest.getRequester().getDisplay() != null) {
                    requester = serviceRequest.getRequester().getDisplay();
                }
            }
            serviceRequestSimplifier = new ServiceRequestSimplifier();
            serviceRequestSimplifier.setId(expectedTransferInPatients.getId());
            serviceRequestSimplifier.setUuid(expectedTransferInPatients.getUuid());
            serviceRequestSimplifier.setNupi(expectedTransferInPatients.getNupiNumber());
            serviceRequestSimplifier.setDateReferred(serviceRequest.getAuthoredOn() != null? new SimpleDateFormat("yyyy-MM-dd hh:mm").format(serviceRequest.getAuthoredOn()):"");
            serviceRequestSimplifier.setReferredFrom(requester);
            serviceRequestSimplifier.setGivenName(expectedTransferInPatients.getClientFirstName() != null ? expectedTransferInPatients.getClientFirstName() : "");
            serviceRequestSimplifier.setMiddleName(expectedTransferInPatients.getClientMiddleName() != null ? expectedTransferInPatients.getClientMiddleName() : "");
            serviceRequestSimplifier.setFamilyName(expectedTransferInPatients.getClientLastName() != null ? expectedTransferInPatients.getClientLastName() : "");
            serviceRequestSimplifier.setBirthdate(kenyaUi.formatDate(expectedTransferInPatients.getClientBirthDate()));
            serviceRequestSimplifier.setGender(expectedTransferInPatients.getClientGender());
            serviceRequestSimplifier.setStatus(expectedTransferInPatients.getReferralStatus());

            SimpleObject completedReferralsObject = SimpleObject.create("id", expectedTransferInPatients.getId(),
                    "uuid", expectedTransferInPatients.getUuid(),
                    "nupi", expectedTransferInPatients.getNupiNumber(),
                    "dateReferred", serviceRequest.getAuthoredOn() != null? new SimpleDateFormat("yyyy-MM-dd").format(serviceRequest.getAuthoredOn()):"",
                    "referredFrom", requester,
                    "givenName", expectedTransferInPatients.getClientFirstName() != null ? expectedTransferInPatients.getClientFirstName() : "",
                    "middleName", expectedTransferInPatients.getClientMiddleName() != null ? expectedTransferInPatients.getClientMiddleName() : "",
                    "familyName", expectedTransferInPatients.getClientLastName() != null ? expectedTransferInPatients.getClientLastName() : "",
                    "birthdate", kenyaUi.formatDate(expectedTransferInPatients.getClientBirthDate()),
                    "gender", expectedTransferInPatients.getClientGender(),
                    "status", expectedTransferInPatients.getReferralStatus());
            completedReferrals.add(completedReferralsObject);

            completedRequestSimplifiers.add(serviceRequestSimplifier);
        }

        model.put("completedReferralList", completedRequestSimplifiers);
    }
}
