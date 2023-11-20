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
  jq("#ftabs").tabs();
  jq("#getReferrals").on("click", function (e) {
        e.preventDefault();
  jQuery.getJSON('${ ui.actionLink("kenyaemrIL", "referralsDataExchange", "pullCommunityReferralsFromFhir")}')
         .success(function (data) {
          if(data.status === "Success") {
              setTimeout(function (){
                  location.reload();
              }, 2000);
          }else{
              console.log("Data ==>"+data);
          }
         })
      .fail(function (err) {
          console.log("Error fetching referral records: " + JSON.stringify(err));
          }
      )
  });
});
</script>
<div class="ke-page-sidebar">
    ${ ui.includeFragment("kenyaui", "widget/panelMenu", [ heading: "Tasks", items: menuItems ]) }
</div>
<div class="ke-page-content">
    <br />
    <div style="float: right;">
        <ul>
            <li id="getReferrals" class="ui-state-default">
                <a class="button confirm" style="color:#fff">
                    <i class="icon-refresh"></i>
                    Pull Referrals
                </a>
            </li>
        </ul>
    </div>
    <br />
    <div>&nbsp;</div>
    <div id="ftabs">
      <ul>
          <li id="activefTFacilitiesR"><a href="#afToFacilitiesReferrals">Active referral to this facility</a></li>
          <li id="servicedfTFacilitiesR"><a href="#sfToFacilitiesReferrals">Serviced referral to this facility</a></li>
          <li id="tFacilitiesR"><a href="#toFacilitiesReferrals">Referral from this facility to other facilities</a></li>
          <li id="fFacilitiesR"><a href="#fromFacilitiesReferrals">Referral from this facility to community</a></li>
      </ul>
      <div id="afToFacilitiesReferrals">
            ${ ui.includeFragment("patientqueueapp", "referral/activeFromFacilitiesAndCommunityUnits")}
      </div>
      <div id="sfToFacilitiesReferrals">
            ${ ui.includeFragment("patientqueueapp", "referral/servicedFromFacilitiesAndCommunityUnits")}
      </div>
      <div id="toFacilitiesReferrals">
            ${ ui.includeFragment("patientqueueapp", "referral/fromFacilityToFacilitiesReferral")}
      </div>
      <div id="fromFacilitiesReferrals">
        ${ ui.includeFragment("patientqueueapp", "referral/fromFacilitiesToCommunityReferrals") }
      </div>
    </div>
</div>

<div id="view-patient-shr-details-dialog" class="dialog" style="display:none;  height: auto !important; width: 650px; !important;">
    <div class="dialog-header">
        <i class="icon-folder-open"></i>
        <h3>Patient SHR Details of <span id="pDetails"></span></h3>
    </div>
    <input type="hidden" id="activeId" name="activeId" />
    <input type="hidden" id="nupiNumber" name="nupiNumber" />
    <input type="hidden" id="firstName" name="firstName" />
    <input type="hidden" id="middleName" name="middleName" />
    <input type="hidden" id="familyName" name="familyName" />
    <input type="hidden" id="sex" name="sex" />
    <input type="hidden" id="dob" name="dob" />
    <div class="dialog-content">
        <div>
          <table border="0" cellpadding="0" cellspacing="0" id="patientShrDtls" width="100%">
              <tr>
                  <td>Date of Referral</td>
                  <td><span id="dateOfReferral"</span></td>
              </tr>
              <tr>
                  <td>Category</td>
                  <td><span id="category"</span></td>
              </tr>
              <tr>
                  <td>Reason for referral</td>
                  <td><span id="reason"</span></td>
              </tr>
              <tr>
                  <td>Clinical notes</td>
                  <td><span id="notes"</span></td>
              </tr>
          </table>
        </div>
        <br />
        <div class="onerow">
            <button class="button confirm right">Serve Client</button>
            <button class="button cancel">Cancel</button>
        </div>
    </div>
</div>