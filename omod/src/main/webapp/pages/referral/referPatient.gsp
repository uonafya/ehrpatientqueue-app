<%
    ui.decorateWith("kenyaemr", "standardPage", [ patient: currentPatient ])
    ui.includeJavascript("financials", "jquery.dataTables.min.js")
    ui.includeJavascript("ehrconfigs", "emr.js")
    ui.includeJavascript("ehrconfigs", "jquery.simplemodal.1.4.4.min.js")
    ui.includeCss("ehrconfigs", "referenceapplication.css")
    ui.includeCss("financials", "jquery.dataTables.min.css")
%>
<script type="text/javascript">
    jq(document).ready(function () {
        jq("#rtabs").tabs();
    });
</script>

<div class="ke-page-content">
    <div id="rtabs">
            <ul>
                <li id="rhistory"><a href="#history">Patient History</a></li>
                <li id="refer"><a href="#referral">Patient Referral</a></li>
            </ul>
            <div id="history">
              ${ ui.includeFragment("patientqueueapp", "referral/referralHistory") }
            </div>
            <div id="referral">
                  ${ ui.includeFragment("patientqueueapp", "referral/actualReferral")}
            </div>
    </div>
</div>