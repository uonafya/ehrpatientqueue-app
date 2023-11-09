<%
	ui.decorateWith("appui", "standardEmrPage", [ layout: "sidebar" ])
	ui.includeCss("ehrconfigs", "referenceapplication.css")
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
          <div id="to-community">
                ${ ui.includeFragment("patientqueueapp", "referral/toCommunityUnits")}
          </div>
    </div>
</div>

