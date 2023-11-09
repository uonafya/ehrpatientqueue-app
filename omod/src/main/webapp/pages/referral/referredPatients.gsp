<%
	ui.decorateWith("appui", "standardEmrPage", [ layout: "sidebar" ])
	ui.includeCss("ehrconfigs", "referenceapplication.css")
	ui.includeJavascript("ehrconfigs", "emr.js")
%>
<script type="text/javascript">
    jq(document).ready(function () {
        jq("#ftabs").tabs();
    });
</script>
<div class="ke-page-content">
    <div id="ftabs">
            <ul>
                <li id="fFacilities"><a href="#from-facilities">From other facilities</a></li>
                <li id="tFacilities"><a href="#to-facilities">To other facilities</a></li>
            </ul>
            <div id="from-facilities">
              ${ ui.includeFragment("patientqueueapp", "referral/fromFacilities") }
          <div id="to-facilities">
                ${ ui.includeFragment("patientqueueapp", "referral/toFacilities")}
          </div>
    </div>
</div>