<%
	ui.decorateWith("appui", "standardEmrPage", [ layout: "sidebar" ])
	ui.includeJavascript("ehrconfigs", "emr.js")

	def menuItems = [

              [ label: "Back to home",
                iconProvider: "kenyaui",
                icon: "buttons/back.png",
                href: ui.pageLink("patientqueueapp", "referral/patientReferral")
              ]
      ]
%>
<script type="text/javascript">
    jq(document).ready(function () {
        jq("#ftabs").tabs();
    });
</script>
<div class="ke-page-sidebar">
    ${ ui.includeFragment("kenyaui", "widget/panelMenu", [ heading: "Tasks", items: menuItems ]) }
</div>
<div class="ke-page-content">
    <div id="ftabs">
            <ul>
                <li id="tFacilitiesR"><a href="#toFacilitiesReferrals">To other facilities</a></li>
                <li id="fFacilitiesR"><a href="#fromFacilitiesReferrals">From other facilities</a></li>
            </ul>

            <div id="toFacilitiesReferrals">
                  ${ ui.includeFragment("patientqueueapp", "referral/toFacilitiesReferral")}
            </div>
            <div id="fromFacilitiesReferrals">
              ${ ui.includeFragment("patientqueueapp", "referral/fromFacilitiesReferrals") }
            </div>
    </div>
</div>