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
       			//Run the fetch tasks
      console.log('Starting the fetch task!');
      jQuery.getJSON('${ ui.actionLink("kenyaemrIL", "referralsDataExchange", "pullCommunityReferralsFromFhir")}')
             .success(function (data) {
              if(data.status === "Success") {
                  // Hide spinner
                  //display_loading_spinner(false, 'wait-loading');
                  console.log("Data ==>"+data);
                  console.log(data.message);
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

      jq('#activeReferralsTbl tbody').on( 'click', 'tr', function (e) {
            e.preventDefault();
            var values = tbl.row(this).data();
            console.log(values);
            jQuery('#activeId').val(values[5]);
            getClientShrDetails();
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
</div>