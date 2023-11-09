<%
	ui.decorateWith("appui", "standardEmrPage", [ layout: "sidebar" ])

	def menuItems = [
                      [
                          label: "Facility referrals",
                          href: ui.pageLink("patientqueueapp", "referral/referredPatients"),
                          iconProvider: "patientqueueapp",
                          icon: "ehr_referrals_facility.png"
                      ],
                      [
                          label: "Community referrals",
                          href: ui.pageLink("patientqueueapp", "referral/communityReferredPatients"),
                          iconProvider: "patientqueueapp",
                          icon: "community_referral.png"
                      ]
              ]
%>
<div class="ke-page-sidebar">
    ${ ui.includeFragment("kenyaemr", "patient/patientSearchForm", [ defaultWhich: "all" ]) }
    ${ ui.includeFragment("kenyaui", "widget/panelMenu", [ heading: "Tasks", items: menuItems ]) }
</div>
<div class="ke-page-content">
        ${ ui.includeFragment("kenyaemr", "patient/patientSearchResults", [ pageProvider: "patientqueueapp", page: "referral/referPatient" ]) }
</div>
<script type="text/javascript">
    jQuery(function() {
        jQuery('input[name="query"]').focus();
    });
</script>