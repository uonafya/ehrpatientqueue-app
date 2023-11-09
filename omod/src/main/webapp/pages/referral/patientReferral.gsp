<%
	ui.decorateWith("appui", "standardEmrPage", [ layout: "sidebar" ])

	def menuItems = [
                      [
                          label: "Facility referred",
                          href: ui.pageLink("patientqueueapp", "referral/referredPatients"),
                          iconProvider: "patientqueueapp",
                          icon: "ehr_referrals_facility"
                      ],
                      [
                          label: "Community referred",
                          href: ui.pageLink("patientqueueapp", "referral/communityReferredPatients"),
                          iconProvider: "patientqueueapp",
                          icon: "community_referral"
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