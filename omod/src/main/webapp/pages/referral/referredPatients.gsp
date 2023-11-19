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
      var activeReferralsTbl = jq('#activeReferralsTbl').DataTable();
      var completedReferralsTbl = jq('#completedReferralsTbl').DataTable();
      populateTableBodyForActiveReferralSummaryItems(${activeReferralList});
      populateTableBodyForServicedReferralSummaryItems(${completedReferralList});
      var patientShrDetailsDialog = emr.setupConfirmationDialog({
          dialogOpts: {
              overlayClose: false,
              close: true
          },
          selector: '#view-patient-shr-details-dialog',
          actions: {
              confirm: function () {
                  jq.getJSON('${ui.actionLink("patientqueueapp", "referral/referralHistory", "addPatientLocallyAndRedirect")}',
                      {
                          activeId: jq("#activeId").val()
                      }
                  ).success(function (data) {
                     //redirect the page to initial patient queue app
                  });
              },
              cancel: function () {
                  patientShrDetailsDialog.close();
                  location.reload();
              }
          }
      });
      jq('#activeReferralsTbl tbody').on( 'click', 'tr', function (e) {
            e.preventDefault();
            patientShrDetailsDialog.show();
            var values = activeReferralsTbl.row(this).data();
            //console.log(values.nipi);
            //jQuery('#activeId').val(values[5]);
            //getClientShrDetails();
            //patientHistoryDialog.show();

      });
    });
    function populateTableBodyForActiveReferralSummaryItems(data) {
    jQuery("#activeReferralsTbl").DataTable().clear().destroy();
      data.map((item) => {
        jQuery("#activeReferralTbody").append("<tr><td>" + item.givenName+ "</td><td>" + item.middleName + "</td><td>" + item.familyName+ "</td><td>" + item.gender +"</td><td>" + item.birthdate +"</td><td>" + item.nupi +"</td><td>" + item.dateReferred
 +"</td><td>" + item.referredFrom + "</td></tr>");
      });
    }
    function populateTableBodyForServicedReferralSummaryItems(data) {
      jQuery("#completedReferralsTbl").DataTable().clear().destroy();
          data.map((item) => {
            jQuery("#servicedReferralTbody").append("<tr><td>" + item.givenName+ "</td><td>" + item.middleName + "</td><td>" + item.familyName+ "</td><td>" + item.gender +"</td><td>" + item.birthdate +"</td><td>" + item.nupi +"</td><td>" + item.dateReferred
     +"</td><td>" + item.referredFrom + "</td></tr>");
          });
    }
    function getClientShrDetails(){
      jq.getJSON('${ ui.actionLink("patientqueueapp", "referral/referralHistory", "getPatientShrHistory") }', {
                      activeId: jq("#activeId").val()
      }).success(function (data) {
        //populateTableHistorySummary(data);
        console.log("Waiting for input from user");
      });
    }
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

    <div id="view-patient-shr-details-dialog" class="dialog" style="display:none;  height: auto !important; width: 650px; !important;">
            <div class="dialog-header">
                <i class="icon-folder-open"></i>
                <h3>Patient SHR Details</h3>
            </div>
            <input type="text" id="activeId" name="activeId" />
            <div class="dialog-content">
                <div>
                  <table border="0" cellpadding="0" cellspacing="0" id="patientShrDtls" width="100%">
                      <tr>
                          <td>Category</td>
                          <td>&nbsp;</td>
                      </tr>
                      <tr>
                          <td>Reason for referral</td>
                          <td>&nbsp;</td>
                      </tr>
                      <tr>
                          <td>Clinical notes</td>
                          <td>&nbsp;</td>
                      </tr>
                  </table>
                </div>
                <div class="onerow">
                    <button class="button confirm right">Confirm</button>
                    <button class="button cancel">Cancel</button>
                </div>
            </div>
        </div>
</div>