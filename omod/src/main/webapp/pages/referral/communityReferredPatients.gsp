<%
	ui.decorateWith("appui", "standardEmrPage", [ layout: "sidebar" ])
	ui.includeJavascript("financials", "jquery.dataTables.min.js")
  ui.includeJavascript("ehrconfigs", "emr.js")
  ui.includeJavascript("ehrconfigs", "jquery.simplemodal.1.4.4.min.js")
  ui.includeCss("ehrconfigs", "referenceapplication.css")
  ui.includeCss("financials", "jquery.dataTables.min.css")

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
        jq("#ctabs").tabs();
    });
</script>
<div class="ke-page-sidebar">
    ${ ui.includeFragment("kenyaui", "widget/panelMenu", [ heading: "Tasks", items: menuItems ]) }
</div>
<div class="ke-page-content">
    <div id="ctabs">
            <ul>
                <li id="fCommunity"><a href="#from-community">From Community Units</a></li>
                <li id="tCommunity"><a href="#to-community">To Community Units</a></li>
            </ul>
            <div id="from-community">
              ${ ui.includeFragment("patientqueueapp", "referral/fromCommunityUnits") }
            </div>
            <div id="to-community">
                  ${ ui.includeFragment("patientqueueapp", "referral/toCommunityUnits")}
            </div>
    </div>
</div>

