<%
    ui.decorateWith("kenyaemr", "standardPage", [ patient: currentPatient ])
    ui.includeCss("ehrconfigs", "referenceapplication.css")
%>
<script type="text/javascript">
    jq(document).ready(function () {
        jq("#itabs").tabs();
    });
</script>

<div class="ke-page-content">
    <div id="itabs">
            <ul>
                <li id="history"><a href="#history">Patient History</a></li>
                <li id="refer"><a href="#referral">Patient Referral</a></li>
            </ul>
            <div id="history">
              ${ ui.includeFragment("patientqueueapp", "referral/referralHistory") }
          <div id="referral">
                ${ ui.includeFragment("patientqueueapp", "referral/")}
          </div>
    </div>
</div>